package com.bitdubai.reference_wallet.crypto_broker_wallet.fragments.common;


import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWallet;
import com.bitdubai.fermat_cbp_api.layer.wallet_module.crypto_broker.interfaces.CryptoBrokerWalletModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_wallet.crypto_broker_wallet.R;
import com.bitdubai.reference_wallet.crypto_broker_wallet.session.CryptoBrokerWalletSession;
import com.bitdubai.reference_wallet.crypto_broker_wallet.util.CommonLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenNegotiationDetailsFragment extends FermatWalletFragment {
    private static final String TAG = "OpenNegotiationDetails";

    // Fermat Managers
    private CryptoBrokerWalletModuleManager moduleManager;
    private CryptoBrokerWallet walletManager;
    private ErrorManager errorManager;


    public static OpenNegotiationDetailsFragment newInstance() {
        return new OpenNegotiationDetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            moduleManager = ((CryptoBrokerWalletSession) walletSession).getModuleManager();
            walletManager = moduleManager.getCryptoBrokerWallet(walletSession.getAppPublicKey());

            errorManager = walletSession.getErrorManager();
        } catch (Exception ex) {
            CommonLogger.exception(TAG, ex.getMessage(), ex);
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CBP_CRYPTO_BROKER_WALLET,
                        UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, ex);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cbw_fragment_negotiation_details_activity, container, false);

        configureToolbar();

        initViews(rootView);

        return rootView;
    }

    private void initViews(View rootView) {
        final Button paymentDateValue = (Button) rootView.findViewById(R.id.payment_date_value);
        paymentDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(paymentDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        final Button paymentTimeValue = (Button) rootView.findViewById(R.id.payment_time_value);
        paymentTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(paymentTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        final Button deliveryDateValue = (Button) rootView.findViewById(R.id.delivery_date_value);
        deliveryDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(deliveryDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        final Button deliveryTimeValue = (Button) rootView.findViewById(R.id.delivery_time_value);
        deliveryTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(deliveryTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });

        final Button expirationDateValue = (Button) rootView.findViewById(R.id.expiration_date_value);
        expirationDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = DatePickerFragment.getNewInstance(expirationDateValue);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        final Button expirationTimeValue = (Button) rootView.findViewById(R.id.expiration_time_value);
        expirationTimeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = TimePickerFragment.getNewInstance(expirationTimeValue);
                timePicker.show(getFragmentManager(), "timePicker");
            }
        });
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
