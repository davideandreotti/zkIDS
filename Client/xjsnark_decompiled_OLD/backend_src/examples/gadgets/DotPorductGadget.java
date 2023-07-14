package examples.gadgets;

import backend.operations.Gadget;
import backend.structure.Wire;

public class DotPorductGadget extends Gadget {
   private Wire[] a;
   private Wire[] b;
   private Wire output;

   public DotPorductGadget(Wire[] a, Wire[] b, String... desc) {
      super(desc);
      if (a.length != b.length) {
         throw new IllegalArgumentException();
      } else {
         this.a = a;
         this.b = b;
         this.buildCircuit();
      }
   }

   private void buildCircuit() {
      this.output = this.generator.__getZeroWire();

      for(int i = 0; i < this.a.length; ++i) {
         Wire product = this.a[i].mul(this.b[i], "Multiply elements # " + i);
         this.output = this.output.add(product);
      }

   }

   public Wire[] getOutputWires() {
      return new Wire[]{this.output};
   }
}
