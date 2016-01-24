package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        View item = inflater.inflate(R.layout.chat_list_item, null);

        FermatTextView lblTitulo = (FermatTextView)item.findViewById(R.id.txtInfo);
        lblTitulo.setText(datos[position].getTitulo());

        FermatTextView lblSubtitulo = (FermatTextView)item.findViewById(R.id.txtMessage);
        lblSubtitulo.setText(datos[position].getSubtitulo());

        return(item);
    }

    public void refreshEvents(Parameters[] datos) {

        for(int i=0; i<datos.length; i++) {
            this.datos[i]=datos[i];
        }

        notifyDataSetChanged();

    }


}