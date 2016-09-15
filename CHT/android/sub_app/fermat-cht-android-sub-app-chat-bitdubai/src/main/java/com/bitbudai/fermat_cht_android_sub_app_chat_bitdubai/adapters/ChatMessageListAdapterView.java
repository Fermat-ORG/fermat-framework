package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSessionReferenceApp;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.ConstantSubtitle;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Utils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_android_api.utils.KeyboardUtil;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.identity.interfaces.ChatIdentity;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by miguel on 22/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 09/01/16.
 */

public class ChatMessageListAdapterView extends LinearLayout {

    private RecyclerView messagesContainer;
    private ChatMessageListAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private ChatManager chatManager;
    private ErrorManager errorManager;
    private ChatPreferenceSettings chatSettings;
    private FermatSession appSession;
    private Toolbar toolbar;
    private Button sendBtn;
    private EditText messageET;
    private ViewGroup rootView;
    private String leftName;
    private UUID chatId;
    private Activity activity;
    private String remotePk;
    private Bitmap contactIcon;
    private BitmapDrawable contactIconCircular;
    private LinearLayoutManager layoutManager;
    private boolean chatWasCreate = false;
    private boolean isScrollingUp = false;
    UUID newChatId;
    int oldChatMessagesCount = 0;

    public ChatMessageListAdapterView(Context context,
                                      ChatManager chatManager,
                                      ErrorManager errorManager,
                                      FermatSession appSession,
                                      Toolbar toolbar,
                                      ChatPreferenceSettings chatSettings, Activity activity) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.chat, (rootView != null) ? rootView : null));
        this.chatManager = chatManager;
        this.errorManager = errorManager;
        this.appSession = appSession;
        this.toolbar = toolbar;
        this.chatSettings = chatSettings;
        this.activity = activity;
        initControls();
    }

    public ChatMessageListAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatMessageListAdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void findValues(Contact contact) { //With contact Id find chatId,pkremote,actortype
        try {
            if (contact != null) {
                remotePk = contact.getRemoteActorPublicKey();
                contactIcon = (new BitmapDrawable(new ByteArrayInputStream(contact.getProfileImage()))).getBitmap();
                leftName = contact.getAlias();
                Chat cht = chatManager.getChatByRemotePublicKey(remotePk);

                if (cht != null) {
                    chatId = cht.getChatId();
                    appSession.setData(ChatSessionReferenceApp.CHAT_DATA, cht);
                } else chatId = null;
            }
        } catch (CantGetChatException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public void whatToDo() {
        try {
            findValues((Contact) appSession.getData(ChatSessionReferenceApp.CONTACT_DATA));
            if(appSession.getData("whocallme") != null) {
                if (appSession.getData("whocallme").equals("chatlist")) {
                    chatWasCreate = true;
                } else if (appSession.getData("whocallme").equals("contact")) {  //fragment contact call this fragment
                    //if I choose a contact, this will search the chat previously created with this contact
                    //Here it is define if we need to create a new chat or just add the message to chat created previously
                    chatWasCreate = chatId != null;
                }
            }
            //appSession.setData("whocallme", null);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public void findMessage() {
        try {
            if (chatHistory == null)
                chatHistory = new ArrayList<>();
            else {
                oldChatMessagesCount = chatHistory.size();
                chatHistory.clear();
            }

            if (chatId != null) {
                List<Message> messL = chatManager.getMessagesByChatId(chatId);
                for (Message mess : messL) {
                    ChatMessage msg = buildChatMessage(mess);

                    if(msg != null)
                        chatHistory.add(msg);
                }
            }

            if (adapter == null || adapter.getItemCount() > 0) {
                adapter = new ChatMessageListAdapter(this.getContext(), chatHistory);
                messagesContainer.setAdapter(adapter);
            } else {
                adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                if (oldChatMessagesCount < chatHistory.size() && !isScrollingUp)
                    scroll();
            }
        } catch (CantGetMessageException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    private ChatMessage buildChatMessage(Message mess) {

        ChatMessage msg = null;
        try {
            msg = new ChatMessage();
            msg.setId(mess.getMessageId());
            if (mess.getType().equals(TypeMessage.OUTGOING)) msg.setMe(true);
            else {
                msg.setMe(false);
                if (!mess.getStatus().equals(MessageStatus.READ)) {
                    chatManager.sendReadMessageNotification(msg.getId(), chatId);
                    chatManager.markAsRead(msg.getId());
                }
            }
            msg.setStatus(mess.getStatus());

            msg.setDate(mess.getMessageDate());
            msg.setMessage(mess.getMessage());
            msg.setType(mess.getType().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return msg;
    }

    public class BackgroundAsyncTaskWriting extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                chatManager.sendWritingStatus(chatId);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class BackgroundAsyncTask extends AsyncTask<Message, Integer, Message> {

        @Override
        protected void onPostExecute(Message result) {

        }

        @Override
        protected Message doInBackground(Message... params) {
            try {
                for (Message param : params) {
                    chatManager.sendMessage(param);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        if (!(Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 21)) {
            RelativeLayout.LayoutParams layoutParams =
                    (RelativeLayout.LayoutParams) messagesContainer.getLayoutParams();
            DisplayMetrics dm = getResources().getDisplayMetrics();
            if (dm.heightPixels < 800)
                layoutParams.height = 764;
            else if (dm.heightPixels < 1080 && dm.heightPixels >= 800)
                layoutParams.height = 944;
            else if (dm.heightPixels < 1280 && dm.heightPixels >= 1080)
                layoutParams.height = 1244;
            messagesContainer.setLayoutParams(layoutParams);
        }
    }

    public void onAdjustKeyboard() {
        if (!(Build.VERSION.SDK_INT > 18 && Build.VERSION.SDK_INT < 21)) {
            try {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) messagesContainer.getLayoutParams();
                layoutParams.height = 440;
                messagesContainer.setLayoutParams(layoutParams);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                onBackPressed();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private boolean isKeyboardShown(View rootView) {
        //SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
            int heightDiff = rootView.getBottom() - r.bottom;
            boolean isKeyboardShown = heightDiff > 128 * dm.density;
            return isKeyboardShown;
    }

    public String setFormatLastTime(Timestamp date) {
        String fecha = "";
        SimpleDateFormat formatter;
        String formattedTime;
        if (date != null) {
            if (android.text.format.DateFormat.is24HourFormat(getContext())) {
                formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            } else {
                formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
            }
            try {
                formatter.setTimeZone(TimeZone.getDefault());

                formattedTime = formatter.format(date);
                //String formattedTime = formatter.format(dater);
                if (formattedTime.length() > 16) {
                    formattedTime = formattedTime.substring(11, 19);
                } else {
                    formattedTime = formattedTime.substring(11, 16);
                }

                if (Validate.isDateToday(date)) {
                    fecha = getContext().getResources().getString(R.string.cht_today) + " " + formattedTime;
                } else {
                    Date today = new Date();
                    long dias = (today.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
                    if (dias == 1) {
                        fecha = getContext().getResources().getString(R.string.cht_yesterday) + " " + formattedTime;
                    }
                }
            } catch (Exception e) {
                Log.e("ErrorOnSetFormatLastTim", e.getMessage(), e);
            }
        }
        return fecha;
    }

    public void changeStatusOnTheSubtitleBar(int state, Timestamp date, Handler h) {
        switch (state) {
            case ConstantSubtitle.IS_OFFLINE:
                if (date != null && !date.equals("no record")) {
                    toolbar.setSubtitle(Html.fromHtml("<small><small>"+getContext().getResources().getString(R.string.cht_last_message_received) + " "  + setFormatLastTime(date) + "</small></small>"));
                    appSession.setData("DATELASTCONNECTION", setFormatLastTime(date));
                } else {
                    Log.i("159753**LastTimeOnChat", "No show");
                }
                break;
            case ConstantSubtitle.IS_ONLINE:
                toolbar.setSubtitle(getContext().getResources().getString(R.string.cht_online));
                break;
            case ConstantSubtitle.IS_WRITING:
                toolbar.setSubtitle(getContext().getResources().getString(R.string.cht_typing));

                h.postDelayed(
                        new Runnable(){
                            @Override
                            public void run() {
                                checkStatus();
                            }
                        },
                        TimeUnit.SECONDS.toMillis(4)
                );
                break;
        }
    }

    public void initControls() {
        messagesContainer = (RecyclerView) findViewById(R.id.messagesContainer);
        layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        messagesContainer.setLayoutManager(layoutManager);

        messagesContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy != 0) { //Scrolling by user
                    int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                    int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                    int pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                    final int lastItem = pastVisiblesItems + visibleItemCount;
                    isScrollingUp = lastItem == totalItemCount ? false : true; //If I'm on the bottom false, if i'm scrolling up, flag it
                } else { //Scrolling by method scroll()
                    isScrollingUp = false;
                }
            }
        });
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        messageET.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
                            if (messageET != null) {
                                if (messageET.getRootView() != null) {
                                    if (!isKeyboardShown(messageET.getRootView())) {
                                        onBackPressed();
                                    } else onAdjustKeyboard();
                                }
                            }
                        }
                    }
                });
        messageET.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                KeyboardUtil keyboardUtil = new KeyboardUtil(activity, v.getRootView().findViewById(R.id.inputContainer), 6);
                if(hasFocus) {
                    if (!isScrollingUp) {
                        scroll();
                    }
                    keyboardUtil.enable();
                }else
                    keyboardUtil.disable();
            }
        });
                toolbar.setSubtitle("");
        messageET.addTextChangedListener(new TextWatcher() {

            private long lastTimeSent = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (chatWasCreate && s.length()>0) {
                    if (s.length()>0) {
                        long currentTimeToSend = System.currentTimeMillis();
                        if (currentTimeToSend > lastTimeSent + 7000) {
                            BackgroundAsyncTaskWriting batw = new BackgroundAsyncTaskWriting();
                            batw.execute();
                            lastTimeSent = currentTimeToSend;
                        }
                    }
                    if ((start >= 0 || s.charAt(s.length() - 1) == '\n' || s.length() > 10) && !isScrollingUp) {
                        scroll();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (appSession != null) {
            whatToDo();
            findMessage();
            scroll();
            checkStatus();

            if (leftName != null) {
                toolbar.setTitle(leftName);
                contactIconCircular = new BitmapDrawable(getResources(), Utils.getRoundedShape(contactIcon, 300));
                toolbar.setLogo(contactIconCircular);

                for (int i = 0; i < toolbar.getChildCount(); i++) {
                    View child = toolbar.getChildAt(i);
                    if (child != null) {
                        if (child.getClass() == ImageView.class) {
                            ImageView iv2 = (ImageView) child;
                            if (iv2.getDrawable() == contactIconCircular) {
                                iv2.setAdjustViewBounds(true);
                                int padding = (int) (5 * getResources().getDisplayMetrics().density);
                                iv2.setPadding(padding, padding, padding, padding);
                                break;
                            }
                        }
                    }
                }
            }
        }

        sendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundAsyncTask sendMessageAsync = new BackgroundAsyncTask();
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText) || messageText.trim().length() == 0) {
                    return;
                }

                messageText = messageText.trim();

                try {
                    UUID messageId = UUID.randomUUID();

                    ChatImpl chat = new ChatImpl();
                    final MessageImpl message = new MessageImpl();
                    Long dv = System.currentTimeMillis();
                    Timestamp timestamp = new Timestamp(dv);
                    String remotePublicKey;
                    if (chatWasCreate) {
                        chat = (ChatImpl) chatManager.getChatByChatId(chatId);
                        chat.setLastMessageDate(timestamp);
                        remotePublicKey = chat.getRemoteActorPublicKey();
                        Chat chatPrevious = chatManager.getChatByRemotePublicKey(remotePublicKey);

                        if (chatPrevious.getChatId() != chatId) {
                            newChatId = chatPrevious.getChatId();
                        } else {
                            newChatId = chatId;
                        }
                        chat.setChatId(newChatId);
                        chat.setStatus(ChatStatus.VISIBLE);
                        chatManager.saveChat(chat);

                        message.setChatId(newChatId);
                        message.setMessageId(messageId);
                        message.setMessage(messageText);
                        message.setMessageDate(timestamp);
                        message.setStatus(MessageStatus.CREATED);
                        message.setType(TypeMessage.OUTGOING);
                        chatManager.saveMessage(message);
                        sendMessageAsync.execute(message);
                    } else {
                        Contact newContact = (Contact) appSession.getData(ChatSessionReferenceApp.CONTACT_DATA);//chatSession.getSelectedContact();
                        remotePublicKey = newContact.getRemoteActorPublicKey();
                        chat.setRemoteActorPublicKey(remotePublicKey);
                        Chat chatPrevious = chatManager.getChatByRemotePublicKey(remotePublicKey);
                        if (newChatId == null) {
                            if (chatPrevious != null) {
                                newChatId = chatPrevious.getChatId();
                            } else {
                                newChatId = UUID.randomUUID();
                            }
                        }
                        chat.setChatId(newChatId);
                        chat.setStatus(ChatStatus.VISIBLE);
                        chat.setCreationDate(timestamp);
                        chat.setLastMessageDate(timestamp);
                        /**
                         * Now we got the identities registered in the device.
                         * To avoid nulls, I'll put default data in chat object
                         *///
                        chat.setLocalActorPublicKey("defaultdata");
                        //if (chatSettings.getLocalPublicKey() != null /*&& chatSettings.getLocalPlatformComponentType() != null*/) {
                        //Asigno pk del usuario y no uso la del NS

                        String pKey = chatSettings.getLocalPublicKey();
                        if (pKey != null) {
                            chat.setLocalActorPublicKey(pKey);
                        } else {
                            List<ChatIdentity> chatIdentities = chatManager.getIdentityChatUsersFromCurrentDeviceUser();
                            chat.setLocalActorPublicKey(chatIdentities.get(0).getPublicKey());
                        }

                        chatManager.saveChat(chat);

                        message.setChatId(newChatId);
                        message.setMessageId(messageId);
                        message.setMessage(messageText);
                        message.setMessageDate(timestamp);
                        message.setStatus(MessageStatus.CREATED);
                        message.setType(TypeMessage.OUTGOING);
                        chatManager.saveMessage(message);
                        sendMessageAsync.execute(message);
                        /**
                         * This chat was created, so, I will put chatWasCreate as true to avoid
                         * the multiple chats from this contact. Also I will put the chatId as
                         * newChatId
                         */
                        chatWasCreate = true;
                        chatId = newChatId;
                    }

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(messageId);
                    chatMessage.setMessage(messageText);
                    chatMessage.setDate(timestamp);
                    chatMessage.setMe(true);
                    messageET.setText("");
                    if (adapter == null || adapter.getItemCount() > 0) {
                        adapter = new ChatMessageListAdapter(getContext(), (chatHistory != null) ? chatHistory : new ArrayList<ChatMessage>());
                        messagesContainer.setAdapter(adapter);
                    }
                    displayMessage(chatMessage);
                } catch (CantSaveMessageException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (CantSaveChatException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });
    }

    public void getFilter(String s) {
        if(adapter != null)
            adapter.getFilter().filter(s);
    }

    public void displayMessage(ChatMessage message) {

        if (message != null && chatHistory != null && adapter != null) {
            chatHistory.add(message);
            adapter.changeDataSet(chatHistory);
            this.scroll();
        }
    }

    public void refreshEvents() {
        findValues((Contact) appSession.getData(ChatSessionReferenceApp.CONTACT_DATA));
        findMessage();
        checkStatus();
    }

    public void clean() {
        adapter = new ChatMessageListAdapter(this.getContext(), new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);
    }

    public void checkStatus(String remotePkWriting, Handler h) {

        try {
            if (remotePkWriting != null && remotePkWriting.equals(remotePk)) {
                changeStatusOnTheSubtitleBar(ConstantSubtitle.IS_WRITING, null, h);
            } else {
                if(chatId != null){
                    Timestamp date = chatManager.getLastMessageReceivedDate(chatId);
                    changeStatusOnTheSubtitleBar(ConstantSubtitle.IS_OFFLINE, date, null);
                }
            }
        } catch (CantGetChatException cantGetOnlineStatus) {
            cantGetOnlineStatus.printStackTrace();
        }
    }

    public void checkStatus() {

        try {

            if(chatId != null){
                Timestamp date = chatManager.getLastMessageReceivedDate(chatId);
                changeStatusOnTheSubtitleBar(ConstantSubtitle.IS_OFFLINE, date, null);
            }
        } catch (CantGetChatException cantGetOnlineStatus) {
            cantGetOnlineStatus.printStackTrace();
        }
    }

    public void updateMessageStatus(UUID messageId, MessageStatus messageStatus) {

        if (messageId != null && messageStatus != null && chatHistory != null && adapter != null) {

            for (ChatMessage chatMessage : chatHistory) {

                if (chatMessage.getId().equals(messageId)) {

                    chatMessage.setStatus(messageStatus);
                    adapter.changeDataSet(chatHistory);
                    break;
                }
            }

        }
    }

    public void addMessage(Message message) {

        if (chatId != null && message != null && chatId.equals(message.getChatId())) {

            if (chatHistory != null && adapter != null) {

                ChatMessage mes = buildChatMessage(message);

                if (mes != null) {
                    chatHistory.add(mes);

                    adapter.changeDataSet(chatHistory);
                    scroll();
                }

            }
        }
    }

    public void scroll() {
        if (adapter != null && adapter.getItemCount() != 0 && adapter.getItemCount() > 0)
            messagesContainer.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    public void addLeftName(String leftName) {
        this.leftName = leftName;
    }

    private void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    private void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    private void setChatSettings(ChatPreferenceSettings chatSettings) {
        this.chatSettings = chatSettings;
    }

    private void setAppSession(FermatSession appSession) {
        this.appSession = appSession;
    }

    private void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
    private void setActivity(Activity activity) {
        this.activity = activity;
    }

    public static class Builder {

        private Context context;
        private ViewGroup rootView;
        private ChatManager chatManager;
        private ErrorManager errorManager;
        private ChatPreferenceSettings chatSettings;
        private FermatSession appSession;
        private Toolbar toolbar;
        private Activity activity;
        String leftName;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder insertInto(ViewGroup rootView) {
            this.rootView = rootView;
            return this;
        }

        public Builder addChatManager(ChatManager chatManager) {
            this.chatManager = chatManager;
            return this;
        }

        public Builder addErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
            return this;
        }

        public Builder addChatSettings(ChatPreferenceSettings chatSettings) {
            this.chatSettings = chatSettings;
            return this;
        }

        public Builder addToolbar(Toolbar toolbar) {
            this.toolbar = toolbar;
            return this;
        }

        public Builder addAppSession(FermatSession appSession) {
            this.appSession = appSession;
            return this;
        }

        public Builder addActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public ChatMessageListAdapterView build() {
            ChatMessageListAdapterView chatView = new ChatMessageListAdapterView(
                    context,
                    chatManager,
                    errorManager,
                    appSession,
                    toolbar,
                    chatSettings,
                    activity
            );

            if (rootView != null) {
                chatView.setRootView(rootView);
            }
            if (chatManager != null) {
                chatView.setChatManager(chatManager);
            }
            if (errorManager != null) {
                chatView.setErrorManager(errorManager);
            }
            if (chatSettings != null) {
                chatView.setChatSettings(chatSettings);
            }

            if (appSession != null) {
                chatView.setAppSession(appSession);
            }
            if (leftName != null) {
                chatView.addLeftName(leftName);
            }
            if (toolbar != null) {
                chatView.setToolbar(toolbar);
            }
            if (activity != null) {
                chatView.setActivity(activity);
            }

            return chatView;
        }
    }
}