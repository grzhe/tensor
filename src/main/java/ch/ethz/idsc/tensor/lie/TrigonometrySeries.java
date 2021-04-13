// code by jph
package ch.ethz.idsc.tensor.lie;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ext.Integers;
import ch.ethz.idsc.tensor.mat.Tolerance;
import ch.ethz.idsc.tensor.sca.Chop;

/* package */ class TrigonometrySeries {
  public static final TrigonometrySeries DEFAULT = new TrigonometrySeries(Tolerance.CHOP);
  private static final int MAX_ITERATIONS = 512 * 2;
  // ---
  private final Chop chop;

  public TrigonometrySeries(Chop chop) {
    this.chop = chop;
  }

  /** @param x
   * @return sine of x */
  public Scalar sin(Scalar x) {
    Scalar xn0 = x.zero();
    Scalar xn1 = x;
    Scalar add = x;
    final Scalar x2 = x.multiply(x);
    int count = 0;
    for (int index = 1; index < MAX_ITERATIONS;) {
      xn0 = xn1;
      add = add.multiply(x2).divide(RealScalar.of(++index * ++index));
      xn1 = Integers.isEven(++count) //
          ? xn1.add(add)
          : xn1.subtract(add);
      if (chop.isClose(xn0, xn1))
        return xn1;
    }
    throw TensorRuntimeException.of(x);
  }

  /** @param x
   * @return hyperbolic sine of x */
  public Scalar sinh(Scalar x) {
    Scalar xn0 = x.zero();
    Scalar xn1 = x;
    Scalar add = x;
    final Scalar x2 = x.multiply(x);
    for (int index = 1; index < MAX_ITERATIONS;) {
      xn0 = xn1;
      add = add.multiply(x2).divide(RealScalar.of(++index * ++index));
      xn1 = xn1.add(add);
      if (chop.isClose(xn0, xn1))
        return xn1;
    }
    throw TensorRuntimeException.of(x);
  }

  /** @param x
   * @return cosine of x */
  public Scalar cos(Scalar x) {
    Scalar xn0 = x.zero();
    Scalar xn1 = RealScalar.ONE;
    Scalar add = RealScalar.ONE;
    final Scalar x2 = x.multiply(x);
    int count = 0;
    for (int index = 0; index < MAX_ITERATIONS;) {
      xn0 = xn1;
      add = add.multiply(x2).divide(RealScalar.of(++index * ++index));
      xn1 = Integers.isEven(++count) //
          ? xn1.add(add)
          : xn1.subtract(add);
      if (chop.isClose(xn0, xn1))
        return xn1;
    }
    throw TensorRuntimeException.of(x);
  }

  /** @param x
   * @return hyperbolic cosine of x */
  public Scalar cosh(Scalar x) {
    Scalar xn0 = x.zero();
    Scalar xn1 = RealScalar.ONE;
    Scalar add = RealScalar.ONE;
    final Scalar x2 = x.multiply(x);
    for (int index = 0; index < MAX_ITERATIONS;) {
      xn0 = xn1;
      add = add.multiply(x2).divide(RealScalar.of(++index * ++index));
      xn1 = xn1.add(add);
      if (chop.isClose(xn0, xn1))
        return xn1;
    }
    throw TensorRuntimeException.of(x);
  }
}
