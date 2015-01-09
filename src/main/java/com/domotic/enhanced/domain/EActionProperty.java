package com.domotic.enhanced.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EActionProperty {
  
  ActionPropertyType value();
  
  public enum ActionPropertyType {
    BLINK,
    DIMER,
    SPEED,
    TIMED
  }

}
