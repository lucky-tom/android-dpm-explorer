package com.alcedo.dpm.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.alcedo.dpm.R;
import com.alcedo.dpm.viewmodel.DPMViewModel;

public class AppSettingsFragment extends PreferenceFragmentCompat {

    DPMViewModel dpmViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dpmViewModel = new ViewModelProvider(this).get(DPMViewModel.class);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.app_preferences, rootKey);
        initHiddenPreference();
    }

    /**
     * 隐藏/显示应用
     */
    public void initHiddenPreference() {
        Preference preference = findPreference("hidden");
        preference.setOnPreferenceClickListener((p) -> {
            AppDialogFragment dialogFragment = AppDialogFragment.newInstance("");
            dialogFragment.show(getParentFragmentManager(),"");
            return true;
        });

    }

}