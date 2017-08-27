// code by jph
package ch.ethz.idsc.tensor.red;

import java.io.Serializable;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

/** interface defines a norm for vectors */
public interface VectorNormInterface extends Serializable {
  Scalar vector(Tensor vector);
}
