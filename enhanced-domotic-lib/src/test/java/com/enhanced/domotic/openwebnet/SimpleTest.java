package com.enhanced.domotic.openwebnet;

import org.junit.Before;
import org.junit.Test;

import com.enhanced.domotic.Config;
import com.enhanced.domotic.EnhancedDomotic;

public class SimpleTest {

  private Config config;

  @Before
  public void setUp() throws Exception {
    config = new OpenwebnetConfig();
  }

  @Test
  public void run() {
    EnhancedDomotic.<String>execRaw(config, "");
  }

}
