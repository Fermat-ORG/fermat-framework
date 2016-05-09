package com.bitdubai.sub_app.crypto_customer_community.common.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.session.CryptoCustomerCommunitySubAppSession;

/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class AcceptDialog extends FermatDialog<CryptoCustomerCommunitySubAppSession, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */

    LinkedCryptoCustomerIdentity cryptoCustomerCommunityInformation;

    CryptoCustomerCommunitySelectableIdentity identity;
    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;


    public AcceptDialog(Activity a,
                        CryptoCustomerCommunitySubAppSession cryptoBrokerCommunitySubAppSession,
                        SubAppResourcesProviderManager subAppResources,
                        LinkedCryptoCustomerIdentity cryptoCustomerInformation,
                        CryptoCustomerCommunitySelectableIdentity identity) {

        super(a, cryptoBrokerCommunitySubAppSession, subAppResources);

        this.cryptoCustomerCommunityInformation = cryptoCustomerInformation;
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
        userName.setText(cryptoCustomerCommunityInformation.getAlias());

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
                if (cryptoCustomerCommunityInformation != null && identity != null) {
                    System.out.println("************* im goint to accept: "+cryptoCustomerCommunityInformation.getConnectionId());
                    getSession().getModuleManager().acceptCryptoCustomer(cryptoCustomerCommunityInformation.getConnectionId());
                    Toast.makeText(getContext(), " Accepted connection request from " + cryptoCustomerCommunityInformation.getAlias(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "There has been an error accepting request", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantAcceptRequestException e) {
                e.printStackTrace();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            try {
                if (cryptoCustomerCommunityInformation != null && identity != null) {
                    getSession().getModuleManager().denyConnection(cryptoCustomerCommunityInformation.getConnectionId());
                } else {
                    Toast.makeText(getContext(), "There has been an error denying request", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantDenyActorConnectionRequestException e) {
                e.printStackTrace();
            }
            dismiss();
        }
    }


}
