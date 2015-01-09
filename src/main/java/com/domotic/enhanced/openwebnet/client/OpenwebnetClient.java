package com.domotic.enhanced.openwebnet.client;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import static com.domotic.enhanced.openwebnet.client.Frame.ACK;
import static com.domotic.enhanced.openwebnet.client.Frame.SESSION_COMMAND;
import static com.domotic.enhanced.openwebnet.client.Frame.SESSION_EVENT;
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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domotic.enhanced.Config;
import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.client.Client;
import com.domotic.enhanced.client.Request;
import com.domotic.enhanced.domain.EClient;
import com.google.common.collect.Lists;

@EClient(OPENWEBNET)
public class OpenwebnetClient extends Client<String> {
  
  private static final Logger log = LoggerFactory.getLogger(OpenwebnetClient.class);

  private final int NUM_THREAD = 5;
  private final ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
  
  public OpenwebnetClient(Request<String> request) {
    super(request);
  }

  @Override
  public void run() {
    request.getHandler().validate(request.getValues());
    final Config config = request.getConfig();

    try (Socket socket = new Socket(config.host(), config.port());
      BufferedReader reader = new BufferedReader(new InputStreamReader(
        socket.getInputStream()));
      PrintWriter writer = new PrintWriter(new OutputStreamWriter(
        socket.getOutputStream()), true)) {

      handshake(reader, writer);
      
      // TODO return response
      executeAll(writer);
      // TODO
      request.getHandler().onSuccess(Lists.<String>newArrayList());

    } catch (IOException | InterruptedException | ExecutionException e) {
      log.error("client", e);
      request.getHandler().onError(e);
    }
  }
  
  private void handshake(Reader reader, Writer writer)
      throws InterruptedException, ExecutionException {

    Future<String> firstPart = executor.submit(new ReadTask(reader));
    Future<Void> secondPart = executor.submit(new WriteTask(writer, channel()));
    Future<String> thirdPart = executor.submit(new ReadTask(reader));

    // TODO timeout
    // TODO @return List<String>
    checkArgument(StringUtils.equals(firstPart.get(), ACK.val()));
    secondPart.get();
    checkArgument(StringUtils.equals(thirdPart.get(), ACK.val()));
  }
  
  // TODO return response
  private void executeAll(Writer writer)
      throws InterruptedException, ExecutionException {
    
    // TODO multimap: pair of write/read future
    ArrayList<Future<Void>> futures = Lists.<Future<Void>> newArrayList();
    for (String value : request.getValues()) {
      futures.add(executor.submit(new WriteTask(writer, value)));
      // TODO add ReadTask for each WriteTask?
    }
    for (Future<Void> future : futures) {
      future.get();
    }
  }
  
  private String channel() {
    switch (getType()) {
    case COMMAND:
      return SESSION_COMMAND.val();
    case STATUS:
      return SESSION_EVENT.val();
    default:
      throw new DomoticException("TODO");
    }
  }

}
