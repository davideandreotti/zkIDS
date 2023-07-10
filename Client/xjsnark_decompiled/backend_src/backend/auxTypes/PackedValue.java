package backend.auxTypes;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.structure.CircuitGenerator;
import backend.structure.ConstantWire;
import backend.structure.Wire;
import backend.structure.WireArray;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import util.Util;

public class PackedValue {
   Wire[] array;
   int[] currentBitwidth;
   BigInteger[] currentMaxValues;
   WireArray bits;
   CircuitGenerator generator;
   boolean witnessIndicator;
   static boolean disableOverflowChecks = false;

   public PackedValue(CircuitGenerator generator, Wire[] array, BigInteger[] currentMaxValues) {
      this.generator = generator;
      this.array = array;
      this.currentMaxValues = currentMaxValues;
      this.currentBitwidth = new int[currentMaxValues.length];

      for(int i = 0; i < this.currentBitwidth.length; ++i) {
         this.currentBitwidth[i] = currentMaxValues[i].bitLength();
      }

      this.checkConstantWires();
   }

   private void checkConstantWires() {
      if (this.array != null) {
         boolean allConstant = true;

         for(int i = 0; i < this.array.length; ++i) {
            if (this.array[i] instanceof ConstantWire) {
               BigInteger value = ((ConstantWire)this.array[i]).getConstant();
               this.currentBitwidth[i] = value.bitLength();
               this.currentMaxValues[i] = value;
            } else {
               allConstant = false;
            }
         }
      }

   }

   public PackedValue(Wire w, int currentBitwidth) {
      this.array = new Wire[]{w};
      this.currentBitwidth = new int[]{currentBitwidth};
      this.currentMaxValues = new BigInteger[]{Util.computeMaxValue(currentBitwidth)};
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      if (currentBitwidth == 1) {
         this.bits = new WireArray(this.array);
      }

      this.checkConstantWires();
   }

   public PackedValue(WireArray bits, int bitwidthPerChunk) {
      if (bitwidthPerChunk >= bits.size()) {
         this.array = new Wire[]{bits.packAsBits(bits.size())};
         this.currentMaxValues = new BigInteger[]{Util.computeMaxValue(bits.size())};
      } else {
         BigInteger maxChunkVal = Util.computeMaxValue(bitwidthPerChunk);
         BigInteger maxLastChunkVal = maxChunkVal;
         int size = bits.size();
         if (size % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
            bits = bits.adjustLength(size + (UnsignedInteger.BITWIDTH_PER_CHUNK - size % UnsignedInteger.BITWIDTH_PER_CHUNK));
            maxLastChunkVal = Util.computeMaxValue(size % UnsignedInteger.BITWIDTH_PER_CHUNK);
         }

         this.array = new Wire[bits.size() / bitwidthPerChunk];
         this.currentMaxValues = new BigInteger[this.array.length];

         for(int i = 0; i < this.array.length; ++i) {
            this.array[i] = (new WireArray((Wire[])Arrays.copyOfRange(bits.asArray(), i * bitwidthPerChunk, (i + 1) * bitwidthPerChunk))).packAsBits();
            if (i == this.array.length - 1) {
               this.currentMaxValues[i] = maxLastChunkVal;
            } else {
               this.currentMaxValues[i] = maxChunkVal;
            }
         }
      }

      this.bits = bits;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.currentBitwidth = new int[this.array.length];

      for(int i = 0; i < this.array.length; ++i) {
         this.currentBitwidth[i] = this.currentMaxValues[i].bitLength();
      }

      this.checkConstantWires();
   }

   public PackedValue(Wire w, BigInteger currentMaxValue) {
      this.array = new Wire[]{w};
      this.currentMaxValues = new BigInteger[]{currentMaxValue};
      this.currentBitwidth = new int[]{currentMaxValue.bitLength()};
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.checkConstantWires();
   }

   public PackedValue(Wire[] w, int[] currentBitwidth) {
      this.array = w;
      this.currentBitwidth = currentBitwidth;
      this.currentMaxValues = new BigInteger[w.length];

      for(int i = 0; i < w.length; ++i) {
         this.currentMaxValues[i] = Util.computeMaxValue(currentBitwidth[i]);
      }

      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.checkConstantWires();
   }

   public PackedValue(BigInteger[] chunks) {
      this.currentMaxValues = chunks;
      this.currentBitwidth = new int[chunks.length];

      for(int i = 0; i < chunks.length; ++i) {
         this.currentBitwidth[i] = this.currentMaxValues[i].bitLength();
      }

      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.array = this.generator.__createConstantWireArray(chunks);
      this.checkConstantWires();
   }

   public PackedValue(Wire[] w, BigInteger[] currentMaxValues) {
      this.array = w;
      this.currentMaxValues = currentMaxValues;
      this.currentBitwidth = new int[w.length];

      for(int i = 0; i < w.length; ++i) {
         this.currentBitwidth[i] = currentMaxValues[i].bitLength();
      }

      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.checkConstantWires();
   }

   public boolean addOverflowCheck(PackedValue o) {
      int length = Math.min(this.array.length, o.array.length);
      boolean overflow = false;

      for(int i = 0; i < length; ++i) {
         BigInteger max1 = i < this.array.length ? this.currentMaxValues[i] : BigInteger.ZERO;
         BigInteger max2 = i < o.array.length ? o.currentMaxValues[i] : BigInteger.ZERO;
         if (max1.add(max2).compareTo(Config.getFiniteFieldModulus()) >= 0) {
            overflow = true;
            break;
         }
      }

      return overflow;
   }

   public boolean mulOverflowCheck(PackedValue o) {
      int length = this.array.length + o.array.length - 1;
      BigInteger[] newMaxValues = new BigInteger[length];
      Arrays.fill(newMaxValues, BigInteger.ZERO);

      int j;
      for(int i = 0; i < this.array.length; ++i) {
         for(j = 0; j < o.array.length; ++j) {
            newMaxValues[i + j] = newMaxValues[i + j].add(this.currentMaxValues[i].multiply(o.currentMaxValues[j]));
         }
      }

      BigInteger[] var7 = newMaxValues;
      int var6 = newMaxValues.length;

      for(j = 0; j < var6; ++j) {
         BigInteger b = var7[j];
         if (b.compareTo(Config.getFiniteFieldModulus()) >= 0) {
            return true;
         }
      }

      return false;
   }

