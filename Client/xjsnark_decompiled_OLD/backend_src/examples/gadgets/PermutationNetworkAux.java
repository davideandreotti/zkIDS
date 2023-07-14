package examples.gadgets;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import sandbox.ArrayIndexComparator;
import util.Util;

public class PermutationNetworkAux {
   private HashMap<Integer, DummySwitch> switchMap = new HashMap();
   private static int switchIndex = 0;

   public HashMap<Integer, DummySwitch> getSetting() {
      return this.switchMap;
   }

   public PermutationNetworkAux(int n, int[] permutation) {
      DummyWire[] ins = new DummyWire[n];
      DummyWire[] outs = new DummyWire[n];

      for(int i = 0; i < n; ++i) {
         ins[i] = new DummyWire();
         outs[i] = new DummyWire();
      }

      PermutationNetwork p = new PermutationNetwork(ins, outs);
      p.route(permutation);
   }

   public static void main(String[] args) {
      int n = 1023;
      BigInteger[] a = new BigInteger[n];

      for(int i = 0; i < n; ++i) {
         a[i] = Util.nextRandomBigInteger(200);
      }

      ArrayIndexComparator comparator = new ArrayIndexComparator(a);
      Integer[] indexes = new Integer[n];

      for(int i = 0; i < n; ++i) {
         indexes[i] = i;
      }

      Arrays.sort(indexes, comparator);
      int[] permutation = new int[n];

      for(int i = 0; i < n; permutation[indexes[i]] = i++) {
      }

      DummyWire[] ins = new DummyWire[n];
      DummyWire[] outs = new DummyWire[n];

      for(int i = 0; i < n; ++i) {
         ins[i] = new DummyWire();
         ins[i].value = a[i];
         outs[i] = new DummyWire();
      }

      PermutationNetworkAux aux = new PermutationNetworkAux(n, permutation);
      PermutationNetwork p = aux.new PermutationNetwork(ins, outs);
      System.out.println("Number of switches in constructed permutation network = " + p.numSwitches);
      int numSwitches = 0;

      int i;
      for(i = 1; i <= n; ++i) {
         numSwitches += (int)Math.ceil(Math.log((double)i) / Math.log(2.0));
      }

      if (numSwitches == p.numSwitches) {
         System.out.println("The number of switches matches the equation.");
      } else {
         System.err.println("There is a mismatch -- double check.");
      }

      p.route(permutation);
      p.eval();

      for(i = 1; i < p.outs.length; ++i) {
         if (p.outs[i - 1].value.compareTo(p.outs[i].value) > 0) {
            System.err.println("The outputs are not sorted as expected. There is likely a problem in routing.");
            throw new RuntimeException();
         }
      }

      System.out.println("The output is sorted");
   }

   public class DummySwitch {
      boolean direction;
      DummyWire in1;
      DummyWire in2;
      DummyWire out1;
      DummyWire out2;

      public DummySwitch(DummyWire w1, DummyWire w2, boolean isInput) {
         HashMap var10000 = PermutationNetworkAux.this.switchMap;
         int var10001 = PermutationNetworkAux.switchIndex;
         PermutationNetworkAux.switchIndex = var10001 + 1;
         var10000.put(var10001, this);
         if (isInput) {
            this.in1 = w1;
            this.in2 = w2;
            this.out1 = new DummyWire();
            this.out2 = new DummyWire();
         } else {
            this.out1 = w1;
            this.out2 = w2;
            this.in1 = new DummyWire();
            this.in2 = new DummyWire();
         }

         if (this.in1 == null || this.in2 == null || this.out1 == null || this.out2 == null) {
            throw new NullPointerException();
         }
      }

      public DummySwitch(DummyWire in1, DummyWire in2, DummyWire out1, DummyWire out2) {
         HashMap var10000 = PermutationNetworkAux.this.switchMap;
         int var10001 = PermutationNetworkAux.switchIndex;
         PermutationNetworkAux.switchIndex = var10001 + 1;
         var10000.put(var10001, this);
         if (in1 != null && in2 != null && out1 != null && out2 != null) {
            this.in1 = in1;
            this.in2 = in2;
            this.out1 = out1;
            this.out2 = out2;
         } else {
            throw new NullPointerException();
         }
      }

      void map() {
         if (this.in1.value != null && this.in2.value != null) {
            if (!this.direction) {
               this.out1.value = this.in1.value;
               this.out2.value = this.in2.value;
            } else {
               this.out1.value = this.in2.value;
               this.out2.value = this.in1.value;
            }

         } else {
            throw new NullPointerException();
         }
      }
   }

   public static class DummyWire {
      BigInteger value;
   }

   public class PermutationNetwork {
      int n;
      DummySwitch[] inSwitches;
      DummySwitch[] outSwitches;
      PermutationNetwork top;
      PermutationNetwork bottom;
      DummyWire[] ins;
      DummyWire[] outs;
      int numSwitches = 0;

      public PermutationNetwork(DummyWire[] ins, DummyWire[] outs) {
         this.ins = ins;
         this.outs = outs;
         this.n = ins.length;
         this.build();
      }

