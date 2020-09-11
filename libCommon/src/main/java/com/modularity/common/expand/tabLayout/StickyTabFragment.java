package com.modularity.common.expand.tabLayout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.modularity.common.R;

import java.lang.reflect.Field;
import java.util.List;

/**
 * private void initTabs(){
 *         List<String> tabslist = new ArrayList<>();
 *         tabslist.add(getString(R.string.tab_fuel_card_loss));
 *         tabslist.add(getString(R.string.tab_fuel_card_change));
 *
 *         fuelCardLossFragment = new FuelCardLossFragment();
 *         fuelCardChangeFragment = new FuelCardChangeFragment();
 *         List<Fragment> fragments = new ArrayList<>();
 *         fragments.add(fuelCardLossFragment);
 *         fragments.add(fuelCardChangeFragment);
 *
 *         new StickyTabFragment.Builder(this)
 *                 .setTabFragmentsList(fragments)
 *                 .setTabsList(tabslist)
 *                 .setTabSpace(this,60,60)
 *                 .setTabLayout((TabLayout) View.inflate(this,R.layout.refuel_order_tab_layout,null))
 *                 .setFragmentSelectListener(new StickyFragmentPagerAdapter.FragmentSelectListener() {
 *                     @Override
 *                     public void onFragmentSelectListener(int position,String tab, Fragment fragment) {
 *                         selectTab = position;
 *                     }
 *                 })
 *                 .create();
 *     }
 *
 *
 *  TabLayout
 * <?xml version="1.0" encoding="utf-8"?>
 * <android.support.design.widget.TabLayout
 *     xmlns:android="http://schemas.android.com/apk/res/android"
 *     style="@style/CustomTabLayout"
 *     android:id="@+id/tablayout"
 *     android:background="@android:color/white"
 *     android:layout_width="match_parent"
 *     android:layout_height="44dp"/>
 */

public class StickyTabFragment extends Fragment {

    private ViewPager mViewPager;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private StickyFragmentPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private View mView;
    private LinearLayout mTabLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.sticky_tab_fragment, container, false);
            initView(mView);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    private void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.ctl_hedaview);
        mTabLinearLayout = (LinearLayout) view.findViewById(R.id.ll_tab);
    }


    private void bindData(TabLayout tabLayout, List<String> tabsList, List<Fragment> tabFragmentsList, View headView, FragmentManager fragmentManager, final StickyFragmentPagerAdapter.FragmentSelectListener fragmentSelectListener, Context context, int leftSpace, int rightSpace) {
        this.mTabLayout = tabLayout;
        mTabLinearLayout.removeAllViews();
        mTabLinearLayout.addView(tabLayout);
        mAdapter = new StickyFragmentPagerAdapter(fragmentManager, tabFragmentsList, tabsList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (fragmentSelectListener != null) {
                    fragmentSelectListener.onFragmentSelectListener(position, mAdapter.getPageTitle(position).toString(), mAdapter.getItem(position));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        if (tabLayout != null) {
            // TabLayout
            mTabLayout.setupWithViewPager(mViewPager);// 将TabLayout和ViewPager关联起来。
//            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);//tab充满布局
//            mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);//tab居中显示
//            mTabLayout.setTabMode(TabLayout.MODE_FIXED);//固定个数
//            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可移动
//
//            if (selectTextColor > 0 && normalTextColor > 0) {
//                mTabLayout.setTabTextColors(normalTextColor, selectTextColor);
//            }
            if (context != null && (leftSpace > 0 || rightSpace > 0)) {
                setIndicator(context, leftSpace, rightSpace);
            }
        }

        if (headView != null) {
            mCollapsingToolbarLayout.addView(headView);
        }


    }

    private void setIndicator(Context context, int leftDip, int rightDip) {
        Class<?> tabLayout = mTabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        if (tabStrip != null) {
            tabStrip.setAccessible(true);
            LinearLayout ll_tab = null;
            try {
                ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            int left = (int) (getDisplayMetrics(context).density * leftDip);
            int right = (int) (getDisplayMetrics(context).density * rightDip);

            for (int i = 0; i < ll_tab.getChildCount(); i++) {
                View child = ll_tab.getChildAt(i);
                child.setPadding(0, 0, 0, 0);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                params.leftMargin = left;
                params.rightMargin = right;
                child.setLayoutParams(params);
                child.invalidate();
            }
        }

    }

    private DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    public static final class Builder {

        private FragmentManager fragmentManager;
        private List<String> tabsList;
        private List<Fragment> tabFragmentsList;
        private View headView;
        private StickyTabFragment stickyTabFragment;
        private StickyFragmentPagerAdapter.FragmentSelectListener fragmentSelectListener;
        private TabLayout tabLayout;
        private Context context;
        private int leftSpace, rightSpace;


        public Builder(Fragment parentFragment) {
            fragmentManager = parentFragment.getChildFragmentManager();
            stickyTabFragment = (StickyTabFragment) fragmentManager.findFragmentById(R.id.sticky_tab_bar);
        }

        public Builder(AppCompatActivity parentActivity) {
            fragmentManager = parentActivity.getSupportFragmentManager();
            stickyTabFragment = (StickyTabFragment) fragmentManager.findFragmentById(R.id.sticky_tab_bar);
        }

        public Builder setHeaderView(View headView) {
            this.headView = headView;
            return this;
        }

        public Builder setTabLayout(TabLayout tabLayout) {
            this.tabLayout = tabLayout;
            return this;
        }

        public Builder setTabsList(List<String> tabsList) {
            this.tabsList = tabsList;
            return this;
        }

        public Builder setTabSpace(Context context, int leftSpace, int rightSpace) {
            this.context = context;
            this.leftSpace = leftSpace;
            this.rightSpace = rightSpace;
            return this;
        }

        public Builder setTabFragmentsList(List<Fragment> tabFragmentsList) {
            this.tabFragmentsList = tabFragmentsList;
            return this;
        }

        public Builder setFragmentSelectListener(StickyFragmentPagerAdapter.FragmentSelectListener fragmentSelectListener) {
            this.fragmentSelectListener = fragmentSelectListener;
            return this;
        }

        public StickyTabFragment create() {
            stickyTabFragment.bindData(tabLayout, tabsList, tabFragmentsList, headView, fragmentManager, fragmentSelectListener, context, leftSpace, rightSpace);
            return stickyTabFragment;
        }
    }


}
