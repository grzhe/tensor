// code by jph
package ch.ethz.idsc.tensor.alg;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.ScalarQ;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.lie.Signature;
import ch.ethz.idsc.tensor.mat.Eigensystem;

/** an application of Ordering is to arrange the eigenvalues in {@link Eigensystem}
 * in descending order.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ordering.html">Ordering</a>
 * 
 * @see Sort
 * @see Signature */
public enum Ordering {
  INCREASING(tensor -> IntStream.range(0, tensor.length()).boxed() //
      .sorted((i, j) -> TensorComparator.INSTANCE.compare(tensor.get(i), tensor.get(j))) //
      .mapToInt(Integer::intValue)), //
  DECREASING(tensor -> IntStream.range(0, tensor.length()).boxed() //
      .sorted((i, j) -> TensorComparator.INSTANCE.compare(tensor.get(j), tensor.get(i))) //
      .mapToInt(Integer::intValue));

  private static interface OrderingInterface {
    /** @param tensor
     * @return stream of indices i[:] so that tensor[i[0]], tensor[i[1]], ... is ordered */
    IntStream stream(Tensor tensor);
  }

  /***************************************************/
  private final OrderingInterface orderingInterface;

  private Ordering(OrderingInterface orderingInterface) {
    this.orderingInterface = orderingInterface;
  }

  /** @param tensor
   * @return stream of indices i[:] so that tensor[i[0]], tensor[i[1]], ... is ordered
   * @throws Exception if given tensor cannot be sorted */
  public IntStream stream(Tensor tensor) {
    ScalarQ.thenThrow(tensor);
    return orderingInterface.stream(tensor);
  }

  /** @param tensor
   * @return array of indices i[:] so that vector[i[0]], vector[i[1]], ... is ordered
   * @throws Exception if given tensor cannot be sorted */
  public int[] of(Tensor tensor) {
    return stream(tensor).toArray();
  }
}
