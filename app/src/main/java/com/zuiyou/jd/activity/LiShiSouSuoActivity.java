package com.zuiyou.jd.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.zuiyou.jd.R;
import com.zuiyou.jd.db.RecordSQLiteOpenHelper;

import java.util.Date;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * Created by gongsensen
 * 作用：历史搜索界面，把曾经搜索过的商品保存到数据库
 */

public class LiShiSouSuoActivity extends AppCompatActivity {
    private boolean showDanmaku;
    private DanmakuView danmakuView;
    private DanmakuContext danmakuContext;
    private BaseDanmakuParser parser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    private EditText et_search;
    private TextView tv_tip;
    private MyListView listView;
    private TextView tv_clear;
    private Button send;
    private EditText inputdanmu;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    ;
    private SQLiteDatabase db;
    private BaseAdapter adapter;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sousuo);
        // 初始化控件
        initView();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // 清空搜索历史
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        // 搜索框的键盘搜索键点击回调
        et_search.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(et_search.getText().toString().trim());
                    if (!hasData) {
                        insertData(et_search.getText().toString().trim());
                        queryData("");
                    }
                    danmakuView.start();
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Toast.makeText(LiShiSouSuoActivity.this, "clicked!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tv_tip.setText("搜索历史");
                } else {
                    tv_tip.setText("搜索结果");
                }
                String tempName = et_search.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                Toast.makeText(LiShiSouSuoActivity.this, name, Toast.LENGTH_SHORT).show();
                // TODO 获取到item上面的文字，根据该关键字跳转到另一个页面查询，由你自己去实现
            }
        });

        // 插入数据，便于测试，否则第一次进入没有数据怎么测试呀？
        Date date = new Date();
        long time = date.getTime();
        insertData("Leo" + time);

        // 第一次进入查询所有的历史记录
        queryData("");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputdanmu.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    addDanmaku(content, true);
                    inputdanmu.setText("");
                }
            }
        });
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void initView() {
        inputdanmu = (EditText) findViewById(R.id.edit_text);
        send = (Button) findViewById(R.id.send);
        et_search = (EditText) findViewById(R.id.et_search);
        tv_tip = (TextView) findViewById(R.id.tv_tip);
        listView = (MyListView) findViewById(R.id.listView);
        tv_clear = (TextView) findViewById(R.id.tv_clear);
        back = (ImageView) findViewById(R.id.back);
        danmakuView = (DanmakuView) findViewById(R.id.danmakuiew);
        // 调整EditText左边的搜索按钮的大小
        Drawable drawable = getResources().getDrawable(R.mipmap.a_u);
        drawable.setBounds(0, 0, 60, 60);// 第一0是距左边距离，第二0是距上边距离，60分别是长宽
        et_search.setCompoundDrawables(drawable, null, null, null);// 只放左边
        danmakuView.enableDanmakuDrawingCache(true);
        danmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                showDanmaku = true;
//                danmakuView.start();
                generateSomeDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });

        danmakuContext = DanmakuContext.create();
        danmakuView.prepare(parser, danmakuContext);
    }

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateSomeDanmaku() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                while (showDanmaku) {
//                    int time = new Random().nextInt(300);
//                    String content = "当然是选择原谅了" + time + time;
//                    addDanmaku(content, false);
//                    try {
//                        Thread.sleep(time);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
                while (showDanmaku) {
                    for (int i = 0; i < Integer.MAX_VALUE; i++) {
                        if (i % 10 == 1) {
                            try {
                                Thread.sleep(120);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String content = "当然是选择原谅了2333";
                            addDanmaku(content, false);
                        } else if (i % 20 == 2) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String str = "一杯敬月光，一杯敬老王" + i;
                            addDanmaku(str, false);
                        } else if (i % 30 == 3) {
                            try {
                                Thread.sleep(170);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String text = "那么爱她为什么不选择原谅。。。";
                            addDanmaku(text, false);
                        }
                    }
                }

            }
        }).start();
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = sp2px(20);
        danmaku.textColor = Color.GREEN;
        danmaku.setTime(danmakuView.getCurrentTime());
        if (withBorder) {
            danmaku.borderColor = Color.RED;
        }
        danmakuView.addDanmaku(danmaku);
    }

    /**
     * sp转px的方法。
     */
    private float sp2px(float spValue) {
        final float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    @Override
    protected void onPause() {
        super.onPause();
        danmakuView.clear();
//        if (danmakuView != null && danmakuView.isPrepared()) {
//            danmakuView.pause();
//            danmakuView.clear();
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (danmakuView != null && danmakuView.isPrepared() && danmakuView.isPaused()) {
//            danmakuView.resume();
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        danmakuView.clear();
//        showDanmaku = false;
//        if (danmakuView != null) {
////            danmakuView.release();
//            danmakuView.clear();
//            danmakuView = null;
//        }
    }
}
