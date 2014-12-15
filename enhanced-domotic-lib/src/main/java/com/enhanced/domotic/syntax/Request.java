package com.enhanced.domotic.syntax;

import java.util.List;

import com.enhanced.domotic.Domotics;

public abstract class Request<T> {
  
  private final Domotics<T> domotics;
  private final Syntax<T> syntax;

  public Request(Domotics<T> domotics, Syntax<T> syntax) {
    this.domotics = domotics;
    this.syntax = syntax;
  }
  
  public abstract List<T> build();

  public Domotics<T> domotics() {
    return domotics;
  }

  public Syntax<T> syntax() {
    return syntax;
  }
  
}
