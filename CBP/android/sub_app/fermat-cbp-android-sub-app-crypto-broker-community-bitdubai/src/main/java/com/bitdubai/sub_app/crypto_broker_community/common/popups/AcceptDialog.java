package com.bitdubai.sub_app.crypto_broker_community.common.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AcceptDialog extends FermatDialog<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */

    CryptoBrokerCommunityInformation cryptoBrokerCommunityInformation;

    CryptoBrokerCommunitySelectableIdentity identity;
    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;

    public AcceptDialog(Activity                                a                                 ,
                        CryptoBrokerCommunitySubAppSession      cryptoBrokerCommunitySubAppSession,
                        SubAppResourcesProviderManager          subAppResources                   ,
                        CryptoBrokerCommunityInformation        cryptoBrokerInformation           ,
                        CryptoBrokerCommunitySelectableIdentity identity                          ) {

        super(a, cryptoBrokerCommunitySubAppSession, subAppResources);

        this.cryptoBrokerCommunityInformation = cryptoBrokerInformation;
        this.identity             = identity               ;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = (FermatTextView) findViewById(R.id.title);
        description = (FermatTextView) findViewById(R.id.description);
        userName = (FermatTextView) findViewById(R.id.user_name);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Connect");
        description.setText("Do you want to accept");
        userName.setText(cryptoBrokerCommunityInformation.getAlias());

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
           // try {
                if (cryptoBrokerCommunityInformation != null && identity != null) {
                    Toast.makeText(getContext(), "TODO ACCEPT ->", Toast.LENGTH_SHORT).show();
                    //getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(), information.getName(), information.getPublicKey(), information.getProfileImage());
                    Toast.makeText(getContext(), cryptoBrokerCommunityInformation.getAlias() + " Accepted connection request", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            /*} catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }*/
            dismiss();
        } else if (i == R.id.negative_button) {
            //try {
                if (cryptoBrokerCommunityInformation != null && identity != null) {
                    Toast.makeText(getContext(), "TODO DENY ->", Toast.LENGTH_SHORT).show();
                    // getSession().getModuleManager().denyConnection(identity.getPublicKey(), information.getPublicKey());
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            /*} catch (IntraUserConnectionDenialFailedException e) {
                e.printStackTrace();
            }*/
            dismiss();
        }
    }


}
