// code by jph
package ch.alpine.tensor.prc;

import java.io.Serializable;
import java.util.Random;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.io.MathematicaFormat;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/WienerProcess.html">WienerProcess</a> */
/* package */ class WienerProcess implements RandomProcess, Serializable {
  private final Scalar mu;
  private final Scalar sigma;

  /** @param mu drift
   * @param sigma volatility */
  public WienerProcess(Scalar mu, Scalar sigma) {
    this.mu = mu;
    this.sigma = sigma;
  }

  @Override
  public Scalar eval(TimeSeries timeSeries, Random random, Scalar x) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String toString() {
    return MathematicaFormat.concise("WienerProcess", mu, sigma);
  }
}
