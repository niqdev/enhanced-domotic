package com.domotic.enhanced.client;

import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.domain.EDomotic.SyntaxType;

public abstract class Client<T> implements Runnable {
  
  private boolean DEBUG = false;
  
  private final Request<T> request;
  private final Handler<T> handler;

  public Client(Request<T> request, Handler<T> handler) {
    this.request = request;
    this.handler = defaultHandler(handler);
  }
  
  private Handler<T> defaultHandler(Handler<T> handler) {
    if (handler == null) {
      return new LogHandler<T>();
    }
    return handler;
  }
  
  @Override
  public final void run() {
    try {
      handler.onValidation(request);
      Response<T> response;
      if (DEBUG) {
        response = executeMock();
      } else {
        response = execute();
      }
      handler.onSuccess(request, response);
    } catch (Exception e) {
      handler.onError(e);
    }
  }

  protected abstract Response<T> execute();
  
  private Response<T> executeMock() {
    try {
      Thread.sleep(5*1000);// 5 seconds
      return new Response<T>();
    } catch (InterruptedException e) {
      throw new DomoticException(e);
    }
  }
  
  public Request<T> request() {
    return request;
  }

  public Handler<T> handler() {
    return handler;
  }

  protected SyntaxType type() {
    return request.getSyntax().syntaxType;
  }
  
}
