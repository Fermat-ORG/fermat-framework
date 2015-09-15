package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Matias Furszyfer 2015.09.10..
 */
public class Node extends View {

    public static final int NODE = 1;
    public static final int LEAF = 2;


    private int type;

    public Node(Context context) {
        super(context);
    }

    public Node(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Node(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Node(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public int getType(){
        return type;
    }



}
