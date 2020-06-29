package com.alcedo.dpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alcedo.dpm.databinding.ActivityMainBinding;
import com.alcedo.dpm.ui.AppSettingsFragment;
import com.alcedo.dpm.ui.SwitchSettingsFragment;
import com.google.android.material.navigation.NavigationView;

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
    ActivityMainBinding binding;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        binding = ActivityMainBinding.inflate(layoutInflater);
        setContentView(binding.getRoot());

//        setContentView(R.layout.activity_main);
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        getWindow().setStatusBarColor(Color.TRANSPARENT);


        //设置toolbar
        binding.toolbar.setTitle("哈哈");
        binding.toolbar.setTitleTextColor(Color.WHITE);
        //可在toolbar上设置图标来作为侧滑的默认图标
        binding.toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> toggle());


        //设置滑动栏效果
        toggle = new ActionBarDrawerToggle(this,binding.drawer,R.string.app_name,R.string.app_name);
        // (被遮挡部分的)阴影部分的颜色
        binding.drawer.setScrimColor(Color.parseColor("#55000000"));
        // 设置边缘颜色
        binding.drawer.setDrawerShadow(new ColorDrawable(Color.parseColor("#22000000")), Gravity.RIGHT);
        toggle.syncState();

        //滑动栏，item点击
        binding.navView.setNavigationItemSelectedListener(new MyNavigationItemSelectedListener());


        dpmApi = new DPMApi(this);

        if (dpmApi.isDeviceOwnerApp()){
            Toast.makeText(this, "是设备拥有者", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "不是设备拥有者", Toast.LENGTH_SHORT).show();
        }


        // 有-为未选中的颜色, 没有-为选中的颜色
        int[][] states = new int[][]{new int[]{-android.R.attr.state_checked},new int[]{android.R.attr.state_checked} };
        int[] colors = new int[]{getResources().getColor(R.color.colorPrimaryDark),  getResources().getColor(R.color.colorAccent) };
        ColorStateList colorStateList = new ColorStateList(states, colors);
        binding.navView.setItemIconTintList(colorStateList);
        binding.navView.setItemTextColor(colorStateList);
        //去除图标颜色显示规则, 显示为原色
        binding.navView.setItemIconTintList(null);
//        String packge = "com.decard.loganservice";
//        boolean is = dpmApi.setPermissionGrantState(packge, Manifest.permission.WRITE_EXTERNAL_STORAGE, DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED);
//        Toast.makeText(this, "读写sd卡："+is, Toast.LENGTH_SHORT).show();
//        boolean is2 = dpmApi.setPermissionGrantState(packge, Manifest.permission.READ_PHONE_STATE, DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED);
//        Toast.makeText(this, "读取手机状态："+is2, Toast.LENGTH_SHORT).show();

        switchFragment("tag_switch");
    }


    void switchFragment(String tag){
        switch (tag){

            case "tag_switch":
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new SwitchSettingsFragment(),tag).commit();
                break;

            case "tag_app":
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new AppSettingsFragment(),tag).commit();
                break;
        }

    }


    void switchFragment(int id){
        switch (id){

            case R.id.nav_switch:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new SwitchSettingsFragment(),"").commit();
                break;

            case R.id.nav_app:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new AppSettingsFragment(),"").commit();
                break;
        }

    }

    void toggle(){
        if(binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawers();
        }else {
            binding.drawer.openDrawer(GravityCompat.START);
        }
    }


    private class MyNavigationItemSelectedListener implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            toggle();
            switchFragment(item.getItemId());
            return false;
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(binding.drawer.isDrawerOpen(GravityCompat.START)){
                binding.drawer.closeDrawers();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if(binding.drawer.isDrawerOpen(GravityCompat.START)){
            binding.drawer.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }
}