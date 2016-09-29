package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.support.v7.widget.SearchView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatMessageListAdapterView;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.util.List;

import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Chat Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 * Update by Miguel Payarez on 15/01/2016
 */

public class ChatMessageListFragment
        extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager> {

    // Fermat Managers
    private ChatManager chatManager;
    private ErrorManager errorManager;
    private ChatPreferenceSettings chatSettings;
    private Toolbar toolbar;
    ChatActorCommunitySelectableIdentity chatIdentity;
    private Handler h = new Handler();

    private ChatMessageListAdapterView adapterView;
    private SearchView searchView;

    public static ChatMessageListFragment newInstance() {
        return new ChatMessageListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            chatManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();

            FermatIntentFilter fermatIntentFilter = new FermatIntentFilter(BroadcasterType.UPDATE_VIEW);
            registerReceiver(fermatIntentFilter, new ChatBroadcastReceiver());

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
                    if (errorManager != null)
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        try {
            chatIdentity = chatSettings.getIdentitySelected();
            if (chatIdentity == null) {
                List<ChatIdentity> chatIdentityList = chatManager
                        .getIdentityChatUsersFromCurrentDeviceUser();
                if (chatIdentityList != null && chatIdentityList.size() > 0) {
                    chatIdentity = chatManager
                            .newInstanceChatActorCommunitySelectableIdentity(
                                    chatIdentityList.get(0));
                    chatSettings.setIdentitySelected(chatIdentity);
                    chatSettings.setProfileSelected(chatIdentity.getPublicKey(),
                            PlatformComponentType.ACTOR_CHAT);
                }
            }

            toolbar = getToolbar();
//            toolbar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    try {
//                        changeActivity(Activities.CHT_CHAT_OPEN_CONTACT_DETAIL, appSession.getAppPublicKey());
//                    }catch (Exception e){
//                        if (errorManager != null)
//                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//                    }
//                }
//            });

        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

        cancelChatMessagesNotifications();
    }

    public void cancelChatMessagesNotifications(){
        //cancel notification of any message if the user is on this fragment
        try {
            FermatBundle fermatBundle = new FermatBundle();
            fermatBundle.put(SOURCE_PLUGIN, Plugins.CHAT_MIDDLEWARE.getCode());
            fermatBundle.put(NOTIFICATION_ID, ChatBroadcasterConstants.CHAT_NEW_INCOMING_MESSAGE_NOTIFICATION);
            cancelNotification(fermatBundle);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public void onUpdateViewUIThread(String remotePk) {
        if(isAttached) {
            if (searchView != null) {
                if (searchView.getQuery().toString().equals("")) {
                    //adapterView.refreshEvents(remotePk, h);
                }
            } else {
               // adapterView.refreshEvents(remotePk, h);
            }
        }else adapterView.clean();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//private void initControls() {}

        getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        adapterView = new ChatMessageListAdapterView.Builder(inflater.getContext())
                .insertInto(container)
                .addErrorManager(errorManager)
                .addAppSession(appSession)
                .addChatManager(chatManager)
                .addChatSettings(chatSettings)
                .addToolbar(toolbar)
                .addActivity(getActivity())
                .build();
        return adapterView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    public void onOptionMenuPrepared(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.chat_menu, menu);
        menu.add(0, 2, 2, getResourceString(R.string.menu_clean_chat))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            searchView.setQueryHint(getResources().getString(R.string.cht_search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    if (s.equals(searchView.getQuery().toString())) {
                        adapterView.refreshEvents();
                        adapterView.getFilter(s);
                    }
                    return false;
                }
            });
            if (appSession.getData("filterString") != null) {
                String filterString = (String) appSession.getData("filterString");
                if (filterString.length() > 0) {
                    searchView.setQuery(filterString, true);
                    searchView.setIconified(false);
                } else {
                    adapterView.refreshEvents();
                }
            }
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        unbindDrawables(adapterView.getRootView().findViewById(R.id.messagesContainer));
        unbindDrawables(adapterView.getRootView().findViewById(R.id.chatSendButton));
        System.gc();
    }

    private void unbindDrawables(View view)
    {
        if (view.getBackground() != null)
        {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView))
        {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++)
            {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        unbindDrawables(adapterView.getRootView().findViewById(R.id.messagesContainer));
//        unbindDrawables(adapterView.getRootView().findViewById(R.id.chatSendButton));
//        adapterView.destroy();
//        adapterView.destroyDrawingCache();
//        adapterView.removeView(getView());
//        adapterView.removeAllViews();
//        adapterView.removeAllViewsInLayout();
//        adapterView = null;
//        chatIdentity = null;
//        chatSettings = null;
//        chatManager = null;
//        appSession = null;
//        destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 1:
                    break;
                case 2:
                    try {
                        final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(), appSession, chatManager, errorManager);
                        alert.setTextTitle(getResourceString(R.string.menu_clean_chat));
                        alert.setTextBody(getResourceString(R.string.menu_clean_chat_body));
                        alert.setType("clean-chat");
                        alert.show();
                        alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                try {
                                    if(alert.cleanChat()){
                                        adapterView.clean();
                                        //onUpdateViewUIThread();
                                    }
                                }catch (Exception e) {
                                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                }
                            }
                        });
                    } catch (Exception e) {
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Receiver class implemented
     */
    private class ChatBroadcastReceiver extends FermatBroadcastReceiver {

        @Override
        public void onReceive(FermatBundle fermatBundle) {
            try {
                if (isAttached) {
                    String code = fermatBundle.getString(Broadcaster.NOTIFICATION_TYPE);

                    if (code.equals(ChatBroadcasterConstants.CHAT_UPDATE_VIEW)) {
                        String remotePK =""; //fermatBundle.getString(ChatBroadcasterConstants.CHAT_WRITING_NOTIFICATION);
                        onUpdateViewUIThread(remotePK);
                    }
                }
            } catch (ClassCastException e) {
                appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CHT_CHAT,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
    }
}