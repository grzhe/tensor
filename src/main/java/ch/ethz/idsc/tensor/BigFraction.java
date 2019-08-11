// code by jph
package ch.ethz.idsc.tensor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;

/** immutable integer fraction in normal form, i.e. denominator is strictly positive */
/* package */ final class BigFraction implements Serializable {
  /** @param num numerator
   * @param den denominator non-zero
   * @return
   * @throws {@link ArithmeticException} if den is zero */
  public static BigFraction of(long num, long den) {
    return of(BigInteger.valueOf(num), BigInteger.valueOf(den));
  }

  /** @param num numerator
   * @param den denominator non-zero
   * @return
   * @throws {@link ArithmeticException} if den is zero */
  public static BigFraction of(BigInteger num, BigInteger den) {
    // LONGTERM "if" is obsolete in some function invocations
    if (den.signum() == 0)
      throw new ArithmeticException(num + "/" + den);
    BigInteger gcd = num.gcd(den);
    BigInteger res = den.divide(gcd);
    return res.signum() == 1 //
        ? new BigFraction(num.divide(gcd), res) //
        : new BigFraction(num.divide(gcd).negate(), res.negate());
  }

  /** @param num
   * @return */
  public static BigFraction integer(long num) {
    return new BigFraction(BigInteger.valueOf(num), BigInteger.ONE);
  }

  /** @param num
   * @return */
  public static BigFraction integer(BigInteger num) {
    return new BigFraction(num, BigInteger.ONE);
  }

  // ---
  /** numerator */
  private final BigInteger num;
  /** denominator (always greater than zero) */
  private final BigInteger den;

  private BigFraction(BigInteger num, BigInteger den) {
    this.num = num;
    this.den = den;
  }

  public BigFraction negate() {
    return new BigFraction(num.negate(), den);
  }

  /** uses gcd of the denominators (better only in special cases than the straight forward formula)
   * 
   * @param bigFraction
   * @return */
  public BigFraction add(BigFraction bigFraction) {
    return of( //
        num.multiply(bigFraction.den).add(bigFraction.num.multiply(den)), //
        den.multiply(bigFraction.den));
  }

  /** multiplication, applies gcd 1x
   * 
   * @param bigFraction
   * @return */
  public BigFraction multiply(BigFraction bigFraction) {
    return of(num.multiply(bigFraction.num), den.multiply(bigFraction.den));
  }

  /** division, applies gcd 1x
   * 
   * @param bigFraction
   * @return */
  public BigFraction divide(BigFraction bigFraction) {
    return of(num.multiply(bigFraction.den), den.multiply(bigFraction.num));
  }

  /** @return reciprocal == den/num */
  public BigFraction reciprocal() {
    int signum = signum();
    if (signum == 0)
      throw new ArithmeticException(den + "/" + num);
    return signum == 1 //
        ? new BigFraction(den, num) //
        : new BigFraction(den.negate(), num.negate()); //
  }

  public String toCompactString() {
    StringBuilder stringBuilder = new StringBuilder(num.toString());
    if (!isInteger())
      stringBuilder.append("/" + den.toString());
    return stringBuilder.toString();
  }

  /** @return true if the fraction encodes an integer, i.e. if the denominator equals 1 */
  public boolean isInteger() {
    return den.equals(BigInteger.ONE);
  }

  public int signum() {
    return num.signum();
  }

  public BigInteger numerator() {
    return num;
  }

  public BigInteger denominator() {
    return den;
  }

  /** @param bigFraction non-null
   * @return */
  boolean _equals(BigFraction bigFraction) {
    return num.equals(bigFraction.num) //
        && den.equals(bigFraction.den); // sufficient since in normal form
  }

  /***************************************************/
  @Override // from Object
  public int hashCode() {
    return Objects.hash(num, den);
  }
  // intentional: no override of Object::equals
}
