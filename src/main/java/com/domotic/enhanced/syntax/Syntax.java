package com.domotic.enhanced.syntax;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.domotic.enhanced.domain.EAction.ActionType;
import com.domotic.enhanced.domain.EActionProperty.ActionPropertyType;
import com.domotic.enhanced.domain.EDevice.DeviceType;
import com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType;
import com.domotic.enhanced.domain.EDomotic.SyntaxType;
import com.google.common.collect.Maps;

public class Syntax<T> {
  
  private Syntax() {
    this.actionProperties = Maps.newHashMap();
    this.deviceProperties = Maps.newHashMap();
  }
  
  public static <T> Syntax<T> newInstance() {
    return new Syntax<T>();
  }
  
  public SyntaxType syntaxType;
  public ActionType actionType;
  public Map<ActionPropertyType, List<?>> actionProperties;
  public DeviceType deviceType;
  public Map<DevicePropertyType, List<?>> deviceProperties;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
      .append("syntaxType", syntaxType != null ? syntaxType : "undefined")
      .append("actionType", actionType != null ? actionType : "undefined")
      .append("actionProperties", actionProperties != null ? actionProperties : "empty")
      .append("deviceType", deviceType != null ? deviceType : "undefined")
      .append("deviceProperties", deviceProperties != null ? deviceProperties : "empty")
      .toString();
  }
  
  

}
