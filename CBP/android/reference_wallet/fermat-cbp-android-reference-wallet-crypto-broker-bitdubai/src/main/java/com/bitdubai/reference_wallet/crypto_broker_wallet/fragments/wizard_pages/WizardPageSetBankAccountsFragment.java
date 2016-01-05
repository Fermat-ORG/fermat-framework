package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWizardPageFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetBankAccountsFragment extends AbstractFermatFragment {

    // Constants
    private static final String TAG = "WizardPageSetBank";

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;


    List<BankAccountNumber> accounts;


    public static WizardPageSetBankAccountsFragment newInstance() {
        return new WizardPageSetBankAccountsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accounts = new ArrayList<>();
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

        final View bankButton = layout.findViewById(R.id.cbw_select_bank_accounts);

        bankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBankAccountsDialog();
            }
        });

        View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_HOME, appSession.getAppPublicKey());
            }
        });

        return layout;
    }

    private void showBankAccountsDialog() {
        try {
            accounts = walletManager.getAccounts(selectedWallet.getWalletPublicKey());

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
                        stockWallets.add(selectedWallet);
                        adapter.changeDataSet(stockWallets);
                        showOrHideNoSelectedWalletsView();
                    }
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
}
