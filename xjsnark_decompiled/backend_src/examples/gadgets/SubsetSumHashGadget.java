package examples.gadgets;

import backend.config.Config;
import backend.operations.Gadget;
import backend.structure.Wire;
import java.math.BigInteger;
import java.util.Arrays;
import util.Util;

public class SubsetSumHashGadget extends Gadget {
   public static final int DIMENSION = 3;
   public static final int INPUT_LENGTH = 6 * Config.getNumBitsFiniteFieldModulus();
   private static final BigInteger[][] COEFFS;
   private Wire[] inputWires;
   private Wire[] outWires;
   private boolean binaryOutput;

   static {
      COEFFS = new BigInteger[3][INPUT_LENGTH];

      for(int i = 0; i < 3; ++i) {
         for(int k = 0; k < INPUT_LENGTH; ++k) {
            COEFFS[i][k] = Util.nextRandomBigInteger(Config.getFiniteFieldModulus());
         }
      }

   }

   public SubsetSumHashGadget(Wire[] ins, boolean binaryOutput, String... desc) {
      super(desc);
      int numBlocks = (int)Math.ceil((double)ins.length * 1.0 / (double)INPUT_LENGTH);
      if (numBlocks > 1) {
         throw new IllegalArgumentException("Only one block is supported at this point");
      } else {
         int rem = numBlocks * INPUT_LENGTH - ins.length;
         Wire[] pad = new Wire[rem];

         for(int i = 0; i < pad.length; ++i) {
            pad[i] = this.generator.__getZeroWire();
         }

         this.inputWires = Util.concat(ins, pad);
         this.binaryOutput = binaryOutput;
         this.buildCircuit();
      }
   }

   private void buildCircuit() {
      Wire[] outDigest = new Wire[3];
      Arrays.fill(outDigest, this.generator.__getZeroWire());

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < INPUT_LENGTH; ++j) {
            Wire t = this.inputWires[j].mul(COEFFS[i][j]);
            System.out.println(t);
            outDigest[i] = outDigest[i].add(t);
         }
      }

      if (!this.binaryOutput) {
         this.outWires = outDigest;
      } else {
         this.outWires = new Wire[3 * Config.getNumBitsFiniteFieldModulus()];

         for(i = 0; i < 3; ++i) {
            Wire[] bits = outDigest[i].getBitWires(Config.getNumBitsFiniteFieldModulus()).asArray();

            for(int j = 0; j < bits.length; ++j) {
               this.outWires[j + i * Config.getNumBitsFiniteFieldModulus()] = bits[j];
            }
         }
      }

   }

   public Wire[] getOutputWires() {
      return this.outWires;
   }
}
