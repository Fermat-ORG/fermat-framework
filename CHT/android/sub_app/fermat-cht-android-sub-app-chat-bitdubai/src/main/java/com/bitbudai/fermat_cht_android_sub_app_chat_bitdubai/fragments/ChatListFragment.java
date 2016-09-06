package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.cht_dialog_yes_no;
import com.bitdubai.fermat_android_api.engine.FermatApplicationCaller;
import com.bitdubai.fermat_android_api.engine.FermatApplicationSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.ui.Views.PresentationDialog;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatBroadcastReceiver;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.FermatIntentFilter;
import com.bitdubai.fermat_api.layer.actor_connection.common.enums.ConnectionState;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.SubAppsPublicKeys;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.BroadcasterType;
import com.bitdubai.fermat_api.layer.osa_android.broadcaster.FermatBundle;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.util.ChatBroadcasterConstants;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ContactImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunityInformation;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatActorCommunitySelectableIdentity;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesProviderManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.NOTIFICATION_ID;
import static com.bitdubai.fermat_api.layer.osa_android.broadcaster.NotificationBundleConstants.SOURCE_PLUGIN;

/**
 * Chat List Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16
 * @version 1.0
 */

public class ChatListFragment extends AbstractFermatFragment<ReferenceAppFermatSession<ChatManager>, SubAppResourcesProviderManager> implements FermatListItemListeners<com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat> {

    private ChatManager chatManager;
    private ErrorManager errorManager;
    private ChatPreferenceSettings chatSettings;
    private LinearLayoutManager layoutManager;
    ChatListAdapter adapter;
    FermatApplicationCaller applicationsHelper;
    ChatActorCommunitySelectableIdentity chatIdentity;
    RecyclerView list;
    private SearchView searchView;

    ArrayList<com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat> chatList = new ArrayList<>();
    View layout;
    PresentationDialog presentationDialog;
    ImageView noData;
    LinearLayout emptyView;
    TextView noDatalabel;
    TextView nochatssubtitle;
    TextView nochatssubtitle1;

    public static ChatListFragment newInstance() {
        return new ChatListFragment();
    }

