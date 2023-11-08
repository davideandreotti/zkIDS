package examples.gadgets;

import backend.operations.Gadget;
import backend.structure.Wire;
import backend.structure.WireArray;
import java.util.Arrays;
import util.Util;

public class SHA256Gadget extends Gadget {
   private static final long[] H = new long[]{1779033703L, 3144134277L, 1013904242L, 2773480762L, 1359893119L, 2600822924L, 528734635L, 1541459225L};
   private static final long[] K = new long[]{1116352408L, 1899447441L, 3049323471L, 3921009573L, 961987163L, 1508970993L, 2453635748L, 2870763221L, 3624381080L, 310598401L, 607225278L, 1426881987L, 1925078388L, 2162078206L, 2614888103L, 3248222580L, 3835390401L, 4022224774L, 264347078L, 604807628L, 770255983L, 1249150122L, 1555081692L, 1996064986L, 2554220882L, 2821834349L, 2952996808L, 3210313671L, 3336571891L, 3584528711L, 113926993L, 338241895L, 666307205L, 773529912L, 1294757372L, 1396182291L, 1695183700L, 1986661051L, 2177026350L, 2456956037L, 2730485921L, 2820302411L, 3259730800L, 3345764771L, 3516065817L, 3600352804L, 4094571909L, 275423344L, 430227734L, 506948616L, 659060556L, 883997877L, 958139571L, 1322822218L, 1537002063L, 1747873779L, 1955562222L, 2024104815L, 2227730452L, 2361852424L, 2428436474L, 2756734187L, 3204031479L, 3329325298L};
   private Wire[] unpaddedInputs;
   private int bitwidthPerInputElement;
   private int totalLengthInBytes;
   private int numBlocks;
   private boolean binaryOutput;
   private boolean paddingRequired;
   private Wire[] preparedInputBits;
   private Wire[] output;

   public SHA256Gadget(Wire[] ins, int bitWidthPerInputElement, int totalLengthInBytes, boolean binaryOutput, boolean paddingRequired, String... desc) {
      super(desc);
      if (totalLengthInBytes * 8 <= ins.length * bitWidthPerInputElement && totalLengthInBytes * 8 >= (ins.length - 1) * bitWidthPerInputElement) {
         if (!paddingRequired && totalLengthInBytes % 64 != 0 && ins.length * bitWidthPerInputElement != totalLengthInBytes) {
            throw new IllegalArgumentException("When padding is not forced, totalLengthInBytes % 64 must be zero.");
         } else {
            this.unpaddedInputs = ins;
            this.bitwidthPerInputElement = bitWidthPerInputElement;
            this.totalLengthInBytes = totalLengthInBytes;
            this.binaryOutput = binaryOutput;
            this.paddingRequired = paddingRequired;
            this.buildCircuit();
         }
      } else {
         throw new IllegalArgumentException("Inconsistent Length Information");
      }
   }

   protected void buildCircuit() {
      this.prepare();
      Wire[] outDigest = new Wire[8];
      Wire[] hWires = new Wire[H.length];

      int i;
      for(i = 0; i < H.length; ++i) {
         hWires[i] = this.generator.__createConstantWire(H[i]);
      }

      for(i = 0; i < this.numBlocks; ++i) {
         Wire[][] wsSplitted = new Wire[64][];
         Wire[] w = new Wire[64];

         Wire b;
         Wire c;
         Wire d;
         Wire e;
         Wire f;
         Wire g;
         Wire h;
         for(int i = 0; i < 64; ++i) {
            if (i < 16) {
               wsSplitted[i] = Util.reverseBytes((Wire[])Arrays.copyOfRange(this.preparedInputBits, i * 512 + i * 32, i * 512 + (i + 1) * 32));
               w[i] = (new WireArray(wsSplitted[i])).packAsBits(32);
            } else {
               b = w[i - 15].rotateRight(32, 7);
               c = w[i - 15].rotateRight(32, 18);
               d = w[i - 15].shiftRight(32, 3);
               e = b.xorBitwise((Wire)c, 32);
               e = e.xorBitwise((Wire)d, 32);
               f = w[i - 2].rotateRight(32, 17);
               g = w[i - 2].rotateRight(32, 19);
               h = w[i - 2].shiftRight(32, 10);
               Wire s1 = f.xorBitwise((Wire)g, 32);
               s1 = s1.xorBitwise((Wire)h, 32);
               w[i] = w[i - 16].add(w[i - 7]);
               w[i] = w[i].add(e).add(s1);
               w[i] = w[i].trimBits(34, 32);
            }
         }

         Wire a = hWires[0];
         b = hWires[1];
         c = hWires[2];
         d = hWires[3];
         e = hWires[4];
         f = hWires[5];
         g = hWires[6];
         h = hWires[7];

         for(int i = 0; i < 64; ++i) {
            Wire t1 = e.rotateRight(32, 6);
            Wire t2 = e.rotateRight(32, 11);
            Wire t3 = e.rotateRight(32, 25);
            Wire s1 = t1.xorBitwise((Wire)t2, 32);
            s1 = s1.xorBitwise((Wire)t3, 32);
            Wire ch = this.computeCh(e, f, g, 32);
            Wire t4 = a.rotateRight(32, 2);
            Wire t5 = a.rotateRight(32, 13);
            Wire t6 = a.rotateRight(32, 22);
            Wire s0 = t4.xorBitwise((Wire)t5, 32);
            s0 = s0.xorBitwise((Wire)t6, 32);
            Wire maj;
            if (i % 2 == 1) {
               maj = this.computeMaj(c, b, a, 32);
            } else {
               maj = this.computeMaj(a, b, c, 32);
            }

            Wire temp1 = w[i].add(K[i]).add(s1).add(h).add(ch);
            Wire temp2 = maj.add(s0);
            h = g;
            g = f;
            f = e;
            e = temp1.add(d);
            e = e.trimBits(35, 32);
            d = c;
            c = b;
            b = a;
            a = temp2.add(temp1);
            a = a.trimBits(35, 32);
         }

         hWires[0] = hWires[0].add(a).trimBits(33, 32);
         hWires[1] = hWires[1].add(b).trimBits(33, 32);
         hWires[2] = hWires[2].add(c).trimBits(33, 32);
         hWires[3] = hWires[3].add(d).trimBits(33, 32);
         hWires[4] = hWires[4].add(e).trimBits(33, 32);
         hWires[5] = hWires[5].add(f).trimBits(33, 32);
         hWires[6] = hWires[6].add(g).trimBits(33, 32);
         hWires[7] = hWires[7].add(h).trimBits(33, 32);
      }

      outDigest[0] = hWires[0];
      outDigest[1] = hWires[1];
      outDigest[2] = hWires[2];
      outDigest[3] = hWires[3];
      outDigest[4] = hWires[4];
      outDigest[5] = hWires[5];
      outDigest[6] = hWires[6];
      outDigest[7] = hWires[7];
      if (!this.binaryOutput) {
         this.output = outDigest;
      } else {
         this.output = new Wire[256];

         for(i = 0; i < 8; ++i) {
            Wire[] bits = outDigest[i].getBitWires(32).asArray();

            for(int j = 0; j < 32; ++j) {
               this.output[j + i * 32] = bits[j];
            }
         }
      }

   }

