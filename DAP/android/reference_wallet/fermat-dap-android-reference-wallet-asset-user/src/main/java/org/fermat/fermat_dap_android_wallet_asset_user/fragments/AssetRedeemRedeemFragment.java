package org.fermat.fermat_dap_android_wallet_asset_user.fragments;


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

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.adapters.AssetRedeemPointAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_asset_user.models.RedeemPoint;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.data.DataManager;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import org.fermat.fermat_dap_api.layer.dap_wallet.common.WalletUtilities;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Penelope Quintero on 18/02/2016.
 */
public class AssetRedeemRedeemFragment extends FermatWalletListFragment<RedeemPoint, ReferenceAppFermatSession<AssetUserWalletSubAppModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<RedeemPoint> {

    // Constants
    private static final String TAG = "AssetTransferSelectUsersFragment";

    // Fermat Managers
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    // Data
    private List<RedeemPoint> redeemPoints;
    private RedeemPoint redeemPointSelect;

    private Asset assetToRedeem;
    private DigitalAsset digitalAsset;
    String digitalAssetPublicKey;

    //UI
    private View noUsersView;
    private Toolbar toolbar;
    private Activity activity;
    private FermatTextView titleRedeem;

    public static AssetRedeemRedeemFragment newInstance() {
        return new AssetRedeemRedeemFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            try {

                assetToRedeem = (Asset) appSession.getData("asset_data");
                digitalAssetPublicKey = assetToRedeem.getDigitalAsset().getPublicKey();
//                digitalAsset = Data.getDigitalAsset(moduleManager, digitalAssetPublicKey);
//                digitalAsset = DataManager.getAssets();
            } catch (Exception e) {
                e.printStackTrace();
            }

            redeemPoints = getMoreDataAsync(FermatRefreshTypes.NEW, 0);

            activity = getActivity();

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.DAP_ASSET_USER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        noUsersView = layout.findViewById(R.id.dap_v3_wallet_asset_user_redeem_no_redeempoints);
        titleRedeem = (FermatTextView) layout.findViewById(R.id.select_redeem_point);


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
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
//                case 1://IC_ACTION_USER_HELP_REDEEM
//                    setUpHelpAssetRedeem(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                    break;
                case 2://IC_ACTION_USER_ASSET_REDEEM
                    if (redeemPointSelect != null) {

                        new ConfirmDialog.Builder(getActivity(), appSession)
                                .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                                .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_redeem))
                                .setColorStyle(getResources().getColor(R.color.card_toolbar))
                                .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                    @Override
                                    public void onClick() {
                                        doRedeem(assetToRedeem.getDigitalAsset(), redeemPoints, 1);
                                    }
                                }).build().show();

                    } else {
                        Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_rp), Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

//            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_REDEEM_SELECT) {
//                setUpHelpAssetRedeem(appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            } else if (id == SessionConstantsAssetUser.IC_ACTION_USER_ASSET_REDEEM) {
//                if (redeemPointSelect != null) {
//
//                    new ConfirmDialog.Builder(getActivity(), appSession)
//                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
//                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_redeem))
//                            .setColorStyle(getResources().getColor(R.color.card_toolbar))
//                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
//                                @Override
//                                public void onClick() {
//                                    doRedeem(assetToRedeem.getDigitalAsset(), redeemPoints, 1);
//                                }
//                            }).build().show();
//
//                } else {
//                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_validate_no_rp), Toast.LENGTH_SHORT).show();
//                }
//            }
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
            toolbar.setBackgroundColor(getResources().getColor(R.color.card_toolbar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.card_toolbar));
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
        return R.id.dap_v3_wallet_asset_user_asset_redeem_select_redeempoints_activity_recycler_view;
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

        for (int i = 0; i < redeemPoints.size(); i++) {
            if (i != position) {
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
        } else {
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
//                redeemPoints = Data.getConnectedRedeemPoints(moduleManager,redeemPoints, digitalAsset);
                redeemPoints = DataManager.getConnectedRedeemPoints(digitalAssetPublicKey);

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
            titleRedeem.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noUsersView.setVisibility(View.GONE);
            titleRedeem.setVisibility(View.VISIBLE);
        }
    }

    private void doRedeem(final org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset asset, final List<RedeemPoint> redeemPoints, final int assetAmount) {
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.redeemAssetToRedeemPoint(asset, WalletUtilities.WALLET_PUBLIC_KEY, DataManager.getRedeemPoints(redeemPoints), assetAmount);
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
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_V3_HOME, appSession.getAppPublicKey());
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
