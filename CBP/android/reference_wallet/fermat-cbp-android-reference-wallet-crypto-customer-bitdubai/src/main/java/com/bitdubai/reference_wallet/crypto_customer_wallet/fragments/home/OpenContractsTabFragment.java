package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.ContractBasicInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.OpenContractsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_CONTRACT_UPDATE_VIEW;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CCW_NEGOTIATION_UPDATE_VIEW;


/**
 * A simple {@link Fragment} subclass.
 */
public class OpenContractsTabFragment extends FermatWalletExpandableListFragment<GrouperItem, ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<ContractBasicInformation> {

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<GrouperItem<ContractBasicInformation>> openContractList;

    // Android Stuffs
    private View emptyListViewsContainer;
    private FermatApplicationCaller applicationCaller;
    private OpenContractBroadcastReceiver broadcastReceiver;


    public static OpenContractsTabFragment newInstance() {
        return new OpenContractsTabFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatIntentFilter fermatIntentFilter = new FermatIntentFilter(BroadcasterType.UPDATE_VIEW);
        broadcastReceiver = new OpenContractBroadcastReceiver();
        registerReceiver(fermatIntentFilter, broadcastReceiver);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            applicationCaller = ((FermatApplicationSession) getActivity().getApplicationContext()).getApplicationManager();

        } catch (Exception ex) {
            errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                    UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        onRefresh();
    }

    @Override
    public void onDestroy() {
        if (broadcastReceiver != null)
            unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onFragmentFocus() {
        super.onFragmentFocus();
        if (isAttached) onRefresh();
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        configureToolbar();

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(getActivity(), R.drawable.ccw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);
        emptyListViewsContainer = layout.findViewById(R.id.empty);
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case FragmentsCommons.OPEN_CUSTOMER_IDENTITY_APP_OPTION_MENU_ID:
                    applicationCaller.openFermatApp(SubAppsPublicKeys.CBP_CUSTOMER_IDENTITY.getCode());
                    break;
                case FragmentsCommons.OPEN_BROKER_COMMUNITY_APP_OPTION_MENU_ID:
                    applicationCaller.openFermatApp(SubAppsPublicKeys.CBP_BROKER_COMMUNITY.getCode());
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error", LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("unchecked")
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

                if (!waitingForCustomer.isEmpty() || !waitingForBroker.isEmpty()) {
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
    @SuppressWarnings("unchecked")
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

        showOrHideEmptyView();
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    private void showOrHideEmptyView() {
        if (openContractList == null || openContractList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyListViewsContainer.setVisibility(View.GONE);
        }
    }

    private class OpenContractBroadcastReceiver extends FermatBroadcastReceiver {

        @Override
        public void onReceive(FermatBundle fermatBundle) {
            try {
                String code = fermatBundle.getString(Broadcaster.NOTIFICATION_TYPE);

                switch (code) {
                    case CCW_NEGOTIATION_UPDATE_VIEW:
                        if (isAttached) onRefresh();
                        break;
                    case CCW_CONTRACT_UPDATE_VIEW:
                        if (isAttached) onRefresh();
                        break;
                }

            } catch (ClassCastException e) {
                appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
    }
}