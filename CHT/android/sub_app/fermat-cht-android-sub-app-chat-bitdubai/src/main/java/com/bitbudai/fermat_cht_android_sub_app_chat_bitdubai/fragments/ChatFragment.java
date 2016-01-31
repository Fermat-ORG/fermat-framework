package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetContactException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantGetMessageException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveChatException;
import com.bitdubai.fermat_cht_api.all_definition.exceptions.CantSaveMessageException;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Chat;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Contact;
import com.bitdubai.fermat_cht_api.layer.middleware.interfaces.Message;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.ChatMock;
import com.bitdubai.fermat_cht_api.layer.middleware.mocks.MessageMock;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    private EventManager eventManager2;
    @NeededPluginReference(platform = Platforms.CHAT_PLATFORM, layer = Layers.NETWORK_SERVICE, plugin = Plugins.CHAT_NETWORK_SERVICE)
    private com.bitdubai.fermat_cht_api.layer.network_service.chat.interfaces.ChatManager networkservicechatmanager;*/
    int i = 4;

    Integer newmessage = 0;
    String mensaje = "";
    ArrayList<String> historialmensaje = new ArrayList<>();
    SwipeRefreshLayout mSwipeRefreshLayout;
    Map<String,Contact>others=new HashMap<String,Contact>();
    Map<String,Contact>me=new HashMap<String,Contact>();;
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

    public void addContactDirection(List<Contact> contactdirection){

        for (int i=0;i<contactdirection.size();i++){
            others.put(contactdirection.get(i).getRemoteName(), contactdirection.get(i));
            System.out.println("\n\nADDCONTACTDIRECTION("+i+"):\n\n"+contactdirection.get(i).getRemoteName()+" ?? "+contactdirection.get(i).getAlias()
                    +" ?? "+contactdirection.get(i).getRemoteActorPublicKey().toString()+" ?? "+contactdirection.get(i).getContactId().toString()+" ?? "
                    +contactdirection.get(i).getRemoteActorType().toString()+" ?? "+contactdirection.get(i).getCreationDate());
        }


    }

    public boolean whoImy(String user){
        boolean correct=false;
        try {
            //TODO: fix this
            /*for (int i=0;i<chatManager.discoverActorsRegistered().size();i++){
                System.out.println("\n\nwhoImy("+i+"):\n\n"+chatManager.discoverActorsRegistered().get(i).getRemoteName()+" ?? "
                        +chatManager.discoverActorsRegistered().get(i).getAlias() +" ?? "+chatManager.discoverActorsRegistered().get(i).getRemoteActorPublicKey().toString()
                        +" ?? "+chatManager.discoverActorsRegistered().get(i).getContactId().toString()+" ?? "
                        +chatManager.discoverActorsRegistered().get(i).getRemoteActorType().toString()+" ?? "
                        +chatManager.discoverActorsRegistered().get(i).getCreationDate().toString());
                if(chatManager.discoverActorsRegistered().get(i).getRemoteName().equals("user"+user)){
                    correct=true;
                    me.put("yo",chatManager.discoverActorsRegistered().get(i));
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

return correct;
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
        ListView lstOpciones;
//new se coloco la variable me para identificar cuando sea yo quien envie el mensaje y actualice el textview correcto

        final ChatAdapterView adaptador =
                new ChatAdapterView(getActivity(), historialmensaje);

        lstOpciones = (ListView)layout.findViewById(R.id.messagesContainer);

        lstOpciones.setAdapter(adaptador);


        adaptador.refreshEvents(historialmensaje);



        if(!whoImy("PAYAREZ")){
            Toast.makeText(getActivity(),"OOOPs...El usuario no existe",Toast.LENGTH_LONG).show();
        }


        //CODE FOR ALERT DIALOG

/*          *//* Alert Dialog Code Start*//*
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Usuario de conexion"); //Set Alert dialog title here
        alert.setMessage("Escriba su usuario segun su conexion"); //Message here

        // Set an EditText view to get user input
        final EditText input = new EditText(getActivity());
        alert.setView(input);

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.

                String srt = input.getEditableText().toString();
                while(!whoImy(srt)){
                    if(!whoImy(srt)){
                        Toast.makeText(getActivity(),"OOOPs...El usuario no existe",Toast.LENGTH_LONG).show();

                    }

                }
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
  *//*      alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        })*//*; //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
       *//* Alert Dialog Code End*/




        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                try {
                    Chat testChat = new ChatMock();
                    //Cambio ya que trabajamos con pk de las otras app
               //    testChat.setLocalActorPublicKey(Fragments.CHT_CHAT_OPEM_MESSAGE_LIST_FRAGMENT.getKey());
                    testChat.setLocalActorPublicKey(me.get(0).getRemoteActorPublicKey());
               //     chatManager.discoverActorsRegistered()
                   if(!(others.get("userPAYAREZ")==null)) {
                       Toast.makeText(getActivity(), "usuario"+others.get("userPAYAREZ").getRemoteName(), Toast.LENGTH_SHORT).show();

                       testChat.setRemoteActorPublicKey(others.get("userPAYAREZ").getRemoteActorPublicKey());
                       Message testMessage = new MessageMock(others.get("userPAYAREZ").getContactId());
                       testMessage.setMessage(messageET.getText().toString());

                       chatManager.saveChat(testChat);
                       chatManager.saveMessage(testMessage);

                       mensaje = chatManager.getMessageByChatId((others.get("userPAYAREZ").getContactId())).getMessage();

                       historialmensaje.add("yo##" + mensaje);

                       adaptador.refreshEvents(historialmensaje);
              //         System.out.println("chatmanager.gettext:" + chatManager.getMessages().size());
                   }else{
                       Toast.makeText(getActivity(), "usuario No encontrado", Toast.LENGTH_SHORT).show();
                   }
                } catch (CantSaveChatException e) {
                    e.printStackTrace();
                } catch (CantSaveMessageException e) {
                    e.printStackTrace();
                } catch (CantGetMessageException e) {
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

                        try {
                            System.out.println("Threar UI corriendo");
                            //TODO: fix this
                            //addContactDirection(chatManager.discoverActorsRegistered());
                            System.out.println("\n\nMENSAJES EN CON GETMESSAGES:"+chatManager.getMessages().size()+"\n\n");
                            for (int i=0;i<chatManager.getMessages().size();i++){
                                System.out.println("MENSAJE("+i+"):"+chatManager.getMessages().size());

                            }
                            if (chatManager.getMessages().size() > newmessage) {

                                newmessage = newmessage + 1;

                                mensaje =chatManager.getMessageByChatId((others.get("userPRUEBA").getContactId())).getMessage();
              //                  mensaje = chatManager.getMessageByChatId(chatManager.networkservicechatmanage)
                                historialmensaje.add("tu##"+mensaje);
                                //        datos.set(0, historialmensaje);
                                adaptador.refreshEvents(historialmensaje);

                            }else{
                                Toast.makeText(getActivity(), "No new message sorry", Toast.LENGTH_SHORT).show();

                            }
                        } catch (CantGetMessageException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            //TODO: fix this
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
        msg.setId("1");
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId("2");
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
