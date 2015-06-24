package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class SendFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_POSITION = "position";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");
    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;

    public static SendFragment newInstance(int position) {
        SendFragment f = new SendFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
            EditText contact_name = (EditText) rootView.findViewById(R.id.contact_name);
            EditText editAddress = (EditText) rootView.findViewById(R.id.address);
            final LinearLayout linear_address = (LinearLayout) rootView.findViewById(R.id.linear_address);
            final LinearLayout linear_amount = (LinearLayout) rootView.findViewById(R.id.linear_amount);
            final LinearLayout linear_notes = (LinearLayout) rootView.findViewById(R.id.linear_notes);
            final LinearLayout linear_send = (LinearLayout) rootView.findViewById(R.id.linear_send);

            ImageView b = (ImageView) rootView.findViewById(R.id.send_button);
            b.setOnClickListener(this);

            //add event finish type

            contact_name.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                    actionId == EditorInfo.IME_ACTION_DONE ||
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                                // the user is done typing.
                                linear_address.setVisibility(View.VISIBLE);
                                return true; // consume.

                            }
                            return false; // pass on to other listeners.
                        }
                    });

            editAddress.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                    actionId == EditorInfo.IME_ACTION_DONE ||
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                                // the user is done typing.
                                linear_amount.setVisibility(View.VISIBLE);
                                return true; // consume.

                            }
                            return false; // pass on to other listeners.
                        }
                    });

            EditText editAmount = (EditText) rootView.findViewById(R.id.amount);
            //add event finish type
            editAmount.setOnEditorActionListener(
                    new EditText.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                    actionId == EditorInfo.IME_ACTION_DONE ||
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                                // the user is done typing.
                                linear_notes.setVisibility(View.VISIBLE);
                                linear_send.setVisibility(View.VISIBLE);
                                return true; // consume.

                            }
                            return false; // pass on to other listeners.
                        }
                    });

            //define icon event to scan Qr code - wallet address
            ImageView scanImage = (ImageView) rootView.findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (intent != null) {
                // Handle successful scan
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                //put code in file wallet address

                TextView tv;

                tv = (EditText) rootView.findViewById(R.id.address);
                tv.setText(contents);
            }

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

                //TODO no se esta guardando en este metodo el memo que cargo y el nombre del contacto.
                cryptoWallet.send(Long.parseLong(amount.getText().toString()), cryptoAddress, wallet_id);

                showMessage("Send OK");

            } catch (CantSendCryptoException e) {
                e.printStackTrace();
                showMessage("Error sending satoshis - " + e.getMessage());
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
