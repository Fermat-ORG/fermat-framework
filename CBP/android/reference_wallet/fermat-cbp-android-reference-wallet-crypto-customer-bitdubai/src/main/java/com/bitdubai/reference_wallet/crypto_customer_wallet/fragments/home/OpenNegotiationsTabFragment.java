package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.home;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForBrokerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.exceptions.CantGetNegotiationsWaitingForCustomerException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.CustomerBrokerNegotiationInformation;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.common.interfaces.IndexInfoSummary;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCryptoCustomerWalletException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.exceptions.CantGetCurrentIndexSummaryForCurrenciesOfInterestException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.MarketExchangeRatesPageAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.OpenNegotiationsExpandableAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.GrouperItem;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.navigationDrawer.NavigationViewAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.FragmentsCommons;
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

    private static final String WALLET_PUBLIC_KEY = "crypto_customer_wallet";
    private static final String TAG = "ContractsHistoryActivityFragment";

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
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
            moduleManager = ((CryptoCustomerWalletSession) walletSession).getModuleManager();
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

        RecyclerView.ItemDecoration itemDecoration = new FermatDividerItemDecoration(activity, R.drawable.ccw_divider_shape);
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
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
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
        View container = layoutInflater.inflate(R.layout.ccw_header_layout, toolbarHeader, true);

        if (marketExchangeRateSummaryList.isEmpty()) {
            FermatTextView noMarketRateTextView = (FermatTextView) container.findViewById(R.id.ccw_no_market_rate);
            noMarketRateTextView.setVisibility(View.VISIBLE);
            View marketRateViewPagerContainer = container.findViewById(R.id.ccw_market_rate_view_pager_container);
            marketRateViewPagerContainer.setVisibility(View.VISIBLE);
        } else {
            ViewPager viewPager = (ViewPager) container.findViewById(R.id.ccw_exchange_rate_view_pager);
            viewPager.setOffscreenPageLimit(3);
            MarketExchangeRatesPageAdapter pageAdapter = new MarketExchangeRatesPageAdapter(getFragmentManager(), marketExchangeRateSummaryList);
            viewPager.setAdapter(pageAdapter);

            LinePageIndicator indicator = (LinePageIndicator) container.findViewById(R.id.ccw_exchange_rate_view_pager_indicator);
            indicator.setViewPager(viewPager);
        }

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
            changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_BROKER_LIST,walletSession.getAppPublicKey());
        }

        return true;
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
        return R.layout.ccw_fragment_open_negotiations_tab;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.ccw_open_negotiations_recycler_view;
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
                CryptoCustomerWallet cryptoCustomerWallet = moduleManager.getCryptoCustomerWallet(WALLET_PUBLIC_KEY);
                GrouperItem<CustomerBrokerNegotiationInformation> grouper;

                grouperText = getActivity().getString(R.string.waiting_for_you);
                List<CustomerBrokerNegotiationInformation> waitingForCustomer = new ArrayList<>();
                waitingForCustomer.addAll(cryptoCustomerWallet.getNegotiationsWaitingForCustomer(0, 10));
                grouper = new GrouperItem<>(grouperText, waitingForCustomer, true);
                data.add(grouper);

                grouperText = getActivity().getString(R.string.waiting_for_broker);
                List<CustomerBrokerNegotiationInformation> waitingForBroker = new ArrayList<>();
                waitingForBroker.addAll(cryptoCustomerWallet.getNegotiationsWaitingForBroker(0, 10));
                grouper = new GrouperItem<>(grouperText, waitingForBroker, true);
                data.add(grouper);

            } catch (CantGetCryptoCustomerWalletException | CantGetNegotiationsWaitingForBrokerException | CantGetNegotiationsWaitingForCustomerException ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
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
                CryptoCustomerWallet wallet = moduleManager.getCryptoCustomerWallet(WALLET_PUBLIC_KEY);
                data.addAll(wallet.getCurrentIndexSummaryForCurrenciesOfInterest());

            } catch (CantGetCryptoCustomerWalletException | CantGetCurrentIndexSummaryForCurrenciesOfInterestException ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null) {
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
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
        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_OPEN_NEGOTIATION_DETAILS,walletSession.getAppPublicKey());
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

