package examples.generators;

import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import examples.gadgets.PinocchioGadget;
import examples.gadgets.SHA256Gadget;
import java.util.Arrays;
import util.Util;

public class AugmentedAuctionCircuitGenerator extends CircuitGenerator {
   private Wire[] secretInputValues;
   private Wire[] secretOutputValues;
   private Wire[][] secretInputRandomness;
   private Wire[][] secretOutputRandomness;
   private String pathToCompiledCircuit;
   private int numParties;

   public AugmentedAuctionCircuitGenerator(String circuitName, String pathToCompiledCircuit, int numParticipants) {
      super(circuitName);
      this.pathToCompiledCircuit = pathToCompiledCircuit;
      this.numParties = numParticipants + 1;
   }

   protected void outsource() {
      this.secretInputValues = this.__createProverWitnessWireArray(this.numParties - 1, new String[0]);
      this.secretInputRandomness = new Wire[this.numParties - 1][];
      this.secretOutputRandomness = new Wire[this.numParties][];

      for(int i = 0; i < this.numParties - 1; ++i) {
         this.secretInputRandomness[i] = this.__createProverWitnessWireArray(7, new String[0]);
         this.secretOutputRandomness[i] = this.__createProverWitnessWireArray(7, new String[0]);
      }

      this.secretOutputRandomness[this.numParties - 1] = this.__createProverWitnessWireArray(7, new String[0]);
      PinocchioGadget auctionGagdet = new PinocchioGadget(Util.concat(this.__zeroWire, this.secretInputValues), this.pathToCompiledCircuit, new String[0]);
      Wire[] outputs = auctionGagdet.getOutputWires();
      this.secretOutputValues = (Wire[])Arrays.copyOfRange(outputs, 0, outputs.length - 1);

      int i;
      SHA256Gadget g;
      for(i = 0; i < this.numParties - 1; ++i) {
         g = new SHA256Gadget(Util.concat(this.secretInputValues[i], this.secretInputRandomness[i]), 64, 64, false, false, new String[0]);
         this.__makeOutputArray(g.getOutputWires(), new String[]{"Commitment for party # " + i + "'s input balance."});
      }

      for(i = 0; i < this.numParties; ++i) {
         this.secretOutputValues[i] = this.secretOutputValues[i].getBitWires(128).packAsBits(64);
         g = new SHA256Gadget(Util.concat(this.secretOutputValues[i], this.secretOutputRandomness[i]), 64, 64, false, false, new String[0]);
         this.__makeOutputArray(g.getOutputWires(), new String[]{"Commitment for party # " + i + "'s output balance."});
      }

   }

   public void __generateSampleInput(CircuitEvaluator evaluator) {
      int i;
      for(i = 0; i < this.numParties - 1; ++i) {
         evaluator.setWireValue(this.secretInputValues[i], Util.nextRandomBigInteger(63));
      }

      Wire w;
      int var4;
      int var5;
      Wire[] var6;
      for(i = 0; i < this.numParties - 1; ++i) {
         var5 = (var6 = this.secretInputRandomness[i]).length;

         for(var4 = 0; var4 < var5; ++var4) {
            w = var6[var4];
            evaluator.setWireValue(w, Util.nextRandomBigInteger(64));
         }
      }

      for(i = 0; i < this.numParties; ++i) {
         var5 = (var6 = this.secretOutputRandomness[i]).length;

         for(var4 = 0; var4 < var5; ++var4) {
            w = var6[var4];
            evaluator.setWireValue(w, Util.nextRandomBigInteger(64));
         }
      }

   }

   public static void main(String[] args) throws Exception {
      AugmentedAuctionCircuitGenerator generator = new AugmentedAuctionCircuitGenerator("augmented_auction_10", "auction_10.arith", 10);
      generator.__generateCircuit();
      generator.__evalCircuit();
      generator.__prepFiles();
   }
}
