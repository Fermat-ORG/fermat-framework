package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_connections;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
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
 */
public class ContactFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager> {

    public List<Contact> contacts;
    private boolean mIsSearchResultView = false;
    private ChatManager chatManager;
    private ErrorManager errorManager;
    private cht_dialog_connections.AdapterCallbackContacts mAdapterCallback;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSessionReferenceApp chatSession;
    private Toolbar toolbar;
    // Defines a tag for identifying log entries
    String TAG = "CHT_ContactFragment";
    ArrayList<String> contactname = new ArrayList<>();
    ArrayList<Bitmap> contacticon = new ArrayList<>();
    ArrayList<String> contactid = new ArrayList<>();
    ArrayList<String> contactalias = new ArrayList<>();

    TextView contactStatus;
    TextView contactName;
    TextView contactStatusDate;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //chatSession=((ChatSessionReferenceApp) appSession);
            chatManager = appSession.getModuleManager();
            //chatManager=moduleManager.getChatManager();
            errorManager = appSession.getErrorManager();
            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
        } catch (Exception e) {
            if (errorManager != null)
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
            Contact con = (Contact) appSession.getData(ChatSessionReferenceApp.CONTACT_DATA); //hatSession.getSelectedContact();
            contactStatus = (TextView) layout.findViewById(R.id.contact_status_input);
            if(con.getContactStatus() != null){
                contactStatus.setText(con.getContactStatus());
            } else
                contactStatus.setText("Available");

            ByteArrayInputStream bytes = new ByteArrayInputStream(con.getProfileImage());
            BitmapDrawable bmd = new BitmapDrawable(bytes);
            contacticon.add(bmd.getBitmap());

            String myDate = (String) appSession.getData("DATELASTCONNECTION");//chatSession.getSelectedContact();
            if(myDate==null)
                myDate="No info available";
            else if(myDate.equals(""))
                    myDate="No info available";

            contactStatusDate = (TextView) layout.findViewById(R.id.contact_status_date);
            contactStatusDate.setText(myDate);

            contactName = (TextView) layout.findViewById(R.id.contact_name_fragment);
            contactName.setText(con.getAlias());

            // set circle bitmap
            ImageView mImage = (ImageView) layout.findViewById(R.id.contact_image);
            mImage.setImageBitmap(contacticon.get(0));

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
            }
        });

        // Inflate the list fragment layout
        return layout;//return inflater.inflate(R.layout.contact_list_fragment, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
