package com.example.administrator.utils;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter{

    private List<View>pagerList;
    private List<String>titleList;

    public MyPagerAdapter(List<View>pagerList,List<String>titleList) {
        this.pagerList = pagerList;
        this.titleList = titleList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(pagerList.get(position));
        return pagerList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(pagerList.get(position));
    }

    @Override
    public int getCount() {
        return pagerList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object age1) {
        return arg0 == age1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
