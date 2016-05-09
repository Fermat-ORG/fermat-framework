package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ProfileListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ImageLoader;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.ChatUserIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Profile List fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 03/03/16
 * @version 1.0
 *
 */
public class ProfileListFragment extends AbstractFermatFragment implements ProfileListAdapter.AdapterCallback {

//    // Bundle key for saving previously selected search result item
//    //private static final String STATE_PREVIOUSLY_SELECTED_KEY =      "SELECTED_ITEM";
    private ProfileListAdapter adapter; // The main query adapter
    private ImageLoader mImageLoader; // Handles loading the contact image in a background thread
//    private String mSearchTerm; // Stores the current search query term
//
//    //private OnContactsInteractionListener mOnContactSelectedListener;
//
//    // Stores the previously selected search item so that on a configuration change the same item
//    // can be reselected again
//    private int mPreviouslySelectedSearchItem = 0;
// public ArrayList<ContactList> contactList;
    public List<ChatUserIdentity> profiles;
//    private ListView contactsContainer;
    private ChatUserIdentity profilesl;//= new Contact();
//
//    // Whether or not the search query has changed since the last time the loader was refreshed
//    private boolean mSearchQueryChanged;

    // Whether or not this fragment is showing in a two-pane layout
    private boolean mIsTwoPaneLayout;

    // Whether or not this is a search result view of this fragment, only used on pre-honeycomb
    // OS versions as search results are shown in-line via Action Bar search from honeycomb onward
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatPreferenceSettings chatSettings;
    private ChatSession chatSession;
    private Toolbar toolbar;
    ListView list;
    // Defines a tag for identifying log entries
    String TAG="CHT_ProfileListFragment";
    ArrayList<String> profilename=new ArrayList<>();
    ArrayList<Bitmap> profileicon=new ArrayList<>();
    ArrayList<String> profileid=new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    TextView text;
    View layout;
    Typeface tf;

    public static ProfileListFragment newInstance() {
        return new ProfileListFragment();}

    @Override
    public void onMethodCallback() {//solution to access to another activity clicking the photo icon of the list
        changeActivity(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL, appSession.getAppPublicKey());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            chatSession=((ChatSession) appSession);
            chatManager= chatSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//            try {
//                chatManager.createSelfIdentities();
//            }catch(CantCreateSelfIdentityException e)
//            {
//                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            }
            //Obtain chatSettings or create new chat settings if first time opening chat platform
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
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));

            adapter=new ProfileListAdapter(getActivity(), profilename, profileicon, profileid, chatManager,
                    moduleManager, errorManager, chatSession, appSession, this);
        }catch (Exception e)
        {
            if(errorManager!=null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }

        setHasOptionsMenu(false);
        // Check if this fragment is part of a two-pane set up or a single pane by reading a
        // boolean from the application resource directories. This lets allows us to easily specify
        // which screen sizes should use a two-pane layout by setting this boolean in the
        // corresponding resource size-qualified directory.
        //mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        //setHasOptionsMenu(true);

        // Create the main contacts adapter
        //adapter=new ContactListAdapter(getActivity(), contactname, contacticon, contactid);
//
//        if (savedInstanceState != null) {
//            // If we're restoring state after this fragment was recreated then
//            // retrieve previous search term and previously selected search
//            // result.
//            mSearchTerm = savedInstanceState.getString(SearchManager.QUERY);
//            mPreviouslySelectedSearchItem =
//                    savedInstanceState.getInt(STATE_PREVIOUSLY_SELECTED_KEY, 0);
//        }

        /*
         * An ImageLoader object loads and resizes an image in the background and binds it to the
         * QuickContactBadge in each item layout of the ListView. ImageLoader implements memory
         * caching for each image, which substantially improves refreshes of the ListView as the
         * user scrolls through it.
         *
         * To learn more about downloading images asynchronously and caching the results, read the
         * Android training class Displaying Bitmaps Efficiently.
         *
         * http://developer.android.com/training/displaying-bitmaps/
         */
//        mImageLoader = new ImageLoader(getContext(), getListPreferredItemHeight()) {
//            @Override
//            protected Bitmap processBitmap(Object data) {
//                // This gets called in a background thread and passed the data from
//                // ImageLoader.loadImage().
//                return loadContactPhotoThumbnail((String) data, getImageSize());
//            }
//        };
//
//        // Set a placeholder loading image for the image loader
//        mImageLoader.setLoadingImage(R.drawable.ic_contact_picture_holo_light);

        // Add a cache to the image loader
        //mImageLoader.addImageCache(getRuntimeManager(), 0.1f);

    }

