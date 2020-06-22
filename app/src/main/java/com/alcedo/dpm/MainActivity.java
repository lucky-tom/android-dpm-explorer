package com.alcedo.dpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DPMApi dpmApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dpmApi = new DPMApi(this);

        if (dpmApi.isDeviceOwnerApp()){
            Toast.makeText(this, "是设备拥有者", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "不是设备拥有者", Toast.LENGTH_SHORT).show();
        }
    }
}