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

public class LeftTypeDemoModel {
    private OkHttpClient okHttpClient;

    /**
     * okhttpClient请求数据
     *
     * @param url
     * @param dataCallBack
     */
    public void getAsync(String url, final DataCallBack<String> dataCallBack) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            //失败
            @Override
            public void onFailure(Call call, IOException e) {

            }


            //成功的
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    dataCallBack.onGetDataSucced(response.body().string());
                }
            }
        });
    }

    //调用Application里的静态方法
    public LeftTypeDemoModel() {
        okHttpClient = MyApp.okHttpClient();
    }

    //自定义一个接口();里面有两个方法---成功的--失败泛型
    public interface DataCallBack<T> {
        void onGetDataSucced(T t);

        void onGetDataFaild(T t);

    }
}
