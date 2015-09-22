package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragmentNew;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestPendingAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Fragment que luce como un Activity donde se muestra la lista de Wallets disponibles en el catalogo de la Wallet Store
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class RequestPaymentFragment extends FermatListFragmentNew<RequestPaymentListItem>
        implements FermatListItemListeners<RequestPaymentListItem>, OnMenuItemClickListener {

    /**
     * MANAGERS
     */
    private WalletStoreModuleManager moduleManager;
    private ErrorManager errorManager;

    /**
     * DATA
     */
    private ArrayList<RequestPaymentListItem> lstPaymentRequest;
    private RequestPaymentListItem selectedItem;

    /**
     * Executor Service
     */
    private ExecutorService executor;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestPaymentFragment newInstance() {
        return new RequestPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // setting up  module
            //moduleManager = ((WalletStoreSubAppSession) subAppsSession).getWalletStoreModuleManager();
            //errorManager = subAppsSession.getErrorManager();
            lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected String getLayoutResourceName() {
        return "layout";//R.layout.wallet_store_fragment_main_activity;
    }

    @Override
    protected String getSwipeRefreshLayoutName() {
        return "swipe";
    }

    @Override
    protected String getRecyclerLayoutName() {
        return "recicler";
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapterNew getAdapter() {
        if (adapter == null) {
            //WalletStoreItemPopupMenuListener listener = getWalletStoreItemPopupMenuListener();
            adapter = new PaymentRequestPendingAdapter(getActivity(), lstPaymentRequest, viewInflater,walletSession.getWalletResourcesProviderManager());
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
    public ArrayList<RequestPaymentListItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<RequestPaymentListItem> data = null;

        try {
//            WalletStoreCatalogue catalogue = moduleManager.getCatalogue();
//            List<WalletStoreCatalogueItem> catalogueItems = catalogue.getWalletCatalogue(0, 0);

            data = new ArrayList<>();
//            for (WalletStoreCatalogueItem catalogItem : catalogueItems) {
//                RequestPaymentListItem item = new RequestPaymentListItem(catalogItem, getResources());
//                data.add(item);
//            }

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            //data = RequestPaymentListItem.getTestData(getResources());
        }

        return data;
    }

    @Override
    public void onItemClickListener(RequestPaymentListItem item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
    }

    @Override
    public void onLongItemClickListener(RequestPaymentListItem data, int position) {
        // do nothing
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstPaymentRequest = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstPaymentRequest);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
//        int menuItemItemId = menuItem.getItemId();
//        if (menuItemItemId == R.id.menu_action_install_wallet) {
//            if (selectedItem != null) {
//                installSelectedWallet(selectedItem);
//            }
//            return true;
//        }
//        if (menuItemItemId == R.id.menu_action_wallet_details) {
//            if (selectedItem != null) {
//                showDetailsActivityFragment(selectedItem);
//            }
//            return true;
//        }
//        if (menuItemItemId == R.id.menu_action_open_wallet) {
//            Toast.makeText(getActivity(), "Opening Wallet...", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
        return false;
    }

//    private void installSelectedWallet(WalletStoreListItem item) {
//
//        final Activity activity = getActivity();
//        FermatWorkerCallBack callBack = new FermatWorkerCallBack() {
//            @Override
//            public void onPostExecute(Object... result) {
//                Toast.makeText(getActivity(), "Installing...", Toast.LENGTH_SHORT).show();
//
//                InstallWalletWorkerCallback callback = new InstallWalletWorkerCallback(getActivity(), errorManager);
//                InstallWalletWorker installWalletWorker = new InstallWalletWorker(getActivity(), callback, moduleManager, subAppsSession);
//                if (executor != null)
//                    executor.shutdownNow();
//                executor = installWalletWorker.execute();
//            }
//
//            @Override
//            public void onErrorOccurred(Exception ex) {
//                final ErrorManager errorManager = subAppsSession.getErrorManager();
//                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
//                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
//
//                Toast.makeText(activity, R.string.cannot_collect_wallet_details, Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        final DetailedCatalogItemWorker worker = new DetailedCatalogItemWorker(moduleManager, subAppsSession, item, activity, callBack);
//        worker.execute();
//    }

//    private void showDetailsActivityFragment(WalletStoreListItem item) {
//
//        final Activity activity = getActivity();
//        FermatWorkerCallBack callBack = new FermatWorkerCallBack() {
//            @Override
//            public void onPostExecute(Object... result) {
//
//                final DetailsActivityFragment fragment = DetailsActivityFragment.newInstance();
//                fragment.setSubAppsSession(subAppsSession);
//                fragment.setSubAppSettings(subAppSettings);
//                fragment.setSubAppResourcesProviderManager(subAppResourcesProviderManager);
//
//                final FragmentTransaction FT = activity.getFragmentManager().beginTransaction();
//                FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                FT.replace(R.id.activity_container, fragment);
//                FT.addToBackStack(null);
//                FT.commit();
//            }
//
//            @Override
//            public void onErrorOccurred(Exception ex) {
//                final ErrorManager errorManager = subAppsSession.getErrorManager();
//                errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
//                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
//
//                Toast.makeText(activity, R.string.cannot_collect_wallet_details, Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        final DetailedCatalogItemWorker worker = new DetailedCatalogItemWorker(moduleManager, subAppsSession, item, activity, callBack);
//        worker.execute();
//    }

//    @NonNull
//    private WalletStoreItemPopupMenuListener getWalletStoreItemPopupMenuListener() {
//        return new WalletStoreItemPopupMenuListener() {
//            @Override
//            public void onMenuItemClickListener(View menuView, WalletStoreListItem item, int position) {
//                selectedItem = item;
//
//                PopupMenu popupMenu = new PopupMenu(getActivity(), menuView);
//                MenuInflater menuInflater = popupMenu.getMenuInflater();
//
//                InstallationStatus installationStatus = item.getInstallationStatus();
//                int resId = UtilsFuncs.INSTANCE.getInstallationStatusStringResource(installationStatus);
//
//                if (resId == R.string.wallet_status_install) {
//                    menuInflater.inflate(R.menu.menu_wallet_store, popupMenu.getMenu());
//                } else {
//                    menuInflater.inflate(R.menu.menu_wallet_store_installed, popupMenu.getMenu());
//                }
//
//                popupMenu.setOnMenuItemClickListener(RequestPaymentFragment.this);
//                popupMenu.show();
//            }
//        };
//    }
}
