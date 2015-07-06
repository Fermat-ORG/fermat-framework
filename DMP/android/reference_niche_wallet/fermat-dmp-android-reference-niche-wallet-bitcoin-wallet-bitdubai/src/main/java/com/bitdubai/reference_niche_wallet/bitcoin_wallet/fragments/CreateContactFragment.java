package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class CreateContactFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_PLATFORM = "platform";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    EditText inputSearch;
    ArrayAdapter<String> adapter;

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private Platform platform;
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    public static CreateContactFragment newInstance(int position) {
        CreateContactFragment f = new CreateContactFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, new Platform());
        f.setArguments(b);
        return f;
    }

    public static CreateContactFragment newInstance(final int position, final Platform platform) {
        CreateContactFragment f = new CreateContactFragment();
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
        errorManager = platform.getErrorManager();

        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("Unexpected error get Contact list - " + e.getMessage());


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
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("CantGetAllWalletContactsException- " + e.getMessage());

            }

            // Get ListView object from xml
            ListView listView = (ListView) rootView.findViewById(R.id.contactlist);

            String[] contacts;
            if (walletContactRecords.size() > 0) {
                contacts = new String[walletContactRecords.size()];
                for (int i = 0; i < walletContactRecords.size(); i++) {
                    contacts[i] = walletContactRecords.get(i).getActorName();
                }
            } else {
                contacts = new String[0];
            }

            adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.wallets_bitcoin_fragment_contacts_list, R.id.contact_name, contacts);

            inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);

            inputSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    adapter.getFilter().filter(cs);
                }
                @Override public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
                @Override public void afterTextChanged(Editable arg0) { }
            });

            // add_contact button definition
            final TextView stringAddressTextView = (TextView) rootView.findViewById(R.id.add_contact_btn);
            stringAddressTextView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    addContact(inputSearch.getText().toString());
                }
            });

            listView.setAdapter(adapter);

        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("Contacts Fragment onCreateView Exception - " + e.getMessage());

        }
        return rootView;
    }

    private void addContact(String name) {

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