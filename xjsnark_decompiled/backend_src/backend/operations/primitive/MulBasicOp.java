package backend.operations.primitive;

import backend.config.Config;
import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;

public class MulBasicOp extends BasicOp {
   public MulBasicOp(Wire w1, Wire w2, Wire output, String... desc) {
      super(new Wire[]{w1, w2}, new Wire[]{output}, desc);
   }

   public String getOpcode() {
      return "mul";
   }

   public void compute(BigInteger[] assignment) {
      BigInteger result = assignment[this.inputs[0].getWireId()].multiply(assignment[this.inputs[1].getWireId()]);
      if (result.compareTo(Config.getFiniteFieldModulus()) > 0) {
         result = result.mod(Config.getFiniteFieldModulus());
      }

      assignment[this.outputs[0].getWireId()] = result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof MulBasicOp)) {
         return false;
      } else {
         MulBasicOp op = (MulBasicOp)obj;
         boolean check1 = this.inputs[0].equals(op.inputs[0]) && this.inputs[1].equals(op.inputs[1]);
         boolean check2 = this.inputs[1].equals(op.inputs[0]) && this.inputs[0].equals(op.inputs[1]);
         return check1 || check2;
      }
   }

   public int getNumMulGates() {
      return 1;
   }

   public Instruction copy(Wire[] wireArray) {
      Wire in = wireArray[this.inputs[0].getWireId()];
      Wire in2 = wireArray[this.inputs[1].getWireId()];
      Wire out = this.outputs[0].copy();
      wireArray[out.getWireId()] = out;
      return new MulBasicOp(in, in2, out, new String[]{this.desc});
   }
}
