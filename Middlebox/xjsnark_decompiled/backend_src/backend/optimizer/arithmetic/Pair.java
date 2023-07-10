package backend.optimizer.arithmetic;

import java.util.Arrays;

public class Pair {
   private int row;
   private int col;

   public Pair(int row, int col) {
      this.row = row;
      this.col = col;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof Pair)) {
         return false;
      } else {
         Pair other = (Pair)o;
         return other.row == this.row && other.col == this.col;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(new int[]{this.row, this.col});
   }
}
