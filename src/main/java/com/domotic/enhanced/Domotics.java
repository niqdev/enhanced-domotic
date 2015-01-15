package com.domotic.enhanced;

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

import org.apache.commons.beanutils.MethodUtils;

import com.domotic.enhanced.client.Client;
import com.domotic.enhanced.client.Handler;
import com.domotic.enhanced.client.Request;
import com.domotic.enhanced.domain.EAction;
import com.domotic.enhanced.domain.EAction.ActionType;
import com.domotic.enhanced.domain.EActionProperty;
import com.domotic.enhanced.domain.EActionProperty.ActionPropertyType;
import com.domotic.enhanced.domain.EClient;
import com.domotic.enhanced.domain.EDevice;
import com.domotic.enhanced.domain.EDevice.DeviceType;
import com.domotic.enhanced.domain.EDeviceProperty;
import com.domotic.enhanced.domain.EDeviceProperty.DevicePropertyType;
import com.domotic.enhanced.domain.EDomotic;
import com.domotic.enhanced.reflection.EnhancedReflection;
import com.domotic.enhanced.reflection.ReflectionFacade;
import com.domotic.enhanced.syntax.Action;
import com.domotic.enhanced.syntax.Device;
import com.domotic.enhanced.syntax.Property;
import com.domotic.enhanced.syntax.Syntax;
import com.domotic.enhanced.syntax.SyntaxComposer;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Helper class to build syntax using reflections.
 * 
 * @author niqdev
 */
public class Domotics<T> {
  
  private final EnhancedReflection reflection;

  private Domotics(Config config) {
    this.reflection = new ReflectionFacade(config);
  }

  /**
   * 
   */
  public static <T> Domotics<T> newInstance(Config config) {
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

  /**
   * Only one value expected.
   * 
   * @throws NoSuchElementException
   * @throws IllegalArgumentException
   */
  private <A extends Annotation, E extends Enum<E>> Method
    findAnnotatedMethod(Class<A> annotationClass, E enumType) {
    
    checkNotNull(annotationClass);
    checkNotNull(enumType);
    Set<Method> candidate = Sets.filter(findMethods(annotationClass), isValidType(annotationClass, enumType));
    return Iterables.getOnlyElement(candidate);
  }
  
  /**
   * Only one value expected.
   * 
   * @throws NoSuchElementException
   * @throws IllegalArgumentException
   */
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
   * @see com.domotic.enhanced.Domotics.invokeGetAnnotation(Class<A>, T)
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
      throw new DomoticException("invoke [getAnnotation] method cause error", e);
    }
  }
  
  @SuppressWarnings("unchecked")
  private static <E extends Enum<E>> E annotationValue(Annotation annotation, Method attribute) {
    // expected annotation with VALUE attribute
    checkArgument("value".equals(attribute.getName()));
    try {
      return (E) attribute.invoke(annotation);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new DomoticException("expected annotation attribute [value]", e);
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
      throw new DomoticException("invoke static method cause error", e);
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
   * Instantiate a {@code new SyntaxComposer(Domotics<T>, Syntax<T>)} and
   * invoke {@code com.domotic.enhanced.syntax.SyntaxComposer.compose()}.
   */
  @SuppressWarnings("unchecked")
  public List<T> build(Syntax<T> syntax) {
    Class<?> klass = findAnnotatedClass(EDomotic.class, syntax.syntaxType);
    if (!SyntaxComposer.class.isAssignableFrom(klass)) {
      throw new DomoticException("unable to find a valid class");
    }
    
    try {
      Constructor<?> constructor = klass.getDeclaredConstructor(Domotics.class, Syntax.class);
      constructor.setAccessible(true);
      SyntaxComposer<T> composer = (SyntaxComposer<T>) constructor.newInstance(this, syntax);
      
      return composer.compose();
    } catch (NoSuchMethodException | SecurityException | InstantiationException
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new DomoticException("error while composing syntax", e);
    }
  }
  
  /**
   * Start a new thread and execute the request depending on
   * the underline {@link com.domotic.enhanced.domain.Protocol}.
   */
  @SuppressWarnings("unchecked")
  public void startClient(Request<T> request, Handler<T> handler) {
    Class<?> clazz = findAnnotatedClass(EClient.class, request.getConfig().protocol());
    if (!Client.class.isAssignableFrom(clazz)) {
      throw new DomoticException("unable to find a valid class");
    } 
    
    try {
      Constructor<?> constructor = clazz.getDeclaredConstructor(Request.class, Handler.class);
      constructor.setAccessible(true);
      Client<T> client = (Client<T>) constructor.newInstance(request, handler);
      
      new Thread(client).start();
    } catch (NoSuchMethodException | SecurityException | InstantiationException
        | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      throw new DomoticException("error while starting client", e);
    }
  }

}
