// code by jph
package ch.alpine.tensor;

import java.util.List;

import ch.alpine.tensor.fft.ListCorrelate;
import ch.alpine.tensor.itp.LinearInterpolation;

/** ViewTensor overrides the methods {@link #extract(int, int)} and {@link #block(List, List)}.
 * The implementation returns the content provided by these methods via reference.
 * Since this access exposes the content of this ViewTensor for modification from the outside
 * the functionality is used only in very special and controlled applications.
 * 
 * A ViewTensor is created using {@link Unprotect#references(Tensor)}.
 * Within the tensor library, ViewTensor is used to speed up the computations in
 * {@link LinearInterpolation}, and {@link ListCorrelate}. */
/* package */ class ViewTensor extends TensorImpl {
  /** @param tensor guaranteed to not be in instance of UnmodifiableTensor
   * @return */
  public static TensorImpl wrap(Tensor tensor) {
    return new ViewTensor(((TensorImpl) tensor).list());
  }

  // ---
  private ViewTensor(List<Tensor> list) {
    super(list);
  }

  @Override // from TensorImpl
  public Tensor extract(int fromIndex, int toIndex) {
    return new ViewTensor(list().subList(fromIndex, toIndex));
  }

  @Override // from TensorImpl
  public Tensor _block(List<Integer> fromIndex, List<Integer> dimensions) {
    int loIndex = fromIndex.get(0);
    List<Tensor> subList = list().subList(loIndex, loIndex + dimensions.get(0));
    int size = fromIndex.size();
    return size == 1 //
        ? new ViewTensor(subList)
        : Tensor.of(subList.stream() //
            .map(tensor -> wrap(tensor)._block(fromIndex.subList(1, size), dimensions.subList(1, size))));
  }
}
