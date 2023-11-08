package examples.generators;

import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;

public class SimpleCircuitGenerator extends CircuitGenerator {
   private Wire[] inputs;

   public SimpleCircuitGenerator(String circuitName) {
      super(circuitName);
   }

   protected void outsource() {
      this.inputs = this.__createInputWireArray(4, new String[0]);
      Wire r1 = this.inputs[0].mul(this.inputs[1]);
      Wire r2 = this.inputs[2].add(this.inputs[3]);
      Wire result = r1.add(5L).mul(r2.mul(6L));
      this.__makeOutput(result, new String[0]);
   }

   public void __generateSampleInput(CircuitEvaluator circuitEvaluator) {
      for(int i = 0; i < 4; ++i) {
         circuitEvaluator.setWireValue(this.inputs[i], (long)(i + 1));
      }

   }

   public static void main(String[] args) throws Exception {
      SimpleCircuitGenerator generator = new SimpleCircuitGenerator("simple_example");
      generator.__generateCircuit();
      generator.__evalCircuit();
      generator.__prepFiles();
   }
}
