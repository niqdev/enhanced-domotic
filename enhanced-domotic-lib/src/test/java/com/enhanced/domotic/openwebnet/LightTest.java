package com.enhanced.domotic.openwebnet;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_OFF;
import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ENVIRONMENT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedDomotic;
import com.enhanced.domotic.EnhancedException;

public class LightTest {

  private Config config;

  @Before
  public void setUp() throws Exception {
    config = new OpenwebnetConfig();
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
  public void turnOnByIds() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 41, 11, 31)
      .command();
    
    assertEquals("invalid commmand", "*1*1*41##", commands.get(0));
    assertEquals("invalid commmand", "*1*1*11##", commands.get(1));
    assertEquals("invalid commmand", "*1*1*31##", commands.get(2));
  }
  
  @Test(expected = EnhancedException.class)
  public void invalidIds() {
    // IllegalArgumentException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 8, 1000)
      .command();
  }
  
  @Test
  public void turnOffGroup() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(GROUP, 2)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*0*#2##", command);
  }
  
  @Test
  public void turnOffGroups() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(GROUP, 3, 1, 2)
      .command();
    
    assertEquals("invalid commmand", "*1*0*#3##", commands.get(0));
    assertEquals("invalid commmand", "*1*0*#1##", commands.get(1));
    assertEquals("invalid commmand", "*1*0*#2##", commands.get(2));
  }
  
  @Test(expected = EnhancedException.class)
  public void invalidGroups() {
    // IllegalArgumentException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(GROUP, 10, -2)
      .command();
  }
  
  @Test
  public void turnOnEnvironment() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ENVIRONMENT, 2)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*1*2##", command);
  }
  
  @Test
  public void turnOffEnvironments() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(ENVIRONMENT, 2, 6, 9)
      .command();
    
    assertEquals("invalid commmand", "*1*0*2##", commands.get(0));
    assertEquals("invalid commmand", "*1*0*6##", commands.get(1));
    assertEquals("invalid commmand", "*1*0*9##", commands.get(2));
  }
  
  @Test(expected = EnhancedException.class)
  public void invalidEnvironments() {
    // IllegalArgumentException
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(ENVIRONMENT, 0, -4, 11)
      .command();
  }
  
}
