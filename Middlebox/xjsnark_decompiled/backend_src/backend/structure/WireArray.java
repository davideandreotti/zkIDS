package backend.structure;

import backend.eval.Instruction;
import backend.operations.primitive.AddBasicOp;
import backend.operations.primitive.PackBasicOp;
import java.math.BigInteger;
import java.util.Arrays;
import util.Util;

public class WireArray {
   protected Wire[] array;
   protected CircuitGenerator generator;

   public WireArray(int n) {
      this(n, CircuitGenerator.__getActiveCircuitGenerator());
   }

   public WireArray(int n, CircuitGenerator generator) {
      this.array = new Wire[n];
      this.generator = generator;
   }

   public WireArray(Wire[] wireArray) {
      this(wireArray, CircuitGenerator.__getActiveCircuitGenerator());
   }

   public WireArray(Wire[] wireArray, CircuitGenerator generator) {
      this.array = wireArray;
      this.generator = generator;
   }

   public Wire get(int i) {
      return this.array[i];
   }

   public void set(int i, Wire w) {
      this.array[i] = w;
   }

   public int size() {
      return this.array.length;
   }

   public Wire[] asArray() {
      return this.array;
   }

   public WireArray mulWireArray(WireArray v, int desiredLength, String... desc) {
      Wire[] ws1 = this.adjustLength(this.array, desiredLength);
      Wire[] ws2 = this.adjustLength(v.array, desiredLength);
      Wire[] out = new Wire[desiredLength];

      for(int i = 0; i < out.length; ++i) {
         out[i] = ws1[i].mul(ws2[i], desc);
      }

      return new WireArray(out);
   }

   public Wire sumAllElements(String... desc) {
      boolean allConstant = true;
      BigInteger sum = BigInteger.ZERO;
      Wire[] var8;
      int var7 = (var8 = this.array).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         Wire w = var8[var6];
         if (!(w instanceof ConstantWire)) {
            allConstant = false;
            break;
         }

         sum = sum.add(((ConstantWire)w).getConstant());
      }

      if (allConstant) {
         Wire output = this.generator.__createConstantWire(sum, desc);
         return output;
      } else {
         Wire output = new LinearCombinationWire(this.generator.__currentWireId++);
         Instruction op = new AddBasicOp(this.array, output, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return output;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      }
   }

   public WireArray addWireArray(WireArray v, int desiredLength, String... desc) {
      Wire[] ws1 = this.adjustLength(this.array, desiredLength);
      Wire[] ws2 = this.adjustLength(v.array, desiredLength);
      Wire[] out = new Wire[desiredLength];

      for(int i = 0; i < out.length; ++i) {
         out[i] = ws1[i].add(ws2[i], desc);
      }

      return new WireArray(out);
   }

   public WireArray xorWireArray(WireArray v, int desiredLength, String... desc) {
      Wire[] ws1 = this.adjustLength(this.array, desiredLength);
      Wire[] ws2 = this.adjustLength(v.array, desiredLength);
      Wire[] out = new Wire[desiredLength];

      for(int i = 0; i < out.length; ++i) {
         out[i] = ws1[i].xor(ws2[i], desc);
      }

      return new WireArray(out);
   }

   public WireArray xorWireArray(WireArray v, String... desc) {
      if (this.size() != v.size()) {
         throw new IllegalArgumentException();
      } else {
         Wire[] ws1 = this.array;
         Wire[] ws2 = v.array;
         Wire[] out = new Wire[this.size()];

         for(int i = 0; i < out.length; ++i) {
            out[i] = ws1[i].xor(ws2[i], desc);
         }

         return new WireArray(out);
      }
   }

   public WireArray andWireArray(WireArray v, int desiredLength, String... desc) {
      Wire[] ws1 = this.adjustLength(this.array, desiredLength);
      Wire[] ws2 = this.adjustLength(v.array, desiredLength);
      Wire[] out = new Wire[desiredLength];

      for(int i = 0; i < out.length; ++i) {
         out[i] = ws1[i].mul(ws2[i], desc);
      }

      return new WireArray(out);
   }

