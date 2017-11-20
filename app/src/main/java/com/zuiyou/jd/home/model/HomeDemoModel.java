package com.zuiyou.jd.home.model;

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

public class HomeDemoModel {
    private OkHttpClient okHttpClient;

    public void getgcnys(String url, final DataCallBack<String> dataCallBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                dataCallBack.onGetDataSucced(response.body().string());
            }
        });
    }

    public HomeDemoModel() {
        okHttpClient = MyApp.okHttpClient();
    }

    public interface DataCallBack<T> {
        void onGetDataSucced(T t);

    }
}
