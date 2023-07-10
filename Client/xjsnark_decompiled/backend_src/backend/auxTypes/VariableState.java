package backend.auxTypes;

public class VariableState {
   private boolean packedAhead;
   private boolean splittedAhead;
   private boolean conditionallySplittedAhead;
   private boolean conditionallySplittedAndAlignedAhead;
   private boolean mustBeWithinRange;
   private int mulIndex;
   private int mulUseCount;
   private int addUseCount;
   private int thresholdBitwidth = -1;
   private VariableState[] prevStates;
   private boolean isMulOutput;
   private int id;
   private int expectedBitwidth;

   public boolean isPackedAhead() {
      return this.packedAhead;
   }

   public void setPackedAhead(boolean packedAhead) {
      this.packedAhead = packedAhead;
   }

   public boolean isSplittedAhead() {
      return this.splittedAhead;
   }

   public void setSplittedAhead(boolean splittedAhead) {
      this.splittedAhead = splittedAhead;
   }

   public boolean isConditionallySplittedAhead() {
      return this.conditionallySplittedAhead;
   }

   public void setConditionallySplittedAhead(boolean conditionallySplittedAhead) {
      this.conditionallySplittedAhead = conditionallySplittedAhead;
   }

   public int getMulIndex() {
      return this.mulIndex;
   }

   public void setMulIndex(int mulIndex) {
      this.mulIndex = mulIndex;
   }

   public void incMulIndex() {
      ++this.mulIndex;
   }

   public void incMulUseCount() {
      ++this.mulUseCount;
   }

   public void decMulUseCount() {
      --this.mulUseCount;
   }

   public void incAddUseCount() {
      ++this.addUseCount;
   }

   public void decAddUseCount() {
      --this.addUseCount;
   }

   public boolean isConditionallySplittedAndAlignedAhead() {
      return this.conditionallySplittedAndAlignedAhead;
   }

   public boolean isMustBeWithinRange() {
      return this.mustBeWithinRange;
   }

   public void setConditionallySplittedAndAlignedAhead(boolean conditionallySplittedAndAlignedAhead) {
      this.conditionallySplittedAndAlignedAhead = conditionallySplittedAndAlignedAhead;
   }

   public void setMustBeWithinRange(boolean mustBeWithinRange) {
      this.mustBeWithinRange = mustBeWithinRange;
   }

   public void setOptimizationAttributes(VariableState st1, VariableState st2, boolean isMulOutput, int bitwidth) {
      this.prevStates = new VariableState[2];
      this.prevStates[0] = st1;
      this.prevStates[1] = st2;
      this.isMulOutput = isMulOutput;
      this.expectedBitwidth = bitwidth;
   }

   public VariableState[] getPrevStates() {
      return this.prevStates;
   }

   public void setPrevStates(VariableState[] prevStates) {
      this.prevStates = prevStates;
   }

   public boolean isMulOutput() {
      return this.isMulOutput;
   }

   public void setMulOutput(boolean isMulOutput) {
      this.isMulOutput = isMulOutput;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getThresholdBitwidth() {
      return this.thresholdBitwidth;
   }

   public void setThresholdBitwidth(int thresholdBitwidth) {
      if (this.thresholdBitwidth == -1 || this.thresholdBitwidth > thresholdBitwidth) {
         this.thresholdBitwidth = thresholdBitwidth;
      }

   }

   public int getExpectedBitwidth() {
      return this.expectedBitwidth;
   }

   public void setExpectedBitwidth(int expectedBitwidth) {
      this.expectedBitwidth = expectedBitwidth;
   }

   public int hashCode() {
      return this.id;
   }

   public int getMulUseCount() {
      return this.mulUseCount;
   }

   public void setMulUseCount(int mulUseCount) {
      this.mulUseCount = mulUseCount;
   }

   public int getAddUseCount() {
      return this.addUseCount;
   }

   public void setAddUseCount(int addUseCount) {
      this.addUseCount = addUseCount;
   }
}
