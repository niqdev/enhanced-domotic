package com.domotic.enhanced;

import static com.domotic.enhanced.EnhancedDomotic.close;
import static com.domotic.enhanced.EnhancedDomotic.delay;
import static com.domotic.enhanced.EnhancedDomotic.dimer;
import static com.domotic.enhanced.EnhancedDomotic.gate;
import static com.domotic.enhanced.EnhancedDomotic.inEnvironment;
import static com.domotic.enhanced.EnhancedDomotic.inGroup;
import static com.domotic.enhanced.EnhancedDomotic.is;
import static com.domotic.enhanced.EnhancedDomotic.light;
import static com.domotic.enhanced.EnhancedDomotic.now;
import static com.domotic.enhanced.EnhancedDomotic.radio;
import static com.domotic.enhanced.EnhancedDomotic.speed;
import static com.domotic.enhanced.EnhancedDomotic.turnOff;
import static com.domotic.enhanced.EnhancedDomotic.turnOn;
import static com.domotic.enhanced.EnhancedDomotic.volume;
import static com.domotic.enhanced.EnhancedDomotic.withId;
import static com.domotic.enhanced.EnhancedDomotic.withName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class App {

  private static final Logger log = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {

    // examples

    now(turnOff().the(light(withId(11))));

    now(turnOn(dimer(80)).the(light(inGroup(3))));

    is(close().the(gate(withName("GATE_1"))));

    delay(close(speed(30)).the(gate(withName("GATE_1"))), 30);

    now(turnOn(volume(60)).the(radio(inEnvironment(1))));
  }

}
