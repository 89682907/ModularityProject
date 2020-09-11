package com.modularity.common.expand.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.modularity.common.R;
import com.modularity.common.expand.banner.adapter.PageAdapter;
import com.modularity.common.expand.banner.helper.LoopScaleHelper;
import com.modularity.common.expand.banner.holder.IBannerHolderCreator;
import com.modularity.common.expand.banner.listener.PageChangeListener;
import com.modularity.common.expand.banner.listener.OnItemClickListener;
import com.modularity.common.expand.banner.listener.OnPageChangeListener;
import com.modularity.common.expand.banner.view.LoopViewPager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BannerView<T> extends RelativeLayout {
    private List<T>              mDates;
    private int[]                mPageIndicatorId;
    private PageAdapter          mPageAdapter;
    private LoopViewPager        mViewPager;
    private ViewGroup            mPageTurningPoint;
    private boolean              turning;
    private LoopScaleHelper      mLoopScaleHelper;
    private PageChangeListener   mPageChangeListener;
    private OnPageChangeListener onPageChangeListener;
    private AdSwitchTask         mSwitchTask;
    private boolean              canTurn         = false;
    private boolean              canLoop         = true;
    private long                 autoTurningTime = -1;
    private ArrayList<ImageView> mPointViews     = new ArrayList<>();

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public BannerView(Context context) {
        super(context);
        init(context);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BannerView);
        canLoop = a.getBoolean(R.styleable.BannerView_canLoop, true);
        autoTurningTime = a.getInteger(R.styleable.BannerView_autoTurningTime, -1);
        a.recycle();
        init(context);
    }

    private void init(Context context) {
        View hView = LayoutInflater.from(context).inflate(R.layout.include_viewpager, this, true);
        mViewPager = hView.findViewById(R.id.cbLoopViewPager);
        mPageTurningPoint = hView.findViewById(R.id.loPageTurningPoint);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mViewPager.setLayoutManager(linearLayoutManager);
        mLoopScaleHelper = new LoopScaleHelper();
        mSwitchTask = new AdSwitchTask(this);
    }

    public BannerView setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mViewPager.setLayoutManager(layoutManager);
        return this;
    }

    public BannerView setPages(IBannerHolderCreator holderCreator, List<T> datas) {
        this.mDates = datas;
        mPageAdapter = new PageAdapter(holderCreator, mDates, canLoop);
        mViewPager.setAdapter(mPageAdapter);
        if (mPageIndicatorId != null)
            setPageIndicator(mPageIndicatorId);
        mLoopScaleHelper.setFirstItemPos(canLoop ? mDates.size() : 0);
        mLoopScaleHelper.attachToRecyclerView(mViewPager);

        return this;
    }

    public BannerView setCanLoop(boolean canLoop) {
        this.canLoop = canLoop;
        mPageAdapter.setCanLoop(canLoop);
        notifyDataSetChanged();
        return this;
    }

    public boolean isCanLoop() {
        return canLoop;
    }


    /**
     * 通知数据变化
     */
    public void notifyDataSetChanged() {
        Objects.requireNonNull(mViewPager.getAdapter()).notifyDataSetChanged();
        if (mPageIndicatorId != null)
            setPageIndicator(mPageIndicatorId);
        mLoopScaleHelper.setCurrentItem(canLoop ? mDates.size() : 0);
    }

    /**
     * 设置底部指示器是否可见
     */
    public BannerView setPointViewVisible(boolean visible) {
        mPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 底部指示器资源图片
     */
    public BannerView setPageIndicator(int[] page_indicatorId) {
        mPageTurningPoint.removeAllViews();
        mPointViews.clear();
        this.mPageIndicatorId = page_indicatorId;
        if (mDates == null) return this;
        for (int count = 0; count < mDates.size(); count++) {
            // 翻页指示的点
            ImageView pointView = new ImageView(getContext());
            pointView.setPadding(5, 0, 5, 0);
            if (mLoopScaleHelper.getFirstItemPos() % mDates.size() == count)
                pointView.setImageResource(page_indicatorId[1]);
            else
                pointView.setImageResource(page_indicatorId[0]);
            mPointViews.add(pointView);
            mPageTurningPoint.addView(pointView);
        }
        mPageChangeListener = new PageChangeListener(mPointViews, page_indicatorId);
        mLoopScaleHelper.setOnPageChangeListener(mPageChangeListener);
        if (onPageChangeListener != null)
            mPageChangeListener.setOnPageChangeListener(onPageChangeListener);

        return this;
    }

    public OnPageChangeListener getOnPageChangeListener() {
        return onPageChangeListener;
    }

    /**
     * 设置翻页监听器
     */
    public BannerView setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener;
        //如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
        if (mPageChangeListener != null)
            mPageChangeListener.setOnPageChangeListener(onPageChangeListener);
        else mLoopScaleHelper.setOnPageChangeListener(onPageChangeListener);
        return this;
    }

    /**
     * 监听item点击
     */
    public BannerView setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        if (onItemClickListener == null) {
            mPageAdapter.setOnItemClickListener(null);
            return this;
        }
        mPageAdapter.setOnItemClickListener(onItemClickListener);
        return this;
    }

    /**
     * 获取当前页对应的position
     */
    public int getCurrentItem() {
        return mLoopScaleHelper.getRealCurrentItem();
    }

    /**
     * 设置当前页对应的position
     */
    public BannerView setCurrentItem(int position, boolean smoothScroll) {
        mLoopScaleHelper.setCurrentItem(canLoop ? mDates.size() + position : position, smoothScroll);
        return this;
    }

    /**
     * 设置第一次加载当前页对应的position
     * setPageIndicator之前使用
     */
    public BannerView setFirstItemPos(int position) {
        mLoopScaleHelper.setFirstItemPos(canLoop ? mDates.size() + position : position);
        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中 （RelativeLayout.CENTER_HORIZONTAL），居右 （RelativeLayout.ALIGN_PARENT_RIGHT）
     */
    public BannerView setPageIndicatorAlign(PageIndicatorAlign align) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPageTurningPoint.getLayoutParams();
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
        mPageTurningPoint.setLayoutParams(layoutParams);
        return this;
    }

    /***
     * 是否开启了翻页
     */
    public boolean isTurning() {
        return turning;
    }

    /***
     * 开始翻页
     * @param autoTurningTime 自动翻页时间
     */
    public BannerView startTurning(long autoTurningTime) {
        if (autoTurningTime < 0) return this;
        //如果是正在翻页的话先停掉
        if (turning) {
            stopTurning();
        }
        //设置可以翻页并开启翻页
        canTurn = true;
        this.autoTurningTime = autoTurningTime;
        turning = true;
        postDelayed(mSwitchTask, autoTurningTime);
        return this;
    }

    public BannerView startTurning() {
        startTurning(autoTurningTime);
        return this;
    }


    public void stopTurning() {
        turning = false;
        removeCallbacks(mSwitchTask);
    }

    //触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (canTurn) startTurning(autoTurningTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (canTurn) stopTurning();
        }

        return super.dispatchTouchEvent(ev);
    }

    static class AdSwitchTask implements Runnable {

        private final WeakReference<BannerView> reference;

        AdSwitchTask(BannerView convenientBanner) {
            this.reference = new WeakReference<>(convenientBanner);
        }

        @Override
        public void run() {
            BannerView convenientBanner = reference.get();

            if (convenientBanner != null) {
                if (convenientBanner.mViewPager != null && convenientBanner.turning) {
                    int page = convenientBanner.mLoopScaleHelper.getCurrentItem() + 1;
                    convenientBanner.mLoopScaleHelper.setCurrentItem(page, true);
                    convenientBanner.postDelayed(convenientBanner.mSwitchTask, convenientBanner.autoTurningTime);
                }
            }
        }
    }

}
