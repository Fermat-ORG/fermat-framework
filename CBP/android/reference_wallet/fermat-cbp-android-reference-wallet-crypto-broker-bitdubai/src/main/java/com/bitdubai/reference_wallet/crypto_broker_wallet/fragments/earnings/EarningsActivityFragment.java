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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsActivityFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, WalletResourcesProviderManager> implements FermatListItemListeners<EarningsPair> {

    // Constants
    private static final String TAG = "EarningsActivity";

    // Data
    private List<EarningsPair> earningsPairs;

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

        currentValue = (FermatTextView) layout.findViewById(R.id.cbw_current_earning_value);
        previousValue = (FermatTextView) layout.findViewById(R.id.cbw_previous_earning_value);

        earningsOverviewRecyclerView = (RecyclerView) layout.findViewById(R.id.earning_overview_recycler_view);
        earningsOverviewRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        currencyPairsRecyclerView = (RecyclerView) layout.findViewById(R.id.cbw_earning_currency_pairs_recycler_view);
        currencyPairsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        configureToolbar();
        setDataInViews();

        return layout;
    }

    @Override
    public void onItemClickListener(EarningsPair data, int position) {
        Currency selectedCurrency = data.getSelectedEarningCurrency();

        List<EarningTestData> earnings = TestData.getEarnings(selectedCurrency, Calendar.MONTH);
        earningsOverviewAdapter.changeDataSet(earnings);

        double currentEarning = TestData.getCurrentEarning(selectedCurrency);
        String currentEarningFormatted = NumberFormat.getInstance().format(currentEarning);
        currentValue.setText(String.format("%s %s", currentEarningFormatted, selectedCurrency.getCode()));

        if (!earnings.isEmpty()) {
            double previousEarningValue = earnings.get(0).getEarningValue();

            double previousEarning = getEarningDifference(currentEarning, previousEarningValue);
            String previousEarningFormatted = NumberFormat.getInstance().format(previousEarning);
            previousValue.setText(String.format("%s %s", previousEarningFormatted, selectedCurrency.getCode()));
        }

        currencyPairsAdapter.setSelectedItem(position);
    }

    @Override
    public void onLongItemClickListener(EarningsPair data, int position) {

    }

    private void setDataInViews() {
        currencyPairsAdapter = new EarningsCurrencyPairsAdapter(getActivity(), earningsPairs);
        currencyPairsAdapter.setFermatListEventListener(this);
        currencyPairsRecyclerView.setAdapter(currencyPairsAdapter);

        final EarningsPair earningsPair = earningsPairs.get(0);
        final Currency earningCurrency = earningsPair.getSelectedEarningCurrency();
        final List<EarningTestData> dataSet = TestData.getEarnings(earningCurrency, Calendar.MONTH);
        earningsOverviewAdapter = new EarningsOverviewAdapter(getActivity(), dataSet);
        earningsOverviewRecyclerView.setAdapter(earningsOverviewAdapter);

        onItemClickListener(earningsPair, 0);

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
