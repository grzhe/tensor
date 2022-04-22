// code by jph
package ch.alpine.tensor.sca;

import java.util.Objects;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.MultiplexScalar;
import ch.alpine.tensor.api.NInterface;

/* package */ class NDouble extends N {
  static final N INSTANCE = new NDouble();

  private NDouble() {
    // ---
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof NInterface nInterface)
      return nInterface.n();
    if (scalar instanceof MultiplexScalar legionScalar)
      return legionScalar.eachMap(this);
    return Objects.requireNonNull(scalar);
  }
}
