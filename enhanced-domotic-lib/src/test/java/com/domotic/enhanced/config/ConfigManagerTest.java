package com.domotic.enhanced.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.domotic.enhanced.exception.EnhancedException;

public class ConfigManagerTest {

  @Test
  public void notNullConfig() {
    ConfigManager.initConfig(ConfigValidTest.class);
    assertNotNull("config should not be null", ConfigManager.getConfig());
  }

  @Test(expected = EnhancedException.class)
  public void invalidConfig() {
    ConfigManager.initConfig(ConfigInvalidTest.class);
    ConfigManager.getConfig();
  }

}
