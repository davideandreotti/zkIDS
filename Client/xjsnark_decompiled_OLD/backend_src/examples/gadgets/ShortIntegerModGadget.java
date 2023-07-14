package examples.gadgets;

import backend.auxTypes.PackedValue;
import backend.auxTypes.UnsignedInteger;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;

public class ShortIntegerModGadget extends Gadget {
   private PackedValue a;
   private PackedValue modulus;
   private PackedValue inverse;
   private PackedValue q;

   public ShortIntegerModGadget(PackedValue a, PackedValue modulus, String... desc) {
      super(desc);
      this.a = a;
      this.modulus = modulus;
      this.buildCircuit();
   }

   private void buildCircuit() {
      int modulusBitwidth = this.modulus.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int aBitwidth = this.a.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int qBitwidth = aBitwidth + 1;
      Wire[] inverseWires = this.generator.__createProverWitnessWireArray(1);
      Wire[] qWires = this.generator.__createProverWitnessWireArray(1);
      int[] inverseChunkBitwidths = new int[]{modulusBitwidth};
      int[] qChunkBitwidths = new int[]{qBitwidth};
      this.inverse = new PackedValue(inverseWires, inverseChunkBitwidths);
      this.q = new PackedValue(qWires, qChunkBitwidths);
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(ShortIntegerModGadget.this.a, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger bValue = evaluator.getWireValue(ShortIntegerModGadget.this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger rValue = aValue.modInverse(bValue);
            BigInteger qValue = aValue.multiply(rValue).divide(bValue);
            evaluator.setWireValue(ShortIntegerModGadget.this.inverse.getArray()[0], rValue);
            evaluator.setWireValue(ShortIntegerModGadget.this.q.getArray()[0], qValue);
         }
      });
      this.inverse.forceBitwidth();
      this.q.forceBitwidth();
      PackedValue res = this.q.mul(this.modulus).add(new PackedValue(new BigInteger[]{BigInteger.ONE}));
      res.forceEquality2(this.a.mul(this.inverse));
   }

   public Wire[] getOutputWires() {
      return this.inverse.getArray();
   }

   public PackedValue getInverse() {
      return this.inverse;
   }
}
