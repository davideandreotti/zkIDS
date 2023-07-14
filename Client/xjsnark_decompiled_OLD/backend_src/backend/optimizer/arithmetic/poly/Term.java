package backend.optimizer.arithmetic.poly;

import backend.config.Config;
import backend.resource.ResourceBundle;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class Term implements Iterable<OptVariable> {
   OptVariable[] vars;
   short[] powers;
   BigInteger coeff;
   short numVars;
   static int threshold = 20;
   static int step = 1;

   Term() {
      this.vars = new OptVariable[step];
      this.powers = new short[step];
      this.coeff = BigInteger.ONE;
      this.numVars = 0;
   }

   Term(Term t) {
      this.vars = (OptVariable[])Arrays.copyOf(t.vars, t.vars.length);
      this.powers = Arrays.copyOf(t.powers, t.powers.length);
      this.coeff = t.coeff;
      this.numVars = t.numVars;
   }

   public Term(OptVariable optVariable) {
      this();
      this.powers[0] = 1;
      this.vars[0] = optVariable;
      this.coeff = BigInteger.ONE;
      this.numVars = 1;
   }

   public Term(OptVariable optVariable, short p) {
      this();
      this.powers[0] = p;
      this.vars[0] = optVariable;
      this.coeff = BigInteger.ONE;
      this.numVars = 1;
   }

   public Term(BigInteger coeff) {
      this();
      this.coeff = ResourceBundle.getInstance().getBigInteger(coeff);
   }

   public Term(OptVariable[] vars, short[] powers, BigInteger coeff, short numVars) {
      this.vars = vars;
      this.powers = powers;
      this.coeff = coeff;
      this.numVars = numVars;
   }

   private void reallocate() {
      OptVariable[] tmpOptVarArray = new OptVariable[this.numVars + step];
      short[] tmpExponents = new short[this.numVars + step];
      System.arraycopy(this.vars, 0, tmpOptVarArray, 0, this.numVars);
      System.arraycopy(this.powers, 0, tmpExponents, 0, this.numVars);
      this.vars = tmpOptVarArray;
      this.powers = tmpExponents;
   }

   public boolean hasOptvar(OptVariable x) {
      OptVariable[] var5;
      int var4 = (var5 = this.vars).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         OptVariable y = var5[var3];
         if (y.equals(x)) {
            return true;
         }
      }

      return false;
   }

   public int getIdx(OptVariable x) {
      for(int i = 0; i < this.numVars; ++i) {
         if (this.vars[i].equals(x)) {
            return i;
         }
      }

      return -1;
   }

   public short getExponent(OptVariable x) {
      for(int i = 0; i < this.numVars; ++i) {
         if (this.vars[i].equals(x)) {
            return this.powers[i];
         }
      }

      return 0;
   }

   public void setExponent(OptVariable v, short exp) {
      if (exp != 0) {
         int idx = this.getIdx(v);
         if (idx == -1) {
            if (this.numVars == this.vars.length) {
               this.reallocate();
            }

            this.vars[this.numVars] = v;
            if (!v.isBit) {
               this.powers[this.numVars] = exp;
            } else {
               this.powers[this.numVars] = 1;
            }

            ++this.numVars;
         } else if (!v.isBit) {
            short[] var10000 = this.powers;
            var10000[idx] += exp;
         }

      }
   }

   public Term multiply(Term t) {
      Term result = new Term(this);
      OptVariable[] var6;
      int var5 = (var6 = t.vars).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         OptVariable v = var6[var4];
         result.setExponent(v, t.getExponent(v));
      }

      BigInteger newCoeff = ResourceBundle.getInstance().getBigInteger(this.coeff.multiply(t.coeff).mod(Config.getFiniteFieldModulus()));
      result.coeff = newCoeff;
      return result;
   }

   public Term addToConstant(BigInteger constant) {
      BigInteger newCoeff = this.coeff.add(constant).mod(Config.getFiniteFieldModulus());
      newCoeff = ResourceBundle.getInstance().getBigInteger(newCoeff);
      return new Term(this.vars, this.powers, newCoeff, this.numVars);
   }

   public Term multiplyConstant(BigInteger constant) {
      BigInteger newCoeff = this.coeff.multiply(constant).mod(Config.getFiniteFieldModulus());
      newCoeff = ResourceBundle.getInstance().getBigInteger(newCoeff);
      return new Term(this.vars, this.powers, newCoeff, this.numVars);
   }

   public int hashCode() {
      int h = 0;

      for(int i = 0; i < this.numVars; ++i) {
         h += this.vars[i].hashCode() + this.powers[i];
      }

      return h;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o instanceof Term) {
         Term t1 = this;
         Term t2 = (Term)o;
         if (this.numVars != t2.numVars) {
            return false;
         } else {
            boolean check = true;

            for(int i = 0; i < t1.numVars; ++i) {
               short p = this.powers[i];
               OptVariable v = this.vars[i];
               if (p != t2.getExponent(v)) {
                  check = false;
               }
            }

            return check;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      String result;
      if (this.coeff.equals(BigInteger.ONE)) {
         result = "";
         if (this.numVars == 0) {
            result = "1";
         }
      } else {
         result = this.coeff.toString();
         if (this.numVars > 0) {
            result = result + "*";
         }
      }

      for(int i = 0; i < this.numVars; ++i) {
         Short p = this.powers[i];
         OptVariable v = this.vars[i];
         if (p == 1) {
            result = result + v;
         } else {
            result = result + v + "^" + p;
         }

         if (i != this.numVars - 1) {
            result = result + "*";
         }
      }

      return result;
   }

   public BigInteger getCoeff() {
      return this.coeff;
   }

   public int getCost() {
      int cost = 0;
      boolean init = false;

      for(int i = 0; i < this.numVars; ++i) {
         Short p = this.powers[i];
         cost += (int)Math.floor(Math.log10((double)p) / Math.log10(2.0)) + (Integer.bitCount(p) - 1);
         if (init) {
            ++cost;
         }

         init = true;
      }

      return cost;
   }

   public Iterator<OptVariable> iterator() {
      return new VarIterator();
   }

   public VarIterator getVarIterator() {
      return new VarIterator();
   }

   public Collection<OptVariable> getVars() {
      return Arrays.asList(this.vars).subList(0, this.numVars);
   }

   public int getVarCount() {
      return this.numVars;
   }

   public short getMaxExponent() {
      short max = 0;

      for(int i = 0; i < this.numVars; ++i) {
         if (this.powers[i] > max) {
            max = this.powers[i];
         }
      }

      return max;
   }

   public class VarIterator implements Iterator<OptVariable> {
      int idx = 0;

      public boolean hasNext() {
         return this.idx < Term.this.numVars;
      }

      public OptVariable next() {
         return Term.this.vars[this.idx++];
      }

      public short getExponent() {
         return Term.this.powers[this.idx - 1];
      }
   }
}
