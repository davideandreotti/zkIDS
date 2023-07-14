package backend.eval;

import backend.auxTypes.PackedValue;
import backend.auxTypes.UnsignedInteger;
import backend.config.Config;
import backend.operations.WireLabelInstruction;
import backend.operations.primitive.BasicOp;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Scanner;
import util.Util;

public class CircuitEvaluator {
   private CircuitGenerator circuitGenerator;
   private BigInteger[] valueAssignment;
   private HashMap<String, int[]> permutations;
   private HashMap<Integer, ArrayList<Instruction>> pendingInstructions = new HashMap();

   public CircuitEvaluator(CircuitGenerator circuitGenerator) {
      this.circuitGenerator = circuitGenerator;
      this.permutations = new HashMap();
      this.valueAssignment = new BigInteger[circuitGenerator.__getNumWires()];
      this.valueAssignment[circuitGenerator.__getOneWire().getWireId()] = BigInteger.ONE;
   }

   public CircuitEvaluator(CircuitGenerator circuitGenerator, BigInteger[] valueAssignment) {
      this.circuitGenerator = circuitGenerator;
      this.valueAssignment = valueAssignment;
      this.permutations = new HashMap();
      valueAssignment[circuitGenerator.__getOneWire().getWireId()] = BigInteger.ONE;
   }

   public CircuitEvaluator(int wireCount) {
      this.valueAssignment = new BigInteger[wireCount];
      this.permutations = new HashMap();
      this.valueAssignment[0] = BigInteger.ONE;
      this.circuitGenerator = CircuitGenerator.__getActiveCircuitGenerator();
   }

   public void setWireValue(Wire w, BigInteger v) {
      if (v.signum() >= 0 && v.compareTo(Config.getFiniteFieldModulus()) < 0) {
         if (this.valueAssignment[w.getWireId()] != null) {
            throw new RuntimeException("Element has been assigned before!");
         } else {
            this.valueAssignment[w.getWireId()] = v;
            if (this.pendingInstructions.containsKey(w.getWireId())) {
               Iterator var4 = ((ArrayList)this.pendingInstructions.get(w.getWireId())).iterator();

               while(true) {
                  Instruction i;
                  label34:
                  while(true) {
                     if (!var4.hasNext()) {
                        this.pendingInstructions.remove(w.getWireId());
                        return;
                     }

                     i = (Instruction)var4.next();
                     if (!(i instanceof BasicOp)) {
                        break;
                     }

                     Wire[] inputs = ((BasicOp)i).getInputs();
                     Wire[] var9 = inputs;
                     int var8 = inputs.length;
                     int var7 = 0;

                     while(true) {
                        if (var7 >= var8) {
                           break label34;
                        }

                        Wire w2 = var9[var7];
                        if (this.valueAssignment[w2.getWireId()] == null) {
                           break;
                        }

                        ++var7;
                     }
                  }

                  i.evaluate(this);
                  i.emit(this);
               }
            }
         }
      } else {
         throw new IllegalArgumentException("[Internal Error] Only positive values that are less than the modulus are allowed for wires.");
      }
   }

   public BigInteger getWireValue(Wire w) {
      return this.valueAssignment[w.getWireId()];
   }

   public BigInteger[] getWiresValues(Wire[] w) {
      BigInteger[] values = new BigInteger[w.length];

      for(int i = 0; i < w.length; ++i) {
         values[i] = this.getWireValue(w[i]);
      }

      return values;
   }

   public void setWireValue(Wire wire, long v) {
      this.setWireValue(wire, new BigInteger(String.valueOf(v)));
   }

   public void setWireValue(Wire[] wires, BigInteger[] v) {
      int i;
      for(i = 0; i < v.length; ++i) {
         this.setWireValue(wires[i], v[i]);
      }

      if (wires.length > v.length) {
         for(i = v.length; i < wires.length; ++i) {
            this.setWireValue(wires[i], BigInteger.ZERO);
         }
      }

   }

   public void evaluate(LinkedHashMap<Instruction, Instruction> evalSequence) {
      Iterator var3 = evalSequence.keySet().iterator();

      while(var3.hasNext()) {
         Instruction e = (Instruction)var3.next();
         boolean skip = false;
         if (e instanceof BasicOp) {
            Wire[] inputs = ((BasicOp)e).getInputs();
            Wire[] var9 = inputs;
            int var8 = inputs.length;

            for(int var7 = 0; var7 < var8; ++var7) {
               Wire w = var9[var7];
               if (this.valueAssignment[w.getWireId()] == null) {
                  if (this.pendingInstructions.get(w.getWireId()) == null) {
                     this.pendingInstructions.put(w.getWireId(), new ArrayList());
                  }

                  ((ArrayList)this.pendingInstructions.get(w.getWireId())).add(e);
                  skip = true;
               }
            }
         }

         if (!skip) {
            e.evaluate(this); //check if pending instructions need to be popped out of the array after this operation
            e.emit(this);
         }
      }

      for(int i = 0; i < this.valueAssignment.length; ++i) {
         if (this.valueAssignment[i] == null) {
            throw new RuntimeException("Wire#" + i + "is without value");
         }
      }

      if (this.pendingInstructions.size() != 0) {
         throw new RuntimeException("Internal Issue: Pending Instruction Sequenece is not empty after terminating");
      }
   }

