package com.bacchoterra.memoriav3.utils;

import android.content.Context;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.bacchoterra.memoriav3.R;
import com.bacchoterra.memoriav3.fragments.BlankFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class TabLayoutUtil {

    private final FragmentManager fm;
    private final Context context;
    private final ViewPager viewPager;
    private final SmartTabLayout tabLayout;

    public TabLayoutUtil (FragmentManager fragmentManager, Context context, ViewPager viewPager2,SmartTabLayout smartTabLayout){

        this.fm = fragmentManager;
        this.context = context;
        this.viewPager = viewPager2;
        this.tabLayout = smartTabLayout;
    }

    public void createAdapter(){

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(fm, FragmentPagerItems.with(context)
                .add(R.string.categories, BlankFragment.class)
                .add(R.string.main_category, BlankFragment.class)
                .add(R.string.search, BlankFragment.class)
                .create());

        viewPager.setAdapter(adapter);
        tabLayout.setViewPager(viewPager);

    }

}
