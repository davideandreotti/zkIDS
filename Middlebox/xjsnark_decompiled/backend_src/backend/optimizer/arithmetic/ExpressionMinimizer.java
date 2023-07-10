package backend.optimizer.arithmetic;

import backend.optimizer.arithmetic.poly.MultivariatePolynomial;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

public class ExpressionMinimizer {
   private String[] inVarsStrings;
   private String[] outVarsStrings;
   private ArrayList<MultivariatePolynomial> list;
   private Cube oneCube;
   int kcmIntermediateVarCounter = 0;
   int cimIntermediateVarCounter = 0;
   int cimExpressionCounter = 0;
   int cubeGlobalCount = 1;
   private HashMap<String, MultivariatePolynomial> solution;

   public ExpressionMinimizer(String[] inVarsStrings, String[] outVarsStrings, ArrayList<MultivariatePolynomial> list) {
      this.inVarsStrings = inVarsStrings;
      this.outVarsStrings = outVarsStrings;
      this.list = list;
   }

   public HashMap<String, MultivariatePolynomial> run(int level) {
      ExpressionMinimizer m = new ExpressionMinimizer(this.inVarsStrings, this.outVarsStrings, this.list, level);
      return m.getSolution();
   }

   public ExpressionMinimizer(String[] inVarsStrings, String[] outVarsStrings, ArrayList<MultivariatePolynomial> list, int level) {
      ArrayList<ExpressionMatrix> polys = new ArrayList();

      ExpressionMatrix exp;
      for(int i = 0; i < list.size(); ++i) {
         exp = new ExpressionMatrix(this, (MultivariatePolynomial)list.get(i), inVarsStrings, outVarsStrings[i]);
         polys.add(exp);
         exp.setIndex(i);
      }

      HashMap<BigInteger, Integer> constantIndices = new LinkedHashMap();
      Iterator var8 = polys.iterator();

      while(var8.hasNext()) {
         exp = (ExpressionMatrix)var8.next();
         exp.extend(constantIndices, false);
      }

      var8 = polys.iterator();

      while(var8.hasNext()) {
         exp = (ExpressionMatrix)var8.next();
         exp.extend(constantIndices, true);
      }

      int i = 0;
      boolean tooLarge = false;

      while(i < 10 && !Thread.currentThread().isInterrupted()) {
         ++i;
         this.oneCube = Cube.getOneCube(((ExpressionMatrix)polys.get(0)).getVars().length, ((ExpressionMatrix)polys.get(0)).getConstStrs().length);
         LinkedHashMap<KernelCoKernelPair, ArrayList<ExpressionMatrix>> kernelCoKernelMap = this.findKernels(polys, ((ExpressionMatrix)polys.get(0)).getLiterals());
         HashSet<Cube> distinctCoKernelSet = new LinkedHashSet();
         HashSet<Cube> distinctKernelCubeSet = new LinkedHashSet();
         Iterator var13 = kernelCoKernelMap.keySet().iterator();

         while(var13.hasNext()) {
            KernelCoKernelPair pair = (KernelCoKernelPair)var13.next();
            distinctCoKernelSet.add(new Cube(pair.getCoKernel()));
            Iterator var15 = pair.getKernel().getPowers().iterator();

            while(var15.hasNext()) {
               Cube c = (Cube)var15.next();
               if (!c.equals(this.oneCube)) {
                  distinctKernelCubeSet.add(new Cube(c));
               }
            }
         }

         distinctKernelCubeSet.add(new Cube(this.oneCube));
         if ((long)distinctCoKernelSet.size() * (long)distinctKernelCubeSet.size() > 1000000L) {
            tooLarge = true;
            break;
         }

         KCM kcm = new KCM(this, kernelCoKernelMap, distinctCoKernelSet, distinctKernelCubeSet, polys);
         ++i;
         boolean updated = kcm.extract();
         if (!updated) {
            break;
         }
      }

      if (!Thread.currentThread().isInterrupted() && !tooLarge) {
         CIM cim = new CIM(this, polys);
         cim.print();
         this.solution = cim.extract();
      }

   }

