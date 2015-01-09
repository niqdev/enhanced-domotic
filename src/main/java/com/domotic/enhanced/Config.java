package com.domotic.enhanced;

import android.content.Context;

import com.domotic.enhanced.domain.Protocol;

public interface Config {

  Protocol protocol();

  String host();

  Integer port();
  
  Context context();

}
