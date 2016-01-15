package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.interfaces.ErrorConnectingFermatNetwork;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 * Changed by Jose Manuel De Sousa Dos Santos on 2015.12.03
 */
@SuppressWarnings("FieldCanBeLocal")
public class ErrorConnectingFermatNetworkDialog extends FermatDialog<IntraUserSubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * Interfaces
     */
    private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
    /**
     * UI components
     */
    private FermatTextView positiveBtn;
    private FermatTextView negativeBtn;


    public ErrorConnectingFermatNetworkDialog(final Activity a,
                                              final IntraUserSubAppSession intraUserSubAppSession,
                                              final SubAppResourcesProviderManager subAppResources) {

        super(a, intraUserSubAppSession, subAppResources);

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