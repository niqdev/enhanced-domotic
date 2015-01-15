package com.domotic.enhanced.client;

public interface Handler<T> {
  
  void onValidation(Request<T> request);
  
  void onSuccess(Request<T> request, Response<T> response);
  
  void onError(Exception e);

}