    public void chatlistview() {

        try {
            List<Chat> chats = chatManager.listVisibleChats();

            if (chats != null && chats.size() > 0) {

                if (chatIdentity != null) {
                    for (Chat chat : chats) {

                        ChatActorCommunityInformation cont = chatManager.getChatActorConnection(chatIdentity.getPublicKey(), chat.getRemoteActorPublicKey());

                        // if not connected i mark the chat as invisible todo maybe we should just not allow the user to send messages but see the chat
                        if (cont != null && cont.getConnectionState() == ConnectionState.CONNECTED) {

                            chatList.add(getChatFromChat(chat, cont));
                        } else {
                            // if not connected then mark the chat as invisible
                            chatManager.markChatAs(chat.getChatId(), ChatStatus.INVISIBLE                                                                                                                                                                                                                                                                                                                                                                                                                                  );
                        }
                    }
                } else setUpHelpChat();

                if (chats.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    noData.setVisibility(View.VISIBLE);
                    noDatalabel.setVisibility(View.VISIBLE);
                    nochatssubtitle.setVisibility(View.VISIBLE);
                    nochatssubtitle1.setVisibility(View.VISIBLE);
                    getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager_nodata);
                    chatList.clear();
                }
            }
        } catch (CantGetChatException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (CantGetMessageException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat getChatFromChat(Chat chat, ChatActorCommunityInformation cont) throws Exception {

        long unreadMessagesCount = chatManager.getUnreadCountMessageByChatId(chat.getChatId());
        String message = "";
        MessageStatus messageStatus = null;
        TypeMessage typeMessage = null;

        try {
            Message mess = chatManager.getLastMessageByChatId(chat.getChatId());
            if (mess != null) {

                message = mess.getMessage();
                messageStatus = mess.getStatus();
                typeMessage = mess.getType();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayInputStream bytes = new ByteArrayInputStream(cont.getImage());
        BitmapDrawable bmd = new BitmapDrawable(bytes);

        return new com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat(
                cont.getAlias(),
                message,
                chat.getLastMessageDate(),
                chat.getChatId(),
                chat.getRemoteActorPublicKey(),
                messageStatus,
                typeMessage,
                unreadMessagesCount,
                bmd.getBitmap()
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatManager = appSession.getModuleManager();
            errorManager = appSession.getErrorManager();
            applicationsHelper = ((FermatApplicationSession) getActivity().getApplicationContext()).getApplicationManager();

            FermatIntentFilter fermatIntentFilter = new FermatIntentFilter(BroadcasterType.UPDATE_VIEW);
            registerReceiver(fermatIntentFilter, new ChatListBroadcastReceiver());
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }

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

        //set local identity
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
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        cancelChatMessagesNotifications();
        // Let this fragment contribute menu items
        setHasOptionsMenu(true);
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

    void updateValues() {
        try {
            if (chatManager.existAnyVisibleChat()) {
                layout.setBackgroundResource(R.drawable.cht_background_white);
                emptyView.setVisibility(View.GONE);
                noData.setVisibility(View.GONE);
                noDatalabel.setVisibility(View.GONE);
                nochatssubtitle.setVisibility(View.GONE);
                nochatssubtitle1.setVisibility(View.GONE);
                try {
                    getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                chatlistview();
            } else {
                emptyView.setVisibility(View.VISIBLE);
                noData.setVisibility(View.VISIBLE);
                noDatalabel.setVisibility(View.VISIBLE);
                nochatssubtitle.setVisibility(View.VISIBLE);
                nochatssubtitle1.setVisibility(View.VISIBLE);
                try {
                    getActivity().getWindow().setBackgroundDrawableResource(R.drawable.cht_background_viewpager_nodata);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                chatList.clear();
            }
        } catch (CantGetChatException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private void setUpHelpChat() {
        try {
            presentationDialog = new PresentationDialog.Builder(getActivity(), (ReferenceAppFermatSession) appSession)
                    .setTemplateType(PresentationDialog.TemplateType.TYPE_PRESENTATION_WITHOUT_IDENTITIES)
                    .setBannerRes(R.drawable.cht_banner)
                    .setIconRes(R.drawable.chat_subapp)
                    .setSubTitle(R.string.cht_chat_subtitle)
                    .setBody(R.string.cht_chat_body)
                    .setTextFooter(R.string.cht_chat_footer)
                    .setTitle(R.string.cht_dialog_welcome)
                    .setTextCloseButton(R.string.cht_dialog_button_close)
                    .setCheckboxText(R.string.cht_dialog_dont_show)
                    .setVIewColor(R.color.cht_color_dialog)
                    .setIsCheckEnabled(false)
                    .build();
            final ChatActorCommunitySelectableIdentity identity = chatIdentity;
            presentationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    if (identity == null) {
                        try {
                            applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            presentationDialog.show();
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = inflater.inflate(R.layout.chats_list_fragment, container, false);
        emptyView = (LinearLayout) layout.findViewById(R.id.empty_view);
        noData = (ImageView) layout.findViewById(R.id.nodata);
        noDatalabel = (TextView) layout.findViewById(R.id.nodatalabel);
        nochatssubtitle = (TextView) layout.findViewById(R.id.nochatssubtitle);
        nochatssubtitle1 = (TextView) layout.findViewById(R.id.nochatssubtitle1);
        updateValues();
        if (chatSettings.isHomeTutorialDialogEnabled()) {
            setUpHelpChat();
        } else {
            final ChatActorCommunitySelectableIdentity identity = chatIdentity;
            if (identity == null) {
                try {
                    applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        adapter = new ChatListAdapter(getActivity(), chatList);
        adapter.setFermatListEventListener(this);

        list = (RecyclerView) layout.findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setAdapter(adapter);
        registerForContextMenu(list);
        return layout;
    }

    public void clean() {
        adapter = new ChatListAdapter(this.getActivity(), null);
        adapter.setFermatListEventListener(this);
        list.setAdapter(adapter);
    }

    @Override
    public void onItemClickListener(com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat chatCurrent, int position) {

        try {

            System.out.println("entré acá positon "+position);

            appSession.setData("whocallme", "chatlist");
            Contact contact = new ContactImpl();
            contact.setRemoteActorPublicKey(chatCurrent.getContactId());
            contact.setAlias(chatCurrent.getContactName());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (chatCurrent.getImgId() != null) {
                chatCurrent.getImgId().compress(Bitmap.CompressFormat.PNG, 100, stream);
            } else {
                Drawable d = getResources().getDrawable(R.drawable.cht_center_profile_icon_center);
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            }
            byte[] byteArray = stream.toByteArray();
            contact.setProfileImage(byteArray);
            Chat chat = new ChatImpl();
            chat.setChatId(chatCurrent.getChatId());
            appSession.setData(ChatSessionReferenceApp.CONTACT_DATA, contact);
            appSession.setData(ChatSessionReferenceApp.CHAT_DATA, chat);
            changeActivity(Activities.CHT_CHAT_OPEN_MESSAGE_LIST, appSession.getAppPublicKey());
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public void onLongItemClickListener(com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.Chat data, int position) {

    }

    public void onUpdateViewUIThread() {
        if(isAttached) {
            if (searchView != null) {
                if (searchView.getQuery().toString().equals("")) {
                    updateValues();
                    adapter.changeDataSet(chatList);
                }
            } else {
                updateValues();
                adapter.changeDataSet(chatList);
            }
        } else clean();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        unbindDrawables(layout.findViewById(R.id.list));
        unbindDrawables(layout.findViewById(R.id.empty_view));
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
            ((ViewGroup) view).removeAllViewsInLayout();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(layout.findViewById(R.id.list));
        unbindDrawables(layout.findViewById(R.id.empty_view));
        //adapter.clear();
        chatSettings = null;
        chatIdentity = null;
        chatManager = null;
        applicationsHelper = null;
        chatList.clear();
        emptyView.removeAllViewsInLayout();
        applicationsHelper =null;
        destroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
    }

    public void onOptionMenuPrepared(Menu menu) {
        menu.clear();
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.chat_list_menu, menu);
        menu.add(0, 2, 2, "Delete All Chats")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 3, 3, "Go to Profile")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 4, 4, "Go to Community")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        menu.add(0, 5, 5, "Help")
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        MenuItem searchItem = menu.findItem(R.id.menu_search);// menu.findItem(1);
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
                        updateValues();
                        adapter.changeDataSet(chatList);
                        adapter.getFilter().filter(s);
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
                    updateValues();
                    adapter.changeDataSet(chatList);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            int id = item.getItemId();
            switch (id) {
                case 5:
                    setUpHelpChat();
                    break;
                case 3:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_CHAT_IDENTITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    try {
                        applicationsHelper.openFermatApp(SubAppsPublicKeys.CHT_COMMUNITY.getCode());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        if (chatList != null && chatList.size() > 0) {
                            final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(), appSession, chatManager, errorManager);
                            alert.setTextTitle("Delete All Chats");
                            alert.setTextBody("Do you want to delete all chats? All chats will be erased");
                            alert.setType("delete-chats");
                            alert.show();
                            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    try {
                                        updateValues();
                                        adapter.changeDataSet(chatList);
                                    } catch (Exception e) {
                                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                    }
                                }
                            });
                        } else
                            Toast.makeText(getActivity(), "No chats now", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    }
                    break;
                case 1:
                    break;
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedUIException(UISource.ACTIVITY,
                    UnexpectedUIExceptionSeverity.UNSTABLE, FermatException.wrapException(e));
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list) {
            if (Build.VERSION.SDK_INT < 23) {
                MenuInflater inflater = new MenuInflater(getActivity());
                inflater.inflate(R.menu.chat_list_context_menu, menu);
            } else {
                MenuInflater inflater = new MenuInflater(getContext());
                inflater.inflate(R.menu.chat_list_context_menu, menu);
            }
        }
        // Get the info on which item was selected
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        try {
            // Set the info of chat selected in session
            appSession.setData(ChatSessionReferenceApp.CHAT_DATA, chatManager.getChatByChatId(chatList.get(info.position).getChatId()));
        } catch (CantGetChatException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = item.getItemId();
        if (id == R.id.menu_delete_chat) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(), appSession, chatManager, errorManager);
                alert.setTextTitle("Delete Chat");
                alert.setTextBody("Do you want to delete this chat?");
                alert.setType("delete-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            updateValues();
                            adapter.changeDataSet(chatList);
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });


            } catch (Exception e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
        if (id == R.id.menu_clean_chat) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(), appSession, chatManager, errorManager);
                alert.setTextTitle("Clear Chat");
                alert.setTextBody("Do you want to clear this chat? All messages in here will be erased");
                alert.setType("clean-chat");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            updateValues();
                            adapter.changeDataSet(chatList);
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });
            } catch (Exception e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }
        if (id == R.id.menu_delete_all_chats) {
            try {
                final cht_dialog_yes_no alert = new cht_dialog_yes_no(getActivity(), appSession, chatManager, errorManager);
                alert.setTextTitle("Delete All Chats");
                alert.setTextBody("Do you want to delete all chats? All chats will be erased");
                alert.setType("delete-chats");
                alert.show();
                alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        try {
                            updateValues();
                            adapter.changeDataSet(chatList);
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                    }
                });
            } catch (Exception e) {
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
            return true;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * Receiver class implemented
     */
    private class ChatListBroadcastReceiver extends FermatBroadcastReceiver {

        @Override
        public void onReceive(FermatBundle fermatBundle) {
            try {
                if (isAttached) {
                    String code = fermatBundle.getString(Broadcaster.NOTIFICATION_TYPE);

                    if (code.equals(ChatBroadcasterConstants.CHAT_LIST_UPDATE_VIEW)) {
                        onUpdateViewUIThread();

                        int chatBroadcasterType = fermatBundle.getInt(ChatBroadcasterConstants.CHAT_BROADCASTER_TYPE);

                        if (chatBroadcasterType != 0) {
                            switch (chatBroadcasterType) {
                                case ChatBroadcasterConstants.WRITING_NOTIFICATION_TYPE:

                                    String remotePK = fermatBundle.getString(ChatBroadcasterConstants.CHAT_REMOTE_PK);

                                    if (remotePK != null) {
                                        /*todo complete

                                        if(contactId != null) {
                                            if (Build.VERSION.SDK_INT < 23)
                                                message.set(contactId.indexOf(remotePK), getActivity().getResources().getString(R.string.cht_typing));
                                            else
                                                message.set(contactId.indexOf(remotePK), getContext().getResources().getString(R.string.cht_typing));
                                            adapter.refreshEvents(contactName, message, dateMessage, chatId, contactId,
                                                    status, typeMessage, noReadMsgs, imgId);
                                        }
                                        */
                                    }
                                    break;
                                case ChatBroadcasterConstants.MESSAGE_STATUS_UPDATE_TYPE:

                                    break;
                            }
                        }


                    }
                }
            } catch (ClassCastException e) {
                appSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CHT_CHAT,
                        UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            }
        }
    }
}
