package com.domotic.enhanced.openwebnet.client;

import java.util.List;

public interface Handler<T> {
  
  void validate(List<T> values);
  
  void onSuccess(List<T> values);
  
  void onError(Exception e);

}
