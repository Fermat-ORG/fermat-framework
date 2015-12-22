package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;


/**
 * Created by Matias Furszyfer on 2015.08.12..
 * Changed by Jose Manuel De Sousa Dos Santos on 2015.12.03
 */

@SuppressWarnings("FieldCanBeLocal")
public class DisconectDialog extends FermatDialog<SubAppsSession, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */
    FermatButton positiveBtn;
    FermatButton negativeBtn;
    FermatTextView mDescription;
    FermatTextView mUsername;
    FermatTextView mTitle;
    CharSequence description;
    CharSequence username;
    CharSequence title;

    IntraUserInformation intraUserInformation;

    IntraUserLoginIdentity identity;


    public DisconectDialog(Activity a, IntraUserSubAppSession intraUserSubAppSession, SubAppResourcesProviderManager subAppResources, IntraUserInformation intraUserInformation, IntraUserLoginIdentity identity) {
        super(a, intraUserSubAppSession, subAppResources);
        this.intraUserInformation = intraUserInformation;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescription = (FermatTextView) findViewById(R.id.description);
        mUsername = (FermatTextView) findViewById(R.id.user_name);
        mTitle = (FermatTextView) findViewById(R.id.title);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mDescription.setText(description != null ? description : "");
        mUsername.setText(username != null ? username : "");
        mTitle.setText(title != null ? title : "");

    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public void setUsername(CharSequence username) {
        this.username = username;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_builder;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.positive_button) {
            try {
                //image null
                if (intraUserInformation != null && identity != null) {
                    ((IntraUserSubAppSession) getSession()).getModuleManager().disconnectIntraUSer(intraUserInformation.getPublicKey());
                    Intent broadcast = new Intent(Constants.LOCAL_BROADCAST_CHANNEL);
                    broadcast.putExtra(Constants.BROADCAST_DISCONNECTED_UPDATE, true);
                    sendLocalBroadcast(broadcast);
                    Toast.makeText(getContext(), "Disconnect", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (IntraUserDisconnectingFailedException e) {
                e.printStackTrace();
            }

            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }


}