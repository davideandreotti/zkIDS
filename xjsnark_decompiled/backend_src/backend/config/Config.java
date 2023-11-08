package backend.config;

import java.math.BigInteger;

public class Config {
   private static BigInteger finiteFieldModulus = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617");
   private static int numBitsFiniteFieldModulus;
   public static final boolean runningMultiGenerators = false;
   public static boolean hexOutputEnabled;
   public static boolean inputVerbose;
   public static boolean outputVerbose;
   public static boolean debugVerbose;
   public static boolean writeCircuits;
   public static String outputFilesPath;
   public static boolean multivariateExpressionMinimization;
   public static int arithOptimizerNumThreads;
   public static int arithOptimizerTimeoutPerProblemMilliSec;
   public static boolean arithOptimizerIncrementalMode;
   public static boolean enforceInternalDivisionNonZeroChecks;
   public static boolean arithOptimizerDisableProgress;
   private static boolean gateSharingOptimization;

   static {
      numBitsFiniteFieldModulus = finiteFieldModulus.toString(2).length();
      hexOutputEnabled = true;
      inputVerbose = true;
      outputVerbose = true;
      debugVerbose = false;
      writeCircuits = false;
      outputFilesPath = "";
      multivariateExpressionMinimization = false;
      arithOptimizerNumThreads = 4;
      arithOptimizerTimeoutPerProblemMilliSec = 15000;
      arithOptimizerIncrementalMode = false;
      enforceInternalDivisionNonZeroChecks = true;
      arithOptimizerDisableProgress = true;
      gateSharingOptimization = false;
   }

   public static void setFiniteFieldModulus(BigInteger p) {
      finiteFieldModulus = p;
      numBitsFiniteFieldModulus = finiteFieldModulus.toString(2).length();
   }

   public static BigInteger getFiniteFieldModulus() {
      return finiteFieldModulus;
   }

   public static int getNumBitsFiniteFieldModulus() {
      return numBitsFiniteFieldModulus;
   }
}
