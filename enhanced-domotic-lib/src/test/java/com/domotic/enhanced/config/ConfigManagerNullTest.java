package com.domotic.enhanced.config;

import org.junit.Test;

import com.domotic.enhanced.exception.EnhancedException;

public class ConfigManagerNullTest {

  @Test(expected = EnhancedException.class)
  public void nullConfig() {
    ConfigManager.getConfig();
  }

}
