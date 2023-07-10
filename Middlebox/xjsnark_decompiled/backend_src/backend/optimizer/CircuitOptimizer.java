package backend.optimizer;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.WireLabelInstruction;
import backend.operations.primitive.AddBasicOp;
import backend.operations.primitive.AssertBasicOp;
import backend.operations.primitive.BasicOp;
import backend.operations.primitive.ConstMulBasicOp;
import backend.operations.primitive.MulBasicOp;
import backend.operations.primitive.NonZeroCheckBasicOp;
import backend.operations.primitive.ORBasicOp;
import backend.operations.primitive.PackBasicOp;
import backend.operations.primitive.SplitBasicOp;
import backend.operations.primitive.XorBasicOp;
import backend.optimizer.arithmetic.ExpressionMinimizer;
import backend.optimizer.arithmetic.poly.MultivariatePolynomial;
import backend.optimizer.arithmetic.poly.OptVariable;
import backend.optimizer.arithmetic.poly.Term;
import backend.resource.ResourceBundle;
import backend.structure.CircuitGenerator;
import backend.structure.ConstantWire;
import backend.structure.Wire;
import backend.structure.WireArray;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import util.Util;

public class CircuitOptimizer {
   private int inVarCounter = 0;
   private int outVarCounter = 0;
   private int newWireCount = 0;
   private int numProblemsOptimized = 0;
   private LinkedHashMap<Wire, Integer> useCounters = new LinkedHashMap();
   private LinkedHashMap<Wire, Boolean> toOverride = new LinkedHashMap();
   private LinkedHashSet<OptVariable> allOptVariables = new LinkedHashSet();
   private LinkedHashMap<OptVarPair, ArrayList<Problem>> optVarProblemMap = new LinkedHashMap();
   private LinkedHashMap<OptVariable, ArrayList<Wire>> optVarDependenciesMap = new LinkedHashMap();
   private LinkedHashMap<OptVariable, Wire> optVarWireMap = new LinkedHashMap();
   private LinkedHashMap<Wire, Problem> problemMap = new LinkedHashMap();
   private LinkedHashMap<Wire, Instruction[]> originalEvalSequenceMap = new LinkedHashMap();
   private LinkedHashMap<Wire, MultivariatePolynomial> mvpMap = new LinkedHashMap();
   private LinkedHashMap<Instruction, Instruction> evalSequence;
   private LinkedHashMap<Instruction, Instruction> tmpEvalSequence;
   private LinkedHashMap<Instruction, Instruction> newEvalSequence;
   private HashMap<Integer, Integer> newToOldIndexMap = new HashMap(16, 1.0F);
   ArrayList<Problem> combinedProblems = new ArrayList();
   private CircuitGenerator generator;
   private int numOriginalMulGates;
   private int numOriginalIns;
   private int numOriginalOuts;
   private int numOriginalWitnesses;
   private int numOriginalSplits;
   private int numOriginalPacks;
   private int numOriginalAssertions;
   private int numOriginalNonzeroChecks;
   private int totalSavings = 0;
   private int originalEvals = 0;

   public CircuitOptimizer(CircuitGenerator generator) {
      this.evalSequence = generator.__getEvaluationQueue();
      this.generator = generator;
      this.numOriginalMulGates = generator.__getNumOfConstraints();
      this.tmpEvalSequence = new LinkedHashMap();
      this.run();
   }

   private void run() {
      BasicOp.setDisableCachingForLinearOps(true);
      this.firstPass();
      System.out.println("[Arithmetic Optimizer] First Stage Done");
      this.secondPass();
      System.out.println("[Arithmetic Optimizer] Second Stage Done");
      this.thirdPass();
   }

   private void groupProblems() {
      if (!Config.arithOptimizerIncrementalMode) {
         System.out.println("[Arithmetic Optimizer] Grouping Problems .. ");
      }

      HashMap<OptVarPair, Boolean> state = new HashMap();
      Iterator var3 = this.optVarProblemMap.keySet().iterator();

      while(var3.hasNext()) {
         OptVarPair p = (OptVarPair)var3.next();
         state.put(p, false);
      }

      int numProcessed = 0;
      HashSet<Problem> problemCollection = new LinkedHashSet();
      HashSet<Problem> visitedProblems = new HashSet();
      Queue<OptVarPair> varQueue = new LinkedList();
      Iterator var7 = this.optVarProblemMap.keySet().iterator();

      label75:
      while(true) {
         OptVarPair pair;
         do {
            if (!var7.hasNext()) {
               if (!Config.arithOptimizerIncrementalMode) {
                  System.out.println("[Arithmetic Optimizer] Done with Grouping Problems");
                  System.out.println("[Arithmetic Optimizer] Number of problems after clustering: " + this.combinedProblems.size());
               } else {
                  System.out.println("[Arithmetic Optimizer] Number of remaining problems: " + this.combinedProblems.size());
               }

               return;
            }

            pair = (OptVarPair)var7.next();
         } while((Boolean)state.get(pair));

         state.put(pair, true);
         ++numProcessed;
         problemCollection.clear();
         varQueue.clear();
         ArrayList<Problem> list = (ArrayList)this.optVarProblemMap.get(pair);
         problemCollection.addAll(list);
         Iterator var10 = list.iterator();

         while(var10.hasNext()) {
            Problem p = (Problem)var10.next();
            if (!visitedProblems.contains(p)) {
               visitedProblems.add(p);
               varQueue.addAll(p.optVarPairs);
            }
         }

         label72:
         while(true) {
            OptVarPair pair2;
            do {
               if (varQueue.isEmpty()) {
                  this.combinedProblems.add(new Problem(problemCollection));
                  continue label75;
               }

               pair2 = (OptVarPair)varQueue.poll();
            } while((Boolean)state.get(pair2));

            list = (ArrayList)this.optVarProblemMap.get(pair2);
            problemCollection.addAll(list);
            state.put(pair2, true);
            Iterator var11 = list.iterator();

            while(true) {
               Problem p;
               do {
                  if (!var11.hasNext()) {
                     ++numProcessed;
                     continue label72;
                  }

                  p = (Problem)var11.next();
               } while(visitedProblems.contains(p));

               visitedProblems.add(p);
               Iterator var13 = p.optVarPairs.iterator();

               while(var13.hasNext()) {
                  OptVarPair pair3 = (OptVarPair)var13.next();
                  if (!(Boolean)state.get(pair3)) {
                     varQueue.add(pair3);
                  }
               }
            }
         }
      }
   }

   private void firstPass() {
      System.out.println("[Arithmetic Optimizer] Starting First Optimization Stage");
      int counter = 0;
      int numProblems = false;
      int step = this.evalSequence.keySet().size() / 10;
      Iterator var5 = this.evalSequence.keySet().iterator();

      while(true) {
         WireLabelInstruction labelInstruction;
         label139:
         do {
            while(var5.hasNext()) {
               Instruction e = (Instruction)var5.next();
               ++counter;
               if (counter % step == 0 && !Config.arithOptimizerDisableProgress) {
                  System.out.println("[Arithmetic Optimizer] Progress = " + Math.ceil((double)((float)counter * 1.0F / (float)this.evalSequence.size() * 100.0F)) + "%");
               }

               if (e instanceof WireLabelInstruction) {
                  labelInstruction = (WireLabelInstruction)e;
                  this.tmpEvalSequence.put(labelInstruction, labelInstruction);
                  if (labelInstruction.getType() == WireLabelInstruction.LabelType.input) {
                     ++this.numOriginalIns;
                  }

                  if (labelInstruction.getType() == WireLabelInstruction.LabelType.nizkinput) {
                     ++this.numOriginalWitnesses;
                  }

                  if (labelInstruction.getType() == WireLabelInstruction.LabelType.output) {
                     ++this.numOriginalOuts;
                     Integer c = (Integer)this.useCounters.get(labelInstruction.getWire());
                     if (c == null) {
                        c = 0;
                     }

                     this.useCounters.put(labelInstruction.getWire(), c + 1);
                     this.toOverride.put(labelInstruction.getWire(), false);
                  }
                  continue label139;
               }

               if (e instanceof BasicOp) {
                  BasicOp op = (BasicOp)e;
                  Wire[] inputs = op.getInputs();
                  Wire[] outputs = op.getOutputs();
                  Wire[] var12 = inputs;
                  int var11 = inputs.length;

                  Wire w;
                  int var10;
                  Integer c;
                  for(var10 = 0; var10 < var11; ++var10) {
                     w = var12[var10];
                     c = (Integer)this.useCounters.get(w);
                     if (c == null) {
                        c = 0;
                     }

                     this.useCounters.put(w, c + 1);
                     this.toOverride.put(w, c + 1 == 1);
                  }

                  if (op instanceof SplitBasicOp) {
                     ++this.numOriginalSplits;
                  }

                  if (op instanceof PackBasicOp) {
                     ++this.numOriginalPacks;
                  }

                  if (op instanceof NonZeroCheckBasicOp) {
                     ++this.numOriginalNonzeroChecks;
                  }

                  if (op instanceof SplitBasicOp || op instanceof PackBasicOp || op instanceof NonZeroCheckBasicOp) {
                     var12 = outputs;
                     var11 = outputs.length;

                     for(var10 = 0; var10 < var11; ++var10) {
                        w = var12[var10];
                        OptVariable variable = new OptVariable("w", w.getWireId(), !(op instanceof PackBasicOp));
                        this.allOptVariables.add(variable);
                        this.optVarWireMap.put(variable, w);
                        MultivariatePolynomial mvp = new MultivariatePolynomial(variable);
                        this.mvpMap.put(w, mvp);
                     }

                     var12 = inputs;
                     var11 = inputs.length;

                     for(var10 = 0; var10 < var11; ++var10) {
                        Wire var10000 = var12[var10];
                     }

                     this.tmpEvalSequence.put(op, op);
                  }

                  if (op instanceof AssertBasicOp) {
                     ++this.numOriginalAssertions;
                     var12 = inputs;
                     var11 = inputs.length;

                     for(var10 = 0; var10 < var11; ++var10) {
                        w = var12[var10];
                        this.toOverride.put(w, false);
                     }

                     var12 = outputs;
                     var11 = outputs.length;

                     for(var10 = 0; var10 < var11; ++var10) {
                        w = var12[var10];
                        c = (Integer)this.useCounters.get(w);
                        if (c == null) {
                           c = 0;
                        }

                        this.useCounters.put(w, c + 1);
                        this.toOverride.put(w, false);
                     }

                     this.tmpEvalSequence.put(op, op);
                  }
               }
            }

            return;
         } while(labelInstruction.getType() != WireLabelInstruction.LabelType.input && labelInstruction.getType() != WireLabelInstruction.LabelType.nizkinput);

         if (labelInstruction.getWire() == this.generator.__getOneWire()) {
            MultivariatePolynomial mvp = new MultivariatePolynomial(new Term(BigInteger.ONE));
            this.mvpMap.put(labelInstruction.getWire(), mvp);
         } else {
            OptVariable variable = new OptVariable("w", labelInstruction.getWire().getWireId());
            this.allOptVariables.add(variable);
            this.optVarWireMap.put(variable, labelInstruction.getWire());
            MultivariatePolynomial mvp = new MultivariatePolynomial(variable);
            this.mvpMap.put(labelInstruction.getWire(), mvp);
         }
      }
   }

