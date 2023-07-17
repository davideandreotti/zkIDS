package examples.generators;

import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import examples.gadgets.FieldDivisionGadget;

public class FieldDivisionCircuitGenerator extends CircuitGenerator {
   private Wire[] a;
   private Wire[] b;
   private FieldDivisionGadget[] gadgets;
   private int gatesNum;

   public FieldDivisionCircuitGenerator(String circuitName, int gatesNum) {
      super(circuitName);
      this.gatesNum = gatesNum;
   }

   protected void outsource() {
      this.a = this.__createInputWireArray(this.gatesNum, new String[]{"Input a"});
      this.b = this.__createInputWireArray(this.gatesNum, new String[]{"Input b"});
      this.gadgets = new FieldDivisionGadget[this.gatesNum];

      for(int i = 0; i < this.gatesNum; ++i) {
         this.gadgets[i] = new FieldDivisionGadget(this.a[i], this.b[i], new String[]{"Divison Gagdet#" + i});
         Wire[] result = this.gadgets[i].getOutputWires();
         this.__makeOutput(result[0], new String[]{"Output of gate # " + i});
      }

   }

   public void __generateSampleInput(CircuitEvaluator circuitEvaluator) {
      for(int i = 0; i < this.gatesNum; ++i) {
         circuitEvaluator.setWireValue(this.a[i], (long)(10 + i));
         circuitEvaluator.setWireValue(this.b[i], (long)(20 + i));
      }

   }

   public static void main(String[] args) throws Exception {
      FieldDivisionCircuitGenerator generator = new FieldDivisionCircuitGenerator("division", 100);
      generator.__generateCircuit();
      generator.__evalCircuit();
      generator.__prepFiles();
   }
}