   public boolean mulAddOverflowCheck(PackedValue p1, PackedValue p2) {
      int length = this.array.length + p1.array.length - 1;
      BigInteger[] newMaxValues = new BigInteger[length];
      Arrays.fill(newMaxValues, BigInteger.ZERO);

      int i;
      int j;
      for(i = 0; i < this.array.length; ++i) {
         for(j = 0; j < p1.array.length; ++j) {
            newMaxValues[i + j] = newMaxValues[i + j].add(this.currentMaxValues[i].multiply(p1.currentMaxValues[j]));
         }
      }

      for(i = 0; i < length; ++i) {
         BigInteger b = newMaxValues[i];
         if (i < p2.array.length) {
            b = b.add(p2.currentMaxValues[i]);
         }

         if (b.compareTo(Config.getFiniteFieldModulus()) >= 0) {
            return true;
         }
      }

      BigInteger[] var8 = newMaxValues;
      int var7 = newMaxValues.length;

      for(j = 0; j < var7; ++j) {
         BigInteger b = var8[j];
         if (b.compareTo(Config.getFiniteFieldModulus()) >= 0) {
            return true;
         }
      }

      return false;
   }

   public PackedValue add(PackedValue o) {
      int length = Math.max(this.array.length, o.array.length);
      Wire[] w1 = (new WireArray(this.array)).adjustLength(length).asArray();
      Wire[] w2 = (new WireArray(o.array)).adjustLength(length).asArray();
      Wire[] result = new Wire[length];
      BigInteger[] newMaxValues = new BigInteger[length];

      int i;
      for(i = 0; i < length; ++i) {
         result[i] = w1[i].add(w2[i]);
         BigInteger max1 = i < this.array.length ? this.currentMaxValues[i] : BigInteger.ZERO;
         BigInteger max2 = i < o.array.length ? o.currentMaxValues[i] : BigInteger.ZERO;
         newMaxValues[i] = max1.add(max2);
         if (newMaxValues[i].compareTo(Config.getFiniteFieldModulus()) >= 0 && !disableOverflowChecks) {
            System.err.println("An unhandled case of possible overflow was detected in addition. (Sanity check failed)");
            throw new RuntimeException();
         }
      }

      for(i = 0; i < newMaxValues.length; ++i) {
         if (newMaxValues[i].compareTo(Config.getFiniteFieldModulus()) >= 0) {
            newMaxValues[i] = Config.getFiniteFieldModulus().subtract(BigInteger.ONE);
         }
      }

      return new PackedValue(result, newMaxValues);
   }

   public PackedValue mul(PackedValue o) {
      int length = this.array.length + o.array.length - 1;
      final Wire[] result;
      int i;
      if (o.array.length != 1 && this.array.length != 1 && !this.isConstant() && !o.isConstant()) {
         result = this.generator.__createProverWitnessWireArray(length);
         final Wire[] array1 = this.array;
         final Wire[] array2 = o.array;
         this.generator.__specifyProverWitnessComputation(new Instruction() {
            public void evaluate(CircuitEvaluator evaluator) {
               BigInteger[] a = evaluator.getWiresValues(array1);
               BigInteger[] b = evaluator.getWiresValues(array2);
               BigInteger[] resultVals = PackedValue.this.multiplyPolys(a, b);
               evaluator.setWireValue(result, resultVals);
            }
         });
         Wire zeroWire = this.generator.__getZeroWire();

         for(int k = 0; k < length; ++k) {
            BigInteger constant = new BigInteger(String.valueOf(k + 1));
            BigInteger coeff = BigInteger.ONE;
            Wire[] vector1 = new Wire[this.array.length];
            Wire[] vector2 = new Wire[o.array.length];
            Wire[] vector3 = new Wire[length];

            for(int i = 0; i < length; ++i) {
               if (i < this.array.length) {
                  vector1[i] = this.array[i].mul(coeff);
               }

               if (i < o.array.length) {
                  vector2[i] = o.array[i].mul(coeff);
               }

               vector3[i] = result[i].mul(coeff);
               coeff = coeff.multiply(constant).mod(Config.getFiniteFieldModulus());
            }

            Wire v1 = (new WireArray(vector1)).sumAllElements();
            Wire v2 = (new WireArray(vector2)).sumAllElements();
            Wire v3 = (new WireArray(vector3)).sumAllElements();
            this.generator.__addAssertion(v1, v2, v3);
         }
      } else {
         result = new Wire[length];
         Arrays.fill(result, this.generator.__getZeroWire());

         for(int i = 0; i < this.array.length; ++i) {
            for(i = 0; i < o.array.length; ++i) {
               if (i == 0 && i == 0) {
                  result[0] = this.array[0].mul(o.array[0]);
               } else {
                  result[i + i] = result[i + i].add(this.array[i].mul(o.array[i]));
               }
            }
         }
      }

      BigInteger[] newMaxValues = new BigInteger[length];
      Arrays.fill(newMaxValues, BigInteger.ZERO);

      for(i = 0; i < this.array.length; ++i) {
         for(int j = 0; j < o.array.length; ++j) {
            newMaxValues[i + j] = newMaxValues[i + j].add(this.currentMaxValues[i].multiply(o.currentMaxValues[j]));
            if (newMaxValues[i + j].compareTo(Config.getFiniteFieldModulus()) >= 0 && !disableOverflowChecks) {
               System.err.println("An unhandled case of possible overflow was detected in multiplication. (Sanity check failed)");
               throw new RuntimeException();
            }
         }
      }

      for(i = 0; i < newMaxValues.length; ++i) {
         if (newMaxValues[i].compareTo(Config.getFiniteFieldModulus()) >= 0) {
            newMaxValues[i] = Config.getFiniteFieldModulus().subtract(BigInteger.ONE);
         }
      }

      return new PackedValue(result, newMaxValues);
   }

   public PackedValue mul2(final PackedValue o) {
      int length = this.array.length + o.array.length - 1;
      final Wire[] result;
      int i;
      int i;
      if (o.array.length != 1 && this.array.length != 1 && !this.isConstant() && !o.isConstant()) {
         result = this.generator.__createProverWitnessWireArray(length);

         for(i = 0; i < result.length; ++i) {
         }

         this.generator.__specifyProverWitnessComputation(new Instruction() {
            public void evaluate(CircuitEvaluator evaluator) {
               BigInteger[] a = evaluator.getWiresValues(PackedValue.this.array);
               BigInteger[] b = evaluator.getWiresValues(o.array);
               BigInteger[] resultVals = PackedValue.this.multiplyPolys(a, b);
               evaluator.setWireValue(result, resultVals);
            }
         });
         Wire zeroWire = this.generator.__getZeroWire();

         for(i = 0; i < length; ++i) {
            BigInteger constant = new BigInteger(String.valueOf(i + 1));
            Wire v1 = zeroWire;
            Wire v2 = zeroWire;
            Wire v3 = zeroWire;
            BigInteger coeff = BigInteger.ONE;

            for(int v = 0; v < length; ++v) {
               if (v < this.array.length) {
                  v1 = v1.add(this.array[v].mul(coeff));
               }

               if (v < o.array.length) {
                  v2 = v2.add(o.array[v].mul(coeff));
               }

               v3 = v3.add(result[v].mul(coeff));
               coeff = coeff.multiply(constant).mod(Config.getFiniteFieldModulus());
            }

            this.generator.__addAssertion(v1, v2, v3);
         }
      } else {
         result = new Wire[length];
         Arrays.fill(result, this.generator.__getZeroWire());

         for(i = 0; i < this.array.length; ++i) {
            for(i = 0; i < o.array.length; ++i) {
               result[i + i] = result[i + i].add(this.array[i].mul(o.array[i]));
            }
         }
      }

      BigInteger[] newMaxValues = new BigInteger[length];
      Arrays.fill(newMaxValues, BigInteger.ZERO);

      for(i = 0; i < this.array.length; ++i) {
         for(int j = 0; j < o.array.length; ++j) {
            newMaxValues[i + j] = newMaxValues[i + j].add(this.currentMaxValues[i].multiply(o.currentMaxValues[j]));
            if (newMaxValues[i + j].compareTo(Config.getFiniteFieldModulus()) >= 0 && !disableOverflowChecks) {
               System.err.println("An unhandled case of possible overflow was detected. (Sanity check failed)");
               throw new RuntimeException();
            }
         }
      }

      for(i = 0; i < newMaxValues.length; ++i) {
         if (newMaxValues[i].compareTo(Config.getFiniteFieldModulus()) >= 0) {
            newMaxValues[i] = Config.getFiniteFieldModulus().subtract(BigInteger.ONE);
         }
      }

      return new PackedValue(result, newMaxValues);
   }

