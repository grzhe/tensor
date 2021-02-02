// code by jph
package ch.ethz.idsc.tensor.opt.nd;

import java.util.Collection;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.sca.Chop;

public class NdDualMap<V> implements NdMap<V> {
  private final NdTreeMap<V> ndTreeMap;
  private final NdListMap<V> ndListMap;

  public NdDualMap(Tensor lbounds, Tensor ubounds, int maxDensity, int maxDepth) {
    ndTreeMap = new NdTreeMap<>(lbounds, ubounds, maxDensity, maxDepth);
    ndListMap = new NdListMap<>();
  }

  @Override // from NdMap
  public void add(Tensor location, V value) {
    ndTreeMap.add(location, value);
    ndListMap.add(location, value);
  }

  @Override // from NdMap
  public int size() {
    return ndTreeMap.size();
  }

  @Override // from NdMap
  public Collection<NdEntry<V>> cluster(NdCenterInterface ndCenter, int limit) {
    Collection<NdEntry<V>> c1 = ndTreeMap.cluster(ndCenter, limit);
    Collection<NdEntry<V>> c2 = ndListMap.cluster(ndCenter, limit);
    {
      Scalar s1 = c1.stream().sorted(NdEntryComparators.INCREASING).map(NdEntry::distance).reduce(Scalar::add).get();
      Scalar s2 = c2.stream().sorted(NdEntryComparators.INCREASING).map(NdEntry::distance).reduce(Scalar::add).get();
      Chop.NONE.requireClose(s1, s2);
    }
    return c1;
  }

  @Override // from NdMap
  public void clear() {
    ndTreeMap.clear();
    ndListMap.clear();
  }

  @Override // from NdMap
  public boolean isEmpty() {
    return size() == 0;
  }
}
