// code by jph
// https://stats.stackexchange.com/questions/66088/analysis-with-complex-data-anything-different
package ch.ethz.idsc.tensor.mat;

import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Unprotect;
import ch.ethz.idsc.tensor.alg.VectorQ;

/** least squares solution x that approximates
 * <pre>
 * matrix . x ~ b
 * </pre>
 * 
 * The general solution is given by
 * <pre>
 * x == PseudoInverse[m] . b
 * </pre>
 * 
 * However, the computation of the pseudo-inverse can often be avoided.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/LeastSquares.html">LeastSquares</a>
 * 
 * @see CholeskyDecomposition
 * @see QRDecomposition
 * @see SingularValueDecomposition
 * @see PseudoInverse */
public enum LeastSquares {
  ;
  /** If given matrix and b are in exact precision and the matrix has rank m
   * the CholeskyDecomposition produces x in exact precision.
   * 
   * @param matrix of size n x m
   * @param b
   * @return x with matrix.dot(x) ~ b */
  public static Tensor of(Tensor matrix, Tensor b) {
    int n = matrix.length();
    int m = Unprotect.dimension1(matrix);
    boolean assumeRankM = true;
    if (ExactTensorQ.of(matrix) && //
        ExactTensorQ.of(b))
      try {
        return usingCholesky(matrix, b, n, m);
      } catch (Exception exception) {
        assumeRankM = false; // rank is not maximal
      }
    if (m <= n && assumeRankM) {
      try {
        return usingQR(matrix, b);
      } catch (Exception exception) {
        assumeRankM = false; // rank is not maximal
      }
      return usingSvd(matrix, b);
    }
    return PseudoInverse.usingSvd(matrix).dot(b);
  }

  /***************************************************/
  /** Remark: The CholeskyDecomposition is used instead of LinearSolve
   * 
   * @param matrix with maximum rank
   * @param b
   * @return x with matrix.dot(x) ~ b
   * @throws Exception if matrix does not have maximum rank */
  public static Tensor usingCholesky(Tensor matrix, Tensor b) {
    return usingCholesky(matrix, b, matrix.length(), Unprotect.dimension1(matrix));
  }

  private static Tensor usingCholesky(Tensor matrix, Tensor b, int n, int m) {
    Tensor mt = ConjugateTranspose.of(matrix);
    return m <= n //
        ? CholeskyDecomposition.of(mt.dot(matrix)).solve(mt.dot(b))
        : ConjugateTranspose.of(CholeskyDecomposition.of(matrix.dot(mt)).solve(matrix)).dot(b);
  }

  /***************************************************/
  /** @param matrix
   * @param b
   * @return x with matrix.dot(x) ~ b */
  public static Tensor usingQR(Tensor matrix, Tensor b) {
    return new QRDecompositionImpl(matrix, b, QRSignOperators.STABILITY).pseudoInverse();
  }

  /***************************************************/
  /** when m does not have full rank, and for numerical stability
   * the function usingSvd(...) is preferred over the function usingLinearSolve(...)
   * 
   * @param matrix with rows >= cols
   * @param b
   * @return x with matrix.dot(x) ~ b */
  public static Tensor usingSvd(Tensor matrix, Tensor b) {
    return of(SingularValueDecomposition.of(matrix), b);
  }

  /** @param svd of matrix
   * @param b
   * @return pseudo inverse of given matrix dot b */
  public static Tensor of(SingularValueDecomposition svd, Tensor b) {
    if (VectorQ.of(b)) { // when b is vector then bypass construction of pseudo inverse matrix
      Tensor wi = SingularValueList.inverted(svd, Tolerance.CHOP);
      return svd.getV().dot(wi.pmul(b.dot(svd.getU()))); // U^t . b == b . U
    }
    return PseudoInverse.of(svd).dot(b);
  }
}
