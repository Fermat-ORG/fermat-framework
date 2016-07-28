package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.wizard_pages;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_customer.interfaces.CryptoCustomerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletProviderSetting;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.ProvidersAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_customer_wallet.common.models.CurrencyPairAndProvider;
import com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.common.SimpleListDialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Created by nelson
 * on 22/12/15.
 */
public class WizardPageSetBitcoinWalletAndProvidersFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager>
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<CurrencyPairAndProvider>, AdapterView.OnItemSelectedListener, DialogInterface.OnDismissListener {

    // Constants
    private static final String TAG = "WizardPageSetBCWP";

    // Data
    private List<CurrencyPairAndProvider> selectedProviders;
    private List<Currency> currencies;
    private Currency currencyFrom;
    private Currency currencyTo;
    private List<InstalledWallet> bitcoinWallets;
    private List<InstalledWallet> fermatWallets;
    private InstalledWallet selectedBitcoinWallet;
    private InstalledWallet selectedFermatWallet;
    private CryptoCustomerIdentity selectedIdentity;
    private boolean walletConfigured;


    // UI
    private RecyclerView recyclerView;
    private FermatTextView emptyView;
    private LinearLayout fragmentContainer;

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private ProvidersAdapter adapter;


    public static WizardPageSetBitcoinWalletAndProvidersFragment newInstance() {
        return new WizardPageSetBitcoinWalletAndProvidersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectedProviders = new ArrayList<>();
        currencies = getCurrenciesList();

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            bitcoinWallets = getBitcoinWallets(moduleManager);
            fermatWallets = getFermatWallets(moduleManager);

            //Obtain walletSettings or create new walletSettings if first time opening wallet/wizard
            CryptoCustomerWalletPreferenceSettings walletSettings;
            try {
                walletSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                walletSettings = new CryptoCustomerWalletPreferenceSettings();
                walletSettings.setIsPresentationHelpEnabled(true);
                walletSettings.setIsWalletConfigured(false);
                moduleManager.persistSettings(appSession.getAppPublicKey(), walletSettings);
            }


            // Verify if wallet has been configured, if it is, go to wallet's home!
            walletConfigured = walletSettings.isWalletConfigured();
            if (walletConfigured) {
                //getRuntimeManager().changeStartActivity(1);
                //changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, appSession.getAppPublicKey());
                return;
            }

            //It it isnt, continue..

            //Delete potential previous configurations made by this wizard page
            //So that they can be reconfigured cleanly
            moduleManager.clearAssociatedIdentities(appSession.getAppPublicKey());
            moduleManager.clearCryptoCustomerWalletProviderSetting(appSession.getAppPublicKey());


        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View layout = inflater.inflate(R.layout.ccw_wizard_step_set_bitcoin_wallet_and_providers, container, false);

        configureToolbar();

        recyclerView = (RecyclerView) layout.findViewById(R.id.ccw_selected_providers_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new ProvidersAdapter(getActivity(), selectedProviders);
        adapter.setDeleteButtonListener(this);
        recyclerView.setAdapter(adapter);

        emptyView = (FermatTextView) layout.findViewById(R.id.ccw_selected_providers_empty_view);
        fragmentContainer = (LinearLayout) layout.findViewById(R.id.ccw_fragment_container);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.ccw_spinner_item, getFormattedCurrencies(currencies));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner currencyFromSpinner = (Spinner) layout.findViewById(R.id.currency_from_spinner);
        currencyFromSpinner.setOnItemSelectedListener(this);
        currencyFromSpinner.setAdapter(adapter);

        final Spinner currencyToSpinner = (Spinner) layout.findViewById(R.id.currency_to_spinner);
        currencyToSpinner.setOnItemSelectedListener(this);
        currencyToSpinner.setAdapter(adapter);

        adapter = new ArrayAdapter<>(getActivity(), R.layout.ccw_spinner_item, getFormattedBitcoinWallets(bitcoinWallets));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner bitCoinWalletsSpinner = (Spinner) layout.findViewById(R.id.bitcoin_wallets_spinner);
        bitCoinWalletsSpinner.setOnItemSelectedListener(this);
        bitCoinWalletsSpinner.setAdapter(adapter);

        adapter = new ArrayAdapter<>(getActivity(), R.layout.ccw_spinner_item, getFormattedBitcoinWallets(fermatWallets));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner fermatWalletsSpinner = (Spinner) layout.findViewById(R.id.fermat_wallets_spinner);
        fermatWalletsSpinner.setOnItemSelectedListener(this);
        fermatWalletsSpinner.setAdapter(adapter);

        final View selectProvidersButton = layout.findViewById(R.id.ccw_select_providers_button);
        selectProvidersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProvidersDialog();
            }
        });

        final View nextStepButton = layout.findViewById(R.id.ccw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
            }
        });

        fragmentContainer.setVisibility(View.VISIBLE);
        showHelpDialog();

