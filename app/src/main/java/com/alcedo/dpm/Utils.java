package com.alcedo.dpm;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<String> getAllPackage(Context context){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfoList =  pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        List<String> stringList = new ArrayList<>();
        for(PackageInfo info:packageInfoList){
            stringList.add(info.packageName);
        }
        return stringList;
    }



//    public static List<String> getPermisson(Context context,String packageName)  {
//        PackageManager pm = context.getPackageManager();
//
//        try {
//
//            PackageInfo pi = pm.getPackageInfo(packageName, 0);
//
//            PackageInfo pkgInfo = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
//            String permissions[] = pkgInfo.requestedPermissions;
//
//            List<String> stringList = new ArrayList<>();
//            for(String permissionInfo:permissions){
//
//                PermissionInfo tmpPermInfo = pm.getApplicationIcon(packageName, 0);
//            }
//
//        }catch (PackageManager.NameNotFoundException e){
//            e.printStackTrace();
//        }
//
//        return stringList;
//    }

}
