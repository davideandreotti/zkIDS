package backend.auxTypes;

import backend.structure.CircuitGenerator;
import java.math.BigInteger;
import java.util.Arrays;
import util.Util;

public class SubtractionAUX {
   static int chunkBitwidth = 64;

   public static BigInteger[] prepSub(PackedValue p2, BigInteger modulus, CircuitGenerator generator, int bitwidth) {
      BigInteger max2 = p2.getMaxVal(UnsignedInteger.BITWIDTH_PER_CHUNK);
      BigInteger[] max2Chunks = p2.getCurrentMaxValues();
      BigInteger f = max2.divide(modulus);

      label36:
      for(int i = 1; i != 50; ++i) {
         BigInteger factor = f.add(BigInteger.valueOf((long)(i + 1)).shiftLeft((i - 1) * UnsignedInteger.BITWIDTH_PER_CHUNK));
         BigInteger[] factorChunks;
         BigInteger[] modChunks;
         if (bitwidth > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            factorChunks = Util.split(factor, UnsignedInteger.BITWIDTH_PER_CHUNK);
            modChunks = Util.split(modulus, UnsignedInteger.BITWIDTH_PER_CHUNK);
         } else {
            factorChunks = new BigInteger[]{factor};
            modChunks = new BigInteger[]{modulus};
         }

         BigInteger[] base = mul(modChunks, factorChunks);
         int maxLength = Math.max(base.length, max2Chunks.length);
         base = Util.padBigIntegerArray(base, maxLength);
         max2Chunks = Util.padBigIntegerArray(max2Chunks, maxLength);
         BigInteger prevCarry = BigInteger.ZERO;

         for(int j = 0; j < maxLength; ++j) {
            if (j == maxLength - 1) {
               base[j] = base[j].subtract(prevCarry);
               if (base[j].subtract(max2Chunks[j]).signum() == -1) {
                  continue label36;
               }
            } else {
               BigInteger delta = max2Chunks[j].add(prevCarry).divide(Util.computeBound(UnsignedInteger.BITWIDTH_PER_CHUNK));
               if (!max2Chunks[j].add(prevCarry).mod(Util.computeBound(UnsignedInteger.BITWIDTH_PER_CHUNK)).equals(BigInteger.ZERO)) {
                  delta = delta.add(BigInteger.ONE);
               }

               base[j] = base[j].subtract(prevCarry).add(delta.multiply(Util.computeBound(UnsignedInteger.BITWIDTH_PER_CHUNK)));
               prevCarry = delta;
            }
         }

         return base;
      }

      throw new RuntimeException("Exceeded iterations limit in subtraction circuit search.");
   }

   public static void main(String[] args) {
   }

   static BigInteger[] adjustLength(BigInteger[] p, int size) {
      if (p.length >= size) {
         return p;
      } else {
         BigInteger[] tmp = new BigInteger[size];
         Arrays.fill(tmp, BigInteger.ZERO);
         System.arraycopy(p, 0, tmp, 0, p.length);
         return tmp;
      }
   }

   static void print(BigInteger[] p) {
      BigInteger[] var4 = p;
      int var3 = p.length;

      for(int var2 = 0; var2 < var3; ++var2) {
         BigInteger pi = var4[var2];
         System.out.print(pi + ",");
      }

      System.out.println();
   }

   static BigInteger getMaxValue(BigInteger[] p) {
      return Util.group(p, chunkBitwidth);
   }

   static BigInteger[] mul(BigInteger[] p1, BigInteger[] p2) {
      BigInteger[] result = new BigInteger[p1.length + p2.length - 1];
      Arrays.fill(result, BigInteger.ZERO);

      for(int i = 0; i < p1.length; ++i) {
         for(int j = 0; j < p2.length; ++j) {
            result[i + j] = result[i + j].add(p1[i].multiply(p2[j]));
         }
      }

      return result;
   }

   static BigInteger[] add(BigInteger[] p1, BigInteger[] p2) {
      BigInteger[] result = new BigInteger[Math.max(p1.length, p2.length)];
      Arrays.fill(result, BigInteger.ZERO);

      for(int i = 0; i < result.length; ++i) {
         if (i < p1.length) {
            result[i] = result[i].add(p1[i]);
         }

         if (i < p2.length) {
            result[i] = result[i].add(p2[i]);
         }
      }

      return result;
   }
}
