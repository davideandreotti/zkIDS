package backend.auxTypes;

import backend.config.Config;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import util.Util;

public class StaticMemorySolver {
   public static void preprocess(BigInteger[] vals, int outputBitwidth, ArrayList<UnsignedInteger> indexes, SmartMemory.MemoryState state) {
      boolean inputBitsSplit = false;
      if (indexes != null) {
         boolean useBits;
         int n;
         if (vals.length <= 256) {
            useBits = true;
            n = 0;
            Iterator var8 = indexes.iterator();

            while(var8.hasNext()) {
               UnsignedInteger u = (UnsignedInteger)var8.next();
               if (u.getState().isSplittedAhead()) {
                  ++n;
               }
            }

            if (n > vals.length / 2) {
               inputBitsSplit = true;
            }
         } else {
            useBits = false;
         }

         n = vals.length;
         int sqrtN = (int)Math.ceil(Math.sqrt((double)n));
         int nearestSqr = sqrtN * sqrtN;
         int indexBitwidth = (int)Math.ceil(Math.log((double)n) / Math.log(2.0));
         int[] indices = new int[nearestSqr];
         BigInteger[] paddVals = new BigInteger[nearestSqr];

         int i;
         for(i = 0; i < n; ++i) {
            indices[i] = i;
            paddVals[i] = vals[i];
         }

         for(i = n; i < nearestSqr; ++i) {
            indices[i] = i - n;
            paddVals[i] = vals[i - n];
         }

         ArrayList<BigInteger[]> allCoeffSet = new ArrayList();
         ArrayList<BigInteger> list = new ArrayList();

         for(int i = 0; i < nearestSqr; ++i) {
            list.add(BigInteger.valueOf((long)indices[i]).multiply(Util.computeBound(outputBitwidth)).add(paddVals[i]));
         }

         boolean done = false;
         int seed = 1;
         int trialCounter = false;
         int bitCount;
         if (useBits) {
            bitCount = sqrtN - 1;
         } else {
            bitCount = 0;
         }

         label114:
         while(bitCount >= 0) {
            int trialCounter = 0;

            while(true) {
               label110:
               while(true) {
                  if (done) {
                     continue label114;
                  }

                  ++trialCounter;
                  if (trialCounter == 10) {
                     --bitCount;
                     continue label114;
                  }

                  System.out.println("Attempting to solve linear systems for efficient Read-only memory access: Attempt#" + trialCounter + " -- bitcount = " + bitCount);
                  ++seed;
                  Collections.shuffle(list, new Random((long)seed));
                  allCoeffSet.clear();

                  for(int i = 0; i <= sqrtN - 1; ++i) {
                     BigInteger[][] mat = new BigInteger[sqrtN][sqrtN + 1];
                     HashSet<BigInteger> memberValueSet = new HashSet();

                     int ii;
                     for(ii = 0; ii < mat.length; ++ii) {
                        BigInteger memberValue = (BigInteger)list.get(ii + i * sqrtN);
                        memberValueSet.add(memberValue);
                        mat[ii][sqrtN] = BigInteger.ONE;
                        BigInteger v = memberValue.add(BigInteger.ONE);
                        BigInteger product = v;
                        if (bitCount != 0) {
                           product = v.multiply(v).mod(Config.getFiniteFieldModulus());
                        }

                        for(int j = 0; j <= sqrtN - 1; ++j) {
                           if (j < bitCount) {
                              mat[ii][j] = memberValue.testBit(j) ? BigInteger.ONE : BigInteger.ZERO;
                           } else {
                              mat[ii][j] = product;
                              product = product.multiply(memberValue.add(BigInteger.ONE)).mod(Config.getFiniteFieldModulus());
                           }
                        }
                     }

                     (new LinearSystemSolver(mat)).solveInPlace();
                     if (checkIfProverCanCheat(mat, memberValueSet, sqrtN, bitCount, outputBitwidth + indexBitwidth)) {
                        System.out.println("Invalid solution");

                        for(ii = 0; ii < sqrtN; ++ii) {
                           if (mat[ii][sqrtN].equals(BigInteger.ZERO)) {
                              System.out.println("Possibly invalid due to having zero coefficient(s)");
                              continue label110;
                           }
                        }
                        continue label110;
                     }

                     BigInteger[] coeffs = new BigInteger[sqrtN];

                     for(int ii = 0; ii < sqrtN; ++ii) {
                        coeffs[ii] = mat[ii][sqrtN];
                     }

                     allCoeffSet.add(coeffs);
                  }

                  done = true;
                  break label114;
               }
            }
         }

         System.out.println("done with read only memory solutions!");
         state.bitCount = bitCount;
         state.allCoeffSet = allCoeffSet;
      }
   }

   private static BigInteger[] getVariableValues(BigInteger val, int sqrtN, int bitCount) {
      BigInteger[] vars = new BigInteger[sqrtN];
      BigInteger v = val.add(BigInteger.ONE);
      BigInteger product = v;
      if (bitCount != 0) {
         product = v.multiply(v).mod(Config.getFiniteFieldModulus());
      }

      for(int j = 0; j < sqrtN; ++j) {
         if (j < bitCount) {
            vars[j] = val.testBit(j) ? BigInteger.ONE : BigInteger.ZERO;
         } else {
            vars[j] = product;
            product = product.multiply(v).mod(Config.getFiniteFieldModulus());
         }
      }

      return vars;
   }

   private static boolean checkIfProverCanCheat(BigInteger[][] mat, HashSet<BigInteger> valueSet, int sqrtN, int bitCount, int totalBitwidth) {
      int nearestSqr = sqrtN * sqrtN;
      BigInteger[] coeffs = new BigInteger[sqrtN];

      int validResults;
      for(validResults = 0; validResults < sqrtN; ++validResults) {
         coeffs[validResults] = mat[validResults][sqrtN];
      }

      validResults = 0;
      int outsidePermissibleSet = 0;
      BigInteger range = Util.computeBound(totalBitwidth);

      for(int k = 0; k < range.intValue(); ++k) {
         BigInteger[] vars = getVariableValues(BigInteger.valueOf((long)k), sqrtN, bitCount);
         BigInteger result = BigInteger.ZERO;

         for(int i = 0; i < sqrtN; ++i) {
            result = result.add(vars[i].multiply(coeffs[i]));
         }

         result = result.mod(Config.getFiniteFieldModulus());
         if (result.equals(BigInteger.ONE)) {
            ++validResults;
            if (!valueSet.contains(BigInteger.valueOf((long)k))) {
               ++outsidePermissibleSet;
            }
         }
      }

      if (validResults == sqrtN && outsidePermissibleSet == 0) {
         return false;
      } else {
         System.out.println("Prover can cheat with linear system solution");
         System.out.println("Num of valid values that the prover can use = " + validResults);
         System.out.println("Num of valid values outside permissible set = " + outsidePermissibleSet);
         return true;
      }
   }
}
