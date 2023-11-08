package backend.operations.primitive;

import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;

public class NonZeroCheckBasicOp extends BasicOp {
   public NonZeroCheckBasicOp(Wire w, Wire out1, Wire out2, String... desc) {
      super(new Wire[]{w}, new Wire[]{out1, out2}, desc);
   }

   public String getOpcode() {
      return "zerop";
   }

   public void compute(BigInteger[] assignment) {
      if (assignment[this.inputs[0].getWireId()].signum() == 0) {
         assignment[this.outputs[1].getWireId()] = BigInteger.ZERO;
      } else {
         assignment[this.outputs[1].getWireId()] = BigInteger.ONE;
      }

      assignment[this.outputs[0].getWireId()] = BigInteger.ZERO;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof NonZeroCheckBasicOp)) {
         return false;
      } else {
         NonZeroCheckBasicOp op = (NonZeroCheckBasicOp)obj;
         return this.inputs[0].equals(op.inputs[0]);
      }
   }

   public int getNumMulGates() {
      return 2;
   }

   public Instruction copy(Wire[] wireArray) {
      Wire in = wireArray[this.inputs[0].getWireId()];
      Wire out = this.outputs[0].copy();
      wireArray[out.getWireId()] = out;
      Wire out2 = this.outputs[1].copy();
      wireArray[out2.getWireId()] = out2;
      return new NonZeroCheckBasicOp(in, out, out2, new String[]{this.desc});
   }
}
