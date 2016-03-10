package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.UUID;

/**
 * Created by richardalexander on 09/03/16.
 */
public class cht_dialog_yes_no extends FermatDialog  implements View.OnClickListener {
    private final ContactConnection contactConn;
    Button btn_yes,btn_no;
    TextView txt_title,txt_body;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    private AdapterCallbackContacts mAdapterCallback;

    public cht_dialog_yes_no(Context activity, FermatSession fermatSession, ResourceProviderManager resources, ContactConnection contactConnm, AdapterCallbackContacts mAdapterCallback) {
        super(activity, fermatSession, resources);
        this.contactConn = contactConnm;
        this.mAdapterCallback = mAdapterCallback;
    }

    public static interface AdapterCallbackContacts {
        void onMethodCallbackContacts();
    }

    @Override

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        txt_title = (TextView)this.findViewById(R.id.cht_alert_txt_title);
        txt_body = (TextView)this.findViewById(R.id.cht_alert_txt_body);
        btn_yes = (Button)this.findViewById(R.id.cht_alert_btn_yes);
        btn_no = (Button) this.findViewById(R.id.cht_alert_btn_no);
        try{
            chatSession=((ChatSession) getSession());
            moduleManager= chatSession.getModuleManager();
            chatManager=moduleManager.getChatManager();
            errorManager=getSession().getErrorManager();

        }catch (Exception e)
        {
            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }

        txt_body.setText("Do you want to add " + contactConn.getRemoteName() + " to your Contact List?");
        setUpListeners();
    }

    protected int setLayoutId() {
        return R.layout.cht_alert_dialog_yes_no;
    }

    private void setUpListeners() {
        btn_yes.setOnClickListener(this);
        btn_no.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.cht_alert_btn_yes) {
            dismiss();
            try {
                //appSession.setData(ChatSession.CONNECTION_DATA, contactConn);
                //Contact conn = chatSession.getSelectedConnection();
                if (chatManager.getContactByLocalPublicKey(contactConn.getRemoteActorPublicKey()) == null) {
                    ContactImpl newContact = new ContactImpl();
                    newContact.setAlias(contactConn.getAlias());
                    newContact.setRemoteActorType(contactConn.getRemoteActorType());
                    newContact.setRemoteActorPublicKey(contactConn.getRemoteActorPublicKey());
                    newContact.setRemoteName(contactConn.getRemoteName());
                    newContact.setContactId(UUID.randomUUID());
                    newContact.setCreationDate(System.currentTimeMillis());
                    newContact.setContactStatus(contactConn.getContactStatus());
                    newContact.setProfileImage(contactConn.getProfileImage());
                    chatManager.saveContact(newContact);
                    Toast.makeText(getActivity(), "Contact added", Toast.LENGTH_SHORT).show();
                    //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Contact already exist", Toast.LENGTH_SHORT).show();

                    //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
                    dismiss();
                }
            } catch (CantSaveContactException e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            } catch (Exception e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            //changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
            dismiss();
            mAdapterCallback.onMethodCallbackContacts();//solution to access to update contacts. j
        }

        if (id == R.id.cht_alert_btn_no) {
            dismiss();
        }
    }

    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }
}
