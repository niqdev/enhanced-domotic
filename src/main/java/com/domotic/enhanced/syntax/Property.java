package com.domotic.enhanced.syntax;

import java.util.List;

public interface Property<T, V> {
  
  List<T> transform(List<V> values);

}
