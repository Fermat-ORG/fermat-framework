package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.models.ChatsList;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

//import android.widget.ImageView;

/**
 * ChatHolder ViewHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */
public class ChatsListHolder extends FermatViewHolder {

    public FermatTextView message_icon_text;//TextView
    public QuickContactBadge message_icon_image;//TextView
    public FermatTextView chat_notification;
    public FermatTextView firstLastName;
    public FermatTextView lastMessage;//TextView
    public FermatTextView contactItemTime;//TextView
    public ProgressBar chat_list_progress_bar;
    public FermatTextView no_chats;

    private static ChatsList chats;

    /**
     * Constructor
     *
     * @param itemView
     */
    public ChatsListHolder(View itemView) {
        super(itemView);
        message_icon_text = (FermatTextView) itemView.findViewById(R.id.message_icon_text);
        message_icon_image = (QuickContactBadge) itemView.findViewById(R.id.message_icon_image);
        firstLastName = (FermatTextView) itemView.findViewById(R.id.firstLastName);
        lastMessage = (FermatTextView) itemView.findViewById(R.id.lastMessage);
        contactItemTime = (FermatTextView) itemView.findViewById(R.id.contactItemTime);
        chat_notification = (FermatTextView) itemView.findViewById(R.id.chat_notification);
        chat_list_progress_bar = (ProgressBar) itemView.findViewById(R.id.chat_list_progress_bar);
        no_chats = (FermatTextView) itemView.findViewById(R.id.no_chats);
    }

    public static ChatsList getChat() {
        return chats;
    }

    public static void setChats(ChatsList chats1) {
        chats = chats1;
    }
}