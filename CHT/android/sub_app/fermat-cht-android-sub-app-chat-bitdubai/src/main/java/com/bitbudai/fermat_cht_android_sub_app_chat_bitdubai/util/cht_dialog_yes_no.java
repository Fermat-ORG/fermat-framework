package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

/**
 * Created by richardalexander on 09/03/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 17/03/16.
 */
public class cht_dialog_yes_no extends FermatDialog implements View.OnClickListener {
    FermatButton btn_yes, btn_no;
    TextView txt_title, txt_body;
    private ChatManager chatManager;
    private FermatSession appSession;
    private ErrorManager errorManager;
    int AlertType = 0;
    String body, title;
    boolean addcontact = false;
    public boolean delete_contact = false;
    public boolean delete_chat = false;
    public boolean clean_chat = false;
    public boolean delete_chats = false;

    public cht_dialog_yes_no(Context activity,
                             FermatSession appSession,
                             ChatManager chatManager,
                             ErrorManager errorManager) {
        super(activity, appSession, null);
        this.appSession = appSession;
        this.chatManager = chatManager;
        this.errorManager = errorManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txt_title = (TextView) this.findViewById(R.id.cht_alert_txt_title_new);
        txt_body = (TextView) this.findViewById(R.id.cht_alert_txt_body_new);
        btn_yes = (FermatButton) this.findViewById(R.id.cht_alert_btn_yes_new);
        btn_no = (FermatButton) this.findViewById(R.id.cht_alert_btn_no_new);

        txt_title.setText(title);
        txt_body.setText(body);
        setUpListeners();
    }

    public void setTextBody(String txt) {
        body = txt;
    }

    public void setTextTitle(String txt) {
        title = txt;
    }

    public void setType(String txt) {
        if (txt.equals("clean-chat")) {
            AlertType = 5;
        }
        if (txt.equals("delete-chat")) {
            AlertType = 4;
        }
        if (txt.equals("delete-chats")) {
            AlertType = 3;
        }
        if (txt.equals("delete-contact")) {
            AlertType = 2;
        }
        if (txt.equals("add-connections")) {
            AlertType = 1;
        }
    }

    protected int setLayoutId() {
        return R.layout.cht_alert_dialog_yes_no_new;
    }

    private void setUpListeners() {
        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    public boolean cleanChat(){
        return clean_chat;
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cht_alert_btn_yes_new) {
            dismiss();
            if (AlertType == 1) {
                try {

                    Toast.makeText(getActivity(), "Contact added", Toast.LENGTH_SHORT).show();

                    addcontact = true;

                } catch (Exception e) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }

            } else if (AlertType == 2) {

                delete_contact = true;
            } else if (AlertType == 3) {

                try {
                    try {
                        // Delete chats and refresh view
                        chatManager.deleteAllChats();

                    } catch (CantDeleteChatException e) {
                        if (errorManager != null)
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    } catch (Exception e) {
                        if (errorManager != null)
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                    dismiss();
                } catch (Exception e) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                delete_chats = true;
            } else if (AlertType == 4) {
                try {
                    try {
                        // Get the info of chat selected from session
                        Chat chat = (Chat) appSession.getData(ChatSessionReferenceApp.CHAT_DATA);
                        // Delete chat and refresh view
                        chatManager.deleteChat(chat.getChatId(), true);
                    } catch (CantDeleteChatException e) {
                        if (errorManager != null)
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    } catch (Exception e) {
                        if (errorManager != null)
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                    dismiss();
                } catch (Exception e) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                delete_chat = true;
            } else if (AlertType == 5) {
                try {
                    try {
                        // Get the info of chat selected from session
                        Chat chat = (Chat) appSession.getData(ChatSessionReferenceApp.CHAT_DATA);//chatSession.getSelectedChat();
                        // Delete chat and refresh view
                        chatManager.deleteChat(chat.getChatId(), false);
                    } catch (Exception e) {
                        if (errorManager != null)
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                    dismiss();
                } catch (Exception e) {
                    if (errorManager != null)
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                clean_chat = true;
            }
        }

        if (id == R.id.cht_alert_btn_no_new) {
            dismiss();
        }
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
