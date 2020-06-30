package com.alcedo.dpm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.alcedo.dpm.DPMApi;
/**
 * @file DPMViewModel.java
 *
 * @brief vm层
 *
 * @author wudd
 *
 * @email 814668064@qq.com
 *
 * @date 20-6-30
 *
 * @attention {使用此类需要注意什么}
 */
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