//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        long viewId = view.getId();
//        try{
//            if (viewId == R.id.icon) {
//                goToContactDetail(chatManager, moduleManager, chatSession,
//                        appSession, errorManager, contactid.get(position));
//            } else {
//                appSession.setData("whocallme", "contact");
//                appSession.setData(ChatSession.CONTACT_DATA, chatManager.getContactByContactId(contactid.get(position)));
//                changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
//            }
//        }catch(CantGetContactException e) {
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }catch (Exception e){
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue Medium.ttf");
        layout = inflater.inflate(R.layout.profile_list_fragment, container, false);
        text=(TextView) layout.findViewById(R.id.text);
        //text.setTypeface(tf, Typeface.NORMAL);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);

        try {
            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
            List <ChatUserIdentity> con=  null;//chatManager.getChatUserIdentities();
            int size = con.size();
            if (size > 0) {
                for (int i=0;i<size;i++){
                    profilename.add(con.get(i).getAlias());
                    profileid.add(con.get(i).getPublicKey());
                    ByteArrayInputStream bytes = new ByteArrayInputStream(con.get(i).getImage());
                    BitmapDrawable bmd = new BitmapDrawable(bytes);
                    profileicon.add(bmd.getBitmap());
                }
                //text.setVisibility(View.GONE);
            }else{
                //Comentar, solo para pruebas
//                ContactImpl cadded=new ContactImpl();
//                cadded.setContactId(UUID.randomUUID());
//                cadded.setAlias("josejcb");
//                cadded.setRemoteActorPublicKey("jose");
//                cadded.setRemoteActorType(PlatformComponentType.ACTOR_ASSET_USER);
//                String dateString = "30/09/2014";
//                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//                Date date = sdf.parse(dateString);
//                long startDate = date.getTime();
//                cadded.setCreationDate(startDate);
//                cadded.setRemoteName("No hay nadie conectado");
//                chatManager.saveContact(cadded);
                //Fin Comentar
                //text.setVisibility(View.VISIBLE);
                text.setText("No Registered Profile. Please create one in any Fermat Community.");
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

        adapter=new ProfileListAdapter(getActivity(), profilename, profileicon, profileid, chatManager,
                moduleManager, errorManager, chatSession, appSession, this);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()/*new AdapterView.OnItemClickListener()*/ {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                    final ChatUserIdentity profileSelected = null;//chatManager.getChatUserIdentity(profileid.get(position));
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setMessage("Do you want to select "+profileSelected.getAlias()+" as your active profile?");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    try {
                                        //appSession.setData(ChatSession.PROFILE_DATA, profileSelected);
                                        //ChatUserIdentity profile = chatSession.getSelectedProfile();
                                        chatSettings.setProfileSelected(profileSelected.getPublicKey(), profileSelected.getPlatformComponentType());
                                        Toast.makeText(getActivity(), "Profile Selected: " + profileSelected.getAlias(), Toast.LENGTH_SHORT).show();
                                        changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
                                    } catch (Exception e) {
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
                //} catch (CantGetChatUserIdentityException e) {
                //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                    appSession.setData(ChatSession.PROFILE_DATA, null);//chatManager.getChatUserIdentity(profileid.get(position)));
                    changeActivity(Activities.CHT_CHAT_OPEN_PROFILE_DETAIL, appSession.getAppPublicKey());
                //}catch(CantGetChatUserIdentityException e) {
                //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }catch (Exception e){
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                return true;
            }
        });

       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                try {
                    Toast.makeText(getActivity(), "Profiles Updated", Toast.LENGTH_SHORT).show();
                    List <ChatUserIdentity> con=  chatManager.getChatUserIdentities();
                    if (con.size() > 0) {
                        profilename.clear();
                        profileid.clear();
                        profileicon.clear();
                        for (int i=0;i<con.size();i++){
                            profilename.add(con.get(i).getAlias());
                            profileid.add(con.get(i).getPublicKey());
                            ByteArrayInputStream bytes = new ByteArrayInputStream(con.get(i).getImage());
                            BitmapDrawable bmd = new BitmapDrawable(bytes);
                            profileicon.add(bmd.getBitmap());
                        }
                        final ProfileListAdapter adaptador =
                                new ProfileListAdapter(getActivity(), profilename, profileicon, profileid, chatManager,
                                        moduleManager, errorManager, chatSession, appSession, null);
                        adaptador.refreshEvents(profilename, profileicon, profileid);
                        list.invalidateViews();
                        list.requestLayout();
                        text.setVisibility(View.GONE);
                    }else{
                        //Toast.makeText(getActivity(), "No Registered Profile", Toast.LENGTH_SHORT).show();
                        text.setVisibility(View.VISIBLE);
                        text.setText("No Registered Profile. Please create one in any Fermat Community.");
                    }
                } catch (CantGetChatUserIdentityException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
                mSwipeRefreshLayout.setRefreshing(false);
                }
            }, 2500);
            }
        });*/
        // Inflate the list fragment layout
        return layout;//return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_add_contact) {
