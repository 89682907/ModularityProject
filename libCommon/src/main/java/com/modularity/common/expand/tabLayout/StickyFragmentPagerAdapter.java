package com.modularity.common.expand.tabLayout;


import android.util.Log;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class StickyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment>  mFragments;
    private List<String>    mTitles;
    private FragmentManager mFragmentManager;
    private List<String>    mTagList = new ArrayList<>();

    public StickyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragments = fragments;
        mTitles = titles;
        this.mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mTagList.add(makeFragmentName(container.getId(), getItemId(position)));
        return super.instantiateItem(container, position);
    }

    public static String makeFragmentName(int viewId, long index) {
        return "android:switcher:" + viewId + ":" + index;
    }

    public void update(int item) {
        Fragment fragment = mFragmentManager.findFragmentByTag(mTagList.get(item));
        if (fragment != null) {
            switch (item) {
                case 0:
                    Log.d("StickyTab", "刷新-------   " + item);
                    break;
                case 1:
                    Log.d("StickyTab", "刷新-------   " + item);
//	        	ContractZWFragment f = (ContractZWFragment) mFragments.get(item);
//	        	f.updata();
//	          break;
                case 2:
                    Log.d("StickyTab", "刷新-------   " + item);
//	        	ContractBMFragment zw = (ContractBMFragment) mFragments.get(item);
//	        	zw.updata();
                    break;
                default:
                    break;
            }
        }
    }

    public interface FragmentSelectListener {
        void onFragmentSelectListener(int position, String tab, Fragment fragment);
    }
}
