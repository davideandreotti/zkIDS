package backend.structure;

import backend.eval.Instruction;
import backend.operations.primitive.ConstMulBasicOp;
import backend.operations.primitive.MulBasicOp;
import backend.operations.primitive.NonZeroCheckBasicOp;
import backend.operations.primitive.ORBasicOp;
import backend.operations.primitive.PackBasicOp;
import backend.operations.primitive.SplitBasicOp;
import backend.operations.primitive.XorBasicOp;
import java.math.BigInteger;

public class Wire {
   protected int wireId = -1;
   protected CircuitGenerator generator;
   protected Instruction srcInstruction;

   public Wire(int wireId) {
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      if (wireId < -1) {
         throw new IllegalArgumentException("wire id cannot be negative < -1");
      } else {
         this.wireId = wireId;
      }
   }

   public Wire(int wireId, CircuitGenerator generator) {
      this.generator = generator;
      if (wireId < -1) {
         throw new IllegalArgumentException("wire id cannot be negative < -1");
      } else {
         this.wireId = wireId;
      }
   }

   protected Wire(WireArray bits) {
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.setBits(bits);
   }

   public String toString() {
      return String.valueOf(this.wireId);
   }

   public int getWireId() {
      return this.wireId;
   }

   WireArray getBitWires() {
      return null;
   }

   void setBits(WireArray bits) {
      System.out.println("Warning --  you are trying to set bits for either a constant or a bit wire. -- Action Ignored");
      throw new NullPointerException();
   }

