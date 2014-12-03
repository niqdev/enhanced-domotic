package com.enhanced.domotic.command.openwebnet;

import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;

import java.util.List;

import com.enhanced.domotic.command.Property;
import com.enhanced.domotic.domain.EDeviceProperty;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class OpenwebnetDeviceProperty {
  
  public OpenwebnetDeviceProperty() {}
  
  @EDeviceProperty(ID)
  public static Property<String, Integer> id() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        return FluentIterable
          .from(values)
          .filter(new Predicate<Integer>() {

            @Override
            public boolean apply(Integer input) {
              // discard invalid value
              return input >= 11 && input <= 99;
            }
          })
          .transform(Functions.toStringFunction())
          .toList();
      }
    };
  }
  
  @EDeviceProperty(GROUP)
  public static Property<String, Integer> group() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        return FluentIterable
          .from(values)
          .filter(new Predicate<Integer>() {

            @Override
            public boolean apply(Integer input) {
              // discard invalid value
              return input >= 1 && input <= 9;
            }
          })
          .transform(new Function<Integer, String>() {

            @Override
            public String apply(Integer input) {
              // format
              return '#' + String.valueOf(input);
            }
          })
          .toList();
      }
    };
  }
  
}
