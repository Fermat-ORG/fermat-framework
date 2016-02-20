package com.mati.horizontalscrollview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import java.util.List;

/**
 * Created by mati on 2016.02.19..
 */
public class HorizontalRecyler extends RecyclerView {

    private LinearLayoutManager layoutManager;
    private List<SetttingsItems> lstItems;

    public HorizontalRecyler(Context context,List<SetttingsItems> lstItems) {
        super(context);
        this.lstItems = lstItems;
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
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);



        setLayoutManager(layoutManager);
    }


}
