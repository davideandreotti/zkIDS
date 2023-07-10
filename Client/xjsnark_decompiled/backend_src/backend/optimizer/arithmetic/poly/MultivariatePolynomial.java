package backend.optimizer.arithmetic.poly;

import backend.config.Config;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MultivariatePolynomial {
   ArrayList<Term> terms;

   public MultivariatePolynomial() {
      this.terms = new ArrayList(1);
   }

   public MultivariatePolynomial(Term t) {
      this();
      if (!t.coeff.equals(BigInteger.ZERO)) {
         this.terms.add(t);
      }

   }

   public MultivariatePolynomial(OptVariable v) {
      this();
      Term t = new Term(v);
      this.terms.add(t);
   }

   public MultivariatePolynomial(ArrayList<Term> terms) {
      terms.trimToSize();
      this.terms = terms;
   }

   public MultivariatePolynomial multiply(MultivariatePolynomial p) {
      ArrayList<Term> newTermList = new ArrayList(this.terms.size() + p.terms.size());
      Iterator var4 = this.terms.iterator();

      while(var4.hasNext()) {
         Term t = (Term)var4.next();
         Iterator var6 = p.terms.iterator();

         while(var6.hasNext()) {
            Term pt = (Term)var6.next();
            Term newTerm = t.multiply(pt);
            int idx = newTermList.indexOf(newTerm);
            if (idx == -1) {
               if (!newTerm.coeff.equals(BigInteger.ZERO)) {
                  newTermList.add(newTerm);
               }
            } else {
               Term exisitingTerm = (Term)newTermList.get(idx);
               exisitingTerm.coeff = exisitingTerm.coeff.add(newTerm.coeff).mod(Config.getFiniteFieldModulus());
            }
         }
      }

      return new MultivariatePolynomial(newTermList);
   }

   public MultivariatePolynomial multiplyInPlace(MultivariatePolynomial p) {
      ArrayList<Term> newTermList = new ArrayList(this.terms.size() + p.terms.size());
      Iterator var4 = this.terms.iterator();

      while(var4.hasNext()) {
         Term t = (Term)var4.next();
         Iterator var6 = p.terms.iterator();

         while(var6.hasNext()) {
            Term pt = (Term)var6.next();
            Term newTerm = t.multiply(pt);
            int idx = newTermList.indexOf(newTerm);
            if (idx == -1) {
               if (!newTerm.coeff.equals(BigInteger.ZERO)) {
                  newTermList.add(newTerm);
               }
            } else {
               Term exisitingTerm = (Term)newTermList.get(idx);
               exisitingTerm.coeff = exisitingTerm.coeff.add(newTerm.coeff).mod(Config.getFiniteFieldModulus());
            }
         }
      }

      this.terms = newTermList;
      this.terms.trimToSize();
      return this;
   }

   public MultivariatePolynomial multiplyConstant(BigInteger c) {
      if (c.equals(BigInteger.ZERO)) {
         return new MultivariatePolynomial();
      } else {
         ArrayList<Term> newTermList = new ArrayList(this.terms.size());
         Iterator var4 = this.terms.iterator();

         while(var4.hasNext()) {
            Term t = (Term)var4.next();
            Term newTerm = t.multiplyConstant(c);
            if (!newTerm.coeff.equals(BigInteger.ZERO)) {
               newTermList.add(newTerm);
            }
         }

         return new MultivariatePolynomial(newTermList);
      }
   }

   public MultivariatePolynomial multiplyConstantInPlace(BigInteger c) {
      if (c.equals(BigInteger.ZERO)) {
         this.terms.clear();
         return this;
      } else {
         ArrayList<Term> newTermList = new ArrayList(this.terms.size());
         Iterator var4 = this.terms.iterator();

         while(var4.hasNext()) {
            Term t = (Term)var4.next();
            Term newTerm = t.multiplyConstant(c);
            if (!newTerm.coeff.equals(BigInteger.ZERO)) {
               newTermList.add(newTerm);
            }
         }

         this.terms = newTermList;
         this.terms.trimToSize();
         return this;
      }
   }

   public MultivariatePolynomial add(MultivariatePolynomial p) {
      ArrayList<Term> newTermList = new ArrayList(this.terms.size());
      Iterator var4 = this.terms.iterator();

      Term pt;
      while(var4.hasNext()) {
         pt = (Term)var4.next();
         newTermList.add(pt);
      }

      var4 = p.terms.iterator();

      while(var4.hasNext()) {
         pt = (Term)var4.next();
         int idx = newTermList.indexOf(pt);
         if (idx == -1) {
            newTermList.add(pt);
         } else {
            Term exisitingTerm = (Term)newTermList.get(idx);
            newTermList.remove(exisitingTerm);
            Term newTerm = exisitingTerm.addToConstant(pt.coeff);
            if (!newTerm.getCoeff().equals(BigInteger.ZERO)) {
               newTermList.add(newTerm);
            }
         }
      }

      return new MultivariatePolynomial(newTermList);
   }

   public MultivariatePolynomial addInPlace(MultivariatePolynomial p) {
      ArrayList<Term> newTermList = new ArrayList(this.terms.size());
      Iterator var4 = this.terms.iterator();

      Term pt;
      while(var4.hasNext()) {
         pt = (Term)var4.next();
         newTermList.add(pt);
      }

      var4 = p.terms.iterator();

      while(var4.hasNext()) {
         pt = (Term)var4.next();
         int idx = newTermList.indexOf(pt);
         if (idx == -1) {
            newTermList.add(pt);
         } else {
            Term exisitingTerm = (Term)newTermList.get(idx);
            newTermList.remove(exisitingTerm);
            Term newTerm = exisitingTerm.addToConstant(pt.coeff);
            if (!newTerm.getCoeff().equals(BigInteger.ZERO)) {
               newTermList.add(newTerm);
            }
         }
      }

      this.terms = newTermList;
      this.terms.trimToSize();
      return this;
   }

   public String toString() {
      String result = "";
      int size = this.terms.size();
      Iterator var4 = this.terms.iterator();

      while(var4.hasNext()) {
         Term t = (Term)var4.next();
         result = result + t;
         --size;
         if (size > 0) {
            result = result + "+";
         }
      }

      return result;
   }

   public static void main(String[] args) {
      MultivariatePolynomial p1 = new MultivariatePolynomial(new OptVariable("v", 1));
      MultivariatePolynomial p2 = new MultivariatePolynomial(new OptVariable("v", 2));
      new MultivariatePolynomial(new OptVariable("v", 3));
      MultivariatePolynomial p4 = p1.multiply(p1).multiply(p2);
      MultivariatePolynomial p5 = p2.multiply(p1).multiply(p1);
      System.out.println(p4.add(p5));
   }

   public Collection<Term> getTerms() {
      return this.terms;
   }

   public int getCost() {
      int cost = 0;

      Term t;
      for(Iterator var3 = this.terms.iterator(); var3.hasNext(); cost += t.getCost()) {
         t = (Term)var3.next();
      }

      return cost;
   }

   public boolean isCostly() {
      if (this.terms.size() >= 5) {
         return true;
      } else {
         Iterator var2 = this.terms.iterator();

         Term t;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            t = (Term)var2.next();
         } while(t.getVarCount() < 4 && t.getMaxExponent() <= 16383);

         return true;
      }
   }
}
