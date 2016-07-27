package org.fermat.fermat_dap_android_wallet_redeem_point.v3.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantListLossProtectedTransactionsException;
import com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.R;

import org.fermat.fermat_dap_android_wallet_redeem_point.models.Data;
import org.fermat.fermat_dap_android_wallet_redeem_point.models.DigitalAsset;
import org.fermat.fermat_dap_android_wallet_redeem_point.sessions.SessionConstantsRedeemPoint;
import org.fermat.fermat_dap_android_wallet_redeem_point.util.CommonLogger;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.adapters.RedeemCardAdapter;
import org.fermat.fermat_dap_android_wallet_redeem_point.v3.filters.RedeemHomeCardAdapterFilter;
import org.fermat.fermat_dap_api.layer.all_definition.DAPConstants;
import org.fermat.fermat_dap_api.layer.all_definition.exceptions.CantGetIdentityRedeemPointException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.RedeemPointSettings;
import org.fermat.fermat_dap_api.layer.dap_module.wallet_asset_redeem_point.interfaces.AssetRedeemPointWalletSubAppModule;
import org.fermat.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.Toast.makeText;

/**
 * Created by Jinmy Bohorquez on 15/04/16.
 */
public class RedeemHomeCardFragment extends FermatWalletListFragment<DigitalAsset, ReferenceAppFermatSession<AssetRedeemPointWalletSubAppModule>, ResourceProviderManager>
        implements FermatListItemListeners<DigitalAsset> {

    private Activity activity;
    private List<DigitalAsset> assets;
    private List<AssetRedeemPointWalletList> assetRedeemPointWalletList;
    RedeemPointSettings settings = null;

    private View noAssetsView;
    private SearchView searchView;

    private AssetRedeemPointWalletSubAppModule moduleManager;
    private ErrorManager errorManager;

    private int menuItemSize;

    public static RedeemHomeCardFragment newInstance() {
        return new RedeemHomeCardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            assets = getMoreDataAsync(FermatRefreshTypes.NEW, 0);


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

        noAssetsView = layout.findViewById(R.id.dap_v3_wallet_asset_redeem_home_no_assets);
        initSettings();
        activity = new Activity();
        configureToolbar();

        if (assets != null)
            showOrHideNoAssetsView(assets.isEmpty());

    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.dap_v3_wallet_asset_redeem_point_home_card;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.redeem_swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.dap_v3_wallet_asset_redeem_home_recycler_view;
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
                    if (!searchView.getQuery().toString().isEmpty())
                        ((RedeemHomeCardAdapterFilter) ((RedeemCardAdapter) getAdapter()).getFilter()).filter(searchView.getQuery().toString());
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
            adapter = new RedeemCardAdapter(this, getActivity(), assets, appSession);
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

    }

    @Override
    public void onLongItemClickListener(DigitalAsset data, int position) {

    }

    @Override
    public void onOptionMenuPrepared(Menu menu){
        super.onOptionMenuPrepared(menu);

        if (menuItemSize == 0 || menuItemSize == menu.size()) {
            menuItemSize = menu.size();
//            searchView = (SearchView) menu.findItem(R.id.action_wallet_redeem_point_search).getActionView();
            searchView = (SearchView) menu.findItem(2).getActionView();
            searchView.setQueryHint(getResources().getString(R.string.dap_redeem_point_wallet_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals(searchView.getQuery().toString())) {
                        ((RedeemHomeCardAdapterFilter) ((RedeemCardAdapter) getAdapter()).getFilter()).filter(s);
                    }
                    return false;
                }
            });
        }
//        menu.add(0, SessionConstantsRedeemPoint.IC_ACTION_REDEEM_HELP_PRESENTATION, 0, "Help")
//                .setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();

            switch (id) {
                case 1://IC_ACTION_REDEEM_HELP_PRESENTATION
                    setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
                    break;
            }
