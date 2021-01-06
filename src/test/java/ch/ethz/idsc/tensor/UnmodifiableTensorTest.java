// code by jph
package ch.ethz.idsc.tensor;

import java.util.Iterator;

import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.usr.AssertFail;
import junit.framework.TestCase;

public class UnmodifiableTensorTest extends TestCase {
  public void testUnmodificableEmptyEquals() {
    assertTrue(Tensors.unmodifiableEmpty() == Tensors.unmodifiableEmpty());
    assertTrue(Tensors.unmodifiableEmpty() != Tensors.empty());
    assertTrue(Tensors.unmodifiableEmpty() != Tensors.empty().unmodifiable());
    assertTrue(Tensors.unmodifiableEmpty() == Tensors.unmodifiableEmpty().unmodifiable());
  }

  public void testUnmodifiable() {
    Tensor tensor = Tensors.vector(3, 4, 5, 6, -2);
    tensor.set(DoubleScalar.of(0.3), 2);
    Tensor unmodi = tensor.unmodifiable();
    assertEquals(tensor, unmodi);
    AssertFail.of(() -> unmodi.set(DoubleScalar.of(0.3), 2));
    AssertFail.of(() -> unmodi.append(Tensors.empty()));
    AssertFail.of(() -> unmodi.set(t -> t.append(RealScalar.ZERO)));
    Tensor dot = unmodi.dot(unmodi);
    assertFalse(Tensors.isUnmodifiable(dot));
    assertEquals(dot, DoubleScalar.of(65.09));
    assertEquals(DoubleScalar.of(65.09), dot);
  }

  public void testUnmodifiable2() {
    Tensor matrix = Tensors.matrixInt(new int[][] { { 1, 2 }, { 3, 4 } }).unmodifiable();
    Tensor copy = matrix.copy();
    matrix.get(1).set(RealScalar.ZERO, 1);
    assertEquals(matrix, copy);
    assertTrue(matrix == matrix.unmodifiable());
  }

  public void testHashUnmod() {
    Tensor a = Tensors.of(Tensors.vectorLong(2, -81, 7, 2, 8), Tensors.vector(32, 3.123));
    Tensor b = a.unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertTrue(b == b.unmodifiable());
  }

  public void testHashUnmodVector() {
    Tensor a = Tensors.vector(2, -81, 7, 2, 8, 3.123);
    Tensor b = a.unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertTrue(b == b.unmodifiable());
  }

  public void testHashUnmodEmpty() {
    Tensor a = Tensors.vector();
    Tensor b = Tensors.empty().unmodifiable();
    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
    assertTrue(b == b.unmodifiable());
  }

  public void testUnmodifiableSet() {
    Tensor eye = IdentityMatrix.of(3).unmodifiable();
    AssertFail.of(() -> eye.set(RealScalar.ZERO, 2, 2));
  }

  public void testUnmodifiableIterator() {
    Tensor eye = IdentityMatrix.of(3).unmodifiable();
    Iterator<Tensor> iterator = eye.iterator();
    Tensor next = iterator.next();
    assertEquals(next, UnitVector.of(3, 0));
    AssertFail.of(() -> next.set(RealScalar.ONE, 1));
    AssertFail.of(() -> iterator.remove());
  }

  public void testIteratorRemove() {
    Tensor tensor = IdentityMatrix.of(4).unmodifiable();
    for (Iterator<Tensor> iterator = tensor.iterator(); iterator.hasNext();) {
      iterator.next();
      AssertFail.of(() -> iterator.remove());
    }
    assertEquals(tensor, IdentityMatrix.of(4));
  }
}
