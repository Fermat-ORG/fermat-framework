package com.bitdubai.fermat_android_api.engine;

import android.app.ActionBar;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

/**
 * Created by Matias Furszyfer on 2015.09.01..
 */

public interface PaintActivityFeatures {

    void paintComboBoxInActionBar(ArrayAdapter adapter, ActionBar.OnNavigationListener listener);

    android.support.v7.widget.Toolbar getToolbar();

    RelativeLayout getToolbarHeader();

    void invalidate();

    void addCollapseAnimation(ElementsWithAnimation elementsWithAnimation);

    // Esto no deberia estar acá
    void addDesktopCallBack(DesktopHolderClickCallback desktopHolderClickCallback);

    void setMenuSettings(View viewGroup, View container_title);
    @Deprecated
    void setActivityBackgroundColor(Drawable drawable);

}
