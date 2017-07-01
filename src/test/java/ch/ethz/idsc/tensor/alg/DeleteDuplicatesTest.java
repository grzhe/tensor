// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import junit.framework.TestCase;

public class DeleteDuplicatesTest extends TestCase {
  public void testSimple() {
    Tensor unique = DeleteDuplicates.of(Tensors.vector(7, 3, 1, 2, 3, 2, 3, 1));
    assertEquals(unique, Tensors.vector(7, 3, 1, 2));
  }
}
