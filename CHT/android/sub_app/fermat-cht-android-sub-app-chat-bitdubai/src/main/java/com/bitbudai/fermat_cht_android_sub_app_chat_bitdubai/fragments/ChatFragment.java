package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapter;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapterView;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.settings.ChatSettings;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Parameters;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
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

    //Data
    List<String> chatmessages =  new ArrayList<>();

    private EditText messageET;
    private ListView messagesContainer;
    public Button sendBtn;
    private ChatAdapter adapter;
    public ArrayList<ChatMessage> chatHistory;


    TextView linear_layout_send_form;

    //CHAT
    String[] itemname ={
            "Safari",
            "Chrone",
            "Opera",
            "FireFox",

    };
    //


    public static ChatFragment newInstance() {return new ChatFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            chatSession = ((ChatSession) appSession);
            moduleManager = chatSession.getModuleManager();
            chatManager=moduleManager.getChatManager();
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
        View layout = inflater.inflate(R.layout.chat, container, false);
        


        messagesContainer = (ListView) layout.findViewById(R.id.messagesContainer);
        messageET = (EditText) layout.findViewById(R.id.messageEdit);
        sendBtn = (Button) layout.findViewById(R.id.chatSendButton);

        final FermatTextView meLabel = (FermatTextView) layout.findViewById(R.id.meLbl);
        FermatTextView companionLabel = (FermatTextView) layout.findViewById(R.id.friendLabel);
        RelativeLayout contain = (RelativeLayout) layout.findViewById(R.id.container);
        companionLabel.setText("My Contact");// Hard Coded
      //  loadDummyHistory();// Hard Coded


        ListView lstOpciones;
        final Parameters[] datos =
                new Parameters[]{
                        new Parameters("Jose", ""),
                        new Parameters("Miguel", ""),

                };


        final ChatAdapterView adaptador =
                new ChatAdapterView(getActivity(), datos);

        lstOpciones = (ListView)layout.findViewById(R.id.messagesContainer);

        lstOpciones.setAdapter(adaptador);




        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                String mensaje="";
                Chat testChat = new ChatMock();
                Message testMessage = new MessageMock(UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"));
                testMessage.setMessage(messageET.getText().toString());
                try {

                    chatManager.saveChat(testChat);
                    chatManager.saveMessage(testMessage);
                    mensaje=chatManager.getMessageByChatId((UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"))).getMessage();
                    datos[0]=new Parameters("Jose",mensaje);

                    adaptador.refreshEvents(datos);
         //           meLabel.setText(chatManager.getMessageByChatId((UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"))).getMessage());
         //           linear_layout_send_form.setText(chatManager.getMessageByChatId((UUID.fromString("52d7fab8-a423-458f-bcc9-49cdb3e9ba8f"))).getMessage());
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
