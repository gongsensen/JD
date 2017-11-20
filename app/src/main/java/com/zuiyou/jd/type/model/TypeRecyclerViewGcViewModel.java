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

public class TypeRecyclerViewGcViewModel {

    private final OkHttpClient okHttpClient;

    public void getAnsy(String url, final GcDataCallBack<String> gcDataCallBack) {
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
                    gcDataCallBack.onGetDataSucced(response.body().string());
                }
            }
        });
    }

    public TypeRecyclerViewGcViewModel() {
        okHttpClient = MyApp.okHttpClient();
    }

    public interface GcDataCallBack<T> {
        void onGetDataSucced(T t);

        void onGetDataFaild(T t);
    }
}
