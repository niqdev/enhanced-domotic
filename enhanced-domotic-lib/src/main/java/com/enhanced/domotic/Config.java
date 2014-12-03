package com.enhanced.domotic;

import com.enhanced.domotic.domain.Protocol;

public interface Config {
  
  Protocol protocol();
  
  String host();
  
  Integer port();

}
