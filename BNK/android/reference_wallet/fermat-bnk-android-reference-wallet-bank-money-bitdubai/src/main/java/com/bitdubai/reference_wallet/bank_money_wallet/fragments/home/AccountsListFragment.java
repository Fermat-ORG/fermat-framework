package com.bitdubai.reference_wallet.bank_money_wallet.fragments.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet_module.interfaces.BankMoneyWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.bank_money_wallet.R;
import com.bitdubai.reference_wallet.bank_money_wallet.common.adapters.AccountListAdapter;
import com.bitdubai.reference_wallet.bank_money_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.bank_money_wallet.session.BankMoneyWalletSession;
import com.bitdubai.reference_wallet.bank_money_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by guillermo on 04/12/15.
 */
public class AccountsListFragment extends FermatWalletListFragment<BankAccountNumber> implements FermatListItemListeners<BankAccountNumber>{

    private BankMoneyWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private ArrayList<BankAccountNumber> accountsList;

    private static final String TAG = "AccountListActivityFragment";
    public AccountsListFragment() {
    }

    public static AccountsListFragment newInstance() {
        return new AccountsListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((BankMoneyWalletSession) walletSession).getModuleManager();
            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.BNK_BANKING_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
        accountsList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }
    /*@Override
    protected void initViews(View layout) {
        TODO: iniciar views
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //todo: añadir el navigationdrawer
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
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity());
        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_accounts_list;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return 0;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(BankAccountNumber data, int position) {
        walletSession.setData("contract_data", data);
        changeActivity(Activities.BNK_BANK_MONEY_WALLET_ACCOUNT_DETAILS, walletSession.getAppPublicKey());
    }

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

    @Override
    public void onPostExecute(Object... result) {

    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.bw_clear_project, container, false);
    }*/

}
