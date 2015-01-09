package com.domotic.enhanced;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domotic.enhanced.client.Request;
import com.domotic.enhanced.domain.EAction.ActionType;
import com.domotic.enhanced.domain.EActionProperty.ActionPropertyType;
import com.domotic.enhanced.domain.EDevice.DeviceType;
import com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType;
import com.domotic.enhanced.domain.EDomotic.SyntaxType;
import com.domotic.enhanced.openwebnet.client.Handler;
import com.domotic.enhanced.syntax.Syntax;

public class EnhancedDomotic<T> {
  
  private static final Logger log = LoggerFactory.getLogger(EnhancedDomotic.class);
  
  private static EnhancedDomotic<?> instance;
  
  private final Config config;
  private final Domotics<T> domotics;
  private Syntax<T> syntax;
  private Handler<T> handler;
  
  private EnhancedDomotic(Config config) {
    instance = this;
    this.config = config;
    this.domotics = Domotics.newInstance(config);
    this.syntax = Syntax.newInstance();
  }
  
  @SuppressWarnings("unchecked")
  public static <T> EnhancedDomotic<T> config(Config config) {
    return (EnhancedDomotic<T>) (instance == null ? new EnhancedDomotic<T>(checkNotNull(config)) : instance);
  }
  
  public EnhancedDomotic<T> resetConfig(Config config) {
    instance = new EnhancedDomotic<T>(checkNotNull(config));
    return this;
  }
  
  /**
   * Defines an handler to manage the request.
   */
  public EnhancedDomotic<T> handler(Handler<T> handler) {
    handler = checkNotNull(handler);
    return this;
  }
  
  public EnhancedDomotic<T> type(SyntaxType syntaxType) {
    syntax.syntaxType = checkNotNull(syntaxType);
    log.debug("SYNTAX TYPE: {}", syntaxType);
    return this;
  }
  
  /**
   * The action to be computed.
   * 
   * @see com.domotic.enhanced.domain.EAction.ActionType
   */
  public EnhancedDomotic<T> action(ActionType actionType) {
    syntax.actionType = checkNotNull(actionType);
    log.debug("ACTION: {}", actionType);
    return this;
  }
  
  /**
   * Specify additional information of the action.
   * 
   * @see com.domotic.enhanced.domain.EActionProperty.ActionPropertyType
   */
  @SuppressWarnings("unchecked")
  public <V> EnhancedDomotic<T> actionProperty(ActionPropertyType property, V... values) {
    syntax.actionProperties.put(checkNotNull(property), Domotics.newSafeList(values));
    log.debug("ACTION PROPERTY: {} - {}", property, values);
    return this;
  }
  
  /**
   * The device on which the action takes effect.
   * 
   * @see com.domotic.enhanced.domain.EDevice.DeviceType
   */
  public EnhancedDomotic<T> device(DeviceType deviceType) {
    syntax.deviceType = checkNotNull(deviceType);
    log.debug("DEVICE: {}", deviceType);
    return this;
  }
  
  /**
   * Specify additional information of the device.
   * 
   * @see com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType
   */
  @SuppressWarnings("unchecked")
  public <V> EnhancedDomotic<T> deviceProperty(DevicePropertyType property, V... values) {
    syntax.deviceProperties.put(checkNotNull(property), Domotics.newSafeList(values));
    log.debug("DEVICE PROPERTY: {} - {}", property, values);
    return this;
  }
  
  /**
   * @throws java.lang.NullPointerException
   * @throws java.lang.IllegalArgumentException
   */
  private void validateSyntax() {
    checkNotNull(syntax.syntaxType, "syntax type can not be null");
    switch (syntax.syntaxType) {
    case COMMAND:
      checkNotNull(syntax.actionType, "action can not be null");
      checkNotNull(syntax.deviceType, "device can not be null");
      checkArgument(!syntax.deviceProperties.isEmpty(), "at least one device property must be specified");
      break;
    case STATUS:
      // TODO
      break;
    default:
      // should never happen
      throw new DomoticException("invalid syntax type");
    }
  }
  
  public List<T> val() {
    try {
      log.debug("SYNTAX VALUES: {}", syntax);
      validateSyntax();
      
      return domotics.build(syntax);
    } catch (Exception e) {
      log.error("error syntax value", e);
      throw new DomoticException(e);
    } finally {
      this.syntax = Syntax.newInstance();
    }
  }
  
  /**
   * Start a new thread and execute the request.
   */
  public void execute() {
    try {
      Syntax<T> clonedSyntax = this.syntax;
      
      domotics.startClient(new Request.Build<T>()
        .config(config).values(val()).syntax(clonedSyntax).handler(handler).build());
    } catch (Exception e) {
      log.error("error client execution", e);
      throw new DomoticException(e);
    }
  }
  
  /**
   * Executes raw request.
   * 
   * @param request
   */
  public void raw(Request<T> request) {
    domotics.startClient(request);
  }

}