      private void build() {
         if (this.n == 2) {
            this.inSwitches = this.outSwitches = new DummySwitch[1];
            this.inSwitches[0] = PermutationNetworkAux.this.new DummySwitch(this.ins[0], this.ins[1], this.outs[0], this.outs[1]);
            this.numSwitches = 1;
         } else if (this.n == 3) {
            this.inSwitches = new DummySwitch[1];
            this.outSwitches = new DummySwitch[1];
            this.inSwitches[0] = PermutationNetworkAux.this.new DummySwitch(this.ins[0], this.ins[1], true);
            this.outSwitches[0] = PermutationNetworkAux.this.new DummySwitch(this.outs[0], this.outs[1], false);
            this.outSwitches[0].in1 = this.inSwitches[0].out1;
            this.top = null;
            this.bottom = PermutationNetworkAux.this.new PermutationNetwork(new DummyWire[]{this.inSwitches[0].out2, this.ins[2]}, new DummyWire[]{this.outSwitches[0].in2, this.outs[2]});
            this.numSwitches += 2 + this.bottom.numSwitches;
         } else {
            DummyWire[] topInput;
            DummyWire[] bottomInput;
            DummyWire[] topOutput;
            DummyWire[] bottomOutput;
            int i;
            if (this.n % 2 == 1) {
               this.inSwitches = new DummySwitch[this.n / 2];
               this.outSwitches = new DummySwitch[this.n / 2];
               topInput = new DummyWire[this.n / 2];
               bottomInput = new DummyWire[this.n / 2 + this.n % 2];
               topOutput = new DummyWire[this.n / 2];
               bottomOutput = new DummyWire[this.n / 2 + this.n % 2];

               for(i = 0; i < this.inSwitches.length; ++i) {
                  this.inSwitches[i] = PermutationNetworkAux.this.new DummySwitch(this.ins[2 * i], this.ins[2 * i + 1], true);
                  topInput[i] = this.inSwitches[i].out1;
                  bottomInput[i] = this.inSwitches[i].out2;
               }

               for(i = 0; i < this.outSwitches.length; ++i) {
                  this.outSwitches[i] = PermutationNetworkAux.this.new DummySwitch(this.outs[2 * i], this.outs[2 * i + 1], false);
                  topOutput[i] = this.outSwitches[i].in1;
                  bottomOutput[i] = this.outSwitches[i].in2;
               }

               bottomInput[bottomInput.length - 1] = this.ins[this.ins.length - 1];
               bottomOutput[bottomOutput.length - 1] = this.outs[this.outs.length - 1];
               this.top = PermutationNetworkAux.this.new PermutationNetwork(topInput, topOutput);
               this.bottom = PermutationNetworkAux.this.new PermutationNetwork(bottomInput, bottomOutput);
               this.numSwitches = this.top.numSwitches + this.bottom.numSwitches + this.outSwitches.length + this.inSwitches.length;
            } else if (this.n % 2 == 0) {
               this.inSwitches = new DummySwitch[this.n / 2];
               this.outSwitches = new DummySwitch[this.n / 2 - 1];
               topInput = new DummyWire[this.n / 2];
               bottomInput = new DummyWire[this.n / 2 + this.n % 2];
               topOutput = new DummyWire[this.n / 2];
               bottomOutput = new DummyWire[this.n / 2 + this.n % 2];

               for(i = 0; i < this.inSwitches.length; ++i) {
                  this.inSwitches[i] = PermutationNetworkAux.this.new DummySwitch(this.ins[2 * i], this.ins[2 * i + 1], true);
                  topInput[i] = this.inSwitches[i].out1;
                  bottomInput[i] = this.inSwitches[i].out2;
               }

               for(i = 0; i < this.outSwitches.length; ++i) {
                  this.outSwitches[i] = PermutationNetworkAux.this.new DummySwitch(this.outs[2 * i], this.outs[2 * i + 1], false);
                  topOutput[i] = this.outSwitches[i].in1;
                  bottomOutput[i] = this.outSwitches[i].in2;
               }

               topOutput[topOutput.length - 1] = this.outs[this.outs.length - 2];
               bottomOutput[bottomOutput.length - 1] = this.outs[this.outs.length - 1];
               this.top = PermutationNetworkAux.this.new PermutationNetwork(topInput, topOutput);
               this.bottom = PermutationNetworkAux.this.new PermutationNetwork(bottomInput, bottomOutput);
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

      public void eval() {
         DummySwitch[] var4;
         int var3 = (var4 = this.inSwitches).length;

         DummySwitch s;
         int var2;
         for(var2 = 0; var2 < var3; ++var2) {
            s = var4[var2];
            s.map();
         }

         if (this.top != null) {
            this.top.eval();
         }

         if (this.bottom != null) {
            this.bottom.eval();
         }

         var3 = (var4 = this.outSwitches).length;

         for(var2 = 0; var2 < var3; ++var2) {
            s = var4[var2];
            s.map();
         }

      }

      public void printOutputs() {
         DummyWire[] var4;
         int var3 = (var4 = this.outs).length;

         for(int var2 = 0; var2 < var3; ++var2) {
            DummyWire w = var4[var2];
            System.out.println("Value = " + w.value);
         }

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
                  throw new RuntimeException("Unexpected case! Please report.");
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
               PermutationNetwork.this.inSwitches[((Edge)this.edges.get(0)).srcIdx / 2].direction = this.switchSetting;
            } else {
               this.switchSetting = ((Edge)this.edges.get(0)).dstIdx > ((Edge)this.edges.get(1)).dstIdx && !((Edge)this.edges.get(0)).color || ((Edge)this.edges.get(1)).dstIdx > ((Edge)this.edges.get(0)).dstIdx && !((Edge)this.edges.get(1)).color;
               if (((Edge)this.edges.get(0)).dstIdx / 2 < PermutationNetwork.this.outSwitches.length) {
                  PermutationNetwork.this.outSwitches[((Edge)this.edges.get(0)).dstIdx / 2].direction = this.switchSetting;
               }
            }

         }
      }
   }
}
