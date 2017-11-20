package com.zuiyou.jd.type.presenter;

import com.zuiyou.jd.home.model.HomeDemoModel;
import com.zuiyou.jd.type.model.ShopsListModel;
import com.zuiyou.jd.baseview.AllView;
import com.zuiyou.jd.basepresenter.BasePresenter;

/**
 * 类描述：商品详情页-------presenter
 * 创建人:巩森森
 */

public class ShopsListPresenter extends BasePresenter<AllView> {

    private ShopsListModel shopsParticularsModel;

    public ShopsListPresenter(AllView iView) {
        super(iView);
    }

    @Override
    protected void initModel() {
        shopsParticularsModel = new ShopsListModel();
    }

    public void ShopsParticularsData(String url) {
        shopsParticularsModel.getGnsy(url, new HomeDemoModel.DataCallBack<String>() {
            @Override
            public void onGetDataSucced(String s) {
                iView.leftsuccedData(s);
            }
        });
    }
}
