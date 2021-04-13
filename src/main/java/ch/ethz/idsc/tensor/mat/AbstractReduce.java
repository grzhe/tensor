// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.ext.Integers;

/** base class of {@link Determinant}, {@link GaussianElimination} and {@link RowReduce} */
/* package */ class AbstractReduce {
  final Tensor[] lhs;
  private final Pivot pivot;
  final int[] ind;
  private int swaps = 0;

  public AbstractReduce(Tensor matrix, Pivot pivot) {
    lhs = matrix.stream().map(Tensor::copy).toArray(Tensor[]::new);
    this.pivot = pivot;
    ind = IntStream.range(0, lhs.length).toArray();
  }

  protected final void pivot(int row, int col) {
    int k = pivot.get(row, col, ind, lhs);
    if (k != row) {
      int swap = ind[k];
      ind[k] = ind[row];
      ind[row] = swap;
      ++swaps;
    }
  }

  /** @return determinant of matrix */
  public final Scalar det() {
    Scalar scalar = IntStream.range(0, lhs.length) //
        .mapToObj(c0 -> lhs[ind[c0]].Get(c0)) //
        .reduce(Scalar::multiply) //
        .get();
    return Integers.isEven(swaps) //
        ? scalar
        : scalar.negate();
  }
}
