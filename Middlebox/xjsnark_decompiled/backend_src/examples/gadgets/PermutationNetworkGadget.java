package examples.gadgets;

import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class PermutationNetworkGadget extends Gadget {
   private String permutationNetworkId;
   private static int switchIndex = 0;
   private int dim;
   private Wire[][] inputs;
   private Wire[][] outputs;
   private HashMap<Integer, PermutationNetworkAux.DummySwitch> switchMap;
   private SwitchingNetwork network;

   public PermutationNetworkGadget(final Wire[][] inputs, final Wire[][] outputs, int[] bitwidth, final String permutationNetworkId) {
      super();
      this.inputs = inputs;
      this.outputs = outputs;
      this.dim = inputs[0].length;
      this.permutationNetworkId = permutationNetworkId;
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            int[] permutation = (int[])evaluator.getPermutations().get(permutationNetworkId);
            if (permutation == null) {
               permutation = new int[inputs.length];
               boolean[] reserved = new boolean[outputs.length];

               for(int i = 0; i < inputs.length; ++i) {
                  boolean found = false;

                  for(int j = 0; j < outputs.length; ++j) {
                     if (!reserved[j]) {
                        boolean equal = true;

                        for(int k = 0; k < PermutationNetworkGadget.this.dim; ++k) {
                           BigInteger v1 = evaluator.getWireValue(inputs[i][k]);
                           BigInteger v2 = evaluator.getWireValue(outputs[j][k]);
                           if (!v1.equals(v2)) {
                              equal = false;
                              break;
                           }
                        }

                        if (equal) {
                           permutation[i] = j;
                           found = true;
                           reserved[j] = true;
                           break;
                        }
                     }
                  }

                  if (!found) {
                     throw new RuntimeException("No Valid permutation found during evaluation.");
                  }
               }
            }

            PermutationNetworkGadget.this.switchMap = (new PermutationNetworkAux(permutation.length, permutation)).getSetting();
         }
      });
      this.buildCircuit();
   }

   private void buildCircuit() {
      this.network = new SwitchingNetwork(this.inputs, this.outputs);
   }

   public Wire[] getOutputWires() {
      return null;
   }

   public SwitchingNetwork getNetwork() {
      return this.network;
   }

   public class Switch {
      boolean direction;
      int id;
      Wire[] in1;
      Wire[] in2;
      Wire[] out1;
      Wire[] out2;
      Wire selector;

      public Switch(Wire[] w1, Wire[] w2, Wire[] w3) {
         int var10001 = PermutationNetworkGadget.switchIndex;
         PermutationNetworkGadget.switchIndex = var10001 + 1;
         this.id = var10001;
         this.out1 = w1;
         this.out2 = w2;
         this.in1 = w3;
         int dim = w1.length;
         this.in2 = new Wire[dim];
         int i;
         if (dim == 1) {
            PermutationNetworkGadget.this.generator.__addAssertion(this.in1[0].sub(this.out1[0]), this.in1[0].sub(this.out2[0]), PermutationNetworkGadget.this.generator.__getZeroWire());
         } else {
            this.selector = PermutationNetworkGadget.this.generator.__createProverWitnessWire();
            PermutationNetworkGadget.this.generator.__specifyProverWitnessComputation(new Instruction() {
               public void evaluate(CircuitEvaluator evaluator) {
                  if (((PermutationNetworkAux.DummySwitch)PermutationNetworkGadget.this.switchMap.get(Switch.this.id)).direction) {
                     evaluator.setWireValue(Switch.this.selector, BigInteger.ONE);
                  } else {
                     evaluator.setWireValue(Switch.this.selector, BigInteger.ZERO);
                  }

               }
            });
            PermutationNetworkGadget.this.generator.__addBinaryAssertion(this.selector);

            for(i = 0; i < dim; ++i) {
               PermutationNetworkGadget.this.generator.__addAssertion(this.selector, this.out2[i].sub(this.out1[i]), this.in1[i].sub(this.out1[i]));
            }
         }

         for(i = 0; i < dim; ++i) {
            this.in2[i] = w1[i].add(w2[i]).sub(this.in1[i]);
         }

      }

      public Switch(Wire[] w1, Wire[] w2, boolean isInput) {
         int var10001 = PermutationNetworkGadget.switchIndex;
         PermutationNetworkGadget.switchIndex = var10001 + 1;
         this.id = var10001;
         int dim;
         int i;
         if (isInput) {
            this.in1 = w1;
            this.in2 = w2;
            dim = w1.length;
            this.out1 = new Wire[dim];
            this.out2 = new Wire[dim];
            if (dim == 1) {
               this.out1 = PermutationNetworkGadget.this.generator.__createProverWitnessWireArray(1);
               PermutationNetworkGadget.this.generator.__specifyProverWitnessComputation(new Instruction() {
                  public void evaluate(CircuitEvaluator evaluator) {
                     if (((PermutationNetworkAux.DummySwitch)PermutationNetworkGadget.this.switchMap.get(Switch.this.id)).direction) {
                        evaluator.setWireValue(Switch.this.out1[0], evaluator.getWireValue(Switch.this.in2[0]));
                     } else {
                        evaluator.setWireValue(Switch.this.out1[0], evaluator.getWireValue(Switch.this.in1[0]));
                     }

                  }
               });
               this.out2 = new Wire[]{w1[0].add(w2[0]).sub(this.out1[0])};
               PermutationNetworkGadget.this.generator.__addAssertion(this.in1[0].sub(this.out1[0]), this.in2[0].sub(this.out1[0]), PermutationNetworkGadget.this.generator.__getZeroWire());
            } else {
               this.selector = PermutationNetworkGadget.this.generator.__createProverWitnessWire();
               PermutationNetworkGadget.this.generator.__specifyProverWitnessComputation(new Instruction() {
                  public void evaluate(CircuitEvaluator evaluator) {
                     if (((PermutationNetworkAux.DummySwitch)PermutationNetworkGadget.this.switchMap.get(Switch.this.id)).direction) {
                        evaluator.setWireValue(Switch.this.selector, BigInteger.ONE);
                     } else {
                        evaluator.setWireValue(Switch.this.selector, BigInteger.ZERO);
                     }

                  }
               });
               PermutationNetworkGadget.this.generator.__addBinaryAssertion(this.selector);

               for(i = 0; i < dim; ++i) {
                  this.out1[i] = this.in1[i].add(this.selector.mul(this.in2[i].sub(this.in1[i])));
                  this.out2[i] = this.in1[i].add(this.in2[i]).sub(this.out1[i]);
               }
            }
         } else {
            this.out1 = w1;
            this.out2 = w2;
            dim = w1.length;
            this.in1 = new Wire[dim];
            this.in2 = new Wire[dim];
            if (dim == 1) {
               this.in1 = PermutationNetworkGadget.this.generator.__createProverWitnessWireArray(1);
               PermutationNetworkGadget.this.generator.__specifyProverWitnessComputation(new Instruction() {
                  public void evaluate(CircuitEvaluator evaluator) {
                     if (((PermutationNetworkAux.DummySwitch)PermutationNetworkGadget.this.switchMap.get(Switch.this.id)).direction) {
                        evaluator.setWireValue(Switch.this.in1[0], evaluator.getWireValue(Switch.this.out2[0]));
                     } else {
                        evaluator.setWireValue(Switch.this.in1[0], evaluator.getWireValue(Switch.this.out1[0]));
                     }

                  }
               });
               this.in2 = new Wire[]{w1[0].add(w2[0]).sub(this.in1[0])};
               PermutationNetworkGadget.this.generator.__addAssertion(this.in1[0].sub(this.out1[0]), this.in1[0].sub(this.out2[0]), PermutationNetworkGadget.this.generator.__getZeroWire());
            } else {
               this.selector = PermutationNetworkGadget.this.generator.__createProverWitnessWire();
               PermutationNetworkGadget.this.generator.__specifyProverWitnessComputation(new Instruction() {
                  public void evaluate(CircuitEvaluator evaluator) {
                     if (((PermutationNetworkAux.DummySwitch)PermutationNetworkGadget.this.switchMap.get(Switch.this.id)).direction) {
                        evaluator.setWireValue(Switch.this.selector, BigInteger.ONE);
                     } else {
                        evaluator.setWireValue(Switch.this.selector, BigInteger.ZERO);
                     }

                  }
               });
               PermutationNetworkGadget.this.generator.__addBinaryAssertion(this.selector);

               for(i = 0; i < dim; ++i) {
                  this.in1[i] = this.out1[i].add(this.selector.mul(this.out2[i].sub(this.out1[i])));
                  this.in2[i] = this.out1[i].add(this.out2[i]).sub(this.in1[i]);
               }
            }
         }

      }

      public Switch(Wire[] in1, Wire[] in2, Wire[] out1, Wire[] out2) {
         int var10001 = PermutationNetworkGadget.switchIndex;
         PermutationNetworkGadget.switchIndex = var10001 + 1;
         this.id = var10001;
         this.in1 = in1;
         this.in2 = in2;
         this.out1 = out1;
         this.out2 = out2;
         int dim = in1.length;
         if (dim == 1) {
            PermutationNetworkGadget.this.generator.__addAssertion(in1[0].sub(out1[0]), in2[0].sub(out1[0]), PermutationNetworkGadget.this.generator.__getZeroWire(), "CASE I");
            PermutationNetworkGadget.this.generator.__addZeroAssertion(in1[0].add(in2[0]).sub(out1[0].add(out2[0])), "CASE II");
         } else {
            this.selector = PermutationNetworkGadget.this.generator.__createProverWitnessWire();
            PermutationNetworkGadget.this.generator.__specifyProverWitnessComputation(new Instruction() {
               public void evaluate(CircuitEvaluator evaluator) {
                  if (((PermutationNetworkAux.DummySwitch)PermutationNetworkGadget.this.switchMap.get(Switch.this.id)).direction) {
                     evaluator.setWireValue(Switch.this.selector, BigInteger.ONE);
                  } else {
                     evaluator.setWireValue(Switch.this.selector, BigInteger.ZERO);
                  }

               }
            });
            PermutationNetworkGadget.this.generator.__addBinaryAssertion(this.selector);

            for(int i = 0; i < dim; ++i) {
               PermutationNetworkGadget.this.generator.__addAssertion(this.selector, out2[i].sub(out1[i]), in1[i].sub(out1[i]));
               PermutationNetworkGadget.this.generator.__addEqualityAssertion(out1[i].add(out2[i]).sub(in1[i]), in2[i]);
            }
         }

      }
   }

   public class SwitchingNetwork {
      int n;
      int dim;
      Switch[] inSwitches;
      Switch[] outSwitches;
      SwitchingNetwork top;
      SwitchingNetwork bottom;
      Wire[][] ins;
      Wire[][] outs;
      int numSwitches = 0;

      public SwitchingNetwork(Wire[][] ins, Wire[][] outs) {
         this.ins = ins;
         this.outs = outs;
         this.dim = ins[0].length;
         this.n = ins.length;
         this.build();
      }

      private void build() {
         if (this.n == 2) {
            this.inSwitches = this.outSwitches = new Switch[1];
            this.inSwitches[0] = PermutationNetworkGadget.this.new Switch(this.ins[0], this.ins[1], this.outs[0], this.outs[1]);
            this.numSwitches = 1;
         } else if (this.n == 3) {
            this.inSwitches = new Switch[1];
            this.outSwitches = new Switch[1];
            this.inSwitches[0] = PermutationNetworkGadget.this.new Switch(this.ins[0], this.ins[1], true);
            this.outSwitches[0] = PermutationNetworkGadget.this.new Switch(this.outs[0], this.outs[1], this.inSwitches[0].out1);
            this.top = null;
            this.bottom = PermutationNetworkGadget.this.new SwitchingNetwork(new Wire[][]{this.inSwitches[0].out2, this.ins[2]}, new Wire[][]{this.outSwitches[0].in2, this.outs[2]});
            this.numSwitches += 2 + this.bottom.numSwitches;
         } else {
            Wire[][] topInput;
            Wire[][] bottomInput;
            Wire[][] topOutput;
            Wire[][] bottomOutput;
            int i;
            if (this.n % 2 == 1) {
               this.inSwitches = new Switch[this.n / 2];
               this.outSwitches = new Switch[this.n / 2];
               topInput = new Wire[this.n / 2][];
               bottomInput = new Wire[this.n / 2 + this.n % 2][];
               topOutput = new Wire[this.n / 2][];
               bottomOutput = new Wire[this.n / 2 + this.n % 2][];

               for(i = 0; i < this.inSwitches.length; ++i) {
                  this.inSwitches[i] = PermutationNetworkGadget.this.new Switch(this.ins[2 * i], this.ins[2 * i + 1], true);
                  topInput[i] = this.inSwitches[i].out1;
                  bottomInput[i] = this.inSwitches[i].out2;
               }

               for(i = 0; i < this.outSwitches.length; ++i) {
                  this.outSwitches[i] = PermutationNetworkGadget.this.new Switch(this.outs[2 * i], this.outs[2 * i + 1], false);
                  topOutput[i] = this.outSwitches[i].in1;
                  bottomOutput[i] = this.outSwitches[i].in2;
               }

               bottomInput[bottomInput.length - 1] = this.ins[this.ins.length - 1];
               bottomOutput[bottomOutput.length - 1] = this.outs[this.outs.length - 1];
               this.top = PermutationNetworkGadget.this.new SwitchingNetwork(topInput, topOutput);
               this.bottom = PermutationNetworkGadget.this.new SwitchingNetwork(bottomInput, bottomOutput);
               this.numSwitches = this.top.numSwitches + this.bottom.numSwitches + this.outSwitches.length + this.inSwitches.length;
            } else if (this.n % 2 == 0) {
               this.inSwitches = new Switch[this.n / 2];
               this.outSwitches = new Switch[this.n / 2 - 1];
               topInput = new Wire[this.n / 2][];
               bottomInput = new Wire[this.n / 2 + this.n % 2][];
               topOutput = new Wire[this.n / 2][];
               bottomOutput = new Wire[this.n / 2 + this.n % 2][];

               for(i = 0; i < this.inSwitches.length; ++i) {
                  this.inSwitches[i] = PermutationNetworkGadget.this.new Switch(this.ins[2 * i], this.ins[2 * i + 1], true);
                  topInput[i] = this.inSwitches[i].out1;
                  bottomInput[i] = this.inSwitches[i].out2;
               }

               for(i = 0; i < this.outSwitches.length; ++i) {
                  this.outSwitches[i] = PermutationNetworkGadget.this.new Switch(this.outs[2 * i], this.outs[2 * i + 1], false);
                  topOutput[i] = this.outSwitches[i].in1;
                  bottomOutput[i] = this.outSwitches[i].in2;
               }

               topOutput[topOutput.length - 1] = this.outs[this.outs.length - 2];
               bottomOutput[bottomOutput.length - 1] = this.outs[this.outs.length - 1];
               this.top = PermutationNetworkGadget.this.new SwitchingNetwork(topInput, topOutput);
               this.bottom = PermutationNetworkGadget.this.new SwitchingNetwork(bottomInput, bottomOutput);
               this.numSwitches = this.top.numSwitches + this.bottom.numSwitches + this.outSwitches.length + this.inSwitches.length;
            }
         }

      }

      public void route(int[] permutation) {
         if (permutation.length == 2) {
            if (permutation[0] != 0) {
               this.inSwitches[0].direction = true;
            } else {
               this.inSwitches[0].direction = false;
            }

         } else {
            int numberOfNodes = (int)Math.ceil((double)permutation.length / 2.0);
            Node[] srcNodes = new Node[numberOfNodes];
            Node[] dstNodes = new Node[numberOfNodes];
            ArrayList<Edge> allEdges = new ArrayList();

            int i;
            for(i = 0; i < numberOfNodes; ++i) {
               srcNodes[i] = new Node(true);
               dstNodes[i] = new Node(false);
            }

            Edge ex;
            for(i = 0; i < permutation.length; ++i) {
               int dstIndex = permutation[i];
               ex = new Edge(srcNodes[i / 2], dstNodes[dstIndex / 2], i, dstIndex);
               allEdges.add(ex);
            }

            dstNodes[dstNodes.length - 1].getSecondEdge().color = true;
            dstNodes[dstNodes.length - 1].getSecondEdge().visited = true;
            Stack<Node> allNodes = new Stack();

            for(int ix = 0; ix < numberOfNodes; ++ix) {
               allNodes.push(srcNodes[ix]);
               allNodes.push(dstNodes[ix]);
            }

            allNodes.push(dstNodes[dstNodes.length - 1]);

            while(true) {
               Node n;
               Edge e;
               Iterator var16;
               do {
                  if (allNodes.isEmpty()) {
                     if (permutation.length >= 3) {
                        int[] upperPermutation = new int[permutation.length / 2];
                        var16 = allEdges.iterator();

                        while(var16.hasNext()) {
                           e = (Edge)var16.next();
                           if (!e.color) {
                              upperPermutation[e.srcIdx / 2] = e.dstIdx / 2;
                           }
                        }

                        if (this.top != null) {
                           this.top.route(upperPermutation);
                        }

                        int[] lowerPermutation = new int[permutation.length / 2 + permutation.length % 2];
                        Iterator var10 = allEdges.iterator();

                        while(var10.hasNext()) {
                           ex = (Edge)var10.next();
                           if (ex.color) {
                              lowerPermutation[ex.srcIdx / 2] = ex.dstIdx / 2;
                           }
                        }

                        if (this.bottom != null) {
                           this.bottom.route(lowerPermutation);
                        }
                     }

                     return;
                  }

                  n = (Node)allNodes.pop();
               } while(n.isVisited());

               n.visited = true;
               n.color();
               n.setSwitch();
               var16 = n.edges.iterator();

               while(var16.hasNext()) {
                  e = (Edge)var16.next();
                  if (!e.src.visited) {
                     allNodes.push(e.src);
                  }

                  if (!e.dst.visited) {
                     allNodes.push(e.dst);
                  }
               }
            }
         }
      }

      public int getNumSwitches() {
         return this.numSwitches;
      }

      class Edge {
         boolean visited;
         boolean color;
         Node src;
         Node dst;
         int srcIdx;
         int dstIdx;

         public Edge(Node src, Node dst, int srcIdx, int dstIdx) {
            this.src = src;
            this.dst = dst;
            this.srcIdx = srcIdx;
            this.dstIdx = dstIdx;
            src.edges.add(this);
            dst.edges.add(this);
         }
      }

      class Node {
         ArrayList<Edge> edges = new ArrayList();
         boolean isSourceNode;
         boolean visited = false;
         boolean switchSetting = false;

         public Node(boolean isSourceNode) {
            this.isSourceNode = isSourceNode;
         }

         public Edge getFirstEdge() {
            if (this.edges.size() == 1) {
               return (Edge)this.edges.get(0);
            } else if (this.isSourceNode) {
               return ((Edge)this.edges.get(0)).srcIdx < ((Edge)this.edges.get(1)).srcIdx ? (Edge)this.edges.get(0) : (Edge)this.edges.get(1);
            } else {
               return ((Edge)this.edges.get(0)).dstIdx < ((Edge)this.edges.get(1)).dstIdx ? (Edge)this.edges.get(0) : (Edge)this.edges.get(1);
            }
         }

         public Edge getSecondEdge() {
            if (this.edges.size() == 1) {
               return (Edge)this.edges.get(0);
            } else if (this.isSourceNode) {
               return ((Edge)this.edges.get(0)).srcIdx > ((Edge)this.edges.get(1)).srcIdx ? (Edge)this.edges.get(0) : (Edge)this.edges.get(1);
            } else {
               return ((Edge)this.edges.get(0)).dstIdx > ((Edge)this.edges.get(1)).dstIdx ? (Edge)this.edges.get(0) : (Edge)this.edges.get(1);
            }
         }

         public boolean isVisited() {
            return this.visited;
         }

         public Edge getAnyUnvisitedEdge() {
            if (this.edges.size() == 1) {
               return ((Edge)this.edges.get(0)).visited ? null : (Edge)this.edges.get(0);
            } else if (((Edge)this.edges.get(0)).visited) {
               return ((Edge)this.edges.get(1)).visited ? null : (Edge)this.edges.get(1);
            } else {
               return (Edge)this.edges.get(0);
            }
         }

         public void color() {
            if (this.edges.size() == 1) {
               if (!((Edge)this.edges.get(0)).visited) {
                  throw new RuntimeException("You should not be here!");
               }
            } else {
               if (((Edge)this.edges.get(0)).visited) {
                  if (!((Edge)this.edges.get(1)).visited) {
                     ((Edge)this.edges.get(1)).color = !((Edge)this.edges.get(0)).color;
                  }
               } else if (((Edge)this.edges.get(1)).visited) {
                  ((Edge)this.edges.get(0)).color = !((Edge)this.edges.get(1)).color;
               } else {
                  ((Edge)this.edges.get(1)).color = true;
                  ((Edge)this.edges.get(0)).color = false;
               }

               ((Edge)this.edges.get(0)).visited = true;
               ((Edge)this.edges.get(1)).visited = true;
            }

         }

         public void setSwitch() {
            if (this.edges.size() == 1) {
               this.switchSetting = false;
            } else if (this.isSourceNode) {
               this.switchSetting = ((Edge)this.edges.get(0)).srcIdx > ((Edge)this.edges.get(1)).srcIdx && !((Edge)this.edges.get(0)).color || ((Edge)this.edges.get(1)).srcIdx > ((Edge)this.edges.get(0)).srcIdx && !((Edge)this.edges.get(1)).color;
               SwitchingNetwork.this.inSwitches[((Edge)this.edges.get(0)).srcIdx / 2].direction = this.switchSetting;
            } else {
               this.switchSetting = ((Edge)this.edges.get(0)).dstIdx > ((Edge)this.edges.get(1)).dstIdx && !((Edge)this.edges.get(0)).color || ((Edge)this.edges.get(1)).dstIdx > ((Edge)this.edges.get(0)).dstIdx && !((Edge)this.edges.get(1)).color;
               if (((Edge)this.edges.get(0)).dstIdx / 2 < SwitchingNetwork.this.outSwitches.length) {
                  SwitchingNetwork.this.outSwitches[((Edge)this.edges.get(0)).dstIdx / 2].direction = this.switchSetting;
               }
            }

         }
      }
   }
}
