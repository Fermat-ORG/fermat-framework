package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.AssetRedeemSelectRedeemPointsAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.RedeemPoint;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetRedeemSelectRedeemPointsFragment extends FermatWalletListFragment<RedeemPoint>
        implements FermatListItemListeners<RedeemPoint> {

    // Constants
    private static final String TAG = "AssetDeliverySelectUsersFragment";

    // Fermat Managers
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<RedeemPoint> redeemPoints;

    //UI
    private View noRPView;
    private Toolbar toolbar;

    public static AssetRedeemSelectRedeemPointsFragment newInstance() {
        return new AssetRedeemSelectRedeemPointsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((AssetUserSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            redeemPoints = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_ASSET_ISSUER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        noRPView = layout.findViewById(R.id.dap_wallet_asset_user_redeem_no_redeempoints);

        showOrHideNoUsersView(redeemPoints.isEmpty());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        try {
//            IssuerWalletNavigationViewPainter navigationViewPainter = new IssuerWalletNavigationViewPainter(getActivity(), null);
//            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
//        } catch (Exception e) {
//            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
//            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
//        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dap_wallet_asset_user_asset_redeem_select_redeempoints_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_select_redeempoints) {
            changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM, appSession.getAppPublicKey());
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar() {
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor("#381a5e"));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(Color.parseColor("#381a5e"));
            }
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_user_asset_redeem_select_redeempoints;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_user_asset_redeem_select_redeempoints_activity_recycler_view;
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
                redeemPoints = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(redeemPoints);

                showOrHideNoUsersView(redeemPoints.isEmpty());
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
            adapter = new AssetRedeemSelectRedeemPointsAdapter(getActivity(), redeemPoints, moduleManager);
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
    public void onItemClickListener(RedeemPoint data, int position) {
        //TODO select user
//        appSession.setData("asset_data", data);
//        changeActivity(Activities.DAP_ASSET_ISSUER_WALLET_ASSET_DETAIL, appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(RedeemPoint data, int position) {
    }

    @Override
    public List<RedeemPoint> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<RedeemPoint> redeemPoints = new ArrayList<>();
        if (moduleManager != null) {
            try {
                redeemPoints = Data.getConnectedRedeemPoints(moduleManager, redeemPoints);

                appSession.setData("redeem_points", redeemPoints);

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_ASSET_USER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry, an error happened in BrokerListActivityFragment (Module == null)",
                    Toast.LENGTH_SHORT).
                    show();
        }
        return redeemPoints;
    }

    private void showOrHideNoUsersView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noRPView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noRPView.setVisibility(View.GONE);
        }
    }
}

