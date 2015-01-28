package com.domotic.enhanced;

import org.apache.commons.validator.routines.InetAddressValidator;

import android.content.Context;

import com.domotic.enhanced.domain.Protocol;

public interface Config {

  Protocol protocol();

  String host();

  Integer port();

  Context context();

  public static class Validator {

    private Validator() {}

    public static boolean isValidHost(String host) {
      return InetAddressValidator.getInstance().isValidInet4Address(host);
    }

    public static boolean isValidPort(Integer port) {
      return port != null && port >= 0 && port <= 65535;
    }
  }

}
