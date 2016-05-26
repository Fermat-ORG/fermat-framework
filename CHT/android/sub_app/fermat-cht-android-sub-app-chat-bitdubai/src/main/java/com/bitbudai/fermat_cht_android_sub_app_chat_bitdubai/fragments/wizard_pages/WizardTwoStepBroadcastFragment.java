package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.wizard_pages;

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
import android.widget.AdapterView;
import android.widget.ListView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.WizardListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ChtConstants;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Lozadaa on 20/01/16.
 */
public class WizardTwoStepBroadcastFragment extends AbstractFermatFragment {
    private static final String TAG = "WizardTwoStepBroadcastFragment";


    // Fermat Managers

    private ErrorManager errorManager;
    ChatManager chatManager;
    ListView list;
    // Defines a tag for identifying log entries
    ChatModuleManager moduleManager;
    ArrayList<String> contactname = new ArrayList<>();
    ArrayList<Bitmap> contacticon = new ArrayList<>();
    ArrayList<UUID> contactid = new ArrayList<>();
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;
    private ChatPreferenceSettings chatSettings;
    WizardListAdapter adapter;

    public static WizardTwoStepBroadcastFragment newInstance() {
        return new WizardTwoStepBroadcastFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            chatSession = ((ChatSession) appSession);
            chatManager = chatSession.getModuleManager();
            //chatManager = moduleManager.getChatManager();
            errorManager = appSession.getErrorManager();
            //toolbar = getToolbar();
            //toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));
            adapter = new WizardListAdapter(getActivity(), contactname, contacticon, contactid, chatManager,
                    moduleManager, errorManager, chatSession, appSession, null);
            chatSettings = null;

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        try {
            chatSettings = chatManager.loadAndGetSettings(appSession.getAppPublicKey());
        } catch (Exception e) {
            chatSettings = null;
        }
        Toolbar toolbar = getToolbar();
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View layout = inflater.inflate(R.layout.cht_wizard_broadcast_two_step, container, false);
        ShowDialogWelcome();
        layout = inflater.inflate(R.layout.contact_list_fragment, container, false);

        try {
            //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
            List<Contact> con = null;//chatManager.getContacts();
            int size = con.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    contactname.add(con.get(i).getAlias());
                    contactid.add(con.get(i).getContactId());
                    ByteArrayInputStream bytes = new ByteArrayInputStream(con.get(i).getProfileImage());
                    BitmapDrawable bmd = new BitmapDrawable(bytes);
                    contacticon.add(bmd.getBitmap());
                }
            }
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
//            }
//        });

        adapter = new WizardListAdapter(getActivity(), contactname, contacticon, contactid, chatManager,
                moduleManager, errorManager, chatSession, appSession, null);
        list = (ListView) layout.findViewById(R.id.list);
        list.setAdapter(adapter);
        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()/*new AdapterView.OnItemClickListener()*/ {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    appSession.setData("whocallme", "contact");
                    //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                    appSession.setData(ChatSession.CONTACT_DATA, null);//chatManager.getContactByContactId(contactid.get(position)));
                    changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });


        return layout;
    }

    public void ShowDialogWelcome() {
        PresentationDialog presentationDialog = new PresentationDialog.Builder(getActivity(), appSession)
                .setBody(R.string.cht_chat_body_broadcast_step_one)
                .setSubTitle(R.string.cht_chat_subtitle_broadcast_step_one)
                .setTextFooter(R.string.cht_chat_footer_broadcast_step_one)
                .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                .setBannerRes(R.drawable.cht_banner)
                .setIconRes(R.drawable.chat_subapp)
                .build();
        presentationDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(0, ChtConstants.CHT_BROADCAST_NEXT_STEP, 0, "Create")
                .setTitle("Create")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case ChtConstants.CHT_BROADCAST_NEXT_STEP:
                saveSettingAndGoNextStep();
                break;
            case android.R.id.home:
                changeActivity(Activities.CHT_CHAT_BROADCAST_WIZARD_ONE_DETAIL, appSession.getAppPublicKey());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSettingAndGoNextStep() {
        //TODO: AÑADIR SAVESETTINGS
        changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST);

    }


}