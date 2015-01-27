package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;


public  class ReceiveFromNewContactFragment extends android.app.Fragment {

    private static final String ARG_POSITION = "position";

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

    public static ReceiveFromNewContactFragment newInstance() {
        ReceiveFromNewContactFragment f = new ReceiveFromNewContactFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};
        whens = new String[]{"4 hours ago", "5 hours ago", "yesterday 11:00 PM", "24 Mar 14","3 Feb 14","1 year ago","1 year ago","2 year ago"};
        notes = new String[]{"New telephone", "Old desk", "Car oil", "Sandwich","Headphones","Computer monitor","Pen","Apartment in Dubai"};

        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
        };
        transactions_amounts = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},

        };

        transactions_whens = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},


        };

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_receive_from_new_contact, container, false);
        TextView tv;
        tv = (TextView) rootView.findViewById(R.id.notes);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv = (TextView) rootView.findViewById(R.id.amount);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.requestFocus();
        tv = (TextView) rootView.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


}