package backend.operations.primitive;

import backend.config.Config;
import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;

public class ConstMulBasicOp extends BasicOp {
   private BigInteger constInteger;
   private boolean inSign;

   public ConstMulBasicOp(Wire w, Wire out, BigInteger constInteger, String... desc) {
      super(new Wire[]{w}, new Wire[]{out}, desc);
      this.inSign = constInteger.signum() == -1;
      if (!this.inSign) {
         constInteger = constInteger.mod(Config.getFiniteFieldModulus());
         this.constInteger = constInteger;
      } else {
         constInteger = constInteger.negate();
         constInteger = constInteger.mod(Config.getFiniteFieldModulus());
         this.constInteger = Config.getFiniteFieldModulus().subtract(constInteger);
      }

   }

   public String getOpcode() {
      return !this.inSign ? "const-mul-" + this.constInteger.toString(16) : "const-mul-neg-" + Config.getFiniteFieldModulus().subtract(this.constInteger).toString(16);
   }

   public void compute(BigInteger[] assignment) {
      BigInteger result = assignment[this.inputs[0].getWireId()].multiply(this.constInteger);
      if (result.bitLength() >= Config.getNumBitsFiniteFieldModulus()) {
         result = result.mod(Config.getFiniteFieldModulus());
      }

      assignment[this.outputs[0].getWireId()] = result;
   }

   public boolean equals(Object obj) {
      if (cachingDisabledForLinearOps) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (!(obj instanceof ConstMulBasicOp)) {
         return false;
      } else {
         ConstMulBasicOp op = (ConstMulBasicOp)obj;
         return this.inputs[0].equals(op.inputs[0]) && this.constInteger.equals(op.constInteger);
      }
   }

   public int getNumMulGates() {
      return 0;
   }

   public BigInteger getConstInteger() {
      return this.constInteger;
   }

   public Instruction copy(Wire[] wireArray) {
      Wire in = wireArray[this.inputs[0].getWireId()];
      Wire out = this.outputs[0].copy();
      wireArray[out.getWireId()] = out;
      return new ConstMulBasicOp(in, out, this.constInteger, new String[]{this.desc});
   }

   public int hashCode() {
      int h = this.constInteger.hashCode();
      Wire[] var5;
      int var4 = (var5 = this.inputs).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Wire in = var5[var3];
         h += in.hashCode();
      }

      return h;
   }
}
