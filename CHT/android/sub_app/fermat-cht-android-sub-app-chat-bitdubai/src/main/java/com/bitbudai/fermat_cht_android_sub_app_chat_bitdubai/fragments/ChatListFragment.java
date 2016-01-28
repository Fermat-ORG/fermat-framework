package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters.ChatListAdapter;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;


/**
 * Chat List Fragment
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 19/01/16
 * @version 1.0
 * Upd
 *
 */

public class ChatListFragment extends AbstractFermatFragment{

    /*boolean dualPane;
    private static int currentCheckPosition = 0;
    private ChatListAdapter adapter;
    private ProgressBar progressBar;
    private static ChatsList chats;
    private LinearLayout layout;
    private ProgressDialog mProgressDialog;
    private boolean fragmentStopped = false;

    private TextView noChatsMessage;

    private long clickedId;*/


    ListView list;
 //   static HashMap<Integer,List<String>> chatinfo=new HashMap<Integer,List<String>>();
   // static Integer[] imgid=new Integer[6];
    String[] chatinfo={"GABRIEL@@hola como estas##24/10/2015",
         "MIGUEL@@chao nos vemos##25/10/2015",
         "FRANKLIN@@A la victoria siempre##24/12/2015",
         "MANUEL@@Tigres Campeon##24/10/2015",
         "JOSE@@gracias totales##24/10/2015",
         "LUIS@@Fermat Rules##20/10/2015"};   //work
    Integer[] imgid={R.drawable.ken,
    R.drawable.sas,
    R.drawable.koj,
    R.drawable.veg,
    R.drawable.ren,
    R.drawable.pat
    };

    static void initchatinfo(){
     //   chatinfo.put(0, Arrays.asList("Miguel", "Que paso?", "12/09/2007"));
        //imgid[0]=R.drawable.ken;
    }
    public static ChatListFragment newInstance() {
        initchatinfo();
        return new ChatListFragment();}

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//System.out.println("**********LISTA:"+chatinfo.get(0).get(0)+" - "+chatinfo.get(0).get(1)+" - "+chatinfo.get(0).get(2));
     //   setContentView(getActivity());
        View layout = inflater.inflate(R.layout.chats_list_fragment, container, false);

        ChatListAdapter adapter=new ChatListAdapter(getActivity(), chatinfo, imgid);
        list=(ListView)layout.findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem= chatinfo[position];
                Toast.makeText(getActivity(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });

        return layout;
    }







//mig chance
    /*   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new ChatListAdapter(getActivity());
        layout = (LinearLayout) getActivity().findViewById(R.id.fragment_layout);
        noChatsMessage = (TextView) getActivity().findViewById(R.id.no_chats);
        return inflater.inflate(R.layout.chats_list_fragment, null);
    }*/
 //

//mig chance
/*    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);

        noChatsMessage = (TextView) getActivity().findViewById(R.id.no_chats);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.findFragmentById(R.id.messages);
        if (manager.findFragmentById(R.id.messages) != null) {
            layout.setVisibility(View.INVISIBLE);
        } else {
            layout.setVisibility(View.VISIBLE);
        }

        progressBar = (ProgressBar) getActivity().findViewById(R.id.chat_list_progress_bar);

        //getChatsList(Const.CHATS_LIST_OFFSET, Const.CHATS_LIST_LIMIT);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);*/
    //
        //fab.attachToListView(getListView());
        //fab.setColorPressedResId(R.color.background_floating_button_pressed);
        //fab.setColorNormalResId(R.color.background_floating_button);
        //fab.setShadow(true);

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TransparentActivity.class);
                intent.putExtra("choice", Const.CONTACT_LIST_FRAGMENT);
                intent.putExtra("destination", getActivity().getString(R.string.chat_list));
                startActivityForResult(intent, Const.REQUEST_CODE_NEW_MESSAGE);
            }
        });
        setListAdapter(adapter);*/
//mig chance
  /*      View detailsFrame = getActivity().findViewById(R.id.messages);
        dualPane = detailsFrame != null
                && detailsFrame.getVisibility() == View.VISIBLE;
        if (savedState != null) {
            currentCheckPosition = savedState.getInt("curChoice", 0);
        }
        if (dualPane) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }*/
//
         /*long id = ((ChatActivity) getActivity()).getIntentChatId();
       if (id != 0) {
            clickedId = id;
            int position = getChatPosition(id);
            showMessages(position);
        }*/
 //   }
//mig
/*    public void setChatsList(final ChatsList chats1) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        chats = chats1;
        //adapter.clear();
        //adapter.addAll(chats.chats);
    }*/
//
    /*public void getChatsList(int offset, int limit) {
        new ApiClient<>(new TdApi.GetChats(offset, limit), new ChatsHandler(), new ApiClient.OnApiResultHandler() {
            @Override
            public void onApiResult(BaseHandler output) {
                if (output == null) {
                    getChatsList(0, 200);
                } else {
                    if (output.getHandlerId() == ChatsHandler.HANDLER_ID) {
                        TdApi.Chats receivedChats = (TdApi.Chats) output.getResponse();
                        if (receivedChats.chats.length == 0) {
                            noChatsMessage.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        } else {
                            noChatsMessage.setVisibility(View.GONE);
                            setChatsList(receivedChats);
                            UserInfoHolder.addUsersToMap(receivedChats);
                            MessagesFragmentHolder.setChats(receivedChats);
                        }
                    }
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }*/
//mig chance
/*    public ChatsList getChat(long id) {
       // if (chats == null *//*|| chats.chats.length == 0*//*) {
        *//*    chats = ChatsListHolder.getChats();
        }
        if (id != 0) {
            clickedId = id;
            getListView().setSelection(getChatPosition(clickedId));
        }
        for (int i = 0; i < chats.chats.length; i++) {
            if (chats.chats[i].id == clickedId) {
                return chats.chats[i];
            }
        }
        if (chats.chats.length == 1) {
            return chats.chats[0];
        } else {
            return chats.chats[currentCheckPosition];
        }*//*
        return null;
    }*/
