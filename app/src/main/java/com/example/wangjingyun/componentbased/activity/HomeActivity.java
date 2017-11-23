package com.example.wangjingyun.componentbased.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.activity.fragment.HomeFragment;
import com.example.wangjingyun.componentbased.activity.fragment.MessageFragment;
import com.example.wangjingyun.componentbased.activity.fragment.MineFragment;
import com.example.wangjingyun.componentbased.utils.StatusBarUtils;
import com.example.wangjingyun.componentbased.widget.TriangleTypeView;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;
import com.example.wangjingyun.componentbasesdk.log.ExctptionCarshHandler;

import java.io.File;
import java.io.FileInputStream;

import butterknife.InjectView;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity {

   @ViewById(R.id.home_fragment)
    LinearLayout home_fragment;

    @InjectView(R.id.message_fragment)
    LinearLayout message_fragment;

    @InjectView(R.id.mine_fragment)
    LinearLayout mine_fragment;

    @ViewById(R.id.btn_homepage)
    TextView btn_homepage;

    @ViewById(R.id.drow_down)
    TextView drow_down;

    @ViewById(R.id.tv_drag)
    TextView tv_drag;

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;

    @Override
    public void setStatusBar() {
        StatusBarUtils.setStatusBarTransParent(HomeActivity.this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    public void initDatas() {

        // 获取上次的崩溃信息文件 上传 服务器
        File crashFile = ExctptionCarshHandler.getInstance().getCrashFile();

        if (crashFile != null && crashFile.exists()) {
            //上传
            try {
                FileInputStream fis = new FileInputStream(crashFile);

                int len = 0;
                byte[] buf = new byte[1024];
                while ((len = fis.read(buf)) != -1) {
                    System.out.println(new String(buf, 0, len));
                }
                //关资源
                fis.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        homeFragment = HomeFragment.getInstance();
        fragmentTransaction.add(R.id.frame_layout, homeFragment)
                .commit();

    }

    @com.example.wangjingyun.componentbasesdk.ioc.OnClick(R.id.btn_homepage)
    public void clickBtnHomePage(){

      //  startActivity(new Intent(HomeActivity.this,CarHomeActivity.class));

        startActivity(new Intent(HomeActivity.this,VDHActivity.class));
    }

    @com.example.wangjingyun.componentbasesdk.ioc.OnClick(R.id.drow_down)
    public void drowDown(){
        startActivity(new Intent(HomeActivity.this,DropDownActivity.class));

    }

    @com.example.wangjingyun.componentbasesdk.ioc.OnClick(R.id.tv_drag)
    public void dragView(){

        startActivity(new Intent(HomeActivity.this,DragCntrolActivity.class));
    }
    @OnClick(R.id.home_fragment)
    public void clickHomeFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(messageFragment, fragmentTransaction);

        hideFragment(mineFragment, fragmentTransaction);


        if (homeFragment == null) {

            homeFragment = HomeFragment.getInstance();

            fragmentTransaction.add(R.id.frame_layout, homeFragment).commit();
        } else {

            fragmentTransaction.show(homeFragment).commit();
        }

    }

    @OnClick(R.id.message_fragment)
    public void clickMessageFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(homeFragment, fragmentTransaction);

        hideFragment(mineFragment, fragmentTransaction);


        if (messageFragment == null) {

            messageFragment = MessageFragment.getInstance();

            fragmentTransaction.add(R.id.frame_layout, messageFragment).commit();
        } else {

            fragmentTransaction.show(messageFragment).commit();
        }


    }

    @OnClick(R.id.mine_fragment)
    public void clickMineFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(homeFragment, fragmentTransaction);

        hideFragment(messageFragment, fragmentTransaction);


        if (mineFragment == null) {

            mineFragment = MineFragment.getInstance();

            fragmentTransaction.add(R.id.frame_layout, mineFragment).commit();
        } else {

            fragmentTransaction.show(mineFragment).commit();
        }

    }


    private void hideFragment(Fragment fragment, FragmentTransaction fragemntTransaction) {

        if (fragment != null) {
            fragemntTransaction.hide(fragment);
        }
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        //未被销毁的fragment 给fragment 重新 赋值
        if (homeFragment == null && fragment instanceof HomeFragment)
            homeFragment = (HomeFragment) fragment;
        if (messageFragment == null && fragment instanceof MessageFragment)
            messageFragment = (MessageFragment) fragment;
        if (mineFragment == null && fragment instanceof MineFragment)
            mineFragment = (MineFragment) fragment;
    }



}
