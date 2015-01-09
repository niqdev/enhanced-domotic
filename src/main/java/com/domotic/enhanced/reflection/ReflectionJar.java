package com.domotic.enhanced.reflection;

import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.domotic.enhanced.Config;

public class ReflectionJar implements EnhancedReflection {
  
  private final Reflections reflections;
  
  private ReflectionJar(Config config) {
    String path = checkNotNull(config.protocol().getPath());
    this.reflections = new Reflections(path, new MethodAnnotationsScanner(),
      new TypeAnnotationsScanner(), new SubTypesScanner());
  }
  
  public static EnhancedReflection newInstance(Config config) {
    return new ReflectionJar(config);
  }

  @Override
  public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getMethodsAnnotatedWith(annotation);
  }

  @Override
  public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getTypesAnnotatedWith(annotation);
  }

}
