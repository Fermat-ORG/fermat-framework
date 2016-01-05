package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_cer_api.layer.search.exceptions.CantGetProviderException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.ProvidersAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetProvidersFragment extends AbstractFermatFragment
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<CurrencyExchangeRateProviderManager>, AdapterView.OnItemSelectedListener {

    // Constants
    private static final String TAG = "WizardPageSetEarning";

    // Data
    private List<CurrencyExchangeRateProviderManager> selectedProviders;
    private List<Currency> currencies;
    private Currency currencyFrom;
    private Currency currencyTo;

    // UI
    private RecyclerView recyclerView;
    private FermatTextView emptyView;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;
    private ProvidersAdapter adapter;


    public static WizardPageSetProvidersFragment newInstance() {
        return new WizardPageSetProvidersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedProviders = new ArrayList<>();
        currencies = getCurrenciesList();

        try {
            CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View layout = inflater.inflate(R.layout.cbw_wizard_step_set_providers, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.cbw_selected_providers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new ProvidersAdapter(getActivity(), selectedProviders);
        adapter.setDeleteButtonListener(this);
        recyclerView.setAdapter(adapter);

        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_providers_empty_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.cbw_spinner_item, getFormattedCurrencies(currencies));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner currencyFromSpinner = (Spinner) layout.findViewById(R.id.currency_from_spinner);
        currencyFromSpinner.setOnItemSelectedListener(this);
        currencyFromSpinner.setAdapter(adapter);

        final Spinner currencyToSpinner = (Spinner) layout.findViewById(R.id.currency_to_spinner);
        currencyToSpinner.setOnItemSelectedListener(this);
        currencyToSpinner.setAdapter(adapter);

        final View selectProvidersButton = layout.findViewById(R.id.cbw_select_providers_button);
        selectProvidersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProvidersDialog();
            }
        });

        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
            }
        });

        return layout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.currency_from_spinner) {
            currencyFrom = currencies.get(position);

        } else if (parent.getId() == R.id.currency_to_spinner) {
            currencyTo = currencies.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showProvidersDialog() {

        try {
            List<CurrencyExchangeRateProviderManager> providers = new ArrayList<>();

            Map<String, CurrencyExchangeRateProviderManager> providersMap = walletManager.getProviderReferencesFromCurrencyPair(currencyFrom, currencyTo);
            if (providersMap != null)
                providers.addAll(providersMap.values());


            final SimpleListDialogFragment<CurrencyExchangeRateProviderManager> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Provider", providers);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<CurrencyExchangeRateProviderManager>() {
                @Override
                public void onItemSelected(CurrencyExchangeRateProviderManager selectedItem) {
                    if (!containProvider(selectedItem)) {
                        selectedProviders.add(selectedItem);
                        adapter.changeDataSet(selectedProviders);
                        showOrHideNoProvidersView();
                    }
                }
            });

            dialogFragment.show(getFragmentManager(), "ProvidersDialog");

        } catch (CantGetProviderException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }


    }

    private void saveSettingAndGoNextStep() {

        if (selectedProviders.isEmpty()) {
            Toast.makeText(getActivity(), R.string.cbw_select_stock_wallets_warning_msg, Toast.LENGTH_SHORT).show();
            // TODO descomentar cuando pueda agregar un proveedor en la lista
            // return;
        }

        try {
            for (CurrencyExchangeRateProviderManager provider : selectedProviders) {
                CryptoBrokerWalletProviderSetting setting = walletManager.newEmptyCryptoBrokerWalletProviderSetting();
                setting.setBrokerPublicKey(appSession.getAppPublicKey());
                setting.setDescription(provider.getProviderName());
                setting.setId(provider.getProviderId());
                setting.setPlugin(provider.getProviderId());

                walletManager.saveCryptoBrokerWalletProviderSetting(setting, appSession.getAppPublicKey());
            }

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS, appSession.getAppPublicKey());
    }

    @Override
    public void deleteButtonClicked(CurrencyExchangeRateProviderManager data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_wallet_dialog_title).setMessage(R.string.cbw_delete_wallet_dialog_msg);
        builder.setPositiveButton(R.string.cbw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedProviders.remove(position);
                adapter.changeDataSet(selectedProviders);
                showOrHideNoProvidersView();
            }
        });
        builder.setNegativeButton(R.string.cbw_cancel_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }

    private void showOrHideNoProvidersView() {
        if (selectedProviders.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private boolean containProvider(CurrencyExchangeRateProviderManager selectedProvider) {
        if (selectedProviders.isEmpty())
            return false;

        try {
            for (CurrencyExchangeRateProviderManager provider : selectedProviders) {
                UUID providerId = provider.getProviderId();
                UUID selectedProviderId = selectedProvider.getProviderId();

                if (providerId.equals(selectedProviderId))
                    return true;
            }
        } catch (CantGetProviderInfoException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return false;
    }

    private List<Currency> getCurrenciesList() {
        ArrayList<Currency> data = new ArrayList<>();

        FiatCurrency[] fiatCurrencies = FiatCurrency.values();
        Collections.addAll(data, fiatCurrencies);

        CryptoCurrency[] cryptoCurrencies = CryptoCurrency.values();
        Collections.addAll(data, cryptoCurrencies);

        return data;
    }

    private List<String> getFormattedCurrencies(List<Currency> currencies) {
        ArrayList<String> data = new ArrayList<>();
        for (Currency currency : currencies) {
            data.add(currency.getFriendlyName() + " (" + currency.getCode() + ")");
        }

        return data;
    }
}
