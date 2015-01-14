package com.domotic.enhanced.openwebnet.client;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import static com.domotic.enhanced.openwebnet.client.Frame.ACK;
import static com.domotic.enhanced.openwebnet.client.Frame.SESSION_COMMAND;
import static com.domotic.enhanced.openwebnet.client.Frame.SESSION_EVENT;
import static com.google.common.base.Preconditions.checkArgument;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.StringUtils;

import com.domotic.enhanced.Config;
import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.client.Client;
import com.domotic.enhanced.client.Request;
import com.domotic.enhanced.domain.EClient;
import com.google.common.collect.Lists;

@EClient(OPENWEBNET)
public class OpenwebnetClient extends Client<String> {
  
  private final int TIMEOUT = 10*1000; // 10 seconds
  private final int NUM_THREAD = 5;
  private final ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
  
  public OpenwebnetClient(Request<String> request) {
    super(request);
  }
  
  @Override
  protected List<String> execute() {
    try (
      Socket socket = initSocket(request.getConfig(), TIMEOUT);
      BufferedReader reader = initReader(socket);
      PrintWriter writer = initWriter(socket)) {

      handshake(reader, writer);
      
      // TODO return response
      executeAll(writer);
      // TODO response converter
      return Lists.<String>newArrayList();

    } catch (IOException | InterruptedException | ExecutionException | TimeoutException e) {
      throw new DomoticException("client connection failure", e);
    }
  }
  
  private Socket initSocket(Config config, int timeout) throws IOException {
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(config.host(), config.port()), timeout);
    return socket;
  }
  
  private BufferedReader initReader(Socket socket) throws IOException {
    return new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }
  
  private PrintWriter initWriter(Socket socket) throws IOException {
    return new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
  }
  
  private void handshake(Reader reader, Writer writer)
      throws InterruptedException, ExecutionException, TimeoutException {

    Future<String> firstPart = executor.submit(new ReadTask(reader));
    Future<Void> secondPart = executor.submit(new WriteTask(writer, channel()));
    Future<String> thirdPart = executor.submit(new ReadTask(reader));

    checkArgument(StringUtils.equals(firstPart.get(TIMEOUT, MILLISECONDS), ACK.val()));
    secondPart.get(TIMEOUT, MILLISECONDS);
    checkArgument(StringUtils.equals(thirdPart.get(TIMEOUT, MILLISECONDS), ACK.val()));
  }
  
  private String channel() {
    switch (getType()) {
    case COMMAND:
      return SESSION_COMMAND.val();
    case STATUS:
      return SESSION_EVENT.val();
    default:
      throw new DomoticException("invalid channel");
    }
  }
  
  // TODO @return response List<String>
  private void executeAll(Writer writer)
      throws InterruptedException, ExecutionException, TimeoutException {
    
    // TODO multimap: pair of write/read future
    ArrayList<Future<Void>> futures = Lists.<Future<Void>> newArrayList();
    for (String value : request.getValues()) {
      futures.add(executor.submit(new WriteTask(writer, value)));
      // TODO add ReadTask for each WriteTask?
    }
    for (Future<Void> future : futures) {
      future.get(TIMEOUT, MILLISECONDS);
    }
  }
  
}
