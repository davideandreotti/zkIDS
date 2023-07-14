package examples.generators;

import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import examples.gadgets.DotPorductGadget;

public class DotProductCircuitGenerator extends CircuitGenerator {
   private Wire[] a;
   private Wire[] b;
   private int dimension;

   public DotProductCircuitGenerator(String circuitName, int dimension) {
      super(circuitName);
      this.dimension = dimension;
   }

   protected void outsource() {
      this.a = this.__createInputWireArray(this.dimension, new String[]{"Input a"});
      this.b = this.__createInputWireArray(this.dimension, new String[]{"Input b"});
      DotPorductGadget dotPorductGadget = new DotPorductGadget(this.a, this.b, new String[0]);
      Wire[] result = dotPorductGadget.getOutputWires();
      this.__makeOutput(result[0], new String[]{"output of dot product a, b"});
   }

   public void __generateSampleInput(CircuitEvaluator circuitEvaluator) {
      for(int i = 0; i < this.dimension; ++i) {
         circuitEvaluator.setWireValue(this.a[i], (long)(10 + i));
         circuitEvaluator.setWireValue(this.b[i], (long)(20 + i));
      }

   }

   public static void main(String[] args) throws Exception {
      DotProductCircuitGenerator generator = new DotProductCircuitGenerator("dot_product", 3);
      generator.__generateCircuit();
      generator.__evalCircuit();
      generator.__prepFiles();
   }
}
