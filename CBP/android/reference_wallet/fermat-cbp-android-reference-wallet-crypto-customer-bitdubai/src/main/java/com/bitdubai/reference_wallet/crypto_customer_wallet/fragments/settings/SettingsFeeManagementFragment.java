package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
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

import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.CryptoCustomerWalletModuleManager;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_customer.interfaces.settings.CryptoCustomerWalletPreferenceSettings;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT;
import static com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT;

/**
 * Created by Miguel Payarez (miguel_payarez@hotmail.com) on 7/5/16.
 */
public class SettingsFeeManagementFragment extends
        AbstractFermatFragment<ReferenceAppFermatSession<CryptoCustomerWalletModuleManager>, ResourceProviderManager> {

    // Constants
    private static final String TAG = "SettingsFeeManagement";

    //

    private long bitoinFee=0;
    private String feeOrigin="";
    private DecimalFormat df=new DecimalFormat("0.00000000");


    CryptoCustomerWalletPreferenceSettings feeSettings;
    //UI


    private RadioGroup radioButtonGroup;
    private RadioButton radioButtonSlow;
    private RadioButton radioButtonNormal;
    private RadioButton radioButtonFast;
    private FermatTextView feeMinerAmount;
    private Spinner feeOriginSpinner;

    // Fermat Managers
    private CryptoCustomerWalletModuleManager moduleManager;
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
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET, DISABLES_THIS_FRAGMENT, ex);
        }
        feeSettings=null;

        try {
            feeSettings = moduleManager.loadAndGetSettings(appSession.getAppPublicKey());

        } catch (Exception e) {
            feeSettings = null;
        }

        if ( feeSettings== null) {
            feeSettings = new CryptoCustomerWalletPreferenceSettings();


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        configureToolbar();

        View layout = inflater.inflate(R.layout.ccw_settings_fee_management, container, false);


        radioButtonGroup = (RadioGroup)layout.findViewById(R.id.ccw_radio_button_group);
        feeMinerAmount=(FermatTextView) layout.findViewById(R.id.ccw_fee_miner_amount);
        feeOriginSpinner=(Spinner)layout.findViewById(R.id.ccw_fee_origin_spinner);

        radioButtonSlow=(RadioButton)layout.findViewById(R.id.ccw_radio_button_slow);
        radioButtonNormal=(RadioButton)layout.findViewById(R.id.ccw_radio_button_normal);
        radioButtonFast=(RadioButton)layout.findViewById(R.id.ccw_radio_button_fast);

        feeMinerAmount.setText(satoshiToBtcFormat(BitcoinFee.SLOW.getFee()) + "BTC");
        bitoinFee=BitcoinFee.SLOW.getFee();

        radioButtonGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.ccw_radio_button_slow) {
                    feeMinerAmount.setText(" "+satoshiToBtcFormat(BitcoinFee.SLOW.getFee()) + " BTC");
                    bitoinFee=BitcoinFee.SLOW.getFee();
                } else if (checkedId == R.id.ccw_radio_button_normal) {
                    feeMinerAmount.setText(" "+satoshiToBtcFormat(BitcoinFee.NORMAL.getFee()) + " BTC");
                    bitoinFee=BitcoinFee.NORMAL.getFee();
                } else if (checkedId == R.id.ccw_radio_button_fast) {
                    feeMinerAmount.setText(" "+satoshiToBtcFormat(BitcoinFee.FAST.getFee()) + " BTC");
                    bitoinFee=BitcoinFee.FAST.getFee();
                }

            }

        });


        List<String> feeOriginValues = new ArrayList<String>();
        feeOriginValues.add(FeeOrigin.SUBSTRACT_FEE_FROM_AMOUNT.getCode());
        feeOriginValues.add(FeeOrigin.SUBSTRACT_FEE_FROM_FUNDS.getCode());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getActivity(), R.layout.ccw_spinner_item,feeOriginValues);

        dataAdapter.setDropDownViewResource(R.layout.ccw_simple_spinner_dropdown_item);

        feeOriginSpinner.setAdapter(dataAdapter);


        final View nextStepButton = layout.findViewById(R.id.ccw_save_fee_button);
        nextStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettingsAndGoBack();
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS, appSession.getAppPublicKey());
            }
        });


        if(feeSettings!=null) {

            feeOriginSpinner.setSelection(dataAdapter.getPosition(feeSettings.getFeeOrigin().getCode()));
            feeMinerAmount.setText(satoshiToBtcFormat(feeSettings.getBitcoinFee().getFee()) + " BTC");
            selectSpinnerValue(feeSettings.getBitcoinFee().getFee());

        }


        return layout;
    }

    private void selectSpinnerValue(long bitCoinFee){

        if (bitCoinFee == BitcoinFee.SLOW.getFee()) {
            radioButtonSlow.setChecked(true);
            radioButtonNormal.setChecked(false);
            radioButtonFast.setChecked(false);
        } else if (bitCoinFee == BitcoinFee.NORMAL.getFee()){
            radioButtonSlow.setChecked(false);
            radioButtonNormal.setChecked(true);
            radioButtonFast.setChecked(false);
        }else if(bitCoinFee==BitcoinFee.FAST.getFee()) {
            radioButtonSlow.setChecked(false);
            radioButtonNormal.setChecked(false);
            radioButtonFast.setChecked(true);
        }
    }



    private String satoshiToBtcFormat(Long satoshi){

        return df.format(Double.valueOf(satoshi) / 100000000);

    }

    private void configureToolbar() {
        Toolbar toolbar = getToolbar();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));

        toolbar.setTitleTextColor(Color.WHITE);
        if (toolbar.getMenu() != null) toolbar.getMenu().clear();
    }

    private void saveSettingsAndGoBack() {

        try {
            feeSettings.setBitcoinFee(BitcoinFee.getByFee(bitoinFee));
            feeSettings.setFeeOrigin(FeeOrigin.getByCode(feeOriginSpinner.getSelectedItem().toString()));
            try {
                moduleManager.persistSettings(appSession.getAppPublicKey(), feeSettings);
            } catch (Exception e) {
                errorManager.reportUnexpectedWalletException(
                        Wallets.CBP_CRYPTO_CUSTOMER_WALLET,
                        DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,
                        e);}

        } catch (FermatException ex) {
            Toast.makeText(SettingsFeeManagementFragment.this.getActivity(), "There was a problem saving your settings", Toast.LENGTH_SHORT).show();

            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_CUSTOMER_WALLET, DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            else
                Log.e(TAG, ex.getMessage(), ex);
        }

        changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS, appSession.getAppPublicKey());
    }




}

