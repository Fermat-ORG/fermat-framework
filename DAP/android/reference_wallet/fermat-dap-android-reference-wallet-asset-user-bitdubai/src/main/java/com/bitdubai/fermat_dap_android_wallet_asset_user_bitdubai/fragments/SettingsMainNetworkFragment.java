package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.fragments;

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

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.sessions.AssetUserSession;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.AssetUserSettings;
import com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_user.interfaces.AssetUserWalletSubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;


import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;


/**
 * Modified by Penelope Quintero for Asset User Wallet on 2016.02.02
 */
public class SettingsMainNetworkFragment extends AbstractFermatFragment implements AdapterView.OnItemSelectedListener {

    private View rootView;
    private AssetUserSession assetUserSession;
    private Spinner spinner;
    List<BlockchainNetworkType> listElementSpinner;

    private AssetUserWalletSubAppModuleManager moduleManager;

    SettingsManager<AssetUserSettings> settingsManager;
    private ErrorManager errorManager;
    AssetUserSettings settings = null;

    public static SettingsMainNetworkFragment newInstance() {
        return new SettingsMainNetworkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        moduleManager = ((AssetUserSession) appSession).getModuleManager();
        errorManager = appSession.getErrorManager();

        settingsManager = appSession.getModuleManager().getSettingsManager();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.dap_wallet_asset_user_settings_main_network, container, false);
            try {
                settings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());
            } catch (Exception e) {
                settings = null;
            }
            if (settings != null) {
                listElementSpinner = settings.getBlockchainNetwork();
            }

            setUpUi();
//            configureToolbar();

            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), R.string.dap_user_wallet_opps_system_error, Toast.LENGTH_SHORT).show();
            errorManager.reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }

        return null;
    }

    public void setUpUi() {
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
//        listElementSpinner.add("MainNet");
//        listElementSpinner.add("TestNet");
//        listElementSpinner.add("RegTest");
        ArrayAdapter<BlockchainNetworkType> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.dap_wallet_asset_user_list_item_spinner, listElementSpinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter.notifyDataSetChanged();
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setSelection(settings.getBlockchainNetworkPosition());
    }

    private void managerSettings(BlockchainNetworkType dataSet, int position) {
        try {
            settings.setBlockchainNetworkPosition(position);

            settingsManager.persistSettings(appSession.getAppPublicKey(), settings);
            moduleManager.changeNetworkType(dataSet);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        BlockchainNetworkType network;

        network = listElementSpinner.get(i);
        adapterView.setSelection(i);

        managerSettings(network, i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
