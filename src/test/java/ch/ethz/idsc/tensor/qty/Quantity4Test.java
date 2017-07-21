// code by jph
package ch.ethz.idsc.tensor.qty;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Multinomial;
import ch.ethz.idsc.tensor.mat.CholeskyDecomposition;
import ch.ethz.idsc.tensor.mat.Det;
import ch.ethz.idsc.tensor.mat.IdentityMatrix;
import ch.ethz.idsc.tensor.mat.Inverse;
import ch.ethz.idsc.tensor.mat.LinearSolve;
import ch.ethz.idsc.tensor.mat.NegativeDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.NegativeSemidefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.NullSpace;
import ch.ethz.idsc.tensor.mat.PositiveDefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.PositiveSemidefiniteMatrixQ;
import ch.ethz.idsc.tensor.mat.RowReduce;
import junit.framework.TestCase;

public class Quantity4Test extends TestCase {
  public void testMultinomial() {
    Scalar qs1;
    {
      qs1 = Quantity.of(RealScalar.of(-4), "[m*s]");
    }
    Scalar qs2 = Quantity.of(RealScalar.of(3), "[m]");
    Scalar val = Quantity.of(RealScalar.of(2), "[s]");
    Scalar res = Multinomial.horner(Tensors.of(qs1, qs2), val);
    // System.out.println(res);
    assertEquals(res.toString(), "2[m*s]");
  }

  public void testLinearSolve() {
    final Scalar one = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(2), "[m]");
    Scalar qs4 = Quantity.of(RealScalar.of(-3), "[m]");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor ve2 = Tensors.of(qs3, qs4);
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2, one);
    Tensor inv = LinearSolve.of(mat, eye);
    Tensor res = mat.dot(inv);
    assertEquals(res, eye);
  }

  public void testInverse2() {
    // final Scalar one = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Scalar qs3 = Quantity.of(RealScalar.of(3), "[rad]");
    Scalar qs4 = Quantity.of(RealScalar.of(4), "[rad]");
    Tensor ve1 = Tensors.of(qs1.multiply(qs1), qs2.multiply(qs3));
    Tensor ve2 = Tensors.of(qs2.multiply(qs3), qs4.multiply(qs4));
    Tensor mat = Tensors.of(ve1, ve2);
    Tensor eye = IdentityMatrix.of(2); // <- yey!
    // System.out.println(Pretty.of(mat));
    {
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      // System.out.println(Pretty.of(res));
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Inverse.of(mat);
    }
  }

  public void testInverse3() {
    // final Scalar one = QuantityScalar.of(RealScalar.of(1), "m", RealScalar.ONE);
    // UnitMap
    Tensor mat = Tensors.fromString( //
        "{{1[m^2], 2[m*rad], 3[kg*m]}, {4[m*rad], 2[rad^2], 2[kg*rad]}, {5[kg*m], 1[kg*rad], 7[kg^2]}}", //
        Quantity::fromString);
    Tensor eye = IdentityMatrix.of(3);
    {
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Inverse.of(mat);
    }
  }

  public void testCholesky3() {
    Tensor mat = Tensors.fromString( //
        "{{60[m^2], 30[m*rad], 20[kg*m]}, {30[m*rad], 20[rad^2], 15[kg*rad]}, {20[kg*m], 15[kg*rad], 12[kg^2]}}", //
        Quantity::fromString);
    Tensor eye = IdentityMatrix.of(3);
    {
      Tensor inv = LinearSolve.of(mat, eye);
      Tensor res = mat.dot(inv);
      assertEquals(eye, res);
      assertEquals(res, eye);
    }
    {
      Inverse.of(mat);
    }
    {
      Scalar d1 = Det.of(mat); // 100[kg^2,m^2,rad^2]
      CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
      Scalar d2 = cd.det();
      assertEquals(d1, d2);
    }
  }

  public void testLinearSolve1() {
    Scalar qs1 = Quantity.of(RealScalar.of(3), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(4), "[s]");
    Tensor ve1 = Tensors.of(qs1);
    Tensor mat = Tensors.of(ve1);
    Tensor rhs = Tensors.of(qs2);
    Tensor sol = LinearSolve.of(mat, rhs);
    Tensor res = mat.dot(sol);
    assertEquals(res, rhs);
  }

  public void testRowReduce() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Tensor ve1 = Tensors.of(qs1, qs2);
    // Tensor ve2 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1, ve1);
    // Tensor nul = RowReduce.of(Transpose.of(mat));
    Tensor nul = RowReduce.of(mat);
    nul.map(a -> a);
    // System.out.println(nul);
    // assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
  }

  public void testNullspace() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Tensor ve1 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1);
    Tensor nul = NullSpace.usingRowReduce(mat);
    // System.out.println(nul);
    assertEquals(nul, Tensors.fromString("{{1, -1/2}}"));
  }

  public void testCholesky() {
    Scalar qs1 = Quantity.of(RealScalar.of(1), "[m]");
    Scalar qs2 = Quantity.of(RealScalar.of(2), "[m]");
    Tensor ve1 = Tensors.of(qs2, qs1);
    Tensor ve2 = Tensors.of(qs1, qs2);
    Tensor mat = Tensors.of(ve1, ve2);
    CholeskyDecomposition cd = CholeskyDecomposition.of(mat);
    assertTrue(Scalars.nonZero(cd.det()));
    assertEquals(cd.diagonal().toString(), "{2[m], 3/2[m]}");
    assertTrue(PositiveDefiniteMatrixQ.ofHermitian(mat));
    assertTrue(PositiveSemidefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeDefiniteMatrixQ.ofHermitian(mat));
    assertFalse(NegativeSemidefiniteMatrixQ.ofHermitian(mat));
  }
}
