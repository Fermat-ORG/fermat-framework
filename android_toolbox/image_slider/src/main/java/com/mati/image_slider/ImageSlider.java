package com.mati.image_slider;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mati on 2016.02.15..
 */
public class ImageSlider<I> extends RelativeLayout {

    ViewPager viewPager;
    ScreenPagerAdapter<I> sliderAdapter;
    private ViewGroup parent;

    public ImageSlider(Context context,ScreenPagerAdapter sliderAdapter) {
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

    public void init(){
        initView();
    }

    public void setParent(RelativeLayout parent) {
        this.parent = parent;
    }

    public void setSliderAdapter(ScreenPagerAdapter<I> sliderAdapter) {
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
        private ScreenPagerAdapter sliderAdapter;

        public Builder(Context context) {
            this.context = context;
            lstItems = new ArrayList<>();
        }

        public Builder addItem(I item){
            lstItems.add(item);
            return this;
        }

        public Builder setSliderAdapter(ScreenPagerAdapter sliderAdapter) {
            this.sliderAdapter = sliderAdapter;
            return this;
        }

        public Builder addItems(List<I> item){
            lstItems.addAll(item);
            return this;
        }

        public ImageSlider build() {
            ImageSlider imageSlider = new ImageSlider(context,sliderAdapter);
            imageSlider.setParent(parent);
            sliderAdapter.changeListItems(lstItems);
            imageSlider.init();
            return imageSlider;
        }

        public Builder setParent(RelativeLayout parent) {
            this.parent = parent;
            return this;
        }
    }

}
