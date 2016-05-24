package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ContactAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_connections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;

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
public class ContactFragment extends AbstractFermatFragment {

//    // Defines a tag for identifying log entries
//    private static final String TAG = "ContactsListFragment";
//
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
   // private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private cht_dialog_connections.AdapterCallbackContacts mAdapterCallback;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    private Toolbar toolbar;
    // Defines a tag for identifying log entries
    String TAG = "CHT_ContactFragment";
    ArrayList<String> contactname=new ArrayList<>();
    ArrayList<Bitmap> contacticon=new ArrayList<>();
    ArrayList<String> contactid=new ArrayList<>();
    ArrayList<String> contactalias =new ArrayList<>();

    public static ContactFragment newInstance() {
        return new ContactFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatSession=((ChatSession) appSession);
            chatManager= chatSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager=appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        } catch (Exception e) {
            if(errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/HelveticaNeue Medium.ttf");
        View layout = inflater.inflate(R.layout.contact_detail_fragment, container, false);

        try {
            Contact con= chatSession.getSelectedContact();
            contactname.add(con.getAlias());
            contactid.add(con.getRemoteActorPublicKey());
            contactalias.add(con.getAlias());
            ByteArrayInputStream bytes = new ByteArrayInputStream(con.getProfileImage());
            BitmapDrawable bmd = new BitmapDrawable(bytes);
            contacticon.add(bmd.getBitmap());

            ContactAdapter adapter = new ContactAdapter(getActivity(), contactname, contactalias, contactid, "detail", errorManager);
            FermatTextView name = (FermatTextView) layout.findViewById(R.id.contact_name);
            name.setText(contactalias.get(0));
            FermatTextView id = (FermatTextView) layout.findViewById(R.id.uuid);
            id.setText(contactid.get(0).toString());

            // set circle bitmap
            ImageView mImage = (ImageView) layout.findViewById(R.id.contact_image);
            mImage.setImageBitmap(Utils.getCircleBitmap(contacticon.get(0)));

            LinearLayout detalles = (LinearLayout) layout.findViewById(R.id.contact_details_layout);

            final int adapterCount = adapter.getCount();

            for (int i = 0; i < adapterCount; i++) {
                View item = adapter.getView(i, null, null);
                detalles.addView(item);
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

        // Inflate the list fragment layout
        return layout;//return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate the menu items
//        inflater.inflate(R.menu.contact_detail_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_block_contact) {
//            //Contact con = chatSession.getSelectedContact();
//            return true;
//        }
//        if (item.getItemId() == R.id.menu_edit_contact) {
//            try {
//               // Contact con = chatSession.getSelectedContact();
//                //TODO:metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                for (ChatActorCommunityInformation cont: chatManager.listAllConnectedChatActor(
//                        chatManager.newInstanceChatActorCommunitySelectableIdentity(chatManager.
//                                getIdentityChatUsersFromCurrentDeviceUser().get(0)), 2000, 0)) {
//                    if (cont.getPublicKey() == chatSession.getData(ChatSession.CONTACT_DATA)) {
//                        appSession.setData(ChatSession.CONTACT_DATA, cont.getPublicKey());
//                        break;
//                        // appSession.setData(ChatSession.CONTACT_DATA, null);//chatManager.getContactByContactId(con.getContactId()));
//                    }
//                }
//                changeActivity(Activities.CHT_CHAT_EDIT_CONTACT, appSession.getAppPublicKey());
//            //}catch(CantGetContactException e) {
//            //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            }catch (Exception e){
//                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            }
//            return true;
//        }
//        if (item.getItemId() == R.id.menu_del_contact) {
//            final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,mAdapterCallback);
//            alert.setTextTitle("Delete contact");
//            alert.setTextBody("Do you want to delete this contact?");
//            alert.setType("delete-contact");
//            alert.show();
//            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                           @Override
//                                           public void onDismiss(DialogInterface dialog) {
//                                               if(alert.getStatusDeleteContact() == true){
//                                                   try {
//                                                       changeActivity(Activities.CHT_CHAT_OPEN_CONTACTLIST);
//                                                   }catch (Exception e){
//                                                       errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                                                   }
//                                               }
//                                           }
//                                       }
//            );
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

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

}
