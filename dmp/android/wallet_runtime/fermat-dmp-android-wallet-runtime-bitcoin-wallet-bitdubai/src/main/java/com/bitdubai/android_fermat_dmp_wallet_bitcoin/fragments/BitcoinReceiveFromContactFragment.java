package com.bitdubai.android_fermat_dmp_wallet_bitcoin.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BitcoinReceiveFromContactFragment extends android.app.Fragment {


    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;
    private String contact_id;
    private String user_address_wallet = "1FGvNMMbwnewMcVzPMsoyj5jAHEYt53ypa";
    final int colorQR = Color.BLACK;
    final int colorBackQR = Color.WHITE;
    final int width = 400;
    final int height = 400;

    public static BitcoinReceiveFromContactFragment newInstance() {
        BitcoinReceiveFromContactFragment f = new BitcoinReceiveFromContactFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get contact Id
        contact_id = this.getArguments().get("contactId").toString();

        contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"","$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};
        whens = new String[]{"","4 hours ago", "5 hours ago", "yesterday 11:00 PM", "24 Mar 14","3 Feb 14","1 year ago","1 year ago","2 year ago"};
        notes = new String[]{"","New telephone", "Old desk", "Car oil", "Sandwich","Headphones","Computer monitor","Pen","Apartment in Dubai"};
        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{
                {},
                {"New telephone","Hot dog","Telephone credit","Coffee"},
                {"Old desk","Flat rent","New glasses","House in Europe","Coffee","Gum"},
                {"Car oil","Headphones","Apartment"},
                {"Sandwich","New kitchen","Camera repair"},
                {"Headphones"},
                {"Computer monitor","New car"},
                {"Pen"},
                {"Apartment in Dubai"}
        };
        transactions_amounts = new String[][]{

                {},
                {"$200.00", "$3.00", "$460.00", "$2.00", "$1.5"},
                {"$3,000.00", "$34,200.00", "$4,500.00", "$4,000,000", "$2,00.00", "$0.50"},
                {"$400,00", "$43.00", "$350,000.00"},
                {"$3.00", "$55,000.00", "$7,500.00"},
                {"$45.00"},
                {"$600.00","$5050.00"},
                {"$50.00"},
                {"$80,000.00"}

        };

        transactions_whens = new String[][]{

                {},
                {"4 hours ago","8 hours ago","yesterday 10:33 PM","yesterday 9:33 PM"},
                {"5 hours ago","yesterday","20 Sep 14","16 Sep 14","13 Sep 14","12 Sep 14"},
                {"yesterday 11:00 PM","23 May 14", "12 May 14"},
                {"24 Mar 14","15 Apr 14","2 years ago"},
                {"3 Feb 14"},
                {"1 year ago","1 year ago"},
                {"1 year ago"},
                {"2 years ago"}


        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_receive_from_contact, container, false);


        String[] tagId = contact_id.split("\\|");
        TextView tv;

        //if tagId[1] = -1 no show data
        if (Integer.parseInt(tagId[1]) == -1) {

            tv = (TextView) rootView.findViewById(R.id.contact_name);
            tv.setText(contacts[Integer.parseInt(tagId[0])]);

        }
        else {
            tv = (TextView) rootView.findViewById(R.id.notes);
            tv.setText(transactions[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

            tv = (TextView) rootView.findViewById(R.id.contact_name);
            tv.setText(contacts[Integer.parseInt(tagId[0])]);
        }

        //define action for click options of spinner control
       final Spinner spinner = (Spinner) rootView.findViewById(R.id.share_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                int option =  spinner.getSelectedItemPosition();
                switch ( option) {
                    case 0:
                        copytoClipboard(rootView);
                        break;
                    case 1:
                        sendWhatsappMessage(rootView); //whatsapp message
                        break;
                    case 2:
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

        //create QR code with user address wallet


        try
        {
            Bitmap bitmapQR = generateBitmap(user_address_wallet, width, height,
                    MARGIN_AUTOMATIC, colorQR, colorBackQR);

            ImageView imageQR = (ImageView)rootView.findViewById(R.id.qr_code);

            imageQR.setImageBitmap(bitmapQR);
        }
        catch(WriterException writerException) {

        }


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
        ClipData  myClip = ClipData.newPlainText("text", this.user_address_wallet);
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
     * @throws WriterException zxing engine is unable to create QR code data
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


