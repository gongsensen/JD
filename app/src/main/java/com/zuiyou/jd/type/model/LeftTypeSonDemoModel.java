package com.zuiyou.jd.type.model;

import com.zuiyou.jd.app.MyApp;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class LeftTypeSonDemoModel {
    private OkHttpClient okHttpClient;

    public void getAnsy(String url, final SonDataCallBack<String> dataCallBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    dataCallBack.onGetDataSucced(response.body().string());
                }
            }
        });
    }

    //调用Application里的静态方法
    public LeftTypeSonDemoModel() {
        okHttpClient = MyApp.okHttpClient();
    }

    public interface SonDataCallBack<T> {
        void onGetDataSucced(T t);

        void onGetDataFaild(T t);
    }
}