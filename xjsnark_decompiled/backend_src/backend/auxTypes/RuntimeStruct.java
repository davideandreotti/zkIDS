package backend.auxTypes;

public abstract class RuntimeStruct {
   public UnsignedInteger ptrReference;

   public void ____assign(RuntimeStruct e) {
   }

   public static RuntimeStruct[] ____createNullArray(int n) {
      return null;
   }

   public static RuntimeStruct ____createNullObject() {
      return null;
   }

   public static RuntimeStruct ____createDummyObject() {
      return null;
   }

   public static RuntimeStruct ____createObjectWithReference(UnsignedInteger ptr) {
      return null;
   }

   public static int ____getIndexBitwidth() {
      return -1;
   }

   public abstract RuntimeStruct ____copy();
}
