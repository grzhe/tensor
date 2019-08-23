// code by jph
package ch.ethz.idsc.tensor;

import java.math.BigDecimal;
import java.math.BigInteger;

/** RealScalar encodes a real number
 * 
 * <p>encodings provided by the tensor library are
 * <ul>
 * <li>integer fraction {@link RationalScalar}
 * <li>decimal with double precision {@link DoubleScalar}
 * <li>decimal with extra precision {@link DecimalScalar}
 * </ul> */
public interface RealScalar extends Scalar {
  /** real scalar 0 as a {@link RationalScalar} */
  static final Scalar ZERO = RationalScalar.of(0, 1);
  /** real scalar 1 as a {@link RationalScalar} */
  static final Scalar ONE = RationalScalar.of(1, 1);

  /** depending on the derived class of the given {@link Number},
   * the value is encoded as {@link RationalScalar},
   * {@link DoubleScalar}, or {@link DecimalScalar}.
   * 
   * @param number non-null
   * @return scalar with best possible accuracy to encode given number
   * @throws Exception if number is null, or instance of an unsupported type */
  static Scalar of(Number number) {
    if (number instanceof Integer || //
        number instanceof Long || //
        number instanceof Short || //
        number instanceof Byte)
      return RationalScalar.integer(number.longValue());
    if (number instanceof Double || //
        number instanceof Float)
      return DoubleScalar.of(number.doubleValue());
    if (number instanceof BigInteger)
      return RationalScalar.integer((BigInteger) number);
    if (number instanceof BigDecimal)
      return DecimalScalar.of((BigDecimal) number);
    throw new IllegalArgumentException(number.getClass().getName());
  }
}
