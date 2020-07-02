package com.alcedo.dpm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alcedo.dpm.DPMApi;
import com.alcedo.dpm.R;

import java.util.List;
/**
 * @file PackageAdapter.java
 *
 * @brief 适配器
 *
 * @author wudd
 *
 * @email 814668064@qq.com
 *
 * @date 20-6-30
 *
 * @attention {使用此类需要注意什么}
 */
public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.MyViewHolder> {
    DPMApi dpmApi;
    private Context context;
    private List<String> packageList;


    public PackageAdapter(Context context, List<String> packageList) {
        this.context = context;
        this.packageList = packageList;
        dpmApi = new DPMApi(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_app_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //不关心，不复用
        holder.setIsRecyclable(false);
        String packageName = packageList.get(position);
        holder.tv.setText(packageName);
        holder.sw.setChecked(dpmApi.isApplicationHidden(packageName));
        holder.sw.setTextOff("显示");
        holder.sw.setTextOn("隐藏");
        holder.sw.setOnCheckedChangeListener((buttonView, isChecked) -> {

           boolean is =  dpmApi.setApplicationHidden(packageName,isChecked);
           Toast.makeText(context,""+is,Toast.LENGTH_SHORT).show();

        });
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout rl;
        private TextView tv;
        private Switch sw;
        public MyViewHolder(View itemView) {
            super(itemView);
            rl =  itemView.findViewById(R.id.item_rl);
            tv = itemView.findViewById(R.id.item_tv);
            sw = itemView.findViewById(R.id.item_sw);
        }
    }

}
