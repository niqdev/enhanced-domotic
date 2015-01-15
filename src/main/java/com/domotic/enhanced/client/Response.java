package com.domotic.enhanced.client;

import java.util.List;

import com.google.common.collect.Lists;

public class Response<T> {
  
  private final List<T> values;
  
  public Response() {
    this.values = Lists.newArrayList();
  }

  public Response(List<T> values) {
    this.values = values;
  }

  public List<T> getValues() {
    return values;
  }
  
}
