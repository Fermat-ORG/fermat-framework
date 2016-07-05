package com.bitdubai.sub_app.chat_community.common.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ActorConnectionRequestNotFoundException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.CantAcceptChatRequestException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.exceptions.ChatActorConnectionDenialFailedException;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.chat_actor_community.interfaces.ChatActorCommunitySubAppModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.sub_app.chat_community.session.ChatUserSubAppSessionReferenceApp;
import com.bitdubai.sub_app.chat_community.R;
import com.bitdubai.sub_app.chat_community.session.SessionConstants;

/**
 * AcceptDialog
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
 */
@SuppressWarnings("FieldCanBeLocal")
public class AcceptDialog
        extends FermatDialog<ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager>, SubAppResourcesProviderManager>
        implements View.OnClickListener {

    /**
     * UI components
     */
    private final ChatActorCommunityInformation chatUserInformation;
    private final ChatActorCommunitySelectableIdentity identity            ;

    private FermatTextView title      ;
    private FermatTextView description;
    private FermatTextView userName   ;
    private FermatButton   positiveBtn;
    private FermatButton   negativeBtn;

    public AcceptDialog(final Context activity              ,
                        final ReferenceAppFermatSession<ChatActorCommunitySubAppModuleManager> chatUserSubAppSession,
                        final SubAppResourcesProviderManager subAppResources       ,
                        final ChatActorCommunityInformation  chatUserInformation  ,
                        final ChatActorCommunitySelectableIdentity identity              ) {

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

        title.setText("Connection Request");
        description.setText(chatUserInformation.getAlias() + " wants to be your friend in P2P Chat");
        userName.setText("");

    }

    @Override
    protected int setLayoutId() {
        return R.layout.cht_comm_dialog_builder;
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
                    getSession().getModuleManager()
                     .acceptChatActor(chatUserInformation.getConnectionId());
                    getSession().setData(SessionConstants.NOTIFICATION_ACCEPTED,Boolean.TRUE);
                    Toast.makeText(getContext(),
                            chatUserInformation.getAlias() + " Accepted connection request",
                            Toast.LENGTH_SHORT).show();
                } else {
                    super.toastDefaultError();
                }
            } catch (final CantAcceptChatRequestException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            } catch (final ActorConnectionRequestNotFoundException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();

        } else if (i == R.id.negative_button) {
            try {
                if (chatUserInformation != null && identity != null) {
                    getSession().getModuleManager()
                        .denyChatConnection(chatUserInformation.getConnectionId());
                }else {
                    super.toastDefaultError();
                }
            } catch (final ChatActorConnectionDenialFailedException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            } catch (final ActorConnectionRequestNotFoundException e) {
                super.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                super.toastDefaultError();
            }
            dismiss();
        }
    }
}
