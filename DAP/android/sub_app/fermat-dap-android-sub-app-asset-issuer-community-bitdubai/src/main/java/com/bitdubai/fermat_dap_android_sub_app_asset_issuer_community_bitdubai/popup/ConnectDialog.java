package com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.SessionConstantsAssetIssuerCommunity;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor.redeem_point.exceptions.CantConnectToActorAssetRedeemPointException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.asset_user.exceptions.CantAskConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Added by Jinmy Bohorquez 09/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class ConnectDialog extends FermatDialog<AssetIssuerCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

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

    private final ActorIssuer actorIssuer;
    List<ActorIssuer> toConnect;
    private final IdentityAssetIssuer identity;


    public ConnectDialog(final Activity a,
                         final AssetIssuerCommunitySubAppSession actorIssuerUserSubAppSession,
                         final SubAppResourcesProviderManager subAppResources,
                         final ActorIssuer actorIssuer,
                         final IdentityAssetIssuer identity) {

        super(a, actorIssuerUserSubAppSession, subAppResources);

        this.actorIssuer = actorIssuer;
        this.identity = identity;
    }

    public ConnectDialog(Activity a,
                         final AssetIssuerCommunitySubAppSession actorIssuerUserSubAppSession,
                         final SubAppResourcesProviderManager subAppResources) {
        super(a, actorIssuerUserSubAppSession, subAppResources);
        this.actorIssuer = null;
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
            //image null

            if (actorIssuer != null) { // && identity != null) {

                try {
                    List<ActorAssetIssuer> actorConnect = new ArrayList<>();

                    actorConnect.add(actorIssuer.getRecord());

                    getSession().getModuleManager().askActorAssetIssuerForConnection(actorConnect);
//                    getSession().getModuleManager().connectToActorAssetIssuer(null, actorConnect);

                    Intent broadcast = new Intent(SessionConstantsAssetIssuerCommunity.LOCAL_BROADCAST_CHANNEL);
                    broadcast.putExtra(SessionConstantsAssetIssuerCommunity.BROADCAST_CONNECTED_UPDATE, true);
                    sendLocalBroadcast(broadcast);
                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();
                } catch (CantAskConnectionActorAssetException e) {
                    e.printStackTrace();
                } catch (CantRequestAlreadySendActorAssetException e) {
                    e.printStackTrace();
//                } catch (CantConnectToActorAssetRedeemPointException e) {
//                    e.printStackTrace();
                }
            } else {
                super.toastDefaultError();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }


}