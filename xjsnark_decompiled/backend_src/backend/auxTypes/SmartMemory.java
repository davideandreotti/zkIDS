package backend.auxTypes;

import backend.config.Config;
import backend.eval.CircuitEvaluator;
import backend.eval.Instruction;
import backend.structure.CircuitGenerator;
import backend.structure.Wire;
import backend.structure.WireArray;
import examples.gadgets.PermutationNetworkGadget;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import util.Util;

public class SmartMemory<T> {
   private long numOfVariableReads = 0L;
   private long numOfVariableWrites = 0L;
   private long numOfInitialConstReads = 0L;
   private long numOfConstReads = 0L;
   private long numOfInitialConstWrites = 0L;
   private long numOfConstWrites = 0L;
   private long numOfCoditionalWrites = 0L;
   private int opCounter = 0;
   private boolean randAccessOccured = false;
   private boolean readOnly = false;
   private T[] elements;
   private int numElements;
   private Class<?> typeClass;
   private Object[] typeArgs;
   private CircuitGenerator generator;
   private T previousReadElement;
   private T previousWrittenElement;
   private UnsignedInteger previousReadIdx;
   private UnsignedInteger previousWriteIdx;
   private boolean dirty = false;
   private int conditionalScopeId;
   private static final int NW_MODE = 1;
   private static final int LINEAR_MODE = 2;
   private static final int SQRT_MOsDE = 3;
   public static int globalMemoryCounter = 0;
   private int memoryIndex = 0;
   private MemoryState state;
   private BigInteger[] runtimeVals;
   private Wire opCounterWire;
   private ArrayList<Wire> operations;
   private ArrayList<Wire> opCounters;
   private ArrayList<Wire> indexesToOperate;
   private ArrayList<PackedValue> data;
   private ArrayList<PackedValue> conditionalWitnessVals;
   private ArrayList<UnsignedInteger> indexes;
   private boolean isEmpty;
   private T defaultElement;
   private HashMap<MemoryCacheRecord, T> cachedAccesses;
   private HashSet<MemoryCacheRecord> cachedWrites;
   private PackedValue defaultValue;

   public SmartMemory(int n, T defaultElement, Class<?> typeClass, Object[] typeArgs) {
      if (n < 0) {
         throw new IllegalArgumentException("Array length must be a positive number!");
      } else {
         this.numElements = n;
         this.readOnly = false;
         this.defaultElement = defaultElement;
         this.typeClass = typeClass;
         this.typeArgs = typeArgs;
         this.generator = CircuitGenerator.__getActiveCircuitGenerator();
         this.conditionalScopeId = 0;
         this.memoryIndex = globalMemoryCounter++;
         this.cachedAccesses = new HashMap();
         this.cachedWrites = new HashSet();
         if (this.generator.__getPhase() == 0) {
            this.state = new MemoryState();
            this.generator.__getMemoryList().add(this);
            this.generator.__getMemoryStateTable().put(this.memoryIndex, this.state);
            if (defaultElement != null && IAuxType.class.isAssignableFrom(typeClass)) {
               IAuxType t = (IAuxType)defaultElement;
               t.getState().setMustBeWithinRange(true);
               t.getState().setConditionallySplittedAndAlignedAhead(true);
               t.getState().setPackedAhead(true);
            }
         } else {
            this.generator.__getMemoryList().add(this);
            this.state = (MemoryState)this.generator.__getMemoryStateTable().get(this.memoryIndex);
            Object[] copiedArray = new Object[this.numElements];
            this.generator.__setUntrackedStateObjects(true);
            if (defaultElement == null) {
               defaultElement = this.constructDefaultElement();
            }

            for(int i = 0; i < this.numElements; ++i) {
               if (typeClass != UnsignedInteger.class && typeClass != Bit.class && typeClass != FieldElement.class && typeClass != GroupElement.class) {
                  copiedArray[i] = ((RuntimeStruct)defaultElement).____copy();
               } else {
                  copiedArray[i] = ((IAuxType)defaultElement).copy();
               }
            }

            this.generator.__setUntrackedStateObjects(false);
            this.elements = copiedArray;
         }

      }
   }

   public SmartMemory(T[] initialElements, char readOnly, Class<?> typeClass, Object[] typeArgs) {
      this.isEmpty = false;
      this.numElements = initialElements.length;
      this.elements = initialElements;
      this.readOnly = readOnly == 's';
      this.typeClass = typeClass;
      this.typeArgs = typeArgs;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.cachedAccesses = new HashMap();
      this.cachedWrites = new HashSet();
      Object[] copiedArray = new Object[this.numElements];

      int i;
      for(i = 0; i < this.numElements; ++i) {
         if (IAuxType.class.isAssignableFrom(typeClass)) {
            copiedArray[i] = ((IAuxType)this.elements[i]).copy();
         } else {
            if (!RuntimeStruct.class.isAssignableFrom(typeClass)) {
               throw new UnsupportedOperationException("Class type not supported ");
            }

            copiedArray[i] = ((RuntimeStruct)this.elements[i]).____copy();
         }
      }

      this.elements = copiedArray;
      if (!this.readOnly) {
         if (this.generator.__getPhase() == 0) {
            if (IAuxType.class.isAssignableFrom(typeClass)) {
               for(i = 0; i < this.numElements; ++i) {
                  ((IAuxType)this.elements[i]).getState().setMustBeWithinRange(true);
                  ((IAuxType)this.elements[i]).getState().setConditionallySplittedAndAlignedAhead(true);
                  ((IAuxType)this.elements[i]).getState().setPackedAhead(true);
               }
            } else if (!RuntimeStruct.class.isAssignableFrom(typeClass)) {
               throw new UnsupportedOperationException("Class type not supportedds");
            }
         }
      } else if (this.generator.__getPhase() == 1 && IAuxType.class.isAssignableFrom(typeClass)) {
         for(i = 0; i < this.numElements; ++i) {
            if (!((IAuxType)this.elements[i]).isConstant()) {
               throw new IllegalArgumentException("In read only memory, all the contents must be constants");
            }
         }
      }

      this.memoryIndex = globalMemoryCounter++;
      if (this.generator.__getPhase() == 0) {
         this.state = new MemoryState();
         this.generator.__getMemoryList().add(this);
         this.generator.__getMemoryStateTable().put(this.memoryIndex, this.state);
      } else {
         this.generator.__getMemoryList().add(this);
         this.state = (MemoryState)this.generator.__getMemoryStateTable().get(this.memoryIndex);
      }

   }

