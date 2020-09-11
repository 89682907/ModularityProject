package com.modularity.common.expand.banner.holder;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class Holder<T> extends RecyclerView.ViewHolder {
    public Holder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    protected abstract void initView(View itemView);

    public abstract void updateUI(T data);
}
