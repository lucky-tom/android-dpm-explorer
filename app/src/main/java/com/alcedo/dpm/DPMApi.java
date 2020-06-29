package com.alcedo.dpm;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.List;

import static android.app.admin.DevicePolicyManager.PERMISSION_GRANT_STATE_DEFAULT;
import static android.app.admin.DevicePolicyManager.PERMISSION_POLICY_PROMPT;

/**
 * @file DPMApi.java
 *
 * @brief DevicePolicyManager的接口探索和测试验证
 *
 * @author wdd
 *
 * @email 814668064@qq.com
 *
 * @date 2020/6/22
 *
 * @attention {使用此类需要注意什么}
 */
public class DPMApi {

    private DevicePolicyManager dpm;
    private ComponentName componentName;
    private Context context;


    public DPMApi (Context c){
        this.context=c;
        //获取设备管理服务
        dpm=(DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(context, DPMReceiver.class);
    }


    /**
     * 查看是否已经获得管理者的权限
     * @return true : 是  false ： 否
     */
    public boolean isAdminActive() {
        return dpm.isAdminActive(componentName);
    }

    /**
     * 查看某个app是否已经是设备拥有者，设备拥有者（DeviceOwner）无法被卸载，
     * @return true : 是  false ： 否
     */
    public boolean isDeviceOwnerApp(String packageName) {
        return dpm.isDeviceOwnerApp(packageName);
    }
    /**
     * 查看本应用是否已经是设备拥有者，设备拥有者（DeviceOwner）无法被卸载，
     * @return true : 是  false ： 否
     */
    public boolean isDeviceOwnerApp() {
        return dpm.isDeviceOwnerApp(context.getPackageName());
    }

    /**
     * app申请激活成为设备管理器 （Device Administration）
     */
    public void activateDeviceAdmin() {

        Intent intent = new Intent(
                DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//                    DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
//            intent.putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME,
                componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "请激活设备管理者");
        context.startActivity(intent);
    }



    /**
     * 移除app，不再是设备管理器， 如果不移除程序 APP无法被卸载
     */
    public void removeActiveAdmin() {
        dpm.removeActiveAdmin(componentName);

    }


    /**
     * 移除app,不再是设备拥有者，
     * @param packageName
     */
    public void clearDeviceOwnerApp(String packageName){
        dpm.clearDeviceOwnerApp(packageName);
    }


    /**
     * 设置解锁密码规则
     *
     *  PASSWORD_QUALITY_ALPHABETIC     //用户输入的密码必须要有字母（或者其他字符）。
     *  PASSWORD_QUALITY_ALPHANUMERIC   //用户输入的密码必须要有字母和数字。
     *  PASSWORD_QUALITY_NUMERIC        // 用户输入的密码必须要有数字
     *  PASSWORD_QUALITY_SOME           // 由设计人员决定的。
     *  PASSWORD_QUALITY_UNSPECIFIED    // 对密码没有要求。
     */
    public void setPasswordQuality(int  mode) {

        if (dpm.isAdminActive(componentName)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
            dpm.setPasswordQuality(componentName, mode);
            context.startActivity(intent);
        }else {
            Toast.makeText(context, "请先激活成为-->设备管理器", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * 立刻锁屏
     */
    public void LockNow() {
        if (dpm.isAdminActive(componentName)) {
            dpm.lockNow();
        }else {
            Toast.makeText(context, "请先激活成为-->设备管理器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 设置多长时间后锁屏
     * @param time 毫秒/单位
     */
    public void setMaximumTimeToLock(long time) {
        if (dpm.isAdminActive(componentName)) {
            dpm.setMaximumTimeToLock(componentName, time);
        }else {
            Toast.makeText(context, "请先激活成为-->设备管理器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 设置锁屏新的密码
     * 设备管理者
     * @attention Android N之前版本可任意设置或重置设备密码,N之后只能为无密码设备设置初始密码，而不能重置或清除已有的设备密码。
     * @param password 密码
     */
    public void resetPassword(String password) {
        if (dpm.isAdminActive(componentName)) {
            dpm.resetPassword(password, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
        }else {
            Toast.makeText(context, "请先激活成为-->设备管理器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 禁用状态栏
     * @param disabled
     * true : 禁用  false: 启用
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean setStatusBarDisabled(boolean disabled) {
        if (isDeviceOwnerApp()) {
            return dpm.setStatusBarDisabled(componentName, disabled);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * 禁用/启用相机
     * @param enable
     * true : 禁用  false: 启用
     */
    public void setCameraDisabled(boolean enable) {

        if (isAdminActive()) {
            dpm.setCameraDisabled(componentName,enable);
        }else {
            Toast.makeText(context, "请先激活成为-->设备管理器", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取相机是否被禁用
     * @return true : 已禁用  false: 已启用
     */
    public boolean getCameraDisabled() {
        return dpm.getCameraDisabled(componentName);
    }


    /**
     * 重启设备
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void reboot() {
        if (isAdminActive()) {
            dpm.reboot(componentName);
        }else {
            Toast.makeText(context, "请先激活成为-->设备管理器", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * 禁止截屏
     */
    public void setScreenCaptureDisabled(boolean enable) {
        if (isDeviceOwnerApp()) {
            dpm.setScreenCaptureDisabled (componentName,enable);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 禁止截屏是否开启
     *
     * true:禁止截屏  false:可以截屏
     */
    public boolean getScreenCaptureDisabled() {
        return dpm.getScreenCaptureDisabled(componentName);
    }




    /**
     * 禁止/启用，据网络自动确定系统时间，设置true后，即使在设置里面打开自动确定日期和时间，有网络也不会同步时间。
     * true:禁止修改  false:可以修改
     */
    public void setAutoTimeRequired(boolean required) {
        if (isDeviceOwnerApp()) {
            dpm.setAutoTimeRequired(componentName,required);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取是否已经禁用据网络自动确定系统时间
     *
     */
    public boolean getAutoTimeRequired() {
        return dpm.getAutoTimeRequired();
    }





    /**
     * 恢复出厂设置
     *
     * WIPE_EXTERNAL_STORAGE 表示也要清除SD卡的数据;
     * WIPE_RESET_PROTECTION_DATA，表示只清除内部空间，不清除SD卡的数据
     */
    public void wipeData(int mode) {
        if (isDeviceOwnerApp()) {
            dpm.wipeData(mode);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 在锁屏页面显示一段信息
     *
     * @param info
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDeviceOwnerLockScreenInfo( CharSequence info) {
        if (isDeviceOwnerApp()) {
            dpm.setDeviceOwnerLockScreenInfo(componentName, info);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取锁屏显示的信息
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private CharSequence getDeviceOwnerLockScreenInfo() {
        CharSequence res = null;
        if (isDeviceOwnerApp()) {
            res = dpm.getDeviceOwnerLockScreenInfo();
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return res;
    }


    /**
     * 启用/禁用app卸载
     *
     * @param packageName 包名
     * @param uninstallBlocked true:不可卸载，false:可以卸载
     */
    public void setUninstallBlocked( String packageName,
                                     boolean uninstallBlocked) {
        if(isDeviceOwnerApp()) {
            dpm.setUninstallBlocked(componentName, packageName, uninstallBlocked);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 查询app是否被启用禁止卸载
     *
     * @param packageName 包名
     * @return true:不可卸载，false:可以卸载
     */
    public boolean isUninstallBlocked( String packageName) {

        if (isDeviceOwnerApp()) {
            return dpm.isUninstallBlocked(componentName, packageName);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }

        return false;
    }



    /**
     * 隐藏/显示app
     *
     * nexus7上的测试效果为，如果设置一个app为隐藏，app图标找不到，在设置--》应用列表可以找到。
     * adb shell pm unhide或 hide 需要先adb  root，否则
     * 会报“ java.lang.SecurityException: Neither user 2000 nor current process has android.permission.MANAGE_USERS.”
     *
     * @param packageName 包名
     * @param hiddlen true:隐藏，false:显示
     */
    public boolean setApplicationHidden(String packageName, boolean hiddlen) {
        if(isDeviceOwnerApp()) {
           return dpm.setApplicationHidden(componentName, packageName, hiddlen);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * 查询app是否被隐藏
     *
     * @param packageName 包名
     * @return true:隐藏，false:没有隐藏
     */
    public boolean isApplicationHidden(String packageName) {
        return dpm.isApplicationHidden(componentName, packageName);
    }


    /**
     * 挂起应用
     *
     * @param packageName 包名
     * @param suspended true:挂起  false:取消挂起
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setPackagesSuspended(String[] packageName, boolean suspended) {
        if(isDeviceOwnerApp()) {
            dpm.setPackagesSuspended(componentName, packageName, suspended);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 查询app是否被挂起
     *
     * @param packageName 包名
     * @return true:被挂起，false:没有被挂起
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean isPackageSuspended(String packageName) throws PackageManager.NameNotFoundException {
        return dpm.isPackageSuspended(componentName, packageName);
    }




    /**
     * 隐藏/显示app
     * 被隐藏的应用可以在设置应用列表中找到。被隐藏的应用重新安装后，依然无法显示
     * @param packageName 包名
     * @param hidden true:隐藏，false:显示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String[]  setApplicationHidden(String[] packageName, boolean hidden) {
        if(isDeviceOwnerApp()) {
           return dpm.setPackagesSuspended(componentName, packageName, hidden);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }

        return null;
    }


    /**
     *这个可以用来将屏幕锁定在你的应用上面，例如车站的电子展牌，可触摸的电子屏广告等等。
     * 通常我们可以在系统设置-安全-屏幕固定中来设置固定屏幕，或者使用ActivityManager的startLockTask()方法
     * ，以上方式会给用户发出信息通知用户即将固定屏幕，并提供退出固定屏幕的方法。
     *
     * 而如果在使用startLockTask之前调用setLockTaskPackages方法，会不发送通知直接固定屏幕，并且隐藏home键和菜单键（如果是虚拟按键的话）。
     * 无法通过长按返回键退出固定屏幕（测试系统为氧os安卓5.0上依然可以长按返回键退出）。
     * 在无法使用长按返回键的情况下有两种方法退出，重启设备或者使用Activity.stopLockTask()。
     * @param packages
     */
    public void setLockTaskPackages(String[] packages) {
        if (packages == null){
            return;
        }
        if(isDeviceOwnerApp()) {
            dpm.setLockTaskPackages(componentName, packages);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 获取固定屏幕的包名
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String[] getLockTaskPackages() {
       return dpm.getLockTaskPackages(componentName);
    }


    /**
     * 设置用户限制
     * 可以配置的功能有很多，包括限制音量调节，应用安装卸载，WIFI蓝牙设置，恢复出厂，添加删除用户，电话短信等等，很强大的的方法
     * @param key
     */
    public void addUserRestriction(String key) {
        if(isDeviceOwnerApp()) {
            dpm.addUserRestriction(componentName, key);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 设置用户限制
     * 可以配置的功能有很多，包括限制音量调节，应用安装卸载，WIFI蓝牙设置，恢复出厂，添加删除用户，电话短信等等，很强大的的方法
     * @param key
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Bundle getUserRestrictions(String key) {
        return dpm.getUserRestrictions(componentName);
    }


    /**
     * 设置可以使用辅助功能的应用
     *
     * 使用此方法后，所有不在此方法设置中的应用都不得使用辅助功能，在系统设置-辅助功能里的选项会变为灰色，但是对系统应用无效。
     *
     * 参数传null可以取消所有限定。
     * @param packageNames 可以使用辅助功能的应用包名集合
     */
    public void setPermittedAccessibilityServices(List<String> packageNames){
        if(isDeviceOwnerApp()) {
            dpm.setPermittedAccessibilityServices(componentName, packageNames);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 查询可以使用辅助功能的应用
     *
     * @return 可以使用辅助功能的应用包名集合
     */
    public List<String> getPermittedAccessibilityServices() {
        return dpm.getPermittedAccessibilityServices(componentName);
    }


    /**
     * 设置可以使用的输入法
     *
     * 此方法只对第三方输入法有效，系统输入法不会被禁用，被禁用的输入法在设置里呈灰色显示。
     *
     * 参数传null可以取消限定。
     *
     * @param packageNames 第三方输入法app包名
     */
    public void setPermittedInputMethods(List<String> packageNames){
        if(isDeviceOwnerApp()) {
            dpm.setPermittedInputMethods(componentName, packageNames);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 查询可以使用的第三方输入法
     * @return 第三方输入法app包名集合
     */
    public List<String> getPermittedInputMethods(){
       return dpm.getPermittedInputMethods(componentName);
    }


    /**
     * 全局设置
     *
     * 包括ADB开关，流量开关，手机连接电脑时是否可以开启USB存储设备模式，开发者模式开关等等。
     *
     * @param setting
     *
     * Settings.Global.ADB_ENABLED                      ：adb开关
     * Settings.Global.AIRPLANE_MODE_ON                 ：飞行模式开关
     * Settings.Global.AIRPLANE_MODE_RADIOS             ：
     * Settings.Global.ALWAYS_FINISH_ACTIVITIES         ：
     * Settings.Global.ANIMATOR_DURATION_SCALE          ：
     * Settings.Global.AUTO_TIME                        ： 自动校准时间（联网）  1=yes, 0=no
     * Settings.Global.AUTO_TIME_ZONE                   ： 自动                1=yes, 0=no
     * Settings.Global.BLUETOOTH_ON                     ：
     * Settings.Global.DATA_ROAMING                     ： 0=disabled. 1=enabled
     * Settings.Global.DEBUG_APP                        ：
     * Settings.Global.DEVELOPMENT_SETTINGS_ENABLED     ：开发者调试模式开关
     * Settings.Global.DEVICE_PROVISIONED               ： 
     * Settings.Global.HTTP_PROXY
     * Settings.Global.INSTALL_NON_MARKET_APPS
     * Settings.Global.MODE_RINGER
     * Settings.Global.NETWORK_PREFERENCE
     * Settings.Global.RADIO_BLUETOOTH
     * Settings.Global.RADIO_CELL
     * Settings.Global.RADIO_NFC
     * Settings.Global.RADIO_WIFI
     * Settings.Global.SHOW_PROCESSES
     * Settings.Global.STAY_ON_WHILE_PLUGGED_IN
     * Settings.Global.SYS_PROP_SETTING_VERSION
     * Settings.Global.TRANSITION_ANIMATION_SCALE
     * Settings.Global.USB_MASS_STORAGE_ENABLED
     * Settings.Global.USE_GOOGLE_MAIL
     * Settings.Global.WAIT_FOR_DEBUGGER
     * Settings.Global.WIFI_DEVICE_OWNER_CONFIGS_LOCKDOWN
     * Settings.Global.WIFI_MAX_DHCP_RETRY_COUNT
     * Settings.Global.WIFI_MOBILE_DATA_TRANSITION_WAKELOCK_TIMEOUT_MS
     * Settings.Global.WIFI_NETWORKS_AVAILABLE_NOTIFICATION_ON
     * Settings.Global.WIFI_NETWORKS_AVAILABLE_REPEAT_DELAY
     * Settings.Global.WIFI_NUM_OPEN_NETWORKS_KEPT
     * Settings.Global.WIFI_ON
     * Settings.Global.WIFI_SLEEP_POLICY
     * Settings.Global.WIFI_SLEEP_POLICY_DEFAULT
     * Settings.Global.WIFI_SLEEP_POLICY_NEVER
     * Settings.Global.WIFI_SLEEP_POLICY_NEVER_WHILE_PLUGGED
     * Settings.Global.WIFI_WATCHDOG_ON
     * Settings.Global.WINDOW_ANIMATION_SCALE
     *
     *
     * @param value
     */
    public void setGlobalSetting(String setting,String value){
        if(isDeviceOwnerApp()) {
            dpm.setGlobalSetting(componentName, setting,value);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 安全设置
     *
     * 包括默认输入法，阻止安装未知来源应用，地理位置等等。
     *
     *
     * @param setting
     * @param value
     */
    public void setSecureSetting(String setting,String value){
        if(isDeviceOwnerApp()) {
            dpm.setSecureSetting(componentName, setting,value);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 单一应用权限设置
     * @param packageName 包名
     * @param permission  权限
     * @param grantState
     *
     * PERMISSION_GRANT_STATE_DEFAULT  默认
     * PERMISSION_GRANT_STATE_DENIED   自动拒绝
     * PERMISSION_GRANT_STATE_GRANTED  自动赋予
     *
     *
     * @return true:成功 false:失败
     *

     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean setPermissionGrantState(String packageName, String permission, int grantState){

        if(isDeviceOwnerApp()) {
            return dpm.setPermissionGrantState(componentName, packageName,permission,grantState);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * 查询单一app权限赋予方式
     * @return
     *
     * PERMISSION_GRANT_STATE_DEFAULT  默认
     * PERMISSION_GRANT_STATE_DENIED   自动拒绝
     * PERMISSION_GRANT_STATE_GRANTED  自动赋予
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public int getPermittedInputMethods(String packageName, String permission){
        return dpm.getPermissionGrantState(componentName,packageName,permission);
    }


    /**
     * 全部应用权限策略
     *
     * PERMISSION_POLICY_PROMPT      用户确认
     * PERMISSION_POLICY_AUTO_DENY   自动拒绝
     * PERMISSION_POLICY_AUTO_GRANT  自动赋予
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setPermissionGrantState(int policy){
        if(isDeviceOwnerApp()) {
            dpm.setPermissionPolicy(componentName,policy);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 全部应用权限策略
     *
     * @return
     * PERMISSION_POLICY_PROMPT      用户确认
     * PERMISSION_POLICY_AUTO_DENY   自动拒绝
     * PERMISSION_POLICY_AUTO_GRANT  自动赋予
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public int  getPermissionPolicy(int policy){
        return dpm.getPermissionPolicy(componentName);
    }


    /**
     * 获取wifi 地址
     *
     * @return  wifi mac地址
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String  getWifiMacAddress(int policy){
        return dpm.getWifiMacAddress(componentName);
    }


    /**
     * 设置静音
     * @param on
     *
     * true:设置静音  false:关闭静音
     */
    public void setMasterVolumeMuted(boolean on){
        if(isDeviceOwnerApp()) {
            dpm.setMasterVolumeMuted(componentName,on);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 是否静音
     * true:设置静音  false:关闭静音
     */
    public boolean isMasterVolumeMuted(){
        if(isDeviceOwnerApp()) {
            return dpm.isMasterVolumeMuted(componentName);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return false;
    }





    /**
     * 禁止/开启搜索联系人功能
     * @param on
     *
     * true:设置静音  false:关闭静音
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCrossProfileContactsSearchDisabled(boolean on){
        if(isDeviceOwnerApp()) {
            dpm.setCrossProfileContactsSearchDisabled(componentName,on);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询搜索联系人功能状态
     * true:禁用  false:启用
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean getCrossProfileContactsSearchDisabled(){
        if(isDeviceOwnerApp()) {
            return dpm.getCrossProfileContactsSearchDisabled(componentName);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return false;
    }




    /**
     * 禁止/开启来电显示功能
     * @param on
     *
     * true:禁用  false:启用
     */
    public void setCrossProfileCallerIdDisabled(boolean on){
        if(isDeviceOwnerApp()) {
            dpm.setCrossProfileCallerIdDisabled(componentName,on);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 查询来电显示功能状态
     * true:禁用  false:启用
     */
    public boolean getCrossProfileCallerIdDisabled(){
        if(isDeviceOwnerApp()) {
            return dpm.getCrossProfileCallerIdDisabled(componentName);
        }else {
            Toast.makeText(context, "请先激活成为-->设备拥有者", Toast.LENGTH_SHORT).show();
        }
        return false;
    }



}
