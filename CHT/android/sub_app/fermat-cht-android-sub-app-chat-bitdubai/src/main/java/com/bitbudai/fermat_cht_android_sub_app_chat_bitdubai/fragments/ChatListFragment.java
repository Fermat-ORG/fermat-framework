package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
//import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatsListHolder;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;


/**
 * Chat List Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16
 * @version 1.0
 * Upd
 *
 */

public class ChatListFragment extends AbstractFermatFragment{

    // Defines a tag for identifying log entries
    //private static final String TAG = "ChatListFragment";

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


    ListView list;
 //   static HashMap<Integer,List<String>> chatinfo=new HashMap<Integer,List<String>>();
   // static Integer[] imgid=new Integer[6];
    String[] chatinfo={"GABRIEL@@hola como estas##24/10/2015",
         "MIGUEL@@chao nos vemos##25/10/2015",
         "FRANKLIN@@A la victoria siempre##24/12/2015",
         "MANUEL@@Tigres Campeon##24/10/2015",
         "JOSE@@gracias totales##24/10/2015",
         "LUIS@@Fermat Rules##20/10/2015"};   //work
    Integer[] imgid={R.drawable.ken,
    R.drawable.sas,
    R.drawable.koj,
    R.drawable.veg,
    R.drawable.ren,
    R.drawable.pat
    };

    static void initchatinfo(){
     //   chatinfo.put(0, Arrays.asList("Miguel", "Que paso?", "12/09/2007"));
        //imgid[0]=R.drawable.ken;
    }

    public static ChatListFragment newInstance() {
        initchatinfo();
        return new ChatListFragment();}

//    public ChatListFragment() {}
//
//    /*public static ChatListFragment newInstance() {
//        return new ChatListFragment();}*/
//
//    public void setSearchQuery(String query) {
//        if (TextUtils.isEmpty(query)) {
//            mIsSearchResultView = false;
//        } else {
//            mSearchTerm = query;
//            mIsSearchResultView = true;
//        }
//    }

    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if this fragment is part of a two-pane set up or a single pane by reading a
        // boolean from the application resource directories. This lets allows us to easily specify
        // which screen sizes should use a two-pane layout by setting this boolean in the
        // corresponding resource size-qualified directory.
        mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);

//        // Create the main contacts adapter
//        //adapter = new ChatListAdapter(getActivity());
//
//        if (savedInstanceState != null) {
//            // If we're restoring state after this fragment was recreated then
//            // retrieve previous search term and previously selected search
//            // result.
//            mSearchTerm = savedInstanceState.getString(SearchManager.QUERY);
//            mPreviouslySelectedSearchItem =
//                    savedInstanceState.getInt(STATE_PREVIOUSLY_SELECTED_KEY, 0);
//        }


        /*mImageLoader = new ImageLoader(getActivity(), getListPreferredItemHeight()) {
            @Override
            protected Bitmap processBitmap(Object data) {
                // This gets called in a background thread and passed the data from
                // ImageLoader.loadImage().
                return loadContactPhotoThumbnail((String) data, getImageSize());
            }
        };

        // Set a placeholder loading image for the image loader
        mImageLoader.setLoadingImage(R.drawable.ic_contact_picture_holo_light);
*/
        // Add a cache to the image loader
        //mImageLoader.addImageCache(getActivity().getSupportFragmentManager(), 0.1f);

   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//System.out.println("**********LISTA:"+chatinfo.get(0).get(0)+" - "+chatinfo.get(0).get(1)+" - "+chatinfo.get(0).get(2));
     //   setContentView(getActivity());
        View layout = inflater.inflate(R.layout.chats_list_fragment, container, false);

        ChatListAdapter adapter=new ChatListAdapter(getActivity(), chatinfo, imgid);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= chatinfo[position];
                Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();
                changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());

            }
        });

        return layout;
    }







//mig chance
    /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.chats_list_fragment, container, false);
        adapter = new ChatListAdapter(getActivity());
