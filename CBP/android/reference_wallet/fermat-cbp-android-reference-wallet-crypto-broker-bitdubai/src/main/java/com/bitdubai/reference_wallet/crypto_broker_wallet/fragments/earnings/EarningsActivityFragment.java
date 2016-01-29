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
import android.widget.Spinner;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsOverviewAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningTestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.TestData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EarningsActivityFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, WalletResourcesProviderManager> {

    // Constants
    private static final String TAG = "EarningsActivity";

    // Fermat Managers
    private ErrorManager errorManager;
    private CryptoBrokerWalletManager walletManager;

    private List<Currency> currencies;
    private RecyclerView recyclerView;
    private Spinner currencySpinner;
    private FermatTextView currentValue;


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

            currencies = getCurrencies();

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
        recyclerView = (RecyclerView) layout.findViewById(R.id.earning_overview_recycler_view);
        currencySpinner = (Spinner) layout.findViewById(R.id.cbw_earning_currency_spinner);

        configureToolbar();
        setDataInViews();

        return layout;
    }

    private void setDataInViews() {
        if (currencies != null && !currencies.isEmpty()) {

            final List<EarningTestData> dataSet = TestData.getEarnings(currencies.get(0));
            final EarningsOverviewAdapter adapter = new EarningsOverviewAdapter(getActivity(), dataSet);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            List<String> arrayList = getFormattedCurrencies(currencies);
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.cbw_spinner_item, arrayList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            currencySpinner.setAdapter(spinnerAdapter);
            currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Currency selectedCurrency = currencies.get(position);

                    List<EarningTestData> earnings = TestData.getEarnings(selectedCurrency);
                    adapter.changeDataSet(earnings);

                    double currentEarning = TestData.getCurrentEarning(selectedCurrency);
                    String currentEarningFormatted = NumberFormat.getInstance().format(currentEarning);
                    currentValue.setText(String.format("%s %s", currentEarningFormatted, selectedCurrency.getCode()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
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

    private List<Currency> getCurrencies() {
        ArrayList<Currency> currencies = new ArrayList<>();
        currencies.add(CryptoCurrency.BITCOIN);
        currencies.add(FiatCurrency.US_DOLLAR);
        currencies.add(FiatCurrency.VENEZUELAN_BOLIVAR);

        return currencies;
    }

    private List<String> getFormattedCurrencies(List<Currency> currencies) {
        ArrayList<String> data = new ArrayList<>();
        for (Currency currency : currencies) {
            data.add(currency.getFriendlyName() + " (" + currency.getCode() + ")");
        }

        return data;
    }
}
