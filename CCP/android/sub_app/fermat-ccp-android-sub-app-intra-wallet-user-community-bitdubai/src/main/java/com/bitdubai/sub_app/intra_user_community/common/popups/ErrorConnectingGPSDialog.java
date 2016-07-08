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


/**
 * Created by Andres Abreu on 2016.07.07.
 */
@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ErrorConnectingGPSDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * Interfaces
     */
   // private ErrorConnectingFermatNetwork errorConnectingFermatNetwork;
    /**
     * onClick listeners
     */
    private View.OnClickListener CloseClick;
    /**
     * Positive and negative button option text
     */

    private CharSequence CloseButton;
    /**
     * UI components
     */
    private FermatTextView mDescrip;
    private CharSequence descrip;

    public ErrorConnectingGPSDialog(final Activity a,
                                    final ReferenceAppFermatSession intraUserSubAppSession,
                                    final SubAppResourcesProviderManager subAppResources) {

        super(a, intraUserSubAppSession, subAppResources);

    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FermatTextView positiveButtonView = (FermatTextView) findViewById(R.id.Close_button);

        mDescrip = (FermatTextView) findViewById(R.id.descrip);
        positiveButtonView.setOnClickListener(CloseClick);
        mDescrip.setText(descrip != null ? descrip : "");
        positiveButtonView.setText(CloseButton != null ? CloseButton : "");
        positiveButtonView.setOnClickListener(CloseClick);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_error_conecting_gps;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.Close_button) {
            dismiss();
        }

    }


    /**
     * Set close button listener and text
     *
     * @param text    CharSequence
     * @param onClick View.OnClickListener
     */
    public void setCloseButton(CharSequence text, View.OnClickListener onClick) {
        CloseClick = onClick;
        CloseButton = text;
    }

    public void setDescription(CharSequence descrip) {
        this.descrip = descrip;

    }
}