package com.bitdubai.reference_wallet.cash_money_wallet.fragments.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.exceptions.CantCreateDepositTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.exceptions.CantCreateWithdrawalTransactionException;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.withdrawal.interfaces.CashWithdrawalTransactionParameters;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.common.CashDepositTransactionParametersImpl;
import com.bitdubai.reference_wallet.cash_money_wallet.common.adapters.TransactionsAdapter;
import com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs.CreateTransactionFragmentDialog;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class BalanceSummaryFragment extends FermatWalletListFragment<CashMoneyWalletTransaction>
implements FermatListItemListeners<CashMoneyWalletTransaction> {

    protected final String TAG = "BalanceSummaryFragment";
    protected final String walletPublicKey = "publicKeyWalletMock";

    // Fermat Managers
    private CashMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //Data
    private ArrayList<CashMoneyWalletTransaction> transactionList;
    CashWalletBalances walletBalances;
    FiatCurrency walletCurrency;

    //UI
    private View noTransactionsView;
    CreateTransactionFragmentDialog dialog;


    public BalanceSummaryFragment() {}
    public static BalanceSummaryFragment newInstance() {return new BalanceSummaryFragment();}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CashMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

        //Get wallet transactions, balances and currency
        //TODO: Cargar el wallet.. loadCashMonetWallet() usando walletSession.getAppPublicKey() ????
        transactionList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        getWalletBalances();
        getWalletCurrency();
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        updateWalletBalances(layout);

        //configureToolbar();
        getToolbar().setBackgroundColor(getResources().getColor(R.color.csh_summary_top_background_color));


        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.csh_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        //Set up no_transactions view
        noTransactionsView = layout.findViewById(R.id.no_transactions);
        showOrHideNoTransactionsView(transactionList.isEmpty());

        layout.findViewById(R.id.fab_withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lauchCreateTransactionDialog(TransactionType.DEBIT);
            }
        });

        layout.findViewById(R.id.fab_deposit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lauchCreateTransactionDialog(TransactionType.CREDIT);
            }
        });
    }


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new TransactionsAdapter(getActivity(), transactionList);
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
    protected int getLayoutResource() {
        return R.layout.balance_summary;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.transactions_swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    /*private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }*/


    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            CustomerNavigationViewPainter navigationViewPainter = new CustomerNavigationViewPainter(getActivity(), null);
            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
        } catch (Exception e) {
            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }*/

    private void showOrHideNoTransactionsView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noTransactionsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noTransactionsView.setVisibility(View.GONE);
        }
    }

    /* @Override
     public void onItemClickListener(ContractBasicInformation data, int position) {
         walletSession.setData("contract_data", data);
         changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS, walletSession.getAppPublicKey());
     }*/

   /* @Override
    public void onLongItemClickListener(ContractBasicInformation data, int position) {
    }*/

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                transactionList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(transactionList);

                showOrHideNoTransactionsView(transactionList.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //TODO: show error, toast?
        }
    }

    /* MISC FUNCTIONS */
    private void lauchCreateTransactionDialog(TransactionType transactionType){
        dialog = new CreateTransactionFragmentDialog(getActivity(), (CashMoneyWalletSession) appSession, getResources(), transactionType);
        //dialog.setOnDismissListener(this);
        dialog.show();
    }

    @Override
    public List<CashMoneyWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CashMoneyWalletTransaction> data = new ArrayList<>();

        List<TransactionType> transactionTypes = new ArrayList<>();
        transactionTypes.add(TransactionType.CREDIT);
        transactionTypes.add(TransactionType.DEBIT);

        List<BalanceType> balanceTypes = new ArrayList<>();
        balanceTypes.add(BalanceType.BOOK);

        if (moduleManager != null) {
            try {
                data.addAll(moduleManager.getTransactions(walletPublicKey, transactionTypes, balanceTypes, 100, 0));

            } catch (Exception ex) {
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        } else {
            Toast.makeText(getActivity(), "Sorry, an error happened in BalanceSummaryFragment (Module == null)", Toast.LENGTH_SHORT).show();
        }
        return data;
    }


    private void getWalletBalances() {
        try {
            this.walletBalances = moduleManager.getWalletBalances(walletPublicKey);
        } catch (CantGetCashMoneyWalletBalancesException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }
    private void getWalletCurrency() {
        try {
            this.walletCurrency = moduleManager.getWalletCurrency(walletPublicKey);
        } catch (CantGetCashMoneyWalletCurrencyException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private void updateWalletBalances(View view) {
        FermatTextView bookTextView = (FermatTextView) view.findViewById(R.id.textView_book_amount);
        FermatTextView availableTextView = (FermatTextView) view.findViewById(R.id.textView_available_amount);

        bookTextView.setText(String.valueOf(this.walletBalances.getBookBalance() + " " + this.walletCurrency.getCode()));
        availableTextView.setText(String.valueOf(this.walletBalances.getAvailableBalance() + " " + this.walletCurrency.getCode()));
        Toast.makeText(getActivity(), this.walletBalances.getBookBalance() + " " + this.walletCurrency.getCode(), Toast.LENGTH_SHORT).show();

        bookTextView.invalidate();
        availableTextView.invalidate();

    }

    @Override
    public void onItemClickListener(CashMoneyWalletTransaction data, int position) {
        Toast.makeText(getActivity(), "onItemClickListener", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLongItemClickListener(CashMoneyWalletTransaction data, int position) {
        Toast.makeText(getActivity(), "onLongItemClickListener", Toast.LENGTH_SHORT).show();

    }
}
