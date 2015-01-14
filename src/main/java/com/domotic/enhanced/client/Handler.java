package com.domotic.enhanced.client;

import java.util.List;

public interface Handler {
  
  void onValidation(List<?> values);
  
  void onSuccess(List<?> values);
  
  void onError(Exception e);
  
  // TODO converter with Function/Iterable

}
