// code by jph
package ch.ethz.idsc.tensor.qty;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.TensorRuntimeException;
import ch.ethz.idsc.tensor.io.StringScalar;
import ch.ethz.idsc.tensor.sca.Power;

/** reference implementation of {@link UnitSystem} with emphasis on simplicity */
public class SimpleUnitSystem implements UnitSystem {
  private static final long serialVersionUID = 3736511423557524434L;

  /** given properties map a unit expression to a {@link Quantity}
   * 
   * <p>Example from the built-in file "/unit/si.properties":
   * <pre>
   * K=1[K]
   * rad=1
   * Hz=1[s^-1]
   * N=1[m*kg*s^-2]
   * Pa=1[m^-1*kg*s^-2]
   * ...
   * </pre>
   * 
   * @param properties
   * @return
   * @throws Exception if keys do not define unit conversions */
  public static UnitSystem from(Properties properties) {
    return from(properties.stringPropertyNames().stream().collect(Collectors.toMap( //
        Function.identity(), // example: "kW"
        key -> Scalars.fromString(properties.getProperty(key))))); // example: 1000[m^2*kg*s^-3]
  }

  /** @param map
   * @return unit system */
  public static UnitSystem from(Map<String, Scalar> map) {
    return _from(requireTransitionFree(map));
  }

  /* package */ static UnitSystem _from(Map<String, Scalar> map) {
    return new SimpleUnitSystem(map.entrySet().stream().collect(Collectors.toMap( //
        entry -> StaticHelper.requireAtomic(entry.getKey()), // example: "kV"
        entry -> requireNumeric(entry.getValue())))); // example: 1000[m^2*kg*s^-3*A^-1]
  }

  private static Map<String, Scalar> requireTransitionFree(Map<String, Scalar> map) {
    for (Scalar scalar : map.values())
      for (String atom : QuantityUnit.of(scalar).map().keySet()) // example: m, kg, s, A
        // TODO can be checked by multiplication x*x == x but x!=0 and unit ^2
        if (map.containsKey(atom) && !scalar.equals(Quantity.of(RealScalar.ONE, atom)))
          throw TensorRuntimeException.of(scalar);
    return map;
  }

  /***************************************************/
  private final Map<String, Scalar> map;

  private SimpleUnitSystem(Map<String, Scalar> map) {
    this.map = map;
  }

  @Override
  public Scalar apply(Scalar scalar) {
    if (scalar instanceof Quantity) {
      Quantity quantity = (Quantity) scalar;
      NavigableMap<String, Scalar> navigableMap = new TreeMap<>();
      Scalar product = null; // avoids to introduce a multiplicative 1
      for (Entry<String, Scalar> entry : quantity.unit().map().entrySet()) {
        Scalar lookup = map.get(entry.getKey());
        if (Objects.isNull(lookup)) // in case of base units, e.g. "m" for SI
          // entry key and entry value are added to unit of value
          StaticHelper.merge(navigableMap, entry.getKey(), entry.getValue());
        else { // in case of unit definitions, e.g. "Pa" for SI
          Scalar factor = Power.of(lookup, entry.getValue());
          product = Objects.isNull(product) //
              ? factor
              : product.multiply(factor);
        }
      }
      return StaticHelper.multiply(Objects.isNull(product) //
          ? quantity.value()
          : product.multiply(quantity.value()), new UnitImpl(navigableMap));
    }
    return Objects.requireNonNull(scalar);
  }

  @Override // from UnitSystem
  public Map<String, Scalar> map() {
    return Collections.unmodifiableMap(map);
  }

  // helper function
  private static Scalar requireNumeric(Scalar scalar) {
    if (scalar instanceof StringScalar)
      throw TensorRuntimeException.of(scalar);
    return scalar;
  }

  // helper function
  /* package */ static Unit format(Entry<String, Scalar> entry) {
    return Unit.of(entry.getKey() + Unit.POWER_DELIMITER + entry.getValue());
  }

  @Override
  public String toString() {
    return String.format("%s[size=%d]", getClass().getSimpleName(), map().size());
  }
}
