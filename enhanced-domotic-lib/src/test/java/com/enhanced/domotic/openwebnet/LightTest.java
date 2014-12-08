package com.enhanced.domotic.openwebnet;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_OFF;
import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedDomotic;
import com.enhanced.domotic.TestConfig;

public class LightTest {

  private Config config;

  @Before
  public void setUp() throws Exception {
    config = new TestConfig();
  }

  @Test
  public void turnOnAll() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ALL)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*1*0##", command);
  }
  
  @Test
  public void turnOffAll() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(ALL)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*0*0##", command);
  }
  
  @Test
  public void turnOnById() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 11)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*1*11##", command);
  }
  
  @Test
  public void turnOnByInvalidId() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 8, 1000)
      .command();
    
    assertTrue("invalid id value", commands.isEmpty());
  }
  
  @Test
  public void turnOnGroup() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(GROUP, 3)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*1*#3##", command);
  }
  
  @Test
  public void turnOnInvalidGroup() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(GROUP, 10, -2)
      .command();
    
    assertTrue("invalid group value", commands.isEmpty());
  }
  
  @Test @Ignore
  public void turnOnInEnvironment() {
    assertEquals("invalid commmand", "*1*1*8##", null);
  }
  
}
