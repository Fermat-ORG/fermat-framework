package com.bitdubai.reference_wallet.bank_money_wallet.fragments.summary;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankMoneyTransactionRecord;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.adapters.AccountListAdapter;
import com.bitdubai.reference_wallet.bank_money_wallet.common.adapters.TransactionListAdapter;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;
import com.bitdubai.reference_wallet.bank_money_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by memo on 08/12/15.
 */
public class AccountDetailFragment extends FermatWalletListFragment<BankMoneyTransactionRecord> implements FermatListItemListeners<BankMoneyTransactionRecord>, DialogInterface.OnDismissListener  {


    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<BankMoneyTransactionRecord> transactionList;
    private String walletPublicKey= "banking_wallet";
    private BankAccountNumber bankAccountNumber;

    private static final String TAG = "AccountListActivityFragment";
    public AccountDetailFragment() {
    }

    public static AccountDetailFragment newInstance() {
        return new AccountDetailFragment();
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bw_account_detail_summary, container, false);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BankAccountNumber data = (BankAccountNumber)appSession.getData("account_data");
        try {

            moduleManager = ((BankMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
        System.out.println("DATA ="+data.getAccount());
        bankAccountNumber = data;
        transactionList = (ArrayList)getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        configureToolbar();
        //showOrHideNoAccountListView(accountsList.isEmpty());
    }

    private void configureToolbar() {
        getToolbar().setBackgroundColor(getResources().getColor(R.color.background_header));
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bw_account_detail_summary;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return false;
    }

    @Override
    public void onPostExecute(Object... result) {

    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

    @Override
    public FermatAdapter getAdapter() {
        if(adapter == null){
            adapter = new TransactionListAdapter(getActivity(), transactionList);
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
    public void onItemClickListener(BankMoneyTransactionRecord data, int position) {

    }

    @Override
    public void onLongItemClickListener(BankMoneyTransactionRecord data, int position) {

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

    }

    @Override
    public List<BankMoneyTransactionRecord> getMoreDataAsync(FermatRefreshTypes refreshType, int pos){
        List<BankMoneyTransactionRecord> data = new ArrayList<>();
        if (moduleManager != null) {
            try {
                data.addAll(moduleManager.getBankingWallet().getTransactions(bankAccountNumber.getAccount()));

            } catch (Exception ex) {
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        } else {
            Toast.makeText(getActivity(), "Sorry, an error happened in BalanceSummaryFragment (Module == null)", Toast.LENGTH_SHORT).show();
        }
        return data;

    }
}
