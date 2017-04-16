package com.pleb.decisionsandroid;

import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Maxime on 16/04/2017.
 */

public class CustomViewPager extends ViewPager {

    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);
        this.enabled = false;
    }



    public boolean OnTouchEvent(MotionEvent event) {

        if(this.enabled)
            return super.onTouchEvent(event);
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled)
            return super.onInterceptTouchEvent(event);
        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}