   public SmartMemory(T[] initialElements, Class<?> typeClass, Object[] typeArgs) {
      this.isEmpty = false;
      this.numElements = initialElements.length;
      this.elements = initialElements;
      this.typeClass = typeClass;
      this.typeArgs = typeArgs;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
      this.cachedAccesses = new HashMap();
      this.cachedWrites = new HashSet();
      Object[] copiedArray = new Object[this.numElements];

      int i;
      for(i = 0; i < this.numElements; ++i) {
         if (IAuxType.class.isAssignableFrom(typeClass)) {
            copiedArray[i] = ((IAuxType)this.elements[i]).copy();
         } else {
            if (!RuntimeStruct.class.isAssignableFrom(typeClass)) {
               throw new UnsupportedOperationException("Class type not supported ");
            }

            copiedArray[i] = ((RuntimeStruct)this.elements[i]).____copy();
         }
      }

      this.elements = copiedArray;
      if (this.generator.__getPhase() == 0) {
         if (IAuxType.class.isAssignableFrom(typeClass)) {
            for(i = 0; i < this.numElements; ++i) {
               ((IAuxType)this.elements[i]).getState().setMustBeWithinRange(true);
               ((IAuxType)this.elements[i]).getState().setConditionallySplittedAndAlignedAhead(true);
               ((IAuxType)this.elements[i]).getState().setPackedAhead(true);
            }
         } else if (!RuntimeStruct.class.isAssignableFrom(typeClass)) {
            throw new UnsupportedOperationException("Class type not supportedds");
         }
      }

      this.memoryIndex = globalMemoryCounter++;
      if (this.generator.__getPhase() == 0) {
         this.state = new MemoryState();
         this.generator.__getMemoryList().add(this);
         this.generator.__getMemoryStateTable().put(this.memoryIndex, this.state);
      } else {
         this.generator.__getMemoryList().add(this);
         this.state = (MemoryState)this.generator.__getMemoryStateTable().get(this.memoryIndex);
         this.readOnly = this.state.readOnly;
      }

   }

