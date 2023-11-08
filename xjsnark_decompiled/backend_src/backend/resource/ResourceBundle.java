package backend.resource;

import java.math.BigInteger;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ResourceBundle {
   public ConcurrentMap<BigInteger, BigInteger> bigIntegerSet = new ConcurrentHashMap();
   private static ResourceBundle instance;

   private ResourceBundle() {
   }

   public static ResourceBundle getInstance() {
      if (instance == null) {
         instance = new ResourceBundle();
      }

      return instance;
   }

   public BigInteger getBigInteger(BigInteger x) {
      this.bigIntegerSet.putIfAbsent(x, x);
      return (BigInteger)this.bigIntegerSet.get(x);
   }
}
