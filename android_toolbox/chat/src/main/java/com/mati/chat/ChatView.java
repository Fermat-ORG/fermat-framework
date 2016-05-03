package com.mati.chat;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mati on 2016.02.03..
 */
public class ChatView extends LinearLayout{


    private RecyclerView messagesContainer;
    private Button sendBtn;
    private ChatAdapter2 adapter;
    private ArrayList<ChatMessage> chatHistory;
    private EditText messageET;
    private ViewGroup rootView;
    private boolean loadDummyData = false;

    public ChatView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        addView(inflater.inflate(R.layout.activity_chat_2,(rootView!=null)?rootView:null));
        initControls();

    }

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void initControls() {
        messagesContainer = (RecyclerView) findViewById(R.id.messagesContainer);
        messagesContainer.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("El otro");// Hard Coded
        loadDummyHistory();

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
    }

    private void loadDummyHistory(){

        if(loadDummyData) {

            chatHistory = new ArrayList<ChatMessage>();

            ChatMessage msg = new ChatMessage();
            msg.setId(1);
            msg.setMe(false);
            msg.setMessage("Hola");
            msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg);
            ChatMessage msg1 = new ChatMessage();
            msg1.setId(2);
            msg1.setMe(false);
            msg1.setMessage("como andas?");
            msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
            chatHistory.add(msg1);
        }
        if(chatHistory==null){
            chatHistory = new ArrayList<ChatMessage>();
        }

        adapter = new ChatAdapter2(this.getContext(),(chatHistory!=null)?chatHistory:new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }

    public void displayMessage(ChatMessage message) {
        adapter.addItem(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.scrollToPosition(adapter.getItemCount() - 1);
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    private void setChatHistory(ArrayList<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }
    private void loadDummyHistory(boolean loadDummyData) {
        this.loadDummyData = loadDummyData;
    }


    public static class Builder{

        private Context context;
        private ViewGroup rootView;
        private ArrayList<ChatMessage> chatHistory;
        private boolean loadDummyData = false;
        private Drawable background;
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

        public Builder insertInto(ViewGroup view){
            rootView = view;
            return this;
        }

        public void addRightName(String rightName) {
            this.rightName = rightName;
        }

        public void addLeftName(String leftName) {
            this.leftName = leftName;
        }

        public void addCustomSendButtom(Button customButtom) {
            this.customButtom = customButtom;
        }

        public void addCustomEditText(EditText customEditText) {
            this.editText = customEditText;
        }

        public void setBackground(Drawable background){
            this.background = background;
        }

        public void addChatHistory(ArrayList<ChatMessage> chatHistory) {
            this.chatHistory = chatHistory;
        }

        public void lLoadDummyData(boolean loadDummyData) {
            this.loadDummyData = loadDummyData;
        }

        public ChatView build() {
            ChatView chatView = new ChatView(context);
            if (rootView != null) {
                chatView.setRootView(rootView);
            }
            if(chatHistory!=null){
                chatView.setChatHistory(chatHistory);
            }
            chatView.loadDummyHistory(loadDummyData);
            return chatView;
        }


    }




}
