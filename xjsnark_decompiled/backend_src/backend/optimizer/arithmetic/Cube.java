package backend.optimizer.arithmetic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Cube {
   private int[] varPowers;
   private int[] constPowers;
   private int serial;
   private int numOfVars;
   private boolean sign;
   private BigInteger constant;

   public static Cube getOneCube(int numVars, int numConstants) {
      return new Cube(new int[numVars], new int[numConstants]);
   }

   public int getSerial() {
      return this.serial;
   }

   public void setSerial(int serial) {
      this.serial = serial;
   }

   public int getNumOfLiterals() {
      return this.varPowers.length + this.constPowers.length;
   }

   public int getNumOfVars() {
      return this.varPowers.length;
   }

   public int getNumOfConstants() {
      return this.constPowers.length;
   }

   public Cube(int[] powers, BigInteger constant) {
      this.numOfVars = powers.length;
      this.varPowers = powers;
      this.constPowers = new int[0];
      this.constant = constant;
      this.sign = constant.signum() == -1;
      if (this.sign) {
         this.constant = this.constant.abs();
      }

   }

   public Cube(int[] powers) {
      this(powers, BigInteger.ONE);
   }

   public Cube(int[] varPowers, int[] constPowers) {
      this.varPowers = varPowers;
      this.constPowers = constPowers;
   }

   public void extendWithConstantPowers(int numLiterals, HashMap<BigInteger, Integer> map) {
      int[] newPowers = new int[map.size()];
      if (map.get(this.constant) != null) {
         newPowers[(Integer)map.get(this.constant)] = 1;
      }

      this.constPowers = newPowers;
   }

   public void extendWithIntermediateVars(int numIntermediate) {
      int[] newPowers = new int[this.varPowers.length + numIntermediate];
      System.arraycopy(this.varPowers, 0, newPowers, 0, this.varPowers.length);
      this.varPowers = newPowers;
   }

   public void genSerial(ExpressionMinimizer minimizer) {
      if (this.serial == 0) {
         this.serial = minimizer.cubeGlobalCount++;
      }

   }

   public int getCost() {
      int s = 0;
      boolean init = false;

      for(int i = 0; i < this.varPowers.length; ++i) {
         int v = this.varPowers[i];
         if (v != 0) {
            s += (int)Math.floor(Math.log10((double)v) / Math.log10(2.0)) + (Integer.bitCount(v) - 1);
            if (!init) {
               init = true;
            } else {
               ++s;
            }
         }
      }

      return s;
   }

   public Cube(Cube o) {
      this.varPowers = Arrays.copyOf(o.varPowers, o.varPowers.length);
      this.constPowers = Arrays.copyOf(o.constPowers, o.constPowers.length);
      this.sign = o.sign;
      this.numOfVars = o.numOfVars;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Cube)) {
         return false;
      } else {
         Cube other = (Cube)o;
         boolean case1 = Arrays.equals(this.varPowers, other.varPowers) && Arrays.equals(this.constPowers, other.constPowers) && this.sign == other.sign;
         boolean case2 = false;
         return case1 || case2;
      }
   }

   private boolean isOne() {
      if (!this.isConstant()) {
         return false;
      } else {
         int s = 0;
         int[] var5;
         int var4 = (var5 = this.constPowers).length;

         for(int var3 = 0; var3 < var4; ++var3) {
            int c = var5[var3];
            s += c;
         }

         return s == 0;
      }
   }

   public int[] getVarPowers() {
      return this.varPowers;
   }

   public int[] getConstPowers() {
      return this.constPowers;
   }

   public boolean isConstant() {
      int sum = 0;

      for(int i = 0; i < this.varPowers.length; ++i) {
         sum += this.varPowers[i];
      }

      return sum == 0;
   }

   public String toString() {
      String s = "";
      if (this.sign) {
         s = s + "-";
      } else {
         s = s + "+";
      }

      int[] var5;
      int var4 = (var5 = this.varPowers).length;

      int a;
      int var3;
      for(var3 = 0; var3 < var4; ++var3) {
         a = var5[var3];
         s = s + a;
      }

      s = s + "*";
      var4 = (var5 = this.constPowers).length;

      for(var3 = 0; var3 < var4; ++var3) {
         a = var5[var3];
         s = s + a;
      }

      return s;
   }

   public int hashCode() {
      return Arrays.hashCode(this.varPowers);
   }

   public int get(int i) {
      return i < this.varPowers.length ? this.varPowers[i] : this.constPowers[i - this.varPowers.length];
   }

   public Cube divide(int varIndex) {
      int[] varPowers;
      int[] constPowers;
      if (varIndex < this.varPowers.length && this.varPowers[varIndex] > 0) {
         varPowers = Arrays.copyOf(this.varPowers, this.varPowers.length);
         constPowers = Arrays.copyOf(this.constPowers, this.constPowers.length);
         int var10002 = varPowers[varIndex]--;
         return new Cube(varPowers, constPowers);
      } else if (varIndex >= this.varPowers.length && this.constPowers[varIndex - this.varPowers.length] > 0) {
         varPowers = Arrays.copyOf(this.varPowers, this.varPowers.length);
         constPowers = Arrays.copyOf(this.constPowers, this.constPowers.length);
         --constPowers[varIndex - varPowers.length];
         return new Cube(varPowers, constPowers);
      } else {
         return null;
      }
   }

   public Cube divide(Cube c) {
      int i;
      for(i = 0; i < this.varPowers.length; ++i) {
         if (this.varPowers[i] < c.varPowers[i]) {
            return null;
         }
      }

      for(i = 0; i < this.constPowers.length; ++i) {
         if (this.constPowers[i] < c.constPowers[i]) {
            return null;
         }
      }

      int[] newVarPowers = Arrays.copyOf(this.varPowers, this.varPowers.length);
      int[] newConstPowers = Arrays.copyOf(this.constPowers, this.constPowers.length);

      int i;
      for(i = 0; i < this.varPowers.length; ++i) {
         newVarPowers[i] -= c.varPowers[i];
      }

      for(i = 0; i < this.constPowers.length; ++i) {
         newConstPowers[i] -= c.constPowers[i];
      }

      return new Cube(newVarPowers, newConstPowers);
   }

   public static Cube add(Cube c1, Cube c2) {
      if (c1.isOne()) {
         return new Cube(c2);
      } else if (c2.isOne()) {
         return new Cube(c1);
      } else {
         int[] varPowers = Arrays.copyOf(c1.varPowers, c1.varPowers.length);

         for(int i = 0; i < c1.varPowers.length; ++i) {
            varPowers[i] += c2.varPowers[i];
         }

         int[] constPowers = Arrays.copyOf(c1.constPowers, c1.constPowers.length);

         for(int i = 0; i < c1.constPowers.length; ++i) {
            constPowers[i] += c2.constPowers[i];
         }

         return new Cube(varPowers, constPowers);
      }
   }

   public static Cube largestDividingCube(ArrayList<Cube> powers) {
      if (powers.size() == 0) {
         return null;
      } else {
         Cube c = (Cube)powers.get(0);
         int[] varPowers = new int[c.varPowers.length];
         Arrays.fill(varPowers, Integer.MAX_VALUE);
         int[] constPowers = new int[c.constPowers.length];
         Arrays.fill(constPowers, Integer.MAX_VALUE);
         Iterator var5 = powers.iterator();

         while(var5.hasNext()) {
            Cube tmp = (Cube)var5.next();

            int i;
            for(i = 0; i < varPowers.length; ++i) {
               if (tmp.varPowers[i] < varPowers[i]) {
                  varPowers[i] = tmp.varPowers[i];
               }
            }

            for(i = 0; i < constPowers.length; ++i) {
               if (tmp.constPowers[i] < constPowers[i]) {
                  constPowers[i] = tmp.constPowers[i];
               }
            }
         }

         return new Cube(varPowers, constPowers);
      }
   }

   public int getPower(int k) {
      return k < this.varPowers.length ? this.varPowers[k] : this.constPowers[k - this.varPowers.length];
   }
}
