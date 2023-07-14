package backend.optimizer.arithmetic.poly;

import backend.config.Config;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Term2 {
   ArrayList<OptVariable> optVariables;
   ArrayList<Short> vals;
   BigInteger coeff;

   public Term2() {
      this.optVariables = new ArrayList();
      this.vals = new ArrayList();
      this.coeff = BigInteger.ONE;
   }

   public Term2(OptVariable optVariable) {
      this();
      this.optVariables.add(optVariable);
      this.vals.add(Short.valueOf((short)1));
      this.coeff = BigInteger.ONE;
   }

   public Term2(ArrayList<OptVariable> optVariables, ArrayList<Short> vals, BigInteger coeff) {
      this.optVariables = optVariables;
      this.vals = vals;
      this.coeff = coeff;
   }

   public Term2(BigInteger coeff) {
      this();
      this.coeff = coeff;
   }

   public Term2 multiply(Term2 t) {
      ArrayList<OptVariable> newOptVariables = new ArrayList(this.optVariables);
      ArrayList<Short> newVals = new ArrayList(this.vals);
      int idx = -1;
      Iterator var6 = t.optVariables.iterator();

      while(var6.hasNext()) {
         OptVariable v = (OptVariable)var6.next();
         ++idx;
         if (this.optVariables.contains(v)) {
            if (!v.isBit) {
               int idx1 = this.optVariables.indexOf(v);
               newVals.set(idx1, (short)((Short)this.vals.get(idx1) + (Short)t.vals.get(idx)));
            }
         } else {
            newOptVariables.add(v);
            newVals.add((short)(Short)t.vals.get(idx));
         }
      }

      BigInteger newCoeff = this.coeff.multiply(t.coeff).mod(Config.getFiniteFieldModulus());
      return new Term2(newOptVariables, newVals, newCoeff);
   }

   public Term2 multiplyConstant(BigInteger constant) {
      BigInteger newCoeff = this.coeff.multiply(constant).mod(Config.getFiniteFieldModulus());
      return new Term2(this.optVariables, this.vals, newCoeff);
   }

   public int hashCode() {
      return this.optVariables.hashCode() + this.vals.hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Term2)) {
         return false;
      } else {
         Term2 t2 = (Term2)o;
         if (t2.vals.size() != this.vals.size()) {
            return false;
         } else {
            int idx = -1;
            Iterator var5 = this.optVariables.iterator();

            while(var5.hasNext()) {
               OptVariable v = (OptVariable)var5.next();
               ++idx;
               if (!((Short)this.vals.get(idx)).equals(t2.getVal(v))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public String toString() {
      String result;
      if (this.coeff.equals(BigInteger.ONE)) {
         result = "";
         if (this.optVariables.size() == 0) {
            result = "1";
         }
      } else {
         result = this.coeff.toString();
         if (this.optVariables.size() > 0) {
            result = result + "*";
         }
      }

      int size = this.optVariables.size();
      int idx = -1;
      Iterator var5 = this.optVariables.iterator();

      while(var5.hasNext()) {
         OptVariable v = (OptVariable)var5.next();
         ++idx;
         Short p = (Short)this.vals.get(idx);
         if (p == 1) {
            result = result + v;
         } else {
            result = result + v + "^" + p;
         }

         --size;
         if (size > 0) {
            result = result + "*";
         }
      }

      return result;
   }

   public BigInteger getCoeff() {
      return this.coeff;
   }

   public Short getVal(OptVariable v) {
      int idx = this.optVariables.indexOf(v);
      return idx != -1 ? (Short)this.vals.get(idx) : null;
   }

   public int getCost() {
      int cost = 0;
      boolean init = false;

      for(Iterator var4 = this.optVariables.iterator(); var4.hasNext(); init = true) {
         OptVariable v = (OptVariable)var4.next();
         Short p = this.getVal(v);
         cost += (int)Math.floor(Math.log10((double)p) / Math.log10(2.0)) + (Integer.bitCount(p) - 1);
         if (init) {
            ++cost;
         }
      }

      return cost;
   }

   public Collection<? extends OptVariable> getOptVars() {
      return this.optVariables;
   }
}
