package com.bitdubai.fermat_android_api.engine;

import android.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.01..
 */

public interface PaintActivtyFeactures {

    public void paintComboBoxInActionBar(ArrayAdapter adapter,ActionBar.OnNavigationListener listener);

    public RelativeLayout getActivityHeader();

    public void changeNavigationDrawerAdapter(FermatAdapter adapter);

    public void addNavigationViewHeader(View view);

    public RelativeLayout getToolbarHeader();

    public void invalidate();
}
