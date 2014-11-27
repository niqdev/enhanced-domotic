package com.domotic.enhanced;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;

import com.domotic.enhanced.config.DomoticConfig;
import com.domotic.enhanced.domain.Protocol;

public class OpenwebnetConfig implements DomoticConfig {

  @Override
  public Protocol getProtocol() {
    return OPENWEBNET;
  }

  @Override
  public String getHost() {
    return "192.168.1.41";
  }

  @Override
  public Integer getPort() {
    return 20000;
  }

}
