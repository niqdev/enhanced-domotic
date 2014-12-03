package com.enhanced.domotic.command.openwebnet;

import static com.enhanced.domotic.domain.EDevice.DeviceType.LIGHT;
import static com.enhanced.domotic.domain.EDevice.DeviceType.RADIO;

import com.enhanced.domotic.command.Device;
import com.enhanced.domotic.domain.EDevice;

public class OpenwebnetDevice {
  
  private OpenwebnetDevice() {}
  
  @EDevice(LIGHT)
  public static Device<String> light() {
    return new Device<String>() {

      @Override
      public String val() {
        return "1";
      }
    };
  }
  
  @EDevice(RADIO)
  public static Device<String> radio() {
    return new Device<String>() {

      @Override
      public String val() {
        return "16";
      }
    };
  }

}
