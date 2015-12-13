package com.bitdubai.reference_wallet.bank_money_wallet.fragments.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.adapters.AccountListAdapter;
import com.bitdubai.reference_wallet.bank_money_wallet.common.navigationDrawer.BankMoneyWalletNavigationViewPainter;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;
import com.bitdubai.reference_wallet.bank_money_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Created by guillermo on 04/12/15.
 */
public class AccountsListFragment extends FermatWalletListFragment<BankAccountNumber> implements FermatListItemListeners<BankAccountNumber>{

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<BankAccountNumber> accountsList;

    private static final String TAG = "AccountListActivityFragment";

    public static AccountsListFragment newInstance() {
        return new AccountsListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((BankMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
        accountsList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }
    @Override
    protected void initViews(View layout) {
        super.initViews(layout);
        showOrHideNoAccountListView(accountsList.isEmpty());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            BankMoneyWalletNavigationViewPainter navigationViewPainter = new BankMoneyWalletNavigationViewPainter(getActivity());
            getPaintActivtyFeactures().addNavigationView(navigationViewPainter);
        } catch (Exception e) {
            makeText(getActivity(), "Oops! recovering from system error", Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //inflater.inflate(R.menu.cbw_contract_history_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == R.id.action_no_filter) {
            filterContractStatus = null;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        if (item.getItemId() == R.id.action_filter_succeed) {
            filterContractStatus = ContractStatus.COMPLETED;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        if (item.getItemId() == R.id.action_filter_cancel) {
            filterContractStatus = ContractStatus.CANCELLED;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    public FermatAdapter getAdapter() {
	    if(adapter == null){
            adapter = new AccountListAdapter(getActivity(), accountsList);
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
    public void onItemClickListener(BankAccountNumber data, int position) {
        appSession.setData("account data", data);
        changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, appSession.getAppPublicKey());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.bw_fragment_accounts_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {

        //TODO: agregar el id del resource recycler layout para mostrar la lista.
        //TODO: arreglar el layout para mostrar el home.
        return R.id.account_list_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    /*
    @Override
    public void onItemClickListener(BankAccountNumber data, int position) {
        walletSession.setData("account data", data);
        changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, walletSession.getAppPublicKey());
    }
    */

    @Override
    public void onLongItemClickListener(BankAccountNumber data, int position) {
    }

    @Override
    public List<BankAccountNumber> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<BankAccountNumber> bankAccountNumbers = new ArrayList<>();
        String grouperText="accounts";
        if(moduleManager!=null) {
            try {
                bankAccountNumbers = moduleManager.getBankingWallet().getAccounts();
            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(
                            Wallets.BNK_BANKING_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
                }
            }

        }
        return bankAccountNumbers;
    }

    private void showOrHideNoAccountListView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            if (result != null && result.length > 0) {
                accountsList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(accountsList);
                showOrHideNoAccountListView(accountsList.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

}
