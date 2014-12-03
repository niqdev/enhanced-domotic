package com.enhanced.domotic.command.openwebnet;

import static com.enhanced.domotic.domain.EDomotic.SyntaxType.COMMAND;

import java.text.MessageFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enhanced.domotic.Domotics;
import com.enhanced.domotic.command.Property;
import com.enhanced.domotic.command.Syntax;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;
import com.enhanced.domotic.domain.EDomotic;
import com.google.common.collect.Lists;

public class Openwebnet {
  
  private static final Logger log = LoggerFactory.getLogger(Openwebnet.class);
  
  //*CHI*COSA*DOVE##
  public static final String FORMAT_COMMAND = "*{0}*{1}*{2}##";

  @EDomotic(COMMAND)
  public static List<String> build(Domotics<String> domotics, Syntax<String> syntax) {
    List<String> commands = Lists.newArrayList();
    
    String who = who(domotics, syntax);
    // TODO how overrides who!
    List<String> how = howList(domotics, syntax);
    
    String what = what(domotics, syntax);
    
    for (String where : whereList(domotics, syntax)) {
      commands.add(MessageFormat.format(FORMAT_COMMAND, who, what, where));
    }
    
    return commands;
  }
  
  private static String who(Domotics<String> domotics, Syntax<String> syntax) {
    return domotics.action(syntax.actionType).val(syntax.deviceType);
  }
  
  private static List<String> howList(Domotics<String> domotics, Syntax<String> syntax) {
    List<String> how = Lists.newArrayList();
    // TODO
    return how;
  }
  
  private static String what(Domotics<String> domotics, Syntax<String> syntax) {
    return domotics.device(syntax.deviceType).val();
  }
  
  @SuppressWarnings("unchecked")
  private static List<String> whereList(Domotics<String> domotics, Syntax<String> syntax) {
    List<String> where = Lists.newArrayList();
    for (DevicePropertyType property : syntax.deviceProperties.keySet()) {
      Property<String, Integer> deviceProperty = domotics.deviceProperty(property);
      List<Integer> properties = (List<Integer>) syntax.deviceProperties.get(property);
      
      where.addAll(deviceProperty.transform(properties));
    }
    return where;
  }
  
}
