package com.bitdubai.sub_app.fan_community.commons.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.exceptions.FanCancellingFailedException;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityInformation;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunityModuleManager;
import com.bitdubai.fermat_art_api.layer.sub_app_module.community.fan.interfaces.FanCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.fan_community.R;
import com.bitdubai.sub_app.fan_community.sessions.FanCommunitySubAppSessionReferenceApp;


/**
 * Created by Gabriel Araujo 29/04/2016.
 *
 */
public class CancelDialog extends FermatDialog<ReferenceAppFermatSession<FanCommunityModuleManager>, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    FermatTextView mDescription;
    FermatTextView mUsername;
    FermatTextView mTitle;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    CharSequence description;
    CharSequence username;
    CharSequence title;

    FanCommunityInformation fanCommunityInformation;

    FanCommunitySelectableIdentity identity;


    public CancelDialog(Activity a,
                        ReferenceAppFermatSession fanCommunitySubAppSession,
                        SubAppResourcesProviderManager subAppResources,
                        FanCommunityInformation fanCommunityInformation,
                        FanCommunitySelectableIdentity identity) {

        super(a, fanCommunitySubAppSession, subAppResources);

        this.fanCommunityInformation = fanCommunityInformation;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitle = (FermatTextView) findViewById(R.id.afc_title);
        mDescription = (FermatTextView) findViewById(R.id.afc_description);
        mUsername = (FermatTextView) findViewById(R.id.afc_user_name);
        positiveBtn = (FermatButton) findViewById(R.id.afc_positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.afc_negative_button);
        mTitle.setText("Cancel");
        mDescription.setText("Do you want to cancel connection with");
        mUsername.setText(fanCommunityInformation.getAlias()+"?");
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);


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

                    getSession().getModuleManager().cancelFan(fanCommunityInformation.getConnectionId());
                    Toast.makeText(getActivity(), "Cancelled successfully", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData("connectionresult", 1);

                } else {
                    Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (FanCancellingFailedException e) {
                Toast.makeText(getActivity(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            } catch (Exception e){
                Toast.makeText(getActivity(), "Unhadle error", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        }else if( i == R.id.afc_negative_button){
            dismiss();
        }
    }


}
