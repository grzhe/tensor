// code by jph
package ch.alpine.tensor.tmp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

class TimeSeriesIntegrateTest {
  @Test
  void testIntegrateBlocks() {
    Tensor p1 = Tensors.fromString("{{1, 3}, {4, 3}, {5, 6}, {7, 5}, {10, 2}}");
    TimeSeries ts1 = TimeSeries.of(p1, ResamplingMethods.HOLD_LO);
    assertEquals(ts1.resamplingMethod(), ResamplingMethods.HOLD_LO);
    assertThrows(Exception.class, () -> TimeSeriesIntegrate.of(ts1, Clips.interval(0, 3)));
    assertThrows(Exception.class, () -> TimeSeriesIntegrate.of(ts1, Clips.interval(2, 11)));
    assertThrows(Exception.class, () -> TimeSeriesIntegrate.of(ts1, Clips.interval(0, 11)));
    Tensor value1 = TimeSeriesIntegrate.of(ts1, ts1.support());
    assertEquals(value1, RealScalar.of(3 * 3 + 3 + 2 * 6 + 3 * 5));
    Tensor value2 = TimeSeriesIntegrate.of(ts1, Clips.interval(2, 8));
    assertEquals(value2, RealScalar.of(2 * 3 + 3 + 2 * 6 + 1 * 5));
    TimeSeries integrate = TimeSeriesIntegrate.of(ts1);
    assertEquals(integrate.support(), ts1.support());
    assertEquals(ts1.keySet(ts1.support()), integrate.keySet(ts1.support()));
    assertEquals(integrate.eval(RealScalar.of(10)), RealScalar.of(39));
  }

  @Test
  void testIntegrateLinear() {
    Tensor p1 = Tensors.fromString("{{1, 3}, {4, 3}, {5, 6}, {7, 5}, {10, 2}}");
    TimeSeries ts1 = TimeSeries.of(p1, ResamplingMethods.LINEAR_INTERPOLATION);
    Tensor value1 = TimeSeriesIntegrate.of(ts1, ts1.support());
    Tolerance.CHOP.requireClose(value1, RealScalar.of(3 * 3 + 4.5 + 2 * 5.5 + 3 * 3.5));
    Tensor value2 = TimeSeriesIntegrate.of(ts1, Clips.interval(2, 8));
    Tensor eval = ts1.eval(RealScalar.of(7.5));
    assertEquals(eval, RealScalar.of(4.5));
    Tolerance.CHOP.requireClose(value2, RealScalar.of(2 * 3 + 4.5 + 2 * 5.5 + 1 * 4.5));
  }
}
