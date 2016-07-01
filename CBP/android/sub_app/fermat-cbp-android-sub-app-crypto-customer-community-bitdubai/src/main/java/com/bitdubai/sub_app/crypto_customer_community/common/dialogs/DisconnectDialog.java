package com.bitdubai.sub_app.crypto_customer_community.common.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.exceptions.CryptoCustomerDisconnectingFailedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_customer_community.interfaces.CryptoCustomerCommunitySubAppModuleManager;
import com.bitdubai.sub_app.crypto_customer_community.R;
import com.bitdubai.sub_app.crypto_customer_community.util.FragmentsCommons;


/**
 * Created by Alejandro Bicelis on 12/2/2016.
 */
public class DisconnectDialog extends FermatDialog<ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager>, ResourceProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    CharSequence description;
    CharSequence subtitle;
    CharSequence title;

    CryptoCustomerCommunityInformation cryptoCustomerCommunityInformation;

    CryptoCustomerCommunitySelectableIdentity identity;


    public DisconnectDialog(Activity activity,
                            ReferenceAppFermatSession<CryptoCustomerCommunitySubAppModuleManager> session,
                            ResourceProviderManager subAppResources,
                            CryptoCustomerCommunityInformation cryptoCustomerCommunityInformation,
                            CryptoCustomerCommunitySelectableIdentity identity) {

        super(activity, session, subAppResources);

        this.cryptoCustomerCommunityInformation = cryptoCustomerCommunityInformation;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView mDescription = (FermatTextView) findViewById(R.id.ccc_description);
        FermatTextView mSubtitle = (FermatTextView) findViewById(R.id.ccc_sub_title);
        FermatTextView mTitle = (FermatTextView) findViewById(R.id.ccc_title);
        FermatTextView positiveBtn = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeBtn = (FermatTextView) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        if (title != null) mTitle.setText(title);
        else mTitle.setVisibility(View.GONE);

        if (subtitle != null) mSubtitle.setText(subtitle);
        else mSubtitle.setVisibility(View.GONE);

        if (description != null) mDescription.setText(description);
        else mDescription.setVisibility(View.GONE);
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public void setSubtitle(CharSequence subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
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
                if (cryptoCustomerCommunityInformation != null && identity != null) {

                    getSession().getModuleManager().disconnectCryptoCustomer(cryptoCustomerCommunityInformation.getConnectionId());
                    Toast.makeText(getContext(), "Disconnected successfully", Toast.LENGTH_SHORT).show();

                    //set flag so that the preceding fragment reads it on dismiss()
                    getSession().setData(FragmentsCommons.CONNECTION_RESULT, ConnectionState.DISCONNECTED_LOCALLY);

                } else {
                    Toast.makeText(getContext(), "Oooops! recovering from system error - ", Toast.LENGTH_SHORT).show();
                }
                dismiss();
            } catch (CryptoCustomerDisconnectingFailedException e) {
                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                Toast.makeText(getContext(), "Could not disconnect, please try again", Toast.LENGTH_SHORT).show();
            }

            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }
}
