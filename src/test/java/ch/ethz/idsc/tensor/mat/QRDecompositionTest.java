// code by jph
package ch.ethz.idsc.tensor.mat;

import java.util.Random;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Array;
import ch.ethz.idsc.tensor.alg.Flatten;
import ch.ethz.idsc.tensor.lie.LieAlgebras;
import ch.ethz.idsc.tensor.lie.Rodriguez;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.qty.Quantity;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.N;
import junit.framework.TestCase;

public class QRDecompositionTest extends TestCase {
  private static QRDecomposition specialOps(Tensor A) {
    QRDecomposition qr = QRDecomposition.of(A);
    Tensor Q = qr.getQ();
    Tensor Qi = qr.getInverseQ();
    Tensor R = qr.getR();
    assertTrue(Chop._10.close(Q.dot(R), A));
    assertTrue(Chop._10.close(Q.dot(Qi), IdentityMatrix.of(A.length())));
    Scalar qrDet = Det.of(Q).multiply(Det.of(R));
    assertTrue(Chop._10.close(qrDet, Det.of(A)));
    Tensor lower = LowerTriangularize.of(R, -1);
    // System.out.println(Pretty.of(lower));
    assertTrue(Chop.NONE.allZero(lower));
    assertTrue(Chop._10.close(qrDet, qr.det()));
    return qr;
  }

  public void testExampleP32() {
    Tensor A = Tensors.matrix(new Number[][] { //
        { -1, -1, 1 }, //
        { 1, 3, 3 }, //
        { -1, -1, 5 }, //
        { 1, 3, 7 } });
    specialOps(A);
  }

  public void testRandomReal() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> RealScalar.of(rnd.nextDouble()), 5, 3);
    specialOps(A);
  }

  public void testRandomReal2() {
    Random rnd = new Random();
    Tensor A = Tensors.matrix((i, j) -> RealScalar.of(rnd.nextDouble()), 3, 5);
    specialOps(A);
  }

  public void testRandomRealSquare() {
    Distribution distribution = NormalDistribution.standard();
    for (int d = 1; d <= 10; ++d)
      specialOps(RandomVariate.of(distribution, d, d));
  }

  public void testDiag() {
    Tensor A = DiagonalMatrix.of(Tensors.vector(2, 3, 4));
    specialOps(A);
  }

  public void testDiag2() {
    Tensor A = DiagonalMatrix.of(2, -3, 0, 0, -1e-10, 0, 4e20);
    specialOps(A);
  }

  public void testZeros() {
    Tensor A = Array.zeros(4, 3);
    specialOps(A);
  }

  public void testRandomOrthogonal() {
    Random rnd = new Random();
    for (int c = 0; c < 5; ++c) {
      Tensor matrix = Rodriguez.of(Tensors.vector(l -> RealScalar.of(rnd.nextGaussian()), 3));
      specialOps(matrix);
      QRDecomposition qr = QRDecomposition.positive(matrix);
      assertTrue(Chop._13.close(qr.getR(), IdentityMatrix.of(3)));
      assertTrue(Chop._13.close(qr.getQ(), matrix));
    }
  }

  public void testRandomComplex1() {
    Random rnd = new Random();
    specialOps(Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 5, 3));
    specialOps(Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 3, 5));
  }

  public void testRandomComplex2() {
    Random rnd = new Random();
    specialOps(Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 4, 4));
    specialOps(Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 5, 5));
    specialOps(Tensors.matrix((i, j) -> ComplexScalar.of(rnd.nextGaussian(), rnd.nextGaussian()), 6, 6));
  }

  public void testComplexDiagonal() {
    Tensor matrix = DiagonalMatrix.of(ComplexScalar.of(2, 3), ComplexScalar.of(-6, -1));
    specialOps(matrix);
    // QRDecomposition qr = QRDecomposition.of(matrix);
    // System.out.println(qr.getR());
    // System.out.println(Det.of(qr.getR()));
    // System.out.println(Det.of(qr.getQ()));
  }

  public void testHilbert() {
    Tensor matrix = HilbertMatrix.of(4, 7);
    specialOps(matrix);
    QRDecomposition qr = QRDecomposition.of(matrix);
    assertEquals(qr.getR().get(1, 0), RealScalar.ZERO);
    assertEquals(qr.getR().get(2, 0), RealScalar.ZERO);
    assertEquals(qr.getR().get(2, 1), RealScalar.ZERO);
    assertEquals(qr.getR().get(3, 0), RealScalar.ZERO);
    assertEquals(qr.getR().get(3, 1), RealScalar.ZERO);
    assertEquals(qr.getR().get(3, 2), RealScalar.ZERO);
  }

  public void testQuantity() {
    Tensor matrix = Tensors.fromString( //
        "{{ 12[s], -51[s], 4[s] }, { 6[s], 167[s], -68[s] }, { -4[s], 24[s], -41[s] } }", //
        Quantity::fromString);
    specialOps(matrix);
    specialOps(N.DOUBLE.of(matrix));
    QRDecomposition qr = QRDecomposition.of(matrix);
    assertTrue(qr.det() instanceof Quantity);
  }

  public void testWikipedia() {
    Tensor matrix = Tensors.matrixInt( //
        new int[][] { { 12, -51, 4 }, { 6, 167, -68 }, { -4, 24, -41 } });
    specialOps(matrix);
    QRDecomposition qr = QRDecomposition.of(matrix);
    Tensor getR = Tensors.matrixInt( //
        new int[][] { { 14, 21, -14 }, { 0, 175, -70 }, { 0, 0, -35 } });
    assertEquals(getR, qr.getR());
    assertTrue(Flatten.of(qr.getR()).stream().allMatch(ExactScalarQ::of));
    assertTrue(Flatten.of(qr.getQ()).stream().allMatch(ExactScalarQ::of));
  }

  public void testLower() {
    Tensor matrix = Tensors.matrixInt( //
        new int[][] { { 0, -51, 4 }, { 6, 167, -68 }, { -4, 24, -41 } });
    specialOps(matrix);
  }

  public void testQuantityMixed() {
    Tensor matrix = Tensors.fromString( //
        "{{ 12[s], -51[A], 4[m] }, { 6[s], 167[A], -68[m] }, { -4[s], 24[A], -41[m] } }", //
        Quantity::fromString);
    specialOps(matrix);
    specialOps(N.DOUBLE.of(matrix));
    QRDecomposition qr = QRDecomposition.of(matrix);
    assertTrue(qr.det() instanceof Quantity);
  }

  public void testComplexMathematica() {
    Tensor matrix = Tensors.fromString( //
        "{{8 + I, 2 - 3 *I}, {3 + I, I}} ", //
        Quantity::fromString);
    specialOps(matrix);
    specialOps(N.DOUBLE.of(matrix));
    // QRDecomposition qr = QRDecomposition.of(matrix);
    // System.out.println(qr.getR());
  }

  public void testQuantityComplex() {
    Tensor matrix = Tensors.fromString( //
        "{{ 12+3*I[s], -51[A], 4[m] }, { 6[s], 167-7*I[A], -68[m] }, { -4*I[s], 24[A], -41-9*I[m] } }", //
        Quantity::fromString);
    specialOps(matrix);
    specialOps(N.DOUBLE.of(matrix));
  }

  public void testEmpty() {
    try {
      QRDecomposition.of(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testFail() {
    try {
      QRDecomposition.of(Tensors.fromString("{{1,2},{3,4,5}}"));
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
    try {
      QRDecomposition.of(LieAlgebras.sl3());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
