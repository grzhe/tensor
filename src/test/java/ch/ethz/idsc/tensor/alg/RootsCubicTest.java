// code by jph
package ch.ethz.idsc.tensor.alg;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.ScalarUnaryOperator;
import junit.framework.TestCase;

public class RootsCubicTest extends TestCase {
  public void testCubic() {
    Tensor coeffs = Tensors.vector(2, 3, 4, 5);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._13.allZero(tensor));
  }

  public void testMonomial() {
    Tensor coeffs = Tensors.vector(0, 0, 0, 1);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
  }

  public void testMonomialShiftedP() {
    Tensor coeffs = Tensors.vector(1, 3, 3, 1);
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, Tensors.vector(-1, -1, -1));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
  }

  public void testMonomialShiftedN() {
    Tensor coeffs = Tensors.vector(1, -3, 3, -1);
    Tensor roots = Roots.of(coeffs);
    assertEquals(roots, Tensors.vector(1, 1, 1));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertEquals(tensor, Array.zeros(3));
  }

  public void testMonomialQuadShift() {
    Tensor coeffs = Tensors.vector(1, 1, -1, -1);
    Tensor roots = Roots.of(coeffs);
    assertTrue(Chop._12.close(roots, Tensors.vector(-1, 1, -1)));
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._07.allZero(tensor));
  }

  public void testCubicQuantity() {
    Tensor coeffs = Tensors.fromString("{2[m^-5], 3[m^-4], 4[m^-3], 5[m^-2]}");
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._13.allZero(tensor));
  }

  public void testCubicNumerics() {
    Tensor coeffs = Tensors.vector(0.7480756509468256, -0.11264914570345713, 0.5215628590156208, -0.8016542468533115);
    Tensor roots = Roots.of(coeffs);
    ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
    Tensor tensor = roots.map(scalarUnaryOperator);
    assertTrue(Chop._05.allZero(tensor));
  }

  public void testCubicRandom() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 20; ++index) {
      Tensor coeffs = RandomVariate.of(distribution, 4);
      Tensor roots = Roots.of(coeffs);
      ScalarUnaryOperator scalarUnaryOperator = Series.of(coeffs);
      Tensor tensor = roots.map(scalarUnaryOperator);
      boolean allZero = Chop._04.allZero(tensor);
      if (!allZero) {
        System.out.println(coeffs);
        System.out.println(tensor);
      }
      assertTrue(allZero);
    }
  }

  public void testCubicChallenge() {
    Tensor coeffs = Tensors.vector(1.8850384838238452, -0.07845356111460325, -0.6128180724984655, -1.5845220466594934);
    Tensor roots = Roots.of(coeffs);
    Scalar m0 = Scalars.fromString("-0.6590816994450482 - 0.9180824258012533 * I");
    Scalar m1 = Scalars.fromString("-0.6590816994450482 + 0.9180824258012533 * I");
    Scalar m2 = RealScalar.of(0.9314107665802999);
    assertTrue(roots.stream().map(scalar -> scalar.subtract(m0)).anyMatch(Chop._05::allZero));
    assertTrue(roots.stream().map(scalar -> scalar.subtract(m1)).anyMatch(Chop._05::allZero));
    assertTrue(roots.stream().map(scalar -> scalar.subtract(m2)).anyMatch(Chop._05::allZero));
  }
}
