package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.RelativeLayout;
import android.view.View.OnTouchListener;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;
import android.view.GestureDetector;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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

        switch ( MyApplication.getTicketId()) {
            case "usd_1":
                imageTicket.setImageResource(R.drawable.usd_1);
                imageTicket.setTag(1);
                imageTicket.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        if ((Integer) v.getTag() == 1) {
                            imageTicket.setImageResource(R.drawable.usd_1_b );
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
                            imageTicket.setImageResource(R.drawable.usd_5_b);
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
                            imageTicket.setImageResource(R.drawable.usd_10_b);
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
                            imageTicket.setImageResource(R.drawable.usd_20_b);
                            v.setTag(2);
                        } else {
                            imageTicket.setImageResource(R.drawable.usd_20);
                            v.setTag(1);
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
                            imageTicket.setImageResource(R.drawable.usd_100_b);
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

            //  "Double Click open external Editor";
            Intent editIntent = new Intent(Intent.ACTION_EDIT);
            editIntent.setDataAndType(Uri.parse("file://" + "smart-wallet/app/src/main/structured_res/os/android/app/subapp/wallet_runtime/wallet_segment/age/sub_segment/kids/sub_segment/all/developer/bitdubai/version_1/drawable-xxhdpi/usd_1.jpg"), "image/*");
            editIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(editIntent, null));


            return true;
        }
    }
}