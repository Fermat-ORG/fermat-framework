package org.fermat.fermat_dap_android_sub_app_redeem_point_community.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_dap_android_sub_app_redeem_point_community_bitdubai.R;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import org.fermat.fermat_dap_android_sub_app_redeem_point_community.models.Actor;
import org.fermat.fermat_dap_android_sub_app_redeem_point_community.sessions.SessionConstantRedeemPointCommunity;
import org.fermat.fermat_dap_api.layer.dap_actor.redeem_point.interfaces.ActorAssetRedeemPoint;
import org.fermat.fermat_dap_api.layer.dap_identity.redeem_point.interfaces.RedeemPointIdentity;
import org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.interfaces.RedeemPointCommunitySubAppModuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Added by Jinmy Bohorquez 11/02/2016
 */
@SuppressWarnings("FieldCanBeLocal")
public class ConnectDialog extends FermatDialog<ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager>, ResourceProviderManager> implements View.OnClickListener {

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
    private final Actor actorRedeem;
    List<ActorAssetRedeemPoint> redeemConnect;

    private final RedeemPointIdentity identity;


    public ConnectDialog(final Activity a,
                         final ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager> assetRedeemCommunitySubAppSession,
                         final SubAppResourcesProviderManager subAppResources,
                         final Actor actorRedeem,
                         final RedeemPointIdentity identity) {

        super(a, assetRedeemCommunitySubAppSession, subAppResources);

        this.actorRedeem = actorRedeem;
        this.identity = identity;
    }

    public ConnectDialog(Activity a,
                         final ReferenceAppFermatSession<RedeemPointCommunitySubAppModuleManager> assetRedeemCommunitySubAppSession,
                         final SubAppResourcesProviderManager subAppResources) {

        super(a, assetRedeemCommunitySubAppSession, subAppResources);
        this.actorRedeem = null;
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
            if (actorRedeem != null) {
                redeemConnect = new ArrayList<>();
                FermatWorker worker = new FermatWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        redeemConnect.add(actorRedeem);

                        getSession().getModuleManager().askActorAssetRedeemForConnection(redeemConnect);

                        Intent broadcast = new Intent(SessionConstantRedeemPointCommunity.LOCAL_BROADCAST_CHANNEL);
                        broadcast.putExtra(SessionConstantRedeemPointCommunity.BROADCAST_CONNECTED_UPDATE, true);
                        sendLocalBroadcast(broadcast);
                        return true;
                    }
                };
                worker.setContext(getActivity());
                worker.setCallBack(new FermatWorkerCallBack() {
                    @Override
                    public void onPostExecute(Object... result) {
                        dismiss();
                        Toast.makeText(getContext(), R.string.dap_other_profile_request_send, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErrorOccurred(Exception ex) {
                        dismiss();
                        Toast.makeText(getActivity(), R.string.before_action_redeem, Toast.LENGTH_LONG).show();

                    }
                });
                worker.execute();
            } else if (i == R.id.negative_button) {
                dismiss();
            }
        }
    }
}