package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.broker_list;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.BrokerIdentityBusinessInfo;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.BrokerListAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.session.CryptoCustomerWalletSession;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BrokerListActivityFragment extends FermatWalletListFragment<BrokerIdentityBusinessInfo>
        implements FermatListItemListeners<BrokerIdentityBusinessInfo> {

    // Constants
    private static final String TAG = "BrokerListActivityFragment";

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    // Data
    private List<BrokerIdentityBusinessInfo> brokerList;

    //UI
    private View noBrokersView;


    public static BrokerListActivityFragment newInstance() {
        return new BrokerListActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoCustomerWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            brokerList = getMoreDataAsync(FermatRefreshTypes.NEW, 0);
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

        noBrokersView = layout.findViewById(R.id.ccw_no_brokers);

        showOrHideNoBrokersView(brokerList.isEmpty());
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            adapter = new BrokerListAdapter(getActivity(), brokerList);
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
        return R.layout.ccw_fragment_broker_list_activity;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.ccw_broker_list_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        Drawable drawable;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            drawable = getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null);
        else
            drawable = getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors);

        toolbar.setBackground(drawable);

        if (toolbar.getMenu() != null) {
            toolbar.getMenu().clear();
        }
    }

    @Override
    public void onItemClickListener(BrokerIdentityBusinessInfo data, int position) {
        final CryptoCustomerWalletSession walletSession = (CryptoCustomerWalletSession) this.appSession;
        walletSession.setCurrencyToBuy(data.getMerchandise());
        walletSession.setSelectedBrokerIdentity(data);
        walletSession.setQuotes(data.getQuotes());
        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_START_NEGOTIATION, this.appSession.getAppPublicKey());
    }

    @Override
    public void onLongItemClickListener(BrokerIdentityBusinessInfo data, int position) {
    }

    @Override
    public List<BrokerIdentityBusinessInfo> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<BrokerIdentityBusinessInfo> data = new ArrayList<>();

        if (moduleManager != null) {
            try {
                //data.addAll(TestData.getBrokerListTestData());

                data.addAll(moduleManager.getListOfConnectedBrokersAndTheirMerchandises());

            } catch (Exception ex) {
                CommonLogger.exception(TAG, ex.getMessage(), ex);
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        } else {
            Toast.makeText(getActivity(),"Sorry, an error happened in BrokerListActivityFragment (Module == null)", Toast.LENGTH_SHORT).show();
        }

        return data;
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                brokerList = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(brokerList);

                showOrHideNoBrokersView(brokerList.isEmpty());
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

    private void showOrHideNoBrokersView(boolean show) {
        if (show) {
            recyclerView.setVisibility(View.GONE);
            noBrokersView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            noBrokersView.setVisibility(View.GONE);
        }
    }
}
