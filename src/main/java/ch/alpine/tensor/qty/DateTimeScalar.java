// code by jph
package ch.alpine.tensor.qty;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import ch.alpine.tensor.AbstractScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.TensorRuntimeException;
import ch.alpine.tensor.api.ExactScalarQInterface;

/** EXPERIMENTAL
 * 
 * Addition of two instances of {@link DateTimeScalar} results in an exception.
 * Subtraction of two instances of {@link DateTimeScalar} results in a {@link DurationScalar}.
 * Negation of a {@link DateTimeScalar} results in an exception.
 * 
 * @implSpec
 * This class is immutable and thread-safe. */
public class DateTimeScalar extends AbstractScalar implements ExactScalarQInterface, //
    Comparable<Scalar>, Serializable {
  /** @param localDateTime
   * @return
   * @throws Exception if given localDateTime is null */
  public static DateTimeScalar of(LocalDateTime localDateTime) {
    return new DateTimeScalar(Objects.requireNonNull(localDateTime));
  }

  // ---
  private final LocalDateTime localDateTime;

  /* package */ DateTimeScalar(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

  @Override // from AbstractScalar
  public Scalar subtract(Tensor tensor) {
    if (tensor instanceof DateTimeScalar) {
      DateTimeScalar dateTimeScalar = (DateTimeScalar) tensor;
      return new DurationScalar(Duration.between(dateTimeScalar.localDateTime, localDateTime));
    }
    if (tensor instanceof DurationScalar) {
      DurationScalar durationScalar = (DurationScalar) tensor;
      // return durationScalar.negate().add(this); // <- alternative
      return new DateTimeScalar(localDateTime.minus(durationScalar.duration()));
    }
    throw TensorRuntimeException.of(this, tensor);
  }

  @Override // from AbstractScalar
  public Scalar multiply(Scalar scalar) {
    if (scalar.equals(one()))
      return this;
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Scalar
  public Scalar negate() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar reciprocal() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Number number() {
    throw TensorRuntimeException.of(this);
  }

  @Override // from Scalar
  public Scalar zero() {
    return DurationScalar.ZERO;
  }

  @Override // from Scalar
  public Scalar one() {
    return RealScalar.ONE;
  }

  @Override // from Scalar
  protected Scalar plus(Scalar scalar) {
    if (scalar instanceof DurationScalar) {
      DurationScalar durationScalar = (DurationScalar) scalar;
      return new DateTimeScalar(localDateTime.plus(durationScalar.duration()));
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from ExactScalarQInterface
  public boolean isExactScalar() {
    return true;
  }

  @Override // from Comparable
  public int compareTo(Scalar scalar) {
    if (scalar instanceof DateTimeScalar) {
      DateTimeScalar dateTimeScalar = (DateTimeScalar) scalar;
      return localDateTime.compareTo(dateTimeScalar.localDateTime);
    }
    throw TensorRuntimeException.of(this, scalar);
  }

  @Override // from Object
  public int hashCode() {
    return localDateTime.hashCode();
  }

  @Override // from Object
  public boolean equals(Object object) {
    if (object instanceof DateTimeScalar) {
      DateTimeScalar dateTimeScalar = (DateTimeScalar) object;
      return localDateTime.equals(dateTimeScalar.localDateTime);
    }
    return false;
  }

  @Override // from Object
  public String toString() {
    return localDateTime.toString();
  }
}
