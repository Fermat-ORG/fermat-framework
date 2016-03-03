package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ConnectionListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveContactException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;*/
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.AbsListView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListAdapter;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
//import com.bitdubai.fermat_cht_api.layer.chat_module.interfaces.ChatModuleManager;
//import com.bitdubai.fermat_cht_api.layer.platform_service.error_manager.interfaces.ErrorManager;


/**
 * Connections List fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 20/01/16
 * @version 1.0
 *
 */
public class ConnectionsListFragment extends AbstractFermatFragment {

    public List<Contact> contacts;
    private Contact contactl;

    // Whether or not this fragment is showing in a two-pane layout
    private boolean mIsTwoPaneLayout;

    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    private Toolbar toolbar;
    ListView list;
    // Defines a tag for identifying log entries
    String TAG="CHT_ConnectionsListFragment";
    ArrayList<String> contactname=new ArrayList<String>();
    ArrayList<Integer> contacticon=new ArrayList<Integer>();
    ArrayList<UUID> contactid=new ArrayList<UUID>();
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static ConnectionsListFragment newInstance() {
        return new ConnectionsListFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            chatSession=((ChatSession) appSession);
            moduleManager= chatSession.getModuleManager();
            chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        }catch (Exception e)
        {
            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.connection_list_fragment, container, false);
        TextView text=(TextView) layout.findViewById(R.id.text);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        try {
//            //Comentar, solo para pruebas
//            ContactImpl cadded=new ContactImpl();
//            cadded.setContactId(UUID.randomUUID());
//            cadded.setAlias("josejcb");
//            cadded.setRemoteActorPublicKey("jose");
//            cadded.setRemoteActorType(PlatformComponentType.ACTOR_ASSET_USER);
//            String dateString = "30/09/2014";
//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = sdf.parse(dateString);
//            long startDate = date.getTime();
//            cadded.setCreationDate(startDate);
//            cadded.setRemoteName("No hay nadie conectado");
//            chatManager.saveContact(cadded);
//            //Fin Comentar
            List <Contact> con=  chatManager.getContacts();
            int size = con.size();
            if (size > 0) {
                for (int i=0;i<size;i++){
                    if(!con.get(i).getRemoteName().equals("Not registered contact")) {
                        contactname.add(con.get(i).getAlias());
                        contactid.add(con.get(i).getContactId());
                        contacticon.add(R.drawable.cht_profile_list_icon);
                    }
                }
                text.setVisibility(View.GONE);
            }else{
                text.setVisibility(View.VISIBLE);
                text.setText("No Connections");
            }
        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
            }
        });

        ConnectionListAdapter adapter=new ConnectionListAdapter(getActivity(), contactname, contacticon, contactid, errorManager);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Contact contactexist= chatSession.getSelectedContactToUpdate();
                    if(contactexist!=null) {
                        if (contactexist.getRemoteActorPublicKey().equals("CONTACTTOUPDATE_DATA")){
                            UUID contactidnew = contactexist.getContactId();
                            contactexist=chatManager.getContactByContactId(contactid.get(position));
                            Chat chat=chatManager.getChatByChatId((UUID)appSession.getData("chatid"));
                            chat.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
                            chatManager.saveChat(chat);
                            Contact contactnew = new ContactImpl();
                            contactnew=chatManager.getContactByContactId(contactidnew);
                            contactnew.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
                            contactnew.setAlias(contactexist.getAlias());
                            contactnew.setRemoteName(contactexist.getRemoteName());
                            contactnew.setRemoteActorType(contactexist.getRemoteActorType());
                            chatManager.saveContact(contactnew);
                            Contact deleteContact;
                            for (int i = 0; i < chatManager.getContacts().size(); i++) {
                                deleteContact=chatManager.getContacts().get(i);
                                if(deleteContact.getRemoteName().equals("Not registered contact")){
                                    if(deleteContact.getContactId().equals(contactidnew)) {
                                        chatManager.deleteContact(deleteContact);
                                    }
                                }

                            }
                            chatManager.deleteContact(contactexist);
                            appSession.setData(ChatSession.CONTACTTOUPDATE_DATA, null);
                            appSession.setData("whocallme", "contact");
                            appSession.setData(ChatSession.CONTACT_DATA, chatManager.getContactByContactId(contactidnew));
                            Toast.makeText(getActivity(), "Connection added as Contact", Toast.LENGTH_SHORT).show();
                            changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
                        }
                    }else {
                        final int pos=position;
                        Contact contacto = chatManager.getContactByContactId(contactid.get(pos));

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setMessage("Do you want to add "+contacto.getRemoteName()+" to your Contact List?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        try {
                                            appSession.setData(ChatSession.CONNECTION_DATA, chatManager.getContactByContactId(contactid.get(pos)));
                                            Contact conn = chatSession.getSelectedConnection();
                                            chatManager.saveContact(conn);
                                            Toast.makeText(getActivity(), "Contact added", Toast.LENGTH_SHORT).show();
                                            changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
                                        }catch(CantSaveContactException e) {
                                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                        }catch (Exception e){
                                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                        }
                                        changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        try {
                                            dialog.cancel();
                                        } catch (Exception e) {
                                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                        }
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
//                        if (contactexist.getRemoteActorPublicKey().equals("CONTACTTOUPDATE_DATA")){
//                            UUID contactidnew = contactexist.getContactId();
//                            contactexist=chatManager.getContactByContactId(contactid.get(position));
//                            Chat chat=chatSession.getSelectedChat();
//                            chat.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
//                            chatManager.saveChat(chat);
//                            Contact contactnew = new ContactImpl();
//                            contactnew=chatManager.getContactByContactId(contactidnew);
//                            contactnew.setRemoteActorPublicKey(contactexist.getRemoteActorPublicKey());
//                            contactnew.setAlias(contactexist.getAlias());
//                            contactnew.setRemoteName(contactexist.getRemoteName());
//                            contactnew.setRemoteActorType(contactexist.getRemoteActorType());
//                            chatManager.saveContact(contactnew);
//                            chatManager.deleteContact(contactexist);
//                            appSession.setData(ChatSession.CONTACTTOUPDATE_DATA, null);
//                            changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
//                            appSession.setData("whocallme", "contact");
//                            appSession.setData(ChatSession.CONTACT_DATA, chatManager.getContactByContactId(contactidnew));
//                            Toast.makeText(getActivity(), "Connection added as Contact", Toast.LENGTH_SHORT).show();
//                            changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
//                        }
//                    }else {
//                        appSession.setData(ChatSession.CONNECTION_DATA, chatManager.getContactByContactId(contactid.get(position)));
//                        Contact conn = chatSession.getSelectedConnection();
//                        //chatManager.saveContact(conn);
//                        Toast.makeText(getActivity(), "Connection added as Contact", Toast.LENGTH_SHORT).show();
//                        //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//                    }

                }catch(CantSaveChatException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }catch(CantDeleteContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }catch(CantSaveContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }catch(CantGetContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }catch (Exception e){
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();

                        try {
                            List <Contact> con=  chatManager.getContacts();
                            if (con.size() > 0) {
                                contactname.clear();
                                contactid.clear();
                                contacticon.clear();
                                for (int i=0;i<con.size();i++){
                                    if(!con.get(i).getRemoteName().equals("Not registered contact")) {
                                        contactname.add(con.get(i).getAlias());
                                        contactid.add(con.get(i).getContactId());
                                        contacticon.add(R.drawable.cht_profile_list_icon);
                                    }
                                }
                                final ConnectionListAdapter adaptador =
                                        new ConnectionListAdapter(getActivity(), contactname, contacticon, contactid,errorManager);
                                adaptador.refreshEvents(contactname, contacticon, contactid);
                                //adaptador.notifyDataSetChanged();

                                list.invalidateViews();
                                list.requestLayout();
                            }else{
                                Toast.makeText(getActivity(), "No Connections", Toast.LENGTH_SHORT).show();
                            }
                        } catch (CantGetContactException e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        return layout;
        // Inflate the list fragment layout
        //return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

}
