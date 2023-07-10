package backend.operations;

import backend.structure.CircuitGenerator;
import backend.structure.Wire;

public abstract class Gadget {
   protected CircuitGenerator generator = CircuitGenerator.__getActiveCircuitGenerator();
   protected String description;

   public Gadget(String... desc) {
      if (desc.length > 0) {
         this.description = desc[0];
      } else {
         this.description = "";
      }

   }

   public abstract Wire[] getOutputWires();

   public String toString() {
      return this.getClass().getSimpleName() + " " + this.description;
   }

   public String debugStr(String s) {
      return this + ":" + s;
   }

   public void defineWitnessWires() {
   }
}
