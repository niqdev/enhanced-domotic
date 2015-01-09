package com.domotic.enhanced.openwebnet;

import static com.domotic.enhanced.domain.EAction.ActionType.TURN_ON;
import static com.domotic.enhanced.domain.EDevice.DeviceType.LIGHT;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ID;
import static com.domotic.enhanced.domain.EDomotic.SyntaxType.COMMAND;

import org.junit.Before;
import org.junit.Test;

import com.domotic.enhanced.DomoticException;
import com.domotic.enhanced.EnhancedDomotic;

public class SyntaxTest {

  private EnhancedDomotic<String> domotic;

  @Before
  public void setUp() throws Exception {
    domotic = EnhancedDomotic.<String>config(new OpenwebnetConfig());
  }
  
  @Test(expected = DomoticException.class)
  public void missingType() {
    domotic.val();
  }

  @Test(expected = DomoticException.class)
  public void missingAction() {
    domotic
      .type(COMMAND)
      .val();
  }
  
  @Test(expected = DomoticException.class)
  public void missingDevice() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .val();
  }
  
  @Test(expected = DomoticException.class)
  public void missingDeviceProperty() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .val();
  }
  
  @Test(expected = DomoticException.class)
  public void invalidAllValue() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ALL, 0)
      .val();
  }
  
  @Test(expected = DomoticException.class)
  public void missingIdValue() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID)
      .val();
  }
  
  @Test(expected = DomoticException.class)
  public void invalidIdValue() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, "1")
      .val();
  }

  @Test(expected = DomoticException.class)
  public void missingGroupValue() {
    domotic
      .type(COMMAND)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(GROUP)
      .val();
  }

}
