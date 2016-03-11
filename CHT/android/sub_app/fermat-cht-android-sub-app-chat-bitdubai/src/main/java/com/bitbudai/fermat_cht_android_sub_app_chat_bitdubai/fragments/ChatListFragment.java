package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;


import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChtConstants;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantCreateSelfIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatUserIdentityException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.TitleBar;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Chat List Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16
 * @version 1.0
 * Upd
 *
 */

public class ChatListFragment extends AbstractFermatFragment{

    // Bundle key for saving previously selected search result item
    //private static final String STATE_PREVIOUSLY_SELECTED_KEY =    "SELECTED_ITEM";
//    private ChatListAdapter adapter; // The main query adapter
//    private ImageLoader mImageLoader; // Handles loading the chat image in a background thread
//    private String mSearchTerm; // Stores the current search query term
//
//    private OnChatInteractionListener mOnChatSelectedListener;
//
//    // Stores the previously selected search item so that on a configuration change the same item
//    // can be reselected again
//    private int mPreviouslySelectedSearchItem = 0;
//    //public ArrayList<ContactList> contactList;
//   // private ListView contactsContainer;
//    //private ContactsAdapter adapter;
//
//    // Whether or not the search query has changed since the last time the loader was refreshed
//    private boolean mSearchQueryChanged;
//
//    // Whether or not this fragment is showing in a two-pane layout
      private boolean mIsTwoPaneLayout;
//
//    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
//    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
//    private boolean mIsSearchResultView = false;

    /*boolean dualPane;
    private static int currentCheckPosition = 0;
    private ProgressBar progressBar;
    private static ChatsList chats;
    private LinearLayout layout;
    private ProgressDialog mProgressDialog;
    private boolean fragmentStopped = false;

    private FermatTextView noChatsMessage;

    private long clickedId;*/


    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatPreferenceSettings chatSettings;
    private ChatSession chatSession;
    ChatListAdapter adapter;

    ListView list;
    // Defines a tag for identifying log entries
    String TAG="CHT_ChatListFragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<String> infochat=new ArrayList<String>();
    ArrayList<ArrayList<String>> chatinfo=new ArrayList<ArrayList<String>>();   //work
    ArrayList<Bitmap> imgid=new ArrayList<>();
    TextView text;
    View layout;
    PresentationDialog presentationDialog;
    Typeface tf;
    private Toolbar toolbar;
    private Bitmap contactIcon;
    private BitmapDrawable contactIconCircular;
    private int size;
    ImageView noData;
    public static ChatListFragment newInstance() {
        return new ChatListFragment();}

