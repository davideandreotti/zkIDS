package examples.gadgets;

import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import backend.structure.WireArray;
import java.math.BigInteger;
import util.Util;

public class ModConstantGadget extends Gadget {
   private Wire a;
   private BigInteger b;
   private Wire r;
   private Wire q;
   private int aBitwidth;
   private boolean restrictRange;

   public ModConstantGadget(Wire a, int bitwidth, BigInteger b, boolean restrictRange, String... desc) {
      super(desc);
      this.a = a;
      this.b = b;
      this.aBitwidth = bitwidth;
      this.restrictRange = restrictRange;
      if (b.signum() != 1) {
         throw new IllegalArgumentException("b must be a positive constant. Signed operations not supported yet.");
      } else if (bitwidth < b.bitLength()) {
         throw new IllegalArgumentException("a's bitwidth < b's bitwidth -- This gadget is not needed.");
      } else {
         this.buildCircuit();
      }
   }

   private void buildCircuit() {
      this.r = this.generator.__createProverWitnessWire("mod result");
      this.q = this.generator.__createProverWitnessWire("division result");
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(ModConstantGadget.this.a);
            BigInteger rValue = aValue.mod(ModConstantGadget.this.b);
            evaluator.setWireValue(ModConstantGadget.this.r, rValue);
            BigInteger qValue = aValue.divide(ModConstantGadget.this.b);
            evaluator.setWireValue(ModConstantGadget.this.q, qValue);
         }
      });
      int bBitwidth = this.b.bitLength();
      this.r.restrictBitLength(bBitwidth);
      this.q.restrictBitLength(this.aBitwidth - bBitwidth + 1);
      this.generator.__addEqualityAssertion(this.q.mul(this.b).add(this.r), this.a);
      if (this.restrictRange) {
         if (this.b.equals(Util.computeBound(bBitwidth - 1).add(BigInteger.ONE))) {
            WireArray bits = this.r.getBitWires(bBitwidth);
            Wire sum = this.generator.__getZeroWire();

            for(int i = 0; i < bits.size() - 1; ++i) {
               sum = sum.add(bits.get(i));
            }

            this.generator.__addAssertion(sum, bits.get(bits.size() - 1), this.generator.__getZeroWire());
         } else {
            this.generator.__addOneAssertion(this.r.isLessThan(this.b, bBitwidth));
         }
      }

   }

   public Wire[] getOutputWires() {
      return new Wire[]{this.r};
   }

   public Wire getRemainder() {
      return this.r;
   }

   public Wire getQuotient() {
      return this.q;
   }
}
