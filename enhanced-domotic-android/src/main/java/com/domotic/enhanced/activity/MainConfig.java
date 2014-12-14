package com.domotic.enhanced.activity;

import static com.enhanced.domotic.domain.Protocol.OPENWEBNET;

import org.androidannotations.annotations.EBean;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.domain.Protocol;

@EBean
public class MainConfig implements Config {

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
