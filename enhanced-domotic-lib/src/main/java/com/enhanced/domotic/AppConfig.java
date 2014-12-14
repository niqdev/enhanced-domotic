package com.enhanced.domotic;

import static com.enhanced.domotic.domain.Protocol.OPENWEBNET;

import com.enhanced.domotic.domain.Protocol;

public class AppConfig implements Config {

  @Override
  public Protocol protocol() {
    return OPENWEBNET;
  }

  @Override
  public String host() {
    return "192.168.1.41";
  }

  @Override
  public Integer port() {
    return 20000;
  }

}
