package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.fragments;

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
import android.widget.Button;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.Views.ConfirmDialog;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.SessionConstantsAssetUser;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.util.CommonLogger;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.common.data.DataManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v2.models.Asset;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.adapters.HomeCardAdapter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.filters.HomeCardAdapterFilter;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.v3.common.holders.HomeCardViewHolder;
import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 3/17/16.
 */
public class HomeCardFragment extends FermatWalletListFragment<Asset> {

    // Data
    private List<Asset> assets;
    private DataManager dataManager;

    //UI
    private View noAssetsView;
    private SearchView searchView;

    //FERMAT
    private AssetUserWalletSubAppModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<AssetUserSettings> settingsManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        try {

            moduleManager = ((AssetUserSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
            settingsManager = appSession.getModuleManager().getSettingsManager();
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

        configureToolbar();
        noAssetsView = layout.findViewById(R.id.dap_v3_wallet_asset_user_home_no_assets);

        assets = (List) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        showOrHideNoAssetsView(assets.isEmpty());

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
        settingsManager = appSession.getModuleManager().getSettingsManager();
        AssetUserSettings settings = null;
        try {
            settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            settings = null;
        }

        if (settings == null) {
            settings = new AssetUserSettings();
            settings.setIsContactsHelpEnabled(true);
            settings.setIsPresentationHelpEnabled(true);

            try {
                settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
                moduleManager.setAppPublicKey(appSession.getAppPublicKey());

                moduleManager.changeNetworkType(settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition()));
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } else {
            moduleManager.changeNetworkType(settings.getBlockchainNetwork().get(settings.getBlockchainNetworkPosition()));
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
    }

    private void setUpPresentation(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
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
                    Object o = appSession.getData(SessionConstantsAssetUser.PRESENTATION_IDENTITY_CREATED);
                    if (o != null) {
                        if ((Boolean) (o)) {
                            //invalidate();
                            appSession.removeData(SessionConstantsAssetUser.PRESENTATION_IDENTITY_CREATED);
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
                setUpPresentation(settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
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
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v3_wallet_asset_user_home;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.dap_v3_wallet_asset_user_home_swipe_refresh;
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
            View.OnClickListener onClickListenerRedeem = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM, appSession.getAppPublicKey());
                }
            };
            View.OnClickListener onClickListenerTransfer = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_TRANSFER_ACTIVITY, appSession.getAppPublicKey());
                }
            };
            View.OnClickListener onClickListenerAppropriate = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Asset asset = (Asset) appSession.getData("asset");
                    new ConfirmDialog.Builder(getActivity(), appSession)
                            .setTitle(getResources().getString(R.string.dap_user_wallet_confirm_title))
                            .setMessage(getResources().getString(R.string.dap_user_wallet_confirm_sure))
                            .setColorStyle(getResources().getColor(R.color.dap_user_wallet_principal))
                            .setYesBtnListener(new ConfirmDialog.OnClickAcceptListener() {
                                @Override
                                public void onClick() {
                                    doAppropriate(asset.getDigitalAsset().getPublicKey());
                                }
                            }).build().show();
                }
            };
            View.OnClickListener onClickListenerSell = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_SELL_ACTIVITY, appSession.getAppPublicKey());
                }
            };
            View.OnClickListener onClickListenerTransactions = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
//                    changeActivity(Activities.DAP_WALLET_ASSET_USER_ASSET_REDEEM_SELECT_REDEEMPOINTS, appSession.getAppPublicKey());
                }
            };
            adapter = new HomeCardAdapter(getActivity(), assets, moduleManager, appSession, onClickListenerRedeem,
                    onClickListenerTransfer, onClickListenerAppropriate, onClickListenerSell, onClickListenerTransactions);
        } else {
            adapter.changeDataSet(assets);
        }
        return adapter;
    }

    private void doAppropriate(final String assetPublicKey) {
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
                moduleManager.appropriateAsset(assetPublicKey, null);
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

    @Override
    public List<Asset> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<Asset> assets = new ArrayList<>();
        if (moduleManager != null) {
            try {
                assets = dataManager.getAssets();
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
}
