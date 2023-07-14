package backend.operations.primitive;

import backend.config.Config;
import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;

public class AssertBasicOp extends BasicOp {
   public AssertBasicOp(Wire w1, Wire w2, Wire output, String... desc) {
      super(new Wire[]{w1, w2}, new Wire[]{output}, desc);
   }

   protected void compute(BigInteger[] assignment) {
      BigInteger leftSide = assignment[this.inputs[0].getWireId()].multiply(assignment[this.inputs[1].getWireId()]).mod(Config.getFiniteFieldModulus());
      BigInteger rightSide = assignment[this.outputs[0].getWireId()];
      boolean check = leftSide.equals(rightSide);
      if (!check) {
         System.err.println("Error - Assertion Failed " + this);
         System.out.println(assignment[this.inputs[0].getWireId()] + "*" + assignment[this.inputs[1].getWireId()] + "!=" + assignment[this.outputs[0].getWireId()]);
         throw new RuntimeException("Error During Circuit Evaluation");
      }
   }

   protected void checkOutputs(BigInteger[] assignment) {
   }

   public String getOpcode() {
      return "assert";
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof AssertBasicOp)) {
         return false;
      } else {
         AssertBasicOp op = (AssertBasicOp)obj;
         boolean check1 = this.inputs[0].equals(op.inputs[0]) && this.inputs[1].equals(op.inputs[1]);
         boolean check2 = this.inputs[1].equals(op.inputs[0]) && this.inputs[0].equals(op.inputs[1]);
         return (check1 || check2) && this.outputs[0].equals(op.outputs[0]);
      }
   }

   public int getNumMulGates() {
      return 1;
   }

   public Instruction copy(Wire[] wireArray) {
      Wire[] ws = new Wire[this.inputs.length];

      for(int i = 0; i < ws.length; ++i) {
         ws[i] = wireArray[this.inputs[i].getWireId()];
      }

      Wire out = wireArray[this.outputs[0].getWireId()];
      return new AssertBasicOp(ws[0], ws[1], out, new String[]{this.desc});
   }
}
