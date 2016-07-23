package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.MoneyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.BankAccountsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/12/15.
 */
public class SettingsBankAccountsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager> implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<BankAccountNumber> {

    // Constants
    private static final String TAG = "WizardPageSetBank";

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private BankAccountsAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;


    List<BankAccountNumber> accounts;
    List<BankAccountNumber> viewAccounts;
    List<String> accountsStrings;


    public static SettingsBankAccountsFragment newInstance() {
        return new SettingsBankAccountsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accounts = new ArrayList<>();
        viewAccounts = new ArrayList<>();
        accountsStrings = new ArrayList<>();

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            List<CryptoBrokerWalletAssociatedSetting> associatedSettings = moduleManager.getCryptoBrokerWalletAssociatedSettings("walletPublicKeyTest");
            List<BankAccountNumber> bankAccountNumbers = moduleManager.getAccounts(WalletsPublicKeys.BNK_BANKING_WALLET.getCode());//"banking_wallet");
            for (final CryptoBrokerWalletAssociatedSetting aux : associatedSettings) {
                for (BankAccountNumber bankAccountNumber : bankAccountNumbers) {
                    if (aux.getPlatform() == Platforms.BANKING_PLATFORM) {
                        if (aux.getBankAccount().equals(bankAccountNumber.getAccount())) {
                            accountsStrings.add(bankAccountNumber.getAccount());
                            accounts.add(bankAccountNumber);
                        }
                    }
                }
            }
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
        configureToolbar();
        View layout = inflater.inflate(R.layout.cbw_settings_bank_accounts, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.cbw_selected_bank_accounts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        adapter = new BankAccountsAdapter(getActivity(), accounts);
        adapter.setDeleteButtonListener(this);
        recyclerView.setAdapter(adapter);
        adapter.changeDataSet(accounts);

        emptyView = layout.findViewById(R.id.cbw_selected_bank_accounts_empty_view);
        final View bankButton = layout.findViewById(R.id.cbw_select_bank_accounts);

        bankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWalletsDialog(Platforms.BANKING_PLATFORM);
            }
        });

        View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetting();
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });
        showOrHideRecyclerView();
        return layout;
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

    @Override
    public void deleteButtonClicked(BankAccountNumber data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_wallet_dialog_title).setMessage(R.string.cbw_delete_wallet_dialog_msg);
        builder.setPositiveButton(R.string.cbw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                accounts.remove(position);
                accountsStrings.remove(position);
                adapter.changeDataSet(accounts);
                saveSetting();
                showOrHideRecyclerView();
            }
        });
        builder.setNegativeButton(R.string.cbw_cancel_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.show();


    }

    private void saveSetting() {
        try {
            List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            for (InstalledWallet wallet : installedWallets) {
                if (wallet.getPlatform().equals(Platforms.BANKING_PLATFORM))
                    filteredList.add(wallet);
            }
            String walletPublicKey = "";

            moduleManager.clearAssociatedWalletSettings(appSession.getAppPublicKey(), Platforms.BANKING_PLATFORM);

            for (BankAccountNumber accountNumber : accounts) {

                for (InstalledWallet wallet : filteredList) {
                    for (BankAccountNumber auxAccountNumber1 : moduleManager.getAccounts(wallet.getWalletPublicKey())) {
                        if (accountNumber.getAccount().equals(auxAccountNumber1.getAccount())) {
                            walletPublicKey = wallet.getWalletPublicKey();
                            break;
                        }
                    }
                }

                Platforms platform = Platforms.BANKING_PLATFORM;
                CryptoBrokerWalletAssociatedSetting associatedSetting = moduleManager.newEmptyCryptoBrokerWalletAssociatedSetting();
                associatedSetting.setBrokerPublicKey(appSession.getAppPublicKey());
                associatedSetting.setId(UUID.randomUUID());
                associatedSetting.setWalletPublicKey(walletPublicKey);
                associatedSetting.setPlatform(platform);
                associatedSetting.setMoneyType(MoneyType.BANK);
                associatedSetting.setBankAccount(accountNumber.getAccount());
                associatedSetting.setMerchandise(accountNumber.getCurrencyType());

                moduleManager.saveWalletSettingAssociated(associatedSetting, appSession.getAppPublicKey());

            }
        } catch (FermatException ex) {
            Toast.makeText(SettingsBankAccountsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    private void showWalletsDialog(final Platforms platform) {
        try {
            List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            for (InstalledWallet wallet : installedWallets) {
                if (wallet.getPlatform().equals(platform))
                    filteredList.add(wallet);
            }

            if (platform != Platforms.BANKING_PLATFORM) {
                /*final SimpleListDialogFragment<InstalledWallet> dialogFragment = new SimpleListDialogFragment<>();
                dialogFragment.configure("Select a Wallet", filteredList);
                dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<InstalledWallet>() {
                    @Override
                    public void onItemSelected(InstalledWallet selectedItem) {
                        //show cualquier otro dialogo
                    }
                });

                dialogFragment.show(getFragmentManager(), "WalletsDialog");*/
            } else {
                showBankAccountsDialog(filteredList);
            }
        } catch (CantListWalletsException ex) {
            Toast.makeText(SettingsBankAccountsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    private void showBankAccountsDialog(List<InstalledWallet> installedWallets) {
        try {
            if (viewAccounts.size() > 0) {
                viewAccounts.clear();
            }
            for (InstalledWallet wallet : installedWallets) {
                viewAccounts.addAll(moduleManager.getAccounts(wallet.getWalletPublicKey()));
            }

            SimpleListDialogFragment<BankAccountNumber> accountsDialog = new SimpleListDialogFragment<>();
            accountsDialog.configure("Select an Account", viewAccounts);
            accountsDialog.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {
                @Override
                public void onItemSelected(BankAccountNumber selectedAccount) {
                    if (!accountsStrings.contains(selectedAccount.getAccount())) {
                        accountsStrings.add(selectedAccount.getAccount());
                        accounts.add(selectedAccount);
                        adapter.changeDataSet(accounts);
                        showOrHideRecyclerView();
                    } else {
                        Toast.makeText(SettingsBankAccountsFragment.this.getActivity(), "Account already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            accountsDialog.show(getFragmentManager(), "accountsDialog");

        } catch (FermatException ex) {
            Toast.makeText(SettingsBankAccountsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    private void showOrHideRecyclerView() {
        if (accounts.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
