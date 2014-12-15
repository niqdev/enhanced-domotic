package com.enhanced.domotic.syntax.openwebnet;

import static com.enhanced.domotic.domain.EDomotic.SyntaxType.STATUS;

import java.util.List;

import com.enhanced.domotic.Domotics;
import com.enhanced.domotic.domain.EDomotic;
import com.enhanced.domotic.syntax.Request;
import com.enhanced.domotic.syntax.Syntax;

@EDomotic(STATUS)
public class OpenwebnetStatus extends Request<String> {

  public OpenwebnetStatus(Domotics<String> domotics, Syntax<String> syntax) {
    super(domotics, syntax);
  }

  @Override
  public List<String> build() {
    throw new UnsupportedOperationException("not implemented yet");
  }

}
