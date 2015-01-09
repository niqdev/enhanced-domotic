package com.domotic.enhanced.openwebnet.client;

import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadTask implements Callable<String> {
  
  private static final Logger log = LoggerFactory.getLogger(ReadTask.class);

  private final Reader reader;

  public ReadTask(Reader reader) {
    this.reader = reader;
  }

  @Override
  public String call() throws Exception {
    // TODO read multiple frame
    String frame = readFrame(reader);
    log.debug("READ frame [{}]", frame);
    return frame;
  }

  /**
   * TODO @return List<String>
   */
  @SuppressWarnings("unused")
  private String readFrame(Reader reader) throws IOException {
    char[] inputChar = new char[1];
    int charRead;
    StringBuilder frame = new StringBuilder();

    while ((charRead = reader.read(inputChar, 0, 1)) != -1) {
      frame.append(inputChar);
      if (StringUtils.endsWith(frame, "##")) {
        return new String(frame);
      }
    }
    return "";
  }

}
