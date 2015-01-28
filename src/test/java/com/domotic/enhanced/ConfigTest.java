package com.domotic.enhanced;

import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {
  
  @Test
  public void validHost() {
    Assert.assertTrue("invalid host", Config.Validator.isValidHost("0.0.0.0"));
    Assert.assertTrue("invalid host", Config.Validator.isValidHost("192.168.1.41"));
  }
  
  @Test
  public void invalidHost() {
    Assert.assertFalse("invalid host", Config.Validator.isValidHost(null));
    Assert.assertFalse("invalid host", Config.Validator.isValidHost(""));
    Assert.assertFalse("invalid host", Config.Validator.isValidHost("host"));
  }
  
  @Test
  public void validPort() {
    Assert.assertTrue("invalid port", Config.Validator.isValidPort(0));
    Assert.assertTrue("invalid port", Config.Validator.isValidPort(20000));
  }
  
  @Test
  public void invalidPort() {
    Assert.assertFalse("invalid port", Config.Validator.isValidPort(null));
    Assert.assertFalse("invalid port", Config.Validator.isValidPort(-1));
    Assert.assertFalse("invalid port", Config.Validator.isValidPort(65536));
  }

}
