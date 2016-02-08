package com.mati.chat;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;


/**
 * Created by mati on 2016.02.03..
 */
public class chatHolder extends FermatViewHolder {

    public TextView txtMessage;
    public TextView txtInfo;
    public LinearLayout content;
    public LinearLayout contentWithBG;


    /**
     * Constructor
     *
     * @param itemView
     */
    protected chatHolder(View itemView) {
        super(itemView);

        txtMessage = (TextView) itemView.findViewById(R.id.txtMessage);
        txtInfo = (TextView) itemView.findViewById(R.id.txtInfo);
        content = (LinearLayout) itemView.findViewById(R.id.content);
        contentWithBG = (LinearLayout) itemView.findViewById(R.id.contentWithBackground);
    }

    public TextView getTxtMessage() {
        return txtMessage;
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
