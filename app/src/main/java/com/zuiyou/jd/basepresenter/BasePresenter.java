package com.zuiyou.jd.basepresenter;

import android.content.Context;

import com.zuiyou.jd.baseview.IView;
import com.zuiyou.jd.app.MyApp;

/**
 * 类描述：
 * 创建人:巩森森
 */
//BasePresenter是所有的presenter的父类
public abstract class BasePresenter<T extends IView> {
    protected T iView;

    public BasePresenter(T iView) {
        this.iView = iView;
        initModel();
    }

    protected abstract void initModel();

    //如果接口提供的环境变量为空的话,就返回applocation的环境变量
    Context context() {
        if (iView != null && iView.context() != null) {
            return iView.context();
        }
        return MyApp.AppContext();
    }
}
