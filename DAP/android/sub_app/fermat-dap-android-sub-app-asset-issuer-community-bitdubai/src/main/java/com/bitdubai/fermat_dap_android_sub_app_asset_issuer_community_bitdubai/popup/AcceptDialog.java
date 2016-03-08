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
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.models.ActorIssuer;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.AssetIssuerCommunitySubAppSession;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.sessions.SessionConstantsAssetIssuerCommunity;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_issuer.interfaces.ActorAssetIssuer;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantAcceptActorAssetUserException;
import com.bitdubai.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantDenyConnectionActorAssetException;
import com.bitdubai.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;

import java.util.ArrayList;
import java.util.List;

/**
 * Added by Jinmy Bohorquez 09/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog extends FermatDialog<AssetIssuerCommunitySubAppSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final ActorIssuer actorIssuer;
    private final IdentityAssetIssuer identity;

    private FermatTextView title;
    private FermatTextView description;
    private FermatTextView userName;
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;

    public AcceptDialog(final Activity activity,
                        final AssetIssuerCommunitySubAppSession assetIssuerCommunitySubAppSession,
                        final SubAppResourcesProviderManager subAppResources,
                        final ActorIssuer actorIssuer,
                        final IdentityAssetIssuer identity) {

        super(activity, assetIssuerCommunitySubAppSession, subAppResources);

        this.actorIssuer = actorIssuer;
        this.identity = identity;
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = (FermatTextView) findViewById(R.id.title);
        description = (FermatTextView) findViewById(R.id.description);
        userName = (FermatTextView) findViewById(R.id.user_name);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);

        title.setText(R.string.connect);
        description.setText(R.string.connection_request_accept);
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
        List<ActorAssetIssuer> toConnect = new ArrayList<>();
        toConnect.add(actorIssuer.getRecord());

        if (i == R.id.positive_button) {
            try {
                //&& identity != null) {
                getSession().getModuleManager().acceptActorAssetIssuer(
                        identity.getPublicKey(),//USER LOCAL
                        toConnect.get(0));// USER OUTSIDE
                getSession().setData(SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_NOTIFICATIONS_ACCEPTED, Boolean.TRUE);
                //Toast.makeText(getContext(), actorIssuer.getRecord().getName() + " " + R.string.connection_request_accepted, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),"Connection has been acepted" , Toast.LENGTH_SHORT).show();
                dismiss();
            } catch (final CantAcceptActorAssetUserException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        } else if (i == R.id.negative_button) {
            try {
                //&& identity != null)
                getSession().getModuleManager().denyConnectionActorAssetIssuer(
                        identity.getPublicKey(),
                        actorIssuer.getRecord());// USER OUTSIDE
//                getSession().setData(SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_NOTIFICATIONS_DENIED, Boolean.FALSE);
//                Toast.makeText(getContext(), actorIssuer.getRecord().getName() + " " + R.string.connection_request_deny, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext()," Connection has been denied" , Toast.LENGTH_SHORT).show();

                dismiss();
            } catch (final CantDenyConnectionActorAssetException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();
        }
    }
}
