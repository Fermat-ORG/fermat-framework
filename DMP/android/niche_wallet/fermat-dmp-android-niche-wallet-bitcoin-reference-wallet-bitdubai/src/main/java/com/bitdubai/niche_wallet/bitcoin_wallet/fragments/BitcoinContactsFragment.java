package com.bitdubai.niche_wallet.bitcoin_wallet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;

/**
 * Created by natalia on 19/06/15.
 */
public class BitcoinContactsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    public static BitcoinContactsFragment newInstance(int position) {
        BitcoinContactsFragment f = new BitcoinContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_contacts, container, false);

        // Get ListView object from xml
        ListView listView = (ListView) rootView.findViewById(R.id.transactionlist);


        // Defined Array values to show in ListView
        String[] contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, contacts);

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
}
