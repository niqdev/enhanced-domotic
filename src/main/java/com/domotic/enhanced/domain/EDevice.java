package com.domotic.enhanced.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EDevice {
  
  DeviceType value();
  
  public enum DeviceType {
    LIGHT,
    RADIO,
    GATE
  }

}
