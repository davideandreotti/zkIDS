package backend.operations.primitive;

import backend.config.Config;
import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;

public class AddBasicOp extends BasicOp {
   public AddBasicOp(Wire[] ws, Wire output, String... desc) {
      super(ws, new Wire[]{output}, desc);
   }

   public String getOpcode() {
      return "add";
   }

   public void compute(BigInteger[] assignment) {
      BigInteger s = BigInteger.ZERO;
      Wire[] var6;
      int var5 = (var6 = this.inputs).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Wire w = var6[var4];
         s = s.add(assignment[w.getWireId()]);
      }

      assignment[this.outputs[0].getWireId()] = s.mod(Config.getFiniteFieldModulus());
   }

   public boolean equals(Object obj) {
      if (cachingDisabledForLinearOps) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (!(obj instanceof AddBasicOp)) {
         return false;
      } else {
         AddBasicOp op = (AddBasicOp)obj;
         if (op.inputs.length != this.inputs.length) {
            return false;
         } else {
            boolean check;
            if (this.inputs.length == 2) {
               check = this.inputs[0].equals(op.inputs[0]) && this.inputs[1].equals(op.inputs[1]);
               boolean check2 = this.inputs[1].equals(op.inputs[0]) && this.inputs[0].equals(op.inputs[1]);
               return check || check2;
            } else {
               check = true;

               for(int i = 0; i < this.inputs.length; ++i) {
                  check = check && this.inputs[i].equals(op.inputs[i]);
               }

               return check;
            }
         }
      }
   }

   public int getNumMulGates() {
      return 0;
   }

   public Instruction copy(Wire[] wireArray) {
      Wire[] ws = new Wire[this.inputs.length];

      for(int i = 0; i < ws.length; ++i) {
         ws[i] = wireArray[this.inputs[i].getWireId()];
      }

      Wire out = this.outputs[0].copy();
      wireArray[out.getWireId()] = out;
      return new AddBasicOp(ws, out, new String[]{this.desc});
   }
}
