package com.bitdubai.sub_app.fan_community.commons.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.LinkedFanIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSession;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 05/04/16.
 */
public class AcceptDialog extends
        FermatDialog<
                FanCommunitySubAppSession,
                SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */
    LinkedFanIdentity fanCommunityInformation;

    FanCommunitySelectableIdentity identity;
    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;


    public AcceptDialog(
            Activity a,
            FanCommunitySubAppSession fanCommunitySubAppSession,
            SubAppResourcesProviderManager subAppResources,
            LinkedFanIdentity fanInformation,
            FanCommunitySelectableIdentity identity) {
        super(a, fanCommunitySubAppSession, subAppResources);
        this.fanCommunityInformation = fanInformation;
        this.identity = identity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = (FermatTextView) findViewById(R.id.afc_title);
        description = (FermatTextView) findViewById(R.id.afc_description);
        userName = (FermatTextView) findViewById(R.id.afc_user_name);
        positiveBtn = (FermatButton) findViewById(R.id.afc_positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.afc_negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Connect");
        description.setText("Do you want to accept");
        userName.setText(fanCommunityInformation.getAlias());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.afc_dialog_builder;
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.afc_positive_button) {
            try {
                if (fanCommunityInformation != null && identity != null) {
                    System.out.println("************* I'm going to accept: "+fanCommunityInformation.getConnectionId());
                    getSession().getModuleManager().acceptFan(fanCommunityInformation.getConnectionId());
                    Toast.makeText(getContext(),
                            " Accepted connection request from " + fanCommunityInformation.getAlias(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "There has been an error accepting request", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }
            dismiss();
        } else if (i == R.id.afc_negative_button) {
            try {
                if (fanCommunityInformation != null && identity != null) {
                    getSession().getModuleManager().denyConnection(fanCommunityInformation.getConnectionId());
                } else {
                    Toast.makeText(getContext(),
                            "There has been an error denying request",
                            Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantDenyActorConnectionRequestException e) {
                e.printStackTrace();
            }
            dismiss();
        }
    }


}

