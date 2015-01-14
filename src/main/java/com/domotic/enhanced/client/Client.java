package com.domotic.enhanced.client;

import java.util.List;

import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.domain.EDomotic.SyntaxType;
import com.google.common.collect.Lists;

public abstract class Client<T> implements Runnable {
  
  private boolean DEBUG = false;
  
  protected final Request<T> request;

  public Client(Request<T> request) {
    this.request = request;
  }
  
  @Override
  public final void run() {
    try {
      onValidation(request.getValues());
      if (DEBUG) {
        onSuccess(executeMock());
      } else {
        onSuccess(execute());
      }
    } catch (Exception e) {
      onError(e);
    }
  }

  protected abstract List<T> execute();
  
  private List<T> executeMock() {
    try {
      Thread.sleep(5*1000);// 5 seconds
      return Lists.newArrayList();
    } catch (InterruptedException e) {
      throw new DomoticException(e);
    }
  }
  
  protected void onValidation(List<T> values) {
    request.getHandler().onValidation(values);
  }
  
  protected void onSuccess(List<T> values){
    request.getHandler().onSuccess(values);
  }
  
  protected void onError(Exception e) {
    request.getHandler().onError(e);
  }
  
  protected SyntaxType getType() {
    return request.getSyntax().syntaxType;
  }
  
}
