package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.fragments.ProfileListFragment;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.sessions.ChatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.FermatSession;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatManager;
import com.bitdubai.fermat_cht_api.layer.sup_app_module.interfaces.ChatModuleManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import java.util.ArrayList;

/**
 * Profile List Adapter
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 03/03/16.
 * @version 1.0
 *
 */
public class ProfileListAdapter extends ArrayAdapter<String> {//public class ChatListAdapter extends FermatAdapter<ChatsList, ChatHolder> {//ChatFactory

    //List<ContactList> contactsList = new ArrayList<>();
    ArrayList<String> profileinfo=new ArrayList<>();
    ArrayList<Bitmap> profileicon=new ArrayList<>();
    ArrayList<String> profileid=new ArrayList<>();
    private ChatManager chatManager;
    private FermatSession appSession;
    private ErrorManager errorManager;
    private ChatModuleManager moduleManager;
    private ChatSession chatSession;
    private ProfileListFragment profilesListFragment;
    private Context context;
    private Context mContext;
    ImageView imagen;
    TextView contactname;
    int position;
    private AdapterCallback mAdapterCallback;
    Typeface tf;

    public ProfileListAdapter(Context context, ArrayList profileinfo, ArrayList profileicon, ArrayList profileid,
                              ChatManager chatManager, ChatModuleManager moduleManager,
                              ErrorManager errorManager, ChatSession chatSession, FermatSession appSession, AdapterCallback mAdapterCallback) {
        super(context, R.layout.profile_list_item, profileinfo);
        //tf = Typeface.createFromAsset(context.getAssets(), "fonts/HelveticaNeue Medium.ttf");
        this.profileinfo = profileinfo;
        this.profileicon = profileicon;
        this.profileid = profileid;
        this.chatManager=chatManager;
        this.moduleManager=moduleManager;
        this.errorManager=errorManager;
        this.chatSession=chatSession;
        this.appSession=appSession;
        this.mContext=context;
        try {
            this.mAdapterCallback = mAdapterCallback;
        }catch (Exception e)
        {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT,UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT,e);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.profile_list_item, null, true);
        try {
            imagen = (ImageView) item.findViewById(R.id.icon);//imagen.setImageResource(contacticon.get(position));//contacticon[position]);
            imagen.setImageBitmap(getRoundedShape(profileicon.get(position), 400));//imagen.setImageBitmap(getRoundedShape(decodeFile(getContext(), contacticon.get(position)), 300));

            contactname = (TextView) item.findViewById(R.id.text1);
            contactname.setText(profileinfo.get(position));
            //contactname.setTypeface(tf, Typeface.NORMAL);

            final int pos=position;
            imagen.setOnClickListener(new View.OnClickListener() {
               // int pos = position;
                @Override
                public void onClick(View v) {
                    try {
                        //TODO:Cardozo revisar esta logica ya no aplica, esto viene de un metodo nuevo que lo buscara del module del actor connections//chatManager.getChatUserIdentities();
                            appSession.setData(ChatSession.PROFILE_DATA, null);//chatManager.getChatUserIdentity(profileid.get(pos)));
                            mAdapterCallback.onMethodCallback();//solution to access to changeactivity. j
                        //} catch (CantGetChatUserIdentityException e) {
                        //    errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        } catch (Exception e) {
                            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        }
                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedSubAppException(SubApps.CHT_CHAT, UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        }
        return item;
    }

    public static interface AdapterCallback {
        void onMethodCallback();
    }

    public void refreshEvents(ArrayList profileinfo, ArrayList profileicon, ArrayList profileid) {
        this.profileinfo=profileinfo;
        this.profileicon=profileicon;
        this.profileid=profileid;
        notifyDataSetChanged();
    }

    public static Bitmap decodeFile(Context context,int resId) {
        // decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resId, o);
        // Find the correct scale value. It should be the power of 2.
        final int REQUIRED_SIZE = 300;
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

//    @Override
//    protected ChatHolder createHolder(View itemView, int type) {
//        return new ChatHolder(itemView);
//    }
//
//    protected int getCardViewResource() {return R.layout.chats_item;  }
//
//    @Override
//    protected void bindHolder(ChatHolder holder, ChatsList data, int position) {
//        View convertView = getView();
//        /*if (convertView == null) {
//            convertView = inflater.inflate(R.layout.chat_list_item, parent, false);
//        }*/
//        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        if (data == null) {
//            convertView = vi.inflate(R.layout.chat_list_item, null);
//            holder = createHolder(convertView, position);
//            convertView.setTag(holder);
//        } else {
//            holder = (ChatHolder) convertView.getTag();
//        }
//
//        //holder.message_icon_text.setText(data.getId());
//       /* holder.firstLastName.setText(data.getName());
//        holder.lastMessage.setText(data.getLastMessage());
//        holder.contactItemTime.setText(data.getDate());*/
//
//    }
//
//    public View getView() {
//
//        View convertView;
//        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = vi.inflate(R.layout.chats_item, null);
//
//        TextView icon = (TextView) convertView.findViewById(R.id.message_icon_text);
//        TextView firstLastName = (TextView) convertView.findViewById(R.id.firstLastName);
//        TextView lastMessage = (TextView) convertView.findViewById(R.id.lastMessage);
//        TextView time = (TextView) convertView.findViewById(R.id.contactItemTime);
//        TextView notify = (TextView) convertView.findViewById(R.id.chat_notification);
//
//        final ImageView imageIcon = (ImageView) convertView.findViewById(R.id.message_icon_image);
//
//        imageIcon.setImageResource(R.drawable.cht_ic_placeholder);
//        lastMessage.setText("");
//        icon.setText("");
//
//        Utils.verifySetBackground(icon, null);
///*
//        TdApi.Chat item = getItem(position);
//        TdApi.ChatInfo info = item.type;
//        TdApi.MessageText text = null;
//        TdApi.Message message = item.topMessage;
//        long timeMls = (long) message.date;
//        Date date = new Date(timeMls * 1000);
//        if (message.message instanceof TdApi.MessageText) {
//            text = (TdApi.MessageText) message.message;
//            lastMessage.setTextColor(Color.BLACK);
//            lastMessage.setText(text.textWithSmilesAndUserRefs);
//        } else {
//            lastMessage.setTextColor(getContext().getResources().getColor(R.color.content_text_color));
//            if (message.message instanceof TdApi.MessagePhoto) {
//                lastMessage.setText(R.string.message_photo);
//            }
//            if (message.message instanceof TdApi.MessageAudio) {
//                lastMessage.setText(R.string.message_audio);
//            }
//            if (message.message instanceof TdApi.MessageContact) {
//                lastMessage.setText(R.string.message_contact);
//            }
//            if (message.message instanceof TdApi.MessageDocument) {
//                lastMessage.setText(R.string.message_document);
//            }
//            if (message.message instanceof TdApi.MessageGeoPoint) {
//                lastMessage.setText(R.string.message_geopoint);
//            }
//            if (message.message instanceof TdApi.MessageSticker) {
//                lastMessage.setText(R.string.message_sticker);
//            }
//            if (message.message instanceof TdApi.MessageVideo) {
//                lastMessage.setText(R.string.message_video);
//            }
//            if (message.message instanceof TdApi.MessageUnsupported) {
//                lastMessage.setText(R.string.message_unknown);
//            }
//        }
//        TdApi.File file = null;
//        long chatId = item.id;
//        String userFirstName = "";
//        String userLastName = "";
//        if (info.getConstructor() == TdApi.PrivateChatInfo.CONSTRUCTOR) {
//            TdApi.PrivateChatInfo privateChatInfo = (TdApi.PrivateChatInfo) info;
//            TdApi.User chatUser = privateChatInfo.user;
//            file = chatUser.photoBig;
//            userFirstName = privateChatInfo.user.firstName;
//            userLastName = privateChatInfo.user.lastName;
//        }
//        if (info.getConstructor() == TdApi.GroupChatInfo.CONSTRUCTOR) {
//            TdApi.GroupChatInfo groupChatInfo = (TdApi.GroupChatInfo) info;
//            file = groupChatInfo.groupChat.photoBig;
//            userFirstName = groupChatInfo.groupChat.title;
//            userLastName = "";
//        }
//        if (item.unreadCount != 0) {
//            notify.setText(String.valueOf(item.unreadCount));
//            Utils.verifySetBackground(notify, Utils.getShapeDrawable(R.dimen.chat_list_item_notification_size, getContext().getResources().getColor(R.color.message_notify)));
//        } else {
//            Utils.verifySetBackground(notify, null);
//            notify.setText("");
//        }
//        Utils.setIcon(file, (int) chatId, userFirstName, userLastName, imageIcon, icon, (Activity) getContext());
//        firstLastName.setText(userFirstName + " " + userLastName);
//        time.setText(Utils.getDateFormat(Const.TIME_PATTERN).format(date));
//*/
//        return convertView;
//    }
//
//    public void add(ChatsList chats) {
//        chatsList.add(chats);
//    }
}