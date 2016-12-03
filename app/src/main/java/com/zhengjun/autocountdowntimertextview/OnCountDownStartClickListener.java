package com.zhengjun.autocountdowntimertextview;

/**
 * OKLINE(Hangzhou) Co.,Ltd
 * Author:Zheng Jun
 * E-Mail:zhengjun@okline.cn
 * Date: 2016/12/3. 14:59
 */
public interface OnCountDownStartClickListener {
    void onStartClick();
    void onCount(long l);
    void onCountFinished();
}
