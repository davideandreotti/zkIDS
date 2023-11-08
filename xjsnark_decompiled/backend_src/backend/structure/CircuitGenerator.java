package backend.structure;

import backend.auxTypes.Bit;
import backend.auxTypes.ConditionalScopeTracker;
import backend.auxTypes.FieldElement;
import backend.auxTypes.IAuxType;
import backend.auxTypes.PackedValue;
import backend.auxTypes.RuntimeStruct;
import backend.auxTypes.SmartMemory;
import backend.auxTypes.UnsignedInteger;
import backend.auxTypes.VariableState;
import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.eval.SampleRun;
import backend.operations.WireLabelInstruction;
import backend.operations.primitive.AssertBasicOp;
import backend.operations.primitive.BasicOp;
import backend.operations.primitive.MulBasicOp;
import backend.operations.primitive.PackBasicOp;
import backend.optimizer.CircuitOptimizer;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import util.Util;

public abstract class CircuitGeneratorMod {
   private static ConcurrentHashMap<Long, CircuitGeneratorMod> __activeCircuitGenerators = new ConcurrentHashMap();
   private static CircuitGeneratorMod __instance;
   protected int __currentWireId;
   private LinkedHashMap<Instruction, Instruction> __evaluationQueue;
   private LinkedHashMap<Instruction, Instruction> __nonOptimizedEvaluationQueue;
   private int __nonOptimalWireCount;
   protected Wire __zeroWire;
   protected Wire __oneWire;
   protected ArrayList<Wire> __inWires;
   protected ArrayList<Wire> __outWires;
   protected ArrayList<Wire> __proverWitnessWires;
   protected ArrayList<IAuxType> __inputAux;
   protected ArrayList<IAuxType> __proverAux;
   protected ArrayList<IAuxType> __verifiedProverAux;
   protected String __circuitName;
   protected HashMap<BigInteger, Wire> __knownConstantWires;
   private int __numOfConstraints;
   private int __phase;
   private int __stateCounter;
   private int __stateCounterPhase1;
   private int __conditionCounter;
   private ArrayList<SmartMemory<?>> __memoryList;
   private HashMap<Integer, VariableState> __varVariableStateTable;
   private ArrayList<Boolean> __conditionalStateList;
   private HashMap<Integer, SmartMemory.MemoryState> __memoryStateTable;
   private ArrayList<Class<? extends RuntimeStruct>> __rumtimeClassesList;
   private boolean __untrackedStateObjects = false;
   private CircuitEvaluator __circuitEvaluator;
   private Instruction __lastInstructionAdded;
   private CircuitOptimizer __circuitOptimizer;

   public CircuitGeneratorMod(String circuitName) {
      this.__circuitName = circuitName;
      __instance = this;
      this.__inWires = new ArrayList();
      this.__outWires = new ArrayList();
      this.__proverWitnessWires = new ArrayList();
      this.__evaluationQueue = new LinkedHashMap();
      this.__nonOptimizedEvaluationQueue = this.__evaluationQueue;
      this.__knownConstantWires = new HashMap();
      this.__currentWireId = 0;
      this.__numOfConstraints = 0;
      this.__inputAux = new ArrayList();
      this.__proverAux = new ArrayList();
      this.__verifiedProverAux = new ArrayList();
      this.__varVariableStateTable = new HashMap();
      this.__conditionalStateList = new ArrayList();
      this.__memoryStateTable = new HashMap();
      this.__memoryList = new ArrayList();
      this.__rumtimeClassesList = new ArrayList();
      this.__stateCounter = 0;
      this.__conditionCounter = 0;
   }

   public static CircuitGeneratorMod __getActiveCircuitGenerator() {
      return __instance;
   }

   protected void outsource() {
   }

   public final void __generateCircuit() {
      System.out.println("[1st Phase] Running Initial Circuit Analysis for < " + this.__circuitName + " >");
      this.__phase1();
      System.out.println("[2nd Phase] Running Circuit Generator for < " + this.__circuitName + " >");
      this.__phase2();
      if (Config.multivariateExpressionMinimization) {
         System.out.println("Initial Circuit Generation Done for < " + this.__circuitName + " >  \n \t Current total number of constraints :  " + this.__getNumOfConstraints() + "\n");
         System.out.println("Now: attempting to apply multivariate expression minimization (might take time/require memory depending on how large the circuit is)");
         if (!Config.arithOptimizerIncrementalMode) {
            System.out.println("** Note: If the size of memory is a bottleneck, e.g., the circuit size is very large, enabling Config.arithOptimizerIncrementalMode could help.");
         }
      } else {
         System.out.println("Circuit Generation Done for < " + this.__circuitName + " >  \n \t Total Number of Constraints :  " + this.__getNumOfConstraints() + "\n");
      }

      this.__nonOptimalWireCount = this.__currentWireId;
      if (Config.multivariateExpressionMinimization) {
         this.__evaluationQueue = this.__copyEvalSeq(this.__evaluationQueue);
         this.__circuitOptimizer = new CircuitOptimizer(this);
      }

      if (Config.writeCircuits) {
         this.__writeCircuitFile(Config.multivariateExpressionMinimization ? "_optimized" : "");
      }

   }

