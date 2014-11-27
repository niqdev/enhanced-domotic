package com.domotic.enhanced.config;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;

import com.domotic.enhanced.exception.EnhancedException;

public class ConfigManager {

  private static DomoticConfig config;

  public static void initConfig(Class<? extends DomoticConfig> type) {
    synchronized (DomoticConfig.class) {
      try {
        config = type.newInstance();
      } catch (InstantiationException | IllegalAccessException e) {
        throw new EnhancedException("unable to instantiate class");
      }
    }
  }

  public static DomoticConfig getConfig() {
    if (config != null) {
      validateConfig();
      return config;
    }
    throw new EnhancedException("config is null: invoked before initialization");
  }

  private static void validateConfig() {
    if (config.getProtocol() == null) {
      throw new EnhancedException("protocol is null");
    }
    if (StringUtils.isBlank(config.getHost())) {
      throw new EnhancedException("host is whitespace, empty or null");
    }
    if (!Range.between(1024, 65535).contains(config.getPort())) {
      throw new EnhancedException("port not in range 1024-65535 inclusive only");
    }
  }

}
