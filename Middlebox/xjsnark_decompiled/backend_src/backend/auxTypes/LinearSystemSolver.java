package backend.auxTypes;

import backend.config.Config;
import java.math.BigInteger;

public class LinearSystemSolver {
   public static BigInteger prime = Config.getFiniteFieldModulus();
   private BigInteger[][] mat;
   private int numRows;
   private int numCols;

   public LinearSystemSolver(BigInteger[][] mat) {
      this.mat = mat;
      this.numRows = mat.length;
      this.numCols = mat[0].length;
   }

   public void solveInPlace() {
      this.guassJordan();
      this.rref();
   }

   private void guassJordan() {
      int colIdx = 0;

      for(int rowIdx = 0; colIdx < this.numCols; ++rowIdx) {
         int pivotRowIdx;
         for(pivotRowIdx = rowIdx; pivotRowIdx < this.numRows && this.mat[pivotRowIdx][colIdx].equals(BigInteger.ZERO); ++pivotRowIdx) {
         }

         if (pivotRowIdx != this.numRows) {
            BigInteger[] tmp = this.mat[pivotRowIdx];
            this.mat[pivotRowIdx] = this.mat[rowIdx];
            this.mat[rowIdx] = tmp;
            pivotRowIdx = rowIdx;
            BigInteger invF = inverse(this.mat[rowIdx][colIdx]);

            int k;
            for(k = 0; k < this.numCols; ++k) {
               this.mat[pivotRowIdx][k] = this.mat[pivotRowIdx][k].multiply(invF).mod(prime);
            }

            for(k = pivotRowIdx + 1; k < this.numRows; ++k) {
               BigInteger f = negate(this.mat[k][colIdx]);

               for(int j = 0; j < this.numCols; ++j) {
                  this.mat[k][j] = this.mat[k][j].add(this.mat[pivotRowIdx][j].multiply(f));
                  this.mat[k][j] = this.mat[k][j].mod(prime);
               }
            }
         }

         ++colIdx;
      }

   }

   private void rref() {
      for(int rowIdx = this.numRows - 1; rowIdx >= 0; --rowIdx) {
         int pivotColIdx;
         for(pivotColIdx = 0; pivotColIdx < this.numCols && this.mat[rowIdx][pivotColIdx].equals(BigInteger.ZERO); ++pivotColIdx) {
         }

         if (pivotColIdx != this.numCols) {
            for(int k = rowIdx - 1; k >= 0; --k) {
               BigInteger f = this.mat[k][pivotColIdx];

               for(int j = 0; j < this.numCols; ++j) {
                  this.mat[k][j] = this.mat[k][j].add(negate(this.mat[rowIdx][j].multiply(f)));
                  this.mat[k][j] = this.mat[k][j].mod(prime);
               }
            }
         }
      }

   }

   private static BigInteger negate(BigInteger x) {
      return prime.subtract(x.mod(prime)).mod(prime);
   }

   private static BigInteger inverse(BigInteger x) {
      return x.mod(prime).modInverse(prime);
   }
}
