// code by jph
package ch.alpine.tensor.alg;

import java.io.IOException;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.Serialization;
import junit.framework.TestCase;

public class AdjacentReduceTest extends TestCase {
  public static class Some extends AdjacentReduce {
    @Override
    protected Tensor reduce(Tensor prev, Tensor next) {
      return prev;
    }
  }

  public void testSimple() throws ClassNotFoundException, IOException {
    Tensor tensor = Range.of(0, 5);
    int length = tensor.length();
    Tensor result = Serialization.copy(new Some()).apply(tensor);
    assertEquals(result, Range.of(0, 4));
    assertEquals(result.length(), length - 1);
  }
}
