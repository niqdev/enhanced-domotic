package com.domotic.enhanced.exception;

public class EnhancedException extends RuntimeException {

  private static final long serialVersionUID = 5010798741487346286L;

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
