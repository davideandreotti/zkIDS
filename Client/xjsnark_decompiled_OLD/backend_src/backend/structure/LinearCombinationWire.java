package backend.structure;

public class LinearCombinationWire extends Wire {
   private WireArray bitWires;

   public LinearCombinationWire(int wireId) {
      super(wireId);
   }

   public LinearCombinationWire(WireArray bits) {
      super(bits);
   }

   WireArray getBitWires() {
      return this.bitWires;
   }

   void setBits(WireArray bitWires) {
      this.bitWires = bitWires;
   }

   public Wire copy() {
      LinearCombinationWire newWire = new LinearCombinationWire(this.wireId);
      newWire.bitWires = this.bitWires;
      return newWire;
   }
}
