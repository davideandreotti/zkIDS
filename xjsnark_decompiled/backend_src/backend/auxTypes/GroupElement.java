package backend.auxTypes;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.operations.Gadget;
import backend.structure.BitWire;
import backend.structure.CircuitGenerator;
import backend.structure.ConstantWire;
import backend.structure.Wire;
import backend.structure.WireArray;
import examples.gadgets.CustomLongFieldDivGadget;
import examples.gadgets.CustomShortFieldDivGadget;
import examples.gadgets.FieldDivisionGadget;
import examples.gadgets.InverseLongIntegerModGadget;
import examples.gadgets.LongIntegerModConstantGadget;
import examples.gadgets.ModConstantGadget;
import examples.gadgets.ShortIntegerModGadget;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import util.Util;

public class GroupElement implements IAuxType, ConditionalScopeImpactedType {
   protected PackedValue packedWire;
   protected WireArray bitWires;
   protected int currentBitwidth;
   protected BigInteger modulus;
   protected BigInteger maxValue;
   protected BigInteger constant;
   protected CircuitGenerator generator;
   protected VariableState variableState;
   protected boolean packedAtCreationTime;
   protected boolean splittedAtCreationTime;
   protected int conditionalScopeId;
   protected int scope;
   protected Stack<HashMap<Integer, GroupElement>> possibleValStack;
   protected Stack<GroupElement> prevValStack;
   private boolean stateChanged;
   protected boolean nativeSnarkField;

   public void setConditionalScopeId(int id) {
      this.conditionalScopeId = id;
   }

   public GroupElement(GroupElement o) {
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.nativeSnarkField = false;
      this.generator = o.generator;
      this.maxValue = o.maxValue;
      this.constant = o.constant;
      this.variableState = o.variableState;
      this.currentBitwidth = o.currentBitwidth;
      this.bitWires = o.bitWires;
      this.packedWire = o.packedWire;
      this.conditionalScopeId = o.conditionalScopeId;
      this.modulus = o.modulus;
      this.nativeSnarkField = o.nativeSnarkField;
      this.scope = o.scope;
   }

   public BigInteger getConstant() {
      return this.constant;
   }

   public void assign(GroupElement target) {
      int current;
      int i;
      if (this.generator.__getPhase() == 0) {
         if (this.scope != ConditionalScopeTracker.getCurrentScopeId()) {
            ConditionalScopeTracker.register(this, this.scope);
            if (this.possibleValStack == null) {
               this.possibleValStack = new Stack();
            }

            if (this.prevValStack == null) {
               this.prevValStack = new Stack();
            }

            current = ConditionalScopeTracker.getCurrentScopeId();

            for(i = 0; i < current - this.scope; ++i) {
               GroupElement c = this.copy();
               c.variableState.setPackedAhead(true);
               this.prevValStack.push(c);
               this.possibleValStack.push(new HashMap());
            }

            this.stateChanged = true;
            this.scope = ConditionalScopeTracker.getCurrentScopeId();
         }

         this.constant = target.constant;
         this.nativeSnarkField = target.nativeSnarkField;
         this.variableState = target.variableState;
      } else if (this.scope == ConditionalScopeTracker.getCurrentScopeId()) {
         this.bitWires = target.bitWires;
         this.splittedAtCreationTime = target.splittedAtCreationTime;
         this.packedAtCreationTime = target.packedAtCreationTime;
         this.packedWire = target.packedWire;
         this.variableState = target.variableState;
         this.maxValue = target.maxValue;
         this.currentBitwidth = target.currentBitwidth;
         this.constant = target.constant;
      } else {
         this.stateChanged = true;
         ConditionalScopeTracker.register(this, this.scope);
         if (this.possibleValStack == null) {
            this.possibleValStack = new Stack();
         }

         if (this.prevValStack == null) {
            this.prevValStack = new Stack();
         }

         current = ConditionalScopeTracker.getCurrentScopeId();

         for(i = this.prevValStack.size(); i < current; ++i) {
            this.prevValStack.push(this.copy());
            this.possibleValStack.push(new HashMap());
         }

         this.modulus = target.modulus;
         this.bitWires = target.bitWires;
         this.splittedAtCreationTime = target.splittedAtCreationTime;
         this.packedAtCreationTime = target.packedAtCreationTime;
         this.packedWire = target.packedWire;
         this.variableState = target.variableState;
         this.maxValue = target.maxValue;
         this.currentBitwidth = target.currentBitwidth;
         this.scope = ConditionalScopeTracker.getCurrentScopeId();
         this.constant = target.constant;
      }

   }

   public void pop(int id) {
      if (this.stateChanged) {
         GroupElement copy = this.copy();
         if (this.generator.__getPhase() == 0) {
            copy.variableState.setPackedAhead(true);
         }

         ((HashMap)this.possibleValStack.peek()).put(id, copy);
         --this.scope;
         GroupElement prev = (GroupElement)this.prevValStack.peek();
         this.packedWire = prev.packedWire;
         this.variableState = prev.variableState;
         this.bitWires = prev.bitWires;
         this.maxValue = prev.maxValue;
         this.constant = prev.constant;
         this.modulus = prev.modulus;
         this.currentBitwidth = prev.currentBitwidth;
         this.stateChanged = false;
      }
   }