//            changeActivity(Activities.CHT_CHAT_OPEN_CONNECTIONLIST, appSession.getAppPublicKey());
//            return true;
//        }else if (item.getItemId() == R.id.menu_switch_profile) {
//            changeActivity(Activities.CHT_CHAT_OPEN_PROFILELIST, appSession.getAppPublicKey());
//            return true;
//        }
        /*else if(item.getItemId()==R.id.menu_search)
        {
            getActivity().onSearchRequested();

        }*/
        return super.onOptionsItemSelected(item);
    }

    // This method uses APIs from newer OS versions than the minimum that this app supports. This
    // annotation tells Android lint that they are properly guarded so they won't run on older OS
    // versions and can be ignored by lint.

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate the menu items
        //inflater.inflate(R.menu.contact_list_menu, menu);
        // Locate the search item
        //MenuItem searchItem = menu.findItem(R.id.menu_search);

        // In versions prior to Android 3.0, hides the search item to prevent additional
        // searches. In Android 3.0 and later, searching is done via a SearchView in the ActionBar.
        // Since the search doesn't create a new Activity to do the searching, the menu item
        // doesn't need to be turned off.
        // if (mIsSearchResultView) {
        //    searchItem.setVisible(false);
        //}

        // Retrieves the system search manager service
/*        final SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        // Retrieves the SearchView from the search menu item
 /*       final SearchView searchView = (SearchView) searchItem.getActionView();

        final SearchView searchView = (SearchView) searchItem.getActionView();

        // Assign searchable info to SearchView
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        // Set listeners for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // Nothing needs to happen when the user submits the search string
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Called when the action bar search text has changed.  Updates
                // the search filter, and restarts the loader to do a new query
                // using the new search string.
                String newFilter = !TextUtils.isEmpty(newText) ? newText : null;

                // Don't do anything if the filter is empty
                if (mSearchTerm == null && newFilter == null) {
                    return true;
                }

                // Don't do anything if the new filter is the same as the current filter
                if (mSearchTerm != null && mSearchTerm.equals(newFilter)) {
                    return true;
                }

                // Updates current filter to new filter
                mSearchTerm = newFilter;

                // Restarts the loader. This triggers onCreateLoader(), which builds the
                // necessary content Uri from mSearchTerm.
                mSearchQueryChanged = true;
                //getLoaderManager().restartLoader(ContactsQuery.QUERY_ID, null, ContactsListFragment.this);
                return true;
            }
        });

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                // Nothing to do when the action item is expanded
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                // When the user collapses the SearchView the current search string is
                // cleared and the loader restarted.
                if (!TextUtils.isEmpty(mSearchTerm)) {
                    onSelectionCleared();
                }
                mSearchTerm = null;
                //getLoaderManager().restartLoader(ContactsQuery.QUERY_ID, null, ContactsListFragment.this);
                return true;
            }
        });

        if (mSearchTerm != null) {
            // If search term is already set here then this fragment is
            // being restored from a saved state and the search menu item
            // needs to be expanded and populated again.

            // Stores the search term (as it will be wiped out by
            // onQueryTextChange() when the menu item is expanded).
            final String savedSearchTerm = mSearchTerm;

            // Expands the search menu item
            searchItem.expandActionView();

            // Sets the SearchView to the previous search string
            searchView.setQuery(savedSearchTerm, false);
        }
*/
    }

