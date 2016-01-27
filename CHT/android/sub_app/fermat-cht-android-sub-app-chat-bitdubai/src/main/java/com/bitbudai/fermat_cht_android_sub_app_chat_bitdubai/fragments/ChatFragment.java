package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapterView;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.ChatMock;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.MessageMock;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.exceptions.CantRequestListException;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


//import android.widget.LinearLayout;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
//import com.bitdubai.fermat_cht_api.layer.chat_module.interfaces.ChatModuleManager;
//import com.bitdubai.fermat_cht_api.layer.platform_service.error_manager.interfaces.ErrorManager;

/**
 * Chat Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 * Update by Miguel Payarez on 15/01/2016 (android-module connection with send button)
 */

public class ChatFragment extends AbstractFermatFragment {//ActionBarActivity

    // Fermat Managers
    private ChatManager chatManager;
    private ChatModuleManager moduleManager;
    private ErrorManager errorManager;
    private SettingsManager<ChatSettings> settingsManager;
    private ChatSession chatSession;

    //New
/*    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager;
    @NeededAddonReference(platform = Platforms.PLUG_INS_PLATFORM, layer = Layers.PLATFORM_SERVICE, addon = Addons.EVENT_MANAGER)
    private EventManager eventManager2;*/
    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CHAT_NETWORK_SERVICE)
    private com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatManager networkservicechatmanager;
    int i = 4;
    boolean me;
    Integer newmessage = 0;
    String mensaje = "";
    ArrayList<String> historialmensaje = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;


    //Data
    List<String> chatmessages = new ArrayList<>();

    private EditText messageET;
    private ListView messagesContainer;
    public Button sendBtn;
    private ChatAdapter adapter;
    public ArrayList<ChatMessage> chatHistory;


    TextView linear_layout_send_form;


    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatSession = ((ChatSession) appSession);
            moduleManager = chatSession.getModuleManager();
            chatManager = moduleManager.getChatManager();
            //settingsManager = moduleManager.getSettingsManager();
            errorManager = appSession.getErrorManager();


        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//private void initControls() {}

        // Inflate the layout for this fragment
        final View layout = inflater.inflate(R.layout.chat, container, false);
        


        messagesContainer = (ListView) layout.findViewById(R.id.messagesContainer);
        messageET = (EditText) layout.findViewById(R.id.messageEdit);
        sendBtn = (Button) layout.findViewById(R.id.chatSendButton);
        mSwipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);

        final FermatTextView meLabel = (FermatTextView) layout.findViewById(R.id.meLbl);
        FermatTextView companionLabel = (FermatTextView) layout.findViewById(R.id.friendLabel);
        RelativeLayout contain = (RelativeLayout) layout.findViewById(R.id.container);
        companionLabel.setText("My Contact");// Hard Coded
      //  loadDummyHistory();// Hard Coded

        historialmensaje.add("yo##Hola estoy haciendo pruebas con la vista");
        historialmensaje.add("tu##Que bueno");
        historialmensaje.add("tu##Como lo pruebo");
        historialmensaje.add("yo##escribes yo seguido de 2 numerales y el mensaje");
        historialmensaje.add("yo##mensaje del otro lado igual pero colocando tu");
        historialmensaje.add("yo##por ultimo swipe down para lanzar un toast");
        ListView lstOpciones;
//new se coloco la variable me para identificar cuando sea yo quien envie el mensaje y actualice el textview correcto

        final ChatAdapterView adaptador =
                new ChatAdapterView(getActivity(), historialmensaje);

        lstOpciones = (ListView)layout.findViewById(R.id.messagesContainer);

        lstOpciones.setAdapter(adaptador);


        adaptador.refreshEvents(historialmensaje);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                try {
                    Chat testChat = new ChatMock();
                    testChat.setLocalActorPublicKey(networkservicechatmanager.getNetWorkServicePublicKey());
                    List<String> remotePublicKey = networkservicechatmanager.getRegisteredPubliKey();
                    testChat.setRemoteActorPublicKey(remotePublicKey.get(0));

                    Message testMessage = new MessageMock(UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"));
                    testMessage.setMessage(messageET.getText().toString());

                    chatManager.saveChat(testChat);

                    chatManager.saveMessage(testMessage);

                    mensaje = chatManager.getMessageByChatId((UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"))).getMessage();

                    historialmensaje.add(mensaje);

                    adaptador.refreshEvents(historialmensaje);
                    System.out.println("chatmanager.gettext:" + chatManager.getMessages().size());
                } catch (CantSaveChatException e) {
                    e.printStackTrace();
                } catch (CantSaveMessageException e) {
                    e.printStackTrace();
                } catch (CantGetMessageException e) {
                    e.printStackTrace();
                } catch (CantRequestListException e) {
                    e.printStackTrace();
                }


/*                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");

                displayMessage(chatMessage);*/
            }
        });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Despierta Kakaroto");
                        Toast.makeText(getActivity(), "Despierta Kakaroto", Toast.LENGTH_SHORT).show();
                        //new Estaba en el plugin root del middleware creo que no es necesario
/*                FermatEvent fermatEvent = eventManager.getNewEvent(EventType.INCOMING_CHAT);
                IncomingChat incomingChat = (IncomingChat) fermatEvent;
                incomingChat.setSource(EventSource.NETWORK_SERVICE_CHAT);
                incomingChat.setChatId(UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"));
                eventManager.raiseEvent(incomingChat);*/
                        try {
                            System.out.println("Threar UI corriendo");
                            if (chatManager.getMessages().size() > newmessage) {
                                me = false;
                                //              i = i + 1;
                                newmessage = newmessage + 1;
                                mensaje = chatManager.getMessageByChatId((UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"))).getMessage();
              //                  mensaje = chatManager.getMessageByChatId(chatManager.networkservicechatmanage)
                                //        historialmensaje.add("tu##"+mensaje);
                                //        datos.set(0, historialmensaje);
                                //        adaptador.refreshEvents(datos);

                            }
                        } catch (CantGetMessageException e) {
                            e.printStackTrace();
                        }


                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });



        return layout;


    }

    /*public void displayMessage(ChatMessage message) {
        adapter.add(message);
        //adapter.notifyDataSetChanged();
        scroll();
    }*/





    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){// Hard Coded

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);
        //adapter = new ChatAdapter(getActivity(), msg);
        //adapter.setFermatListEventListener(this);
        //recyclerView.setAdapter(adapter);
        //adapter = new ChatAdapter(getActivity());//,
        //messagesContainer.setAdapter((ListAdapter) msg);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            //displayMessage(message);
        }
    }
}