   private void __phase2() {
      this.__declareGenericConstants();
      this.__init();
      this.__defineInputs();
      this.__defineVerifiedWitnesses();
      this.__defineWitnesses();
      Iterator var2 = this.__rumtimeClassesList.iterator();

      while(var2.hasNext()) {
         Class<? extends RuntimeStruct> c = (Class)var2.next();

         try {
            Method m = c.getMethod("____reset");
            m.invoke((Object)null);
         } catch (Exception var4) {
            var4.printStackTrace();
         }
      }

      this.outsource();
      this.__checkWitnesses();
      this.__defineOutputs();
      var2 = this.__memoryList.iterator();

      while(var2.hasNext()) {
         SmartMemory<?> mem = (SmartMemory)var2.next();
         mem.finalize();
      }

      if (this.__stateCounter != this.__stateCounterPhase1) {
         System.err.println("Internal Inconsistency Detected! -- Inconsistent State Counters [" + this.__stateCounterPhase1 + "," + this.__stateCounter + "]");
         throw new RuntimeException("Inconsistent state counters.");
      }
   }

   private void __checkWitnesses() {
      Iterator var2 = this.__verifiedProverAux.iterator();

      while(var2.hasNext()) {
         IAuxType t = (IAuxType)var2.next();
         t.verifyRange();
      }

   }

   private void __phase1() {
      this.__phase = 0;
      this.__declareGenericConstants();
      this.__init();
      this.__defineInputs();
      this.__defineVerifiedWitnesses();
      this.__defineWitnesses();
      this.outsource();
      this.__checkWitnesses();
      this.__defineOutputs();
      Iterator var2 = this.__memoryList.iterator();

      while(var2.hasNext()) {
         SmartMemory<?> mem = (SmartMemory)var2.next();
         mem.analyzeWorkload();
      }

      this.__stateCounterPhase1 = this.__stateCounter;
      this.__clear();
      System.out.println("Phase 1: Analysis Completed!");
      ++this.__phase;
   }

   private LinkedHashMap<Instruction, Instruction> __copyEvalSeq(LinkedHashMap<Instruction, Instruction> evaluationQueue) {
      LinkedHashMap<Instruction, Instruction> c = new LinkedHashMap();
      this.__oneWire = this.__oneWire.copy();
      Wire[] wireList = new Wire[this.__getCurrentWireId()];
      wireList[0] = this.__oneWire;
      Iterator var5 = evaluationQueue.keySet().iterator();

      while(var5.hasNext()) {
         Instruction i = (Instruction)var5.next();
         Instruction copiedInstruction = i.copy(wireList);
         if (copiedInstruction != null) {
            c.put(copiedInstruction, copiedInstruction);
         }
      }

      this.__zeroWire = wireList[1];
      this.__knownConstantWires.clear();
      this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
      this.__knownConstantWires.put(BigInteger.ZERO, this.__zeroWire);
      return c;
   }

   private void __clear() {
      this.__inWires.clear();
      this.__outWires.clear();
      this.__proverWitnessWires.clear();
      this.__evaluationQueue.clear();
      this.__nonOptimizedEvaluationQueue.clear();
      this.__knownConstantWires.clear();
      this.__inputAux.clear();
      this.__proverAux.clear();
      this.__verifiedProverAux.clear();
      this.__currentWireId = 0;
      this.__stateCounter = 0;
      this.__conditionCounter = 0;
      this.__numOfConstraints = 0;
      this.__memoryList.clear();
      SmartMemory.globalMemoryCounter = 0;
   }

   public String __getName() {
      return this.__circuitName;
   }

   public void __generateSampleInput(CircuitEvaluator evaluator) {
   }

