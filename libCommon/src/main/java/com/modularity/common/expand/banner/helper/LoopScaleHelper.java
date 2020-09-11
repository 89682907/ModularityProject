package com.modularity.common.expand.banner.helper;

import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.modularity.common.expand.banner.adapter.PageAdapter;
import com.modularity.common.expand.banner.listener.OnPageChangeListener;
import com.modularity.common.expand.banner.view.LoopViewPager;

import java.util.Objects;

public class LoopScaleHelper {

    private int mPagePadding       = 0; // 卡片的padding, 卡片间的距离等于2倍的mPagePadding
    private int mShowLeftCardWidth = 0;   // 左边卡片显示大小

    private int           mFirstItemPos;
    private LoopViewPager mRecyclerView;

    private PagerSnapHelper      mPagerSnapHelper = new PagerSnapHelper();
    private OnPageChangeListener onPageChangeListener;

    public void attachToRecyclerView(final LoopViewPager mRecyclerView) {
        if (mRecyclerView == null) {
            return;
        }
        this.mRecyclerView = mRecyclerView;
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int position = getCurrentItem();
                //这里变换位置实现循环
                PageAdapter adapter = (PageAdapter) mRecyclerView.getAdapter();
                int count = adapter.getRealItemCount();
                if (adapter.isCanLoop()) {
                    if (position < count) {
                        position = count + position;
                        setCurrentItem(position);
                    } else if (position >= 2 * count) {
                        position = position - count;
                        setCurrentItem(position);
                    }
                }
                if (onPageChangeListener != null) {
                    onPageChangeListener.onScrollStateChanged(recyclerView, newState);
                    if (count != 0)
                        onPageChangeListener.onPageSelected(position % count);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onPageChangeListener != null)
                    onPageChangeListener.onScrolled(recyclerView, dx, dy);
                onScrolledChangedCallback();
            }
        });
        initWidth();
        mPagerSnapHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 初始化卡片宽度
     */
    private void initWidth() {
        mRecyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                scrollToPosition(mFirstItemPos);
            }
        });
    }

    public void setCurrentItem(int item) {
        setCurrentItem(item, false);
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        if (mRecyclerView == null) {
            return;
        }
        if (smoothScroll) {
            mRecyclerView.smoothScrollToPosition(item);
        } else {
            scrollToPosition(item);
        }
    }

    public void scrollToPosition(int pos) {
        if (mRecyclerView == null) {
            return;
        }
        ((LinearLayoutManager) Objects.requireNonNull(mRecyclerView.getLayoutManager())).scrollToPositionWithOffset(pos, (mPagePadding + mShowLeftCardWidth));
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                onScrolledChangedCallback();
            }
        });
    }

    public void setFirstItemPos(int firstItemPos) {
        this.mFirstItemPos = firstItemPos;
    }


    /**
     * RecyclerView位移事件监听, view大小随位移事件变化
     */
    private void onScrolledChangedCallback() {

    }

    public int getCurrentItem() {
        try {
            RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
            View view = mPagerSnapHelper.findSnapView(layoutManager);
            if (view != null)
                return layoutManager.getPosition(view);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getRealCurrentItem() {
        PageAdapter adapter = (PageAdapter) mRecyclerView.getAdapter();
        int count = adapter.getRealItemCount();
        return getCurrentItem() % count;
    }

    public void setPagePadding(int pagePadding) {
        mPagePadding = pagePadding;
    }

    public void setShowLeftCardWidth(int showLeftCardWidth) {
        mShowLeftCardWidth = showLeftCardWidth;
    }

    public int getFirstItemPos() {
        return mFirstItemPos;
    }

    public int getRealItemCount() {
        PageAdapter adapter = (PageAdapter) mRecyclerView.getAdapter();
        return adapter.getRealItemCount();
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
    }

}
