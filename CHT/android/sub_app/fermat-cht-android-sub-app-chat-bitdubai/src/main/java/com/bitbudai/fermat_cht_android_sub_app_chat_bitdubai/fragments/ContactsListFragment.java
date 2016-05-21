package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChtConstants;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_connections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Contact List fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/16
 * @version 1.0
 *
 */
public class ContactsListFragment extends AbstractFermatFragment implements ContactListAdapter.AdapterCallback, cht_dialog_connections.AdapterCallbackContacts {

//    // Bundle key for saving previously selected search result item
//    //private static final String STATE_PREVIOUSLY_SELECTED_KEY =      "SELECTED_ITEM";
    private ContactListAdapter adapter; // The main query adapter
    //private ImageLoader mImageLoader; // Handles loading the contact image in a background thread
//    private String mSearchTerm; // Stores the current search query term
//
//    //private OnContactsInteractionListener mOnContactSelectedListener;
//
//    // Stores the previously selected search item so that on a configuration change the same item
//    // can be reselected again
//    private int mPreviouslySelectedSearchItem = 0;
// public ArrayList<ContactList> contactList;
    public List<Contact> contacts;
//    private ListView contactsContainer;
    //private Contact contactl;//= new Contact();
//
//    // Whether or not the search query has changed since the last time the loader was refreshed
//    private boolean mSearchQueryChanged;

    // Whether or not this fragment is showing in a two-pane layout
    //private boolean mIsTwoPaneLayout;

    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
    //private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    //private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    private ChatPreferenceSettings chatSettings;
    ChatActorCommunitySelectableIdentity chatIdentity;
    PresentationDialog presentationDialog;
    //private Toolbar toolbar;
    ListView list;
    // Defines a tag for identifying log entries
    String TAG="CHT_ContactsListFragment";
    ArrayList<String> contactname=new ArrayList<>();
    ArrayList<Bitmap> contacticon=new ArrayList<>();
    ArrayList<String> contactid=new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    ImageView noData;
    View layout;
    TextView noDatalabel;
    private static final int MAX = 20;
    private int offset = 0;
    private SearchView searchView;

    public static ContactsListFragment newInstance() {
        return new ContactsListFragment();}

    @Override
    public void onMethodCallback() {//solution to access to another activity clicking the photo icon of the list
        changeActivity(Activities.CHT_CHAT_OPEN_CONTACT_DETAIL, appSession.getAppPublicKey());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            chatSession=((ChatSession) appSession);
            chatManager= chatSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            //toolbar = getToolbar();
            //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
            adapter=new ContactListAdapter(getActivity(), contactname, contacticon, contactid, chatManager,
                    null, errorManager, chatSession, appSession, this);
            chatSettings = null;

        }catch (Exception e)
        {
            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }
        try {
            chatSettings = chatManager.loadAndGetSettings(appSession.getAppPublicKey());
        }catch (Exception e) {
            chatSettings = null;
        }

