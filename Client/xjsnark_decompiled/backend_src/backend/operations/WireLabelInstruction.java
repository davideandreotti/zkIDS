package backend.operations;

import backend.auxTypes.IAuxType;
import backend.auxTypes.PackedValue;
import backend.auxTypes.UnsignedInteger;
import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.structure.Wire;

public class WireLabelInstruction implements Instruction {
   private LabelType type;
   private Wire w = null;
   private IAuxType t;
   private PackedValue p;
   private String desc;

   public WireLabelInstruction(LabelType type, Wire w, String... desc) {
      this.type = type;
      this.w = w;
      if (desc.length > 0) {
         this.desc = desc[0];
      } else {
         this.desc = "";
      }

   }

   public WireLabelInstruction(LabelType type, IAuxType t, String... desc) {
      this.type = type;
      this.t = t;
      if (desc.length > 0) {
         this.desc = desc[0];
      } else {
         this.desc = "";
      }

   }

   public WireLabelInstruction(LabelType type, PackedValue p, String... desc) {
      this.type = type;
      this.p = p;
      if (desc.length > 0) {
         this.desc = desc[0];
      } else {
         this.desc = "";
      }

   }

   public Wire getWire() {
      return this.w;
   }

   public String toString() {
      return this.type + " " + this.w + (this.desc.length() == 0 ? "" : "\t\t\t # " + this.desc);
   }

   public void evaluate(CircuitEvaluator evaluator) {
   }

   public void emit(CircuitEvaluator evaluator) {
      if (this.type == WireLabelInstruction.LabelType.input && Config.inputVerbose || this.type == WireLabelInstruction.LabelType.output && Config.outputVerbose || this.type == WireLabelInstruction.LabelType.debug && Config.debugVerbose) {
         if (this.w == null) {
            if (this.t != null) {
               System.out.println("\t[" + this.type + "] Value of Object  " + (this.desc.length() > 0 ? " (" + this.desc + ")" : "") + " :: " + this.t.getValueFromEvaluator(evaluator).toString(Config.hexOutputEnabled ? 16 : 10));
            } else {
               System.out.println("\t[" + this.type + "] Value of PackedValue  " + (this.desc.length() > 0 ? " (" + this.desc + ")" : "") + " :: " + evaluator.getWireValue(this.p, UnsignedInteger.BITWIDTH_PER_CHUNK).toString(Config.hexOutputEnabled ? 16 : 10));
            }
         } else {
            if (evaluator.getWireValue(this.w) == null && (this.type == WireLabelInstruction.LabelType.input || this.type == WireLabelInstruction.LabelType.nizkinput)) {
               throw new RuntimeException("Errors: Some inputs are without values  (" + this.desc + ")");
            }

            System.out.println("\t[" + this.type + "] Value of Wire # " + this.w + (this.desc.length() > 0 ? " (" + this.desc + ")" : "") + " :: " + evaluator.getWireValue(this.w).toString(Config.hexOutputEnabled ? 16 : 10));
         }
      }
   }

   public LabelType getType() {
      return this.type;
   }

   public boolean doneWithinCircuit() {
      return this.type != WireLabelInstruction.LabelType.debug;
   }

   public Wire[] getUsedWires() {
      return new Wire[]{this.w};
   }

   public Instruction copy(Wire[] wireArray) {
      if (this.w != null) {
         Wire newWire;
         if (this.type != WireLabelInstruction.LabelType.input && this.type != WireLabelInstruction.LabelType.nizkinput) {
            newWire = wireArray[this.w.getWireId()];
            return new WireLabelInstruction(this.type, newWire, new String[]{this.desc});
         } else if (this.w.getWireId() != 0) {
            newWire = this.w.copy();
            wireArray[newWire.getWireId()] = newWire;
            return new WireLabelInstruction(this.type, newWire, new String[]{this.desc});
         } else {
            return new WireLabelInstruction(this.type, wireArray[0], new String[]{this.desc});
         }
      } else {
         return null;
      }
   }

   public static enum LabelType {
      input,
      output,
      nizkinput,
      debug;
   }
}
