package com.bitdubai.reference_wallet.crypto_customer_wallet.fragments.settings;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.reference_wallet.crypto_customer_wallet.R;
import com.bitdubai.reference_wallet.crypto_customer_wallet.util.CommonLogger;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsActivityFragment extends AbstractFermatFragment {

    private static final String TAG = "SettingsActivityFragment";

    // Fermat Managers
    private ErrorManager errorManager;


    public static SettingsActivityFragment newInstance() {
        return new SettingsActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

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
        View layout = inflater.inflate(R.layout.ccw_fragment_settings, container, false);

        configureActionBar();

        View mylocationsBtn = layout.findViewById(R.id.settings_my_location);
        mylocationsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_MY_LOCATIONS, appSession.getAppPublicKey());
            }
        });

        View settingsBankAccountsBtn = layout.findViewById(R.id.settings_bank_account);
        settingsBankAccountsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_BANK_ACCOUNTS, appSession.getAppPublicKey());
            }
        });

        View settingsProvidersBtn = layout.findViewById(R.id.settings_providers);
        settingsProvidersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_PROVIDERS, appSession.getAppPublicKey());
            }
        });

        View settingsManagementFee = layout.findViewById(R.id.settings_management_fee);
        settingsManagementFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeActivity(Activities.CBP_CRYPTO_CUSTOMER_WALLET_SETTINGS_MANAGEMENT_FEE, appSession.getAppPublicKey());
            }
        });


        return layout;
    }

    private void configureActionBar() {
        Toolbar toolbar = getToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors, null));
        else
            toolbar.setBackground(getResources().getDrawable(R.drawable.ccw_action_bar_gradient_colors));
    }
}
