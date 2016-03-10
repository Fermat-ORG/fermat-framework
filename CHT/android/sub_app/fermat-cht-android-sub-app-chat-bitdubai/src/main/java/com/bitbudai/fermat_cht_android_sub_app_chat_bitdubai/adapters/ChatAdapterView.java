package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.enums.ChatStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.MessageStatus;
import com.bitdubai.fermat_cht_api.all_definition.enums.TypeMessage;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetNetworkServicePublicKeyException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.ChatImpl;
import com.bitdubai.fermat_cht_api.layer.middleware.utils.MessageImpl;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatPreferenceSettings;
import com.bitdubai.fermat_dap_api.layer.all_definition.util.Validate;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

/**
 * Created by miguel on 22/01/16.
 * Updated by Jose Cardozo josejcb (josejcb89@gmail.com) on 09/01/16.
 */

public class ChatAdapterView extends LinearLayout {

    private RecyclerView messagesContainer;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private ChatSession chatSession;
    private ChatPreferenceSettings chatSettings;
    private FermatSession appSession;
    private Toolbar toolbar;
    private Button sendBtn;
    private EditText messageET;
    private ViewGroup rootView;
    private String leftName;
    private String rightName;
    private UUID chatId;
    private UUID contactId;
    private int background = -1;
    private String remotePk;
    private PlatformComponentType remotePCT;
    private Bitmap contactIcon;
    private BitmapDrawable contactIconCircular;
    private boolean loadDummyData = false;
    private boolean chatWasCreate =false;
    private Calendar today;

