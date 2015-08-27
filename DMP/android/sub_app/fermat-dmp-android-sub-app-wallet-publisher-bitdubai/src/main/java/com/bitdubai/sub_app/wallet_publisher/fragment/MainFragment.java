/*
 * @#MainFragment.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.commons.adapters.InformationPublishedComponentAdapter;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment</code> is
 * the fragment ui that represent the main page on the wallet publisher.
 * <p/>
 *
 * Created by natalia on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 26/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MainFragment extends FermatListFragment implements FermatListItemListeners<InformationPublishedComponent> {

    /**
     * Represent the TAG
     */
    private static final String TAG = "WalletPublisher";

    /**
     * Represent the walletPublisherModuleManager
     */
    private WalletPublisherModuleManager walletPublisherModuleManager;

    /**
     * Represent the informationPublishedComponentList
     */
    private List<InformationPublishedComponent> informationPublishedComponentList;

    /**
     * Represent the rootView
     */
    private View rootView;

    /**
     * Factory instance method
     *
     * @return MainFragment
     */
    public static MainFragment newInstance() {
        MainFragment f = new MainFragment();
        return f;
    }

    /**
     * (no-javadoc)
     * @see FermatFragment#onCreate(Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            /*
             * Get the module instance
             */
            walletPublisherModuleManager = ((WalletPublisherSubAppSession) subAppsSession).getWalletPublisherManager();

            /*
             * Load the data
             */
            loadInformationPublishedComponentList();


        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    /**
     * (no-javadoc)
     * @see FermatFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallet_publisher_main_fragment, container, false);




        return rootView;
    }

    /**
     * Load the Information Published Component
     *
     * @throws CantGetPublishedComponentInformationException
     */
    private void loadInformationPublishedComponentList() throws CantGetPublishedComponentInformationException {

        this.informationPublishedComponentList = walletPublisherModuleManager.getPublishedComponents(new PublisherIdentity() {
            @Override
            public String getAlias() {
                return null;
            }

            @Override
            public String getPublicKey() {
                return "04D707E1C33B2C82AE81E3FACA2025D1E0E439F9AAFD52CA844D3AFA47A0480093EF343790546F1E7C1BB454A426E054E26F080A61B1C0083C25EE77C7F97C6A80";
            }

            @Override
            public String getWebsiteurl() {
                return null;
            }

            @Override
            public String createMessageSignature(String mensage) {
                return null;
            }
        });

        Log.i(TAG, String.valueOf((informationPublishedComponentList != null? informationPublishedComponentList.size() : 0)));

    }


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.wallet_publisher_main_fragment;
    }

    @Override
    public RecyclerView getRecycler(View rootView) {

        if (recyclerView == null) {
            recyclerView = (RecyclerView) rootView.findViewById(R.id.component_recycler_view);
        }
        return recyclerView;
    }

    @Override
    public FermatAdapter getAdapter() {

        if (adapter == null) {

            adapter = new InformationPublishedComponentAdapter((Context)getActivity(), (ArrayList<InformationPublishedComponent>) informationPublishedComponentList);

        }

        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity());
        }
        return layoutManager;
    }

    @Override
    public void onItemClickListener(InformationPublishedComponent data, int position) {

    }

    @Override
    public void onLongItemClickListener(InformationPublishedComponent data, int position) {

    }
}
