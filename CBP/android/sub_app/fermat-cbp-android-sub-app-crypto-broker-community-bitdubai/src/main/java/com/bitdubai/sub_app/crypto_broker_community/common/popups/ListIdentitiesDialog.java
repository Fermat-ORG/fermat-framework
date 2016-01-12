package com.bitdubai.sub_app.crypto_broker_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.bitdubai.sub_app.crypto_broker_community.constants.Constants;
import com.bitdubai.sub_app.crypto_broker_community.session.CryptoBrokerCommunitySubAppSession;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ListIdentitiesDialog extends FermatDialog<CryptoBrokerCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

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

    private CryptoBrokerCommunityInformation        information;
    private CryptoBrokerCommunitySelectableIdentity identity   ;


    public ListIdentitiesDialog(final Activity                                activity       ,
                                final CryptoBrokerCommunitySubAppSession      subAppSession  ,
                                final SubAppResourcesProviderManager          subAppResources,
                                final CryptoBrokerCommunityInformation        information    ,
                                final CryptoBrokerCommunitySelectableIdentity identity       ) {

        super(
                activity       ,
                subAppSession  ,
                subAppResources
        );

        this.information = information;
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
            //try {
                //image null
                if (information != null && identity != null) {
                    Toast.makeText(getContext(), "TODO DISCONNECT ->", Toast.LENGTH_SHORT).show();
                    //getSession().getModuleManager().disconnectIntraUSer(information.getPublicKey());

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                    prefs.edit().putBoolean("Connected", true).apply();
                    Intent broadcast = new Intent(Constants.LOCAL_BROADCAST_CHANNEL);
                    broadcast.putExtra(Constants.BROADCAST_DISCONNECTED_UPDATE, true);
                    sendLocalBroadcast(broadcast);
                    Toast.makeText(getContext(), "Disconnected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            /*} catch (IntraUserDisconnectingFailedException e) {
                e.printStackTrace();
            }*/

            dismiss();
        }else if( i == R.id.negative_button){
            dismiss();
        }
    }


}
