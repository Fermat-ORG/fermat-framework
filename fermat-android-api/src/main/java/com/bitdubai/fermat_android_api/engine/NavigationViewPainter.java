package com.bitdubai.fermat_android_api.engine;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

/**
 * Created by mati on 2015.11.24..
 */
public interface NavigationViewPainter {

    View addNavigationViewHeader();

    FermatAdapter addNavigationViewAdapter();

    ViewGroup addNavigationViewBodyContainer(LayoutInflater layoutInflater,ViewGroup base);

    Drawable addBodyBackground();

    int addBodyBackgroundColor();
}