   public void evaluate() {
      this.evaluate(this.circuitGenerator.__getEvaluationQueue());
   }

   public void writeInputFile(String arg) {
      try {
         LinkedHashMap<Instruction, Instruction> evalSequence = this.circuitGenerator.__getEvaluationQueue();
         PrintWriter printWriter = new PrintWriter(Config.outputFilesPath + (Config.outputFilesPath.isEmpty() ? "" : File.separator) + this.circuitGenerator.__getName() + "_" + arg + ".in");
         Iterator var5 = evalSequence.keySet().iterator();

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
            } while(((WireLabelInstruction)e).getType() != WireLabelInstruction.LabelType.input && ((WireLabelInstruction)e).getType() != WireLabelInstruction.LabelType.nizkinput);

            int id = ((WireLabelInstruction)e).getWire().getWireId();
            printWriter.println(id + " " + this.valueAssignment[id].toString(16));
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }
   public void onlyInput(String arg) {
      try {
         LinkedHashMap<Instruction, Instruction> evalSequence = this.circuitGenerator.__getEvaluationQueue();
         PrintWriter printWriter = new PrintWriter(Config.outputFilesPath + (Config.outputFilesPath.isEmpty() ? "" : File.separator) + this.circuitGenerator.__getName() + "_" + arg + ".in");
         Iterator var5 = evalSequence.keySet().iterator();

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
            printWriter.println(id + " " + this.valueAssignment[id].toString(16));
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }
   }

