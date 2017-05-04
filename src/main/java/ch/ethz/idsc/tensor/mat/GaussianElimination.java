// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.ZeroScalar;

/** the most important algorithm of all time */
/* package */ class GaussianElimination {
  final Tensor lhs;
  final int[] ind;
  final Tensor rhs;
  int transpositions = 0;

  /** @param m square matrix
   * @param b tensor with first dimension identical to size of matrix
   * @param pivot
   * @throws TensorRuntimeException if matrix m is singular */
  GaussianElimination(Tensor m, Tensor b, Pivot pivot) {
    lhs = m.copy();
    int n = lhs.length();
    ind = new int[n];
    IntStream.range(0, n).forEach(index -> ind[index] = index);
    rhs = b.copy();
    for (int c0 = 0; c0 < n; ++c0) {
      int k = pivot.get(c0, c0, ind, lhs);
      if (ind[k] != ind[c0]) {
        ++transpositions;
        int swap = ind[k];
        ind[k] = ind[c0];
        ind[c0] = swap;
      }
      Scalar piv = lhs.Get(ind[c0], c0);
      if (piv.equals(ZeroScalar.get()))
        // TODO there might be still hope depending on rhs...?
        throw TensorRuntimeException.of(m);
      Scalar den = piv.invert();
      for (int c1 = c0 + 1; c1 < n; ++c1) {
        Scalar fac = (Scalar) lhs.get(ind[c1], c0).multiply(den).negate();
        lhs.set(lhs.get(ind[c1]).add(lhs.get(ind[c0]).multiply(fac)), ind[c1]);
        rhs.set(rhs.get(ind[c1]).add(rhs.get(ind[c0]).multiply(fac)), ind[c1]);
      }
    }
  }

  GaussianElimination(Tensor m, Pivot pivot) {
    Tensor tmp = m.copy();
    int n = tmp.length();
    final int cols = tmp.get(0).length();
    ind = new int[n];
    rhs = null;
    IntStream.range(0, n).forEach(index -> ind[index] = index);
    int j = 0;
    for (int c0 = 0; c0 < n && j < cols; ++j) {
      // System.out.println("c0=" + c0 + " j=" + j);
      int k = pivot.get(c0, j, ind, tmp);
      if (ind[k] != ind[c0]) {
        ++transpositions;
        int swap = ind[k];
        ind[k] = ind[c0];
        ind[c0] = swap;
      }
      Scalar piv = tmp.Get(ind[c0], j);
      if (!piv.equals(ZeroScalar.get())) {
        Scalar den = piv.invert();
        for (int c1 = 0; c1 < n; ++c1)
          if (c1 != c0) {
            Scalar fac = (Scalar) tmp.get(ind[c1], j).multiply(den).negate();
            tmp.set(tmp.get(ind[c1]).add(tmp.get(ind[c0]).multiply(fac)), ind[c1]);
          }
        tmp.set(tmp.get(ind[c0]).multiply(den), ind[c0]);
        ++c0;
      }
    }
    lhs = Tensor.of(IntStream.range(0, n).boxed().map(i -> tmp.get(ind[i])));
  }

  /** @return determinant */
  Scalar det() {
    Scalar scalar = IntStream.range(0, lhs.length()).boxed() //
        .map(c0 -> lhs.Get(ind[c0], c0)) //
        .reduce(Scalar::multiply) //
        .orElse(ZeroScalar.get());
    return transpositions % 2 == 0 ? scalar : scalar.negate();
  }

  /** @return x with m.dot(x) == b */
  Tensor solution() {
    Tensor sol = rhs.map(scalar -> ZeroScalar.get()); // all-zeros copy of rhs
    for (int c0 = ind.length - 1; 0 <= c0; --c0) {
      Scalar factor = lhs.Get(ind[c0], c0).invert();
      sol.set(rhs.get(ind[c0]).subtract(lhs.get(ind[c0]).dot(sol)).multiply(factor), c0);
    }
    return sol;
  }
}
