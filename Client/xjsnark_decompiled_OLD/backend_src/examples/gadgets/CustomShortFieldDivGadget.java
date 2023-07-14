package examples.gadgets;

import backend.auxTypes.PackedValue;
import backend.auxTypes.UnsignedInteger;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;

public class CustomShortFieldDivGadget extends Gadget {
   private PackedValue a;
   private PackedValue b;
   private PackedValue m;
   private PackedValue result;
   private PackedValue q;

   public CustomShortFieldDivGadget(PackedValue a, PackedValue b, PackedValue m, String... desc) {
      super(desc);
      this.a = a;
      this.b = b;
      this.m = m;
      this.buildCircuit();
   }

   private void buildCircuit() {
      int bBitwidth = this.b.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int mBitwidth = this.m.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int aBitwidth = this.a.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int qBitwidth = bBitwidth + 1;
      int resultChunkLength = 1;
      int qChunkLength = 1;
      Wire[] resultWires = this.generator.__createProverWitnessWireArray(resultChunkLength);
      Wire[] qWires = this.generator.__createProverWitnessWireArray(qChunkLength);
      int[] resultChunkBitwidths = new int[resultChunkLength];
      resultChunkBitwidths[0] = mBitwidth;
      int[] qChunkBitwidths = new int[qChunkLength];
      qChunkBitwidths[0] = qBitwidth;
      this.result = new PackedValue(resultWires, resultChunkBitwidths);
      this.q = new PackedValue(qWires, qChunkBitwidths);
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(CustomShortFieldDivGadget.this.a, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger bValue = evaluator.getWireValue(CustomShortFieldDivGadget.this.b, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger mValue = evaluator.getWireValue(CustomShortFieldDivGadget.this.m, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger rValue = bValue.modInverse(mValue).multiply(aValue).mod(mValue);
            BigInteger qValue = bValue.multiply(rValue).divide(mValue);
            evaluator.setWireValue(CustomShortFieldDivGadget.this.result.getArray()[0], rValue);
            evaluator.setWireValue(CustomShortFieldDivGadget.this.q.getArray()[0], qValue);
         }
      });
      this.result.forceBitwidth();
      this.q.forceBitwidth();
      this.result.setWitnessIndicator(true);
      this.q.setWitnessIndicator(true);
      PackedValue res = this.q.mul(this.m).add(this.a);
      res.forceEquality2(this.b.mul(this.result));
   }

   public Wire[] getOutputWires() {
      return this.result.getArray();
   }

   public PackedValue getResult() {
      return this.result;
   }
}
