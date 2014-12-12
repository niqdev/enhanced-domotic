package com.enhanced.domotic;

import static com.enhanced.domotic.domain.EDomotic.SyntaxType.COMMAND;
import static com.enhanced.domotic.domain.EDomotic.SyntaxType.STATUS;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enhanced.domotic.domain.EAction.ActionType;
import com.enhanced.domotic.domain.EActionProperty.ActionPropertyType;
import com.enhanced.domotic.domain.EDevice.DeviceType;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;
import com.enhanced.domotic.syntax.Syntax;

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
   * Executes raw requests.
   * 
   * @param config
   * @param values
   */
  @SuppressWarnings("unchecked")
  public static <V> void execRaw(Config config, V... values) {
    config(config).execute((List<Object>) Domotics.newSafeList(values));
  }
  
  /**
   * The action to be computed.
   * 
   * @see com.enhanced.domotic.domain.EAction.ActionType
   */
  public EnhancedDomotic<T> action(ActionType actionType) {
    this.syntax.actionType = checkNotNull(actionType);
    log.debug("ACTION: {}", actionType);
    return this;
  }
  
  /**
   * Additional property of the action.
   * {@link ActionPropertyType} of the same are overridden.
   * 
   * @see com.enhanced.domotic.domain.EActionProperty.ActionPropertyType
   */
  @SuppressWarnings("unchecked")
  public <V> EnhancedDomotic<T> actionProperty(ActionPropertyType property, V... values) {
    this.syntax.actionProperties.put(checkNotNull(property), Domotics.newSafeList(values));
    log.debug("ACTION PROPERTY: {} - {}", property, values);
    return this;
  }
  
  /**
   * The device on which the action takes effect.
   * 
   * @see com.enhanced.domotic.domain.EDevice.DeviceType
   */
  public EnhancedDomotic<T> device(DeviceType deviceType) {
    this.syntax.deviceType = checkNotNull(deviceType);
    log.debug("DEVICE: {}", deviceType);
    return this;
  }
  
  /**
   * Specify additional information on the device.
   * 
   * @see com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType
   */
  @SuppressWarnings("unchecked")
  public <V> EnhancedDomotic<T> deviceProperty(DevicePropertyType property, V... values) {
    this.syntax.deviceProperties.put(checkNotNull(property), Domotics.newSafeList(values));
    log.debug("DEVICE PROPERTY: {} - {}", property, values);
    return this;
  }
  
  /**
   * @return Request command implementation.
   */
  public List<T> command() {
    checkNotNull(this.syntax.actionType, "action can not be null");
    checkNotNull(this.syntax.deviceType, "device can not be null");
    checkArgument(!this.syntax.deviceProperties.isEmpty(), "at least one device propery must be specified");
    
    List<T> commands = domotics.build(COMMAND, syntax);
    log.debug("COMMANDS: {}", commands);
    return commands;
  }
  
  /**
   * TODO not implemented yet
   * 
   * @return Request status implementation.
   */
  public List<T> status() {
    log.debug("request for STATUS");
    // TODO validation
    return domotics.build(STATUS, syntax);
  }
  
  /**
   * Executes all the commands now (WHEN).
   */
  public void execCommand() {
    execute(command());
  }
  
  /**
   * Spawn a new thread to execute the request.
   */
  private void execute(List<T> values) {
    domotics.startClient(values);
  }
  
}
