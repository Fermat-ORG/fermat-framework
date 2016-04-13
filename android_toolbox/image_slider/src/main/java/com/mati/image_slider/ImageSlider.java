package com.mati.image_slider;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.ui.adapters.AbstractViewPagerAdapter;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.15..
 */
public class ImageSlider<I> extends RelativeLayout {

    ViewPager viewPager;
    AbstractViewPagerAdapter<I,FermatViewHolder> sliderAdapter;
    private ViewGroup parent;

    public ImageSlider(Context context,AbstractViewPagerAdapter sliderAdapter) {
        super(context);
        this.sliderAdapter = sliderAdapter;
        initView();
    }

    public ImageSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageSlider(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void initView(){
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.slider_view_base_main, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(sliderAdapter);
    }


    public void setParent(RelativeLayout parent) {
        this.parent = parent;
    }

    public void setSliderAdapter(AbstractViewPagerAdapter<I,FermatViewHolder> sliderAdapter) {
        this.sliderAdapter = sliderAdapter;
    }

    public void changeListItems(List<I> listItems) {
        this.sliderAdapter.changeListItems(listItems);
        sliderAdapter.notifyDataSetChanged();
    }



    public static class Builder<I>{

        private Context context;
        private RelativeLayout parent;
        private List<I> lstItems;
        private AbstractViewPagerAdapter sliderAdapter;
        private FermatViewHolder fermatViewHolder;

        public Builder(Context context) {
            this.context = context;
            lstItems = new ArrayList<>();
        }

        public Builder addItem(I item){
            lstItems.add(item);
            return this;
        }

        public Builder setSliderAdapter(AbstractViewPagerAdapter sliderAdapter) {
            this.sliderAdapter = sliderAdapter;
            return this;
        }

        public Builder addItems(List<I> item){
            lstItems.addAll(item);
            return this;
        }

        public ImageSlider build() {
            sliderAdapter.changeListItems(lstItems);
            ImageSlider imageSlider = new ImageSlider(context,sliderAdapter);
            imageSlider.setParent(parent);
            return imageSlider;
        }

        public Builder setParent(RelativeLayout parent) {
            this.parent = parent;
            return this;
        }
    }

}
