package com.enhanced.domotic.syntax.openwebnet;

import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ALL;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.GROUP;
import static com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType.ID;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import com.enhanced.domotic.domain.EDeviceProperty;
import com.enhanced.domotic.syntax.Property;
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
        checkArgument(isEmpty(values), "invalid parameters: no value allowed");
        
        return Lists.newArrayList("0");
      }
    };
  }
  
  @EDeviceProperty(ID)
  public static Property<String, Integer> id() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        checkArgument(isNotEmpty(values), "invalid parameters: at least one value");
        
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
        checkArgument(isNotEmpty(values), "invalid parameters: at least one value");
        
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