    public void chatlistview (){

        int sizeofmessagelist=0;
        UUID chatidtemp;
        String contactid;
        String name,message,datemessage,chatid;
        try {

            //for (int i=0;i<chatManager.getChats().size();i++){
            List<Chat> chats = chatManager.getChats();
            if (chats != null && chats.size() > 0) {
                infochat.clear();
                for (Chat chat : chats) {//for (int i=0;i<chatManager.getChats().size();i++){
                    chatidtemp = chat.getChatId();
                    if (chatidtemp != null) {
                        List<Message> messageChat = chatManager.getMessageByChatId(chatidtemp);//
                        if (messageChat != null) {
                            Message mess = messageChat.get(0);//3er chat id en mensajes buscar
                            if (mess != null) {
                                List<Message> messl = chatManager.getMessageByChatId(chatidtemp);
                                contactid = String.valueOf(mess.getContactId());
                                Contact cont = chatManager.getContactByContactId(mess.getContactId());
                                name = cont.getRemoteName();
                                message = messl.get(messl.size() - 1).getMessage();
                                Chat chatl = chatManager.getChatByChatId(chatidtemp);
                                long milliseconds = chatl.getLastMessageDate().getTime() + (chatl.getLastMessageDate().getNanos() / 1000000);
                                if (Validate.isDateToday(new Date(DateFormat.getDateTimeInstance().format(new java.util.Date(milliseconds))))) {
                                    datemessage = new SimpleDateFormat("HH:mm").format(new java.util.Date(milliseconds));
                                } else {
                                    Date old = new Date(DateFormat.getDateTimeInstance().format(new java.util.Date(milliseconds)));
                                    Date today = new Date();
                                    long dias = (today.getTime() - old.getTime()) / (1000 * 60 * 60 * 24);
                                    if (dias == 1) {
                                        datemessage = "YESTERDAY";
                                    } else
                                        datemessage = new SimpleDateFormat("dd/MM/yy").format(new java.util.Date(milliseconds));//.toString();
                                }
                                chatid = chatidtemp.toString();
                                infochat.add(name + "@#@#" + message + "@#@#" + datemessage + "@#@#" + chatid + "@#@#" + contactid + "@#@#");
                                ByteArrayInputStream bytes = new ByteArrayInputStream(cont.getProfileImage());
                                BitmapDrawable bmd = new BitmapDrawable(bytes);
                                imgid.add(bmd.getBitmap());
                            }
                        }
                    }
                }
        }
        } catch (CantGetChatException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (CantGetMessageException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch(Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            chatSession = ((ChatSession) appSession);
            moduleManager = chatSession.getModuleManager();
            chatManager = moduleManager.getChatManager();
            //settingsManager = moduleManager.getSettingsManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        //Obtain chatSettings  or create new chat settings if first time opening chat platform
        chatSettings = null;
        try {
            chatSettings = moduleManager.getSettingsManager().loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            chatSettings = null;
        }

        if (chatSettings == null) {
            chatSettings = new ChatPreferenceSettings();
            chatSettings.setIsPresentationHelpEnabled(true);
            try {
                moduleManager.getSettingsManager().persistSettings(appSession.getAppPublicKey(), chatSettings);
            } catch (Exception e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
        try {
            chatManager.createSelfIdentities();
            List <ChatUserIdentity> con=  chatManager.getChatUserIdentities();
            size = con.size();
            if((chatSettings.getLocalPlatformComponentType()==null || chatSettings.getLocalPublicKey()==null) && size > 0) {
                ChatUserIdentity profileSelected = chatManager.getChatUserIdentity(con.get(0).getPublicKey());
                chatSettings.setProfileSelected(profileSelected.getPublicKey(), profileSelected.getPlatformComponentType());
            }
        }catch(CantCreateSelfIdentityException e)
        {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch(CantGetChatUserIdentityException e)
        {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        //      filldatabase();


        // Check if this fragment is part of a two-pane set up or a single pane by reading a
        // boolean from the application resource directories. This lets allows us to easily specify
        // which screen sizes should use a two-pane layout by setting this boolean in the
        // corresponding resource size-qualified directory.
        // mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
   }

    void updatevalues(){
        try{
            if(!chatManager.getMessages().isEmpty()) {
                chatlistview();
                text.setVisibility(View.GONE);
                noData.setVisibility(View.GONE);
            }else{
                Toast.makeText(getActivity(), "No chats, swipe to create with contact table", Toast.LENGTH_SHORT).show();
                text.setVisibility(View.VISIBLE);
                noData.setVisibility(View.VISIBLE);
                text.setText(" ");
                text.setBackgroundResource(R.drawable.cht_empty_chat_background);
            }
        } catch (CantGetMessageException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private void setUpHelpChat(boolean checkButton) {
        try {
            presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.cht_banner)
                    .setIconRes(R.drawable.chat_subapp)
                    .setSubTitle(R.string.cht_chat_subtitle)
                    .setBody(R.string.cht_chat_body)
                    .setTextFooter(R.string.cht_chat_footer)
                    .setIsCheckEnabled(checkButton)
                    .build();

            presentationDialog.show();
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue Medium.ttf");
        layout = inflater.inflate(R.layout.chats_list_fragment, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        text = (TextView) layout.findViewById(R.id.text);
        noData=(ImageView) layout.findViewById(R.id.nodata);
        //text.setTypeface(tf, Typeface.NORMAL);
        updatevalues();
        if (chatSettings.isHomeTutorialDialogEnabled() == true)
        {
            setUpHelpChat(false);
        }
        try {
            toolbar = getToolbar();
            if (chatSettings.getLocalPublicKey() != null) {
                ChatUserIdentity localUser = chatManager.getChatUserIdentity(chatSettings.getLocalPublicKey());
                //toolbar = getToolbar();
                //getContext().getActionBar().setTitle("");
                ByteArrayInputStream bytes = new ByteArrayInputStream(localUser.getImage());
                BitmapDrawable bmd = new BitmapDrawable(bytes);
                contactIcon =bmd.getBitmap();
                //toolbar.setTitle(localUser.getAlias());
                contactIconCircular = new BitmapDrawable( getResources(), Utils.getRoundedShape( contactIcon, 100));//in the future, this image should come from chatmanager
                toolbar.setLogo(contactIconCircular);
                //getActivity().getActionBar().setLogo(contactIconCircular);
            }
        }catch (CantGetChatUserIdentityException e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
//        if (chatSettings.getLocalActorType() == null || chatSettings.getLocalActorType() == null)
//        {
//            try {
//                changeActivity(Activities.CHT_CHAT_OPEN_PROFILELIST, appSession.getAppPublicKey());
//            } catch (Exception e) {
//                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            }
//        }

        adapter=new ChatListAdapter(getActivity(), infochat, imgid, errorManager);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String values = infochat.get(position);
                List<String> converter = new ArrayList<String>();
                converter.addAll(Arrays.asList(values.split("@#@#")));
                try{
                    appSession.setData("whocallme", "chatlist");
                    appSession.setData("contactid", chatManager.getContactByContactId(UUID.fromString(converter.get(4))));//esto no es necesario, haces click a un chat
                    //appSession.setData(ChatSession.CHAT_DATA, chatManager.getChatByChatId(UUID.fromString(converter.get(3))));//este si hace falta
                    changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
                } catch (CantGetContactException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch(Exception e)
                {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });
/*
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //TODO: fix this
                            if (!chatManager.getContacts().isEmpty()) {
                                // specialfilldatabase();
                                updatevalues();
                                adapter.refreshEvents(infochat, imgid);
                            } else {
                                Toast.makeText(getActivity(), "No Chats now", Toast.LENGTH_SHORT).show();
                                text.setVisibility(View.VISIBLE);
                                text.setText(" ");
                                text.setBackgroundResource(R.drawable.cht_empty_chat_background);
                            }
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });*/
        return layout;
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        super.onUpdateViewOnUIThread(code);
   //     Toast.makeText(getActivity(), "Broadcast chatlist", Toast.LENGTH_SHORT).show();
        if(code.equals("13")){
            updatevalues();
            adapter.refreshEvents(infochat, imgid);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        try {
           if(chatManager.isIdentityDevice() != false) {//if((chatSettings.getLocalPlatformComponentType()!=null && chatSettings.getLocalPublicKey()!=null))
                // Inflate the menu items
                inflater.inflate(R.menu.chat_list_menu, menu);
                // Locate the search item
                //MenuItem searchItem = menu.findItem(R.id.menu_search);
           }
           menu.add(0, ChtConstants.CHT_ICON_HELP, 0, "help").setIcon(R.drawable.ic_menu_help_cht)
                   .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        } catch (CantGetChatUserIdentityException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == ChtConstants.CHT_ICON_HELP){
            setUpHelpChat(false);
        }
        if (id == R.id.menu_open_chat) {
            changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
            return true;
        }
        if (id == R.id.menu_switch_profile) {
            changeActivity(Activities.CHT_CHAT_OPEN_PROFILELIST, appSession.getAppPublicKey());
            return true;
        }
        if (id == R.id.menu_error_report) {
            changeActivity(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT, appSession.getAppPublicKey());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void specialfilldatabase(){
        ChatImpl dato;
        MessageImpl mess;
        ContactImpl cont=new ContactImpl();
        Calendar c = Calendar.getInstance();
        UUID chatid;
        UUID contactid;

        try {
            String dateString = "30/09/2014";
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(dateString);
            long startDate = date.getTime();
        }catch (java.text.ParseException e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        try {
            Long startDate=System.currentTimeMillis();
            //Chat
            if(!chatManager.getContacts().isEmpty()) {
                if(chatManager.getMessages().isEmpty()) {
                    for (int i = 0; i < chatManager.getContacts().size(); i++) {

                        if(chatManager.getContacts().get(i).getRemoteName().contains("chatlight")) {
                            chatid = UUID.randomUUID();
                            contactid = chatManager.getContacts().get(i).getContactId();
                            mess = new MessageImpl();
                            mess.setType(TypeMessage.INCOMMING);
                            mess.setStatus(MessageStatus.DELIVERED);
                            mess.setChatId(chatid);
                            mess.setMessage("HOLA A TODOS");
                            mess.setMessageDate(new Timestamp(startDate));
                            mess.setMessageId(UUID.randomUUID());
                            mess.setContactId(contactid);
                            chatManager.saveMessage(mess);
                            dato = new ChatImpl(chatid,
                                    UUID.randomUUID(),
                                    PlatformComponentType.ACTOR_ASSET_ISSUER,
                                    chatManager.getNetworkServicePublicKey(),
                                    chatManager.getContacts().get(i).getRemoteActorType(),
                                    chatManager.getContacts().get(i).getRemoteActorPublicKey(),
                                    "Nuevo",
                                    ChatStatus.VISSIBLE,
                                    new Timestamp(startDate),
                                    new Timestamp(startDate));
                            chatManager.saveChat(dato);
                        }
                    }
                }
            }else{
                System.out.println("\n\n################# NO CONTACT ON TABLE ###################");
            }
        }
        catch (CantGetContactException e) {
            System.out.println("/n/n CHT FILLDATA SAVECONTACT:"+e);
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (CantSaveMessageException e) {
            System.out.println("/n/n CHT FILLDATA SAVEMESSAGE:"+e);
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (CantSaveChatException e) {
            System.out.println("/n/n CHT FILLDATA SAVECHAT:"+e);
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }


}
