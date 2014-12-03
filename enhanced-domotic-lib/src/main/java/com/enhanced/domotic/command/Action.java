package com.enhanced.domotic.command;

import com.enhanced.domotic.domain.EDevice.DeviceType;

public interface Action<T> {
  
  T val(DeviceType device);

}
