package com.enhanced.domotic.domain;

public enum Protocol {
  
  OPENWEBNET("com.enhanced.domotic.command.openwebnet");
  
  private String path;

  private Protocol(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

}
