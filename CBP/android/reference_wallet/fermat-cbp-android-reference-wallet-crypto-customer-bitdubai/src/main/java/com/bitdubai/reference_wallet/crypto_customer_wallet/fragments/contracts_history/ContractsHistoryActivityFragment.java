package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.contracts_history;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractStatus;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.ContractHistoryAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContractsHistoryActivityFragment extends FermatWalletListFragment<ContractBasicInformation>
        implements FermatListItemListeners<ContractBasicInformation> {

    // Constants
    private static final String TAG = "ContractsHistoryActivityFragment";

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private ArrayList<ContractBasicInformation> contractHistoryList;
    private ContractStatus filterContractStatus = null;

    //UI
    private View noContractsView;


    public static ContractsHistoryActivityFragment newInstance() {
        return new ContractsHistoryActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoCustomerWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            contractHistoryList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);

            //contractHistoryList = (ArrayList) TestData.getContractsHistory(null);

        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.ccw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        noContractsView = layout.findViewById(R.id.ccw_no_contracts);

        showOrHideNoContractsView(contractHistoryList.isEmpty());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.ccw_contract_history_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.ccw_action_no_filter) {
            filterContractStatus = null;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        if (item.getItemId() == R.id.ccw_action_filter_succeed) {
            filterContractStatus = ContractStatus.COMPLETED;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        if (item.getItemId() == R.id.ccw_action_filter_canceled) {
            filterContractStatus = ContractStatus.CANCELLED;
            swipeRefreshLayout.setRefreshing(true);
            onRefresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ContractHistoryAdapter(getActivity(), contractHistoryList);
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
        return R.layout.ccw_fragment_contracts_history_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.ccw_contracts_history_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }

    private void showOrHideNoContractsView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noContractsView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noContractsView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClickListener(ContractBasicInformation data, int position) {
        appSession.setData(CryptoCustomerWalletSession.CONTRACT_DATA, data);
        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CLOSE_CONTRACT_DETAILS, appSession.getAppPublicKey());
        //changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACT_DETAILS, appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(ContractBasicInformation data, int position) {
    }

    @Override
    public List<ContractBasicInformation> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<ContractBasicInformation> data = new ArrayList<>();

        if (moduleManager != null) {
            try {

                data.addAll(moduleManager.getContractsHistory(filterContractStatus, 20, 0));

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(
                            Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
            }
        } else {
            Toast.makeText(getActivity(),
                    "Sorry, an error happened in ContractHistoryActivityFragment (Module == null)",
                    Toast.LENGTH_SHORT).
                    show();
        }

        return data;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                contractHistoryList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(contractHistoryList);

                showOrHideNoContractsView(contractHistoryList.isEmpty());
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }
}