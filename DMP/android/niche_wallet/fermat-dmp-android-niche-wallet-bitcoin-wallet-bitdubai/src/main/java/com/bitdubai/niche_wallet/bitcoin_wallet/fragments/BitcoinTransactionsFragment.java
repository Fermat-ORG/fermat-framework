package com.bitdubai.niche_wallet.bitcoin_wallet.fragments;


import android.content.Context;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by natalia on 17/06/15.
 */
public class BitcoinTransactionsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;
    public static Typeface mDefaultTypeface;

    List TRANSACTIONS = new ArrayList<Transactions>();


    public static BitcoinTransactionsFragment newInstance(int position) {
        BitcoinTransactionsFragment f = new BitcoinTransactionsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Construct the data source
        TRANSACTIONS.add(new Transactions("Lucia Alarcon De Zamacona", "4 hours ago", "0.0012", "New telephone","Send"));
        TRANSACTIONS.add(new Transactions("Juan Luis R Pons","5 hours ago","0.0023","Old desk","Received"));
        TRANSACTIONS.add(new Transactions("Karina Rodríguez","yesterday 11:00 PM","0.1023","Car oil","Received"));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_transactions, container, false);
       // Get ListView object from xml
        ListView listView = (ListView) rootView.findViewById(R.id.transactionlist);



// Create the adapter to convert the array to views
        TransactionArrayAdapter adapter = new TransactionArrayAdapter(this.getActivity().getApplicationContext(), TRANSACTIONS);



        // Assign adapter to ListView
       listView.setAdapter(adapter);



        // ListView Item Click Listener
       /* listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();

            }

        });*/
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    public class TransactionArrayAdapter extends ArrayAdapter<Transactions> {

        public TransactionArrayAdapter(Context context, List<Transactions> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            //get inflater
            LayoutInflater inflater = (LayoutInflater)getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


            View listItemView = convertView;

            //if view exist
            if (null == convertView) {
                //Si no existe, entonces inflarlo con image_list_view.xml
                listItemView = inflater.inflate(
                        R.layout.wallets_bitcoin_fragment_transactions_list_items,
                        parent,
                        false);
            }

            //Get TextViews
            TextView contact_name = (TextView)listItemView.findViewById(R.id.contact_name);
            TextView amount = (TextView)listItemView.findViewById(R.id.amount);
            TextView notes = (TextView)listItemView.findViewById(R.id.notes);
            TextView when = (TextView)listItemView.findViewById(R.id.when);
            TextView type = (TextView)listItemView.findViewById(R.id.type);

            //Getting Transactions instance at the current position
            Transactions item = getItem(position);

            contact_name.setText(item.getName());
            amount.setText(item.getAmount());
            notes.setText(item.getMemo());
            when.setText(item.getDate());
            type.setText(item.getType());

            //return ListView
            return listItemView;

        }
    }




    public class Transactions{

        private String name;
        private String date;
        private String amount;
        private String memo;
        private String type;

        public Transactions(String name,String date,String amount, String memo,String type){
            this.name = name;
            this.date = date;
            this.amount=amount;
            this.memo=memo;
            this.type=type;

        }

        public void setname(String name){
            this.name = name;
        }

        public void setDate(String date){
            this.date = date;
        }

        public void setAmount(String amount){
            this.amount=amount;
        }

        public void setMemo(String memo){
            this.memo=memo;
        }

        public void setType(String type){
            this.type=type;
        }

        public String getName(){return this.name;}
        public String getDate(){return this.date;}
        public String getAmount(){return this.amount;}
        public String getMemo(){return this.memo;}
        public String getType(){return this.type;}

    }
}
