// code by gjoel
package ch.ethz.idsc.tensor.crd;

import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class CompatibleSystemQTest extends TestCase {
  public void testWith() {
    Coordinates coords = Coordinates.of(Tensors.vector(1, 2, 3));
    assertTrue(CompatibleSystemQ.in(CoordinateSystem.DEFAULT).with(coords));
    assertTrue(CompatibleSystemQ.to(coords).with(CoordinateSystem.DEFAULT));
    assertFalse(CompatibleSystemQ.in(CoordinateSystem.DEFAULT).with(Tensors.empty()));
  }

  public void testRequire() {
    Coordinates coords = Coordinates.of(Tensors.vector(1, 2, 3));
    assertEquals(CompatibleSystemQ.in(CoordinateSystem.DEFAULT).require(coords), coords);
    {
      boolean thrown = false;
      try {
        CompatibleSystemQ.in(CoordinateSystem.DEFAULT).require(Tensors.empty());
      } catch (ClassCastException e) {
        thrown = true;
      }
      assertTrue(thrown);
    }
    {
      boolean thrown = false;
      try {
        CompatibleSystemQ.in(CoordinateSystem.DEFAULT).require(CoordinateSystem.of("test").origin());
      } catch (UnsupportedOperationException e) {
        thrown = true;
      }
      assertTrue(thrown);
    }
  }
}
