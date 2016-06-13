package com.bitdubai.sub_app.crypto_customer_community.common.popups;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.actor_connection.common.exceptions.CantDenyActorConnectionRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.sub_app.crypto_customer_community.R;


/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class AcceptDialog extends FermatDialog<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>, SubAppResourcesProviderManager> implements
        View.OnClickListener {

    /**
     * UI components
     */

    LinkedCryptoCustomerIdentity cryptoCustomerCommunityInformation;

    CryptoCustomerCommunitySelectableIdentity identity;


    public AcceptDialog(Activity activity,
                        ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> session,
                        SubAppResourcesProviderManager subAppResources,
                        LinkedCryptoCustomerIdentity cryptoCustomerInformation,
                        CryptoCustomerCommunitySelectableIdentity identity) {

        super(activity, session, subAppResources);

        this.cryptoCustomerCommunityInformation = cryptoCustomerInformation;
        this.identity = identity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView title = (FermatTextView) findViewById(R.id.title);
        FermatTextView description = (FermatTextView) findViewById(R.id.description);
        FermatTextView userName = (FermatTextView) findViewById(R.id.user_name);
        FermatButton positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        FermatButton negativeBtn = (FermatButton) findViewById(R.id.negative_button);

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
                    System.out.println("************* im goint to accept: " + cryptoCustomerCommunityInformation.getConnectionId());
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