//
    /*public int getChatPosition(long id) {
        if (chats == null) {
            chats = MessagesFragmentHolder.getChats();
        }
        for (int i = 0; i < chats.chats.length; i++) {
            if (chats.chats[i].id == id) {
                return i;
            }
        }
        return Const.CHAT_NOT_FOUND;
    }*/

   /* public void setAdapterFilter(String filter) {
        if (chats != null) {
            if (filter.isEmpty()) {
                adapter.clear();
                adapter.addAll(chats.chats);
            } else {
                List<TdApi.Chat> list = new ArrayList<>();
                for (int i = 0; i < chats.chats.length; i++) {
                    String name;
                    String messageText = "";
                    TdApi.ChatInfo info = chats.chats[i].type;
                    if (info.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
                        TdApi.PrivateChatInfo privateInfo = (TdApi.PrivateChatInfo) info;
                        name = privateInfo.user.firstName + " " + privateInfo.user.lastName;
                    } else {
                        TdApi.GroupChatInfo groupInfo = (TdApi.GroupChatInfo) info;
                        name = groupInfo.groupChat.title;
                    }
                    TdApi.MessageContent message = chats.chats[i].topMessage.message;
                    if (message.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
                        TdApi.MessageText textMessage = (TdApi.MessageText) message;
                        messageText = textMessage.text;
                    }
                    if (name.toLowerCase().contains(filter.toLowerCase()) || messageText.toLowerCase().contains(filter.toLowerCase())) {
                        list.add(chats.chats[i]);
                    }
                    adapter.clear();
                    adapter.addAll(list);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }*/
//mig chanve
/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", currentCheckPosition);
    }*/
//
    /*@Override
    public void onListItemClick(ListView l, View v, int pos, long id) {
        TdApi.Chat selectedItem = adapter.getItem(pos);
        clickedId = selectedItem.id;
        showMessages(pos);
    }*/

    /*void showMessages(int index) {
        currentCheckPosition = index;
        if (getFragmentManager() != null) {
            FragmentTransaction ft
                    = getFragmentManager().beginTransaction();
            getListView().setItemChecked(index, true);
            MessagesFragment messagesFragment = new MessagesFragment();
            if (!fragmentStopped) {
                ft.replace(R.id.messages, messagesFragment);
                ft.commit();
            }
            layout.setVisibility(View.INVISIBLE);
        }
    }*/
//mig chance
/*    public void openChat(long resultId) {
        clickedId = resultId;
        *//*int position = getChatPosition(resultId);
        if (position == Const.CHAT_NOT_FOUND) {
            newPrivateChat(resultId);
        } else {
            showMessages(position);
        }*//*
    }*/
//
   /* private void newPrivateChat(final long userId) {
        getActivity().setRequestedOrientation(getResources().getConfiguration().orientation);
        mProgressDialog = ProgressDialog.show(getActivity(), getActivity().getString(R.string.loading),
                getActivity().getString(R.string.please_wait), true, false);
        ApiHelper.createPrivateChat(userId);
        new ApiClient<>(new TdApi.GetChat(userId), new ChatHandler(), new ApiClient.OnApiResultHandler() {
            @Override
            public void onApiResult(BaseHandler output) {
                if (output.getHandlerId() == ChatHandler.HANDLER_ID) {
                    TdApi.Chat chat = (TdApi.Chat) output.getResponse();
                    clickedId = chat.id;
                    addChatToChatsArray(chat);
                    adapter.clear();
                    adapter.addAll(chats.chats);
                    showMessages(adapter.getPosition(chat));
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        try {
                            mProgressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
                }
            }
        }).executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }*/

   /* private void addChatToChatsArray(TdApi.Chat chat) {
        TdApi.Chat[] newChatArray = new TdApi.Chat[chats.chats.length + 1];
        newChatArray[0] = chat;
        System.arraycopy(chats.chats, 0, newChatArray, 1, chats.chats.length);
        chats = new TdApi.Chats(newChatArray);
    }*/

   /* public void updateChat(long chatId, int unread, int lastRead) {
        boolean updated = false;
        for (int i = 0; i < adapter.getCount(); i++) {
            TdApi.Chat chat = adapter.getItem(i);
            if (chat.id == chatId) {
                adapter.getItem(i).unreadCount = unread;
                adapter.getItem(i).lastReadInboxMessageId = lastRead;
                updated = true;
            }
        }
        if (!updated) {
            getChatsList(Const.CHATS_LIST_OFFSET, Const.CHATS_LIST_LIMIT);
        }
        adapter.notifyDataSetChanged();
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_NEW_MESSAGE && resultCode == Activity.RESULT_OK) {
            long resultId = data.getLongExtra("id", 0);
            openChat(resultId);
        }
    }*/

    //mig change
 /*   @Override
    public void onStop() {
        super.onStop();
        fragmentStopped = true;
    }*/
    //
    /*@Override
    public void onResume() {
        fragmentStopped = false;
        if (adapter.getCount() == 0) {
            getChatsList(Const.CHATS_LIST_OFFSET, Const.CHATS_LIST_LIMIT);
        }
        super.onResume();
    }*/
}
