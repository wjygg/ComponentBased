package com.example.wangjingyun.componentbased.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseActivity;
import com.example.wangjingyun.componentbased.activity.fragment.HomeFragment;
import com.example.wangjingyun.componentbased.activity.fragment.MessageFragment;
import com.example.wangjingyun.componentbased.activity.fragment.MineFragment;

import butterknife.InjectView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @InjectView(R.id.home_fragment)
    LinearLayout home_fragment;

    @InjectView(R.id.message_fragment)
    LinearLayout message_fragment;

    @InjectView(R.id.mine_fragment)
    LinearLayout mine_fragment;

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private MineFragment mineFragment;
    @Override
    public int getLayoutId() {
        return R.layout.activity_home_layout;
    }

    @Override
    public void initDatas() {

        FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();

        homeFragment=HomeFragment.getInstance();
        fragmentTransaction.add(R.id.frame_layout, homeFragment)
                .commit();

    }

    @OnClick(R.id.home_fragment)
    public void clickHomeFragment(){

        FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(messageFragment,fragmentTransaction);

        hideFragment(mineFragment,fragmentTransaction);


        if(homeFragment==null){

            homeFragment=HomeFragment.getInstance();

            fragmentTransaction.add(R.id.frame_layout,homeFragment).commit();
        }else {

            fragmentTransaction.show(homeFragment).commit();
        }

    }

    @OnClick(R.id.message_fragment)
    public void clickMessageFragment(){

        FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(homeFragment,fragmentTransaction);

        hideFragment(mineFragment,fragmentTransaction);


        if(messageFragment==null){

            messageFragment=MessageFragment.getInstance();

            fragmentTransaction.add(R.id.frame_layout,messageFragment).commit();
        }else {

            fragmentTransaction.show(messageFragment).commit();
        }


    }

    @OnClick(R.id.mine_fragment)
    public void  clickMineFragment(){

        FragmentTransaction  fragmentTransaction = getSupportFragmentManager().beginTransaction();

        hideFragment(homeFragment,fragmentTransaction);

        hideFragment(messageFragment,fragmentTransaction);


        if(mineFragment==null){

            mineFragment=MineFragment.getInstance();

            fragmentTransaction.add(R.id.frame_layout,mineFragment).commit();
        }else {

            fragmentTransaction.show(mineFragment).commit();
        }

    }


    private void hideFragment(Fragment fragment,FragmentTransaction fragemntTransaction){

        if(fragment!=null){
            fragemntTransaction.hide(fragment);
        }
    }


}
