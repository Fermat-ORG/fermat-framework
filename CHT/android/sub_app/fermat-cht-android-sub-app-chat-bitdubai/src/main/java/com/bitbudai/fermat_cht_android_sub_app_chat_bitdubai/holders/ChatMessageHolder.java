package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * ChatMessageHolder ViewHolder
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 05/01/15.
 * @version 1.0
 */
public class ChatMessageHolder extends FermatViewHolder {

    public TextView txtMessage;
    public TextView txtInfo;
    public ImageView tickstatusimage;
    public LinearLayout content;
    public LinearLayout contentWithBG;

    /**
     * Constructor
     *
     * @param itemView
     */
    public ChatMessageHolder(View itemView) {
        super(itemView);

        txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
        content = (LinearLayout) itemView.findViewById(R.id.content);
        contentWithBG = (LinearLayout) itemView.findViewById(R.id.contentWithBackground);
        txtInfo = (TextView) itemView.findViewById(R.id.txtInfo);
        tickstatusimage = (ImageView) itemView.findViewById(R.id.tick_status_image);

    }

    public TextView getTxtMessage() {
        return txtMessage;
    }

    public ImageView getTickStatusImage() {
        return tickstatusimage;
    }

    public TextView getTxtInfo() {
        return txtInfo;
    }

    public LinearLayout getContent() {
        return content;
    }

    public LinearLayout getContentWithBG() {
        return contentWithBG;
    }
}