package com.domotic.enhanced.openwebnet;

import static com.domotic.enhanced.domain.EAction.ActionType.TURN_ON;
import static com.domotic.enhanced.domain.EActionProperty.ActionPropertyType.DIMER;
import static com.domotic.enhanced.domain.EDevice.DeviceType.LIGHT;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ENVIRONMENT;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ID;
import static com.domotic.enhanced.domain.EDomotic.SyntaxType.COMMAND;
import static org.apache.commons.collections4.CollectionUtils.size;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.EnhancedDomotic;

public class LightOptionalTest {

  private EnhancedDomotic<String> domotic;

  @Before
  public void setUp() throws Exception {
    domotic = EnhancedDomotic.<String>config(new OpenwebnetConfig());
  }

  @Test
  public void dimer() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .actionProperty(DIMER, 80)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*8*21##", commands.get(0));
  }
  
  @Test(expected = DomoticException.class)
  public void tooManyDimer() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .actionProperty(DIMER, 80, 90)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .val();
  }
  
  @Test(expected = DomoticException.class)
  public void invalidDimer() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .actionProperty(DIMER, 1)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .val();
  }
  
  @Test
  public void skipInvalidDimer() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .actionProperty(DIMER, 1, 2, 33, 4, 5)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .val();
    
    assertTrue("only 1 value expected", size(commands) == 1);
    assertEquals("invalid commmand", "*1*3*21##", commands.get(0));
  }
  
  @Test
  public void multipleDimer() {
    List<String> commands = domotic
      .type(COMMAND)
      .action(TURN_ON)
      .actionProperty(DIMER, 56)
      .device(LIGHT)
      .deviceProperty(ID, 21, 22, 23, -4)
      .deviceProperty(GROUP, 3, 4, 99)
      .deviceProperty(ENVIRONMENT, 2, 50, 1000)
      .val();
    
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
