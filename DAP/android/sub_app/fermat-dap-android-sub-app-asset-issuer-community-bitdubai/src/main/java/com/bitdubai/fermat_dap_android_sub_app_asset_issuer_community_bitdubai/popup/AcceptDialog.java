package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models.ActorIssuer;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Added by Jinmy Bohorquez 09/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog extends FermatDialog<AssetIssuerCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final ActorIssuer actorIssuer;
    private final IdentityAssetIssuer identity            ;

    private FermatTextView title      ;
    private FermatTextView description;
    private FermatTextView userName   ;
    private FermatButton   positiveBtn;
    private FermatButton   negativeBtn;

    public AcceptDialog(final Activity                       activity              ,
                        final AssetIssuerCommunitySubAppSession         assetIssuerCommunitySubAppSession,
                        final SubAppResourcesProviderManager subAppResources       ,
                        final ActorIssuer           actorIssuer  ,
                        final IdentityAssetIssuer         identity              ) {

        super(activity, assetIssuerCommunitySubAppSession, subAppResources);

        this.actorIssuer = actorIssuer;
        this.identity             = identity            ;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title       = (FermatTextView) findViewById(R.id.title          );
        description = (FermatTextView) findViewById(R.id.description    );
        userName    = (FermatTextView) findViewById(R.id.user_name      );
        positiveBtn = (FermatButton)   findViewById(R.id.positive_button);
        negativeBtn = (FermatButton)   findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText("Connect");
        description.setText("Do you want to accept");
        userName.setText(actorIssuer.getRecord().getName());

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

//            try {
//                if (actorIssuer != null && identity != null) {
//
//                    getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(), actorIssuer.getName(), actorIssuer.getPublicKey(), actorIssuer.getProfileImage());
//                    getSession().setData(SessionConstantsAssetUserCommunity.NOTIFICATION_ACCEPTED,Boolean.TRUE);
                    Toast.makeText(getContext(), actorIssuer.getRecord().getName() + " Accepted connection request", Toast.LENGTH_SHORT).show();
//                } else {
//                    super.toastDefaultError();
//                }
//                dismiss();
//            } catch (final CantAcceptRequestException e) {
//
//                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
//                super.toastDefaultError();
//            }
            dismiss();

        } else if (i == R.id.negative_button) {
//            try {
//                if (actorIssuer != null && identity != null)
//                    //getSession().getModuleManager().denyConnection(identity.getPublicKey(), actorIssuer.getPublicKey());
//                else {
//                    super.toastDefaultError();
//                }
//                dismiss();
//            } catch (final IntraUserConnectionDenialFailedException e) {
//
//                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
//                super.toastDefaultError();
//            }
            Toast.makeText(getContext(), actorIssuer.getRecord().getName() + " Deny connection request", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
