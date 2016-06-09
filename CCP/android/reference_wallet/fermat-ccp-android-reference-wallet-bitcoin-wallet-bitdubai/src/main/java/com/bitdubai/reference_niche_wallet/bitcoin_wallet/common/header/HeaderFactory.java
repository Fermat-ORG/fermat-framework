package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.header;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by Matias Furszyfer on 2015.11.16..
 */
public abstract class HeaderFactory <OCL extends View.OnClickListener>{

    protected ViewGroup mRootView;
    private View mHeader;
    protected OCL onClickListener;


    public static View constructHeader(ViewGroup view){
        BitcoinWalletHeaderFactory headerFactory = new BitcoinWalletHeaderFactory(view);
        return headerFactory.obtainView();
    }

    public HeaderFactory(ViewGroup mRootView) {
        this.mRootView = mRootView;
        mRootView.setVisibility(View.VISIBLE);
        mRootView.setBackgroundColor(Color.parseColor(setParentBackgroundColor()));
        onClickListener = setOnClickListener();
        setUp();
    }

    public void setVisibility(int visibility){
        mRootView.setVisibility(visibility);
    }

    protected LayoutInflater getLayoutInflater(){
        return (LayoutInflater) mRootView.getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    private void setUp(){
        LayoutInflater layoutInflater = getLayoutInflater();
        mHeader = View.inflate(mRootView.getContext(), setLayoutId(), mRootView);
        constructHeader(layoutInflater,(ViewGroup)mHeader);
    }

    public View obtainView() {
        return mRootView;
    }

    protected View getHeader(){
        return mHeader;
    }

    public abstract void constructHeader(LayoutInflater layoutInflater,ViewGroup header);

    public abstract String setParentBackgroundColor();

    public abstract int setLayoutId();

    protected abstract OCL setOnClickListener();
}
