package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Settings Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 09/03/16.
 * @version 1.0
 */

public class ChatErrorReportFragment extends AbstractFermatFragment {

    // Fermat Managers
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatPreferenceSettings chatSettings;
    private ChatSession chatSession;

    private Button okBtn;
    private EditText messageEdit;
    private Button cancelBtn;

    public static ChatErrorReportFragment newInstance() { return new ChatErrorReportFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatSession = ((ChatSession) appSession);
            moduleManager = chatSession.getModuleManager();
            chatManager = moduleManager.getChatManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.send_errors_report, container, false);
        messageEdit = (EditText) layout.findViewById(R.id.messageEdit);
        okBtn = (Button) layout.findViewById(R.id.okButton);
        cancelBtn = (Button) layout.findViewById(R.id.cancelButton);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageEdit.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }else {
                    try {
                        sendErrorReport(messageText);
                        messageEdit.getText().clear();
                    } catch (Exception e) {
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });

        return layout;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate the menu items
        //inflater.inflate(R.menu.chat_menu, menu);
    }



}