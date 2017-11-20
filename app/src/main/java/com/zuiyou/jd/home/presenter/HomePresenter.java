package com.zuiyou.jd.home.presenter;

import com.zuiyou.jd.home.model.HomeDemoModel;
import com.zuiyou.jd.basepresenter.BasePresenter;
import com.zuiyou.jd.baseview.AllView;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class HomePresenter extends BasePresenter<AllView> {

    private HomeDemoModel homeDemoModel;

    public HomePresenter(AllView iView) {
        super(iView);
    }



    @Override
    protected void initModel() {
        homeDemoModel = new HomeDemoModel();
    }

    public void HomeData(String url) {
        homeDemoModel.getgcnys(url, new HomeDemoModel.DataCallBack<String>() {
            @Override
            public void onGetDataSucced(String s) {
                iView.leftsuccedData(s);
            }
        });

    }
}
