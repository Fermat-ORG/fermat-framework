package org.fermat.fermat_dap_android_wallet_asset_user.v3.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_asset_user.sessions.AssetUserSession;
import org.fermat.fermat_dap_android_wallet_asset_user.sessions.SessionConstantsAssetUser;
import org.fermat.fermat_dap_android_wallet_asset_user.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_asset_user.util.Utils;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.common.data.DataManager;
import org.fermat.fermat_dap_android_wallet_asset_user.v2.models.Asset;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.common.adapters.HomeCardAdapter;
import org.fermat.fermat_dap_android_wallet_asset_user.v3.common.filters.HomeCardAdapterFilter;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import org.fermat.fermat_dap_api.layer.dap_funds_transaction.asset_buyer.exceptions.CantProcessBuyingTransactionException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static android.widget.Toast.makeText;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/17/16.
 */
public class HomeCardFragment extends FermatWalletListFragment<Asset> implements FermatListItemListeners<Asset> {
    private Activity activity;
    // Data
    private List<Asset> assets;
    private DataManager dataManager;

    //UI
    private View noAssetsView;
    private SearchView searchView;

    //FERMAT
    private AssetUserWalletSubAppModuleManager moduleManager;
    AssetUserSession assetUserSession;
    AssetUserSettings settings = null;
    private ErrorManager errorManager;
//    private SettingsManager<AssetUserSettings> settingsManager;

    private long bitcoinWalletBalanceSatoshis;

