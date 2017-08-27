// code by jph
package ch.ethz.idsc.tensor.red;

import ch.ethz.idsc.tensor.ComplexScalar;
import ch.ethz.idsc.tensor.DoubleScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.sca.Chop;
import junit.framework.TestCase;

public class Norm2Test extends TestCase {
  public void testScalar() {
    assertEquals(Norm._2.of(Tensors.fromString("{0}")), RealScalar.ZERO);
    assertEquals(Norm._2.of(Tensors.fromString("{-3.90512}")), Scalars.fromString("3.90512"));
    assertEquals(Norm._2.of(Tensors.fromString("{-3/7}")), Scalars.fromString("3/7"));
    Scalar rs = Norm._2.of(Tensors.of(ComplexScalar.of(RealScalar.ONE, RealScalar.of(2)))); // <- sqrt(5)
    assertEquals(rs, Scalars.fromString("2.23606797749979"));
    assertEquals(Norm2Squared.vector(Tensors.of(Scalars.fromString("-3/7"))), Scalars.fromString("9/49"));
  }

  public void testVector1() {
    Tensor A = Tensors.vectorDouble(new double[] { 2, 1.5, 3 });
    assertEquals(Norm._2.of(A), Scalars.fromString("3.905124837953327"));
  }

  public void testVector2() {
    Tensor A = Tensors.of(ComplexScalar.of( //
        RealScalar.ONE, RealScalar.of(2)), DoubleScalar.of(1.5));
    assertEquals(Norm._2.of(A), Scalars.fromString("2.6925824035672523"));
    Tensor a = Tensors.vector(2, 3, 4);
    assertEquals(Norm2Squared.vector(a), a.dot(a));
  }

  public void testVector3() {
    Tensor A = Tensors.of(ComplexScalar.of(1, 2), DoubleScalar.of(1.5));
    assertEquals(Norm._2.of(A), DoubleScalar.of(2.6925824035672523)); // 2.69258
  }

  public void testMatrix1() {
    Tensor matrix = Tensors.matrix(new Number[][] { { 1, 2, 3 }, { 9, -3, 0 } });
    Scalar nrm = Norm._2.matrix(matrix);
    assertEquals(nrm, Norm._2.matrix(Transpose.of(matrix)));
    // Mathematica: 9.493062577750756
    assertTrue(Chop._14.close(nrm, DoubleScalar.of(9.493062577750756)));
  }

  public void testMatrix2() {
    Tensor matrix = Tensors.fromString("{{}}");
    try {
      Norm._2.of(matrix);
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEmpty() {
    try {
      Norm._2.vector(Tensors.empty());
      assertTrue(false);
    } catch (Exception exception) {
      // ---
    }
  }
}