//    public void goToContactDetail(ChatManager chatManager, ChatModuleManager moduleManager, ChatSession chatSession,
//                                  FermatSession appSession, ErrorManager errorManager, UUID id){
//        try{
//            appSession.setData(ChatSession.CONTACT_DATA, chatManager.getContactByContactId(id));
//            changeActivity(Activities.CHT_CHAT_OPEN_CONTACT_DETAIL, appSession.getAppPublicKey());
//        }catch(CantGetContactException e) {
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }catch (Exception e){
//            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//        }
//    }

//    public void setSearchQuery(String query) {
//        if (TextUtils.isEmpty(query)) {
//            mIsSearchResultView = false;
//        } else {
//            mSearchTerm = query;
//            mIsSearchResultView = true;
//        }
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        // Set up ListView, assign adapter and set some listeners. The adapter was previously
//        // created in onCreate().
//        /*setListAdapter(mAdapter);
//        getListView().setOnItemClickListener(this);
//        getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
//                // Pause image loader to ensure smoother scrolling when flinging
//                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
//                    mImageLoader.setPauseWork(true);
//                } else {
//                    mImageLoader.setPauseWork(false);
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView absListView, int i, int i1, int i2) {}
//        });
//
//        if (mIsTwoPaneLayout) {
//            // In a two-pane layout, set choice mode to single as there will be two panes
//            // when an item in the ListView is selected it should remain highlighted while
//            // the content shows in the second pane.
//            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        }
//        */
//        // If there's a previously selected search item from a saved state then don't bother
//        // initializing the loader as it will be restarted later when the query is populated into
//        // the action bar search view (see onQueryTextChange() in onCreateOptionsMenu()).
//        /*if (mPreviouslySelectedSearchItem == 0) {
//            // Initialize the loader, and create a loader identified by ContactsQuery.QUERY_ID
//            getLoaderManager().initLoader(ContactsQuery.QUERY_ID, null, this);
//        }*/
//    }

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            // Assign callback listener which the holding activity must implement. This is used
            // so that when a contact item is interacted with (selected by the user) the holding
            // activity will be notified and can take further action such as populating the contact
            // detail pane (if in multi-pane layout) or starting a new activity with the contact
            // details (single pane layout).
            mOnContactSelectedListener = (OnContactsInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnContactsInteractionListener");
        }
    }*/

//    @Override
//    public void onPause() {
//        super.onPause();
//
//        // In the case onPause() is called during a fling the image loader is
//        // un-paused to let any remaining background work complete.
//        mImageLoader.setPauseWork(false);
//    }

    //@Override
//    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//        // Gets the Cursor object currently bound to the ListView
//        final Cursor cursor = mAdapter.getCursor();
//
//        // Moves to the Cursor row corresponding to the ListView item that was clicked
//        cursor.moveToPosition(position);
//
//        // Creates a contact lookup Uri from contact ID and lookup_key
//        final Uri uri = ContactsContract.Contacts.getLookupUri(
//                cursor.getLong(ContactsQuery.ID),
//                cursor.getString(ContactsQuery.LOOKUP_KEY));
//
//        // Notifies the parent activity that the user selected a contact. In a two-pane layout, the
//        // parent activity loads a ContactDetailFragment that displays the details for the selected
//        // contact. In a single-pane layout, the parent activity starts a new activity that
//        // displays contact details in its own Fragment.
//        mOnContactSelectedListener.onContactSelected(uri);
//
//        // If two-pane layout sets the selected item to checked so it remains highlighted. In a
//        // single-pane layout a new activity is started so this is not needed.
//        /*if (mIsTwoPaneLayout) {
//            getListView().setItemChecked(position, true);
//        }*/
//    }

    /**
     * Called when ListView selection is cleared, for example
     * when search mode is finished and the currently selected
     * contact should no longer be selected.
     */
   /* private void onSelectionCleared() {
        // Uses callback to notify activity this contains this fragment
        mOnContactSelectedListener.onSelectionCleared();

        // Clears currently checked item
        //getListView().clearChoices();
    }*/



