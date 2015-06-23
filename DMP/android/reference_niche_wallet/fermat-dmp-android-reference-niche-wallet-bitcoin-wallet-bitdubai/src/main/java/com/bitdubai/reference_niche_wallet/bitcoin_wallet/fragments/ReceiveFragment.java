package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
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
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;
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
public class ReceiveFragment extends Fragment {

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


    public static ReceiveFragment newInstance(int position) {
        ReceiveFragment f = new ReceiveFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        //setHasOptionsMenu(false);
        super.onCreate(savedInstanceState);
    try
    {
        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }
    } catch(Exception ex) {
        showMessage("Unexpected error init Crypto Manager - " + ex.getMessage());
        ex.printStackTrace();
    }



    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_receive, container, false);
        TextView tv;

        final EditText nameText = (EditText)rootView.findViewById(R.id.contact_name);

        //add event finish type
        nameText.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                                actionId == EditorInfo.IME_ACTION_DONE ||
                                event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (!event.isShiftPressed()) {
                                // the user is done typing.
                                getWalletAddress(nameText.getText().toString());
                                return true; // consume.
                            }
                        }
                        return false; // pass on to other listeners.
                    }
                });




        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    /* show qr wallet code

     */

    private void getWalletAddress(String contact_name) {
        try {

             try {
                CryptoAddress cryptoAddress = cryptoWallet.requestAddress(contact_name.toString(), Actors.EXTRA_USER, PlatformWalletType.BASIC_WALLET_BITCOIN_WALLET, wallet_id);

                user_address_wallet = cryptoAddress.getAddress();
            } catch (CantRequestCryptoAddressException e) {
                 showMessage("Cant Request Crypto Address Exception - " + e.getMessage());
                e.printStackTrace();
            }

        }  catch(Exception ex) {
            showMessage("Unexpected error get wallet address - " + ex.getMessage());
        }
        //create QR code with user address wallet


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