package backend.operations.primitive;

import backend.config.Config;
import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;
import util.Util;

public class PackBasicOp extends BasicOp {
   public PackBasicOp(Wire[] inBits, Wire out, String... desc) {
      super(inBits, new Wire[]{out}, desc);
   }

   public String getOpcode() {
      return "pack";
   }

   public void checkInputs(BigInteger[] assignment) {
      super.checkInputs(assignment);
      boolean check = true;

      for(int i = 0; i < this.inputs.length; ++i) {
         check &= Util.isBinary(assignment[this.inputs[i].getWireId()]);
      }

      if (!check) {
         System.err.println("Error - Input(s) to Pack are not binary. " + this);
         throw new RuntimeException("Error During Evaluation");
      }
   }

   public void compute(BigInteger[] assignment) {
      BigInteger sum = BigInteger.ZERO;

      for(int i = 0; i < this.inputs.length; ++i) {
         sum = sum.add(assignment[this.inputs[i].getWireId()].multiply((new BigInteger("2")).pow(i)));
      }

      assignment[this.outputs[0].getWireId()] = sum.mod(Config.getFiniteFieldModulus());
   }

   public boolean equals(Object obj) {
      if (cachingDisabledForLinearOps) {
         return false;
      } else if (this == obj) {
         return true;
      } else if (!(obj instanceof PackBasicOp)) {
         return false;
      } else {
         PackBasicOp op = (PackBasicOp)obj;
         if (op.inputs.length != this.inputs.length) {
            return false;
         } else {
            boolean check = true;

            for(int i = 0; i < this.inputs.length; ++i) {
               check = check && this.inputs[i].equals(op.inputs[i]);
            }

            return check;
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
      return new PackBasicOp(ws, out, new String[]{this.desc});
   }
}
