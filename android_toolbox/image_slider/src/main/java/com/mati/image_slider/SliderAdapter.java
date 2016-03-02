package com.mati.image_slider;

import android.content.Context;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.AbstractViewPagerAdapter;

import java.util.List;

/**
 * Created by mati on 2016.02.16..
 */
public class SliderAdapter extends AbstractViewPagerAdapter<Integer> {


    public SliderAdapter(Context context) {
        super(context);
    }

    public SliderAdapter(Context context,List<Integer> list) {
        super(context,list);
    }

    @Override
    protected void bindHolder(View item_view) {

    }

    @Override
    protected int getItemLayout(Integer item, int position) {
        return R.layout.slider_image_holder;
    }
}
