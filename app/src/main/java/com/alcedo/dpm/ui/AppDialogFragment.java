package com.alcedo.dpm.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.DialogPreference;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alcedo.dpm.R;
import com.alcedo.dpm.Utils;
import com.alcedo.dpm.adapter.PackageAdapter;
import com.alcedo.dpm.viewmodel.DPMViewModel;

import java.util.List;

public class AppDialogFragment extends DialogFragment{

    DPMViewModel dpmViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dpmViewModel = new ViewModelProvider(this).get(DPMViewModel.class);
    }


    public static AppDialogFragment newInstance(String title) {
        AppDialogFragment fragment = new AppDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        fragment.setArguments(bundle);
        return fragment;
    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_dialog,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        List<String> packageList = Utils.getAllPackage(getContext());
        PackageAdapter adapter = new PackageAdapter(getContext(),packageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return view;
    }
}
