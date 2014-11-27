package com.domotic.enhanced.domain;

import com.domotic.enhanced.command.SubCommand;

public interface Device extends SubCommand {

  /*
   * DEVICE
   */

  interface Light extends Device {

  }

  interface Radio extends Device {

  }

  interface Gate extends Device {

  }

  /*
   * DEVICE PROPERTY
   */

  interface DeviceProperty extends Property {

    interface Id extends DeviceProperty {

    }

    interface Name extends DeviceProperty {

    }

    interface Group extends DeviceProperty {

    }

    interface Environment extends DeviceProperty {

    }

  }

}
