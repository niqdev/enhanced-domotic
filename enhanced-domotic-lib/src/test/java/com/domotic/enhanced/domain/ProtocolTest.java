package com.domotic.enhanced.domain;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProtocolTest {

	@Test
	public void openwebnetPackage() {
		String pkg = "com.domotic.enhanced.protocol.openwebnet";
		assertTrue("invalid package", pkg.equals(OPENWEBNET.getDefaultPackage()));
	}

}