   public void popMain() {
      if (this.generator.__getPhase() == 0) {
         this.variableState = this.generator.__retrieveVariableState();
         this.variableState.setPackedAhead(true);
         HashMap<Integer, GroupElement> possibleVals = (HashMap)this.possibleValStack.pop();
         int mulIndex = ((GroupElement)this.prevValStack.pop()).getVariableState().getMulIndex();
         Iterator var4 = possibleVals.keySet().iterator();

         while(var4.hasNext()) {
            Integer idx = (Integer)var4.next();
            int m = ((GroupElement)possibleVals.get(idx)).variableState.getMulIndex();
            if (m > mulIndex) {
               mulIndex = m;
            }
         }

         this.variableState.setMulIndex(mulIndex);
         this.bitWires = null;
         this.constant = null;
         this.stateChanged = true;
      } else {
         int tmp = this.scope;
         if (ConditionalScopeTracker.getCurrentScopeId() > tmp) {
            this.stateChanged = true;
         }

         this.variableState = null;
         ConditionalScopeTracker.ConditionalStatementData condData = ConditionalScopeTracker.getCurrentConditionalStmtData();
         this.bitWires = null;
         int numberOfValues = condData.getBitList().size();
         ArrayList<Bit> conditionList = condData.getBitList();
         GroupElement[] candidateList = new GroupElement[numberOfValues];
         HashMap<Integer, GroupElement> possibleVals = (HashMap)this.possibleValStack.pop();

         Integer idx;
         for(Iterator var8 = possibleVals.keySet().iterator(); var8.hasNext(); candidateList[idx] = (GroupElement)possibleVals.get(idx)) {
            idx = (Integer)var8.next();
         }

         for(int i = 0; i < numberOfValues; ++i) {
            if (candidateList[i] == null) {
               candidateList[i] = this.copy();
            }
         }

         GroupElement initial = candidateList[numberOfValues - 1];
         int startingIndex = -1;

         for(int i = numberOfValues - 2; i >= 0; --i) {
            if (candidateList[i].packedWire != initial.packedWire) {
               startingIndex = i;
               break;
            }
         }

         if (startingIndex == -1) {
            this.packedWire = initial.packedWire;
            this.maxValue = initial.maxValue;
            this.bitWires = initial.bitWires;
            this.currentBitwidth = initial.currentBitwidth;
            this.modulus = initial.modulus;
            this.constant = initial.constant;
         } else {
            this.modulus = initial.modulus;
            this.packedWire = initial.packedWire;
            this.maxValue = initial.maxValue;
            this.bitWires = initial.bitWires;
            this.currentBitwidth = initial.currentBitwidth;

            for(int j = startingIndex; j >= 0; --j) {
               GroupElement current = candidateList[j];
               Bit selectionBit = (Bit)conditionList.get(j);
               this.packedWire = this.packedWire.muxBit(current.packedWire, selectionBit.wire);
               this.bitWires = null;
               this.constant = null;
               this.maxValue = this.maxValue.compareTo(current.maxValue) > 0 ? this.maxValue : current.maxValue;
               this.currentBitwidth = Math.max(this.currentBitwidth, current.currentBitwidth);
            }
         }

         this.prevValStack.pop();
         this.init();
      }

   }

   public GroupElement copy() {
      GroupElement v;
      if (this.generator.__getPhase() == 0) {
         v = new GroupElement(this.generator, this.modulus, this.variableState);
         v.constant = this.constant;
         v.packedWire = this.packedWire;
         v.bitWires = this.bitWires;
         v.maxValue = this.maxValue;
         return v;
      } else {
         v = new GroupElement();
         v.modulus = this.modulus;
         v.bitWires = this.bitWires;
         v.splittedAtCreationTime = this.splittedAtCreationTime;
         v.packedAtCreationTime = this.packedAtCreationTime;
         v.packedWire = this.packedWire;
         v.variableState = this.variableState;
         v.maxValue = this.maxValue;
         v.currentBitwidth = this.currentBitwidth;
         v.generator = this.generator;
         v.constant = this.constant;
         v.nativeSnarkField = this.nativeSnarkField;
         return v;
      }
   }

   public GroupElement() {
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.nativeSnarkField = false;
   }

   public GroupElement(BigInteger modulus, PackedValue packedWire, BigInteger maxValue) {
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.nativeSnarkField = false;
      this.maxValue = maxValue;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.packedWire = packedWire;
      this.modulus = modulus;
      this.currentBitwidth = maxValue == null ? modulus.bitLength() : maxValue.bitLength();
      this.init();
   }

   public GroupElement(BigInteger modulus, PackedValue packedWire) {
      this((BigInteger)modulus, (PackedValue)packedWire, (BigInteger)null);
   }

   public GroupElement(BigInteger modulus, BigInteger constant) {
      this(modulus, CircuitGenerator.__getActiveCircuitGenerator().__createConstantPackedValue(Util.prepConstant(constant, modulus), modulus));
   }

   private GroupElement(CircuitGenerator generator, BigInteger modulus) {
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.nativeSnarkField = false;
      this.modulus = modulus;
      this.generator = generator;
      this.maxValue = modulus.subtract(BigInteger.ONE);
      this.currentBitwidth = this.maxValue.bitLength();
      this.init();
   }

   private GroupElement(CircuitGenerator generator, BigInteger modulus, VariableState st) {
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.nativeSnarkField = false;
      this.modulus = modulus;
      this.generator = generator;
      this.variableState = st;
      this.maxValue = modulus.subtract(BigInteger.ONE);
      this.currentBitwidth = this.maxValue.bitLength();
   }

   public GroupElement(BigInteger modulus) {
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.nativeSnarkField = false;
      this.modulus = modulus;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.currentBitwidth = modulus.bitLength();
      this.init();
   }

   private void init() {
      if (this.modulus.equals(Config.getFiniteFieldModulus())) {
         this.nativeSnarkField = true;
      }

      this.checkConstant();
      if (this.variableState == null) {
         this.variableState = this.generator.__retrieveVariableState();
      }

      if (this.maxValue == null) {
         this.maxValue = this.modulus.subtract(BigInteger.ONE);
         this.currentBitwidth = this.maxValue.bitLength();
      }

      if (this.generator.__getPhase() == 1) {
         if (this.constant != null) {
            this.maxValue = this.constant.mod(this.modulus);
            this.packedWire = this.generator.__createConstantPackedValue(this.maxValue, this.modulus);
            this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
         }

         if (this.variableState != null) {
            if (this.variableState.isPackedAhead() && this.packedWire == null) {
               WireArray modified = this.bitWires.adjustLength(Math.min(this.bitWires.size(), this.modulus.bitLength()));
               if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_PER_CHUNK && !this.nativeSnarkField) {
                  this.packedWire = new PackedValue(modified, UnsignedInteger.BITWIDTH_PER_CHUNK);
               } else {
                  this.packedWire = new PackedValue(modified, this.modulus.bitLength());
               }

               this.maxValue = Util.min(this.bitWires.computeTightUpperBoundOfBitWires(this.modulus.bitLength()), this.modulus.subtract(BigInteger.ONE));
               this.currentBitwidth = this.maxValue.bitLength();
            } else if (this.variableState.isSplittedAhead() && this.bitWires == null) {
               this.getBackInRange(true);
               if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !this.nativeSnarkField) {
                  this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
               } else {
                  this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), this.modulus.bitLength());
               }

