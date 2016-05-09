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
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectDialog extends FermatDialog<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    FermatButton positiveBtn;
    FermatButton negativeBtn;
    FermatTextView mDescription;
    FermatTextView mUsername;
    FermatTextView mSecondDescription;
    FermatTextView mTitle;
    CharSequence description;
    CharSequence secondDescription;
    CharSequence username;
    CharSequence title;

    private final CryptoBrokerCommunityInformation        information;
    private final CryptoBrokerCommunitySelectableIdentity identity   ;


    public ConnectDialog(final Activity                                activity                          ,
                         final CryptoBrokerCommunitySubAppSession      cryptoBrokerCommunitySubAppSession,
                         final SubAppResourcesProviderManager          subAppResources                   ,
                         final CryptoBrokerCommunityInformation        information                       ,
                         final CryptoBrokerCommunitySelectableIdentity identity                          ) {

        super(activity, cryptoBrokerCommunitySubAppSession, subAppResources);

        this.information = information;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescription = (FermatTextView) findViewById(R.id.description);
        mUsername = (FermatTextView) findViewById(R.id.user_name);
        mSecondDescription = (FermatTextView) findViewById(R.id.second_description);
        mTitle = (FermatTextView) findViewById(R.id.title);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);
        mSecondDescription.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mSecondDescription.setText(secondDescription != null ? secondDescription : "");
        mDescription.setText(description != null ? description : "");
        mUsername.setText(username != null ? username : "");
        mTitle.setText(title != null ? title : "");

    }

    public void setSecondDescription(CharSequence secondDescription) {
        this.secondDescription = secondDescription;
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
                if (information != null && identity != null) {

                    //System.out.println("*********** i'm the selected identity: "+identity);
                    //System.out.println("*********** i'm the selected broker information: " + information);

                    getSession().getModuleManager().requestConnectionToCryptoBroker(identity, information);
                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData("connectionresult", 2);

                } else {
                    Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (ActorTypeNotSupportedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "There has been an error, please try again", Toast.LENGTH_SHORT).show();
            } catch (CantRequestConnectionException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "Could not request connection, please try again", Toast.LENGTH_SHORT).show();
            } catch (ActorConnectionAlreadyRequestedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "The connection has already been requested", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }

}
