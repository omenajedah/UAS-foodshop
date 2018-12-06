package com.firman.ecommerce.foodshop.activity.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Firman on 11/18/2018.
 */
public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();



    public TabFragmentPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public  CharSequence getPageTitle(int position){
        return titles.get(position);
    }


    @Override
    public  int getCount(){
        return  fragments.size();
    }

    public void add(String title, Fragment fragment) {
        titles.add(title);
        fragments.add(fragment);
        notifyDataSetChanged();
    }

    public void set(int index, String title, Fragment fragment) {
        titles.set(index, title);
        fragments.set(index, fragment);
        notifyDataSetChanged();
    }

    public void delete(int index) {
        titles.remove(index);
        fragments.remove(index);
        notifyDataSetChanged();
    }




}