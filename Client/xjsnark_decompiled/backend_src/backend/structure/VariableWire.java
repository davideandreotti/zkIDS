package backend.structure;

public class VariableWire extends Wire {
   private WireArray bitWires;

   public VariableWire(int wireId) {
      super(wireId);
   }

   public VariableWire(WireArray bits) {
      super(bits);
   }

   WireArray getBitWires() {
      return this.bitWires;
   }

   void setBits(WireArray bitWires) {
      this.bitWires = bitWires;
   }

   public Wire copy() {
      VariableWire newWire = new VariableWire(this.wireId);
      newWire.bitWires = this.bitWires;
      return newWire;
   }
}
