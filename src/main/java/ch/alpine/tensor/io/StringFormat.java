// code by jph
package ch.alpine.tensor.io;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

/** LONGTERM StringFormat should be the replacement of TensorParser
 * that is used in {@link Tensors#fromString(String)} */
/* package */ class StringFormat<T> {
  /** @param string
   * @param function
   * @return */
  public static Tensor parse(String string, Function<String, Scalar> function) {
    return new StringFormat<>(new TensorJoiningInverse(function), string).parse();
  }

  /** @param string
   * @return */
  public static Tensor parse(String string) {
    return parse(string, Scalars::fromString);
  }

  /***************************************************/
  private final JoiningInverse<T> joiningInverse;
  private final String string;
  private int head = 0;
  private int index = 0;

  private StringFormat(JoiningInverse<T> joiningInverse, String string) {
    this.joiningInverse = joiningInverse;
    this.string = string;
  }

  private void handle(int chr) {
    if (chr == Tensor.OPENING_BRACKET) {
      joiningInverse.prefix();
      head = index + 1;
    } else //
    if (chr == ',') {
      joiningInverse.delimiter(string.substring(head, index));
      head = index + 1;
    } else //
    if (chr == Tensor.CLOSING_BRACKET) {
      joiningInverse.suffix(string.substring(head, index));
      head = index + 1;
    }
    ++index;
  }

  private T parse() {
    try {
      string.chars().forEach(this::handle);
      if (head != index)
        joiningInverse.delimiter(string.substring(head, index));
      return joiningInverse.emit();
    } catch (Exception exception) {
      // ---
    }
    return joiningInverse.fallback(string);
  }
}

/* package */ interface JoiningInverse<T> {
  void prefix();

  void suffix(String string);

  /** @param string from last separator to delimiter excluding */
  void delimiter(String string);

  T emit();

  T fallback(String string);
}

/* package */ class TensorJoiningInverse implements JoiningInverse<Tensor> {
  private final Function<String, Scalar> function;
  private final Deque<Tensor> deque = new ArrayDeque<>();

  public TensorJoiningInverse(Function<String, Scalar> function) {
    this.function = function;
    deque.push(Tensors.empty());
  }

  private void handle(String _string) {
    String string = _string.strip();
    if (!string.isEmpty())
      deque.peek().append(function.apply(string));
  }

  @Override
  public void prefix() {
    deque.push(Tensors.empty());
  }

  @Override
  public void suffix(String string) {
    handle(string);
    Tensor pop = deque.pop();
    deque.peek().append(pop);
  }

  @Override
  public void delimiter(String string) {
    handle(string);
  }

  @Override
  public Tensor emit() {
    Tensor tensor = deque.pop();
    if (deque.isEmpty() && tensor.length() <= 1)
      return tensor.get(0);
    throw new IllegalStateException();
  }

  @Override
  public Tensor fallback(String string) {
    return StringScalar.of(string);
  }
}
