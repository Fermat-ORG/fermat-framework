package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.kids.sub_segment.all.developer.bitdubai.version_1.fragment;

/**
 * Created by ciencias on 25.11.14.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.SendToNewContactActivity;

public class ProfileCardFrontFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private int position;

    public static ProfileCardFrontFragment newInstance(int position) {
        ProfileCardFrontFragment f = new ProfileCardFrontFragment();
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
        View view;
        TextView name;
        view = inflater.inflate(R.layout.wallets_kids_fragment_profile_card_front, container, false); //Contains empty RelativeLayout

        name = (TextView) view.findViewById(R.id.user_name);
        name.setTypeface(MyApplication.getDefaultTypeface());
        name.setText("Johnny");

        return view;
    }


}