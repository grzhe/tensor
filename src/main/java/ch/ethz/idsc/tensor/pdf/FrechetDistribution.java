// code by jph
package ch.ethz.idsc.tensor.pdf;

import java.util.Random;

import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.NumberQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Exp;
import ch.ethz.idsc.tensor.sca.Gamma;
import ch.ethz.idsc.tensor.sca.Log;
import ch.ethz.idsc.tensor.sca.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/FrechetDistribution.html">FrechetDistribution</a> */
public class FrechetDistribution implements Distribution, //
    CDF, MeanInterface, PDF, RandomVariateInterface, VarianceInterface {
  private static final double NEXTDOWNONE = Math.nextDown(1.0);
  private static final Scalar TWO = RealScalar.of(2);

  /** @param alpha positive
   * @param beta positive, may be instanceof {@link Quantity}
   * @return */
  public static Distribution of(Scalar alpha, Scalar beta) {
    if (Scalars.lessEquals(alpha, RealScalar.ZERO) || !NumberQ.of(alpha))
      throw TensorRuntimeException.of(alpha);
    if (Scalars.lessEquals(beta, RealScalar.ZERO))
      throw TensorRuntimeException.of(beta);
    return new FrechetDistribution(alpha, beta);
  }

  // ---
  private final Scalar alpha;
  private final Scalar beta;

  private FrechetDistribution(Scalar alpha, Scalar beta) {
    this.alpha = alpha;
    this.beta = beta;
  }

  @Override // from RandomVariateInterface
  public Scalar randomVariate(Random random) {
    return randomVariate(random.nextDouble());
  }

  /* package for testing */ Scalar randomVariate(double reference) {
    // avoid result -Infinity when reference is close to 1.0
    double uniform = reference == NEXTDOWNONE ? reference : Math.nextUp(reference);
    return beta.multiply(Power.of( //
        Log.FUNCTION.apply(DoubleScalar.of(uniform)).negate(), //
        alpha.reciprocal().negate()));
  }

  @Override // from MeanInterface
  public Scalar mean() {
    if (Scalars.lessEquals(alpha, RealScalar.ONE))
      return DoubleScalar.POSITIVE_INFINITY;
    return beta.multiply(Gamma.FUNCTION.apply(RealScalar.ONE.subtract(alpha.reciprocal())));
  }

  @Override // from VarianceInterface
  public Scalar variance() {
    if (Scalars.lessEquals(alpha, TWO))
      return DoubleScalar.POSITIVE_INFINITY;
    Scalar term = Gamma.FUNCTION.apply(RealScalar.ONE.subtract(TWO.divide(alpha)));
    return beta.multiply(beta).multiply(term).subtract(AbsSquared.FUNCTION.apply(mean()));
  }

  @Override // from PDF
  public Scalar at(Scalar x) {
    Scalar factor = Power.of(x.divide(beta), RealScalar.ONE.add(alpha).negate());
    return p_lessThan(x).multiply(alpha).multiply(factor).divide(beta);
  }

  @Override // from CDF
  public Scalar p_lessThan(Scalar x) {
    if (Scalars.lessEquals(x, RealScalar.ZERO))
      return RealScalar.ZERO;
    return Exp.of(Power.of(x.divide(beta), alpha.negate()).negate());
  }

  @Override // from CDF
  public Scalar p_lessEquals(Scalar x) {
    return p_lessThan(x);
  }
}
