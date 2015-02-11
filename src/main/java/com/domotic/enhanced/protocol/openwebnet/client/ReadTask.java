package com.domotic.enhanced.protocol.openwebnet.client;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class ReadTask implements Callable<List<String>> {
  
  private static final Logger log = LoggerFactory.getLogger(ReadTask.class);

  private final Reader reader;

  public ReadTask(Reader reader) {
    this.reader = reader;
  }

  @Override
  public List<String> call() throws Exception {
    return readFrames();
  }

  private List<String> readFrames() throws IOException {
    ArrayList<String> frames = Lists.newArrayList();
    
    char[] inputChar = new char[1];
    StringBuilder frame = new StringBuilder();

    while (reader.read(inputChar, 0, 1) != -1) {
      frame.append(inputChar);
      //log.debug("reading frame [{}]", frame);
      if (StringUtils.endsWith(frame, "##")) {
        log.debug("READ new frame [{}]", frame);
        frames.add(frame.toString());
      }
    }
    //log.debug("READ all frames [{}]", frames);
    return frames;
  }

}
