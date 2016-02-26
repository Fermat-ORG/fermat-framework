package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.earnings;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsPair;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsCurrencyPairsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsOverviewAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningTestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsActivityFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, WalletResourcesProviderManager>
        implements FermatListItemListeners<EarningsPair>, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    // Constants
    private static final String TAG = "EarningsActivity";

    // Data
    private List<EarningsPair> earningsPairs;
    private Currency selectedCurrency;

    // Fermat Managers
    private ErrorManager errorManager;
    private CryptoBrokerWalletManager walletManager;

    // UI
    private RecyclerView earningsOverviewRecyclerView;
    private RecyclerView currencyPairsRecyclerView;
    private EarningsOverviewAdapter earningsOverviewAdapter;
    private EarningsCurrencyPairsAdapter currencyPairsAdapter;
    private FermatTextView currentValue;
    private FermatTextView previousValue;
    private ImageView sortByButton;
    private PopupMenu popupMenu;
    private FermatTextView timeFieldTextView;


    public static EarningsActivityFragment newInstance() {
        return new EarningsActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoBrokerWalletModuleManager moduleManager = appSession.getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            earningsPairs = TestData.getEarningsPairs();

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cbw_fragment_earnings_activity, container, false);

        configureToolbar();

        currentValue = (FermatTextView) layout.findViewById(R.id.cbw_current_earning_value);
        previousValue = (FermatTextView) layout.findViewById(R.id.cbw_previous_earning_value);
        timeFieldTextView = (FermatTextView) layout.findViewById(R.id.cbw_earning_time_field);
        timeFieldTextView.setText("Month");

        sortByButton = (ImageView) layout.findViewById(R.id.cbw_earnings_sort_by_button);
        sortByButton.setOnClickListener(this);

        currencyPairsAdapter = new EarningsCurrencyPairsAdapter(getActivity(), earningsPairs);
        currencyPairsAdapter.setFermatListEventListener(this);
        currencyPairsRecyclerView = (RecyclerView) layout.findViewById(R.id.cbw_earning_currency_pairs_recycler_view);
        currencyPairsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        currencyPairsRecyclerView.setAdapter(currencyPairsAdapter);

        final EarningsPair earningsPair = earningsPairs.get(0);
        final Currency earningCurrency = earningsPair.getEarningCurrency();
        final List<EarningTestData> dataSet = TestData.getEarnings(earningCurrency, Calendar.MONTH);
        earningsOverviewAdapter = new EarningsOverviewAdapter(getActivity(), dataSet);
        earningsOverviewRecyclerView = (RecyclerView) layout.findViewById(R.id.earning_overview_recycler_view);
        earningsOverviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        earningsOverviewRecyclerView.setAdapter(earningsOverviewAdapter);

        onItemClickListener(earningsPair, 0);

        return layout;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cbw_earnings_sort_by_button) {
            if (popupMenu == null) {
                popupMenu = new PopupMenu(getActivity(), sortByButton);
                popupMenu.inflate(R.menu.cbw_earning_sort_by_menu);
                popupMenu.setOnMenuItemClickListener(this);
            }

            popupMenu.show();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        if (menuItem.getItemId() == R.id.cbw_sort_by_month) {
            int timeField = Calendar.MONTH;
            List<EarningTestData> earnings = TestData.getEarnings(selectedCurrency, timeField);
            earningsOverviewAdapter.setTimeFrecuency(timeField);
            earningsOverviewAdapter.changeDataSet(earnings);
            timeFieldTextView.setText("Month");
            return true;
        }

        if (menuItem.getItemId() == R.id.cbw_sort_by_day) {
            int timeField = Calendar.DATE;
            List<EarningTestData> earnings = TestData.getEarnings(selectedCurrency, timeField);
            earningsOverviewAdapter.setTimeFrecuency(timeField);
            earningsOverviewAdapter.changeDataSet(earnings);
            timeFieldTextView.setText("Day");
            return true;
        }

        return false;
    }

    @Override
    public void onItemClickListener(EarningsPair data, int position) {
        selectedCurrency = data.getEarningCurrency();

        List<EarningTestData> earnings = TestData.getEarnings(selectedCurrency, Calendar.MONTH);
        earningsOverviewAdapter.changeDataSet(earnings);

        double currentEarning = TestData.getCurrentEarning(selectedCurrency);
        String currentEarningFormatted = NumberFormat.getInstance().format(currentEarning);
        currentValue.setText(String.format("%s %s", currentEarningFormatted, selectedCurrency.getCode()));

        if (!earnings.isEmpty()) {
            double previousEarningValue = earnings.get(0).getEarningValue();

            double previousEarning = getEarningDifference(currentEarning, previousEarningValue);
            String previousEarningFormatted = NumberFormat.getInstance().format(previousEarning);
            String format = previousEarning <= 0 ? "%s %s" : "+ %s %s";
            previousValue.setText(String.format(format, previousEarningFormatted, selectedCurrency.getCode()));
        }

        currencyPairsAdapter.setSelectedItem(position);
    }

    @Override
    public void onLongItemClickListener(EarningsPair data, int position) {
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

    private double getEarningDifference(double currentEarningValue, double previousEarningValue) {
        BigDecimal earningValueBigDec = BigDecimal.valueOf(currentEarningValue);
        BigDecimal previousEarningValueBigDec = BigDecimal.valueOf(previousEarningValue);

        return earningValueBigDec.subtract(previousEarningValueBigDec).doubleValue();
    }
}
