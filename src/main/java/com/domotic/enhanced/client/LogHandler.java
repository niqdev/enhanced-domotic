package com.domotic.enhanced.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHandler implements Handler {

  private final Logger log = LoggerFactory.getLogger(LogHandler.class);

  @Override
  public void onValidation(List<?> values) {
    log.debug("handler validate: {}", values);
  }

  @Override
  public void onSuccess(List<?> values) {
    log.debug("handler onSuccess: {}", values);
  }

  @Override
  public void onError(Exception e) {
    log.error("handler onError", e);
  }

}
