package com.domotic.enhanced.domain;

public enum Protocol {
  
  OPENWEBNET("com.domotic.enhanced.openwebnet");
  
  private final String path;
  
  private Protocol(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

}
