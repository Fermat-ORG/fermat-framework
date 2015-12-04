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
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUsersListException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.adapters.IntraUserIdentityInfoAdapter;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;
import com.bitdubai.sub_app.intra_user_community.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class RequestConnectionsFragment extends FermatListFragment<IntraUserInformation>
        implements FermatListItemListeners<IntraUserInformation> {


    private static final int MAX = 15;
    private IntraUserModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<IntraUserInformation> identityInformationList;
    private int offset = 0;


    public static RequestConnectionsFragment newInstance() {
        return new RequestConnectionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            moduleManager = ((IntraUserSubAppSession) subAppsSession).getModuleManager();
            errorManager = subAppsSession.getErrorManager();
            identityInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_intra_user_connection_list_main;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.recycler_view;
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
        try {
            if (moduleManager != null) {
                data = moduleManager.getIntraUsersWaitingYourAcceptance(moduleManager.getActiveIntraUserIdentity().getPublicKey(), MAX, offset);
                offset = data.size();
            } else {
            }

        } catch (CantGetIntraUsersListException e) {
            e.printStackTrace();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    public void onItemClickListener(IntraUserInformation data, int position) {

        ConnectionOtherProfileFragment.newInstance();

        /*AcceptDialog acceptDialog = null;
        try {
            acceptDialog = new AcceptDialog(getActivity(),(IntraUserSubAppSession)subAppsSession,subAppResourcesProviderManager,data,moduleManager.getActiveIntraUserIdentity());
            acceptDialog.show();
        } catch (CantGetActiveLoginIdentityException e) {
            e.printStackTrace();
        }*/
        onRefresh();
    }

    @Override
    public void onLongItemClickListener(IntraUserInformation data, int position) {

    }
}
