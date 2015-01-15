package com.domotic.enhanced.protocol.openwebnet.client;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import static com.domotic.enhanced.protocol.openwebnet.client.Frame.ACK;
import static com.domotic.enhanced.protocol.openwebnet.client.Frame.SESSION_COMMAND;
import static com.domotic.enhanced.protocol.openwebnet.client.Frame.SESSION_EVENT;
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
import com.domotic.enhanced.client.Handler;
import com.domotic.enhanced.client.Request;
import com.domotic.enhanced.client.Response;
import com.domotic.enhanced.domain.EClient;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

@EClient(OPENWEBNET)
public class OpenwebnetClient extends Client<String> {
  
  private final int TIMEOUT = 10*1000; // 10 seconds
  private final int NUM_THREAD = 5;
  private final ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
  
  public OpenwebnetClient(Request<String> request, Handler<String> handler) {
    super(request, handler);
  }
  
  @Override
  protected Response<String> execute() {
    try (
        Socket socket = initSocket(request().getConfig(), TIMEOUT);
        BufferedReader reader = initReader(socket);
        PrintWriter writer = initWriter(socket)) {

        handshake(reader, writer);
        
        //executeAll(writer);
        //return new Response<String>();
        
        // TODO response converter
        return new Response<String>(send(reader, writer));

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
  
  /*
   * OpenWebNet handshake:
   * 
   * server                       client
   *            ---[ACK]--->
   *     <---[*99*0## / *99*1##]---
   *            ---[ACK]--->
   * 
   * then send requests.
   */
  private void handshake(Reader reader, Writer writer)
      throws InterruptedException, ExecutionException, TimeoutException {

    Future<List<String>> firstPart = executor.submit(new ReadTask(reader));
    Future<Void> secondPart = executor.submit(new WriteTask(writer, channel()));
    Future<List<String>> thirdPart = executor.submit(new ReadTask(reader));
    
    String firstPartValue = Iterables.getOnlyElement(firstPart.get(TIMEOUT, MILLISECONDS));
    checkArgument(StringUtils.equals(firstPartValue, ACK.val()));
    secondPart.get(TIMEOUT, MILLISECONDS);
    String thirdPartValue = Iterables.getOnlyElement(thirdPart.get(TIMEOUT, MILLISECONDS));
    checkArgument(StringUtils.equals(thirdPartValue, ACK.val()));
  }
  
  private String channel() {
    switch (type()) {
    case COMMAND:
      return SESSION_COMMAND.val();
    case STATUS:
      return SESSION_EVENT.val();
    default:
      throw new DomoticException("invalid channel");
    }
  }
  
  /*
   * TEST and remove
   */
  @Deprecated
  private void executeAll(Writer writer)
    throws InterruptedException, ExecutionException, TimeoutException {
    
    // TODO multimap: pair of write/read future
    ArrayList<Future<Void>> futures = Lists.<Future<Void>> newArrayList();
    for (String value : request().getValues()) {
      futures.add(executor.submit(new WriteTask(writer, value)));
      // TODO add ReadTask for each WriteTask?
    }
    for (Future<Void> future : futures) {
      future.get(TIMEOUT, MILLISECONDS);
    }
  }
  
  /*
   * COMMAND
   * req: *1*1*21## (accendi luce)
   * res: ACK
   * 
   * STATUS
   * req: *#1*21## (stato luce 21)
   * res: *1*1*21## o *1*0*21##
   * res: ACK
   * 
   * req: *#1*0## (stato luci)
   * res: ...
   * res: ...
   * res: ACK
   * 
   */
  @SuppressWarnings("unchecked")
  protected List<String> send(Reader reader, Writer writer)
    throws InterruptedException, ExecutionException, TimeoutException {
    
    ArrayList<Future<?>> requests = Lists.<Future<?>>newArrayList();
    
    for (String value : request().getValues()) {
      requests.add(executor.submit(new WriteTask(writer, value)));
      requests.add(executor.submit(new ReadTask(reader)));
    }
    
    ArrayList<String> responses = Lists.<String>newArrayList();
    for (Future<?> future : requests) {
      if (future instanceof WriteTask) {
        future.get(TIMEOUT, MILLISECONDS);
      } else {
        // ReadTask
        responses.addAll((List<String>) future.get(TIMEOUT, MILLISECONDS));
      }
    }
    return responses;
  }

}
