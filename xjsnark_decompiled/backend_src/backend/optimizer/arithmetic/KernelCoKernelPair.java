package backend.optimizer.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;

public class KernelCoKernelPair {
   private ExpressionMatrix kernel;
   private Cube coKernel;

   public KernelCoKernelPair(ExpressionMatrix exp, Cube cube) {
      this.kernel = exp;
      this.coKernel = cube;
   }

   public int hashCode() {
      return this.coKernel.hashCode() + this.kernel.hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof KernelCoKernelPair)) {
         return false;
      } else {
         KernelCoKernelPair other = (KernelCoKernelPair)o;
         ArrayList<Cube> pwrs1 = this.kernel.getPowers();
         ArrayList<Cube> pwrs2 = other.kernel.getPowers();
         boolean checkLengths = pwrs1.size() == pwrs2.size();
         boolean checkVars = Arrays.equals(this.kernel.getLiterals(), other.kernel.getLiterals());
         if (checkLengths && checkVars) {
            boolean check = true;
            int size = pwrs1.size();

            for(int i = 0; i < size; ++i) {
               check = check && ((Cube)pwrs1.get(i)).equals(pwrs2.get(i));
            }

            if (other.coKernel.equals(this.coKernel) && check) {
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public ExpressionMatrix getKernel() {
      return this.kernel;
   }

   public Cube getCoKernel() {
      return this.coKernel;
   }
}
