package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapterView;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantDeleteMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetOnlineStatus;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;

import java.util.List;

/**
 * Chat Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 * Update by Miguel Payarez on 15/01/2016
 */

public class ChatFragment extends AbstractFermatFragment {//ActionBarActivity

    // Fermat Managers
    private ChatManager chatManager;
    //private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatPreferenceSettings chatSettings;
    private ChatSession chatSession;
    private Toolbar toolbar;
    ChatActorCommunitySelectableIdentity chatIdentity;

    // Defines a tag for identifying log entries
    String TAG="CHT_ChatFragment";
    private ChatAdapterView adapterView;
    private ChatAdapter adapter;
    private SearchView searchView;

    public static ChatFragment newInstance() { return new ChatFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatSession = ((ChatSession) appSession);
            chatManager = chatSession.getModuleManager();
            //chatManager = moduleManager.getChatManager();
            errorManager = appSession.getErrorManager();

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
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }

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

            toolbar = getToolbar();
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.cht_ic_back_buttom));

            //ChatAdapter chatAdapter = new ChatAdapter(getContext(), (chatHistory != null) ? chatHistory : new ArrayList<ChatMessage>());

//            if(contactid!=null){
//                if(chatManager.getContactByContactId(contactid).getRemoteName().equals("Not registered contact"))
//                {
//                    setHasOptionsMenu(true);
//                }else{ setHasOptionsMenu(false); }
//            }

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public void onUpdateViewOnUIThread(String code) {
        super.onUpdateViewOnUIThread(code);
        if(code.equals("13") && searchView.getQuery().toString().equals("") ){
            adapterView.refreshEvents();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//private void initControls() {}
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(Activities.CHT_CHAT_OPEN_CHATLIST, appSession.getAppPublicKey());
            }
        });
        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        adapterView=new ChatAdapterView.Builder(inflater.getContext())
                .insertInto(container)
                .addModuleManager(null)
                .addErrorManager(errorManager)
                .addChatSession(chatSession)
                .addAppSession(appSession)
                .addChatManager(chatManager)
                .addChatSettings(chatSettings)
                .addToolbar(toolbar)
                .build();
        return adapterView;
    }

    public void onStop () {
        try {
            chatManager.activeOnlineStatus(null);
        } catch (CantGetOnlineStatus cantGetOnlineStatus) {
            cantGetOnlineStatus.printStackTrace();
        }
        super.onStop();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        // Inflate the menu items
        inflater.inflate(R.menu.chat_menu, menu);
        // Locate the search item
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        searchView = (SearchView) searchItem.getActionView();
        //todo: no la encuentra
        //searchView.setQueryHint(getResources().getString(R.string.cht_search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.equals(searchView.getQuery().toString())) {
                    adapterView.getFilter(s);
                }
                return false;
            }
        });
        if (chatSession.getData("filterString") != null) {
            String filterString = (String) chatSession.getData("filterString");
            if (filterString.length() > 0) {
                searchView.setQuery(filterString, true);
                searchView.setIconified(false);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.menu_search) {
//            return true;
//        }
        if (item.getItemId() == R.id.menu_clean_chat) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(),appSession,null,null,null);
                alert.setTextTitle("Clean Chat");
                alert.setTextBody("Do you want to clean this chat? All messages in here will be erased");
                alert.setType("clean-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            adapterView.refreshEvents();
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
//        if (item.getItemId() == R.id.menu_send_chat_email) {
//            //changeActivity(Activities.CHT_CHAT_OPEN_CONNECTIONLIST, appSession.getAppPublicKey());
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}