package com.domotic.enhanced.client;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

import java.util.List;

import com.domotic.enhanced.Config;
import com.domotic.enhanced.syntax.Syntax;

public class Request<T> {
  
  private final Config config;
  
  private final Handler handler;
  
  private final List<T> values;
  
  private final Syntax<T> syntax;
  
  private Request(Build<T> build) {
    this.config = build.config;
    this.handler = build.handler;
    this.values = build.values;
    this.syntax = build.syntax;
  }
  
  /**
   *
   */
  public static class Build<T> {
    
    private Config config;
    
    private Handler handler;
    
    private List<T> values;
    
    private Syntax<T> syntax;
    
    public Build<T> config(Config config) {
      this.config = config;
      return this;
    }
    
    public Build<T> handler(Handler handler) {
      this.handler = defaultHandler(handler);
      return this;
    }
    
    public Build<T> values(List<T> values) {
      this.values = values;
      return this;
    }
    
    public Build<T> syntax(Syntax<T> syntax) {
      this.syntax = syntax;
      return this;
    }
    
    public Request<T> build() {
      validate();
      return new Request<T>(this);
    }
    
    private void validate() {
      checkNotNull(config);
      checkArgument(isNotEmpty(values));
      checkNotNull(syntax);
    }
    
    private static Handler defaultHandler(Handler handler) {
      if (handler != null) {
        return handler;
      }
      return new LogHandler();
    }
  }

  public Config getConfig() {
    return config;
  }

  public Handler getHandler() {
    return handler;
  }

  public List<T> getValues() {
    return values;
  }

  public Syntax<T> getSyntax() {
    return syntax;
  }
  
}
