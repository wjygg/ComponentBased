package com.example.wangjingyun.componentbased.activity.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.utils.ImageUtil;

import java.io.File;

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

        String fileLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "1.jpg";

        String fileLocation1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "2.jpg";

        Bitmap bitmap = BitmapFactory.decodeFile(fileLocation);

        ImageUtil.compressImage(bitmap,30,fileLocation1);

    }
}