//            if (id == SessionConstantsRedeemPoint.IC_ACTION_REDEEM_HELP_PRESENTATION) {
//                setUpPresentation(moduleManager.loadAndGetSettings(appSession.getAppPublicKey()).isPresentationHelpEnabled());
//                return true;
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), R.string.dap_redeem_point_wallet_system_error,
                    Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);

    }

    private void initSettings() {
        try {

            try {
                settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                settings = null;
            }

            if (settings == null) {
                int position = 0;
                settings = new RedeemPointSettings();
                settings.setIsContactsHelpEnabled(true);
                settings.setNotificationEnabled(true);
                settings.setIsPresentationHelpEnabled(true);
                settings.setNotificationEnabled(true);
                settings.setAssetNotificationEnabled(true);

                settings.setBlockchainNetwork(Arrays.asList(BlockchainNetworkType.values()));
                for (BlockchainNetworkType networkType : Arrays.asList(BlockchainNetworkType.values())) {
                    if (networkType.getCode().equals(BlockchainNetworkType.getDefaultBlockchainNetworkType().getCode())) {
                        settings.setBlockchainNetworkPosition(position);
                        break;
                    } else {
                        position++;
                    }
                }

//            try {
                if (moduleManager != null) {
                    moduleManager.persistSettings(appSession.getAppPublicKey(), settings);
                    moduleManager.setAppPublicKey(appSession.getAppPublicKey());
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

            final RedeemPointSettings assetRedeemPointSettingsTemp = settings;


            Handler handlerTimer = new Handler();
            handlerTimer.postDelayed(new Runnable() {
                public void run() {
                    if (assetRedeemPointSettingsTemp.isPresentationHelpEnabled()) {
                        setUpPresentation(false);
                    }
                }
            }, 500);

        } catch (Exception e) {
            e.printStackTrace();
        }
        onRefresh();
    }

    private void setUpPresentation(boolean checkButton) {
        try {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)

                    .setBannerRes(R.drawable.banner_redeem_point_wallet)
                    .setIconRes(R.drawable.redeem_point)
                    .setImageLeft(R.drawable.profile_actor)
                    .setVIewColor(R.color.dap_redeem_point_view_color)
                    .setTitleTextColor(R.color.dap_redeem_point_view_color)
                    .setTextNameLeft(R.string.dap_redeem_wallet_welcome_name_left)
                    .setSubTitle(R.string.dap_redeem_wallet_welcome_subTitle)
                    .setBody(R.string.dap_redeem_wallet_welcome_body)
                    .setTextFooter(R.string.dap_redeem_wallet_welcome_Footer)
                    .setTemplateType((moduleManager.getActiveAssetRedeemPointIdentity() == null) ? PresentationDialog.TemplateType.TYPE_PRESENTATION_WITH_ONE_IDENTITY : PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Object o = appSession.getData(SessionConstantsRedeemPoint.PRESENTATION_IDENTITY_CREATED);
                    if (o != null) {
                        if ((Boolean) (o)) {
                            //invalidate();
                            appSession.removeData(SessionConstantsRedeemPoint.PRESENTATION_IDENTITY_CREATED);
                        }
                    }
                    try {
                        RedeemPointIdentity identityAssetRedeemPoint = moduleManager.getActiveAssetRedeemPointIdentity();
                        if (identityAssetRedeemPoint == null) {
                            getActivity().onBackPressed();
                        } else {
                            invalidate();
                        }

                    } catch (CantGetIdentityRedeemPointException e) {
                        e.printStackTrace();
                    }
                }
            });

            presentationDialog.show();

        } catch (CantGetIdentityRedeemPointException e) {
            e.printStackTrace();
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.redeem_card_titlebar));
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setBottom(Color.WHITE);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                Window window = getActivity().getWindow();
                window.setStatusBarColor(getResources().getColor(R.color.redeem_card_titlebar));
            }
        }
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

    @Override
    public List<DigitalAsset> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) throws CantListCryptoWalletIntraUserIdentityException, CantGetCryptoLossProtectedWalletException, CantListLossProtectedTransactionsException {

        List<DigitalAsset> assets = new ArrayList<>();
        if (moduleManager != null) {
            try {
//                assets = Data.getAllDigitalAssets(moduleManager);
                assets = Data.getAllRedeemPointAssetsNew(moduleManager);
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.DAP_ASSET_USER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            makeText(getActivity(),
                    R.string.dap_redeem_point_wallet_system_error,
                    Toast.LENGTH_SHORT).
                    show();
        }
        return assets;
    }

    public void activityChange() {
        changeActivity(Activities.DAP_WALLET_REDEEM_POINT_DETAILS_ACTIVITY, appSession.getAppPublicKey());
    }

    public void doAcceptAsset() {

    }

    public void doRejectAsset() {
    }

    public void doDeliver() {
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
}
