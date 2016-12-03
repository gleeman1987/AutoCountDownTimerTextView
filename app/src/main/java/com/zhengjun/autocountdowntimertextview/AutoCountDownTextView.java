package com.zhengjun.autocountdowntimertextview;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Author:Zheng Jun
 * E-Mail:zhengjun1987@outlook.com
 * Date: 2016/12/3. 14:24
 */

public class AutoCountDownTextView extends TextView {

    private SharedPreferences sharedPreferences;
    private String pre_text;
    private String unit;
    private String sharedpreference_name;
    private int count_down_color;
    private int post_color;
    private int duration;
    private String post_text;
    private OnCountDownStartClickListener onCountDownStartClickListener;

    public AutoCountDownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AutoCountDownTextView);
        count_down_color = typedArray.getColor(R.styleable.AutoCountDownTextView_count_down_color, getResources().getColor(R.color.colorAccent));
        post_color = typedArray.getColor(R.styleable.AutoCountDownTextView_post_color, getResources().getColor(R.color.colorPrimary));
        post_text = typedArray.getString(R.styleable.AutoCountDownTextView_post_text);
        pre_text = typedArray.getString(R.styleable.AutoCountDownTextView_pre_text);
        duration = typedArray.getInt(R.styleable.AutoCountDownTextView_duration, 90);
        unit = typedArray.getString(R.styleable.AutoCountDownTextView_unit);
        sharedpreference_name = typedArray.getString(R.styleable.AutoCountDownTextView_sharedpreference_name);
        typedArray.recycle();
        sharedPreferences = context.getSharedPreferences("config.xml", Context.MODE_PRIVATE);
        Log.d(TAG, "AutoCountDownTextView:isClickable() " + isClickable());

        setState();

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putLong(sharedpreference_name, System.currentTimeMillis()).apply();
                setState();
                onCountDownStartClickListener.onStartClick();
                Log.d(TAG, "onClick: onCountDownStartClickListener.onStartClick();");
            }
        });
    }

    private static final String TAG = "AutoCountDownTextView";

    public AutoCountDownTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setState() {
        final long aLong = sharedPreferences.getLong(sharedpreference_name, 0);
        if (aLong == 0) {
            Log.d(TAG, "setState: aLong == 0");
            setText(pre_text);
            setTextColor(post_color);
            setClickable(true);
        }
        if (aLong != 0 && aLong <= System.currentTimeMillis() - duration * 1000) {
            setText(post_text);
            setTextColor(post_color);
            setClickable(true);
        }
        if (aLong != 0 && aLong > System.currentTimeMillis() - duration * 1000) {
            setTextColor(count_down_color);
            setClickable(false);
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    long l = (aLong + duration * 1000 - System.currentTimeMillis()) / 1000;
                    if (l > 1) {
                        setText(l + unit);
                        onCountDownStartClickListener.onCount(l);
                        handler.postDelayed(this, 1000);
                    } else {
                        handler.removeCallbacks(this);
                        onCountDownStartClickListener.onCountFinished();
                        Log.d(TAG, "run: onCountDownStartClickListener.onCountFinished();");
                        setState();
                    }
                }
            };
            handler.post(runnable);
        }

    }

    public void setOnCountDownStartClickListener(OnCountDownStartClickListener onCountDownStartClickListener) {
        Log.d(TAG, "setOnCountDownStartClickListener() called with: onCountDownStartClickListener = [" + onCountDownStartClickListener + "]");
        this.onCountDownStartClickListener = onCountDownStartClickListener;
    }

    public void clearTimeStamp() {
        Log.d(TAG, "clearTimeStamp() called");
        sharedPreferences.edit().remove(sharedpreference_name).apply();
    }

}
