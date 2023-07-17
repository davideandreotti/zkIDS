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

public class LongIntegerModConstantGadget extends Gadget {
   private PackedValue a;
   private PackedValue b;
   private PackedValue r;
   private PackedValue q;
   private boolean restrictRange;

   public LongIntegerModConstantGadget(PackedValue a, PackedValue b, boolean restrictRange, String... desc) {
      super(desc);
      this.a = a;
      this.b = b;
      this.restrictRange = restrictRange;
      this.buildCircuit();
   }

   private void buildCircuit() {
      int aBitwidth = this.a.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      int bBitwidth = this.b.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK).bitLength();
      if (aBitwidth < bBitwidth) {
         throw new IllegalArgumentException("a's bitwidth < b's bitwidth -- This gadget is not needed.");
      } else {
         int qBitwidth = aBitwidth - bBitwidth + 1;
         int rChunkLength = (int)Math.ceil((double)bBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         int qChunkLength = (int)Math.ceil((double)qBitwidth * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         Wire[] rWires = this.generator.__createProverWitnessWireArray(rChunkLength);
         Wire[] qWires = this.generator.__createProverWitnessWireArray(qChunkLength);
         int[] rChunkBitwidths = new int[rChunkLength];
         int[] qChunkBitwidths = new int[qChunkLength];
         Arrays.fill(rChunkBitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
         Arrays.fill(qChunkBitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
         if (bBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
            rChunkBitwidths[rChunkLength - 1] = bBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
         }

         if (qBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
            qChunkBitwidths[qChunkLength - 1] = qBitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
         }

         this.r = new PackedValue(rWires, rChunkBitwidths);
         this.q = new PackedValue(qWires, qChunkBitwidths);
         this.generator.__specifyProverWitnessComputation(new Instruction() {
            public void evaluate(CircuitEvaluator evaluator) {
               BigInteger aValue = evaluator.getWireValue(LongIntegerModConstantGadget.this.a, UnsignedInteger.BITWIDTH_PER_CHUNK);
               BigInteger bValue = evaluator.getWireValue(LongIntegerModConstantGadget.this.b, UnsignedInteger.BITWIDTH_PER_CHUNK);
               BigInteger rValue = aValue.mod(bValue);
               BigInteger qValue = aValue.divide(bValue);
               evaluator.setWireValue(LongIntegerModConstantGadget.this.r.getArray(), Util.split(rValue, UnsignedInteger.BITWIDTH_PER_CHUNK));
               evaluator.setWireValue(LongIntegerModConstantGadget.this.q.getArray(), Util.split(qValue, UnsignedInteger.BITWIDTH_PER_CHUNK));
            }
         });
         this.r.forceBitwidth();
         this.q.forceBitwidth();
         PackedValue res = this.q.mul(this.b).add(this.r);
         res.forceEquality2(this.a);
         if (this.restrictRange) {
            this.generator.__addOneAssertion(this.r.isLessThan(this.b, bBitwidth).getWire());
         }

      }
   }

   public Wire[] getOutputWires() {
      return this.r.getArray();
   }

   public PackedValue getRemainder() {
      return this.r;
   }

   public PackedValue getQuotient() {
      return this.q;
   }
}