               this.maxValue = Util.min(this.bitWires.computeTightUpperBoundOfBitWires(this.modulus.bitLength()), this.modulus.subtract(BigInteger.ONE));
               this.currentBitwidth = this.maxValue.bitLength();
               if (!this.nativeSnarkField && (this.maxValue.compareTo(this.modulus) >= 0 || !this.packedWire.isAligned()) && this.variableState.isPackedAhead()) {
                  if (this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
                     this.packedWire = new PackedValue(this.bitWires, this.modulus.bitLength());
                  } else {
                     this.packedWire = new PackedValue(this.bitWires, UnsignedInteger.BITWIDTH_PER_CHUNK);
                  }
               }
            } else if ((this.variableState.isConditionallySplittedAhead() || this.variableState.isConditionallySplittedAndAlignedAhead()) && this.bitWires == null) {
               if (this.maxValue.compareTo(this.modulus) >= 0) {
                  if (!this.variableState.isMustBeWithinRange()) {
                     this.getBackInRange(false);
                  } else {
                     this.getBackInRange(true);
                     if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !this.nativeSnarkField) {
                        this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
                     } else {
                        this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), this.modulus.bitLength());
                     }

                     this.maxValue = Util.min(this.bitWires.computeTightUpperBoundOfBitWires(this.modulus.bitLength()), this.modulus.subtract(BigInteger.ONE));
                     this.currentBitwidth = this.maxValue.bitLength();
                     if (!this.nativeSnarkField && (this.maxValue.compareTo(this.modulus) >= 0 || !this.packedWire.isAligned()) && this.variableState.isPackedAhead()) {
                        if (this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
                           this.packedWire = new PackedValue(this.bitWires, this.modulus.bitLength());
                        } else {
                           this.packedWire = new PackedValue(this.bitWires, UnsignedInteger.BITWIDTH_PER_CHUNK);
                        }
                     }
                  }
               } else if (!this.nativeSnarkField && this.packedWire != null && !this.packedWire.isAligned() && this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && this.variableState.isConditionallySplittedAndAlignedAhead()) {
                  this.packedWire = this.packedWire.align((int)Math.ceil((double)this.modulus.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK), UnsignedInteger.BITWIDTH_LIMIT_SHORT);
               } else if (this.packedWire == null && this.variableState.isConditionallySplittedAndAlignedAhead()) {
                  throw new RuntimeException("Case Unexpected");
               }
            } else if (this.bitWires != null && this.bitWires.size() > this.modulus.bitLength()) {
               this.bitWires = new WireArray((Wire[])Arrays.copyOfRange(this.bitWires.asArray(), 0, this.modulus.bitLength()));
               if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_PER_CHUNK && !this.nativeSnarkField) {
                  this.packedWire = new PackedValue(this.bitWires, UnsignedInteger.BITWIDTH_PER_CHUNK);
               } else {
                  this.packedWire = new PackedValue(this.bitWires, this.modulus.bitLength());
               }

               this.maxValue = Util.min(this.bitWires.computeTightUpperBoundOfBitWires(this.modulus.bitLength()), this.modulus.subtract(BigInteger.ONE));
               this.currentBitwidth = this.maxValue.bitLength();
            }

            if (this.variableState.getThresholdBitwidth() != -1 && this.bitWires == null) {
               this.currentBitwidth = this.maxValue.bitLength();
               if (this.currentBitwidth > this.variableState.getThresholdBitwidth()) {
                  if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !this.nativeSnarkField) {
                     this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
                  } else {
                     this.bitWires = this.packedWire.getBits(this.modulus.bitLength(), this.modulus.bitLength());
                  }

                  this.maxValue = Util.min(this.bitWires.computeTightUpperBoundOfBitWires(this.modulus.bitLength()), this.modulus.subtract(BigInteger.ONE));
                  this.currentBitwidth = this.maxValue.bitLength();
                  if ((this.maxValue.compareTo(this.modulus) >= 0 || !this.packedWire.isAligned()) && this.variableState.isPackedAhead()) {
                     if (this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
                        this.packedWire = new PackedValue(this.bitWires, this.modulus.bitLength());
                     } else {
                        this.packedWire = new PackedValue(this.bitWires, UnsignedInteger.BITWIDTH_PER_CHUNK);
                     }
                  }
               }
            }
         }
      }

   }

   private void checkConstant() {
      if (this.packedWire != null && (this.constant = this.packedWire.getConstant(UnsignedInteger.BITWIDTH_PER_CHUNK)) != null) {
         if (this.constant.signum() == -1) {
            throw new IllegalArgumentException("Unisgned Integer is being instantiated from a negative constant");
         }
      } else if (this.bitWires != null) {
         boolean allConstant = true;
         BigInteger v = BigInteger.ZERO;
         int i = 0;
         Wire[] var7;
         int var6 = (var7 = this.bitWires.asArray()).length;

         for(int var5 = 0; var5 < var6; ++var5) {
            Wire w = var7[var5];
            if (!(w instanceof ConstantWire)) {
               allConstant = false;
               break;
            }

            ConstantWire constWire = (ConstantWire)w;
            if (!constWire.isBinary()) {
               throw new RuntimeException("Non-binary bit value used to construct an integer");
            }

            v = v.add(constWire.getConstant().multiply((new BigInteger("2")).pow(i++)));
         }

         if (allConstant) {
            this.constant = v;
         }
      }

   }

   public GroupElement mul(BigInteger b) {
      return this.mul(new GroupElement(this.modulus, b));
   }

   public static void handleOverflow(GroupElement x1, GroupElement x2, boolean isMul) {
      boolean longSetting = false;
      boolean adjusted = false;
      int maxBitWidth = x1.modulus.bitLength();
      if (maxBitWidth > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
         longSetting = true;
      }

      int b1Max = x1.packedWire.getBitwidthOfLargestChunk();
      int b2Max = x2.packedWire.getBitwidthOfLargestChunk();
      if (isMul) {
         if (longSetting) {
            if (b1Max + UnsignedInteger.BITWIDTH_PER_CHUNK >= Config.getNumBitsFiniteFieldModulus()) {
               x1.getBackInRange(false);
               adjusted = true;
            }

            if (b2Max + UnsignedInteger.BITWIDTH_PER_CHUNK >= Config.getNumBitsFiniteFieldModulus()) {
               x2.getBackInRange(false);
               adjusted = true;
            }
         } else {
            if (b1Max > Config.getNumBitsFiniteFieldModulus() - 2) {
               x1.getBackInRange(false);
               adjusted = true;
            }

            if (b2Max > Config.getNumBitsFiniteFieldModulus() - 2) {
               x2.getBackInRange(false);
               adjusted = true;
            }
         }
      } else {
         if (b1Max > Config.getNumBitsFiniteFieldModulus() - 2) {
            x1.getBackInRange(false);
            adjusted = true;
         }

         if (b2Max > Config.getNumBitsFiniteFieldModulus() - 2) {
            x2.getBackInRange(false);
            adjusted = true;
         }
      }

      if (!adjusted) {
         int excesss1 = x1.variableState.getMulUseCount() * (x1.currentBitwidth - maxBitWidth);
         int excesss2 = x2.variableState.getMulUseCount() * (x2.currentBitwidth - maxBitWidth);
         if (excesss1 > excesss2) {
            x1.getBackInRange(false);
         } else if (excesss2 < excesss1) {
            x2.getBackInRange(false);
         } else {
            excesss1 = x1.variableState.getAddUseCount() * (x1.currentBitwidth - maxBitWidth);
            excesss2 = x2.variableState.getAddUseCount() * (x2.currentBitwidth - maxBitWidth);
            if (excesss1 > excesss2) {
               x1.getBackInRange(false);
            } else if (excesss2 < excesss1) {
               x2.getBackInRange(false);
            } else if (x1.currentBitwidth - maxBitWidth > 0) {
               x1.getBackInRange(false);
            } else {
               x2.getBackInRange(false);
            }
         }

      }
   }

   private VariableState getVariableState() {
      return this.variableState;
   }

   public GroupElement mul(GroupElement o) {
      if (this.isConstant() && o.isConstant()) {
         return new GroupElement(this.modulus, this.getConstant().multiply(o.getConstant()));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ONE)) {
         CircuitGenerator.__getActiveCircuitGenerator().__retrieveVariableState();
         return o.copy();
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ONE)) {
         CircuitGenerator.__getActiveCircuitGenerator().__retrieveVariableState();
         return this.copy();
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ZERO)) {
         return new GroupElement(this.modulus, BigInteger.ZERO);
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         return new GroupElement(this.modulus, BigInteger.ZERO);
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         o.variableState.setPackedAhead(true);
         this.variableState.incMulUseCount();
         o.variableState.incMulUseCount();
         int idx1 = this.getVariableState().getMulIndex();
         int idx2 = o.getVariableState().getMulIndex();
         if (idx1 == 1 && this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            this.variableState.setConditionallySplittedAhead(true);
         }

         if (idx2 == 1 && o.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            o.variableState.setConditionallySplittedAhead(true);
         }

         GroupElement result = new GroupElement(this.generator, this.modulus);
         result.getVariableState().incMulIndex();
         return result;
      } else {
         this.maxValue.multiply(o.maxValue);
         this.variableState.decMulUseCount();
         o.variableState.decMulUseCount();
         if (!this.nativeSnarkField) {
            boolean overflowCheck = this.packedWire.mulOverflowCheck(o.packedWire);
            if (overflowCheck) {
               handleOverflow(this, o, true);
               overflowCheck = this.packedWire.mulOverflowCheck(o.packedWire);
               if (overflowCheck) {
                  handleOverflow(this, o, true);
               }
            }
         }

         BigInteger outMaxValue = this.maxValue.multiply(o.maxValue);
         if (this.nativeSnarkField) {
            outMaxValue = Util.min(outMaxValue, this.modulus.subtract(BigInteger.ONE));
         }

         PackedValue.disableOverflowChecks = true;
         GroupElement result = new GroupElement(this.modulus, this.packedWire.mul(o.packedWire), outMaxValue);
         PackedValue.disableOverflowChecks = false;
         return result;
      }
   }

   public GroupElement div(GroupElement b) {
      BigInteger aux;
      if (this.isConstant() && b.isConstant()) {
         aux = b.getConstant().modInverse(this.modulus).multiply(this.getConstant()).mod(this.modulus);
         return new GroupElement(this.modulus, aux);
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         int idx1 = this.getVariableState().getMulIndex();
         this.variableState.setConditionallySplittedAhead(true);
         this.variableState.setMustBeWithinRange(true);
         if (Config.enforceInternalDivisionNonZeroChecks) {
            b.variableState.setConditionallySplittedAndAlignedAhead(true);
            b.variableState.setMustBeWithinRange(true);
         }

         b.variableState.setPackedAhead(true);
         int idx2 = b.getVariableState().getMulIndex();
         if (idx2 == 1 && b.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            b.variableState.setConditionallySplittedAhead(true);
         }

         GroupElement result = new GroupElement(this.generator, this.modulus);
         return result;
      } else {
         if (Config.enforceInternalDivisionNonZeroChecks) {
            b.packedWire.forceNonZero();
         }

         if (this.nativeSnarkField) {
            PackedValue.disableOverflowChecks = true;
            FieldDivisionGadget f = new FieldDivisionGadget(this.packedWire.array[0], b.packedWire.array[0], new String[0]);
            PackedValue.disableOverflowChecks = false;
            return new GroupElement(this.modulus, new PackedValue(f.getOutputWires()[0], this.modulus.subtract(BigInteger.ONE)), this.modulus.subtract(BigInteger.ONE));
         } else {
            BigInteger[] auxChunks;
            boolean overflowCheck;
            PackedValue v;
            GroupElement result;
            if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
               aux = Util.computeMaxValue(b.modulus.bitLength());
               auxChunks = Util.split(aux, UnsignedInteger.BITWIDTH_PER_CHUNK);
               overflowCheck = b.packedWire.mulOverflowCheck(new PackedValue(auxChunks));
               if (overflowCheck) {
                  b.getBackInRange(false);
               }

               v = (new CustomLongFieldDivGadget(this.packedWire, b.packedWire, new PackedValue(Util.split(this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK)), new String[0])).getResult();
               result = new GroupElement(this.modulus, v, Util.computeMaxValue(this.modulus.bitLength()));
               return result;
            } else {
               aux = Util.computeMaxValue(this.modulus.bitLength());
               auxChunks = new BigInteger[]{aux};
               overflowCheck = b.packedWire.mulOverflowCheck(new PackedValue(auxChunks));
               if (overflowCheck) {
                  b.getBackInRange(false);
               }

               v = (new CustomShortFieldDivGadget(this.packedWire, b.packedWire, new PackedValue(new BigInteger[]{this.modulus}), new String[0])).getResult();
               result = new GroupElement(this.modulus, v, Util.computeMaxValue(this.modulus.bitLength()));
               return result;
            }
         }
      }
   }

   public GroupElement inv() {
      if (this.isConstant()) {
         return new GroupElement(this.modulus, this.getConstant().modInverse(this.modulus));
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         if (Config.enforceInternalDivisionNonZeroChecks) {
            this.variableState.setConditionallySplittedAndAlignedAhead(true);
            this.variableState.setMustBeWithinRange(true);
         }

         int idx1 = this.getVariableState().getMulIndex();
         if (idx1 == 1 && this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            this.variableState.setConditionallySplittedAhead(true);
         }

         GroupElement result = new GroupElement(this.generator, this.modulus);
         return result;
      } else {
         if (Config.enforceInternalDivisionNonZeroChecks) {
            this.packedWire.forceNonZero();
         }

         if (this.nativeSnarkField) {
            PackedValue.disableOverflowChecks = true;
            FieldDivisionGadget f = new FieldDivisionGadget(this.generator.__getOneWire(), this.packedWire.array[0], new String[0]);
            PackedValue.disableOverflowChecks = false;
            return new GroupElement(this.modulus, new PackedValue(f.getOutputWires()[0], this.modulus.subtract(BigInteger.ONE)), this.modulus.subtract(BigInteger.ONE));
         } else {
            BigInteger aux;
            BigInteger[] auxChunks;
            boolean overflowCheck;
            PackedValue v;
            GroupElement result;
            if (this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
               aux = Util.computeMaxValue(this.modulus.bitLength());
               auxChunks = Util.split(aux, UnsignedInteger.BITWIDTH_PER_CHUNK);
               overflowCheck = this.packedWire.mulOverflowCheck(new PackedValue(auxChunks));
               if (overflowCheck) {
                  this.getBackInRange(false);
               }

               v = (new InverseLongIntegerModGadget(this.packedWire, new PackedValue(Util.split(this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK)), new String[0])).getInverse();
               result = new GroupElement(this.modulus, v, Util.computeMaxValue(this.modulus.bitLength()));
               return result;
            } else {
               aux = Util.computeMaxValue(this.modulus.bitLength());
               auxChunks = new BigInteger[]{aux};
               overflowCheck = this.packedWire.mulOverflowCheck(new PackedValue(auxChunks));
               if (overflowCheck) {
                  this.getBackInRange(false);
               }

               v = (new ShortIntegerModGadget(this.packedWire, new PackedValue(new BigInteger[]{this.modulus}), new String[0])).getInverse();
               result = new GroupElement(this.modulus, v, Util.computeMaxValue(this.modulus.bitLength()));
               return result;
            }
         }
      }
   }

   public WireArray getBitWires() {
      if (this.generator.__getPhase() == 0) {
         this.variableState.setSplittedAhead(true);
         Wire[] result = new Wire[this.modulus.bitLength()];
         Arrays.fill(result, new BitWire(-1));
         return new WireArray(result);
      } else {
         return this.bitWires.adjustLength(this.modulus.bitLength());
      }
   }

   public Bit[] getBitElements() {
      Bit[] result;
      if (this.generator.__getPhase() == 0) {
         this.variableState.setSplittedAhead(true);
         result = new Bit[this.modulus.bitLength()];
         if (this.constant == null) {
            Arrays.fill(result, new Bit(new Wire(-1)));
         } else {
            for(int i = 0; i < this.modulus.bitLength(); ++i) {
               boolean b = this.constant.testBit(i);
               Wire w = b ? this.generator.__getOneWire() : this.generator.__getZeroWire();
               result[i] = new Bit(w);
            }
         }

         return result;
      } else {
         result = new Bit[this.modulus.bitLength()];
         WireArray array = this.bitWires.adjustLength(this.modulus.bitLength());

         for(int i = 0; i < this.modulus.bitLength(); ++i) {
            result[i] = new Bit(array.get(i));
         }

         return result;
      }
   }

   public boolean isProbablyOverflowed() {
      return this.maxValue.compareTo(this.modulus) >= 0;
   }

   private void getBackInRange(boolean strict) {
      if (this.modulus != null && !this.nativeSnarkField) {
         PackedValue modValue;
         if (this.maxValue.compareTo(this.modulus) >= 0 && this.maxValue.compareTo(Util.computeMaxValue(this.modulus.bitLength())) <= 0 && this.packedWire.isWitness()) {
            modValue = new PackedValue(Util.split(this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK));
            this.generator.__addOneAssertion(this.packedWire.isLessThan(modValue, this.maxValue.bitLength()).getWire());
         } else if (this.maxValue.compareTo(this.modulus) >= 0 && this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            Gadget modCGadget = new ModConstantGadget(this.packedWire.array[0], this.maxValue.bitLength(), this.modulus, strict, new String[0]);
            this.packedWire = new PackedValue(modCGadget.getOutputWires()[0], this.maxValue);
         } else if (this.maxValue.compareTo(this.modulus) >= 0 && this.modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            modValue = new PackedValue(Util.split(this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK));
            LongIntegerModConstantGadget g = new LongIntegerModConstantGadget(this.packedWire, modValue, strict, new String[0]);
            this.packedWire = g.getRemainder();
         }

         if (strict) {
            this.maxValue = this.modulus.subtract(BigInteger.ONE);
            this.currentBitwidth = this.maxValue.bitLength();
         } else {
            this.maxValue = Util.computeMaxValue(this.modulus.bitLength());
            this.currentBitwidth = this.maxValue.bitLength();
         }
      }

   }

   public boolean isConstant() {
      return this.constant != null;
   }

   public PackedValue getPackedWire() {
      if (this.packedWire == null && this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         return new PackedValue(new Wire(-1), this.modulus.bitLength());
      } else {
         return this.packedWire;
      }
   }

   public BigInteger getMaxValue() {
      return this.maxValue;
   }

   public int getRequiredBitWidth() {
      return this.modulus.bitLength();
   }

   public BigInteger getModulus() {
      return this.modulus;
   }

   public int getCurrentBitWidth() {
      return this.currentBitwidth;
   }

   public Wire[] toWires() {
      return this.packedWire != null ? this.packedWire.getArray() : null;
   }

   public void mapValue(BigInteger value, CircuitEvaluator evaluator) {
      if (this.packedWire != null) {
         if (this.nativeSnarkField) {
            evaluator.setWireValue(this.packedWire.array[0], value);
         } else {
            evaluator.setWireValue(this.packedWire, value, this.modulus.bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
         }
      } else {
         int length = this.bitWires.size();

         for(int i = 0; i < length; ++i) {
            evaluator.setWireValue(this.bitWires.get(i), value.testBit(i) ? BigInteger.ONE : BigInteger.ZERO);
         }
      }

   }

   public BigInteger getValueFromEvaluator(CircuitEvaluator evaluator) {
      if (this.packedWire != null) {
         return evaluator.getWireValue(this.packedWire, UnsignedInteger.BITWIDTH_PER_CHUNK);
      } else {
         BigInteger s = BigInteger.ZERO;
         BigInteger powerTwo = BigInteger.ONE;
         int length = this.bitWires.size();

         for(int i = 0; i < length; ++i) {
            s = s.add(powerTwo.multiply(evaluator.getWireValue(this.bitWires.get(i))));
            powerTwo = powerTwo.add(powerTwo);
         }

         return s;
      }
   }

   public static GroupElement createInput(CircuitGenerator generator, BigInteger modulus, String... desc) {
      PackedValue v;
      if (modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !modulus.equals(Config.getFiniteFieldModulus())) {
         int numChunks = (int)Math.ceil((double)modulus.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         Wire[] w = generator.__createInputWireArray(numChunks);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
         if (numChunks * UnsignedInteger.BITWIDTH_PER_CHUNK != modulus.bitLength()) {
            bitwidths[numChunks - 1] = modulus.bitLength() % UnsignedInteger.BITWIDTH_PER_CHUNK;
         }

         v = new PackedValue(w, bitwidths);
      } else {
         Wire w = generator.__createInputWire(desc);
         v = new PackedValue(w, modulus.bitLength());
      }

      GroupElement o = new GroupElement(modulus, v, modulus.subtract(BigInteger.ONE));
      generator.__getInputAux().add(o.copy());
      return o;
   }

   public static GroupElement createWitness(CircuitGenerator generator, BigInteger modulus, String... desc) {
      PackedValue v;
      if (modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !modulus.equals(Config.getFiniteFieldModulus())) {
         int numChunks = (int)Math.ceil((double)modulus.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         Wire[] w = generator.__createProverWitnessWireArray(numChunks);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
         if (numChunks * UnsignedInteger.BITWIDTH_PER_CHUNK != modulus.bitLength()) {
            bitwidths[numChunks - 1] = modulus.bitLength() % UnsignedInteger.BITWIDTH_PER_CHUNK;
         }

         v = new PackedValue(w, bitwidths);
      } else {
         Wire w = generator.__createProverWitnessWire(desc);
         v = new PackedValue(w, modulus.bitLength());
      }

      GroupElement o = new GroupElement(modulus, v, modulus.subtract(BigInteger.ONE));
      generator.__getProverAux().add(o.copy());
      return o;
   }

   public static GroupElement createVerifiedWitness(CircuitGenerator generator, BigInteger modulus, String... desc) {
      PackedValue v;
      if (modulus.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT && !modulus.equals(Config.getFiniteFieldModulus())) {
         int numChunks = (int)Math.ceil((double)modulus.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
         Wire[] w = generator.__createProverWitnessWireArray(numChunks);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
         if (numChunks * UnsignedInteger.BITWIDTH_PER_CHUNK != modulus.bitLength()) {
            bitwidths[numChunks - 1] = modulus.bitLength() % UnsignedInteger.BITWIDTH_PER_CHUNK;
         }

         v = new PackedValue(w, bitwidths);
      } else {
         Wire w = generator.__createProverWitnessWire(desc);
         v = new PackedValue(w, modulus.bitLength());
      }

      GroupElement o = new GroupElement(modulus, v, modulus.subtract(BigInteger.ONE));
      generator.__getVerifiedProverAux().add(o.copy());
      return o;
   }

   public void verifyRange() {
      if (!this.modulus.equals(Config.getFiniteFieldModulus())) {
         if (this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
            this.packedWire.array[0].restrictBitLength(this.modulus.bitLength());
            this.generator.__addOneAssertion(this.packedWire.array[0].isLessThan(this.modulus, this.modulus.bitLength() + 1));
         } else {
            int numChunks = (int)Math.ceil((double)this.modulus.bitLength() * 1.0 / (double)UnsignedInteger.BITWIDTH_PER_CHUNK);
            int[] bitwidths = new int[numChunks];
            Arrays.fill(bitwidths, UnsignedInteger.BITWIDTH_PER_CHUNK);
            if (numChunks * UnsignedInteger.BITWIDTH_PER_CHUNK != this.modulus.bitLength()) {
               bitwidths[numChunks - 1] = this.modulus.bitLength() % UnsignedInteger.BITWIDTH_PER_CHUNK;
            }

            for(int i = 0; i < numChunks; ++i) {
               this.packedWire.array[i].restrictBitLength(bitwidths[numChunks - 1]);
            }

            this.generator.__addOneAssertion(this.packedWire.isLessThan(new PackedValue(Util.split(this.modulus, UnsignedInteger.BITWIDTH_PER_CHUNK)), this.modulus.bitLength() + 1).wire);
         }

      }
   }

   public static GroupElement[] createZeroArray(CircuitGenerator generator, int size, BigInteger modulus, String... desc) {
      GroupElement[] out = new GroupElement[size];

      for(int i = 0; i < size; ++i) {
         out[i] = new GroupElement(modulus, BigInteger.ZERO);
      }

      return out;
   }

   public static Object createZeroArray(CircuitGenerator generator, int[] dims, BigInteger modulus, String... desc) {
      if (dims.length == 1) {
         return createZeroArray(generator, dims[0], modulus, desc);
      } else {
         int i;
         if (dims.length == 2) {
            GroupElement[][] out = new GroupElement[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createZeroArray(generator, dims[1], modulus, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dim arrays not supported at this point");
         } else {
            GroupElement[][][] out = new GroupElement[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createZeroArray(generator, dims[2], modulus, desc);
               }
            }

            return out;
         }
      }
   }

   public static GroupElement[] createInputArray(CircuitGenerator generator, int size, BigInteger modulus, String... desc) {
      GroupElement[] out = new GroupElement[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createInput(generator, modulus, desc);
      }

      return out;
   }

   public static GroupElement[] createWitnessArray(CircuitGenerator generator, int size, BigInteger modulus, String... desc) {
      GroupElement[] out = new GroupElement[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createWitness(generator, modulus, desc);
      }

      return out;
   }

   public static GroupElement[] createVerifiedWitnessArray(CircuitGenerator generator, int size, BigInteger modulus, String... desc) {
      GroupElement[] out = new GroupElement[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createVerifiedWitness(generator, modulus, desc);
      }

      return out;
   }

   public void makeOutput(String... desc) {
      if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         this.variableState.setConditionallySplittedAhead(true);
         this.variableState.setMustBeWithinRange(true);
         this.variableState.setConditionallySplittedAndAlignedAhead(true);
      } else {
         this.generator.__makeOutputArray(this.packedWire.array, desc);
      }

   }

   public static void makeOutput(CircuitGenerator generator, GroupElement x, String... desc) {
      x.makeOutput();
   }

   public static void makeOutput(CircuitGenerator generator, GroupElement[] a, String... desc) {
      GroupElement[] var6 = a;
      int var5 = a.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         GroupElement x = var6[var4];
         x.makeOutput();
      }

   }

   public static Object createInputArray(CircuitGenerator generator, int[] dims, BigInteger modulus, String... desc) {
      if (dims.length == 1) {
         return createInputArray(generator, dims[0], modulus, desc);
      } else {
         int i;
         if (dims.length == 2) {
            GroupElement[][] out = new GroupElement[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createInputArray(generator, dims[1], modulus, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            GroupElement[][][] out = new GroupElement[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createInputArray(generator, dims[2], modulus, desc);
               }
            }

            return out;
         }
      }
   }

   public static Object createWitnessArray(CircuitGenerator generator, int[] dims, BigInteger modulus, String... desc) {
      if (dims.length == 1) {
         return createWitnessArray(generator, dims[0], modulus, desc);
      } else {
         int i;
         if (dims.length == 2) {
            GroupElement[][] out = new GroupElement[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createWitnessArray(generator, dims[1], modulus, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            GroupElement[][][] out = new GroupElement[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createWitnessArray(generator, dims[2], modulus, desc);
               }
            }

            return out;
         }
      }
   }

   public static Object createVerifiedWitnessArray(CircuitGenerator generator, int[] dims, BigInteger modulus, String... desc) {
      if (dims.length == 1) {
         return createVerifiedWitnessArray(generator, dims[0], modulus, desc);
      } else {
         int i;
         if (dims.length == 2) {
            GroupElement[][] out = new GroupElement[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createVerifiedWitnessArray(generator, dims[1], modulus, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            GroupElement[][][] out = new GroupElement[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createVerifiedWitnessArray(generator, dims[2], modulus, desc);
               }
            }

            return out;
         }
      }
   }

   public static void makeOutput(CircuitGenerator generator, Object a, String... desc) {
      int i;
      if (a instanceof GroupElement[]) {
         GroupElement[] array = (GroupElement[])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, array[i], desc);
         }
      } else if (a instanceof GroupElement[][]) {
         GroupElement[][] array = (GroupElement[][])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, array[i], desc);
         }
      } else {
         if (!(a instanceof GroupElement[][][])) {
            throw new IllegalArgumentException("Declaring higher dimensional arrays as outputs not supported at this point. Only 3 dimensions are supported");
         }

         GroupElement[][][] array = (GroupElement[][][])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, (Object)array[i], desc);
         }
      }

   }

   public void mapRandomValue(CircuitEvaluator evaluator) {
      BigInteger rnd = Util.nextRandomBigInteger(this.modulus);
      if (this.packedWire != null) {
         evaluator.setWireValue(this.packedWire, rnd, this.modulus.bitLength(), UnsignedInteger.BITWIDTH_PER_CHUNK);
      } else {
         int length = this.bitWires.size();

         for(int i = 0; i < length; ++i) {
            evaluator.setWireValue(this.bitWires.get(i), rnd.testBit(i) ? BigInteger.ONE : BigInteger.ZERO);
         }
      }

   }

   public GroupElement mul(Bit bit) {
      return this.mul(new GroupElement(this.modulus, new PackedValue(bit.wire, BigInteger.ONE), BigInteger.ONE));
   }

   public void forceEqual(IAuxType o) {
      if (!(o instanceof GroupElement)) {
         throw new IllegalArgumentException("FieldType expected");
      } else {
         GroupElement other = (GroupElement)o;
         if (this.getConstant() != null && other.getConstant() != null && !this.getConstant().equals(other.getConstant())) {
            throw new RuntimeException("Constraint fails on constant uints");
         } else {
            if (this.generator.__getPhase() == 0) {
               this.variableState.setPackedAhead(true);
               other.variableState.setPackedAhead(true);
               this.variableState.setConditionallySplittedAhead(true);
               other.variableState.setConditionallySplittedAhead(true);
               this.variableState.setMustBeWithinRange(true);
               other.variableState.setMustBeWithinRange(true);
            } else if (this.nativeSnarkField || this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT && other.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
               this.generator.__addEqualityAssertion(this.packedWire.array[0], other.packedWire.array[0]);
            } else {
               this.packedWire.forceEquality2(other.packedWire);
            }

         }
      }
   }

   public static Class<?> __getClassRef() {
      return GroupElement.class;
   }

   public VariableState getState() {
      return this.variableState;
   }

   public PackedValue getPackedValue() {
      return this.packedWire;
   }

   public Bit isEqualTo(IAuxType o) {
      if (!(o instanceof GroupElement)) {
         throw new IllegalArgumentException("UnsignedINT expected");
      } else if (this == o) {
         return new Bit(true);
      } else {
         GroupElement other = (GroupElement)o;
         if (this.getConstant() != null && other.getConstant() != null) {
            return new Bit(this.getConstant().equals(other.getConstant()));
         } else if (this.generator.__getPhase() == 0) {
            this.variableState.setPackedAhead(true);
            other.variableState.setPackedAhead(true);
            this.variableState.setConditionallySplittedAndAlignedAhead(true);
            other.variableState.setConditionallySplittedAndAlignedAhead(true);
            return new Bit(new Wire(-1));
         } else {
            return this.nativeSnarkField || this.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT && other.modulus.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT ? new Bit(this.packedWire.array[0].isEqualTo(other.packedWire.array[0])) : this.packedWire.isEqualTo(other.packedWire);
         }
      }
   }

   public Bit isNotEqualTo(IAuxType o) {
      return this.isEqualTo(o).inv();
   }

   public static GroupElement instantiateFrom(BigInteger modulus, int v) {
      return new GroupElement(modulus, BigInteger.valueOf((long)v));
   }

   public static GroupElement instantiateFrom(BigInteger modulus, long v) {
      return new GroupElement(modulus, BigInteger.valueOf(v));
   }

   public static GroupElement instantiateFrom(BigInteger modulus, BigInteger v) {
      return new GroupElement(modulus, v);
   }

   public static GroupElement instantiateFrom(BigInteger modulus, GroupElement e) {
      return modulus.equals(e.getModulus()) ? e.copy() : instantiateFrom(modulus, UnsignedInteger.instantiateFrom(e.getModulus().bitLength(), e));
   }

   public static GroupElement instantiateFrom(BigInteger modulus, UnsignedInteger e) {
      if (e.isConstant()) {
         return new GroupElement(modulus, e.getConstant());
      } else {
         CircuitGenerator generator = CircuitGenerator.__getActiveCircuitGenerator();
         GroupElement result;
         if (generator.__getPhase() == 0) {
            e.getState().setConditionallySplittedAhead(true);
            e.getState().setMustBeWithinRange(true);
            e.getState().setPackedAhead(true);
            result = new GroupElement(generator, modulus);
            return result;
         } else if (!modulus.equals(Config.getFiniteFieldModulus())) {
            if (e.maxValue.compareTo(modulus) >= 0 && e.maxValue.bitLength() <= UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
               Gadget modCGadget = new ModConstantGadget(e.packedWire.array[0], e.maxValue.bitLength(), modulus, true, new String[0]);
               GroupElement result = new GroupElement(modulus, new PackedValue(modCGadget.getOutputWires()[0], modulus.subtract(BigInteger.ONE)));
               return result;
            } else if (e.maxValue.compareTo(modulus) >= 0 && e.maxValue.bitLength() > UnsignedInteger.BITWIDTH_LIMIT_SHORT) {
               PackedValue modValue = new PackedValue(Util.split(modulus, UnsignedInteger.BITWIDTH_PER_CHUNK));
               LongIntegerModConstantGadget g = new LongIntegerModConstantGadget(e.packedWire, modValue, true, new String[0]);
               PackedValue remainder = g.getRemainder();
               BigInteger[] maxVals = new BigInteger[remainder.array.length];
               Arrays.fill(maxVals, Util.computeMaxValue(UnsignedInteger.BITWIDTH_PER_CHUNK));
               if (remainder.array.length * UnsignedInteger.BITWIDTH_PER_CHUNK != modulus.bitLength()) {
                  maxVals[remainder.array.length - 1] = Util.computeMaxValue(modulus.bitLength() % UnsignedInteger.BITWIDTH_PER_CHUNK);
               }

               if (maxVals.length == 1) {
                  maxVals[0] = modulus.subtract(BigInteger.ONE);
               }

               GroupElement result = new GroupElement(modulus, new PackedValue(g.getRemainder().array, maxVals), modulus.subtract(BigInteger.ONE));
               return result;
            } else {
               result = new GroupElement(modulus, e.packedWire, e.maxValue);
               return result;
            }
         } else {
            Wire w = e.packedWire.array[0];

            for(int i = 1; i < e.packedWire.array.length; ++i) {
               w = w.add(e.packedWire.array[i].mul(2L, i * UnsignedInteger.BITWIDTH_PER_CHUNK));
            }

            BigInteger maxVal = modulus.subtract(BigInteger.ONE);
            if (e.maxValue.compareTo(maxVal) < 0) {
               maxVal = e.maxValue;
            }

            GroupElement result = new GroupElement(modulus, new PackedValue(w, maxVal), maxVal);
            return result;
         }
      }
   }

   public static GroupElement instantiateFrom(BigInteger modulus, String v) {
      return new GroupElement(modulus, new BigInteger(v));
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, int[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, byte[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, long[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, BigInteger[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, String[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, GroupElement[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public static GroupElement[] instantiateFrom(BigInteger modulus, UnsignedInteger[] v) {
      GroupElement[] a = new GroupElement[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(modulus, v[i]);
      }

      return a;
   }

   public boolean isNativeSnarkField() {
      return this.nativeSnarkField;
   }
}
