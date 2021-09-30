// code by jph
package ch.alpine.tensor.alg;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.num.RandomPermutation;
import ch.alpine.tensor.usr.AssertFail;
import junit.framework.TestCase;

public class SizeTest extends TestCase {
  public void testTotal() {
    assertEquals(Size.of(Arrays.asList(4, 2, 3)).total(), 24);
    assertEquals(Size.of(Arrays.asList(3, 5, 7)).total(), 3 * 5 * 7);
  }

  public void testString() {
    Size size = Size.of(Arrays.asList(4, 2, 3));
    String string = size.toString();
    assertFalse(string.isEmpty());
  }

  public void testStream() {
    List<Integer> list = Stream.iterate(3, i -> i < 10, i -> i + 1).collect(Collectors.toList());
    assertEquals(list, Arrays.asList(3, 4, 5, 6, 7, 8, 9));
  }

  public void testEmptyFail() {
    AssertFail.of(() -> Size.of(Arrays.asList()));
  }

  public void testIdentity() {
    int[] permute = Size.of(Arrays.asList(2, 3, 4)).permute(new int[] { 0, 1, 2 });
    assertEquals(permute[0], 2);
    assertEquals(permute[1], 3);
    assertEquals(permute[2], 4);
  }

  public void testInv() {
    int[] src = new int[] { 2, 0, 3, 4, 1 };
    int[] dst = Transpose.inverse(src);
    int[] rem = Transpose.inverse(dst);
    assertEquals(Tensors.vectorInt(src), Tensors.vectorInt(rem));
  }

  public void testInv2() {
    int[] src = new int[] { 2, 0, 3, 4, 1 };
    int[] sigma = Transpose.inverse(src);
    int[] size = new int[] { 4, 1, 0, 3, 2 };
    int[] result = Size.of(IntStream.of(size).boxed().collect(Collectors.toList())).permute(sigma);
    // System.out.println(Tensors.vectorInt(result));
    int[] value = new int[src.length];
    for (int index = 0; index < src.length; ++index)
      value[index] = size[src[index]];
    // System.out.println(Tensors.vectorInt(value));
    assertEquals(Tensors.vectorInt(result), Tensors.vectorInt(value));
  }

  public void testMultiCheck() {
    for (int count = 0; count < 50; ++count) {
      int n = 4 + (count % 5);
      int[] src = RandomPermutation.ofLength(n);
      int[] sigma = Transpose.inverse(src);
      int[] size = RandomPermutation.ofLength(n);
      List<Integer> list = IntStream.of(size).boxed().collect(Collectors.toList());
      int[] result = Size.of(list).permute(sigma);
      List<Integer> value = Transpose.reorder(list, src);
      assertEquals(Tensors.vectorInt(result), Tensors.vector(value));
    }
  }

  public void testRotate() {
    int[] permute = Size.of(Arrays.asList(2, 3, 4)).permute(new int[] { 2, 0, 1 });
    assertEquals(permute[0], 3);
    assertEquals(permute[1], 4);
    assertEquals(permute[2], 2);
  }

  public void testReorder() {
    List<Integer> list = Transpose.reorder(Arrays.asList(2, 3, 4), new int[] { 2, 0, 1 });
    assertEquals(list, Arrays.asList(4, 2, 3));
  }

  public void testPermuteList() {
    List<Integer> list = Transpose.inverse(Arrays.asList(2, 3, 4), new int[] { 2, 0, 1 });
    assertEquals(list, Arrays.asList(3, 4, 2));
  }

  public void testVisibility() {
    assertEquals(Size.class.getModifiers(), 0);
  }
}
