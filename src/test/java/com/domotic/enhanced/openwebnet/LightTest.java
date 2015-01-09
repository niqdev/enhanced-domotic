package com.domotic.enhanced.openwebnet;

import static com.domotic.enhanced.domain.EAction.ActionType.TURN_OFF;
import static com.domotic.enhanced.domain.EAction.ActionType.TURN_ON;
import static com.domotic.enhanced.domain.EDevice.DeviceType.LIGHT;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ENVIRONMENT;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ID;
import static com.domotic.enhanced.domain.EDomotic.SyntaxType.COMMAND;
import static org.junit.Assert.*;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.*;
import org.junit.Before;
import org.junit.Test;

import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.EnhancedDomotic;

public class LightTest {

  private EnhancedDomotic<String> domotic;

  @Before
  public void setUp() throws Exception {
    domotic = EnhancedDomotic.<String>config(new OpenwebnetConfig());
  }

  @Test
  public void turnOnAll() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ALL)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*1*0##", commands.get(0));
  }
  
  @Test
  public void turnOffAll() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(ALL)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*0*0##", commands.get(0));
  }
  
  @Test
  public void turnOnById() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 11)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*1*11##", commands.get(0));
  }
  
  @Test
  public void turnOnByIds() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 41, 11, 31)
      .val();
    
    assertTrue("3 values expected", size(commands) == 3);
    assertEquals("invalid commmand", "*1*1*41##", commands.get(0));
    assertEquals("invalid commmand", "*1*1*11##", commands.get(1));
    assertEquals("invalid commmand", "*1*1*31##", commands.get(2));
  }
  
  @Test(expected = DomoticException.class)
  public void invalidIds() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 8, 1000)
      .val();
  }
  
  @Test
  public void turnOffGroup() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(GROUP, 2)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*0*#2##", commands.get(0));
  }
  
  @Test
  public void turnOffGroups() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(GROUP, 3, 1, 2)
      .val();
    
    assertTrue("3 values expected", size(commands) == 3);
    assertEquals("invalid commmand", "*1*0*#3##", commands.get(0));
    assertEquals("invalid commmand", "*1*0*#1##", commands.get(1));
    assertEquals("invalid commmand", "*1*0*#2##", commands.get(2));
  }
  
  @Test(expected = DomoticException.class)
  public void invalidGroups() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(GROUP, 10, -2)
      .val();
  }
  
  @Test
  public void turnOnEnvironment() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ENVIRONMENT, 2)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*1*2##", commands.get(0));
  }
  
  @Test
  public void turnOffEnvironments() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(ENVIRONMENT, 2, 6, 9)
      .val();
    
    assertTrue("3 values expected", size(commands) == 3);
    assertEquals("invalid commmand", "*1*0*2##", commands.get(0));
    assertEquals("invalid commmand", "*1*0*6##", commands.get(1));
    assertEquals("invalid commmand", "*1*0*9##", commands.get(2));
  }
  
  @Test(expected = DomoticException.class)
  public void invalidEnvironments() {
    domotic
      .type(COMMAND)
      .action(TURN_OFF)
      .device(LIGHT)
      .deviceProperty(ENVIRONMENT, 0, -4, 11)
      .val();
  }
}
