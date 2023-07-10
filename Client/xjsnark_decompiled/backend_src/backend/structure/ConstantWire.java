package backend.structure;

import backend.config.Config;
import backend.eval.Instruction;
import backend.operations.primitive.ConstMulBasicOp;
import java.math.BigInteger;

public class ConstantWire extends Wire {
   protected BigInteger constant;

   public ConstantWire(int wireId, BigInteger value) {
      super(wireId);
      this.constant = value.mod(Config.getFiniteFieldModulus());
   }

   public BigInteger getConstant() {
      return this.constant;
   }

   public boolean isBinary() {
      return this.constant.equals(BigInteger.ONE) || this.constant.equals(BigInteger.ZERO);
   }

   public Wire mul(Wire w, String... desc) {
      return w instanceof ConstantWire ? this.generator.__createConstantWire(this.constant.multiply(((ConstantWire)w).constant), desc) : w.mul(this.constant, desc);
   }

   public Wire mul(BigInteger b, String... desc) {
      boolean sign = b.signum() == -1;
      BigInteger newConstant;
      if (this.constant.equals(BigInteger.ONE)) {
         if (b.signum() >= 0 && b.compareTo(Config.getFiniteFieldModulus()) < 0) {
            newConstant = b;
         } else {
            newConstant = b.mod(Config.getFiniteFieldModulus());
         }
      } else {
         newConstant = this.constant.multiply(b).mod(Config.getFiniteFieldModulus());
      }

      Wire out = (Wire)this.generator.__knownConstantWires.get(newConstant);
      if (out == null) {
         ConstantWire out;
         if (!sign) {
            out = new ConstantWire(this.generator.__currentWireId++, newConstant);
         } else {
            out = new ConstantWire(this.generator.__currentWireId++, newConstant.subtract(Config.getFiniteFieldModulus()));
         }

         Instruction op = new ConstMulBasicOp(this, out, b, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            this.generator.__knownConstantWires.put(newConstant, out);
            return out;
         } else {
            --this.generator.__currentWireId;
            this.generator.__knownConstantWires.put(newConstant, cachedOutputs[0]);
            return cachedOutputs[0];
         }
      } else {
         return out;
      }
   }

   public Wire checkNonZero(Wire w, String... desc) {
      return this.constant.equals(BigInteger.ZERO) ? this.generator.__zeroWire : this.generator.__oneWire;
   }

   public Wire invAsBit(String... desc) {
      if (!this.isBinary()) {
         throw new RuntimeException("Trying to invert a non-binary constant!");
      } else {
         return this.constant.equals(BigInteger.ZERO) ? this.generator.__oneWire : this.generator.__zeroWire;
      }
   }

   public Wire or(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         ConstantWire cw = (ConstantWire)w;
         if (this.isBinary() && cw.isBinary()) {
            return this.constant.equals(BigInteger.ZERO) && cw.getConstant().equals(BigInteger.ZERO) ? this.generator.__zeroWire : this.generator.__oneWire;
         } else {
            throw new RuntimeException("Trying to OR two non-binary constants");
         }
      } else {
         return this.constant.equals(BigInteger.ONE) ? this.generator.__oneWire : w;
      }
   }

   public Wire xor(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         ConstantWire cw = (ConstantWire)w;
         if (this.isBinary() && cw.isBinary()) {
            return this.constant.equals(cw.getConstant()) ? this.generator.__zeroWire : this.generator.__oneWire;
         } else {
            throw new RuntimeException("Trying to XOR two non-binary constants");
         }
      } else {
         return this.constant.equals(BigInteger.ONE) ? w.invAsBit(desc) : w;
      }
   }

   public WireArray getBitWires(int bitwidth, String... desc) {
      if (this.constant.bitLength() > bitwidth) {
         throw new RuntimeException("Trying to split a constant of " + this.constant.bitLength() + " bits into " + bitwidth + "bits");
      } else {
         Wire[] bits = new ConstantWire[bitwidth];

         for(int i = 0; i < bitwidth; ++i) {
            bits[i] = this.constant.testBit(i) ? this.generator.__oneWire : this.generator.__zeroWire;
         }

         return new WireArray(bits);
      }
   }

   public void restrictBitLength(int bitwidth, String... desc) {
      this.getBitWires(bitwidth, desc);
   }

   protected void pack(String... desc) {
   }

   public Wire copy() {
      ConstantWire newWire = new ConstantWire(this.wireId, this.constant);
      return newWire;
   }
}
