package xjsnark.test_compiler;

/*Generated by MPS */

import backend.structure.CircuitGenerator;
import backend.eval.SampleRun;
import backend.eval.CircuitEvaluator;

public class empty_test extends CircuitGenerator {



  public static void main(String[] args) {
    // This is the java main method. Its purpose is to make the Progam runnable in the environment 
    // This method can be left empty, or used to set Configuration params (see examples) 
    new empty_test();
  }

  public empty_test() {
    super("empty_test");
    __generateCircuit();
    this.__evaluateSampleRun(new SampleRun("Sample_Run1", true) {
      public void pre() {
      }
      public void post() {
      }

    });

  }



  public void __init() {
  }


  @Override
  public void __defineInputs() {
    super.__defineInputs();















  }
  @Override
  public void __defineOutputs() {
    super.__defineOutputs();









  }
  @Override
  public void __defineVerifiedWitnesses() {
    super.__defineVerifiedWitnesses();



















  }
  @Override
  public void __defineWitnesses() {
    super.__defineWitnesses();

















  }
  public void outsource() {
    // Entry point for the circuit. Input and witness arrays/structs must be instantiated outside this method 
  }

  public void __generateSampleInput(CircuitEvaluator evaluator) {
    __generateRandomInput(evaluator);
  }

}