   private boolean isConstant() {
      boolean constant = true;
      if (this.array != null) {
         for(int i = 0; i < this.array.length; ++i) {
            constant &= this.array[i] instanceof ConstantWire;
         }
      }

      return constant;
   }

   public int getSize() {
      return this.array.length;
   }

   public PackedValue align(int totalNumChunks, int bitwidthPerChunk) {
      Wire[] newArray = (Wire[])Arrays.copyOfRange(this.array, 0, totalNumChunks);

      for(int i = 0; i < newArray.length; ++i) {
         if (newArray[i] == null) {
            newArray[i] = this.generator.__getZeroWire();
         }
      }

      BigInteger[] newMaxValues = new BigInteger[totalNumChunks];
      Arrays.fill(newMaxValues, BigInteger.ZERO);
      int[] newBitwidths = new int[totalNumChunks];
      Arrays.fill(newBitwidths, 0);
      System.arraycopy(this.currentMaxValues, 0, newMaxValues, 0, Math.min(totalNumChunks, this.currentMaxValues.length));

      for(int i = 0; i < totalNumChunks; ++i) {
         if (newMaxValues[i].bitLength() > bitwidthPerChunk) {
            Wire[] chunkBits = newArray[i].getBitWires(newMaxValues[i].bitLength()).asArray();
            newArray[i] = (new WireArray((Wire[])Arrays.copyOfRange(chunkBits, 0, bitwidthPerChunk))).packAsBits();
            Wire rem = (new WireArray((Wire[])Arrays.copyOfRange(chunkBits, bitwidthPerChunk, newMaxValues[i].bitLength()))).packAsBits();
            if (i != totalNumChunks - 1) {
               newMaxValues[i + 1] = newMaxValues[i].shiftRight(bitwidthPerChunk).add(newMaxValues[i + 1]);
               newArray[i + 1] = rem.add(newArray[i + 1]);
            }

            newMaxValues[i] = Util.computeMaxValue(bitwidthPerChunk);
            newBitwidths[i] = bitwidthPerChunk;
         }
      }

      return new PackedValue(this.generator, newArray, newMaxValues);
   }

   public WireArray getBits(int totalBitwidth, int bitwidthPerChunk, String... desc) {
      if (this.bits != null) {
         return this.bits.adjustLength(totalBitwidth);
      } else {
         if (totalBitwidth == bitwidthPerChunk) {
            if (this.array.length != 1) {
               return null;
            }

            this.bits = this.array[0].getBitWires(this.currentMaxValues[0].bitLength(), desc);
            this.bits = this.bits.adjustLength(totalBitwidth);
         } else {
            int limit = totalBitwidth;
            Wire[] bitWires;
            if (totalBitwidth != -1) {
               bitWires = new Wire[totalBitwidth];
            } else {
               BigInteger maxVal = this.getMaxVal(bitwidthPerChunk);
               bitWires = new Wire[maxVal.bitLength()];
               limit = maxVal.bitLength();
            }

            Arrays.fill(bitWires, this.generator.__getZeroWire());
            int newLength = (int)Math.ceil((double)this.getMaxVal(bitwidthPerChunk).bitLength() * 1.0 / (double)bitwidthPerChunk);
            if (newLength == 0) {
               ++newLength;
            }

            Wire[] newArray = new Wire[newLength];
            BigInteger[] newMaxValues = new BigInteger[newLength];
            Arrays.fill(newMaxValues, BigInteger.ZERO);
            Arrays.fill(newArray, this.generator.__getZeroWire());
            System.arraycopy(this.currentMaxValues, 0, newMaxValues, 0, this.currentMaxValues.length);
            System.arraycopy(this.array, 0, newArray, 0, this.array.length);
            int idx = 0;

            Wire[] alignedChunkBits;
            for(int chunkIndex = 0; idx < limit && chunkIndex < newLength; idx += alignedChunkBits.length) {
               if (newMaxValues[chunkIndex].bitLength() > bitwidthPerChunk) {
                  Wire[] chunkBits = newArray[chunkIndex].getBitWires(newMaxValues[chunkIndex].bitLength()).asArray();
                  alignedChunkBits = (Wire[])Arrays.copyOfRange(chunkBits, 0, bitwidthPerChunk);
                  Wire rem = (new WireArray((Wire[])Arrays.copyOfRange(chunkBits, bitwidthPerChunk, newMaxValues[chunkIndex].bitLength()))).packAsBits();
                  if (chunkIndex != newArray.length - 1) {
                     newMaxValues[chunkIndex + 1] = newMaxValues[chunkIndex].shiftRight(bitwidthPerChunk).add(newMaxValues[chunkIndex + 1]);
                     newArray[chunkIndex + 1] = rem.add(newArray[chunkIndex + 1]);
                  }
               } else {
                  alignedChunkBits = newArray[chunkIndex].getBitWires(bitwidthPerChunk).asArray();
               }

               System.arraycopy(alignedChunkBits, 0, bitWires, idx, Math.min(alignedChunkBits.length, limit - idx));
               ++chunkIndex;
            }

            this.bits = new WireArray(bitWires);
         }

         return this.bits;
      }
   }

   public BigInteger getMaxVal(int bitwidth) {
      return Util.group(this.currentMaxValues, bitwidth);
   }