   public Wire __createInputWire(String... desc) {
      Wire newInputWire = new VariableWire(this.__currentWireId++);
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.input, newInputWire, desc));
      this.__inWires.add(newInputWire);
      return newInputWire;
   }

   public Wire[] __createInputWireArray(int n, String... desc) {
      Wire[] list = new Wire[n];

      for(int i = 0; i < n; ++i) {
         if (desc.length == 0) {
            list[i] = this.__createInputWire("");
         } else {
            list[i] = this.__createInputWire(desc[0] + " " + i);
         }
      }

      return list;
   }

   public Wire __createProverWitnessWire(String... desc) {
      Wire wire = new VariableWire(this.__currentWireId++);
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.nizkinput, wire, desc));
      this.__proverWitnessWires.add(wire);
      return wire;
   }

   public Wire[] __createProverWitnessWireArray(int n, String... desc) {
      Wire[] ws = new Wire[n];

      for(int k = 0; k < n; ++k) {
         if (desc.length == 0) {
            ws[k] = this.__createProverWitnessWire("");
         } else {
            ws[k] = this.__createProverWitnessWire(desc[0] + " " + k);
         }
      }

      return ws;
   }

   public Wire[] __generateZeroWireArray(int n) {
      Wire[] zeroWires = new ConstantWire[n];
      Arrays.fill(zeroWires, this.__zeroWire);
      return zeroWires;
   }

   public Wire[] __generateOneWireArray(int n) {
      Wire[] oneWires = new ConstantWire[n];
      Arrays.fill(oneWires, this.__oneWire);
      return oneWires;
   }

   public Wire __makeOutput(Wire wire, String... desc) {
      Wire outputWire = wire;
      if ((wire instanceof VariableWire || wire instanceof VariableBitWire) && !this.__inWires.contains(wire)) {
         if (!this.__inWires.contains(wire) && !this.__proverWitnessWires.contains(wire)) {
            wire.packIfNeeded();
         } else {
            outputWire = this.__makeVariable(wire, desc);
         }
      } else {
         wire.packIfNeeded(desc);
         outputWire = this.__makeVariable(wire, desc);
      }

      this.__outWires.add(outputWire);
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.output, outputWire, desc));
      return outputWire;
   }

   protected Wire __makeVariable(Wire wire, String... desc) {
      Wire outputWire = new VariableWire(this.__currentWireId++);
      Instruction op = new MulBasicOp(wire, this.__oneWire, outputWire, desc);
      Wire[] cachedOutputs = this.__addToEvaluationQueue(op);
      if (cachedOutputs == null) {
         return outputWire;
      } else {
         --this.__currentWireId;
         return cachedOutputs[0];
      }
   }

   public Wire[] __makeOutputArray(Wire[] wires, String... desc) {
      Wire[] outs = new Wire[wires.length];

      for(int i = 0; i < wires.length; ++i) {
         if (desc.length == 0) {
            outs[i] = this.__makeOutput(wires[i], "");
         } else {
            outs[i] = this.__makeOutput(wires[i], desc[0] + "[" + i + "]");
         }
      }

      return outs;
   }

   public void __addDebugInstruction(Wire w, String... desc) {
      w.packIfNeeded();
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.debug, w, desc));
   }

   public void __addDebugInstruction(IAuxType t, String... desc) {
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.debug, t.copy(), desc));
   }

   public void __addDebugInstruction(PackedValue v, String... desc) {
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.debug, v, desc));
   }

   public void __addDebugInstruction(Wire[] wires, String... desc) {
      for(int i = 0; i < wires.length; ++i) {
         wires[i].packIfNeeded();
         this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.debug, wires[i], new String[]{desc.length > 0 ? desc[0] + " - " + i : ""}));
      }

   }

   public void __writeCircuitFile(String arg) {
      try {
         PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(Config.outputFilesPath + (Config.outputFilesPath.isEmpty() ? "" : File.separator) + this.__getName() + arg + ".arith")));
         printWriter.println("total " + this.__currentWireId);
         System.out.println("Instruction queue size: "+this.__evaluationQueue.size());
         Iterator var4 = this.__evaluationQueue.keySet().iterator();

         while(var4.hasNext()) {
            Instruction e = (Instruction)var4.next();
            if (e.doneWithinCircuit()) {
               printWriter.print(e + "\n");
            }
         }

         printWriter.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }
   //Working here
   public void __readCircuitFile(String path) {


   }
   //Working here
   // - wireMapping: list of all wires to be used in next instruction commands
   // - consider if create all wires at start or one at a time
   public Wire[] buildCircuit(String path) throws FileNotFoundException {
      ArrayList<Wire> proverWitnessWires = new ArrayList();
      ArrayList<Wire> outputWires = new ArrayList();
      Scanner scanner = new Scanner(new File(path));
      //Wire[] wireMapping;
      if (!scanner.next().equals("total")) {
         scanner.close();
         throw new RuntimeException("Expected total %d in the first line");
      } else {
         int numWires = scanner.nextInt();
         scanner.nextLine();
         scanner.nextLine();
         scanner.nextLine();
         this.__declareGenericConstants();
         Wire[] wireMapping = new Wire[numWires];
         wireMapping[0] = this.__oneWire;
         wireMapping[1] = this.__zeroWire;

         int inputCount = 0;
         int statusCount = 0;
         boolean act=false;
         while(true) {
            while(true) {
               statusCount++;
               if(statusCount%10000 ==0)
               {
                  System.out.println(statusCount);
               }
               if(statusCount >= 1167470)
               {
                  act = true;
               }
               if(statusCount>1167480)
               {
                  act=false;
               }
               String line;
               do {
                  if (!scanner.hasNext()) {
                     scanner.close();
                     this.__nonOptimalWireCount = this.__currentWireId;
                     //this.__proverWitnessWires = new Wire[proverWitnessWires.size()];
                     //proverWitnessWires.toArray(this.__proverWitnessWires);
                     //this.__outWires = new Wire[outputWires.size()];
                     //outputWires.toArray(this.__outWires);
                     return wireMapping;
                  }

                  line = scanner.nextLine();
                  if (line.contains("#")) {
                     line = line.substring(0, line.indexOf("#"));
                  }
               } while(line.equals(""));
               int in;
               String[] tokens;
               if (line.startsWith("input")) {
                  tokens = line.split("\\s+");
                  in = Integer.parseInt(tokens[1]);
                  //System.out.println(in +" "+line);
                  if (wireMapping[in] != null) {
                     this.throwParsingError(scanner, "Wire assigned twice! " + in);
                  }
                  wireMapping[in] = __createInputWire(); //check if it's necessary to add the inputAux
                  //inputwires.add(...)
               }
               else if (line.startsWith("output")) {
                  tokens = line.split("\\s+");
                  in = Integer.parseInt(tokens[1]);
                  wireMapping[in] = __makeOutput(wireMapping[in]);
                  //outputWires.add(wireMapping[in]);
               } else {
                  Wire result;
                  if (line.startsWith("nizkinput")) {
                     tokens = line.split("\\s+");
                     in = Integer.parseInt(tokens[1]);
                     if (wireMapping[in] != null) {
                        this.throwParsingError(scanner, "Wire assigned twice! " + in);
                     }
                     result = this.__instance.__createProverWitnessWire();
                     proverWitnessWires.add(result); //necessary?
                     wireMapping[in] = result;
                  } //TODO: implement debug?
                  //else: instruction is an operation (BasicOP potentially)
                  else {
                     ArrayList<Integer> ins = this.parseInputs(line);
                     Iterator ins_iterator = ins.iterator();

                     while(ins_iterator.hasNext()) {
                        in = (Integer)ins_iterator.next();
                        if (wireMapping[in] == null) {
                           this.throwParsingError(scanner, "Undefined input wire " + in + " at line " + line);
                        }
                     }

                     ArrayList<Integer> outs = this.parseOutputs(line);
                     if (line.startsWith("mul ")) {
                        wireMapping[(Integer)outs.get(0)] = wireMapping[(Integer)ins.get(0)].mul(wireMapping[(Integer)ins.get(1)]);
                     } else {
                        if(act){ System.out.println(line + ins);}
                        int i;
                        if (!line.startsWith("add ")) {
                           if (line.startsWith("zerop ")) {
                              wireMapping[(Integer)outs.get(1)] = wireMapping[(Integer)ins.get(0)].checkNonZero();
                           }
                           //split works ok
                           else if (line.startsWith("split ")) {
                              Wire[] bits = wireMapping[(Integer)ins.get(0)].getBitWires(outs.size()).asArray();

                              for(i = 0; i < outs.size(); ++i) {
                                 wireMapping[(Integer)outs.get(i)] = bits[i];
                              }
                           }
                           else if (line.startsWith("pack ")){
                              //convert this, guess if something else is needed to implement pack
                              //TODO: continue from here!!
                              Wire wire = new Wire(outs.get(0));
                              this.__instance.__currentWireId++;
                              wireMapping[(Integer)outs.get(0)] = wire;
                              //System.out.println(wireMapping[(Integer)outs.get(0)]);
                              //output has some problems: check if needs to be initialized somehow
                              //System.out.println("pack");
                              Wire[] arr = new Wire[ins.size()];
                              int temp=0;
                              for (int ind : ins){
                                 arr[temp]=wireMapping[ind];
                                 //System.out.println(wireMapping[ind]);
                                 temp++;
                              }
                              Instruction op = new PackBasicOp(arr, wire);
                              //System.out.println("out");
                              Wire[] w = this.__instance.__addToEvaluationQueue(op);
                              //

                           }
                           else if (line.startsWith("xor ")){
                              wireMapping[(Integer)outs.get(0)] = wireMapping[(Integer)ins.get(0)].xor(wireMapping[(Integer)ins.get(1)]);
                           }
                           else if(line.startsWith("assert ")){
                              //this.__instance.__currentWireId++;
                              //wireMapping[(Integer)outs.get(0)] = new Wire(outs.get(0));
                              __addAssertion(wireMapping[(Integer)ins.get(0)], wireMapping[(Integer)ins.get(1)], wireMapping[(Integer)outs.get(0)]);

                              //zeroAssertion
                              /*if(ins.get(1) == 0){

                                 wireMapping[(Integer)outs.get(0)] = __addZeroAssertion(wireMapping[ins.get(0)]);
                              }
                              else if(ins.get(1) == 1){

                                 wireMapping[(Integer)outs.get(0)] = __addOneAssertion(wireMapping[ins.get(0)]);
                              }*/
                           }else {
                              String constantStr;
                              BigInteger constant;
                              if (line.startsWith("const-mul-neg-")) {
                                 constantStr = line.substring("const-mul-neg-".length(), line.indexOf(" "));
                                 constant = new BigInteger(constantStr, 16);
                                 wireMapping[(Integer)outs.get(0)] = wireMapping[(Integer)ins.get(0)].mul(constant.negate());
                              } else if (line.startsWith("const-mul-")) {
                                 constantStr = line.substring("const-mul-".length(), line.indexOf(" "));
                                 constant = new BigInteger(constantStr, 16);
                                 //System.out.println("Const-mul-"+constant+" "+wireMapping[(Integer)outs.get(0)]);
                                 wireMapping[(Integer)outs.get(0)] = wireMapping[(Integer)ins.get(0)].mul(constant);
                              } else {
                                 this.throwParsingError(scanner, "Unsupport Circuit Line " + line);
                              }
                           }
                        } else {
                           result = wireMapping[(Integer)ins.get(0)];

                           for(i = 1; i < ins.size(); ++i) {
                              result = result.add(wireMapping[(Integer)ins.get(i)]);
                           }

                           wireMapping[(Integer)outs.get(0)] = result;
                        }
                     }
                  }
               }
            }
         }

      }
   }
   public void writePublicInputs(String arg) {
      try {
         LinkedHashMap<Instruction, Instruction> evalSequence = this.__getEvaluationQueue();
         PrintWriter printWriter = new PrintWriter(Config.outputFilesPath + (Config.outputFilesPath.isEmpty() ? "" : File.separator) + this.__getName() + "_" + arg + ".in");
         Iterator var5 = evalSequence.keySet().iterator();
         BigInteger[] valueAssignment = this.__circuitEvaluator.getAssignment();
         while(true) {
            Instruction e;
            do {
               do {
                  if (!var5.hasNext()) {
                     printWriter.close();
                     return;
                  }

                  e = (Instruction)var5.next();
               } while(!(e instanceof WireLabelInstruction));
            } while(((WireLabelInstruction)e).getType() != WireLabelInstruction.LabelType.input );

            int id = ((WireLabelInstruction)e).getWire().getWireId();
            System.out.println(valueAssignment[id]);
            printWriter.println(id + " " + valueAssignment[id].toString(16));
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   private static ArrayList<Integer> parseInputs(String line) {
      Scanner scanner = new Scanner(line.substring(line.indexOf("<") + 1, line.indexOf(">")));
      ArrayList<Integer> line_inputs = new ArrayList();
      while(scanner.hasNextInt()) {
         line_inputs.add(scanner.nextInt());
      }
      scanner.close();
      return line_inputs;
   }
   private static ArrayList<Integer> parseOutputs(String line) {
      Scanner scanner = new Scanner(line.substring(line.lastIndexOf("<") + 1, line.lastIndexOf(">")));
      ArrayList<Integer> outs = new ArrayList();
      while(scanner.hasNextInt()) {
         int v = scanner.nextInt();
         outs.add(v);
      }
      scanner.close();
      return outs;
   }
   private void throwParsingError(Scanner s, String m) {
      s.close();
      throw new RuntimeException(m);
   }
   public void __printCircuit() {
      Iterator var2 = this.__evaluationQueue.keySet().iterator();

      while(var2.hasNext()) {
         Instruction e = (Instruction)var2.next();
         if (e.doneWithinCircuit()) {
            System.out.println(e);
         }
      }

   }

   private void __declareGenericConstants() {
      this.__oneWire = new ConstantWire(this.__currentWireId++, BigInteger.ONE);
      this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
      this.__addToEvaluationQueue(new WireLabelInstruction(WireLabelInstruction.LabelType.input, this.__oneWire, new String[]{"The one-input wire."}));
      this.__inWires.add(this.__oneWire);
      this.__zeroWire = this.__oneWire.mul(0L);
   }

   public void __init() {
   }

   public void __evaluateSampleRun(SampleRun sampleRun) {
      if (sampleRun.isEnabled()) {
         System.out.println("Running Sample Run: " + sampleRun.getName());
         this.__knownConstantWires.clear();
         this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
         this.__circuitEvaluator = new CircuitEvaluator(this.__nonOptimalWireCount);
         sampleRun.pre();
         System.out.println("Evaluating Input on the circuit " + (Config.multivariateExpressionMinimization ? "without multivariate optimizations attempts" : ""));
         this.__circuitEvaluator.evaluate(this.__nonOptimizedEvaluationQueue);
         sampleRun.post();
         System.out.println("Evaluation Done ");
         if (Config.multivariateExpressionMinimization) {
            System.out.println("Evaluating Input on the circuit after multivariate optimizations attempt");
            this.__knownConstantWires.clear();
            this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
            this.__circuitEvaluator = this.__circuitOptimizer.mapFromOldEvaluationSeq(this.__circuitEvaluator);
            System.out.println("Evaluation Done");
            System.out.println("[Pass] Output values after multivariate optimizations match the previous output of the circuit.");
         }

         System.out.println("Sample Run: " + sampleRun.getName() + " finished!");
         if (Config.writeCircuits) {
            this.__prepInputFile(sampleRun.getName() + (Config.multivariateExpressionMinimization ? "_optimized" : ""));
         }
      }
   }
   public void __generatePublicInputs(SampleRun sampleRun) {
      if (sampleRun.isEnabled()) {
         System.out.println("Generating public parameters for: " + sampleRun.getName());
         this.__knownConstantWires.clear();
         this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
         this.__circuitEvaluator = new CircuitEvaluator(this.__nonOptimalWireCount);
         sampleRun.pre();
         //System.out.println("Evaluating Input on the circuit " + (Config.multivariateExpressionMinimization ? "without multivariate optimizations attempts" : ""));
         //this.__circuitEvaluator.evaluate(this.__nonOptimizedEvaluationQueue);
         //sampleRun.post();
         //System.out.println("Evaluation Done ");
         if (Config.multivariateExpressionMinimization) {
            System.out.println("Evaluating Input on the circuit after multivariate optimizations attempt");
            this.__knownConstantWires.clear();
            this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
            this.__circuitEvaluator = this.__circuitOptimizer.mapFromOldEvaluationSeq(this.__circuitEvaluator);
            System.out.println("Evaluation Done");
            System.out.println("[Pass] Output values after multivariate optimizations match the previous output of the circuit.");
         }

         System.out.println("Sample Run: " + sampleRun.getName() + " finished!");
         if (Config.writeCircuits) {
            this.writePublicInputs(sampleRun.getName() + (Config.multivariateExpressionMinimization ? "_optimized" : "" + ".pub"));
         }
      }
   }

   public Wire __createConstantWire(BigInteger x, String... desc) {
      return this.__oneWire.mul(x, desc);
   }

   public Wire[] __createConstantWireArray(BigInteger[] a, String... desc) {
      Wire[] w = new Wire[a.length];

      for(int i = 0; i < a.length; ++i) {
         w[i] = this.__createConstantWire(a[i], desc);
      }

      return w;
   }

   public Wire __createConstantWire(long x, String... desc) {
      return this.__oneWire.mul(x, desc);
   }

   public Wire[] __createConstantWireArray(long[] a, String... desc) {
      Wire[] w = new Wire[a.length];

      for(int i = 0; i < a.length; ++i) {
         w[i] = this.__createConstantWire(a[i], desc);
      }

      return w;
   }

   public Wire __createNegConstantWire(BigInteger x, String... desc) {
      return this.__oneWire.mul(x.negate(), desc);
   }

   public Wire __createNegConstantWire(long x, String... desc) {
      return this.__oneWire.mul(-x, desc);
   }

   public void __specifyProverWitnessComputation(Instruction instruction) {
      this.__addToEvaluationQueue(instruction);
   }

   public final Wire __getZeroWire() {
      return this.__zeroWire;
   }

   public final Wire __getOneWire() {
      return this.__oneWire;
   }

   public LinkedHashMap<Instruction, Instruction> __getEvaluationQueue() {
      //System.out.println(this.__evaluationQueue);
      return this.__evaluationQueue;
   }

   public int __getNumWires() {
      return this.__currentWireId;
   }

   public Wire[] __addToEvaluationQueue(Instruction e) {
      this.__lastInstructionAdded = e;
      if (this.__evaluationQueue.containsKey(e) && e instanceof BasicOp) {
         return ((BasicOp)this.__evaluationQueue.get(e)).getOutputs();
      } else {
         if (e instanceof BasicOp) {
            this.__numOfConstraints += ((BasicOp)e).getNumMulGates();
            ((BasicOp)e).getNumMulGates();
         }

         this.__evaluationQueue.put(e, e);
         return null;
      }
   }

   public void __printState(String message) {
      System.out.println("\nGenerator State @ " + message);
      System.out.println("\tCurrent Number of Multiplication Gates  :: " + this.__numOfConstraints + "\n");
   }

   public int __getNumOfConstraints() {
      return this.__numOfConstraints;
   }

   public ArrayList<Wire> __getInWires() {
      return this.__inWires;
   }

   public ArrayList<Wire> __getOutWires() {
      return this.__outWires;
   }

   public ArrayList<Wire> __getProverWitnessWires() {
      return this.__proverWitnessWires;
   }

   public void __addAssertion(Wire w1, Wire w2, Wire w3, String... desc) {
      if (w1 instanceof ConstantWire && w2 instanceof ConstantWire && w3 instanceof ConstantWire) {
         BigInteger const1 = ((ConstantWire)w1).getConstant();
         BigInteger const2 = ((ConstantWire)w2).getConstant();
         BigInteger const3 = ((ConstantWire)w3).getConstant();
         if (!const3.equals(const1.multiply(const2).mod(Config.getFiniteFieldModulus()))) {
            throw new RuntimeException("This assertion can never work on the provided constant wires .. ");
         }
      } else {
         w1.packIfNeeded();
         w2.packIfNeeded();
         w3.packIfNeeded();
         if (ConditionalScopeTracker.getCurrentScopeId() > 0) {
            Wire active = ConditionalScopeTracker.getAccumActiveBit().getWire();
            AssertBasicOp op;
            if (w1 instanceof ConstantWire) {
               op = new AssertBasicOp(w1.mul(active), w2, w3.mul(active), desc);
               this.__addToEvaluationQueue(op);
            } else {
               op = new AssertBasicOp(w1, w2.mul(active), w3.mul(active), desc);
               this.__addToEvaluationQueue(op);
            }
         } else {
            Instruction op = new AssertBasicOp(w1, w2, w3, desc);
            this.__addToEvaluationQueue(op);
         }
      }

   }

   public void __forceNativeConstraint(FieldElement a, FieldElement b, FieldElement c, String... desc) {
      if (a.isNativeSnarkField() && b.isNativeSnarkField() && c.isNativeSnarkField()) {
         this.__addAssertion(a.getPackedWire().getArray()[0], b.getPackedWire().getArray()[0], c.getPackedWire().getArray()[0], desc);
      } else {
         throw new IllegalArgumentException("Verifying native constraints works only on native field types.");
      }
   }

   public void __addZeroAssertion(Wire w, String... desc) {
      this.__addAssertion(w, this.__oneWire, this.__zeroWire, desc);
   }

   public void __addOneAssertion(Wire w, String... desc) {
      this.__addAssertion(w, this.__oneWire, this.__oneWire, desc);
   }

   public void __addBinaryAssertion(Wire w, String... desc) {
      Wire inv = w.invAsBit(desc);
      this.__addAssertion(w, inv, this.__zeroWire, desc);
   }

   public void __addEqualityAssertion(Wire w1, Wire w2, String... desc) {
      this.__addAssertion(w1, this.__oneWire, w2, desc);
   }

   public void __addEqualityAssertion(Wire w1, BigInteger b, String... desc) {
      this.__addAssertion(w1, this.__oneWire, this.__createConstantWire(b, desc), desc);
   }

   public void __evalCircuit() {
      this.__knownConstantWires.clear();
      this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
      this.__circuitEvaluator = new CircuitEvaluator(this.__nonOptimalWireCount);
      this.__generateSampleInput(this.__circuitEvaluator);
      System.out.println(this.__nonOptimizedEvaluationQueue);
      this.__circuitEvaluator.evaluate(this.__nonOptimizedEvaluationQueue);
      if (Config.multivariateExpressionMinimization) {
         this.__knownConstantWires.clear();
         this.__circuitEvaluator = this.__circuitOptimizer.mapFromOldEvaluationSeq(this.__circuitEvaluator);
      }

   }

   public void __prepFiles() {
      this.__writeCircuitFile("");
      if (this.__circuitEvaluator == null) {
         throw new NullPointerException("evalCircuit() must be called before prepFiles()");
      } else {
         this.__circuitEvaluator.writeInputFile("");
      }
   }

   public void __prepFiles(String arg) {
      this.__writeCircuitFile(arg);
      if (this.__circuitEvaluator == null) {
         throw new NullPointerException("evalCircuit() must be called before prepFiles()");
      } else {
         this.__circuitEvaluator.writeInputFile(arg);
      }
   }

   public void __prepInputFile(String arg) {
      if (this.__circuitEvaluator == null) {
         throw new NullPointerException("evalCircuit() must be called before prepFiles()");
      } else {
         this.__circuitEvaluator.writeInputFile(arg);
      }
   }

   public CircuitEvaluator __getCircuitEvaluator() {
      if (this.__circuitEvaluator == null) {
         throw new NullPointerException("evalCircuit() must be called before getCircuitEvaluator()");
      } else {
         return this.__circuitEvaluator;
      }
   }

   public void __defineInputs() {
   }

   public void __defineWitnesses() {
   }

   public void __defineVerifiedWitnesses() {
   }

   public void __defineOutputs() {
   }

   public int __getPhase() {
      return this.__phase;
   }

   public VariableState __retrieveVariableState() {
      VariableState variableState;
      if (this.__phase == 0) {
         variableState = new VariableState();
         if (!this.__untrackedStateObjects) {
            this.__varVariableStateTable.put(this.__stateCounter, variableState);
            variableState.setId(this.__stateCounter);
            ++this.__stateCounter;
         }

         return variableState;
      } else {
         if (!this.__untrackedStateObjects) {
            variableState = (VariableState)this.__varVariableStateTable.get(this.__stateCounter);
            ++this.__stateCounter;
         } else {
            variableState = new VariableState();
         }

         return variableState;
      }
   }

   public boolean __checkConstantState(Bit b) {
      boolean recalledDecision;
      if (this.__phase == 0) {
         recalledDecision = b.isConstant();
         this.__conditionalStateList.add(recalledDecision);
         ++this.__conditionCounter;
         return recalledDecision;
      } else {
         recalledDecision = (Boolean)this.__conditionalStateList.get(this.__conditionCounter);
         ++this.__conditionCounter;
         return recalledDecision;
      }
   }

   public void __setUntrackedStateObjects(boolean untrackedStateObjects) {
      this.__untrackedStateObjects = untrackedStateObjects;
   }

   public int __getStateCounter() {
      return this.__stateCounter;
   }

   public void __setEvaluationQueue(LinkedHashMap<Instruction, Instruction> evaluationQueue) {
      this.__knownConstantWires.clear();
      this.__knownConstantWires.put(BigInteger.ONE, this.__oneWire);
      this.__evaluationQueue = evaluationQueue;
   }

   public int __getCurrentWireId() {
      return this.__currentWireId;
   }

   public void __setCurrentWireId(int newWireCount) {
      this.__currentWireId = newWireCount;
   }

   public Instruction __getLastInstructionAdded() {
      return this.__lastInstructionAdded;
   }

   public ArrayList<IAuxType> __getInputAux() {
      return this.__inputAux;
   }

   public ArrayList<IAuxType> __getProverAux() {
      return this.__proverAux;
   }

   public ArrayList<IAuxType> __getProverVerifiedAux() {
      return this.__verifiedProverAux;
   }

   public void __generateRandomInput(CircuitEvaluator evaluator) {
      Iterator var3 = this.__inputAux.iterator();

      while(var3.hasNext()) {
         IAuxType t = (IAuxType)var3.next();
         t.mapRandomValue(evaluator);
      }

   }

   public PackedValue __createConstantPackedValue(BigInteger constant, int bitWidth) {
      if (bitWidth <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
         return new PackedValue(this.__createConstantWire(constant), constant);
      } else {
         constant = constant.mod((new BigInteger("2")).pow(bitWidth));
         int numChunks = (int)Math.ceil((double)constant.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         BigInteger[] chunks = Util.split(constant, numChunks, UnsignedInteger.BITWIDTH_PER_CHUNK);
         Wire[] array = new Wire[numChunks];

         for(int i = 0; i < numChunks; ++i) {
            array[i] = this.__createConstantWire(chunks[i]);
         }

         return new PackedValue(array, chunks);
      }
   }

   public PackedValue __createConstantPackedValue(BigInteger constant, BigInteger modulus) {
      if (modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !modulus.equals(Config.getFiniteFieldModulus())) {
         constant = constant.mod(modulus);
         int numChunks = (int)Math.ceil((double)constant.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         BigInteger[] chunks = Util.split(constant, numChunks, UnsignedInteger.BITWIDTH_PER_CHUNK);
         Wire[] array = new Wire[numChunks];

         for(int i = 0; i < numChunks; ++i) {
            array[i] = this.__createConstantWire(chunks[i]);
         }

         return new PackedValue(array, chunks);
      } else {
         return new PackedValue(this.__createConstantWire(constant), constant);
      }
   }

   public ArrayList<SmartMemory<?>> __getMemoryList() {
      return this.__memoryList;
   }

   public HashMap<Integer, SmartMemory.MemoryState> __getMemoryStateTable() {
      return this.__memoryStateTable;
   }

   public ArrayList<Class<? extends RuntimeStruct>> __getRumtimeClassesList() {
      return this.__rumtimeClassesList;
   }

   public ArrayList<IAuxType> __getVerifiedProverAux() {
      return this.__verifiedProverAux;
   }
}
