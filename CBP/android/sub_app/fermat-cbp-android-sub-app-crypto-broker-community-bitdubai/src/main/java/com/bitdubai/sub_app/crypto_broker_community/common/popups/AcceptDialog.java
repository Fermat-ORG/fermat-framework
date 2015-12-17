package com.bitdubai.sub_app.crypto_broker_community.common.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConectionDenegationFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Joaquin C on 12/11/15.
 * Modified by Jose Manuel De Sousa 08/12/2015
 */
public class AcceptDialog extends FermatDialog<SubAppsSession, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */

    IntraUserInformation intraUserInformation;

    IntraUserLoginIdentity identity;
    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;

    public AcceptDialog(Activity a, CryptoBrokerCommunitySubAppSession cryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager subAppResources, IntraUserInformation intraUserInformation, IntraUserLoginIdentity identity) {
        super(a, cryptoBrokerCommunitySubAppSession, subAppResources);
        this.intraUserInformation = intraUserInformation;
        this.identity = identity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = (FermatTextView) findViewById(R.id.title);
        description = (FermatTextView) findViewById(R.id.description);
        userName = (FermatTextView) findViewById(R.id.user_name);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Conect");
        description.setText("Do you want to accept");
        userName.setText(intraUserInformation.getName());

    }

    @Override
    protected int setLayoutId() {
        return R.layout.dialog_builder;
    }

    @Override
    protected int setWindowFeacture() {
        return Window.FEATURE_NO_TITLE;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.positive_button) {
            try {
                if (intraUserInformation != null && identity != null) {
                    ((CryptoBrokerCommunitySubAppSession) getSession()).getModuleManager().acceptIntraUser(identity.getPublicKey(), intraUserInformation.getName(), intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage());
                    Toast.makeText(getContext(), intraUserInformation.getName() + " accepted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            try {
                if (intraUserInformation != null && identity != null)
                    ((CryptoBrokerCommunitySubAppSession) getSession()).getModuleManager().denyConnection(identity.getPublicKey(), intraUserInformation.getPublicKey());
                else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (IntraUserConectionDenegationFailedException e) {
                e.printStackTrace();
            }
            dismiss();
        }
    }


}
