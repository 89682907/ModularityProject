package com.modularity.common.expand.banner.holder;

import android.view.View;

public interface JViewHolderCreator {
    Holder createHolder(View itemView);
    int getLayoutId();
}
