package com.enhanced.domotic.openwebnet;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;

import org.junit.Before;
import org.junit.Test;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedDomotic;
import com.enhanced.domotic.EnhancedException;

public class SyntaxTest {

  private Config config;

  @Before
  public void setUp() throws Exception {
    config = new OpenwebnetConfig();
  }

  @Test(expected = NullPointerException.class)
  public void missingAction() {
    EnhancedDomotic
      .<String>config(config)
      .device(LIGHT)
      .command();
  }
  
  @Test(expected = NullPointerException.class)
  public void missingDevice() {
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .command();
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void missingDeviceProperty() {
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .command();
  }
  
  @Test(expected = EnhancedException.class)
  public void invalidAllValue() {
    // IllegalArgumentException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ALL, 0)
      .command();
  }
  
  @Test(expected = EnhancedException.class)
  public void missingIdValue() {
    // IllegalArgumentException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID)
      .command();
  }
  
  @Test(expected = EnhancedException.class)
  public void invalidIdValue() {
    // ClassCastException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, "1")
      .command();
  }
  
  @Test(expected = EnhancedException.class)
  public void missingGroupValue() {
    // IllegalArgumentException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(GROUP)
      .command();
  }

}
