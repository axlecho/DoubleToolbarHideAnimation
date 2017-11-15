package com.axlecho.learn.doubletoolbarhideanimation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by axlecho on 2017/10/14 0014.
 */

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private int hideThreshold = 20; //移动多少距离后显示隐藏
    private int scrolledDistance = 0; //移动的中距离
    private boolean controlsVisible = true; //显示或隐藏

    public HidingScrollListener(Context context) {
        int barHight = context.getResources().getDimensionPixelSize(R.dimen.second_navigation_bar_height);
        hideThreshold = barHight;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (scrolledDistance > hideThreshold && controlsVisible) {//移动总距离大于规定距离 并且是显示状态就隐藏
            onHide();
            controlsVisible = false;
            scrolledDistance = 0;//归零
        } else if (scrolledDistance < -hideThreshold && !controlsVisible) {
            onShow();
            controlsVisible = true;
            scrolledDistance = 0;
        }
        if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) { //显示状态向上滑动 或 隐藏状态向下滑动 总距离增加
            scrolledDistance += dy;
        }
    }

    public abstract void onHide();

    public abstract void onShow();
}
