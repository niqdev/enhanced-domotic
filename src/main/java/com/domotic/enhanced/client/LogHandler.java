package com.domotic.enhanced.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHandler<T> implements Handler<T> {

  private final Logger log = LoggerFactory.getLogger(LogHandler.class);

  @Override
  public void onValidation(Request<T> request) {
    // TODO
    log.debug("onValidation");
  }

  @Override
  public void onSuccess(Request<T> request, Response<T> response) {
    // TODO
    log.debug("onSuccess");
  }

  @Override
  public void onError(Exception e) {
    // TODO
    log.error("onError", e);
  }

}
