package com.modularity.common.expand.banner.holder;

import android.view.View;

public interface IBannerHolderCreator {
    BannerHolder createHolder(View itemView);
    int getLayoutId();
}
