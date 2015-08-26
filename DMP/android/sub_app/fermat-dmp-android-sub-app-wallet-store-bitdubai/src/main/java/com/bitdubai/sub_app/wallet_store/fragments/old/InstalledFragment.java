package com.bitdubai.sub_app.wallet_store.fragments.old;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;

/**
 * List of Installed Wallets Fragment
 *
 * @author Francisco Vasquez
 * @version 1.0
 */
public class InstalledFragment extends FermatListFragment {

    /**
     * STATIC
     */
    private static final String ARG_POSITION = "position";
    private static final int PORTRAIT_SPAN_COUNT = 2;
    private static final int LANDSCAPE_SPAN_COUNT = 3;

    /**
     * MODULE
     */
    private WalletStoreModuleManager _manager;

    /**
     * Set module manager
     *
     * @param manager WalletStoreModuleManager object
     */
    public void setWalletManager(WalletStoreModuleManager manager) {
        _manager = manager;
    }

    /**
     * Create a new instance of this fragment
     *
     * @param position tab position
     * @param manager  WalletStoreModuleManager instance object
     * @return InstalledFragment instance object
     */
    public static InstalledFragment newInstance(int position, @Nullable WalletStoreModuleManager manager) {
        InstalledFragment f = new InstalledFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        f.setWalletManager(manager);
        return f;
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public RecyclerView getRecycler(View rootView) {
        if (recyclerView == null) {
            //// TODO: 19/08/15 implement layout of this fragment and get recyclerview id
            recyclerView = (RecyclerView) rootView.findViewById(0);
            recyclerView.setHasFixedSize(true);
        }
        return recyclerView;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            //// TODO: 19/08/15 Create an adapter instance
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            int span = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE ?
                    LANDSCAPE_SPAN_COUNT : PORTRAIT_SPAN_COUNT;
            layoutManager = new GridLayoutManager(getActivity(), span);
        }
        return layoutManager;
    }
}
