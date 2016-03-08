package com.bitdubai.fermat_android_api.engine;

import android.graphics.drawable.Drawable;
import android.widget.RelativeLayout;


/**
 * Created by Matias Furszyfer on 2015.09.01..
 */

public interface PaintActivityFeatures {

    public android.support.v7.widget.Toolbar getToolbar();

    public RelativeLayout getToolbarHeader();

    public void invalidate();

    public void addCollapseAnimation(ElementsWithAnimation elementsWithAnimation);

    // Esto no deberia estar acá
    public void addDesktopCallBack(DesktopHolderClickCallback desktopHolderClickCallback);

    @Deprecated
    void setActivityBackgroundColor(Drawable drawable);

}
