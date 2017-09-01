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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.utils.ImageUtil;
import com.example.wangjingyun.componentbased.widget.WithCity;
import com.example.wangjingyun.componentbased.widget.WithCityLoading;
import com.example.wangjingyun.componentbased.widget.bannerview.BannerBaseAdapter;
import com.example.wangjingyun.componentbased.widget.bannerview.BannerView;
import com.example.wangjingyun.componentbasesdk.ioc.CheckNet;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    @ViewById(R.id.bannerview)
    BannerView bannerView;

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

        bannerView.setAdapter(new BannerBaseAdapter() {
            @Override
            public View getView(int position) {

                ImageView imageView=new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(getActivity()).load("http://img2.3lian.com/2014/f6/173/d/51.jpg").into(imageView);

                return imageView;
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public String getString(int position) {

                return "第"+position+"页内容";
            }
        });
        bannerView.startCarousel();

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


