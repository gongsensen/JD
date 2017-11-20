package com.zuiyou.jd.type.presenter;

import com.zuiyou.jd.basepresenter.BasePresenter;
import com.zuiyou.jd.type.model.TypeRecyclerViewGcViewModel;
import com.zuiyou.jd.baseview.AllView;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class TypeRecyclerViewGcViewPresenter extends BasePresenter<AllView> {

    private TypeRecyclerViewGcViewModel typeRecyclerViewGcViewModel;

    public TypeRecyclerViewGcViewPresenter(AllView iView) {
        super(iView);
    }



    @Override
    protected void initModel() {
        typeRecyclerViewGcViewModel = new TypeRecyclerViewGcViewModel();
    }

    public void GcDataSon(String url) {
        typeRecyclerViewGcViewModel.getAnsy(url, new TypeRecyclerViewGcViewModel.GcDataCallBack<String>() {
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
