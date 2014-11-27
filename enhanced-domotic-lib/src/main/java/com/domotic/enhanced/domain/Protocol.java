package com.domotic.enhanced.domain;

public enum Protocol {

  OPENWEBNET("com.domotic.enhanced.protocol.openwebnet");

  private String defaultPackage;

  private Protocol(String defaultPackage) {
    this.defaultPackage = defaultPackage;
  }

  public String getDefaultPackage() {
    return defaultPackage;
  }

}
