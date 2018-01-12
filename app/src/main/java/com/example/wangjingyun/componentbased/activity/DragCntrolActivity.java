package com.example.wangjingyun.componentbased.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.dialog.BaseDialog;
import com.example.wangjingyun.componentbased.dialog.titlebar.BaseTitleBar;
import com.example.wangjingyun.componentbased.entity.TriangleTypeEntity;
import com.example.wangjingyun.componentbased.network.HttpCallBackEntity;
import com.example.wangjingyun.componentbased.service.compressbitmap.BitmapParams;
import com.example.wangjingyun.componentbased.service.compressbitmap.CompressBitmapService;
import com.example.wangjingyun.componentbased.utils.StatusBarUtils;
import com.example.wangjingyun.componentbased.widget.BouquetPraiseView;
import com.example.wangjingyun.componentbased.widget.DragControlView;
import com.example.wangjingyun.componentbasesdk.http.HttpCallBack;
import com.example.wangjingyun.componentbasesdk.http.HttpCallBackProgress;
import com.example.wangjingyun.componentbasesdk.http.HttpUtils;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2017/11/7.
 *
 */

public class DragCntrolActivity extends BaseActivity{

    @ViewById(R.id.bouquetpraiseview)
    BouquetPraiseView bouquetpraiseview;

    @ViewById(R.id.dragcontrolview)
    DragControlView dragcontrolview;

    @ViewById(R.id.textview)
    TextView textview;

    @ViewById(R.id.dialog)
    TextView dialog;
    private String strDir= Environment.getExternalStorageDirectory()+"/imooc/imooc.apk";

    @Override
    public void setStatusBar() {
        super.setStatusBar();

        StatusBarUtils.setStatusBarUtils(DragCntrolActivity.this, ContextCompat.getColor(DragCntrolActivity.this,R.color.pink_text_color));
    }

    @Override
    public void initTitle() {
        BaseTitleBar baseTitleBar=new BaseTitleBar.Builder(DragCntrolActivity.this)
                .setAlignText(R.id.tv_align,"测试").setAlignTextColor(R.id.tv_align,R.color.white).setBackGround(R.color.pink_text_color)
                .builder();

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dragcontrol_layout;
    }

    private class CompressReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            int intExtra = intent.getIntExtra(CompressBitmapService.ACTION_COMPRESS_FLAG, 0);
            if(intExtra==1){
                //开始压缩
             Toast.makeText(DragCntrolActivity.this,"开始压缩",Toast.LENGTH_SHORT).show();
            }else if(intExtra==2){
                //压缩完成
                Toast.makeText(DragCntrolActivity.this,"压缩完成",Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void initDatas() {


        CompressReceiver receiver=new CompressReceiver();
        IntentFilter filter=new IntentFilter(CompressBitmapService.ACTION_COMPRESS_BROADCAST);
        //注册广播
        registerReceiver(receiver,filter);


        ArrayList<String> compressFiles = getImagesPathFormAlbum();
        int size = compressFiles.size() > 10 ? 10:compressFiles.size();
        ArrayList<BitmapParams> tasks = new ArrayList<BitmapParams>(compressFiles.size());

        for (int i = 0; i < compressFiles.size(); ++i) {
            BitmapParams param = new BitmapParams();
            param.setOutHeight(1080);
            param.setOutWidth(720);
            param.setMaxFileSize(50);
            param.setSrcImageUri(compressFiles.get(i));
            tasks.add(param);
        }
        Intent intent = new Intent(DragCntrolActivity.this, CompressBitmapService.class);
        intent.putParcelableArrayListExtra(CompressBitmapService.BITMAPPARAMS, tasks);
        startService(intent);



        dragcontrolview.setOnTouchListener(new DragControlView.DragViewListener(DragCntrolActivity.this));
    }

    @OnClick(R.id.textview)
    public void setTextview(){

        bouquetpraiseview.addView();
    }

    @OnClick(R.id.dialog)
    public void clickDialog(){
        BaseDialog baseDialog = new BaseDialog.Builder(DragCntrolActivity.this, R.style.DialogTheme).setView(R.layout.dialog_dragcntrol_layout)
               .setOnClickListener(R.id.textview, new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Toast.makeText(DragCntrolActivity.this,((TextView)v).getText().toString(),Toast.LENGTH_SHORT).show();
                   }
               }).setText(R.id.textview,"测试").setGravity(Gravity.TOP).builder();

        baseDialog.show();

    }


    private ArrayList<String> getImagesPathFormAlbum() {
        ArrayList<String> paths = new ArrayList<>();
        //selection: 指定查询条件
        String selection = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?";

        //定义selection参数匹配值
        String[] selectionArgs = {"image/jpeg", "image/png"};

        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null,
                selection,
                selectionArgs,
                MediaStore.Images.Media.DATE_MODIFIED);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                long size = (int) cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                long lastModified = (int) cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED));
                //排除size为0的无效文件
                if (size != 0) {
                    paths.add(url);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return paths;
    }

}
