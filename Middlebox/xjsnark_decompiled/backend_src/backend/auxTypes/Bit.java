package backend.auxTypes;

import backend.eval.CircuitEvaluator;
import backend.structure.CircuitGenerator;
import backend.structure.ConstantWire;
import backend.structure.Wire;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import util.Util;

public class Bit implements IAuxType, ConditionalScopeImpactedType {
   protected Wire wire;
   protected BigInteger constant;
   protected CircuitGenerator generator;
   protected int conditionalScopeId = ConditionalScopeTracker.getCurrentScopeId();
   protected int scope = ConditionalScopeTracker.getCurrentScopeId();
   protected Stack<HashMap<Integer, Bit>> possibleValStack;
   protected Stack<Bit> prevValStack;
   protected boolean stateChanged;

   public Bit(Wire wire) {
      this.wire = wire;
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      if (wire instanceof ConstantWire) {
         this.constant = ((ConstantWire)wire).getConstant();
      }

   }

   public Bit() {
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
   }

   public Bit(BigInteger value) {
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      if (!Util.isBinary(value)) {
         throw new IllegalArgumentException();
      } else {
         this.wire = this.generator.__createConstantWire(value);
         this.constant = value;
      }
   }

   public Bit(boolean b) {
      this.generator = CircuitGenerator.__getActiveCircuitGenerator();
      this.wire = this.generator.__createConstantWire((long)(b ? 1 : 0));
      this.constant = b ? BigInteger.ONE : BigInteger.ZERO;
   }

   private void init() {
   }

