
package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.Views.DividerItemDecoration;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedPaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 27/04/16.
 */
public class RequestSendHistoryFragment2 extends FermatWalletListFragment<LossProtectedPaymentRequest> implements FermatListItemListeners<LossProtectedPaymentRequest>, View.OnClickListener, onRefreshList {

    /**
     * Session
     */
    LossProtectedWalletSession referenceWalletSession;
    String walletPublicKey = "loss_protected_wallet";
    /**
     * MANAGERS
     */
    private LossProtectedWallet cryptoWallet;
    /**
     * DATA
     */
    private List<LossProtectedPaymentRequest> lstPaymentRequest;
    private LossProtectedPaymentRequest selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;

    SettingsManager<LossProtectedWalletSettings> settingsManager;

    BlockchainNetworkType blockchainNetworkType;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestSendHistoryFragment2 newInstance() {
        return new RequestSendHistoryFragment2();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (LossProtectedWalletSession) appSession;

        lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();

        lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data

        getExecutor().execute(new Runnable() {
            @Override
            public void run() {
//                final Drawable drawable = getResources().getDrawable(R.drawable.background_gradient, null);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //                      getPaintActivtyFeactures().setActivityBackgroundColor(drawable);
                    }
                });
            }
        });
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();
            settingsManager = referenceWalletSession.getModuleManager().getSettingsManager();


            LossProtectedWalletSettings bitcoinWalletSettings;
            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
                this.blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
            } catch (Exception e) {

            }

            onRefresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            recyclerView.addItemDecoration(itemDecoration);
            empty = (LinearLayout) rootView.findViewById(R.id.empty);
            setUp();
            return rootView;
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    private void setUp() {


    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.loss_fragment_transaction_main;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
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
            adapter = new PaymentRequestHistoryAdapter(getActivity(), lstPaymentRequest, cryptoWallet, referenceWalletSession, this);
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
    public List<LossProtectedPaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LossProtectedPaymentRequest> lstPaymentRequest = new ArrayList<LossProtectedPaymentRequest>();
        if(blockchainNetworkType != null)
        {
            try {
                //when refresh offset set 0
                if (refreshType.equals(FermatRefreshTypes.NEW))
                    offset = 0;

                lstPaymentRequest = cryptoWallet.listSentPaymentRequest(walletPublicKey, blockchainNetworkType, 10, offset);
                offset += MAX_TRANSACTIONS;
            } catch (Exception e) {
                referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                e.printStackTrace();
            }
        }


        return lstPaymentRequest;
    }

    @Override
    public void onItemClickListener(LossProtectedPaymentRequest item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(LossProtectedPaymentRequest data, int position) {

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
                if (lstPaymentRequest.isEmpty())
                    FermatAnimationsUtils.showEmpty(getActivity(), true, empty);
                else FermatAnimationsUtils.showEmpty(getActivity(), false, empty);
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


    public void setReferenceWalletSession(LossProtectedWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }

    @Override
    public void onClick(View v) {
        try {
            LossProtectedPaymentRequest paymentRequest = referenceWalletSession.getLastRequestSelected();
            int id = v.getId();
            if (id == R.id.btn_refuse_request) {

                cryptoWallet.refuseRequest(paymentRequest.getRequestId());
                Toast.makeText(getActivity(), "Denegado", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.btn_accept_request) {
                cryptoWallet.approveRequest(paymentRequest.getRequestId(), referenceWalletSession.getIntraUserModuleManager().getPublicKey());
                Toast.makeText(getActivity(), "Aceptado", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            WalletUtils.showMessage(getActivity(), "Cant Accept or Denied Send Payment Exception- " + e.getMessage());
        }

    }


}