   public void contructNetwork() {
      if (this.generator.__getPhase() != 0) {
         this.runtimeVals = new BigInteger[this.numElements];
         PackedValue tmp;
         int i;
         PackedValue tmp2;
         if (IAuxType.class.isAssignableFrom(this.typeClass)) {
            tmp = ((IAuxType)this.elements[0]).getPackedValue();

            for(i = 1; i < this.numElements; ++i) {
               tmp2 = ((IAuxType)this.elements[i]).getPackedValue();
               if (!tmp2.equals(tmp)) {
                  tmp = null;
                  break;
               }
            }

            this.defaultValue = tmp;
         } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
            tmp = ((RuntimeStruct)this.elements[0]).ptrReference.packedWire;

            for(i = 1; i < this.numElements; ++i) {
               tmp2 = ((RuntimeStruct)this.elements[i]).ptrReference.packedWire;
               if (!tmp2.equals(tmp)) {
                  tmp = null;
                  break;
               }
            }

            this.defaultValue = tmp;
         }

         this.generator.__specifyProverWitnessComputation(new Instruction() {
            public void evaluate(CircuitEvaluator evaluator) {
               int i;
               if (IAuxType.class.isAssignableFrom(SmartMemory.this.typeClass)) {
                  for(i = 0; i < SmartMemory.this.numElements; ++i) {
                     SmartMemory.this.runtimeVals[i] = evaluator.getWireValue(((IAuxType)SmartMemory.this.elements[i]).getPackedValue(), UnsignedInteger.BITWIDTH_PER_CHUNK);
                  }
               } else if (RuntimeStruct.class.isAssignableFrom(SmartMemory.this.typeClass)) {
                  for(i = 0; i < SmartMemory.this.numElements; ++i) {
                     SmartMemory.this.runtimeVals[i] = evaluator.getWireValue(((RuntimeStruct)SmartMemory.this.elements[i]).ptrReference.packedWire, UnsignedInteger.BITWIDTH_PER_CHUNK);
                  }
               }

            }
         });
         this.conditionalWitnessVals = new ArrayList();
         this.opCounterWire = this.generator.__getZeroWire();
         this.opCounters = new ArrayList();
         this.operations = new ArrayList();
         this.data = new ArrayList();
         this.indexesToOperate = new ArrayList();
         if (this.defaultValue == null) {
            for(int i = 0; i < this.numElements; ++i) {
               this.opCounters.add(this.opCounterWire);
               this.operations.add(this.generator.__getOneWire());
               this.indexesToOperate.add(this.generator.__createConstantWire((long)i));
               this.data.add(this.getPackedValue(this.elements[i]));
               this.opCounterWire = this.opCounterWire.add(1L);
            }
         }

      }
   }

   public T read(long idx) {
      return this.read(new UnsignedInteger(64, idx));
   }

   public void write(long idx, T element) {
      this.write(new UnsignedInteger(64, idx), element);
   }

   public T read(UnsignedInteger idx) {
      ++this.opCounter;
      this.cachedWrites.clear();
      MemoryCacheRecord cacheRecord = new MemoryCacheRecord(idx, idx.internalStateSerial);
      if (this.cachedAccesses.containsKey(cacheRecord)) {
         return this.cachedAccesses.get(cacheRecord);
      } else {
         if (idx.isConstant()) {
            if (!this.randAccessOccured) {
               ++this.numOfInitialConstReads;
            } else {
               ++this.numOfConstReads;
            }
         } else {
            ++this.numOfVariableReads;
            this.randAccessOccured = true;
         }

         if (this.indexes == null) {
            this.indexes = new ArrayList();
         }

         this.indexes.add(idx.copy());
         Object accessedElement;
         if (this.generator.__getPhase() == 0) {
            idx.getState().setPackedAhead(true);
            idx.getState().setConditionallySplittedAhead(true);
            accessedElement = null;
            accessedElement = this.constructDummyElement();
            this.cachedAccesses.put(cacheRecord, accessedElement);
            return accessedElement;
         } else {
            accessedElement = null;
            if (!this.state.readOnly) {
               if (idx.isConstant() && !this.randAccessOccured) {
                  accessedElement = this.constructFromExistingElement(this.elements[idx.constant.intValue()]);
               } else if (this.state.mode == 2) {
                  accessedElement = this.readLinearMode(idx);
               } else if (this.state.mode == 1) {
                  accessedElement = this.readNetworkMode(idx);
               }
            } else if (idx.isConstant()) {
               accessedElement = this.constructFromExistingElement(this.elements[idx.constant.intValue()]);
            } else {
               accessedElement = this.readReadOnlyMode(idx);
            }

            this.cachedAccesses.put(cacheRecord, accessedElement);
            return accessedElement;
         }
      }
   }

   private T readReadOnlyMode(UnsignedInteger idx) {
      if (this.runtimeVals == null) {
         this.readFromInputArray();
      }

      final T output = this.constructWitnessElement();
      final UnsignedInteger idx2 = idx.copy();
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger value = evaluator.getWireValue(idx2.packedWire.array[0]);
            int bitwidth = 1;
            if (SmartMemory.this.typeClass == UnsignedInteger.class) {
               bitwidth = Integer.parseInt((String)SmartMemory.this.typeArgs[0]);
            } else if (SmartMemory.this.typeClass == FieldElement.class || SmartMemory.this.typeClass == GroupElement.class) {
               bitwidth = (new BigInteger((String)SmartMemory.this.typeArgs[0])).bitLength();
            }

            PackedValue p = SmartMemory.this.getPackedValue(output);
            if (value.intValue() >= SmartMemory.this.numElements) {
               evaluator.setWireValue(p, BigInteger.ZERO, bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
            } else {
               evaluator.setWireValue(p, SmartMemory.this.runtimeVals[value.intValue()], bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
            }

         }
      });
      int bitCount = this.state.bitCount;
      int indexBitwidth = (int)Math.ceil(Math.log((double)this.numElements) / Math.log(2.0));
      int outputBitwidth = 0;
      int[] var10;
      int var9 = (var10 = this.getElementSize()).length;

      int sqrtN;
      for(int var8 = 0; var8 < var9; ++var8) {
         sqrtN = var10[var8];
         outputBitwidth += sqrtN;
      }

      sqrtN = (int)Math.ceil(Math.sqrt((double)this.numElements));
      this.getPackedValue(output).forceBitwidth();
      Wire[] bitsIn = null;
      Wire[] bitsOut = null;
      if (bitCount > 0) {
         bitsIn = idx.packedWire.getArray()[0].getBitWires(indexBitwidth).asArray();
         bitsOut = this.getPackedValue(output).getBits(outputBitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK).asArray();
      }

      Wire inputWire = idx.packedWire.getArray()[0];
      PackedValue outputPackedChunks = this.getPackedValue(output);
      Wire outputWire = (new WireArray(outputPackedChunks.array)).packWordsIntoLargerWords(UnsignedInteger.BITWIDTH_PER_CHUNK, outputPackedChunks.array.length)[0];
      Wire[] vars = new Wire[sqrtN];
      Wire p = inputWire.mul(Util.computeBound(this.state.maxBitwidth)).add(outputWire).add(1L);
      Wire currentProduct = p;
      if (bitCount != 0) {
         currentProduct = p.mul(p);
      }

      for(int i = 0; i < sqrtN; ++i) {
         if (i < bitCount) {
            if (i < outputBitwidth) {
               vars[i] = bitsOut[i];
            } else {
               vars[i] = bitsIn[i - outputBitwidth];
            }
         } else {
            vars[i] = currentProduct;
            if (i != sqrtN - 1) {
               currentProduct = currentProduct.mul(p);
            }
         }
      }

      ArrayList<BigInteger[]> allCoeffSet = this.state.allCoeffSet;
      Wire product = this.generator.__getOneWire();

      Wire accum;
      for(Iterator var19 = allCoeffSet.iterator(); var19.hasNext(); product = product.mul(accum)) {
         BigInteger[] coeffs = (BigInteger[])var19.next();
         accum = this.generator.__getZeroWire();

         for(int j = 0; j < vars.length; ++j) {
            accum = accum.add(vars[j].mul(coeffs[j]));
         }

         accum = accum.sub(1L);
      }

      this.generator.__addZeroAssertion(product);
      return output;
   }

   private void readFromInputArray() {
      BigInteger[] vals = new BigInteger[this.numElements];
      int maxBitwidth = 0;

      for(int i = 0; i < this.numElements; ++i) {
         BigInteger c = null;
         if (IAuxType.class.isAssignableFrom(this.typeClass)) {
            c = ((IAuxType)this.elements[i]).getConstant();
         } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
            c = ((RuntimeStruct)this.elements[i]).ptrReference.getConstant();
         }

         vals[i] = c;
         int bitwidth = c.bitLength();
         if (bitwidth > maxBitwidth) {
            maxBitwidth = bitwidth;
         }
      }

      this.runtimeVals = vals;
   }

   private T readLinearMode(UnsignedInteger idx) {
      if (idx.isConstant()) {
         int v = idx.constant.intValue();
         if (v < 0 || v >= this.elements.length) {
            v = 0;
         }

         return this.constructFromExistingElement(this.elements[v]);
      } else {
         Wire idxWire = idx.packedWire.array[0];
         int expectedElementArrayLength = this.getElementSize().length;
         PackedValue result = new PackedValue(Util.split(BigInteger.ZERO, expectedElementArrayLength, UnsignedInteger.BITWIDTH_PER_CHUNK));
         int i;
         Wire bitWire;
         if (IAuxType.class.isAssignableFrom(this.typeClass)) {
            for(i = 0; i < this.numElements; ++i) {
               bitWire = idxWire.isEqualTo((long)i);
               result = result.muxBit(((IAuxType)this.elements[i]).getPackedValue(), bitWire);
            }
         } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
            for(i = 0; i < this.numElements; ++i) {
               this.generator.__addDebugInstruction((IAuxType)((RuntimeStruct)this.elements[i]).ptrReference.copy(), "Wire dduring linear");
               bitWire = idxWire.isEqualTo((long)i);
               result = result.muxBit(((RuntimeStruct)this.elements[i]).ptrReference.packedWire, bitWire);
            }
         }

         return this.constructFromPackedValue(result);
      }
   }

   private T readNetworkMode(UnsignedInteger idx) {
      final UnsignedInteger idx2;
      if (idx.maxValue.compareTo(BigInteger.valueOf((long)this.numElements)) >= 0) {
         this.generator.__setUntrackedStateObjects(true);
         int idxBitlength = BigInteger.valueOf((long)(this.numElements - 1)).bitLength();
         Wire w = null;
         if (idx.isConstant()) {
            int v = idx.constant.intValue();
            if (v >= 0 && v < this.elements.length) {
               w = this.generator.__createConstantWire(idx.constant);
            } else {
               w = this.generator.__getZeroWire();
            }
         } else {
            Wire isInRange = idx.packedWire.array[0].isLessThan((long)this.numElements, idx.maxValue.bitLength());
            w = idx.packedWire.array[0].mul(isInRange);
         }

         idx2 = new UnsignedInteger(idxBitlength, new PackedValue(w, BigInteger.valueOf((long)this.numElements)));
         this.generator.__setUntrackedStateObjects(false);
      } else {
         idx2 = idx.copy();
      }

      if (this.runtimeVals == null) {
         this.contructNetwork();
      }

      final T readWitness = this.constructWitnessElement();
      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger idxValue = evaluator.getWireValue(idx2.packedWire, UnsignedInteger.BITWIDTH_PER_CHUNK);
            int bitwidth = 1;
            if (SmartMemory.this.typeClass == UnsignedInteger.class) {
               bitwidth = Integer.parseInt((String)SmartMemory.this.typeArgs[0]);
            } else if (SmartMemory.this.typeClass == FieldElement.class || SmartMemory.this.typeClass == GroupElement.class) {
               bitwidth = (new BigInteger((String)SmartMemory.this.typeArgs[0])).bitLength();
            }

            PackedValue p = SmartMemory.this.getPackedValue(readWitness);
            if (idxValue.intValue() >= SmartMemory.this.numElements) {
               evaluator.setWireValue(p, SmartMemory.this.runtimeVals[0], bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
            } else {
               evaluator.setWireValue(p, SmartMemory.this.runtimeVals[idxValue.intValue()], bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
            }

         }
      });
      this.opCounters.add(this.opCounterWire);
      this.operations.add(this.generator.__getZeroWire());
      this.indexesToOperate.add(idx2.packedWire.array[0]);
      this.data.add(this.getPackedValue(readWitness));
      this.opCounterWire = this.opCounterWire.add(1L);
      this.generator.__setUntrackedStateObjects(false);
      return readWitness;
   }

   private T constructFromExistingElement(T element) {
      if (IAuxType.class.isAssignableFrom(this.typeClass)) {
         return this.constructFromPackedValue(((IAuxType)element).getPackedValue());
      } else {
         return RuntimeStruct.class.isAssignableFrom(this.typeClass) ? this.constructFromPackedValue(((RuntimeStruct)element).ptrReference.getPackedValue()) : null;
      }
   }

   private T constructFromPackedValue(PackedValue p) {
      if (this.typeClass == UnsignedInteger.class) {
         return new UnsignedInteger(Integer.parseInt((String)this.typeArgs[0]), p);
      } else if (this.typeClass == FieldElement.class) {
         return new FieldElement(new BigInteger((String)this.typeArgs[0]), p);
      } else if (this.typeClass == GroupElement.class) {
         return new GroupElement(new BigInteger((String)this.typeArgs[0]), p);
      } else if (this.typeClass == Bit.class) {
         return new Bit(p.array[0]);
      } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
         try {
            Method m = this.typeClass.getMethod("____createObjectWithReference", UnsignedInteger.class);
            Object[] params = new Object[]{new UnsignedInteger(p.currentBitwidth[0], p)};
            return m.invoke((Object)null, params[0]);
         } catch (Exception var4) {
            var4.printStackTrace();
            return null;
         }
      } else {
         return null;
      }
   }

   private T constructDummyElement() {
      if (this.typeClass == UnsignedInteger.class) {
         return new UnsignedInteger(Integer.parseInt((String)this.typeArgs[0]));
      } else if (this.typeClass == FieldElement.class) {
         return new FieldElement(new BigInteger((String)this.typeArgs[0]));
      } else if (this.typeClass == GroupElement.class) {
         return new GroupElement(new BigInteger((String)this.typeArgs[0]));
      } else if (this.typeClass == Bit.class) {
         return new Bit();
      } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
         try {
            Method m = this.typeClass.getMethod("____createDummyObject");
            return m.invoke((Object)null);
         } catch (Exception var2) {
            var2.printStackTrace();
            return null;
         }
      } else {
         return null;
      }
   }

   private T constructWitnessElement() {
      if (this.typeClass == UnsignedInteger.class) {
         int bitwidth = Integer.parseInt((String)this.typeArgs[0]);
         UnsignedInteger e2 = UnsignedInteger.createWitness(this.generator, bitwidth, "read at " + this.numOfVariableReads);
         return e2;
      } else {
         BigInteger m;
         if (this.typeClass == FieldElement.class) {
            m = new BigInteger((String)this.typeArgs[0]);
            FieldElement e2 = FieldElement.createWitness(this.generator, m);
            return e2;
         } else if (this.typeClass == GroupElement.class) {
            m = new BigInteger((String)this.typeArgs[0]);
            GroupElement e2 = GroupElement.createWitness(this.generator, m);
            return e2;
         } else if (this.typeClass == Bit.class) {
            Bit e2 = Bit.createWitness(this.generator);
            return e2;
         } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
            try {
               Method m = this.typeClass.getMethod("____createObjectWithReference", UnsignedInteger.class);
               Object[] params = new Object[]{UnsignedInteger.createWitness(this.generator, this.getElementSize()[0])};
               return m.invoke((Object)null, params[0]);
            } catch (Exception var3) {
               var3.printStackTrace();
               return null;
            }
         } else {
            return null;
         }
      }
   }

   private T constructDefaultElement() {
      if (this.typeClass == UnsignedInteger.class) {
         return new UnsignedInteger(Integer.parseInt((String)this.typeArgs[0]), BigInteger.ZERO);
      } else if (this.typeClass == FieldElement.class) {
         return new FieldElement(new BigInteger((String)this.typeArgs[0]), BigInteger.ZERO);
      } else if (this.typeClass == GroupElement.class) {
         return new GroupElement(new BigInteger((String)this.typeArgs[0]), BigInteger.ZERO);
      } else if (this.typeClass == Bit.class) {
         return new Bit(false);
      } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
         try {
            Method m = this.typeClass.getMethod("____createNullObject");
            return m.invoke((Object)null);
         } catch (Exception var2) {
            var2.printStackTrace();
            return null;
         }
      } else {
         return null;
      }
   }

   private PackedValue getPackedValue(T e) {
      if (IAuxType.class.isAssignableFrom(this.typeClass)) {
         return ((IAuxType)e).getPackedValue();
      } else {
         return RuntimeStruct.class.isAssignableFrom(this.typeClass) ? ((RuntimeStruct)e).ptrReference.packedWire : null;
      }
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
      } else if (this.typeClass != FieldElement.class && this.typeClass != GroupElement.class) {
         if (this.typeClass == Bit.class) {
            return new int[]{1};
         } else {
            if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
               try {
                  Method m = this.typeClass.getMethod("____getIndexBitwidth");
                  return new int[]{(Integer)m.invoke((Object)null)};
               } catch (Exception var3) {
                  var3.printStackTrace();
               }
            }

            return null;
         }
      } else {
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
      }
   }

   public void write(UnsignedInteger idx, T element) {
      ++this.opCounter;
      this.cachedAccesses.clear();
      if (this.state.redundantWrites == null || !this.state.redundantWrites.contains(this.opCounter)) {
         MemoryCacheRecord cacheRecord = new MemoryCacheRecord(idx, idx.internalStateSerial);
         if (this.cachedWrites.contains(cacheRecord) && ConditionalScopeTracker.getCurrentScopeId() == 0) {
            System.out.println("Removing prev Writes");
            this.state.recordRedundantWire(this.opCounter - 1);
         }

         if (ConditionalScopeTracker.getCurrentScopeId() == 0) {
            this.cachedAccesses.put(cacheRecord, element);
         }

         this.cachedWrites.add(cacheRecord);
         if (idx.isConstant()) {
            if (!this.randAccessOccured) {
               ++this.numOfInitialConstWrites;
            } else {
               ++this.numOfConstWrites;
            }
         } else {
            ++this.numOfVariableWrites;
            this.randAccessOccured = true;
         }

         boolean conditionalWrite = false;
         Bit activeBit = new Bit(true);
         int conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
         if (conditionalScopeId != this.conditionalScopeId) {
            conditionalWrite = true;
            ++this.numOfCoditionalWrites;
            activeBit = ConditionalScopeTracker.getAccumActiveBit();
         }

         if (this.generator.__getPhase() == 0) {
            idx.getState().setPackedAhead(true);
            idx.getState().setConditionallySplittedAhead(true);
            if (IAuxType.class.isAssignableFrom(this.typeClass)) {
               IAuxType t = (IAuxType)element;
               t.getState().setMustBeWithinRange(true);
               t.getState().setConditionallySplittedAndAlignedAhead(true);
               t.getState().setPackedAhead(true);
            }
         } else {
            if (idx.isConstant() && !this.randAccessOccured) {
               int v = idx.constant.intValue();
               if (v >= 0 && v < this.elements.length) {
                  this.generator.__setUntrackedStateObjects(true);
                  PackedValue x1 = this.getPackedValue(this.elements[v]);
                  PackedValue x2 = this.getPackedValue(element);
                  this.elements[v] = this.constructFromPackedValue(x1.muxBit(x2, activeBit.wire));
                  this.generator.__setUntrackedStateObjects(false);
                  return;
               }

               System.out.println(">> elements.length:" + this.elements.length);
               return;
            }

            if (this.state.mode == 2) {
               this.writeLinearMode(idx, element, conditionalWrite, activeBit, cacheRecord);
            } else if (this.state.mode == 1) {
               this.writeNetworkMode(idx, element, conditionalWrite, activeBit, cacheRecord);
            }
         }

      }
   }

   private void writeNetworkMode(UnsignedInteger idx, final T element, boolean conditionalWrite, Bit activeBit, MemoryCacheRecord cacheRecord) {
      Wire overflowDetected = this.generator.__getZeroWire();
      final UnsignedInteger idx2;
      if (idx.maxValue.compareTo(BigInteger.valueOf((long)this.numElements)) >= 0) {
         this.generator.__setUntrackedStateObjects(true);
         int idxBitlength = BigInteger.valueOf((long)(this.numElements - 1)).bitLength();
         Wire w = null;
         if (idx.isConstant()) {
            int v = idx.constant.intValue();
            if (v >= 0 && v < this.elements.length) {
               w = this.generator.__createConstantWire(idx.constant);
            } else {
               overflowDetected = this.generator.__getOneWire();
               w = this.generator.__getZeroWire();
            }
         } else {
            Wire isInRange = idx.packedWire.array[0].isLessThan((long)this.numElements, idx.maxValue.bitLength());
            w = idx.packedWire.array[0].mul(isInRange);
         }

         idx2 = new UnsignedInteger(idxBitlength, new PackedValue(w, BigInteger.valueOf((long)this.numElements)));
         this.generator.__setUntrackedStateObjects(false);
      } else {
         idx2 = idx.copy();
      }

      if (this.runtimeVals == null) {
         this.contructNetwork();
      }

      final Wire activeBitAfterOverflowCheck = activeBit.wire.mul(overflowDetected.invAsBit());
      this.generator.__setUntrackedStateObjects(true);
      final T conditionalWitness = this.constructWitnessElement();
      if (conditionalWrite) {
         this.conditionalWitnessVals.add(this.getPackedValue(conditionalWitness));
      }

      this.generator.__specifyProverWitnessComputation(new Instruction() {
         public void evaluate(CircuitEvaluator evaluator) {
            BigInteger idxValue = evaluator.getWireValue(idx2.packedWire, UnsignedInteger.BITWIDTH_PER_CHUNK);
            BigInteger activeBitValue = BigInteger.ONE;
            activeBitValue = evaluator.getWireValue(activeBitAfterOverflowCheck);
            int bitwidth;
            if (activeBitValue.equals(BigInteger.ONE)) {
               SmartMemory.this.runtimeVals[idxValue.intValue()] = evaluator.getWireValue(SmartMemory.this.getPackedValue(element), UnsignedInteger.BITWIDTH_PER_CHUNK);
               bitwidth = 1;
               if (SmartMemory.this.typeClass == UnsignedInteger.class) {
                  bitwidth = Integer.parseInt((String)SmartMemory.this.typeArgs[0]);
               } else if (SmartMemory.this.typeClass == FieldElement.class || SmartMemory.this.typeClass == GroupElement.class) {
                  bitwidth = (new BigInteger((String)SmartMemory.this.typeArgs[0])).bitLength();
               }

               evaluator.setWireValue(SmartMemory.this.getPackedValue(conditionalWitness), BigInteger.ZERO, bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
            } else {
               bitwidth = 1;
               if (SmartMemory.this.typeClass == UnsignedInteger.class) {
                  bitwidth = Integer.parseInt((String)SmartMemory.this.typeArgs[0]);
               } else if (SmartMemory.this.typeClass == FieldElement.class || SmartMemory.this.typeClass == GroupElement.class) {
                  bitwidth = (new BigInteger((String)SmartMemory.this.typeArgs[0])).bitLength();
               }

               if (idxValue.intValue() >= SmartMemory.this.numElements) {
                  evaluator.setWireValue(SmartMemory.this.getPackedValue(conditionalWitness), SmartMemory.this.runtimeVals[0], bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
               } else {
                  evaluator.setWireValue(SmartMemory.this.getPackedValue(conditionalWitness), SmartMemory.this.runtimeVals[idxValue.intValue()], bitwidth, UnsignedInteger.BITWIDTH_PER_CHUNK);
               }
            }

         }
      });
      PackedValue v1 = this.getPackedValue(element);
      PackedValue v2 = this.getPackedValue(conditionalWitness);
      PackedValue v3 = v2.muxBit(v1, activeBit.wire);
      T elementToNw = this.constructFromPackedValue(v3);
      this.opCounters.add(this.opCounterWire);
      this.operations.add(activeBitAfterOverflowCheck);
      this.indexesToOperate.add(idx2.packedWire.array[0]);
      this.data.add(this.getPackedValue(elementToNw));
      this.opCounterWire = this.opCounterWire.add(1L);
      this.generator.__setUntrackedStateObjects(false);
   }

   public void writeLinearMode(UnsignedInteger idx, T element, boolean conditionalWrite, Bit activeBit, MemoryCacheRecord cacheRecord) {
      int i;
      PackedValue w3;
      if (idx.isConstant()) {
         i = idx.constant.intValue();
         if (i >= 0 && i < this.elements.length) {
            this.generator.__setUntrackedStateObjects(true);
            PackedValue x1 = this.getPackedValue(this.elements[idx.constant.intValue()]);
            w3 = this.getPackedValue(element);
            this.elements[idx.constant.intValue()] = this.constructFromPackedValue(x1.muxBit(w3, activeBit.wire));
            this.generator.__setUntrackedStateObjects(false);
         }
      } else {
         this.generator.__setUntrackedStateObjects(true);

         for(i = 0; i < this.numElements; ++i) {
            Bit bit = idx.isEqualTo((IAuxType)(new UnsignedInteger(BigInteger.valueOf((long)i))));
            if (conditionalWrite) {
               bit = bit.mul(activeBit);
            }

            w3 = this.getPackedValue(this.elements[i]).muxBit(this.getPackedValue(element), bit.wire);
            this.elements[i] = this.constructFromPackedValue(w3);
         }

         this.generator.__setUntrackedStateObjects(false);
      }
   }

   public void finalize() {
      if (this.generator.__getPhase() == 0) {
         throw new RuntimeException("Should be called in the second phase");
      } else {
         if (this.state.mode == 1 && this.opCounters != null) {
            final int k = this.opCounters.size();
            int[] elementSizes = this.getElementSize();
            final int numWiresPerElement = elementSizes.length;
            final ArrayList<Wire> operations2 = new ArrayList();
            final ArrayList<Wire> indexesToOperate2 = new ArrayList();
            final ArrayList<Wire> opCounters2 = new ArrayList();
            final ArrayList<PackedValue> data2 = new ArrayList();
            int indexBitwidth = (int)Math.ceil(Math.log((double)this.numElements) / Math.log(2.0));
            int opCounterBitwidth = (int)Math.ceil(Math.log((double)k) / Math.log(2.0));

            for(int i = 0; i < k; ++i) {
               operations2.add(this.generator.__createProverWitnessWire());
               indexesToOperate2.add(this.generator.__createProverWitnessWire());
               opCounters2.add(this.generator.__createProverWitnessWire());
               data2.add(new PackedValue(this.generator.__createProverWitnessWireArray(numWiresPerElement), elementSizes));
            }

            this.generator.__specifyProverWitnessComputation(new Instruction() {
               public void evaluate(CircuitEvaluator evaluator) {
                  MemoryConsistencyRecord[] array = new MemoryConsistencyRecord[k];

                  for(int ix = 0; ix < k; ++ix) {
                     array[ix] = new MemoryConsistencyRecord((MemoryConsistencyRecord)null);
                     array[ix].op = evaluator.getWireValue((Wire)SmartMemory.this.operations.get(ix));
                     array[ix].counter = evaluator.getWireValue((Wire)SmartMemory.this.opCounters.get(ix));
                     array[ix].index = evaluator.getWireValue((Wire)SmartMemory.this.indexesToOperate.get(ix));
                     array[ix].data = new BigInteger[numWiresPerElement];

                     for(int jx = 0; jx < numWiresPerElement; ++jx) {
                        array[ix].data[jx] = evaluator.getWireValue(((PackedValue)SmartMemory.this.data.get(ix)).array[jx]);
                     }
                  }

                  ArrayIndexComparator comparator = new ArrayIndexComparator(array);
                  Integer[] indexes = comparator.createIndexArray();
                  Arrays.sort(indexes, comparator);
                  int[] permutation = new int[k];

                  int i;
                  for(i = 0; i < k; permutation[indexes[i]] = i++) {
                  }

                  Arrays.sort(array);

                  for(i = 0; i < k; ++i) {
                     evaluator.setWireValue((Wire)operations2.get(i), array[i].op);
                     evaluator.setWireValue((Wire)opCounters2.get(i), array[i].counter);
                     evaluator.setWireValue((Wire)indexesToOperate2.get(i), array[i].index);

                     for(int j = 0; j < numWiresPerElement; ++j) {
                        evaluator.setWireValue(((PackedValue)data2.get(i)).array[j], array[i].data[j]);
                     }
                  }

                  evaluator.getPermutations().put("INT_" + SmartMemory.this.memoryIndex, permutation);
               }
            });
            Wire[][] inputs = new Wire[k][];
            Wire[][] outputs = new Wire[k][];
            int i;
            int i;
            Wire greaterIndex;
            int j;
            Wire packedWire2;
            if (this.state.packingOption == 1) {
               for(i = 0; i < k; ++i) {
                  BigInteger factor = BigInteger.valueOf(2L);
                  greaterIndex = (Wire)this.operations.get(i);
                  greaterIndex = greaterIndex.add(((Wire)this.indexesToOperate.get(i)).mul(factor));
                  factor = factor.shiftLeft(indexBitwidth);
                  greaterIndex = greaterIndex.add(((Wire)this.opCounters.get(i)).mul(factor));
                  factor = factor.shiftLeft(opCounterBitwidth);

                  for(int j = 0; j < numWiresPerElement; ++j) {
                     greaterIndex = greaterIndex.add(((PackedValue)this.data.get(i)).array[j].mul(factor));
                     factor = factor.shiftLeft(UnsignedInteger.BITWIDTH_PER_CHUNK);
                  }

                  PackedValue element = (PackedValue)this.data.get(i);
                  if (numWiresPerElement == 1) {
                     element.array[0].restrictBitLength(elementSizes[0]);
                  } else {
                     for(int j = 0; j < numWiresPerElement; ++j) {
                        element.array[j].restrictBitLength(elementSizes[j]);
                     }
                  }

                  inputs[i] = new Wire[]{greaterIndex};
                  factor = BigInteger.valueOf(2L);
                  packedWire2 = (Wire)operations2.get(i);
                  packedWire2 = packedWire2.add(((Wire)indexesToOperate2.get(i)).mul(factor));
                  factor = factor.shiftLeft(indexBitwidth);
                  packedWire2 = packedWire2.add(((Wire)opCounters2.get(i)).mul(factor));
                  factor = factor.shiftLeft(opCounterBitwidth);

                  for(int j = 0; j < numWiresPerElement; ++j) {
                     packedWire2 = packedWire2.add(((PackedValue)data2.get(i)).array[j].mul(factor));
                     factor = factor.shiftLeft(UnsignedInteger.BITWIDTH_PER_CHUNK);
                  }

                  outputs[i] = new Wire[]{packedWire2};
                  this.generator.__addBinaryAssertion((Wire)operations2.get(i), "assert on OP");
                  ((Wire)opCounters2.get(i)).restrictBitLength(opCounterBitwidth);
                  ((Wire)indexesToOperate2.get(i)).restrictBitLength(indexBitwidth);
                  PackedValue element2 = (PackedValue)data2.get(i);
                  if (numWiresPerElement == 1) {
                     element2.array[0].restrictBitLength(elementSizes[0]);
                  } else {
                     for(j = 0; j < numWiresPerElement; ++j) {
                        element2.array[j].restrictBitLength(elementSizes[j]);
                     }
                  }
               }
            } else {
               for(i = 0; i < k; ++i) {
                  inputs[i] = new Wire[3 + numWiresPerElement];
                  outputs[i] = new Wire[3 + numWiresPerElement];
                  inputs[i][0] = (Wire)this.operations.get(i);
                  inputs[i][1] = (Wire)this.indexesToOperate.get(i);
                  inputs[i][2] = (Wire)this.opCounters.get(i);
                  outputs[i][0] = (Wire)operations2.get(i);
                  outputs[i][1] = (Wire)indexesToOperate2.get(i);
                  outputs[i][2] = (Wire)opCounters2.get(i);

                  for(i = 0; i < numWiresPerElement; ++i) {
                     inputs[i][3 + i] = ((PackedValue)this.data.get(i)).array[i];
                     outputs[i][3 + i] = ((PackedValue)data2.get(i)).array[i];
                  }
               }
            }

            new PermutationNetworkGadget(inputs, outputs, new int[0], "INT_" + this.memoryIndex);
            PackedValue defaultPackedValue = null;
            int j;
            Wire isRead;
            if (this.defaultElement != null) {
               defaultPackedValue = this.getPackedValue(this.defaultElement);
               isRead = ((Wire)operations2.get(0)).invAsBit();

               for(j = 0; j < numWiresPerElement; ++j) {
                  if (j < defaultPackedValue.array.length) {
                     this.generator.__addZeroAssertion(isRead.mul(((PackedValue)data2.get(0)).array[j].sub(defaultPackedValue.array[j])), "Checking equality with default value if first operation is read");
                  } else {
                     this.generator.__addZeroAssertion(isRead.mul(((PackedValue)data2.get(0)).array[j].sub(this.generator.__getZeroWire())), "Checking equality with default value if first operation is read");
                  }
               }
            } else {
               isRead = ((Wire)operations2.get(0)).invAsBit();

               for(j = 0; j < numWiresPerElement; ++j) {
                  this.generator.__addZeroAssertion(isRead.mul(((PackedValue)data2.get(0)).array[j].sub(this.generator.__getZeroWire())), "Checking equality with default value if first operation is read");
               }
            }

            for(i = 1; i < k; ++i) {
               greaterIndex = ((Wire)indexesToOperate2.get(i)).isGreaterThan((Wire)indexesToOperate2.get(i - 1), indexBitwidth);
               Wire equalIndex = ((Wire)indexesToOperate2.get(i)).isEqualTo((Wire)indexesToOperate2.get(i - 1));
               packedWire2 = ((Wire)opCounters2.get(i)).isGreaterThan((Wire)opCounters2.get(i - 1), opCounterBitwidth);
               this.generator.__addOneAssertion(greaterIndex.add(equalIndex.mul(packedWire2)), "Either a greater index or equal index and higher op counter");
               Wire isRead = ((Wire)operations2.get(i)).invAsBit();
               if (defaultPackedValue != null) {
                  for(j = 0; j < numWiresPerElement; ++j) {
                     Wire tmp;
                     if (j < defaultPackedValue.array.length) {
                        tmp = defaultPackedValue.array[j];
                     } else {
                        tmp = this.generator.__getZeroWire();
                     }

                     tmp = tmp.add(equalIndex.mul(((PackedValue)data2.get(i - 1)).array[j].sub(tmp)));
                     this.generator.__addZeroAssertion(isRead.mul(((PackedValue)data2.get(i)).array[j].sub(tmp)));
                  }
               } else {
                  this.generator.__addZeroAssertion(equalIndex.invAsBit().mul(isRead.invAsBit().sub(1L)), "if index not equal, verify it's a write");

                  for(j = 0; j < numWiresPerElement; ++j) {
                     this.generator.__addZeroAssertion(isRead.mul(((PackedValue)data2.get(i)).array[j].sub(((PackedValue)data2.get(i - 1)).array[j])), "consistent data items");
                  }
               }
            }
         }

      }
   }

   public void incrementSize() {
      if (this.generator.__getPhase() == 0) {
         ++this.numElements;
      }

   }

   public void analyzeWorkload() {
      System.out.println("Analyzing memory workload (more updates optimizing memory access costs are expected soon) .. ");
      if (this.generator.__getPhase() == 1) {
         throw new RuntimeException("Should be called in the first phase");
      } else {
         int[] elementSizes = this.getElementSize();
         int numOfWordsPerElement = elementSizes.length;
         int elementSize = 0;
         int[] var7 = elementSizes;
         int var6 = elementSizes.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            int x = var7[var5];
            elementSize += x;
         }

         long readOnlyMethodCost = 0L;
         boolean isConstant = false;
         boolean inSupportedRange = false;
         int readOnlyMaxBitwidth = 0;
         BigInteger[] vals = null;
         if (this.numOfVariableWrites == 0L && this.numOfConstWrites == 0L && this.numOfInitialConstWrites == 0L) {
            isConstant = true;
            vals = new BigInteger[this.numElements];
            inSupportedRange = true;

            for(int i = 0; i < this.numElements; ++i) {
               BigInteger c = null;
               if (IAuxType.class.isAssignableFrom(this.typeClass)) {
                  c = ((IAuxType)this.elements[i]).getConstant();
               } else if (RuntimeStruct.class.isAssignableFrom(this.typeClass)) {
                  c = ((RuntimeStruct)this.elements[i]).ptrReference.getConstant();
               }

               if (c == null) {
                  isConstant = false;
                  break;
               }

               if (c.bitLength() > 128) {
                  inSupportedRange = false;
                  break;
               }

               vals[i] = c;
               int bitwidth = c.bitLength();
               if (bitwidth > readOnlyMaxBitwidth) {
                  readOnlyMaxBitwidth = bitwidth;
               }
            }

            if (isConstant && inSupportedRange) {
               readOnlyMethodCost = (long)Math.ceil(Math.sqrt((double)this.numElements)) * this.numOfVariableReads * 2L;
               this.readOnly = true;
            }
         }

         System.out.println("Num of variable reads = " + this.numOfVariableReads);
         System.out.println("Num of conditional writes = " + this.numOfCoditionalWrites);
         System.out.println("Num of words per element = " + numOfWordsPerElement);
         System.out.println("Num of variable writes = " + this.numOfVariableWrites);
         long linearCost = this.numOfVariableReads * (long)this.numElements * (long)numOfWordsPerElement + (this.numOfVariableWrites - this.numOfCoditionalWrites) * (long)this.numElements * (long)numOfWordsPerElement + this.numOfCoditionalWrites * 2L * (long)this.numElements * (long)numOfWordsPerElement + (this.numOfVariableReads + this.numOfVariableWrites) * 2L * (long)this.numElements;
         long accesses = this.numOfVariableReads + this.numOfVariableWrites + this.numOfConstReads + this.numOfConstWrites;
         System.out.println("num of accesses = " + accesses);
         if (!this.isEmpty) {
            accesses += (long)this.numElements;
         }

         int indexBitwidth = (int)Math.ceil(Math.log((double)this.numElements) / Math.log(2.0));
         int opCounterBitwidth = (int)Math.ceil(Math.log((double)accesses) / Math.log(2.0));
         int b1 = 1 + elementSize + indexBitwidth + opCounterBitwidth;
         long cost1;
         if (b1 > Config.getNumBitsFiniteFieldModulus() - 1) {
            cost1 = -1L;
         } else {
            cost1 = (long)(1 + elementSize + indexBitwidth + opCounterBitwidth) * accesses + accesses * (long)opCounterBitwidth + accesses * (long)(7 + indexBitwidth + opCounterBitwidth) + accesses * (long)elementSize;
         }

         int numOfWires = elementSizes.length + 3;
         long cost2 = accesses * (long)opCounterBitwidth * (long)(numOfWires + 1) + accesses * (long)(7 + indexBitwidth + opCounterBitwidth);
         long nwCost = cost2;
         this.state.packingOption = 0;
         if (cost1 < cost2 && cost1 > 0L) {
            nwCost = cost1;
            this.state.packingOption = 1;
         }

         System.out.println("Packing Option = " + this.state.packingOption);
         if (this.readOnly && readOnlyMethodCost < linearCost && readOnlyMethodCost < nwCost) {
            System.out.println("Decision: Using Read-only O(sqrt n) mode for this memory");
            this.state.readOnly = true;
            this.state.maxBitwidth = readOnlyMaxBitwidth;
            StaticMemorySolver.preprocess(vals, readOnlyMaxBitwidth, this.indexes, this.state);
         } else {
            if (nwCost > linearCost) {
               this.state.mode = 2;
               System.out.println("Decision: Using Linear mode for this memory");
            } else {
               this.state.mode = 1;
               System.out.println("Decision: Using Network mode for this memory");
            }

         }
      }
   }

   public MemoryState getState() {
      return this.state;
   }

   public void verifyMembership(T element) {
   }

   public Bit checkMembership(T element) {
      return null;
   }

   private static class ArrayIndexComparator implements Comparator<Integer> {
      private final MemoryConsistencyRecord[] array;

      public ArrayIndexComparator(MemoryConsistencyRecord[] array) {
         this.array = array;
      }

      public Integer[] createIndexArray() {
         Integer[] indexes = new Integer[this.array.length];

         for(int i = 0; i < this.array.length; ++i) {
            indexes[i] = i;
         }

         return indexes;
      }

      public int compare(Integer index1, Integer index2) {
         return this.array[index1].compareTo(this.array[index2]);
      }
   }

   private static class MemoryCacheRecord {
      private UnsignedInteger idx;
      private int serialCounter;

      public MemoryCacheRecord(UnsignedInteger idx, int internalStateSerial) {
         this.idx = idx;
         this.serialCounter = internalStateSerial;
      }

      public int hashCode() {
         return this.idx.hashCode() + this.serialCounter;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o instanceof MemoryCacheRecord) {
            MemoryCacheRecord r = (MemoryCacheRecord)o;
            return r.idx == this.idx && r.serialCounter == this.serialCounter;
         } else {
            return false;
         }
      }
   }

   private static class MemoryConsistencyRecord implements Comparable<MemoryConsistencyRecord> {
      private BigInteger op;
      private BigInteger index;
      private BigInteger counter;
      private BigInteger[] data;

      private MemoryConsistencyRecord() {
      }

      public int compareTo(MemoryConsistencyRecord o) {
         if (this.index.compareTo(o.index) < 0) {
            return -1;
         } else {
            return this.index.compareTo(o.index) > 0 ? 1 : this.counter.compareTo(o.counter);
         }
      }

      // $FF: synthetic method
      MemoryConsistencyRecord(MemoryConsistencyRecord var1) {
         this();
      }
   }

   public static class MemoryState {
      private int mode;
      private int packingOption;
      private int indexBitsSplitted;
      private HashSet<Integer> redundantWrites;
      private boolean readOnly = false;
      public int bitCount = 0;
      public ArrayList<BigInteger[]> allCoeffSet;
      public int maxBitwidth;

      public int getMode() {
         return this.mode;
      }

      public int getPackingOption() {
         return this.packingOption;
      }

      public int getIndexBitsSplitted() {
         return this.indexBitsSplitted;
      }

      public void recordRedundantWire(int op) {
         if (this.redundantWrites == null) {
            this.redundantWrites = new HashSet();
         }

         this.redundantWrites.add(op);
      }
   }
}
