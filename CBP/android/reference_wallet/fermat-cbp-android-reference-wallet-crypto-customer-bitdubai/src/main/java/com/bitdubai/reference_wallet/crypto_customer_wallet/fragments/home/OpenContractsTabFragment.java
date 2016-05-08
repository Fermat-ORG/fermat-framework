package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home;

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

import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.OpenContractsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_NEGOTIATION_UPDATE_VIEW;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_UPDATE_VIEW;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenContractsTabFragment extends FermatWalletExpandableListFragment<GrouperItem,CryptoCustomerWalletSession,ResourceProviderManager>
        implements FermatListItemListeners<ContractBasicInformation> {

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private ArrayList<GrouperItem<ContractBasicInformation>> openContractList;

    private View emptyListViewsContainer;

    public static OpenContractsTabFragment newInstance() {
        return new OpenContractsTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_CUSTOMER_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openContractList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.ccw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);
        emptyListViewsContainer = layout.findViewById(R.id.empty);
        if (openContractList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyListViewsContainer.setVisibility(View.GONE);
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.ccw_menu_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_start_negotiation) {
            changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST, appSession.getAppPublicKey());
        }

        return true;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new OpenContractsExpandableAdapter(getActivity(), openContractList);
            // setting up event listeners
            adapter.setChildItemFermatEventListeners(this);
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
        return R.layout.ccw_fragment_open_contracts_tab;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.ccw_open_contracts_recycler_view;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    public List<GrouperItem> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        ArrayList<GrouperItem> data = new ArrayList<>();
        String grouperText;

        if (moduleManager != null) {
            try {
                GrouperItem<ContractBasicInformation> grouper;

                List<ContractBasicInformation> waitingForCustomer = new ArrayList<>();
                waitingForCustomer.addAll(moduleManager.getContractsWaitingForCustomer(10, 0));

                List<ContractBasicInformation> waitingForBroker = new ArrayList<>();
                waitingForBroker.addAll(moduleManager.getContractsWaitingForBroker(10, 0));

                if(!waitingForCustomer.isEmpty() || !waitingForBroker.isEmpty()){
                    grouperText = getActivity().getString(R.string.waiting_for_you);
                    grouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
                    data.add(grouper);

                    grouperText = getActivity().getString(R.string.waiting_for_broker);
                    grouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                    data.add(grouper);
                }

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                }
            }
        } else {
            Toast.makeText(getActivity(), "Sorry, an error happened in OpenContractsTabFragment (Module == null)", Toast.LENGTH_SHORT).show();
        }

        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(ContractBasicInformation data, int position) {
        appSession.setData("contract_data", data);
        //changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_CONTRACT_DETAILS, appSession.getAppPublicKey());
        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_CONTRACT_DETAILS, appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(ContractBasicInformation data, int position) {
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                openContractList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openContractList);
            }
        }
        if (openContractList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            emptyListViewsContainer.setVisibility(View.GONE);
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

    @Override
    public void onUpdateViewOnUIThread(String code) {
        switch (code) {
            case CCW_CONTRACT_UPDATE_VIEW:
                onRefresh();
                break;
            case CCW_NEGOTIATION_UPDATE_VIEW:
                onRefresh();
                break;
        }
    }
}
