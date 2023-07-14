package backend.auxTypes;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.resource.ResourceBundle;
import backend.structure.BitWire;
import backend.structure.CircuitGenerator;
import backend.structure.ConstantWire;
import backend.structure.Wire;
import backend.structure.WireArray;
import examples.gadgets.LongIntegerModConstantGadget;
import examples.gadgets.LongIntegerModNotStrictModulusGadget;
import examples.gadgets.ModConstantGadget;
import examples.gadgets.ModGadget;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import util.Util;

public class UnsignedInteger implements IAuxType, ConditionalScopeImpactedType {
   public static int BITWIDTH_PER_CHUNK = 32;
   public static int BITWIDTH_LIMIT_SHORT = Config.getNumBitsFiniteFieldModulus() / 2 - 2;
   protected PackedValue packedWire;
   protected WireArray bitWires;
   protected int currentBitwidth;
   protected int bitWidth;
   protected BigInteger maxValue;
   protected BigInteger constant;
   protected CircuitGenerator generator;
   protected VariableState variableState;
   protected boolean packedAtCreationTime;
   protected boolean splittedAtCreationTime;
   protected int scope;
   protected Stack<HashMap<Integer, UnsignedInteger>> possibleValStack;
   protected Stack<UnsignedInteger> prevValStack;
   private boolean stateChanged;
   protected int internalStateSerial;

   public void setConditionalScopeId(int id) {
      this.scope = id;
   }

   public UnsignedInteger(UnsignedInteger o) {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
      this.generator = o.generator;
      this.maxValue = o.maxValue;
      this.constant = o.constant;
      this.variableState = o.variableState;
      this.currentBitwidth = o.currentBitwidth;
      this.bitWires = o.bitWires;
      this.packedWire = o.packedWire;
      this.scope = o.scope;
      this.bitWidth = o.bitWidth;
   }

   public void assign(UnsignedInteger target, int expectedBitwidth) {
      ++this.internalStateSerial;
      int current;
      int i;
      if (this.generator.__getPhase() == 0) {
         if (target.bitWidth < expectedBitwidth) {
            target.variableState.setConditionallySplittedAhead(true);
         }

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
               UnsignedInteger c = this.copy();
               c.variableState.setPackedAhead(true);
               this.prevValStack.push(c);
               this.possibleValStack.push(new HashMap());
            }

            this.stateChanged = true;
            this.scope = ConditionalScopeTracker.getCurrentScopeId();
         }

         this.constant = target.constant;
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

