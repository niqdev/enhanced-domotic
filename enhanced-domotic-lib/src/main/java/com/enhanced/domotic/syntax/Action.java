package com.enhanced.domotic.syntax;

import com.enhanced.domotic.domain.EDevice.DeviceType;

public interface Action<T> {
  
  T val(DeviceType device);

}