//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (!TextUtils.isEmpty(mSearchTerm)) {
//            // Saves the current search string
//            outState.putString(SearchManager.QUERY, mSearchTerm);
//
//            // Saves the currently selected contact
//            //outState.putInt(STATE_PREVIOUSLY_SELECTED_KEY, getListView().getCheckedItemPosition());
//        }
//    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Sends a request to the People app to display the create contact screen
        if (item.getItemId()==R.id.menu_add_contact) {
            final Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
            startActivity(intent);
        } else if(item.getItemId()==R.id.menu_search)
        {
            getActivity().onSearchRequested();

        }

        return super.onOptionsItemSelected(item);
    }

    //@Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // If this is the loader for finding contacts in the Contacts Provider
        // (the only one supported)
        if (id == ContactsQuery.QUERY_ID) {
            Uri contentUri;

            // There are two types of searches, one which displays all contacts and
            // one which filters contacts by a search query. If mSearchTerm is set
            // then a search query has been entered and the latter should be used.

            if (mSearchTerm == null) {
                // Since there's no search string, use the content URI that searches the entire
                // Contacts table
                contentUri = ContactsQuery.CONTENT_URI;
            } else {
                // Since there's a search string, use the special content Uri that searches the
                // Contacts table. The URI consists of a base Uri and the search string.
                contentUri =
                        Uri.withAppendedPath(ContactsQuery.FILTER_URI, Uri.encode(mSearchTerm));
            }

            // Returns a new CursorLoader for querying the Contacts table. No arguments are used
            // for the selection clause. The search string is either encoded onto the content URI,
            // or no contacts search string is used. The other search criteria are constants. See
            // the ContactsQuery interface.
            return new CursorLoader(getActivity(),
                    contentUri,
                    ContactsQuery.PROJECTION,
                    ContactsQuery.SELECTION,
                    null,
                    ContactsQuery.SORT_ORDER);
        }

        Log.e(TAG, "onCreateLoader - incorrect ID provided (" + id + ")");
        return null;
    }

    //@Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // This swaps the new cursor into the adapter.
        if (loader.getId() == ContactsQuery.QUERY_ID) {
            mAdapter.swapCursor(data);

            // If this is a two-pane layout and there is a search query then
            // there is some additional work to do around default selected
            // search item.
            if (mIsTwoPaneLayout && !TextUtils.isEmpty(mSearchTerm) && mSearchQueryChanged) {
                // Selects the first item in results, unless this fragment has
                // been restored from a saved state (like orientation change)
                // in which case it selects the previously selected search item.
                if (data != null && data.moveToPosition(mPreviouslySelectedSearchItem)) {
                    // Creates the content Uri for the previously selected contact by appending the
                    // contact's ID to the Contacts table content Uri
                    final Uri uri = Uri.withAppendedPath(
                            ContactsContract.Contacts.CONTENT_URI, String.valueOf(data.getLong(ContactsQuery.ID)));
                    mOnContactSelectedListener.onContactSelected(uri);
                    //getListView().setItemChecked(mPreviouslySelectedSearchItem, true);
                } else {
                    // No results, clear selection.
                    onSelectionCleared();
                }
                // Only restore from saved state one time. Next time fall back
                // to selecting first item. If the fragment state is saved again
                // then the currently selected item will once again be saved.
                mPreviouslySelectedSearchItem = 0;
                mSearchQueryChanged = false;
            }
        }
    }

    //@Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (loader.getId() == ContactsQuery.QUERY_ID) {
            // When the loader is being reset, clear the cursor from the adapter. This allows the
            // cursor resources to be freed.
            mAdapter.swapCursor(null);
        }
    }
*/
    /**
     * Gets the preferred height for each item in the ListView, in pixels, after accounting for
     * screen density. ImageLoader uses this value to resize thumbnail images to match the ListView
     * item height.
     *
     * @return The preferred height in pixels, based on the current theme.
     */
//    private int getListPreferredItemHeight() {
//        final TypedValue typedValue = new TypedValue();
//
//        // Resolve list item preferred height theme attribute into typedValue
//        getActivity().getTheme().resolveAttribute(
//                android.R.attr.listPreferredItemHeight, typedValue, true);
//
//        // Create a new DisplayMetrics object
//        final DisplayMetrics metrics = new android.util.DisplayMetrics();
//
//        // Populate the DisplayMetrics
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//        // Return theme value based on DisplayMetrics
//        return (int) typedValue.getDimension(metrics);
//    }

    /**
     * Decodes and scales a contact's image from a file pointed to by a Uri in the contact's data,
     * and returns the result as a Bitmap. The column that contains the Uri varies according to the
     * platform version.
     *
     * @param photoData For platforms prior to Android 3.0, provide the Contact._ID column value.
     *                  For Android 3.0 and later, provide the Contact.PHOTO_THUMBNAIL_URI value.
     * @param imageSize The desired target width and height of the output image in pixels.
     * @return A Bitmap containing the contact's image, resized to fit the provided image size. If
     * no thumbnail exists, returns null.
     */
