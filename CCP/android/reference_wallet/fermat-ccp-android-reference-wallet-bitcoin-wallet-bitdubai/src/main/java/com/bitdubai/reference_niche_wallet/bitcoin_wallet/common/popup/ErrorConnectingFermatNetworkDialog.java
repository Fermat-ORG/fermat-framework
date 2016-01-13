package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.interfaces.ErrorConnectingFermatNetwork;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

/**
 * Created by natalia on 13/01/16.
 */
public class ErrorConnectingFermatNetworkDialog extends FermatDialog<ReferenceWalletSession,WalletResourcesProviderManager> implements View.OnClickListener {

    /**
     * Interfaces
     */
    private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
    /**
     * UI components
     */
    private FermatTextView positiveBtn;
    private FermatTextView negativeBtn;
    private final Activity activity;


    public ErrorConnectingFermatNetworkDialog(Activity activity, ReferenceWalletSession fermatSession, WalletResourcesProviderManager resources) {
        super(activity, fermatSession, resources);
        this.activity = activity;

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        positiveBtn = (FermatTextView) findViewById(R.id.positive_button);
        negativeBtn = (FermatTextView) findViewById(R.id.negative_button);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_error_conecting_fermat_network;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.positive_button) {
            dismiss();
        }
        if (i == R.id.negative_button) {
            if (errorConnectingFermatNetwork != null) {
                errorConnectingFermatNetwork.errorConnectingFermatNetwork(false);
            }
            dismiss();
        }
    }


    public void setErrorConnectingFermatNetwork(ErrorConnectingFermatNetwork errorConnectingFermatNetwork) {
        this.errorConnectingFermatNetwork = errorConnectingFermatNetwork;
    }
}