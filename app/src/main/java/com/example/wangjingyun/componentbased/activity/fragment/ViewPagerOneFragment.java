package com.example.wangjingyun.componentbased.activity.fragment;

import android.widget.TextView;

import com.example.wangjingyun.componentbased.R;
import com.example.wangjingyun.componentbased.activity.base.BaseFragment;
import com.example.wangjingyun.componentbased.entity.Persion;
import com.example.wangjingyun.componentbasesdk.db.DaoSupportFactory;
import com.example.wangjingyun.componentbasesdk.db.IDaoSupport;
import com.example.wangjingyun.componentbasesdk.db.QuerySupport;
import com.example.wangjingyun.componentbasesdk.ioc.OnClick;
import com.example.wangjingyun.componentbasesdk.ioc.ViewById;

import java.util.List;
import java.util.jar.Manifest;

/**
 * Created by Administrator on 2018/1/19.
 */

public class ViewPagerOneFragment extends BaseFragment{

    @ViewById(R.id.textpersion)
    private TextView textpersion;

    public static  ViewPagerOneFragment getInstance(){

        ViewPagerOneFragment fragment=new ViewPagerOneFragment();

        return fragment;
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_viewpagerone_layout;
    }

    @Override
    public void initDatas() {



    }

    @OnClick(R.id.textpersion)
    public void textpersion(){

        //数据写权限
        if(hasPermission(getActivity(),  android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            Persion persion = new Persion("小明", "17", true);
            //数据库设置数据
            IDaoSupport<Persion> dao = DaoSupportFactory.getInstance().getDao(Persion.class);
            dao.insert(persion);

        }else{

            requestPermission(WRITE_EXTERNAL_CODE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }
    @Override
    public void implementPermission() {

        Persion persion = new Persion("小明", "17", true);
        //数据库设置数据
        IDaoSupport<Persion> dao = DaoSupportFactory.getInstance().getDao(Persion.class);
        dao.insert(persion);
    }
}
