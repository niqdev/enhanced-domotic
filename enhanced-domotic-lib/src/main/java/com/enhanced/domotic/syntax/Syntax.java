package com.enhanced.domotic.syntax;

import java.util.List;
import java.util.Map;

import com.enhanced.domotic.domain.EAction.ActionType;
import com.enhanced.domotic.domain.EActionProperty.ActionPropertyType;
import com.enhanced.domotic.domain.EDevice.DeviceType;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;
import com.google.common.collect.Maps;

public class Syntax<T> {
  
  private Syntax() {
    this.actionProperties = Maps.newHashMap();
    this.deviceProperties = Maps.newHashMap();
  }
  
  public static <T> Syntax<T> init() {
    return new Syntax<T>();
  }
  
  public ActionType actionType;
  public Map<ActionPropertyType, List<?>> actionProperties;
  public DeviceType deviceType;
  public Map<DevicePropertyType, List<?>> deviceProperties;

}
