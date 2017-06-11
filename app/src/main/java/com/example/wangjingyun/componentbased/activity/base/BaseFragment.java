package com.example.wangjingyun.componentbased.activity.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wangjingyun.componentbasesdk.ioc.ViewUtils;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/11.
 */

public abstract  class BaseFragment extends Fragment {

    //sd卡 读权限
    public static final int READ_EXTERNAL_CODE =0x001;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View  view=inflater.inflate(getLayoutId(),null);

        ButterKnife.inject(this,view);

        ViewUtils.Inject(view,this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }


    public abstract int getLayoutId();

    public abstract void initDatas();

    //判断 6.0权限
    public boolean hasPermission(Context context, String ... permissions){

        for(String permission: permissions){

            if(ContextCompat.checkSelfPermission(context,permission)!= PackageManager.PERMISSION_GRANTED){

                return false;
            }
        }
        return true;
    }

    public void requestPermission(int requestCode,String ... permissions){

        if(Build.VERSION.SDK_INT>=23){

            requestPermissions(permissions,requestCode);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){

            case READ_EXTERNAL_CODE:

                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    readSd();
                }else{

                   pleaseRequestPermission();
                }
                break;
        }

    }
    //请申请权限
    public void pleaseRequestPermission() {

        Toast.makeText(getActivity(),"请申请权限",Toast.LENGTH_SHORT).show();
    }

    //执行 读sd卡权限
    public void readSd(){};
}
