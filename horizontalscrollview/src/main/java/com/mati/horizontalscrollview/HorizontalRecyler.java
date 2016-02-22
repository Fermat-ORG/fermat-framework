package com.mati.horizontalscrollview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;

import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class HorizontalRecyler extends RecyclerView {

    private int DEFAULT_HEIGHT = 200;

    private LinearLayoutManager layoutManager;
    private HorizontalRecyclerAdapter horizontalRecyclerAdapter;
    private List<SetttingsItems> lstItems;
    private int height=0;
    private FermatListItemListeners fermatListItemListeners;

    public HorizontalRecyler(Context context,List<SetttingsItems> lstItems) {
        super(context);
        this.lstItems = lstItems;
        init();
    }

    public HorizontalRecyler(Context context) {
        super(context);
    }

    public HorizontalRecyler(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalRecyler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init(){

        ViewGroup.LayoutParams lps = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.height = (height>0)?height:DEFAULT_HEIGHT;
        setLayoutParams(lps);

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        setLayoutManager(layoutManager);

        horizontalRecyclerAdapter = new HorizontalRecyclerAdapter(getContext(), lstItems);
        horizontalRecyclerAdapter.setFermatListEventListener(fermatListItemListeners);
        setAdapter(horizontalRecyclerAdapter);

        setHasFixedSize(true);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setFermatListItemListeners(FermatListItemListeners fermatListItemListeners) {
        this.fermatListItemListeners = fermatListItemListeners;
    }


    public static class Builder{

        private Context context;
        private int height;
        private List<SetttingsItems> lstItems;
        private FermatListItemListeners fermatListItemListeners;


        public Builder(Context context, List<SetttingsItems> lstItems) {
            this.context = context;
            this.lstItems = lstItems;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setFermatListItemListeners(FermatListItemListeners fermatListItemListeners) {
            this.fermatListItemListeners = fermatListItemListeners;
            return this;
        }

        public HorizontalRecyler build() {
            HorizontalRecyler horizontalRecyler = new HorizontalRecyler(context, lstItems);
            horizontalRecyler.setHeight(height);
            horizontalRecyler.setFermatListItemListeners(fermatListItemListeners);
            return horizontalRecyler;
        }
    }


}