   private Wire computeMaj(Wire a, Wire b, Wire c, int numBits) {
      Wire[] result = new Wire[numBits];
      Wire[] aBits = a.getBitWires(numBits).asArray();
      Wire[] bBits = b.getBitWires(numBits).asArray();
      Wire[] cBits = c.getBitWires(numBits).asArray();

      for(int i = 0; i < numBits; ++i) {
         Wire t1 = aBits[i].mul(bBits[i]);
         Wire t2 = aBits[i].add(bBits[i]).add(t1.mul(-2L));
         result[i] = t1.add(cBits[i].mul(t2));
      }

      return (new WireArray(result)).packAsBits();
   }

   private Wire computeCh(Wire a, Wire b, Wire c, int numBits) {
      Wire[] result = new Wire[numBits];
      Wire[] aBits = a.getBitWires(numBits).asArray();
      Wire[] bBits = b.getBitWires(numBits).asArray();
      Wire[] cBits = c.getBitWires(numBits).asArray();

      for(int i = 0; i < numBits; ++i) {
         Wire t1 = bBits[i].sub(cBits[i]);
         Wire t2 = t1.mul(aBits[i]);
         result[i] = t2.add(cBits[i]);
      }

      return (new WireArray(result)).packAsBits();
   }

   private void prepare() {
      this.numBlocks = (int)Math.ceil((double)this.totalLengthInBytes * 1.0 / 64.0);
      if (this.numBlocks == 0) {
         ++this.numBlocks;
      }

      Wire[] bits = (new WireArray(this.unpaddedInputs)).getBits(this.bitwidthPerInputElement).asArray();
      int tailLength = this.totalLengthInBytes % 64;
      if (this.paddingRequired) {
         Wire[] pad;
         if (64 - tailLength >= 9) {
            pad = new Wire[64 - tailLength];
         } else {
            pad = new Wire[128 - tailLength];
            ++this.numBlocks;
         }

         pad[0] = this.generator.__createConstantWire(128L);

         for(int i = 1; i < pad.length - 8; ++i) {
            pad[i] = this.generator.__getZeroWire();
         }

         long lengthInBits = (long)(this.totalLengthInBytes * 8);
         Wire[] lengthBits = new Wire[64];

         int totalNumberOfBits;
         for(totalNumberOfBits = 0; totalNumberOfBits < 8; ++totalNumberOfBits) {
            pad[pad.length - 1 - totalNumberOfBits] = this.generator.__createConstantWire(lengthInBits >>> 8 * totalNumberOfBits & 255L);
            Wire[] tmp = pad[pad.length - 1 - totalNumberOfBits].getBitWires(8).asArray();
            System.arraycopy(tmp, 0, lengthBits, (7 - totalNumberOfBits) * 8, 8);
         }

         totalNumberOfBits = this.numBlocks * 512;
         this.preparedInputBits = new Wire[totalNumberOfBits];
         Arrays.fill(this.preparedInputBits, this.generator.__getZeroWire());
         System.arraycopy(bits, 0, this.preparedInputBits, 0, bits.length);
         this.preparedInputBits[bits.length + 7] = this.generator.__getOneWire();
         System.arraycopy(lengthBits, 0, this.preparedInputBits, this.preparedInputBits.length - 64, 64);
      } else {
         this.preparedInputBits = bits;
      }

   }

   public Wire[] getOutputWires() {
      return this.output;
   }
}
