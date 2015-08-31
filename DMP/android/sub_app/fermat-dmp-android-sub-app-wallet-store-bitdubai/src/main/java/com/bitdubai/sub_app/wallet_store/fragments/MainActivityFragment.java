package com.bitdubai.sub_app.wallet_store.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogue;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreCatalogueItem;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces.WalletStoreModuleManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.sub_app.wallet_store.common.DetailedCatalogItemWorker;
import com.bitdubai.sub_app.wallet_store.common.DetailedCatalogItemWorkerCallback;
import com.bitdubai.sub_app.wallet_store.common.adapters.WalletStoreCatalogueAdapter;
import com.bitdubai.sub_app.wallet_store.common.models.WalletStoreListItem;
import com.bitdubai.sub_app.wallet_store.session.WalletStoreSubAppSession;
import com.bitdubai.sub_app.wallet_store.util.CommonLogger;
import com.wallet_store.bitdubai.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment que luce como un Activity donde se muestra la lista de Wallets disponibles en el catalogo de la Wallet Store
 *
 * @author Nelson Ramirez
 * @version 1.0
 */
public class MainActivityFragment extends FermatListFragment<WalletStoreListItem> implements FermatListItemListeners<WalletStoreListItem> {

    /**
     * MANAGERS
     */
    private WalletStoreModuleManager moduleManager;
    private ErrorManager errorManager;

    /**
     * DATA
     */
    private ArrayList<WalletStoreListItem> catalogueItemList;

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
            moduleManager = ((WalletStoreSubAppSession) subAppsSession).getWalletStoreModuleManager();
            errorManager = subAppsSession.getErrorManager();
            catalogueItemList = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
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
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new WalletStoreCatalogueAdapter(getActivity(), catalogueItemList);
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
    public void onItemClickListener(WalletStoreListItem data, int position) {

        Activity activity = getActivity();
        DetailedCatalogItemWorkerCallback workerCallback = new DetailedCatalogItemWorkerCallback(
                data, activity, subAppsSession, subAppSettings, subAppResourcesProviderManager);

        DetailedCatalogItemWorker worker = new DetailedCatalogItemWorker(activity, workerCallback);
        worker.setCatalogItemId(data.getId());
        worker.setModuleManager(moduleManager);
        worker.run();
    }

    @Override
    public ArrayList<WalletStoreListItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<WalletStoreListItem> data;

        try {
            WalletStoreCatalogue catalogue = moduleManager.getCatalogue();
            List<WalletStoreCatalogueItem> catalogueItems = catalogue.getWalletCatalogue(0, 0);

            data = new ArrayList<>();
            for (WalletStoreCatalogueItem catalogItem : catalogueItems) {
                WalletStoreListItem item = new WalletStoreListItem(catalogItem, getResources());
                data.add(item);
            }

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            data = WalletStoreListItem.getTestData(getResources());
        }

        return data;
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
                catalogueItemList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(catalogueItemList);
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
}