   private BigInteger[] multiplyPolys(BigInteger[] aiVals, BigInteger[] biVals) {
      BigInteger[] solution = new BigInteger[aiVals.length + biVals.length - 1];
      Arrays.fill(solution, BigInteger.ZERO);

      for(int i = 0; i < aiVals.length; ++i) {
         for(int j = 0; j < biVals.length; ++j) {
            solution[i + j] = solution[i + j].add(aiVals[i].multiply(biVals[j])).mod(Config.getFiniteFieldModulus());
         }
      }

      return solution;
   }

   public PackedValue muxBit(PackedValue other, Wire w) {
      int length = Math.max(this.array.length, other.array.length);
      Wire[] newArray = new Wire[length];
      BigInteger[] newMaxValues = new BigInteger[length];

      for(int i = 0; i < length; ++i) {
         BigInteger b1 = i < this.array.length ? this.currentMaxValues[i] : BigInteger.ZERO;
         BigInteger b2 = i < other.array.length ? other.currentMaxValues[i] : BigInteger.ZERO;
         newMaxValues[i] = b1.compareTo(b2) == 1 ? b1 : b2;
         Wire w1 = i < this.array.length ? this.array[i] : this.generator.__getZeroWire();
         Wire w2 = i < other.array.length ? other.array[i] : this.generator.__getZeroWire();
         newArray[i] = w1.add(w.mul(w2.sub(w1)));
         if (newArray[i] instanceof ConstantWire) {
            newMaxValues[i] = ((ConstantWire)newArray[i]).getConstant();
         }
      }

      return new PackedValue(newArray, newMaxValues);
   }

   public PackedValue mulWire(Wire wire) {
      Wire[] newArray = new Wire[this.array.length];

      for(int i = 0; i < this.array.length; ++i) {
         newArray[i] = this.array[i].mul(wire);
      }

      return new PackedValue(newArray, this.currentBitwidth);
   }

   public Wire[] getArray() {
      return this.array;
   }

   public int[] getCurrentBitwidth() {
      return this.currentBitwidth;
   }

   public BigInteger[] getCurrentMaxValues() {
      return this.currentMaxValues;
   }

   public WireArray getBits() {
      return this.bits;
   }

   public BigInteger getConstant(int bitwidth_per_chunk) {
      BigInteger[] constants = new BigInteger[this.array.length];

      for(int i = 0; i < this.array.length; ++i) {
         if (!(this.array[i] instanceof ConstantWire)) {
            return null;
         }

         constants[i] = ((ConstantWire)this.array[i]).getConstant();
      }

      return Util.group(constants, bitwidth_per_chunk);
   }

   public PackedValue addsub(BigInteger[] a, PackedValue o) {
      int length = Math.max(this.array.length, Math.max(a.length, o.array.length));
      Wire[] w1 = (new WireArray(this.array)).adjustLength(length).asArray();
      Wire[] w2 = (new WireArray(o.array)).adjustLength(length).asArray();
      Wire[] result = new Wire[length];
      BigInteger[] newMaxValues = new BigInteger[length];

      int i;
      for(i = 0; i < length; ++i) {
         result[i] = w1[i].sub(w2[i]);
         if (i < a.length) {
            result[i] = result[i].add(a[i]);
         }

         BigInteger max1 = i < this.array.length ? this.currentMaxValues[i] : BigInteger.ZERO;
         BigInteger max2 = i < a.length ? a[i] : BigInteger.ZERO;
         newMaxValues[i] = max1.add(max2);
         if (newMaxValues[i].compareTo(Config.getFiniteFieldModulus()) >= 0 && !disableOverflowChecks) {
            System.err.println("An unhandled case of possible overflow was detected in subtraction. (Sanity check failed)");
            throw new RuntimeException();
         }
      }

      for(i = 0; i < newMaxValues.length; ++i) {
         if (newMaxValues[i].compareTo(Config.getFiniteFieldModulus()) >= 0) {
            newMaxValues[i] = Config.getFiniteFieldModulus().subtract(BigInteger.ONE);
         }
      }

      return new PackedValue(result, newMaxValues);
   }

   public boolean addSubOverflowCheck(BigInteger[] o) {
      int length = Math.min(this.array.length, o.length);
      boolean overflow = false;

      for(int i = 0; i < length; ++i) {
         BigInteger max1 = i < this.array.length ? this.currentMaxValues[i] : BigInteger.ZERO;
         BigInteger max2 = i < o.length ? o[i] : BigInteger.ZERO;
         if (max1.add(max2).compareTo(Config.getFiniteFieldModulus()) >= 0) {
            overflow = true;
            break;
         }
      }

      return overflow;
   }

   public void forceBitwidth() {
      for(int i = 0; i < this.array.length; ++i) {
         this.array[i].restrictBitLength(this.currentBitwidth[i]);
      }

   }

