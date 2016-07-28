package com.bitdubai.sub_app.crypto_customer_community.common.dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.LinkedCryptoCustomerIdentity;
import com.bitdubai.sub_app.crypto_customer_community.R;


/**
 * Created by Alejandro Bicelis on 2/2/2016.
 */
public class AcceptDialog
        extends FermatDialog<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>, ResourceProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */

    LinkedCryptoCustomerIdentity linkedCryptoCustomerIdentity;

    CryptoCustomerCommunitySelectableIdentity identity;

    public AcceptDialog(Activity activity,
                        ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> session,
                        ResourceProviderManager subAppResources,
                        LinkedCryptoCustomerIdentity linkedCryptoCustomerIdentity,
                        CryptoCustomerCommunitySelectableIdentity identity) {

        super(activity, session, subAppResources);

        this.linkedCryptoCustomerIdentity = linkedCryptoCustomerIdentity;
        this.identity = identity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView title = (FermatTextView) findViewById(R.id.ccc_title);
        FermatTextView subTitle = (FermatTextView) findViewById(R.id.ccc_sub_title);
        FermatTextView description = (FermatTextView) findViewById(R.id.ccc_description);
        FermatTextView positiveBtn = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeBtn = (FermatTextView) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Connection Request");
        subTitle.setText("Accept Request");
        final String text = String.format("Do you want to accept the connection request from %1$s?", linkedCryptoCustomerIdentity.getAlias());
        description.setText(text);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.ccc_dialog_generic_use;
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
                if (linkedCryptoCustomerIdentity != null && identity != null) {
                    getSession().getModuleManager().acceptCryptoCustomer(linkedCryptoCustomerIdentity.getConnectionId());
                    Toast.makeText(getContext(), new StringBuilder().append("Accepted connection request from ").append(linkedCryptoCustomerIdentity.getAlias()).toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CantAcceptRequestException ex) {
                getSession().getErrorManager().reportUnexpectedSubAppException(SubApps.CBP_CRYPTO_CUSTOMER_COMMUNITY,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
            }

        } else if (i == R.id.negative_button) {
            //try {
            if (linkedCryptoCustomerIdentity != null && identity != null) {
                Toast.makeText(getContext(), "TODO DENY ->", Toast.LENGTH_SHORT).show();
                // getSession().getModuleManager().denyConnection(identity.getPublicKey(), information.getPublicKey());
            } else {
                Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
            }
        }

        dismiss();
    }
}
