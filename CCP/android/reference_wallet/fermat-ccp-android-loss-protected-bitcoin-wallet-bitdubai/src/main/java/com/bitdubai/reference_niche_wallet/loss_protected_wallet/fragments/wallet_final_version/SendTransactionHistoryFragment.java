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
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatAnimationsUtils;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletTransaction;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.adapters.TransactionsHistoryAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.onRefreshList;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.makeText;

/**
 * Created by root on 18/05/16.
 */
public class SendTransactionHistoryFragment
        extends FermatWalletListFragment<LossProtectedWalletTransaction,ReferenceAppFermatSession<LossProtectedWallet>,ResourceProviderManager>
        implements FermatListItemListeners<LossProtectedWalletTransaction>, onRefreshList {

    /**
     * Session
     */
    ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;
    String walletPublicKey = "loss_protected_wallet";
    /**
     * MANAGERS
     */
    private LossProtectedWallet lossProtectedWalletManager;
    /**
     * DATA
     */
    private List<LossProtectedWalletTransaction> lstWalletTransaction;
    private LossProtectedWalletTransaction selectedItem;
    /**
     * Executor Service
     */
    private ExecutorService executor;
    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;
    private View rootView;
    private LinearLayout empty;

    LossProtectedWalletSettings lossProtectedWalletSettings;

    BlockchainNetworkType blockchainNetworkType;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static SendTransactionHistoryFragment newInstance() {
        return new SendTransactionHistoryFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        lossProtectedWalletSession =  appSession;

        lstWalletTransaction = new ArrayList<>();
        try {
            lossProtectedWalletManager = appSession.getModuleManager();


            try {
                lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(appSession.getAppPublicKey());
                this.blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();

            } catch (CantGetSettingsException e) {
                makeText(getActivity(), "Oooops! recovering from system error: CantGetSettingsException", Toast.LENGTH_SHORT).show();
                lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
            }

            onRefresh();

        } catch (Exception ex) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, ex);

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = super.onCreateView(inflater, container, savedInstanceState);
            //RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), R.drawable.divider_shape);
            //recyclerView.addItemDecoration(itemDecoration);
            empty = (LinearLayout) rootView.findViewById(R.id.empty);
            //setUp();
            return rootView;
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
        return container;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
            lstWalletTransaction = new ArrayList<>();
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error: onActivityCreated", Toast.LENGTH_SHORT).show();
            lossProtectedWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.loss_fragment_transaction_main_send;
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
    public FermatAdapter getAdapter() {
        if (adapter==null){
            adapter = new TransactionsHistoryAdapter(
                    getActivity(),
                    lstWalletTransaction,
                    lossProtectedWalletManager,
                    lossProtectedWalletSession,this);
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
    public List<LossProtectedWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<LossProtectedWalletTransaction> lstTransaction = new ArrayList<LossProtectedWalletTransaction>();

        if(blockchainNetworkType != null)
        {
            try {
                //when refresh offset set 0
                if (refreshType.equals(FermatRefreshTypes.NEW))
                    offset = 0;

                ActiveActorIdentityInformation intraUserLoginIdentity = null;
                intraUserLoginIdentity = lossProtectedWalletManager.getSelectedActorIdentity();
                String intraUserPk = null;
                if (intraUserLoginIdentity != null) {
                    intraUserPk = intraUserLoginIdentity.getPublicKey();
                }

                lstTransaction = lossProtectedWalletManager.listAllActorTransactionsByTransactionType(
                        BalanceType.AVAILABLE,
                        TransactionType.DEBIT,
                        lossProtectedWalletSession.getAppPublicKey(),
                        intraUserPk,
                        blockchainNetworkType,
                        MAX_TRANSACTIONS,0);

                //offset += MAX_TRANSACTIONS;
            } catch (Exception e) {
                lossProtectedWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                e.printStackTrace();
            }
        }



        return lstTransaction;
    }

    @Override
    public void onItemClickListener(LossProtectedWalletTransaction item, int position) {
        selectedItem = item;
        onRefresh();
    }

    @Override
    public void onLongItemClickListener(LossProtectedWalletTransaction data, int position) {

    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstWalletTransaction = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstWalletTransaction);
                if (lstWalletTransaction.isEmpty())
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
        }
    }

    public void setReferenceWalletSession(ReferenceAppFermatSession<LossProtectedWallet> referenceWalletSession) {
        this.lossProtectedWalletSession = referenceWalletSession;
    }

}