    public ChatAdapterView(Context context, ArrayList<ChatMessage> chatHistory,
                           ChatManager chatManager, ChatModuleManager moduleManager,
                           ErrorManager errorManager, ChatSession chatSession, FermatSession appSession, int background, Toolbar toolbar, ChatPreferenceSettings chatSettings) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.chat, (rootView != null) ? rootView : null));
        this.chatHistory=chatHistory;
        this.chatManager=chatManager;
        this.moduleManager=moduleManager;
        this.errorManager=errorManager;
        this.chatSession=chatSession;
        this.appSession=appSession;
        this.toolbar=toolbar;
        this.chatSettings=chatSettings;
        //this.background=background;
        initControls();
    }

    public ChatAdapterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatAdapterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChatAdapterView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    void findValues(Contact contact){ //With contact Id find chatId,pkremote,actortype
        try {
            if (contact != null){
                remotePk = contact.getRemoteActorPublicKey();
                remotePCT = contact.getRemoteActorType();
                contactId =contact.getContactId();
                ByteArrayInputStream bytes = new ByteArrayInputStream(contact.getProfileImage());
                BitmapDrawable bmd = new BitmapDrawable(bytes);
                contactIcon =bmd.getBitmap();
                leftName=contact.getAlias();
                for (int i = 0; i < chatManager.getMessages().size(); i++) {
                    if (contactId.equals(chatManager.getMessages().get(i).getContactId())) {
                        chatId = chatManager.getMessages().get(i).getChatId();
                    }
                }
            }
        }catch (CantGetMessageException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch(Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public void whatToDo(){
        try {
            System.out.println("WHOCALME NOW:" + chatSession.getData("whocallme"));
            if (chatSession.getData("whocallme").equals("chatlist")) {
                findValues((Contact) chatSession.getData("contactid"));//if I choose a chat, this will retrieve the chatId
                chatWasCreate = true;
            } else if (chatSession.getData("whocallme").equals("contact")) {  //fragment contact call this fragment
                findValues(chatSession.getSelectedContact());//if I choose a contact, this will search the chat previously created with this contact
                //Here it is define if we need to create a new chat or just add the message to chat created previously
                chatWasCreate = chatId != null;
            }
        }catch(Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public void findMessage(){
        String message;
        String inorout;
        String estatus;
        ChatMessage msg;
        Chat chat;
        int messSize;
        try {
            setChatHistory(null);
            chatHistory=null;
            if(chatId !=null){
                chat=chatManager.getChatByChatId(chatId);
            }else{
                chat=chatSession.getSelectedChat();
            }

            if(chat!=null)
                chatId =chat.getChatId();
            if (chatHistory == null) {
                chatHistory = new ArrayList<ChatMessage>();
            }

            if(chatId !=null){
                //messSize=chatManager.getMessageByChatId(chatId).size();
                List<Message> messL=  chatManager.getMessageByChatId(chatId);
                MessageImpl messagei;
                // messSize= messL.size();
                //for (int i = 0; i < messSize; i++) {
                for(Message mess : messL){
                    msg = new ChatMessage();
                    message = mess.getMessage();
                    inorout = mess.getType().toString();
                    estatus = mess.getStatus().toString();
                    msg.setId(mess.getMessageId());
                    if (inorout == TypeMessage.OUTGOING.toString()) msg.setMe(true);
                    else {
                        msg.setMe(false);
                        if(estatus!= MessageStatus.READ.toString()) {
                            messagei = (MessageImpl) chatManager.getMessageByMessageId(msg.getId());
                            msg.setStatus(MessageStatus.READ.toString());
                            messagei.setStatus(MessageStatus.READ);
                            chatManager.saveMessage(messagei);
                        }
                    }
                    msg.setStatus(mess.getStatus().toString());
                    long milliseconds = mess.getMessageDate().getTime() + (mess.getMessageDate().getNanos() / 1000000);
                    if (Validate.isDateToday(new Date(DateFormat.getDateTimeInstance().format(new java.util.Date(milliseconds)))))
                    {
                        String S = new SimpleDateFormat("HH:mm").format(new java.util.Date(milliseconds));
                        msg.setDate(S);
                    }else
                    {
                        msg.setDate(DateFormat.getDateTimeInstance().format(new java.util.Date(milliseconds)));
                    }
                    msg.setUserId(mess.getContactId());
                    msg.setMessage(message);
                    msg.setType(mess.getType().toString());
                    chatHistory.add(msg);
                }
                adapter = new ChatAdapter(this.getContext(), (chatHistory != null) ? chatHistory : new ArrayList<ChatMessage>());
                messagesContainer.setAdapter(adapter);
            }else{
                Toast.makeText(getContext(),"Waiting for chat message", Toast.LENGTH_SHORT).show();
            }
        //}catch (CantSaveMessageException e) {
           // errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (CantGetMessageException e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }catch (Exception e){
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
    }

    public static Bitmap decodeFile(Context context,int resId) {
// decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, o);
// Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 50;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true)
        {
            if (width_tmp / 2 < REQUIRED_SIZE
                    || height_tmp / 2 < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale++;
        }
// decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeResource(context.getResources(), resId, o2);
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage,int width) {
        // TODO Auto-generated method stub
        int targetWidth = width;
        int targetHeight = width;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);
        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }

    public void initControls() {
        messagesContainer = (RecyclerView) findViewById(R.id.messagesContainer);
        messagesContainer.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        //messageET.setText("Type message");
        //TextView meLabel = (TextView) findViewById(R.id.meLbl);
        //TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);

        if(chatSession!= null){
            whatToDo();
            findMessage();
        
//        if (rightName != null) {
//            meLabel.setText(rightName);
//        } else {
//            meLabel.setText("");
//        }

            if (leftName != null) {
                toolbar.setTitle(leftName);
                contactIconCircular = new BitmapDrawable( getResources(),  getRoundedShape( contactIcon, 80));//in the future, this image should come from chatmanager
                toolbar.setLogo(contactIconCircular);
            }
        }
        //companionLabel.setText(leftName);
//        } else {
//            companionLabel.setText("Contacto");
//        }
        
        if (background != -1) {
            container.setBackgroundColor(background);
        }

        messageET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //messageET.setText("");
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                try {
                    ChatImpl chat = new ChatImpl();
                    MessageImpl message = new MessageImpl();
                    Long dv = System.currentTimeMillis();

                    if (chatWasCreate) {

                        chat = (ChatImpl) chatManager.getChatByChatId(chatId);
                        chat.setLastMessageDate(new Timestamp(dv));
                        chatManager.saveChat(chat);

//                        chat=(ChatImpl)chatManager.getChatByChatId(chatId);
//                        chatManager.saveChat(chat);

                        message.setChatId(chatId);
                        message.setMessageId(UUID.randomUUID());
                        message.setMessage(messageText);
                        message.setMessageDate(new Timestamp(dv));
                        message.setStatus(MessageStatus.CREATED);
                        message.setType(TypeMessage.OUTGOING);
                        message.setContactId(contactId);
                        chatManager.saveMessage(message);
                    } else {
                        UUID newChatId = UUID.randomUUID();
                        chat.setChatId(newChatId);
                        chat.setObjectId(UUID.randomUUID());
                        chat.setStatus(ChatStatus.VISSIBLE);
                        //Todo: find another chat name
                        chat.setChatName("Chat_" + remotePk);
                        chat.setDate(new Timestamp(dv));
                        chat.setLastMessageDate(new Timestamp(dv));                       ;
                        Calendar c = Calendar.getInstance(Locale.getDefault());
                        /**
                         * Now we got the identities registered in the device.
                         * To avoid nulls, I'll put default data in chat object
                         */
                        chat.setLocalActorPublicKey(chatManager.getNetworkServicePublicKey());
                        chat.setLocalActorType(PlatformComponentType.NETWORK_SERVICE);
                        if(chatSettings.getLocalPublicKey()!=null && chatSettings.getLocalPlatformComponentType()!=null)
                        {
                            chat.setLocalActorPublicKey(chatSettings.getLocalPublicKey());
                            chat.setLocalActorType(chatSettings.getLocalPlatformComponentType());//chatSettings.getLocalActorType()
                        }

                        //Revisar esto ya que cambio el mapa por el actor como tal
//                        HashMap<PlatformComponentType, String> identitiesMap=chatManager.getSelfIdentities();
//                        Set<PlatformComponentType> keySet=identitiesMap.keySet();
//                        for(PlatformComponentType key : keySet) {
//                            chat.setLocalActorPublicKey(identitiesMap.get(key));
//                            chat.setLocalActorType(key);
//                            break;
//                        }
                        //chat.setLocalActorPublicKey(chatManager.getNetworkServicePublicKey());
                        /**
                         * This case is when I got an unregistered contact, I'll set the
                         * LocalActorType as is defined in database
                         */
                        //chat.setLocalActorType(PlatformComponentType.ACTOR_ASSET_ISSUER);
                        Contact newContact = chatManager.getContactByContactId(
                                contactId);
                        PlatformComponentType remoteActorType = newContact.getRemoteActorType();
                        String remotePublicKey = newContact.getRemoteActorPublicKey();
                        //chat.setLocalActorType(PlatformComponentType.NETWORK_SERVICE);
                        //chat.setRemoteActorPublicKey(remotePk);
                        //chat.setRemoteActorType(remotePCT);
                        chat.setRemoteActorType(remoteActorType);
                        chat.setRemoteActorPublicKey(remotePublicKey);
                        chatManager.saveChat(chat);

                        message.setChatId(newChatId);
                        message.setMessageId(UUID.randomUUID());
                        message.setMessage(messageText);
                        message.setMessageDate(new Timestamp(dv));
                        message.setStatus(MessageStatus.CREATED);
                        message.setType(TypeMessage.OUTGOING);
                        message.setContactId(contactId);
                        chatManager.saveMessage(message);
                        //If everything goes OK, we save the chat in the fragment session.
                        chatSession.setData("whocallme", "chatlist");
                        chatSession.setData(
                                "contactid",
                                newContact
                                );
                        /**
                         * This chat was created, so, I will put chatWasCreate as true to avoid
                         * the multiple chats from this contact. Also I will put the chatId as
                         * newChatId
                         */
                        chatWasCreate = true;
                        chatId = newChatId;
                    }

                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setId(UUID.randomUUID());//dummy
                    chatMessage.setMessage(messageText);
                    //chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                    String S = new SimpleDateFormat("HH:mm").format(new Date());
                    chatMessage.setDate(S);
                    chatMessage.setMe(true);
                    messageET.setText("");
                    adapter = new ChatAdapter(getContext(), (chatHistory != null) ? chatHistory : new ArrayList<ChatMessage>());
                    messagesContainer.setAdapter(adapter);
                    displayMessage(chatMessage);


                } catch (CantSaveMessageException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (CantSaveChatException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (CantGetNetworkServicePublicKeyException e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                } catch (Exception e) {
                    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                }
            }
        });
/*
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                        try {
                            findMessage();
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });*/
    }

    private void loadDummyHistory() {

        if (loadDummyData) {

            chatHistory = new ArrayList<ChatMessage>();

            ChatMessage msg = new ChatMessage();
            msg.setId(UUID.randomUUID());
            msg.setMe(false);
            msg.setMessage("Hola");
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg);
            ChatMessage msg1 = new ChatMessage();
            msg1.setId(UUID.randomUUID());
            msg1.setMe(true);
            msg1.setMessage("como andas?");
            msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg1);
        }
        if (chatHistory == null) {
            chatHistory = new ArrayList<ChatMessage>();
        }

        adapter = new ChatAdapter(this.getContext(), (chatHistory != null) ? chatHistory : new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for (int i = 0; i < chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    public void displayMessage(ChatMessage message) {
        adapter.addItem(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    public void refreshEvents() {
        whatToDo();
        findMessage();
        scroll();
    }

    private void scroll() {
        if(adapter != null && adapter.getItemCount()!=0 && adapter.getItemCount()>0)
            messagesContainer.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    public void addLeftName(String leftName) {
        this.leftName = leftName;
    }

    public void addRightName(String rightName) {
        this.rightName = rightName;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    private void setChatHistory(ArrayList<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    private void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;
    }

    private void setModuleManager(ChatModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    private void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }

    private void setChatSession(ChatSession chatSession) {
        this.chatSession = chatSession;
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

    private void loadDummyHistory(boolean loadDummyData) {
        this.loadDummyData = loadDummyData;
    }

    public static class Builder {

        private Context context;
        private ViewGroup rootView;
        private ArrayList<ChatMessage> chatHistory;
        private ChatManager chatManager;
        private ChatModuleManager moduleManager;
        private ErrorManager errorManager;
        private ChatSession chatSession;
        private ChatPreferenceSettings chatSettings;
        private FermatSession appSession;
        private Toolbar toolbar;
        private boolean loadDummyData = false;
        private int background = -1;
        private float chatTextSize;
        private int chatTextColor;
        private float dateTextSize;
        private int dateTextColor;
        String rightName;
        String leftName;
        Button customButtom;
        EditText editText;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder insertInto(ViewGroup rootView) {
            this.rootView = rootView;
            return this;
        }

        public Builder addRightName(String rightName) {
            this.rightName = rightName;
            return this;
        }

        public Builder addLeftName(String leftName) {
            this.leftName = leftName;
            return this;
        }

        public void addCustomSendButtom(Button customButtom) {
            this.customButtom = customButtom;
        }

        public void addCustomEditText(EditText customEditText) {
            this.editText = customEditText;
        }

        public Builder setBackground(int background) {
            this.background = background;
            return this;
        }

        public Builder addChatHistory(ArrayList<ChatMessage> chatHistory) {
            this.chatHistory = chatHistory;
            return this;
        }

        public Builder addChatManager(ChatManager chatManager) {
            this.chatManager = chatManager;
            return this;
        }

        public Builder addModuleManager(ChatModuleManager moduleManager) {
            this.moduleManager = moduleManager;
            return this;
        }

        public Builder addErrorManager(ErrorManager errorManager) {
            this.errorManager = errorManager;
            return this;
        }

        public Builder addChatSession(ChatSession chatSession) {
            this.chatSession = chatSession;
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

        public void loadDummyData(boolean loadDummyData) {
            this.loadDummyData = loadDummyData;
        }

        public ChatAdapterView build() {
            ChatAdapterView chatView = new ChatAdapterView(context, chatHistory,
                    chatManager, moduleManager, errorManager, chatSession, appSession, background, toolbar, chatSettings);
            if (rootView != null) {
                chatView.setRootView(rootView);
            }
            if (chatHistory != null) {
                chatView.setChatHistory(chatHistory);
            }
            if (chatManager != null) {
                chatView.setChatManager(chatManager);
            }
            if (moduleManager != null) {
                chatView.setModuleManager(moduleManager);
            }
            if (errorManager != null) {
                chatView.setErrorManager(errorManager);
            }
            if (chatSettings != null) {
                chatView.setChatSettings(chatSettings);
            }
            if (chatSession != null) {
                chatView.setChatSession(chatSession);
            }
            if (appSession != null) {
                chatView.setAppSession(appSession);
            }
            if (leftName != null) {
                chatView.addLeftName(leftName);
            }
            if (rightName != null) {
                chatView.addRightName(rightName);
            }
            if (toolbar != null) {
                chatView.setToolbar(toolbar);
            }
            if (background != -1) {
                chatView.setBackground(background);
            }
            chatView.loadDummyHistory(loadDummyData);
            return chatView;
        }
    }

// extends ArrayAdapter {
//    ArrayList<String> datos=new ArrayList<String>();
//
//public ChatAdapterView(Context context,ArrayList datos) {
//    super(context, R.layout.listviewme, datos);
//    this.datos=datos;
//
//}
//
//            public View getView(int position, View convertView, ViewGroup parent) {
//                LayoutInflater inflater = LayoutInflater.from(getContext());
//                View item = inflater.inflate(R.layout.listviewme, null);
//
//                if(!datos.isEmpty()) {
//                    if (datos.get(position).split("@#@#")[0].equals("OUTGOING")) {
//                        item = inflater.inflate(R.layout.listviewme, null);
//                        TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
//                        //           TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
//                        System.out.println("*************DATALEFT:" + Arrays.toString(datos.toArray()));
//                        System.out.println("*************PositionLEFT:" + position);
//
//                        try {
//
//                            lblTitulo.setText(datos.get(position).split("@#@#")[1]);
//
//                        } catch (NullPointerException e) {
//                            System.out.print("Null on position:" + position);
//                }
//            } else {
//                item = inflater.inflate(R.layout.listviewyou, null);
//                TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
//       //         TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
//                System.out.println("*************DATARIGHT:" + Arrays.toString(datos.toArray()));
//                System.out.println("*************PositionRIGHT:" + position);
//
//        try {
//
//        lblTitulo.setText(datos.get(position).split("@#@#")[1]);
//
//        } catch (NullPointerException e) {
//        System.out.print("Null on position:" + position);
//        }
//        }
//        }
//        return(item);

//    }
//
//    public void refreshEvents(ArrayList<String> datos) {
//        this.datos=datos;
//
//        notifyDataSetChanged();
//
//    }


}