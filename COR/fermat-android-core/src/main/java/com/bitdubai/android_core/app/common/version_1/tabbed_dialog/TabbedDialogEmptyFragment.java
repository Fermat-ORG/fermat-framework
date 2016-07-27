package com.bitdubai.android_core.app.common.version_1.tabbed_dialog;

/**
 * Created by ciencias on 25.11.14.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.bitdubai.fermat.R;

public class TabbedDialogEmptyFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static TabbedDialogEmptyFragment newInstance(int position) {
        TabbedDialogEmptyFragment f = new TabbedDialogEmptyFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        FrameLayout fl = new FrameLayout(getActivity());
        fl.setLayoutParams(params);

        final int margin = 250;//(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
        //  .getDisplayMetrics());

        TextView v = new TextView(getActivity());
        params.setMargins(margin, margin, margin, margin);
        v.setLayoutParams(params);
        v.setLayoutParams(params);
        v.setGravity(Gravity.CENTER);
        v.setBackgroundResource(R.drawable.background_card);
        v.setText(new StringBuilder().append("CARD ").append(position + 1).toString());

        fl.addView(v);
        return fl;
    }

}