   public void forceEquality(PackedValue a) {
      WireArray bits1 = a.getBits(a.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
      WireArray bits2 = this.getBits(this.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
      PackedValue v1 = new PackedValue(bits1, UnsignedInteger.BITWIDTH_PER_CHUNK);
      PackedValue v2 = new PackedValue(bits2, UnsignedInteger.BITWIDTH_PER_CHUNK);

      for(int i = 0; i < v1.array.length; ++i) {
         this.generator.__addEqualityAssertion(v1.array[i], v2.array[i]);
      }

   }

   public void forceEquality2(PackedValue p) {
      PackedValue operand1 = this;
      PackedValue operand2 = p;
      if (this.checkEqualityAssertionOverflow(p)) {
         operand1 = this.align((int)Math.ceil((double)this.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK), UnsignedInteger.BITWIDTH_PER_CHUNK);
         operand2 = p.align((int)Math.ceil((double)p.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK), UnsignedInteger.BITWIDTH_PER_CHUNK);
      }

      if (operand1.checkEqualityAssertionOverflow(operand2)) {
         throw new RuntimeException("Unhandled overflow possibility detected in equality verification (Sanity Check Failed).");
      } else {
         final ArrayList<Wire> group1 = new ArrayList();
         ArrayList<BigInteger> group1_bound = new ArrayList();
         final ArrayList<Wire> group2 = new ArrayList();
         ArrayList<BigInteger> group2_bound = new ArrayList();
         final ArrayList<Integer> steps = new ArrayList();
         Wire[] a1 = operand1.array;
         Wire[] a2 = operand2.array;
         int limit = Math.max(a1.length, a2.length);
         BigInteger[] bounds1 = operand1.currentMaxValues;
         BigInteger[] bounds2 = operand2.currentMaxValues;
         if (operand2.array.length != limit) {
            a2 = (new WireArray(a2)).adjustLength(limit).asArray();
            bounds2 = new BigInteger[limit];
            Arrays.fill(bounds2, BigInteger.ZERO);
            System.arraycopy(operand2.currentMaxValues, 0, bounds2, 0, operand2.currentMaxValues.length);
         }

         if (operand1.array.length != limit) {
            a1 = (new WireArray(a1)).adjustLength(limit).asArray();
            bounds1 = new BigInteger[limit];
            Arrays.fill(bounds1, BigInteger.ZERO);
            System.arraycopy(operand1.currentMaxValues, 0, bounds1, 0, operand1.currentMaxValues.length);
         }

         Config.debugVerbose = true;
         if (a1.length == a2.length && a1.length == 1) {
            this.generator.__addEqualityAssertion(a1[0], a2[0], "Equality assertion of long elements | case 1");
         } else if (operand1.isAligned() && operand2.isAligned() && a1.length != 1 && a2.length != 1) {
            for(int i = 0; i < limit; ++i) {
               this.generator.__addEqualityAssertion(a1[i], a2[i], "Equality assertion of long elements | case 2 | index " + i);
            }

         } else {
            if (operand2.array.length != limit) {
               a2 = (new WireArray(a2)).adjustLength(limit).asArray();
               bounds2 = new BigInteger[limit];
               Arrays.fill(bounds2, BigInteger.ZERO);
               System.arraycopy(operand2.currentMaxValues, 0, bounds2, 0, operand2.currentMaxValues.length);
            }

            if (operand1.array.length != limit) {
               a1 = (new WireArray(a1)).adjustLength(limit).asArray();
               bounds1 = new BigInteger[limit];
               Arrays.fill(bounds1, BigInteger.ZERO);
               System.arraycopy(operand1.currentMaxValues, 0, bounds1, 0, operand1.currentMaxValues.length);
            }

            BigInteger shift = (new BigInteger("2")).pow(UnsignedInteger.BITWIDTH_PER_CHUNK);

            int newChunkSize;
            BigInteger auxConstant;
            for(int i = 0; i < limit; i += newChunkSize) {
               newChunkSize = 1;
               Wire w1 = a1[i];
               Wire w2 = a2[i];
               auxConstant = bounds1[i];

               BigInteger b2;
               for(b2 = bounds2[i]; i + newChunkSize <= limit - 1; ++newChunkSize) {
                  BigInteger delta = shift.pow(newChunkSize);
                  if (auxConstant.add(bounds1[i + newChunkSize].multiply(delta)).bitLength() >= Config.getNumBitsFiniteFieldModulus() - 2 || b2.add(bounds2[i + newChunkSize].multiply(delta)).bitLength() >= Config.getNumBitsFiniteFieldModulus() - 2) {
                     break;
                  }

                  w1 = w1.add(a1[i + newChunkSize].mul(delta));
                  w2 = w2.add(a2[i + newChunkSize].mul(delta));
                  auxConstant = auxConstant.add(bounds1[i + newChunkSize].multiply(delta));
                  b2 = b2.add(bounds2[i + newChunkSize].multiply(delta));
               }

               group1.add(w1);
               group1_bound.add(auxConstant);
               group2.add(w2);
               group2_bound.add(b2);
               steps.add(newChunkSize);
            }

            if (group1.size() == 1) {
               this.generator.__addEqualityAssertion((Wire)group1.get(0), (Wire)group2.get(0), "Equality Assertion | Case 3 | Group Index 0");
            } else {
               newChunkSize = group1.size();
               final Wire[] carries = this.generator.__createProverWitnessWireArray(newChunkSize - 1);
               final BigInteger[] auxConstantChunks = new BigInteger[newChunkSize];
               auxConstant = BigInteger.ZERO;
               int accumStep = 0;
               int[] carriesBitBounds = new int[carries.length];

               for(int j = 0; j < auxConstantChunks.length - 1; ++j) {
                  auxConstantChunks[j] = (new BigInteger("2")).pow(((BigInteger)group2_bound.get(j)).bitLength());
                  auxConstant = auxConstant.add(auxConstantChunks[j].multiply(shift.pow(accumStep)));
                  accumStep += (Integer)steps.get(j);
                  carriesBitBounds[j] = Math.max(auxConstantChunks[j].bitLength(), ((BigInteger)group1_bound.get(j)).bitLength()) - (Integer)steps.get(j) * UnsignedInteger.BITWIDTH_PER_CHUNK + 1;
               }

               auxConstantChunks[auxConstantChunks.length - 1] = BigInteger.ZERO;
               final BigInteger[] alignedCoeffs = new BigInteger[newChunkSize];
               Arrays.fill(alignedCoeffs, BigInteger.ZERO);
               BigInteger[] smallerAlignedCoeffs = Util.split(auxConstant, UnsignedInteger.BITWIDTH_PER_CHUNK);
               int idx = 0;

               int j;
               int j;
               label115:
               for(j = 0; j < newChunkSize; ++j) {
                  for(j = 0; j < (Integer)steps.get(j); ++j) {
                     alignedCoeffs[j] = alignedCoeffs[j].add(smallerAlignedCoeffs[idx].multiply(shift.pow(j)));
                     ++idx;
                     if (idx == smallerAlignedCoeffs.length) {
                        break label115;
                     }
                  }
               }

               if (idx != smallerAlignedCoeffs.length) {
                  if (idx != smallerAlignedCoeffs.length - 1) {
                     throw new RuntimeException("Case not expected. Pleas report.");
                  }

                  alignedCoeffs[newChunkSize - 1] = alignedCoeffs[newChunkSize - 1].add(smallerAlignedCoeffs[idx].multiply(shift.pow((Integer)steps.get(newChunkSize - 1) + 0)));
               }

               this.generator.__specifyProverWitnessComputation(new Instruction() {
                  public void evaluate(CircuitEvaluator evaluator) {
                     BigInteger prevCarry = BigInteger.ZERO;

                     for(int i = 0; i < carries.length; ++i) {
                        BigInteger a = evaluator.getWireValue((Wire)group1.get(i));
                        BigInteger b = evaluator.getWireValue((Wire)group2.get(i));
                        BigInteger carryValue = auxConstantChunks[i].add(a).subtract(b).subtract(alignedCoeffs[i]).add(prevCarry);
                        carryValue = carryValue.shiftRight((Integer)steps.get(i) * UnsignedInteger.BITWIDTH_PER_CHUNK);
                        evaluator.setWireValue(carries[i], carryValue);
                        prevCarry = carryValue;
                     }

                  }
               });

               for(j = 0; j < carries.length; ++j) {
                  carries[j].restrictBitLength(carriesBitBounds[j]);
               }

               Wire prevCarry = this.generator.__getZeroWire();

               for(j = 0; j < carries.length + 1; ++j) {
                  Wire auxConstantChunkWire = this.generator.__createConstantWire(auxConstantChunks[j]);
                  Wire alignedCoeffWire = this.generator.__createConstantWire(alignedCoeffs[j]);
                  Wire currentCarry = j == carries.length ? this.generator.__getZeroWire() : carries[j];
                  this.generator.__addEqualityAssertion(auxConstantChunkWire.add(((Wire)group1.get(j)).sub((Wire)group2.get(j))).add(prevCarry), alignedCoeffWire.add(currentCarry.mul(shift.pow((Integer)steps.get(j)))), "Equality Assertion | Case 3 | Group Index " + j);
                  prevCarry = currentCarry;
               }

            }
         }
      }
   }

   private boolean checkEqualityAssertionOverflow(PackedValue p) {
      ArrayList<Wire> group1 = new ArrayList();
      ArrayList<BigInteger> group1_bound = new ArrayList();
      ArrayList<Wire> group2 = new ArrayList();
      ArrayList<BigInteger> group2_bound = new ArrayList();
      ArrayList<Integer> steps = new ArrayList();
      Wire[] a1 = this.array;
      Wire[] a2 = p.array;
      int limit = Math.max(a1.length, a2.length);
      BigInteger[] bounds1 = this.currentMaxValues;
      BigInteger[] bounds2 = p.currentMaxValues;
      if (p.array.length != limit) {
         a2 = (new WireArray(a2)).adjustLength(limit).asArray();
         bounds2 = new BigInteger[limit];
         Arrays.fill(bounds2, BigInteger.ZERO);
         System.arraycopy(p.currentMaxValues, 0, bounds2, 0, p.currentMaxValues.length);
      }

      if (this.array.length != limit) {
         a1 = (new WireArray(a1)).adjustLength(limit).asArray();
         bounds1 = new BigInteger[limit];
         Arrays.fill(bounds1, BigInteger.ZERO);
         System.arraycopy(this.currentMaxValues, 0, bounds1, 0, this.currentMaxValues.length);
      }

      if (a1.length == a2.length && a1.length == 1) {
         return false;
      } else if (this.isAligned() && p.isAligned() && a1.length != 1 && a2.length != 1) {
         return false;
      } else {
         BigInteger shift = (new BigInteger("2")).pow(UnsignedInteger.BITWIDTH_PER_CHUNK);

         int newChunkSize;
         for(int i = 0; i < limit; i += newChunkSize) {
            newChunkSize = 1;
            Wire w1 = a1[i];
            Wire w2 = a2[i];
            BigInteger b1 = bounds1[i];

            BigInteger b2;
            for(b2 = bounds2[i]; i + newChunkSize <= limit - 1; ++newChunkSize) {
               BigInteger delta = shift.pow(newChunkSize);
               if (b1.add(bounds1[i + newChunkSize].multiply(delta)).bitLength() >= Config.getNumBitsFiniteFieldModulus() - 2 || b2.add(bounds2[i + newChunkSize].multiply(delta)).bitLength() >= Config.getNumBitsFiniteFieldModulus() - 2) {
                  break;
               }

               w1 = w1.add(a1[i + newChunkSize].mul(delta));
               w2 = w2.add(a2[i + newChunkSize].mul(delta));
               b1 = b1.add(bounds1[i + newChunkSize].multiply(delta));
               b2 = b2.add(bounds2[i + newChunkSize].multiply(delta));
            }

            group1.add(w1);
            group1_bound.add(b1);
            group2.add(w2);
            group2_bound.add(b2);
            steps.add(newChunkSize);
         }

         if (group1.size() == 1) {
            return false;
         } else {
            newChunkSize = group1.size();
            BigInteger[] auxConstantChunks = new BigInteger[newChunkSize];
            BigInteger auxConstant = BigInteger.ZERO;
            int accumStep = 0;
            int[] carriesBitBounds = new int[newChunkSize - 1];

            for(int j = 0; j < auxConstantChunks.length - 1; ++j) {
               auxConstantChunks[j] = (new BigInteger("2")).pow(((BigInteger)group2_bound.get(j)).bitLength());
               auxConstant = auxConstant.add(auxConstantChunks[j].multiply(shift.pow(accumStep)));
               accumStep += (Integer)steps.get(j);
               carriesBitBounds[j] = Math.max(auxConstantChunks[j].bitLength(), ((BigInteger)group1_bound.get(j)).bitLength()) - (Integer)steps.get(j) * UnsignedInteger.BITWIDTH_PER_CHUNK + 1;
            }

            auxConstantChunks[auxConstantChunks.length - 1] = BigInteger.ZERO;
            BigInteger[] alignedCoeffs = new BigInteger[newChunkSize];
            Arrays.fill(alignedCoeffs, BigInteger.ZERO);
            BigInteger[] smallerAlignedCoeffs = Util.split(auxConstant, UnsignedInteger.BITWIDTH_PER_CHUNK);
            int idx = 0;

            int j;
            label86:
            for(int j = 0; j < newChunkSize; ++j) {
               for(j = 0; j < (Integer)steps.get(j); ++j) {
                  alignedCoeffs[j] = alignedCoeffs[j].add(smallerAlignedCoeffs[idx].multiply(shift.pow(j)));
                  ++idx;
                  if (idx == smallerAlignedCoeffs.length) {
                     break label86;
                  }
               }
            }

            if (idx != smallerAlignedCoeffs.length) {
               if (idx != smallerAlignedCoeffs.length - 1) {
                  throw new RuntimeException("Case not expected. Investigate why!");
               }

               alignedCoeffs[newChunkSize - 1] = alignedCoeffs[newChunkSize - 1].add(smallerAlignedCoeffs[idx].multiply(shift.pow((Integer)steps.get(newChunkSize - 1) + 0)));
            }

            BigInteger prevBound = BigInteger.ZERO;

            for(j = 0; j < carriesBitBounds.length + 1; ++j) {
               if (auxConstantChunks[j].add((BigInteger)group1_bound.get(j)).add(prevBound).compareTo(Config.getFiniteFieldModulus()) >= 0) {
                  return true;
               }

               if (j != carriesBitBounds.length) {
                  prevBound = Util.computeMaxValue(carriesBitBounds[j]);
               }
            }

            return false;
         }
      }
   }

   public boolean isAligned() {
      if (this.array.length <= 1) {
         return true;
      } else {
         boolean check = true;

         for(int i = 0; i < this.array.length; ++i) {
            check &= this.currentBitwidth[i] <= UnsignedInteger.BITWIDTH_PER_CHUNK;
         }

         return check;
      }
   }

   public void assertLessThan(final PackedValue other) {
      if (this.isAligned() && other.isAligned()) {
         Wire[] a1 = this.getArray();
         Wire[] a2 = other.getArray();
         int length = Math.max(a1.length, a2.length);
         final Wire[] paddedA1 = Util.padWireArray(a1, length);
         final Wire[] paddedA2 = Util.padWireArray(a2, length);
         final Wire[] helperBits = this.generator.__createProverWitnessWireArray(other.getArray().length);
         this.generator.__specifyProverWitnessComputation(new Instruction() {
            public void evaluate(CircuitEvaluator evaluator) {
               Wire[] otherArray = other.getArray();
               Wire[] array = PackedValue.this.getArray();
               boolean found = false;

               for(int i = otherArray.length - 1; i >= 0; --i) {
                  BigInteger v1 = evaluator.getWireValue(paddedA1[i]);
                  BigInteger v2 = evaluator.getWireValue(paddedA2[i]);
                  boolean check = v2.compareTo(v1) > 0 && !found;
                  evaluator.setWireValue(helperBits[i], check ? BigInteger.ONE : BigInteger.ZERO);
                  if (check) {
                     found = true;
                  }
               }

            }
         });
         Wire[] var11 = helperBits;
         int i = helperBits.length;

         Wire chunk1;
         for(int var9 = 0; var9 < i; ++var9) {
            chunk1 = var11[var9];
            this.generator.__addBinaryAssertion(chunk1);
         }

         this.generator.__addOneAssertion((new WireArray(helperBits)).sumAllElements());
         chunk1 = this.generator.__getZeroWire();
         Wire chunk2 = this.generator.__getZeroWire();

         for(i = 0; i < helperBits.length; ++i) {
            chunk1 = chunk1.add(paddedA1[i].mul(helperBits[i]));
            chunk2 = chunk2.add(paddedA2[i].mul(helperBits[i]));
         }

         this.generator.__addOneAssertion(chunk1.isLessThan(chunk2, UnsignedInteger.BITWIDTH_PER_CHUNK));
         Wire[] helperBits2 = new Wire[helperBits.length];
         helperBits2[0] = this.generator.__getZeroWire();

         for(int i = 1; i < helperBits.length; ++i) {
            helperBits2[i] = helperBits2[i - 1].add(helperBits[i - 1]);
            this.generator.__addZeroAssertion(helperBits2[i].mul(paddedA1[i].sub(paddedA2[i])));
         }

      } else {
         throw new IllegalArgumentException("input chunks are not aligned");
      }
   }

   public Bit isLessThan(PackedValue packedWire, int bitwidth) {
      return this.compare(packedWire, bitwidth, "<");
   }

   public Bit isLessThanOrEqual(PackedValue packedWire, int bitwidth) {
      return this.compare(packedWire, bitwidth, "<=");
   }

   public Bit isGreaterThan(PackedValue packedWire, int bitwidth) {
      return this.compare(packedWire, bitwidth, ">");
   }

   public Bit isGreaterThanOrEqual(PackedValue packedWire, int bitwidth) {
      return this.compare(packedWire, bitwidth, ">=");
   }

   public Bit compare(PackedValue packedWire, int bitwidth, String op) {
      final int length;
      if (packedWire.array.length == 1 && this.array.length == 1) {
         BigInteger max1 = this.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK);
         BigInteger max2 = packedWire.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK);
         int b1 = max1.bitLength();
         length = max2.bitLength();
         int maxB = Math.max(b1, length);
         if (maxB + 2 >= Config.getNumBitsFiniteFieldModulus()) {
            System.err.println("Unhandled overflow possibility in comparison of integers. (Sanity Check Failed)");
            throw new RuntimeException();
         } else {
            Wire comparisonResult;
            if (op.equals("<=")) {
               comparisonResult = this.array[0].isLessThanOrEqual(packedWire.array[0], maxB);
            } else if (op.equals("<")) {
               comparisonResult = this.array[0].isLessThan(packedWire.array[0], maxB);
            } else if (op.equals(">=")) {
               comparisonResult = this.array[0].isGreaterThanOrEqual(packedWire.array[0], maxB);
            } else {
               if (!op.equals(">")) {
                  throw new IllegalArgumentException("Unknown Opcode");
               }

               comparisonResult = this.array[0].isGreaterThan(packedWire.array[0], maxB);
            }

            return new Bit(comparisonResult);
         }
      } else {
         int unitBitwidth = UnsignedInteger.BITWIDTH_PER_CHUNK;
         if (packedWire.array.length == 1) {
            unitBitwidth = packedWire.currentMaxValues[0].bitLength();
         } else if (this.array.length == 1) {
            unitBitwidth = this.currentMaxValues[0].bitLength();
         }

         if (unitBitwidth == 0) {
            unitBitwidth = 1;
         }

         int step = (int)Math.ceil((double)unitBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         unitBitwidth = step * UnsignedInteger.BITWIDTH_PER_CHUNK;
         if (unitBitwidth + 2 >= Config.getNumBitsFiniteFieldModulus()) {
            System.err.println("Unhandled overflow possibility in comparison of long integers. (Sanity Check Failed)");
            throw new RuntimeException();
         } else {
            Wire[] a1 = this.combine(unitBitwidth);
            Wire[] a2 = packedWire.combine(unitBitwidth);
            length = Math.max(a1.length, a2.length);
            final Wire[] paddedA1 = Util.padWireArray(a1, length);
            final Wire[] paddedA2 = Util.padWireArray(a2, length);
            final Wire[] helperBits = this.generator.__createProverWitnessWireArray(length);
            this.generator.__specifyProverWitnessComputation(new Instruction() {
               public void evaluate(CircuitEvaluator evaluator) {
                  boolean found = false;

                  for(int i = length - 1; i >= 0; --i) {
                     BigInteger v1 = evaluator.getWireValue(paddedA1[i]);
                     BigInteger v2 = evaluator.getWireValue(paddedA2[i]);
                     boolean check = v2.compareTo(v1) != 0 && !found;
                     evaluator.setWireValue(helperBits[i], check ? BigInteger.ONE : BigInteger.ZERO);
                     if (check) {
                        found = true;
                     }
                  }

               }
            });
            Wire[] var15 = helperBits;
            int var14 = helperBits.length;

            Wire sumBits;
            for(int var13 = 0; var13 < var14; ++var13) {
               sumBits = var15[var13];
               this.generator.__addBinaryAssertion(sumBits);
            }

            sumBits = (new WireArray(helperBits)).sumAllElements();
            this.generator.__addBinaryAssertion(sumBits);
            Wire chunk1 = this.generator.__getZeroWire();
            Wire chunk2 = this.generator.__getZeroWire();

            for(int i = 0; i < helperBits.length; ++i) {
               chunk1 = chunk1.add(paddedA1[i].mul(helperBits[i]));
               chunk2 = chunk2.add(paddedA2[i].mul(helperBits[i]));
            }

            this.generator.__addEqualityAssertion(chunk1.sub(chunk2).isEqualTo(0L).invAsBit(), sumBits);
            Wire areEqual = sumBits.invAsBit();
            Wire[] helperBits2 = new Wire[helperBits.length];
            helperBits2[0] = sumBits.invAsBit();

            for(int i = 1; i < helperBits.length; ++i) {
               helperBits2[i] = helperBits2[i - 1].add(helperBits[i - 1]);
               this.generator.__addAssertion(helperBits2[i], paddedA1[i].sub(paddedA2[i]), this.generator.__getZeroWire());
            }

            Wire comparisonResult = null;
            if (op.equals("<=")) {
               comparisonResult = chunk1.isLessThan(chunk2, unitBitwidth);
               comparisonResult = comparisonResult.add(areEqual);
            } else if (op.equals("<")) {
               comparisonResult = chunk1.isLessThan(chunk2, unitBitwidth);
            } else if (op.equals(">=")) {
               comparisonResult = chunk1.isGreaterThan(chunk2, unitBitwidth);
               comparisonResult = comparisonResult.add(areEqual);
            } else {
               if (!op.equals(">")) {
                  throw new IllegalArgumentException("Unknown Opcode");
               }

               comparisonResult = chunk1.isGreaterThan(chunk2, unitBitwidth);
            }

            return new Bit(comparisonResult);
         }
      }
   }

   public boolean checkComparisonOverflow(PackedValue o) {
      int length = Math.max(this.array.length, o.array.length);
      BigInteger max = BigInteger.ZERO;
      boolean overflow = false;
      if (length == 1) {
         max = this.currentMaxValues[0];
         if (o.currentMaxValues[0].compareTo(this.currentMaxValues[0]) > 0) {
            max = o.currentMaxValues[0];
         }

         if (max.bitLength() + 2 >= Config.getNumBitsFiniteFieldModulus()) {
            overflow = true;
         }
      } else {
         int step;
         int tmp;
         if (this.array.length == 1) {
            step = (int)Math.ceil((double)this.currentMaxValues[0].bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
            if (step == 0) {
               step = 1;
            }

            tmp = step * UnsignedInteger.BITWIDTH_PER_CHUNK;
            if (tmp + 2 >= Config.getNumBitsFiniteFieldModulus()) {
               overflow = true;
            }
         } else if (o.array.length == 1) {
            step = (int)Math.ceil((double)o.currentMaxValues[0].bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
            if (step == 0) {
               step = 1;
            }

            tmp = step * UnsignedInteger.BITWIDTH_PER_CHUNK;
            if (tmp + 2 >= Config.getNumBitsFiniteFieldModulus()) {
               overflow = true;
            }
         } else if (!this.isAligned() || !o.isAligned()) {
            System.err.println("Warning: unexpected case found during sanity checks of overflows.");
            throw new RuntimeException("[Sanity Check Failed] unexpected case found during overflow checking.");
         }
      }

      return overflow;
   }

   public Bit isEqualTo(PackedValue packedWire) {
      if (packedWire.array.length == 1 && this.array.length == 1) {
         return new Bit(packedWire.array[0].isEqualTo(this.array[0]));
      } else {
         Wire[] a1 = this.combineAligned();
         Wire[] a2 = packedWire.combineAligned();
         int min = Math.min(a1.length, a2.length);
         Wire result = this.generator.__getOneWire();

         int j;
         for(j = 0; j < min; ++j) {
            result = result.mul(a1[j].isEqualTo(a2[j]));
         }

         if (a1.length > a2.length) {
            for(j = min; j < a1.length; ++j) {
               result = result.mul(a1[j].isEqualTo(0L));
            }
         } else {
            for(j = min; j < a2.length; ++j) {
               result = result.mul(a2[j].isEqualTo(0L));
            }
         }

         return new Bit(result);
      }
   }

   private Wire[] combineAligned() {
      if (!this.isAligned()) {
         throw new IllegalArgumentException("Method should be called on aligned long integers");
      } else {
         int step = Config.getNumBitsFiniteFieldModulus() / UnsignedInteger.BITWIDTH_PER_CHUNK;
         int numChunks = (int)Math.ceil((double)this.array.length * 1.0 / (double)step);
         Wire[] result = new Wire[numChunks];

         for(int i = 0; i < result.length; ++i) {
            Wire w = this.generator.__getZeroWire();

            for(int j = 0; j < step && i * step + j < this.array.length; ++j) {
               w = w.add(this.array[i * step + j].mul(Util.computeBound(UnsignedInteger.BITWIDTH_PER_CHUNK * j)));
            }

            result[i] = w;
         }

         return result;
      }
   }

   private Wire[] combine(int bitwidthLowerBound) {
      if (!this.isAligned()) {
         throw new IllegalArgumentException("Method should be called on aligned integers");
      } else {
         int step = (int)Math.ceil((double)bitwidthLowerBound * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         int numChunks = (int)Math.ceil((double)this.array.length * 1.0 / (double)step);
         Wire[] result = new Wire[numChunks];

         for(int i = 0; i < result.length; ++i) {
            Wire w = this.generator.__getZeroWire();

            for(int j = 0; j < step && i * step + j < this.array.length; ++j) {
               w = w.add(this.array[i * step + j].mul(Util.computeBound(UnsignedInteger.BITWIDTH_PER_CHUNK * j)));
            }

            result[i] = w;
         }

         return result;
      }
   }

   public void forceNonZero() {
      BigInteger c = this.getConstant(UnsignedInteger.BITWIDTH_PER_CHUNK);
      if (c != null && c.equals(BigInteger.ZERO)) {
         throw new RuntimeException("The input constant has a value of zero.");
      } else {
         final Wire sum;
         if (this.array.length == 1) {
            sum = this.generator.__createProverWitnessWire();
            final Wire tmp = this.array[0];
            this.generator.__specifyProverWitnessComputation(new Instruction() {
               public void evaluate(CircuitEvaluator evaluator) {
                  BigInteger v = evaluator.getWireValue(tmp);
                  evaluator.setWireValue(sum, v.modInverse(Config.getFiniteFieldModulus()));
               }
            });
            this.generator.__addAssertion(tmp, sum, this.generator.__getOneWire(), "Non-zero check");
         } else {
            if (!this.isAligned()) {
               throw new RuntimeException("Unexpected case in non-zero checks of long integers");
            }

            sum = this.generator.__getZeroWire();

            for(int i = 0; i < this.array.length; ++i) {
               sum = sum.add(this.array[i].checkNonZero());
            }

            this.generator.__addOneAssertion(sum.checkNonZero());
         }

      }
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof PackedValue) {
         PackedValue v = (PackedValue)o;
         if (v.array.length != this.array.length) {
            return false;
         } else {
            boolean check = true;

            for(int i = 0; i < this.array.length; ++i) {
               if (!v.array[i].equals(this.array[i])) {
                  check = false;
                  break;
               }
            }

            return check;
         }
      } else {
         return false;
      }
   }

   public int getBitwidthOfLargestChunk() {
      BigInteger max = BigInteger.ZERO;
      BigInteger[] var5;
      int var4 = (var5 = this.currentMaxValues).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         BigInteger f = var5[var3];
         if (f.compareTo(max) > 0) {
            max = f;
         }
      }

      return max.bitLength();
   }

   public boolean isWitness() {
      return this.witnessIndicator;
   }

   public void setWitnessIndicator(boolean witnessIndicator) {
      this.witnessIndicator = witnessIndicator;
   }
}
