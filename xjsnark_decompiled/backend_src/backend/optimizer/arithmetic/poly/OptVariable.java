package backend.optimizer.arithmetic.poly;

public class OptVariable {
   String label;
   int id = -1;
   boolean isBit;

   public int hashCode() {
      return this.label.hashCode() + this.id;
   }

   public OptVariable(String label, int id, boolean isBit) {
      this.label = label;
      this.id = id;
      this.isBit = isBit;
   }

   public OptVariable(String fullIdentifier) {
      StringBuilder b = new StringBuilder();
      int s = -1;

      for(int i = 0; i < fullIdentifier.length(); ++i) {
         char c = fullIdentifier.charAt(i);
         if (c >= '0' && fullIdentifier.charAt(i) <= '9') {
            s = i;
            break;
         }

         b.append(c);
      }

      this.label = b.toString();
      this.id = Integer.parseInt(fullIdentifier.substring(s));
   }

   public OptVariable(String label, int id) {
      this.label = label;
      this.id = id;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o instanceof OptVariable) {
         return this.label.equals(((OptVariable)o).label) && this.id == ((OptVariable)o).id;
      } else {
         return false;
      }
   }

   public String toString() {
      return this.label == null ? "null" : this.label + this.id;
   }

   public String getLabel() {
      return this.label + this.id;
   }

   public boolean isBit() {
      return this.isBit;
   }
}
