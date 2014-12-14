package com.enhanced.domotic.client.openwebnet;

import static ch.lambdaj.Lambda.forEach;
import static com.enhanced.domotic.client.openwebnet.Frame.ACK;
import static com.enhanced.domotic.client.openwebnet.Frame.SESSION_COMMAND;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedException;
import com.google.common.collect.Lists;

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
    try (
      Socket socket = new Socket(config.host(), config.port());
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true)
    ) {
      
      // TODO command or status
      handshake(reader, writer, SESSION_COMMAND);
      executeAll(writer);
      
    } catch (IOException | InterruptedException | ExecutionException e) {
      log.error("openwebnet command", e);
      throw new EnhancedException(e);
    }
  }
  
  private void handshake(Reader reader, Writer writer, Frame channelType)
      throws InterruptedException, ExecutionException {
    
    Future<String> firstPart = executor.submit(new ReadTask(reader));
    Future<Void> secondPart = executor.submit(new WriteTask(writer, channelType.val()));
    Future<String> thirdPart = executor.submit(new ReadTask(reader));
    
    // TODO timeout
    checkArgument(StringUtils.equals(firstPart.get(), ACK.val()));
    secondPart.get();
    checkArgument(StringUtils.equals(thirdPart.get(), ACK.val()));
  }
  
  private void executeAll(Writer writer) throws InterruptedException, ExecutionException {
    ArrayList<Future<Void>> request = Lists.<Future<Void>>newArrayList();
    for (String value : values) {
      request.add(executor.submit(new WriteTask(writer, value)));
    }
    forEach(request).get();
  }

}
