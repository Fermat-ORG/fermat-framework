package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConectionDenegationFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;

/**
 * Created by root on 12/11/15.
 */
public class AcceptDialog extends FermatDialog<SubAppsSession,SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     *  UI components
     */
    Button btn_connect;
    Button btn_cancel;
    FermatTextView txt_person_to_connect;

    IntraUserInformation intraUserInformation;

    IntraUserLoginIdentity identity;

    public AcceptDialog(Activity a,IntraUserSubAppSession intraUserSubAppSession,SubAppResourcesProviderManager subAppResources,IntraUserInformation intraUserInformation,IntraUserLoginIdentity identity) {
        super(a, intraUserSubAppSession, subAppResources);
        this.intraUserInformation = intraUserInformation;
        this.identity = identity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txt_person_to_connect = (FermatTextView)findViewById(R.id.txt_person_to_connect);
        btn_connect =(FermatButton) findViewById(R.id.btn_connect);
        btn_cancel = (FermatButton) findViewById(R.id.btn_close);

        btn_connect.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


        txt_person_to_connect.setText("Do you want to accept  "+ intraUserInformation.getName());

    }



    @Override
    protected int setLayoutId() {
        return R.layout.connect_dialog_layout;
    }

    @Override
    protected int setWindowFeacture() {
        return Window.FEATURE_NO_TITLE;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_connect) {
            try {
                if(intraUserInformation!=null && identity!=null) {
                    ((IntraUserSubAppSession) getSession()).getIntraUserModuleManager().acceptIntraUser(identity.getPublicKey(), intraUserInformation.getName(), intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage());
                    Toast.makeText(getContext(), intraUserInformation.getName() + " accepted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }
            dismiss();
        }else if( i == R.id.btn_close){
            try {
                if(intraUserInformation!=null && identity!=null)
                    ((IntraUserSubAppSession)getSession()).getIntraUserModuleManager().denyConnection(intraUserInformation.getPublicKey());
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
