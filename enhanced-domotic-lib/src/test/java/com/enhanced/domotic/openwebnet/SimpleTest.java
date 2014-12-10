package com.enhanced.domotic.openwebnet;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ALL;

import org.junit.Before;
import org.junit.Test;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedDomotic;

public class SimpleTest {

  private Config config;

  @Before
  public void setUp() throws Exception {
    config = new OpenwebnetConfig();
  }

  @Test
  public void run() {
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ALL)
      .execute();
  }

}
