package com.domotic.enhanced.protocol.openwebnet.syntax;

import static com.domotic.enhanced.domain.EAction.ActionType.TURN_OFF;
import static com.domotic.enhanced.domain.EAction.ActionType.TURN_ON;
import static com.domotic.enhanced.domain.EDevice.DeviceType.LIGHT;
import static com.domotic.enhanced.domain.EDevice.DeviceType.RADIO;

import java.util.Map;

import com.domotic.enhanced.domain.EAction;
import com.domotic.enhanced.domain.EDevice.DeviceType;
import com.domotic.enhanced.syntax.Action;
import com.google.common.collect.ImmutableMap;

public class OpenwebnetAction {
  
  private OpenwebnetAction() {}
  
  @EAction(TURN_ON)
  public static Action<String> turnOn() {
    return new Action<String>() {
      
      private final Map<DeviceType, String> TURN_ON =
        ImmutableMap.<DeviceType, String>builder()
          .put(LIGHT, "1")
          .put(RADIO, "3")
          .build();

      @Override
      public String val(DeviceType device) {
        return TURN_ON.get(device);
      }
    };
  }
  
  @EAction(TURN_OFF)
  public static Action<String> turnOff() {
    return new Action<String>() {
      
      private final Map<DeviceType, String> TURN_OFF =
        ImmutableMap.<DeviceType, String>builder()
          .put(LIGHT, "0")
          .put(RADIO, "13")
          .build();

      @Override
      public String val(DeviceType device) {
        return TURN_OFF.get(device);
      }
    };
  }
  
}
