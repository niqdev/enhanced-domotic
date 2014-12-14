package com.enhanced.domotic.openwebnet;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.DIMER;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ENVIRONMENT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;
import static org.apache.commons.collections4.CollectionUtils.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedDomotic;
import com.enhanced.domotic.EnhancedException;

public class LightOptionalTest {

  private Config config;

  @Before
  public void setUp() throws Exception {
    config = new OpenwebnetConfig();
  }

  @Test
  public void dimer() {
    String command = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .actionProperty(DIMER, 80)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .command().get(0);
    
    assertEquals("invalid commmand", "*1*8*21##", command);
  }
  
  @Test(expected = EnhancedException.class)
  public void tooManyDimer() {
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .actionProperty(DIMER, 80, 90)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .command();
  }
  
  @Test(expected = EnhancedException.class)
  public void invalidDimer() {
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .actionProperty(DIMER, 1)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .command();
  }
  
  @Test
  public void skipInvalidDimer() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .actionProperty(DIMER, 1, 2, 33, 4, 5)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .command();
    
    assertTrue("only 1 command", size(commands) == 1);
    assertEquals("invalid commmand", "*1*3*21##", commands.get(0));
  }
  
  @Test
  public void multipleDimer() {
    List<String> commands = EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .actionProperty(DIMER, 56)
      .device(LIGHT)
      .deviceProperty(ID, 21, 22, 23, -4)
      .deviceProperty(GROUP, 3, 4, 99)
      .deviceProperty(ENVIRONMENT, 2, 50, 1000)
      .command();
    
    assertTrue("invalid total commands", size(commands) == 6);
    assertTrue("missing commmand", commands.contains("*1*5*21##"));
    assertTrue("missing commmand", commands.contains("*1*5*22##"));
    assertTrue("missing commmand", commands.contains("*1*5*23##"));
    assertTrue("missing commmand", commands.contains("*1*5*#3##"));
    assertTrue("missing commmand", commands.contains("*1*5*#4##"));
    assertTrue("missing commmand", commands.contains("*1*5*2##"));
  }
  
  /*
   * TODO
   */
  
  @Test @Ignore
  public void blinking() {
    
  }
  
  @Test @Ignore
  public void speed() {

  }
  
  @Test @Ignore
  public void timed() {
    
  }

}
