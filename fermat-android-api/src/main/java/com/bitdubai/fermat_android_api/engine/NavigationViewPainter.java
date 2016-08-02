package com.bitdubai.fermat_android_api.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.support.v4.content.LocalBroadcastManager;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

import java.lang.ref.WeakReference;

/**
 * Created by mati on 2015.11.24..
 */
public abstract class NavigationViewPainter implements NavigationViewPainterInterface{

    private WeakReference<Context> context;

    public NavigationViewPainter(Context context) {
        this.context = new WeakReference<Context>(context);
    }
    public View addNavigationViewHeader(){
        return null;
    }

    public FermatAdapter addNavigationViewAdapter(){
        return null;
    }

    public ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater, ViewGroup base){
        return null;
    }

    public Bitmap addBodyBackground(){
        return null;
    }

    public int addBodyBackgroundColor(){
        return -1;
    }

    public RecyclerView.ItemDecoration addItemDecoration(){
        return null;
    }

    public boolean hasBodyBackground(){
        return false;
    }

    public boolean hasClickListener(){
        return false;
    }

    protected void sendBroadcast(){
//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance();
    }

    public Context getContext() {
        return context.get();
    }

    public void onDestroy(){
        context.clear();
    }
}
