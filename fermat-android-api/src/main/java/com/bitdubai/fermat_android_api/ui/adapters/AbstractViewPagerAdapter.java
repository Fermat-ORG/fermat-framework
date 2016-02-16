package com.bitdubai.fermat_android_api.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Matias on 23/07/2015.
 */

/**
 * AbstractViewPagerAdapter to add new subApp
 */
public abstract class AbstractViewPagerAdapter<I> extends PagerAdapter {

    private List<I> lstItems;
    private Context context;
    private LayoutInflater layoutInflater;

    public AbstractViewPagerAdapter(Context context, List<I> lstItems) {
        this.context = context;
        this.lstItems = lstItems;

    }

    public AbstractViewPagerAdapter(Context context) {
        this.context = context;
        this.lstItems = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return lstItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(getItemLayout(lstItems.get(position),position),container,false);
        bindHolder(item_view);
        ((ViewPager)container).addView(item_view);
        return item_view;
    }

    protected abstract void bindHolder(View item_view);


    protected abstract int getItemLayout(I item,int position);


    public void changeListItems(List<I> listItems) {
        this.lstItems = listItems;
    }
}