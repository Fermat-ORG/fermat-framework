package com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.sessions.AssetRedeemPointCommunitySubAppSession;
import com.bitdubai.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

/**
 * Added by Jinmy Bohorquez 11/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog extends FermatDialog<AssetRedeemPointCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final Actor actor;
    private final RedeemPointIdentity identity            ;

    private FermatTextView title      ;
    private FermatTextView description;
    private FermatTextView userName   ;
    private FermatButton   positiveBtn;
    private FermatButton   negativeBtn;

    public AcceptDialog(final Activity activity,
                        final AssetRedeemPointCommunitySubAppSession assetUserCommunitySubAppSession,
                        final SubAppResourcesProviderManager subAppResources,
                        final Actor actor,
                        final RedeemPointIdentity identity) {

        super(activity, assetUserCommunitySubAppSession, subAppResources);

        this.actor = actor;
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
        userName.setText(actor.getName());

    }

    @Override
    protected int setLayoutId() {
        return R.layout.dap_redeempoint_dialog_builder;
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
//                if (actor != null && identity != null) {
//
//                    getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(), actor.getName(), actor.getPublicKey(), actor.getProfileImage());
//                    getSession().setData(SessionConstantsAssetUserCommunity.NOTIFICATION_ACCEPTED,Boolean.TRUE);
                    Toast.makeText(getContext(), actor.getName() + " Accepted connection request", Toast.LENGTH_SHORT).show();
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
//                if (actor != null && identity != null)
//                    //getSession().getModuleManager().denyConnection(identity.getPublicKey(), actor.getPublicKey());
//                else {
//                    super.toastDefaultError();
//                }
//                dismiss();
//            } catch (final IntraUserConnectionDenialFailedException e) {
//
//                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
//                super.toastDefaultError();
//            }
            Toast.makeText(getContext(), actor.getName() + " Deny connection request", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
