package com.domotic.enhanced.openwebnet;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import android.content.Context;

import com.domotic.enhanced.Config;
import com.domotic.enhanced.domain.Protocol;

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

  @Override
  public Context context() {
    return null;
  }

}
