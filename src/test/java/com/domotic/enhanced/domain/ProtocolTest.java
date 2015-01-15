package com.domotic.enhanced.domain;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProtocolTest {

  @Test
  public void test() {
    String expected = "com.domotic.enhanced.protocol.openwebnet";
    assertEquals("invalid OpenWebNet package", expected, OPENWEBNET.getPath());
  }

}
