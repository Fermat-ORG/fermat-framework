package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;

import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;

/**
 * Created by nelson on 28/10/15.
 */
public interface RecyclerExpandableListFragment {
    ExpandableRecyclerAdapter getAdapter();

    RecyclerView.LayoutManager getLayoutManager();
}
