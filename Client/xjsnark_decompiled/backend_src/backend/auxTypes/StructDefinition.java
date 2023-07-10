package backend.auxTypes;

import java.util.ArrayList;

public abstract class StructDefinition {
   public abstract void __makeInput();

   public abstract void __makeOutput();

   public abstract void __makeWitness();

   public abstract void __makeVerifiedWitness();

   public abstract StructDefinition __copy();

   public abstract void __alignAndPackAll();

   public abstract int[] __getBasicElementsDetails();

   public abstract ArrayList<IAuxType> __getBasicElements();
}
