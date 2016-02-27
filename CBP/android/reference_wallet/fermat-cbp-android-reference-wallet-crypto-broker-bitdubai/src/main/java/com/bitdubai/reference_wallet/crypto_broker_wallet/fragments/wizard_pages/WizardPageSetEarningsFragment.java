package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.FiatCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsWizardAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.SingleCheckableItemAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsWizardData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetEarningsFragment extends AbstractFermatFragment
        implements SingleCheckableItemAdapter.OnCheckboxClickedListener<EarningsWizardData> {

    // Constants
    private static final String TAG = "WizardPageSetEarning";

    //Data
    private List<EarningsWizardData> earningDataList;

    // Fermat Managers
    private CryptoBrokerWalletManager walletManager;
    private ErrorManager errorManager;
    private EarningsWizardAdapter adapter;


    public static WizardPageSetEarningsFragment newInstance() {
        return new WizardPageSetEarningsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CryptoBrokerWalletModuleManager moduleManager = ((CryptoBrokerWalletSession) appSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(appSession.getAppPublicKey());
            errorManager = appSession.getErrorManager();

            List<String> temp = new ArrayList<>();
            String tempS;

            List<EarningsWizardData> _earningDataList = createEarningDataList();
            earningDataList = new ArrayList<>();

            for (EarningsWizardData EP : _earningDataList) {
                tempS = EP.getEarningCurrency().getCode() + " - " + EP.getLinkedCurrency().getCode();
                if (!temp.contains(tempS)) {
                    temp.add(tempS);
                    earningDataList.add(EP);
                }
            }

        } catch (Exception ex) {
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,
                        ex
                );
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View layout = inflater.inflate(R.layout.cbw_wizard_step_set_earnings, container, false);

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.cbw_selected_earning_wallets_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        FermatTextView emptyView = (FermatTextView) layout.findViewById(R.id.cbw_selected_earning_wallets_empty_view);

        if (earningDataList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            adapter = new EarningsWizardAdapter(getActivity(), earningDataList);
            adapter.setCheckboxListener(this);
            recyclerView.setAdapter(adapter);
        }

        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
            }
        });

        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setBannerRes(R.drawable.banner_crypto_broker)
                .setIconRes(R.drawable.crypto_broker)
                .setSubTitle(R.string.cbw_crypto_broker_wallet_earning_subTitle)
                .setBody(R.string.cbw_crypto_broker_wallet_earning_body)
                .setTextFooter(R.string.cbw_crypto_broker_wallet_earning_footer)
                .build();
        presentationDialog.show();

        return layout;
    }

    @Override
    public void checkedChanged(boolean isChecked, EarningsWizardData data, final int position) {

        if (isChecked && !data.isChecked()) {
            showWalletsDialog(data, position);
        }

        if (!isChecked && data.isChecked()) {
            data.clearWalletInfo();
            adapter.notifyItemChanged(position);
        }
    }

    private void showWalletsDialog(final EarningsWizardData data, final int position) {
        try {
            List<InstalledWallet> installedWallets = walletManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            Currency earningCurrency = data.getEarningCurrency();
            for (InstalledWallet wallet : installedWallets) {
                Platforms platform = wallet.getPlatform();
                switch (platform) {
                    case BANKING_PLATFORM:
                        List<BankAccountNumber> accounts = walletManager.getAccounts(wallet.getWalletPublicKey());
                        for (BankAccountNumber account : accounts) {
                            FiatCurrency currencyType = account.getCurrencyType();
                            if (currencyType.getCode().equals(earningCurrency.getCode())) {
                                filteredList.add(wallet);
                                break;
                            }
                        }
                        break;
                    case CASH_PLATFORM:
                        FiatCurrency cashCurrency = walletManager.getCashCurrency(wallet.getWalletPublicKey());
                        if (cashCurrency.getCode().equals(earningCurrency.getCode()))
                            filteredList.add(wallet);
                        break;
                    case CRYPTO_CURRENCY_PLATFORM:
                        CryptoCurrency cryptoCurrency = wallet.getCryptoCurrency();
                        if (cryptoCurrency.getCode().equals(earningCurrency.getCode()))
                            filteredList.add(wallet);
                        break;
                }
            }

            final SimpleListDialogFragment<InstalledWallet> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.configure("Select a Wallet", filteredList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<InstalledWallet>() {
                @Override
                public void onItemSelected(InstalledWallet selectedWallet) {
                    final Platforms platform = selectedWallet.getPlatform();

                    if (!platform.equals(Platforms.BANKING_PLATFORM)) {
                        data.setWalletInfo(selectedWallet.getWalletPublicKey(), selectedWallet.getWalletName());
                        adapter.notifyItemChanged(position);
                    } else
                        showBankAccountsDialog(selectedWallet, data, position);
                }
            });

            dialogFragment.show(getFragmentManager(), "WalletsDialog");

        } catch (FermatException ex) {
            Toast.makeText(getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        }
    }

    private void showBankAccountsDialog(final InstalledWallet selectedWallet, final EarningsWizardData data, final int position) {
        try {
            List<BankAccountNumber> accounts = walletManager.getAccounts(selectedWallet.getWalletPublicKey());

            SimpleListDialogFragment<BankAccountNumber> accountsDialog = new SimpleListDialogFragment<>();
            accountsDialog.configure("Select an Account", accounts);
            accountsDialog.setListener(new SimpleListDialogFragment.ItemSelectedListener<BankAccountNumber>() {
                @Override
                public void onItemSelected(BankAccountNumber selectedAccount) {

                    data.setWalletInfo(selectedWallet.getWalletPublicKey(), selectedWallet.getWalletName());
                    adapter.notifyItemChanged(position);
                }
            });

            accountsDialog.show(getFragmentManager(), "accountsDialog");

        } catch (FermatException ex) {
            Toast.makeText(getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    private void saveSettingAndGoNextStep() {

        for (EarningsWizardData data : earningDataList) {
            if (data.getWalletPublicKey() != null) {
                try {
                    walletManager.addEarningsPairToEarningSettings(
                            data.getEarningCurrency(),
                            data.getLinkedCurrency(),
                            data.getWalletPublicKey(),
                            appSession.getAppPublicKey());
                } catch (FermatException ex) {
                    Toast.makeText(getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();
                    if (errorManager != null)
                        errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                                UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                    else
                        Log.e(TAG, ex.getMessage(), ex);
                }
            }
        }

        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS, appSession.getAppPublicKey());
    }


    private List<EarningsWizardData> createEarningDataList() {
        ArrayList<EarningsWizardData> list = new ArrayList<>();

        try {
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = walletManager.getCryptoBrokerWalletAssociatedSettings(appSession.getAppPublicKey());

            for (CryptoBrokerWalletAssociatedSetting associatedWallet1 : associatedWallets) {
                for (CryptoBrokerWalletAssociatedSetting associatedWallet2 : associatedWallets) {
                    Currency merchandise1 = associatedWallet1.getMerchandise();
                    Currency merchandise2 = associatedWallet2.getMerchandise();

                    if (!merchandise1.equals(merchandise2)) {
                        final EarningsWizardData data = new EarningsWizardData(merchandise1, merchandise2);
                        if (!list.contains(data))
                            list.add(data);
                    }

                }
            }

        } catch (FermatException ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        return list;
    }
}
