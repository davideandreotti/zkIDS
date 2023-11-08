package backend.optimizer.arithmetic;

import backend.optimizer.arithmetic.poly.MultivariatePolynomial;
import backend.optimizer.arithmetic.poly.OptVariable;
import java.util.ArrayList;

public class PolynomialExample {
   public static void main(String[] args) {
   }

   private static void testcase5() {
      int dim = 10;
      int numOfVars = dim * dim;
      String[] vars = new String[numOfVars];

      for(int i = 0; i < numOfVars; ++i) {
         vars[i] = "x" + i;
      }

      ArrayList<MultivariatePolynomial> list = new ArrayList();
      OptVariable[] varList = new OptVariable[numOfVars];

      for(int i = 0; i < numOfVars; ++i) {
         varList[i] = new OptVariable("x", i);
      }

      MultivariatePolynomial[] mvp1 = new MultivariatePolynomial[numOfVars];
      MultivariatePolynomial[] mvp2 = new MultivariatePolynomial[numOfVars];

      int i;
      for(i = 0; i < numOfVars; ++i) {
         mvp1[i] = new MultivariatePolynomial(varList[i]);
         mvp2[i] = new MultivariatePolynomial(varList[i]);
      }

      for(i = 0; i < dim; ++i) {
         for(int j = 0; j < dim; ++j) {
            MultivariatePolynomial mvp = new MultivariatePolynomial();

            for(int k = 0; k < dim; ++k) {
               mvp = mvp.add(mvp1[i * dim + k].multiply(mvp2[k * dim + j]));
            }

            list.add(mvp);
            System.out.println(mvp);
         }
      }

   }

   private static void testcase4() {
      int dim = 5;
      int numOfVars = dim;
      String[] vars = new String[dim];

      for(int i = 0; i < numOfVars; ++i) {
         vars[i] = "x" + i;
      }

      ArrayList<MultivariatePolynomial> list = new ArrayList();
      OptVariable[] varList = new OptVariable[numOfVars];

      for(int i = 0; i < numOfVars; ++i) {
         varList[i] = new OptVariable("x", i);
      }

      MultivariatePolynomial[] mvp1 = new MultivariatePolynomial[numOfVars];
      MultivariatePolynomial[] mvp2 = new MultivariatePolynomial[numOfVars];

      for(int i = 0; i < numOfVars; ++i) {
         mvp1[i] = new MultivariatePolynomial(varList[i]);
         mvp2[i] = new MultivariatePolynomial(varList[i]);
      }

      MultivariatePolynomial[] out = new MultivariatePolynomial[2 * (dim - 1) + 1];

      int i;
      for(i = 0; i < 2 * (dim - 1) + 1; ++i) {
         out[i] = new MultivariatePolynomial();
      }

      for(i = 0; i < dim; ++i) {
         for(int j = 0; j < dim; ++j) {
            out[i + j] = out[i + j].addInPlace(mvp1[i].multiply(mvp2[j]));
         }
      }

      for(i = 0; i < 2 * (dim - 1) + 1; ++i) {
         list.add(out[i]);
         System.out.println(out[i]);
      }

   }
}
