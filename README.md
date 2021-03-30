## JConsole Plugin For XPocket
![XPocket](resourse/xpocket.jpg)

### JConsole Plugin For XPocket
#### 简介
JConsole 是一个内置Java性能分析器，用于对JVM中内存，线程和类等的监控，这款工具的好处在于，占用系统资源少，而且结合Jstat，可以有效监控到java内存的变动情况，以及引起变动的原因。在项目追踪内存泄露问题时，很实用。

#### 操作指南
 Command-Name                   Command-Description
   attach                       attach <local jvm pid>
  
 Tips:
 1.使用help获取帮助信息。
 2.更详细的功能，请attach一个进程后使用
注：插件化后的JConsole相比gui版本的JConsole在显示与操作上有所区别，但是数据的采集方式是一样的。

更多操作以及介绍请参考[官方介绍](https://docs.oracle.com/javase/7/docs/technotes/guides/management/jconsole.html)

[XPocket主框架](https://github.com/perfma/xpocket)

[插件下载](https://plugin.xpocket.perfma.com/plugin/55)