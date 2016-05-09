package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.settings;


import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsActivityFragment extends AbstractFermatFragment<CryptoBrokerWalletSession, ResourceProviderManager> {

    // Constants
    private static final String TAG = "SettingsActivityFragment";

    // Fermat Managers
    private ErrorManager errorManager;


    public static SettingsActivityFragment newInstance() {
        return new SettingsActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            errorManager = appSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.cbw_fragment_settings, container, false);
        configureToolbar();

        View mylocationsBtn = layout.findViewById(R.id.settings_my_location);
        mylocationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS_MY_LOCATIONS, appSession.getAppPublicKey());
            }
        });

        View bankButton = layout.findViewById(R.id.settings_bank_account);
        bankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS_BANK_ACCOUNT, appSession.getAppPublicKey());
            }
        });

        View merchandiseButton = layout.findViewById(R.id.settings_stock_managment);
        merchandiseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS_STOCK_MERCHANDISES, appSession.getAppPublicKey());
            }
        });




        /*View earningsButton = layout.findViewById(R.id.settings_public_information);
        earningsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_BROKER_WALLET_SETTINGS_MERCHANDISES, appSession.getAppPublicKey());
            }
        });*/

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


}