<<<<<<< HEAD
        //layout = (LinearLayout) getActivity().findViewById(R.id.fragment_layout);
        noChatsMessage = (FermatTextView) layout.findViewById(R.id.no_chats);//getActivity()
        return layout;
       // return inflater.inflate(R.layout.chats_list_fragment, null);
    }
=======
        layout = (LinearLayout) getActivity().findViewById(R.id.fragment_layout);
        noChatsMessage = (TextView) getActivity().findViewById(R.id.no_chats);
        return inflater.inflate(R.layout.chats_list_fragment, null);
    }*/
 //

//mig chance
/*    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        noChatsMessage = (FermatTextView) getActivity().findViewById(R.id.no_chats);

        //FragmentManager manager = getActivity().getFragmentManager();
        ///manager.findFragmentById(R.id.messages);
        /*if (manager.findFragmentById(R.id.messages) != null) {
            layout.setVisibility(View.INVISIBLE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }
*/
   /*     progressBar = (ProgressBar) getActivity().findViewById(R.id.chat_list_progress_bar);

        //getChatsList(Const.CHATS_LIST_OFFSET, Const.CHATS_LIST_LIMIT);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);*/
    //
        //fab.attachToListView(getListView());
        //fab.setColorPressedResId(R.color.background_floating_button_pressed);
        //fab.setColorNormalResId(R.color.background_floating_button);
        //fab.setShadow(true);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransparentActivity.class);
                intent.putExtra("choice", Const.CONTACT_LIST_FRAGMENT);
                intent.putExtra("destination", getActivity().getString(R.string.chat_list));
                startActivityForResult(intent, Const.REQUEST_CODE_NEW_MESSAGE);
            }
        });
        setListAdapter(adapter);*/
//mig chance
  /*      View detailsFrame = getActivity().findViewById(R.id.messages);
        dualPane = detailsFrame != null
                && detailsFrame.getVisibility() == View.VISIBLE;
        if (savedState != null) {
            currentCheckPosition = savedState.getInt("curChoice", 0);
        }
       /* if (dualPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }*/
         /*long id = ((ChatActivity) getActivity()).getIntentChatId();
       if (id != 0) {
            clickedId = id;
            int position = getChatPosition(id);
            showMessages(position);
        }

    }*/
//
//    @Override
//    public void onPause() {
//        super.onPause();
//
//        // In the case onPause() is called during a fling the image loader is
//        // un-paused to let any remaining background work complete.
//        mImageLoader.setPauseWork(false);
//    }
/*
    public void setChatsList(final ChatsList chats1) {

 //   }
//mig
/*    public void setChatsList(final ChatsList chats1) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        chats = chats1;
        //adapter.clear();
        //adapter.addAll(chats.chats);

    }

    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // Gets the Cursor object currently bound to the ListView
        //final Cursor cursor = adapter.getCursor();

        // Moves to the Cursor row corresponding to the ListView item that was clicked
        //cursor.moveToPosition(position);

       /*
       // Creates a contact lookup Uri from contact ID and lookup_key

        final Uri uri = ChatsContract.Contacts.getLookupUri(
                cursor.getLong(ContactsQuery.ID),
                cursor.getString(ChatsQuery.LOOKUP_KEY));
*/
        // Notifies the parent activity that the user selected a contact. In a two-pane layout, the
        // parent activity loads a ContactDetailFragment that displays the details for the selected
        // contact. In a single-pane layout, the parent activity starts a new activity that
        // displays contact details in its own Fragment.
        //mOnChatSelectedListener.onContactSelected(uri);

        // If two-pane layout sets the selected item to checked so it remains highlighted. In a
        // single-pane layout a new activity is started so this is not needed.
        /*if (mIsTwoPaneLayout) {
            getListView().setItemChecked(position, true);
        }
    }
*/

    /*public void getChatsList(int offset, int limit) {
        new ApiClient<>(new TdApi.GetChats(offset, limit), new ChatsHandler(), new ApiClient.OnApiResultHandler() {
            @Override
            public void onApiResult(BaseHandler output) {
                if (output == null) {
                    getChatsList(0, 200);
                } else {
                    if (output.getHandlerId() == ChatsHandler.HANDLER_ID) {
                        TdApi.Chats receivedChats = (TdApi.Chats) output.getResponse();
                        if (receivedChats.chats.length == 0) {
                            noChatsMessage.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            noChatsMessage.setVisibility(View.GONE);
                            setChatsList(receivedChats);
                            UserInfoHolder.addUsersToMap(receivedChats);
                            MessagesFragmentHolder.setChats(receivedChats);
                        }
                    }
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }*/


