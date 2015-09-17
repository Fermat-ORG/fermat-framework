package com.bitdubai.reference_niche_wallet.age.kids.boys.fragments;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View.OnTouchListener;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_network_service.CantGetResourcesException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesManager;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.Wallets;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_dmp.wallet_runtime.R;
import com.bitdubai.reference_niche_wallet.age.kids.boys.Platform;
//import com.bitdubai.runtime_wallet.age.kids.boys.MyApplication;

import android.view.GestureDetector;
import android.view.View.OnClickListener;
import java.io.InputStream;
import java.io.File;
import android.os.Environment;
import android.content.Intent;
import android.net.Uri;


/**
 * Created by Natalia on 16/01/2015.
 */
public class TicketFragment  extends  DialogFragment  {
    /**
     * TicketFragment member variables.
     */

    private final int EDITED_TICKET = 1;
    private static final String ARG_POSITION = "position";
    private int position;
    private String[] tickets;
    private ImageView imageMoney;
    private GestureDetector gestureDetector;
    private String ticketFace = "A";
    private DialogFragment fragment = this;
    private  static WalletResourcesManager walletResourceManger;
    private static Platform platform;


    /**
     * TicketFragment constructor.
     */

    static TicketFragment newInstance(int position) {

        walletResourceManger = platform.getWalletResourcesManager();
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);

