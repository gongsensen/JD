package com.zuiyou.jd.type.presenter;

import com.zuiyou.jd.basepresenter.BasePresenter;
import com.zuiyou.jd.type.model.LeftTypeDemoModel;
import com.zuiyou.jd.baseview.AllView;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class LeftTypePresenter extends BasePresenter<AllView> {

    private LeftTypeDemoModel model;

    public LeftTypePresenter(AllView iView) {
        super(iView);
    }

    //找到自己的model
    @Override
    protected void initModel() {
        model = new LeftTypeDemoModel();
    }

    public void getLeftData(String url) {
        model.getAsync(url, new LeftTypeDemoModel.DataCallBack<String>() {
            //调用成功的方法
            @Override
            public void onGetDataSucced(String s) {
                iView.leftsuccedData(s);
            }

            @Override
            public void onGetDataFaild(String s) {
                iView.leftsuccedData(s);
            }
        });


    }
}
