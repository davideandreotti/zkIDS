package backend.optimizer.arithmetic;

import backend.optimizer.arithmetic.poly.MultivariatePolynomial;
import backend.optimizer.arithmetic.poly.OptVariable;
import backend.optimizer.arithmetic.poly.Term;
import backend.resource.ResourceBundle;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class CIM {
   ArrayList<ArrayList<Integer>> powersMatrix;
   ArrayList<Cube> rowCubes;
   ArrayList<Integer> expressionIndex;
   ArrayList<ExpressionMatrix> exps;
   ArrayList<String> vars;
   ArrayList<String> constants;
   LinkedHashMap<String, ArrayList<Integer>> expressionMap;
   private int numOriginalExps;
   private int numOriginalTerms;
   private ExpressionMinimizer minimizer;

   public CIM(ExpressionMinimizer minimizer, ArrayList<ExpressionMatrix> exps) {
      this.minimizer = minimizer;
      this.vars = new ArrayList();
      String[] variables = ((ExpressionMatrix)exps.get(0)).getVars();
      String[] var7 = variables;
      int var6 = variables.length;

      String constant;
      int var5;
      for(var5 = 0; var5 < var6; ++var5) {
         constant = var7[var5];
         this.vars.add(constant);
      }

      this.constants = new ArrayList();
      var6 = (var7 = ((ExpressionMatrix)exps.get(0)).getConstStrs()).length;

      for(var5 = 0; var5 < var6; ++var5) {
         constant = var7[var5];
         this.constants.add(constant);
      }

      this.powersMatrix = new ArrayList();
      this.exps = exps;
      this.numOriginalExps = exps.size();
      this.rowCubes = new ArrayList();
      this.expressionIndex = new ArrayList();
      this.expressionMap = new LinkedHashMap();

      for(int i = 0; i < this.numOriginalExps; ++i) {
         ExpressionMatrix exp = (ExpressionMatrix)exps.get(i);
         ArrayList<Integer> listOfTerms = new ArrayList();
         this.expressionMap.put(exp.getLabel(), listOfTerms);

         for(Iterator var8 = exp.getPowers().iterator(); var8.hasNext(); ++minimizer.cimExpressionCounter) {
            Cube c = (Cube)var8.next();
            this.rowCubes.add(c);
            ArrayList<Integer> row = new ArrayList();
            this.powersMatrix.add(row);
            int[] var13;
            int var12 = (var13 = c.getVarPowers()).length;

            for(int var11 = 0; var11 < var12; ++var11) {
               int p = var13[var11];
               row.add(p);
            }

            this.expressionIndex.add(i);
            listOfTerms.add(minimizer.cimExpressionCounter);
         }
      }

      this.numOriginalTerms = minimizer.cimExpressionCounter;
   }

   public HashMap<String, MultivariatePolynomial> extract() {
      ArrayList<ArrayList<Integer>> checkMatrix = new ArrayList();
      Iterator var3 = this.powersMatrix.iterator();

      while(var3.hasNext()) {
         ArrayList<Integer> a = (ArrayList)var3.next();
         checkMatrix.add(new ArrayList(a));
      }

      while(true) {
         int numRows = checkMatrix.size();
         int numCols = ((ArrayList)checkMatrix.get(0)).size();
         ArrayList<Rectangle> selectedRectangles = new ArrayList();

         ArrayList primeRectangles;
         ArrayList a;
         Iterator var8;
         ArrayList colAccum;
         int p;
         int i;
         int idx;
         Rectangle r;
         label226:
         while(true) {
            primeRectangles = new ArrayList();
            ArrayList<ArrayList<Integer>> roundCheckMatrix = new ArrayList();
            var8 = checkMatrix.iterator();

            while(var8.hasNext()) {
               a = (ArrayList)var8.next();
               roundCheckMatrix.add(new ArrayList(a));
            }

            Rectangle bestRectangle;
            for(i = 0; i < numRows; ++i) {
               for(idx = 0; idx < numCols; ++idx) {
                  if ((Integer)((ArrayList)roundCheckMatrix.get(i)).get(idx) > 0) {
                     bestRectangle = null;
                     int maxSavings = 0;
                     colAccum = new ArrayList();
                     new ArrayList();
                     colAccum.add(idx);
                     p = 0;
                     HashSet<Integer> set = new HashSet();

                     int k;
                     for(k = idx + 1; k < numCols; ++k) {
                        if ((Integer)((ArrayList)roundCheckMatrix.get(i)).get(k) > 0) {
                           ++p;
                           set.add(k);
                           if (p > 5) {
                              break;
                           }
                        }
                     }

                     ArrayList rowAccum;
                     int col;
                     if (p <= 5) {
                        Set<Set<Integer>> sets = Util.powerSet(set);
                        Iterator var17 = sets.iterator();

                        while(var17.hasNext()) {
                           Set<Integer> s = (Set)var17.next();
                           colAccum.clear();
                           colAccum.add(idx);
                           colAccum.addAll(s);
                           rowAccum = new ArrayList();
                           rowAccum.add(i);

                           for(int l = i + 1; l < numRows; ++l) {
                              boolean thingsOK = true;
                              Iterator var21 = colAccum.iterator();

                              while(var21.hasNext()) {
                                 int col = (Integer)var21.next();
                                 if ((Integer)((ArrayList)roundCheckMatrix.get(l)).get(col) == 0) {
                                    thingsOK = false;
                                 }
                              }

                              if (thingsOK) {
                                 rowAccum.add(l);
                              }
                           }

                           Rectangle tmp = new Rectangle(rowAccum, new ArrayList(colAccum));
                           col = tmp.getSavings(roundCheckMatrix);
                           if (col > maxSavings) {
                              maxSavings = col;
                              bestRectangle = tmp;
                           }
                        }
                     } else {
                        for(k = idx + 1; k < numCols; ++k) {
                           rowAccum = new ArrayList();
                           rowAccum.add(i);
                           if ((Integer)((ArrayList)roundCheckMatrix.get(i)).get(k) > 0) {
                              colAccum.add(k);

                              for(int l = i + 1; l < numRows; ++l) {
                                 boolean thingsOK = true;
                                 ArrayList<Integer> a3 = (ArrayList)roundCheckMatrix.get(l);
                                 Iterator var51 = colAccum.iterator();

                                 while(var51.hasNext()) {
                                    col = (Integer)var51.next();
                                    if ((Integer)a3.get(col) == 0) {
                                       thingsOK = false;
                                    }
                                 }

                                 if (thingsOK) {
                                    rowAccum.add(l);
                                 }
                              }

                              Rectangle tmp = new Rectangle(rowAccum, new ArrayList(colAccum));
                              int tmpSavings = tmp.getSavings(roundCheckMatrix);
                              if (tmpSavings > maxSavings) {
                                 maxSavings = tmpSavings;
                                 bestRectangle = tmp;
                              }
                           }
                        }
                     }

                     if (bestRectangle != null) {
                        primeRectangles.add(bestRectangle);
                     }
                  }
               }
            }

            i = 0;
            r = null;
            Iterator var34 = primeRectangles.iterator();

            while(var34.hasNext()) {
               bestRectangle = (Rectangle)var34.next();
               int s = bestRectangle.savings;
               if (s > i) {
                  i = s;
                  r = bestRectangle;
               }
            }

            if (i == 0) {
               break;
            }

            if (i == 1) {
               var34 = primeRectangles.iterator();

               while(true) {
                  if (!var34.hasNext()) {
                     break label226;
                  }

                  bestRectangle = (Rectangle)var34.next();
                  if (bestRectangle.getSavings(checkMatrix) == 1) {
                     selectedRectangles.add(bestRectangle);
                     bestRectangle.markArea(checkMatrix);
                  }
               }
            }

            selectedRectangles.add(r);
            r.markArea(checkMatrix);
         }

         int numOfOldVars = numCols;
         int numOfNewVars = selectedRectangles.size();
         ArrayList term;
         if (numOfNewVars == 0) {
            HashMap<String, MultivariatePolynomial> solutions = new HashMap();
            Iterator var26 = this.expressionMap.keySet().iterator();

            while(var26.hasNext()) {
               String label = (String)var26.next();
               primeRectangles = (ArrayList)this.expressionMap.get(label);
               MultivariatePolynomial mvp = new MultivariatePolynomial();

               for(i = 0; i < primeRectangles.size(); ++i) {
                  idx = (Integer)primeRectangles.get(i);
                  BigInteger constValue = BigInteger.ONE;
                  int i;
                  if (idx < this.numOriginalTerms) {
                     Cube c = (Cube)this.rowCubes.get(idx);
                     int[] powers = c.getConstPowers();

                     for(i = 0; i < powers.length; ++i) {
                        p = powers[i];
                        if (p > 0) {
                           constValue = ResourceBundle.getInstance().getBigInteger(new BigInteger((String)this.constants.get(i)));
                           break;
                        }
                     }
                  }

                  term = (ArrayList)checkMatrix.get(idx);
                  Term t = new Term(constValue);

                  for(i = 0; i < term.size(); ++i) {
                     p = (Integer)term.get(i);
                     if (p > 0) {
                        t = t.multiply(new Term(new OptVariable((String)this.vars.get(i)), (short)p));
                     }
                  }

                  mvp = mvp.addInPlace(new MultivariatePolynomial(t));
               }

               solutions.put(label, mvp);
            }

            return solutions;
         }

         for(i = 0; i < numOfNewVars; ++i) {
            this.vars.add("tmp_" + this.minimizer.cimIntermediateVarCounter++);
         }

         var8 = checkMatrix.iterator();

         while(var8.hasNext()) {
            a = (ArrayList)var8.next();

            for(int j = 0; j < numOfNewVars; ++j) {
               a.add(0);
            }
         }

         i = 0;

         for(Iterator var36 = selectedRectangles.iterator(); var36.hasNext(); ++this.minimizer.cimExpressionCounter) {
            r = (Rectangle)var36.next();
            term = r.getNewRecord(i, numOfOldVars, numOfNewVars);
            this.expressionIndex.add(this.minimizer.cimExpressionCounter);
            colAccum = new ArrayList();
            colAccum.add(this.minimizer.cimExpressionCounter);
            this.expressionMap.put((String)this.vars.get(numOfOldVars + i), colAccum);
            r.applyReplacement(numOfOldVars, i, checkMatrix);
            checkMatrix.add(term);
            ++i;
         }
      }
   }

   public void print() {
   }

   private class Rectangle {
      private ArrayList<Integer> rowList = new ArrayList();
      private ArrayList<Integer> colList = new ArrayList();
      private int[] newCube;
      int savings = -1;
      int[] minPowers;

      public Rectangle(ArrayList<Integer> rowList, ArrayList<Integer> colList) {
         this.rowList = rowList;
         this.colList = colList;
      }

      public ArrayList<Integer> getNewRecord(int index, int numOfOldVars, int numOfNewVars) {
         ArrayList<Integer> newRecord = new ArrayList();

         int i;
         for(i = 0; i < numOfOldVars + numOfNewVars; ++i) {
            newRecord.add(0);
         }

         i = 0;

         for(Iterator var7 = this.colList.iterator(); var7.hasNext(); ++i) {
            int c = (Integer)var7.next();
            newRecord.set(c, this.minPowers[i]);
         }

         return newRecord;
      }

      public void applyReplacement(int numOfOldVars, int index, ArrayList<ArrayList<Integer>> extendedCheckMatrix) {
         Iterator var5 = this.rowList.iterator();

         while(var5.hasNext()) {
            int r = (Integer)var5.next();
            ((ArrayList)extendedCheckMatrix.get(r)).set(numOfOldVars + index, 1);
         }

      }

      public void markArea(ArrayList<ArrayList<Integer>> checkMatrix) {
         int numRows = this.rowList.size();
         int numCols = this.colList.size();

         for(int i = 0; i < numRows; ++i) {
            ArrayList<Integer> a = (ArrayList)checkMatrix.get((Integer)this.rowList.get(i));

            for(int j = 0; j < numCols; ++j) {
               int v = (Integer)a.get((Integer)this.colList.get(j));
               a.set((Integer)this.colList.get(j), v - this.minPowers[j]);
            }
         }

      }

      public int getSavings(ArrayList<ArrayList<Integer>> roundCheckMatrix) {
         int s = 0;
         int numRows = this.rowList.size();
         int numCols = this.colList.size();
         this.minPowers = new int[numCols];
         Arrays.fill(this.minPowers, Integer.MAX_VALUE);

         int p;
         int j;
         for(p = 0; p < numRows; ++p) {
            ArrayList<Integer> a = (ArrayList)roundCheckMatrix.get((Integer)this.rowList.get(p));

            for(j = 0; j < numCols; ++j) {
               int v = (Integer)a.get((Integer)this.colList.get(j));
               if (v < this.minPowers[j]) {
                  this.minPowers[j] = v;
               }
            }
         }

         int[] var10;
         j = (var10 = this.minPowers).length;

         for(int var9 = 0; var9 < j; ++var9) {
            p = var10[var9];
            s += p;
         }

         this.savings = (s - 1) * (numRows - 1);
         return this.savings;
      }

      public void print() {
         Iterator var2 = this.rowList.iterator();

         while(var2.hasNext()) {
            int r = (Integer)var2.next();
            Iterator var4 = this.colList.iterator();

            while(var4.hasNext()) {
               int c = (Integer)var4.next();
               System.out.print("(" + r + "," + c + ") ");
            }
         }

         System.out.println();
      }
   }
}
