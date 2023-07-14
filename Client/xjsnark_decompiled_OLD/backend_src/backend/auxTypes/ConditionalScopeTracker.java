package backend.auxTypes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Stack;

public class ConditionalScopeTracker {
   private static int currentScopeId = 0;
   private static ConditionalStatementData currentConditionalStmtData;
   private static Hashtable<Integer, LinkedHashSet<ConditionalScopeImpactedType>> table = new Hashtable();
   private static Stack<ConditionalStatementData> condDataStack = new Stack();

   public static void push(Bit active) {
      currentConditionalStmtData.push(active.copy());
   }

   public static void pop() {
      HashSet<ConditionalScopeImpactedType> list = (HashSet)table.get(currentScopeId);
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ConditionalScopeImpactedType t = (ConditionalScopeImpactedType)var2.next();
         t.pop(currentConditionalStmtData.getSubscopeId());
      }

   }

   public static Bit getIndividualActiveBit() {
      return (Bit)currentConditionalStmtData.activeIndividualBitList.get(condDataStack.size() - 1);
   }

   public static Bit getAccumActiveBit() {
      Bit result = new Bit(true);

      Bit activeCondition;
      for(Iterator var2 = condDataStack.iterator(); var2.hasNext(); result = result.mul(activeCondition)) {
         ConditionalStatementData condData = (ConditionalStatementData)var2.next();
         activeCondition = new Bit(true);

         for(int i = 0; i < condData.activeIndividualBitList.size() - 1; ++i) {
            activeCondition = activeCondition.mul(((Bit)condData.activeIndividualBitList.get(i)).inv());
         }

         activeCondition = activeCondition.mul(condData.getCurrentActiveBit());
      }

      return result;
   }

   public static void pushMain() {
      currentConditionalStmtData = new ConditionalStatementData();
      condDataStack.push(currentConditionalStmtData);
      ++currentScopeId;
      LinkedHashSet<ConditionalScopeImpactedType> list = new LinkedHashSet();
      table.put(currentScopeId, list);
   }

   public static void popMain() {
      HashSet<ConditionalScopeImpactedType> list = (HashSet)table.get(currentScopeId);
      condDataStack.pop();
      Iterator var2 = list.iterator();

      while(var2.hasNext()) {
         ConditionalScopeImpactedType t = (ConditionalScopeImpactedType)var2.next();
         t.popMain();
      }

      currentConditionalStmtData = condDataStack.isEmpty() ? null : (ConditionalStatementData)condDataStack.peek();
      table.remove(currentScopeId);
      --currentScopeId;
   }

   public static ConditionalStatementData getCurrentConditionalStmtData() {
      return currentConditionalStmtData;
   }

   public static int getCurrentScopeId() {
      return currentScopeId;
   }

   public static void register(ConditionalScopeImpactedType v, int originalScopeId) {
      int s = currentScopeId;

      for(int i = s; i > originalScopeId; --i) {
         ((LinkedHashSet)table.get(i)).add(v);
      }

   }

   public static class ConditionalStatementData {
      private ArrayList<Bit> activeIndividualBitList = new ArrayList();

      void push(Bit active) {
         this.activeIndividualBitList.add(active.copy());
      }

      ArrayList<Bit> getBitList() {
         return this.activeIndividualBitList;
      }

      Bit getCurrentActiveBit() {
         return (Bit)this.activeIndividualBitList.get(this.activeIndividualBitList.size() - 1);
      }

      int getSubscopeId() {
         return this.activeIndividualBitList.size() - 1;
      }
   }
}
