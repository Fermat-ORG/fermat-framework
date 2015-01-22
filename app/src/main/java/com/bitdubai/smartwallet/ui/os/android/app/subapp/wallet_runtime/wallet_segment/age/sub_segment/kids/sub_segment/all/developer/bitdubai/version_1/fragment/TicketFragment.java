package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;
import android.view.GestureDetector;
import android.view.View.OnClickListener;

import java.io.File;
import android.os.Environment;
import android.content.Intent;
import android.net.Uri;


/**
 * Created by Natalia on 16/01/2015.
 */
public class TicketFragment  extends DialogFragment  {

    private static final String ARG_POSITION = "position";
    private static final long DOUBLE_PRESS_INTERVAL = 250; // in millis
    private long lastPressTime;
    private int position;
    View rootView;
    boolean mHasDoubleClicked = false;
    private String[] tickets;

    GestureDetector gestureDetector;
    boolean tapped;
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
        ImageView money = (ImageView)view.findViewById(R.id.ticket1);
        money.setTag(1);
        imageTicket = money;

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
                            imageTicket.setImageResource(R.drawable.ar_bill_1_b );
                            v.setTag(2);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_1);
                            v.setTag(1);
                        }
                    }

                });


                break;
            case "usd_5":
                imageTicket.setImageResource(R.drawable.usd_5);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.ar_bill_5_b);
                            v.setTag(2);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_5);
                            v.setTag(1);
                        }
                    }

                });

                break;
            case "usd_10":
                imageTicket.setImageResource(R.drawable.usd_10);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.usd_10);
                            v.setTag(2);
                        } else {
                            imageTicket.setImageResource(R.drawable.ar_bill_10_b);
                            v.setTag(1);
                        }
                    }

                });

                break;
            case "usd_20":
                imageTicket.setImageResource(R.drawable.usd_20);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.ar_bill_2_b);
                            v.setTag(2);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_20);
                            v.setTag(1);
                        }

                    }

                });

                break;
            case "usd_50":
                imageTicket.setImageResource(R.drawable.usd_50);
                imageTicket.setTag(50);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.ar_bill_50_b);
                            v.setTag(502);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_50);
                            v.setTag(50);
                        }
                    }

                });

                break;
            case "usd_100":
                imageTicket.setImageResource(R.drawable.usd_100);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.ar_bill_100_b);
                            v.setTag(2);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_100);
                            v.setTag(1);
                        }

                    }
                });
                break;

        }



        return view;
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
                    fileName ="usd_1.jpg";
                    BitmapFactory.decodeResource(getResources(),R.drawable.usd_1);
                    break;
                case "usd_5":
                    fileName ="usd_5.jpg";
                    BitmapFactory.decodeResource(getResources(),R.drawable.usd_5);
                    break;
                case "usd_10":
                    fileName ="usd_10.jpg";
                    BitmapFactory.decodeResource(getResources(),R.drawable.usd_10);
                    break;
                case "usd_20":
                    fileName ="usd_20.jpg";
                    BitmapFactory.decodeResource(getResources(),R.drawable.usd_20);
                    break;
                case "usd_50":
                    fileName ="usd_50.jpg";
                    BitmapFactory.decodeResource(getResources(),R.drawable.usd_50);
                    break;
                case "usd_100":
                    fileName ="usd_100.jpg";
                    BitmapFactory.decodeResource(getResources(),R.drawable.usd_100);
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
                startActivity(Intent.createChooser(editIntent, null));
//startActivityForResult(intent, Constants.LINK_CALENDER);
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