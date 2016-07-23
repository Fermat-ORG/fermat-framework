package com.bitdubai.fermat_android_api.engine;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Matias Furszyfer on 2015.11.27..
 */
public interface FooterViewPainter {


    ViewGroup addFooterViewContainer(LayoutInflater layoutInflater, ViewGroup footer_container);

    View addNavigationViewFooterElementVisible(LayoutInflater layoutInflater, FrameLayout slide_container);

    Drawable addBodyBackground();

    int addBodyBackgroundColor();

}
