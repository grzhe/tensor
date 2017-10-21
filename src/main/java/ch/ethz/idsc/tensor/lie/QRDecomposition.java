// code by jph
package ch.ethz.idsc.tensor.lie;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/QRDecomposition.html">QRDecomposition</a> */
public interface QRDecomposition extends Serializable {
  /** @param matrix
   * @return qr-decomposition of matrix
   * @throws Exception if input is not a matrix */
  static QRDecomposition of(Tensor matrix) {
    return new QRDecompositionImpl(matrix, QRSignOperator.DEFAULT);
  }

  /** @param matrix
   * @return qr-decomposition of matrix
   * @throws Exception if input is not a matrix */
  static QRDecomposition positive(Tensor matrix) {
    return new QRDecompositionImpl(matrix, QRSignOperator.POSITIVE);
  }

  /** @return orthogonal matrix */
  Tensor getInverseQ();

  /** @return upper triangular matrix */
  Tensor getR();

  /** @return orthogonal matrix */
  Tensor getQ();

  /** @return determinant of matrix */
  Scalar det();
}
