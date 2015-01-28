package com.domotic.enhanced.domain;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.EnumSet;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableBiMap.Builder;

public enum Protocol {
  
  OPENWEBNET("com.domotic.enhanced.protocol.openwebnet");
  
  private static final BiMap<String, Protocol> biMap;
  static {
    Builder<String, Protocol> builder = ImmutableBiMap.builder();
    for (Protocol protocol : EnumSet.allOf(Protocol.class)) {
      builder.put(protocol.name(), protocol);
    }
    biMap = builder.build();
  }
  
  private final String path;
  
  private Protocol(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
  
  /*
   * 
   */
  
  public static Protocol stringToEnum(String value) {
    return biMap.get(checkNotNull(value));
  }
  
  public static String enumToString(Protocol protocol) {
    return biMap.inverse().get(checkNotNull(protocol));
  }
  
  public static boolean contains(String value) {
    return value != null && biMap.containsKey(value);
  }
  
  public static boolean contains(Protocol protocol) {
    return protocol != null && biMap.inverse().containsKey(protocol);
  }

}
