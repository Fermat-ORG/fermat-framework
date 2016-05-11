package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Contact fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/16
 * @version 1.0
 *
 */
public class ContactEditFragment extends AbstractFermatFragment {

//    // Bundle key for saving previously selected search result item
//    //private static final String STATE_PREVIOUSLY_SELECTED_KEY =      "SELECTED_ITEM";
//    //private ContactsAdapter mAdapter; // The main query adapter
//    private ImageLoader mImageLoader; // Handles loading the contact image in a background thread
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
//    //private ContactsAdapter adapter;
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
    private ChatSession chatSession;
    private Toolbar toolbar;
    //Defines a tag for identifying log entries
    private static final String TAG = "CHT_ContactEditFragment";

    ArrayList<String> contactname=new ArrayList<>();
    ArrayList<Bitmap> contacticon=new ArrayList<>();
    ArrayList<String> contactid=new ArrayList<>();
    ArrayList<String> contactalias =new ArrayList<>();
    Contact cont;
    EditText aliasET ;
    Button saveBtn ;
    //public ContactsListFragment() {}
  //  static void initchatinfo(){
        //   chatinfo.put(0, Arrays.asList("Miguel", "Que paso?", "12/09/2007"));
        //imgid[0]=R.drawable.ken;
  //  }

    public static ContactEditFragment newInstance() {
  //      initchatinfo();
        return new ContactEditFragment();}

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

        //mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        //setHasOptionsMenu(true);

        try {
            chatSession=((ChatSession) appSession);
            chatManager= chatSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        } catch (Exception e) {
           // CommonLogger.exception(TAG + "oncreate", e.getMessage(), e);
            if(errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        // Check if this fragment is part of a two-pane set up or a single pane by reading a
        // boolean from the application resource directories. This lets allows us to easily specify
        // which screen sizes should use a two-pane layout by setting this boolean in the
        // corresponding resource size-qualified directory.
       // mIsTwoPaneLayout = getResources().getBoolean(R.bool.has_two_panes);

        // Let this fragment contribute menu items
        //setHasOptionsMenu(true);

        // Create the main contacts adapter
//        mAdapter = new ContactsAdapter(getActivity());
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
//        mImageLoader = new ImageLoader(getActivity(), getListPreferredItemHeight()) {
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
        //mImageLoader.addImageCache(getActivity().getSupportFragmentManager(), 0.1f);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View layout = inflater.inflate(R.layout.contact_edit_fragment, container, false);
        try {
            //Contact cont= chatSession.getSelectedContact();
            //TODO: metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
             for (ChatActorCommunityInformation cont: chatManager.listAllConnectedChatActor(
                     chatManager.newInstanceChatActorCommunitySelectableIdentity(chatManager.
                             getIdentityChatUsersFromCurrentDeviceUser().get(0)), 2000, 0)) {
                if (cont.getPublicKey() == chatSession.getData(ChatSession.CONTACT_DATA)) {
                    contactname.add(cont.getAlias());
                    contactid.add(cont.getPublicKey());
                    contactalias.add(cont.getAlias());
                    ByteArrayInputStream bytes = new ByteArrayInputStream(cont.getImage());
                    BitmapDrawable bmd = new BitmapDrawable(bytes);
                    contacticon.add(bmd.getBitmap());
                    ContactAdapter adapter = new ContactAdapter(getActivity(), contactname, contactalias, contactid, "edit", errorManager);
                    //FermatTextView name =(FermatTextView)layout.findViewById(R.id.contact_name);
                    //name.setText(contactname.get(0));
                    //FermatTextView id =(FermatTextView)layout.findViewById(R.id.uuid);
                    //id.setText(contactid.get(0).toString());

                    // create bitmap from resource
                    //Bitmap bm = BitmapFactory.decodeResource(getResources(), contacticon.get(0));

                    // set circle bitmap
                    ImageView mImage = (ImageView) layout.findViewById(R.id.contact_image);
                    mImage.setImageBitmap(getCircleBitmap(contacticon.get(0)));
                    break;
                }
            }
            aliasET =(EditText)layout.findViewById(R.id.aliasEdit);
            aliasET.setText(contactalias.get(0));
            saveBtn = (Button) layout.findViewById(R.id.saveContactButton);
            RelativeLayout contain = (RelativeLayout) layout.findViewById(R.id.containere);
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

//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String aliasText = aliasET.getText().toString();
//                if (TextUtils.isEmpty(aliasText)) {
//                    return;
//                }
//                try {
//                    for (ChatActorCommunityInformation cont: chatManager.listAllConnectedChatActor(
//                            (ChatActorCommunitySelectableIdentity) chatManager.
//                                    getIdentityChatUsersFromCurrentDeviceUser().get(0), 2000, 0)) {
//                        if (cont.getPublicKey() == chatSession.getData(ChatSession.CONTACT_DATA)) {
//                            cont.setAlias(aliasText);
//                            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                            //chatManager.saveContact(con);
//                            Toast.makeText(getActivity(), "Contact Updated", Toast.LENGTH_SHORT).show();
//                            //} catch (CantSaveContactException e) {
//                            //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//
//                        }
//                    }
//                }catch (Exception e){
//                    if (errorManager != null)
//                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                }
//            }
//        });

        return layout;
        //loadDummyHistory();
        // Inflate the list fragment layout
        //return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();
        return output;
    }
//    private void loadDummyHistory(){// Hard Coded
//
//        contactList = new ArrayList<ContactList>();
//
//        ContactList cl = new ContactList();
//        cl.setId(1);
//        cl.setName("John Doe");
//        cl.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        contactList.add(cl);
//        ContactList cl1 = new ContactList();
//        cl1.setId(2);
//        cl1.setName("Jane Doe");
//        cl1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
//        contactList.add(cl1);
//        //adapter = new ChatAdapter(getActivity());//,
//        //contactsContainer.setAdapter((ListAdapter) cl);
//
//        for(int i=0; i<contactList.size(); i++) {
//            ContactList contact_list = contactList.get(i);
//            //displayMessage(contact_list);
//        }
//    }

   /* public void displayMessage(ContactList message) {
        adapter.add(message);
        //adapter.notifyDataSetChanged();
        scroll();
    }
*/
    /*private void scroll() {
        contactsContainer.setSelection(contactsContainer.getCount() - 1);
    }*/

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
//        if (!isAdded() || getActivity() == null) {
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
//            afd = getActivity().getContentResolver().openAssetFileDescriptor(thumbUri, "r");
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
     * in the {@link ContactsContract.Contacts} class.
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
