package com.enhanced.domotic.domain;

import static com.enhanced.domotic.domain.Protocol.OPENWEBNET;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProtocolTest {

  @Test
  public void test() {
    String expected = "com.enhanced.domotic.syntax.openwebnet";
    assertEquals("invalid OpenWebNet package", expected, OPENWEBNET.getPath());
  }

}
