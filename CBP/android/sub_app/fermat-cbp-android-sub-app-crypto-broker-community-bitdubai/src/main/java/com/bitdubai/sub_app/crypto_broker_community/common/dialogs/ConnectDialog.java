package com.bitdubai.sub_app.crypto_broker_community.common.dialogs;

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
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorConnectionAlreadyRequestedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.ActorTypeNotSupportedException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.exceptions.CantRequestConnectionException;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunityInformation;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySelectableIdentity;
import com.bitdubai.fermat_cbp_api.layer.sub_app_module.crypto_broker_community.interfaces.CryptoBrokerCommunitySubAppModuleManager;
import com.bitdubai.sub_app.crypto_broker_community.R;
import com.bitdubai.sub_app.crypto_broker_community.util.FragmentsCommons;


/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public class ConnectDialog extends FermatDialog<ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager>, ResourceProviderManager>
        implements View.OnClickListener {

    private CharSequence description;
    private CharSequence subtitle;
    private CharSequence title;

    private final CryptoBrokerCommunityInformation information;
    private final CryptoBrokerCommunitySelectableIdentity identity;


    public ConnectDialog(final Activity activity,
                         final ReferenceAppFermatSession<CryptoBrokerCommunitySubAppModuleManager> session,
                         final ResourceProviderManager subAppResources,
                         final CryptoBrokerCommunityInformation information,
                         final CryptoBrokerCommunitySelectableIdentity identity) {

        super(activity, session, subAppResources);

        this.information = information;
        this.identity = identity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FermatTextView mTitle = (FermatTextView) findViewById(R.id.cbc_title);
        FermatTextView mSubtitle = (FermatTextView) findViewById(R.id.cbc_sub_title);
        FermatTextView mDescription = (FermatTextView) findViewById(R.id.cbc_description);
        FermatTextView positiveBtn = (FermatTextView) findViewById(R.id.positive_button);
        FermatTextView negativeBtn = (FermatTextView) findViewById(R.id.negative_button);

        mDescription.setText(description != null ? description : "");
        mSubtitle.setText(subtitle != null ? subtitle : "");
        mTitle.setText(title != null ? title : "");
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
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
            try {
                if (information != null && identity != null) {

                    System.out.println(new StringBuilder().append("*********** i'm the selected identity: ").append(identity).toString());
                    System.out.println(new StringBuilder().append("*********** i'm the selected broker information: ").append(information).toString());

                    getSession().getModuleManager().requestConnectionToCryptoBroker(identity, information);
                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();

                    getSession().setData(FragmentsCommons.CONNECTION_RESULT, ConnectionState.PENDING_REMOTELY_ACCEPTANCE);

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
