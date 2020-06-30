package com.alcedo.dpm.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.alcedo.dpm.R;
import com.alcedo.dpm.viewmodel.DPMViewModel;
/**
 * @file SwitchSettingsFragment.java
 *
 * @brief 一些启用/禁用的功能控制界面
 *
 * @author wudd
 *
 * @email 814668064@qq.com
 *
 * @date 20-6-30
 *
 * @attention {使用此类需要注意什么}
 */
public class SwitchSettingsFragment extends PreferenceFragmentCompat {


    DPMViewModel dpmViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dpmViewModel = new ViewModelProvider(this).get(DPMViewModel.class);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.switch_preferences, rootKey);

        initCameraPreference();
        initScreenCapturePreference();
        initVolumeMutedPreference();
        initAutoTimeRequiredPreference();
        initCrossProfileCallerIdPreference();
        initCrossProfileContactsSearchPreference();
    }


    /**
     * 处理相机偏好
     */
    public void initCameraPreference() {

        SwitchPreferenceCompat preferenceCompat = findPreference("camera");
        if(dpmViewModel.getDpmApi().getCameraDisabled()){
            preferenceCompat.setChecked(true);
        }else {
            preferenceCompat.setChecked(false);
        }
        preferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(),""+newValue,Toast.LENGTH_SHORT).show();
            dpmViewModel.getDpmApi().setCameraDisabled((Boolean) newValue);
            return true;
        });

    }


    /**
     * 处理截屏偏好
     */
    public void initScreenCapturePreference() {

        SwitchPreferenceCompat preferenceCompat = findPreference("screenCapture");
        if(dpmViewModel.getDpmApi().getScreenCaptureDisabled()){
            preferenceCompat.setChecked(true);
        }else {
            preferenceCompat.setChecked(false);
        }
        preferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(),""+newValue,Toast.LENGTH_SHORT).show();
            dpmViewModel.getDpmApi().setScreenCaptureDisabled((Boolean) newValue);
            return true;
        });

    }


    /**
     * 处理静音偏好
     */
    public void initVolumeMutedPreference() {

        SwitchPreferenceCompat preferenceCompat = findPreference("volumeMuted");
        if(dpmViewModel.getDpmApi().isMasterVolumeMuted()){
            preferenceCompat.setChecked(true);
        }else {
            preferenceCompat.setChecked(false);
        }
        preferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(),""+newValue,Toast.LENGTH_SHORT).show();
            dpmViewModel.getDpmApi().setMasterVolumeMuted((Boolean) newValue);
            return true;
        });

    }


    /**
     * 处理修改系统时间偏好
     */
    public void initAutoTimeRequiredPreference() {

        SwitchPreferenceCompat preferenceCompat = findPreference("systime");
        if(dpmViewModel.getDpmApi().getAutoTimeRequired()){
            preferenceCompat.setChecked(true);
        }else {
            preferenceCompat.setChecked(false);
        }
        preferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(),""+newValue,Toast.LENGTH_SHORT).show();
            dpmViewModel.getDpmApi().setAutoTimeRequired((Boolean) newValue);
            return true;
        });

    }



    /**
     * 处理来电显示功能偏好
     */
    public void initCrossProfileCallerIdPreference() {

        SwitchPreferenceCompat preferenceCompat = findPreference("profileCallerId");

        if(dpmViewModel.getDpmApi().getCrossProfileCallerIdDisabled()){
            preferenceCompat.setChecked(true);
        }else {
            preferenceCompat.setChecked(false);
        }
        preferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(),""+newValue,Toast.LENGTH_SHORT).show();
            dpmViewModel.getDpmApi().setCrossProfileCallerIdDisabled((Boolean) newValue);
            return true;
        });

    }


    /**
     * 处理搜索联系人功能偏好
     */
    public void initCrossProfileContactsSearchPreference() {

        SwitchPreferenceCompat preferenceCompat = findPreference("profileContactsSearch");

        if(Build.VERSION.SDK_INT<24){
            preferenceCompat.setEnabled(false);
            preferenceCompat.setSummary("此功能需要7.0以上支持");
            return;
        }


        if(dpmViewModel.getDpmApi().getCrossProfileContactsSearchDisabled()){
            preferenceCompat.setChecked(true);
        }else {
            preferenceCompat.setChecked(false);
        }
        preferenceCompat.setOnPreferenceChangeListener((preference, newValue) -> {
            Toast.makeText(getContext(),""+newValue,Toast.LENGTH_SHORT).show();
            dpmViewModel.getDpmApi().setCrossProfileContactsSearchDisabled((Boolean) newValue);
            return true;
        });

    }

}