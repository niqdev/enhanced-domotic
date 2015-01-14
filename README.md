enhanced-domotic
================
The aim of the project is to write a domotic library in Java.

For now, only [OpenWebNet](http://www.myopen-legrandgroup.com/resources/own_protocol/default.aspx) protocol is supported.

Example with fluent syntax
```java
EnhancedDomotic.config(config)
  .type(COMMAND)
  .action(TURN_ON)
  .device(LIGHT)
  .deviceProperty(ID, 21)
  .execute();
```

[Here](https://github.com/niqdev/enhanced-domotic/tree/master/src/test/java/com/domotic/enhanced/openwebnet) for more examples.

Android development: closed [issue](https://github.com/niqdev/enhanced-domotic/issues/1).
