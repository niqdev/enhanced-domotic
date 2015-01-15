package com.domotic.enhanced.protocol.openwebnet.syntax;

import static com.domotic.enhanced.domain.EDevice.DeviceType.LIGHT;
import static com.domotic.enhanced.domain.EDevice.DeviceType.RADIO;

import com.domotic.enhanced.domain.EDevice;
import com.domotic.enhanced.syntax.Device;

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
