package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ConnectionListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.DialogConnectionListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.ui.dialogs.FermatDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactConnectionException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ContactConnection;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import java.util.UUID;


/**
 * Created by Lozadaa on 05/03/16.
 */
public class cht_dialog_connections extends FermatDialog<FermatSession, SubAppResourcesProviderManager> implements View.OnClickListener {

    private final Activity activity;
    private static final String TAG = "cht_dialog_connections";
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    public List<ContactConnection> contacts;
    ArrayList<String> contactname=new ArrayList<String>();
    ArrayList<Bitmap> contacticon=new ArrayList<>();
    ArrayList<UUID> contactid=new ArrayList<UUID>();
    ListView list;
    CheckBox check = (CheckBox) findViewById(R.id.checkBoxdialog);
    Button btn_add, btn_cancel;
    public cht_dialog_connections(Activity activity, FermatSession fermatSession, SubAppResourcesProviderManager resources, ChatManager chatManager) {
        super(activity , fermatSession, null);
        this.activity = activity;
        this.chatManager = chatManager;
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            chatSession=((ChatSession) getSession());
            moduleManager= chatSession.getModuleManager();
            chatManager=moduleManager.getChatManager();
            errorManager=getSession().getErrorManager();

        }catch (Exception e)
        {
            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        } check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Contact contactexist = chatSession.getSelectedContactToUpdate();
                    if (contactexist != null) {
                        if (contactexist.getRemoteActorPublicKey().equals("CONTACTTOUPDATE_DATA")) {
                            UUID contactidnew = contactexist.getContactId();
                            contactexist = chatManager.getContactByContactId(contactid.get((int) check.getTag()));
                            Chat chat = chatManager.getChatByChatId((UUID) getSession().getData("chatid"));
                            chat.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
                            chatManager.saveChat(chat);
                            Contact contactnew = new ContactImpl();
                            contactnew = chatManager.getContactByContactId(contactidnew);
                            contactnew.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
                            contactnew.setAlias(contactexist.getAlias());
                            contactnew.setRemoteName(contactexist.getRemoteName());
                            contactnew.setRemoteActorType(contactexist.getRemoteActorType());
                            chatManager.saveContact(contactnew);
                            Contact deleteContact;
                            for (int i = 0; i < chatManager.getContacts().size(); i++) {
                                deleteContact = chatManager.getContacts().get(i);
                                if (deleteContact.getRemoteName().equals("Not registered contact")) {
                                    if (deleteContact.getContactId().equals(contactidnew)) {
                                        chatManager.deleteContact(deleteContact);
                                    }
                                }

                            }
                            chatManager.deleteContact(contactexist);
                            getSession().setData(ChatSession.CONTACTTOUPDATE_DATA, null);
                            getSession().setData("whocallme", "contact");
                            getSession().setData(ChatSession.CONTACT_DATA, chatManager.getContactByContactId(contactidnew));
                            Toast.makeText(getActivity(), "Connection added as Contact", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    }
                } catch (CantSaveChatException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (CantDeleteContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (CantSaveContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (CantGetContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }

            }
        });

        TextView text=(TextView) findViewById(R.id.text);
         btn_add = (Button) findViewById(R.id.btn_add);
         btn_cancel = (Button) findViewById(R.id.btn_cancel);
        setUpListeners();
        try {
            List<ContactConnection> con = chatManager.getContactConnections();
            int size = con.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    contactname.add(con.get(i).getAlias());
                    contactid.add(con.get(i).getContactId());
                    ByteArrayInputStream bytes = new ByteArrayInputStream(con.get(i).getProfileImage());
                    BitmapDrawable bmd = new BitmapDrawable(bytes);
                    contacticon.add(bmd.getBitmap());
                }
                text.setVisibility(View.GONE);
            } else {
                text.setVisibility(View.VISIBLE);
                text.setText("No Connections");
            }

        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        DialogConnectionListAdapter adapter=new DialogConnectionListAdapter(getActivity(), contactname, contacticon, contactid, errorManager);
        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);






    }

    protected int setLayoutId() {
            return R.layout.cht_dialog_connections;
    }

    private void setUpListeners() {
        check.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_add) {
            final ContactConnection contactConn;
            try {
                contactConn = chatManager.getContactConnectionByContactId(contactid.get(1));
                if (contactConn.getRemoteName() != null) {
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
                        } else {
                            Toast.makeText(getActivity(), "Contact already exist", Toast.LENGTH_SHORT).show();
                            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
                        }
                    } catch (CantSaveContactException e) {
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    } catch (Exception e) {
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                }
            } catch (CantGetContactConnectionException e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
        if (id == R.id.btn_cancel) {
            dismiss();
        }

    }
    @Override
    protected int setWindowFeature() {
        return Window.FEATURE_NO_TITLE;
    }

}
