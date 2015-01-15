package com.domotic.enhanced.protocol.openwebnet.syntax;

import static com.domotic.enhanced.domain.EDomotic.SyntaxType.COMMAND;

import java.text.MessageFormat;
import java.util.List;

import com.domotic.enhanced.Domotics;
import com.domotic.enhanced.domain.EActionProperty.ActionPropertyType;
import com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType;
import com.domotic.enhanced.domain.EDomotic;
import com.domotic.enhanced.syntax.Property;
import com.domotic.enhanced.syntax.Syntax;
import com.domotic.enhanced.syntax.SyntaxComposer;
import com.google.common.collect.Lists;

@EDomotic(COMMAND)
public class OpenwebnetCommand extends SyntaxComposer<String> {
  
  public static final String FORMAT = "*{0}*{1}*{2}##";
  
  public OpenwebnetCommand(Domotics<String> domotics, Syntax<String> syntax) {
    super(domotics, syntax);
  }

  @Override
  public List<String> compose() {
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
