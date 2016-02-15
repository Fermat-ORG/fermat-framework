package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.structure.MessageType;
import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.enums.MessageContentType;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by miguel on 22/01/16.
 */

public class ChatAdapterView extends ArrayAdapter {
    ArrayList<String> datos=new ArrayList<String>();

    public ChatAdapterView(Context context,ArrayList datos) {
        super(context, R.layout.listviewme, datos);
        this.datos=datos;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listviewme, null);

        if(!datos.isEmpty()) {
            if (datos.get(position).split("@#@#")[0].equals("OUTGOING")) {
                item = inflater.inflate(R.layout.listviewme, null);
                TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
     //           TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
                System.out.println("*************DATALEFT:" + Arrays.toString(datos.toArray()));
                System.out.println("*************PositionLEFT:" + position);

                try {

                    lblTitulo.setText(datos.get(position).split("@#@#")[1]);

                } catch (NullPointerException e) {
                    System.out.print("Null on position:" + position);
                }
            } else {
                item = inflater.inflate(R.layout.listviewyou, null);
                TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
       //         TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
                System.out.println("*************DATARIGHT:" + Arrays.toString(datos.toArray()));
                System.out.println("*************PositionRIGHT:" + position);

                try {

                    lblTitulo.setText(datos.get(position).split("@#@#")[1]);

                } catch (NullPointerException e) {
                    System.out.print("Null on position:" + position);
                }
            }
        }
        return(item);
    }

    public void refreshEvents(ArrayList<String> datos) {
        this.datos=datos;

        notifyDataSetChanged();

    }


}