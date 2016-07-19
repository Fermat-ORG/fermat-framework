package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConnectionDenialFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.R;

import com.bitdubai.sub_app.intra_user_community.session.SessionConstants;
import android.content.Intent;
/**
 * Created by Joaquin C on 12/11/15.
 * Modified by Jose Manuel De Sousa 08/12/2015
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final IntraUserInformation   intraUserInformation;
    private final IntraUserLoginIdentity identity            ;

    private final Activity                       activity;
    private final ReferenceAppFermatSession intraUserSubAppSession;
    private final SubAppResourcesProviderManager subAppResources ;

    private FermatTextView title      ;
    private FermatTextView description;
    private FermatTextView userName   ;
    private FermatButton   positiveBtn;
    private FermatButton   negativeBtn;

    private FermatTextView textConnectionSuccess;
    private View ConnectionSuccess;
    private boolean result = false;

    public AcceptDialog(final Activity                       activity              ,
                        final ReferenceAppFermatSession intraUserSubAppSession,
                        final SubAppResourcesProviderManager subAppResources       ,
                        final IntraUserInformation           intraUserInformation  ,
                        final IntraUserLoginIdentity         identity              ) {

        super(activity, intraUserSubAppSession, subAppResources);



        this.intraUserInformation = intraUserInformation;
        this.identity             = identity            ;
        this.activity = activity;
        this.intraUserSubAppSession = intraUserSubAppSession;
        this.subAppResources = subAppResources;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            title       = (FermatTextView) findViewById(R.id.title);
            description = (FermatTextView) findViewById(R.id.description);
            userName    = (FermatTextView) findViewById(R.id.second_description);
            positiveBtn = (FermatButton)   findViewById(R.id.positive_button);
            negativeBtn = (FermatButton)   findViewById(R.id.negative_button);

            positiveBtn.setOnClickListener(this);
            negativeBtn.setOnClickListener(this);

            title.setText("CONNECTION REQUEST");
            title.setTextColor(Color.BLACK);
            description.setText("New Connection");
            description.setTextColor(Color.parseColor("#5ddad1"));
            userName.setText(intraUserInformation.getName() + " wants to connect with you");
            userName.setTextColor(Color.parseColor("#3f3f3f"));
            userName.setVisibility(View.VISIBLE);
            userName.setTextSize(14);
            //userName.addRule(RelativeLayout.BELOW, R.id.below_id)
            positiveBtn.setTextColor(Color.BLACK);
            negativeBtn.setTextColor(Color.parseColor("#3f3f3f"));
        }catch(Exception exc){
            Toast.makeText(getContext(), "Whoops! an unespected error has ocurred!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccp_comm_dialog_builder;
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
                if (intraUserInformation != null && identity != null) {
                    getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(), intraUserInformation.getName(), intraUserInformation.getPublicKey(), intraUserInformation.getProfileImage());
                    getSession().setData(SessionConstants.NOTIFICATION_ACCEPTED, Boolean.TRUE);
                    //Toast.makeText(getContext(), intraUserInformation.getName() + " Accepted connection request", Toast.LENGTH_SHORT).show();
                    //Crear un nuevo intent
                 //   Intent intent = new Intent(AccpetMessage);
                 //   startActivity(intent);
                    result = true;
                } else {
                    super.toastDefaultError();
                }
                dismiss();
            } catch (final CantAcceptRequestException e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        } else if (i == R.id.negative_button) {
            try {
                if (intraUserInformation != null && identity != null)
                    getSession().getModuleManager().denyConnection(identity.getPublicKey(), intraUserInformation.getPublicKey());
                else {
                    super.toastDefaultError();
                }
                dismiss();
            } catch (final IntraUserConnectionDenialFailedException e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        }
    }

    public boolean getResultado(){
        return result;
    }
}
