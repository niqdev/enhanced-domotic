package com.domotic.enhanced.domain;

import static com.domotic.enhanced.domain.Protocol.OPENWEBNET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProtocolTest {

  @Test
  public void pathOpenwebnet() {
    String expected = "com.domotic.enhanced.protocol.openwebnet";
    assertEquals("invalid OpenWebNet package", expected, OPENWEBNET.getPath());
  }
  
  @Test
  public void utilStringToEnum() {
    assertTrue("invalid string", Protocol.stringToEnum("OPENWEBNET") == OPENWEBNET);
    assertFalse("invalid string", Protocol.stringToEnum("") == OPENWEBNET);
    assertFalse("invalid string", Protocol.stringToEnum("XXX") == OPENWEBNET);
  }
  
  @Test(expected = NullPointerException.class)
  public void utilStringToEnumNull() {
    Protocol.stringToEnum(null);
  }
  
  @Test
  public void utilEnumToString() {
    assertTrue("invalid enum", Protocol.enumToString(OPENWEBNET).equals("OPENWEBNET"));
    assertFalse("invalid enum", Protocol.enumToString(OPENWEBNET).equals("openwebnet"));
  }
  
  @Test
  public void utilContainsString() {
    assertFalse("invalid string", Protocol.contains((String) null));
    assertFalse("invalid string", Protocol.contains(""));
    assertFalse("invalid string", Protocol.contains("openwebnet"));
    assertTrue("invalid string", Protocol.contains("OPENWEBNET"));
  }
  
  @Test
  public void utilContainsEnum() {
    assertFalse("invalid enum", Protocol.contains((Protocol) null));
    assertTrue("invalid enum", Protocol.contains(OPENWEBNET));
  }

}
