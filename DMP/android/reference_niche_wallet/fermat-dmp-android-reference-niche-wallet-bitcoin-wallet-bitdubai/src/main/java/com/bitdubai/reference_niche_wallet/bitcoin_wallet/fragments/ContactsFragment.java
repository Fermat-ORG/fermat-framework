package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class ContactsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_PLATFORM = "platform";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private Platform platform;
    private CryptoWallet cryptoWallet;

    public static ContactsFragment newInstance(int position) {
        ContactsFragment f = new ContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, new Platform());
        f.setArguments(b);
        return f;
    }

    public static ContactsFragment newInstance(final int position, final Platform platform) {
        ContactsFragment f = new ContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, platform);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        platform = (Platform) getArguments().getSerializable(ARG_PLATFORM);

        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {

            showMessage("Unexpected error get Contact list - " + e.getMessage());
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_contacts, container, false);
        try {

            //get contacts list
            List<WalletContactRecord> walletContactRecords = new ArrayList<>();
            try {
                walletContactRecords = cryptoWallet.listWalletContacts(wallet_id);
            } catch (CantGetAllWalletContactsException e) {
                showMessage("CantGetAllWalletContactsException- " + e.getMessage());
                e.printStackTrace();
            }

            // Get ListView object from xml
            ListView listView = (ListView) rootView.findViewById(R.id.contactlist);


            // Defined Array values to show in ListView
            //String[] contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };

            String[] contacts;
            if (walletContactRecords.size() > 0) {
                contacts = new String[walletContactRecords.size()];
                for (int i = 0; i < walletContactRecords.size(); i++) {
                    contacts[i] = walletContactRecords.get(i).getActorName();
                }
            } else {
                contacts = new String[0];
            }

            // Define a new Adapter
            // First parameter - Context
            // Second parameter - Layout for the row
            // Third parameter - ID of the TextView to which the data is written
            // Forth - the Array of data

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                    R.layout.wallets_bitcoin_fragment_contacts_list, R.id.contact_name, contacts);

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
        } catch (Exception e) {
            showMessage("Contacts Fragment onCreateView Exception - " + e.getMessage());
            e.printStackTrace();
        }
        return rootView;
    }

    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
}