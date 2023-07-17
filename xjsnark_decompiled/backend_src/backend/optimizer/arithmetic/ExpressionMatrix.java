package backend.optimizer.arithmetic;

import backend.optimizer.arithmetic.poly.MultivariatePolynomial;
import backend.optimizer.arithmetic.poly.OptVariable;
import backend.optimizer.arithmetic.poly.Term;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ExpressionMatrix {
   private String[] vars;
   private String[] constStrs;
   private ArrayList<BigInteger> constants;
   private ArrayList<Cube> powers;
   private HashSet<Cube> cubeSet = new HashSet();
   private int numLiterals;
   private String label;
   private int index;
   private ExpressionMinimizer minimizer;
   private String[] allLiterals;

   public int hashCode() {
      int code = 0;

      Cube c;
      for(Iterator var3 = this.powers.iterator(); var3.hasNext(); code += c.hashCode()) {
         c = (Cube)var3.next();
      }

      return code;
   }

   public void setLabel(String label) {
      this.label = label;
   }

   public String getLabel() {
      return this.label;
   }

   public void extendTempVariables(String[] newVariables) {
      this.numLiterals += newVariables.length;
      this.vars = (String[])Util.concatenate(this.vars, newVariables);
      Iterator var3 = this.powers.iterator();

      while(var3.hasNext()) {
         Cube c = (Cube)var3.next();
         c.extendWithIntermediateVars(newVariables.length);
      }

   }

   public ExpressionMatrix(ExpressionMinimizer minimizer, ArrayList<BigInteger> constants, ArrayList<Cube> powers, String[] vars, String[] consts) {
      this.minimizer = minimizer;
      this.constants = constants;
      this.powers = powers;
      this.cubeSet.addAll(powers);
      this.numLiterals = ((Cube)powers.get(0)).getNumOfLiterals();
      this.vars = vars;
      this.constStrs = consts;
   }

   public ExpressionMatrix(ExpressionMatrix mat) {
      this.minimizer = mat.minimizer;
      this.constants = mat.constants;
      this.constStrs = mat.constStrs;
      this.vars = mat.vars;
      this.powers = mat.getPowersCopy();
      this.cubeSet.addAll(this.powers);
      this.numLiterals = ((Cube)this.powers.get(0)).getNumOfLiterals();
   }

   public ExpressionMatrix(ExpressionMinimizer minimizer, MultivariatePolynomial p, String[] vars, String label) {
      this.minimizer = minimizer;
      this.vars = vars;
      this.constants = new ArrayList();
      this.powers = new ArrayList();
      this.constructMatrix(p);
      this.label = label;
      this.cubeSet.addAll(this.powers);
   }

   private void constructMatrix(MultivariatePolynomial p) {
      HashMap<String, Integer> indexMap = new HashMap();
      int index = 0;
      String[] var7;
      int var6 = (var7 = this.vars).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         String v = var7[var5];
         indexMap.put(v, index++);
      }

      Iterator var11 = p.getTerms().iterator();

      while(var11.hasNext()) {
         Term t = (Term)var11.next();
         this.constants.add(t.getCoeff());
         int[] arr = new int[this.vars.length];

         OptVariable v;
         short power;
         for(Term.VarIterator it = t.getVarIterator(); it.hasNext(); arr[(Integer)indexMap.get(v.getLabel())] = power) {
            v = it.next();
            power = it.getExponent();
         }

         Cube c = new Cube(arr, t.getCoeff());
         c.genSerial(this.minimizer);
         this.powers.add(c);
      }

   }

   public void extend(HashMap<BigInteger, Integer> constantIndices, boolean state) {
      int index;
      Iterator var5;
      if (!state) {
         index = constantIndices.size();
         var5 = this.constants.iterator();

         while(var5.hasNext()) {
            BigInteger c = (BigInteger)var5.next();
            if (!c.abs().equals(BigInteger.ONE) && !constantIndices.containsKey(c)) {
               constantIndices.put(c, index);
               ++index;
            }
         }
      } else {
         int index = false;
         var5 = this.powers.iterator();

         while(var5.hasNext()) {
            Cube c = (Cube)var5.next();
            c.extendWithConstantPowers(constantIndices.size(), constantIndices);
         }

         int constSize = constantIndices.size();
         this.constStrs = new String[constSize];
         this.numLiterals += constSize;
         index = 0;

         BigInteger k;
         for(Iterator var6 = constantIndices.keySet().iterator(); var6.hasNext(); this.constStrs[index++] = k.toString()) {
            k = (BigInteger)var6.next();
         }
      }

   }

   public ArrayList<BigInteger> getConstants() {
      return this.constants;
   }

   public ArrayList<Cube> getPowers() {
      return this.powers;
   }

   public ArrayList<Cube> getPowersCopy() {
      ArrayList<Cube> result = new ArrayList();
      Iterator var3 = this.powers.iterator();

      while(var3.hasNext()) {
         Cube c = (Cube)var3.next();
         result.add(new Cube(c));
      }

      return result;
   }

   public boolean isDivisible(int[] cube) {
      return false;
   }

   public ExpressionMatrix divide(Cube c) {
      ArrayList<BigInteger> newConstants = new ArrayList();
      ArrayList<Cube> newPowers = new ArrayList();

      for(int i = 0; i < this.powers.size(); ++i) {
         Cube tmp = ((Cube)this.powers.get(i)).divide(c);
         if (tmp != null) {
            newPowers.add(tmp);
         }
      }

      return new ExpressionMatrix(this.minimizer, newConstants, newPowers, this.vars, this.constStrs);
   }

   public Cube largestDividingCube() {
      return Cube.largestDividingCube(this.powers);
   }

   public boolean hasCube(Cube c) {
      return this.cubeSet.contains(c);
   }

   public int serialOfCube(Cube c) {
      int idx = this.powers.indexOf(c);
      return ((Cube)this.powers.get(idx)).getSerial();
   }

   public int numCubes() {
      return this.powers.size();
   }

   public ExpressionMatrix divide(int varIndex) {
      ArrayList<BigInteger> newConstants = new ArrayList();
      ArrayList<Cube> newPowers = new ArrayList();

      for(int i = 0; i < this.powers.size(); ++i) {
         Cube c = ((Cube)this.powers.get(i)).divide(varIndex);
         if (c != null) {
            newPowers.add(c);
         }
      }

      return new ExpressionMatrix(this.minimizer, newConstants, newPowers, this.vars, this.constStrs);
   }

   public void print() {
      for(int i = 0; i < this.powers.size(); ++i) {
         System.out.println(((Cube)this.powers.get(i)).getSerial() + ">>" + this.powers.get(i));
      }

      System.out.println("===================");
   }

   public void apply(ArrayList<KCM.Replacement> replacements) {
      Iterator var3 = replacements.iterator();

      while(true) {
         KCM.Replacement replacement;
         boolean found;
         ArrayList toDelete;
         Iterator var8;
         do {
            if (!var3.hasNext()) {
               return;
            }

            replacement = (KCM.Replacement)var3.next();
            ArrayList<Integer> snList = replacement.getSNList();
            found = false;
            toDelete = new ArrayList();
            var8 = snList.iterator();

            while(var8.hasNext()) {
               int sn = (Integer)var8.next();
               Iterator var10 = this.powers.iterator();

               while(var10.hasNext()) {
                  Cube c = (Cube)var10.next();
                  if (c.getSerial() == sn) {
                     found = true;
                     toDelete.add(c);
                  }
               }
            }
         } while(!found);

         var8 = toDelete.iterator();

         Cube c;
         while(var8.hasNext()) {
            c = (Cube)var8.next();
            this.powers.remove(c);
         }

         c = new Cube(replacement.getNewCube());
         c.genSerial(this.minimizer);
         this.powers.add(c);
         this.cubeSet.add(c);
      }
   }

   public String[] getVars() {
      return this.vars;
   }

   public String[] getConstStrs() {
      return this.constStrs;
   }

   public String[] getLiterals() {
      if (this.allLiterals == null) {
         this.allLiterals = (String[])Util.concatenate(this.vars, this.constStrs);
      }

      return this.allLiterals;
   }

   public void setIndex(int i) {
      this.index = i;
   }

   public int getIndex() {
      return this.index;
   }
}
