package com.bitdubai.sub_app.wallet_store.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings.basic_classes.BasicWalletSettings;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.sub_app.wallet_store.common.adapters.WalletStoreCatalogueAdapter;
import com.bitdubai.sub_app.wallet_store.common.interfaces.WalletStoreItemPopupMenuListener;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.common.workers.DetailedCatalogItemWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorker;
import com.bitdubai.sub_app.wallet_store.common.workers.InstallWalletWorkerCallback;
import com.bitdubai.sub_app.wallet_store.fragmentFactory.WalletStoreFragmentsEnumType;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.bitdubai.sub_app.wallet_store.util.CommonLogger;
import com.bitdubai.sub_app.wallet_store.util.UtilsFuncs;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.BASIC_DATA;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.DEVELOPER_NAME;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.LANGUAGE_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.PREVIEW_IMGS;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.SKIN_ID;
import static com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession.WALLET_VERSION;


/**
 * Fragment que luce como un Activity donde se muestra la lista de Wallets disponibles en el catalogo de la Wallet Store
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MainActivityFragment extends FermatListFragment<WalletStoreListItem>
        implements FermatListItemListeners<WalletStoreListItem>, OnMenuItemClickListener {

    /**
     * MANAGERS
     */
    private WalletStoreModuleManager moduleManager;
    private ErrorManager errorManager;

    /**
     * DATA
     */
    private ArrayList<WalletStoreListItem> catalogueItemList;
    private WalletStoreListItem selectedItem;

    /**
     * Executor Service
     */
    private ExecutorService executor;

    /**
     * UI
     */
    private ProgressDialog dialog;
    private PresentationDialog presentationDialog;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up  module
            moduleManager = ((WalletStoreSubAppSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
            catalogueItemList = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.app_store_main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.app_store_menu_action_help) {
            presentationDialog.show();
            return true;
        }

        return false;
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();
        BasicWalletSettings preferenceSettings = getPreferenceSettings();

        presentationDialog = new PresentationDialog.Builder(getActivity(), appSession).
                setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES).
                setBannerRes(R.drawable.banner_app_store).
                setSubTitle(R.string.presentation_dialog_subtitle_app_store_list).
                setBody(R.string.presentation_dialog_body_app_store_list).
                build();

        if (preferenceSettings != null && preferenceSettings.isHomeTutorialDialogEnabled()) {
            presentationDialog.show();
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.wallet_store_fragment_main_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.catalog_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter != null)
            return adapter;

        WalletStoreCatalogueAdapter catalogueAdapter = new WalletStoreCatalogueAdapter(getActivity(), catalogueItemList);
        // TODO - mantener esto comentado por ahora: catalogueAdapter.setMenuClickListener(getWalletStoreItemPopupMenuListener());
        // TODO - mantener esto comentado por ahora: catalogueAdapter.setFermatListEventListener(this);

        return catalogueAdapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public ArrayList<WalletStoreListItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<WalletStoreListItem> data;

        Object walletList = appSession.getData(WalletStoreSubAppSession.WALLET_LIST);
        if (walletList != null) {
            data = (ArrayList<WalletStoreListItem>) walletList;
        } else {
            data = WalletStoreListItem.getTestData(getResources());
            appSession.setData(WalletStoreSubAppSession.WALLET_LIST, data);
        }

