package com.bitdubai.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.content.Context;
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
import com.bitdubai.smartwallet.R;
import com.bitdubai.android.app.common.version_1.classes.MyApplication;
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
    private final int EDITED_TICKET = 1;
    private static final String ARG_POSITION = "position";
    private static final long DOUBLE_PRESS_INTERVAL = 250; // in millis
    private long lastPressTime;
    private int position;
    View rootView;
    boolean mHasDoubleClicked = false;
    private String[] tickets;
    private ImageView imageMoney;
    GestureDetector gestureDetector;
    boolean tapped;
    Context context;
    String ticketFace = "A";
    DialogFragment fragment = this;

    static TicketFragment newInstance(int position) {
        TicketFragment f = new TicketFragment();

        // Supply num input as an argument.
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);

        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        imageMoney = (ImageView)view.findViewById(R.id.ticket1);
        imageMoney.setTag(1);
        imageTicket = imageMoney;

        imageTicket.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return gestureDetector.onTouchEvent(event);
            }

        });

        String ticketId = MyApplication.getTicketId();
        switch ( MyApplication.getTicketId()) {
            case "usd_1":
                imageTicket.setImageResource(R.drawable.usd_1);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.usd_1_b );
                            ticketFace = "B";
                            v.setTag(12);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_1);
                            ticketFace = "A";
                            v.setTag(1);
                        }
                    }

                });


                break;
            case "usd_5":
                imageTicket.setImageResource(R.drawable.usd_5);
                imageTicket.setTag(5);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 5) {
                            imageTicket.setImageResource(R.drawable.usd_5_b);
                            ticketFace = "B";
                            v.setTag(52);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_5);
                            ticketFace = "A";
                            v.setTag(5);
                        }
                    }

                });

                break;
            case "usd_10":
                imageTicket.setImageResource(R.drawable.usd_10);
                imageTicket.setTag(10);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 10) {
                            imageTicket.setImageResource(R.drawable.usd_10_b);
                            ticketFace = "B";
                            v.setTag(102);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_10);
                            ticketFace = "A";
                            v.setTag(10);
                        }
                    }

                });

                break;
            case "usd_20":
                imageTicket.setImageResource(R.drawable.usd_20);
                imageTicket.setTag(20);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 20) {
                            imageTicket.setImageResource(R.drawable.usd_20_b);
                            ticketFace = "B";
                            v.setTag(202);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_20);
                            ticketFace = "A";
                            v.setTag(20);
                        }

                    }

                });

                break;
            case "usd_50":
                imageTicket.setImageResource(R.drawable.usd_50);
                imageTicket.setTag(50);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 50) {
                            imageTicket.setImageResource(R.drawable.usd_50_b);
                            ticketFace = "B";
                            v.setTag(502);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_50);
                            ticketFace = "A";
                            v.setTag(50);
                        }
                    }

                });

                break;
            case "usd_100":
                imageTicket.setImageResource(R.drawable.usd_100);
                imageTicket.setTag(100);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 100) {
                            imageTicket.setImageResource(R.drawable.usd_100_b);
                            ticketFace = "B";
                            v.setTag(1002);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_100);
                            ticketFace = "A";
                            v.setTag(100);
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

                        imageMoney.setImageDrawable(d);
                    } catch (Exception e) {
                        String strError = e.getMessage();
                    }

                }
        }
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
            switch ( MyApplication.getTicketId()) {
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

            } catch (FileNotFoundException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            String imagePath = mFile1.getAbsolutePath().toString()+"/"+fileName;
            File temp=new File(imagePath);

            if(temp.exists()){
                //  "Double Click open external Editor";
                //  String imagePath = "android.resource://" + getResources().getResourcePackageName(R.drawable.usd_1) + "/drawable-xxhdpi/usd_1.jpg";
                // String imagePath ="file://" +  getResources().getResourcePackageName(R.drawable.usd_1) + "/structured_res/drawable-xxhdpi/usd_1.jpg";
                Intent editIntent = new Intent(Intent.ACTION_EDIT);
                //getResources().getIdentifier("ic_launcher", "drawable", getPackageName());
                editIntent.setDataAndType(Uri.parse("file://" + imagePath), "image/*");
                editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                editIntent.putExtra("finishActivityOnSaveCompleted", true);
                //startActivity(Intent.createChooser(editIntent, null));
                fragment.startActivityForResult(editIntent, EDITED_TICKET);
            }


            return true;
        }




    }



    /* 57 down vote accepted


You can create a Drawable or Bitmap from a string path like this:

 String pathName = "/path/to/file/xxx.jpg";
 Drawable d = Drawable.createFromPath(pathName);

For a Bitmap:

String pathName = "/path/to/file/xxx.jpg";
bitmap b = BitmapFactory.decodeFile(pathName);

*/
}