//    private void onSelectionCleared() {
//        // Uses callback to notify activity this contains this fragment
//        mOnChatSelectedListener.onSelectionCleared();
//
//        // Clears currently checked item
//        //getListView().clearChoices();
//    }
//
//
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu items
        inflater.inflate(R.menu.chat_list_menu, menu);
        // Locate the search item
        MenuItem searchItem = menu.findItem(R.id.menu_search);

        // In versions prior to Android 3.0, hides the search item to prevent additional
        // searches. In Android 3.0 and later, searching is done via a SearchView in the ActionBar.
        // Since the search doesn't create a new Activity to do the searching, the menu item
        // doesn't need to be turned off.
//        if (mIsSearchResultView) {
//            searchItem.setVisible(false);
//        }
    }

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

    }*/


//mig chance
/*    public ChatsList getChat(long id) {
       // if (chats == null *//*|| chats.chats.length == 0*//*) {
        *//*    chats = ChatsListHolder.getChats();
        }
        if (id != 0) {
            clickedId = id;
            getListView().setSelection(getChatPosition(clickedId));
        }
        for (int i = 0; i < chats.chats.length; i++) {
            if (chats.chats[i].id == clickedId) {
                return chats.chats[i];
            }
        }
        if (chats.chats.length == 1) {
            return chats.chats[0];
        } else {
            return chats.chats[currentCheckPosition];
        }*//*
        return null;
    }*/
//
    /*public int getChatPosition(long id) {
        if (chats == null) {
            chats = MessagesFragmentHolder.getChats();
        }
        for (int i = 0; i < chats.chats.length; i++) {
            if (chats.chats[i].id == id) {
                return i;
            }
        }
        return Const.CHAT_NOT_FOUND;
    }*/

   /* public void setAdapterFilter(String filter) {
        if (chats != null) {
            if (filter.isEmpty()) {
                adapter.clear();
                adapter.addAll(chats.chats);
            } else {
                List<TdApi.Chat> list = new ArrayList<>();
                for (int i = 0; i < chats.chats.length; i++) {
                    String name;
                    String messageText = "";
                    TdApi.ChatInfo info = chats.chats[i].type;
                    if (info.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                        TdApi.PrivateChatInfo privateInfo = (TdApi.PrivateChatInfo) info;
                        name = privateInfo.user.firstName + " " + privateInfo.user.lastName;
                    } else {
                        TdApi.GroupChatInfo groupInfo = (TdApi.GroupChatInfo) info;
                        name = groupInfo.groupChat.title;
                    }
                    TdApi.MessageContent message = chats.chats[i].topMessage.message;
                    if (message.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                        TdApi.MessageText textMessage = (TdApi.MessageText) message;
                        messageText = textMessage.text;
                    }
                    if (name.toLowerCase().contains(filter.toLowerCase()) || messageText.toLowerCase().contains(filter.toLowerCase())) {
                        list.add(chats.chats[i]);
                    }
                    adapter.clear();
                    adapter.addAll(list);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }*/
