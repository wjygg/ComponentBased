package com.example.wangjingyun.componentbased.activity.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.utils.ImageUtil;
import com.example.wangjingyun.componentbased.widget.WithCity;
import com.example.wangjingyun.componentbased.widget.WithCityLoading;
import com.example.wangjingyun.componentbasesdk.ioc.CheckNet;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MineFragment extends BaseFragment {

    String fileLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "1.jpg";

    String fileLocation1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "2.jpg";

    @ViewById(R.id.button)
    Button button;

    @ViewById(R.id.withcityloading)
    WithCityLoading withcityloading;

    public static MineFragment getInstance() {

        MineFragment fragment = new MineFragment();

        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    public void initDatas() {


    }

    @OnClick(R.id.withcityloading)
    @CheckNet
    public void onClickCity(){

       /* final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                withCity.setCurrentType();

                handler.postDelayed(this,1000);
            }
        },1000);*/

        withcityloading.setVisibility(View.GONE);

    }

    @OnClick(R.id.button)
    public void onClickListener(){

        //判断权限
        if(hasPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)){


            //调用系统相册 6.0以上
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,1000);

        }else{

            requestPermission(READ_EXTERNAL_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
           Bundle bundle = data.getExtras();
             // 获取相机返回的数据，并转换为Bitmap图片格式，这是缩略图
            Bitmap bitmap = (Bitmap) bundle.get("data");

             ;

            ImageUtil.compressBitmap(ImageUtil.decodeFile(fileLocation),30,fileLocation1);


       }
    }

    @Override
    public void readSd() {

        //调用系统相册 5.0
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1000);

    }
}


