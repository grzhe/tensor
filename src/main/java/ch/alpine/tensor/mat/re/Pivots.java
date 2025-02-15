// code by jph
package ch.alpine.tensor.mat.re;

import java.util.stream.IntStream;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.chq.ExactTensorQ;
import ch.alpine.tensor.sca.Abs;

public enum Pivots implements Pivot {
  /** selects entry with largest absolute value
   * 
   * in order to compute the inverse of matrices with mixed unit, for instance:
   * {{1[m^2], 6[m*rad]}, {6[m*rad], 16[rad^2]}}
   * the pivot is computer over the absolute numeric value of the columns */
  ARGMAX_ABS {
    @Override // from Pivot
    public int index(int row, int col, int[] ind, Tensor[] lhs) {
      Scalar max = Abs.FUNCTION.apply(Unprotect.withoutUnit(lhs[ind[row]].Get(col)));
      int arg = row;
      for (int i = row + 1; i < ind.length; ++i) {
        Scalar cmp = Abs.FUNCTION.apply(Unprotect.withoutUnit(lhs[ind[i]].Get(col)));
        if (Scalars.lessThan(max, cmp)) {
          max = cmp;
          arg = i;
        }
      }
      return arg;
    }
  },
  /** picks the first non-zero element in the column as pivot
   * the return value is c0 in the case when the element at (ind[c0], j)
   * is non-zero, but also if none of the candidates is non-zero
   * 
   * @see Det
   * @see MatrixRank */
  FIRST_NON_ZERO {
    @Override // from Pivot
    public int index(int row, int col, int[] ind, Tensor[] lhs) {
      return IntStream.range(row, ind.length) //
          .filter(c1 -> Scalars.nonZero(lhs[ind[c1]].Get(col))) //
          .findFirst().orElse(row);
    }
  };

  /** @param matrix
   * @return */
  public static Pivot selection(Tensor matrix) {
    return ExactTensorQ.of(matrix) //
        ? FIRST_NON_ZERO
        : ARGMAX_ABS;
  }
}
