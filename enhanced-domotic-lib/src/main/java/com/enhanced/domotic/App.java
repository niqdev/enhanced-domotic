package com.enhanced.domotic;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.DIMER;
import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.TIMED;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;

public class App {
  
  public static void main(String[] args) {
    
    // TODO singleton
    Config config = new AppConfig();
    
    // TODO junit test!!
    EnhancedDomotic
      .config(config)
      .action(TURN_ON)
      .actionProperty(DIMER, 80)
      .actionProperty(TIMED)
      .device(LIGHT)
      .deviceProperty(ID, 13, 14)
      .deviceProperty(ID, 11, 12, 1)
      .deviceProperty(GROUP, 2, 33, 5)
      .execute();
  }

}
