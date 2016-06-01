package com.bitdubai.reference_wallet.cash_money_wallet.fragments.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_csh_api.all_definition.constants.CashMoneyWalletBroadcasterConstants;
import com.bitdubai.fermat_csh_api.all_definition.enums.BalanceType;
import com.bitdubai.fermat_csh_api.all_definition.enums.TransactionType;
import com.bitdubai.fermat_csh_api.all_definition.interfaces.CashWalletBalances;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.exceptions.CantGetCashMoneyWalletCurrencyException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet.interfaces.CashMoneyWalletTransaction;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.exceptions.CantGetCashMoneyWalletBalancesException;
import com.bitdubai.fermat_csh_api.layer.csh_wallet_module.interfaces.CashMoneyWalletModuleManager;
import com.bitdubai.reference_wallet.cash_money_wallet.R;
import com.bitdubai.reference_wallet.cash_money_wallet.common.adapters.TransactionsAdapter;
import com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs.CreateTransactionFragmentDialog;
import com.bitdubai.reference_wallet.cash_money_wallet.common.dialogs.HomeTutorialFragmentDialog;
import com.bitdubai.reference_wallet.cash_money_wallet.session.CashMoneyWalletSession;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro Bicelis on 12/9/2015.
 */
public class HomeFragment extends FermatWalletListFragment<CashMoneyWalletTransaction>
implements FermatListItemListeners<CashMoneyWalletTransaction>, DialogInterface.OnDismissListener {

    protected final String TAG = "HomeFragment";

    // Fermat Managers
    private CashMoneyWalletSession walletSession;
    private CashMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //Data
    private ArrayList<CashMoneyWalletTransaction> transactionList;
    CashWalletBalances walletBalances;
    FiatCurrency walletCurrency;

    //UI
    private View noTransactionsView;
    FermatTextView bookTextView;
    FermatTextView availableTextView;
    FermatTextView availableCurrencyTextView;
    FermatTextView bookCurrencyTextView;
    View bookBalanceHr;
    LinearLayout bookBalanceAmountCode;
    FermatTextView bookBalanceText;
    LinearLayout topLip;
    com.getbase.floatingactionbutton.FloatingActionsMenu fab;
    com.getbase.floatingactionbutton.FloatingActionButton fabWithdraw;
    CreateTransactionFragmentDialog transactionFragmentDialog;
    HomeTutorialFragmentDialog homeTutorialDialog;
    private static final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance();

    public HomeFragment() {}
    public static HomeFragment newInstance() {return new HomeFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            walletSession = ((CashMoneyWalletSession) appSession);
            moduleManager = walletSession.getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }

        //Get wallet transactions, balances and currency
        transactionList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        getWalletBalances();
        getWalletCurrency();
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        this.bookTextView = (FermatTextView) layout.findViewById(R.id.textView_book_amount);
        this.availableTextView = (FermatTextView) layout.findViewById(R.id.textView_available_amount);
        this.bookCurrencyTextView = (FermatTextView) layout.findViewById(R.id.textView_available_currency);
        this.availableCurrencyTextView = (FermatTextView) layout.findViewById(R.id.textView_book_currency);
        this.bookBalanceHr = layout.findViewById(R.id.csh_home_balance_book_hr);
        this.bookBalanceAmountCode = (LinearLayout) layout.findViewById(R.id.csh_home_balance_book_amount_code);
        this.bookBalanceText = (FermatTextView) layout.findViewById(R.id.csh_home_balance_book_text);
        this.topLip = (LinearLayout) layout.findViewById(R.id.csh_home_top_lip);
        this.fab = (com.getbase.floatingactionbutton.FloatingActionsMenu) layout.findViewById(R.id.fab_multiple_actions);
        this.fabWithdraw = (com.getbase.floatingactionbutton.FloatingActionButton) layout.findViewById(R.id.fab_withdraw);
        updateWalletBalances();
        updateWalletCurrency();
        handleWidhtrawalFabVisibilityAccordingToBalance();

        //configureToolbar();
        //getToolbar().setBackgroundColor(getResources().getColor(R.color.csh_summary_top_background_color));


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


        boolean showHomeTutorial = false;
        try{
            showHomeTutorial = moduleManager.loadAndGetSettings(walletSession.getAppPublicKey()).isHomeTutorialDialogEnabled();
        } catch (CantGetSettingsException | SettingsNotFoundException  e){}

        if(showHomeTutorial)
            lauchHomeTutorialDialog();


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
        return R.layout.csh_home_page;
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

                getWalletBalances();
                updateWalletBalances();
                handleWidhtrawalFabVisibilityAccordingToBalance();
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
        transactionFragmentDialog = new CreateTransactionFragmentDialog(getActivity(), (CashMoneyWalletSession) appSession, getResources(), transactionType, null, null);
        transactionFragmentDialog.setOnDismissListener(this);
        transactionFragmentDialog.show();
    }
    private void lauchHomeTutorialDialog(){
        homeTutorialDialog = new HomeTutorialFragmentDialog(getActivity(), (CashMoneyWalletSession) appSession, getResources());
        homeTutorialDialog.setOnDismissListener(this);
        homeTutorialDialog.show();
    }



    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        fab.collapse();
        getWalletBalances();
        updateWalletBalances();
        handleWidhtrawalFabVisibilityAccordingToBalance();
        layoutManager.smoothScrollToPosition(recyclerView, null, 0);
        onRefresh();
    }


    @Override
    public List<CashMoneyWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CashMoneyWalletTransaction> data = new ArrayList<>();

        List<TransactionType> transactionTypes = new ArrayList<>();
        transactionTypes.add(TransactionType.CREDIT);
        transactionTypes.add(TransactionType.DEBIT);
        transactionTypes.add(TransactionType.HOLD);
        transactionTypes.add(TransactionType.UNHOLD);

        List<BalanceType> balanceTypes = new ArrayList<>();
        balanceTypes.add(BalanceType.AVAILABLE);

        if (moduleManager != null) {
            try {
                data.addAll(moduleManager.getPendingTransactions());
                data.addAll(moduleManager.getTransactions(walletSession.getAppPublicKey(), transactionTypes, balanceTypes, 100, 0));

            } catch (Exception ex) {
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        } else {
            Toast.makeText(getActivity(), "Sorry, an error happened in HomeFragment (Module == null)", Toast.LENGTH_SHORT).show();
        }
        return data;
    }




    private void getWalletBalances() {
        try {
            this.walletBalances = moduleManager.getWalletBalances(walletSession.getAppPublicKey());
        } catch (CantGetCashMoneyWalletBalancesException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }
    private void getWalletCurrency() {
        try {
            this.walletCurrency = moduleManager.getWalletCurrency(walletSession.getAppPublicKey());
        } catch (CantGetCashMoneyWalletCurrencyException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private void updateWalletBalances() {

        bookTextView.setText(String.valueOf(this.walletBalances.getBookBalance()));
        availableTextView.setText(String.valueOf(this.walletBalances.getAvailableBalance()));
        int topLipHeight = 0;

        //Hide book balance if balances are equal
        if(this.walletBalances.getAvailableBalance().compareTo(this.walletBalances.getBookBalance()) == 0){
            this.bookBalanceHr.setVisibility(View.GONE);
            this.bookBalanceAmountCode.setVisibility(View.GONE);
            this.bookBalanceText.setVisibility(View.GONE);
            topLipHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 180, getResources().getDisplayMetrics());;
            topLip.setBackground(getResources().getDrawable(R.drawable.csh_top_lip_medium));
        }else {
            this.bookBalanceHr.setVisibility(View.VISIBLE);
            this.bookBalanceAmountCode.setVisibility(View.VISIBLE);
            this.bookBalanceText.setVisibility(View.VISIBLE);
            topLipHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 240, getResources().getDisplayMetrics());;
            topLip.setBackground(getResources().getDrawable(R.drawable.csh_top_lip_big));
        }

        //Change toplip height
        ViewGroup.LayoutParams params = topLip.getLayoutParams();
        params.height = topLipHeight;
        topLip.setLayoutParams(params);


        topLip.invalidate();
        bookTextView.invalidate();
        availableTextView.invalidate();
    }

    private void updateWalletCurrency() {

        availableCurrencyTextView.setText(this.walletCurrency.getCode());
        bookCurrencyTextView.setText(this.walletCurrency.getCode());

        availableCurrencyTextView.invalidate();
        bookCurrencyTextView.invalidate();
    }

    private void handleWidhtrawalFabVisibilityAccordingToBalance()
    {
        if(this.walletBalances.getAvailableBalance().compareTo(new BigDecimal(0)) == 0 && this.walletBalances.getBookBalance().compareTo(new BigDecimal(0)) == 0)
            fabWithdraw.setVisibility(View.INVISIBLE);
        else
            fabWithdraw.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemClickListener(CashMoneyWalletTransaction data, int position) {
        handleTransactionClick(data, position);
    }

    @Override
    public void onLongItemClickListener(CashMoneyWalletTransaction data, int position) {
        handleTransactionClick(data, position);
    }


    private void handleTransactionClick(CashMoneyWalletTransaction data, int position){

        if(data.isPending()) {
            //Try to cancel transaction if it is pending
            try{
                moduleManager.cancelAsyncCashTransaction(data);
                Thread.sleep(100);
            } catch (Exception e) {
                //Ignore this error
            }

            appSession.setData("transaction", data);
            appSession.setData("checkIfTransactionHasBeenCommitted", true);
            changeActivity(Activities.CSH_CASH_MONEY_WALLET_TRANSACTION_DETAIL, appSession.getAppPublicKey());
        }
        else {
            appSession.setData("transaction", data);
            appSession.setData("checkIfTransactionHasBeenCommitted", false);
            changeActivity(Activities.CSH_CASH_MONEY_WALLET_TRANSACTION_DETAIL, appSession.getAppPublicKey());
        }

    }


    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW:
                onRefresh();
                break;
            case CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW_INSUFICCIENT_FUNDS:
                Toast.makeText(getActivity(), "Transaction failed due to insufficient funds", Toast.LENGTH_SHORT).show();

                onRefresh();
                break;
            case CashMoneyWalletBroadcasterConstants.CSH_REFERENCE_WALLET_UPDATE_TRANSACTION_VIEW_TRANSACTION_FAILED:
                Toast.makeText(getActivity(), "Sorry, the transaction has failed.", Toast.LENGTH_SHORT).show();
                onRefresh();
                break;
            default:
                super.onUpdateViewOnUIThread(code);
        }
    }
}

