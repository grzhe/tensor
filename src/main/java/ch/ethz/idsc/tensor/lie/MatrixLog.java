// code by jph
package ch.ethz.idsc.tensor.lie;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.PositiveDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.Tolerance;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Sign;

/** Hint: implementation uses inverse of the scaling and squaring procedure that
 * involves repeated matrix square roots.
 * 
 * References:
 * "Matrix Computations" 4th Edition
 * by Gene H. Golub, Charles F. Van Loan, 2012
 * 
 * "Approximating the Logarithm of a Matrix to Specified Accuracy"
 * by Sheung Hun Cheng, Nicholas J. Higham, Charles S. Kenny, Alan J. Laub 2001
 *
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/MatrixLog.html">MatrixLog</a>
 * 
 * @see MatrixExp */
public enum MatrixLog {
  ;
  private static final int MAX_EXPONENT = 20;
  private static final Scalar RHO_MAX = RealScalar.of(0.99);
  private static final int MAX_ITERATIONS = 100;

  /** Hint: currently only matrices of dimensions 2 x 2 are supported
   * as well as symmetric positive definite matrices
   * 
   * @param matrix
   * @return
   * @throws Exception if computation is not supported for given matrix */
  public static Tensor of(Tensor matrix) {
    int dim1 = Unprotect.dimension1(matrix);
    if (matrix.length() == 2)
      if (dim1 == 2)
        return MatrixLog2.of(matrix);
    // ---
    int n = matrix.length();
    Tensor id = IdentityMatrix.of(n);
    int roots = 0;
    Tensor rem = matrix.subtract(id);
    Deque<DenmanBeaversDet> deque = new ArrayDeque<>();
    for (; roots < MAX_EXPONENT; ++roots) {
      Scalar rho_max = Norm2Bound.ofMatrix(rem);
      if (Scalars.lessThan(rho_max, RHO_MAX)) {
        Tensor sum = Array.zeros(n, n);
        Iterator<DenmanBeaversDet> iterator = deque.iterator();
        Scalar factor = RealScalar.ONE;
        while (iterator.hasNext()) {
          sum = sum.add(iterator.next().mk().subtract(id).multiply(factor));
          factor = factor.add(factor);
        }
        // System.out.println(factor);
        // System.out.println(Pretty.of(sum));
        return sum.add(series1(rem).multiply(factor));
      }
      DenmanBeaversDet denmanBeaversDet = new DenmanBeaversDet(matrix, Tolerance.CHOP);
      deque.add(denmanBeaversDet);
      matrix = denmanBeaversDet.sqrt();
      rem = matrix.subtract(id);
    }
    throw TensorRuntimeException.of(matrix);
  }

  /** Hint: use {@link Symmetrize} on result for extra precision
   * 
   * @param matrix symmetric with all positive eigenvalues
   * @return
   * @see PositiveDefiniteMatrixQ */
  public static Tensor ofSymmetric(Tensor matrix) {
    return StaticHelper.ofSymmetric(matrix, MatrixLog::logPositive);
  }

  // helper function
  private static Scalar logPositive(Scalar scalar) {
    return Log.FUNCTION.apply(Sign.requirePositive(scalar));
  }

  /** @param x square matrix with spectral radius below 1
   * @return log[ I + x ]
   * @throws Exception if given matrix is non-square */
  /* package */ static Tensor series1(Tensor x) {
    Tensor nxt = x;
    Tensor sum = nxt;
    for (int k = 2; k < MAX_ITERATIONS; ++k) {
      nxt = nxt.dot(x);
      Tensor prv = sum;
      sum = sum.add(nxt.divide(RealScalar.of(k % 2 == 0 ? -k : k)));
      if (Chop.NONE.isClose(sum, prv))
        return sum;
    }
    throw TensorRuntimeException.of(x); // insufficient convergence
  }
}
