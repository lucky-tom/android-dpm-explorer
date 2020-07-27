# Android 设备管理器

## 概述

Android DevicePolicyManager服务提供了提供了三种设备管理角色：

- Device Administration：设备管理员
- Profile Owner：配置文件所有者（需要接EMM提供商，连外网，付费）
- Device Owner：设备所有者

其中根据权限级别来划分 **DeviceAdmin < ProfileOwner < DeviceOwner**

本案重点讨论**DeviceOwner**的api

## 测试条件

| 设备   | 系统版本 |
| ------ | -------- |
| Nexus7 | 6.0.1    |

<u>有些api在android N或更高版本加入，需要标注</u>



### DeviceOwner的特点

1，一个设备只能设置一个**DeviceOwner**角色，设置后不能在设置取消（按钮置灰），不能卸载（卸载按钮还在，系统会提示你不能卸载）



2，要让一个app成为**DeviceOwner**，首先他得是一个**DeviceAdmin**，**DeviceAdmin**的配置流程请参考百度。



3，不必刻意去激活**DeviceAdmin**，系统在设置**DeviceOwner**的过程中会自动先激活**DeviceAdmin**，这也是**DeviceOwner**拥有**DeviceAdmin**所有能力的原因。



4，第三方应用和系统应用都没有权限设置**DeviceOwner**，Android官方值提供两种设置**DeviceOwner**应用的方法：

- adb shell命令
- 通过nfc（请查阅google 官方demo）



## 使能DeviceOwner

本案通过 adb shell指令来使能app成为DeviceOwner：

```java
adb shell dpm set-device-owner  com.alcedo.dpm/.DPMReceiver
```

**问题1：设备上存在账户**

```
java.lang.IllegalStateException: Not allowed to set the device owner because there are already some accounts on the device
```

解决方法：1：删除所有账户，使账户总数为0

```java
1，adb shell dumpsys account   列出所有账户
2，adb shell pm hide com.xxx.xxx 隐藏提供账户的app (需要先adb root)
3，adb shell dpm set-device-owner  com.alcedo.dpm/.DPMReceiver
4，adb shell pm unhide com.xxx.xxx 恢复提供账户的app(需要先adb root)

如果2，4不行，就adb shell pm remove-user USER_ID 根据id删除user。有一种情况，提示不能删除user_id为0的，只能参考方法2了。
```

解决方法：2，恢复出厂设置，解千愁（哭脸）。

------

**问题2：设备之前已经拥有device owner**

```
java.lang.IllegalStateException: Trying to set device owner but device is already provisioned.
```

解决方法：参考下文，怎样取消DeviceOwner



## 取消DeviceOwner

目前已知方法有两种：

1，之前的 device  owner app 调用 devicePolicyManager.clearDeviceOwnerApp。

2，恢复出厂设置。

------

## 参考文章：

[Android DeviceOwner 应用的能力] : https://blog.csdn.net/visionliao/article/details/84767383	"博主：怒草"

[一键设置 DeviceAdmin/ProfileOwner/DeviceOwner 应用] : https://blog.csdn.net/visionliao/article/details/84768035	"博主：怒草"

[Android之解决Gigaset手机不能设置DeviceOwner权限提示already provisioned问题] : https://blog.csdn.net/u011068702/article/details/53191952?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-4.nonecase&amp;depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-4.nonecase	"博主：chen.yu"

[android权限级别探索（三），设置 DeviceOwner及api收集] : https://blog.csdn.net/qq_35501560/article/details/105948631?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.nonecase&amp;depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-3.nonecase	"博主：炭烤葫芦娃"

[安卓玩机@太极 免ROOT使用Xposed模块] : https://www.cnblogs.com/qq438649499/p/12096017.html	"博主：默月"


