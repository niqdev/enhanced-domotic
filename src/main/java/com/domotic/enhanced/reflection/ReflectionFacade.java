package com.domotic.enhanced.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.domotic.enhanced.Config;

import dalvik.system.BaseDexClassLoader;

public class ReflectionFacade implements EnhancedReflection {

  private final EnhancedReflection reflection;

  public ReflectionFacade(Config config) {
    this.reflection = isDexClassLoader() ?
      ReflectionApk.newInstance(config) : ReflectionJar.newInstance(config);
  }

  @Override
  public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflection.getMethodsAnnotatedWith(annotation);
  }

  @Override
  public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflection.getTypesAnnotatedWith(annotation);
  }
  
  private boolean isDexClassLoader() {
    return Thread.currentThread().getContextClassLoader() instanceof BaseDexClassLoader;
  }

}
