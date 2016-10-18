package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
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
import com.bitdubai.fermat_bch_api.layer.crypto_network.faucet.CantGetCoinsFromFaucetException;
import com.bitdubai.fermat_ccp_api.all_definition.ExchangeRateProvider;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantCreateLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantFindLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_cer_api.layer.provider.interfaces.CurrencyExchangeRateProviderManager;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.SessionConstant;
import com.mati.fermat_preference_settings.drawer.FermatPreferenceFragment;
import com.mati.fermat_preference_settings.drawer.interfaces.PreferenceSettingsItem;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsLinkText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsOpenDialogText;
import com.mati.fermat_preference_settings.drawer.models.PreferenceSettingsSwithItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2016.02.09..
 * updated by Andres Abreu on 18/08/16
 */
public class LossProtectedSettingsFragment extends FermatPreferenceFragment<ReferenceAppFermatSession<LossProtectedWallet>,WalletResourcesProviderManager> {



    private ReferenceAppFermatSession<LossProtectedWallet> lossProtectedWalletSession;
    private LossProtectedWallet lossProtectedWalletManager;
    private String PublicKey;
    LossProtectedWalletSettings lossProtectedWalletSettings;
    private String previousSelectedItem = "MainNet";
    private String previousSelectedItemExchange = null;

    FermatWorker worker;

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
            PublicKey = lossProtectedWalletManager.getSelectedActorIdentity().getPublicKey();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

