package com.domotic.enhanced.reflection;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;

import com.domotic.enhanced.Config;
import com.domotic.enhanced.DomoticException;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import dalvik.system.DexFile;

public class ReflectionApk implements EnhancedReflection {
  
  // TODO refactor: multimap<class, set<method>> or weakhashmap
  private final Set<Class<?>> classpathClasses;

  private ReflectionApk(Config config) {
    this.classpathClasses = scanPackage(config);
  }
  
  public static EnhancedReflection newInstance(Config config) {
    return new ReflectionApk(config);
  }
  
  private DexFile loadDexFile(Context context) {
    try {
      return new DexFile(context.getPackageCodePath());
    } catch (IOException e) {
      throw new DomoticException("unable to search for classes from current ClassLoader", e);
    }
  }
  
  private Set<Class<?>> scanPackage(Config config) {
    Context context = checkNotNull(config.context());
    final String path = checkNotNull(config.protocol().getPath());
    
    DexFile dex = loadDexFile(context);
    List<String> entries = Collections.list(checkNotNull(dex.entries()));
    
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    
    return FluentIterable
      .from(entries).filter(new Predicate<String>() {

        @Override
        public boolean apply(String input) {
          return StringUtils.startsWithIgnoreCase(input, path);
        }
      }).transform(new Function<String, Class<?>>() {

        @Override
        public Class<?> apply(String className) {
          try {
            return classLoader.loadClass(className);
          } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
          }
        }
      }).toSet();
  }

  @Override
  public Set<Method> getMethodsAnnotatedWith(Class<? extends Annotation> annotation) {
    Iterable<Method> methods = Sets.newHashSet();
    for (Class<?> clazz : classpathClasses) {
      methods = Iterables.concat(methods, Sets.newHashSet(clazz.getMethods()));
    }
    return FluentIterable.from(methods).filter(isMethodAnnotationPresent(annotation)).toSet();
  }

  @Override
  public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return FluentIterable.from(classpathClasses).filter(isTypeAnnotationPresent(annotation)).toSet();
  }
  
  /*
   * TODO refactor: isAnnotationPresent for both method and class
   */
  
  private Predicate<Method> isMethodAnnotationPresent(final Class<? extends Annotation> annotation) {
    return new Predicate<Method>() {
      
      @Override
      public boolean apply(Method input) {
        return input.isAnnotationPresent(annotation);
      }
    };
  }
  
  private Predicate<Class<?>> isTypeAnnotationPresent(final Class<? extends Annotation> annotation) {
    return new Predicate<Class<?>>() {

      @Override
      public boolean apply(Class<?> input) {
        return input.isAnnotationPresent(annotation);
      }
    };
  }

}
