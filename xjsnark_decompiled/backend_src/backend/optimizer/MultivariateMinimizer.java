package backend.optimizer;

import backend.config.Config;
import backend.optimizer.arithmetic.ExpressionMinimizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MultivariateMinimizer {
   private ArrayList<CircuitOptimizer.Problem> combinedProblems;
   private Queue<Future<?>> tasks;
   private Queue<CircuitOptimizer.Problem> problems;
   private Queue<CircuitOptimizer.Problem> problemsToSolve;
   private LinkedList<Long> startTimes;
   private int numThreads;
   private int timeout;
   private ExecutorService executor;
   private int numPolls;
   private int numPuts;
   int total;

   public MultivariateMinimizer(ArrayList<CircuitOptimizer.Problem> combinedProblems) {
      this.numThreads = Config.arithOptimizerNumThreads;
      this.timeout = Config.arithOptimizerTimeoutPerProblemMilliSec;
      this.numPolls = 0;
      this.numPuts = 0;
      this.total = 0;
      this.combinedProblems = combinedProblems;
      this.tasks = new LinkedList();
      this.problems = new LinkedList();
      this.startTimes = new LinkedList();
      this.problemsToSolve = new LinkedList();
   }

   private CircuitOptimizer.Problem getNext() {
      return (CircuitOptimizer.Problem)this.problemsToSolve.poll();
   }

   public void run() {
      Iterator p = this.combinedProblems.iterator();

      CircuitOptimizer.Problem f;
      while(p.hasNext()) {
         f = (CircuitOptimizer.Problem)p.next();
         if (!f.isEmpty() && !f.isDontSolve() && f.getMvpList().size() < 100 && f.getVariables().size() > 1 && f.getVariables().size() < 100) {
            this.problemsToSolve.add(f);
         }
      }

      this.executor = Executors.newFixedThreadPool(this.numThreads);
      synchronized(this) {
         for(int i = 0; i < this.combinedProblems.size(); ++i) {
            CircuitOptimizer.Problem p = this.getNext();
            if (p == null) {
               break;
            }

            this.startTimes.add(System.currentTimeMillis());
            Future<?> f = this.executor.submit(() -> {
               HashMap solution;
               try {
                  ExpressionMinimizer minimizer = p.prep();
                  solution = minimizer.run(1);
                  this.taskCompleted();
               } catch (Exception var4) {
                  var4.printStackTrace();
                  solution = null;
               }

               return solution;
            });
            this.tasks.add(f);
            this.problems.add(p);
            ++this.numPuts;
            if (this.tasks.size() == this.numThreads) {
               break;
            }
         }
      }

      while(!this.tasks.isEmpty()) {
         ++this.numPolls;
         f = null;
         p = null;
         Long t1 = 0L;
         Future f;
         CircuitOptimizer.Problem p;
         synchronized(this) {
            f = (Future)this.tasks.poll();
            p = (CircuitOptimizer.Problem)this.problems.poll();
            t1 = (Long)this.startTimes.poll();
         }

         Long t2 = System.currentTimeMillis();

         try {
            Object s = f.get((long)this.timeout - (t2 - t1) > 0L ? (long)this.timeout - (t2 - t1) : 1L, TimeUnit.MILLISECONDS);
            if (s != null && f.isDone()) {
               p.setSolutions((HashMap)s);
            }

            if (this.tasks.size() == 0) {
               this.taskCompleted();
            }
         } catch (ExecutionException | TimeoutException | InterruptedException var7) {
            f.cancel(true);
            if (this.tasks.size() == 0) {
               this.taskCompleted();
            }
         }
      }

      this.executor.shutdown();
   }

   public void taskCompleted() {
      synchronized(this) {
         CircuitOptimizer.Problem p = this.getNext();
         if (p != null) {
            this.startTimes.add(System.currentTimeMillis());
            Future<?> f = this.executor.submit(() -> {
               ExpressionMinimizer minimizer = p.prep();
               Object solution = minimizer.run(1);
               this.taskCompleted();
               return solution;
            });
            this.tasks.add(f);
            this.problems.add(p);
         }
      }
   }
}
