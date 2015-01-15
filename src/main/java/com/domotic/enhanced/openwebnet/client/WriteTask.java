package com.domotic.enhanced.openwebnet.client;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.Writer;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteTask implements Callable<Void> {
  
  private static final Logger log = LoggerFactory.getLogger(WriteTask.class);

  private final Writer writer;
  private final String value;

  public WriteTask(Writer writer, String value) {
    checkArgument(StringUtils.isNoneBlank(value));
    this.writer = writer;
    this.value = value;
  }

  @Override
  public Void call() throws Exception {
    log.debug("WRITE frame [{}]", value);
    writer.write(value);
    writer.flush();
    return null;
  }
  
}
