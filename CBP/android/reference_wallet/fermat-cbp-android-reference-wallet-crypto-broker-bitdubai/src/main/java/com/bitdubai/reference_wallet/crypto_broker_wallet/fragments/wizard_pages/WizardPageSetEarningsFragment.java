package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.wizard_pages;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
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
import com.bitdubai.fermat_bnk_api.layer.bnk_wallet.bank_money.interfaces.BankAccountNumber;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CantGetCryptoBrokerWalletSettingException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.exceptions.CryptoBrokerWalletNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingSpread;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.CryptoBrokerWalletPreferenceSettings;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.adapters.EarningsWizardAdapter;
import com.bitdubai.reference_wallet.crypto_broker_wallet.common.models.EarningsWizardData;
import com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common.SimpleListDialogFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;


/**
 * Created by nelson on 22/12/15.
 */
public class WizardPageSetEarningsFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>, ResourceProviderManager>
        implements FermatListItemListeners<EarningsWizardData> {

    // Constants
    private static final String TAG = "WizardPageSetEarning";

    //UI
    boolean isHomeTutorialDialogEnabled = false;

    //Data
    private List<EarningsWizardData> earningDataList;
    private List<EarningsWizardData> earningDataListSelected;
    private Map<String, EarningsWizardData> currencyEarningWallet;
    private int spreadValue = 0;


    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;
    private EarningsWizardAdapter adapter;


    public static WizardPageSetEarningsFragment newInstance() {
        return new WizardPageSetEarningsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            List<String> temp = new ArrayList<>();
            String tempS;

            //Delete potential previous configurations made by this wizard page
            //So that they can be reconfigured cleanly
            moduleManager.clearEarningPairsFromEarningSettings(appSession.getAppPublicKey());

            //Delete potential previous configurations made by this wizard page
            //So that they can be reconfigured cleanly
            moduleManager.clearWalletSetting(appSession.getAppPublicKey());

            CryptoBrokerWalletPreferenceSettings settings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());
            isHomeTutorialDialogEnabled = settings.isHomeTutorialDialogEnabled();

            List<EarningsWizardData> _earningDataList = createEarningDataList();
            earningDataList = new ArrayList<>();
            earningDataListSelected = new ArrayList<>();
            currencyEarningWallet = new HashMap<>();

            for (EarningsWizardData EP : _earningDataList) {
                tempS = new StringBuilder().append(EP.getEarningCurrency().getCode()).append(" - ").append(EP.getLinkedCurrency().getCode()).toString();
                if (!temp.contains(tempS)) {
                    temp.add(tempS);
                    earningDataList.add(EP);
                }
            }

        } catch (Exception ex) {
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_THIS_FRAGMENT,
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
            adapter.setFermatListEventListener(this);
            recyclerView.setAdapter(adapter);
        }

        final View nextStepButton = layout.findViewById(R.id.cbw_next_step_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingAndGoNextStep();
            }
        });


        final FermatTextView spreadTextView = (FermatTextView) layout.findViewById(R.id.cbw_spread_value_text);
        spreadTextView.setText(String.format("%1$s %%", spreadValue));

        final SeekBar spreadSeekBar = (SeekBar) layout.findViewById(R.id.cbw_spread_value_seek_bar);
        spreadSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                spreadValue = progress;
                spreadTextView.setText(String.format("%1$s %%", spreadValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        if (isHomeTutorialDialogEnabled) {
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.banner_crypto_broker)
                    .setIconRes(R.drawable.crypto_broker)
                    .setSubTitle(R.string.cbw_wizard_earnings_dialog_sub_title)
                    .setBody(R.string.cbw_wizard_earnings_dialog_body)
                    .setCheckboxText(R.string.cbw_wizard_not_show_text)
                    .setVIewColor(R.color.cbw_wizard_merchandises_wallet_button_color)
                    .setIsCheckEnabled(false)
                    .build();
            presentationDialog.show();
        }

        return layout;
    }


    @Override
    public void onItemClickListener(EarningsWizardData data, int position) {
        earningsWalletClicked(data, position);
    }

    @Override
    public void onLongItemClickListener(EarningsWizardData data, int position) {
        earningsWalletClicked(data, position);
    }

    private void earningsWalletClicked(EarningsWizardData data, int position) {

        //If there's an assigned wallet already. clear it
        if (data.isChecked())
            data.clearWalletInfo();

            //Otherwise show dialog and assign a wallet
        else
            showWalletsDialog(data, position);

        //In any case, refresh the adapter
        adapter.notifyDataSetChanged();
    }

    private void showWalletsDialog(final EarningsWizardData data, final int position) {
        try {
            List<InstalledWallet> installedWallets = moduleManager.getInstallWallets();
            List<InstalledWallet> filteredList = new ArrayList<>();

            final Currency earningCurrency = data.getEarningCurrency();
            final Currency linkedCurrency = data.getLinkedCurrency();

            for (InstalledWallet wallet : installedWallets) {
                Platforms platform = wallet.getPlatform();
                switch (platform) {
                    case BANKING_PLATFORM:
                        List<BankAccountNumber> accounts;
                        try {
                            accounts = moduleManager.getAccounts(wallet.getWalletPublicKey());
                        } catch (Exception e) {
                            accounts = new ArrayList<>();
                        }

                        for (BankAccountNumber account : accounts) {
                            FiatCurrency currencyType = account.getCurrencyType();
                            if (currencyType.equals(earningCurrency) || currencyType.equals(linkedCurrency)) {
                                if (isWalletAssociated(wallet.getWalletPublicKey(), currencyType.getCode(), account.getAccount())) {
                                    filteredList.add(wallet);
                                    setCurrencyEarningWallet(wallet.getWalletPublicKey(), data, currencyType);
                                    break;
                                }
                            }
                        }
                        break;
                    case CASH_PLATFORM:
                        FiatCurrency cashCurrency;
                        try {
                            cashCurrency = moduleManager.getCashCurrency(wallet.getWalletPublicKey());
                        } catch (Exception ignore) {
                            cashCurrency = null;
                        }

                        if (earningCurrency.equals(cashCurrency) || linkedCurrency.equals(cashCurrency)) {
                            if (isWalletAssociated(wallet.getWalletPublicKey(), cashCurrency.getCode())) {
                                filteredList.add(wallet);
                                setCurrencyEarningWallet(wallet.getWalletPublicKey(), data, cashCurrency);
                                break;
                            }
                        }
                        break;
                    case CRYPTO_CURRENCY_PLATFORM:
                        CryptoCurrency cryptoCurrency = wallet.getCryptoCurrency();
                        if (cryptoCurrency.equals(earningCurrency) || cryptoCurrency.equals(linkedCurrency)) {
                            if (isWalletAssociated(wallet.getWalletPublicKey(), cryptoCurrency.getCode())) {
                                filteredList.add(wallet);
                                setCurrencyEarningWallet(wallet.getWalletPublicKey(), data, cryptoCurrency);
                                break;
                            }
                        }
                        break;
                }
            }

            final SimpleListDialogFragment<InstalledWallet> dialogFragment = new SimpleListDialogFragment<>();
            dialogFragment.setCancelable(false);
            dialogFragment.configure("Select a Wallet", filteredList);
            dialogFragment.setListener(new SimpleListDialogFragment.ItemSelectedListener<InstalledWallet>() {
                @Override
                public void onItemSelected(InstalledWallet selectedWallet) {

                    data.setWalletInfo(selectedWallet.getWalletPublicKey(), selectedWallet.getWalletName());
                    EarningsWizardData earningsWizardData = currencyEarningWallet.get(selectedWallet.getWalletPublicKey());
                    earningsWizardData.setWalletInfo(selectedWallet.getWalletPublicKey(), selectedWallet.getWalletName());
                    earningDataListSelected.add(earningsWizardData);
                    adapter.notifyItemChanged(position);

                }
            });

            dialogFragment.show(getFragmentManager(), "WalletsDialog");

        } catch (FermatException ex) {
            Toast.makeText(getActivity(), "Oops a error occurred...", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }
        }
    }

    private void saveSettingAndGoNextStep() {


        try {

            //Save spread settings
            CryptoBrokerWalletSettingSpread walletSetting = moduleManager.newEmptyCryptoBrokerWalletSetting();
            walletSetting.setId(null);
            walletSetting.setBrokerPublicKey(appSession.getAppPublicKey());
            walletSetting.setSpread(spreadValue);
            walletSetting.setRestockAutomatic(false);                       //Automatic restock no longer used

            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());

            //Save earning wallets data
            for (EarningsWizardData data : earningDataListSelected) {
                if (data.getWalletPublicKey() != null) {
                    moduleManager.addEarningsPairToEarningSettings(
                            data.getEarningCurrency(),
                            data.getLinkedCurrency(),
                            data.getWalletPublicKey(),
                            appSession.getAppPublicKey());
                }
            }

            //Go to next wizard page
            changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SET_PROVIDERS, appSession.getAppPublicKey());
        } catch (FermatException ex) {
            Toast.makeText(WizardPageSetEarningsFragment.this.getActivity(), "There was a problem saving the settings. Try again later.", Toast.LENGTH_SHORT).show();

            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            } else
                Log.e(TAG, ex.getMessage(), ex);
        }

    }


    private List<EarningsWizardData> createEarningDataList() {
        ArrayList<EarningsWizardData> list = new ArrayList<>();

        try {
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = moduleManager.getCryptoBrokerWalletAssociatedSettings(appSession.getAppPublicKey());

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
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        return list;
    }

    private boolean isWalletAssociated(String walletPublicKey, String fiatCurrencyCode) {
        try {
            List<CryptoBrokerWalletAssociatedSetting> associatedWallets = moduleManager.getCryptoBrokerWalletAssociatedSettings(walletPublicKey);

            for (CryptoBrokerWalletAssociatedSetting associatedWallet : associatedWallets) {
                final String associatedWalletPublicKey = associatedWallet.getWalletPublicKey();
                final String merchandiseCode = associatedWallet.getMerchandise().getCode();

                if (associatedWalletPublicKey.equals(walletPublicKey) && merchandiseCode.equals(fiatCurrencyCode)) {
                    return true;
                }
            }

        } catch (CantGetCryptoBrokerWalletSettingException | CryptoBrokerWalletNotFoundException ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        return false;

    }

    private boolean isWalletAssociated(String walletPublicKey, String currencyCode, String account) {
        try {
            List<CryptoBrokerWalletAssociatedSetting> walletAssociated = moduleManager.getCryptoBrokerWalletAssociatedSettings(walletPublicKey);

            for (CryptoBrokerWalletAssociatedSetting walletAssociatedItem : walletAssociated) {
                final String associatedWalletPublicKey = walletAssociatedItem.getWalletPublicKey();
                final String merchandiseCode = walletAssociatedItem.getMerchandise().getCode();
                final String associatedAccount = walletAssociatedItem.getBankAccount();

                if (walletPublicKey.equals(associatedWalletPublicKey) && merchandiseCode.equals(currencyCode) && account.equals(associatedAccount)) {
                    return true;
                }
            }

        } catch (CantGetCryptoBrokerWalletSettingException | CryptoBrokerWalletNotFoundException ex) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        return false;
    }

    private void setCurrencyEarningWallet(String walletPublicKey, EarningsWizardData dataEarning, Currency currencyEarning) {

        Currency earningCurrency = dataEarning.getEarningCurrency();
        Currency linkedCurrency = dataEarning.getLinkedCurrency();

        if (!currencyEarning.getCode().equals(dataEarning.getEarningCurrency().getCode())) {
            earningCurrency = dataEarning.getLinkedCurrency();
            linkedCurrency = dataEarning.getEarningCurrency();
        }

        EarningsWizardData earningsWizardData = new EarningsWizardData(earningCurrency, linkedCurrency);
        currencyEarningWallet.put(walletPublicKey, earningsWizardData);
    }
}
