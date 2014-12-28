enhanced-domotic
================
The aim of the project is to write a domotic library in Java.

For now, only [OpenWebNet](http://www.myopen-legrandgroup.com/resources/own_protocol/default.aspx) protocol is supported.

Example with fluent syntax
```java
EnhancedDomotic
  .config(config)
  .action(TURN_ON)
  .actionProperty(DIMER, 80)
  .device(LIGHT)
  .deviceProperty(ID, 11, 22)
  .deviceProperty(GROUP, 3)
  .execute();
```
or simply
```java  
EnhancedDomotic.<String>raw(config, "*1*1*21##");
```

[Here](https://github.com/niqdev/enhanced-domotic/tree/master/enhanced-domotic-lib/src/test/java/com/enhanced/domotic/openwebnet) for more examples.

Android development: open [issue](https://github.com/niqdev/enhanced-domotic/issues/1).
