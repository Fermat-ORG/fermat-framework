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
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_connections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

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
public class ContactFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager>{

    public List<Contact> contacts;
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
   // private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private cht_dialog_connections.AdapterCallbackContacts mAdapterCallback;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSessionReferenceApp chatSession;
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
            //chatSession=((ChatSessionReferenceApp) appSession);
            chatManager= appSession.getModuleManager();
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
            Contact con= (Contact) appSession.getData(ChatSessionReferenceApp.CONTACT_DATA); //hatSession.getSelectedContact();
            contactname.add(con.getAlias());
            contactid.add(con.getRemoteActorPublicKey());
            if(con.getContactStatus() != null) {
                contactalias.add(con.getContactStatus().toString());
            }
            ByteArrayInputStream bytes = new ByteArrayInputStream(con.getProfileImage());
            BitmapDrawable bmd = new BitmapDrawable(bytes);
            contacticon.add(bmd.getBitmap());

            ContactAdapter adapter = new ContactAdapter(getActivity(), contactname, contactalias, contactid, "detail", errorManager);
            FermatTextView name = (FermatTextView) layout.findViewById(R.id.contact_name);
            if(contactalias != null  && !contactalias.isEmpty()) {
                name.setText(contactalias.get(0));
            }
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
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getId() == R.id.menu_block_contact) {
//            //Contact con = chatSession.getSelectedContact();
//            return true;
//        }
//        if (item.getId() == R.id.menu_edit_contact) {
//            try {
//               // Contact con = chatSession.getSelectedContact();
//                //TODO:metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
//                for (ChatActorCommunityInformation cont: chatManager.listAllConnectedChatActor(
//                        chatManager.newInstanceChatActorCommunitySelectableIdentity(chatManager.
//                                getIdentityChatUsersFromCurrentDeviceUser().get(0)), 2000, 0)) {
//                    if (cont.getPublicKey() == chatSession.getData(ChatSessionReferenceApp.CONTACT_DATA)) {
//                        appSession.setData(ChatSessionReferenceApp.CONTACT_DATA, cont.getPublicKey());
//                        break;
//                        // appSession.setData(ChatSessionReferenceApp.CONTACT_DATA, null);//chatManager.getContactByContactId(con.getContactId()));
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
//        if (item.getId() == R.id.menu_del_contact) {
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
}
