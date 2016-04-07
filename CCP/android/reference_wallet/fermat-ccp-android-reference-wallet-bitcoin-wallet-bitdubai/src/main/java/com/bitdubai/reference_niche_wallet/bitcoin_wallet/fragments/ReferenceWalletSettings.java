package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.WindowManager;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.mnemonic.MnemonicFragment;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.mnemonic.MnemonicSendDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceFragmentsEnumType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.mati.fermat_preference_settings.drawer.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsEditText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsTextPlusRadioItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsOpenDialogText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsSwithItem;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2016.02.09..
 */
public class ReferenceWalletSettings extends FermatPreferenceFragment<ReferenceWalletSession,WalletResourcesProviderManager> {



    private ReferenceWalletSession referenceWalletSession;
    private CryptoWallet cryptoWallet;
    SettingsManager<BitcoinWalletSettings> settingsManager;
    private BitcoinWalletSettings bitcoinWalletSettings = null;

    public static ReferenceWalletSettings newInstance() {
        return new ReferenceWalletSettings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = appSession;
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            settingsManager = referenceWalletSession.getModuleManager().getSettingsManager();
        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {
        BlockchainNetworkType blockchainNetworkType= null;
        List<PreferenceSettingsItem> list = new ArrayList<>();
        try{

            bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());

            list.add(new PreferenceSettingsSwithItem(1,"Enabled Notifications",bitcoinWalletSettings.getNotificationEnabled()));

        if (bitcoinWalletSettings.getBlockchainNetworkType()!=null)
            blockchainNetworkType =  bitcoinWalletSettings.getBlockchainNetworkType();

        List<PreferenceSettingsTextPlusRadioItem> strings = new ArrayList<PreferenceSettingsTextPlusRadioItem>();

        strings.add(new PreferenceSettingsTextPlusRadioItem(6,"MainNet",(blockchainNetworkType.equals(BlockchainNetworkType.PRODUCTION)) ? true : false));
        strings.add(new PreferenceSettingsTextPlusRadioItem(7,"TestNet",(blockchainNetworkType.equals(BlockchainNetworkType.TEST_NET)) ? true : false));
        strings.add(new PreferenceSettingsTextPlusRadioItem(8,"RegTest",(blockchainNetworkType.equals(BlockchainNetworkType.REG_TEST)) ? true : false));

        list.add(new PreferenceSettingsOpenDialogText(5,"Select Network",strings));



       // list.add(new PreferenceSettingsEditText(9,"Export Private key","Click Here"));

        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Cuando se elige algun settings va a pasar por acá
     *
     * @param preferenceSettingsItem
     * @param position
     */
    @Override
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {

        try {

            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }
            bitcoinWalletSettings.setIsPresentationHelpEnabled(false);



            if (preferenceSettingsItem.getId() == 9) {
                //export key show fragment

                changeActivity(Activities.CCP_BITCOIN_WALLET_MNEMONIC_ACTIVITY,referenceWalletSession.getAppPublicKey());

               // MnemonicSendDialog MnemonicDialog = new MnemonicSendDialog(getActivity());
               // MnemonicDialog.show();

            }
            else {
                PreferenceSettingsTextPlusRadioItem preferenceSettingsTextPlusRadioItem = (PreferenceSettingsTextPlusRadioItem) preferenceSettingsItem;
                BlockchainNetworkType blockchainNetworkType = null;

                switch (preferenceSettingsTextPlusRadioItem.getText()) {

                    case "MainNet":
                        blockchainNetworkType = BlockchainNetworkType.PRODUCTION;

                        break;

                    case "TestNet":
                        blockchainNetworkType = BlockchainNetworkType.TEST_NET;
                        break;

                    case "RegTest":
                        blockchainNetworkType = BlockchainNetworkType.REG_TEST;
                        break;

                    default:
                        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                        break;

                }

                preferenceSettingsTextPlusRadioItem.setIsRadioTouched(true);

                System.out.println("SETTING SELECTED IS " + preferenceSettingsTextPlusRadioItem.getText());
                System.out.println("NETWORK TYPE TO BE SAVED IS  " + blockchainNetworkType.getCode());

                if (blockchainNetworkType == null) {
                    if (bitcoinWalletSettings.getBlockchainNetworkType() != null) {
                        blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
                    } else {
                        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                    }
                }

                bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);

            }

            try {
                settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
        }

    }

    @Override
    public void onSettingsChanged(PreferenceSettingsItem preferenceSettingsItem, int position, boolean isChecked) {

        try {

            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }


            if (preferenceSettingsItem.getId() == 1){
                //enable notifications settings
                bitcoinWalletSettings.setNotificationEnabled(isChecked);
            }



            try {
                settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
        }

    }


    @Override
    public int getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public int getBackgroundAlpha() {
        return 95;
    }


}
