package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;

/**
 * Chat List Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16
 * @version 1.0
 * Upd
 *
 */

public class ChatListFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager>{
    private ChatManager chatManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatPreferenceSettings chatSettings;
    private ReferenceAppFermatSession<ChatManager> chatSession;
    ChatListAdapter adapter;
    FermatApplicationCaller applicationsHelper;
    ChatActorCommunitySelectableIdentity chatIdentity;
    ListView list;
    private SearchView searchView;
    // Defines a tag for identifying log entries
    String TAG="CHT_ChatListFragment";
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<String> contactName=new ArrayList<>();
    ArrayList<String> message=new ArrayList<>();
    ArrayList<String> dateMessage=new ArrayList<>();
    ArrayList<UUID> chatId=new ArrayList<>();
    ArrayList<String> contactId=new ArrayList<>();
    ArrayList<String> status=new ArrayList<>();
    ArrayList<String> typeMessage=new ArrayList<>();
    ArrayList<Integer> noReadMsgs=new ArrayList<>();
    ArrayList<Bitmap> imgId=new ArrayList<>();
    View layout;
    PresentationDialog presentationDialog;
    ImageView noData;
    TextView noDatalabel;
    TextView nochatssubtitle;
    TextView nochatssubtitle1;
    private static final int MAX = 20;
    private int offset = 0;
    Toolbar toolbar;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    public void chatlistview (){
        UUID chatidtemp;
        int chatscounter=0;
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
                    if (chat.getStatus() != ChatStatus.INVISSIBLE) {
                        chatidtemp = chat.getChatId();
                        if (chatidtemp != null) {
                            noReadMsgs.add(chatManager.getCountMessageByChatId(chatidtemp));
                            contactId.add(chat.getRemoteActorPublicKey());
                            if (chatIdentity != null) {
                                List<ChatActorCommunityInformation> chatActorCommunityInformations = chatManager.listAllConnectedChatActor(chatIdentity, MAX, offset);
                                for (ChatActorCommunityInformation cont : chatActorCommunityInformations) {
                                    String pk1 = cont.getPublicKey();
                                    String pk2 = chat.getRemoteActorPublicKey();
                                    if (pk2.equals(pk1)) {
                                        contactName.add(cont.getAlias());
                                        Message mess = null;
                                        try {
                                            mess = chatManager.getMessageByChatId(chatidtemp);
                                        } catch (Exception e) {
                                            mess = null;
                                        }
                                        if (mess != null) {
                                            if (chatManager.checkWritingStatus(chatidtemp)) {
                                                message.add("Typing...");
                                            } else {
                                                message.add(mess.getMessage());
                                            }
                                            status.add(mess.getStatus().toString());
                                            typeMessage.add(mess.getType().toString());
                                        } else {
                                            if (chatManager.checkWritingStatus(chatidtemp)) {
                                                message.add("Typing...");
                                            } else {
                                                message.add("");
                                            }
                                            status.add("");
                                            typeMessage.add("");
                                        }
                                        long timemess = chat.getLastMessageDate().getTime();
                                        long nanos = (chat.getLastMessageDate().getNanos() / 1000000);
                                        long milliseconds = timemess + nanos;
                                        Date dated = new java.util.Date(milliseconds);
                                        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                                        formatter.setTimeZone(TimeZone.getDefault());
                                        String datef = formatter.format(new java.util.Date(milliseconds));
                                        if (Validate.isDateToday(dated)) {
                                            if (Validate.isDateToday(dated)) {
                                                if (Build.VERSION.SDK_INT < 23) {
                                                    if (android.text.format.DateFormat.is24HourFormat(getActivity())) {
                                                        formatter = new SimpleDateFormat("HH:mm");
                                                    } else {
                                                        formatter = new SimpleDateFormat("hh:mm aa");
                                                    }
                                                } else {
                                                    if (android.text.format.DateFormat.is24HourFormat(getContext())) {
                                                        formatter = new SimpleDateFormat("HH:mm");
                                                    } else {
                                                        formatter = new SimpleDateFormat("hh:mm aa");
                                                    }
                                                }
                                            }
                                            formatter.setTimeZone(TimeZone.getDefault());
                                            datef = formatter.format(new java.util.Date(milliseconds));
                                        } else {
                                            Date old = new Date(datef);
                                            Date today = new Date();
                                            long dias = (today.getTime() - old.getTime()) / (1000 * 60 * 60 * 24);
                                            if (dias == 1) {
                                                datef = "YESTERDAY";
                                            }
                                        }
                                        dateMessage.add(datef);
                                        chatId.add(chatidtemp);
                                        ByteArrayInputStream bytes = new ByteArrayInputStream(cont.getImage());
                                        BitmapDrawable bmd = new BitmapDrawable(bytes);
                                        imgId.add(bmd.getBitmap());
                                        chatscounter++;
                                        break;
                                    }
                                }
                            } else setUpHelpChat(false);
                        }
                    }
                }
                if (chatscounter==0)
                {
                    //layout.setBackgroundResource(R.drawable.cht_background_color);
                    noData.setVisibility(View.VISIBLE);
                    noDatalabel.setVisibility(View.VISIBLE);
                    nochatssubtitle.setVisibility(View.VISIBLE);
                    nochatssubtitle1.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager_nodata);
                    contactName.clear();
                    message.clear();
                    chatId.clear();
                    dateMessage.clear();
                    contactId.clear();
                    status.clear();
                    typeMessage.clear();
                    noReadMsgs.clear();
                    imgId.clear();
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
            chatManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            applicationsHelper = ((FermatApplicationSession)getActivity().getApplicationContext()).getApplicationManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        //Obtain chatSettings  or create new chat settings if first time opening chat platform
        chatSettings = null;
        try {
            chatSettings = chatManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            chatSettings = null;
        }

