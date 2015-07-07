package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.interfaces.WalletContactRecord;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.IntentResult;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.WalletContactListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class SendContactsFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    private static final String ARG_PLATFORM = "platform";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    EditText inputSearch;
    WalletContactListAdapter adapter;

    List<WalletContact> contacts;
    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private Platform platform;
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    LinearLayout linearLayout;
    ListView listView;

    public static SendContactsFragment newInstance(int position) {
        SendContactsFragment f = new SendContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, new Platform());
        f.setArguments(b);
        return f;
    }

    public static SendContactsFragment newInstance(final int position, final Platform platform) {
        SendContactsFragment f = new SendContactsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        b.putSerializable(ARG_PLATFORM, platform);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        platform = new Platform();
        errorManager = platform.getErrorManager();

        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("Unexpected error getting Crypto Wallet - " + e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data != null) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                //get references to edit text control to show scand result
                EditText textResult = IntentIntegrator.getTextResult();
                if (scanResult != null) {

                    // handle scan result
                    String contantsString = scanResult.getContents() == null ? "0" : scanResult.getContents();
                    if (contantsString.equalsIgnoreCase("0")) {
                        Toast.makeText(this.getActivity(), "Problem to get contact address", Toast.LENGTH_LONG).show();

                    } else {
                        //load into text address
                        textResult.setText(contantsString);
                    }
                } else {
                    Toast.makeText(this.getActivity(), "Problem to scan the barcode.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(" Load address Exception- " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send_new, container, false);

        linearLayout =(LinearLayout)rootView.findViewById(R.id.send_contacts_container2);
        try {
            //get contacts list
            List<WalletContactRecord> walletContactRecords = new ArrayList<>();
            try {
                walletContactRecords = cryptoWallet.listWalletContacts(wallet_id);
            } catch (CantGetAllWalletContactsException e) {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("CantGetAllWalletContactsException- " + e.getMessage());
            }

            contacts = new ArrayList<>();
            for (WalletContactRecord wcr : walletContactRecords) {
                contacts.add(new WalletContact(wcr.getActorName(), wcr.getReceivedCryptoAddress().getAddress()));
            }

            listView = (ListView) rootView.findViewById(R.id.send_contactlist);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    WalletContact walletContact = (WalletContact) listView.getItemAtPosition(position);
                    goToSend(walletContact);
                }
            });

            inputSearch = (EditText) rootView.findViewById(R.id.send_inputSearch);

            inputSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    adapter.getFilter().filter(cs);
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                }
            });

            // add_contact button definition
            final Button addContactButton = (Button) rootView.findViewById(R.id.send_add_contact_btn);
            addContactButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    goToSend(new WalletContact(inputSearch.getText().toString(), null));
                }
            });

            adapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, contacts);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("Contacts Fragment onCreateView Exception - " + e.getMessage());

        }
        return rootView;
    }

    private void goToSend(WalletContact walletContact) {
        SendFragment sendFragment = new SendFragment();
        sendFragment.setCryptoAddress(new CryptoAddress(walletContact.address, CryptoCurrency.BITCOIN));
        sendFragment.setName(walletContact.name);

        FragmentTransaction FT = getFragmentManager().beginTransaction();

        FT.replace(R.id.send_contacts_container, sendFragment);
        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        linearLayout.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        FT.commit();
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