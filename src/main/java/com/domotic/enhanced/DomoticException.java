package com.domotic.enhanced;

public class DomoticException extends RuntimeException {

  private static final long serialVersionUID = -3466045251238874205L;

  public DomoticException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomoticException(String message) {
    super(message);
  }

  public DomoticException(Throwable cause) {
    super(cause);
  }

}
