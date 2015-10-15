package com.bitdubai.sub_app.intra_user_community.fragments;

/**
 * Created by mati on 2015.10.14..
 */

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_cbp_api.layer.cbp_sub_app_module.crypto_broker_identity.interfaces.CryptoBrokerIdentityInformation;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.IntraUserIdentityInfoAdapter;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class RequestConnectionsFragment extends FermatListFragment<IntraUserInformation>
        implements FermatListItemListeners<CryptoBrokerIdentityInformation> {


    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<IntraUserInformation> identityInformationList;


    public static RequestConnectionsFragment newInstance() {
        return new RequestConnectionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            moduleManager = ((IntraUserSubAppSession) subAppsSession).getIntraUserModuleManager();
            errorManager = subAppsSession.getErrorManager();
            identityInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

//        FloatingActionButton newIdentityButton = (FloatingActionButton) layout.findViewById(R.id.new_crypto_broker_identity_float_action_button);
//        newIdentityButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changeActivity(Activities.CCP_SUB_APP_INTRA_IDENTITY_CREATE_IDENTITY.getCode());
//            }
//        });
//
//        if (getActivity().getActionBar() != null) {
//            getActivity().getActionBar().setDisplayShowHomeEnabled(false);
//        }

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
            adapter = new IntraUserIdentityInfoAdapter(getActivity(), identityInformationList);
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
    public List<IntraUserInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<IntraUserInformation> data = new ArrayList<>();
        if (moduleManager == null) {
            for (int i = 0; i < 20; i++) {
                //data.add(new IntraUserIdentityInformationImp("Broker Name " + i));
            }
        } else {
            //data = moduleManager.getAllCryptoBrokersIdentities(0, 0);
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
