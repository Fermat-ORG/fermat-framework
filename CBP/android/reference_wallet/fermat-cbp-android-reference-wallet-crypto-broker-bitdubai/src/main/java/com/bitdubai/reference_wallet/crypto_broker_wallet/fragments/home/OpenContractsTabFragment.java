package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetContractsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenContractsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenContractsTabFragment extends FermatWalletExpandableListFragment<GrouperItem>
        implements FermatListItemListeners<ContractBasicInformation> {

    // Constants
    private static final String WALLET_PUBLIC_KEY = "crypto_broker_wallet";

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private ArrayList<GrouperItem<ContractBasicInformation>> openContractList;


    public static OpenContractsTabFragment newInstance() {
        return new OpenContractsTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoBrokerWalletSession) walletSession).getModuleManager();
            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openContractList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureActionBar(getActivity());

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openContractList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            View emptyListViewsContainer = layout.findViewById(R.id.empty);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }
    }

    private void configureActionBar(Activity activity) {

        if (activity instanceof AppCompatActivity) {
            android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
            if (actionBar != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
                else
                    actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));
            }
        } else {
            ActionBar actionBar = activity.getActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));
            }
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
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
        return R.layout.fragment_open_contracts_tab;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.open_contracts_recycler_view;
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
                CryptoBrokerWallet cryptoBrokerWallet = moduleManager.getCryptoBrokerWallet(WALLET_PUBLIC_KEY);
                GrouperItem<ContractBasicInformation> grouper;

                grouperText = getActivity().getString(R.string.waiting_for_the_customer);
                List<ContractBasicInformation> waitingForCustomer = new ArrayList<>();
                waitingForCustomer.addAll(cryptoBrokerWallet.getContractsWaitingForCustomer(10, 0));
                grouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
                data.add(grouper);

                grouperText = getActivity().getString(R.string.waiting_for_you);
                List<ContractBasicInformation> waitingForBroker = new ArrayList<>();
                waitingForBroker.addAll(cryptoBrokerWallet.getContractsWaitingForBroker(10, 0));
                grouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                data.add(grouper);

            } catch (CantGetContractsWaitingForCustomerException | CantGetContractsWaitingForBrokerException | CantGetCryptoBrokerWalletException ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(
                            Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                            ex);
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
        //TODO abrir actividad de detalle de contrato abierto
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

