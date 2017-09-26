package com.example.wangjingyun.componentbased.activity.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coremedia.iso.boxes.Container;
import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.utils.CompositeVideo;
import com.example.wangjingyun.componentbased.utils.ImageUtil;
import com.example.wangjingyun.componentbased.widget.WithCity;
import com.example.wangjingyun.componentbased.widget.WithCityLoading;
import com.example.wangjingyun.componentbased.widget.bannerview.BannerBaseAdapter;
import com.example.wangjingyun.componentbased.widget.bannerview.BannerView;
import com.example.wangjingyun.componentbasesdk.ioc.CheckNet;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @ViewById(R.id.btn_write)
    Button btn_write;

    @ViewById(R.id.btn_delete)
    Button btn_delete;

    @ViewById(R.id.btn_compressedvideo)
    Button btn_compressedvideo;

    ProgressDialog dialog;
    String mp4Datas2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "33.mp4";
    private Handler mHandler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {

            mHandler.postDelayed(this,1000);

            File file=new File(mp4Datas2);

            if(file.exists()){

                mHandler.removeCallbacks(this);
                dialog.dismiss();
            }
        }
    };

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

            @Override
            public void bannerClick(int position) {
                //点击事件
                Toast.makeText(getActivity(),position+"",Toast.LENGTH_SHORT).show();

            }
        });
        bannerView.startCarousel();

    }
    private String str="cesidijfidjfic";
    private String writeLocation=Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"commontext.txt";
    @OnClick(R.id.btn_write)
    public void setBtn_write(){

        File file=new File(writeLocation);
            try {
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] bytes = str.getBytes();
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    @OnClick(R.id.btn_delete)
    public void setBtn_delete(){

        File file=new File(writeLocation);
        if(file.exists()){

            file.delete();
        }
    }

    /**
     * 视频合成
     */
    @OnClick(R.id.btn_compressedvideo)
    public void comPressVideo(){

        dialog=new ProgressDialog(getActivity());
        dialog.show();
        mHandler.postDelayed(runnable,1000);
        new Thread(){
            @Override
            public void run() {
                super.run();
                String mp4Datas = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "11.mp4";
                String mp4Datas1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "22.mp4";
                String mp4Datas2 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "33.mp4";
                String[] videoUris = new String[]{
                        mp4Datas,
                        mp4Datas1
                };
                //自定义视频拼接类
                CompositeVideo myVideoSplicing = new CompositeVideo(getActivity(), videoUris,mp4Datas2);
                myVideoSplicing.videoSplice();


            }
        }.start();
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


