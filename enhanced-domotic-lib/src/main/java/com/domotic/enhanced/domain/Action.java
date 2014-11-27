package com.domotic.enhanced.domain;

import com.domotic.enhanced.command.Command;
import com.domotic.enhanced.command.SubCommand;

public interface Action extends SubCommand {

  <C> Command<C> the(Device device);

  /*
   * ACTION
   */

  interface TurnOn extends Action {

  }

  interface TurnOff extends Action {

  }

  interface Open extends Action {

  }

  interface Close extends Action {

  }

  /*
   * ACTION PROPERTY
   */

  interface ActionProperty extends Property {

    interface Timed extends ActionProperty {

    }

    interface Blink extends ActionProperty {

    }

    // TODO assignable only from dimer ?
    interface Dimer extends ActionProperty {

    }

    // TODO assignable only from dimer ?
    interface Speed extends ActionProperty {

    }

    interface Volume extends ActionProperty {

    }

  }

}
