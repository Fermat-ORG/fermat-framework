package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;

import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.BitcoinFee;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletAssociatedSetting;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.setting.CryptoBrokerWalletSettingFee;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;

import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/5/16.
 */
public class SettingsFeeManagementFragment extends FermatWalletListFragment<CryptoBrokerWalletAssociatedSetting,ReferenceAppFermatSession<CryptoBrokerWalletModuleManager>,ResourceProviderManager>  {

    // Constants
    private static final String TAG = "SettingsFeeManagement";

    //DATA
    private long bitoinFee=0;
    private String feeOrigin="";

    private CryptoBrokerWalletSettingFee feeSettings;

    //UI

    private RadioGroup radioButtonGroup;
    private FermatTextView feeMinerAmount;
    private Spinner feeOriginSpinner;

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private ErrorManager errorManager;


    public static SettingsFeeManagementFragment newInstance() { return new SettingsFeeManagementFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get managers
        try {
            moduleManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_THIS_FRAGMENT, ex);
        }

        //Get Fee Settings
        try {

            feeSettings = moduleManager.getCryptoBrokerWalletSettingFee("walletPublicKeyTest");

            if (feeSettings != null) {
                feeOrigin=feeSettings.getFeeOrigin();
                bitoinFee=feeSettings.getBitcoinFee();
            }
        } catch (FermatException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            if (errorManager != null) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_BROKER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        ex);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        configureToolbar();
        View layout = inflater.inflate(R.layout.cbw_settings_fee_management, container, false);


        radioButtonGroup = (RadioGroup)layout.findViewById(R.id.cbw_radio_button_group);
        feeMinerAmount=(FermatTextView) layout.findViewById(R.id.cbw_fee_miner_amount);
        feeOriginSpinner=(Spinner)layout.findViewById(R.id.cbw_country_spinner);

        feeMinerAmount.setText(String.valueOf(BitcoinFee.SLOW.getFee() / 100000000) + "BTC");
        bitoinFee=BitcoinFee.SLOW.getFee();

        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.cbw_radio_button_slow) {
                    feeMinerAmount.setText(String.valueOf(BitcoinFee.SLOW.getFee() / 100000000) + "BTC");
                    BitcoinFee.SLOW.getFee();
                } else if (checkedId == R.id.cbw_radio_button_normal) {
                    feeMinerAmount.setText(String.valueOf(BitcoinFee.NORMAL.getFee() / 100000000) + "BTC");
                    BitcoinFee.NORMAL.getFee();
                } else if (checkedId == R.id.cbw_radio_button_fast) {
                    BitcoinFee.FAST.getFee();
                    feeMinerAmount.setText(String.valueOf(BitcoinFee.FAST.getFee() / 100000000) + "BTC");
                }

            }

        });


        List<String> feeOriginValues = new ArrayList<String>();
        feeOriginValues.add(FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT.getCode());
        feeOriginValues.add(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS.getCode());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_item,feeOriginValues);

        dataAdapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);

        feeOriginSpinner.setAdapter(dataAdapter);


        final View nextStepButton = layout.findViewById(R.id.cbw_save_fee_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingsAndGoBack();
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });


        if(feeSettings!=null){

            feeOriginSpinner.setSelection(dataAdapter.getPosition(feeOrigin));
            feeMinerAmount.setText(String.valueOf(bitoinFee/100000000)+"BTC");
        }


        return layout;
    }




    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.cbw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void saveSettingsAndGoBack() {

        try {
            CryptoBrokerWalletSettingFee walletSetting = moduleManager.newCryptoBrokerWalletSettingFee();
            walletSetting.setBitcoinFee(bitoinFee);
            walletSetting.setFeeOrigin(feeOriginSpinner.getSelectedItem().toString());
            walletSetting.setFeeOrigin(appSession.getAppPublicKey());

            moduleManager.saveWalletSetting(walletSetting, appSession.getAppPublicKey());

        } catch (FermatException ex) {
            Toast.makeText(SettingsFeeManagementFragment.this.getActivity(), "There was a problem saving your settings", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS, appSession.getAppPublicKey());
    }



    @Override
    protected boolean hasMenu() {
        return true;
    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return 0;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return 0;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    public void onPostExecute(Object... result) {

    }

    @Override
    public void onErrorOccurred(Exception ex) {

    }


    @Override
    public FermatAdapter getAdapter() {
        return null;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return null;
    }
}

