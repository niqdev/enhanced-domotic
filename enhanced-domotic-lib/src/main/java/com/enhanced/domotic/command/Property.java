package com.enhanced.domotic.command;

import java.util.List;

public interface Property<T, V> {
  
  List<T> transform(List<V> values);

}
