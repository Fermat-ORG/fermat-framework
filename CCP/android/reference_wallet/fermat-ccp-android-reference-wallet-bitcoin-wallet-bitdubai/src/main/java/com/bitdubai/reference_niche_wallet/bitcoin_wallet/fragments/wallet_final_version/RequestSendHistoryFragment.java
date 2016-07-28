package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestHistoryAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.onRefreshList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2015.09.30..
 */
public class RequestSendHistoryFragment extends FermatWalletListFragment<PaymentRequest,ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager> implements FermatListItemListeners<PaymentRequest>, View.OnClickListener,onRefreshList {

    /**
     * Session
     */

    String walletPublicKey = "reference_wallet";
    /**
     * MANAGERS
     */
    private CryptoWallet cryptoWallet;
    /**
     * DATA
     */
    private List<PaymentRequest> lstPaymentRequest;
    private PaymentRequest selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;
    private String feeLevel = "";
    private String feeOrigin = "";


    BlockchainNetworkType blockchainNetworkType;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestSendHistoryFragment newInstance() {
        return new RequestSendHistoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        lstPaymentRequest = new ArrayList<PaymentRequest>();

        getExecutor().execute(new Runnable() {
            @Override
            public void run() {
//                final FermatDrawable drawable = getResources().getDrawable(R.drawable.background_gradient, null);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //                      getPaintActivtyFeactures().setActivityBackgroundColor(drawable);
                    }
                });
            }
        });
        try {
            cryptoWallet = appSession.getModuleManager();



            BitcoinWalletSettings bitcoinWalletSettings;
            try {
                bitcoinWalletSettings = cryptoWallet.loadAndGetSettings(appSession.getAppPublicKey());
                this.blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();

                if (bitcoinWalletSettings.getFeedLevel() == null)
                    bitcoinWalletSettings.setFeedLevel(BitcoinFee.SLOW.toString());
                else
                    feeLevel = bitcoinWalletSettings.getFeedLevel();
            }catch (Exception e){

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
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    private void setUp(){


    }

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();
        isRefreshing = false;
        onRefresh();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstPaymentRequest = new ArrayList<PaymentRequest>();
        } catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }


    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_transaction_main;
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
            adapter = new PaymentRequestHistoryAdapter(getActivity(), lstPaymentRequest,cryptoWallet,appSession,this);
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
        List<PaymentRequest> lstPaymentRequest  = new ArrayList<PaymentRequest>();

        try {
            //when refresh offset set 0
            if(refreshType.equals(FermatRefreshTypes.NEW))
                offset = 0;

            lstPaymentRequest = cryptoWallet.listSentPaymentRequest(walletPublicKey,blockchainNetworkType,10,offset);
            offset+=1;
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
           e.printStackTrace();
       }

            return lstPaymentRequest;

    }

    @Override
    public void onItemClickListener(PaymentRequest item, int position) {
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
    public void onLongItemClickListener(PaymentRequest data, int position) {

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
                if(lstPaymentRequest.isEmpty()) FermatAnimationsUtils.showEmpty(getActivity(), true, empty);
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



    public void setReferenceWalletSession(ReferenceAppFermatSession referenceWalletSession) {
        this.appSession = referenceWalletSession;
    }

    @Override
    public void onClick(View v) {
        try {
            PaymentRequest paymentRequest = (PaymentRequest) appSession.getData("LastRequestSelected");
            int id = v.getId();
            if(id == R.id.btn_refuse_request){

                cryptoWallet.refuseRequest(paymentRequest.getRequestId());
                Toast.makeText(getActivity(),"Denegado",Toast.LENGTH_SHORT).show();
            }
            else if ( id == R.id.btn_accept_request){
                cryptoWallet.approveRequest(paymentRequest.getRequestId(),cryptoWallet.getSelectedActorIdentity().getPublicKey(),
                        BitcoinFee.valueOf(feeLevel).getFee(), FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT);
                Toast.makeText(getActivity(),"Aceptado",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            showMessage(getActivity(), "Cant Accept or Denied Send Payment Exception- " + e.getMessage());
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            if (id == 1) {
//                moduleManager.launchNotification();
                changeActivity(Activities.CCP_BITCOIN_WALLET_SEND_FORM_ACTIVITY, appSession.getAppPublicKey());
                return true;
            }

            if (id == 4) {
                changeActivity(Activities.CCP_BITCOIN_WALLET_REQUEST_FORM_ACTIVITY, appSession.getAppPublicKey());
                return true;
            }
        } catch (Exception e) {
            // errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }



}