//mig chanve
/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (!TextUtils.isEmpty(mSearchTerm)) {
            // Saves the current search string
            outState.putString(SearchManager.QUERY, mSearchTerm);
            //outState.putInt("curChoice", currentCheckPosition);
            // Saves the currently selected contact
            //outState.putInt(STATE_PREVIOUSLY_SELECTED_KEY, getListView().getCheckedItemPosition());
        }
    }

        outState.putInt("curChoice", currentCheckPosition);
    }*/
//

    /*@Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        TdApi.Chat selectedItem = adapter.getItem(pos);
        clickedId = selectedItem.id;
        showMessages(pos);
    }*/

    /*void showMessages(int index) {
        currentCheckPosition = index;
        if (getFragmentManager() != null) {
            FragmentTransaction ft
                    = getFragmentManager().beginTransaction();
            getListView().setItemChecked(index, true);
            MessagesFragment messagesFragment = new MessagesFragment();
            if (!fragmentStopped) {
                ft.replace(R.id.messages, messagesFragment);
                ft.commit();
            }
            layout.setVisibility(View.INVISIBLE);
        }
    }*/
//mig chance
/*    public void openChat(long resultId) {
        clickedId = resultId;
        *//*int position = getChatPosition(resultId);
        if (position == Const.CHAT_NOT_FOUND) {
            newPrivateChat(resultId);
        } else {
            showMessages(position);
        }*//*
    }*/
//
   /* private void newPrivateChat(final long userId) {
        getActivity().setRequestedOrientation(getResources().getConfiguration().orientation);
        mProgressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.loading),
                getActivity().getString(R.string.please_wait), true, false);
        ApiHelper.createPrivateChat(userId);
        new ApiClient<>(new TdApi.GetChat(userId), new ChatHandler(), new ApiClient.OnApiResultHandler() {
            @Override
            public void onApiResult(BaseHandler output) {
                if (output.getHandlerId() == ChatHandler.HANDLER_ID) {
                    TdApi.Chat chat = (TdApi.Chat) output.getResponse();
                    clickedId = chat.id;
                    addChatToChatsArray(chat);
                    adapter.clear();
                    adapter.addAll(chats.chats);
                    showMessages(adapter.getPosition(chat));
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        try {
                            mProgressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }*/

   /* private void addChatToChatsArray(TdApi.Chat chat) {
        TdApi.Chat[] newChatArray = new TdApi.Chat[chats.chats.length + 1];
        newChatArray[0] = chat;
        System.arraycopy(chats.chats, 0, newChatArray, 1, chats.chats.length);
        chats = new TdApi.Chats(newChatArray);
    }*/

   /* public void updateChat(long chatId, int unread, int lastRead) {
        boolean updated = false;
        for (int i = 0; i < adapter.getCount(); i++) {
            TdApi.Chat chat = adapter.getItem(i);
            if (chat.id == chatId) {
                adapter.getItem(i).unreadCount = unread;
                adapter.getItem(i).lastReadInboxMessageId = lastRead;
                updated = true;
            }
        }
        if (!updated) {
            getChatsList(Const.CHATS_LIST_OFFSET, Const.CHATS_LIST_LIMIT);
        }
        adapter.notifyDataSetChanged();
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_NEW_MESSAGE && resultCode == Activity.RESULT_OK) {
            long resultId = data.getLongExtra("id", 0);
            openChat(resultId);
        }
    }*/

    //mig change
 /*   @Override
    public void onStop() {
        super.onStop();
        fragmentStopped = true;
    }*/
    //
    /*@Override
    public void onResume() {
        fragmentStopped = false;
        if (adapter.getCount() == 0) {
            getChatsList(Const.CHATS_LIST_OFFSET, Const.CHATS_LIST_LIMIT);
        }
        super.onResume();
    }*/

//    public interface OnChatInteractionListener {
//        /**
//         * Called when a chat is selected from the ListView.
//         * @param chatUri The contact Uri.
//         */
//        public void onChatSelected(Uri chatUri);
//
//        /**
//         * Called when the ListView selection is cleared like when
//         * a chat search is taking place or is finishing.
//         */
//        public void onSelectionCleared();
//    }
}
