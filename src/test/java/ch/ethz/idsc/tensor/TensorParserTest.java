// code by jph
package ch.ethz.idsc.tensor;

import junit.framework.TestCase;

public class TensorParserTest extends TestCase {
  public void testFromString() {
    assertEquals(Tensors.fromString("{   }"), Tensors.empty());
    assertEquals(Tensors.fromString("{ 2 ,-3   , 4}"), Tensors.vector(2, -3, 4));
    assertEquals(Tensors.fromString("{   {2, -3, 4  }, {2.3,-.2   }, {  }   }"), //
        Tensors.of(Tensors.vector(2, -3, 4), Tensors.vector(2.3, -.2), Tensors.empty()));
  }

  public void testFailBug() {
    assertTrue(Tensors.fromString("{2.5") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5}}") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5,") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5,}}") instanceof StringScalar);
    assertTrue(Tensors.fromString("{2.5,},}") instanceof StringScalar);
  }

  public void testComma() {
    Tensor scalar = Tensors.fromString("3.12,");
    assertTrue(scalar instanceof StringScalar);
  }

  public void testDubious() {
    Tensor vector = Tensors.fromString("{2.2,3,}"); // parses to {2.2, 3}
    vector.length();
  }

  public void testFromStringFunction() {
    Tensor tensor = Tensors.fromString("{ 2 ,-3   , 4}", string -> RealScalar.of(3));
    assertEquals(tensor, Tensors.vector(3, 3, 3));
  }

  public void testFromStringFunctionNested() {
    Tensor tensor = Tensors.fromString("{ 2 ,{-3   , 4} }", string -> RealScalar.of(3));
    assertEquals(tensor, Tensors.fromString("{3, {3, 3}}"));
  }
}
