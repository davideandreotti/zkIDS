package examples.gadgets;

import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;

public class ModGadget extends Gadget {
   private Wire a;
   private Wire b;
   private Wire r;
   private Wire q;
   private int bitwidth1;
   private int bitwidth2;
   private boolean restrictRange;

   public ModGadget(Wire a, int bitwidth1, Wire b, int bitwidth2, boolean restrictRange, String... desc) {
      super(desc);
      this.a = a;
      this.b = b;
      this.bitwidth1 = bitwidth1;
      this.bitwidth2 = bitwidth2;
      this.restrictRange = restrictRange;
      this.buildCircuit();
   }

   private void buildCircuit() {
      this.r = this.generator.__createProverWitnessWire("mod result");
      this.q = this.generator.__createProverWitnessWire("division result");
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(ModGadget.this.a);
            BigInteger bValue = evaluator.getWireValue(ModGadget.this.b);
            BigInteger rValue = aValue.mod(bValue);
            evaluator.setWireValue(ModGadget.this.r, rValue);
            BigInteger qValue = aValue.divide(bValue);
            evaluator.setWireValue(ModGadget.this.q, qValue);
         }
      });
      this.r.restrictBitLength(Math.min(this.bitwidth1, this.bitwidth2));
      this.q.restrictBitLength(this.bitwidth1);
      this.generator.__addEqualityAssertion(this.q.mul(this.b).add(this.r), this.a);
      if (this.restrictRange) {
         this.generator.__addOneAssertion(this.r.isLessThan(this.b, this.bitwidth2));
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
