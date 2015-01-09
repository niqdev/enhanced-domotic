package com.domotic.enhanced.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

public interface EnhancedReflection {
  
  Set<Method> getMethodsAnnotatedWith(final Class<? extends Annotation> annotation);
  
  Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation);

}
