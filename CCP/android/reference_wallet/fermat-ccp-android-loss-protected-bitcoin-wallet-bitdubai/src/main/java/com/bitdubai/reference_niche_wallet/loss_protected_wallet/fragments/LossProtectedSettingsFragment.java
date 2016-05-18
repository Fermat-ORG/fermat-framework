package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments;



import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;

import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_cer_api.layer.provider.exceptions.CantGetProviderInfoException;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;

import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
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
import java.util.List;
import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2016.02.09..
 */
public class LossProtectedSettingsFragment extends FermatPreferenceFragment<LossProtectedWalletSession,WalletResourcesProviderManager> {



    private LossProtectedWalletSession lossProtectedWalletSession;
    private LossProtectedWallet lossProtectedWalletManager;
    LossProtectedWalletSettings lossProtectedWalletSettings;
    //private LossProtectedWalletSettings bitcoinWalletSettings = null;
    private String previousSelectedItem = "RegTest";
    private String previousSelectedItemExchange = null;

    BlockchainNetworkType blockchainNetworkType;

    public static LossProtectedSettingsFragment newInstance() {
        return new LossProtectedSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lossProtectedWalletSession = appSession;
        try {
            lossProtectedWalletManager = lossProtectedWalletSession.getModuleManager();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
           } catch (CantGetSettingsException e) {
            lossProtectedWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());

        } catch (SettingsNotFoundException e) {
            lossProtectedWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());

        }
    }

    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {

        UUID exchangeProviderId = null;
        List<PreferenceSettingsItem> list = new ArrayList<>();
        try{


            lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());

            list.add(new PreferenceSettingsSwithItem(1,"Enabled Notifications",lossProtectedWalletSettings.getNotificationEnabled()));

            list.add(new PreferenceSettingsSwithItem(2,"Enabled Loss Protected",lossProtectedWalletSettings.getLossProtectedEnabled()));

            if (lossProtectedWalletSettings.getBlockchainNetworkType() != null) {
                blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();

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

            final Bundle networkDialog = new Bundle();
            String items[] = new String[]{"MainNet", "TestNet", "RegTest"};
            networkDialog.putStringArray("items_array", items);
            networkDialog.putString("positive_button_text", getResources().getString(R.string.ok_label));
            networkDialog.putString("negative_button_text", getResources().getString(R.string.cancel_label));
            networkDialog.putString("title", getResources().getString(R.string.title_label));
            networkDialog.putString("mode", "single_option");
            networkDialog.putString("previous_selected_item", previousSelectedItem);
            list.add(new PreferenceSettingsOpenDialogText(5, "Select Network", networkDialog));


            // Exchange Rate Provider
           if (lossProtectedWalletManager.getExchangeProvider() != null)
                exchangeProviderId =  lossProtectedWalletManager.getExchangeProvider();

            List<CurrencyExchangeRateProviderManager> providers = new ArrayList<>(lossProtectedWalletManager.getExchangeRateProviderManagers());
            String itemsProviders[] = new String[providers.size()];
            for (int i=0; i<providers.size(); i++) {
                CurrencyExchangeRateProviderManager provider = providers.get(i);
                itemsProviders[i] = provider.getProviderName();
                if(provider.getProviderId().equals(exchangeProviderId))
                    previousSelectedItemExchange = provider.getProviderName();
            }
            final Bundle providerDialog = new Bundle();
            providerDialog.putStringArray("items_array", itemsProviders);
            providerDialog.putString("positive_button_text", getResources().getString(R.string.ok_label));
            providerDialog.putString("negative_button_text", getResources().getString(R.string.cancel_label));
            providerDialog.putString("title", getResources().getString(R.string.exchange_title_label));
            providerDialog.putString("mode", "single_option");
            providerDialog.putString("previous_selected_item", previousSelectedItemExchange);
            list.add(new PreferenceSettingsOpenDialogText(10, "Exchange Rate Providers", providerDialog));


            list.add(new PreferenceSettingsLinkText(11, "Received Regtest Bitcoins", "", 15, Color.GRAY));

        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    @Override
    public void optionSelected(PreferenceSettingsItem preferenceSettingsItem, int position) {

    }

    /**
     * @param preferenceSettingsItem
     * @param position
     */
    @Override
    public void onSettingsTouched(PreferenceSettingsItem preferenceSettingsItem, int position) {

        try {

            try {
                lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }
            if(preferenceSettingsItem.getId() == 10){
                //Get providers list
                List<CurrencyExchangeRateProviderManager> providers = new ArrayList(lossProtectedWalletManager.getExchangeRateProviderManagers());

                String stringsProviders[] = new String[providers.size()];
                int i = 0;
                for (CurrencyExchangeRateProviderManager provider :  providers)
                {
                    if(lossProtectedWalletManager.getExchangeProvider().equals(provider.getProviderId()))
                        previousSelectedItem = provider.getProviderName();

                    stringsProviders[i] = provider.getProviderName();

                    i++;
                }
            }

            if (preferenceSettingsItem.getId() == 11){
                //receive Regtest test bitcoins
                Runnable _longPressed = new Runnable() {
                    public void run() {
                        Log.i("info", "LongPress");
                        Toast.makeText(getActivity(), "Regtest download Init", Toast.LENGTH_SHORT).show();
                        GET("", getActivity());
                    }
                };

                _longPressed.run();

            }
        } catch (Exception e){
        }

    }

    @Override
    public void onSettingsTouched(String item, int position) {


    }

    @Override
    public void onSettingsChanged(PreferenceSettingsItem preferenceSettingsItem, int position, boolean isChecked) {
        try {
            try {
                lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            } catch (CantGetSettingsException e) {
                e.printStackTrace();
            } catch (SettingsNotFoundException e) {
                e.printStackTrace();
            }


            if (preferenceSettingsItem.getId() == 1){
                //enable notifications settings
                lossProtectedWalletSettings.setNotificationEnabled(isChecked);
            }

            if (preferenceSettingsItem.getId() == 2){
                //enable Loss Protected
                lossProtectedWalletSettings.setLossProtectedEnabled(isChecked);
            }



            try {
                lossProtectedWalletManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), lossProtectedWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        } catch (Exception e){
        }

    }


    @Override
    public void dialogOptionSelected(String item, int position) {


        BlockchainNetworkType blockchainNetworkType;
        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
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
                //provider exchange
                // Exchange Rate Provider
                try {
                    UUID exchangeProviderId = null;
                    List<CurrencyExchangeRateProviderManager> providers = new ArrayList<>(lossProtectedWalletManager.getExchangeRateProviderManagers());

                    for (int i=0; i<providers.size(); i++) {
                        CurrencyExchangeRateProviderManager provider = providers.get(i);

                        if(provider.getProviderName().equals(item))

                                exchangeProviderId = provider.getProviderId();

                    }

                    lossProtectedWalletManager.setExchangeProvider(exchangeProviderId);
                } catch (CantGetProviderInfoException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }


        if (blockchainNetworkType == null) {
            if (lossProtectedWalletSettings.getBlockchainNetworkType() != null) {
                blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();
            } else {
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
            }
        }

        lossProtectedWalletSettings.setBlockchainNetworkType(blockchainNetworkType);


        try {
            lossProtectedWalletManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), lossProtectedWalletSettings);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public int getBackgroundAlpha() {
        return 70;
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
                            CryptoAddress cryptoAddress = new CryptoAddress(finalReceivedAddress, CryptoCurrency.BITCOIN);
                            LossProtectedWalletContact cryptoWalletWalletContact = null;
                            try {
                                cryptoWalletWalletContact = lossProtectedWalletManager.createWalletContact(cryptoAddress, "regtest_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(), blockchainNetworkType);

                            } catch (Exception e) {

                            }

                            assert cryptoWalletWalletContact != null;
                            String myCryptoAddress = getWalletAddress(cryptoWalletWalletContact.getActorPublicKey());
                            HttpGet httpget = new HttpGet("http://52.27.68.19:15400/mati/hello/?address=" + myCryptoAddress);
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            SetServerString = Client.execute(httpget, responseHandler);

                            response = SetServerString;
                        } catch (IOException e) {

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
            CryptoAddress cryptoAddress = lossProtectedWalletManager.requestAddressToKnownUser(
                    lossProtectedWalletSession.getIntraUserModuleManager().getPublicKey(),
                    Actors.INTRA_USER,
                    actorPublicKey,
                    Actors.EXTRA_USER,
                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    "BITV",
                    appSession.getAppPublicKey(),
                    ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                    blockchainNetworkType
            );
            walletAddres = cryptoAddress.getAddress();
        } catch (CantRequestLossProtectedAddressException e) {
            //errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        } catch (CantGetCryptoLossProtectedWalletException e) {
            e.printStackTrace();
        } catch (CantListCryptoWalletIntraUserIdentityException e) {
            e.printStackTrace();
        }
        return walletAddres;
    }
}
