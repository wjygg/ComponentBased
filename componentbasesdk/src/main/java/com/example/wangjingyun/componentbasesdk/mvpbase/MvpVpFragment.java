package com.example.wangjingyun.componentbasesdk.mvpbase;


import com.example.wangjingyun.componentbasesdk.base.BaseVpFragment;

import java.lang.reflect.Field;
import java.util.List;

/**
 * mvp 基类fragment
 *;
 * Created by wjy on 2018/5/7.
 */

public abstract class MvpVpFragment<T,M,P extends MvpPresenter<T,M>> extends BaseVpFragment {

    public P presenter;

    private List<MvpPresenter<T,M>> mPresenters;

    @Override
    public void initBefore() {
        super.initBefore();
        //初始化 presenter
        presenter=initPresenter();
        //presenter 层与view层绑定
        presenter.attachView((T)this);

        // 这个地方要去注入 Presenter 通过反射 (Dagger) soEasy
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if(injectPresenter != null){
                // 创建注入
                Class<? extends MvpPresenter> presenterClazz = null;
                // 自己去判断一下类型？ 获取继承的父类，如果不是 继承 BasePresenter 抛异常
                try {
                    presenterClazz = (Class<? extends MvpPresenter>) field.getType();
                } catch (Exception e){
                    // 乱七八糟一些注入
                    throw new RuntimeException("No support inject presenter type " + field.getType().getName());
                }

                try {
                    // 创建 Presenter 对象
                    MvpPresenter basePresenter = presenterClazz.newInstance();
                    //并没有解绑，还是会有问题，这个怎么办？1 2
                    basePresenter.attachView((T)this);
                    // 设置
                    field.setAccessible(true);
                    field.set(this,basePresenter);
                    mPresenters.add(basePresenter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public abstract P initPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();

        for (MvpPresenter presenter : mPresenters) {
            presenter.detachView();
        }

        if(presenter!=null){
            //view层 和P 层解绑
            presenter.detachView();
        }
    }
}
