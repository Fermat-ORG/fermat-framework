package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.IntentResult;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class SendFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_POSITION = "position";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    Bundle savedInstanceState;
    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;

    private TextView txtAddress;

    public static SendFragment newInstance(int position) {
        SendFragment f = new SendFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;
        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            showMessage("CantGetCryptoWalletException- " + e.getMessage());
            e.printStackTrace();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send, container, false);

        try {
            TextView tv;
            final EditText contact_name = (EditText) rootView.findViewById(R.id.contact_name);
            final EditText editAddress = (EditText) rootView.findViewById(R.id.address);
            final EditText editAmount = (EditText) rootView.findViewById(R.id.amount);
            final LinearLayout linear_address = (LinearLayout) rootView.findViewById(R.id.linear_address);
            final LinearLayout linear_amount = (LinearLayout) rootView.findViewById(R.id.linear_amount);
            final LinearLayout linear_notes = (LinearLayout) rootView.findViewById(R.id.linear_notes);
            final LinearLayout linear_send = (LinearLayout) rootView.findViewById(R.id.linear_send);

            ImageView b = (ImageView) rootView.findViewById(R.id.send_button);
            b.setOnClickListener(this);

            //add event finish type

            contact_name.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (contact_name != null && contact_name.getText() != null && !contact_name.getText().toString().equals("")) {
                        linear_address.setVisibility(View.VISIBLE);
                    } else {
                        linear_address.setVisibility(View.GONE);
                        linear_amount.setVisibility(View.GONE);
                        linear_notes.setVisibility(View.GONE);
                        linear_send.setVisibility(View.GONE);
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });

            editAddress.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (editAddress != null && editAddress.getText() != null && !editAddress.getText().toString().equals("")) {
                        linear_amount.setVisibility(View.VISIBLE);
                    } else {
                        linear_amount.setVisibility(View.GONE);
                        linear_notes.setVisibility(View.GONE);
                        linear_send.setVisibility(View.GONE);
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });

            editAmount.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (editAmount != null && editAmount.getText() != null && !editAmount.getText().toString().equals("")) {
                        linear_notes.setVisibility(View.VISIBLE);
                        linear_send.setVisibility(View.VISIBLE);
                    } else {
                        linear_notes.setVisibility(View.GONE);
                        linear_send.setVisibility(View.GONE);
                    }
                }
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
            });



            txtAddress = (EditText) rootView.findViewById(R.id.address);

            //define icon event to scan Qr code - wallet address
            ImageView scanImage = (ImageView) rootView.findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                //pass reference to edit text control to show scan result and main activity
                   IntentIntegrator integrator = new IntentIntegrator(getActivity(),(EditText) rootView.findViewById(R.id.address));

                    integrator.initiateScan();

                }
            });


        } catch (Exception e) {
            showMessage(" CreateView Exception- " + e.getMessage());
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try
        {


            if (data != null) {


                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                //get references to edit text control to show scand result
                EditText textResult =  IntentIntegrator.getTextResult();
                if (scanResult != null) {

                    // handle scan result
                    String contantsString =  scanResult.getContents()==null?"0":scanResult.getContents();
                    if (contantsString.equalsIgnoreCase("0")) {
                        Toast.makeText(this.getActivity(), "Problem to get the  contant Number", Toast.LENGTH_LONG).show();

                    }else {
                        //load into text address

                       textResult.setText(contantsString);
                    }

                }
                else{
                    Toast.makeText(this.getActivity(), "Problem to scan the barcode.", Toast.LENGTH_LONG).show();
                }

            }
    }
    catch (Exception e) {
        showMessage(" Load address Exception- " + e.getMessage());
        e.printStackTrace();
    }

    }

    @Override
    public void onClick(View v) {
        try {
            EditText contact_name = (EditText) rootView.findViewById(R.id.contact_name);
            EditText amount = (EditText) rootView.findViewById(R.id.amount);
            EditText address = (EditText) rootView.findViewById(R.id.address);
            CryptoAddress cryptoAddress = new CryptoAddress();

            try {

                cryptoAddress.setAddress(address.getText().toString());
                cryptoAddress.setCryptoCurrency(CryptoCurrency.BITCOIN);

                // first i add the contact
                cryptoWallet.createWalletContact(cryptoAddress, contact_name.getText().toString(), Actors.EXTRA_USER, PlatformWalletType.BASIC_WALLET_BITCOIN_WALLET, wallet_id);

                cryptoWallet.send(Long.parseLong(amount.getText().toString()), cryptoAddress, wallet_id);

                showMessage("Send OK");
            } catch (CantCreateWalletContactException e) {
                e.printStackTrace();
                showMessage("Error creating wallet contact - " + e.getMessage());
            } catch (CantSendCryptoException e) {
                e.printStackTrace();
                showMessage("Error send satoshis - " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error send satoshis - " + e.getMessage());
        }

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
