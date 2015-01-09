package com.domotic.enhanced.syntax;

import com.domotic.enhanced.domain.EDevice.DeviceType;

public interface Action<T> {
  
  T val(DeviceType device);

}
