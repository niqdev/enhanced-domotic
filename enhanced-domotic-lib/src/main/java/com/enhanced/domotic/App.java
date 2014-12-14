package com.enhanced.domotic;

import static com.enhanced.domotic.domain.EAction.ActionType.TURN_ON;
import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;

public class App {
  
  public static void main(String[] args) {

    Config config = new AppConfig();
    
    //EnhancedDomotic.<String>execRaw(config, "*1*1*21##");
    
    EnhancedDomotic
      .<String>config(config)
      .action(TURN_ON)
      .device(LIGHT)
      .deviceProperty(ID, 21)
      .execCommand();
    
     // TODO app do not exit ?
  }

}
