package com.domotic.enhanced;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.domotic.enhanced.client.Handler;
import com.domotic.enhanced.client.Request;
import com.domotic.enhanced.domain.EAction.ActionType;
import com.domotic.enhanced.domain.EActionProperty.ActionPropertyType;
import com.domotic.enhanced.domain.EDevice.DeviceType;
import com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType;
import com.domotic.enhanced.domain.EDomotic.SyntaxType;
import com.domotic.enhanced.syntax.Syntax;

/**
 * A simple domotic library in Java.
 * 
 * <pre><i>Example with fluent syntax</i></pre>
 * <pre>
 * {@code
 * EnhancedDomotic.config(config)
 *    .type(COMMAND)
 *    .action(TURN_ON)
 *    .device(LIGHT)
 *    .deviceProperty(ID, 21)
 *    .execute();
 * }
 * </pre>
 * 
 * @author niqdev
 */
public class EnhancedDomotic<T> {
  
  private static final Logger log = LoggerFactory.getLogger(EnhancedDomotic.class);
  
  private static EnhancedDomotic<?> instance;
  
  private final Config config;
  private final Domotics<T> domotics;
  private Syntax<T> syntax;
  private Handler handler;
  
  private EnhancedDomotic(Config config) {
    instance = this;
    this.config = config;
    this.domotics = Domotics.newInstance(config);
    this.syntax = Syntax.newInstance();
  }
  
  /**
   * Initiate the library. After the first time the same instance is returned.
   * 
   * @see com.domotic.enhanced.EnhancedDomotic.resetConfig(Config)
   */
  @SuppressWarnings("unchecked")
  public static <T> EnhancedDomotic<T> config(Config config) {
    return (EnhancedDomotic<T>) (instance == null ? new EnhancedDomotic<T>(checkNotNull(config)) : instance);
  }
  
  /**
   * Reset library configuration.
   */
  public EnhancedDomotic<T> resetConfig(Config config) {
    instance = new EnhancedDomotic<T>(checkNotNull(config));
    return this;
  }
  
  /**
   * Manage and intercept the request.
   * 
   * @see com.domotic.enhanced.client.Handler
   */
  public EnhancedDomotic<T> handler(Handler handler) {
    this.handler = checkNotNull(handler);
    return this;
  }
  
  /**
   * The type of request to be computed.
   * 
   * @see com.domotic.enhanced.domain.EDomotic.SyntaxType
   */
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
  
  /*
   * @throws java.lang.NullPointerException
   * @throws java.lang.IllegalArgumentException
   */
  private void validateSyntax() {
    log.info("SYNTAX: {}", syntax);
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
  
  /**
   * Raw value of the request. Mainly for test purpose.
   * 
   * @see com.domotic.enhanced.EnhancedDomotic.raw(Request<T>)
   */
  public List<T> val() {
    try {
      validateSyntax();
      List<T> values = domotics.build(syntax);
      log.info("VALUES: {}", values);
      return values;
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

}