   private void secondPass() {
      System.out.println("[Arithmetic Optimizer] Starting Second Optimization Stage");
      if (Config.arithOptimizerIncrementalMode) {
         System.out.println("[Arithmetic Optimizer] Incremental mode is activtated. This is a new option under testing.");
      }

      int counter = 0;
      int step = this.evalSequence.keySet().size() / 10;
      Iterator var4 = this.evalSequence.keySet().iterator();

      while(true) {
         while(var4.hasNext()) {
            Instruction e = (Instruction)var4.next();
            ++counter;
            if (counter % step == 0) {
               if (!Config.arithOptimizerDisableProgress && !Config.arithOptimizerIncrementalMode) {
                  System.out.println("[Arithmetic Optimizer] Progress = " + Math.ceil((double)((float)counter * 1.0F / (float)this.evalSequence.size() * 100.0F)) + "%");
               }

               if (Config.arithOptimizerIncrementalMode) {
                  this.checkForSolvableProblems((float)counter * 1.0F / (float)this.evalSequence.size() * 100.0F);
               }
            }

            if (e instanceof WireLabelInstruction) {
               WireLabelInstruction label = (WireLabelInstruction)e;
               if (label.getType() != WireLabelInstruction.LabelType.input && label.getType() != WireLabelInstruction.LabelType.nizkinput) {
                  this.problemMap.put(label.getWire(), new Problem(label.getWire(), (MultivariatePolynomial)this.mvpMap.get(label.getWire())));
               }
            } else if (e instanceof BasicOp) {
               BasicOp op = (BasicOp)e;
               Wire[] inputs = op.getInputs();
               Wire[] outputs = op.getOutputs();
               Wire[] var11 = inputs;
               int i = inputs.length;

               Wire w;
               int idx;
               Integer c;
               for(idx = 0; idx < i; ++idx) {
                  w = var11[idx];
                  c = (Integer)this.useCounters.get(w);
                  this.useCounters.put(w, c - 1);
               }

               if (op instanceof AssertBasicOp) {
                  var11 = outputs;
                  i = outputs.length;

                  for(idx = 0; idx < i; ++idx) {
                     w = var11[idx];
                     c = (Integer)this.useCounters.get(w);
                     this.useCounters.put(w, c - 1);
                  }
               }

               if (!(op instanceof SplitBasicOp) && !(op instanceof PackBasicOp) && !(op instanceof NonZeroCheckBasicOp)) {
                  if (op instanceof AssertBasicOp) {
                     var11 = inputs;
                     i = inputs.length;

                     for(idx = 0; idx < i; ++idx) {
                        w = var11[idx];
                        if (this.problemMap.get(w) == null) {
                           this.problemMap.put(w, new Problem(w, (MultivariatePolynomial)this.mvpMap.get(w)));
                        }
                     }

                     var11 = outputs;
                     i = outputs.length;

                     for(idx = 0; idx < i; ++idx) {
                        w = var11[idx];
                        if (this.problemMap.get(w) == null) {
                           this.problemMap.put(w, new Problem(w, (MultivariatePolynomial)this.mvpMap.get(w)));
                        }
                     }
                  } else {
                     MultivariatePolynomial mvp;
                     OptVariable variable;
                     if (op instanceof AddBasicOp) {
                        idx = this.checkInputToOverride(inputs);
                        if (idx != -1) {
                           mvp = (MultivariatePolynomial)this.mvpMap.get(inputs[idx]);
                           this.mvpMap.put(inputs[idx], (Object)null);
                           if (mvp == null) {
                              System.out.println(op);
                              System.out.println(inputs[idx]);
                              throw new RuntimeException("Unxpected Case : Please report this case.");
                           }

                           for(i = 0; i < inputs.length; ++i) {
                              if (idx != i) {
                                 mvp = mvp.addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[i]));
                              }
                           }
                        } else {
                           mvp = new MultivariatePolynomial();
                           Wire[] var13 = inputs;
                           int var18 = inputs.length;

                           for(int var19 = 0; var19 < var18; ++var19) {
                              Wire w = var13[var19];
                              mvp = mvp.addInPlace((MultivariatePolynomial)this.mvpMap.get(w));
                           }
                        }

                        if (mvp.isCostly()) {
                           if (this.problemMap.get(outputs[0]) == null) {
                              this.problemMap.put(outputs[0], new Problem(outputs[0], mvp));
                           }

                           variable = new OptVariable("w", outputs[0].getWireId());
                           this.allOptVariables.add(variable);
                           this.optVarWireMap.put(variable, outputs[0]);
                           this.mvpMap.put(outputs[0], new MultivariatePolynomial(variable));
                        } else {
                           this.mvpMap.put(outputs[0], mvp);
                        }
                     } else if (op instanceof MulBasicOp) {
                        idx = this.checkInputToOverride(inputs);
                        if (idx != -1) {
                           mvp = (MultivariatePolynomial)this.mvpMap.get(inputs[idx]);
                           this.mvpMap.put(inputs[idx], (Object)null);

                           for(i = 0; i < inputs.length; ++i) {
                              if (idx != i) {
                                 mvp = mvp.multiplyInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[i]));
                              }
                           }
                        } else {
                           mvp = ((MultivariatePolynomial)this.mvpMap.get(inputs[0])).multiply((MultivariatePolynomial)this.mvpMap.get(inputs[1]));
                        }

                        if (mvp.isCostly()) {
                           if (this.problemMap.get(outputs[0]) == null) {
                              this.problemMap.put(outputs[0], new Problem(outputs[0], mvp));
                           }

                           variable = new OptVariable("w", outputs[0].getWireId());
                           this.allOptVariables.add(variable);
                           this.optVarWireMap.put(variable, outputs[0]);
                           this.mvpMap.put(outputs[0], new MultivariatePolynomial(variable));
                        } else {
                           this.mvpMap.put(outputs[0], mvp);
                        }
                     } else {
                        MultivariatePolynomial tmp;
                        if (op instanceof XorBasicOp) {
                           idx = this.checkInputToOverride(inputs);
                           if (idx != -1) {
                              mvp = (MultivariatePolynomial)this.mvpMap.get(inputs[idx]);
                              this.mvpMap.put(inputs[idx], (Object)null);

                              for(i = 0; i < inputs.length; ++i) {
                                 if (idx != i) {
                                    tmp = mvp.multiply((MultivariatePolynomial)this.mvpMap.get(inputs[i]));
                                    tmp = tmp.multiplyConstantInPlace(ResourceBundle.getInstance().getBigInteger(new BigInteger("-2")));
                                    mvp = mvp.addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[i])).addInPlace(tmp);
                                 }
                              }
                           } else {
                              mvp = ((MultivariatePolynomial)this.mvpMap.get(inputs[1])).multiply((MultivariatePolynomial)this.mvpMap.get(inputs[0]));
                              mvp = mvp.multiplyConstantInPlace(ResourceBundle.getInstance().getBigInteger(new BigInteger("-2")));
                              mvp = mvp.addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[1])).addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[0]));
                           }

                           if (mvp.isCostly()) {
                              this.problemMap.get(outputs[0]);
                           }

                           if (mvp.isCostly()) {
                              if (this.problemMap.get(outputs[0]) == null) {
                                 this.problemMap.put(outputs[0], new Problem(outputs[0], mvp));
                              }

                              variable = new OptVariable("w", outputs[0].getWireId(), true);
                              this.allOptVariables.add(variable);
                              this.optVarWireMap.put(variable, outputs[0]);
                              this.mvpMap.put(outputs[0], new MultivariatePolynomial(variable));
                           } else {
                              this.mvpMap.put(outputs[0], mvp);
                           }
                        } else if (!(op instanceof ORBasicOp)) {
                           if (op instanceof ConstMulBasicOp) {
                              idx = this.checkInputToOverride(inputs);
                              if (idx != -1) {
                                 mvp = (MultivariatePolynomial)this.mvpMap.get(inputs[idx]);
                                 this.mvpMap.put(inputs[idx], (Object)null);
                                 mvp.multiplyConstantInPlace(((ConstMulBasicOp)op).getConstInteger());
                              } else {
                                 mvp = (MultivariatePolynomial)this.mvpMap.get(inputs[0]);
                                 mvp = mvp.multiplyConstant(((ConstMulBasicOp)op).getConstInteger());
                              }

                              this.mvpMap.put(outputs[0], mvp);
                           }
                        } else {
                           idx = this.checkInputToOverride(inputs);
                           if (idx != -1) {
                              mvp = (MultivariatePolynomial)this.mvpMap.get(inputs[idx]);
                              this.mvpMap.put(inputs[idx], (Object)null);

                              for(i = 0; i < inputs.length; ++i) {
                                 if (idx != i) {
                                    tmp = mvp.multiply((MultivariatePolynomial)this.mvpMap.get(inputs[i]));
                                    tmp = tmp.multiplyConstantInPlace(ResourceBundle.getInstance().getBigInteger(new BigInteger("-1")));
                                    mvp = mvp.addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[i])).addInPlace(tmp);
                                 }
                              }
                           } else {
                              mvp = ((MultivariatePolynomial)this.mvpMap.get(inputs[1])).multiply((MultivariatePolynomial)this.mvpMap.get(inputs[0]));
                              mvp = mvp.multiplyConstantInPlace(ResourceBundle.getInstance().getBigInteger(new BigInteger("-1")));
                              mvp = mvp.addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[1])).addInPlace((MultivariatePolynomial)this.mvpMap.get(inputs[0]));
                           }

                           if (mvp.isCostly()) {
                              if (this.problemMap.get(outputs[0]) == null) {
                                 this.problemMap.put(outputs[0], new Problem(outputs[0], mvp));
                              }

                              variable = new OptVariable("w", outputs[0].getWireId(), true);
                              this.allOptVariables.add(variable);
                              this.optVarWireMap.put(variable, outputs[0]);
                              this.mvpMap.put(outputs[0], new MultivariatePolynomial(variable));
                           } else {
                              this.mvpMap.put(outputs[0], mvp);
                           }
                        }
                     }
                  }
               } else {
                  var11 = inputs;
                  i = inputs.length;

                  for(idx = 0; idx < i; ++idx) {
                     w = var11[idx];
                     if (this.problemMap.get(w) == null) {
                        this.problemMap.put(w, new Problem(w, (MultivariatePolynomial)this.mvpMap.get(w)));
                     }
                  }
               }
            }
         }

         return;
      }
   }

   private int checkInputToOverride(Wire[] inputs) {
      int i = 0;
      Wire[] var6 = inputs;
      int var5 = inputs.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Wire w = var6[var4];
         if ((Boolean)this.toOverride.get(w) && (Integer)this.useCounters.get(w) == 0) {
            return i;
         }

         ++i;
      }

      return -1;
   }

   private void checkForSolvableProblems(float f) {
      ArrayList<Problem> combinedProblemsToSolve = new ArrayList();
      System.out.print("[Arithmetic Optimizer - Incremental Mode (Progress: " + Math.ceil((double)f) + "%)] Checking for problems that can be solved at this stage:");
      HashMap<OptVarPair, Boolean> state = new HashMap();
      Iterator var5 = this.optVarProblemMap.keySet().iterator();

      while(var5.hasNext()) {
         OptVarPair p = (OptVarPair)var5.next();
         state.put(p, false);
      }

      int numProcessed = 0;
      HashSet<Problem> problemCollection = new LinkedHashSet();
      HashSet<Problem> visitedProblems = new HashSet();
      Queue<OptVarPair> varQueue = new LinkedList();
      HashSet<OptVarPair> pairs = new HashSet();
      Set<OptVarPair> keySet = this.optVarProblemMap.keySet();
      Iterator var11 = keySet.iterator();

      while(true) {
         Iterator var15;
         boolean canBeSolvedNow;
         Problem p;
         label165:
         do {
            OptVarPair pair;
            do {
               if (!var11.hasNext()) {
                  var11 = pairs.iterator();

                  while(var11.hasNext()) {
                     pair = (OptVarPair)var11.next();
                     this.optVarProblemMap.remove(pair);
                  }

                  this.numProblemsOptimized = 0;
                  System.out.println("Now solving " + combinedProblemsToSolve.size() + " problems.");
                  var11 = combinedProblemsToSolve.iterator();

                  Problem p;
                  Wire w;
                  Iterator var26;
                  while(var11.hasNext()) {
                     p = (Problem)var11.next();
                     p.constructOriginalSubcircuit();
                     p.optVarPairs = null;
                     var26 = p.getIntermediateWires().iterator();

                     while(var26.hasNext()) {
                        w = (Wire)var26.next();
                        this.mvpMap.remove(w);
                     }

                     var26 = p.mvpList.keySet().iterator();

                     while(var26.hasNext()) {
                        w = (Wire)var26.next();
                        if (this.useCounters.get(w) != null && (Integer)this.useCounters.get(w) == 0) {
                           this.mvpMap.remove(w);
                        }
                     }
                  }

                  (new MultivariateMinimizer(combinedProblemsToSolve)).run();

                  for(var11 = combinedProblemsToSolve.iterator(); var11.hasNext(); p.mvpList = null) {
                     p = (Problem)var11.next();
                     if (p.solutions == null) {
                        if (p.mvpList != null) {
                           var26 = p.mvpList.keySet().iterator();

                           while(var26.hasNext()) {
                              w = (Wire)var26.next();
                              this.problemMap.remove(w);
                              this.originalEvalSequenceMap.put(w, p.originalEvalSequenceArray);
                           }
                        } else {
                           var26 = p.keyWireList.iterator();

                           while(var26.hasNext()) {
                              w = (Wire)var26.next();
                              this.problemMap.remove(w);
                              this.originalEvalSequenceMap.put(w, p.originalEvalSequenceArray);
                           }
                        }
                     }
                  }

                  System.gc();
                  return;
               }

               pair = (OptVarPair)var11.next();
            } while((Boolean)state.get(pair));

            state.put(pair, true);
            ++numProcessed;
            problemCollection.clear();
            varQueue.clear();
            ArrayList<Problem> list = (ArrayList)this.optVarProblemMap.get(pair);
            problemCollection.addAll(list);
            Iterator var14 = list.iterator();

            while(var14.hasNext()) {
               Problem p = (Problem)var14.next();
               if (!visitedProblems.contains(p)) {
                  visitedProblems.add(p);
                  varQueue.addAll(p.optVarPairs);
               }
            }

            label142:
            while(true) {
               Iterator var17;
               OptVarPair pair2;
               do {
                  if (varQueue.isEmpty()) {
                     canBeSolvedNow = true;
                     var15 = problemCollection.iterator();

                     while(true) {
                        while(true) {
                           if (!var15.hasNext()) {
                              continue label165;
                           }

                           p = (Problem)var15.next();
                           if (!p.checkCompletedUsageIntermediateWires()) {
                              canBeSolvedNow = false;
                              continue label165;
                           }

                           var17 = p.variables.iterator();

                           while(var17.hasNext()) {
                              OptVariable v = (OptVariable)var17.next();
                              Wire w = (Wire)this.optVarWireMap.get(v);
                              if (this.useCounters.get(w) == null || (Integer)this.useCounters.get(w) != 0) {
                                 canBeSolvedNow = false;
                                 break;
                              }
                           }
                        }
                     }
                  }

                  pair2 = (OptVarPair)varQueue.poll();
               } while((Boolean)state.get(pair2));

               list = (ArrayList)this.optVarProblemMap.get(pair2);
               problemCollection.addAll(list);
               state.put(pair2, true);
               var15 = list.iterator();

               while(true) {
                  do {
                     if (!var15.hasNext()) {
                        ++numProcessed;
                        continue label142;
                     }

                     p = (Problem)var15.next();
                  } while(visitedProblems.contains(p));

                  visitedProblems.add(p);
                  var17 = p.optVarPairs.iterator();

                  while(var17.hasNext()) {
                     OptVarPair pair3 = (OptVarPair)var17.next();
                     if (!(Boolean)state.get(pair3)) {
                        varQueue.add(pair3);
                     }
                  }
               }
            }
         } while(!canBeSolvedNow);

         var15 = problemCollection.iterator();

         while(var15.hasNext()) {
            p = (Problem)var15.next();
            pairs.addAll(p.optVarPairs);
         }

         p = new Problem(problemCollection);
         combinedProblemsToSolve.add(p);
      }
   }

   private void thirdPass() {
      this.groupProblems();
      if (!Config.arithOptimizerIncrementalMode) {
         System.out.println("[Arithmetic Optimizer] Minimizing Multivariate Expressions");
      } else {
         System.out.println("[Arithmetic Optimizer] Minimizing Remaining Multivariate Expressions");
      }

      int c = false;
      int max = false;
      this.mvpMap.clear();
      this.optVarProblemMap.clear();
      this.useCounters.clear();
      Iterator var4 = this.combinedProblems.iterator();

      while(var4.hasNext()) {
         Problem p = (Problem)var4.next();
         p.constructOriginalSubcircuit();
         p.optVarPairs = null;
      }

      (new MultivariateMinimizer(this.combinedProblems)).run();
      this.newEvalSequence = new LinkedHashMap();
      this.generator.__setEvaluationQueue(this.newEvalSequence);
      int tempTotalCost = 0;
      Iterator var5 = this.evalSequence.keySet().iterator();

      while(true) {
         while(true) {
            int i;
            Instruction e;
            while(var5.hasNext()) {
               e = (Instruction)var5.next();
               if (e instanceof WireLabelInstruction) {
                  WireLabelInstruction labelInstruction = (WireLabelInstruction)e;
                  if (labelInstruction.getType() != WireLabelInstruction.LabelType.debug && labelInstruction.getType() != WireLabelInstruction.LabelType.output) {
                     this.newEvalSequence.put(e, e);
                  } else {
                     Problem p = (Problem)this.problemMap.get(labelInstruction.getWire());
                     if (p != null) {
                        p.getSuperProblem().integrateEvalSequence();
                     } else {
                        Instruction[] seq = (Instruction[])this.originalEvalSequenceMap.get(labelInstruction.getWire());

                        for(int i = seq.length - 1; i >= 0; --i) {
                           this.newEvalSequence.put(seq[i], seq[i]);
                        }
                     }

                     this.newEvalSequence.put(e, e);
                  }
               } else if (e instanceof BasicOp) {
                  boolean addToEval = false;
                  BasicOp op = (BasicOp)e;
                  Wire[] inputs = op.getInputs();
                  Wire[] outputs = op.getOutputs();
                  Wire w;
                  int var11;
                  int var12;
                  Wire[] var13;
                  Problem p;
                  Instruction[] seq;
                  if (!(e instanceof SplitBasicOp) && !(e instanceof PackBasicOp) && !(e instanceof NonZeroCheckBasicOp) && !(e instanceof AssertBasicOp)) {
                     var13 = outputs;
                     var12 = outputs.length;

                     for(var11 = 0; var11 < var12; ++var11) {
                        w = var13[var11];
                        p = (Problem)this.problemMap.get(w);
                        if (p != null) {
                           p.getSuperProblem().integrateEvalSequence();
                        } else {
                           seq = (Instruction[])this.originalEvalSequenceMap.get(w);
                           if (seq != null) {
                              for(i = seq.length - 1; i >= 0; --i) {
                                 this.newEvalSequence.put(seq[i], seq[i]);
                              }
                           }
                        }
                     }
                  } else {
                     var13 = inputs;
                     var12 = inputs.length;

                     for(var11 = 0; var11 < var12; ++var11) {
                        w = var13[var11];
                        p = (Problem)this.problemMap.get(w);
                        if (p != null) {
                           p.getSuperProblem().integrateEvalSequence();
                           addToEval = true;
                        } else {
                           seq = (Instruction[])this.originalEvalSequenceMap.get(w);
                           if (seq != null) {
                              addToEval = true;

                              for(i = seq.length - 1; i >= 0; --i) {
                                 this.newEvalSequence.put(seq[i], seq[i]);
                              }
                           }
                        }
                     }

                     if (e instanceof AssertBasicOp) {
                        var13 = outputs;
                        var12 = outputs.length;

                        for(var11 = 0; var11 < var12; ++var11) {
                           w = var13[var11];
                           p = (Problem)this.problemMap.get(w);
                           if (p != null) {
                              addToEval = true;
                              p.getSuperProblem().integrateEvalSequence();
                           } else {
                              seq = (Instruction[])this.originalEvalSequenceMap.get(w);
                              if (seq != null) {
                                 addToEval = true;

                                 for(i = seq.length - 1; i >= 0; --i) {
                                    this.newEvalSequence.put(seq[i], seq[i]);
                                 }
                              }
                           }
                        }
                     }

                     if (addToEval) {
                        this.newEvalSequence.put(e, e);
                     }
                  }
               }
            }

            this.originalEvalSequenceMap = null;
            this.problemMap = null;
            System.gc();
            var5 = this.newEvalSequence.keySet().iterator();

            while(var5.hasNext()) {
               e = (Instruction)var5.next();
               if (e instanceof BasicOp) {
                  tempTotalCost += ((BasicOp)e).getNumMulGates();
               }
            }

            boolean[] wireDefined = new boolean[this.generator.__getCurrentWireId()];
            boolean[] wireVisited = new boolean[this.generator.__getCurrentWireId()];
            HashMap<Wire, ArrayList<Instruction>> wireInstructionDependencies = new HashMap();
            HashMap<Instruction, ArrayList<Wire>> instructionWireDependencies = new HashMap();
            LinkedHashMap<Instruction, Instruction> sortedEvalSequence = new LinkedHashMap();
            Queue<Instruction> iQueue = new LinkedList();
            ArrayList<Wire> newOutputs = new ArrayList();
            HashMap<Wire, Instruction> newOutputInstructions = new HashMap();
            Instruction debugInstruction = null;
            LinkedHashSet<Integer> notDefinedWires = new LinkedHashSet();
            Iterator var46 = this.newEvalSequence.keySet().iterator();

            while(true) {
               while(true) {
                  int numNewWitnesses;
                  int numNewSplits;
                  Instruction instruction;
                  Instruction instruction;
                  Wire w;
                  Wire w;
                  int numNewPacks;
                  ArrayList ilist;
                  label390:
                  while(var46.hasNext()) {
                     instruction = (Instruction)var46.next();

                     while(true) {
                        BasicOp basicOp;
                        do {
                           if (iQueue.isEmpty()) {
                              if (instruction instanceof WireLabelInstruction) {
                                 WireLabelInstruction labelInstruction = (WireLabelInstruction)instruction;
                                 w = labelInstruction.getWire();
                                 ArrayList ilist;
                                 if (labelInstruction.getType() != WireLabelInstruction.LabelType.input && labelInstruction.getType() != WireLabelInstruction.LabelType.nizkinput) {
                                    if (!wireDefined[w.getWireId()]) {
                                       wireVisited[w.getWireId()] = true;
                                       notDefinedWires.add(w.getWireId());
                                       ilist = (ArrayList)wireInstructionDependencies.get(w);
                                       if (ilist == null) {
                                          ilist = new ArrayList();
                                          wireInstructionDependencies.put(w, ilist);
                                       }

                                       ilist.add(instruction);
                                       ArrayList<Wire> wlist = (ArrayList)instructionWireDependencies.get(instruction);
                                       if (wlist == null) {
                                          wlist = new ArrayList();
                                          instructionWireDependencies.put(instruction, wlist);
                                       }

                                       wlist.add(w);
                                       instructionWireDependencies.put(instruction, wlist);
                                    } else {
                                       wireVisited[w.getWireId()] = true;
                                       sortedEvalSequence.put(instruction, instruction);
                                    }
                                    continue label390;
                                 }

                                 sortedEvalSequence.put(instruction, instruction);
                                 wireDefined[w.getWireId()] = true;
                                 wireVisited[w.getWireId()] = true;
                                 notDefinedWires.remove(w.getWireId());
                                 ilist = (ArrayList)wireInstructionDependencies.get(w);
                                 if (ilist != null) {
                                    Iterator var70 = ilist.iterator();

                                    while(var70.hasNext()) {
                                       Instruction i = (Instruction)var70.next();
                                       ArrayList<Wire> list = (ArrayList)instructionWireDependencies.get(i);
                                       list.remove(w);
                                       if (list.isEmpty()) {
                                          iQueue.add(i);
                                          instructionWireDependencies.remove(i);
                                       }
                                    }

                                    wireInstructionDependencies.remove(w);
                                 }
                                 continue label390;
                              }

                              if (instruction instanceof BasicOp) {
                                 BasicOp basicOp = (BasicOp)instruction;
                                 boolean allDefined = true;
                                 Wire[] inList = basicOp.getInputs();
                                 if (instruction instanceof AssertBasicOp) {
                                    inList = Util.concat(basicOp.getOutputs()[0], inList);
                                 }

                                 Wire[] var71 = inList;
                                 numNewPacks = inList.length;

                                 for(numNewSplits = 0; numNewSplits < numNewPacks; ++numNewSplits) {
                                    w = var71[numNewSplits];
                                    if (!wireDefined[w.getWireId()]) {
                                       notDefinedWires.add(w.getWireId());
                                       allDefined = false;
                                       ilist = (ArrayList)wireInstructionDependencies.get(w);
                                       if (ilist == null) {
                                          ilist = new ArrayList();
                                          wireInstructionDependencies.put(w, ilist);
                                       }

                                       ilist.add(instruction);
                                       ArrayList<Wire> wlist = (ArrayList)instructionWireDependencies.get(instruction);
                                       if (wlist == null) {
                                          wlist = new ArrayList();
                                          instructionWireDependencies.put(instruction, wlist);
                                       }

                                       wlist.add(w);
                                    }
                                 }

                                 if (!(instruction instanceof AssertBasicOp)) {
                                    numNewPacks = (var71 = basicOp.getOutputs()).length;

                                    for(numNewSplits = 0; numNewSplits < numNewPacks; ++numNewSplits) {
                                       w = var71[numNewSplits];
                                       wireVisited[w.getWireId()] = true;
                                    }
                                 }

                                 if (allDefined) {
                                    sortedEvalSequence.put(instruction, instruction);
                                    numNewPacks = (var71 = basicOp.getOutputs()).length;

                                    for(numNewSplits = 0; numNewSplits < numNewPacks; ++numNewSplits) {
                                       w = var71[numNewSplits];
                                       wireDefined[w.getWireId()] = true;
                                       notDefinedWires.remove(w.getWireId());
                                       ilist = (ArrayList)wireInstructionDependencies.get(w);
                                       if (ilist != null) {
                                          Iterator var86 = ilist.iterator();

                                          while(var86.hasNext()) {
                                             Instruction i = (Instruction)var86.next();
                                             ArrayList<Wire> list = (ArrayList)instructionWireDependencies.get(i);
                                             if (list != null) {
                                                list.remove(w);
                                                if (list.isEmpty()) {
                                                   iQueue.add(i);
                                                   instructionWireDependencies.remove(i);
                                                }
                                             }
                                          }
                                       }

                                       wireInstructionDependencies.remove(w);
                                    }
                                 }
                              }
                              continue label390;
                           }

                           instruction = (Instruction)iQueue.poll();
                           sortedEvalSequence.put(instruction, instruction);
                           if (!(instruction instanceof BasicOp)) {
                              throw new RuntimeException("Unexpected case (sanity check failed). Please report this case.");
                           }

                           basicOp = (BasicOp)instruction;
                        } while(basicOp instanceof AssertBasicOp);

                        Wire[] var21;
                        numNewSplits = (var21 = basicOp.getOutputs()).length;

                        for(numNewWitnesses = 0; numNewWitnesses < numNewSplits; ++numNewWitnesses) {
                           Wire w = var21[numNewWitnesses];
                           wireDefined[w.getWireId()] = true;
                           wireVisited[w.getWireId()] = true;
                           notDefinedWires.remove(w.getWireId());
                           ArrayList<Instruction> ilist = (ArrayList)wireInstructionDependencies.get(w);
                           if (ilist != null) {
                              Iterator var24 = ilist.iterator();

                              while(var24.hasNext()) {
                                 Instruction i3 = (Instruction)var24.next();
                                 ArrayList<Wire> list = (ArrayList)instructionWireDependencies.get(i3);
                                 if (list != null) {
                                    list.remove(w);
                                    if (list.isEmpty()) {
                                       iQueue.add(i3);
                                       instructionWireDependencies.remove(i3);
                                    }
                                 }
                              }
                           }

                           wireInstructionDependencies.remove(w);
                        }
                     }
                  }

                  int numNewIns;
                  int numNewOuts;
                  while(!iQueue.isEmpty()) {
                     instruction = (Instruction)iQueue.poll();
                     sortedEvalSequence.put(instruction, instruction);
                     if (!(instruction instanceof BasicOp)) {
                        throw new RuntimeException("Unexpected case (sanity check failed). Please report this case.");
                     }

                     BasicOp basicOp = (BasicOp)instruction;
                     if (!(basicOp instanceof AssertBasicOp)) {
                        Wire[] var69;
                        numNewOuts = (var69 = basicOp.getOutputs()).length;

                        for(numNewIns = 0; numNewIns < numNewOuts; ++numNewIns) {
                           Wire w = var69[numNewIns];
                           wireDefined[w.getWireId()] = true;
                           wireVisited[w.getWireId()] = true;
                           notDefinedWires.remove(w.getWireId());
                           ArrayList<Instruction> ilist = (ArrayList)wireInstructionDependencies.get(w);
                           if (ilist != null) {
                              Iterator var77 = ilist.iterator();

                              while(var77.hasNext()) {
                                 Instruction i3 = (Instruction)var77.next();
                                 ilist = (ArrayList)instructionWireDependencies.get(i3);
                                 if (ilist != null) {
                                    ilist.remove(w);
                                    if (ilist.isEmpty()) {
                                       iQueue.add(i3);
                                       instructionWireDependencies.remove(i3);
                                    }
                                 }
                              }
                           }

                           wireInstructionDependencies.remove(w);
                        }
                     }
                  }

                  this.newEvalSequence = sortedEvalSequence;
                  Wire[] wireMap = new Wire[this.generator.__getCurrentWireId()];
                  Iterator var58 = this.newEvalSequence.keySet().iterator();

                  while(var58.hasNext()) {
                     Instruction ii = (Instruction)var58.next();
                     Wire[] var76;
                     numNewWitnesses = (var76 = ii.getUsedWires()).length;

                     for(numNewOuts = 0; numNewOuts < numNewWitnesses; ++numNewOuts) {
                        w = var76[numNewOuts];
                        if (wireMap[w.getWireId()] == null) {
                           ++this.newWireCount;
                           wireMap[w.getWireId()] = w;
                        } else if (!wireMap[w.getWireId()].equals(w)) {
                           throw new RuntimeException("Unexpected Case (sanity check failed)- Please report this case.");
                        }
                     }
                  }

                  int idx = 0;

                  for(i = 0; i < wireMap.length; ++i) {
                     if (wireMap[i] != null) {
                        this.newToOldIndexMap.put(idx, i);
                        wireMap[i].setWireId(idx);
                        ++idx;
                     }
                  }

                  Iterator var60 = this.newEvalSequence.keySet().iterator();

                  while(var60.hasNext()) {
                     instruction = (Instruction)var60.next();
                     if (instruction instanceof WireLabelInstruction && ((WireLabelInstruction)instruction).getType() == WireLabelInstruction.LabelType.output) {
                        newOutputInstructions.put(((WireLabelInstruction)instruction).getWire(), instruction);
                        newOutputs.add(((WireLabelInstruction)instruction).getWire());
                     }
                  }

                  ArrayList<Wire> outputsToPromote = new ArrayList();
                  Iterator var68 = this.newEvalSequence.keySet().iterator();

                  while(true) {
                     BasicOp basicOp;
                     int numNewAssertions;
                     do {
                        Instruction instruction;
                        do {
                           if (!var68.hasNext()) {
                              var68 = outputsToPromote.iterator();

                              while(var68.hasNext()) {
                                 w = (Wire)var68.next();
                                 this.newEvalSequence.remove(newOutputInstructions.get(w));
                                 w = new Wire(this.newWireCount++);
                                 Instruction i1 = new MulBasicOp(w, this.generator.__getOneWire(), w, new String[0]);
                                 Instruction i2 = new WireLabelInstruction(WireLabelInstruction.LabelType.output, w, new String[0]);
                                 this.newEvalSequence.put(i1, i1);
                                 this.newEvalSequence.put(i2, i2);
                                 this.newToOldIndexMap.put(w.getWireId(), (Integer)this.newToOldIndexMap.get(w.getWireId()));
                              }

                              this.generator.__setCurrentWireId(this.newWireCount);
                              this.generator.__setEvaluationQueue(this.newEvalSequence);
                              numNewIns = 0;
                              numNewOuts = 0;
                              numNewWitnesses = 0;
                              numNewSplits = 0;
                              numNewPacks = 0;
                              numNewAssertions = 0;
                              int numNewNonzeroChecks = 0;
                              int numMulGates = 0;
                              Iterator var90 = this.newEvalSequence.keySet().iterator();

                              while(var90.hasNext()) {
                                 Instruction instruction = (Instruction)var90.next();
                                 if (instruction instanceof WireLabelInstruction) {
                                    WireLabelInstruction labelInstruction = (WireLabelInstruction)instruction;
                                    if (labelInstruction.getType() != WireLabelInstruction.LabelType.input) {
                                       labelInstruction.getType();
                                       WireLabelInstruction.LabelType var10000 = WireLabelInstruction.LabelType.nizkinput;
                                    }

                                    if (labelInstruction.getType() == WireLabelInstruction.LabelType.input) {
                                       ++numNewIns;
                                    }

                                    if (labelInstruction.getType() == WireLabelInstruction.LabelType.nizkinput) {
                                       ++numNewWitnesses;
                                    }

                                    if (labelInstruction.getType() == WireLabelInstruction.LabelType.output) {
                                       ++numNewOuts;
                                    }
                                 } else if (instruction instanceof BasicOp) {
                                    BasicOp op = (BasicOp)instruction;
                                    numMulGates += op.getNumMulGates();
                                    if (instruction instanceof SplitBasicOp) {
                                       ++numNewSplits;
                                    }

                                    if (instruction instanceof PackBasicOp) {
                                       ++numNewPacks;
                                    }

                                    if (instruction instanceof AssertBasicOp) {
                                       ++numNewAssertions;
                                    }

                                    if (instruction instanceof NonZeroCheckBasicOp) {
                                       ++numNewNonzeroChecks;
                                    }
                                 }
                              }

                              System.out.println("[Arithmetic Optimizer] Savings due to arithmetic minimization = " + this.totalSavings + "  (Note: Sometimes, the number of savings reported in this line could be higher than the actual total savings.)");
                              System.out.println("[Arithmetic Optimizer] Number of total mul gates before arithmetic minimization =  " + this.numOriginalMulGates);
                              System.out.println("[Arithmetic Optimizer] Number of total mul gates after  arithmetic minimization =  " + numMulGates);
                              if (numNewOuts == this.numOriginalOuts && numNewIns == this.numOriginalIns && numNewWitnesses == this.numOriginalWitnesses && numNewSplits == this.numOriginalSplits && numNewAssertions == this.numOriginalAssertions && numNewPacks == this.numOriginalPacks && numNewNonzeroChecks == this.numOriginalNonzeroChecks) {
                                 if (notDefinedWires.size() > 0) {
                                    throw new RuntimeException("Inconsistency found in the new version of the circuit. Please report this case.");
                                 }

                                 System.out.println("Initial sanity checks passed.");
                                 return;
                              }

                              System.out.println("Mismatches Found: ");
                              System.out.println(numNewIns + "," + numNewOuts + "," + numNewWitnesses + "," + numNewSplits + "," + numNewPacks + "," + numNewAssertions + "," + numNewNonzeroChecks);
                              System.out.println(this.numOriginalIns + "," + this.numOriginalOuts + "," + this.numOriginalWitnesses + "," + this.numOriginalSplits + "," + this.numOriginalPacks + "," + this.numOriginalAssertions + "," + this.numOriginalNonzeroChecks);
                              throw new RuntimeException("Mismatches found in the new version of the circuit. Please report this case.");
                           }

                           instruction = (Instruction)var68.next();
                        } while(!(instruction instanceof BasicOp));

                        basicOp = (BasicOp)instruction;
                     } while(!(basicOp instanceof AddBasicOp) && !(basicOp instanceof PackBasicOp) && !(basicOp instanceof ConstMulBasicOp));

                     Wire[] var84;
                     numNewAssertions = (var84 = basicOp.getOutputs()).length;

                     for(numNewPacks = 0; numNewPacks < numNewAssertions; ++numNewPacks) {
                        Wire w = var84[numNewPacks];
                        if (newOutputs.contains(w)) {
                           outputsToPromote.add(w);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public CircuitEvaluator mapFromOldEvaluationSeq(CircuitEvaluator oldEvaluator) {
      BigInteger[] oldAssignment = oldEvaluator.getAssignment();
      BigInteger[] newAssignment = new BigInteger[this.newWireCount];
      Iterator var5 = this.newEvalSequence.keySet().iterator();

      while(true) {
         WireLabelInstruction labelInstruction;
         do {
            Instruction instruction;
            do {
               if (!var5.hasNext()) {
                  CircuitEvaluator circuitEvaluator = new CircuitEvaluator(this.generator, newAssignment);
                  circuitEvaluator.evaluate();
                  Iterator var10 = this.newEvalSequence.keySet().iterator();

                  while(var10.hasNext()) {
                     Instruction instruction = (Instruction)var10.next();
                     if (instruction instanceof WireLabelInstruction) {
                        WireLabelInstruction labelInstruction = (WireLabelInstruction)instruction;
                        if (labelInstruction.getType() == WireLabelInstruction.LabelType.output && !newAssignment[labelInstruction.getWire().getWireId()].equals(oldAssignment[(Integer)this.newToOldIndexMap.get(labelInstruction.getWire().getWireId())])) {
                           throw new RuntimeException("Circuit output after multivariate optimization don't match the expected output");
                        }
                     }
                  }

                  return circuitEvaluator;
               }

               instruction = (Instruction)var5.next();
            } while(!(instruction instanceof WireLabelInstruction));

            labelInstruction = (WireLabelInstruction)instruction;
         } while(labelInstruction.getType() != WireLabelInstruction.LabelType.input && labelInstruction.getType() != WireLabelInstruction.LabelType.nizkinput);

         newAssignment[labelInstruction.getWire().getWireId()] = oldAssignment[(Integer)this.newToOldIndexMap.get(labelInstruction.getWire().getWireId())];
      }
   }

   private class OptVarPair {
      OptVariable v1;
      OptVariable v2;

      public OptVarPair(OptVariable v1, OptVariable v2) {
         this.v1 = v1;
         this.v2 = v2;
      }

      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else if (!(obj instanceof OptVarPair)) {
            return false;
         } else {
            OptVarPair p = (OptVarPair)obj;
            if (p.v2 != null && this.v2 != null) {
               return p.v1.equals(this.v1) && p.v2.equals(this.v2) || p.v1.equals(this.v2) && p.v2.equals(this.v1);
            } else {
               return this.v1 != null ? p.v1.equals(this.v1) : true;
            }
         }
      }

      public int hashCode() {
         if (this.v2 != null) {
            return this.v1.hashCode() + this.v2.hashCode();
         } else {
            return this.v1 != null ? this.v1.hashCode() : 1;
         }
      }

      public String toString() {
         String s = "";
         if (this.v1 == null) {
            s = s + "null ,";
         } else {
            s = s + this.v1.toString() + ", ";
         }

         if (this.v2 == null) {
            s = s + "null";
         } else {
            s = s + this.v2.toString();
         }

         return s;
      }
   }

   public class Problem {
      private Problem superProblem = null;
      private ArrayList<OptVariable> variables;
      private ArrayList<OptVarPair> optVarPairs;
      private HashMap<Wire, MultivariatePolynomial> mvpList;
      private HashSet<Wire> keyWireList;
      private ArrayList<Problem> subProblems;
      private Wire key;
      private MultivariatePolynomial mvp;
      private LinkedHashMap<Instruction, Instruction> originalEvalSequence;
      private Instruction[] originalEvalSequenceArray;
      private int originalNumMulGates = 0;
      private int optimizedNumMulGates = -1;
      private HashMap<String, MultivariatePolynomial> solutions;
      private boolean integrated;
      private boolean dontSolve = false;

      Problem() {
         this.superProblem = this;
      }

      Problem(Wire key, MultivariatePolynomial poly) {
         this.key = key;
         this.mvp = poly;
         HashSet<OptVariable> variableSet = new HashSet(1, 1.0F);
         Iterator var6 = poly.getTerms().iterator();

         while(var6.hasNext()) {
            Term t = (Term)var6.next();
            variableSet.addAll(t.getVars());
         }

         HashSet<OptVarPair> optVarPairsSet = new HashSet(1, 1.0F);
         if (variableSet.size() == 0) {
            OptVarPair optVarPairxxx = CircuitOptimizer.this.new OptVarPair((OptVariable)null, (OptVariable)null);
            optVarPairsSet.add(optVarPairxxx);
            ArrayList<Problem> listx = (ArrayList)CircuitOptimizer.this.optVarProblemMap.get(optVarPairxxx);
            if (listx == null) {
               listx = new ArrayList(1);
               CircuitOptimizer.this.optVarProblemMap.put(optVarPairxxx, listx);
            }

            listx.add(this);
            listx.trimToSize();
         }

         OptVariable tmp = null;
         Iterator var8 = poly.getTerms().iterator();

         while(var8.hasNext()) {
            Term tx = (Term)var8.next();
            Collection<OptVariable> s = tx.getVars();
            variableSet.addAll(s);
            int to = 0;

            for(Iterator var12 = s.iterator(); var12.hasNext(); ++to) {
               OptVariable v1 = (OptVariable)var12.next();
               int index = 0;
               tmp = v1;
               if (tx.getExponent(v1) > 1) {
                  OptVarPair optVarPairx = CircuitOptimizer.this.new OptVarPair(v1, v1);
                  optVarPairsSet.add(optVarPairx);
                  ArrayList<Problem> listxx = (ArrayList)CircuitOptimizer.this.optVarProblemMap.get(optVarPairx);
                  if (listxx == null) {
                     listxx = new ArrayList(1);
                     CircuitOptimizer.this.optVarProblemMap.put(optVarPairx, listxx);
                  }

                  listxx.add(this);
                  listxx.trimToSize();
               }

               Iterator var25 = s.iterator();

               while(var25.hasNext()) {
                  OptVariable v2 = (OptVariable)var25.next();
                  if (index++ >= to) {
                     break;
                  }

                  OptVarPair optVarPairxx = CircuitOptimizer.this.new OptVarPair(v1, v2);
                  optVarPairsSet.add(optVarPairxx);
                  ArrayList<Problem> listxxx = (ArrayList)CircuitOptimizer.this.optVarProblemMap.get(optVarPairxx);
                  if (listxxx == null) {
                     listxxx = new ArrayList(1);
                     CircuitOptimizer.this.optVarProblemMap.put(optVarPairxx, listxxx);
                  }

                  listxxx.add(this);
                  listxxx.trimToSize();
               }
            }
         }

         if (optVarPairsSet.size() == 0 && tmp != null) {
            OptVarPair optVarPair = CircuitOptimizer.this.new OptVarPair(tmp, (OptVariable)null);
            optVarPairsSet.add(optVarPair);
            ArrayList<Problem> list = (ArrayList)CircuitOptimizer.this.optVarProblemMap.get(optVarPair);
            if (list == null) {
               list = new ArrayList(1);
               CircuitOptimizer.this.optVarProblemMap.put(optVarPair, list);
            }

            list.add(this);
            list.trimToSize();
         }

         this.variables = new ArrayList(variableSet);
         this.optVarPairs = new ArrayList(optVarPairsSet);
         this.superProblem = this;
      }

      public String toString() {
         String s = "";

         Wire w;
         for(Iterator var3 = this.mvpList.keySet().iterator(); var3.hasNext(); s = s + w + ":" + this.mvpList.get(w) + "\n") {
            w = (Wire)var3.next();
         }

         return s;
      }

      Problem(Collection<Problem> list) {
         this.mvpList = new LinkedHashMap();
         HashSet<OptVariable> variableSet = new HashSet();

         Problem p;
         for(Iterator var5 = list.iterator(); var5.hasNext(); p.optVarPairs = null) {
            p = (Problem)var5.next();
            variableSet.addAll(p.variables);
            if (p.mvpList == null) {
               if (p.key != null) {
                  this.mvpList.put(p.key, p.mvp);
               }
            } else {
               this.mvpList.putAll(p.mvpList);
            }

            p.setSuperProblem(this);
            p.variables = null;
            p.mvpList = null;
         }

         this.superProblem = this;
         this.variables = new ArrayList(variableSet);
      }

      Problem getSuperProblem() {
         Problem p;
         for(p = this; p.superProblem != p; p = p.superProblem) {
         }

         this.superProblem = p;
         return p;
      }

      void setSuperProblem(Problem p) {
         this.superProblem = p;
         if (this.subProblems != null) {
            Iterator var3 = this.subProblems.iterator();

            while(var3.hasNext()) {
               Problem subProblem = (Problem)var3.next();
               subProblem.setSuperProblem(p);
            }
         }

      }

      boolean isEmpty() {
         return this.mvpList.size() == 0;
      }

      public void setSolutions(HashMap<String, MultivariatePolynomial> sols) {
         if (sols != null) {
            this.solutions = sols;
            this.optimizedNumMulGates = 0;

            MultivariatePolynomial mvp;
            Iterator var3;
            for(var3 = this.solutions.values().iterator(); var3.hasNext(); this.optimizedNumMulGates += mvp.getCost()) {
               mvp = (MultivariatePolynomial)var3.next();
            }

            if (this.optimizedNumMulGates < this.originalNumMulGates) {
               CircuitOptimizer var10000 = CircuitOptimizer.this;
               var10000.totalSavings = var10000.totalSavings + (this.originalNumMulGates - this.optimizedNumMulGates);
               this.originalEvalSequence = null;
               this.originalEvalSequenceArray = null;
               var10000 = CircuitOptimizer.this;
               var10000.numProblemsOptimized = var10000.numProblemsOptimized + 1;
               this.keyWireList = new HashSet();
               var3 = this.mvpList.keySet().iterator();

               while(var3.hasNext()) {
                  Wire w = (Wire)var3.next();
                  this.keyWireList.add(w);
               }

               this.mvpList = null;
            } else {
               this.solutions = null;
               this.variables = null;
               this.optVarPairs = null;
               this.subProblems = null;
            }

         }
      }

      public ExpressionMinimizer prep() {
         ArrayList<MultivariatePolynomial> list = new ArrayList();
         String[] inputVarsStrings = new String[this.variables.size()];
         String[] outVarsStrings = new String[this.mvpList.size()];
         int i = 0;

         Map.Entry e;
         Iterator var6;
         for(var6 = this.mvpList.entrySet().iterator(); var6.hasNext(); outVarsStrings[i++] = "w" + ((Wire)e.getKey()).toString()) {
            e = (Map.Entry)var6.next();
            list.add((MultivariatePolynomial)e.getValue());
         }

         i = 0;

         OptVariable var;
         for(var6 = this.variables.iterator(); var6.hasNext(); inputVarsStrings[i++] = var.toString()) {
            var = (OptVariable)var6.next();
         }

         return new ExpressionMinimizer(inputVarsStrings, outVarsStrings, list);
      }

      public void solve() {
         long t1 = System.currentTimeMillis();
         ArrayList<MultivariatePolynomial> list = new ArrayList();
         String[] inputVarsStrings = new String[this.variables.size()];
         String[] outVarsStrings = new String[this.mvpList.size()];
         int i = 0;

         Map.Entry e;
         Iterator var8;
         for(var8 = this.mvpList.entrySet().iterator(); var8.hasNext(); outVarsStrings[i++] = "w" + ((Wire)e.getKey()).toString()) {
            e = (Map.Entry)var8.next();
            list.add((MultivariatePolynomial)e.getValue());
         }

         i = 0;

         OptVariable var;
         for(var8 = this.variables.iterator(); var8.hasNext(); inputVarsStrings[i++] = var.toString()) {
            var = (OptVariable)var8.next();
         }

         this.solutions = (new ExpressionMinimizer(inputVarsStrings, outVarsStrings, list, 1)).getSolution();
         this.optimizedNumMulGates = 0;

         MultivariatePolynomial mvp;
         for(var8 = this.solutions.values().iterator(); var8.hasNext(); this.optimizedNumMulGates += mvp.getCost()) {
            mvp = (MultivariatePolynomial)var8.next();
         }

         if (this.optimizedNumMulGates < this.originalNumMulGates) {
            CircuitOptimizer var10000 = CircuitOptimizer.this;
            var10000.totalSavings = var10000.totalSavings + (this.originalNumMulGates - this.optimizedNumMulGates);
         } else {
            this.solutions = null;
            this.variables = null;
            this.mvpList = null;
            CircuitOptimizer.this.mvpMap = null;
         }

         long t2 = System.currentTimeMillis();
         System.out.println("Time spent = " + (t2 - t1) + ", " + this.mvpList.size() + " " + this.variables.size() + " " + "savings: = " + (this.originalNumMulGates - this.optimizedNumMulGates));
         throw new RuntimeException("Method should not be called in this release");
      }

      public void integrateEvalSequence() {
         if (!this.integrated) {
            if (this.optimizedNumMulGates != -1 && this.optimizedNumMulGates < this.originalNumMulGates) {
               HashMap<String, Wire> wireRecord = new HashMap();
               HashMap<String, Boolean> state = new HashMap();
               Iterator var4 = this.variables.iterator();

               while(var4.hasNext()) {
                  OptVariable var = (OptVariable)var4.next();
                  wireRecord.put(var.toString(), (Wire)CircuitOptimizer.this.optVarWireMap.get(var));
                  if (!this.keyWireList.contains(CircuitOptimizer.this.optVarWireMap.get(var))) {
                     state.put(var.toString(), true);
                  }
               }

               var4 = this.keyWireList.iterator();

               while(var4.hasNext()) {
                  Wire w = (Wire)var4.next();
                  wireRecord.put("w" + w.toString(), w);
               }

               int numConstraintsPrev = CircuitOptimizer.this.generator.__getNumOfConstraints();
               Iterator var5 = this.solutions.keySet().iterator();

               while(var5.hasNext()) {
                  String key = (String)var5.next();
                  this.recursiveResolve(wireRecord, state, key);
               }

               int var10 = CircuitOptimizer.this.generator.__getNumOfConstraints();
            } else {
               for(int i = this.originalEvalSequenceArray.length - 1; i >= 0; --i) {
                  CircuitOptimizer.this.newEvalSequence.put(this.originalEvalSequenceArray[i], this.originalEvalSequenceArray[i]);
               }

               CircuitOptimizer var10000 = CircuitOptimizer.this;
               var10000.originalEvals = var10000.originalEvals + 1;
            }

            this.integrated = true;
         }
      }

      private void recursiveResolve(HashMap<String, Wire> wireRecord, HashMap<String, Boolean> state, String key) {
         if (state.get(key) == null || !(Boolean)state.get(key)) {
            MultivariatePolynomial mvp = (MultivariatePolynomial)this.solutions.get(key);
            Wire[] termWires = new Wire[mvp.getTerms().size()];
            int idx = 0;
            Iterator var8 = mvp.getTerms().iterator();

            label80:
            while(true) {
               if (!var8.hasNext()) {
                  Wire result;
                  Instruction ii;
                  BasicOp op;
                  if (termWires.length > 1) {
                     result = (new WireArray(termWires)).sumAllElements();
                     if (key.startsWith("w")) {
                        ii = CircuitOptimizer.this.generator.__getLastInstructionAdded();
                        op = (BasicOp)ii;
                        op.getOutputs()[0] = (Wire)wireRecord.get(key);
                     } else {
                        wireRecord.put(key, result);
                     }
                  } else if (key.startsWith("w")) {
                     ii = CircuitOptimizer.this.generator.__getLastInstructionAdded();
                     if (!(ii instanceof BasicOp)) {
                        throw new RuntimeException("Unexpected case (sanity check failed). Please report this case.");
                     }

                     op = (BasicOp)ii;
                     if (op.getOutputs().length != 1 || op.getOutputs()[0] != termWires[0]) {
                        throw new RuntimeException("Unexpected case (sanity check failed). Please report this case.");
                     }

                     op = (BasicOp)ii;
                     op.getOutputs()[0] = (Wire)wireRecord.get(key);
                  } else {
                     result = termWires[0];
                     wireRecord.put(key, result);
                  }

                  state.put(key, true);
                  break;
               }

               Term t = (Term)var8.next();
               Iterator var10 = t.getVars().iterator();

               while(true) {
                  OptVariable var;
                  do {
                     if (!var10.hasNext()) {
                        if (t.getCoeff().bitLength() > Config.getNumBitsFiniteFieldModulus() - 2) {
                           termWires[idx] = CircuitOptimizer.this.generator.__createConstantWire(Config.getFiniteFieldModulus().subtract(t.getCoeff()).negate());
                        } else {
                           termWires[idx] = CircuitOptimizer.this.generator.__createConstantWire(t.getCoeff());
                        }

                        Term.VarIterator it = t.getVarIterator();

                        while(it.hasNext()) {
                           OptVariable v = it.next();
                           int power = it.getExponent();
                           if (power <= 0) {
                              throw new IllegalArgumentException("Unexpected case (sanity check failed) - Please report this case.");
                           }

                           if (power == 1) {
                              termWires[idx] = termWires[idx].mul((Wire)wireRecord.get(v.toString()));
                           } else {
                              Wire powered = this.exp((Wire)wireRecord.get(v.toString()), power);
                              termWires[idx] = termWires[idx].mul(powered);
                           }
                        }

                        ++idx;
                        continue label80;
                     }

                     var = (OptVariable)var10.next();
                  } while(state.get(var.toString()) != null && (Boolean)state.get(var.toString()));

                  this.recursiveResolve(wireRecord, state, var.toString());
               }
            }
         }

      }

      private Wire exp(Wire wire, int power) {
         if (power == 0) {
            return CircuitOptimizer.this.generator.__getOneWire();
         } else if (power == 1) {
            return wire;
         } else {
            return power % 2 == 0 ? this.exp(wire.mul(wire), power / 2) : this.exp(wire.mul(wire), power / 2).mul(wire);
         }
      }

      public void print() {
         System.out.println("Printing Problem");
         Iterator var2 = this.mvpList.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry<Wire, MultivariatePolynomial> e = (Map.Entry)var2.next();
            System.out.println("w" + ((Wire)e.getKey()).toString() + " = " + e.getValue());
         }

      }

      public void constructOriginalSubcircuit() {
         this.originalEvalSequence = new LinkedHashMap();
         Queue<Wire> traverseBackQueue = new LinkedList();
         if (this.mvpList == null) {
            this.mvpList = new HashMap();
            this.mvpList.put(this.key, this.mvp);
         }

         Iterator var3 = this.mvpList.keySet().iterator();

         Wire w;
         while(var3.hasNext()) {
            w = (Wire)var3.next();
            traverseBackQueue.add(w);
         }

         while(true) {
            BasicOp op;
            do {
               do {
                  Instruction instruction;
                  do {
                     do {
                        do {
                           do {
                              do {
                                 OptVariable v;
                                 do {
                                    do {
                                       if (traverseBackQueue.isEmpty()) {
                                          ListIterator<Instruction> iterator = (new ArrayList(this.originalEvalSequence.keySet())).listIterator(this.originalEvalSequence.size());
                                          this.originalEvalSequenceArray = new Instruction[this.originalEvalSequence.size()];

                                          for(int idx = this.originalEvalSequenceArray.length - 1; iterator.hasPrevious(); this.originalEvalSequenceArray[idx--] = instruction) {
                                             instruction = (Instruction)iterator.previous();
                                          }

                                          this.originalEvalSequence = null;
                                          return;
                                       }

                                       w = (Wire)traverseBackQueue.poll();
                                       v = new OptVariable("w", w.getWireId());
                                    } while(!this.mvpList.containsKey(w) && this.variables.contains(v));

                                    if (CircuitOptimizer.this.allOptVariables.contains(v) && !this.variables.contains(v) && !this.mvpList.containsKey(w)) {
                                       this.dontSolve = true;
                                    }
                                 } while(this.variables.contains(v) && (!this.variables.contains(v) || !this.mvpList.containsKey(w)));

                                 instruction = w.getSrcInstruction();
                              } while(instruction == null);
                           } while(!(instruction instanceof BasicOp));
                        } while(instruction instanceof SplitBasicOp);
                     } while(instruction instanceof PackBasicOp);
                  } while(instruction instanceof NonZeroCheckBasicOp);

                  op = (BasicOp)instruction;
               } while(this.originalEvalSequence.containsKey(op));

               this.originalEvalSequence.put(op, op);
               Wire[] var9;
               int var8 = (var9 = op.getInputs()).length;

               for(int var7 = 0; var7 < var8; ++var7) {
                  Wire w2 = var9[var7];
                  traverseBackQueue.add(w2);
               }
            } while(op instanceof MulBasicOp && (op.getInputs()[0] == CircuitOptimizer.this.generator.__getOneWire() || op.getInputs()[1] == CircuitOptimizer.this.generator.__getOneWire()));

            this.originalNumMulGates += op.getNumMulGates();
         }
      }

      public boolean checkCompletedUsageIntermediateWires() {
         this.constructOriginalSubcircuit();
         boolean allFree = true;
         Instruction[] var5;
         int var4 = (var5 = this.originalEvalSequenceArray).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Instruction i = var5[var3];
            Wire[] var9;
            int var8 = (var9 = i.getUsedWires()).length;

            for(int var7 = 0; var7 < var8; ++var7) {
               Wire w = var9[var7];
               if (!this.mvpList.containsKey(w) && (Integer)CircuitOptimizer.this.useCounters.get(w) != 0 && !(w instanceof ConstantWire) && !w.equals(CircuitOptimizer.this.generator.__getOneWire())) {
                  allFree = false;
               }
            }
         }

         this.originalEvalSequenceArray = null;
         return allFree;
      }

      public ArrayList<Wire> getIntermediateWires() {
         ArrayList<Wire> list = new ArrayList();
         Instruction[] var5;
         int var4 = (var5 = this.originalEvalSequenceArray).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            Instruction i = var5[var3];
            Wire[] var9;
            int var8 = (var9 = i.getUsedWires()).length;

            for(int var7 = 0; var7 < var8; ++var7) {
               Wire w = var9[var7];
               if (!this.mvpList.containsKey(w) && !(w instanceof ConstantWire) && !w.equals(CircuitOptimizer.this.generator.__getOneWire())) {
                  list.add(w);
               }
            }
         }

         return list;
      }

      public Collection<OptVariable> getVariables() {
         return this.variables;
      }

      public HashMap<Wire, MultivariatePolynomial> getMvpList() {
         return this.mvpList;
      }

      public boolean isDontSolve() {
         return this.dontSolve;
      }
   }
}
