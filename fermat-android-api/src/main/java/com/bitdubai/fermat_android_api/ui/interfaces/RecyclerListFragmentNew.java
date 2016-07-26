package com.bitdubai.fermat_android_api.ui.interfaces;

import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;

/**
 * RecyclerListFragment
 *
 * @author Matias Furszyfer
 * @version 1.0
 */
public interface RecyclerListFragmentNew {

    FermatAdapterNew getAdapter();

    RecyclerView.LayoutManager getLayoutManager();


}
