package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.common.adapters.IssuerIdentityAdapter;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.common.views.DividerItemDecoration;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.session.IssuerIdentitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuerManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentityListFragment extends FermatListFragment<IdentityAssetIssuer>
        implements FermatListItemListeners<IdentityAssetIssuer> {


    private IdentityAssetIssuerManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<IdentityAssetIssuer> identityInformationList;


    public static IdentityListFragment newInstance() {
        return new IdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            moduleManager = ((IssuerIdentitySubAppSession) subAppsSession).getModuleManager();
            errorManager = subAppsSession.getErrorManager();
            identityInformationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        try {

            FloatingActionButton newIdentityButton = (FloatingActionButton) layout.findViewById(R.id.new_crypto_broker_identity_float_action_button);
            newIdentityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode(),subAppsSession.getAppPublicKey());
                }
            });

            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().setDisplayShowHomeEnabled(false);
            }

            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            recyclerView.addItemDecoration(itemDecoration);

            //TODO: para la primer version limitamos la cantidad de identidades a una sola
            //TODO: Robert sacá esto si queres testear lo tuyo XXOO
            if (moduleManager.getIdentityAssetIssuersFromCurrentDeviceUser().size() > 0)
                newIdentityButton.setVisibility(View.INVISIBLE);
            else
                newIdentityButton.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Intra User List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dap_issuer_identity_list;
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


    @SuppressWarnings("unchecked")
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

    @SuppressWarnings("unchecked")
    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new IssuerIdentityAdapter(getActivity(), identityInformationList);
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
    public List<IdentityAssetIssuer> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<IdentityAssetIssuer> data = new ArrayList<>();

        try {
            if (moduleManager == null) {
                Toast.makeText(getActivity(), "Module manager null", Toast.LENGTH_SHORT).show();
            } else {
                data = moduleManager.getIdentityAssetIssuersFromCurrentDeviceUser();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Identity Asset Issuer ist", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        return data;
    }

    @Override
    public void onItemClickListener(IdentityAssetIssuer data, int position) {

    }

    @Override
    public void onLongItemClickListener(IdentityAssetIssuer data, int position) {

    }
}