        //set and / or get local identity
        try {
            chatIdentity = chatSettings.getIdentitySelected();
            if(chatIdentity==null)
            {
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

    void updateValues(){
        try {
            contactname.clear();
            contactid.clear();
            contacticon.clear();
            //TODO: metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();
            if(chatIdentity != null) {
                List<ChatActorCommunityInformation> con = chatManager
                        .listAllConnectedChatActor(chatIdentity, MAX, offset); //null;//chatManager.getContacts();
                if (con != null) {
                    int size = con.size();
                    if (size > 0) {
                        for (ChatActorCommunityInformation conta:con) {
                            contactname.add(conta.getAlias());
                            contactid.add(conta.getPublicKey());
                            ByteArrayInputStream bytes = new ByteArrayInputStream(conta.getImage());
                            BitmapDrawable bmd = new BitmapDrawable(bytes);
                            contacticon.add(bmd.getBitmap());
                        }
                        noData.setVisibility(View.GONE);
                        noDatalabel.setVisibility(View.GONE);
                        layout.setBackgroundResource(0);
                        ColorDrawable bgcolor = new ColorDrawable(Color.parseColor("#F9F9F9"));
                        layout.setBackground(bgcolor);
                    } else {
                        noData.setVisibility(View.VISIBLE);
                        noDatalabel.setVisibility(View.VISIBLE);
                        layout.setBackgroundResource(R.drawable.cht_background_1);
                    }
                }  else {
                    noData.setVisibility(View.VISIBLE);
                    noDatalabel.setVisibility(View.VISIBLE);
                    layout.setBackgroundResource(R.drawable.cht_background_1);
                }
            } else{
                noData.setVisibility(View.VISIBLE);
                noDatalabel.setVisibility(View.VISIBLE);
                layout.setBackgroundResource(R.drawable.cht_background_1);
            }
        }catch (Exception e){
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = inflater.inflate(R.layout.contact_list_fragment, container, false);
        noData=(ImageView) layout.findViewById(R.id.nodata);
        noDatalabel = (TextView) layout.findViewById(R.id.nodatalabel);
        noData.setVisibility(View.VISIBLE);
        noDatalabel.setVisibility(View.VISIBLE);
        layout.setBackgroundResource(R.drawable.cht_background_1);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        updateValues();
        adapter=new ContactListAdapter(getActivity(), contactname, contacticon, contactid, chatManager,
                null, errorManager, chatSession, appSession, this);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);
        registerForContextMenu(list);

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
//            }
//        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()/*new AdapterView.OnItemClickListener()*/ {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //public void onClick(View view) {
                try {
                    appSession.setData("whocallme", "contact");
                    //appSessionSetDataContact(position);
                    appSessionSetDataContact(position);
                    changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
                } catch (Exception e) {
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
                        try{
                            Toast.makeText(getActivity(), "Contacts Updated", Toast.LENGTH_SHORT).show();
                            updateValues();
                            final ContactListAdapter adaptador =
                                    new ContactListAdapter(getActivity(), contactname, contacticon, contactid, chatManager,
                                            null, errorManager, chatSession, appSession, null);
                            adaptador.refreshEvents(contactname, contacticon, contactid);
                            list.invalidateViews();
                            list.requestLayout();
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });
        // Inflate the list fragment layout
        return layout;//return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onMethodCallbackContacts() {//solution to access to update contacts view
        try {
            if(chatIdentity!=null) {
                List<ChatActorCommunityInformation> con = chatManager
                        .listAllConnectedChatActor(
                                chatIdentity, MAX, offset); //null;//chatManager.getContacts();
                if (con.size() > 0) {
                    contactname.clear();
                    contactid.clear();
                    contacticon.clear();
                    for (int i = 0; i < con.size(); i++) {
                        contactname.add(con.get(i).getAlias());
                        contactid.add(con.get(i).getPublicKey());
                        ByteArrayInputStream bytes = new ByteArrayInputStream(con.get(i).getImage());
                        BitmapDrawable bmd = new BitmapDrawable(bytes);
                        contacticon.add(bmd.getBitmap());
                    }
                    final ContactListAdapter adaptador =
                            new ContactListAdapter(getActivity(), contactname, contacticon, contactid, chatManager,
                                    null, errorManager, chatSession, appSession, null);
                    adaptador.refreshEvents(contactname, contacticon, contactid);
                    list.invalidateViews();
                    list.requestLayout();
                }
            }else noData.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate the menu items
        inflater.inflate(R.menu.contact_list_menu, menu);
        menu.add(0, ChtConstants.CHT_ICON_HELP, 0, "help").setIcon(R.drawable.ic_menu_help_cht)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        // Locate the search item
        MenuItem searchItem = menu.findItem(R.id.menu_search);
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
                    updateValues();
                    adapter.refreshEvents(contactname, contacticon, contactid);
                    adapter.getFilter().filter(s);
                }
                return false;
            }
        });
        if (chatSession.getData("filterString") != null) {
            String filterString = (String) chatSession.getData("filterString");
            if (filterString.length() > 0) {
                searchView.setQuery(filterString, true);
                searchView.setIconified(false);
            }else{
                updateValues();
                adapter.refreshEvents(contactname, contacticon, contactid);
            }
        }
        menu.add(0, ChtConstants.CHT_ICON_HELP, 0, "help").setIcon(R.drawable.cht_help_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == ChtConstants.CHT_ICON_HELP){
            setUpHelpChat(false);
        }
        if(id == ChtConstants.CHT_ICON_HELP){
            PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.cht_banner)
                    .setIconRes(R.drawable.chat_subapp)
                    .setSubTitle(R.string.cht_chat_subtitle)
                    .setBody(R.string.cht_chat_body)
                    .setTextFooter(R.string.cht_chat_footer)
                    .build();
            presentationDialog.show();
            return true;
        }

        if (id == R.id.menu_search) {
            return true;
        }

//        if (id == R.id.menu_error_report) {
//            changeActivity(Activities.CHT_CHAT_OPEN_SEND_ERROR_REPORT, appSession.getAppPublicKey());
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    // This method uses APIs from newer OS versions than the minimum that this app supports. This
    // annotation tells Android lint that they are properly guarded so they won't run on older OS
    // versions and can be ignored by lint.

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.list) {
            if (Build.VERSION.SDK_INT < 23) {
                MenuInflater inflater = new MenuInflater(getActivity());
                inflater.inflate(R.menu.contact_list_context_menu, menu);
            }else{
                MenuInflater inflater = new MenuInflater(getContext());
                inflater.inflate(R.menu.contact_list_context_menu, menu);
            }
        }
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id =item.getItemId();
        if (id == R.id.menu_view_contact) {
            try {
                //Contact con = chatSession.getSelectedContact();// type ChatActorCommunityInformation
                appSessionSetDataContact(info.position);
                changeActivity(Activities.CHT_CHAT_OPEN_CONTACT_DETAIL, appSession.getAppPublicKey());
            }catch (Exception e){
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
//        if (id == R.id.menu_edit_contact) {
//            try {
//                appSessionSetDataContact(info.position);
//                changeActivity(Activities.CHT_CHAT_EDIT_CONTACT, appSession.getAppPublicKey());
//            }catch (Exception e){
//                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            }
//            return true;
//        }
//        if (id == R.id.menu_block_contact) {
//            //changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST, appSession.getAppPublicKey());
//            return true;
//        }
        return super.onContextItemSelected(item);
    }

    public void appSessionSetDataContact (int position){
        //TODO: metodo nuevo que lo buscara del module del identity//chatManager.getChatUserIdentities();
        Contact contact=new ContactImpl();
        contact.setRemoteActorPublicKey(adapter.getContactId(position));
        contact.setAlias(adapter.getItem(position));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        adapter.getContactIcon(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        contact.setProfileImage(byteArray);
        appSession.setData(ChatSession.CONTACT_DATA, contact);
    }
}
