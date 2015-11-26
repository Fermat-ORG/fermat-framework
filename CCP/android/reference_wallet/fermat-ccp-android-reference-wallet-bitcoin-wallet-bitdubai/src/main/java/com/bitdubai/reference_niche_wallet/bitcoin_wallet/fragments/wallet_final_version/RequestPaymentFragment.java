package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragmentNew;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestPendingAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 *
 *
 * @author Matias Furszyfer
 * @version 1.0
 */


public class RequestPaymentFragment extends FermatListFragmentNew<PaymentRequest>
        implements FermatListItemListeners<PaymentRequest>, OnMenuItemClickListener {

    /**
     * MANAGERS
     */
    private CryptoWallet cryptoWallet;

    /**
     * Session
     */
    ReferenceWalletSession referenceWalletSession;

    /**
     * DATA
     */
    private List<PaymentRequest> lstPaymentRequest;
    private PaymentRequest selectedItem;
    String walletPublicKey = "reference_wallet";
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
        RequestPaymentFragment requestPaymentFragment = new RequestPaymentFragment();
        return new RequestPaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        referenceWalletSession = (ReferenceWalletSession)walletSession;

        super.onCreate(savedInstanceState);
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();

            lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data

             viewInflater = new ViewInflater(getActivity(),null);

            View rootView = viewInflater.inflate("layout",null);


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
        return "payment_request_base";//R.layout.wallet_store_fragment_main_activity;
    }

    @Override
    protected String getSwipeRefreshLayoutName() {
        return "swipe_refresh";
    }

    @Override
    protected String getRecyclerLayoutName() {
        return "payment_request_recycler_view";
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
            adapter = new PaymentRequestPendingAdapter(getActivity(), lstPaymentRequest, viewInflater,(ReferenceWalletSession)walletSession);
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
    public List<PaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<PaymentRequest> lstPaymentRequest  = null;

        try {
            lstPaymentRequest = cryptoWallet.listPaymentRequestDateOrder(walletPublicKey,10,0);

        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

           // data = RequestPaymentListItem.getTestData(getResources());
        }

        return lstPaymentRequest;
    }

    @Override
    public void onItemClickListener(PaymentRequest item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
    }

    @Override
    public void onLongItemClickListener(PaymentRequest data, int position) {
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

    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
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
