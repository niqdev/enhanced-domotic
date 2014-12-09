package com.enhanced.domotic.syntax.openwebnet;

import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.BLINK;
import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.DIMER;
import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.SPEED;
import static com.enhanced.domotic.domain.EActionProperty.ActionPropertyType.TIMED;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.collections4.CollectionUtils.size;

import java.util.List;

import com.enhanced.domotic.domain.EActionProperty;
import com.enhanced.domotic.syntax.Property;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

public class OpenwebnetActionProperty {
  
  private OpenwebnetActionProperty() {}
  
  @EActionProperty(BLINK)
  public static Property<String, Integer> blink() {
    throw new UnsupportedOperationException("not implemented yet");
  }
  
  @EActionProperty(DIMER)
  public static Property<String, Integer> dimer() {
    return new Property<String, Integer>() {

      @Override
      public List<String> transform(List<Integer> values) {
        List<String> properties = FluentIterable
          .from(values)
          .filter(new Predicate<Integer>() {

            @Override
            public boolean apply(Integer input) {
              // discard invalid value
              return input >= 20 && input <= 100;
            }
            
          })
          .transform(new Function<Integer, String>() {

            @Override
            public String apply(Integer input) {
              // remove decimal part
              return String.valueOf((int) input/10);
            }
          })
          .toList();
        
        checkArgument(size(properties) == 1, "invalid parameters: one and only one value allowed");
        return properties;
      }
    };
  }
  
  @EActionProperty(SPEED)
  public static Property<String, Integer> speed() {
    throw new UnsupportedOperationException("not implemented yet");
  }
  
  @EActionProperty(TIMED)
  public static Property<String, Integer> timed() {
    throw new UnsupportedOperationException("not implemented yet");
  }

}
