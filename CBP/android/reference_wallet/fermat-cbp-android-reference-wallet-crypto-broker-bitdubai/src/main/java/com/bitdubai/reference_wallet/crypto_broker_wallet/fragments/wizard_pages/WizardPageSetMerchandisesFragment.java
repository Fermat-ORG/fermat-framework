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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CBPInstalledWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.WalletsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.FragmentsCommons;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.InputDialogCBP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetMerchandisesFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager>
        implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<CBPInstalledWallet>, DialogInterface.OnDismissListener {

    // Constants
    private static final String TAG = "WizardPageSetMerchand";

    //Data
    private boolean walletConfigured;
    private List<CBPInstalledWallet> stockWallets;
    private Map<String, FiatCurrency> bankCurrencies;
    private Map<String, String> bankAccounts;
    private CryptoBrokerIdentity selectedIdentity;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;

    //UI
    private WalletsAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout fragmentContainer;
    private FermatTextView emptyView;


    public static WizardPageSetMerchandisesFragment newInstance() {
        return new WizardPageSetMerchandisesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stockWallets = new ArrayList<>();
        bankCurrencies = new HashMap<>();
        bankAccounts = new HashMap<>();


        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            // Verify if wallet has been configured, if true show this fragment, else show the home fragment (the second start fragment)
            try {
                walletConfigured = moduleManager.isWalletConfigured(appSession.getAppPublicKey());
            } catch (Exception ex) {
                Object data = appSession.getData(FragmentsCommons.CONFIGURED_DATA);
                walletConfigured = (data != null);
            }

            if (walletConfigured) {
                return;
            } else {
                //Delete potential previous configurations made by this wizard page so that they can be reconfigured cleanly
                moduleManager.clearAssociatedIdentities(appSession.getAppPublicKey());

                final List<CBPInstalledWallet> installWallets = moduleManager.getCbpInstallWallets();
                for (CBPInstalledWallet wallet : installWallets)
                    moduleManager.clearAssociatedWalletSettings(appSession.getAppPublicKey(), wallet.getPlatform());
            }

            //Obtain walletSettings or create new wallet settings if first time opening wallet
            CryptoBrokerWalletPreferenceSettings walletSettings;
            try {
                walletSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                walletSettings = null;
            }

            if (walletSettings == null) {
                walletSettings = new CryptoBrokerWalletPreferenceSettings();
                walletSettings.setIsPresentationHelpEnabled(true);
                moduleManager.persistSettings(appSession.getAppPublicKey(), walletSettings);
            } else {
                List<CryptoBrokerIdentity> list = moduleManager.getListOfIdentities();
                if (list != null) {
                    if (!list.isEmpty())
                        selectedIdentity = list.get(0);
                }

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

        //TODO PASAR POR ACA LA CURRENCY COMO PARAMETRO.
        adapter = new WalletsAdapter(getActivity(), stockWallets);
        adapter.setDeleteButtonListener(this);
        recyclerView.setAdapter(adapter);

        emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_stock_wallets_empty_view);
        fragmentContainer = (LinearLayout) layout.findViewById(R.id.cbw_fragment_container);


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

        fragmentContainer.setVisibility(View.VISIBLE);
        showHelpDialog();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
        //If wallet already configured, go directly to wallet
//                if (walletConfigured) {
//                    changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
//                } else {  //otherwise, show wizard page
//                    fragmentContainer.setVisibility(View.VISIBLE);
//                    showHelpDialog();
//                }
//            }
//        }, 250);

        return layout;
    }

    private void showHelpDialog() {

        try {
            final boolean haveAssociatedIdentity = moduleManager.haveAssociatedIdentity(appSession.getAppPublicKey());
            if (haveAssociatedIdentity)
                return;

            final PresentationDialog presentationDialog;

            List list = moduleManager.getListOfIdentities();
            if (list != null) {
                if (list.isEmpty()) {
                    presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION)
                            .setBannerRes(R.drawable.banner_crypto_broker)
                            .setIconRes(R.drawable.crypto_broker)
                            .setSubTitle(R.string.cbw_wizard_merchandise_dialog_sub_title)
                            .setBody(R.string.cbw_wizard_merchandise_dialog_body)
                            .setTextFooter(R.string.cbw_wizard_merchandise_dialog_footer)
                            .setCheckboxText(R.string.cbw_wizard_not_show_text)
                            .setVIewColor(R.color.cbw_wizard_merchandises_wallet_button_color)
                            .setIsCheckEnabled(false)
                            .build();
                    presentationDialog.show();

                } else {
                    presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                            .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                            .setBannerRes(R.drawable.banner_crypto_broker)
                            .setIconRes(R.drawable.crypto_broker)
                            .setSubTitle(R.string.cbw_wizard_merchandise_dialog_sub_title)
                            .setBody(R.string.cbw_wizard_merchandise_dialog_body)
                            .setTextFooter(R.string.cbw_wizard_merchandise_dialog_footer)
                            .setCheckboxText(R.string.cbw_wizard_not_show_text)
                            .setVIewColor(R.color.cbw_wizard_merchandises_wallet_button_color)
                            .setIsCheckEnabled(false)
                            .build();
                }

                presentationDialog.setOnDismissListener(this);

                final CryptoBrokerWalletPreferenceSettings preferenceSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
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

        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }
    }


    private void showWalletsDialog(final Platforms platform) {
        try {
            List<CBPInstalledWallet> installedWallets = moduleManager.getCbpInstallWallets();
            List<CBPInstalledWallet> filteredList = new ArrayList<>();

            for (CBPInstalledWallet wallet : installedWallets) {
                if (wallet.getPlatform().equals(platform))
                    filteredList.add(wallet);
            }

            final SimpleListDialogFragment<CBPInstalledWallet> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Wallet", filteredList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<CBPInstalledWallet>() {
                @Override
                public void onItemSelected(final CBPInstalledWallet selectedItem) {
                    switch (platform) {
                        case BANKING_PLATFORM:
                            showBankAccountsDialog(selectedItem);
                            break;

                        case CASH_PLATFORM:
                            if (!moduleManager.cashMoneyWalletExists(WalletsPublicKeys.CSH_MONEY_WALLET.getCode())) {

                                final InputDialogCBP inputDialogCBP = new InputDialogCBP(getActivity(), appSession, null, moduleManager, InputDialogCBP.CASH_DIALOG);
                                inputDialogCBP.show();
                                inputDialogCBP.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {
                                        if (!containWallet(selectedItem)) {
                                            FiatCurrency cashCurrency = getCashCurrency(WalletsPublicKeys.CSH_MONEY_WALLET.getCode());
                                            if (cashCurrency != null) {
                                                selectedItem.setCurrency(cashCurrency);
                                                stockWallets.add(selectedItem);
                                                adapter.changeDataSet(stockWallets);
                                                showOrHideNoSelectedWalletsView();
                                            }
                                        }
                                    }
                                });
                            } else if (!containWallet(selectedItem)) {
                                FiatCurrency cashCurrency = getCashCurrency(WalletsPublicKeys.CSH_MONEY_WALLET.getCode());
                                selectedItem.setCurrency(cashCurrency);
                                stockWallets.add(selectedItem);
                                adapter.changeDataSet(stockWallets);
                                showOrHideNoSelectedWalletsView();
                            }
                            break;
                        case CRYPTO_CURRENCY_PLATFORM:
                            if (!containWallet(selectedItem)) {
                                selectedItem.setCurrency(selectedItem.getCryptoCurrency());
                                stockWallets.add(selectedItem);
                                adapter.changeDataSet(stockWallets);
                                showOrHideNoSelectedWalletsView();
                            }
                            break;
                    }
                }
            });

            dialogFragment.show(getFragmentManager(), "WalletsDialog");

        } catch (CantListWalletsException ex) {
            Toast.makeText(WizardPageSetMerchandisesFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }
    }


    private void showBankAccountsDialog(final CBPInstalledWallet selectedWallet) {
        try {
            List<BankAccountNumber> accounts = moduleManager.getAccounts(WalletsPublicKeys.BNK_BANKING_WALLET.getCode());

            //If there is at least one bank wallet account created
            if (!accounts.isEmpty()) {

                SimpleListDialogFragment<BankAccountNumber> accountsDialog = new SimpleListDialogFragment<>();
                accountsDialog.configure("Select an Account", accounts);
                accountsDialog.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {

                    @Override
                    public void onItemSelected(BankAccountNumber selectedAccount) {
                        FiatCurrency currency = selectedAccount.getCurrencyType();
                        String account = selectedAccount.getAccount();
                        bankCurrencies.put(selectedWallet.getWalletPublicKey(), currency);
                        bankAccounts.put(selectedWallet.getWalletPublicKey(), account);
                        if (!containWallet(selectedWallet)) {
                            selectedWallet.setCurrency(currency);
                            stockWallets.add(selectedWallet);
                            adapter.changeDataSet(stockWallets);
                            showOrHideNoSelectedWalletsView();
                        }
                    }

                });
                accountsDialog.show(getFragmentManager(), "accountsDialog");
            }

            //If there are no accounts, prompt user to create a new bank account
            else {
                final InputDialogCBP inputDialogCBP = new InputDialogCBP(getActivity(), appSession, null, moduleManager, InputDialogCBP.BANK_DIALOG);
                inputDialogCBP.show();
                inputDialogCBP.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (!containWallet(selectedWallet)) {

                            if (inputDialogCBP.getCreatedBankAccount() != null) {

                                FiatCurrency currency_dialog = inputDialogCBP.getCreatedBankAccount().getCurrencyType();
                                String account_dialog = inputDialogCBP.getCreatedBankAccount().getAccount();
                                bankCurrencies.put(selectedWallet.getWalletPublicKey(), currency_dialog);
                                bankAccounts.put(selectedWallet.getWalletPublicKey(), account_dialog);

                                selectedWallet.setCurrency(currency_dialog);
                                stockWallets.add(selectedWallet);
                                adapter.changeDataSet(stockWallets);
                                showOrHideNoSelectedWalletsView();
                            }
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

        if (stockWallets.size() < 2) {
            Toast.makeText(getActivity(), R.string.cbw_select_stock_wallets_warning_msg, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            moduleManager.associateIdentity(selectedIdentity, appSession.getAppPublicKey());

            for (CBPInstalledWallet wallet : stockWallets) {
                String walletPublicKey = wallet.getWalletPublicKey();
                Platforms platform = wallet.getPlatform();

                CryptoBrokerWalletAssociatedSetting associatedSetting = moduleManager.newEmptyCryptoBrokerWalletAssociatedSetting();
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
                    FiatCurrency cashCurrency = getCashCurrency(walletPublicKey);
                    associatedSetting.setMerchandise(cashCurrency);
                    associatedSetting.setMoneyType(MoneyType.CASH_ON_HAND);
                }

                moduleManager.saveWalletSettingAssociated(associatedSetting, appSession.getAppPublicKey());
            }

        } catch (FermatException ex) {
            Toast.makeText(WizardPageSetMerchandisesFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        //TODO: agregar esta instruccion en el try/catch cuando funcione el saveSettingSpread
        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_EARNINGS, appSession.getAppPublicKey());
    }

    @Override
    public void deleteButtonClicked(CBPInstalledWallet data, final int position) {
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

    private boolean containWallet(CBPInstalledWallet selectedWallet) {
        if (stockWallets.isEmpty())
            return false;

        for (CBPInstalledWallet wallet : stockWallets) {
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
            List<CryptoBrokerIdentity> listOfIdentities = moduleManager.getListOfIdentities();
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

    private FiatCurrency getCashCurrency(String walletPublicKey) {

        FiatCurrency currency;

        try {
            currency = moduleManager.getCashCurrency(walletPublicKey);
        } catch (FermatException ex) {
            currency = null;
        }

        return currency;
    }

}