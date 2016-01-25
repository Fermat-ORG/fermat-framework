package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.holders.ChatHolder;
import com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.util.Parameters;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

/**
 * Created by miguel on 22/01/16.
 */

public class ChatAdapterView extends ArrayAdapter<Parameters> {
    Parameters[] datos;
    public ChatAdapterView(Context context, Parameters[] datos) {
        super(context, R.layout.chat_list_item, datos);
        this.datos=datos;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview, null);

        TextView lblTitulo = (TextView)item.findViewById(R.id.LblTitulo);
        lblTitulo.setText(datos[position].getTitulo());

        TextView lblSubtitulo = (TextView)item.findViewById(R.id.LblSubTitulo);
        lblSubtitulo.setText(datos[position].getSubtitulo());
        ///setAlignment(holder, boolean isMe)
        return(item);
    }

    public void refreshEvents(Parameters[] datos) {

        for(int i=0; i<datos.length; i++) {
            this.datos[i]=datos[i];
        }
        notifyDataSetChanged();
    }
/*
    private void setAlignment(ChatHolder holder, boolean isMe) {
        if (!isMe) {

            //holder.contentWithBG.setBackgroundResource(R.drawable.in_message_bg);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtInfo.setLayoutParams(layoutParams);
        } else {
            //holder.contentWithBG.setBackgroundResource(R.drawable.out_message_bg);

            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) holder.contentWithBG.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.contentWithBG.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp =
                    (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtInfo.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtInfo.setLayoutParams(layoutParams);
        }
    }
*/


}