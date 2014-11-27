package com.domotic.enhanced.config;

import com.domotic.enhanced.domain.Protocol;

public interface DomoticConfig {

  Protocol getProtocol();

  String getHost();

  Integer getPort();

}
