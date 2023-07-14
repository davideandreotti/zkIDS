package examples.generators;

import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import examples.gadgets.SHA256Gadget;

public class SHA2CircuitGenerator extends CircuitGenerator {
   private Wire[] inputWires;
   private SHA256Gadget sha2Gadget;

   public SHA2CircuitGenerator(String circuitName) {
      super(circuitName);
   }

   protected void outsource() {
      this.inputWires = this.__createInputWireArray(16, new String[0]);
      this.sha2Gadget = new SHA256Gadget(this.inputWires, 32, 64, false, false, new String[0]);
      Wire[] digest = this.sha2Gadget.getOutputWires();
      this.__makeOutputArray(digest, new String[]{"digest"});
   }

   public void __generateSampleInput(CircuitEvaluator evaluator) {
      String inputStr = "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijkl";

      for(int i = 0; i < this.inputWires.length; ++i) {
         evaluator.setWireValue(this.inputWires[i], (long)inputStr.charAt(i));
      }

   }

   public static void main(String[] args) throws Exception {
      SHA2CircuitGenerator generator = new SHA2CircuitGenerator("sha_256");
      generator.__generateCircuit();
      generator.__evalCircuit();
      generator.__prepFiles();
   }
}
