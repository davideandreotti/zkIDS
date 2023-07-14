package examples.gadgets;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;

public class FieldDivisionGadget extends Gadget {
   private Wire a;
   private Wire b;
   private Wire c;

   public FieldDivisionGadget(Wire a, Wire b, String... desc) {
      super(desc);
      this.a = a;
      this.b = b;
      this.buildCircuit();
   }

   private void buildCircuit() {
      this.c = this.generator.__createProverWitnessWire(this.debugStr("division result"));
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger aValue = evaluator.getWireValue(FieldDivisionGadget.this.a);
            BigInteger bValue = evaluator.getWireValue(FieldDivisionGadget.this.b);
            BigInteger cValue = aValue.multiply(bValue.modInverse(Config.getFiniteFieldModulus())).mod(Config.getFiniteFieldModulus());
            evaluator.setWireValue(FieldDivisionGadget.this.c, cValue);
         }
      });
      this.generator.__addAssertion(this.b, this.c, this.a, this.debugStr("Assertion for division result"));
   }

   public Wire[] getOutputWires() {
      return new Wire[]{this.c};
   }
}
