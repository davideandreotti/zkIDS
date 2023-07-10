package examples.gadgets;

import backend.config.Config;
import backend.operations.Gadget;
import backend.structure.Wire;
import backend.structure.WireArray;

public class MerkleTreePathGadget extends Gadget {
   private static int digestWidth = 3;
   private int treeHeight;
   private Wire directionSelectorWire;
   private Wire[] directionSelectorBits;
   private Wire[] leafWires;
   private Wire[] intermediateHashWires;
   private Wire[] outRoot;
   private int leafWordBitWidth;

   public MerkleTreePathGadget(Wire directionSelectorWire, Wire[] leafWires, Wire[] intermediateHasheWires, int leafWordBitWidth, int treeHeight, String... desc) {
      super(desc);
      this.directionSelectorWire = directionSelectorWire;
      this.treeHeight = treeHeight;
      this.leafWires = leafWires;
      this.intermediateHashWires = intermediateHasheWires;
      this.leafWordBitWidth = leafWordBitWidth;
      this.buildCircuit();
   }

   private void buildCircuit() {
      this.directionSelectorBits = this.directionSelectorWire.getBitWires(this.treeHeight).asArray();
      Wire[] leafBits = (new WireArray(this.leafWires)).getBits(this.leafWordBitWidth).asArray();
      SubsetSumHashGadget subsetSumGadget = new SubsetSumHashGadget(leafBits, false, new String[0]);
      Wire[] currentHash = subsetSumGadget.getOutputWires();

      for(int i = 0; i < this.treeHeight; ++i) {
         Wire[] inHash = new Wire[2 * digestWidth];

         int j;
         Wire temp;
         for(j = 0; j < digestWidth; ++j) {
            temp = currentHash[j].sub(this.intermediateHashWires[i * digestWidth + j]);
            Wire temp2 = this.directionSelectorBits[i].mul(temp);
            inHash[j] = this.intermediateHashWires[i * digestWidth + j].add(temp2);
         }

         for(j = digestWidth; j < 2 * digestWidth; ++j) {
            temp = currentHash[j - digestWidth].add(this.intermediateHashWires[i * digestWidth + j - digestWidth]);
            inHash[j] = temp.sub(inHash[j - digestWidth]);
         }

         Wire[] nextInputBits = (new WireArray(inHash)).getBits(Config.getNumBitsFiniteFieldModulus()).asArray();
         subsetSumGadget = new SubsetSumHashGadget(nextInputBits, false, new String[0]);
         currentHash = subsetSumGadget.getOutputWires();
      }

      this.outRoot = currentHash;
   }

   public Wire[] getOutputWires() {
      return this.outRoot;
   }
}
