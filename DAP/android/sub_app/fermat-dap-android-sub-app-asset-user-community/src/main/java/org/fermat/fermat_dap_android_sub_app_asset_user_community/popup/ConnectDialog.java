package org.fermat.fermat_dap_android_sub_app_asset_user_community.popup;

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
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_dap_android_sub_app_asset_user_community_bitdubai.R;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions.AssetUserCommunitySubAppSession;
import org.fermat.fermat_dap_android_sub_app_asset_user_community.sessions.SessionConstantsAssetUserCommunity;
import org.fermat.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAskConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantRequestAlreadySendActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_user.interfaces.IdentityAssetUser;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * Added by Jinmy Bohorquez 09/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class ConnectDialog extends FermatDialog<AssetUserCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

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

    private final Actor actor;
    private final IdentityAssetUser identity;

    List<ActorAssetUser> userConnect;

    public ConnectDialog(final Activity a,
                         final AssetUserCommunitySubAppSession actorUserSubAppSession,
                         final SubAppResourcesProviderManager subAppResources,
                         final Actor actor,
                         final IdentityAssetUser identity) {

        super(a, actorUserSubAppSession, subAppResources);

        this.actor = actor;
        this.identity = identity;
    }

    public ConnectDialog(Activity a,
                         final AssetUserCommunitySubAppSession actorUserSubAppSession,
                         final SubAppResourcesProviderManager subAppResources) {
        super(a, actorUserSubAppSession, subAppResources);
        this.actor = null;
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
            try {
                //image null
                if (actor != null) {
                    userConnect = new ArrayList<>();

                    userConnect.add(actor);

                    getSession().getModuleManager().askActorAssetUserForConnection(userConnect);

                    Intent broadcast = new Intent(SessionConstantsAssetUserCommunity.LOCAL_BROADCAST_CHANNEL);
                    broadcast.putExtra(SessionConstantsAssetUserCommunity.BROADCAST_CONNECTED_UPDATE, true);
                    sendLocalBroadcast(broadcast);
                    Toast.makeText(getContext(), "Connection request sent", Toast.LENGTH_SHORT).show();
                } else {
                    super.toastDefaultError();
                }
            } catch (CantRequestAlreadySendActorAssetException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            } catch (CantAskConnectionActorAssetException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }
}