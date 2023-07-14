package backend.optimizer.arithmetic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Util {
   public static <T> T[] concatenate(T[] a, T[] b) {
      int aLen = a.length;
      int bLen = b.length;
      Object[] c = (Object[])Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
      System.arraycopy(a, 0, c, 0, aLen);
      System.arraycopy(b, 0, c, aLen, bLen);
      return c;
   }

   public static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
      Set<Set<T>> sets = new HashSet();
      if (originalSet.isEmpty()) {
         sets.add(new HashSet());
         return sets;
      } else {
         List<T> list = new ArrayList(originalSet);
         T head = list.get(0);
         Set<T> rest = new HashSet(list.subList(1, list.size()));
         Iterator var6 = powerSet(rest).iterator();

         while(var6.hasNext()) {
            Set<T> set = (Set)var6.next();
            Set<T> newSet = new HashSet();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
         }

         return sets;
      }
   }
}
