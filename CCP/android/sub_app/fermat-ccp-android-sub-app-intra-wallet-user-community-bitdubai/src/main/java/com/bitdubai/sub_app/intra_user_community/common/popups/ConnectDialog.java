package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;


/**
 * Created by Matias Furszyfer on 2015.08.12..
 * Changed by Jose Manuel De Sousa Dos Santos on 2015.12.03
 */

@SuppressWarnings("FieldCanBeLocal")
public class ConnectDialog extends FermatDialog<SubAppsSession, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */
    FermatTextView positiveBtn;
    FermatTextView negativeBtn;
    FermatTextView description;
    FermatTextView username;

    IntraUserInformation intraUserInformation;

    IntraUserLoginIdentity identity;


    public ConnectDialog(Activity a, IntraUserSubAppSession intraUserSubAppSession, SubAppResourcesProviderManager subAppResources, IntraUserInformation intraUserInformation, IntraUserLoginIdentity identity) {
        super(a, intraUserSubAppSession, subAppResources);
        this.intraUserInformation = intraUserInformation;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        description = (FermatTextView) findViewById(R.id.description);
        username = (FermatTextView) findViewById(R.id.user_name);

        positiveBtn = (FermatTextView) findViewById(R.id.positive_button);
        negativeBtn = (FermatTextView) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        description.setText("Want connect with ");
        username.setText(intraUserInformation.getName());

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
                //image null
                if (intraUserInformation != null && identity != null) {
                    ((IntraUserSubAppSession) getSession()).getModuleManager().askIntraUserForAcceptance(intraUserInformation.getName(), intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage(), identity.getPublicKey(), identity.getAlias());
                    Toast.makeText(getContext(), "Conected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantStartRequestException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "Oooops! recovering from system error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }


}