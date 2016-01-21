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
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.exceptions.CantLoadBankMoneyWalletException;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.all_definition.enums.CurrencyType;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.BankAccountsAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleDeletableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetBankAccountsFragment extends AbstractFermatFragment implements SingleDeletableItemAdapter.OnDeleteButtonClickedListener<BankAccountNumber> {

    // Constants
    private static final String TAG = "WizardPageSetBank";

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;
    private BankAccountsAdapter adapter;
    private RecyclerView recyclerView;
    private View emptyView;


    List<BankAccountNumber> accounts;
    List<BankAccountNumber> viewAccounts;


    public static WizardPageSetBankAccountsFragment newInstance() {
        return new WizardPageSetBankAccountsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accounts = new ArrayList<>();
        viewAccounts = new ArrayList<>();
        //bankWallets = new ArrayList<>();

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

        View layout = inflater.inflate(R.layout.cbw_wizard_step_set_bank_accounts, container, false);

        recyclerView = (RecyclerView) layout.findViewById(R.id.cbw_selected_bank_accounts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        adapter = new BankAccountsAdapter(getActivity(), accounts);
        adapter.setDeleteButtonListener(this);
        recyclerView.setAdapter(adapter);

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
                appSession.setData(CryptoBrokerWalletSession.CONFIGURED_DATA, true); // TODO Solo para testing, eliminar despues
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
            }
        });

        return layout;
    }

    @Override
    public void deleteButtonClicked(BankAccountNumber data, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.cbw_delete_wallet_dialog_title).setMessage(R.string.cbw_delete_wallet_dialog_msg);
        builder.setPositiveButton(R.string.cbw_delete_caps, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                accounts.remove(position);
                adapter.changeDataSet(accounts);
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
        //TODO: preguntar como asociar la bank account en un setting cuando se escoge como earnings bank
        /*try {
            List<InstalledWallet> installedWallets = walletManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            for (InstalledWallet wallet : installedWallets) {
                if (wallet.getPlatform().equals(Platforms.BANKING_PLATFORM))
                    filteredList.add(wallet);
            }
            String walletPublicKey = "";


            for (BankAccountNumber accountNumber : accounts) {
                for (InstalledWallet wallet : filteredList) {
                    if (accountNumber.getAccount().equals(walletManager.getAccounts(wallet.getWalletPublicKey()))) {
                        walletPublicKey = wallet.getWalletPublicKey();
                        break;
                    }
                }
                Platforms platform = Platforms.BANKING_PLATFORM;
                CryptoBrokerWalletAssociatedSetting associatedSetting = walletManager.newEmptyCryptoBrokerWalletAssociatedSetting();
                associatedSetting.setBrokerPublicKey(appSession.getAppPublicKey());
                associatedSetting.setId(UUID.randomUUID());
                associatedSetting.setWalletPublicKey(walletPublicKey);
                associatedSetting.setPlatform(platform);
                associatedSetting.setCurrencyType(CurrencyType.BANK_MONEY);
                associatedSetting.setBankAccount(accountNumber.getAccount());
                walletManager.saveWalletSettingAssociated(associatedSetting, appSession.getAppPublicKey());
            }
        } catch (FermatException ex) {
            Toast.makeText(WizardPageSetBankAccountsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }*/
    }

    private void showWalletsDialog(final Platforms platform) {
        try {
            List<InstalledWallet> installedWallets = walletManager.getInstallWallets();
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
            Toast.makeText(WizardPageSetBankAccountsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

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

            for (InstalledWallet wallet : installedWallets) {
                viewAccounts.addAll(walletManager.getAccounts(wallet.getWalletPublicKey()));
            }

            SimpleListDialogFragment<BankAccountNumber> accountsDialog = new SimpleListDialogFragment<>();
            accountsDialog.configure("Select an Account", viewAccounts);
            accountsDialog.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {
                @Override
                public void onItemSelected(BankAccountNumber selectedAccount) {
                    accounts.add(selectedAccount);
                    adapter.changeDataSet(accounts);
                    showOrHideRecyclerView();
                }
            });

            accountsDialog.show(getFragmentManager(), "accountsDialog");

        } catch (FermatException ex) {
            Toast.makeText(WizardPageSetBankAccountsFragment.this.getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

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
