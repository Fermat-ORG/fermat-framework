package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import com.bitdubai.smartwallet.R;

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

        tickets = new String[]{"Céline Begnis", "Kimberly Brown", "Juan Luis R. Pons", "Karina Rodríguez","Guillermo Villanueva","Lucia Alarcon De Zamacona", "Luis Fernando Molina", "Mariana Duyos", "Pedro Perrotta", "Simon Cushing","Stephanie Himonidis","Taylor Backus", "Ginny Kaltabanis","Piper Faust","Deniz Caglar","Helen Nisbet","Dea Vanagan","Tim Hunter","Madeleine Jordan","Kate Bryan","Victoria Gandit","Jennifer Johnson","Robert Wint","Kevin Helms","Teddy Truchot","Hélène Derosier","John Smith","Caroline Mignaux","Guillaume Thery","Brant Cryder","Thomas Levy","Louis Stenz" };


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



        private final class dragTouchListener implements OnTouchListener {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        }

    private class dropListener implements OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
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
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    LinearLayout container = (LinearLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                   // v.setBackgroundDrawable(normalShape);
                    break;
                default:
                    break;
            }
            return true;
        }

    }


}