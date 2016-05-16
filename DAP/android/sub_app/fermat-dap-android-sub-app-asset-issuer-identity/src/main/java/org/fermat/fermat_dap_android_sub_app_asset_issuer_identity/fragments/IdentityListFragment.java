package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.fragments;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_identity_bitdubai.R;
import com.melnykov.fab.FloatingActionButton;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.common.adapters.IssuerIdentityAdapter;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.common.views.DividerItemDecoration;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.session.IssuerIdentitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.exceptions.CantListAssetIssuersException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_identity.interfaces.AssetIssuerIdentityModuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentityListFragment extends FermatListFragment<IdentityAssetIssuer>
        implements FermatListItemListeners<IdentityAssetIssuer> {


    private AssetIssuerIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<IdentityAssetIssuer> identityInformationList;

    IssuerIdentitySubAppSession issuerIdentitySubAppSession;

    public static IdentityListFragment newInstance() {
        return new IdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            issuerIdentitySubAppSession = (IssuerIdentitySubAppSession) appSession;
            moduleManager = issuerIdentitySubAppSession.getModuleManager();
            errorManager = appSession.getErrorManager();
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
                    changeActivity(Activities.DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY.getCode(), appSession.getAppPublicKey());
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
        } catch (CantListAssetIssuersException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Can't Get Asset Issuer List", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
        } catch (Exception e) {
                Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Asset Issuer List", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Identity Asset Issuer List", Toast.LENGTH_SHORT).show();
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
