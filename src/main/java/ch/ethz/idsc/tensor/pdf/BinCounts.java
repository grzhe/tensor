// code by joel and jph
package ch.ethz.idsc.tensor.pdf;

import java.util.NavigableMap;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.red.Tally;
import ch.ethz.idsc.tensor.sca.Floor;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/BinCounts.html">BinCounts</a> */
public enum BinCounts {
  ;
  /** counts elements in the intervals:
   * [0, 1) [1, 2) [2, 3) ...
   * 
   * Important: negative scalars in the input vector are ignored without a warning.
   * 
   * @param vector of non-negative scalars
   * @return */
  public static Tensor of(Tensor vector) {
    if (vector.length() == 0)
      return Tensors.empty();
    NavigableMap<Tensor, Long> navigableMap = Tally.sorted(Floor.of(vector));
    int length = Math.max(0, navigableMap.lastKey().Get().number().intValue() + 1);
    return Tensors.vector(index -> {
      Scalar key = RealScalar.of(index);
      return navigableMap.containsKey(key) ? RealScalar.of(navigableMap.get(key)) : RealScalar.ZERO;
    }, length);
  }

  /** counts elements in the intervals:
   * [0, width) [width 2*width) [2*width 3*width) ...
   * 
   * Example:
   * BinCounts.of(Tensors.vector(6, 7, 1, 2, 3, 4, 2), RealScalar.of(2)) == {1, 3, 1, 2}
   * 
   * Important: negative scalars in the input vector are ignored without a warning.
   * 
   * @param vector of non-negative scalars
   * @param width of a single bin, strictly positive number
   * @return */
  // EXPERIMENTAL API not finalized
  public static Tensor of(Tensor vector, Scalar width) {
    if (Scalars.lessEquals(width, RealScalar.ZERO))
      throw TensorRuntimeException.of(width);
    if (vector.length() == 0)
      return Tensors.empty();
    NavigableMap<Tensor, Long> navigableMap = Tally.sorted(Floor.of(vector.multiply(width.invert())));
    int length = Math.max(0, navigableMap.lastKey().Get().number().intValue() + 1);
    return Tensors.vector(index -> {
      Scalar key = RealScalar.of(index);
      return navigableMap.containsKey(key) ? RealScalar.of(navigableMap.get(key)) : RealScalar.ZERO;
    }, length);
  }
}
