package com.modularity.common.expand.banner.listener;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PageChangeListener implements OnPageChangeListener {
    private ArrayList<ImageView> mPointViews;
    private int[]                mPageIndicatorId;
    private OnPageChangeListener onPageChangeListener;

    public PageChangeListener(ArrayList<ImageView> pointViews, int[] pid) {
        this.mPointViews = pointViews;
        this.mPageIndicatorId = pid;
    }

    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        if (onPageChangeListener != null)
            onPageChangeListener.onScrollStateChanged(recyclerView, newState);
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (onPageChangeListener != null) onPageChangeListener.onScrolled(recyclerView, dx, dy);
    }

    public void onPageSelected(int index) {
        for (int i = 0; i < mPointViews.size(); i++) {
            mPointViews.get(index).setImageResource(mPageIndicatorId[1]);
            if (index != i) {
                mPointViews.get(i).setImageResource(mPageIndicatorId[0]);
            }
        }
        if (onPageChangeListener != null) onPageChangeListener.onPageSelected(index);

    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

}
