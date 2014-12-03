package com.enhanced.domotic;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import com.enhanced.domotic.command.Action;
import com.enhanced.domotic.command.Device;
import com.enhanced.domotic.command.Property;
import com.enhanced.domotic.command.Syntax;
import com.enhanced.domotic.command.openwebnet.Openwebnet;
import com.enhanced.domotic.domain.EAction;
import com.enhanced.domotic.domain.EAction.ActionType;
import com.enhanced.domotic.domain.EDevice;
import com.enhanced.domotic.domain.EDevice.DeviceType;
import com.enhanced.domotic.domain.EDeviceProperty;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;
import com.enhanced.domotic.domain.EDomotic.SyntaxType;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Domotics<T> {

  private final Config config;
  private final Reflections reflection;

  private Domotics(Config config) {
    this.config = config;
    this.reflection = new Reflections(config.protocol().getPath(), new MethodAnnotationsScanner());
  }
  
  public static <T> Domotics<T> getInstance(Config config) {
    return new Domotics<T>(config);
  }
  
  /**
   * Find method annotated with {@link EAction} and invoke
   * its implementation depending on protocol.
   * 
   * @throws NoSuchElementException if no implementation found 
   * @throws IllegalArgumentException if multiple implementation found
   */
  public Action<T> action(final ActionType actionType) {
    Method method = findAnnotatedMethod(EAction.class, actionType);
    return Domotics.<Action<T>>invokeStaticMethod(method);
  }
  
  /**
   * Find method annotated with {@link EDevice} and invoke
   * its implementation depending on protocol.
   * 
   * @throws NoSuchElementException if no implementation found 
   * @throws IllegalArgumentException if multiple implementation found
   */
  public Device<T> device(DeviceType deviceType) {
    Method method = findAnnotatedMethod(EDevice.class, deviceType);
    return Domotics.<Device<T>>invokeStaticMethod(method);
  }
  
  /**
   * Find method annotated with {@link EDeviceProperty} and invoke
   * its implementation depending on protocol.
   * 
   * @throws NoSuchElementException if no implementation found 
   * @throws IllegalArgumentException if multiple implementation found
   */
  public <V> Property<T, V> deviceProperty(DevicePropertyType propertyType) {
    Method method = findAnnotatedMethod(EDeviceProperty.class, propertyType);
    return Domotics.<Property<T, V>>invokeStaticMethod(method);
  }
  
  private <A extends Annotation, E extends Enum<E>> Method
    findAnnotatedMethod(Class<A> annotationClass, E enumType) {
    
    checkNotNull(annotationClass);
    checkNotNull(enumType);
    Set<Method> candidate = Sets.filter(findMethods(annotationClass), isValidType(annotationClass, enumType));
    return Iterables.getOnlyElement(candidate);
  }
  
  private Set<Method> findMethods(Class<? extends Annotation> annotation) {
    return reflection.getMethodsAnnotatedWith(annotation);
  }
  
  private static <A extends Annotation, E extends Enum<E>> Predicate<Method>
    isValidType(final Class<A> annotationClass, final E enumType) {
    
    return new Predicate<Method>() {
  
      @Override
      public boolean apply(Method input) {
        // find annotation on method
        final Annotation annotation = input.getAnnotation(annotationClass);
        // find attributes on annotation
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
          // test attribute VALUE on annotation
          if (annotationValue(annotation, method) == enumType) {
            return true;
          }
        }
        return false;
      }
    };
  }
  
  @SuppressWarnings("unchecked")
  private static <E extends Enum<E>> E annotationValue(Annotation annotation, Method attribute) {
    // expected VALUE attribute
    checkArgument("value".equals(attribute.getName()));
    try {
      return (E) attribute.invoke(annotation);
    } catch (IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      throw new EnhancedException("annotation attribute VALUE expected", e);
    }
  }
  
  /**
   * Invoke {@code static} method with no parameters.
   */
  @SuppressWarnings("unchecked")
  private static <C> C invokeStaticMethod(Method method) {
    try {
      return (C) method.invoke(null);
    } catch (IllegalAccessException | IllegalArgumentException
        | InvocationTargetException e) {
      throw new EnhancedException("error invoking single static method", e);
    }
  }
  
  /**
   * Safe {@code Varargs} to {@code List} conversion.
   */
  @SafeVarargs
  public static <V> List<V> newSafeList(V... values) {
    return values != null ? Lists.newArrayList(values) : new ArrayList<V>();
  }
  
  /*
   * TODO
   */
  public List<T> build(SyntaxType syntaxType, Syntax<T> syntax) {
    //Method method = findAnnotatedMethod(EDomotic.class, syntaxType);

    // TODO
    return (List<T>) Openwebnet.build((Domotics<String>) this, (Syntax<String>)syntax);
  }
  
}
