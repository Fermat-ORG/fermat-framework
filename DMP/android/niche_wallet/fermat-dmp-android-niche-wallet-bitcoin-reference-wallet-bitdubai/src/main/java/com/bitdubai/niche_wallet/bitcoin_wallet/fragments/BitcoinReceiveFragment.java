package com.bitdubai.niche_wallet.bitcoin_wallet.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.niche_wallet.bitcoin_wallet.Platform;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BitcoinReceiveFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    private String user_address_wallet = "1FGvNMMbwnewMcVzPMsoyj5jAHEYt53ypa";
    final int colorQR = Color.BLACK;
    final int colorBackQR = Color.WHITE;
    final int width = 400;
    final int height = 400;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();
    CryptoWallet cryptoWallet;


    public static BitcoinReceiveFragment newInstance(int position) {
        BitcoinReceiveFragment f = new BitcoinReceiveFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        //setHasOptionsMenu(false);
        super.onCreate(savedInstanceState);


        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_receive, container, false);
        TextView tv;

        final EditText nameText = (EditText)rootView.findViewById(R.id.contact_name);

        //add onchange event
        nameText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // show qrcode and share
                //get wallet address
                //TODO CryptoVaultManager is null, falta la referencia para poder probar
                // try {
                // CryptoAddress cryptoAddress = cryptoWallet.requestAddress(nameText.getText().toString(), Actors.EXTRA_USER, PlatformWalletType.BASIC_WALLET_BITCOIN_WALLET, wallet_id);

                // user_address_wallet = cryptoAddress.getAddress();
                //} catch (CantRequestCryptoAddressException e) {
                //    e.printStackTrace();
                // }
                //create QR code with user address wallet

                if(count > 3){
                    try {
                        Bitmap bitmapQR = generateBitmap(user_address_wallet, width, height,
                                MARGIN_AUTOMATIC, colorQR, colorBackQR);

                        ImageView imageQR = (ImageView) rootView.findViewById(R.id.qr_code);

                        imageQR.setImageBitmap(bitmapQR);
                        imageQR.setVisibility(View.VISIBLE);
                    } catch (WriterException writerException) {

                    }

                    //define action for click options of spinner control
                    final Spinner spinner = (Spinner) rootView.findViewById(R.id.share_spinner);
                    spinner.setVisibility(View.VISIBLE);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view,
                                                   int position, long id) {

                            int option = spinner.getSelectedItemPosition();
                            switch (option) {
                                case 1:
                                    copytoClipboard(rootView);
                                    break;
                                case 2:
                                    sendWhatsappMessage(rootView); //whatsapp message
                                    break;
                                case 3:
                                    sendMessage(rootView); //sms message
                                    break;
                                default:
                                    break;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {

                        }
                    });

                }

            }
        });



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    /*
       Send to whatsapp
        */
    public void sendWhatsappMessage(View v) {

        String whatsAppMessage = "This is my wallet address  "; //message.getText().toString();
        //I save QR code image on memory, then i send it
        //Uri.parse("file://" + imagePath), "image/*"
        // ImageView imageQR = (ImageView)tv.findViewById(R.id.qr_code);
        //You can read the image from external drove too
        Uri uri = Uri.parse("android.resource://com.code2care.example.whatsappintegrationexample/drawable/mona");

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM,uri);
        intent.setType("image/jpeg");

        // Do not forget to add this to open whatsApp App specifically
        intent.setPackage("com.whatsapp");
        startActivity(intent);

    }

    /*
   Send to SMS message
    */
    public void sendMessage(View v) {

        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", "This is my wallet address " + user_address_wallet);
        sendIntent.setType("vnd.android-dir/mms-sms");
        startActivity(sendIntent);

    }

    /**
     * copy wallet address to clipboard
     */

    public void copytoClipboard(View v) {

        ClipboardManager myClipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", this.user_address_wallet);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getActivity().getApplicationContext(), "Text Copied",
                Toast.LENGTH_SHORT).show();
    }



    /**
     * Allow the zxing engine use the default argument for the margin variable
     */
    static public int MARGIN_AUTOMATIC = -1;

    /**
     * Set no margin to be added to the QR code by the zxing engine
     */
    static public int MARGIN_NONE = 0;

    /**
     * Encode a string into a QR Code and return a bitmap image of the QR code
     *
     * @param contentsToEncode String to be encoded, this will often be a URL, but could be any string
     * @param imageWidth number of pixels in width for the resultant image
     * @param imageHeight number of pixels in height for the resultant image
     * @param marginSize the EncodeHintType.MARGIN parameter into zxing engine
     * @param color data color for QR code
     * @param colorBack background color for QR code
     * @return bitmap containing QR code image
     * @throws com.google.zxing.WriterException zxing engine is unable to create QR code data
     * @throws IllegalStateException when executed on the UI thread
     */
    static public Bitmap generateBitmap(@NonNull String contentsToEncode,
                                        int imageWidth, int imageHeight,
                                        int marginSize, int color, int colorBack)
            throws WriterException, IllegalStateException {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            // throw new IllegalStateException("Should not be invoked from the UI thread");
        }

        Map<EncodeHintType, Object> hints = null;
        if (marginSize != MARGIN_AUTOMATIC) {
            hints = new EnumMap<>(EncodeHintType.class);
            // We want to generate with a custom margin size
            hints.put(EncodeHintType.MARGIN, marginSize);
        }

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = writer.encode(contentsToEncode, BarcodeFormat.QR_CODE, imageWidth, imageHeight, hints);

        final int width = result.getWidth();
        final int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? color : colorBack;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}