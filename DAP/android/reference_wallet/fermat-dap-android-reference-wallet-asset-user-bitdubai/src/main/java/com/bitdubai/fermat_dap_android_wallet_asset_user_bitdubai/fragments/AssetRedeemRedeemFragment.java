package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.AssetRedeemPointAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.adapters.AssetTransferUserAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.Data;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.DigitalAsset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models.RedeemPoint;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.SessionConstantsAssetUser;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Jinmy Bohorquez on 18/02/2016.
 */
public class AssetRedeemRedeemFragment extends FermatWalletListFragment<RedeemPoint>
        implements FermatListItemListeners<RedeemPoint> {

    // Constants
    private static final String TAG = "AssetTransferSelectUsersFragment";

    // Fermat Managers
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<RedeemPoint> redeemPoints;
    private RedeemPoint redeemPointSelect;
    private DigitalAsset assetToRedeem;

    SettingsManager<AssetUserSettings> settingsManager;

    //UI
    private View noUsersView;
    private Toolbar toolbar;
    private Activity activity;
    
    public static AssetRedeemRedeemFragment newInstance() {
        return new AssetRedeemRedeemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((AssetUserSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            settingsManager = appSession.getModuleManager().getSettingsManager();

            redeemPoints = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
            assetToRedeem = (DigitalAsset) appSession.getData("asset_data");
            activity = getActivity();

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

        noUsersView = layout.findViewById(R.id.dap_wallet_asset_user_redeem_no_redeempoints);

        showOrHideNoUsersView(redeemPoints.isEmpty());
    }

    private void setUpHelpAssetRedeem(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setVIewColor(R.color.dap_user_view_color)
                    .setTitleTextColor(R.color.dap_user_view_color)
                    .setSubTitle(R.string.dap_user_wallet_redeem_subTitle)
                    .setBody(R.string.dap_user_wallet_redeem_body)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_ASSET_REDEEM, 0, "Redeem")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        //menu.add(1, SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM_SELECT, 0, "Help")
                //.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM_SELECT) {
                setUpHelpAssetRedeem(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ASSET_REDEEM){
                if (redeemPointSelect != null) {

                new ConfirmDialog.Builder(getActivity(), appSession)
                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_entered_info))
                            .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                @Override
                                public void onClick() {
                                    int assetsAmount = Integer.parseInt("1");
                                    doRedeem(assetToRedeem.getAssetPublicKey(), redeemPoints, assetsAmount);
                                }
                                }).build().show();

                } else {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_user), Toast.LENGTH_SHORT).show();
                }

            }


        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
        toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.dap_user_wallet_new));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.dap_user_wallet_principal));
            }
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v3_wallet_asset_user_asset_redeem_select_redeempoint;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh_redeempoint_v3;
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
            adapter = new AssetRedeemPointAdapter(getActivity(), redeemPoints, moduleManager);
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
        //TODO select redeemPoint
       //appSession.setData("redeemPoint_selected", data);

        for (int i=0; i < redeemPoints.size(); i++)
        {
            if (i != position)
            {
                redeemPoints.get(i).setSelected(false);
            }
        }

//        for (RedeemPoint redeemPoint : redeemPoints)
//        {
//            if(!redeemPoint.equals(data))
//            {
//                redeemPoint.setSelected(false);
//            }
//            else {
//                data.setSelected(!data.isSelected());
//                if(data.isSelected())
//                {redeemPointSelect = data;}
//                else
//                {redeemPointSelect = null;}
//            }
//        }
//
        //data.setSelected(!data.isSelected());
        redeemPoints.get(position).setSelected(!redeemPoints.get(position).isSelected());

        if (redeemPoints.get(position).isSelected()) {
            redeemPointSelect = data;
        }
        else {
            redeemPointSelect = null;
        }

        //
//        RedeemPoint rp = (RedeemPoint) adapter.getItem(position);
//        rp.setSelected(false);
//        adapter.notifyItemChanged(position);

        getAdapter().changeDataSet(redeemPoints);
    }

    @Override
    public void onLongItemClickListener(RedeemPoint data, int position) {
    }

    @Override
    public List<RedeemPoint> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<RedeemPoint> redeemPoints = new ArrayList<>();
        if (moduleManager != null) {
            try {
                DigitalAsset assetToRedeem = (DigitalAsset) appSession.getData("asset_data");
                redeemPoints = Data.getConnectedRedeemPoints(moduleManager,redeemPoints, assetToRedeem);
                appSession.setData("redeemPoints_to_redeem", redeemPoints);
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
                    getResources().getString(R.string.dap_user_wallet_system_error),
                    Toast.LENGTH_SHORT).
                    show();
        }
        return redeemPoints;
    }

    private void showOrHideNoUsersView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noUsersView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noUsersView.setVisibility(View.GONE);
        }
    }

    private void doRedeem(final String assetPublicKey, final List<RedeemPoint> redeemPoints, final int assetAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.redeemAssetToRedeemPoint(assetPublicKey, WalletUtilities.WALLET_PUBLIC_KEY, Data.getRedeemPoints(redeemPoints), assetAmount);
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
//                    refreshUIData();
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_redeem_ok), Toast.LENGTH_LONG).show();
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_DETAIL, appSession.getAppPublicKey());
                }
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                dialog.dismiss();
                if (activity != null)
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_exception_retry),
                            Toast.LENGTH_SHORT).show();
            }
        });
        task.execute();
    }
}

