package com.enhanced.domotic;

public class EnhancedException extends RuntimeException {

  private static final long serialVersionUID = -5085430167658294483L;

  public EnhancedException(String message, Throwable cause) {
    super(message, cause);
  }

  public EnhancedException(String message) {
    super(message);
  }

  public EnhancedException(Throwable cause) {
    super(cause);
  }

}