   public void assign(Bit target) {
      int current;
      int i;
      if (this.generator.__getPhase() == 0) {
         this.constant = target.constant;
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
               Bit c = this.copy();
               this.prevValStack.push(c);
               this.possibleValStack.push(new HashMap());
            }

            this.stateChanged = true;
            this.scope = ConditionalScopeTracker.getCurrentScopeId();
         }
      } else if (this.scope == ConditionalScopeTracker.getCurrentScopeId()) {
         this.wire = target.wire;
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

         this.wire = target.wire;
         this.scope = ConditionalScopeTracker.getCurrentScopeId();
         this.constant = target.constant;
      }

   }

   public void pop(int id) {
      if (this.stateChanged) {
         Bit copy = this.copy();
         ((HashMap)this.possibleValStack.peek()).put(id, copy);
         --this.scope;
         Bit prev = (Bit)this.prevValStack.peek();
         this.wire = prev.wire;
         this.constant = prev.constant;
         this.stateChanged = false;
      }
   }

   public void popMain() {
      if (this.generator.__getPhase() == 0) {
         this.constant = null;
         this.stateChanged = true;
      } else {
         int tmp = this.scope;
         if (ConditionalScopeTracker.getCurrentScopeId() > tmp) {
            this.stateChanged = true;
         }

         ConditionalScopeTracker.ConditionalStatementData condData = ConditionalScopeTracker.getCurrentConditionalStmtData();
         int numberOfValues = condData.getBitList().size();
         ArrayList<Bit> conditionList = condData.getBitList();
         Bit[] candidateList = new Bit[numberOfValues];
         HashMap<Integer, Bit> possibleVals = (HashMap)this.possibleValStack.pop();

         Integer idx;
         for(Iterator var8 = possibleVals.keySet().iterator(); var8.hasNext(); candidateList[idx] = (Bit)possibleVals.get(idx)) {
            idx = (Integer)var8.next();
         }

         for(int i = 0; i < numberOfValues; ++i) {
            if (candidateList[i] == null) {
               candidateList[i] = this.copy();
            }
         }

         Bit initial = candidateList[numberOfValues - 1];
         int startingIndex = -1;

         for(int i = numberOfValues - 2; i >= 0; --i) {
            if (candidateList[i].wire != initial.wire) {
               startingIndex = i;
               break;
            }
         }

         if (startingIndex == -1) {
            this.wire = initial.wire;
            this.constant = initial.constant;
         } else {
            this.wire = initial.wire;

            for(int j = startingIndex; j >= 0; --j) {
               Bit current = candidateList[j];
               Bit selectionBit = (Bit)conditionList.get(j);
               this.wire = this.wire.add(selectionBit.wire.mul(current.wire.sub(this.wire)));
               this.constant = null;
            }
         }

         this.prevValStack.pop();
         this.init();
      }

   }

   public Bit copy() {
      Bit v;
      if (this.generator.__getPhase() == 0) {
         v = new Bit();
         v.wire = this.wire;
         v.constant = this.constant;
         v.generator = this.generator;
         return v;
      } else {
         v = new Bit();
         v.wire = this.wire;
         v.generator = this.generator;
         v.constant = this.constant;
         return v;
      }
   }

   public UnsignedInteger toUnsignedInteger() {
      return new UnsignedInteger(1, new PackedValue(this.wire, 1));
   }

   public UnsignedInteger add(Bit o) {
      return this.toUnsignedInteger().add(o.toUnsignedInteger());
   }

   public UnsignedInteger add(boolean o) {
      return this.toUnsignedInteger().add((new Bit(o)).toUnsignedInteger());
   }

   public UnsignedInteger add(BigInteger o) {
      return this.toUnsignedInteger().add((new Bit(o)).toUnsignedInteger());
   }

   public Bit mul(Bit o) {
      if (this.isConstant() && o.isConstant()) {
         return new Bit(this.getConstant().and(o.getConstant()).equals(BigInteger.ONE));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ZERO)) {
         return new Bit(false);
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         return new Bit(false);
      } else {
         return this.generator.__getPhase() == 0 ? new Bit(new Wire(-1)) : new Bit(this.wire.mul(o.wire));
      }
   }

   public Bit xor(Bit o) {
      if (this.isConstant() && o.isConstant()) {
         return new Bit(this.getConstant().xor(o.getConstant()).equals(BigInteger.ONE));
      } else {
         return this.generator.__getPhase() == 0 ? new Bit(new Wire(-1)) : new Bit(this.wire.xor(o.wire));
      }
   }

   public Bit or(Bit o) {
      if (this.isConstant() && o.isConstant()) {
         return new Bit(this.getConstant().or(o.getConstant()).equals(BigInteger.ONE));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ONE)) {
         return new Bit(true);
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ONE)) {
         return new Bit(true);
      } else {
         return this.generator.__getPhase() == 0 ? new Bit(new Wire(-1)) : new Bit(this.wire.or(o.wire));
      }
   }

   public Bit and(Bit o) {
      if (this.isConstant() && o.isConstant()) {
         return new Bit(this.getConstant().and(o.getConstant()).equals(BigInteger.ONE));
      } else if (this.isConstant() && this.getConstant().equals(BigInteger.ZERO)) {
         return new Bit(false);
      } else if (o.isConstant() && o.getConstant().equals(BigInteger.ZERO)) {
         return new Bit(false);
      } else {
         return this.generator.__getPhase() == 0 ? new Bit(new Wire(-1)) : new Bit(this.wire.mul(o.wire));
      }
   }

   public Bit inv() {
      if (this.isConstant()) {
         return new Bit(this.getConstant().equals(BigInteger.ZERO) ? BigInteger.ONE : BigInteger.ZERO);
      } else {
         return this.generator.__getPhase() == 0 ? new Bit(new Wire(-1)) : new Bit(this.wire.invAsBit());
      }
   }

   public boolean isConstant() {
      return this.constant != null;
   }

   public BigInteger getConstant() {
      if (!this.isConstant()) {
         return null;
      } else {
         return this.getConstantValue() ? BigInteger.ONE : BigInteger.ZERO;
      }
   }

   public Boolean getConstantValue() {
      return this.constant == null ? null : this.constant.equals(BigInteger.ONE);
   }

   public Wire getWire() {
      return this.wire;
   }

   public int getCurrentBitWidth() {
      return 1;
   }

   public Wire[] toWires() {
      return new Wire[]{this.wire};
   }

   public void mapValue(BigInteger value, CircuitEvaluator evaluator) {
      evaluator.setWireValue(this.wire, value);
   }

   public BigInteger getValueFromEvaluator(CircuitEvaluator evaluator) {
      return evaluator.getWireValue(this.wire);
   }

   public static Bit createInput(CircuitGenerator generator, String... desc) {
      Wire w = generator.__createInputWire(desc);
      Bit o = new Bit(w);
      generator.__getInputAux().add(o.copy());
      return o;
   }

   public static Bit[] createInputArray(CircuitGenerator generator, int size, String... desc) {
      Bit[] out = new Bit[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createInput(generator, desc);
      }

      return out;
   }

   public static Bit[] createZeroArray(CircuitGenerator generator, int size, String... desc) {
      Bit[] out = new Bit[size];

      for(int i = 0; i < size; ++i) {
         out[i] = new Bit(false);
      }

      return out;
   }

   public static Object createZeroArray(CircuitGenerator generator, int[] dims, String... desc) {
      if (dims.length == 1) {
         return createZeroArray(generator, dims[0], desc);
      } else {
         int i;
         if (dims.length == 2) {
            Bit[][] out = new Bit[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createZeroArray(generator, dims[1], desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dim arrays not supported at this point");
         } else {
            Bit[][][] out = new Bit[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createZeroArray(generator, dims[2], desc);
               }
            }

            return out;
         }
      }
   }

   public static Bit createWitness(CircuitGenerator generator, String... desc) {
      Wire w = generator.__createInputWire(desc);
      Bit o = new Bit(w);
      generator.__getProverAux().add(o.copy());
      return o;
   }

   public static Bit[] createWitnessArray(CircuitGenerator generator, int size, String... desc) {
      Bit[] out = new Bit[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createWitness(generator, desc);
      }

      return out;
   }

   public static Bit createVerifiedWitness(CircuitGenerator generator, String... desc) {
      Wire w = generator.__createInputWire(desc);
      Bit o = new Bit(w);
      generator.__getProverVerifiedAux().add(o.copy());
      return o;
   }

   public static Bit[] createVerifiedWitnessArray(CircuitGenerator generator, int size, String... desc) {
      Bit[] out = new Bit[size];

      for(int i = 0; i < size; ++i) {
         out[i] = createVerifiedWitness(generator, desc);
      }

      return out;
   }

   public void makeOutput(String... desc) {
      this.generator.__makeOutput(this.wire, desc);
   }

   public static void makeOutput(CircuitGenerator generator, Bit x, String... desc) {
      x.makeOutput();
   }

   public static void makeOutput(CircuitGenerator generator, Bit[] a, String... desc) {
      Bit[] var6 = a;
      int var5 = a.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         Bit x = var6[var4];
         x.makeOutput();
      }

   }

   public void mapRandomValue(CircuitEvaluator evaluator) {
      BigInteger rnd = Util.nextRandomBigInteger(1);
      evaluator.setWireValue(this.wire, rnd);
   }

   public int getRequiredBitWidth() {
      return 1;
   }

   public UnsignedInteger mul(UnsignedInteger k) {
      return k.mul(this);
   }

   public UnsignedInteger add(UnsignedInteger k) {
      return k.add(this);
   }

   public FieldElement mul(FieldElement k) {
      return k.mul(this);
   }

   public GroupElement mul(GroupElement k) {
      return k.mul(this);
   }

   public FieldElement add(FieldElement k) {
      return k.add(this);
   }

   public Bit[] getBitElements() {
      return new Bit[]{this};
   }

   public void forceEqual(IAuxType o) {
      if (!(o instanceof Bit)) {
         throw new IllegalArgumentException("not an instance of bit type");
      } else {
         Bit other = (Bit)o;
         if (this.getConstant() != null && other.getConstant() != null && !this.getConstant().equals(other.getConstant())) {
            throw new RuntimeException("Constraint fails on constant bits");
         } else {
            if (this.generator.__getPhase() != 0) {
               this.generator.__addEqualityAssertion(this.wire, ((Bit)o).wire);
            }

         }
      }
   }

   public Bit isEqualTo(IAuxType o) {
      if (!(o instanceof Bit)) {
         throw new IllegalArgumentException("not an instance of bit");
      } else {
         Bit b = (Bit)o;
         if (b.isConstant() && o.isConstant()) {
            return new Bit(b.constant.equals(this.constant));
         } else {
            return this.generator.__getPhase() == 0 ? new Bit(new Wire(-1)) : new Bit(this.wire.xor(b.wire).invAsBit());
         }
      }
   }

   public Bit isNotEqualTo(IAuxType o) {
      return this.isEqualTo(o).inv();
   }

   public static Class<?> __getClassRef() {
      return Bit.class;
   }

   public VariableState getState() {
      return new VariableState();
   }

   public PackedValue getPackedValue() {
      return new PackedValue(this.wire, 1);
   }

   public void verifyRange() {
      this.generator.__addBinaryAssertion(this.wire);
   }

   public static Bit instantiateFrom(boolean v) {
      return new Bit(v);
   }

   public static Bit[] instantiateFrom(boolean[] v) {
      Bit[] a = new Bit[v.length];

      for(int i = 0; i < a.length; ++i) {
         a[i] = instantiateFrom(v[i]);
      }

      return a;
   }

   public static Object createInputArray(CircuitGenerator generator, int[] dims, String... desc) {
      if (dims.length == 1) {
         return createInputArray(generator, dims[0], desc);
      } else {
         int i;
         if (dims.length == 2) {
            Bit[][] out = new Bit[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createInputArray(generator, dims[1], desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            Bit[][][] out = new Bit[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createInputArray(generator, dims[2], desc);
               }
            }

            return out;
         }
      }
   }

   public static Object createWitnessArray(CircuitGenerator generator, int[] dims, String... desc) {
      if (dims.length == 1) {
         return createWitnessArray(generator, dims[0], desc);
      } else {
         int i;
         if (dims.length == 2) {
            Bit[][] out = new Bit[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createWitnessArray(generator, dims[1], desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            Bit[][][] out = new Bit[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createWitnessArray(generator, dims[2], desc);
               }
            }

            return out;
         }
      }
   }

   public static Object createVerifiedWitnessArray(CircuitGenerator generator, int[] dims, String... desc) {
      if (dims.length == 1) {
         return createVerifiedWitnessArray(generator, dims[0], desc);
      } else {
         int i;
         if (dims.length == 2) {
            Bit[][] out = new Bit[dims[0]][];

            for(i = 0; i < dims[0]; ++i) {
               out[i] = createVerifiedWitnessArray(generator, dims[1], desc);
            }

            return out;
         } else if (dims.length != 3) {
            throw new IllegalArgumentException("Initialization of higher dimensional arrays as inputs not supported at this point. Only 3 dimensions are supported");
         } else {
            Bit[][][] out = new Bit[dims[0]][dims[1]][];

            for(i = 0; i < dims[0]; ++i) {
               for(int j = 0; j < dims[1]; ++j) {
                  out[i][j] = createVerifiedWitnessArray(generator, dims[2], desc);
               }
            }

            return out;
         }
      }
   }

   public static void makeOutput(CircuitGenerator generator, Object a, String... desc) {
      int i;
      if (a instanceof Bit[]) {
         Bit[] array = (Bit[])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, array[i], desc);
         }
      } else if (a instanceof Bit[][]) {
         Bit[][] array = (Bit[][])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, array[i], desc);
         }
      } else {
         if (!(a instanceof Bit[][][])) {
            throw new IllegalArgumentException("Declaring higher dimensional arrays as outputs not supported at this point. Only 3 dimensions are supported");
         }

         Bit[][][] array = (Bit[][][])a;

         for(i = 0; i < array.length; ++i) {
            makeOutput(generator, (Object)array[i], desc);
         }
      }

   }
}