   public Wire mul(BigInteger b, String... desc) {
      this.packIfNeeded(desc);
      if (b.equals(BigInteger.ONE)) {
         return this;
      } else if (b.equals(BigInteger.ZERO)) {
         return this.generator.__zeroWire;
      } else {
         Wire out = new LinearCombinationWire(this.generator.__currentWireId++);
         Instruction op = new ConstMulBasicOp(this, out, b, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return out;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      }
   }

   public Wire mul(long l, String... desc) {
      return this.mul(new BigInteger(String.valueOf(l)), desc);
   }

   public Wire mul(long base, int exp, String... desc) {
      BigInteger b = new BigInteger(String.valueOf(base));
      b = b.pow(exp);
      return this.mul(b, desc);
   }

   public Wire mul(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         return this.mul(((ConstantWire)w).getConstant(), desc);
      } else {
         this.packIfNeeded(desc);
         w.packIfNeeded(desc);
         Wire output = new VariableWire(this.generator.__currentWireId++);
         Instruction op = new MulBasicOp(this, w, output, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return output;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      }
   }

   public Wire add(Wire w, String... desc) {
      this.packIfNeeded(desc);
      w.packIfNeeded(desc);
      if (w instanceof ConstantWire && ((ConstantWire)w).getConstant().equals(BigInteger.ZERO)) {
         return this;
      } else {
         return this instanceof ConstantWire && ((ConstantWire)this).getConstant().equals(BigInteger.ZERO) ? w : (new WireArray(new Wire[]{this, w})).sumAllElements(desc);
      }
   }

   public Wire add(long v, String... desc) {
      return this.add(this.generator.__createConstantWire(v, desc), desc);
   }

   public Wire add(BigInteger b, String... desc) {
      return this.add(this.generator.__createConstantWire(b, desc), desc);
   }

   public Wire sub(Wire w, String... desc) {
      w.packIfNeeded(desc);
      Wire neg = w.mul(-1L, desc);
      return this.add(neg, desc);
   }

   public Wire sub(long v, String... desc) {
      return this.sub(this.generator.__createConstantWire(v, desc), desc);
   }

   public Wire sub(BigInteger b, String... desc) {
      return this.sub(this.generator.__createConstantWire(b, desc), desc);
   }

   public Wire checkNonZero(String... desc) {
      this.packIfNeeded(desc);
      Wire out1 = new Wire(this.generator.__currentWireId++);
      Wire out2 = new VariableBitWire(this.generator.__currentWireId++);
      Instruction op = new NonZeroCheckBasicOp(this, out1, out2, desc);
      Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
      if (cachedOutputs == null) {
         return out2;
      } else {
         CircuitGenerator var10000 = this.generator;
         var10000.__currentWireId -= 2;
         return cachedOutputs[1];
      }
   }

   public Wire invAsBit(String... desc) {
      this.packIfNeeded(desc);
      Wire w1 = this.mul(-1L, desc);
      Wire out = this.generator.__oneWire.add(w1, desc);
      return out;
   }

   public Wire or(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         return w.or(this, desc);
      } else if (this == w) {
         return w;
      } else {
         this.packIfNeeded(desc);
         Wire out = new VariableWire(this.generator.__currentWireId++);
         Instruction op = new ORBasicOp(this, w, out, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return out;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      }
   }

   public Wire xor(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         return w.xor(this, desc);
      } else if (this == w) {
         return this.generator.__zeroWire;
      } else {
         this.packIfNeeded(desc);
         Wire out = new VariableWire(this.generator.__currentWireId++);
         Instruction op = new XorBasicOp(this, w, out, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return out;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      }
   }

   public Wire and(Wire w, String... desc) {
      return this.mul(w, desc);
   }

   public WireArray getBitWires(int bitwidth, String... desc) {
      WireArray bitWires = this.getBitWires();
      if (bitWires == null) {
         bitWires = this.forceSplit(bitwidth, desc);
         this.setBits(bitWires);
         return bitWires;
      } else {
         return bitWires.adjustLength(bitwidth);
      }
   }

   protected WireArray forceSplit(int bitwidth, String... desc) {
      Wire[] ws = new VariableBitWire[bitwidth];

      for(int i = 0; i < bitwidth; ++i) {
         ws[i] = new VariableBitWire(this.generator.__currentWireId++);
      }

      Instruction op = new SplitBasicOp(this, ws, desc);
      Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
      if (cachedOutputs == null) {
         WireArray bitWires = new WireArray(ws);
         return bitWires;
      } else {
         CircuitGenerator var10000 = this.generator;
         var10000.__currentWireId -= bitwidth;
         return (new WireArray(cachedOutputs)).adjustLength(bitwidth);
      }
   }

   public void restrictBitLength(int bitWidth, String... desc) {
      WireArray bitWires = this.getBitWires();
      if (bitWires == null) {
         this.getBitWires(bitWidth, desc);
      } else if (bitWires.size() > bitWidth) {
         bitWires = this.forceSplit(bitWidth, desc);
         this.setBits(bitWires);
      }

   }

   public Wire xorBitwise(Wire w, int numBits, String... desc) {
      WireArray bits1 = this.getBitWires(numBits, desc);
      WireArray bits2 = w.getBitWires(numBits, desc);
      WireArray result = bits1.xorWireArray(bits2, numBits, desc);
      BigInteger v = result.checkIfConstantBits(desc);
      return (Wire)(v == null ? new LinearCombinationWire(result) : this.generator.__createConstantWire(v));
   }

   public Wire xorBitwise(long v, int numBits, String... desc) {
      return this.xorBitwise(this.generator.__createConstantWire(v, desc), numBits, desc);
   }

   public Wire xorBitwise(BigInteger b, int numBits, String... desc) {
      return this.xorBitwise(this.generator.__createConstantWire(b, desc), numBits, desc);
   }

   public Wire andBitwise(Wire w, int numBits, String... desc) {
      WireArray bits1 = this.getBitWires(numBits, desc);
      WireArray bits2 = w.getBitWires(numBits, desc);
      WireArray result = bits1.andWireArray(bits2, numBits, desc);
      BigInteger v = result.checkIfConstantBits(desc);
      return (Wire)(v == null ? new LinearCombinationWire(result) : this.generator.__createConstantWire(v));
   }

   public Wire andBitwise(long v, int numBits, String... desc) {
      return this.andBitwise(this.generator.__createConstantWire(v, desc), numBits, desc);
   }

   public Wire andBitwise(BigInteger b, int numBits, String... desc) {
      return this.andBitwise(this.generator.__createConstantWire(b, desc), numBits, desc);
   }

   public Wire orBitwise(Wire w, int numBits, String... desc) {
      WireArray bits1 = this.getBitWires(numBits, desc);
      WireArray bits2 = w.getBitWires(numBits, desc);
      WireArray result = bits1.orWireArray(bits2, numBits, desc);
      BigInteger v = result.checkIfConstantBits(desc);
      return (Wire)(v == null ? new LinearCombinationWire(result) : this.generator.__createConstantWire(v));
   }

   public Wire orBitwise(long v, int numBits, String... desc) {
      return this.orBitwise(this.generator.__createConstantWire(v, desc), numBits, desc);
   }

   public Wire orBitwise(BigInteger b, int numBits, String... desc) {
      return this.orBitwise(this.generator.__createConstantWire(b, desc), numBits, desc);
   }

   public Wire isEqualTo(Wire w, String... desc) {
      this.packIfNeeded(desc);
      w.packIfNeeded(desc);
      Wire s = this.sub(w, desc);
      return s.checkNonZero(desc).invAsBit(desc);
   }

   public Wire isEqualTo(BigInteger b, String... desc) {
      return this.isEqualTo(this.generator.__createConstantWire(b, desc));
   }

   public Wire isEqualTo(long v, String... desc) {
      return this.isEqualTo(this.generator.__createConstantWire(v, desc));
   }

   public Wire isLessThanOrEqual(Wire w, int bitwidth, String... desc) {
      this.packIfNeeded(desc);
      w.packIfNeeded(desc);
      BigInteger p = (new BigInteger("2")).pow(bitwidth);
      Wire pWire = this.generator.__createConstantWire(p, desc);
      Wire sum = pWire.add(w, desc).sub(this, desc);
      WireArray bitWires = sum.getBitWires(bitwidth + 1, desc);
      return bitWires.get(bitwidth);
   }

   public Wire isLessThanOrEqual(long v, int bitwidth, String... desc) {
      return this.isLessThanOrEqual(this.generator.__createConstantWire(v, desc), bitwidth, desc);
   }

   public Wire isLessThanOrEqual(BigInteger b, int bitwidth, String... desc) {
      return this.isLessThanOrEqual(this.generator.__createConstantWire(b, desc), bitwidth, desc);
   }

   public Wire isLessThan(Wire w, int bitwidth, String... desc) {
      this.packIfNeeded(desc);
      w.packIfNeeded(desc);
      BigInteger p = (new BigInteger("2")).pow(bitwidth);
      Wire pWire = this.generator.__createConstantWire(p, desc);
      Wire sum = pWire.add(this, desc).sub(w, desc);
      WireArray bitWires = sum.getBitWires(bitwidth + 1, desc);
      return bitWires.get(bitwidth).invAsBit(desc);
   }

   public Wire isLessThan(long v, int bitwidth, String... desc) {
      return this.isLessThan(this.generator.__createConstantWire(v, desc), bitwidth, desc);
   }

   public Wire isLessThan(BigInteger b, int bitwidth, String... desc) {
      return this.isLessThan(this.generator.__createConstantWire(b, desc), bitwidth, desc);
   }

   public Wire isGreaterThanOrEqual(Wire w, int bitwidth, String... desc) {
      this.packIfNeeded(desc);
      w.packIfNeeded(desc);
      BigInteger p = (new BigInteger("2")).pow(bitwidth);
      Wire pWire = this.generator.__createConstantWire(p, desc);
      Wire sum = pWire.add(this, desc).sub(w, desc);
      WireArray bitWires = sum.getBitWires(bitwidth + 1, desc);
      return bitWires.get(bitwidth);
   }

   public Wire isGreaterThanOrEqual(long v, int bitwidth, String... desc) {
      return this.isGreaterThanOrEqual(this.generator.__createConstantWire(v, desc), bitwidth, desc);
   }

   public Wire isGreaterThanOrEqual(BigInteger b, int bitwidth, String... desc) {
      return this.isGreaterThanOrEqual(this.generator.__createConstantWire(b, desc), bitwidth, desc);
   }

   public Wire isGreaterThan(Wire w, int bitwidth, String... desc) {
      this.packIfNeeded(desc);
      w.packIfNeeded(desc);
      BigInteger p = (new BigInteger("2")).pow(bitwidth);
      Wire pWire = this.generator.__createConstantWire(p, desc);
      Wire sum = pWire.add(w, desc).sub(this, desc);
      WireArray bitWires = sum.getBitWires(bitwidth + 1, desc);
      return bitWires.get(bitwidth).invAsBit(desc);
   }

   public Wire isGreaterThan(long v, int bitwidth, String... desc) {
      return this.isGreaterThan(this.generator.__createConstantWire(v, desc), bitwidth, desc);
   }

   public Wire isGreaterThan(BigInteger b, int bitwidth, String... desc) {
      return this.isGreaterThan(this.generator.__createConstantWire(b, desc), bitwidth, desc);
   }

   public Wire rotateLeft(int numBits, int s, String... desc) {
      WireArray bits = this.getBitWires(numBits, desc);
      Wire[] rotatedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i < s) {
            rotatedBits[i] = bits.get(i + (numBits - s));
         } else {
            rotatedBits[i] = bits.get(i - s);
         }
      }

