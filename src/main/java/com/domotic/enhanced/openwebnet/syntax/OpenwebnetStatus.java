package com.domotic.enhanced.openwebnet.syntax;

import static com.domotic.enhanced.domain.EDomotic.SyntaxType.STATUS;

import java.util.List;

import com.domotic.enhanced.Domotics;
import com.domotic.enhanced.domain.EDomotic;
import com.domotic.enhanced.syntax.Syntax;
import com.domotic.enhanced.syntax.SyntaxComposer;

@EDomotic(STATUS)
public class OpenwebnetStatus extends SyntaxComposer<String> {

  public OpenwebnetStatus(Domotics<String> domotics, Syntax<String> syntax) {
    super(domotics, syntax);
  }

  @Override
  public List<String> compose() {
    throw new UnsupportedOperationException("not implemented yet");
  }

}
