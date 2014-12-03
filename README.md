enhanced-domotic
================
The aim of the project is to write a domotic library in Java.

For now, only [OpenWebNet](http://www.myopen-legrandgroup.com/resources/own_protocol/default.aspx) protocol is supported.

### Work in progress...

Example
```java
EnhancedDomotic
  .config(config)
  .action(TURN_ON)
  .actionProperty(DIMER, 80)
  .device(LIGHT)
  .deviceProperty(ID, 11, 22)
  .deviceProperty(GROUP, 33)
  .execute();
```
