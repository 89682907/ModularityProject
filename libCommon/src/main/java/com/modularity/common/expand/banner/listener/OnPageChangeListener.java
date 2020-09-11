package com.modularity.common.expand.banner.listener;

import androidx.recyclerview.widget.RecyclerView;

public interface OnPageChangeListener {
    void onScrollStateChanged(RecyclerView recyclerView, int newState);
    void onScrolled(RecyclerView recyclerView, int dx, int dy);
    void onPageSelected(int index);
}
