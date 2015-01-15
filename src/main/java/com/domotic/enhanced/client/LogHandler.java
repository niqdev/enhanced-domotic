package com.domotic.enhanced.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHandler<T> implements Handler<T> {

  private final Logger log = LoggerFactory.getLogger(LogHandler.class);

  @Override
  public void onValidation(Request<T> request) {
    log.debug("onValidation");
  }

  @Override
  public void onSuccess(Request<T> request, Response<T> response) {
    log.debug("onSuccess");
  }

  @Override
  public void onError(Exception e) {
    log.error("onError");
  }

}
