package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.home;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.expandableRecicler.ExpandableRecyclerAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletExpandableListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_android_api.ui.util.FermatDividerItemDecoration;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.common.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerWalletException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetCurrentIndexSummaryForStockCurrenciesException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.cbp_wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.MarketExchangeRatesPageAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.OpenNegotiationsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.navigationDrawer.NavigationViewAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;
import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;


/**
 * Fragment the show the list of open negotiations waiting for the broker and the customer un the Home activity
 *
 * @author Nelson Ramirez
 * @version 1.0
 * @since 20/10/2015
 */
public class OpenNegotiationsTabFragment extends FermatWalletExpandableListFragment<GrouperItem>
        implements FermatListItemListeners<CustomerBrokerNegotiationInformation> {

    // Constants
    private static final String WALLET_PUBLIC_KEY = "crypto_broker_wallet";

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<GrouperItem> openNegotiationList;
    private List<IndexInfoSummary> marketExchangeRateSummaryList;


    public static OpenNegotiationsTabFragment newInstance() {
        return new OpenNegotiationsTabFragment();
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
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

        openNegotiationList = (ArrayList) getMoreDataAsync(FermatRefreshTypes.NEW, 0);
        marketExchangeRateSummaryList = getMarketExchangeRateSummaryData();
    }


    @Override
    protected void initViews(View layout) {
        super.initViews(layout);

        Activity activity = getActivity();
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        configureActivityHeader(layoutInflater);
        configureToolbar();


        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(activity, R.drawable.cbw_divider_shape);
        recyclerView.addItemDecoration(itemDecoration);

        if (openNegotiationList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            View emptyListViewsContainer = layout.findViewById(R.id.empty);
            emptyListViewsContainer.setVisibility(View.VISIBLE);
        }

        try {
            addNavigationHeader(FragmentsCommons.setUpHeaderScreen(layoutInflater, getActivity(), null));
            NavigationViewAdapter adapter = new NavigationViewAdapter(activity, null);
            setNavigationDrawer(adapter);
        } catch (CantGetActiveLoginIdentityException e) {
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
        }
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        Menu menu = toolbar.getMenu();
        if (menu != null) menu.clear();
    }

    private void configureActivityHeader(LayoutInflater layoutInflater) {

        RelativeLayout toolbarHeader = getToolbarHeader();
        try {
            toolbarHeader.removeAllViews();
        } catch (Exception exception) {
            CommonLogger.exception(TAG, "Error removing all views from toolbarHeader ", exception);
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, exception);
        }
        toolbarHeader.setVisibility(View.VISIBLE);
        View container = layoutInflater.inflate(R.layout.cbw_header_layout, toolbarHeader, true);

        if (marketExchangeRateSummaryList.isEmpty()) {
            FermatTextView noMarketRateTextView = (FermatTextView) container.findViewById(R.id.cbw_no_market_rate);
            noMarketRateTextView.setVisibility(View.VISIBLE);
            View marketRateViewPagerContainer = container.findViewById(R.id.cbw_market_rate_view_pager_container);
            marketRateViewPagerContainer.setVisibility(View.VISIBLE);
        } else {
            ViewPager viewPager = (ViewPager) container.findViewById(R.id.cbw_exchange_rate_view_pager);
            viewPager.setOffscreenPageLimit(3);
            MarketExchangeRatesPageAdapter pageAdapter = new MarketExchangeRatesPageAdapter(getFragmentManager(), marketExchangeRateSummaryList);
            viewPager.setAdapter(pageAdapter);

            LinePageIndicator indicator = (LinePageIndicator) container.findViewById(R.id.cbw_exchange_rate_view_pager_indicator);
            indicator.setViewPager(viewPager);
        }

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

                grouperText = getActivity().getString(R.string.waiting_for_you);
                List<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
                waitingForBroker.addAll(cryptoBrokerWallet.getNegotiationsWaitingForBroker(0, 10));
                GrouperItem<CustomerBrokerNegotiationInformation> waitingForBrokerGrouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                data.add(waitingForBrokerGrouper);

                grouperText = getActivity().getString(R.string.waiting_for_the_customer);
                List<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();
                waitingForCustomer.addAll(cryptoBrokerWallet.getNegotiationsWaitingForCustomer(0, 10));
                GrouperItem<CustomerBrokerNegotiationInformation> waitingForCustomerGrouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
                data.add(waitingForCustomerGrouper);

            } catch (CantGetCryptoBrokerWalletException | CantGetNegotiationsWaitingForBrokerException | CantGetNegotiationsWaitingForCustomerException ex) {
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

    private List<IndexInfoSummary> getMarketExchangeRateSummaryData() {
        List<IndexInfoSummary> data = new ArrayList<>();

        if (moduleManager != null) {
            try {
                CryptoBrokerWallet wallet = moduleManager.getCryptoBrokerWallet(WALLET_PUBLIC_KEY);
                data.addAll(wallet.getCurrentIndexSummaryForStockCurrencies());

            } catch (CantGetCryptoBrokerWalletException | CantGetCurrentIndexSummaryForStockCurrenciesException ex) {
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
        walletSession.setData("negotiation_data", data);
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_OPEN_NEGOTIATION_DETAILS);
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

