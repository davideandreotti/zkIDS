package backend.eval;

import backend.structure.Wire;

public interface Instruction {
   void evaluate(CircuitEvaluator var1);

   default void emit(CircuitEvaluator evaluator) {
   }

   default boolean doneWithinCircuit() {
      return false;
   }

   default Wire[] getUsedWires() {
      return new Wire[0];
   }

   default Instruction copy(Wire[] wireArray) {
      return null;
   }
}
