package com.zuiyou.jd.type.presenter;

import com.zuiyou.jd.basepresenter.BasePresenter;
import com.zuiyou.jd.type.model.LeftTypeSonDemoModel;
import com.zuiyou.jd.baseview.AllView;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class LeftTypeSonPresenter extends BasePresenter<AllView> {

    private LeftTypeSonDemoModel leftTypeSonDemoModel;

    public LeftTypeSonPresenter(AllView iView) {
        super(iView);
    }

    @Override
    protected void initModel() {
        leftTypeSonDemoModel = new LeftTypeSonDemoModel();
    }

    public void LeftDataSon(String url) {
        leftTypeSonDemoModel.getAnsy(url, new LeftTypeSonDemoModel.SonDataCallBack<String>() {

            @Override
            public void onGetDataSucced(String s) {
                iView.rightsuccedData(s);
            }

            @Override
            public void onGetDataFaild(String s) {

            }
        });
    }
}
