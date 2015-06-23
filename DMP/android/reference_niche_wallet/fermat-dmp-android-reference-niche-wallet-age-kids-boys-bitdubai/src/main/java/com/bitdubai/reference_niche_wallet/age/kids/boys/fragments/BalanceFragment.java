package com.bitdubai.reference_niche_wallet.age.kids.boys.fragments;

/**
 * Created by ciencias on 25.11.14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.content.ClipData;
import android.view.DragEvent;
import android.view.MotionEvent;

import com.bitdubai.fermat_dmp.wallet_runtime.R;

public class BalanceFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;
    View rootView;

    private String[] tickets;
    private String[] countries;

    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;


    public static BalanceFragment newInstance(int position) {
        BalanceFragment f = new BalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
        tickets = new String[]{"usd_5", "usd_10", "usd_20", "usd_100", "usd_1", "usd_5", "usd_5", "usd_10", "usd_20"};

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.wallets_kids_fragment_balance, container, false); //Contains empty RelativeLayout

        ImageView money = (ImageView)view.findViewById(R.id.ticket1);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket2);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket3);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket4);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());

        money = (ImageView)view.findViewById(R.id.ticket5);
        money.setOnTouchListener(new dragTouchListener());
        money.setOnDragListener(new dropListener());
        return view;
    }



    private final class dragTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class dropListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    // v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                  /*  View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) owner;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    // v.setBackgroundDrawable(normalShape);
                /*    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) owner;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);*/
                    break;
                default:
                    break;
            }
            return true;
        }

    }

}