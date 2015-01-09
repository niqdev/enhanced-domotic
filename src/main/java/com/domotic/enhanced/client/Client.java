package com.domotic.enhanced.client;

import com.domotic.enhanced.domain.EDomotic.SyntaxType;
import com.domotic.enhanced.openwebnet.client.Handler;

public abstract class Client<T> implements Runnable {
  
  protected final Request<T> request;

  public Client(Request<T> request) {
    this.request = request;
  }
  
  public SyntaxType getType() {
    return request.getSyntax().syntaxType;
  }

  public Handler<T> handler() {
    return request.getHandler();
  }
  
}
