package com.domotic.enhanced;

import com.domotic.enhanced.command.Command;
import com.domotic.enhanced.domain.Action.ActionProperty;
import com.domotic.enhanced.domain.Action.ActionProperty.Blink;
import com.domotic.enhanced.domain.Action.ActionProperty.Dimer;
import com.domotic.enhanced.domain.Action.ActionProperty.Speed;
import com.domotic.enhanced.domain.Action.ActionProperty.Timed;
import com.domotic.enhanced.domain.Action.ActionProperty.Volume;
import com.domotic.enhanced.domain.Action.Close;
import com.domotic.enhanced.domain.Action.Open;
import com.domotic.enhanced.domain.Action.TurnOff;
import com.domotic.enhanced.domain.Action.TurnOn;
import com.domotic.enhanced.domain.Device.DeviceProperty;
import com.domotic.enhanced.domain.Device.DeviceProperty.Environment;
import com.domotic.enhanced.domain.Device.DeviceProperty.Group;
import com.domotic.enhanced.domain.Device.DeviceProperty.Id;
import com.domotic.enhanced.domain.Device.DeviceProperty.Name;
import com.domotic.enhanced.domain.Device.Gate;
import com.domotic.enhanced.domain.Device.Light;
import com.domotic.enhanced.domain.Device.Radio;

public class EnhancedDomotic {

  /*
   * EXECUTOR with handler?
   */

  public static <C> void now(Command<C> command) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static <C> void delay(Command<C> command, Integer seconds) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static <C> boolean is(Command<C> command) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  /*
   * ACTION (what)
   */

  public static TurnOn turnOn(ActionProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static TurnOff turnOff(ActionProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Open open(ActionProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Close close(ActionProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  /*
   * ACTION PROPERTY (how)
   */

  public static Timed timed(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Blink blink(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Dimer dimer(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Speed speed(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Volume volume(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  /*
   * DEVICE (who)
   */

  public static Light light(DeviceProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Radio radio(DeviceProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Gate gate(DeviceProperty... properties) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  /*
   * DEVICE PROPERTY (how)
   */

  public static Id withId(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Name withName(String... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Group inGroup(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

  public static Environment inEnvironment(Integer... values) {
    throw new UnsupportedOperationException("not implemented yet");
  }

}
