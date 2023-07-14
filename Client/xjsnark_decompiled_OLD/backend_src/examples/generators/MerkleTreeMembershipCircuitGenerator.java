package examples.generators;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import examples.gadgets.MerkleTreePathGadget;
import util.Util;

public class MerkleTreeMembershipCircuitGenerator extends CircuitGenerator {
   private Wire[] publicRootWires;
   private Wire[] intermediateHasheWires;
   private Wire directionSelector;
   private Wire[] leafWires;
   private int leafNumOfWords = 10;
   private int leafWordBitWidth = 32;
   private int treeHeight;
   private int hashDigestDimension = 3;
   private MerkleTreePathGadget merkleTreeGadget;

   public MerkleTreeMembershipCircuitGenerator(String circuitName, int treeHeight) {
      super(circuitName);
      this.treeHeight = treeHeight;
   }

   protected void outsource() {
      this.publicRootWires = this.__createInputWireArray(this.hashDigestDimension, new String[]{"Input Merkle Tree Root"});
      this.intermediateHasheWires = this.__createProverWitnessWireArray(this.hashDigestDimension * this.treeHeight, new String[]{"Intermediate Hashes"});
      this.directionSelector = this.__createProverWitnessWire(new String[]{"Direction selector"});
      this.leafWires = this.__createProverWitnessWireArray(this.leafNumOfWords, new String[]{"Secret Leaf"});
      this.merkleTreeGadget = new MerkleTreePathGadget(this.directionSelector, this.leafWires, this.intermediateHasheWires, this.leafWordBitWidth, this.treeHeight, new String[0]);
      Wire[] actualRoot = this.merkleTreeGadget.getOutputWires();
      Wire errorAccumulator = this.__getZeroWire();

      for(int i = 0; i < this.hashDigestDimension; ++i) {
         Wire diff = actualRoot[i].sub(this.publicRootWires[i]);
         Wire check = diff.checkNonZero();
         errorAccumulator = errorAccumulator.add(check);
      }

      this.__makeOutputArray(actualRoot, new String[]{"Computed Root"});
      this.__makeOutput(errorAccumulator.checkNonZero(), new String[]{"Error if NON-zero"});
   }

   public void __generateSampleInput(CircuitEvaluator circuitEvaluator) {
      int i;
      for(i = 0; i < this.hashDigestDimension; ++i) {
         circuitEvaluator.setWireValue(this.publicRootWires[i], Util.nextRandomBigInteger(Config.getFiniteFieldModulus()));
      }

      circuitEvaluator.setWireValue(this.directionSelector, Util.nextRandomBigInteger(this.treeHeight));

      for(i = 0; i < this.hashDigestDimension * this.treeHeight; ++i) {
         circuitEvaluator.setWireValue(this.intermediateHasheWires[i], Util.nextRandomBigInteger(Config.getFiniteFieldModulus()));
      }

      for(i = 0; i < this.leafNumOfWords; ++i) {
         circuitEvaluator.setWireValue(this.leafWires[i], 2147483647L);
      }

   }

   public static void main(String[] args) throws Exception {
      MerkleTreeMembershipCircuitGenerator generator = new MerkleTreeMembershipCircuitGenerator("tree_64", 64);
      generator.__generateCircuit();
      generator.__evalCircuit();
      generator.__prepFiles();
   }
}
