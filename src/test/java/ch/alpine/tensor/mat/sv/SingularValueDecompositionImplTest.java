// code by jph
package ch.alpine.tensor.mat.sv;

import java.lang.reflect.Modifier;
import java.nio.file.Paths;

import ch.alpine.tensor.NumberQ;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.io.Get;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.mat.HilbertMatrix;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.mat.NullSpace;
import ch.alpine.tensor.mat.SquareMatrixQ;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Chop;
import ch.alpine.tensor.sca.N;
import junit.framework.TestCase;

public class SingularValueDecompositionImplTest extends TestCase {
  private static void _check(Tensor matrix) {
    assertTrue(SquareMatrixQ.of(matrix));
    TestHelper.specialOps(matrix);
    Tensor svd = IdentityMatrix.of(matrix.length()).subtract(Transpose.of(matrix));
    TestHelper.specialOps(svd);
  }

  public void testResource() throws Exception {
    String string = getClass().getResource("/mat/svd0.mathematica").getPath();
    _check(Get.of(Paths.get(string).toFile()));
  }

  public void testCondition1() {
    Tensor matrix = ResourceData.of("/mat/svd3.csv");
    TestHelper.specialOps(matrix);
     
  }

  public void testCondition2() {
    Tensor matrix = ResourceData.of("/mat/svd2.csv");
    TestHelper.specialOps(matrix);
  }

  public void testCondition1UnitA() {
    Tensor matrix = ResourceData.of("/mat/svd3.csv");
    TestHelper.specialOps(matrix.map(s -> Quantity.of(s, "m")));
  }
  public void testCondition1UnitB() {
    Tensor matrix = ResourceData.of("/mat/svd3.csv").map(s -> Quantity.of(s, "m"));
    matrix.append(matrix.get(0));
    TestHelper.specialOps(matrix);
  }

  public void testCondition2UnitA() {
    Tensor matrix = ResourceData.of("/mat/svd2.csv").map(s -> Quantity.of(s, "m"));
    TestHelper.specialOps(matrix);
  }

  public void testCondition2UnitB() {
    Tensor matrix = ResourceData.of("/mat/svd2.csv").map(s -> Quantity.of(s, "m"));
    matrix.append(matrix.get(0));
    TestHelper.specialOps(matrix);
  }

  public void testEps() {
    Tensor A = Tensors.fromString("{{1, 0}, {0, 1E-14}}");
    assertTrue(NumberQ.all(A));
    SingularValueDecomposition svd = SingularValueDecomposition.of(A);
    assertEquals(NullSpace.of(svd).length(), 1);
    assertEquals(NullSpace.of(svd, Chop._20), Tensors.empty());
    assertTrue(svd.toString().startsWith("SingularValueDecomposition["));
  }

  public void testDecimalScalar() {
    Tensor matrix = HilbertMatrix.of(5, 3).map(N.DECIMAL128);
    SingularValueDecomposition.of(matrix);
  }

  public void testPackageVisibility() {
    assertTrue(Modifier.isPublic(SingularValueDecomposition.class.getModifiers()));
    assertFalse(Modifier.isPublic(SingularValueDecompositionIter.class.getModifiers()));
    assertFalse(Modifier.isPublic(SingularValueDecompositionInit.class.getModifiers()));
  }
}