//    private Bitmap loadContactPhotoThumbnail(String photoData, int imageSize) {
//
//        // Ensures the Fragment is still added to an activity. As this method is called in a
//        // background thread, there's the possibility the Fragment is no longer attached and
//        // added to an activity. If so, no need to spend resources loading the contact photo.
//        if (!isAdded() || getContext() == null) {
//            return null;
//        }
//
//        // Instantiates an AssetFileDescriptor. Given a content Uri pointing to an image file, the
//        // ContentResolver can return an AssetFileDescriptor for the file.
//        AssetFileDescriptor afd = null;
//
//        // This "try" block catches an Exception if the file descriptor returned from the Contacts
//        // Provider doesn't point to an existing file.
//        try {
//            Uri thumbUri;
//            // If Android 3.0 or later, converts the Uri passed as a string to a Uri object.
//            thumbUri = Uri.parse(photoData);
//
//            // Retrieves a file descriptor from the Contacts Provider. To learn more about this
//            // feature, read the reference documentation for
//            // ContentResolver#openAssetFileDescriptor.
//            afd = getContext().getContentResolver().openAssetFileDescriptor(thumbUri, "r");
//
//            // Gets a FileDescriptor from the AssetFileDescriptor. A BitmapFactory object can
//            // decode the contents of a file pointed to by a FileDescriptor into a Bitmap.
//            FileDescriptor fileDescriptor = afd.getFileDescriptor();
//
//            if (fileDescriptor != null) {
//                // Decodes a Bitmap from the image pointed to by the FileDescriptor, and scales it
//                // to the specified width and height
//                return ImageLoader.decodeSampledBitmapFromDescriptor(
//                        fileDescriptor, imageSize, imageSize);
//            }
//        } catch (FileNotFoundException e) {
//            // If the file pointed to by the thumbnail URI doesn't exist, or the file can't be
//            // opened in "read" mode, ContentResolver.openAssetFileDescriptor throws a
//            // FileNotFoundException.
//            if (BuildConfig.DEBUG) {
//                Log.d(TAG, "Contact photo thumbnail not found for contact " + photoData
//                        + ": " + e.toString());
//            }
//        } finally {
//            // If an AssetFileDescriptor was returned, try to close it
//            if (afd != null) {
//                try {
//                    afd.close();
//                } catch (IOException e) {
//                    // Closing a file descriptor might cause an IOException if the file is
//                    // already closed. Nothing extra is needed to handle this.
//                }
//            }
//        }
//
//        // If the decoding failed, returns null
//        return null;
//    }

    /**
     * This is a subclass of CursorAdapter that supports binding Cursor columns to a view layout.
     * If those items are part of search results, the search string is marked by highlighting the
     * query text. An {@link AlphabetIndexer} is used to allow quicker navigation up and down the
     * ListView.
     */
