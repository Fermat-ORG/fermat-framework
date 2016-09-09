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
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

/**
 * Contact fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/16
 * @version 1.0
 */
public class ContactFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager> {

    private ErrorManager errorManager;
    private Toolbar toolbar;
    ArrayList<Bitmap> contacticon = new ArrayList<>();

    TextView contactName;
    TextView contactStatusDate;

    public static ContactFragment newInstance() {
        return new ContactFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
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
        View layout = inflater.inflate(R.layout.contact_detail_fragment, container, false);

        try {
            Contact con = (Contact) appSession.getData(ChatSessionReferenceApp.CONTACT_DATA);
            //uncomment when this status or user phrase is visible or available in back end to show it

            ByteArrayInputStream bytes = new ByteArrayInputStream(con.getProfileImage());
            BitmapDrawable bmd = new BitmapDrawable(bytes);
            contacticon.add(bmd.getBitmap());

            String myDate = (String) appSession.getData("DATELASTCONNECTION");
            if(myDate==null)
                myDate=getResourceString(R.string.no_info_available);
            else if(myDate.equals(""))
                    myDate=getResourceString(R.string.no_info_available);

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
        return layout;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
