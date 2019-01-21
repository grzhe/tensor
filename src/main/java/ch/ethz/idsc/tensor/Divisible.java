// code by jph
package ch.ethz.idsc.tensor;

import ch.ethz.idsc.tensor.sca.Mod;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Divisible.html">Divisible</a> */
public enum Divisible {
  ;
  /** @param n numerator in exact precision
   * @param m denominator in exact precision
   * @return Mod[m, n] == 0
   * @throws Exception if given n or m are not in exact precision */
  public static boolean of(Scalar n, Scalar m) {
    return Scalars.isZero(ExactScalarQ.require(Mod.function(m).apply(n)));
  }
}