         this.bitWidth = target.bitWidth;
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
      ++this.internalStateSerial;
      if (this.stateChanged) {
         UnsignedInteger copy = this.copy();
         if (this.generator.__getPhase() == 0) {
            copy.variableState.setPackedAhead(true);
         }

         ((HashMap)this.possibleValStack.peek()).put(id, copy);
         --this.scope;
         UnsignedInteger prev = (UnsignedInteger)this.prevValStack.peek();
         this.packedWire = prev.packedWire;
         this.variableState = prev.variableState;
         this.bitWires = prev.bitWires;
         this.maxValue = prev.maxValue;
         this.constant = prev.constant;
         this.bitWidth = prev.bitWidth;
         this.currentBitwidth = prev.currentBitwidth;
         this.stateChanged = false;
      }
   }

   public void popMain() {
      ++this.internalStateSerial;
      if (this.generator.__getPhase() == 0) {
         this.variableState = this.generator.__retrieveVariableState();
         this.variableState.setPackedAhead(true);
         HashMap<Integer, UnsignedInteger> possibleVals = (HashMap)this.possibleValStack.pop();
         int mulIndex = ((UnsignedInteger)this.prevValStack.pop()).getVariableState().getMulIndex();
         Iterator var4 = possibleVals.keySet().iterator();

         while(var4.hasNext()) {
            Integer idx = (Integer)var4.next();
            int m = ((UnsignedInteger)possibleVals.get(idx)).variableState.getMulIndex();
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
         UnsignedInteger[] candidateList = new UnsignedInteger[numberOfValues];
         HashMap<Integer, UnsignedInteger> possibleVals = (HashMap)this.possibleValStack.pop();

         Integer idx;
         for(Iterator var8 = possibleVals.keySet().iterator(); var8.hasNext(); candidateList[idx] = (UnsignedInteger)possibleVals.get(idx)) {
            idx = (Integer)var8.next();
         }

         for(int i = 0; i < numberOfValues; ++i) {
            if (candidateList[i] == null) {
               candidateList[i] = this.copy();
            }
         }

         UnsignedInteger initial = candidateList[numberOfValues - 1];
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
            this.bitWidth = initial.bitWidth;
            this.constant = initial.constant;
         } else {
            this.bitWidth = initial.bitWidth;
            this.packedWire = initial.packedWire;
            this.maxValue = initial.maxValue;
            this.bitWires = initial.bitWires;
            this.currentBitwidth = initial.currentBitwidth;

            for(int j = startingIndex; j >= 0; --j) {
               UnsignedInteger current = candidateList[j];
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

   public UnsignedInteger copy() {
      return this.copy(this.bitWidth);
   }

   public UnsignedInteger copy(int expectedBitwidth) {
      UnsignedInteger v;
      if (this.generator.__getPhase() == 0) {
         if (this.bitWidth < expectedBitwidth) {
            this.variableState.setConditionallySplittedAhead(true);
         } else if (expectedBitwidth < this.bitWidth) {
            this.variableState.setThresholdBitwidth(expectedBitwidth);
         }

         v = new UnsignedInteger(this.generator, expectedBitwidth, this.variableState);
         v.constant = this.constant;
         v.packedWire = this.packedWire;
         v.bitWires = this.bitWires;
         v.maxValue = this.maxValue;
         v.currentBitwidth = this.currentBitwidth;
         return v;
      } else {
         v = new UnsignedInteger();
         v.bitWidth = expectedBitwidth;
         if (this.bitWires != null) {
            v.bitWires = this.bitWires.adjustLength(expectedBitwidth);
         }

         v.splittedAtCreationTime = this.splittedAtCreationTime;
         v.packedAtCreationTime = this.packedAtCreationTime;
         v.constant = this.constant;
         if (expectedBitwidth < this.bitWidth && this.bitWires != null) {
            if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
               v.packedWire = new PackedValue(this.bitWires.adjustLength(expectedBitwidth), expectedBitwidth);
            } else {
               v.packedWire = new PackedValue(this.bitWires.adjustLength(expectedBitwidth), BITWIDTH_PER_CHUNK);
            }

            if (this.constant == null) {
               v.currentBitwidth = expectedBitwidth;
               v.maxValue = Util.computeMaxValue(expectedBitwidth);
            } else {
               v.currentBitwidth = this.constant.bitLength();
               v.maxValue = this.constant;
            }
         } else {
            v.packedWire = this.packedWire;
            v.currentBitwidth = this.currentBitwidth;
            v.maxValue = this.maxValue;
         }

         v.variableState = this.variableState;
         v.generator = this.generator;
         return v;
      }
   }

   public UnsignedInteger() {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
   }

   public UnsignedInteger(int bitWidth, PackedValue packedWire, BigInteger maxValue) {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.packedWire = packedWire;
      this.bitWidth = bitWidth;
      this.maxValue = maxValue;
      this.currentBitwidth = maxValue == null ? bitWidth : maxValue.bitLength();
      this.init();
   }

   public UnsignedInteger(int bitWidth, WireArray bitWires) {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.bitWires = bitWires;
      this.bitWidth = bitWidth;
      this.currentBitwidth = bitWires.size();
      if (this.currentBitwidth > bitWidth) {
         this.bitWires = new WireArray((Wire[])Arrays.copyOfRange(bitWires.asArray(), 0, bitWidth));
         this.currentBitwidth = bitWidth;
      }

      this.init();
   }

   public UnsignedInteger(int bitWidth, PackedValue packedWire) {
      this(bitWidth, packedWire, (BigInteger)null);
   }

   public UnsignedInteger(int bitWidth, PackedValue packedWire, int initialNumOfBits) {
      this(bitWidth, packedWire, (BigInteger)null);
   }

   public UnsignedInteger(int bitWidth, BigInteger constant) {
      this(bitWidth, CircuitGenerator.__getActiveCircuitGenerator().__createConstantPackedValue(Util.prepConstant(constant, bitWidth), bitWidth));
   }

   public UnsignedInteger(BigInteger constant) {
      this(constant.bitLength() + (constant.signum() == -1 ? 1 : 0), constant);
   }

   public UnsignedInteger(int bitWidth, long constant) {
      this(bitWidth, BigInteger.valueOf(constant));
   }

   public UnsignedInteger(CircuitGenerator generator, int bitWidth) {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
      this.bitWidth = bitWidth;
      this.generator = generator;
      this.currentBitwidth = bitWidth;
      this.init();
   }

   public UnsignedInteger(int bitWidth) {
      this(CircuitGenerator.__getActiveCircuitGenerator(), bitWidth);
   }

   private UnsignedInteger(CircuitGenerator generator, int bitWidth, VariableState st) {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
      this.bitWidth = bitWidth;
      this.generator = generator;
      this.variableState = st;
      this.currentBitwidth = bitWidth;
   }

   private UnsignedInteger(CircuitGenerator generator, int bitWidth, BigInteger constant, VariableState st) {
      this.scope = ConditionalScopeTracker.getCurrentScopeId();
      this.internalStateSerial = 0;
      this.bitWidth = bitWidth;
      this.generator = generator;
      this.variableState = st;
      this.currentBitwidth = bitWidth;
      this.constant = constant;
   }

   private void init() {
      this.checkConstant();
      if (this.variableState == null) {
         this.variableState = this.generator.__retrieveVariableState();
      }

      if (this.maxValue == null) {
         this.maxValue = ResourceBundle.getInstance().getBigInteger((new BigInteger("2")).pow(this.bitWidth).subtract(BigInteger.ONE));
         this.currentBitwidth = this.bitWidth;
      }

      if (this.generator.__getPhase() == 1) {
         if (this.constant != null) {
            this.packedWire = this.generator.__createConstantPackedValue(this.maxValue, this.bitWidth);
            this.bitWires = this.packedWire.getBits(this.bitWidth, BITWIDTH_PER_CHUNK);
         } else if (this.variableState != null) {
            if (this.variableState.isPackedAhead() && this.packedWire == null) {
               WireArray modified = this.bitWires.adjustLength(Math.min(this.bitWires.size(), this.bitWidth));
               if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                  this.packedWire = new PackedValue(modified, this.bitWidth);
               } else {
                  this.packedWire = new PackedValue(modified, BITWIDTH_PER_CHUNK);
               }

               this.maxValue = modified.computeTightUpperBoundOfBitWires(this.bitWidth);
               this.currentBitwidth = this.maxValue.bitLength();
            } else if (this.variableState.isSplittedAhead() && this.bitWires == null) {
               if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                  this.bitWires = this.packedWire.getBits(this.bitWidth, this.bitWidth);
               } else {
                  this.bitWires = this.packedWire.getBits(this.bitWidth, BITWIDTH_PER_CHUNK);
               }

               if ((this.packedWire.getMaxVal(BITWIDTH_PER_CHUNK).bitLength() > this.bitWidth || !this.packedWire.isAligned()) && this.variableState.isPackedAhead()) {
                  if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                     this.packedWire = new PackedValue(this.bitWires, this.bitWidth);
                  } else {
                     this.packedWire = new PackedValue(this.bitWires, BITWIDTH_PER_CHUNK);
                  }
               }

               this.maxValue = this.bitWires.computeTightUpperBoundOfBitWires(this.bitWidth);
               this.currentBitwidth = this.maxValue.bitLength();
            } else if ((this.variableState.isConditionallySplittedAhead() || this.variableState.isConditionallySplittedAndAlignedAhead()) && this.bitWires == null) {
               this.currentBitwidth = this.maxValue.bitLength();
               if (this.currentBitwidth > this.bitWidth) {
                  if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                     this.bitWires = this.packedWire.getBits(this.bitWidth, this.bitWidth);
                  } else {
                     this.bitWires = this.packedWire.getBits(this.bitWidth, BITWIDTH_PER_CHUNK);
                  }

                  if (this.variableState.isPackedAhead()) {
                     if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                        this.packedWire = new PackedValue(this.bitWires, this.bitWidth);
                     } else {
                        this.packedWire = new PackedValue(this.bitWires, BITWIDTH_PER_CHUNK);
                     }
                  }

                  this.maxValue = this.bitWires.computeTightUpperBoundOfBitWires(this.bitWidth);
                  this.currentBitwidth = this.maxValue.bitLength();
               } else if (this.packedWire != null && !this.packedWire.isAligned() && this.bitWidth > BITWIDTH_LIMIT_SHORT && this.variableState.isConditionallySplittedAndAlignedAhead()) {
                  this.packedWire = this.packedWire.align((int)Math.ceil((double)this.bitWidth * 1.0 / (double)BITWIDTH_PER_CHUNK), BITWIDTH_PER_CHUNK);
               } else if (this.packedWire == null && this.variableState.isConditionallySplittedAndAlignedAhead()) {
                  throw new RuntimeException("Case Unexpected");
               }
            } else if (this.bitWires != null && this.bitWires.size() > this.bitWidth) {
               this.bitWires = new WireArray((Wire[])Arrays.copyOfRange(this.bitWires.asArray(), 0, this.bitWidth));
               if (this.variableState.isPackedAhead()) {
                  if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                     this.packedWire = new PackedValue(this.bitWires, this.bitWidth);
                  } else {
                     this.packedWire = new PackedValue(this.bitWires, BITWIDTH_PER_CHUNK);
                  }
               }

               this.maxValue = this.bitWires.computeTightUpperBoundOfBitWires(this.bitWidth);
               this.currentBitwidth = this.maxValue.bitLength();
            }

            if (this.variableState.getThresholdBitwidth() != -1 && this.bitWires == null) {
               this.currentBitwidth = this.maxValue.bitLength();
               if (this.currentBitwidth > this.variableState.getThresholdBitwidth()) {
                  if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                     this.bitWires = this.packedWire.getBits(this.bitWidth, this.bitWidth);
                  } else {
                     this.bitWires = this.packedWire.getBits(this.bitWidth, BITWIDTH_PER_CHUNK);
                  }

                  this.maxValue = this.bitWires.computeTightUpperBoundOfBitWires(this.bitWidth);
                  this.currentBitwidth = this.maxValue.bitLength();
                  if ((this.packedWire.getMaxVal(BITWIDTH_PER_CHUNK).bitLength() > this.bitWidth || !this.packedWire.isAligned()) && this.variableState.isPackedAhead()) {
                     if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
                        this.packedWire = new PackedValue(this.bitWires, this.bitWidth);
                     } else {
                        this.packedWire = new PackedValue(this.bitWires, BITWIDTH_PER_CHUNK);
                     }
                  }
               }
            }
         }
      }

   }

   private void checkConstant() {
      if (this.constant == null) {
         if (this.packedWire != null) {
            this.constant = this.packedWire.getConstant(BITWIDTH_PER_CHUNK);
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

      if (this.constant != null) {
         this.constant = Util.prepConstant(this.constant, this.bitWidth);
         this.maxValue = this.constant;
         this.currentBitwidth = this.constant.bitLength();
      }

   }

   public UnsignedInteger promote(int numBits) {
      if (this.bitWidth > numBits) {
         throw new IllegalArgumentException("The given integer is less in bitwidth");
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setConditionallySplittedAhead(true);
         this.variableState.setPackedAhead(true);
         return new UnsignedInteger(this.generator, numBits);
      } else {
         return new UnsignedInteger(numBits, this.packedWire);
      }
   }

   public UnsignedInteger add(BigInteger b) {
      return this.add(new UnsignedInteger(b));
   }

   public UnsignedInteger mul(BigInteger b) {
      return this.mul(new UnsignedInteger(b));
   }

   public UnsignedInteger mul(Bit b) {
      return this.mul(new UnsignedInteger(1, new PackedValue(b.wire, 1)));
   }

   public UnsignedInteger add(Bit b) {
      return this.add(new UnsignedInteger(1, new PackedValue(b.wire, 1)));
   }

   public UnsignedInteger xorBitwise(BigInteger b) {
      return this.xorBitwise(new UnsignedInteger(b));
   }

   public UnsignedInteger orBitwise(BigInteger b) {
      return this.orBitwise(new UnsignedInteger(b));
   }

   public UnsignedInteger andBitwise(BigInteger b) {
      return this.andBitwise(new UnsignedInteger(b));
   }

   public UnsignedInteger add(UnsignedInteger o) {
      int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
      if (this.isConstant() && o.isConstant()) {
         return new UnsignedInteger(maxBitWidth, this.getConstant().add(o.getConstant()));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ZERO)) {
         CircuitGenerator.__getActiveCircuitGenerator().__retrieveVariableState();
         return o.copy();
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         CircuitGenerator.__getActiveCircuitGenerator().__retrieveVariableState();
         return this.copy();
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         o.variableState.setPackedAhead(true);
         this.variableState.incAddUseCount();
         o.variableState.incAddUseCount();
         if (o.bitWidth < this.bitWidth) {
            o.variableState.setConditionallySplittedAhead(true);
         } else if (o.bitWidth > this.bitWidth) {
            this.variableState.setConditionallySplittedAhead(true);
         }

         UnsignedInteger result = new UnsignedInteger(this.generator, Math.max(this.bitWidth, o.bitWidth));
         result.getVariableState().setMulIndex(Math.max(o.getVariableState().getMulIndex(), this.variableState.getMulIndex()));
         result.getVariableState().setOptimizationAttributes(this.variableState, o.variableState, false, maxBitWidth);
         return result;
      } else {
         this.variableState.decAddUseCount();
         o.variableState.decAddUseCount();
         int newBitWidth = Math.max(this.bitWidth, o.bitWidth);
         boolean overflowCheck = this.packedWire.addOverflowCheck(o.packedWire);
         if (overflowCheck) {
            handleOverflow(this, o, false);
            overflowCheck = this.packedWire.addOverflowCheck(o.packedWire);
            if (overflowCheck) {
               handleOverflow(this, o, false);
            }
         }

         BigInteger outMaxValue = this.maxValue.add(o.maxValue);
         UnsignedInteger result = new UnsignedInteger(newBitWidth, this.packedWire.add(o.packedWire), outMaxValue);
         return result;
      }
   }

   public static void handleOverflow(UnsignedInteger x1, UnsignedInteger x2, boolean isMul) {
      boolean longSetting = false;
      boolean adjusted = false;
      int maxBitWidth = Math.max(x1.bitWidth, x2.bitWidth);
      if (maxBitWidth > BITWIDTH_LIMIT_SHORT) {
         longSetting = true;
      }

      int b1Max = x1.packedWire.getBitwidthOfLargestChunk();
      int b2Max = x2.packedWire.getBitwidthOfLargestChunk();
      if (isMul) {
         if (longSetting) {
            if (b1Max + BITWIDTH_PER_CHUNK >= Config.getNumBitsFiniteFieldModulus()) {
               x1.adjustBitwidth();
               adjusted = true;
            }

            if (b2Max + BITWIDTH_PER_CHUNK >= Config.getNumBitsFiniteFieldModulus()) {
               x2.adjustBitwidth();
               adjusted = true;
            }
         } else {
            if (b1Max > Config.getNumBitsFiniteFieldModulus() - 2) {
               x1.adjustBitwidth();
               adjusted = true;
            }

            if (b2Max > Config.getNumBitsFiniteFieldModulus() - 2) {
               x2.adjustBitwidth();
               adjusted = true;
            }
         }
      } else {
         if (b1Max > Config.getNumBitsFiniteFieldModulus() - 2) {
            x1.adjustBitwidth();
            adjusted = true;
         }

         if (b2Max > Config.getNumBitsFiniteFieldModulus() - 2) {
            x2.adjustBitwidth();
            adjusted = true;
         }
      }

      if (!adjusted) {
         int excesss1 = x1.variableState.getMulUseCount() * (x1.currentBitwidth - x1.bitWidth);
         int excesss2 = x2.variableState.getMulUseCount() * (x2.currentBitwidth - x2.bitWidth);
         if (excesss1 > excesss2) {
            x1.adjustBitwidth();
         } else if (excesss2 < excesss1) {
            x2.adjustBitwidth();
         } else {
            excesss1 = x1.variableState.getAddUseCount() * (x1.currentBitwidth - x1.bitWidth);
            excesss2 = x2.variableState.getAddUseCount() * (x2.currentBitwidth - x2.bitWidth);
            if (excesss1 > excesss2) {
               x1.adjustBitwidth();
            } else if (excesss2 < excesss1) {
               x2.adjustBitwidth();
            } else if (x1.currentBitwidth - x1.bitWidth > 0) {
               x1.adjustBitwidth();
            } else {
               x2.adjustBitwidth();
            }
         }

      }
   }

   public void adjustBitwidth() {
      if (this.constant == null) {
         if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
            this.bitWires = this.packedWire.getBits(this.bitWidth, this.bitWidth);
         } else {
            this.bitWires = this.packedWire.getBits(this.bitWidth, BITWIDTH_PER_CHUNK);
         }

         this.currentBitwidth = this.bitWidth;
         if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
            this.packedWire = new PackedValue(this.bitWires, this.bitWidth);
         } else {
            this.packedWire = new PackedValue(this.bitWires, BITWIDTH_PER_CHUNK);
         }

         this.maxValue = Util.computeMaxValue(this.currentBitwidth);
      }

   }

   public UnsignedInteger mul(UnsignedInteger o) {
      int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
      if (this.isConstant() && o.isConstant()) {
         return new UnsignedInteger(maxBitWidth, this.getConstant().multiply(o.getConstant()));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ONE)) {
         return o.copy();
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ONE)) {
         return this.copy();
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ZERO)) {
         return new UnsignedInteger(maxBitWidth, 0L);
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         return new UnsignedInteger(maxBitWidth, 0L);
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setPackedAhead(true);
            o.variableState.setPackedAhead(true);
            if (o.bitWidth < this.bitWidth) {
               o.variableState.setConditionallySplittedAhead(true);
            } else if (o.bitWidth > this.bitWidth) {
               this.variableState.setConditionallySplittedAhead(true);
            }

            this.variableState.incMulUseCount();
            o.variableState.incMulUseCount();
            int idx1 = this.getVariableState().getMulIndex();
            int idx2 = o.getVariableState().getMulIndex();
            if (idx1 == 1 && this.bitWidth > BITWIDTH_LIMIT_SHORT) {
               this.variableState.setConditionallySplittedAhead(true);
            }

            if (idx2 == 1 && o.bitWidth > BITWIDTH_LIMIT_SHORT) {
               o.variableState.setConditionallySplittedAhead(true);
            }

            result = new UnsignedInteger(this.generator, maxBitWidth);
            result.getVariableState().incMulIndex();
            result.getVariableState().setOptimizationAttributes(this.variableState, o.variableState, true, maxBitWidth);
            return result;
         } else {
            this.variableState.decMulUseCount();
            o.variableState.decMulUseCount();
            boolean overflowCheck = this.packedWire.mulOverflowCheck(o.packedWire);
            if (overflowCheck) {
               handleOverflow(this, o, true);
               overflowCheck = this.packedWire.mulOverflowCheck(o.packedWire);
               if (overflowCheck) {
                  handleOverflow(this, o, true);
               }
            }

            BigInteger outMaxValue = this.maxValue.multiply(o.maxValue);
            result = new UnsignedInteger(maxBitWidth, this.packedWire.mul(o.packedWire), outMaxValue);
            return result;
         }
      }
   }

   public UnsignedInteger subtract(UnsignedInteger o) {
      int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
      if (this.isConstant() && o.isConstant()) {
         return new UnsignedInteger(maxBitWidth, this.getConstant().subtract(o.getConstant()));
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         CircuitGenerator.__getActiveCircuitGenerator().__retrieveVariableState();
         return this.copy();
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         o.variableState.setPackedAhead(true);
         this.variableState.incAddUseCount();
         o.variableState.incAddUseCount();
         if (o.bitWidth < this.bitWidth) {
            o.variableState.setConditionallySplittedAhead(true);
         } else if (o.bitWidth > this.bitWidth) {
            this.variableState.setConditionallySplittedAhead(true);
         }

         UnsignedInteger result = new UnsignedInteger(this.generator, Math.max(this.bitWidth, o.bitWidth));
         result.getVariableState().setMulIndex(Math.max(o.getVariableState().getMulIndex(), this.variableState.getMulIndex()));
         result.getVariableState().setOptimizationAttributes(this.variableState, o.variableState, false, maxBitWidth);
         return result;
      } else {
         this.variableState.decAddUseCount();
         o.variableState.decAddUseCount();
         int newBitWidth = Math.max(this.bitWidth, o.bitWidth);
         BigInteger[] aux = SubtractionAUX.prepSub(o.packedWire, Util.computeBound(newBitWidth), this.generator, newBitWidth);
         boolean overflowCheck = this.packedWire.addSubOverflowCheck(aux);
         if (overflowCheck) {
            handleOverflow(this, o, false);
            aux = SubtractionAUX.prepSub(o.packedWire, Util.computeBound(newBitWidth), this.generator, newBitWidth);
            overflowCheck = this.packedWire.addSubOverflowCheck(aux);
            if (overflowCheck) {
               handleOverflow(this, o, false);
               aux = SubtractionAUX.prepSub(o.packedWire, Util.computeBound(newBitWidth), this.generator, newBitWidth);
            }
         }

         BigInteger outMaxValue = this.maxValue.add(Util.group(aux, BITWIDTH_PER_CHUNK));
         PackedValue p = this.packedWire.addsub(aux, o.packedWire);
         UnsignedInteger result = new UnsignedInteger(newBitWidth, p, outMaxValue);
         return result;
      }
   }

   public UnsignedInteger negate() {
      return this.isConstant() ? new UnsignedInteger(this.bitWidth, Util.computeBound(this.bitWidth).subtract(this.getConstant())) : (new UnsignedInteger(this.bitWidth, 0L)).subtract(this);
   }

   public UnsignedInteger xorBitwise(UnsignedInteger o) {
      int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
      if (this.isConstant() && o.isConstant()) {
         return new UnsignedInteger(maxBitWidth, this.getConstant().xor(o.getConstant()));
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setSplittedAhead(true);
            o.variableState.setSplittedAhead(true);
            result = new UnsignedInteger(this.generator, maxBitWidth);
            return result;
         } else {
            result = new UnsignedInteger(maxBitWidth, this.bitWires.xorWireArray(o.bitWires, maxBitWidth));
            return result;
         }
      }
   }

   public UnsignedInteger orBitwise(UnsignedInteger o) {
      int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
      if (this.isConstant() && o.isConstant()) {
         return new UnsignedInteger(maxBitWidth, this.getConstant().or(o.getConstant()));
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setSplittedAhead(true);
            o.variableState.setSplittedAhead(true);
            result = new UnsignedInteger(this.generator, maxBitWidth);
            return result;
         } else {
            result = new UnsignedInteger(maxBitWidth, this.bitWires.orWireArray(o.bitWires, maxBitWidth));
            return result;
         }
      }
   }

   public UnsignedInteger andBitwise(UnsignedInteger o) {
      int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
      if (this.isConstant() && o.isConstant()) {
         return new UnsignedInteger(maxBitWidth, this.getConstant().and(o.getConstant()));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ZERO)) {
         return new UnsignedInteger(maxBitWidth, 0L);
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         return new UnsignedInteger(maxBitWidth, 0L);
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setSplittedAhead(true);
            o.variableState.setSplittedAhead(true);
            result = new UnsignedInteger(this.generator, maxBitWidth);
            return result;
         } else {
            result = new UnsignedInteger(maxBitWidth, this.bitWires.mulWireArray(o.bitWires, maxBitWidth));
            return result;
         }
      }
   }

   public UnsignedInteger invBits() {
      if (this.isConstant()) {
         return new UnsignedInteger(this.bitWidth, Util.invertBits(this.getConstant(), this.bitWidth));
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setSplittedAhead(true);
            result = new UnsignedInteger(this.generator, this.bitWidth);
            return result;
         } else {
            result = new UnsignedInteger(this.bitWidth, this.bitWires.invAsBits(this.bitWidth));
            return result;
         }
      }
   }

   public UnsignedInteger shiftRight(int n) {
      if (this.isConstant()) {
         return new UnsignedInteger(this.bitWidth, this.getConstant().shiftRight(n));
      } else if (n == 0) {
         return this.copy();
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setSplittedAhead(true);
            result = new UnsignedInteger(this.generator, this.bitWidth);
            return result;
         } else {
            result = new UnsignedInteger(this.bitWidth, this.bitWires.shiftRight(this.bitWidth, n, ""));
            return result;
         }
      }
   }

   public UnsignedInteger shiftLeft(int n) {
      if (this.isConstant()) {
         return new UnsignedInteger(this.bitWidth, this.getConstant().shiftLeft(n));
      } else if (n == 0) {
         return this.copy();
      } else {
         UnsignedInteger result;
         if (this.generator.__getPhase() == 0) {
            this.variableState.setSplittedAhead(true);
            result = new UnsignedInteger(this.generator, this.bitWidth);
            return result;
         } else {
            result = new UnsignedInteger(this.bitWidth, this.bitWires.shiftLeft(this.bitWidth, n));
            return result;
         }
      }
   }

   public WireArray getBitWires() {
      if (this.generator.__getPhase() != 0) {
         return this.bitWires.adjustLength(this.bitWidth);
      } else {
         this.variableState.setSplittedAhead(true);
         Wire[] result = new Wire[this.bitWidth];
         if (this.constant == null) {
            Arrays.fill(result, new BitWire(-1));
         } else {
            for(int i = 0; i < this.bitWidth; ++i) {
               boolean b = this.constant.testBit(i);
               result[i] = b ? this.generator.__getOneWire() : this.generator.__getZeroWire();
            }
         }

         return new WireArray(result);
      }
   }

   public boolean isProbablyOverflowed() {
      return this.maxValue.bitLength() > this.bitWidth;
   }

   public boolean isConstant() {
      return this.constant != null;
   }

   public PackedValue getPackedWire() {
      if (this.packedWire == null && this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         return new PackedValue(new Wire(-1), this.bitWidth);
      } else {
         return this.packedWire;
      }
   }

   public BigInteger getMaxValue() {
      return this.maxValue;
   }

   public int getRequiredBitWidth() {
      return this.bitWidth;
   }

   public int getCurrentBitWidth() {
      return this.currentBitwidth;
   }

   public Wire[] toWires() {
      return this.packedWire != null ? this.packedWire.getArray() : null;
   }

   public void mapValue(BigInteger value, CircuitEvaluator evaluator) {
      value = Util.prepConstant(value, this.bitWidth);
      if (this.packedWire != null) {
         evaluator.setWireValue(this.packedWire, value, this.bitWidth, BITWIDTH_PER_CHUNK);
      } else {
         int length = this.bitWires.size();

         for(int i = 0; i < length; ++i) {
            evaluator.setWireValue(this.bitWires.get(i), value.testBit(i) ? BigInteger.ONE : BigInteger.ZERO);
         }
      }

   }

   public BigInteger getValueFromEvaluator(CircuitEvaluator evaluator) {
      if (this.packedWire != null) {
         return evaluator.getWireValue(this.packedWire, BITWIDTH_PER_CHUNK);
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

   public static UnsignedInteger createInput(CircuitGenerator generator, int bitwidth, String... desc) {
      PackedValue v;
      if (bitwidth <= BITWIDTH_LIMIT_SHORT) {
         Wire w = generator.__createInputWire(desc);
         v = new PackedValue(w, bitwidth);
      } else {
         int numChunks = (int)Math.ceil((double)bitwidth * 1.0 / (double)BITWIDTH_PER_CHUNK);
         Wire[] w = generator.__createInputWireArray(numChunks);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, BITWIDTH_PER_CHUNK);
         if (numChunks * BITWIDTH_PER_CHUNK != bitwidth) {
            bitwidths[numChunks - 1] = bitwidth % BITWIDTH_PER_CHUNK;
         }

         v = new PackedValue(w, bitwidths);
      }

      UnsignedInteger o = new UnsignedInteger(bitwidth, v);
      generator.__getInputAux().add(o.copy(bitwidth));
      return o;
   }

   public static UnsignedInteger createWitness(CircuitGenerator generator, int bitwidth, String... desc) {
      PackedValue v;
      if (bitwidth <= BITWIDTH_LIMIT_SHORT) {
         Wire w = generator.__createProverWitnessWire(desc);
         v = new PackedValue(w, bitwidth);
      } else {
         int numChunks = (int)Math.ceil((double)bitwidth * 1.0 / (double)BITWIDTH_PER_CHUNK);
         Wire[] w = generator.__createProverWitnessWireArray(numChunks);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, BITWIDTH_PER_CHUNK);
         if (numChunks * BITWIDTH_PER_CHUNK != bitwidth) {
            bitwidths[numChunks - 1] = bitwidth % BITWIDTH_PER_CHUNK;
         }

         v = new PackedValue(w, bitwidths);
      }

      UnsignedInteger o = new UnsignedInteger(bitwidth, v);
      generator.__getProverAux().add(o.copy(bitwidth));
      return o;
   }

   public static UnsignedInteger createVerifiedWitness(CircuitGenerator generator, int bitwidth, String... desc) {
      PackedValue v;
      if (bitwidth <= BITWIDTH_LIMIT_SHORT) {
         Wire w = generator.__createProverWitnessWire(desc);
         v = new PackedValue(w, bitwidth);
      } else {
         int numChunks = (int)Math.ceil((double)bitwidth * 1.0 / (double)BITWIDTH_PER_CHUNK);
         Wire[] w = generator.__createProverWitnessWireArray(numChunks);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, BITWIDTH_PER_CHUNK);
         if (numChunks * BITWIDTH_PER_CHUNK != bitwidth) {
            bitwidths[numChunks - 1] = bitwidth % BITWIDTH_PER_CHUNK;
         }

         v = new PackedValue(w, bitwidths);
      }

      UnsignedInteger o = new UnsignedInteger(bitwidth, v);
      generator.__getProverVerifiedAux().add(o.copy(bitwidth));
      return o;
   }

   public void verifyRange() {
      if (this.bitWidth <= BITWIDTH_LIMIT_SHORT) {
         this.packedWire.array[0].restrictBitLength(this.bitWidth);
      } else {
         int numChunks = (int)Math.ceil((double)this.bitWidth * 1.0 / (double)BITWIDTH_PER_CHUNK);
         int[] bitwidths = new int[numChunks];
         Arrays.fill(bitwidths, BITWIDTH_PER_CHUNK);
         if (numChunks * BITWIDTH_PER_CHUNK != this.bitWidth) {
            bitwidths[numChunks - 1] = this.bitWidth % BITWIDTH_PER_CHUNK;
         }

         for(int i = 0; i < numChunks; ++i) {
            this.packedWire.array[i].restrictBitLength(bitwidths[numChunks - 1]);
         }
      }

   }

   public static UnsignedInteger[] createInputArray(CircuitGenerator generator, int size, int bitwidth, String... desc) {
      UnsignedInteger[] out = new UnsignedInteger[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createInput(generator, bitwidth, desc);
      }

      return out;
   }

   public static UnsignedInteger[] createZeroArray(CircuitGenerator generator, int size, int bitwidth, String... desc) {
      UnsignedInteger[] out = new UnsignedInteger[size];

      for(int i = 0; i < size; ++i) {
         out[i] = new UnsignedInteger(bitwidth, 0L);
      }

      return out;
   }

   public static Object createZeroArray(CircuitGenerator generator, int[] dims, int bitwidth, String... desc) {
      if (dims.length == 1) {
         return createZeroArray(generator, dims[0], bitwidth, desc);
      } else {
         int i;
         if (dims.length == 2) {
            UnsignedInteger[][] out = new UnsignedInteger[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createZeroArray(generator, dims[1], bitwidth, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dim arrays not supported at this point. Only 3 dimensions are supported.");
         } else {
            UnsignedInteger[][][] out = new UnsignedInteger[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createZeroArray(generator, dims[2], bitwidth, desc);
               }
            }

            return out;
         }
      }
   }

   public static UnsignedInteger[] createWitnessArray(CircuitGenerator generator, int size, int bitwidth, String... desc) {
      UnsignedInteger[] out = new UnsignedInteger[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createWitness(generator, bitwidth, desc);
      }

      return out;
   }

   public static UnsignedInteger[] createVerifiedWitnessArray(CircuitGenerator generator, int size, int bitwidth, String... desc) {
      UnsignedInteger[] out = new UnsignedInteger[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createVerifiedWitness(generator, bitwidth, desc);
      }

      return out;
   }

   public void makeOutput(String... desc) {
      if (this.generator.__getPhase() == 0) {
         this.variableState.setPackedAhead(true);
         this.variableState.setConditionallySplittedAhead(true);
      } else {
         this.generator.__makeOutputArray(this.packedWire.array, desc);
      }

   }

   public static void makeOutput(CircuitGenerator generator, UnsignedInteger x, String... desc) {
      x.makeOutput();
   }

   public static void makeOutput(CircuitGenerator generator, UnsignedInteger[] a, String... desc) {
      UnsignedInteger[] var6 = a;
      int var5 = a.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         UnsignedInteger x = var6[var4];
         x.makeOutput();
      }

   }

   public static Object createInputArray(CircuitGenerator generator, int[] dims, int bitwidth, String... desc) {
      if (dims.length == 1) {
         return createInputArray(generator, dims[0], bitwidth, desc);
      } else {
         int i;
         if (dims.length == 2) {
            UnsignedInteger[][] out = new UnsignedInteger[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createInputArray(generator, dims[1], bitwidth, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            UnsignedInteger[][][] out = new UnsignedInteger[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createInputArray(generator, dims[2], bitwidth, desc);
               }
            }

            return out;
         }
      }
   }

   public static Object createWitnessArray(CircuitGenerator generator, int[] dims, int bitwidth, String... desc) {
      if (dims.length == 1) {
         return createWitnessArray(generator, dims[0], bitwidth, desc);
      } else {
         int i;
         if (dims.length == 2) {
            UnsignedInteger[][] out = new UnsignedInteger[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createWitnessArray(generator, dims[1], bitwidth, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            UnsignedInteger[][][] out = new UnsignedInteger[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createWitnessArray(generator, dims[2], bitwidth, desc);
               }
            }

            return out;
         }
      }
   }

   public static Object createVerifiedWitnessArray(CircuitGenerator generator, int[] dims, int bitwidth, String... desc) {
      if (dims.length == 1) {
         return createVerifiedWitnessArray(generator, dims[0], bitwidth, desc);
      } else {
         int i;
         if (dims.length == 2) {
            UnsignedInteger[][] out = new UnsignedInteger[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createVerifiedWitnessArray(generator, dims[1], bitwidth, desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            UnsignedInteger[][][] out = new UnsignedInteger[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createVerifiedWitnessArray(generator, dims[2], bitwidth, desc);
               }
            }

            return out;
         }
      }
   }

   public static void makeOutput(CircuitGenerator generator, Object a, String... desc) {
      int i;
      if (a instanceof UnsignedInteger[]) {
         UnsignedInteger[] array = (UnsignedInteger[])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, array[i], desc);
         }
      } else if (a instanceof UnsignedInteger[][]) {
         UnsignedInteger[][] array = (UnsignedInteger[][])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, array[i], desc);
         }
      } else {
         if (!(a instanceof UnsignedInteger[][][])) {
            throw new IllegalArgumentException("Declaring higher dimensional arrays as outputs not supported at this point. Only 3 dimensions are supported");
         }

         UnsignedInteger[][][] array = (UnsignedInteger[][][])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, (Object)array[i], desc);
         }
      }

   }

   public void mapRandomValue(CircuitEvaluator evaluator) {
      BigInteger rnd = Util.nextRandomBigInteger(this.bitWidth);
      if (this.packedWire != null) {
         evaluator.setWireValue(this.packedWire, rnd, this.bitWidth, BITWIDTH_PER_CHUNK);
      } else {
         int length = this.bitWires.size();

         for(int i = 0; i < length; ++i) {
            evaluator.setWireValue(this.bitWires.get(i), rnd.testBit(i) ? BigInteger.ONE : BigInteger.ZERO);
         }
      }

   }

   public Bit isEqualTo(BigInteger b) {
      return this.isEqualTo((IAuxType)(new UnsignedInteger(b)));
   }

   public Bit isGreaterThan(UnsignedInteger o) {
      if (o == this) {
         return new Bit(false);
      } else if (o.isConstant() && this.isConstant()) {
         return new Bit(this.getConstant().compareTo(o.getConstant()) > 0);
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setConditionallySplittedAndAlignedAhead(true);
         this.variableState.setPackedAhead(true);
         o.variableState.setConditionallySplittedAndAlignedAhead(true);
         o.variableState.setPackedAhead(true);
         return new Bit(new Wire(-1));
      } else {
         int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
         handleComparisonOverflow(this, o);
         return this.packedWire.isGreaterThan(o.packedWire, maxBitWidth);
      }
   }

   public Bit isGreaterThanOrEquals(UnsignedInteger o) {
      if (o == this) {
         return new Bit(true);
      } else if (o.isConstant() && this.isConstant()) {
         return new Bit(this.getConstant().compareTo(o.getConstant()) >= 0);
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setConditionallySplittedAndAlignedAhead(true);
         this.variableState.setPackedAhead(true);
         o.variableState.setConditionallySplittedAndAlignedAhead(true);
         o.variableState.setPackedAhead(true);
         return new Bit(new Wire(-1));
      } else {
         int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
         handleComparisonOverflow(this, o);
         return this.packedWire.isGreaterThanOrEqual(o.packedWire, maxBitWidth);
      }
   }

   public Bit isLessThan(UnsignedInteger o) {
      if (o == this) {
         return new Bit(false);
      } else if (o.isConstant() && this.isConstant()) {
         return new Bit(this.getConstant().compareTo(o.getConstant()) < 0);
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setConditionallySplittedAndAlignedAhead(true);
         this.variableState.setPackedAhead(true);
         o.variableState.setConditionallySplittedAndAlignedAhead(true);
         o.variableState.setPackedAhead(true);
         return new Bit(new Wire(-1));
      } else {
         int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
         handleComparisonOverflow(this, o);
         Bit result = this.packedWire.isLessThan(o.packedWire, maxBitWidth);
         return result;
      }
   }

   private static void handleComparisonOverflow(UnsignedInteger op1, UnsignedInteger op2) {
      if (op1.packedWire.checkComparisonOverflow(op2.packedWire)) {
         if (op1.packedWire.array.length == 1) {
            op1.adjustBitwidth();
         }

         if (op2.packedWire.array.length == 1) {
            op2.adjustBitwidth();
         }
      }

   }

   public Bit isLessThanOrEquals(UnsignedInteger o) {
      if (o == this) {
         return new Bit(true);
      } else if (o.isConstant() && this.isConstant()) {
         return new Bit(this.getConstant().compareTo(o.getConstant()) <= 0);
      } else if (this.generator.__getPhase() == 0) {
         this.variableState.setConditionallySplittedAndAlignedAhead(true);
         this.variableState.setPackedAhead(true);
         o.variableState.setConditionallySplittedAndAlignedAhead(true);
         o.variableState.setPackedAhead(true);
         return new Bit(new Wire(-1));
      } else {
         int maxBitWidth = Math.max(this.bitWidth, o.bitWidth);
         handleComparisonOverflow(this, o);
         return this.packedWire.isLessThanOrEqual(o.packedWire, maxBitWidth);
      }
   }

   public Bit isNotEqualTo(BigInteger b) {
      return this.isNotEqualTo((IAuxType)(new UnsignedInteger(b)));
   }

   public BigInteger getConstant() {
      return this.constant;
   }

   public Bit[] getBitElements() {
      Bit[] result;
      if (this.generator.__getPhase() == 0) {
         this.variableState.setSplittedAhead(true);
         result = new Bit[this.bitWidth];
         if (this.constant == null) {
            Arrays.fill(result, new Bit(new Wire(-1)));
         } else {
            for(int i = 0; i < this.bitWidth; ++i) {
               boolean b = this.constant.testBit(i);
               Wire w = b ? this.generator.__getOneWire() : this.generator.__getZeroWire();
               result[i] = new Bit(w);
            }
         }

         return result;
      } else {
         result = new Bit[this.bitWidth];
         WireArray array = this.bitWires.adjustLength(this.bitWidth);

         for(int i = 0; i < this.bitWidth; ++i) {
            result[i] = new Bit(array.get(i));
         }

         return result;
      }
   }

   public VariableState getVariableState() {
      return this.variableState;
   }

   public void forceEqual(IAuxType o) {
      if (!(o instanceof UnsignedInteger)) {
         throw new IllegalArgumentException("UnsignedINT expected");
      } else {
         UnsignedInteger other = (UnsignedInteger)o;
         if (this.getConstant() != null && other.getConstant() != null && !this.getConstant().equals(other.getConstant())) {
            throw new RuntimeException("Constraint fails on constant uints");
         } else {
            if (this.generator.__getPhase() == 0) {
               this.variableState.setPackedAhead(true);
               other.variableState.setPackedAhead(true);
               this.variableState.setConditionallySplittedAhead(true);
               other.variableState.setConditionallySplittedAhead(true);
            } else if (this.bitWidth <= BITWIDTH_LIMIT_SHORT && other.bitWidth <= BITWIDTH_LIMIT_SHORT) {
               this.generator.__addEqualityAssertion(this.packedWire.array[0], other.packedWire.array[0]);
            } else {
               this.packedWire.forceEquality2(other.packedWire);
            }

         }
      }
   }

   public UnsignedInteger div(UnsignedInteger o) {
      if (o.isConstant() && this.isConstant()) {
         return new UnsignedInteger(this.bitWidth, this.getConstant().divide(o.getConstant()));
      } else {
         int resultBitwidth;
         if (o.isConstant()) {
            if (o.getConstant().equals(BigInteger.ZERO)) {
               throw new RuntimeException("Error: Division by zero");
            }

            if (o.getConstant().bitLength() > this.bitWidth) {
               return new UnsignedInteger(this.bitWidth, BigInteger.ZERO);
            }

            BigInteger modulus = o.getConstant();
            resultBitwidth = modulus.bitLength();
            if (modulus.equals(BigInteger.valueOf(2L).pow(resultBitwidth - 1))) {
               return this.shiftRight(resultBitwidth - 1).copy(this.bitWidth);
            }
         }

         if (this.generator.__getPhase() == 0) {
            this.variableState.setPackedAhead(true);
            this.variableState.setConditionallySplittedAhead(true);
            o.variableState.setPackedAhead(true);
            o.variableState.setConditionallySplittedAndAlignedAhead(true);
            UnsignedInteger result = new UnsignedInteger(this.generator, this.bitWidth);
            return result;
         } else {
            if (Config.enforceInternalDivisionNonZeroChecks) {
               o.packedWire.forceNonZero();
            }

            if (this.bitWidth <= BITWIDTH_LIMIT_SHORT && o.bitWidth <= BITWIDTH_LIMIT_SHORT) {
               Wire q;
               if (o.isConstant()) {
                  q = (new ModConstantGadget(this.packedWire.array[0], this.bitWidth, o.getConstant(), true, new String[0])).getQuotient();
                  resultBitwidth = this.bitWidth - o.getConstant().bitLength() + 1;
                  return new UnsignedInteger(this.bitWidth, new PackedValue(q, resultBitwidth), Util.computeMaxValue(resultBitwidth));
               } else {
                  q = (new ModGadget(this.packedWire.array[0], this.bitWidth, o.packedWire.array[0], o.bitWidth, true, new String[0])).getQuotient();
                  return new UnsignedInteger(this.bitWidth, new PackedValue(q, this.bitWidth));
               }
            } else {
               PackedValue q;
               if (o.isConstant()) {
                  q = (new LongIntegerModConstantGadget(this.packedWire, o.packedWire, true, new String[0])).getQuotient();
                  resultBitwidth = this.bitWidth - o.getConstant().bitLength() + 1;
                  return new UnsignedInteger(this.bitWidth, q, resultBitwidth);
               } else {
                  q = (new LongIntegerModNotStrictModulusGadget(this.packedWire, o.packedWire, true, new String[0])).getQuotient();
                  return new UnsignedInteger(this.bitWidth, q);
               }
            }
         }
      }
   }

   public UnsignedInteger mod(UnsignedInteger o) {
      int minBitwidth = Math.min(this.bitWidth, o.bitWidth);
      if (o.isConstant() && this.isConstant()) {
         return new UnsignedInteger(minBitwidth, this.getConstant().mod(o.getConstant()));
      } else {
         if (o.isConstant()) {
            if (o.getConstant().equals(BigInteger.ZERO)) {
               throw new RuntimeException("Error: Division by zero");
            }

            if (o.getConstant().bitLength() > this.bitWidth) {
               return this.copy();
            }

            BigInteger modulus = o.getConstant();
            int bitlength = modulus.bitLength();
            if (modulus.equals(BigInteger.valueOf(2L).pow(bitlength - 1))) {
               return this.andBitwise(modulus.subtract(BigInteger.ONE)).copy(bitlength - 1);
            }
         }

         if (this.generator.__getPhase() == 0) {
            this.variableState.setPackedAhead(true);
            this.variableState.setConditionallySplittedAhead(true);
            o.variableState.setPackedAhead(true);
            o.variableState.setConditionallySplittedAndAlignedAhead(true);
            UnsignedInteger result = new UnsignedInteger(this.generator, minBitwidth);
            return result;
         } else {
            if (Config.enforceInternalDivisionNonZeroChecks) {
               o.packedWire.forceNonZero();
            }

            if (this.bitWidth <= BITWIDTH_LIMIT_SHORT && o.bitWidth <= BITWIDTH_LIMIT_SHORT) {
               Wire r;
               if (o.isConstant()) {
                  r = (new ModConstantGadget(this.packedWire.array[0], this.bitWidth, o.getConstant(), true, new String[0])).getRemainder();
                  return new UnsignedInteger(minBitwidth, new PackedValue(r, minBitwidth));
               } else {
                  r = (new ModGadget(this.packedWire.array[0], this.bitWidth, o.packedWire.array[0], o.bitWidth, true, new String[0])).getRemainder();
                  return new UnsignedInteger(minBitwidth, new PackedValue(r, minBitwidth));
               }
            } else {
               PackedValue r;
               if (o.isConstant()) {
                  r = (new LongIntegerModConstantGadget(this.packedWire, o.packedWire, true, new String[0])).getRemainder();
               } else {
                  r = (new LongIntegerModNotStrictModulusGadget(this.packedWire, o.packedWire, true, new String[0])).getRemainder();
               }

               return new UnsignedInteger(minBitwidth, r);
            }
         }
      }
   }

   public Bit isEqualTo(IAuxType o) {
      if (!(o instanceof UnsignedInteger)) {
         throw new IllegalArgumentException("UnsignedINT expected");
      } else if (this == o) {
         return new Bit(true);
      } else {
         UnsignedInteger other = (UnsignedInteger)o;
         if (this.getConstant() != null && other.getConstant() != null) {
            return new Bit(this.getConstant().equals(other.getConstant()));
         } else if (this.generator.__getPhase() == 0) {
            this.variableState.setPackedAhead(true);
            other.variableState.setPackedAhead(true);
            this.variableState.setConditionallySplittedAndAlignedAhead(true);
            other.variableState.setConditionallySplittedAndAlignedAhead(true);
            return new Bit(new Wire(-1));
         } else {
            return this.bitWidth <= BITWIDTH_LIMIT_SHORT && other.bitWidth <= BITWIDTH_LIMIT_SHORT ? new Bit(this.packedWire.array[0].isEqualTo(other.packedWire.array[0])) : this.packedWire.isEqualTo(other.packedWire);
         }
      }
   }

   public Bit isNotEqualTo(IAuxType o) {
      return this.isEqualTo(o).inv();
   }

   public static Class<?> __getClassRef() {
      return UnsignedInteger.class;
   }

   public VariableState getState() {
      return this.variableState;
   }

   public PackedValue getPackedValue() {
      return this.packedWire;
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, byte v) {
      return new UnsignedInteger(bitwidth, (long)v);
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, int v) {
      return new UnsignedInteger(bitwidth, (long)v);
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, long v) {
      return new UnsignedInteger(bitwidth, v);
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, BigInteger v) {
      return new UnsignedInteger(bitwidth, v);
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, String v) {
      return new UnsignedInteger(bitwidth, new BigInteger(v));
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, UnsignedInteger v) {
      return v.copy(bitwidth);
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, Bit v) {
      return v.toUnsignedInteger().copy(bitwidth);
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, FieldElement e) {
      if (e.isConstant()) {
         return new UnsignedInteger(e.getConstant().and(Util.computeMaxValue(bitwidth)));
      } else if (e.getModulus().bitLength() > bitwidth) {
         return instantiateFrom(e.getModulus().bitLength() + 1, e).copy(bitwidth);
      } else {
         CircuitGenerator g = CircuitGenerator.__getActiveCircuitGenerator();
         if (g.__getPhase() == 0) {
            e.getState().setConditionallySplittedAhead(true);
            e.getState().setMustBeWithinRange(true);
            e.getState().setPackedAhead(true);
            g.__retrieveVariableState();
            return new UnsignedInteger(g, bitwidth, e.getState());
         } else {
            UnsignedInteger v = new UnsignedInteger();
            v.bitWidth = bitwidth;
            v.constant = e.getConstant();
            v.packedWire = e.packedWire;
            v.bitWires = e.bitWires;
            v.variableState = e.variableState;
            v.generator = g;
            v.maxValue = e.maxValue;
            v.currentBitwidth = e.currentBitwidth;
            g.__retrieveVariableState();
            return v;
         }
      }
   }

   public static UnsignedInteger instantiateFrom(int bitwidth, GroupElement e) {
      if (e.isConstant()) {
         return new UnsignedInteger(e.getConstant().and(Util.computeMaxValue(bitwidth)));
      } else if (e.getModulus().bitLength() > bitwidth) {
         return instantiateFrom(e.getModulus().bitLength() + 1, e).copy(bitwidth);
      } else {
         CircuitGenerator g = CircuitGenerator.__getActiveCircuitGenerator();
         if (g.__getPhase() == 0) {
            e.getState().setConditionallySplittedAhead(true);
            e.getState().setMustBeWithinRange(true);
            e.getState().setPackedAhead(true);
            g.__retrieveVariableState();
            return new UnsignedInteger(g, bitwidth, e.getState());
         } else {
            UnsignedInteger v = new UnsignedInteger();
            v.bitWidth = bitwidth;
            v.constant = e.getConstant();
            v.packedWire = e.packedWire;
            v.bitWires = e.bitWires;
            v.variableState = e.variableState;
            v.generator = g;
            v.maxValue = e.maxValue;
            v.currentBitwidth = e.currentBitwidth;
            g.__retrieveVariableState();
            return v;
         }
      }
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, int[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, byte[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, long[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, UnsignedInteger[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, BigInteger[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, String[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, FieldElement[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, GroupElement[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }

   public static UnsignedInteger[] instantiateFrom(int bitwidth, Bit[] v) {
      UnsignedInteger[] a = new UnsignedInteger[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(bitwidth, v[i]);
      }

      return a;
   }
}
