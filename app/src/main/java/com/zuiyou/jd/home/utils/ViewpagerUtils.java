package com.zuiyou.jd.home.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类描述：
 * 创建人:巩森森
 */

public class ViewpagerUtils extends ViewPager {
    public ViewpagerUtils(Context context) {
        super(context);
    }

    public ViewpagerUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
