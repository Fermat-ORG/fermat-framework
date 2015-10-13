package com.bitdubai.sub_app.crypto_broker_identity.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.exceptions.CantGetCryptoBrokerListException;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.crypto_broker_identity.common.adapters.CryptoBrokerIdentityInfoAdapter;
import com.bitdubai.sub_app.crypto_broker_identity.common.model.CryptoBrokerIdentityInformationImp;
import com.bitdubai.sub_app.crypto_broker_identity.common.views.DividerItemDecoration;
import com.bitdubai.sub_app.crypto_broker_identity.session.CryptoBrokerIdentitySubAppSession;
import com.bitdubai.sub_app.crypto_broker_identity.util.CommonLogger;
import com.bitdubai.sub_app.intra_user_identity.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CryptoBrokerIdentityListFragment extends FermatListFragment<CryptoBrokerIdentityInformation>
        implements FermatListItemListeners<CryptoBrokerIdentityInformation> {


    private CryptoBrokerIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<CryptoBrokerIdentityInformation> identityInformationList;


    public static CryptoBrokerIdentityListFragment newInstance() {
        return new CryptoBrokerIdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            moduleManager = ((CryptoBrokerIdentitySubAppSession) subAppsSession).getModuleManager();
            errorManager = subAppsSession.getErrorManager();
            identityInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        FloatingActionButton newIdentityButton = (FloatingActionButton) layout.findViewById(R.id.new_crypto_broker_identity_float_action_button);
        newIdentityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_SUB_APP_CRYPTO_BROKER_IDENTITY_CREATE_IDENTITY.getCode());
            }
        });

        if (getActivity().getActionBar() != null) {
            getActivity().getActionBar().setDisplayShowHomeEnabled(false);
        }

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_crypto_broker_identity_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.crypto_broker_identity_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                identityInformationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(identityInformationList);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CryptoBrokerIdentityInfoAdapter(getActivity(), identityInformationList);
            adapter.setFermatListEventListener(this); // setting up event listeners
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
    public List<CryptoBrokerIdentityInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoBrokerIdentityInformation> data = new ArrayList<>();
        if (moduleManager == null) {
            for (int i = 0; i < 20; i++) {
                data.add(new CryptoBrokerIdentityInformationImp("Broker Name " + i));
            }
        } else {
            try {
                data = moduleManager.getAllCryptoBrokersIdentities(0, 0);
            } catch (CantGetCryptoBrokerListException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    @Override
    public void onItemClickListener(CryptoBrokerIdentityInformation data, int position) {

    }

    @Override
    public void onLongItemClickListener(CryptoBrokerIdentityInformation data, int position) {

    }
}
