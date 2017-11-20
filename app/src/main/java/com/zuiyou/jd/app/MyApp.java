package com.zuiyou.jd.app;

import android.app.Application;
import android.content.Context;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.utils.Log;

import org.xutils.x;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class MyApp extends Application {
    private static Context context;
    public static OkHttpClient okHttpClient;

    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        UMShareAPI.get(this);
        Config.DEBUG = true;
        x.Ext.init(this);

        okHttpClient = new OkHttpClient();
        okHttpClient = okHttpClient.newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new MyLogInterceptor())
                .build();
    }

    public static Context AppContext() {
        return context;
    }

    public static OkHttpClient okHttpClient() {
        return okHttpClient;
    }


    //拦截器,可以修改header,可以通过拦截器打印日志
    public class MyLogInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder()
                    .header("shenfen", "chinesse")
                    .build();
            HttpUrl url = request.url();
            String httpUrl = url.url().toString();
            Log.e("TAG", "============" + httpUrl);
            Response response = chain.proceed(request);
            int code = response.code();
            Log.e("TAG", "============response.code() == " + code);
            return response;
        }
    }

}
