package com.bitdubai.fermat_android_api.ui.interfaces;

import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;

/**
 * RecyclerListFragment
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public interface RecyclerListFragment {

    FermatAdapter getAdapter();

    RecyclerView.LayoutManager getLayoutManager();
}
