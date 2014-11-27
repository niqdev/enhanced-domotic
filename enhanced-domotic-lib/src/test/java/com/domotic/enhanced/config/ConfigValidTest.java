package com.domotic.enhanced.config;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;

import com.domotic.enhanced.domain.Protocol;

public class ConfigValidTest implements DomoticConfig {

  @Override
  public Protocol getProtocol() {
    return OPENWEBNET;
  }

  @Override
  public String getHost() {
    return "0.0.0.0";
  }

  @Override
  public Integer getPort() {
    return 20000;
  }

}
