package org.fermat.fermat_dap_android_sub_app_redeem_point_community.popup;

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
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.SessionConstantRedeemPointCommunity;
import org.fermat.fermat_dap_api.layer.dap_actor_network_service.exceptions.CantCancelConnectionActorAssetException;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;


/**
 * Added by Jinmy Bohorquez 06/03/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class CancelDialog extends FermatDialog<ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager>, ResourceProviderManager> implements View.OnClickListener {

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

    private final Actor actor;
    private final RedeemPointIdentity identity;

    public CancelDialog(final Activity activity,
                        final ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager> assetRedeemCommunitySubAppSession,
                        final SubAppResourcesProviderManager subAppResources,
                        final Actor actor,
                        final RedeemPointIdentity identity) {

        super(activity, assetRedeemCommunitySubAppSession, subAppResources);

        this.actor = actor;
        this.identity = identity;
    }

    public CancelDialog(Activity a,
                        final ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager> assetRedeemCommunitySubAppSession,
                        final SubAppResourcesProviderManager subAppResources) {

        super(a, assetRedeemCommunitySubAppSession, subAppResources);
        this.actor = null;
        this.identity = null;
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
            try {
                getSession().getModuleManager().cancelActorAssetRedeem(
//                        identity.getPublicKey(),  // ACTOR INSIDE/LOCAL
                        actor.getActorPublicKey() // ACTOR OUTSIDE/EXTERNAL
                );
                getSession().setData(SessionConstantRedeemPointCommunity.IC_ACTION_REDEEM_NOTIFICATIONS_CANCELED, Boolean.TRUE);
                Toast.makeText(getContext(), "Connection has Canceled", Toast.LENGTH_SHORT).show();

                dismiss();
            } catch (CantCancelConnectionActorAssetException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();
        } else if (i == R.id.negative_button) {
            dismiss();
        }
    }


}