package backend.structure;

import backend.eval.Instruction;
import backend.operations.primitive.AddBasicOp;
import backend.operations.primitive.ConstMulBasicOp;
import backend.operations.primitive.MulBasicOp;
import backend.operations.primitive.ORBasicOp;
import backend.operations.primitive.XorBasicOp;
import java.math.BigInteger;

public class BitWire extends Wire {
   public BitWire(int wireId) {
      super(wireId);
   }

   public Wire mul(Wire w, String desc) {
      if (w instanceof ConstantWire) {
         return this.mul(((ConstantWire)w).getConstant(), desc);
      } else if (w instanceof BitWire && this == w) {
         return w;
      } else {
         Object output;
         if (w instanceof BitWire) {
            output = new VariableBitWire(this.generator.__currentWireId++);
         } else {
            output = new VariableWire(this.generator.__currentWireId++);
         }

         Instruction op = new MulBasicOp(this, w, (Wire)output, new String[]{desc});
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return (Wire)output;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      }
   }

   public Wire mul(BigInteger b, String... desc) {
      if (b.equals(BigInteger.ZERO)) {
         return this.generator.__zeroWire;
      } else if (b.equals(BigInteger.ONE)) {
         return this;
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

   public Wire invAsBit(String... desc) {
      Wire neg = this.mul(-1L, desc);
      Wire out = new LinearCombinationBitWire(this.generator.__currentWireId++);
      Instruction op = new AddBasicOp(new Wire[]{this.generator.__oneWire, neg}, out, desc);
      Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
      if (cachedOutputs == null) {
         return out;
      } else {
         --this.generator.__currentWireId;
         return cachedOutputs[0];
      }
   }

   public Wire or(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         return w.or(this, desc);
      } else if (this == w) {
         return w;
      } else if (w instanceof BitWire) {
         Wire out = new VariableBitWire(this.generator.__currentWireId++);
         Instruction op = new ORBasicOp(this, w, out, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return out;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      } else {
         return super.or(w, desc);
      }
   }

   public Wire xor(Wire w, String... desc) {
      if (w instanceof ConstantWire) {
         return w.xor(this, desc);
      } else if (this == w) {
         return this.generator.__zeroWire;
      } else if (w instanceof BitWire) {
         Wire out = new VariableBitWire(this.generator.__currentWireId++);
         Instruction op = new XorBasicOp(this, w, out, desc);
         Wire[] cachedOutputs = this.generator.__addToEvaluationQueue(op);
         if (cachedOutputs == null) {
            return out;
         } else {
            --this.generator.__currentWireId;
            return cachedOutputs[0];
         }
      } else {
         return super.xor(w, desc);
      }
   }

   public WireArray getBits(Wire w, int bitwidth, String... desc) {
      return (new WireArray(new Wire[]{this})).adjustLength(bitwidth);
   }

   public Wire copy() {
      BitWire newWire = new BitWire(this.wireId);
      return newWire;
   }
}