//    private class ContactsAdapter extends CursorAdapter implements SectionIndexer {
//        private LayoutInflater mInflater; // Stores the layout inflater
//        private AlphabetIndexer mAlphabetIndexer; // Stores the AlphabetIndexer instance
//        private TextAppearanceSpan highlightTextSpan; // Stores the highlight text appearance style
//        List<ContactList> contactList = new ArrayList<>();
//        /**
//         * Instantiates a new Contacts Adapter.
//         * @param context A context that has access to the app's layout.
//         */
//        public ContactsAdapter(Context context) {
//            super(context, null, 0);
//
//            // Stores inflater for use later
//            mInflater = LayoutInflater.from(context);
//
//            // Loads a string containing the English alphabet. To fully localize the app, provide a
//            // strings.xml file in res/values-<x> directories, where <x> is a locale. In the file,
//            // define a string with android:name="alphabet" and contents set to all of the
//            // alphabetic characters in the language in their proper sort order, in upper case if
//            // applicable.
//            final String alphabet = context.getString(R.string.alphabet);
//
//            // Instantiates a new AlphabetIndexer bound to the column used to sort contact names.
//            // The cursor is left null, because it has not yet been retrieved.
//            mAlphabetIndexer = new AlphabetIndexer(null, ContactsQuery.SORT_KEY, alphabet);
//
//            // Defines a span for highlighting the part of a display name that matches the search
//            // string
//            highlightTextSpan = new TextAppearanceSpan(getActivity(), R.style.searchTextHiglight);
//        }
//
//        /**
//         * Identifies the start of the search string in the display name column of a Cursor row.
//         * E.g. If displayName was "Adam" and search query (mSearchTerm) was "da" this would
//         * return 1.
//         *
//         * @param displayName The contact display name.
//         * @return The starting position of the search string in the display name, 0-based. The
//         * method returns -1 if the string is not found in the display name, or if the search
//         * string is empty or null.
//         */
//        private int indexOfSearchQuery(String displayName) {
//            if (!TextUtils.isEmpty(mSearchTerm)) {
//                return displayName.toLowerCase(Locale.getDefault()).indexOf(
//                        mSearchTerm.toLowerCase(Locale.getDefault()));
//            }
//            return -1;
//        }
//
//        /**
//         * Overrides newView() to inflate the list item views.
//         */
//        @Override
//        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
//            // Inflates the list item layout.
//            final View itemLayout =
//                    mInflater.inflate(R.layout.contact_list_item, viewGroup, false);
//
//            // Creates a new ViewHolder in which to store handles to each view resource. This
//            // allows bindView() to retrieve stored references instead of calling findViewById for
//            // each instance of the layout.
//            final ViewHolder holder = new ViewHolder();
//            holder.text1 = (TextView) itemLayout.findViewById(android.R.id.text1);
//            holder.text2 = (TextView) itemLayout.findViewById(android.R.id.text2);
//            holder.icon = (QuickContactBadge) itemLayout.findViewById(android.R.id.icon);
//
//            // Stores the resourceHolder instance in itemLayout. This makes resourceHolder
//            // available to bindView and other methods that receive a handle to the item view.
//            itemLayout.setTag(holder);
//
//            // Returns the item layout view
//            return itemLayout;
//        }
//
//        /**
//         * Binds data from the Cursor to the provided view.
//         */
//        @Override
//        public void bindView(View view, Context context, Cursor cursor) {
//            // Gets handles to individual view resources
//            final ViewHolder holder = (ViewHolder) view.getTag();
//
//            // For Android 3.0 and later, gets the thumbnail image Uri from the current Cursor row.
//            // For platforms earlier than 3.0, this isn't necessary, because the thumbnail is
//            // generated from the other fields in the row.
//            final String photoUri = cursor.getString(ContactsQuery.PHOTO_THUMBNAIL_DATA);
//
//            final String displayName = cursor.getString(ContactsQuery.DISPLAY_NAME);
//
//            final int startIndex = indexOfSearchQuery(displayName);
//
//            if (startIndex == -1) {
//                // If the user didn't do a search, or the search string didn't match a display
//                // name, show the display name without highlighting
//                holder.text1.setText(displayName);
//
//                if (TextUtils.isEmpty(mSearchTerm)) {
//                    // If the search search is empty, hide the second line of text
//                    holder.text2.setVisibility(View.GONE);
//                } else {
//                    // Shows a second line of text that indicates the search string matched
//                    // something other than the display name
//                    holder.text2.setVisibility(View.VISIBLE);
//                }
//            } else {
//                // If the search string matched the display name, applies a SpannableString to
//                // highlight the search string with the displayed display name
//
//                // Wraps the display name in the SpannableString
//                final SpannableString highlightedName = new SpannableString(displayName);
//
//                // Sets the span to start at the starting point of the match and end at "length"
//                // characters beyond the starting point
//                highlightedName.setSpan(highlightTextSpan, startIndex,
//                        startIndex + mSearchTerm.length(), 0);
//
//                // Binds the SpannableString to the display name View object
//                holder.text1.setText(highlightedName);
//
//                // Since the search string matched the name, this hides the secondary message
//                holder.text2.setVisibility(View.GONE);
//            }
//
//            // Processes the QuickContactBadge. A QuickContactBadge first appears as a contact's
//            // thumbnail image with styling that indicates it can be touched for additional
//            // information. When the user clicks the image, the badge expands into a dialog box
//            // containing the contact's details and icons for the built-in apps that can handle
//            // each detail type.
//
//            // Generates the contact lookup Uri
//            final Uri contactUri = ContactsContract.Contacts.getLookupUri(
//                    cursor.getLong(ContactsQuery.ID),
//                    cursor.getString(ContactsQuery.LOOKUP_KEY));
//
//            // Binds the contact's lookup Uri to the QuickContactBadge
//            holder.icon.assignContactUri(contactUri);
//
//            // Loads the thumbnail image pointed to by photoUri into the QuickContactBadge in a
//            // background worker thread
//            mImageLoader.loadImage(photoUri, holder.icon);
//        }
//
//        /**
//         * Overrides swapCursor to move the new Cursor into the AlphabetIndex as well as the
//         * CursorAdapter.
//         */
//        @Override
//        public Cursor swapCursor(Cursor newCursor) {
//            // Update the AlphabetIndexer with new cursor as well
//            mAlphabetIndexer.setCursor(newCursor);
//            return super.swapCursor(newCursor);
//        }
//
//        /**
//         * An override of getCount that simplifies accessing the Cursor. If the Cursor is null,
//         * getCount returns zero. As a result, no test for Cursor == null is needed.
//         */
//        @Override
//        public int getCount() {
//            if (getCursor() == null) {
//                return 0;
//            }
//            return super.getCount();
//        }
//
//        /**
//         * Defines the SectionIndexer.getSections() interface.
//         */
//        @Override
//        public Object[] getSections() {
//            return mAlphabetIndexer.getSections();
//        }
//
//        /**
//         * Defines the SectionIndexer.getPositionForSection() interface.
//         */
//        @Override
//        public int getPositionForSection(int i) {
//            if (getCursor() == null) {
//                return 0;
//            }
//            return mAlphabetIndexer.getPositionForSection(i);
//        }
//
//        /**
//         * Defines the SectionIndexer.getSectionForPosition() interface.
//         */
//        @Override
//        public int getSectionForPosition(int i) {
//            if (getCursor() == null) {
//                return 0;
//            }
//            return mAlphabetIndexer.getSectionForPosition(i);
//        }
//
//        /**
//         * A class that defines fields for each resource ID in the list item layout. This allows
//         * ContactsAdapter.newView() to store the IDs once, when it inflates the layout, instead of
//         * calling findViewById in each iteration of bindView.
//         */
//        private class ViewHolder {
//            TextView text1;
//            TextView text2;
//            QuickContactBadge icon;
//        }
//
//        public void add(ContactList contactlist) {contactList.add(contactlist);
//        }
//    }

    /**
     * This interface must be implemented by any activity that loads this fragment. When an
     * interaction occurs, such as touching an item from the ListView, these callbacks will
     * be invoked to communicate the event back to the activity.
     */