//        try {
//            WalletStoreCatalogue catalogue = moduleManager.getCatalogue();
//            List<WalletStoreCatalogueItem> catalogueItems = catalogue.getWalletCatalogue(0, 0);
//
//            data = new ArrayList<>();
//            for (WalletStoreCatalogueItem catalogItem : catalogueItems) {
//                WalletStoreListItem item = new WalletStoreListItem(catalogItem, getResources());
//                data.add(item);
//            }
//
//        } catch (Exception e) {
//            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
//                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//
//            data = WalletStoreListItem.getTestData(getResources());
//        }

        return data;
    }

    @Override
    public void onItemClickListener(WalletStoreListItem item, int position) {
        selectedItem = item;
        showDetailsActivityFragment(selectedItem);
    }

    @Override
    public void onLongItemClickListener(WalletStoreListItem data, int position) {
        // do nothing
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                if (!WalletStoreListItem.started) {
                    catalogueItemList = (ArrayList) result[0];
                    if (adapter != null)
                        adapter.changeDataSet(catalogueItemList);
                }
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
    public boolean onMenuItemClick(MenuItem menuItem) {
        int menuItemItemId = menuItem.getItemId();
        if (menuItemItemId == R.id.menu_action_install_wallet) {
            if (selectedItem != null) {
                installSelectedWallet(selectedItem);
            }
            return true;
        }
        if (menuItemItemId == R.id.menu_action_wallet_details) {
            if (selectedItem != null) {
                showDetailsActivityFragment(selectedItem);
            }
            return true;
        }
        if (menuItemItemId == R.id.menu_action_open_wallet) {
            Toast.makeText(getActivity(), "Opening Wallet...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WalletStoreListItem.started = false;
    }

    @NonNull
    private WalletStoreItemPopupMenuListener getWalletStoreItemPopupMenuListener() {
        return new WalletStoreItemPopupMenuListener() {
            @Override
            public void onMenuItemClickListener(View menuView, WalletStoreListItem item, int position) {
                selectedItem = item;

                PopupMenu popupMenu = new PopupMenu(getActivity(), menuView);
                MenuInflater menuInflater = popupMenu.getMenuInflater();

                InstallationStatus installationStatus = item.getInstallationStatus();
                int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installationStatus);

                if (resId == R.string.wallet_status_install) {
                    menuInflater.inflate(R.menu.menu_wallet_store, popupMenu.getMenu());
                } else {
                    menuInflater.inflate(R.menu.menu_wallet_store_installed, popupMenu.getMenu());
                }

                popupMenu.setOnMenuItemClickListener(MainActivityFragment.this);
                popupMenu.show();
            }
        };
    }

    private void installSelectedWallet(WalletStoreListItem item) {

        final Activity activity = getActivity();
        FermatWorkerCallBack callBack = new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                dialog = UtilsFuncs.INSTANCE.showProgressDialog(dialog, getActivity(), R.string.installing_message, R.string.wait_please_message);

                InstallWalletWorkerCallback callback = new InstallWalletWorkerCallback(getActivity(), errorManager, dialog);
                InstallWalletWorker installWalletWorker = new InstallWalletWorker(getActivity(), callback, moduleManager, (SubAppsSession) appSession);
                if (executor != null)
                    executor.shutdownNow();
                executor = installWalletWorker.execute();
            }

            @Override
            public void onErrorOccurred(Exception ex) {
                final ErrorManager errorManager = appSession.getErrorManager();
                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

                Toast.makeText(activity, R.string.cannot_collect_wallet_details_message, Toast.LENGTH_SHORT).show();
            }
        };

        final DetailedCatalogItemWorker worker = new DetailedCatalogItemWorker(moduleManager, (SubAppsSession) appSession, item, activity, callBack);
        worker.execute();
    }

    private void showDetailsActivityFragment(WalletStoreListItem item) {

        if (item.isTestData()) {
            appSession.setData(DEVELOPER_NAME, "developerAlias");
            appSession.setData(LANGUAGE_ID, new UUID(0, 0));
            appSession.setData(WALLET_VERSION, new Version(1, 0, 0));
            appSession.setData(SKIN_ID, new UUID(0, 0));
            appSession.setData(PREVIEW_IMGS, item.getScreenshots());
            appSession.setData(BASIC_DATA, item);

            changeActivity(Activities.CWP_WALLET_STORE_DETAIL_ACTIVITY.getCode(), appSession.getAppPublicKey());

        } else {
            final Activity activity = getActivity();
            FermatWorkerCallBack callBack = new FermatWorkerCallBack() {
                @Override
                public void onPostExecute(Object... result) {

                    final DetailsActivityFragment fragment = DetailsActivityFragment.newInstance();
                    fragment.setAppSession(appSession);
                    fragment.setAppResourcesProviderManager(appResourcesProviderManager);

                    changeActivity(WalletStoreFragmentsEnumType.CWP_WALLET_STORE_DETAIL_ACTIVITY.getKey());
                }

                @Override
                public void onErrorOccurred(Exception ex) {

                    final ErrorManager errorManager = appSession.getErrorManager();
                    errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                            UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);

                    Toast.makeText(activity, R.string.cannot_collect_wallet_details_message, Toast.LENGTH_SHORT).show();

                }
            };

            final DetailedCatalogItemWorker worker = new DetailedCatalogItemWorker(moduleManager, (SubAppsSession) appSession, item, activity, callBack);
            worker.execute();
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.app_store_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.app_store_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private BasicWalletSettings getPreferenceSettings() {
        final SettingsManager<BasicWalletSettings> settingsManager = moduleManager.getSettingsManager();

        BasicWalletSettings preferenceSettings;
        try {
            preferenceSettings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            preferenceSettings = null;
        }

        if (preferenceSettings == null) {
            preferenceSettings = new BasicWalletSettings();
            preferenceSettings.setIsPresentationHelpEnabled(true);

            try {
                settingsManager.persistSettings(appSession.getAppPublicKey(), preferenceSettings);
            } catch (Exception e) {
                preferenceSettings = null;
            }
        }

        return preferenceSettings;
    }
}