//            @Override
//            public void run() {
//                //If wallet already configured, go directly to wallet
//                if (walletConfigured) {
//                    changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_HOME, appSession.getAppPublicKey());
//                } else {  //otherwise, show wizard page
//                    fragmentContainer.setVisibility(View.VISIBLE);
//                    showHelpDialog();
//                }
//            }
//        }, 250);

        return layout;
    }

    @SuppressWarnings("deprecation")
    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }

    private void showHelpDialog() {

        try {
            final boolean haveAssociatedIdentity = moduleManager.haveAssociatedIdentity(appSession.getAppPublicKey());
            if (haveAssociatedIdentity)
                return;

            final PresentationDialog presentationDialog;

            List<CryptoCustomerIdentity> listOfIdentities = moduleManager.getListOfIdentities();
            if (listOfIdentities != null) {
                if (listOfIdentities.isEmpty()) {
                    presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                            .setBannerRes(R.drawable.cbp_banner_crypto_customer_wallet)
                            .setIconRes(R.drawable.cbp_crypto_customer)
                            .setSubTitle(R.string.ccw_wizard_providers_dialog_sub_title)
                            .setBody(R.string.ccw_wizard_providers_dialog_body)
                            .setTextFooter(R.string.ccw_wizard_providers_dialog_footer)
                            .setCheckboxText(R.string.ccw_wizard_not_show_text)
                            .setVIewColor(R.color.ccw_wizard_wallet_button_color)
                            .setIsCheckEnabled(false)
                            .build();
                    presentationDialog.setOnDismissListener(this);
                    presentationDialog.show();

                } else {
                    selectedIdentity = listOfIdentities.get(0);
                    presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.cbp_banner_crypto_customer_wallet)
                            .setIconRes(R.drawable.cbp_crypto_customer)
                            .setSubTitle(R.string.ccw_wizard_providers_dialog_sub_title)
                            .setBody(R.string.ccw_wizard_providers_dialog_body)
                            .setCheckboxText(R.string.ccw_wizard_not_show_text)
                            .setVIewColor(R.color.ccw_wizard_wallet_button_color)
                            .setIsCheckEnabled(false)
                            .build();

                    presentationDialog.setOnDismissListener(this);

                    final CryptoCustomerWalletPreferenceSettings preferenceSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
                    final boolean showDialog = preferenceSettings.isHomeTutorialDialogEnabled();
                    if (showDialog) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                presentationDialog.show();
                            }
                        });
                    }
                }
            }

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {

        try {
            List<CryptoCustomerIdentity> listOfIdentities = moduleManager.getListOfIdentities();
            if (listOfIdentities.isEmpty())
                getActivity().onBackPressed();
            else {
                invalidate();
                selectedIdentity = listOfIdentities.get(0);
            }

        } catch (FermatException e) {
            Log.e(TAG, e.getMessage(), e);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.currency_from_spinner) {
            currencyFrom = currencies.get(position);

        } else if (parent.getId() == R.id.currency_to_spinner) {
            currencyTo = currencies.get(position);

        } else if (parent.getId() == R.id.bitcoin_wallets_spinner) {
            selectedBitcoinWallet = bitcoinWallets.get(position);
        } else if (parent.getId() == R.id.fermat_wallets_spinner) {
            selectedFermatWallet = fermatWallets.get(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showProvidersDialog() {

        if (currencyFrom == currencyTo) {
            Toast.makeText(getActivity(), R.string.ccw_same_currencies_providers_warning_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            List<CurrencyPairAndProvider> providers = new ArrayList<>();

            Map<String, UUID> providersMap = moduleManager.getProviderReferencesFromCurrencyPair(currencyFrom, currencyTo);
            if (providersMap != null) {
                final Set<String> providerNames = providersMap.keySet();
                for (String providerName : providerNames) {
                    final UUID providerId = providersMap.get(providerName);
                    providers.add(new CurrencyPairAndProvider(currencyFrom, currencyTo, providerId, providerName));
                }

            }

            if (providers.size() == 0) {
                Toast.makeText(getActivity(), R.string.ccw_no_providers_for_chosen_currencies_msg, Toast.LENGTH_SHORT).show();
                return;
            }

            final SimpleListDialogFragment<CurrencyPairAndProvider> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Provider", providers);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<CurrencyPairAndProvider>() {
                @Override
                public void onItemSelected(CurrencyPairAndProvider selectedItem) {
                    if (!containProvider(selectedItem)) {
                        selectedProviders.add(selectedItem);
                        adapter.changeDataSet(selectedProviders);
                        Log.i("DATA PROVIDERS:", new StringBuilder().append("").append(selectedProviders).append(" Item seleccionado: ").append(selectedItem).toString());
                        showOrHideNoProvidersView();
                    }
                }
            });

            dialogFragment.show(getFragmentManager(), "ProvidersDialog");

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }
    }

    private void saveSettingAndGoNextStep() {

        if (selectedProviders.isEmpty()) {
            Toast.makeText(getActivity(), R.string.ccw_select_providers_warning_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            moduleManager.associateIdentity(selectedIdentity, appSession.getAppPublicKey());

            final CryptoCustomerWalletAssociatedSetting associatedWallet = moduleManager.newEmptyCryptoBrokerWalletAssociatedSetting();
            associatedWallet.setId(UUID.randomUUID());
            associatedWallet.setMoneyType(MoneyType.CRYPTO);
            associatedWallet.setMerchandise(selectedBitcoinWallet.getCryptoCurrency());
            associatedWallet.setPlatform(selectedBitcoinWallet.getPlatform());
            associatedWallet.setWalletPublicKey(selectedBitcoinWallet.getWalletPublicKey());
            associatedWallet.setCustomerPublicKey(appSession.getAppPublicKey());

            moduleManager.saveWalletSettingAssociated(associatedWallet, appSession.getAppPublicKey());

            if (selectedFermatWallet != null) {

                final CryptoCustomerWalletAssociatedSetting associatedFermatWallet = moduleManager.newEmptyCryptoBrokerWalletAssociatedSetting();
                associatedFermatWallet.setId(UUID.randomUUID());
                associatedFermatWallet.setMoneyType(MoneyType.CRYPTO);
                associatedFermatWallet.setMerchandise(selectedFermatWallet.getCryptoCurrency());
                associatedFermatWallet.setPlatform(selectedFermatWallet.getPlatform());
                associatedFermatWallet.setWalletPublicKey(selectedFermatWallet.getWalletPublicKey());
                associatedFermatWallet.setCustomerPublicKey(appSession.getAppPublicKey());

                moduleManager.saveWalletSettingAssociated(associatedWallet, appSession.getAppPublicKey());

            }

            for (CurrencyPairAndProvider provider : selectedProviders) {
                String providerName = provider.getProviderName();
                final UUID providerId = provider.getProviderId();

                CryptoCustomerWalletProviderSetting setting = moduleManager.newEmptyCryptoCustomerWalletProviderSetting();
                setting.setCustomerPublicKey(appSession.getAppPublicKey());
                setting.setDescription(providerName);
                setting.setId(providerId);
                setting.setPlugin(providerId);
                setting.setCurrencyFrom(provider.getCurrencyFrom());
                setting.setCurrencyTo(provider.getCurrencyTo());

                moduleManager.saveCryptoCustomerWalletProviderSetting(setting, appSession.getAppPublicKey());
            }

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SET_LOCATIONS, appSession.getAppPublicKey());
    }

    @Override
    public void deleteButtonClicked(CurrencyPairAndProvider data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.ccw_delete_wallet_dialog_title).setMessage(R.string.ccw_delete_wallet_dialog_msg);
        builder.setPositiveButton(R.string.ccw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedProviders.remove(position);
                adapter.changeDataSet(selectedProviders);
                showOrHideNoProvidersView();
            }
        });
        builder.setNegativeButton(R.string.ccw_cancel_caps, new DialogInterface.OnClickListener() {
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

        data.add(CryptoCurrency.BITCOIN);

        return data;
    }

    private List<String> getFormattedCurrencies(List<Currency> currencies) {
        ArrayList<String> data = new ArrayList<>();
        for (Currency currency : currencies) {
            data.add(new StringBuilder().append(currency.getFriendlyName()).append(" (").append(currency.getCode()).append(")").toString());
        }

        return data;
    }

    private List<InstalledWallet> getBitcoinWallets(CryptoCustomerWalletModuleManager moduleManager) {
        ArrayList<InstalledWallet> data = new ArrayList<>();

        try {
            List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();

            if (installedWallets != null)
                for (InstalledWallet wallet : installedWallets)
                    if (wallet.getPlatform().equals(Platforms.CRYPTO_CURRENCY_PLATFORM))
                        if (wallet.getCryptoCurrency() == CryptoCurrency.BITCOIN)
                            data.add(wallet);

        } catch (CantListWalletsException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return data;
    }

    private List<InstalledWallet> getFermatWallets(CryptoCustomerWalletModuleManager moduleManager) {
        ArrayList<InstalledWallet> data = new ArrayList<>();

        try {
            List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();

            if (installedWallets != null)
                for (InstalledWallet wallet : installedWallets) {

                    if (wallet.getPlatform().equals(Platforms.CRYPTO_CURRENCY_PLATFORM)) {
                        if (wallet.getCryptoCurrency() == CryptoCurrency.FERMAT) {
                            data.add(wallet);
                        }
                    }

                }

        } catch (CantListWalletsException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
        }

        return data;
    }

    private List<String> getFormattedBitcoinWallets(List<InstalledWallet> bitcoinWallets) {
        ArrayList<String> data = new ArrayList<>();
        for (InstalledWallet wallet : bitcoinWallets) {
            data.add(wallet.getWalletName());
        }
        return data;
    }
}
