// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;
import java.lang.reflect.Modifier;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.ExactTensorQ;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.Serialization;
import ch.ethz.idsc.tensor.mat.Tolerance;
import ch.ethz.idsc.tensor.num.GaussScalar;
import ch.ethz.idsc.tensor.opt.Pi;
import ch.ethz.idsc.tensor.pdf.Distribution;
import ch.ethz.idsc.tensor.pdf.NormalDistribution;
import ch.ethz.idsc.tensor.pdf.RandomVariate;
import ch.ethz.idsc.tensor.sca.Abs;
import ch.ethz.idsc.tensor.sca.AbsSquared;
import ch.ethz.idsc.tensor.sca.Chop;
import ch.ethz.idsc.tensor.sca.Power;
import ch.ethz.idsc.tensor.sca.Sign;
import junit.framework.TestCase;

public class QuaternionImplTest extends TestCase {
  public void testImmutable() {
    Quaternion quaternion = Quaternion.of(1, 3, -2, 2);
    try {
      quaternion.xyz().set(RealScalar.ONE, 1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testPower2() {
    Scalar quaternion = Quaternion.of(1, 3, -2, 2);
    Scalar q2 = Power.of(quaternion, RealScalar.of(2));
    Scalar qm = quaternion.multiply(quaternion);
    Chop._12.requireClose(q2, qm);
  }

  public void testPower3() {
    Scalar quaternion = Quaternion.of(1, 3, -2, 2);
    Scalar q3 = Power.of(quaternion, RealScalar.of(3));
    Scalar qm = quaternion.multiply(quaternion).multiply(quaternion);
    Chop._12.requireClose(q3, qm);
  }

  public void testPower3Random() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 10; ++index) {
      Scalar quaternion = Quaternion.of(RandomVariate.of(distribution), RandomVariate.of(distribution, 3));
      Scalar q3 = Power.of(quaternion, RealScalar.of(3));
      Scalar qm = quaternion.multiply(quaternion).multiply(quaternion);
      Chop._12.requireClose(q3, qm);
    }
  }

  public void testPowerN1Random() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 10; ++index) {
      Scalar quaternion = Quaternion.of(RandomVariate.of(distribution), RandomVariate.of(distribution, 3));
      Scalar qr = Power.of(quaternion, RealScalar.of(-1));
      Scalar qm = quaternion.reciprocal();
      Chop._12.requireClose(qr, qm);
    }
  }

  public void testPowerN2Random() {
    Distribution distribution = NormalDistribution.standard();
    for (int index = 0; index < 10; ++index) {
      Scalar quaternion = Quaternion.of(RandomVariate.of(distribution), RandomVariate.of(distribution, 3));
      Scalar q2r = Power.of(quaternion, RealScalar.of(-2));
      Scalar qm = quaternion.multiply(quaternion).reciprocal();
      Chop._12.requireClose(q2r, qm);
    }
  }

  public void testPowerReal() {
    Scalar quaternion = Quaternion.of(3, 0, 0, 0);
    Scalar qm = quaternion.multiply(quaternion);
    Scalar q2 = Power.of(quaternion, RealScalar.of(2));
    Chop._12.requireClose(q2, qm);
  }

  public void testPower0() {
    assertEquals(Power.of(Quaternion.of(3, 1, 2, 3), RealScalar.ZERO), Quaternion.ONE);
    assertEquals(Power.of(Quaternion.of(3, 0, 0, 0), RealScalar.ZERO), Quaternion.ONE);
    assertEquals(Power.of(Quaternion.of(0, 1, 2, 3), RealScalar.ZERO), Quaternion.ONE);
  }

  public void testPowerExact() {
    Scalar scalar = Power.of(Quaternion.of(-2, 1, 2, 3), RealScalar.of(4));
    assertEquals(scalar, Quaternion.of(-124, 80, 160, 240));
    ExactScalarQ.require(scalar);
  }

  public void testPowerExactNumeric() {
    Scalar scalar = Power.of(Quaternion.of(-2, 1, 2, 3), Pi.VALUE);
    assertFalse(ExactScalarQ.of(scalar));
  }

  public void testPowerXYZ0() {
    Scalar scalar = Power.of(Quaternion.of(2.0, 0, 0, 0), RealScalar.of(3));
    assertEquals(scalar, Quaternion.of(8, 0, 0, 0));
    Quaternion quaternion = (Quaternion) scalar;
    ExactTensorQ.require(quaternion.xyz());
  }

  public void testUnaffected() {
    Tensor vector = Tensors.vector(1, 2, 3);
    Quaternion quaternion = Quaternion.of(RealScalar.ZERO, vector);
    vector.set(RealScalar.ZERO, 1);
    assertEquals(quaternion.xyz(), Tensors.vector(1, 2, 3));
  }

  public void testPlusFail() {
    Scalar quaternion = Quaternion.of(1, 3, -2, 2);
    Scalar quantity = Quantity.of(1, "m");
    try {
      quaternion.add(quantity);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      quantity.add(quaternion);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testMultiplyFail() {
    Quaternion quaternion = Quaternion.of(1, 3, -2, 2);
    GaussScalar gaussScalar = GaussScalar.of(3, 11);
    assertFalse(quaternion.equals(gaussScalar));
    assertFalse(gaussScalar.equals(quaternion));
    try {
      quaternion.multiply(gaussScalar);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      gaussScalar.multiply(quaternion);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testAbs() {
    Scalar scalar = Abs.FUNCTION.apply(Quaternion.of(2, 2, 4, 5));
    assertEquals(ExactScalarQ.require(scalar), RealScalar.of(7));
  }

  public void testAbsSquared() {
    Scalar scalar = AbsSquared.FUNCTION.apply(Quaternion.of(1, 2, 3, 4));
    assertEquals(ExactScalarQ.require(scalar), RealScalar.of(30));
  }

  public void testNumberFail() {
    Quaternion quaternion = Quaternion.of(1, 3, -2, 2);
    try {
      quaternion.number();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSign() {
    Scalar q1 = Quaternion.of(Double.MIN_VALUE, 0, Double.MIN_VALUE, 0);
    Scalar abs = Abs.FUNCTION.apply(q1);
    assertEquals(abs, RealScalar.of(Double.MIN_VALUE));
    Scalar scalar = Sign.FUNCTION.apply(q1);
    Tolerance.CHOP.requireClose(scalar, Quaternion.of(0.7071067811865475, 0, 0.7071067811865475, 0));
  }

  public void testEquals() {
    Quaternion q0 = Quaternion.of(1, 3, -2, 2);
    Quaternion q1 = Quaternion.of(1, 3, -2, 2);
    Quaternion q2 = Quaternion.of(1, 3, -2, 4);
    assertTrue(q0.equals(q1));
    assertFalse(q1.equals(q2));
  }

  public void testHashcode() {
    Tensor tensor = Tensors.of( //
        Quaternion.of(1, 3, -2, 2), //
        Quaternion.of(3, 1, -2, 2), //
        Quaternion.of(3, 2, -2, 1), //
        Quaternion.of(1, 3, 2, -2));
    long count = tensor.stream().mapToInt(Tensor::hashCode).distinct().count();
    assertEquals(count, tensor.length());
  }

  public void testSerializable() throws ClassNotFoundException, IOException {
    Scalar q1 = Quaternion.of(1, 3, -2, 2);
    Scalar q2 = Serialization.copy(q1);
    assertEquals(q1, q2);
  }

  public void testToString() {
    Quaternion quaternion = Quaternion.of(1, 2, 3, 4);
    String string = quaternion.toString();
    assertEquals(string, "{\"w\": 1, \"xyz\": {2, 3, 4}}");
  }

  public void testPackageVisibility() {
    assertFalse(Modifier.isPublic(QuaternionImpl.class.getModifiers()));
  }
}