    public static HomeCardFragment newInstance() {
        return new HomeCardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {
            appSession.setData("redeem_points", null);

            assetUserSession = ((AssetUserSession) appSession);
            moduleManager = assetUserSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            dataManager = new DataManager(moduleManager);

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

        //Initialize settings
        initSettings();

        activity = new Activity();

        configureToolbar();
        noAssetsView = layout.findViewById(R.id.dap_v3_wallet_asset_user_home_no_assets);

        assets = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        showOrHideNoAssetsView(assets.isEmpty());
        try {
            bitcoinWalletBalanceSatoshis = moduleManager.getBitcoinWalletBalance(Utils.getBitcoinWalletPublicKey(moduleManager));
        } catch (Exception e) {
            // bitcoinBalanceText.setText(getResources().getString(R.string.dap_user_wallet_buy_no_available));
        }

        onRefresh();
    }

    private void showOrHideNoAssetsView(boolean empty) {
        if (empty) {
            recyclerView.setVisibility(View.GONE);
            noAssetsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noAssetsView.setVisibility(View.GONE);
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
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

    private void initSettings() {

        try {
            assetUserSession = ((AssetUserSession) appSession);
            moduleManager = assetUserSession.getModuleManager();

            try {
                settings = moduleManager.loadAndGetSettings(assetUserSession.getAppPublicKey());
            } catch (Exception e) {
                settings = null;
            }

            if (settings == null) {
                int position = 0;
                settings = new AssetUserSettings();
                settings.setIsContactsHelpEnabled(true);
                settings.setIsPresentationHelpEnabled(true);
                settings.setNotificationEnabled(true);

                settings.setBlockchainNetwork(Arrays.asList(BlockchainNetworkType.values()));
                for (BlockchainNetworkType networkType : Arrays.asList(BlockchainNetworkType.values())) {
                    if (Objects.equals(networkType.getCode(), BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode())) {
                        settings.setBlockchainNetworkPosition(position);
                        break;
                    } else {
                        position++;
                    }
                }

//            try {
                if (moduleManager != null) {
                    moduleManager.persistSettings(assetUserSession.getAppPublicKey(), settings);
                    moduleManager.setAppPublicKey(assetUserSession.getAppPublicKey());
                    moduleManager.changeNetworkType(settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition()));
                }
//            } catch (CantPersistSettingsException e) {
//                e.printStackTrace();
//            }
            } else {
                if (moduleManager != null) {
                    moduleManager.changeNetworkType(settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition()));
                }
            }

            final AssetUserSettings assetUserSettingsTemp = settings;


            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                public void run() {
                    if (assetUserSettingsTemp.isPresentationHelpEnabled()) {
                        setUpPresentation(false);
                    }
                }
            }, 500);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpPresentation(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), assetUserSession)
                    .setBannerRes(R.drawable.banner_asset_user_wallet)
                    .setIconRes(R.drawable.asset_user_wallet)
                    .setImageLeft(R.drawable.asset_user_identity)
                    .setVIewColor(R.color.card_toolbar)
                    .setTitleTextColor(R.color.card_toolbar)
                    .setTextNameLeft(R.string.dap_user_wallet_welcome_name_left)
                    .setSubTitle(R.string.dap_user_wallet_welcome_subTitle)
                    .setBody(R.string.dap_user_wallet_welcome_body)
                    .setTextFooter(R.string.dap_user_wallet_welcome_Footer)
                    .setTemplateType((moduleManager.getActiveAssetUserIdentity() == null) ? PresentationDialog.TemplateType.DAP_TYPE_PRESENTATION : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = assetUserSession.getData(SessionConstantsAssetUser.PRESENTATION_IDENTITY_CREATED);
                    if (o != null) {
                        if ((Boolean) (o)) {
                            assetUserSession.removeData(SessionConstantsAssetUser.PRESENTATION_IDENTITY_CREATED);
                        }
                    }
                    try {
                        IdentityAssetUser identityAssetUser = moduleManager.getActiveAssetUserIdentity();
                        if (identityAssetUser == null) {
                            getActivity().onBackPressed();
                        } else {
                            invalidate();
                        }
                    } catch (CantGetIdentityAssetUserException e) {
                        e.printStackTrace();
                    }
                }
            });

            presentationDialog.show();
        } catch (CantGetIdentityAssetUserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dap_wallet_asset_user_home_menu, menu);
        searchView = (SearchView) menu.findItem(R.id.action_wallet_user_search).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.dap_user_wallet_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals(searchView.getQuery().toString())) {
                    ((HomeCardAdapterFilter) ((HomeCardAdapter) getAdapter()).getFilter()).filter(s);
                }
                return false;
            }
        });
        menu.add(0, SessionConstantsAssetUser.IC_ACTION_USER_HELP_PRESENTATION, 2, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            if (id == SessionConstantsAssetUser.IC_ACTION_USER_HELP_PRESENTATION) {
                setUpPresentation(moduleManager.loadAndGetSettings(assetUserSession.getAppPublicKey()).isPresentationHelpEnabled());
                return true;
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_user_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v3_wallet_asset_user_home;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_v3_wallet_asset_user_home_recycler_view;
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
                assets = (ArrayList) result[0];
                if (adapter != null) {
                    adapter.changeDataSet(assets);
                    ((HomeCardAdapterFilter) ((HomeCardAdapter) getAdapter()).getFilter()).filter(searchView.getQuery().toString());
                }
                showOrHideNoAssetsView(assets.isEmpty());
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
            adapter = new HomeCardAdapter(this, getActivity(), assets, moduleManager, assetUserSession);
            adapter.setFermatListEventListener(this);
        } else {
            adapter.changeDataSet(assets);
        }
        return adapter;
    }

    private void doAppropriate(final org.fermat.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset asset) {
        final Activity activity = getActivity();
        final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
//                    manager.distributionAssets(
//                            asset.getAssetPublicKey(),
//                            asset.getWalletPublicKey(),
//                            asset.getActorAssetRedeemPoint()
//                    );
                //TODO: only for Appropriate test
                moduleManager.appropriateAsset(asset, null);
                return true;
            }
        };
        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_appropriation_ok), Toast.LENGTH_LONG).show();
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

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }


    private boolean isValidBuy(Asset asset) {

        if (asset.getAssetUserNegotiation().getAmount() > bitcoinWalletBalanceSatoshis) {
            makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_validate_buy_available),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doBuy(final UUID uuid) {
        try {
            moduleManager.acceptAsset(uuid);
            onRefresh();
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_sell_ok), Toast.LENGTH_LONG).show();
        } catch (CantProcessBuyingTransactionException e) {
            e.printStackTrace();
        }

        /*final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                //moduleManager.startSell(user.getActorAssetUser(), amountPerUnity, amountTotal, quantityToSell, digitalAsset.getAssetPublicKey());
                moduleManager.acceptAsset(uuid);
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
                    onRefresh();
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_sell_ok), Toast.LENGTH_LONG).show();

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
        task.execute();*/
    }

    private void doDecline(final Asset digitalAsset) {
        try {
            moduleManager.declineAsset(digitalAsset.getAssetUserNegotiation().getId());
            onRefresh();
            Toast.makeText(getActivity(), getResources().getString(R.string.dap_user_wallet_sell_cancel), Toast.LENGTH_LONG).show();
        } catch (CantProcessBuyingTransactionException e) {
            e.printStackTrace();
        }

        /*final ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setMessage(getResources().getString(R.string.dap_user_wallet_wait));
        dialog.setCancelable(false);
        dialog.show();
        FermatWorker task = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                moduleManager.declineAsset(digitalAsset.getAssetUserNegotiation().getId());
                return true;
            }
        };

        task.setContext(activity);
        task.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog.dismiss();
                if (activity != null) {
                    onRefresh();
                    Toast.makeText(activity, getResources().getString(R.string.dap_user_wallet_sell_cancel), Toast.LENGTH_LONG).show();

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
        task.execute();*/
    }

    public void doRedeem() {
        changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM, assetUserSession.getAppPublicKey());
    }

    public void doTransfer() {
        changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_TRANSFER_ACTIVITY, assetUserSession.getAppPublicKey());
    }

    public void doSell() {
        changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_SELL_ACTIVITY, assetUserSession.getAppPublicKey());
    }

    public void doTransaction() {
        changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_DETAIL, assetUserSession.getAppPublicKey());
    }

    public void doAppropiate() {
        final Asset asset = (Asset) assetUserSession.getData("asset_data");
        new ConfirmDialog.Builder(getActivity(), assetUserSession)
                .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_appropriate))
                .setColorStyle(getResources().getColor(R.color.card_toolbar))
                .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                    @Override
                    public void onClick() {
                        doAppropriate(asset.getDigitalAsset());
                    }
                }).build().show();
    }

    public void doAcceptNegotiation() {
        final Asset asset = (Asset) assetUserSession.getData("asset_data");

        if (isValidBuy(asset)) {
            new ConfirmDialog.Builder(getActivity(), assetUserSession)
                    .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                    .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_asset_buy))
                    .setColorStyle(getResources().getColor(R.color.card_toolbar))
                    .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                        @Override
                        public void onClick() {
                            doBuy(asset.getAssetUserNegotiation().getId());
                            asset.setAssetUserNegotiation(null);
                        }
                    }).build().show();
        }

    }

    public void doRejectNegotiation() {
        final Asset asset = (Asset) assetUserSession.getData("asset_data");
        doDecline(asset);
        asset.setAssetUserNegotiation(null);
    }


    @Override
    public List<Asset> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<Asset> assets = new ArrayList<>();
        if (moduleManager != null) {
            try {
                assets = DataManager.getAssets();
                assets.addAll(DataManager.getAllPendingNegotiations());

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
                    R.string.dap_user_wallet_system_error,
                    Toast.LENGTH_SHORT).
                    show();
        }
        return assets;
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case DAPConstants.DAP_UPDATE_VIEW_ANDROID:
                onRefresh();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }


    public void onItemClickListener(Asset data, int position) {
        //appSession.setData("asset_data", data);
    }

    @Override
    public void onLongItemClickListener(Asset data, int position) {

    }
}

