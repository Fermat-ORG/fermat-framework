package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.adapters.MyAssetsAdapter;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.sessions.RedeemPointSession;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by frank on 12/14/15.
 */
public class MyAssetsActivityFragment extends FermatWalletListFragment<DigitalAsset>
        implements FermatListItemListeners<DigitalAsset> {

    // Constants
    private static final String TAG = "MyAssetsActivityFragment";

    // Fermat Managers
    private AssetRedeemPointWalletSubAppModule moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<DigitalAsset> digitalAssets;

    //UI
    private View noAssetsView;

    public static MyAssetsActivityFragment newInstance() {
        return new MyAssetsActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((RedeemPointSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            digitalAssets = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_REDEEM_POINT_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        noAssetsView = layout.findViewById(R.id.dap_wallet_asset_issuer_no_assets);

        showOrHideNoAssetsView(digitalAssets.isEmpty());
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

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
//            toolbar.setBackgroundColor(Color.parseColor("#1d1d25"));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBackgroundColor(Color.TRANSPARENT);
            toolbar.setBottom(Color.WHITE);
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//                Window window = getActivity().getWindow();
//                window.setStatusBarColor(Color.parseColor("#1d1d25"));
//            }
            Drawable drawable = null;
            //TODO uncomment
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_redeem_point_action_bar_gradient_colors, null);
            else
                drawable = getResources().getDrawable(R.drawable.dap_wallet_asset_redeem_point_action_bar_gradient_colors);

            toolbar.setBackground(drawable);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_wallet_asset_redeem_point_my_assets_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_wallet_asset_issuer_my_assets_activity_recycler_view;
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
                digitalAssets = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(digitalAssets);

                showOrHideNoAssetsView(digitalAssets.isEmpty());
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
            adapter = new MyAssetsAdapter(getActivity(), digitalAssets, moduleManager);
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
    public void onItemClickListener(DigitalAsset data, int position) {
        appSession.setData("asset_data", data);
//        changeActivity(Activities.DAP_ASSET_ISSUER_WALLET_ASSET_DETAIL, walletSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(DigitalAsset data, int position) {

    }

    @Override
    public List<DigitalAsset> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<DigitalAsset> digitalAssets = new ArrayList<>();
        //TODO load more assets
        if (moduleManager != null) {
            try {
                digitalAssets = Data.getAllDigitalAssets(moduleManager);

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry, an error happened in BrokerListActivityFragment (Module == null)",
                    Toast.LENGTH_SHORT).
                    show();
        }
        return digitalAssets;
    }

    private void showOrHideNoAssetsView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noAssetsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noAssetsView.setVisibility(View.GONE);
        }
    }
}