        if (chatSettings == null) {
            chatSettings = new ChatPreferenceSettings();
            chatSettings.setIsPresentationHelpEnabled(true);
            try {
                chatManager.persistSettings(appSession.getAppPublicKey(), chatSettings);
            } catch (Exception e) {
                if (errorManager != null)
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }

        //set local identity
        try {
            chatIdentity = chatSettings.getIdentitySelected();
            if (chatIdentity == null) {
                List<ChatIdentity> chatIdentityList=chatManager
                        .getIdentityChatUsersFromCurrentDeviceUser();
                if(chatIdentityList != null && chatIdentityList.size()>0) {
                    chatIdentity = chatManager
                            .newInstanceChatActorCommunitySelectableIdentity(
                                    chatIdentityList.get(0));
                    chatSettings.setIdentitySelected(chatIdentity);
                    chatSettings.setProfileSelected(chatIdentity.getPublicKey(),
                            PlatformComponentType.ACTOR_CHAT);
                }
            }
        } catch (Exception e) {
                if (errorManager != null)
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
   }

    void updatevalues(){
        try{
            if(!chatManager.getChats().isEmpty()) {
                layout.setBackgroundResource(R.drawable.cht_background_white);
                noData.setVisibility(View.GONE);
                noDatalabel.setVisibility(View.GONE);
                nochatssubtitle.setVisibility(View.GONE);
                nochatssubtitle1.setVisibility(View.GONE);
                getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager);
                chatlistview();
            }else{
                //layout.setBackgroundResource(R.drawable.cht_background_color);
                noData.setVisibility(View.VISIBLE);
                noDatalabel.setVisibility(View.VISIBLE);
                nochatssubtitle.setVisibility(View.VISIBLE);
                nochatssubtitle1.setVisibility(View.VISIBLE);
                getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager_nodata);
                contactName.clear();
                message.clear();
                chatId.clear();
                dateMessage.clear();
                contactId.clear();
                status.clear();
                typeMessage.clear();
                noReadMsgs.clear();
                imgId.clear();//text.setBackgroundResource(R.drawable.cht_empty_chat_background);
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
            presentationDialog = new PresentationDialog.Builder(getActivity(), (ReferenceAppFermatSession) appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.cht_banner)
                    .setIconRes(R.drawable.chat_subapp)
                    .setSubTitle(R.string.cht_chat_subtitle)
                    .setBody(R.string.cht_chat_body)
                    .setTextFooter(R.string.cht_chat_footer)
                    .setIsCheckEnabled(false)
                    .build();
            final ChatActorCommunitySelectableIdentity identity=chatIdentity;
            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if(identity==null) {
                        try {
                            applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            presentationDialog.show();
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = inflater.inflate(R.layout.chats_list_fragment, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        //text = (TextView) layout.findViewById(R.id.text);
        noData=(ImageView) layout.findViewById(R.id.nodata);
        noDatalabel = (TextView) layout.findViewById(R.id.nodatalabel);
        nochatssubtitle = (TextView) layout.findViewById(R.id.nochatssubtitle);
        nochatssubtitle1 = (TextView) layout.findViewById(R.id.nochatssubtitle1);
        //text.setTypeface(tf, Typeface.NORMAL);
        updatevalues();
        if (chatSettings.isHomeTutorialDialogEnabled() == true)
        {
            setUpHelpChat(false);
        }
//        Just if chat is going to allow multiple identities
// try {
//            toolbar = getToolbar();
//            if (chatSettings.getLocalPublicKey() != null) {
//                ChatIdentity localUser = chatManager.getIdentityChatUsersFromCurrentDeviceUser().get(0), MAX, offset);//ChatUserIdentity localUser = null;//chatManager.getChatUserIdentity(chatSettings.getLocalPublicKey());
//                //toolbar = getToolbar();
//                //getContext().getActionBar().setTitle("");
//                ByteArrayInputStream bytes = new ByteArrayInputStream(localUser.getImage());
//                BitmapDrawable bmd = new BitmapDrawable(bytes);
//                contactIcon =bmd.getBitmap();
//                //toolbar.setTitle(localUser.getAlias());
//                contactIconCircular = new BitmapDrawable( getResources(), Utils.getRoundedShape( contactIcon, 100));//in the future, this image should come from chatmanager
//                toolbar.setLogo(contactIconCircular);
//                //getActivity().getActionBar().setLogo(contactIconCircular);
//            }
//        //}catch (CantGetChatUserIdentityException e){
//         //   errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        } catch (Exception e) {
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }
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
                    //TODO: metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();
                    //int newPosition= Utils.safeLongToInt(adapter.getItem(position));
                    Contact contact=new ContactImpl();
                    contact.setRemoteActorPublicKey(adapter.getContactIdItem(position));
                    contact.setAlias(adapter.getItem(position));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if(adapter.getImgIdItem(position)!=null)
                    {
                        adapter.getImgIdItem(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                    } else {
                        Drawable d = getResources().getDrawable(R.drawable.cht_center_profile_icon_center); // the drawable (Captain Obvious, to the rescue!!!)
                        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    }
                    byte[] byteArray = stream.toByteArray();
                    contact.setProfileImage(byteArray);
                    appSession.setData(ChatSessionReferenceApp.CONTACT_DATA, contact);
                    changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
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
        if(code.equals("13")) {
            if (searchView != null) {
                if (searchView.getQuery().toString().equals("")) {
                    updatevalues();
                    chatlistview();
                    adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                }
            } else {
                updatevalues();
                chatlistview();
                adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //menu.clear();
        //super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.chat_list_menu, menu);
        // Locate the search item = (MenuItem) fermatFragmentType.getOptionsMenu().getItem(1);
        //OptionsMenu menuu = fermatFragmentType.getOptionsMenu();
//        MenuItem searchItem = menu.findItem(fermatFragmentType.getOptionsMenu().getItem(1).getId());
        MenuItem searchItem = menu.findItem(1);
        if (searchItem!=null) {
            searchView = (SearchView) searchItem.getActionView();
            //searchView.setQueryHint(getResources().getString(R.string.cht_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
               @Override
               public boolean onQueryTextSubmit(String s) {
                   return false;
               }

               @Override
               public boolean onQueryTextChange(String s) {
                   if (s.equals(searchView.getQuery().toString())) {
                       updatevalues();
                       adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                       adapter.getFilter().filter(s);
                   }
                   return false;
               }
            });
            if (appSession.getData("filterString") != null) {
               String filterString = (String) appSession.getData("filterString");
               if (filterString.length() > 0) {
                   searchView.setQuery(filterString, true);
                   searchView.setIconified(false);
               }else{
                   updatevalues();
                   adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
               }
            }
    //        menu.add(0, ChtConstants.CHT_ICON_HELP, 0, "help").setIcon(R.drawable.cht_help_icon)
    //               .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
    }

    public void onOptionMenuPrepared(Menu menu){
        MenuItem searchItem = menu.findItem(1);
        if (searchItem!=null) {
            searchView = (SearchView) searchItem.getActionView();
            //searchView.setQueryHint(getResources().getString(R.string.cht_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals(searchView.getQuery().toString())) {
                        updatevalues();
                        adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                        adapter.getFilter().filter(s);
                    }
                    return false;
                }
            });
            if (appSession.getData("filterString") != null) {
                String filterString = (String) appSession.getData("filterString");
                if (filterString.length() > 0) {
                    searchView.setQuery(filterString, true);
                    searchView.setIconified(false);
                }else{
                    updatevalues();
                    adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 5:
                    setUpHelpChat(false);
                    break;
                case 3:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_COMMUNITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        if(chatId!= null && chatId.size()>0){
                            final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null, chatManager, errorManager);
                            alert.setTextTitle("Delete All Chats");
                            alert.setTextBody("Do you want to delete all chats? All chats will be erased");
                            alert.setType("delete-chats");
                            alert.show();
                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    try {
                                        updatevalues();
                                        adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
                                    }catch (Exception e) {
                                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                    }
                                }
                            });
                        }else
                            Toast.makeText(getActivity(), "No chats now", Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                    break;
                case 1:
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);

//        if (id == R.id.menu_open_chat) {
//            changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//            return true;
//        }
//        if (id == R.id.menu_create_group) {
//            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//            return true;
//        }
//        if (id == R.id.menu_create_broadcasting) {
//            changeActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL, appSession.getAppPublicKey());
//            return true;
//        }
//        if (id == R.id.menu_delete_all_chats) {
//
//        }

//        if (id == R.id.menu_error_report) {
//            changeActivity(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT, appSession.getAppPublicKey());
//            return true;
//        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            if (Build.VERSION.SDK_INT < 23) {
                MenuInflater inflater = new MenuInflater(getActivity());
                inflater.inflate(R.menu.chat_list_context_menu, menu);
            }else{
                MenuInflater inflater = new MenuInflater(getContext());
                inflater.inflate(R.menu.chat_list_context_menu, menu);
            }
        }
        // Get the info on which item was selected
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        try{
            // Set the info of chat selected in session
            appSession.setData(ChatSessionReferenceApp.CHAT_DATA, chatManager.getChatByChatId(chatId.get(info.position)));
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
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null, chatManager, errorManager);
                alert.setTextTitle("Delete Chat");
                alert.setTextBody("Do you want to delete this chat?");
                alert.setType("delete-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
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
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null, chatManager, errorManager);
                alert.setTextTitle("Clean Chat");
                alert.setTextBody("Do you want to clean this chat? All messages in here will be erased");
                alert.setType("clean-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
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
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null, chatManager,errorManager);
                alert.setTextTitle("Delete All Chats");
                alert.setTextBody("Do you want to delete all chats? All chats will be erased");
                alert.setType("delete-chats");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            updatevalues();
                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId, status, typeMessage, noReadMsgs, imgId);
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
//        if (id == R.id.menu_delete_contact) {
//            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//            return true;
//        }
//        if (id == R.id.menu_block_contact) {
//            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//            return true;
//        }
        return super.onContextItemSelected(item);
    }
}
