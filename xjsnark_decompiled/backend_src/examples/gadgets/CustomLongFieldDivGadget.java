package examples.gadgets;

import backend.auxTypes.PackedValue;
import backend.auxTypes.UnsignedInteger;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;
import java.util.Arrays;
import util.Util;

public class CustomLongFieldDivGadget extends Gadget {
   private PackedValue a;
   private PackedValue b;
   private PackedValue m;
   private PackedValue result;
   private PackedValue q;

   public CustomLongFieldDivGadget(PackedValue a, PackedValue b, PackedValue m, String... desc) {
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
      int resultChunkLength = (int)Math.ceil((double)mBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
      int qChunkLength = (int)Math.ceil((double)qBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
      Wire[] resultWires = this.generator.__createProverWitnessWireArray(resultChunkLength);
      Wire[] qWires = this.generator.__createProverWitnessWireArray(qChunkLength);
      int[] resultChunkBitwidths = new int[resultChunkLength];
      int[] qChunkBitwidths = new int[qChunkLength];
      Arrays.fill(resultChunkBitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
      Arrays.fill(qChunkBitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
      if (mBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
         resultChunkBitwidths[resultChunkLength - 1] = mBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
      }

      if (qBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
         qChunkBitwidths[qChunkLength - 1] = qBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
      }

      this.result = new PackedValue(resultWires, resultChunkBitwidths);
      this.q = new PackedValue(qWires, qChunkBitwidths);
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(CustomLongFieldDivGadget.this.a, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger bValue = evaluator.getWireValue(CustomLongFieldDivGadget.this.b, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger mValue = evaluator.getWireValue(CustomLongFieldDivGadget.this.m, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger rValue = bValue.modInverse(mValue).multiply(aValue).mod(mValue);
            BigInteger qValue = bValue.multiply(rValue).subtract(aValue).divide(mValue);
            evaluator.setWireValue(CustomLongFieldDivGadget.this.result.getArray(), Util.split(rValue, UnsignedInteger.BITWIDTH_PER_CHUNK));
            evaluator.setWireValue(CustomLongFieldDivGadget.this.q.getArray(), Util.split(qValue, UnsignedInteger.BITWIDTH_PER_CHUNK));
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
