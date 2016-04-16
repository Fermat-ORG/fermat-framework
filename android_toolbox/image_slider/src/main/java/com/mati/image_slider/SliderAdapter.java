package com.mati.image_slider;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.AbstractViewPagerAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

import java.util.List;

/**
 * Created by mati on 2016.02.16..
 */
public class SliderAdapter extends AbstractViewPagerAdapter<Integer,FermatViewHolder> {


    public SliderAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemType(Integer item, int position) {
        return R.layout.slider_image_holder;
    }

    @Override
    protected FermatViewHolder createHolder(View item_view, int position, int itemType) {
        return null;
    }

    @Override
    protected void bindHolder(FermatViewHolder fermatViewHolder, int position, Integer item) {

    }

    @Override
    protected int getItemLayout(Integer item, int position, int itemType) {
        return 0;
    }

    public SliderAdapter(Context context,List<Integer> list) {
        super(context,list);
    }



}
