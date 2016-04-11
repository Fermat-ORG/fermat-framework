package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChtConstants;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
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
    private SearchView searchView;
    // Defines a tag for identifying log entries
    String TAG="CHT_ChatListFragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<String> contactName=new ArrayList<>();
    ArrayList<String> message=new ArrayList<>();
    ArrayList<String> dateMessage=new ArrayList<>();
    ArrayList<UUID> chatId=new ArrayList<>();
    ArrayList<UUID> contactId=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();
    ArrayList<String> typeMessage=new ArrayList<>();
    ArrayList<Integer> noReadMsgs=new ArrayList<>();
    ArrayList<Bitmap> imgId=new ArrayList<>();
    //TextView text;
    View layout;
    PresentationDialog presentationDialog;
    private Toolbar toolbar;
    private Bitmap contactIcon;
    private BitmapDrawable contactIconCircular;
    private int size;
    ImageView noData;
    public static ChatListFragment newInstance() {
        return new ChatListFragment();}

    public void chatlistview (){
        UUID chatidtemp;
        try {
            List<Chat> chats = chatManager.getChats();
            if (chats != null && chats.size() > 0) {
                contactName.clear();
                message.clear();
                chatId.clear();
                dateMessage.clear();
                contactId.clear();
                status.clear();
                typeMessage.clear();
                noReadMsgs.clear();
                imgId.clear();
                for (Chat chat : chats) {
                    chatidtemp = chat.getChatId();
                    if (chatidtemp != null) {
                        Message mess = chatManager.getMessageByChatId(chatidtemp);
                        if (mess != null) {
                            noReadMsgs.add(chatManager.getCountMessageByChatId(chatidtemp));
                            contactId.add(mess.getContactId());
                            Contact cont = null; //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();//chatManager.getContactByContactId(mess.getContactId());
                            if(cont != null) {
                                contactName.add(cont.getAlias());
                                message.add(mess.getMessage());
                                status.add(mess.getStatus().toString());
                                typeMessage.add(mess.getType().toString());
                                long timemess = chat.getLastMessageDate().getTime();
                                long nanos = (chat.getLastMessageDate().getNanos() / 1000000);
                                long milliseconds = timemess + nanos;
                                Date dated = new java.util.Date(milliseconds);
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                formatter.setTimeZone(TimeZone.getDefault());
                                String datef=formatter.format(new java.util.Date(milliseconds));
                                if (Validate.isDateToday(dated)) {
                                    formatter = new SimpleDateFormat("HH:mm");
                                    formatter.setTimeZone(TimeZone.getDefault());
                                    datef=formatter.format(new java.util.Date(milliseconds));
                                } else {
                                    Date old = new Date(datef);
                                    Date today = new Date();
                                    long dias = (today.getTime() - old.getTime()) / (1000 * 60 * 60 * 24);
                                    if (dias == 1) {
                                        datef="YESTERDAY";
                                    }
                                }
                                dateMessage.add(datef);
                                chatId.add(chatidtemp);
                                ByteArrayInputStream bytes = new ByteArrayInputStream(cont.getProfileImage());
                                BitmapDrawable bmd = new BitmapDrawable(bytes);
                                imgId.add(bmd.getBitmap());
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
                if (errorManager != null)
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
        try {
            //TODO:Cardozo revisar esta logica ya no aplica
            //chatManager.createSelfIdentities();
            List<ChatUserIdentity> con = null; //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();
            size = con.size();
            if ((chatSettings.getLocalPlatformComponentType() == null || chatSettings.getLocalPublicKey() == null) && size > 0) {

                ChatUserIdentity profileSelected = null;//TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();//chatManager.getChatUserIdentity(con.get(0).getPublicKey());
                //chatSettings.setProfileSelected(profileSelected.getPublicKey(), profileSelected.getPlatformComponentType());
            }
            //}catch(CantCreateSelfIdentityException e)
        } catch (Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }//catch(CantGetChatUserIdentityException e)
        //{
        //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        //}

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
   }

    void updatevalues(){
        try{
            if(!chatManager.getChats().isEmpty()) {
                chatlistview();
                noData.setVisibility(View.GONE);
            }else{
                noData.setVisibility(View.VISIBLE);
                //text.setBackgroundResource(R.drawable.cht_empty_chat_background);
            }
        } catch (CantGetChatException e) {
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
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager);
        //tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue Medium.ttf");
        layout = inflater.inflate(R.layout.chats_list_fragment, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        //text = (TextView) layout.findViewById(R.id.text);
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
                //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();
                ChatUserIdentity localUser = null;//chatManager.getChatUserIdentity(chatSettings.getLocalPublicKey());
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
        //}catch (CantGetChatUserIdentityException e){
         //   errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        adapter=new ChatListAdapter(getActivity(), contactName, message, dateMessage, chatId, contactId, status,
                typeMessage, noReadMsgs, imgId, errorManager);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    appSession.setData("whocallme", "chatlist");
                    //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();
                    //appSession.setData(ChatSession.CONTACT_DATA, chatManager.getContactByContactId(contactId.get(position)));//esto no es necesaio, haces click a un chat
                    appSession.setData(ChatSession.CONTACT_DATA, null);//esto no es necesaio, haces click a un chat
                    //appSession.setData(ChatSession.CHAT_DATA, chatManager.getChatByChatId(UUID.fromString(converter.get(3))));//este si hace falta
                    changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
                //} catch (CantGetContactException e) {
                //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
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
        if(code.equals("13")){
            updatevalues();
            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        //try {
           //if(chatManager.isIdentityDevice() != false) {
               // Inflate the menu items
               inflater.inflate(R.menu.chat_list_menu, menu);
               // Locate the search item
               //MenuItem searchItem = menu.findItem(R.id.menu_search);

//               searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//               searchView.setQueryHint(getResources().getString(R.string.search_hint));
//               searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                   @Override
//                   public boolean onQueryTextSubmit(String s) {
//                       return false;
//                   }
//
//                   @Override
//                   public boolean onQueryTextChange(String s) {
//                       if (s.equals(searchView.getQuery().toString())) {
//                           adapter.getFilter().filter(s);
//                       }
//                       return false;
//                   }
//               });
//               if (chatSession.getData("filterString") != null) {
//                   String filterString = (String) chatSession.getData("filterString");
//                   if (filterString.length() > 0) {
//                       searchView.setQuery(filterString, true);
//                       searchView.setIconified(false);
//                   }
//               }
           //}
           menu.add(0, ChtConstants.CHT_ICON_HELP, 0, "help").setIcon(R.drawable.ic_menu_help_cht)
                   .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        //} catch (CantGetChatUserIdentityException e) {
        //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        //}
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == ChtConstants.CHT_ICON_HELP){
            setUpHelpChat(false);
        }
        if (id == R.id.menu_search) {
            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
            return true;
        }
        if (id == R.id.menu_open_chat) {
            changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
            return true;
        }
        if (id == R.id.menu_create_group) {
            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
            return true;
        }
        if (id == R.id.menu_create_broadcasting) {
            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
           return true;
        }
        if (id == R.id.menu_delete_all_chats) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null);
                alert.setTextTitle("Delete All Chats");
                alert.setTextBody("Do you want to delete all chats? All chats will be erased");
                alert.setType("delete-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            // Delete chats and refresh view
                            chatManager.deleteChats();
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                        } catch (CantDeleteChatException e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });
            }catch (Exception e){
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
        /*if (id == R.id.menu_switch_profile) {
            changeActivity(Activities.CHT_CHAT_OPEN_PROFILELIST, appSession.getAppPublicKey());
            return true;
        }*/
        if (id == R.id.menu_error_report) {
            changeActivity(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT, appSession.getAppPublicKey());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        v.setBackgroundColor(Color.WHITE);
        if (v.getId()==R.id.list) {
            MenuInflater inflater = new MenuInflater(getContext());
            inflater.inflate(R.menu.chat_list_context_menu, menu);
        }
        // Get the info on which item was selected
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        try{
            // Set the info of chat selected in session
            appSession.setData(ChatSession.CHAT_DATA, chatManager.getChatByChatId(chatId.get(info.position)));
        }catch(CantGetChatException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id =item.getItemId();
        if (id == R.id.menu_delete_chat) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null);
                alert.setTextTitle("Delete Chat");
                alert.setTextBody("Do you want to delete this chat?");
                alert.setType("delete-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            // Get the info of chat selected from session
                            Chat chat = chatSession.getSelectedChat();
                            // Delete chat and refresh view
                            chatManager.deleteMessagesByChatId(chat.getChatId());
                            chatManager.deleteChat(chat);
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                        } catch (CantDeleteChatException e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        } catch (CantDeleteMessageException e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });


            }catch (Exception e){
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
        if (id == R.id.menu_clean_chat) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null);
                alert.setTextTitle("Clean Chat");
                alert.setTextBody("Do you want to clean this chat? All messages in here will be erased");
                alert.setType("delete-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            // Get the info of chat selected from session
                            Chat chat = chatSession.getSelectedChat();
                            // Delete chat and refresh view
                            chatManager.deleteMessagesByChatId(chat.getChatId());
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                        } catch (CantDeleteMessageException e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });
            }catch (Exception e){
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
        if (id == R.id.menu_delete_all_chats) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null);
                alert.setTextTitle("Delete All Chats");
                alert.setTextBody("Do you want to delete all chats? All chats will be erased");
                alert.setType("delete-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            // Delete chats and refresh view
                            chatManager.deleteChats();
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                        } catch (CantDeleteChatException e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });
            }catch (Exception e){
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
        if (id == R.id.menu_delete_contact) {
            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
            return true;
        }
        if (id == R.id.menu_block_contact) {
            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
            return true;
        }
        return super.onContextItemSelected(item);
    }
}
