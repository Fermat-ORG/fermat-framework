package com.bitbudai.fermat_cht_android_sub_app_chat_bitdubai.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bitdubai.fermat_cht_android_sub_app_chat_bitdubai.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by miguel on 22/01/16.
 */

public class ChatAdapterView extends ArrayAdapter {
    ArrayList<String> datos=new ArrayList<String>();

    public ChatAdapterView(Context context,ArrayList datos) {
        super(context, R.layout.listview, datos);
        this.datos=datos;


    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.listview, null);
        System.out.println("*************CAMBIO:"+ Arrays.toString(datos.toArray()));
        System.out.println("*************Position:"+ position);

                try {
                if (datos.get(position).split("##")[0].equals("yo")) {
                    TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
                    lblTitulo.setText(datos.get(position).split("##")[1]);
                } else {
                    TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
                    lblSubtitulo.setText(datos.get(position).split("##")[1]);
                }
                }catch (NullPointerException e){
                      System.out.print("Null on position:"+position);
                }

        return(item);
    }

    public void refreshEvents(ArrayList<String> datos) {
        this.datos=datos;

        notifyDataSetChanged();

    }


}