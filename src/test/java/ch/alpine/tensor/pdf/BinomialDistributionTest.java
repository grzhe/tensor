// code by jph
package ch.alpine.tensor.pdf;

import java.util.Arrays;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.red.Median;
import ch.alpine.tensor.red.Tally;
import ch.alpine.tensor.usr.AssertFail;
import junit.framework.TestCase;

public class BinomialDistributionTest extends TestCase {
  public void testPdf() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 7));
    PDF pdf = PDF.of(distribution);
    Scalar prob = RealScalar.ZERO;
    for (int c = 0; c <= 10; ++c)
      prob = prob.add(pdf.at(RealScalar.of(c)));
    assertTrue(IntegerQ.of(prob));
    assertEquals(prob, RealScalar.ONE);
  }

  public void testValue() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 2));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(1, 1024));
    assertEquals(pdf.at(RealScalar.of(0.5)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(5, 512));
  }

  public void testValue2() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(0)), RationalScalar.of(1024, 59049));
    // PDF[BinomialDistribution[10, 1/3], 1] == 5120/59049
    assertEquals(pdf.at(RealScalar.of(1)), RationalScalar.of(5120, 59049));
    // PDF[BinomialDistribution[10, 1/3], 10] == 1/59049
    assertEquals(pdf.at(RealScalar.of(10)), RationalScalar.of(1, 59049));
  }

  public void testValue3() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(1, 3));
    PDF pdf = PDF.of(distribution);
    assertEquals(pdf.at(RealScalar.of(-1)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(11)), RealScalar.ZERO);
    assertEquals(pdf.at(RealScalar.of(12)), RealScalar.ZERO);
  }

  public void testMean() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    PDF pdf = PDF.of(distribution);
    Tensor sum = RealScalar.ZERO;
    for (Tensor x : Range.of(0, 22))
      sum = sum.add(x.multiply(pdf.at((Scalar) x)));
    assertEquals(Expectation.mean(distribution), sum);
  }

  public void testMean2() {
    Distribution distribution = BinomialDistribution.of(10, RationalScalar.of(3, 10));
    assertEquals(Expectation.mean(distribution), RealScalar.of(3));
  }

  public void testHigh() {
    Distribution distribution = BinomialDistribution.of(21, RationalScalar.of(7, 13));
    CDF cdf = CDF.of(distribution);
    cdf.p_lessThan(RealScalar.of(-1000000000));
    cdf.p_lessThan(RealScalar.of(+1000000000));
    cdf.p_lessEquals(RealScalar.of(-1000000000));
    cdf.p_lessEquals(RealScalar.of(+1000000000));
  }

  public void testCornerCase() {
    assertEquals(RandomVariate.of(BinomialDistribution.of(0, RealScalar.ONE)), RealScalar.ZERO);
    assertEquals(RandomVariate.of(BinomialDistribution.of(0, RealScalar.ZERO)), RealScalar.ZERO);
    assertEquals(RandomVariate.of(BinomialDistribution.of(0, RealScalar.of(0.3))), RealScalar.ZERO);
  }

  public void testBug1() {
    assertTrue(10 < Tally.of(RandomVariate.of(BinomialDistribution.of(20, RationalScalar.of(2, 3)), 10000)).size());
  }

  public void testBug2() {
    assertTrue(20 < Tally.of(RandomVariate.of(BinomialDistribution.of(100, RationalScalar.of(2, 3)), 10000)).size());
  }

  public void testBug3() {
    Distribution distribution = BinomialDistribution.of(1207, RationalScalar.of(2, 3));
    int size = Tally.of(RandomVariate.of(distribution, 1000)).size();
    assertTrue(50 < size);
  }

  public void testBlub() {
    BinomialDistribution.of(1200, RealScalar.of(0.1));
    BinomialDistribution.of(1200, RealScalar.of(0.9));
  }

  public void testInRange() {
    assertEquals(BinomialDistribution.of(1000, DoubleScalar.of(0.5)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(2000, DoubleScalar.of(0.1)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(5000, DoubleScalar.of(0.01)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(5000, DoubleScalar.of(0.99)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(10000, DoubleScalar.of(0.0)).getClass(), BinomialDistribution.class);
    assertEquals(BinomialDistribution.of(10000, DoubleScalar.of(1.0)).getClass(), BinomialDistribution.class);
  }

  public void testNextDownOne() {
    AbstractDiscreteDistribution distribution = //
        (AbstractDiscreteDistribution) BinomialDistribution.of(1000, DoubleScalar.of(0.5));
    distribution.quantile(RealScalar.of(Math.nextDown(1.0)));
  }

  public void testNZero() {
    Distribution distribution = BinomialDistribution.of(0, RealScalar.ZERO);
    assertEquals(Expectation.mean(distribution), RealScalar.ZERO);
    assertEquals(Expectation.variance(distribution), RealScalar.ZERO);
    assertEquals(PDF.of(distribution).at(RealScalar.ZERO), RealScalar.ONE);
    assertEquals(RandomVariate.of(distribution), RealScalar.ZERO);
  }

  public void testInverseCDF() {
    InverseCDF inverseCDF = InverseCDF.of(BinomialDistribution.of(100, RationalScalar.of(2, 3)));
    Scalar x0 = inverseCDF.quantile(RealScalar.ZERO);
    Scalar x1 = inverseCDF.quantile(RealScalar.of(0.5));
    Scalar x2 = inverseCDF.quantile(RealScalar.of(0.8));
    Scalar x3 = inverseCDF.quantile(RealScalar.of(Math.nextDown(1.0)));
    assertEquals(x0, RealScalar.ZERO);
    assertEquals(x1, RealScalar.of(67));
    assertEquals(x2, RealScalar.of(71));
    assertEquals(x3, RealScalar.of(98));
  }

  public void testCDFMathematica() {
    int n = 5;
    Distribution distribution = BinomialDistribution.of(n, RationalScalar.of(1, 4));
    CDF cdf = CDF.of(distribution);
    Tensor actual = Range.of(0, n + 1).map(cdf::p_lessEquals);
    Tensor expect = Tensors.fromString("{243/1024, 81/128, 459/512, 63/64, 1023/1024, 1}");
    assertEquals(actual, expect);
  }

  public void testInverseCDFMathematica() {
    int n = 5;
    Distribution distribution = BinomialDistribution.of(n, RationalScalar.of(1, 4));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    Scalar actual = inverseCDF.quantile(RationalScalar.of(81, 128));
    Scalar expect = RealScalar.ONE;
    assertEquals(actual, expect);
  }

  public void testCDFInverseCDF() {
    int n = 20;
    Distribution distribution = BinomialDistribution.of(n, RationalScalar.of(1, 4));
    CDF cdf = CDF.of(distribution);
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    for (Tensor _x : Range.of(0, n + 1)) {
      Scalar x = (Scalar) _x;
      Scalar q = inverseCDF.quantile(cdf.p_lessEquals(x));
      assertEquals(x, q);
    }
  }

  public void testInverseCDF2() {
    InverseCDF inverseCDF = InverseCDF.of(BinomialDistribution.of(10, RationalScalar.of(1, 2)));
    Scalar x0 = inverseCDF.quantile(RealScalar.ZERO);
    Scalar x1 = inverseCDF.quantile(RealScalar.of(0.5));
    Scalar x2 = inverseCDF.quantile(RealScalar.of(0.8));
    Scalar x9 = inverseCDF.quantile(RealScalar.of(0.9));
    Scalar x3 = inverseCDF.quantile(RealScalar.of(Math.nextDown(1.0)));
    assertEquals(x0, RealScalar.ZERO);
    assertEquals(x1, RealScalar.of(5));
    assertEquals(x2, RealScalar.of(6));
    assertEquals(x9, RealScalar.of(7));
    assertEquals(x3, RealScalar.of(10));
  }

  public void testInverseCDFOne() {
    InverseCDF inverseCDF = InverseCDF.of(BinomialDistribution.of(10, RationalScalar.of(2, 3)));
    Scalar last = inverseCDF.quantile(RealScalar.ONE);
    assertEquals(last, RealScalar.of(10)); // consistent with Mathematica
  }

  public void testHashEquals() throws Exception {
    Distribution d1 = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    Distribution d2 = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    Distribution d3 = BinomialDistribution.of(3, RationalScalar.of(1, 3));
    Distribution d4 = NormalDistribution.of(1, 2);
    // assertEquals(d1, d2);
    // assertEquals(d1.hashCode(), d2.hashCode());
    assertFalse(d1.equals(d3));
    assertFalse(d1.hashCode() == d3.hashCode());
    assertFalse(d1.equals(d4));
    assertFalse(d1.hashCode() == d4.hashCode());
    byte[] b1 = Serialization.of(d1);
    byte[] b2 = Serialization.of(d2);
    assertTrue(Arrays.equals(b1, b2));
  }

  public void testToString() {
    Distribution distribution = BinomialDistribution.of(3, RationalScalar.of(1, 2));
    String string = distribution.toString();
    assertEquals(string, "BinomialDistribution[3, 1/2]");
  }

  public void testMedian() {
    for (int index = 0; index < 5; ++index) {
      Distribution distribution = BinomialDistribution.of(index * 2, RationalScalar.HALF);
      Scalar scalar = Median.of(distribution);
      assertEquals(scalar, RealScalar.of(index));
    }
  }

  public void testFailN() {
    AssertFail.of(() -> BinomialDistribution.of(-1, RationalScalar.of(1, 3)));
  }

  public void testFailP() {
    BinomialDistribution.of(3, RealScalar.ZERO);
    AssertFail.of(() -> BinomialDistribution.of(10, RationalScalar.of(-1, 3)));
    BinomialDistribution.of(3, RealScalar.ONE);
    AssertFail.of(() -> BinomialDistribution.of(10, RationalScalar.of(4, 3)));
    AssertFail.of(() -> BinomialDistribution.of(10, Quantity.of(0.2, "s")));
  }
}
