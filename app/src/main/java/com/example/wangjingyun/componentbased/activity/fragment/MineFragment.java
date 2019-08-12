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

import com.coremedia.iso.boxes.Container;
import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.utils.BitmapUtils;
import com.example.wangjingyun.componentbased.utils.CompositeVideo;
import com.example.wangjingyun.componentbased.utils.ImageUtil;
import com.example.wangjingyun.componentbased.widget.WithCity;
import com.example.wangjingyun.componentbased.widget.WithCityLoading;
import com.example.wangjingyun.componentbased.widget.bannerview.BannerBaseAdapter;
import com.example.wangjingyun.componentbased.widget.bannerview.BannerView;
import com.example.wangjingyun.componentbasesdk.ioc.CheckNet;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/3/11.
 */

public class MineFragment extends BaseFragment {


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

}


