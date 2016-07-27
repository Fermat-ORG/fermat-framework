package com.bitdubai.fermat_android_api.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Matias on 23/07/2015.
 */

/**
 * AbstractViewPagerAdapter to add new subApp
 */
public abstract class AbstractViewPagerAdapter<I, F extends FermatViewHolder> extends PagerAdapter {

    private List<I> lstItems;
    private Context context;
    private LayoutInflater layoutInflater;
    private HashMap<Integer, View> typesLoaded;

    public AbstractViewPagerAdapter(Context context, List<I> lstItems) {
        this.context = context;
        this.lstItems = lstItems;
        typesLoaded = new HashMap<>();
    }

    public AbstractViewPagerAdapter(Context context) {
        this.context = context;
        this.lstItems = new ArrayList<>();
        typesLoaded = new HashMap<>();
    }

    @Override
    public int getCount() {
        return lstItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        I item = lstItems.get(position);
        int itemType = getItemType(item, position);
        View itemView = null;
//        if(!typesLoaded.containsKey(itemType)){
//            itemView = layoutInflater.inflate(getItemLayout(item,position,itemType),container,false);
//            typesLoaded.put(itemType,itemView);
//        }else{
//            itemView = typesLoaded.get(itemType);
//        }
        itemView = layoutInflater.inflate(getItemLayout(item, position, itemType), container, false);
        bindHolder(createHolder(itemView, position, itemType), position, item);
        container.addView(itemView);
        return itemView;
    }

    protected abstract int getItemType(I item, int position);

    protected abstract F createHolder(View item_view, int position, int itemType);

    protected abstract void bindHolder(F fermatViewHolder, int position, I item);


    protected abstract int getItemLayout(I item, int position, int itemType);


    public void changeListItems(List<I> listItems) {
        this.lstItems = listItems;
    }
}