      WireArray result = new WireArray(rotatedBits);
      BigInteger v = result.checkIfConstantBits(desc);
      if (v == null) {
         return new LinearCombinationWire(result);
      } else {
         return this.generator.__createConstantWire(v);
      }
   }

   public Wire rotateRight(int numBits, int s, String... desc) {
      WireArray bits = this.getBitWires(numBits, desc);
      Wire[] rotatedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i >= numBits - s) {
            rotatedBits[i] = bits.get(i - (numBits - s));
         } else {
            rotatedBits[i] = bits.get(i + s);
         }
      }

      WireArray result = new WireArray(rotatedBits);
      BigInteger v = result.checkIfConstantBits(desc);
      if (v == null) {
         return new LinearCombinationWire(result);
      } else {
         return this.generator.__createConstantWire(v);
      }
   }

   public Wire shiftLeft(int numBits, int s, String... desc) {
      WireArray bits = this.getBitWires(numBits, desc);
      Wire[] shiftedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i < s) {
            shiftedBits[i] = this.generator.__zeroWire;
         } else {
            shiftedBits[i] = bits.get(i - s);
         }
      }

      WireArray result = new WireArray(shiftedBits);
      BigInteger v = result.checkIfConstantBits(desc);
      if (v == null) {
         return new LinearCombinationWire(result);
      } else {
         return this.generator.__createConstantWire(v);
      }
   }

   public Wire shiftRight(int numBits, int s, String... desc) {
      WireArray bits = this.getBitWires(numBits, desc);
      Wire[] shiftedBits = new Wire[numBits];

      for(int i = 0; i < numBits; ++i) {
         if (i >= numBits - s) {
            shiftedBits[i] = this.generator.__zeroWire;
         } else {
            shiftedBits[i] = bits.get(i + s);
         }
      }

      WireArray result = new WireArray(shiftedBits);
      BigInteger v = result.checkIfConstantBits(desc);
      if (v == null) {
         return new LinearCombinationWire(result);
      } else {
         return this.generator.__createConstantWire(v);
      }
   }

   public Wire invBits(int bitwidth, String... desc) {
      Wire[] bits = this.getBitWires(bitwidth, desc).asArray();
      Wire[] resultBits = new Wire[bits.length];

      for(int i = 0; i < resultBits.length; ++i) {
         resultBits[i] = bits[i].invAsBit(desc);
      }

      WireArray result = new WireArray(resultBits);
      BigInteger v = result.checkIfConstantBits(desc);
      return (Wire)(v == null ? new LinearCombinationWire(result) : this.generator.__createConstantWire(v));
   }

   public Wire trimBits(int currentNumOfBits, int desiredNumofBits, String... desc) {
      WireArray bitWires = this.getBitWires(currentNumOfBits, desc);
      WireArray result = bitWires.adjustLength(desiredNumofBits);
      BigInteger v = result.checkIfConstantBits(desc);
      return (Wire)(v == null ? new LinearCombinationWire(result) : this.generator.__createConstantWire(v));
   }

   protected void packIfNeeded(String... desc) {
      if (this.wireId == -1) {
         this.pack();
      }

   }

   protected void pack(String... desc) {
      if (this.wireId == -1 && this.generator.__getPhase() > 0) {
         WireArray bits = this.getBitWires();
         if (bits == null && this.generator.__getPhase() > 0) {
            throw new RuntimeException("A Pack operation is tried on a wire that has no bits.");
         }

         this.wireId = this.generator.__currentWireId++;
         Instruction op = new PackBasicOp(bits.array, this, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs != null) {
            --this.generator.__currentWireId;
            this.wireId = cachedOutputs[0].getWireId();
         }
      } else {
         this.wireId = this.generator.__currentWireId++;
      }

   }

   public int hashCode() {
      return this.wireId;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Wire)) {
         return false;
      } else {
         Wire w = (Wire)obj;
         return w.wireId == this.wireId && w.generator == this.generator;
      }
   }

   public Instruction getSrcInstruction() {
      return this.srcInstruction;
   }

   public void setSrcInstruction(Instruction srcInstruction) {
      this.srcInstruction = srcInstruction;
   }

   public void setWireId(int wireId) {
      this.wireId = wireId;
   }

   public Wire copy() {
      return new Wire(this.wireId);
   }
}
