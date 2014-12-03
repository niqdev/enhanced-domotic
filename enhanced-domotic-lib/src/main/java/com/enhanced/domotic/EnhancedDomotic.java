package com.enhanced.domotic;

import static com.enhanced.domotic.domain.EDomotic.SyntaxType.COMMAND;
import static com.enhanced.domotic.domain.EDomotic.SyntaxType.STATUS;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enhanced.domotic.command.Syntax;
import com.enhanced.domotic.domain.EAction.ActionType;
import com.enhanced.domotic.domain.EActionProperty.ActionPropertyType;
import com.enhanced.domotic.domain.EDevice.DeviceType;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;

public class EnhancedDomotic<T> {
  
  private static final Logger log = LoggerFactory.getLogger(EnhancedDomotic.class);
  
  private final Domotics<T> domotics;
  private final Syntax<T> syntax;
  
  private EnhancedDomotic(Config config) {
    this.domotics = Domotics.getInstance(config);
    this.syntax = Syntax.init();
  }
  
  /**
   * @see com.enhanced.domotic.Config
   */
  public static <T> EnhancedDomotic<T> config(Config config) {
    return new EnhancedDomotic<T>(checkNotNull(config));
  }
  
  /**
   * The action to be computed (WHO).
   * <p>This field is mandatory.
   * 
   * @see com.enhanced.domotic.domain.EAction.ActionType
   */
  public EnhancedDomotic<T> action(ActionType actionType) {
    this.syntax.actionType = checkNotNull(actionType);
    return this;
  }
  
  /**
   * Additional property of the action (HOW).
   * {@link ActionPropertyType} of the same are overridden.
   * <p>This field is optional.
   * 
   * @see com.enhanced.domotic.domain.EActionProperty.ActionPropertyType
   */
  @SuppressWarnings("unchecked")
  public <V> EnhancedDomotic<T> actionProperty(ActionPropertyType property, V... values) {
    this.syntax.actionProperties.put(checkNotNull(property), Domotics.newSafeList(values));
    return this;
  }
  
  /**
   * The device on which the action takes effect (WHAT).
   * <p>This field is mandatory.
   * 
   * @see com.enhanced.domotic.domain.EDevice.DeviceType
   */
  public EnhancedDomotic<T> device(DeviceType deviceType) {
    this.syntax.deviceType = checkNotNull(deviceType);
    return this;
  }
  
  /**
   * Specify additional information on the device (WHERE).
   * <p>This field is optional.
   * 
   * @see com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType
   */
  @SuppressWarnings("unchecked")
  public <V> EnhancedDomotic<T> deviceProperty(DevicePropertyType property, V... values) {
    this.syntax.deviceProperties.put(checkNotNull(property), Domotics.newSafeList(values));
    return this;
  }
  
  /**
   * @return Request command implementation.
   */
  public List<T> command() {
    return domotics.build(COMMAND, syntax);
  }
  
  /**
   * @return Request status implementation.
   */
  public List<T> status() {
    return domotics.build(STATUS, syntax);
  }
  
  /**
   * Executes all the command right now (WHEN).
   */
  public void execute() {
    // TODO
    List<T> command = command();
    log.debug(command.toString());
  }

}
