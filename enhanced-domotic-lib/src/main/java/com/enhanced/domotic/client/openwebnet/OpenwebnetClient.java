package com.enhanced.domotic.client.openwebnet;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedException;

public class OpenwebnetClient implements Runnable {
  
  private static final Logger log = LoggerFactory.getLogger(OpenwebnetClient.class);
  
  private final ExecutorService executor = Executors.newFixedThreadPool(3);
  private final List<String> values;
  private final Config config;

  private OpenwebnetClient(List<String> values, Config config) {
    this.values = values;
    this.config = config;
  }
  
  @SuppressWarnings("unchecked")
  public static <T> void startThread(List<T> values, Config config) {
    new Thread(new OpenwebnetClient((List<String>) values, config)).start();
  }

  @Override
  public void run() {
    try (Socket socket = new Socket(config.host(), config.port())) {
      
      // TODO command or status
      Future<Boolean> handshake = executor.submit(new Handshake(socket, Frame.SESSION_COMMAND));
      Future<Boolean> request = executor.submit(new RequestFrame(socket, values));
      
      checkArgument(handshake.get());
      checkArgument(request.get());
      
      // TODO handle exception
    } catch (IOException | InterruptedException | ExecutionException e) {
      log.error("openwebnet client error", e);
      throw new EnhancedException(e);
    }
  }

}
