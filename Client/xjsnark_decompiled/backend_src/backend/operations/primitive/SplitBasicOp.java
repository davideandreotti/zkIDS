package backend.operations.primitive;

import backend.config.Config;
import backend.eval.Instruction;
import backend.structure.Wire;
import java.math.BigInteger;

public class SplitBasicOp extends BasicOp {
   public SplitBasicOp(Wire w, Wire[] outs, String... desc) {
      super(new Wire[]{w}, outs, desc);
   }

   public String getOpcode() {
      return "split";
   }

   protected void checkInputs(BigInteger[] assignment) {
      super.checkInputs(assignment);
      if (this.outputs.length < assignment[this.inputs[0].getWireId()].bitLength()) {
         System.err.println("Error in Split --- The number of bits does not fit -- Input: " + assignment[this.inputs[0].getWireId()].toString(16) + "\n\t" + this);
         throw new RuntimeException("Error During Evaluation -- " + this);
      }
   }

   protected void compute(BigInteger[] assignment) {
      BigInteger inVal = assignment[this.inputs[0].getWireId()];
      if (inVal.compareTo(Config.getFiniteFieldModulus()) > 0) {
         inVal = inVal.mod(Config.getFiniteFieldModulus());
      }

      for(int i = 0; i < this.outputs.length; ++i) {
         assignment[this.outputs[i].getWireId()] = inVal.testBit(i) ? BigInteger.ONE : BigInteger.ZERO;
      }

   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof SplitBasicOp)) {
         return false;
      } else {
         SplitBasicOp op = (SplitBasicOp)obj;
         return this.inputs[0].equals(op.inputs[0]) && this.outputs.length == op.outputs.length;
      }
   }

   public int getNumMulGates() {
      return this.outputs.length + 1;
   }

   public Instruction copy(Wire[] wireArray) {
      Wire[] ws = new Wire[this.outputs.length];

      for(int i = 0; i < ws.length; ++i) {
         ws[i] = this.outputs[i].copy();
         wireArray[ws[i].getWireId()] = ws[i];
      }

      Wire in = wireArray[this.inputs[0].getWireId()];
      return new SplitBasicOp(in, ws, new String[]{this.desc});
   }
}
