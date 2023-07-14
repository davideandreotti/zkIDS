package backend.optimizer.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class KCM {
   private int[][] matrix;
   private ArrayList<SerialRecord>[][] serialNums;
   private LinkedHashMap<Cube, Integer> coKernelIndexMap;
   private LinkedHashMap<Cube, Integer> kernelCubeIndexMap;
   private int[] rowCosts;
   private int[] colCosts;
   private boolean[] rowConstFlags;
   private boolean[] colConstFlags;
   private Cube[] rowCubes;
   private Cube[] colCubes;
   private int numRows;
   private int numCols;
   private ArrayList<ExpressionMatrix> exps;
   private ExpressionMinimizer minimizer;

   public KCM(ExpressionMinimizer expressionMinimizer, LinkedHashMap<KernelCoKernelPair, ArrayList<ExpressionMatrix>> kernelCoKernelMap, HashSet<Cube> distinctCoKernelSet, HashSet<Cube> distinctKernelCubeSet, ArrayList<ExpressionMatrix> exps) {
      this.exps = exps;
      this.minimizer = expressionMinimizer;
      this.matrix = new int[distinctCoKernelSet.size()][distinctKernelCubeSet.size()];
      this.serialNums = new ArrayList[distinctCoKernelSet.size()][distinctKernelCubeSet.size()];
      this.coKernelIndexMap = new LinkedHashMap();
      this.kernelCubeIndexMap = new LinkedHashMap();
      int idx = 0;
      this.rowCosts = new int[distinctCoKernelSet.size()];
      this.colCosts = new int[distinctKernelCubeSet.size()];
      this.rowCubes = new Cube[distinctCoKernelSet.size()];
      this.colCubes = new Cube[distinctKernelCubeSet.size()];
      this.colConstFlags = new boolean[this.colCosts.length];
      this.rowConstFlags = new boolean[this.rowCosts.length];
      this.numRows = this.rowCubes.length;
      this.numCols = this.colCubes.length;

      Cube c;
      Iterator var8;
      for(var8 = distinctCoKernelSet.iterator(); var8.hasNext(); ++idx) {
         c = (Cube)var8.next();
         this.coKernelIndexMap.put(c, idx);
         this.rowCubes[idx] = c;
         this.rowCosts[idx] = c.getCost();
         this.rowConstFlags[idx] = c.isConstant();
      }

      idx = 0;

      for(var8 = distinctKernelCubeSet.iterator(); var8.hasNext(); ++idx) {
         c = (Cube)var8.next();
         this.kernelCubeIndexMap.put(c, idx);
         this.colCubes[idx] = c;
         this.colCosts[idx] = c.getCost();
         this.colConstFlags[idx] = c.isConstant();
      }

      int c = false;
      int r = false;
      Iterator var10 = kernelCoKernelMap.keySet().iterator();

      while(var10.hasNext()) {
         KernelCoKernelPair pair = (KernelCoKernelPair)var10.next();
         ArrayList<ExpressionMatrix> affectExpressions = (ArrayList)kernelCoKernelMap.get(pair);
         Cube coKernel = pair.getCoKernel();
         int r = (Integer)this.coKernelIndexMap.get(coKernel);
         Iterator var14 = pair.getKernel().getPowers().iterator();

         while(var14.hasNext()) {
            Cube cube = (Cube)var14.next();
            int c = (Integer)this.kernelCubeIndexMap.get(cube);
            Cube c3 = Cube.add(coKernel, cube);
            Iterator var17 = affectExpressions.iterator();

            while(var17.hasNext()) {
               ExpressionMatrix exp = (ExpressionMatrix)var17.next();
               if (exp.hasCube(c3)) {
                  int var10002;
                  if (this.matrix[r][c] > 0) {
                     this.serialNums[r][c].add(new SerialRecord(exp.getIndex(), exp.serialOfCube(c3)));
                     var10002 = this.matrix[r][c]++;
                  } else {
                     var10002 = this.matrix[r][c]++;
                     this.serialNums[r][c] = new ArrayList();
                     this.serialNums[r][c].add(new SerialRecord(exp.getIndex(), exp.serialOfCube(c3)));
                  }
               }
            }
         }
      }

   }

   public boolean extract() {
      int[][] checkMatrix = new int[this.numRows][this.numCols];

      for(int i = 0; i < this.numRows; ++i) {
         System.arraycopy(this.matrix[i], 0, checkMatrix[i], 0, this.numCols);
      }

      ArrayList<Rectangle> selectedRectangles = new ArrayList();
      HashSet<Integer> coveredSerialNumbers = new HashSet();

      while(!Thread.currentThread().isInterrupted()) {
         ArrayList<Rectangle> primeRectangles = new ArrayList();
         HashSet<Integer> roundCoveredSerialNumbers = new HashSet();
         int[][] roundCheckMatrix = new int[this.numRows][this.numCols];

         int i;
         for(i = 0; i < this.numRows; ++i) {
            System.arraycopy(checkMatrix[i], 0, roundCheckMatrix[i], 0, this.numCols);
         }

         Rectangle bestRectangle;
         int maxSavings;
         ArrayList replacements;
         for(i = 0; i < this.numRows - 1; ++i) {
            for(int j = 0; j < this.numCols; ++j) {
               if (Thread.currentThread().isInterrupted()) {
                  return false;
               }

               if (checkMatrix[i][j] > 0) {
                  bestRectangle = null;
                  maxSavings = 0;
                  ArrayList<Integer> colAccum = new ArrayList();
                  new ArrayList();
                  colAccum.add(j);
                  int count = 0;
                  HashSet<Integer> set = new HashSet();

                  int k;
                  for(k = j + 1; k < this.numCols; ++k) {
                     if (checkMatrix[i][k] > 0) {
                        ++count;
                        set.add(k);
                        if (count > 5) {
                           break;
                        }
                     }
                  }

                  int l;
                  if (count <= 5) {
                     Set<Set<Integer>> sets = Util.powerSet(set);
                     Iterator var17 = sets.iterator();

                     label207:
                     while(true) {
                        do {
                           if (!var17.hasNext()) {
                              break label207;
                           }

                           Set<Integer> s = (Set)var17.next();
                           colAccum.clear();
                           colAccum.add(j);
                           colAccum.addAll(s);
                           replacements = new ArrayList();
                           replacements.add(i);

                           for(l = i + 1; l < this.numRows; ++l) {
                              boolean thingsOK = true;
                              Iterator var21 = colAccum.iterator();

                              while(var21.hasNext()) {
                                 int col = (Integer)var21.next();
                                 if (checkMatrix[l][col] == 0) {
                                    thingsOK = false;
                                 }
                              }

                              if (thingsOK) {
                                 replacements.add(l);
                              }
                           }
                        } while(replacements.size() == 1 && colAccum.size() == 1);

                        Rectangle tmp = new Rectangle(replacements, new ArrayList(colAccum));
                        int tmpSavings = tmp.getSavings(coveredSerialNumbers, roundCoveredSerialNumbers);
                        if (tmpSavings > maxSavings) {
                           maxSavings = tmpSavings;
                           bestRectangle = tmp;
                        }
                     }
                  } else {
                     for(k = j + 1; k < this.numCols; ++k) {
                        replacements = new ArrayList();
                        replacements.add(i);
                        if (checkMatrix[i][k] > 0) {
                           colAccum.add(k);

                           for(int l = i + 1; l < this.numRows; ++l) {
                              boolean thingsOK = true;
                              Iterator var47 = colAccum.iterator();

                              while(var47.hasNext()) {
                                 l = (Integer)var47.next();
                                 if (checkMatrix[l][l] == 0) {
                                    thingsOK = false;
                                 }
                              }

                              if (thingsOK) {
                                 replacements.add(l);
                              }
                           }

                           Rectangle tmp = new Rectangle(replacements, new ArrayList(colAccum));
                           int tmpSavings = tmp.getSavings(coveredSerialNumbers, roundCoveredSerialNumbers);
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
         Rectangle best = null;
         Iterator var31 = primeRectangles.iterator();

         int s;
         while(var31.hasNext()) {
            bestRectangle = (Rectangle)var31.next();
            s = bestRectangle.savings;
            if (s > i) {
               i = s;
               best = bestRectangle;
            }
         }

         if (i == 0) {
            int prevVarsNum = ((Cube)((ExpressionMatrix)this.exps.get(0)).getPowers().get(0)).getNumOfVars();
            int numOfNewVariables = selectedRectangles.size();
            if (numOfNewVariables == 0) {
               return false;
            }

            String[] newVariables = new String[numOfNewVariables];

            for(i = 0; i < newVariables.length; ++i) {
               newVariables[i] = "d_" + this.minimizer.kcmIntermediateVarCounter++;
            }

            String[] allVars = (String[])Util.concatenate(((ExpressionMatrix)this.exps.get(0)).getVars(), newVariables);
            String[] allConsts = ((ExpressionMatrix)this.exps.get(0)).getConstStrs();
            var31 = this.exps.iterator();

            while(var31.hasNext()) {
               ExpressionMatrix exp = (ExpressionMatrix)var31.next();
               exp.extendTempVariables(newVariables);
            }

            Cube[] var35;
            s = (var35 = this.colCubes).length;

            Cube c;
            for(maxSavings = 0; maxSavings < s; ++maxSavings) {
               c = var35[maxSavings];
               c.extendWithIntermediateVars(numOfNewVariables);
            }

            s = (var35 = this.rowCubes).length;

            for(maxSavings = 0; maxSavings < s; ++maxSavings) {
               c = var35[maxSavings];
               c.extendWithIntermediateVars(numOfNewVariables);
            }

            int index = prevVarsNum;

            Iterator var34;
            Rectangle r;
            for(var34 = selectedRectangles.iterator(); var34.hasNext(); ++index) {
               r = (Rectangle)var34.next();
               replacements = r.getReplacements(index);
               Iterator var39 = this.exps.iterator();

               while(var39.hasNext()) {
                  ExpressionMatrix exp = (ExpressionMatrix)var39.next();
                  exp.apply(replacements);
               }
            }

            index = 0;

            for(var34 = selectedRectangles.iterator(); var34.hasNext(); ++index) {
               r = (Rectangle)var34.next();
               ExpressionMatrix expMat = r.getNewExpression(allVars, allConsts);
               expMat.setLabel(newVariables[index]);
               this.exps.add(expMat);
               expMat.setIndex(this.exps.size() - 1);
            }

            return true;
         }

         selectedRectangles.add(best);
         coveredSerialNumbers.addAll(best.coveredSerialNumbers);
         best.markArea(checkMatrix);
      }

      return false;
   }

   public void print() {
      System.out.print("\t\t");

      int i;
      for(i = 0; i < this.kernelCubeIndexMap.size(); ++i) {
         System.out.print("(" + i + ")\t");
      }

      System.out.println();
      System.out.print("\t\t");
      Iterator var2 = this.kernelCubeIndexMap.keySet().iterator();

      while(var2.hasNext()) {
         Cube c = (Cube)var2.next();
         System.out.print(c + "\t");
      }

      System.out.println();
      i = 0;

      for(Iterator var3 = this.coKernelIndexMap.keySet().iterator(); var3.hasNext(); ++i) {
         Cube c = (Cube)var3.next();
         System.out.print("(" + i + ")\t");
         System.out.print(c + "\t");

         for(int j = 0; j < this.kernelCubeIndexMap.size(); ++j) {
            this.print(this.serialNums[i][j]);
            System.out.print(",\t");
         }

         System.out.println();
      }

   }

   private void print(ArrayList<SerialRecord> arr) {
      if (arr == null) {
         System.out.print("null");
      } else {
         Iterator var3 = arr.iterator();

         while(var3.hasNext()) {
            SerialRecord a = (SerialRecord)var3.next();
            System.out.print(a + ",");
         }

      }
   }

   private class Rectangle {
      private ArrayList<Integer> rowList = new ArrayList();
      private ArrayList<Integer> colList = new ArrayList();
      private ExpressionMatrix newExpression;
      private ArrayList<ArrayList<Integer>> serialNumberPerRow;
      ArrayList<Integer>[] expIdsPerRow;
      ArrayList<Integer> coveredSerialNumbers;
      int[][] coveredCounts;
      int savings = -1;

      public ExpressionMatrix getNewExpression(String[] allVars, String[] allConsts) {
         ArrayList<Cube> newCubes = new ArrayList();
         Iterator var5 = this.colList.iterator();

         while(var5.hasNext()) {
            int col = (Integer)var5.next();
            Cube c = new Cube(KCM.this.colCubes[col]);
            c.genSerial(KCM.this.minimizer);
            newCubes.add(c);
         }

         return new ExpressionMatrix(KCM.this.minimizer, new ArrayList(), newCubes, allVars, allConsts);
      }

      public Rectangle(ArrayList<Integer> rowList, ArrayList<Integer> colList) {
         this.rowList = rowList;
         this.colList = colList;
      }

      public int getSavings(HashSet<Integer> globalCoveredSerialNumbers, HashSet<Integer> roundCoveredSerialNumbers) {
         int[] rowCount = new int[this.rowList.size()];
         int[] colCount = new int[this.colList.size()];
         int s = 0;
         this.serialNumberPerRow = new ArrayList();
         this.expIdsPerRow = new ArrayList[this.rowList.size()];
         this.coveredSerialNumbers = new ArrayList();
         this.coveredCounts = new int[this.rowList.size()][this.colList.size()];
         int numRows = this.rowList.size();
         int numCols = this.colList.size();

         int i;
         boolean validRow;
         label132:
         for(i = 0; i < numRows; ++i) {
            this.serialNumberPerRow.add(new ArrayList());
            this.expIdsPerRow[i] = new ArrayList();
            validRow = false;
            Iterator var11 = KCM.this.serialNums[(Integer)this.rowList.get(i)][(Integer)this.colList.get(0)].iterator();

            while(true) {
               int expId;
               boolean validExpId;
               int[] tmpCounts;
               ArrayList tmpCoveredSerialNumbers;
               int j;
               do {
                  if (!var11.hasNext()) {
                     if (i == 0 && !validRow) {
                        return 0;
                     }
                     continue label132;
                  }

                  SerialRecord sr = (SerialRecord)var11.next();
                  expId = sr.expIdx;
                  validExpId = true;
                  tmpCounts = new int[numCols];
                  tmpCoveredSerialNumbers = new ArrayList();
                  if (!globalCoveredSerialNumbers.contains(sr.serialNum) && !roundCoveredSerialNumbers.contains(sr.serialNum)) {
                     tmpCoveredSerialNumbers.add(sr.serialNum);
                     int var10002 = tmpCounts[0]++;

                     for(j = 1; j < numCols; ++j) {
                        boolean found = false;
                        Iterator var19 = KCM.this.serialNums[(Integer)this.rowList.get(i)][(Integer)this.colList.get(j)].iterator();

                        while(var19.hasNext()) {
                           SerialRecord sr2 = (SerialRecord)var19.next();
                           if (expId == sr2.expIdx && !globalCoveredSerialNumbers.contains(sr2.serialNum) && !roundCoveredSerialNumbers.contains(sr2.serialNum)) {
                              found = true;
                              var10002 = tmpCounts[j]++;
                              tmpCoveredSerialNumbers.add(sr2.serialNum);
                              break;
                           }
                        }

                        if (!found) {
                           validExpId = false;
                           break;
                        }
                     }
                  } else {
                     validExpId = false;
                  }
               } while(!validExpId);

               validRow = true;
               this.expIdsPerRow[i].add(expId);
               this.coveredSerialNumbers.addAll(tmpCoveredSerialNumbers);
               ((ArrayList)this.serialNumberPerRow.get(i)).addAll(tmpCoveredSerialNumbers);

               for(j = 0; j < numCols; ++j) {
                  int[] var10000 = this.coveredCounts[i];
                  var10000[j] += tmpCounts[j];
               }
            }
         }

         i = 0;
         validRow = false;
         int numFullCells = 0;
         Iterator var23 = this.rowList.iterator();

         int colIndex;
         int c;
         int v;
         int r;
         while(var23.hasNext()) {
            c = (Integer)var23.next();
            v = 0;
            r = 0;
            colIndex = 0;

            for(Iterator var28 = this.colList.iterator(); var28.hasNext(); ++colIndex) {
               int cx = (Integer)var28.next();
               v += this.coveredCounts[i][colIndex];
               numFullCells += this.coveredCounts[i][colIndex] > 0 ? 1 : 0;
               if (!KCM.this.colConstFlags[cx]) {
                  r += this.coveredCounts[i][colIndex];
               }
            }

            if (v != 0 && !KCM.this.rowConstFlags[c]) {
               s += r - 1;
            }

            rowCount[i++] = v;
            if (v != 0) {
               s += v * KCM.this.rowCosts[c] - KCM.this.rowCosts[c];
            }
         }

         if (numFullCells <= 1) {
            return 0;
         } else {
            colIndex = 0;
            var23 = this.colList.iterator();

            while(var23.hasNext()) {
               c = (Integer)var23.next();
               v = 0;
               i = 0;

               for(Iterator var27 = this.rowList.iterator(); var27.hasNext(); ++i) {
                  r = (Integer)var27.next();
                  v += this.coveredCounts[i][colIndex];
               }

               colCount[colIndex++] = v;
               if (v != 0) {
                  s += v * KCM.this.colCosts[c] - KCM.this.colCosts[c];
               }
            }

            if (numRows == 1 && numCols == 1) {
            }

            this.savings = s;
            return s;
         }
      }

      public ArrayList<Replacement> getReplacements(int index) {
         ArrayList<Replacement> list = new ArrayList();
         int i = 0;

         for(Iterator var5 = this.rowList.iterator(); var5.hasNext(); ++i) {
            int r = (Integer)var5.next();
            Replacement rep = KCM.this.new Replacement();
            int[] powers = Arrays.copyOf(KCM.this.rowCubes[r].getVarPowers(), KCM.this.rowCubes[r].getVarPowers().length);
            powers[index] = 1;
            rep.newCube = new Cube(powers, KCM.this.rowCubes[r].getConstPowers());
            rep.newCube.genSerial(KCM.this.minimizer);
            rep.snList = (ArrayList)this.serialNumberPerRow.get(i);
            list.add(rep);
         }

         return list;
      }

      public void markArea(int[][] checkMatrix) {
         int i = 0;

         for(Iterator var4 = this.rowList.iterator(); var4.hasNext(); ++i) {
            int r = (Integer)var4.next();
            int j = 0;

            for(Iterator var7 = this.colList.iterator(); var7.hasNext(); ++j) {
               int c = (Integer)var7.next();
               checkMatrix[r][c] -= this.coveredCounts[i][j];
            }
         }

      }

      public void print() {
         Iterator var2 = this.rowList.iterator();

         int sn;
         int snx;
         Iterator var4;
         while(var2.hasNext()) {
            sn = (Integer)var2.next();
            var4 = this.colList.iterator();

            while(var4.hasNext()) {
               snx = (Integer)var4.next();
               System.out.print("(" + sn + "," + snx + ") ");
            }
         }

         System.out.println();
         System.out.println("covered terms::");
         if (this.serialNumberPerRow != null) {
            var2 = this.serialNumberPerRow.iterator();

            while(var2.hasNext()) {
               ArrayList<Integer> lst = (ArrayList)var2.next();
               var4 = lst.iterator();

               while(var4.hasNext()) {
                  snx = (Integer)var4.next();
                  System.out.print(snx + ",");
               }
            }
         }

         System.out.println();
         if (this.coveredSerialNumbers != null) {
            var2 = this.coveredSerialNumbers.iterator();

            while(var2.hasNext()) {
               sn = (Integer)var2.next();
               System.out.print(sn + ",");
            }
         }

         System.out.println();
      }
   }

   public class Replacement {
      private ArrayList<Integer> snList;
      private Cube newCube;

      public Cube getNewCube() {
         return this.newCube;
      }

      public ArrayList<Integer> getSNList() {
         return this.snList;
      }
   }

   public class SerialRecord {
      int serialNum;
      int expIdx;

      SerialRecord(int expIdx, int serialNum) {
         this.expIdx = expIdx;
         this.serialNum = serialNum;
      }

      public String toString() {
         return this.expIdx + ":" + this.serialNum;
      }
   }
}
