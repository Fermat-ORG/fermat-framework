package com.bitdubai.sub_app.crypto_broker_community.common.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.sub_app.crypto_broker_community.R;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class AcceptDialog
        extends FermatDialog<ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>, ResourceProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */

    CryptoBrokerCommunityInformation cryptoBrokerCommunityInformation;

    CryptoBrokerCommunitySelectableIdentity identity;
    private String description;

    public AcceptDialog(Activity activity,
                        ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager> session,
                        ResourceProviderManager subAppResources,
                        CryptoBrokerCommunityInformation cryptoBrokerInformation,
                        CryptoBrokerCommunitySelectableIdentity identity) {

        super(activity, session, subAppResources);

        this.cryptoBrokerCommunityInformation = cryptoBrokerInformation;
        this.identity = identity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView title = (FermatTextView) findViewById(R.id.cbc_title);
        FermatTextView subTitle = (FermatTextView) findViewById(R.id.cbc_sub_title);
        FermatTextView description = (FermatTextView) findViewById(R.id.cbc_description);
        FermatTextView positiveBtn = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeBtn = (FermatTextView) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Connection Request");
        subTitle.setText("Accept Request");
        final String text = String.format("Do you want to accept the connection request from %1$s?", cryptoBrokerCommunityInformation.getAlias());
        description.setText(text);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.cbc_dialog_generic_use;
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
                //   Toast.makeText(getContext(), "TODO ACCEPT ->", Toast.LENGTH_SHORT).show();
                //TODO: cuando se puede recibir una solicitud de conexion por parte de un Broker ejecutar lo que esta comentado para aceptarla
                //getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(), information.getName(), information.getPublicKey(), information.getProfileImage());
                Toast.makeText(getContext(), new StringBuilder().append(cryptoBrokerCommunityInformation.getAlias()).append(" Accepted connection request").toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            if (cryptoBrokerCommunityInformation != null && identity != null) {
                Toast.makeText(getContext(), "TODO DENY ->", Toast.LENGTH_SHORT).show();
                //TODO: cuando se puede recibir una solicitud de conexion por parte de un Broker ejecutar lo que esta comentado para rechazarla
                // getSession().getModuleManager().denyConnection(identity.getPublicKey(), information.getPublicKey());
            } else {
                Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        }
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
