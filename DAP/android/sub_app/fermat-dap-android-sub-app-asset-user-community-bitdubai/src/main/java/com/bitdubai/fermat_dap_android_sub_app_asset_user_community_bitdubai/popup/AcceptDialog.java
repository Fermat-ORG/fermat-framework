package com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConnectionDenialFailedException;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.models.Actor;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.AssetUserCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.sessions.SessionConstantsAssetUserCommunity;

/**
 * Added by Jinmy Bohorquez 09/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog extends FermatDialog<AssetUserCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final Actor actor;
    private final IdentityAssetUser identity            ;

    private FermatTextView title      ;
    private FermatTextView description;
    private FermatTextView userName   ;
    private FermatButton   positiveBtn;
    private FermatButton   negativeBtn;

    public AcceptDialog(final Activity                       activity              ,
                        final AssetUserCommunitySubAppSession         assetUserCommunitySubAppSession,
                        final SubAppResourcesProviderManager subAppResources       ,
                        final Actor           actor  ,
                        final IdentityAssetUser         identity              ) {

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
                if (actor != null ) { //&& identity != null) {
//
                    getSession().getModuleManager().acceptActorAssetUser(
//                            identity.getPublicKey(),
                            actor.getName(),
                            actor.getActorPublicKey()
//                            actor.getProfileImage()
                    );
                    getSession().setData(SessionConstantsAssetUserCommunity.IC_ACTION_USER_NOTIFICATIONS_ACCEPTED,Boolean.TRUE);
                    Toast.makeText(getContext(), actor.getName() + " Accepted connection request", Toast.LENGTH_SHORT).show();
                } else {
                    super.toastDefaultError();
                }
                dismiss();
            } catch (final CantAcceptActorAssetUserException e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        } else if (i == R.id.negative_button) {
            try {
                if (actor != null )  //&& identity != null)
                    getSession().getModuleManager().denyConnectionActorAssetUser(actor.getActorPublicKey(), actor.getActorPublicKey());
                else {
                    super.toastDefaultError();
                }
                dismiss();
            } catch (final CantDenyConnectionActorAssetException e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            Toast.makeText(getContext(), actor.getName() + " Deny connection request", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
