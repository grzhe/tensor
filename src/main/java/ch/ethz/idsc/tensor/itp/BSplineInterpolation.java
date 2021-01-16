// code by jph
package ch.ethz.idsc.tensor.itp;

import java.io.Serializable;
import java.util.stream.IntStream;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Range;
import ch.ethz.idsc.tensor.alg.Transpose;
import ch.ethz.idsc.tensor.alg.UnitVector;
import ch.ethz.idsc.tensor.api.ScalarTensorFunction;
import ch.ethz.idsc.tensor.mat.LinearSolve;

/** BSplineInterpolation defines a parametric curve that interpolates
 * the given control points at integer values.
 * 
 * <p>The input to {@link #get(Tensor)} is required to be of the form
 * <pre>
 * get({x_0, i_1, ..., i_n})
 * </pre>
 * where x_0 is a real number, and i_1, ..., i_n are integers. */
public class BSplineInterpolation extends AbstractInterpolation implements Serializable {
  private static final long serialVersionUID = -5285476231580435365L;

  /** @param degree of b-spline basis functions: 1 for linear, 2 for quadratic, etc.
   * @param control points with at least one element
   * @return */
  public static Interpolation of(int degree, Tensor control) {
    return new BSplineInterpolation(degree, control);
  }

  /** @param degree of b-spline basis functions: 1 for linear, 2 for quadratic, etc.
   * @param n number of control points
   * @return */
  public static Tensor matrix(int degree, int n) {
    Tensor domain = Range.of(0, n);
    return Transpose.of(Tensor.of(IntStream.range(0, n) //
        .mapToObj(index -> domain.map(BSplineFunction.string(degree, UnitVector.of(n, index))))));
  }

  /** @param degree
   * @param control
   * @return control points that define a limit that interpolates the points in the given tensor */
  public static Tensor solve(int degree, Tensor control) {
    return LinearSolve.of(matrix(degree, control.length()), control);
  }

  /***************************************************/
  private final ScalarTensorFunction scalarTensorFunction;

  private BSplineInterpolation(int degree, Tensor control) {
    scalarTensorFunction = BSplineFunction.string(degree, solve(degree, control));
  }

  @Override // from Interpolation
  public Tensor get(Tensor index) {
    return at(index.Get(0)).get(index.stream() //
        .skip(1) //
        .map(Scalar.class::cast) //
        .map(Scalars::intValueExact) //
        .toArray(Integer[]::new));
  }

  @Override // from Interpolation
  public Tensor at(Scalar index) {
    return scalarTensorFunction.apply(index);
  }
}
