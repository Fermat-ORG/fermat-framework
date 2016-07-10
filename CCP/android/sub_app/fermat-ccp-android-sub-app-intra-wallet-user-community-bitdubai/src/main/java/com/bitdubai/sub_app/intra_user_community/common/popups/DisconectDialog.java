package com.bitdubai.sub_app.intra_user_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;

/**
 * Created by Matias Furszyfer on 2015.08.12..
 * Changed by Jose Manuel De Sousa Dos Santos on 2015.12.03
 */
@SuppressWarnings("FieldCanBeLocal")
public class DisconectDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private FermatButton   positiveBtn ;
    private FermatButton   negativeBtn ;
    private FermatTextView mDescription;
    private FermatTextView mUsername   ;
    private FermatTextView mTitle      ;
    private CharSequence   description ;
    private CharSequence   username    ;
    private CharSequence   title       ;

    private final IntraUserInformation   intraUserInformation;
    private final IntraUserLoginIdentity identity            ;

    public DisconectDialog(final Activity                       activity              ,
                           final ReferenceAppFermatSession intraUserSubAppSession,
                           final SubAppResourcesProviderManager subAppResources       ,
                           final IntraUserInformation           intraUserInformation  ,
                           final IntraUserLoginIdentity         identity              ) {

        super(activity, intraUserSubAppSession, subAppResources);

        this.intraUserInformation = intraUserInformation;
        this.identity             = identity            ;
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

                    getSession().getModuleManager().disconnectIntraUSer(identity.getPublicKey(),intraUserInformation.getPublicKey());

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    prefs.edit().putBoolean("Connected", true).apply();
                    Intent broadcast = new Intent(Constants.LOCAL_BROADCAST_CHANNEL);
                    broadcast.putExtra(Constants.BROADCAST_DISCONNECTED_UPDATE, true);
                    sendLocalBroadcast(broadcast);

                    Toast.makeText(getContext(), "Disconnected", Toast.LENGTH_SHORT).show();

                } else {
                    super.toastDefaultError();
                }
                dismiss();

            } catch (final IntraUserDisconnectingFailedException e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }

            dismiss();
        }else if( i == R.id.negative_button){
            dismiss();
        }
    }


}