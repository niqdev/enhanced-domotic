package com.enhanced.domotic;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import com.enhanced.domotic.client.openwebnet.OpenwebnetClient;
import com.enhanced.domotic.domain.EAction;
import com.enhanced.domotic.domain.EAction.ActionType;
import com.enhanced.domotic.domain.EActionProperty;
import com.enhanced.domotic.domain.EActionProperty.ActionPropertyType;
import com.enhanced.domotic.domain.EDevice;
import com.enhanced.domotic.domain.EDevice.DeviceType;
import com.enhanced.domotic.domain.EDeviceProperty;
import com.enhanced.domotic.domain.EDeviceProperty.DevicePropertyType;
import com.enhanced.domotic.domain.EDomotic;
import com.enhanced.domotic.domain.EDomotic.SyntaxType;
import com.enhanced.domotic.syntax.Action;
import com.enhanced.domotic.syntax.Device;
import com.enhanced.domotic.syntax.Property;
import com.enhanced.domotic.syntax.Request;
import com.enhanced.domotic.syntax.Syntax;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class Domotics<T> {

  private final Config config;
  private final Reflections reflection;

  private Domotics(Config config) {
    this.config = config;
    this.reflection = new Reflections(config.protocol().getPath(),
        new MethodAnnotationsScanner(), new TypeAnnotationsScanner(), new SubTypesScanner());
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
   * Find method annotated with {@link EActionProperty} and invoke
   * its implementation depending on protocol.
   * 
   * @throws NoSuchElementException if no implementation found 
   * @throws IllegalArgumentException if multiple implementation found
   */
  public <V> Property<T, V> actionProperty(ActionPropertyType propertyType) {
    Method method = findAnnotatedMethod(EActionProperty.class, propertyType);
    return Domotics.<Property<T, V>>invokeStaticMethod(method);
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
  
  private <A extends Annotation, E extends Enum<E>> Class<?>
    findAnnotatedClass(Class<A> annotationClass, E enumType) {
    
    checkNotNull(annotationClass);
    checkNotNull(enumType);
    Set<Class<?>> candidate = Sets.filter(findClasses(annotationClass), isValidType(annotationClass, enumType));
    return Iterables.getOnlyElement(candidate);
  }
  
  private Set<Method> findMethods(Class<? extends Annotation> annotation) {
    return reflection.getMethodsAnnotatedWith(annotation);
  }
  
  private Set<Class<?>> findClasses(Class<? extends Annotation> annotation) {
    return reflection.getTypesAnnotatedWith(annotation);
  }

  /**
   * Validate {@code Method} or {@code Class}.
   * 
   * @see com.enhanced.domotic.Domotics.invokeGetAnnotation(Class<A>, T)
   */
  private static <C, A extends Annotation, E extends Enum<E>> Predicate<C>
    isValidType(final Class<A> annotationClass, final E enumType) {
    
    return new Predicate<C>() {
  
      @Override
      public boolean apply(C input) {
        final Annotation annotation = invokeGetAnnotation(annotationClass, input);
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
  
  /**
   * Find annotation on {@code Method} or {@code Class}.
   * 
   * @see java.lang.reflect.Method.getAnnotation(Class<T>)
   * @see java.lang.Class.getAnnotation(Class<A>)
   */
  private static <A extends Annotation, T> Annotation invokeGetAnnotation(final Class<A> annotationClass, T input) {
    try {
      return (Annotation) MethodUtils.invokeExactMethod(input, "getAnnotation", annotationClass);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      throw new EnhancedException("invoke [getAnnotation] method cause error", e);
    }
  }
  
  @SuppressWarnings("unchecked")
  private static <E extends Enum<E>> E annotationValue(Annotation annotation, Method attribute) {
    // expected annotation with VALUE attribute
    checkArgument("value".equals(attribute.getName()));
    try {
      return (E) attribute.invoke(annotation);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new EnhancedException("expected annotation attribute [value]", e);
    }
  }
  
  /**
   * Invoke {@code static} method.
   */
  @SuppressWarnings("unchecked")
  private static <C> C invokeStaticMethod(Method method, Object... args) {
    try {
      return (C) method.invoke(null, args);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new EnhancedException("invoke static method cause error", e);
    }
  }
  
  /**
   * Safe {@code Varargs} to {@code List} conversion.
   */
  @SafeVarargs
  public static <V> List<V> newSafeList(V... values) {
    return values != null ? Lists.newArrayList(values) : new ArrayList<V>();
  }
  
  /**
   * Instantiate a {@code new Request(Domotics<T>, Syntax<T>)} and
   * invoke {@code com.enhanced.domotic.syntax.Request.build()}.
   */
  @SuppressWarnings("unchecked")
  public List<T> build(SyntaxType syntaxType, Syntax<T> syntax) {
    //return (List<T>) new OpenwebnetCommand((Domotics<String>) this, (Syntax<String>) syntax).build();

    Class<?> klass = findAnnotatedClass(EDomotic.class, syntaxType);
    if (Request.class.isAssignableFrom(klass)) {
      try {
        Constructor<?> constructor = klass.getDeclaredConstructor(Domotics.class, Syntax.class);
        constructor.setAccessible(true);
        Request<T> command = (Request<T>) constructor.newInstance(this, syntax);
        
        Method method = Request.class.getDeclaredMethod("build");
        return (List<T>) method.invoke(command);
      } catch (NoSuchMethodException | SecurityException | InstantiationException
          | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
        throw new EnhancedException("error while invoking [build] method", e);
      }
    }
    throw new EnhancedException("unable to find class annotated with @EDomotic");
  }
  
  public void startClient(List<T> values) {
    // TODO
    OpenwebnetClient.startThread(values, config);
  }
  
}
