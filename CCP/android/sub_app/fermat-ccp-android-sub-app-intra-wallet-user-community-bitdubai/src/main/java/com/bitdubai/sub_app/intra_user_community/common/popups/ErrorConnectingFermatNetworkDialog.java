package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.interfaces.ErrorConnectingFermatNetwork;


/**
 * Created by Matias Furszyfer on 2015.08.12..
 * Changed by Jose Manuel De Sousa Dos Santos on 2015.12.03
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ErrorConnectingFermatNetworkDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * Interfaces
     */
    private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
    /**
     * onClick listeners
     */
    private View.OnClickListener leftClick;
    private View.OnClickListener rightClick;
    /**
     * Positive and negative button option text
     */
    private CharSequence leftButton;
    private CharSequence rightButton;
    /**
     * UI components
     */
    private FermatTextView mDescription;
    private CharSequence description;

    public ErrorConnectingFermatNetworkDialog(final Activity a,
                                              final ReferenceAppFermatSession intraUserSubAppSession,
                                              final SubAppResourcesProviderManager subAppResources) {

        super(a, intraUserSubAppSession, subAppResources);

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FermatTextView positiveButtonView = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeButtonView = (FermatTextView) findViewById(R.id.negative_button);
        mDescription = (FermatTextView) findViewById(R.id.description);
        positiveButtonView.setOnClickListener(rightClick);
        negativeButtonView.setOnClickListener(leftClick);
        mDescription.setText(description != null ? description : "");
        positiveButtonView.setText(leftButton != null ? leftButton : "");
        negativeButtonView.setText(rightButton != null ? rightButton : "");
        positiveButtonView.setOnClickListener(leftClick);
        negativeButtonView.setOnClickListener(rightClick);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.comm_dialog_error_conecting_fermat_network;
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


    /**
     * Set positive button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setLeftButton(CharSequence text, View.OnClickListener onClick) {
        leftClick = onClick;
        leftButton = text;
    }

    /**
     * Set negative button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setRightButton(CharSequence text, View.OnClickListener onClick) {
        rightClick = onClick;
        rightButton = text;
    }

    public void setErrorConnectingFermatNetwork(ErrorConnectingFermatNetwork errorConnectingFermatNetwork) {
        this.errorConnectingFermatNetwork = errorConnectingFermatNetwork;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }
}