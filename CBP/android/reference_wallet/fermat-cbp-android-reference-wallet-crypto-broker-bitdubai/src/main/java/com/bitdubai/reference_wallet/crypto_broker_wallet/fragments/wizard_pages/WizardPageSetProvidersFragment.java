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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletProviderSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CurrencyPairAndProvider;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cer_api.all_definition.interfaces.CurrencyPair;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.ProvidersAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetProvidersFragment extends AbstractFermatFragment
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<CurrencyPairAndProvider>, AdapterView.OnItemSelectedListener {

    // Constants
    private static final String TAG = "WizardPageSetEarning";

    // Data
    private List<CurrencyPairAndProvider> selectedProviders;
    private List<Currency> currencies;

    // UI
    boolean hideHelperDialogs = false;
    private RecyclerView recyclerView;
    private FermatTextView emptyView;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
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
            moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();

            //Delete potential previous configurations made by this wizard page
            //So that they can be reconfigured cleanly
            moduleManager.clearCryptoBrokerWalletProviderSetting(appSession.getAppPublicKey());

            //If PRESENTATION_SCREEN_ENABLED == true, then user does not want to see more help dialogs inside the wizard
            Object aux = appSession.getData(PresentationDialog.PRESENTATION_SCREEN_ENABLED);
            if(aux != null && aux instanceof Boolean)
                hideHelperDialogs = (boolean) aux;

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

//        final Spinner currencyFromSpinner = (Spinner) layout.findViewById(R.id.currency_from_spinner);
//        currencyFromSpinner.setOnItemSelectedListener(this);
//        currencyFromSpinner.setAdapter(adapter);

//        final Spinner currencyToSpinner = (Spinner) layout.findViewById(R.id.currency_to_spinner);
//        currencyToSpinner.setOnItemSelectedListener(this);
//        currencyToSpinner.setAdapter(adapter);

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

        if(!hideHelperDialogs) {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.banner_crypto_broker)
                    .setIconRes(R.drawable.crypto_broker)
                    .setSubTitle(R.string.cbw_wizard_providers_dialog_sub_title)
                    .setBody(R.string.cbw_wizard_providers_dialog_body)
                    .setCheckboxText(R.string.cbw_wizard_not_show_text)
                    .build();
            presentationDialog.show();
        }

        return layout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        if (parent.getId() == R.id.currency_from_spinner) {
//            currencyFrom = currencies.get(position);
//
//        } else if (parent.getId() == R.id.currency_to_spinner) {
//            currencyTo = currencies.get(position);
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showProvidersDialog() {

        try {

            List<String> temp = new ArrayList<>();
            String tempS;

            List<CurrencyPairAndProvider> providers = new ArrayList<>();
            Map<String, CurrencyPair> map = moduleManager.getWalletProviderAssociatedCurrencyPairs(null, appSession.getAppPublicKey());

            for (Map.Entry<String, CurrencyPair> e : map.entrySet()) {

                Currency currencyFrom = e.getValue().getFrom();
                Currency currencyTo = e.getValue().getTo();

                Collection<CurrencyPairAndProvider> providerManagers = moduleManager.getProviderReferencesFromCurrencyPair(currencyFrom, currencyTo);
                if (providerManagers != null){
                    for (CurrencyPairAndProvider providerManager : providerManagers) {

                        tempS = currencyFrom.getCode()+" "+currencyTo.getCode()+" "+providerManager.getProviderName();

                        if (!temp.contains(tempS)) {
                            temp.add(currencyFrom.getCode()+" "+currencyTo.getCode()+" "+providerManager.getProviderName());
                            providers.add(providerManager);
                        }
                    }
                }

            }

            final SimpleListDialogFragment<CurrencyPairAndProvider> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Provider", providers);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<CurrencyPairAndProvider>() {
                @Override
                public void onItemSelected(CurrencyPairAndProvider selectedItem) {
                    if (!containProvider(selectedItem)) {
                        selectedProviders.add(selectedItem);
                        adapter.changeDataSet(selectedProviders);
                        showOrHideNoProvidersView();
                    }
                }
            });

            dialogFragment.show(getFragmentManager(), "ProvidersDialog");

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    private void saveSettingAndGoNextStep() {

        if (selectedProviders.isEmpty()) {
            Toast.makeText(getActivity(), R.string.cbw_select_providers_warning_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            for (CurrencyPairAndProvider currencyPairAndProvider : selectedProviders) {
                CryptoBrokerWalletProviderSetting setting = moduleManager.newEmptyCryptoBrokerWalletProviderSetting();
                setting.setBrokerPublicKey(appSession.getAppPublicKey());

                setting.setDescription(currencyPairAndProvider.getProviderName());
                setting.setId(currencyPairAndProvider.getProviderId());
                setting.setPlugin(currencyPairAndProvider.getProviderId());
                setting.setCurrencyFrom(currencyPairAndProvider.getCurrencyFrom().getCode());
                setting.setCurrencyTo(currencyPairAndProvider.getCurrencyTo().getCode());

                moduleManager.saveCryptoBrokerWalletProviderSetting(setting, appSession.getAppPublicKey());
            }

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        // TODO
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_LOCATIONS, appSession.getAppPublicKey());
    }

    @Override
    public void deleteButtonClicked(CurrencyPairAndProvider data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_provider_dialog_title).setMessage(R.string.cbw_delete_provider_dialog_msg);
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

    private boolean containProvider(CurrencyPairAndProvider selectedProvider) {
        if (selectedProviders.isEmpty())
            return false;

        for (CurrencyPairAndProvider provider : selectedProviders) {
            UUID providerId = provider.getProviderId();
            UUID selectedProviderId = selectedProvider.getProviderId();

            Currency providerFrom = provider.getCurrencyFrom();
            Currency providerTo = provider.getCurrencyTo();

            Currency SelectedFrom = selectedProvider.getCurrencyFrom();
            Currency SelectedTo = selectedProvider.getCurrencyTo();

            if (providerId.equals(selectedProviderId) && providerFrom == SelectedFrom && providerTo == SelectedTo)
                return true;
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