   public WireArray orWireArray(WireArray v, int desiredLength, String... desc) {
      Wire[] ws1 = this.adjustLength(this.array, desiredLength);
      Wire[] ws2 = this.adjustLength(v.array, desiredLength);
      Wire[] out = new Wire[desiredLength];

      for(int i = 0; i < out.length; ++i) {
         out[i] = ws1[i].or(ws2[i], desc);
      }

      return new WireArray(out);
   }

   public WireArray invAsBits(int desiredBitWidth, String... desc) {
      Wire[] out = new Wire[desiredBitWidth];

      for(int i = 0; i < desiredBitWidth; ++i) {
         if (i < this.array.length) {
            out[i] = this.array[i].invAsBit(desc);
         } else {
            out[i] = this.generator.__oneWire;
         }
      }

      return new WireArray(out);
   }

   private Wire[] adjustLength(Wire[] ws, int desiredLength) {
      if (ws.length == desiredLength) {
         return ws;
      } else {
         Wire[] newWs = new Wire[desiredLength];
         System.arraycopy(ws, 0, newWs, 0, Math.min(ws.length, desiredLength));
         if (ws.length < desiredLength) {
            for(int i = ws.length; i < desiredLength; ++i) {
               newWs[i] = this.generator.__zeroWire;
            }
         }

         return newWs;
      }
   }

   public WireArray adjustLength(int desiredLength) {
      if (this.array.length == desiredLength) {
         return this;
      } else {
         Wire[] newWs = new Wire[desiredLength];
         System.arraycopy(this.array, 0, newWs, 0, Math.min(this.array.length, desiredLength));
         if (this.array.length < desiredLength) {
            for(int i = this.array.length; i < desiredLength; ++i) {
               newWs[i] = this.generator.__zeroWire;
            }
         }

         return new WireArray(newWs);
      }
   }

   public Wire packAsBits(int n, String... desc) {
      return this.packAsBits(0, n, desc);
   }

   public Wire packAsBits(String... desc) {
      return this.packAsBits(this.array.length, desc);
   }

   protected BigInteger checkIfConstantBits(String... desc) {
      boolean allConstant = true;
      BigInteger sum = BigInteger.ZERO;

      for(int i = 0; i < this.array.length; ++i) {
         Wire w = this.array[i];
         if (w instanceof ConstantWire) {
            ConstantWire cw = (ConstantWire)w;
            BigInteger v = cw.constant;
            if (v.equals(BigInteger.ONE)) {
               sum = sum.add(v.shiftLeft(i));
            } else if (!v.equals(BigInteger.ZERO)) {
               System.err.println("Warning, one of the bit wires is constant but not binary : " + Util.getDesc(desc));
            }
         } else {
            allConstant = false;
         }
      }

      if (allConstant) {
         return sum;
      } else {
         return null;
      }
   }

   public Wire packAsBits(int from, int to, String... desc) {
      if (from <= to && to <= this.array.length) {
         Wire[] bits = (Wire[])Arrays.copyOfRange(this.array, from, to);
         boolean allConstant = true;
         BigInteger sum = BigInteger.ZERO;

         for(int i = 0; i < bits.length; ++i) {
            Wire w = bits[i];
            if (w instanceof ConstantWire) {
               ConstantWire cw = (ConstantWire)w;
               BigInteger v = cw.constant;
               if (v.equals(BigInteger.ONE)) {
                  sum = sum.add(v.shiftLeft(i));
               } else if (!v.equals(BigInteger.ZERO)) {
                  throw new RuntimeException("Trying to pack non-binary constant bits : " + Util.getDesc(desc));
               }
            } else {
               allConstant = false;
            }
         }

         if (!allConstant) {
            Wire out = new LinearCombinationWire(this.generator.__currentWireId++);
            out.setBits(new WireArray(bits));
            Instruction op = new PackBasicOp(bits, out, desc);
            Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
            if (cachedOutputs == null) {
               return out;
            } else {
               --this.generator.__currentWireId;
               return cachedOutputs[0];
            }
         } else {
            return this.generator.__createConstantWire(sum, desc);
         }
      } else {
         throw new IllegalArgumentException("Invalid bounds: from > to");
      }
   }

