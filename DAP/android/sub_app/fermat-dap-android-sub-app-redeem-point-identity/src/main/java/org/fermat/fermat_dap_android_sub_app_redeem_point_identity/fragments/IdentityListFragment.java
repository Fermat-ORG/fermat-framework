package org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_identity_bitdubai.R;
import com.melnykov.fab.FloatingActionButton;

import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.common.adapters.RedeemPointIdentityAdapter;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.common.views.DividerItemDecoration;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.session.RedeemPointIdentitySubAppSessionReferenceApp;
import org.fermat.fermat_dap_android_sub_app_redeem_point_identity.util.CommonLogger;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.exceptions.CantListAssetRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_identity.interfaces.RedeemPointIdentityModuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings({"unchecked", "FieldCanBeLocal"})
public class IdentityListFragment extends FermatListFragment<RedeemPointIdentity, ReferenceAppFermatSession>
        implements FermatListItemListeners<RedeemPointIdentity> {


    private RedeemPointIdentityModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<RedeemPointIdentity> identityInformationList;

    RedeemPointIdentitySubAppSessionReferenceApp redeemPointIdentitySubAppSession;

    public static IdentityListFragment newInstance() {
        return new IdentityListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            // setting up  module
            redeemPointIdentitySubAppSession = (RedeemPointIdentitySubAppSessionReferenceApp) appSession;
            moduleManager = redeemPointIdentitySubAppSession.getModuleManager();
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
                    changeActivity(Activities.DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY.getCode(), appSession.getAppPublicKey());
                }
            });

            if (getActivity().getActionBar() != null) {
                getActivity().getActionBar().setDisplayShowHomeEnabled(false);
            }

            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            recyclerView.addItemDecoration(itemDecoration);

            //TODO: para la primer version limitamos la cantidad de identidades a una sola
            //TODO: Robert sacá esto si queres testear lo tuyo XXOO
            if (moduleManager.getRedeemPointsFromCurrentDeviceUser().size() > 0)
                newIdentityButton.setVisibility(View.INVISIBLE);
            else
                newIdentityButton.setVisibility(View.VISIBLE);
        } catch (CantListAssetRedeemPointException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Can't Get Asset Redeem Point List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Asset Redeem Point List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_dap_redeem_point_identity_list;
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
            adapter = new RedeemPointIdentityAdapter(getActivity(), identityInformationList);
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
    public List<RedeemPointIdentity> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<RedeemPointIdentity> data = new ArrayList<>();

        try {
            if (moduleManager == null) {
                Toast.makeText(getActivity(), "Module manager null", Toast.LENGTH_SHORT).show();
            } else {
                data = moduleManager.getRedeemPointsFromCurrentDeviceUser();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error. Get Identity Asset Redeem Point List", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return data;
    }

    @Override
    public void onItemClickListener(RedeemPointIdentity data, int position) {

    }

    @Override
    public void onLongItemClickListener(RedeemPointIdentity data, int position) {

    }
}
