package com.bitdubai.sub_app.intra_user_community.common.popups;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserDisconnectingFailedException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.intra_user_community.R;
import com.bitdubai.sub_app.intra_user_community.constants.Constants;

/**
 * Created by root on 15/07/16.
 */
public class DeleteContactConfirmationDialog extends FermatDialog<ReferenceAppFermatSession<IntraUserModuleManager>, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final IntraUserInformation intraUserInformation;
    private final IntraUserLoginIdentity identity;

    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    private FermatTextView mTitle;
    private FermatTextView mDescription;

    private CharSequence   description ;
    private CharSequence   title       ;

    public DeleteContactConfirmationDialog(final Activity activity,
                                            final ReferenceAppFermatSession intraUserSubAppSession,
                                            final SubAppResourcesProviderManager subAppResources,
                                            final IntraUserInformation intraUserInformation,
                                            final IntraUserLoginIdentity identity) {

        super(activity, intraUserSubAppSession, subAppResources);
        this.intraUserInformation = intraUserInformation;
        this.identity= identity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle       = (FermatTextView) findViewById(R.id.title);
        mDescription = (FermatTextView) findViewById(R.id.description);
        positiveBtn = (FermatButton)   findViewById(R.id.positive_button);
        negativeBtn = (FermatButton)   findViewById(R.id.negative_button);

        mDescription.setText(description != null ? description : "");
        mTitle.setText(title != null ? title : "");

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.delete_connection_confirmation_dialog;
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
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }
}
