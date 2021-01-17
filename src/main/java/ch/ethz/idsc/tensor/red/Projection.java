// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.api.TensorUnaryOperator;
import ch.ethz.idsc.tensor.sca.Conjugate;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Projection.html">Projection</a> */
public class Projection implements TensorUnaryOperator {
  private static final long serialVersionUID = 750482198290554076L;

  /** @param vector
   * @return projection operator to given vector
   * @throws Exception if given vector is not a tensor of rank 1 */
  public static TensorUnaryOperator on(Tensor vector) {
    return new Projection(vector);
  }

  /** Hint: function is not commutative!
   * 
   * @param u vector
   * @param vector
   * @return Mathematica::Projection[u, vector]
   * @throws Exception if either parameter is not a tensor of rank 1 */
  public static Tensor of(Tensor u, Tensor vector) {
    return on(vector).apply(u);
  }

  /***************************************************/
  private final Tensor vc;
  private final Tensor vs;

  private Projection(Tensor vector) {
    vc = Conjugate.of(vector);
    Scalar scalar = (Scalar) vc.dot(vector);
    if (Scalars.isZero(scalar))
      throw TensorRuntimeException.of(vector);
    vs = vector.divide(scalar);
  }

  @Override // from TensorUnaryOperator
  public Tensor apply(Tensor u) {
    return vs.multiply((Scalar) vc.dot(u));
  }
}
