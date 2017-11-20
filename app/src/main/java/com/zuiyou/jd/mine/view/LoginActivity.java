package com.zuiyou.jd.mine.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zuiyou.jd.R;

import java.util.Map;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_phone;
    private EditText et_pwd;
    private Button btnlogin;
    private ImageView et_clear_phone;
    private ImageView et_clear_pwd;
    private Boolean pwdshow = false;
    private CheckBox ck_pwd_show;
    private ImageView btnqqlogin;
    private static String iconurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initview();
        //判断输入框是否为空,登录按钮能否点击
        etphoneOnListener();
        et_pwdOnListener();
        checkboxpwdShow();
        btnqqlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(LoginActivity.this).getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, authListener);
            }
        });
    }

    private void checkboxpwdShow() {
        ck_pwd_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    et_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    /**
     * 判断密码输入框是否输入内容
     * 如果有内容可点击button
     * 删除内容后,button会重新进入初始状态
     */
    private void et_pwdOnListener() {
        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(et_pwd.getText())) {
                    btnlogin.setEnabled(Boolean.FALSE);
//                    Toast.makeText(LoginActivity.this, "按钮不可点击", Toast.LENGTH_SHORT).show();
                    btnlogin.setBackgroundResource(R.drawable.sharp_btn_longin);
                } else {
                    btnlogin.setEnabled(Boolean.TRUE);
//                    Toast.makeText(LoginActivity.this, "按钮可点击", Toast.LENGTH_SHORT).show();
                    btnlogin.setBackgroundResource(R.drawable.sharp_btn_longin2);
                }
                /**
                 * 判断输入框内容长度,按钮是否显示
                 */
                if (et_pwd.getText().length() > 0) {
                    et_clear_pwd.setVisibility(View.VISIBLE);
                } else {
                    et_clear_pwd.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 判断账号输入框是否输入内容
     * 判断输入框是否为空
     */
    private void etphoneOnListener() {
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(et_phone.getText()) || TextUtils.isEmpty(et_pwd.getText())) {
                    //让button不可点击
                    btnlogin.setEnabled(Boolean.FALSE);
//                    Toast.makeText(LoginActivity.this, "按钮不可点击", Toast.LENGTH_SHORT).show();
                    btnlogin.setBackgroundResource(R.drawable.sharp_btn_longin);
                } else {
                    //button 可以点击
                    btnlogin.setEnabled(Boolean.TRUE);
//                    Toast.makeText(LoginActivity.this, "按钮可点击", Toast.LENGTH_SHORT).show();
                    btnlogin.setBackgroundResource(R.drawable.sharp_btn_longin2);
                }
                /**
                 * 判断输入框内容长度,按钮是否显示
                 */
                if (et_phone.getText().length() > 0) {
                    et_clear_phone.setVisibility(View.VISIBLE);
                } else {
                    et_clear_phone.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * QQ登录回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 友盟QQ登录
     */
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
            //传QQ头像路径
            iconurl = data.get("iconurl");
            MyFragment.setsendUrl(iconurl);
            finish();
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(LoginActivity.this, "失败：" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(LoginActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };


    //初始化控件
    private void initview() {
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_pwd = (EditText) findViewById(R.id.ed_pwd);
        btnlogin = (Button) findViewById(R.id.btn_login);
        et_clear_phone = (ImageView) findViewById(R.id.et_clear_phone);
        et_clear_pwd = (ImageView) findViewById(R.id.et_clear_pwd);
        ck_pwd_show = (CheckBox) findViewById(R.id.ck_pwd_show);
        btnqqlogin = (ImageView) findViewById(R.id.btnqqlogin);
        //登录按钮点击事件
        btnlogin.setOnClickListener(this);
    }

    //关闭登录页面
    public void login_close(View view) {
        finish();
    }

    //点击清除输入框账号内容
    public void etclearphone(View view) {
        et_phone.setText("");
    }

    //点击清空输入框密码
    public void etclearpwd(View view) {
        et_pwd.setText("");
    }

    //按钮登录成功
    @Override
    public void onClick(View v) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        finish();
    }
}
