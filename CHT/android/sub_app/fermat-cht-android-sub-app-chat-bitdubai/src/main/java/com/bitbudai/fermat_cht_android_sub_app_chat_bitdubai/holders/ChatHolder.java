package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.ui.holders.FermatViewHolder;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * ChatHolder ViewHolder
 *
 * <p/>
 * Created by Leon Acosta (laion.cj91@gmail.com) on 06/09/2016.
 *
 * @author  lnacosta
 * @version 1.0
 */
public class ChatHolder extends FermatViewHolder {

    public ImageView imageTick;
    public ImageView image;
    public TextView  contactname;
    public TextView  lastmessage;
    public TextView  dateofmessage;
    public TextView  tvnumber;

    /**
     * Constructor
     *
     * @param item
     */
    public ChatHolder(View item) {
        super(item);

        imageTick     = (ImageView) item.findViewById(R.id.imagetick);
        image         = (ImageView) item.findViewById(R.id.image);
        contactname   = (TextView)  item.findViewById(R.id.tvtitle);
        lastmessage   = (TextView)  item.findViewById(R.id.tvdesc);
        dateofmessage = (TextView)  item.findViewById(R.id.tvdate);
        tvnumber      = (TextView)  item.findViewById(R.id.tvnumber);
    }
}