//    public interface OnContactsInteractionListener {
//        /**
//         * Called when a contact is selected from the ListView.
//         * @param contactUri The contact Uri.
//         */
//        public void onContactSelected(Uri contactUri);
//
//        /**
//         * Called when the ListView selection is cleared like when
//         * a contact search is taking place or is finishing.
//         */
//        public void onSelectionCleared();
//    }

    /**
     * This interface defines constants for the Cursor and CursorLoader, based on constants defined
     * in the {@link android.provider.ContactsContract.Contacts} class.
     */
//    public interface ContactsQuery {
//
//        // An identifier for the loader
//        final static int QUERY_ID = 1;
//
//        // A content URI for the Contacts table
//        final static Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
//
//        // The search/filter query Uri
//        final static Uri FILTER_URI = ContactsContract.Contacts.CONTENT_FILTER_URI;
//
//        // The selection clause for the CursorLoader query. The search criteria defined here
//        // restrict results to contacts that have a display name and are linked to visible groups.
//        // Notice that the search on the string provided by the user is implemented by appending
//        // the search string to CONTENT_FILTER_URI.
//
//        final static String SELECTION =
//                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;// ContactsContract.Contacts.DISPLAY_NAME) +                        "<>''" + " AND " + ContactsContract.Contacts.IN_VISIBLE_GROUP + "=1";
//
//        // The desired sort order for the returned Cursor. In Android 3.0 and later, the primary
//        // sort key allows for localization. In earlier versions. use the display name as the sort
//        // key.
//
//        final static String SORT_ORDER =
//                ContactsContract.Contacts.SORT_KEY_PRIMARY;//ContactsContract.Contacts.DISPLAY_NAME;
//
//        // The projection for the CursorLoader query. This is a list of columns that the Contacts
//        // Provider should return in the Cursor.
//
//        final static String[] PROJECTION = {
//
//                // The contact's row id
//                ContactsContract.Contacts._ID,
//
//                // A pointer to the contact that is guaranteed to be more permanent than _ID. Given
//                // a contact's current _ID value and LOOKUP_KEY, the Contacts Provider can generate
//                // a "permanent" contact URI.
//                ContactsContract.Contacts.LOOKUP_KEY,
//
//                // In platform version 3.0 and later, the Contacts table contains
//                // DISPLAY_NAME_PRIMARY, which either contains the contact's displayable name or
//                // some other useful identifier such as an email address. This column isn't
//                // available in earlier versions of Android, so you must use Contacts.DISPLAY_NAME
//                // instead.
//                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,//ContactsContract.Contacts.DISPLAY_NAME,
//
//                // In Android 3.0 and later, the thumbnail image is pointed to by
//                // PHOTO_THUMBNAIL_URI. In earlier versions, there is no direct pointer; instead,
//                // you generate the pointer from the contact's ID value and constants defined in
//                // android.provider.ContactsContract.Contacts.
//                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,//ContactsContract.Contacts._ID,
//
//                // The sort order column for the returned Cursor, used by the AlphabetIndexer
//                SORT_ORDER,
//        };
//
//        // The query column numbers which map to each value in the projection
//        final static int ID = 0;
//        final static int LOOKUP_KEY = 1;
//        final static int DISPLAY_NAME = 2;
//        final static int PHOTO_THUMBNAIL_DATA = 3;
//        final static int SORT_KEY = 4;
//    }

}
