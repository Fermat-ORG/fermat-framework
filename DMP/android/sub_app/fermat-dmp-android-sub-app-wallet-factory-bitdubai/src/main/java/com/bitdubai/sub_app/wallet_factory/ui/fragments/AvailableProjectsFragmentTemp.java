package com.bitdubai.sub_app.wallet_factory.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.adapters.InstalledWalletsAdapter;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.bitdubai.sub_app.wallet_factory.utils.CommonLogger;

/**
 * Available projects fragment to clone
 *
 * @author Francisco Vasquez
 * @author Matias Furszy
 * @version 1.0
 */
public class AvailableProjectsFragmentTemp extends FermatFragment {

    private final String TAG = "FactoryProjects";
    /**
     * Manager
     */
    private WalletFactoryManager manager;

    /**
     * View references
     */
    private View rootView;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private InstalledWalletsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.factory_available_projects_fragment, container, false);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up wallet manager
            setManager(((WalletFactorySubAppSession) subAppsSession).getWalletFactoryManager());
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    public void setManager(WalletFactoryManager manager) {
        this.manager = manager;
    }

    public WalletFactoryManager getManager() {
        return manager;
    }
}
