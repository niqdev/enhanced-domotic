package com.domotic.enhanced;

import static com.domotic.enhanced.EnhancedDomotic.inEnvironment;
import static com.domotic.enhanced.EnhancedDomotic.inGroup;
import static com.domotic.enhanced.EnhancedDomotic.light;
import static com.domotic.enhanced.EnhancedDomotic.turnOff;
import static com.domotic.enhanced.EnhancedDomotic.turnOn;
import static com.domotic.enhanced.EnhancedDomotic.withId;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.domotic.enhanced.command.Command;
import com.domotic.enhanced.config.ConfigManager;
import com.domotic.enhanced.config.ConfigValidTest;

public class LightTest {

  @Before
  public void setUp() throws Exception {
    ConfigManager.initConfig(ConfigValidTest.class);
  }

  @Test
  @Ignore
  public void turnOnAll() {
    assertEquals("invalid commmand", "*1*1*0##", cmd(turnOn().the(light())));
  }

  @Test
  @Ignore
  public void turnOffAll() {
    assertEquals("invalid commmand", "*1*0*0##", cmd(turnOff().the(light())));
  }

  @Test
  @Ignore
  public void turnOnWithId() {
    assertEquals("invalid commmand", "*1*1*11##",
        cmd(turnOn().the(light(withId(11)))));
  }

  @Test
  @Ignore
  public void turnOnInGroup() {
    assertEquals("invalid commmand", "*1*1*#3##",
        cmd(turnOn().the(light(inGroup(3)))));
  }

  @Test
  @Ignore
  public void turnOnInEnvironment() {
    assertEquals("invalid commmand", "*1*1*8##",
        cmd(turnOn().the(light(inEnvironment(8)))));
  }

  private static <T> String cmd(Command<T> cmd) {
    return (String) cmd.val().get(0);
  }

}