        TicketFragment f = new TicketFragment();
        // Supply num input as an argument.
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }


    /**
     * Fragment Class implementation.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        walletResourceManger = platform.getWalletResourcesManager();
        walletResourceManger.setwalletType(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI);

        gestureDetector = new GestureDetector(getActivity(),new GestureListener());
        position = getArguments().getInt(ARG_POSITION);

        tickets = new String[]{"usd_1", "usd_1", "usd_1", "usd_5", "usd_10", "usd_20", "usd_100", "usd_1", "usd_5", "usd_5", "usd_10", "usd_20"};

        int style = DialogFragment.STYLE_NO_TITLE, theme = 0;
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.wallets_kids_fragment_ticket, container, false); //Contains empty RelativeLayout
        final ImageView imageTicket;
        this.imageMoney = (ImageView)view.findViewById(R.id.ticket1);
        this.imageMoney.setTag(1);
        imageTicket = this.imageMoney;

        imageTicket.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return gestureDetector.onTouchEvent(event);
            }

        });


        switch ( platform.getTicketId()) {
            case "usd_1":
                imageTicket.setImageResource(R.drawable.usd_1);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v)  {
                        try{
                            if ((Integer) v.getTag() == 1) {

                               byte[] imageResource = walletResourceManger.getImageResource("usd_1_b.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "B";
                                v.setTag(12);
                            } else {

                                byte[] imageResource = walletResourceManger.getImageResource("usd_1.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "A";
                                v.setTag(1);
                            }
                        } catch (CantGetResourcesException e) {
                            System.err.println("CantGetResourcesException: " + e.getMessage());

                        }

                    }

                });


                break;
            case "usd_5":
                imageTicket.setImageResource(R.drawable.usd_5);
                imageTicket.setTag(5);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        try{


                            if ((Integer) v.getTag() == 5) {

                                 byte[] imageResource = walletResourceManger.getImageResource("usd_5_b.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "B";
                                v.setTag(52);
                            } else {
                                imageTicket.setImageResource(R.drawable.usd_5);
                                 byte[] imageResource = walletResourceManger.getImageResource("usd_5.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "A";
                                v.setTag(5);
                            }
                        } catch (CantGetResourcesException e) {
                            System.err.println("CantGetResourcesException: " + e.getMessage());

                        }
                    }

                });

                break;
            case "usd_10":
                imageTicket.setImageResource(R.drawable.usd_10);
                imageTicket.setTag(10);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        try{
                            if ((Integer) v.getTag() == 10) {

                                byte[] imageResource = walletResourceManger.getImageResource("usd_10_b.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "B";
                                v.setTag(102);
                            } else {

                                byte[] imageResource = walletResourceManger.getImageResource("usd_10.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "A";
                                v.setTag(10);
                            }
                        } catch (CantGetResourcesException e) {
                            System.err.println("CantGetResourcesException: " + e.getMessage());

                        }
                    }

                });

                break;
            case "usd_20":
                imageTicket.setImageResource(R.drawable.usd_20);
                imageTicket.setTag(20);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        try{
                            if ((Integer) v.getTag() == 20) {

                                byte[] imageResource = walletResourceManger.getImageResource("usd_20_b.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "B";
                                v.setTag(202);
                            } else {

                                byte[] imageResource = walletResourceManger.getImageResource("usd_20.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "A";
                                v.setTag(20);
                            }
                        } catch (CantGetResourcesException e) {
                            System.err.println("CantGetResourcesException: " + e.getMessage());

                        }

                    }

                });

                break;
            case "usd_50":
                imageTicket.setImageResource(R.drawable.usd_50);
                imageTicket.setTag(50);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        try{
                            if ((Integer) v.getTag() == 50) {

                                byte[] imageResource = walletResourceManger.getImageResource("usd_50_b.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "B";
                                v.setTag(502);
                            } else {

                                 byte[] imageResource = walletResourceManger.getImageResource("usd_50.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "A";
                                v.setTag(50);
                            }
                        } catch (CantGetResourcesException e) {
                            System.err.println("CantGetResourcesException: " + e.getMessage());

                        }
                    }

                });

                break;
            case "usd_100":
                imageTicket.setImageResource(R.drawable.usd_100);
                imageTicket.setTag(100);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        try{
                            if ((Integer) v.getTag() == 100) {
                                byte[] imageResource = walletResourceManger.getImageResource("usd_100_b.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);
                                ticketFace = "B";
                                v.setTag(1002);
                            } else {
                                 byte[] imageResource = walletResourceManger.getImageResource("usd_100.jpg");
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageResource, 0, imageResource.length);
                                imageTicket.setImageBitmap(bitmap);

                                ticketFace = "A";
                                v.setTag(100);
                            }
                        } catch (CantGetResourcesException cantGetResource) {
                            platform.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, cantGetResource);
                        }

                    }
                });
                break;

        }



        return view;
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {

        switch(requestCode) {
            case EDITED_TICKET:
                if(imageReturnedIntent != null){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        Drawable d = new BitmapDrawable(getResources(),selectedImage);

                        this.imageMoney.setImageDrawable(d);
                    } catch (FileNotFoundException e) {
                        FermatException exception = new FermatException(e.getMessage(), e, "", "");
                        platform.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, exception);
                    }

                }
        }
    }

    /**
     * TicketFragment implementation.
     */

    public  static void setPlatform (Platform platformWallet){
        platform = platformWallet;
    }

    public class GestureListener extends
            GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            return false;
        }

        // event when double tap occurs
        @Override
        public boolean onDoubleTap(MotionEvent e) {


            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_1);
            File mFile1 = Environment.getExternalStorageDirectory();
            String fileName ="";

            switch ( platform.getTicketId()) {
                case "usd_1":
                    if(ticketFace == "B")
                    {
                        fileName ="usd_1_b.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_1_b);
                    }
                    else{
                        fileName ="usd_1.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_1);
                    }
                    break;
                case "usd_5":

                    if(ticketFace == "B")
                    {
                        fileName ="usd_5_b.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_5_b);
                    }
                    else{
                        fileName ="usd_5.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_5);
                    }
                    break;
                case "usd_10":

                    if(ticketFace == "B")
                    {
                        fileName ="usd_10_b.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_10_b);
                    }
                    else{
                        fileName ="usd_10.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_10);
                    }
                    break;
                case "usd_20":
                    if(ticketFace == "B")
                    {
                        fileName ="usd_20_b.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_20_b);
                    }
                    else{
                        fileName ="usd_20.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_20);
                    }
                    break;
                case "usd_50":

                    if(ticketFace == "B")
                    {
                        fileName ="usd_50_b.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_50_b);
                    }
                    else{
                        fileName ="usd_50.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_50);
                    }
                    break;
                case "usd_100":

                    if(ticketFace == "B")
                    {
                        fileName ="usd_100_b.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_100_b);
                    }
                    else{
                        fileName ="usd_100.jpg";
                        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.usd_100);
                    }
                    break;

            }


            File mFile2 = new File(mFile1,fileName);
            try {
                FileOutputStream outStream;
                outStream = new FileOutputStream(mFile2);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();

            } catch (FileNotFoundException ex) {
                FermatException exception = new FermatException(ex.getMessage(), ex, "", "");
                platform.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, exception);
            } catch (IOException ex) {
                FermatException exception = new FermatException(ex.getMessage(), ex, "", "");
                platform.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_AGE_KIDS_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT, exception);
            }

            String imagePath = mFile1.getAbsolutePath().toString()+"/"+fileName;
            File temp=new File(imagePath);

            if(temp.exists()){
                //  "Double Click open external Editor";
                Intent editIntent = new Intent(Intent.ACTION_EDIT);

                editIntent.setDataAndType(Uri.parse("file://" + imagePath), "image/*");
                editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                editIntent.putExtra("finishActivityOnSaveCompleted", true);

                fragment.startActivityForResult(editIntent, EDITED_TICKET);
            }


            return true;
        }




    }


}