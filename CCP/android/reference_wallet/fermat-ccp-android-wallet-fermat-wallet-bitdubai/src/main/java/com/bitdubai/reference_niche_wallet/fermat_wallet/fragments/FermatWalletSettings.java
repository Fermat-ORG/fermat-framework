package com.bitdubai.reference_niche_wallet.fermat_wallet.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.classes.vault_seed.exceptions.CantLoadExistingVaultSeed;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantRequestFermatAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup.SendToLossProtectedWalletDialog;
import com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils;
import com.mati.fermat_preference_settings.drawer.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsLinkText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsOpenDialogText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsSwithItem;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bitdubai.reference_niche_wallet.fermat_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2016.02.09..
 */
public class FermatWalletSettings extends FermatPreferenceFragment<ReferenceAppFermatSession<FermatWallet>,WalletResourcesProviderManager> implements FermatSettings {

    private ReferenceAppFermatSession<FermatWallet> fermatWalletSessionReferenceApp;
    private com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings bitcoinWalletSettings = null;
    private String previousSelectedItem = "RegTest";

    private FermatWalletWalletContact cryptoWalletWalletContact;
    private BlockchainNetworkType blockchainNetworkType;
    private FermatWallet fermatWalletModule;

    public static FermatWalletSettings newInstance() {
        return new FermatWalletSettings();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fermatWalletSessionReferenceApp = appSession;


        try {

            fermatWalletModule = appSession.getModuleManager();

            try {
                bitcoinWalletSettings = fermatWalletSessionReferenceApp.getModuleManager().loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());

                if (blockchainNetworkType == null) {
                    if (bitcoinWalletSettings.getBlockchainNetworkType() != null) {
                        blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
                    } else {
                        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

                        bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);


                        try {
                            fermatWalletSessionReferenceApp.getModuleManager().persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), bitcoinWalletSettings);
                        } catch (CantPersistSettingsException e) {
                            e.printStackTrace();
                        }
                    }
                }



            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            //noinspection unchecked
        } catch (Exception e) {
            fermatWalletSessionReferenceApp.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {
        BlockchainNetworkType blockchainNetworkType = null;
        List<PreferenceSettingsItem> list = new ArrayList<>();

        //noinspection TryWithIdenticalCatches
        try {




            list.add(new PreferenceSettingsSwithItem(1, "Enabled Notifications", bitcoinWalletSettings.getNotificationEnabled()));

            if (bitcoinWalletSettings.getBlockchainNetworkType() != null) {
                blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();

                switch (blockchainNetworkType) {
                    case PRODUCTION:
                        previousSelectedItem = "MainNet";
                        break;
                    case REG_TEST:
                        previousSelectedItem = "RegTest";
                        break;
                    case TEST_NET:
                        previousSelectedItem = "TestNet";
                        break;
                }

            }


            final Bundle dataDialog = new Bundle();
            dataDialog.putInt("items", R.array.items);
            dataDialog.putString("positive_button_text", getResources().getString(R.string.ok_label));
            dataDialog.putString("negative_button_text", getResources().getString(R.string.cancel_label));
            dataDialog.putString("title", getResources().getString(R.string.title_label));
            dataDialog.putString("mode", "single_option");
            dataDialog.putString("previous_selected_item", previousSelectedItem);
            list.add(new PreferenceSettingsOpenDialogText(5, "Select Network", dataDialog));


            list.add(new PreferenceSettingsLinkText(9, "Send Error Report", "",15,Color.parseColor("#54ACEC")));

            list.add(new PreferenceSettingsLinkText(13, "Received Regtest Bitcoins", "", 15, Color.parseColor("#54ACEC")));

            //list.add(new PreferenceSettingsLinkText(10, "Export Private key ", "",15,Color.GRAY));

           // list.add(new PreferenceSettingsLinkText(11, "Send Bitcoins To Loss Protected Wallet", "",15,Color.GRAY));

            //list.add(new PreferenceSettingsLinkText(12, "Import Mnemonic code", "",15,Color.GRAY));



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


            bitcoinWalletSettings.setIsPresentationHelpEnabled(false);
            if (preferenceSettingsItem.getId() == 10) {
                //export key show fragment

                changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_MNEMONIC_ACTIVITY, fermatWalletSessionReferenceApp.getAppPublicKey());

            } else if (preferenceSettingsItem.getId() == 9) {
                //export key show fragment

                changeActivity(Activities.CCP_BITCOIN_FERMAT_WALLET_OPEN_SEND_ERROR_REPORT, fermatWalletSessionReferenceApp.getAppPublicKey());

            }else if (preferenceSettingsItem.getId() == 11){
                //send btc to loss protected
                SendToLossProtectedWalletDialog sendToLossProtectedWalletDialog = new SendToLossProtectedWalletDialog(
                        getActivity(),
                        fermatWalletModule,
                        fermatWalletSessionReferenceApp,
                        blockchainNetworkType);
                sendToLossProtectedWalletDialog.show();

            } else if(preferenceSettingsItem.getId() == 12){

                openImportMnemonicScreen();

            }
            else if(preferenceSettingsItem.getId() == 13){

                //receive Regtest test bitcoins
               Log.i("info", "LongPress");
               Toast.makeText(getActivity(), "Regtest download Init", Toast.LENGTH_SHORT).show();
               GET("", getActivity());
            }



            try {
                fermatWalletSessionReferenceApp.getModuleManager().persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }

    }

    String m_Text;

    private void openImportMnemonicScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Title");

// Set up the input
        final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                final List<String> mnemonicCodePlusDate = Arrays.asList(m_Text.split(" "));
                final long date = Long.parseLong(mnemonicCodePlusDate.get(mnemonicCodePlusDate.size()-1));
                ArrayList<String> mnemonicCode = new ArrayList<String>(mnemonicCodePlusDate);
                mnemonicCode.remove(mnemonicCode.size() - 1);
                final List<String> tempList = new ArrayList<String>(mnemonicCode);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            fermatWalletModule.importMnemonicCode(tempList,date,BlockchainNetworkType.getDefaultBlockchainNetworkType());
                        } catch (CantLoadExistingVaultSeed cantLoadExistingVaultSeed) {
                            cantLoadExistingVaultSeed.printStackTrace();
                        }
                    }
                }).start();


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void sendCrypto() {
        try {
            if (cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType) != null) {
                CryptoAddress validAddress = WalletUtils.validateAddress(cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress(),fermatWalletModule);
                if (validAddress != null) {

                }
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onSettingsTouched(String item, int position) {

    }

    @Override
    public void onSettingsChanged(PreferenceSettingsItem preferenceSettingsItem, int position, boolean isChecked) {

        try {

            try {
                bitcoinWalletSettings = fermatWalletSessionReferenceApp.getModuleManager().loadAndGetSettings(fermatWalletSessionReferenceApp.getAppPublicKey());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }


            if (preferenceSettingsItem.getId() == 1) {
                //enable notifications settings
                bitcoinWalletSettings.setNotificationEnabled(isChecked);
            }



            try {
                fermatWalletSessionReferenceApp.getModuleManager().persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
        }

    }


    @Override
    public int getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public int getBackgroundAlpha() {
        return 0;
    }

    @Override
    public void optionSelected(PreferenceSettingsItem preferenceSettingsItem, int position)
    {

    }

    @Override
    public void dialogOptionSelected(String item, int position) {


        switch (item) {

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



        if (blockchainNetworkType == null) {
            if (bitcoinWalletSettings.getBlockchainNetworkType() != null) {
                blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
            } else {
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
            }
        }

        bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);


        try {
            fermatWalletSessionReferenceApp.getModuleManager().persistSettings(fermatWalletSessionReferenceApp.getAppPublicKey(), bitcoinWalletSettings);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }

    public void GET(String url, final Context context){
        final Handler mHandler = new Handler();
        try {

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String receivedAddress = "";
                    final HttpClient Client = new DefaultHttpClient();
                    try {
                        String SetServerString = "";

                        // Create Request to server and get response

                        HttpGet httpget = new HttpGet("http://52.27.68.19:15400/mati/address/");
                        ResponseHandler<String> responseHandler = new BasicResponseHandler();
                        SetServerString = Client.execute(httpget, responseHandler);
                        // Show response on activity

                        receivedAddress = SetServerString;
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    final String finalReceivedAddress = receivedAddress;

                    String response = "";
                    try {


                        String SetServerString = "";
                        CryptoAddress cryptoAddress = new CryptoAddress(finalReceivedAddress, CryptoCurrency.FERMAT);
                       FermatWalletWalletContact cryptoWalletWalletContact = null;
                        try {
                            cryptoWalletWalletContact = fermatWalletModule.createWalletContact(cryptoAddress, "regtest_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(), blockchainNetworkType);

                        } catch (Exception e) {
                           // e.printStackTrace();
                            try {
                                cryptoWalletWalletContact = fermatWalletModule.findWalletContactByName("regtest_bitcoins",appSession.getAppPublicKey(),fermatWalletModule.getSelectedActorIdentity().getPublicKey());
                            } catch (CantFindWalletContactException e1) {
                                e1.printStackTrace();
                            } catch (WalletContactNotFoundException e1) {
                                e1.printStackTrace();
                            } catch (CantGetSelectedActorIdentityException e1) {
                                e1.printStackTrace();
                            } catch (ActorIdentityNotSelectedException e1) {
                                e1.printStackTrace();
                            }
                        }

                        if (cryptoWalletWalletContact != null) {
                            String myCryptoAddress = getWalletAddress(cryptoWalletWalletContact.getActorPublicKey());
                            HttpGet httpget = new HttpGet("http://52.27.68.19:15400/mati/hello/?address=" + myCryptoAddress);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);
                        }

                        response = SetServerString;
                    } catch (IOException e) {
                        e.printStackTrace();

                    }


                    final String finalResponse = response;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            if (!finalResponse.equals("transaccion fallida")) {
                                Toast.makeText(context, "Regtest bitcoin arrived", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });
            thread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getWalletAddress(String actorPublicKey) {
        String walletAddres="";
        try {
            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
            CryptoAddress cryptoAddress = fermatWalletModule.requestAddressToKnownUser(
                    fermatWalletModule.getSelectedActorIdentity().getPublicKey(),
                    Actors.INTRA_USER,
                    actorPublicKey,
                    Actors.EXTRA_USER,
                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    CryptoCurrencyVault.FERMAT_VAULT.getCode(),
                    appSession.getAppPublicKey(),
                    ReferenceWallet.BASIC_WALLET_FERMAT_WALLET,
                    blockchainNetworkType
            );
            walletAddres = cryptoAddress.getAddress();
        }catch (CantGetSelectedActorIdentityException e) {
            Toast.makeText(getActivity().getApplicationContext(), "CantGetSelectedActorIdentityException", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            Toast.makeText(getActivity().getApplicationContext(), "ActorIdentityNotSelectedException", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        } catch (CantRequestFermatAddressException e) {
            Toast.makeText(getActivity().getApplicationContext(), "CantRequestLossProtectedAddressException", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        return walletAddres;
    }
}
