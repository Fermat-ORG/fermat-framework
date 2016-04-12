package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.mati.fermat_preference_settings.drawer.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.drawer.dialog.CustomDialogFragment;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsOpenDialogText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsSwithItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsTextPlusRadioItem;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2016.02.09..
 */
public class ReferenceWalletSettings extends FermatPreferenceFragment<ReferenceWalletSession,WalletResourcesProviderManager> {

    private ReferenceWalletSession referenceWalletSession;
    private SettingsManager<BitcoinWalletSettings> settingsManager;
    private BitcoinWalletSettings bitcoinWalletSettings = null;
    private String previousSelectedItem = "MainNet";

    public static ReferenceWalletSettings newInstance() {
        return new ReferenceWalletSettings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = appSession;
        try {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            //noinspection unchecked
            settingsManager = referenceWalletSession.getModuleManager().getSettingsManager();
        } catch (Exception e) {
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
        BlockchainNetworkType blockchainNetworkType = null;
        List<PreferenceSettingsItem> list = new ArrayList<>();

        //noinspection TryWithIdenticalCatches
        try{
            bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());

            list.add(new PreferenceSettingsSwithItem(1,"Enabled Notifications",bitcoinWalletSettings.getNotificationEnabled()));

            if (bitcoinWalletSettings.getBlockchainNetworkType() != null)
                blockchainNetworkType =  bitcoinWalletSettings.getBlockchainNetworkType();

            //List<PreferenceSettingsTextPlusRadioItem> strings = new ArrayList<>();
            //strings.add(new PreferenceSettingsTextPlusRadioItem(6,"MainNet",(blockchainNetworkType.equals(BlockchainNetworkType.PRODUCTION)) ? true : false));
            //strings.add(new PreferenceSettingsTextPlusRadioItem(7,"TestNet",(blockchainNetworkType.equals(BlockchainNetworkType.TEST_NET)) ? true : false));
            //strings.add(new PreferenceSettingsTextPlusRadioItem(8,"RegTest",(blockchainNetworkType.equals(BlockchainNetworkType.REG_TEST)) ? true : false));
            final Bundle dataDialog = new Bundle();
            dataDialog.putInt("items", R.array.items);
            dataDialog.putString("positive_button_text", getResources().getString(R.string.ok_label));
            dataDialog.putString("negative_button_text", getResources().getString(R.string.cancel_label));
            dataDialog.putString("title", getResources().getString(R.string.title_label));
            dataDialog.putString("mode", "single_option");
            dataDialog.putString("previous_selected_item", previousSelectedItem);
            list.add(new PreferenceSettingsOpenDialogText(5,"Select Network",dataDialog));

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
        System.out.println(getClass().getSimpleName() + "------------------------------ onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position)");
        /*try {
            //noinspection TryWithIdenticalCatches
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

            } else {
                //PreferenceSettingsTextPlusRadioItem preferenceSettingsTextPlusRadioItem = (PreferenceSettingsTextPlusRadioItem) preferenceSettingsItem;
                BlockchainNetworkType blockchainNetworkType;
                //previousSelectedItem = dialog.getPreviousSelectedItem();
                switch (previousSelectedItem) {
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

                //preferenceSettingsTextPlusRadioItem.setIsRadioTouched(true);
                //System.out.println("SETTING SELECTED IS " + preferenceSettingsTextPlusRadioItem.getText());
                System.out.println("NETWORK TYPE TO BE SAVED IS  " + blockchainNetworkType.getCode());

                if (bitcoinWalletSettings.getBlockchainNetworkType() != null)
                    blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
                else
                    blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

                bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);
            }

            try {
                settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void onSettingsTouched(String item, int position) {
        System.out.println(getClass().getSimpleName() + "------------------------------ onSettingsTouched(String item, int position)");
        try {
            //noinspection TryWithIdenticalCatches
            try {
                bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }
            bitcoinWalletSettings.setIsPresentationHelpEnabled(false);


            //if (preferenceSettingsItem.getId() == 9) {
                //export key show fragment
                //changeActivity(Activities.CCP_BITCOIN_WALLET_MNEMONIC_ACTIVITY,referenceWalletSession.getAppPublicKey());

                // MnemonicSendDialog MnemonicDialog = new MnemonicSendDialog(getActivity());
                // MnemonicDialog.show();

            //} else {
                //PreferenceSettingsTextPlusRadioItem preferenceSettingsTextPlusRadioItem = (PreferenceSettingsTextPlusRadioItem) preferenceSettingsItem;
                BlockchainNetworkType blockchainNetworkType;
                previousSelectedItem = item;
                switch (previousSelectedItem) {
                    case "MainNet":
                        System.out.println(getClass().getSimpleName() + "------------------------------ MainNet");
                        blockchainNetworkType = BlockchainNetworkType.PRODUCTION;
                        break;

                    case "TestNet":
                        System.out.println(getClass().getSimpleName() + "------------------------------ TestNet");
                        blockchainNetworkType = BlockchainNetworkType.TEST_NET;
                        break;

                    case "RegTest":
                        System.out.println(getClass().getSimpleName() + "------------------------------ RegTest");
                        blockchainNetworkType = BlockchainNetworkType.REG_TEST;
                        break;

                    default:
                        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                        break;
                }

                //preferenceSettingsTextPlusRadioItem.setIsRadioTouched(true);
                System.out.println("SETTING SELECTED IS " + previousSelectedItem);
                System.out.println("NETWORK TYPE TO BE SAVED IS  " + blockchainNetworkType.getCode());

                if (bitcoinWalletSettings.getBlockchainNetworkType() != null)
                    blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
                else
                    blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

                bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);
            //}

            try {
                settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onSettingsChanged(PreferenceSettingsItem preferenceSettingsItem, int position, boolean isChecked) {

        try {
            //noinspection TryWithIdenticalCatches
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
        } catch (Exception e) {
            e.printStackTrace();
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
