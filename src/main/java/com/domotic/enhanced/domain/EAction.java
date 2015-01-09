package com.domotic.enhanced.domain;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EAction {
  
  ActionType value();
  
  public enum ActionType {
    TURN_ON,
    TURN_OFF,
    OPEN,
    CLOSE
  }

}