   public static void eval(String circuitFilePath, String inFilePath) throws Exception {
      Scanner circuitScanner = new Scanner(new BufferedInputStream(new FileInputStream(circuitFilePath)));
      Scanner inFileScanner = new Scanner(new File(inFilePath));
      int totalWires = Integer.parseInt(circuitScanner.nextLine().replace("total ", ""));
      BigInteger[] assignment = new BigInteger[totalWires];
      ArrayList<Integer> wiresToReport = new ArrayList();
      HashSet<Integer> ignoreWires = new HashSet();

      String line;
      while(inFileScanner.hasNextInt()) {
         int wireNumber = inFileScanner.nextInt();
         line = inFileScanner.next();
         assignment[wireNumber] = new BigInteger(line, 16);
         wiresToReport.add(wireNumber);
      }

      BigInteger prime = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");
      circuitScanner.nextLine();

      while(circuitScanner.hasNext()) {
         line = circuitScanner.nextLine();
         if (line.contains("#")) {
            line = line.substring(0, line.indexOf("#"));
            line = line.trim();
         }

         if (!line.startsWith("input") && !line.startsWith("nizkinput")) {
            if (line.startsWith("output ")) {
               line = line.replace("output ", "");
               System.out.println(Integer.parseInt(line) + "::" + assignment[Integer.parseInt(line)].toString(16));
               wiresToReport.add(Integer.parseInt(line));
            } else {
               int in;
               if (line.startsWith("DEBUG ")) {
                  line = line.replace("DEBUG ", "");
                  Scanner scanner = new Scanner(line);
                  in = Integer.parseInt(scanner.next());
                  System.out.println(in + "::" + assignment[in].toString(16) + " >> " + scanner.nextLine());
                  scanner.close();
               } else {
                  ArrayList<Integer> ins = getInputs(line);
                  Iterator var12 = ins.iterator();

                  while(var12.hasNext()) {
                     in = (Integer)var12.next();
                     if (assignment[in] == null) {
                        System.err.println("Undefined value for a used wire, at line " + line);
                     }
                  }

                  ArrayList<Integer> outs = getOutputs(line);
                  int i;
                  Iterator var14;
                  BigInteger sum;
                  if (line.startsWith("mul ")) {
                     sum = BigInteger.ONE;

                     for(var14 = ins.iterator(); var14.hasNext(); sum = sum.multiply(assignment[i])) {
                        i = (Integer)var14.next();
                     }

                     wiresToReport.add((Integer)outs.get(0));
                     assignment[(Integer)outs.get(0)] = sum.mod(prime);
                  } else if (line.startsWith("add ")) {
                     sum = BigInteger.ZERO;

                     for(var14 = ins.iterator(); var14.hasNext(); sum = sum.add(assignment[i])) {
                        i = (Integer)var14.next();
                     }

                     assignment[(Integer)outs.get(0)] = sum.mod(prime);
                  } else if (line.startsWith("xor ")) {
                     sum = assignment[(Integer)ins.get(0)].equals(assignment[(Integer)ins.get(1)]) ? BigInteger.ZERO : BigInteger.ONE;
                     assignment[(Integer)outs.get(0)] = sum;
                     wiresToReport.add((Integer)outs.get(0));
                  } else if (line.startsWith("zerop ")) {
                     ignoreWires.add((Integer)outs.get(0));
                     if (assignment[(Integer)ins.get(0)].signum() == 0) {
                        assignment[(Integer)outs.get(1)] = BigInteger.ZERO;
                     } else {
                        assignment[(Integer)outs.get(1)] = BigInteger.ONE;
                     }

                     wiresToReport.add((Integer)outs.get(1));
                  } else if (line.startsWith("split ")) {
                     if (outs.size() < assignment[(Integer)ins.get(0)].bitLength()) {
                        System.err.println("Error in Split");
                        System.out.println(assignment[(Integer)ins.get(0)].toString(16));
                        System.out.println(line);
                     }
                     int j;
                     for(j=0; j < outs.size(); ++j) {
                        assignment[(Integer)outs.get(j)] = assignment[(Integer)ins.get(0)].testBit(j) ? BigInteger.ONE : BigInteger.ZERO;
                        wiresToReport.add((Integer)outs.get(j));
                     }
                  } else if (!line.startsWith("pack ")) {
                     String constantStr;
                     BigInteger constant;
                     if (line.startsWith("const-mul-neg-")) {
                        constantStr = line.substring("const-mul-neg-".length(), line.indexOf(" "));
                        constant = prime.subtract(new BigInteger(constantStr, 16));
                        assignment[(Integer)outs.get(0)] = assignment[(Integer)ins.get(0)].multiply(constant).mod(prime);
                     } else if (line.startsWith("const-mul-")) {
                        constantStr = line.substring("const-mul-".length(), line.indexOf(" "));
                        constant = new BigInteger(constantStr, 16);
                        assignment[(Integer)outs.get(0)] = assignment[(Integer)ins.get(0)].multiply(constant).mod(prime);
                     } else {
                        System.err.println("Unknown Circuit Statement");
                     }
                  } else {
                     sum = BigInteger.ZERO;

                     for(i = 0; i < ins.size(); ++i) {
                        sum = sum.add(assignment[(Integer)ins.get(i)].multiply((new BigInteger("2")).pow(i)));
                     }

                     wiresToReport.add((Integer)outs.get(0));
                     assignment[(Integer)outs.get(0)] = sum;
                  }
               }
            }
         }
      }

      for(int i = 0; i < totalWires; ++i) {
         if (assignment[i] == null && !ignoreWires.contains(i)) {
            System.out.println("Wire " + i + " is Null");
         }
      }

      circuitScanner.close();
      inFileScanner.close();
      PrintWriter printWriter = new PrintWriter(inFilePath + ".full.2");
      Iterator var21 = wiresToReport.iterator();

      while(var21.hasNext()) {
         int id = (Integer)var21.next();
         printWriter.println(id + " " + assignment[id].toString(16));
      }

      printWriter.close();
   }

   private static ArrayList<Integer> getOutputs(String line) {
      Scanner scanner = new Scanner(line.substring(line.lastIndexOf("<") + 1, line.lastIndexOf(">")));
      ArrayList<Integer> outs = new ArrayList();

      while(scanner.hasNextInt()) {
         int v = scanner.nextInt();
         outs.add(v);
      }

      scanner.close();
      return outs;
   }

   private static ArrayList<Integer> getInputs(String line) {
      Scanner scanner = new Scanner(line.substring(line.indexOf("<") + 1, line.indexOf(">")));
      ArrayList<Integer> ins = new ArrayList();

      while(scanner.hasNextInt()) {
         ins.add(scanner.nextInt());
      }

      scanner.close();
      return ins;
   }

   public BigInteger[] getAssignment() {
      return this.valueAssignment;
   }

   public void setWireValue(PackedValue packedWire, BigInteger rnd, int bitWidth, int bitwidth_per_chunk) {
      Wire[] array = packedWire.getArray();
      if (bitWidth <= UnsignedInteger.BITWIDTH_LIMIT_SHORT && array.length == 1) {
         this.setWireValue(array[0], rnd);
      } else {
         BigInteger[] chunks = Util.split(rnd, array.length, bitwidth_per_chunk);

         for(int i = 0; i < array.length; ++i) {
            this.setWireValue(array[i], chunks[i]);
         }
      }

   }

   public BigInteger getWireValue(PackedValue packedWire, int bitwidth_per_chunk) {
      return Util.combine(this.valueAssignment, packedWire.getArray(), bitwidth_per_chunk);
   }

   public HashMap<String, int[]> getPermutations() {
      return this.permutations;
   }

   public void setPermutations(HashMap<String, int[]> permutations) {
      this.permutations = permutations;
   }
}
