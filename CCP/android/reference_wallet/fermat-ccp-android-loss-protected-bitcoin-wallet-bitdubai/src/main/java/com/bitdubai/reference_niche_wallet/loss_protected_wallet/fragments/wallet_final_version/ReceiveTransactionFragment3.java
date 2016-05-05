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
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletIntraUserIdentity;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.TransactionsHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by Joaquin Carrasquero on 05/04/16.
 */
public class ReceiveTransactionFragment3 extends FermatWalletListFragment<LossProtectedWalletTransaction> implements FermatListItemListeners<LossProtectedWalletTransaction>, View.OnClickListener,onRefreshList {

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
    private List<LossProtectedWalletTransaction> lst;
    private LossProtectedWalletTransaction selectedItem;
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
    public static ReceiveTransactionFragment3 newInstance() {
        return new ReceiveTransactionFragment3();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (LossProtectedWalletSession)appSession;

        lst = new ArrayList<LossProtectedWalletTransaction>();

        lst = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
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
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lst = new ArrayList<LossProtectedWalletTransaction>();
        } catch (Exception e){
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
        return R.layout.transaction_history_main;
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
            adapter = new TransactionsHistoryAdapter(getActivity(), lst,cryptoWallet,referenceWalletSession,this);
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
    public List<LossProtectedWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LossProtectedWalletTransaction> list  = new ArrayList<LossProtectedWalletTransaction>();

        try {
            //when refresh offset set 0
            if(refreshType.equals(FermatRefreshTypes.NEW))
                offset = 0;

            LossProtectedWalletIntraUserIdentity intraUserLoginIdentity = referenceWalletSession.getIntraUserModuleManager();
            if(intraUserLoginIdentity!=null) {
                String intraUserPk = intraUserLoginIdentity.getPublicKey();
                lst = cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.CREDIT, referenceWalletSession.getAppPublicKey(), intraUserPk, blockchainNetworkType, MAX_TRANSACTIONS, 0);
                offset+=MAX_TRANSACTIONS;
            }


        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            e.printStackTrace();
        }

        return lst;

    }

    @Override
    public void onItemClickListener(LossProtectedWalletTransaction item, int position) {
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
    public void onLongItemClickListener(LossProtectedWalletTransaction data, int position) {

    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lst = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lst);
                if(lst.isEmpty()) FermatAnimationsUtils.showEmpty(getActivity(), true, empty);
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

    }
}
