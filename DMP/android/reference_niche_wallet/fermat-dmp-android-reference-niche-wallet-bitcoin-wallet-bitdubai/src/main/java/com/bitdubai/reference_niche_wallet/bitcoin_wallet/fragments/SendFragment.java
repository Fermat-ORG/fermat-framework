package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

/**
 * Created by natalia on 19/06/15.
 */
public class SendFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();

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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send, container, false);


        TextView tv;

        tv = (EditText) rootView.findViewById(R.id.amount);
        tv.requestFocus();

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

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0){
            if(intent != null){
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

    //show alert
    private void showMessage(String text){
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
