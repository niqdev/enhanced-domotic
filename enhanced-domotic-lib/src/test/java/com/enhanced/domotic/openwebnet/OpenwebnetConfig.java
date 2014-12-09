package com.enhanced.domotic.openwebnet;

import static com.enhanced.domotic.domain.Protocol.OPENWEBNET;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.domain.Protocol;

public class OpenwebnetConfig implements Config {

  @Override
  public Protocol protocol() {
    return OPENWEBNET;
  }

  @Override
  public String host() {
    return "0.0.0.0";
  }

  @Override
  public Integer port() {
    return 20000;
  }

  

}
