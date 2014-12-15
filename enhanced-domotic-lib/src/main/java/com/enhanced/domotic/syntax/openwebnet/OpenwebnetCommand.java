package com.enhanced.domotic.syntax.openwebnet;

import static com.enhanced.domotic.domain.EDomotic.SyntaxType.COMMAND;

import java.text.MessageFormat;
import java.util.List;

import com.enhanced.domotic.Domotics;
import com.enhanced.domotic.domain.EActionProperty.ActionPropertyType;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;
import com.enhanced.domotic.domain.EDomotic;
import com.enhanced.domotic.syntax.Request;
import com.enhanced.domotic.syntax.Property;
import com.enhanced.domotic.syntax.Syntax;
import com.google.common.collect.Lists;

/*
 * TODO refactor
 */
@EDomotic(COMMAND)
public class OpenwebnetCommand extends Request<String> {
  
  public static final String FORMAT = "*{0}*{1}*{2}##";
  
  public OpenwebnetCommand(Domotics<String> domotics, Syntax<String> syntax) {
    super(domotics, syntax);
  }

  @Override
  public List<String> build() {
    List<String> commands = Lists.newArrayList();
    for (String how : howList()) {
      commands.addAll(formatCommand(how));
    }
    return commands;
  }
  
  private List<String> formatCommand(String what) {
    List<String> commands = Lists.newArrayList();
    for (String where : whereList()) {
      commands.add(MessageFormat.format(FORMAT, who(), what, where));
    }
    return commands;
  }
  
  /**
   * Action = WHAT
   */
  private String what() {
    return domotics().action(syntax().actionType).val(syntax().deviceType);
  }
  
  /**
   * Action Properties = HOW
   */
  @SuppressWarnings("unchecked")
  private <V> List<String> howList() {
    if (syntax().actionProperties.isEmpty()) {
      return Lists.newArrayList(what());
    }
    // how overrides what
    List<String> howList = Lists.newArrayList();
    for (ActionPropertyType property : syntax().actionProperties.keySet()) {
      Property<String, V> actionProperty = domotics().actionProperty(property);
      howList.addAll(actionProperty.transform((List<V>) syntax().actionProperties.get(property)));
    }
    return howList;
  }
  
  /**
   * Device = WHO
   */
  private String who() {
    return domotics().device(syntax().deviceType).val();
  }
  
  /**
   * Device Properties = WHERE
   */
  @SuppressWarnings("unchecked")
  private <V> List<String> whereList() {
    List<String> whereList = Lists.newArrayList();
    for (DevicePropertyType property : syntax().deviceProperties.keySet()) {
      Property<String, V> deviceProperty = domotics().deviceProperty(property);
      whereList.addAll(deviceProperty.transform((List<V>) syntax().deviceProperties.get(property)));
    }
    return whereList;
  }
  
}
