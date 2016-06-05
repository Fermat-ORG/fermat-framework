package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_asset_issuer_community_bitdubai.R;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_android_sub_app_asset_issuer_community.models.ActorIssuer;
import org.fermat.fermat_dap_api.layer.dap_identity.asset_issuer.interfaces.IdentityAssetIssuer;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.asset_issuer_community.interfaces.AssetIssuerCommunitySubAppModuleManager;


/**
 * Added by Jinmy Bohorquez 09/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class DisconectDialog extends FermatDialog<ReferenceAppFermatSession<AssetIssuerCommunitySubAppModuleManager>, ResourceProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private FermatButton positiveBtn;
    private FermatButton negativeBtn;
    private FermatTextView mDescription;
    private FermatTextView mUsername;
    private FermatTextView mTitle;
    private CharSequence description;
    private CharSequence username;
    private CharSequence title;

    private final ActorIssuer actorIssuer;
    private final IdentityAssetIssuer identity;

    public DisconectDialog(final Activity activity,
                           final ReferenceAppFermatSession<AssetIssuerCommunitySubAppModuleManager> assetIssuerCommunitySubAppSession,
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

        mDescription = (FermatTextView) findViewById(R.id.description);
        mUsername = (FermatTextView) findViewById(R.id.user_name);
        mTitle = (FermatTextView) findViewById(R.id.title);
        positiveBtn = (FermatButton) findViewById(R.id.positive_button);
        negativeBtn = (FermatButton) findViewById(R.id.negative_button);

        positiveBtn.setOnClickListener(this);
        negativeBtn.setOnClickListener(this);
        mDescription.setText(description != null ? description : "");
        mUsername.setText(username != null ? username : "");
        mTitle.setText(title != null ? title : "");

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
//            Toast.makeText(getContext(), R.string.disconnected, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Not available for this Actor", Toast.LENGTH_SHORT).show();
//            try {
//                //&& identity != null) {
//                getSession().getModuleManager().acceptActorAssetIssuer(
//                        identity.getPublicKey(),//ACTOR INSIDE/LOCAL
//                        actorIssuer.getRecord());// ACTOR OUTSIDE/EXTERNAL
//                getSession().setData(SessionConstantsAssetIssuerCommunity.IC_ACTION_ISSUER_NOTIFICATIONS_ACCEPTED, Boolean.TRUE);
//                //Toast.makeText(getContext(), actorIssuer.getRecord().getName() + " " + R.string.connection_request_accepted, Toast.LENGTH_LONG).show();
//                Toast.makeText(getContext(),"Connection has been accepted" , Toast.LENGTH_SHORT).show();
//                dismiss();
//            } catch (final CantAcceptActorAssetUserException e) {
//                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
//                super.toastDefaultError();
//            Toast.makeText(getContext(), R.string.disconnected, Toast.LENGTH_SHORT).show();
//            }
            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }


}