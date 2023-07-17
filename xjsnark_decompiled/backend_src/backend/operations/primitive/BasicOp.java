package backend.operations.primitive;

import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import java.math.BigInteger;
import util.Util;

public abstract class BasicOp implements Instruction {
   protected Wire[] inputs;
   protected Wire[] outputs;
   protected String desc;
   protected static boolean cachingDisabledForLinearOps = false;

   public static void setDisableCachingForLinearOps(boolean arg) {
      cachingDisabledForLinearOps = arg;
   }

   public BasicOp(Wire[] inputs, Wire[] outputs, String... desc) {
      this.inputs = inputs;
      this.outputs = outputs;
      if (desc.length > 0) {
         this.desc = desc[0];
      } else {
         this.desc = "";
      }

      Wire w;
      int var5;
      int var6;
      Wire[] var7;
      if (CircuitGenerator.__getActiveCircuitGenerator().__getPhase() == 1) {
         var7 = inputs;
         var6 = inputs.length;

         for(var5 = 0; var5 < var6; ++var5) {
            w = var7[var5];
            if (w == null) {
               System.err.println("One of the input wires is null: " + this);
               throw new NullPointerException("A null wire");
            }

            if (w.getWireId() == -1) {
               System.err.println("One of the input wires is not packed: " + this);
               throw new IllegalArgumentException("A wire with a negative id");
            }
         }
      }

      var7 = outputs;
      var6 = outputs.length;

      for(var5 = 0; var5 < var6; ++var5) {
         w = var7[var5];
         if (w == null && CircuitGenerator.__getActiveCircuitGenerator().__getPhase() == 1) {
            System.err.println("One of the output wires is null" + this);
            throw new NullPointerException("A null wire");
         }

         if (!(this instanceof AssertBasicOp)) {
            w.setSrcInstruction(this);
         }
      }

   }

   public BasicOp(Wire[] inputs, Wire[] outputs) {
      this(inputs, outputs, "");
   }

   public void evaluate(CircuitEvaluator evaluator) {
      BigInteger[] assignment = evaluator.getAssignment();
      this.checkInputs(assignment);
      this.checkOutputs(assignment);
      this.compute(assignment);
   }

   protected void checkInputs(BigInteger[] assignment) {
      Wire[] var5;
      int var4 = (var5 = this.inputs).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Wire w = var5[var3];
         if (assignment[w.getWireId()] == null) {
            System.err.println("Error - The inWire " + w + " has not been assigned\n" + this);
            throw new RuntimeException("Error During Evaluation");
         }
      }

   }

   protected abstract void compute(BigInteger[] var1);

   protected void checkOutputs(BigInteger[] assignment) {
      Wire[] var5;
      int var4 = (var5 = this.outputs).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Wire w = var5[var3];
         if (assignment[w.getWireId()] != null) {
            System.err.println("Error - The outWire " + w + " has already been assigned\n" + this);
            throw new RuntimeException("Error During Evaluation");
         }
      }

   }

   public abstract String getOpcode();

   public abstract int getNumMulGates();

   public String toString() {
      return this.getOpcode() + " in " + this.inputs.length + " <" + Util.arrayToString(this.inputs, " ") + "> out " + this.outputs.length + " <" + Util.arrayToString(this.outputs, " ") + ">" + (this.desc.length() > 0 ? " \t\t# " + this.desc : "");
   }

   public Wire[] getInputs() {
      return this.inputs;
   }

   public Wire[] getOutputs() {
      return this.outputs;
   }

   public boolean doneWithinCircuit() {
      return true;
   }

   public int hashCode() {
      int h = this.getOpcode().hashCode();
      Wire[] var5;
      int var4 = (var5 = this.inputs).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         Wire in = var5[var3];
         h += in.hashCode();
      }

      return h;
   }

   public boolean equals(Object obj) {
      return this == obj;
   }

   public Wire[] getUsedWires() {
      return Util.concat(this.inputs, this.outputs);
   }
}
