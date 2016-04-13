package com.bitdubai.sub_app.chat_community.common.popups;

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
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantAcceptRequestException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.IntraUserConnectionDenialFailedException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.LinkedChatActorIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSession;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.session.SessionConstants;

/**
 * AcceptDialog
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog extends FermatDialog<ChatUserSubAppSession,
        SubAppResourcesProviderManager> implements View.OnClickListener {

    /**
     * UI components
     */
    private final ChatActorCommunityInformation chatUserInformation;
    private final LinkedChatActorIdentity identity            ;

    private FermatTextView title      ;
    private FermatTextView description;
    private FermatTextView userName   ;
    private FermatButton   positiveBtn;
    private FermatButton   negativeBtn;

    public AcceptDialog(final Activity                       activity              ,
                        final ChatUserSubAppSession          chatUserSubAppSession,
                        final SubAppResourcesProviderManager subAppResources       ,
                        final ChatActorCommunityInformation  chatUserInformation  ,
                        final LinkedChatActorIdentity        identity              ) {

        super(activity, chatUserSubAppSession, subAppResources);

        this.chatUserInformation = chatUserInformation;
        this.identity            = identity;
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
        userName.setText(chatUserInformation.getActorAlias());

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
                if (chatUserInformation != null && identity != null) {
                    getSession().getModuleManager().acceptIntraUser(identity.getPublicKey(),
                            chatUserInformation.getName(),
                            chatUserInformation.getPublicKey(),
                            chatUserInformation.getProfileImage());
                    getSession().setData(SessionConstants.NOTIFICATION_ACCEPTED,Boolean.TRUE);
                    Toast.makeText(getContext(),
                            chatUserInformation.getName() + " Accepted connection request",
                            Toast.LENGTH_SHORT).show();
                } else {
                    super.toastDefaultError();
                }
                dismiss();
            } catch (final CantAcceptRequestException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        } else if (i == R.id.negative_button) {
            try {
                if (chatUserInformation != null && identity != null)
                    getSession().getModuleManager().denyConnection(identity.getPublicKey(), intraUserInformation.getPublicKey());
                else {
                    super.toastDefaultError();
                }
                dismiss();
            } catch (final IntraUserConnectionDenialFailedException e) {

                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();
        }
    }
}
