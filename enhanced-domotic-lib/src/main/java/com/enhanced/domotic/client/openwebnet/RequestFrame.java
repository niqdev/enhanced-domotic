package com.enhanced.domotic.client.openwebnet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestFrame implements Callable<Boolean> {
  
  private static final Logger log = LoggerFactory.getLogger(RequestFrame.class);
  
  private final Socket socket;
  private final List<String> values;

  public RequestFrame(Socket socket, List<String> values) {
    this.socket = socket;
    this.values = values;
  }

  @Override
  public Boolean call() throws Exception {
    try (PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)) {
      for (String frame : values) {
        printWriter.write(frame);
      }
      printWriter.flush();
      return Boolean.TRUE;
    } catch (IOException e) {
      log.error("request frame error", e);
      return Boolean.FALSE;
    }
  }

}