            if(appSession.getData(SessionConstant.BLOCKCHANIN_TYPE) != null)
                blockchainNetworkType = (BlockchainNetworkType)appSession.getData(SessionConstant.BLOCKCHANIN_TYPE);
            else
                blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

        } catch (Exception e) {
            lossProtectedWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());

        }
    }

    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected List<PreferenceSettingsItem> setSettingsItems() {

        final UUID exchangeProviderId2 ;
        UUID exchangeProviderId = null;
        final List<PreferenceSettingsItem> list = new ArrayList<>();
        try{



            list.add(new PreferenceSettingsSwithItem(1,getResources().getString(R.string.Enabled_notificacions),(Boolean) appSession.getData(SessionConstant.NOTIFICATION_ENABLED)));

            list.add(new PreferenceSettingsSwithItem(2,getResources().getString(R.string.Enabled_loss_protected),(Boolean) appSession.getData(SessionConstant.LOSS_PROTECTED_ENABLED)));



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



            final Bundle networkDialog = new Bundle();
            String items[] = new String[]{"MainNet", "TestNet"};
            networkDialog.putStringArray("items_array", items);
            networkDialog.putString("positive_button_text", getResources().getString(R.string.ok_label));
            networkDialog.putString("negative_button_text", getResources().getString(R.string.cancel_label));
            networkDialog.putString("title", getResources().getString(R.string.title_label));
            networkDialog.putString("mode", "single_option");
            networkDialog.putString("previous_selected_item", previousSelectedItem);
            list.add(new PreferenceSettingsOpenDialogText(5, getResources().getString(R.string.Select_network), networkDialog));


            // Exchange Rate Provider

            if (lossProtectedWalletManager.getExchangeProvider() != null)
                exchangeProviderId =  lossProtectedWalletManager.getExchangeProvider();

            exchangeProviderId2 = exchangeProviderId;

            getExecutor().submit(new Runnable() {
                @Override
                public void run() {

                    try {

                        List<ExchangeRateProvider> providers = new ArrayList<>(lossProtectedWalletManager.getExchangeRateProviderManagers());
                        String itemsProviders[] = new String[providers.size()];
                        for (int i = 0; i < providers.size(); i++) {
                            ExchangeRateProvider provider = providers.get(i);

                            itemsProviders[i] = provider.getProviderName();

                            if (provider.getProviderId().equals(exchangeProviderId2))
                                previousSelectedItemExchange = provider.getProviderName();
                        }

                        final Bundle providerDialog = new Bundle();
                        providerDialog.putStringArray("items_array", itemsProviders);
                        providerDialog.putString("positive_button_text", getResources().getString(R.string.ok_label));
                        providerDialog.putString("negative_button_text", getResources().getString(R.string.cancel_label));
                        providerDialog.putString("title", getResources().getString(R.string.exchange_title_label));
                        providerDialog.putString("mode", "single_option");
                        providerDialog.putString("previous_selected_item", previousSelectedItemExchange);


                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                list.add(new PreferenceSettingsOpenDialogText(10, getResources().getString(R.string.exchange_title_label), providerDialog));

                                adapter.changeDataSet(list);
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);
                            }
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });



           // list.add(new PreferenceSettingsLinkText(11, "Received Testnet Bitcoins", "", 15, Color.GRAY));
           list.add(new PreferenceSettingsLinkText(11, getResources().getString(R.string.Received_Testnet_Bitcoins), "", 15, Color.GRAY));

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
                        //Toast.makeText(getActivity(), "TestNet download Init", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), getResources().getString(R.string.TestNet_download), Toast.LENGTH_SHORT).show();
                        GETTestNet(getActivity());
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
                appSession.setData(SessionConstant.NOTIFICATION_ENABLED, isChecked);
            }

            if (preferenceSettingsItem.getId() == 2){
                //enable Loss Protected
                lossProtectedWalletSettings.setLossProtectedEnabled(isChecked);
                appSession.setData(SessionConstant.LOSS_PROTECTED_ENABLED, isChecked);
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
                    List<ExchangeRateProvider> providers = new ArrayList<>(lossProtectedWalletManager.getExchangeRateProviderManagers());

                    for (int i=0; i<providers.size(); i++) {
                        ExchangeRateProvider provider = providers.get(i);

                        if(provider.getProviderName().equals(item))

                                exchangeProviderId = provider.getProviderId();

                    }

                    lossProtectedWalletManager.setExchangeProvider(exchangeProviderId);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }







        try {

            lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(lossProtectedWalletSession.getAppPublicKey());
            lossProtectedWalletSettings.setBlockchainNetworkType(blockchainNetworkType);

            appSession.setData(SessionConstant.BLOCKCHANIN_TYPE, blockchainNetworkType);

            lossProtectedWalletManager.persistSettings(lossProtectedWalletSession.getAppPublicKey(), lossProtectedWalletSettings);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
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



    public void GETTestNet( final Context context){

        worker = new FermatWorker() {
            @Override
            protected Object doInBackground() throws Exception {

                String finalResponse = "";
                LossProtectedWalletContact walletContact = null;

                try {
                    CryptoAddress cryptoAddress = new CryptoAddress("mtMFTiGfBpjL1GBki8zrk5UW8otD6Gt541", CryptoCurrency.BITCOIN);

                    try {
                        walletContact = lossProtectedWalletManager.findWalletContactByName("Testnet_bitcoins", appSession.getAppPublicKey(), PublicKey);

                        if(walletContact == null)
                        {
                            walletContact = lossProtectedWalletManager.createWalletContact(
                                    cryptoAddress, "Testnet_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(),blockchainNetworkType);

                        }
                    } catch (CantFindLossProtectedWalletContactException | com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {

                        walletContact = lossProtectedWalletManager.createWalletContact(
                                cryptoAddress, "Testnet_bitcoins", "", "", Actors.EXTRA_USER, appSession.getAppPublicKey(),blockchainNetworkType);


                    } catch (ContactNameAlreadyExistsException |CantCreateLossProtectedWalletContactException e) {

                        finalResponse = "transaccion fallida";
                        e.printStackTrace();

                    } catch (Exception e) {
                        finalResponse = "transaccion fallida";
                        e.printStackTrace();
                    }

                    if(walletContact != null)
                        lossProtectedWalletManager.testNetGiveMeCoins(blockchainNetworkType, getWalletAddress(walletContact.getActorPublicKey()));

                }
                catch (CantGetCoinsFromFaucetException e) {
                    finalResponse = "transaccion fallida";
                    e.printStackTrace();
                }


                return finalResponse;

            }
        };
        worker.setContext(getActivity());
        worker.setCallBack(new FermatWorkerCallBack() {
            @SuppressWarnings("unchecked")
            @Override
            public void onPostExecute(Object... result) {

                if (result != null &&
                        result.length > 0) {
                    if (!result[0].toString().equals("transaccion fallida"))
                        //Toast.makeText(context, "TestNet bitcoin arrived", Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, getResources().getString(R.string.TestNet_bitcoin_arrived), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onErrorOccurred(Exception ex) {
                Toast.makeText(context, getResources().getString(R.string.TestNet_Request_Error), Toast.LENGTH_SHORT).show();

            }
        });
        worker.execute();


    }


    private CryptoAddress getWalletAddress(String actorPublicKey) {
        CryptoAddress walletAddres = null;
        try {
            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
            CryptoAddress cryptoAddress = lossProtectedWalletManager.requestAddressToKnownUser(
                    PublicKey,
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
            walletAddres = cryptoAddress;
        } catch (CantRequestLossProtectedAddressException e) {
            //errorManager.reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "CantRequestLossProtectedAddressException", Toast.LENGTH_SHORT).show();


        }
        catch (Exception e) {
         //   Toast.makeText(getActivity().getApplicationContext(), "ActorIdentityNotSelectedException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
       /* catch (CantGetSelectedActorIdentityException e) {
            Toast.makeText(getActivity().getApplicationContext(), "CantGetSelectedActorIdentityException", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            Toast.makeText(getActivity().getApplicationContext(), "ActorIdentityNotSelectedException", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }*/
        return walletAddres;
    }
}
