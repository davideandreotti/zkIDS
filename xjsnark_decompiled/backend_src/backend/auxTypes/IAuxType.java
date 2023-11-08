package backend.auxTypes;

import backend.eval.CircuitEvaluator;
import backend.structure.Wire;
import java.math.BigInteger;

public interface IAuxType {
   Wire[] toWires();

   void mapValue(BigInteger var1, CircuitEvaluator var2);

   void mapRandomValue(CircuitEvaluator var1);

   BigInteger getValueFromEvaluator(CircuitEvaluator var1);

   void makeOutput(String... var1);

   Bit[] getBitElements();

   void forceEqual(IAuxType var1);

   Bit isEqualTo(IAuxType var1);

   Bit isNotEqualTo(IAuxType var1);

   IAuxType copy();

   VariableState getState();

   PackedValue getPackedValue();

   boolean isConstant();

   BigInteger getConstant();

   int getRequiredBitWidth();

   int getCurrentBitWidth();

   void verifyRange();
}
