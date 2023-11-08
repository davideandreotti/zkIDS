package examples.gadgets;

import backend.operations.Gadget;
import backend.structure.Wire;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PinocchioGadget extends Gadget {
   private Wire[] inputWires;
   private Wire[] proverWitnessWires;
   private Wire[] outputWires;

   public PinocchioGadget(Wire[] inputWires, String pathToArithFile, String... desc) {
      super(desc);
      this.inputWires = inputWires;

      try {
         this.buildCircuit(pathToArithFile);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private void buildCircuit(String path) throws FileNotFoundException {
      ArrayList<Wire> proverWitnessWires = new ArrayList();
      ArrayList<Wire> outputWires = new ArrayList();
      Scanner scanner = new Scanner(new File(path));
      if (!scanner.next().equals("total")) {
         scanner.close();
         throw new RuntimeException("Expected total %d in the first line");
      } else {
         int numWires = scanner.nextInt();
         scanner.nextLine();
         Wire[] wireMapping = new Wire[numWires];
         int inputCount = 0;

         while(true) {
            while(true) {
               String line;
               do {
                  if (!scanner.hasNext()) {
                     scanner.close();
                     this.proverWitnessWires = new Wire[proverWitnessWires.size()];
                     proverWitnessWires.toArray(this.proverWitnessWires);
                     this.outputWires = new Wire[outputWires.size()];
                     outputWires.toArray(this.outputWires);
                     return;
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
                  if (wireMapping[in] != null) {
                     this.throwParsingError(scanner, "Wire assigned twice! " + in);
                  }

                  if (inputCount < this.inputWires.length) {
                     wireMapping[in] = this.inputWires[inputCount];
                  } else {
                     wireMapping[in] = this.generator.__getOneWire();
                  }

                  ++inputCount;
               } else if (line.startsWith("output")) {
                  tokens = line.split("\\s+");
                  in = Integer.parseInt(tokens[1]);
                  outputWires.add(wireMapping[in]);
               } else {
                  Wire result;
                  if (line.startsWith("nizk")) {
                     tokens = line.split("\\s+");
                     in = Integer.parseInt(tokens[1]);
                     if (wireMapping[in] != null) {
                        this.throwParsingError(scanner, "Wire assigned twice! " + in);
                     }

                     result = this.generator.__createProverWitnessWire();
                     proverWitnessWires.add(result);
                     wireMapping[in] = result;
                  } else {
                     ArrayList<Integer> ins = this.getInputs(line);
                     Iterator var11 = ins.iterator();

                     while(var11.hasNext()) {
                        in = (Integer)var11.next();
                        if (wireMapping[in] == null) {
                           this.throwParsingError(scanner, "Undefined input wire " + in + " at line " + line);
                        }
                     }

                     ArrayList<Integer> outs = this.getOutputs(line);
                     if (line.startsWith("mul ")) {
                        wireMapping[(Integer)outs.get(0)] = wireMapping[(Integer)ins.get(0)].mul(wireMapping[(Integer)ins.get(1)]);
                     } else {
                        int i;
                        if (!line.startsWith("add ")) {
                           if (line.startsWith("zerop ")) {
                              wireMapping[(Integer)outs.get(1)] = wireMapping[(Integer)ins.get(0)].checkNonZero();
                           } else if (line.startsWith("split ")) {
                              Wire[] bits = wireMapping[(Integer)ins.get(0)].getBitWires(outs.size()).asArray();

                              for(i = 0; i < outs.size(); ++i) {
                                 wireMapping[(Integer)outs.get(i)] = bits[i];
                              }
                           } else {
                              String constantStr;
                              BigInteger constant;
                              if (line.startsWith("const-mul-neg-")) {
                                 constantStr = line.substring("const-mul-neg-".length(), line.indexOf(" "));
                                 constant = new BigInteger(constantStr, 16);
                                 wireMapping[(Integer)outs.get(0)] = wireMapping[(Integer)ins.get(0)].mul(constant.negate());
                              } else if (line.startsWith("const-mul-")) {
                                 constantStr = line.substring("const-mul-".length(), line.indexOf(" "));
                                 constant = new BigInteger(constantStr, 16);
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

   private ArrayList<Integer> getOutputs(String line) {
      Scanner scanner = new Scanner(line.substring(line.lastIndexOf("<") + 1, line.lastIndexOf(">")));
      ArrayList<Integer> outs = new ArrayList();

      while(scanner.hasNextInt()) {
         int v = scanner.nextInt();
         outs.add(v);
      }

      scanner.close();
      return outs;
   }

   private ArrayList<Integer> getInputs(String line) {
      Scanner scanner = new Scanner(line.substring(line.indexOf("<") + 1, line.indexOf(">")));
      ArrayList<Integer> ins = new ArrayList();

      while(scanner.hasNextInt()) {
         ins.add(scanner.nextInt());
      }

      scanner.close();
      return ins;
   }

   public Wire[] getOutputWires() {
      return this.outputWires;
   }

   public Wire[] getProverWitnessWires() {
      return this.proverWitnessWires;
   }

   private void throwParsingError(Scanner s, String m) {
      s.close();
      throw new RuntimeException(m);
   }
}
