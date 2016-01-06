package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
//import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ListView;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatAdapter;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.ui.inflater.ViewInflater;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatMessage;
//import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
//import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
//import com.bitdubai.fermat_cht_api.layer.chat_module.interfaces.ChatModuleManager;
//import com.bitdubai.fermat_cht_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;
import java.util.Date;
import java.text.DateFormat;
/**
 * Chat Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 30/12/15.
 * @version 1.0
 */

public class ChatFragment extends AbstractFermatFragment  {//ActionBarActivity

    // Fermat Managers
    //private ChatModuleManager moduleManager;
    //private ErrorManager errorManager;

    //Data
    List<String> chatmessages =  new ArrayList<>();

    private EditText messageET;
    private ListView messagesContainer;
    public Button sendBtn;
    private ChatAdapter adapter;
    public ArrayList<ChatMessage> chatHistory;

    public ChatFragment() {}
    public static ChatFragment newInstance() {return new ChatFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.chat);
        //initControls();
        /*try {
            moduleManager = ((CashMoneyWalletSession) appSession).getModuleManager();
            errorManager = appSession.getErrorManager();
        } catch (Exception e) {
            if (errorManager != null)
                errorManager.reportUnexpectedWalletException(Wallets.CSH_CASH_WALLET, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, e);
        }*/

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//private void initControls() {}

        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.chat, container, false);

        messagesContainer = (ListView) layout.findViewById(R.id.messagesContainer);
        messageET = (EditText) layout.findViewById(R.id.messageEdit);
        sendBtn = (Button) layout.findViewById(R.id.chatSendButton);

        FermatTextView meLabel = (FermatTextView) layout.findViewById(R.id.meLbl);
        FermatTextView companionLabel = (FermatTextView) layout.findViewById(R.id.friendLabel);
        RelativeLayout contain = (RelativeLayout) layout.findViewById(R.id.container);
        companionLabel.setText("My Contact");// Hard Coded
        loadDummyHistory();// Hard Coded

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);

                messageET.setText("");

                displayMessage(chatMessage);
            }
        });
        return layout;
    }



    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        //adapter.notifyDataSetChanged();
        scroll();
    }

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
        adapter = new ChatAdapter(getActivity());//,
        messagesContainer.setAdapter((ListAdapter) adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
}
