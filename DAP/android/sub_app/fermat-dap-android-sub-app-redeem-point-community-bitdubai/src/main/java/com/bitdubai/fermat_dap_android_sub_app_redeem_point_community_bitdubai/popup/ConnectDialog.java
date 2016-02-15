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
public class ConnectDialog extends FermatDialog<AssetRedeemPointCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    private FermatTextView mDescription;
    private FermatTextView mUsername;
    private FermatTextView mSecondDescription;
    private FermatTextView mTitle;
    private CharSequence description;

    private CharSequence secondDescription;
    private CharSequence username;
    private CharSequence title;
    private int actorsSelected;
    private final Actor   actor;
    private final RedeemPointIdentity identity            ;


    public ConnectDialog(final Activity                       a                     ,
                         final AssetRedeemPointCommunitySubAppSession         actorUserSubAppSession,
                         final SubAppResourcesProviderManager subAppResources       ,
                         final Actor           actor  ,
                         final RedeemPointIdentity         identity              ) {

        super(a, actorUserSubAppSession, subAppResources);

        this.actor = actor;
        this.identity             = identity            ;
    }

    public ConnectDialog(Activity a,
                        final AssetRedeemPointCommunitySubAppSession         actorUserSubAppSession,
                        final SubAppResourcesProviderManager subAppResources ) {
        super(a, actorUserSubAppSession, subAppResources);
        this.actor  =   null;
        this.identity = null;
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDescription = (FermatTextView) findViewById(R.id.description);
        mUsername = (FermatTextView) findViewById(R.id.user_name);
        mSecondDescription = (FermatTextView) findViewById(R.id.second_description);
        mTitle = (FermatTextView) findViewById(R.id.title);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);
        mSecondDescription.setVisibility(View.VISIBLE);
        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mSecondDescription.setText(secondDescription != null ? secondDescription : "");
        mDescription.setText(description != null ? description : "");
        mUsername.setText(username != null ? username : "");
        mTitle.setText(title != null ? title : "");

    }

    public void setSecondDescription(CharSequence secondDescription) {
        this.secondDescription = secondDescription;
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
//                //image null
//                if (actor != null && identity != null) {
//                    getSession().getModuleManager().askIntraUserForAcceptance(actor.getName(), actor.getPhrase(),actor.getPublicKey(),actor.getProfileImage(), identity.getProfileImage(), identity.getPublicKey(), identity.getAlias());
//                    Intent broadcast = new Intent(Constants.LOCAL_BROADCAST_CHANNEL);
//                    broadcast.putExtra(Constants.BROADCAST_CONNECTED_UPDATE, true);
//                    sendLocalBroadcast(broadcast);
                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();
//                } else {
//                    super.toastDefaultError();
//                }
//                dismiss();
//            } catch (CantStartRequestException e) {
//                getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
//                super.toastDefaultError();
//            }

            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }


}