package com.alcedo.dpm;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class DPMReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        Toast.makeText(context, "DPM：可用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
        Toast.makeText(context, "DPM：不可用", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordChanged(Context context, Intent intent) {
        Toast.makeText(context, "密码己经改变", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordFailed(Context context, Intent intent) {
        Toast.makeText(context, "改变密码失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordSucceeded(Context context, Intent intent) {
        Toast.makeText(context, "改变密码成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordExpiring(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle user) {
        Toast.makeText(context, "密码过期", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onUserAdded(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle newUser) {
        Toast.makeText(context, "增加了新用户", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserRemoved(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle removedUser) {
        Toast.makeText(context, "移除了用户", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserSwitched(@NonNull Context context, @NonNull Intent intent, @NonNull UserHandle switchedUser) {
        Toast.makeText(context, "切换了用户", Toast.LENGTH_SHORT).show();
    }

}
