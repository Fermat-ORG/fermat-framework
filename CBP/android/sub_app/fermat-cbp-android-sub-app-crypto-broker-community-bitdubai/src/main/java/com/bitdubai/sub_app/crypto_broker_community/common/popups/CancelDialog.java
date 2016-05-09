package com.bitdubai.sub_app.crypto_broker_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ConnectionRequestNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CryptoBrokerCancellingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;


/**
 * Created by Alejandro Bicelis on 15/02/2016
 *
 */
public class CancelDialog extends FermatDialog<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager>
        implements View.OnClickListener {

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

    CryptoBrokerCommunityInformation cryptoBrokerCommunityInformation;

    CryptoBrokerCommunitySelectableIdentity identity;


    public CancelDialog(Activity a,
                        CryptoBrokerCommunitySubAppSession cryptoBrokerCommunitySubAppSession,
                        SubAppResourcesProviderManager subAppResources,
                        CryptoBrokerCommunityInformation cryptoBrokerCommunityInformation,
                        CryptoBrokerCommunitySelectableIdentity identity) {

        super(a, cryptoBrokerCommunitySubAppSession, subAppResources);

        this.cryptoBrokerCommunityInformation = cryptoBrokerCommunityInformation;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescription = (FermatTextView) findViewById(R.id.description);
        mUsername = (FermatTextView) findViewById(R.id.user_name);
        mTitle = (FermatTextView)findViewById(R.id.title);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mDescription.setText(description!= null ? description : "");
        mUsername.setText(username!= null ? username: "");
        mTitle.setText(title != null ? title: "");

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
                if (cryptoBrokerCommunityInformation != null && identity != null) {

                    getSession().getModuleManager().cancelCryptoBroker(cryptoBrokerCommunityInformation.getConnectionId());
                    Toast.makeText(getContext(), "Cancelled successfully", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData("connectionresult", 1);

                } else {
                    Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CryptoBrokerCancellingFailedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "Could not cancel, please try again", Toast.LENGTH_SHORT).show();
            } catch (ConnectionRequestNotFoundException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error. Could not cancel.", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        }else if( i == R.id.negative_button){
            dismiss();
        }
    }


}
