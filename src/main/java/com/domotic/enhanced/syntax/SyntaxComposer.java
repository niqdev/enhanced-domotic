package com.domotic.enhanced.syntax;

import java.util.List;

import com.domotic.enhanced.Domotics;

public abstract class SyntaxComposer<T> {
  
  private final Domotics<T> domotics;
  private final Syntax<T> syntax;

  public SyntaxComposer(Domotics<T> domotics, Syntax<T> syntax) {
    this.domotics = domotics;
    this.syntax = syntax;
  }
  
  public abstract List<T> compose();

  public Domotics<T> domotics() {
    return domotics;
  }

  public Syntax<T> syntax() {
    return syntax;
  }

}
