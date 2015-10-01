package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapterNew;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatListFragment;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestPendingAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by mati on 2015.10.01..
 */
public class RequestHomePaymentFragment extends FermatWalletListFragment<PaymentRequest>
        implements FermatListItemListeners<PaymentRequest> {

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
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

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
    protected int getLayoutResource() {
        return 0;//R.layout.wallet_store_fragment_main_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return 0;//R.id.recicler
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            //WalletStoreItemPopupMenuListener listener = getWalletStoreItemPopupMenuListener();
            adapter = null;//new PaymentRequestPendingAdapter(getActivity(), lstPaymentRequest, viewInflater,(ReferenceWalletSession)walletSession);
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
            lstPaymentRequest = cryptoWallet.listPaymentRequestDatOrder();

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



}
