package com.domotic.enhanced.protocol.openwebnet.client;

/**
 * Common OpenWebNet frame.
 */
public enum Frame {

  ACK("*#*1##"),
  NACK("*#*0##"),
  SESSION_COMMAND("*99*0##"),
  SESSION_EVENT("*99*1##");

  private final String val;

  private Frame(String val) {
    this.val = val;
  }

  public String val() {
    return val;
  }

}
