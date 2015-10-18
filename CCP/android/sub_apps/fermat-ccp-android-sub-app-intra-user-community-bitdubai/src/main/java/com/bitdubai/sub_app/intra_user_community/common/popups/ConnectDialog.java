package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantStartRequestException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResources;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.session.IntraUserSubAppSession;


/**
 * Created by Matias Furszyfer on 2015.08.12..
 */

public class ConnectDialog extends FermatDialog<SubAppsSession,SubAppResourcesProviderManager> implements
        View.OnClickListener {


    /**
     *  UI components
     */
    Button btn_connect;
    Button btn_cancel;
    FermatTextView txt_person_to_connect;

    IntraUserInformation intraUserInformation;



    public ConnectDialog(Activity a,IntraUserSubAppSession intraUserSubAppSession,SubAppResourcesProviderManager subAppResources,IntraUserInformation intraUserInformation) {
        super(a, intraUserSubAppSession, subAppResources);
        this.intraUserInformation = intraUserInformation;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        txt_person_to_connect = (FermatTextView)findViewById(R.id.txt_person_to_connect);
        btn_connect =(FermatButton) findViewById(R.id.btn_connect);
        btn_cancel = (FermatButton) findViewById(R.id.btn_close);

        btn_connect.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);


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
                //image null
                ((IntraUserSubAppSession)getSession()).getIntraUserModuleManager().askIntraUserForAcceptance(intraUserInformation.getName(),intraUserInformation.getPublicKey(),null);
            } catch (CantStartRequestException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE,e);
                Toast.makeText(getOwnerActivity(), "Oooops! recovering from system error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }else if( i == R.id.btn_close){
            dismiss();
        }
    }








}