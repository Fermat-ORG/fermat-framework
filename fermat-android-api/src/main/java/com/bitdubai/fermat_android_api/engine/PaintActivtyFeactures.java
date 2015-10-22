package com.bitdubai.fermat_android_api.engine;

import android.app.ActionBar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.01..
 */

public interface PaintActivtyFeactures {

    public void paintComboBoxInActionBar(ArrayAdapter adapter,ActionBar.OnNavigationListener listener);

    public RelativeLayout getActivityHeader();

    public void changeNavigationDrawerAdapter(ListAdapter listAdapter);

    public void invalidate();
}
