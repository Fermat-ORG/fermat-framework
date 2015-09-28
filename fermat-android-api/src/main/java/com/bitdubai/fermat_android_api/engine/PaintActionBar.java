package com.bitdubai.fermat_android_api.engine;

import android.app.ActionBar;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.01..
 */

public interface PaintActionBar {

    public void paintComboBoxInActionBar(ArrayAdapter adapter,ActionBar.OnNavigationListener listener);
}
