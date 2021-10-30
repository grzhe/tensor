// code by jph
package ch.alpine.tensor.qty;

import java.time.LocalDateTime;

import ch.alpine.tensor.ExactTensorQ;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.sca.Sign;
import junit.framework.TestCase;

public class TemporalScalarsTest extends TestCase {
  public void testSimple() {
    Tensor a = Tensors.of( //
        DateTimeScalar.of(LocalDateTime.of(1657, 11, 10, 4, 8)), //
        DateTimeScalar.of(LocalDateTime.of(1857, 10, 5, 7, 18)));
    Tensor b = Tensors.of( //
        DateTimeScalar.of(LocalDateTime.of(2021, 7, 3, 14, 48)), //
        DateTimeScalar.of(LocalDateTime.of(1976, 4, 1, 17, 28)));
    Tensor diff = a.subtract(b);
    Tensor recv = Tensors.fromString(diff.toString(), TemporalScalars::fromString);
    assertEquals(diff, recv);
    assertEquals(b.add(recv), a);
    assertEquals(recv.add(b), a);
    assertEquals(a.subtract(recv), b);
    assertEquals(recv.negate().add(a), b);
    ExactTensorQ.require(diff);
    assertEquals(Sign.of(b.subtract(a)), Tensors.vector(+1, +1));
    assertEquals(Sign.of(a.subtract(b)), Tensors.vector(-1, -1));
  }

  public void testParsing() {
    Tensor a = Tensors.of( //
        DateTimeScalar.of(LocalDateTime.of(1657, 11, 10, 4, 8)), //
        DateTimeScalar.of(LocalDateTime.of(1857, 10, 5, 7, 18)));
    Tensor recv = Tensors.fromString(a.toString(), TemporalScalars::fromString);
    assertEquals(a, recv);
    ExactTensorQ.require(a);
  }
}
