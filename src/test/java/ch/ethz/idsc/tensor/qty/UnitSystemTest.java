// code by jph
package ch.ethz.idsc.tensor.qty;

import java.io.IOException;
import java.util.Properties;

import ch.ethz.idsc.tensor.ExactScalarQ;
import ch.ethz.idsc.tensor.RationalScalar;
import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.ext.Serialization;
import ch.ethz.idsc.tensor.io.ResourceData;
import ch.ethz.idsc.tensor.red.Total;
import ch.ethz.idsc.tensor.usr.AssertFail;
import junit.framework.TestCase;

public class UnitSystemTest extends TestCase {
  public void testSize() {
    TestHelper.checkInvariant(UnitSystem.SI());
    int size = UnitSystem.SI().map().size();
    if (size < 113) {
      System.err.println("unit count: " + size);
      fail();
    }
  }

  public void testExact() {
    Scalar scalar = UnitSystem.SI().apply(Quantity.of(3, "Hz^-2*N*m^-1"));
    assertEquals(scalar, Quantity.of(3, "kg"));
    ExactScalarQ.require(scalar);
  }

  public void testScalar() {
    Scalar scalar = RealScalar.ONE;
    Scalar result = UnitSystem.SI().apply(scalar);
    assertEquals(result, scalar);
    ExactScalarQ.require(result);
  }

  public void testVoltage() {
    Scalar normal = UnitSystem.SI().apply(Quantity.of(1, "V"));
    assertEquals(normal, Quantity.of(1, "A^-1*kg*m^2*s^-3"));
    ExactScalarQ.require(normal);
  }

  public void testMiles() {
    Scalar normal = UnitSystem.SI().apply(Quantity.of(125, "mi"));
    assertEquals(normal, Quantity.of(201168, "m"));
  }

  public void testNullFail() {
    AssertFail.of(() -> UnitSystem.SI().apply(null));
  }

  public void testMore() {
    Tensor tensor = Tensors.of( //
        Quantity.of(3, "Hz^-2*N*m^-1"), //
        Quantity.of(3.6, "km*h^-1"), //
        Quantity.of(2, "cm^2"));
    Tensor result = tensor.map(UnitSystem.SI());
    assertEquals(result, Tensors.of( //
        Quantity.of(3, "kg"), //
        Quantity.of(1, "m*s^-1"), //
        Scalars.fromString("1/5000[m^2]")));
  }

  public void testElectric() {
    UnitSystem unitSystem = UnitSystem.SI();
    Scalar r1 = unitSystem.apply(Quantity.of(3, "Ohm"));
    Scalar r2 = unitSystem.apply(Quantity.of(3, "V*A^-1"));
    assertEquals(r1, r2);
  }

  public void testCustom() {
    Properties properties = new Properties();
    properties.setProperty("EUR", "1.25[CHF]");
    properties.setProperty("Apples", "2[CHF]");
    properties.setProperty("Chocolates", "3[CHF]");
    properties.setProperty("Oranges", "1[CHF]");
    UnitSystem prices = SimpleUnitSystem.from(properties);
    assertEquals(prices.apply(Quantity.of(3, "Apples")), Quantity.of(6, "CHF"));
    Tensor cart = Tensors.of(Quantity.of(2, "Apples"), Quantity.of(3, "Chocolates"), Quantity.of(3, "Oranges"));
    AssertFail.of(() -> Total.of(cart));
    Scalar total = Total.ofVector(cart.map(prices));
    assertEquals(total, Quantity.of(16, "CHF"));
    Scalar euro = UnitConvert.of(prices).to(Unit.of("EUR")).apply(total);
    assertEquals(euro, Quantity.of(12.8, "EUR"));
  }

  public void testKnots() throws ClassNotFoundException, IOException {
    UnitSystem unitSystem = Serialization.copy(UnitSystem.SI());
    Scalar r1 = unitSystem.apply(Quantity.of(1, "knots"));
    Unit unit = QuantityUnit.of(r1);
    assertEquals(unit, Unit.of("m*s^-1"));
    ExactScalarQ.require(r1);
    Scalar r2 = UnitConvert.SI().to(Unit.of("km*h^-1")).apply(r1);
    ExactScalarQ.require(r2);
    Scalar r3 = Quantity.of(RationalScalar.of(463, 250), "km*h^-1");
    ExactScalarQ.require(r3);
    assertEquals(r2, r3);
  }

  public void testFail1() {
    Properties properties = ResourceData.properties("/unit/fail1.properties");
    assertFalse(properties.entrySet().isEmpty());
    AssertFail.of(() -> SimpleUnitSystem.from(properties));
  }

  public void testFail2() {
    Properties properties = ResourceData.properties("/unit/fail2.properties");
    assertFalse(properties.entrySet().isEmpty());
    AssertFail.of(() -> SimpleUnitSystem.from(properties));
  }

  public void testFail3() {
    Properties properties = ResourceData.properties("/unit/fail3.properties");
    assertFalse(properties.entrySet().isEmpty());
    AssertFail.of(() -> SimpleUnitSystem.from(properties));
  }

  public void testFail4() {
    Properties properties = ResourceData.properties("/unit/fail4.properties");
    assertFalse(properties.entrySet().isEmpty());
    AssertFail.of(() -> SimpleUnitSystem.from(properties));
  }

  public void testFail5() {
    Properties properties = ResourceData.properties("/unit/fail5.properties");
    assertFalse(properties.entrySet().isEmpty());
    AssertFail.of(() -> SimpleUnitSystem.from(properties));
  }
}