   private LinkedHashMap<KernelCoKernelPair, ArrayList<ExpressionMatrix>> findKernels(ArrayList<ExpressionMatrix> polys, String[] vars) {
      LinkedHashSet<KernelCoKernelPair> set = new LinkedHashSet();
      LinkedHashMap<KernelCoKernelPair, ArrayList<ExpressionMatrix>> map = new LinkedHashMap();
      Iterator var6 = polys.iterator();

      ExpressionMatrix exp;
      while(var6.hasNext()) {
         exp = (ExpressionMatrix)var6.next();
         HashSet<KernelCoKernelPair> s = this.findKernelsHelpers(0, exp, (Cube)null);
         Iterator var9 = s.iterator();

         while(var9.hasNext()) {
            KernelCoKernelPair p = (KernelCoKernelPair)var9.next();
            if (map.containsKey(p)) {
               ((ArrayList)map.get(p)).add(exp);
            } else {
               ArrayList<ExpressionMatrix> l = new ArrayList();
               l.add(exp);
               map.put(p, l);
            }
         }

         set.addAll(this.findKernelsHelpers(0, exp, (Cube)null));
      }

      var6 = polys.iterator();

      while(var6.hasNext()) {
         exp = (ExpressionMatrix)var6.next();
         KernelCoKernelPair p = new KernelCoKernelPair(new ExpressionMatrix(exp), new Cube(this.oneCube));
         if (map.containsKey(p)) {
            ((ArrayList)map.get(p)).add(exp);
         } else {
            ArrayList<ExpressionMatrix> l = new ArrayList();
            l.add(exp);
            map.put(p, l);
         }
      }

      return map;
   }

   private HashSet<KernelCoKernelPair> findKernelsHelpers(int idx, ExpressionMatrix exp, Cube cube) {
      String[] literals = exp.getLiterals();
      ArrayList<Cube> terms = exp.getPowers();
      HashSet<KernelCoKernelPair> out = new HashSet();

      for(int i = idx; i < literals.length; ++i) {
         int count = 0;
         Iterator var10 = terms.iterator();

         while(var10.hasNext()) {
            Cube c = (Cube)var10.next();
            if (c.get(i) > 0) {
               ++count;
               if (count == 2) {
                  break;
               }
            }
         }

         if (count > 1) {
            ExpressionMatrix F_t = exp.divide(i);
            Cube largestDividingCube = F_t.largestDividingCube();
            boolean visited = false;

            for(int k = 0; k < i; ++k) {
               if (largestDividingCube.getPower(k) > 0) {
                  visited = true;
                  break;
               }
            }

            if (!visited) {
               ExpressionMatrix F_l = F_t.divide(largestDividingCube);
               Cube merged = this.merge(cube, largestDividingCube, i);
               Cube c = new Cube(merged);
               out.add(new KernelCoKernelPair(F_l, c));
               out.addAll(this.findKernelsHelpers(i, F_l, merged));
            }
         }
      }

      return out;
   }

   private Cube merge(Cube cube, Cube largestDividingCube, int varIdx) {
      int[] newCubeVars = new int[largestDividingCube.getNumOfVars()];
      int[] newCubeConsts = new int[largestDividingCube.getNumOfConstants()];
      if (varIdx < newCubeVars.length) {
         int var10002 = newCubeVars[varIdx]++;
      } else {
         ++newCubeConsts[varIdx - newCubeVars.length];
      }

      int i;
      for(i = 0; i < newCubeVars.length; ++i) {
         if (cube != null) {
            newCubeVars[i] += cube.get(i);
         }

         newCubeVars[i] += largestDividingCube.get(i);
      }

      for(i = 0; i < newCubeConsts.length; ++i) {
         if (cube != null) {
            newCubeConsts[i] += cube.get(i + newCubeVars.length);
         }

         newCubeConsts[i] += largestDividingCube.get(i + newCubeVars.length);
      }

      return new Cube(newCubeVars, newCubeConsts);
   }

   private int[] merge(int[] cube, int[] largestDividingCube, int varIdx) {
      int[] newCube = new int[largestDividingCube.length];
      newCube[varIdx] = 1;

      for(int i = 0; i < largestDividingCube.length; ++i) {
         if (cube != null) {
            newCube[i] += cube[i];
         }

         newCube[i] += largestDividingCube[i];
      }

      return newCube;
   }

   public HashMap<String, MultivariatePolynomial> getSolution() {
      return this.solution;
   }
}
