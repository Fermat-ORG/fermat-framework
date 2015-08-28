/*
 * @#MainFragment.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_identity.publisher.interfaces.PublisherIdentity;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.exceptions.CantGetPublishedComponentInformationException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.InformationPublishedComponent;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.WalletPublisherModuleManager;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.adapters.InformationPublishedComponentAdapter;
import com.bitdubai.sub_app.wallet_publisher.session.WalletPublisherSubAppSession;
import com.bitdubai.sub_app.wallet_publisher.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class <code>com.bitdubai.sub_app.wallet_publisher.fragment.MainFragment</code> is
 * the fragment ui that represent the main page on the wallet publisher.
 * <p/>
 * <p/>
 * Created by natalia on 09/07/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 26/08/2015
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MainFragment extends FermatListFragment<InformationPublishedComponent>
        implements FermatListItemListeners<InformationPublishedComponent> {

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
     * Factory instance method
     *
     * @return MainFragment
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    /**
     * (no-javadoc)
     *
     * @see FermatFragment#onCreate(Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            CommonLogger.info(TAG, "Setting up WalletPublisherModule");
            /*
             * Get the module instance
             */
            walletPublisherModuleManager = ((WalletPublisherSubAppSession) subAppsSession).getWalletPublisherManager();
            /*
             * Load the data
             */
            informationPublishedComponentList = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    /**
     * Use this function to setting up your custom views, focused on any view that is not the recycler view.
     *
     * @param layout View root
     */
    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        CommonLogger.info(TAG, "Setting up other views");
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
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.component_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new InformationPublishedComponentAdapter(getActivity(), (ArrayList<InformationPublishedComponent>) informationPublishedComponentList);
            adapter.setFermatListEventListener(this);
        }
        return adapter;
    }


    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public void onItemClickListener(InformationPublishedComponent data, int position) {
        Toast.makeText(getActivity(), "Item Clicked: " + data.getDescriptions(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongItemClickListener(InformationPublishedComponent data, int position) {
        // do nothing..
    }

    /* Fermat Worker CallBack Methods */

    /**
     * @param result array of native object (handle result field with result[0], result[1],... result[n]
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) { // -> this mean that this fragment is attached to he activity and it still active on the UI Thread
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                informationPublishedComponentList = (ArrayList) result[0];
                if (adapter != null) {
                    adapter.changeDataSet((ArrayList) informationPublishedComponentList);
                }
            }
        }
    }

    /**
     * @param ex Throwable object
     */
    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {// -> this mean that this fragment is attached to he activity and it still active on the UI Thread
            swipeRefreshLayout.setRefreshing(false);
            //todo: alert to the user that error was thrown and need to try again...
        }
    }

    @Override
    public ArrayList<InformationPublishedComponent> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) throws CantGetPublishedComponentInformationException {
        ArrayList<InformationPublishedComponent> items = (ArrayList<InformationPublishedComponent>) walletPublisherModuleManager.getPublishedComponents(new PublisherIdentity() {
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
        CommonLogger.info(TAG, String.valueOf((informationPublishedComponentList != null ? informationPublishedComponentList.size() : 0)));
        return items; //todo: implement paging with refresh types and pos(values: 0 if the request is NEW and Last Item size if the request is OLD)
    }
}
