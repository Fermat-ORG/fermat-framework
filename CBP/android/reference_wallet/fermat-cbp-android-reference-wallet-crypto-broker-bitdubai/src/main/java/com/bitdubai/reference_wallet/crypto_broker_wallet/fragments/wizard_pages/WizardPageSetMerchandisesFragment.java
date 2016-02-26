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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.exceptions.CantListCryptoBrokerIdentitiesException;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.exceptions.CantGetCryptoBrokerIdentityListException;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.WalletsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.InputDialogCBP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetMerchandisesFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager>
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<InstalledWallet>, DialogInterface.OnDismissListener {

    // Constants

    private static final String TAG = "WizardPageSetMerchand";

    private List<InstalledWallet> stockWallets;
    private Map<String, FiatCurrency> bankCurrencies;
    private Map<String, String> bankAccounts;
    private CryptoBrokerIdentity selectedIdentity;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private WalletsAdapter adapter;
    private RecyclerView recyclerView;
    private FermatTextView emptyView;
    String walletPublicKey = "banking_wallet";


    public static WizardPageSetMerchandisesFragment newInstance() {
        return new WizardPageSetMerchandisesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stockWallets = new ArrayList<>();
        bankCurrencies = new HashMap<>();
        bankAccounts = new HashMap<>();
        moduleManager = appSession.getModuleManager();

        try {
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            // Verify if wallet configured, if it is, show this fragment, else show the home fragment (the second start fragment)
            boolean walletConfigured;
            try {
                walletConfigured = walletManager.isWalletConfigured(appSession.getAppPublicKey());
            } catch (Exception ex) {
                Object data = appSession.getData(CryptoBrokerWalletSession.CONFIGURED_DATA);
                walletConfigured = (data != null);
            }

            if (walletConfigured) {
                getRuntimeManager().changeStartActivity(1);
            }

            //Obtain walletSettings or create new wallet settings if first time opening wallet
            CryptoBrokerWalletPreferenceSettings walletSettings;
            try {
                walletSettings = moduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                walletSettings = null;
            }

            if (walletSettings == null) {
                walletSettings = new CryptoBrokerWalletPreferenceSettings();
                walletSettings.setIsPresentationHelpEnabled(true);
                moduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), walletSettings);
            }

        } catch (Exception ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View layout = inflater.inflate(R.layout.cbw_wizard_step_set_merchandises, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.cbw_selected_stock_wallets_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter = new WalletsAdapter(getActivity(), stockWallets);
        adapter.setDeleteButtonListener(this);
        recyclerView.setAdapter(adapter);

        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_stock_wallets_empty_view);


        final View cryptoButton = layout.findViewById(R.id.cbw_select_crypto_wallets);
        cryptoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletsDialog(Platforms.CRYPTO_CURRENCY_PLATFORM);
            }
        });

        final View bankButton = layout.findViewById(R.id.cbw_select_bank_wallets);
        bankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletsDialog(Platforms.BANKING_PLATFORM);
            }
        });

        final View cashButton = layout.findViewById(R.id.cbw_select_cash_wallets);
        cashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        showWalletsDialog(Platforms.CASH_PLATFORM);


            }
        });


        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
            }
        });

        showHelpDialog();

        return layout;
    }

    private void showHelpDialog() {

        try {
            final boolean haveAssociatedIdentity = walletManager.haveAssociatedIdentity(appSession.getAppPublicKey());
            if (haveAssociatedIdentity)
                return;

            PresentationDialog presentationDialog;

            if (walletManager.getListOfIdentities().isEmpty()) {
                presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                        .setBannerRes(R.drawable.banner_crypto_broker)
                        .setIconRes(R.drawable.crypto_broker)
                        .setBody(R.string.cbw_wizard_merchandise_dialog_body)
                        .setSubTitle(R.string.cbw_wizard_merchandise_dialog_sub_title)
                        .setTextFooter(R.string.cbw_wizard_merchandise_dialog_footer)
                        .build();

            } else {
                presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                        .setSubTitle(R.string.cbw_crypto_broker_wallet_merchandises_subTitle)
                        .setBody(R.string.cbw_crypto_broker_wallet_merchandises_body)
                        .setTextFooter(R.string.cbw_crypto_broker_wallet_merchandises_footer)
                        .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                        .setBannerRes(R.drawable.banner_crypto_broker)
                        .setIconRes(R.drawable.crypto_broker)
                        .build();
            }


            presentationDialog.setOnDismissListener(this);


            final SettingsManager<CryptoBrokerWalletPreferenceSettings> settingsManager = moduleManager.getSettingsManager();
            final CryptoBrokerWalletPreferenceSettings preferenceSettings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
            final boolean showDialog = preferenceSettings.isHomeTutorialDialogEnabled();
            if (showDialog)
                presentationDialog.show();

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }


    private void showWalletsDialog(final Platforms platform) {
        try {
            List<InstalledWallet> installedWallets = walletManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            for (InstalledWallet wallet : installedWallets) {
                if (wallet.getPlatform().equals(platform))
                    filteredList.add(wallet);
            }

            final SimpleListDialogFragment<InstalledWallet> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Wallet", filteredList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<InstalledWallet>() {
                @Override
                public void onItemSelected(InstalledWallet selectedItem) {
                    if (!platform.equals(Platforms.BANKING_PLATFORM)) {
                        if (Platforms.CRYPTO_BROKER_PLATFORM.getCode().equals(platform.getCode())) {

                        }
                        if (Platforms.CASH_PLATFORM.getCode().equals(platform.getCode())) {
                            if (walletManager.cashMoneyWalletExists("cash_wallet") == false) {
                                final InputDialogCBP inputDialogCBP = new InputDialogCBP(getActivity(), appSession, null, walletManager);
                                inputDialogCBP.DialogType(2);
                                inputDialogCBP.show();
                                inputDialogCBP.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                    }
                                });
                            }

                            if (!containWallet(selectedItem)) {
                                stockWallets.add(selectedItem);
                                adapter.changeDataSet(stockWallets);
                                showOrHideNoSelectedWalletsView();
                            }
                        }
                    } else {
                        showBankAccountsDialog(selectedItem);
                    }
                }

        });

            dialogFragment.show(getFragmentManager(), "WalletsDialog");

        } catch (CantListWalletsException ex) {
            Toast.makeText(WizardPageSetMerchandisesFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }


    private void showBankAccountsDialog(final InstalledWallet selectedWallet) {
        try {
            List<BankAccountNumber> accounts = walletManager.getAccounts("banking_wallet");
            if (!accounts.isEmpty()) {

                SimpleListDialogFragment<BankAccountNumber> accountsDialog = new SimpleListDialogFragment<>();
                accountsDialog.configure("Select an Account", accounts);
                accountsDialog.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {

                    @Override
                    public void onItemSelected(BankAccountNumber selectedAccount) {
                        FiatCurrency currency = selectedAccount.getCurrencyType();
                    String account = selectedAccount.getAccount();
                    bankCurrencies.put(selectedWallet.getWalletPublicKey(),currency);
                    bankAccounts.put(selectedWallet.getWalletPublicKey(),account);
                    if(!containWallet(selectedWallet))
                    {
                        stockWallets.add(selectedWallet);
                        adapter.changeDataSet(stockWallets);
                        showOrHideNoSelectedWalletsView();
                    }
                }

            });
                accountsDialog.show(getFragmentManager(), "accountsDialog");
            } else {
                     final InputDialogCBP inputDialogCBP = new InputDialogCBP(getActivity(), appSession, null, walletManager);
                    inputDialogCBP.DialogType(1);
                    inputDialogCBP.show();
                    inputDialogCBP.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            String account_dialog = inputDialogCBP.getAccountnumber().getAccount();
                            FiatCurrency currency_dialog = inputDialogCBP.getAccountnumber().getCurrencyType();
                            bankCurrencies.put(selectedWallet.getWalletPublicKey(), currency_dialog);
                            bankAccounts.put(selectedWallet.getWalletPublicKey(), account_dialog);
                            if (!containWallet(selectedWallet)) {
                                stockWallets.add(selectedWallet);
                                adapter.changeDataSet(stockWallets);
                                showOrHideNoSelectedWalletsView();
                            }
                        }

                    });
            }

        } catch (FermatException ex) {
            Toast.makeText(WizardPageSetMerchandisesFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            } else {
                Log.e(TAG, ex.getMessage(), ex);
            }
        }
    }

    private void saveSettingAndGoNextStep() {

        if (stockWallets.isEmpty()) {
            Toast.makeText(getActivity(), R.string.cbw_select_stock_wallets_warning_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            walletManager.associateIdentity(selectedIdentity, appSession.getAppPublicKey());

            for (InstalledWallet wallet : stockWallets) {
                String walletPublicKey = wallet.getWalletPublicKey();
                Platforms platform = wallet.getPlatform();

                CryptoBrokerWalletAssociatedSetting associatedSetting = walletManager.newEmptyCryptoBrokerWalletAssociatedSetting();
                associatedSetting.setBrokerPublicKey(appSession.getAppPublicKey());
                associatedSetting.setId(UUID.randomUUID());
                associatedSetting.setWalletPublicKey(walletPublicKey);
                associatedSetting.setPlatform(platform);

                if (platform.equals(Platforms.BANKING_PLATFORM)) {
                    associatedSetting.setMoneyType(MoneyType.BANK);
                    associatedSetting.setMerchandise(bankCurrencies.get(walletPublicKey));
                    associatedSetting.setBankAccount(bankAccounts.get(walletPublicKey));

                } else if (platform.equals(Platforms.CRYPTO_CURRENCY_PLATFORM)) {
                    associatedSetting.setMoneyType(MoneyType.CRYPTO);
                    associatedSetting.setMerchandise(wallet.getCryptoCurrency());

                } else {
                    FiatCurrency cashCurrency = walletManager.getCashCurrency(walletPublicKey);
                    associatedSetting.setMerchandise(cashCurrency);
                    associatedSetting.setMoneyType(MoneyType.CASH_ON_HAND);
                }

                walletManager.saveWalletSettingAssociated(associatedSetting, appSession.getAppPublicKey());
            }

        } catch (FermatException ex) {
            Toast.makeText(WizardPageSetMerchandisesFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }

        //TODO: agregar esta instruccion en el try/catch cuando funcione el saveSettingSpread
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS, appSession.getAppPublicKey());
    }

    @Override
    public void deleteButtonClicked(InstalledWallet data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_wallet_dialog_title).setMessage(R.string.cbw_delete_wallet_dialog_msg);
        builder.setPositiveButton(R.string.cbw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stockWallets.remove(position);
                adapter.changeDataSet(stockWallets);
                showOrHideNoSelectedWalletsView();
            }
        });
        builder.setNegativeButton(R.string.cbw_cancel_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();
    }

    private void showOrHideNoSelectedWalletsView() {
        if (stockWallets.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private boolean containWallet(InstalledWallet selectedWallet) {
        if (stockWallets.isEmpty())
            return false;

        for (InstalledWallet wallet : stockWallets) {
            String walletPublicKey = wallet.getWalletPublicKey();
            String selectedWalletPublicKey = selectedWallet.getWalletPublicKey();
            if (walletPublicKey.equals(selectedWalletPublicKey))
                return true;
        }

        return false;
    }

    /**
     * This method will be invoked when the dialog is dismissed.
     *
     * @param dialog The dialog that was dismissed will be passed into the
     *               method.
     */
    @Override
    public void onDismiss(DialogInterface dialog) {
        try {
            //Buscar la identidad
            List<CryptoBrokerIdentity> listOfIdentities = walletManager.getListOfIdentities();
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
}