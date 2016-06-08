package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatEditText;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;


import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by josemanueldsds on 20/01/16.
 */
public class SettingsMainNetworkFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoWallet>,ResourceProviderManager> {

    private View rootView;

    private CryptoWallet cryptoWallet;
    private FermatEditText port;
    private FermatEditText ipAdress;
    private Spinner spinner;



    public static SettingsMainNetworkFragment newInstance() {
        return new SettingsMainNetworkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            cryptoWallet = appSession.getModuleManager();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } catch (Exception e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.settings_main_network, container, false);
            setUpUi();
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    public void setUpUi() {
        port = (FermatEditText) rootView.findViewById(R.id.edit_text_port);
        ipAdress = (FermatEditText) rootView.findViewById(R.id.edit_text_ip_adress);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("MainNet");
        list.add("TestNet");
        list.add("RegTest");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_spinner, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                String text = spinner.getSelectedItem().toString();
                BlockchainNetworkType blockchainNetworkType;

                try {

                    if(text.equals("MainNet")){
                        blockchainNetworkType = BlockchainNetworkType.PRODUCTION;
                    }else if(text.equals("TestNet")){
                        blockchainNetworkType = BlockchainNetworkType.TEST_NET;
                    }else if(text.equals("RegTest")){
                        blockchainNetworkType = BlockchainNetworkType.REG_TEST;
                    }else{
                        blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();
                    }


                    System.out.println("NETWORK TYPE SELECTED IS "+text);
                    System.out.println("NETWORK TYPE TO BE SAVED IS  " + blockchainNetworkType.getCode());

                    BitcoinWalletSettings bitcoinWalletSettings = cryptoWallet.loadAndGetSettings(appSession.getAppPublicKey());
                    bitcoinWalletSettings.setIsPresentationHelpEnabled(false);
                    bitcoinWalletSettings.setBlockchainNetworkType(blockchainNetworkType);
                    cryptoWallet.persistSettings(appSession.getAppPublicKey(),bitcoinWalletSettings);
                } catch (CantGetSettingsException e) {
                    e.printStackTrace();
                } catch (SettingsNotFoundException e) {
                    e.printStackTrace();
                } catch (CantPersistSettingsException e) {
                    e.printStackTrace();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

}