   public WireArray rotateLeft(int numBits, int s, String... desc) {
      Wire[] bits = this.adjustLength(this.array, numBits);
      Wire[] rotatedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i < s) {
            rotatedBits[i] = bits[i + (numBits - s)];
         } else {
            rotatedBits[i] = bits[i - s];
         }
      }

      return new WireArray(rotatedBits);
   }

   public WireArray rotateRight(int numBits, int s, String... desc) {
      Wire[] bits = this.adjustLength(this.array, numBits);
      Wire[] rotatedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i >= numBits - s) {
            rotatedBits[i] = bits[i - (numBits - s)];
         } else {
            rotatedBits[i] = bits[i + s];
         }
      }

      return new WireArray(rotatedBits);
   }

   public WireArray shiftLeft(int numBits, int s, String... desc) {
      Wire[] bits = this.adjustLength(this.array, numBits);
      Wire[] shiftedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i < s) {
            shiftedBits[i] = this.generator.__zeroWire;
         } else {
            shiftedBits[i] = bits[i - s];
         }
      }

      return new WireArray(shiftedBits);
   }

   public WireArray shiftRight(int numBits, int s, String... desc) {
      Wire[] bits = this.adjustLength(this.array, numBits);
      Wire[] shiftedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i >= numBits - s) {
            shiftedBits[i] = this.generator.__zeroWire;
         } else {
            shiftedBits[i] = bits[i + s];
         }
      }

      return new WireArray(shiftedBits);
   }

   public Wire[] packBitsIntoWords(int wordBitwidth, String... desc) {
      int numWords = (int)Math.ceil((double)this.array.length * 1.0 / (double)wordBitwidth);
      Wire[] padded = this.adjustLength(this.array, wordBitwidth * numWords);
      Wire[] result = new Wire[numWords];

      for(int i = 0; i < numWords; ++i) {
         result[i] = (new WireArray((Wire[])Arrays.copyOfRange(padded, i * wordBitwidth, (i + 1) * wordBitwidth))).packAsBits();
      }

      return result;
   }

   public Wire[] packWordsIntoLargerWords(int wordBitwidth, int numWordsPerLargerWord, String... desc) {
      int numLargerWords = (int)Math.ceil((double)this.array.length * 1.0 / (double)numWordsPerLargerWord);
      Wire[] result = new Wire[numLargerWords];
      Arrays.fill(result, this.generator.__zeroWire);

      for(int i = 0; i < this.array.length; ++i) {
         int subIndex = i % numWordsPerLargerWord;
         result[i / numWordsPerLargerWord] = result[i / numWordsPerLargerWord].add(this.array[i].mul((new BigInteger("2")).pow(subIndex * wordBitwidth)));
      }

      return result;
   }

   public WireArray getBits(int bitwidth, String... desc) {
      Wire[] bits = new Wire[bitwidth * this.array.length];
      int idx = 0;

      for(int i = 0; i < this.array.length; ++i) {
         Wire[] tmp = this.array[i].getBitWires(bitwidth, desc).asArray();

         for(int j = 0; j < bitwidth; ++j) {
            bits[idx++] = tmp[j];
         }
      }

      return new WireArray(bits);
   }

   public BigInteger computeTightUpperBoundOfBitWires(int bitwidth) {
      BigInteger m = BigInteger.ZERO;

      for(int i = 0; i < Math.min(this.size(), bitwidth); ++i) {
         Wire w = this.get(i);
         if (!(w instanceof ConstantWire) || !((ConstantWire)w).getConstant().equals(BigInteger.ZERO)) {
            m = m.add(BigInteger.ONE.shiftLeft(i));
         }
      }

      return m;
   }
}
