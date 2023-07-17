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

public class InverseLongIntegerModGadget extends Gadget {
   private PackedValue a;
   private PackedValue modulus;
   private PackedValue inverse;
   private PackedValue q;

   public InverseLongIntegerModGadget(PackedValue a, PackedValue modulus, String... desc) {
      super(desc);
      this.a = a;
      this.modulus = modulus;
      this.buildCircuit();
   }

   private void buildCircuit() {
      int modulusBitwidth = this.modulus.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int aBitwidth = this.a.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int qBitwidth = aBitwidth + 1;
      int inverseChunkLength = (int)Math.ceil((double)modulusBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
      int qChunkLength = (int)Math.ceil((double)qBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
      Wire[] inverseWires = this.generator.__createProverWitnessWireArray(inverseChunkLength);
      Wire[] qWires = this.generator.__createProverWitnessWireArray(qChunkLength);
      int[] inverseChunkBitwidths = new int[inverseChunkLength];
      int[] qChunkBitwidths = new int[qChunkLength];
      Arrays.fill(inverseChunkBitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
      Arrays.fill(qChunkBitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
      if (modulusBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
         inverseChunkBitwidths[inverseChunkLength - 1] = modulusBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
      }

      if (qBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
         qChunkBitwidths[qChunkLength - 1] = qBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
      }

      this.inverse = new PackedValue(inverseWires, inverseChunkBitwidths);
      this.q = new PackedValue(qWires, qChunkBitwidths);
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(InverseLongIntegerModGadget.this.a, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger bValue = evaluator.getWireValue(InverseLongIntegerModGadget.this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger rValue = aValue.modInverse(bValue);
            BigInteger qValue = aValue.multiply(rValue).divide(bValue);
            evaluator.setWireValue(InverseLongIntegerModGadget.this.inverse.getArray(), Util.split(rValue, UnsignedInteger.BITWIDTH_PER_CHUNK));
            evaluator.setWireValue(InverseLongIntegerModGadget.this.q.getArray(), Util.split(qValue, UnsignedInteger.BITWIDTH_PER_CHUNK));
         }
      });
      this.inverse.forceBitwidth();
      this.q.forceBitwidth();
      this.inverse.setWitnessIndicator(true);
      this.q.setWitnessIndicator(true);
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
