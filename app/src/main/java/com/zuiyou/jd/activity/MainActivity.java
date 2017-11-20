package com.zuiyou.jd.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zuiyou.jd.R;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    //计时器
    private Timer timer;
    //倒计时数
    private TextView coundowntime;
    //handler处理事件完成倒计时跳转
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                int m = (Integer) msg.obj;
                coundowntime.setText(m + "s");
                if (m == 0) {
                    // 停止计时
                    timer.cancel();
                    // 跳转到登录成功页面
                    Intent intent = new Intent(MainActivity.this,
                            ShouYeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
        //倒计时跳转
        Countdown();

    }

    //初始化控件
    private void initView() {
        coundowntime = (TextView) findViewById(R.id.coundowntime);
    }

    //倒计时 开启一个计时器
    private void Countdown() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int m = 5;

            @Override
            public void run() {
                m--;
                Message msg = Message.obtain();
                msg.what = 100;
                msg.obj = m;
                handler.sendMessage(msg);
            }
        }, 0, 1000);
    }

    //点击button,停止计时器，直接跳转
    public void fcomein(View view) {
        timer.cancel();
        Intent intent = new Intent(MainActivity.this,
                ShouYeActivity.class);
        startActivity(intent);
        finish();
    }
}
