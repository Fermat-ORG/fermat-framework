package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenNegotiationsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CBW_NEGOTIATION_UPDATE_VIEW;
import static com.bitdubai.fermat_cbp_api.all_definition.constants.CBPBroadcasterConstants.CBW_CONTRACT_UPDATE_VIEW;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 20/10/2015
 */
public class OpenNegotiationsTabFragment extends FermatWalletExpandableListFragment<GrouperItem, CryptoBrokerWalletSession, ResourceProviderManager>
        implements FermatListItemListeners<CustomerBrokerNegotiationInformation> {

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<GrouperItem> openNegotiationList;

    private  View emptyListViewsContainer;


    public static OpenNegotiationsTabFragment newInstance() {
        return new OpenNegotiationsTabFragment();
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
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openNegotiationList = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
    }


    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        Activity activity = getActivity();
        configureToolbar();

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(activity, R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);
        emptyListViewsContainer = layout.findViewById(R.id.empty);
        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }else {
            emptyListViewsContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    public ExpandableRecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new OpenNegotiationsExpandableAdapter(getActivity(), openNegotiationList);
            // setting up event listeners
            adapter.setChildItemFermatEventListeners(this);
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null)
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        return layoutManager;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.cbw_fragment_open_negotiations_and_contracts_tab;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.cbw_open_negotiations_and_contracts_recycler_view;
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
            GrouperItem<CustomerBrokerNegotiationInformation> grouper;
            List<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
            List<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();

            try {

                waitingForBroker.addAll(moduleManager.getNegotiationsWaitingForBroker(0, 10));
                waitingForCustomer.addAll(moduleManager.getNegotiationsWaitingForCustomer(0, 10));

                if (!waitingForBroker.isEmpty() || !waitingForCustomer.isEmpty()) {
                    grouperText = getActivity().getString(R.string.waiting_for_you);
                    grouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                    data.add(grouper);

                    grouperText = getActivity().getString(R.string.waiting_for_the_customer);
                    grouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
                    data.add(grouper);
                }

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                }
            }

        } else {
            Toast.makeText(getActivity(), "Sorry, an error happened OpenNegotiationsTabFragment (Module == null)", Toast.LENGTH_SHORT).show();
        }

        return data;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    @Override
    public void onItemClickListener(CustomerBrokerNegotiationInformation data, int position) {
        appSession.setData(CryptoBrokerWalletSession.NEGOTIATION_DATA, data);
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS, appSession.getAppPublicKey());
        /*
        if(data.getStatus() == NegotiationStatus.SENT_TO_BROKER || data.getStatus() == NegotiationStatus.WAITING_FOR_BROKER){
            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS, appSession.getAppPublicKey());
        }else{
            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_CLOSE_NEGOTIATION_DETAILS_CLOSE_CONTRACT, appSession.getAppPublicKey());
        }
        */
    }

    @Override
    public void onLongItemClickListener(CustomerBrokerNegotiationInformation data, int position) {
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                openNegotiationList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(openNegotiationList);
            }
        }
        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }else {
            emptyListViewsContainer.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
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
            case CBW_NEGOTIATION_UPDATE_VIEW:
                onRefresh();
                break;
            case CBW_CONTRACT_UPDATE_VIEW:
                onRefresh();
                break;
        }

    }
}

