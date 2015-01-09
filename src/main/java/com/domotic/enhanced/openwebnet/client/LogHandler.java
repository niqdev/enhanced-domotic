package com.domotic.enhanced.openwebnet.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHandler<T> implements Handler<T> {

  private final Logger log = LoggerFactory.getLogger(LogHandler.class);

  @Override
  public void validate(List<T> values) {
    log.debug("handler validate: {}", values);
  }

  @Override
  public void onSuccess(List<T> values) {
    log.debug("handler onSuccess: {}", values);
  }

  @Override
  public void onError(Exception e) {
    log.error("handler onError", e);
  }

}
