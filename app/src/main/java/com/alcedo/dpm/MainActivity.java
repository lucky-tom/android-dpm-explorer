package com.alcedo.dpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
/**
 * @file MainActivity.java
 *
 * @brief 主页面
 *
 * @author wdd
 *
 * @email 814668064@decard.com
 *
 * @date 2020/6/23
 *
 * @attention
 */
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