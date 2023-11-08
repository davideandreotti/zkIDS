package backend.auxTypes;

import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import examples.gadgets.PermutationNetworkGadget;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class PermutationVerifier<T> {
   private T[] a1;
   private T[] a2;
   private String id;
   private Class<?> typeClass;
   private Object[] typeArgs;
   private CircuitGenerator generator;

   public PermutationVerifier(T[] a1, T[] a2, String id, Class<?> typeClass, Object[] typeArgs) {
      this.a1 = a1;
      this.a2 = a2;
      if (a1.length != a2.length) {
         throw new IllegalArgumentException();
      } else {
         this.id = id;
         this.typeArgs = typeArgs;
         this.typeClass = typeClass;
         this.generator = CircuitGenerator.__getActiveCircuitGenerator();
         if (this.generator.__getPhase() == 0) {
            this.prepare();
         } else {
            this.constructCircuit();
         }

      }
   }

   private void prepare() {
      int n = this.a1.length;
      int i;
      if (IAuxType.class.isAssignableFrom(this.typeClass)) {
         for(i = 0; i < n; ++i) {
            VariableState st1 = ((IAuxType)this.a1[i]).getState();
            st1.setPackedAhead(true);
            st1.setConditionallySplittedAndAlignedAhead(true);
            st1.setMustBeWithinRange(true);
            VariableState st2 = ((IAuxType)this.a2[i]).getState();
            st2.setPackedAhead(true);
            st2.setConditionallySplittedAndAlignedAhead(true);
            st2.setMustBeWithinRange(true);
         }
      } else if (StructDefinition.class.isAssignableFrom(this.typeClass)) {
         for(i = 0; i < n; ++i) {
            ((StructDefinition)this.a1[i]).__alignAndPackAll();
            ((StructDefinition)this.a2[i]).__alignAndPackAll();
         }
      }

   }

   private void constructCircuit() {
      int n = this.a1.length;
      Wire[][] input = new Wire[n][];
      Wire[][] output = new Wire[n][];
      if (IAuxType.class.isAssignableFrom(this.typeClass)) {
         int[] elementSizes = this.getElementSize();
         int numWires = elementSizes.length;

         for(int i = 0; i < n; ++i) {
            input[i] = ((IAuxType)this.a1[i]).getPackedValue().array;
            output[i] = ((IAuxType)this.a2[i]).getPackedValue().array;
         }
      } else if (StructDefinition.class.isAssignableFrom(this.typeClass)) {
         for(int i = 0; i < n; ++i) {
            ArrayList<IAuxType> list1 = ((StructDefinition)this.a1[i]).__getBasicElements();
            ArrayList<Wire> wireList1 = new ArrayList();
            Iterator var8 = list1.iterator();

            while(var8.hasNext()) {
               IAuxType t = (IAuxType)var8.next();
               PackedValue v = t.getPackedValue();
               Wire[] var13;
               int var12 = (var13 = v.array).length;

               for(int var11 = 0; var11 < var12; ++var11) {
                  Wire w = var13[var11];
                  wireList1.add(w);
               }
            }

            input[i] = new Wire[wireList1.size()];

            for(int j = 0; j < input[i].length; ++j) {
               input[i][j] = (Wire)wireList1.get(i);
            }

            ArrayList<IAuxType> list2 = ((StructDefinition)this.a2[i]).__getBasicElements();
            ArrayList<Wire> wireList2 = new ArrayList();
            Iterator var24 = list2.iterator();

            while(var24.hasNext()) {
               IAuxType t = (IAuxType)var24.next();
               PackedValue v = t.getPackedValue();
               Wire[] var15;
               int var14 = (var15 = v.array).length;

               for(int var27 = 0; var27 < var14; ++var27) {
                  Wire w = var15[var27];
                  wireList2.add(w);
               }
            }

            output[i] = new Wire[wireList2.size()];

            for(int j = 0; j < output[i].length; ++j) {
               output[i][j] = (Wire)wireList2.get(i);
            }
         }
      }

      new PermutationNetworkGadget(input, output, new int[0], "ext_" + this.id);
   }

   private int[] getElementSize() {
      int bitwidth;
      int[] sizes;
      if (this.typeClass == UnsignedInteger.class) {
         bitwidth = Integer.parseInt((String)this.typeArgs[0]);
         if (bitwidth <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            return new int[]{bitwidth};
         } else {
            sizes = new int[bitwidth / UnsignedInteger.BITWIDTH_PER_CHUNK + bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK == 0 ? 0 : 1];
            Arrays.fill(sizes, UnsignedInteger.BITWIDTH_PER_CHUNK);
            if (bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
               sizes[sizes.length - 1] = bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
            }

            return sizes;
         }
      } else if (this.typeClass == FieldElement.class) {
         bitwidth = (new BigInteger((String)this.typeArgs[0])).bitLength();
         if (bitwidth <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            return new int[]{bitwidth};
         } else {
            sizes = new int[bitwidth / UnsignedInteger.BITWIDTH_PER_CHUNK + bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK == 0 ? 0 : 1];
            Arrays.fill(sizes, UnsignedInteger.BITWIDTH_PER_CHUNK);
            if (bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
               sizes[sizes.length - 1] = bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
            }

            return sizes;
         }
      } else if (this.typeClass == GroupElement.class) {
         bitwidth = (new BigInteger((String)this.typeArgs[0])).bitLength();
         if (bitwidth <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            return new int[]{bitwidth};
         } else {
            sizes = new int[bitwidth / UnsignedInteger.BITWIDTH_PER_CHUNK + bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK == 0 ? 0 : 1];
            Arrays.fill(sizes, UnsignedInteger.BITWIDTH_PER_CHUNK);
            if (bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK != 0) {
               sizes[sizes.length - 1] = bitwidth % UnsignedInteger.BITWIDTH_PER_CHUNK;
            }

            return sizes;
         }
      } else {
         return this.typeClass == Bit.class ? new int[]{1} : null;
      }
   }
}
