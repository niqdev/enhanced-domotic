package com.domotic.enhanced.protocol.openwebnet.syntax;

import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ENVIRONMENT;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType.ID;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import com.domotic.enhanced.domain.EDeviceProperty;
import com.domotic.enhanced.syntax.Property;
import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;

public class OpenwebnetDeviceProperty {
  
  public OpenwebnetDeviceProperty() {}
  
  @EDeviceProperty(ALL)
  public static Property<String, Integer> all() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        checkArgument(isEmpty(values), "invalid parameters: value not allowed");
        
        return Lists.newArrayList("0");
      }
    };
  }
  
  @EDeviceProperty(ID)
  public static Property<String, Integer> id() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        List<String> properties = FluentIterable
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
        
        checkArgument(isNotEmpty(properties), "invalid parameters: at least one value expected");
        return properties;
      }
    };
  }
  
  @EDeviceProperty(GROUP)
  public static Property<String, Integer> group() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        List<String> properties = FluentIterable
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
        
        checkArgument(isNotEmpty(properties), "invalid parameters: at least one value expected");
        return properties;
      }
    };
  }
  
  @EDeviceProperty(ENVIRONMENT)
  public static Property<String, Integer> environment() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        List<String> properties = FluentIterable
          .from(values)
          .filter(new Predicate<Integer>() {

            @Override
            public boolean apply(Integer input) {
              // discard invalid value
              return input >= 1 && input <= 9;
            }
          })
          .transform(Functions.toStringFunction())
          .toList();
        
        checkArgument(isNotEmpty(properties), "invalid parameters: at least one value expected");
        return properties;
      }
    };
  }
  
}
