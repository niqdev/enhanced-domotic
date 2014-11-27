package com.domotic.enhanced;

import static com.domotic.enhanced.protocol.openwebnet.EnhancedDomotic.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class App {
	
	private static final Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		
		now(turnOff().the(light(withId(11))));
		
		now(turnOn(dimer(80)).the(light(inGroup(3))));
		
		is(close().the(gate(withName("GATE_1"))));
		
		delay(close(speed(30)).the(gate(withName("GATE_1"))), 30);
		
		now(turnOn(volume(60)).the(radio(inEnvironment(1))));
	}
	
}
