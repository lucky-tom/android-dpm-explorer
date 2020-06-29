package com.alcedo.dpm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alcedo.dpm.DPMApi;

public class DPMViewModel extends AndroidViewModel {

    DPMApi dpmApi;

    public DPMViewModel(@NonNull Application application) {
        super(application);
        dpmApi = new DPMApi(application);
    }


    public DPMApi getDpmApi() {
        return dpmApi;
    }
}
