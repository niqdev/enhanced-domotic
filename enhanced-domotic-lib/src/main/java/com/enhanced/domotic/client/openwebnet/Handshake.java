package com.enhanced.domotic.client.openwebnet;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handshake implements Callable<Boolean> {
  
  private static final Logger log = LoggerFactory.getLogger(Handshake.class);

  private final Socket socket;
  private final Frame frame;

  public Handshake(Socket socket, Frame frame) {
    this.socket = socket;
    this.frame = frame;
  }

  @Override
  public Boolean call() throws Exception {
    try (
        PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))
    ) {
      String firstPart = bufferedReader.readLine();
      checkNotNull(firstPart);
      checkArgument(Frame.ACK.val().equals(firstPart), "first part expected ACK");
      
      printWriter.write(frame.val());
      printWriter.flush();
      
      String thirdPart = bufferedReader.readLine();
      checkNotNull(thirdPart);
      checkArgument(Frame.ACK.val().equals(thirdPart), "third part expected ACK");
      
      return Boolean.TRUE;
    } catch (IOException e) {
      log.error("handshake error", e);
      return Boolean.FALSE;
    }
  }

}
