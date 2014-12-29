package com.bitdubai.smartwallet.wallets.teens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.walletframework.MyApplication;




public  class SendToContactFragment extends android.app.Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] Discounted;
    private String[] Percentage;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;



    public static SendToContactFragment newInstance() {
        SendToContactFragment f = new SendToContactFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        contacts = new String[]{ "", "Luis Fernando Molina", "Guillermo Villanueva", "Pedro Perrotta", "Mariana Duyos"};
        amounts = new String[]{ "", "$325.00", "$1,400.00", "$0.50", "$25.00"};
        whens = new String[]{ "", "3 min ago", "2 hours ago", "today 9:24 AM", "yesterday"};
        notes = new String[]{"",  "Electricity bill", "Flat rent", "Test address", "More pictures"};
        Percentage = new String[]{"0.00%","0.00%","0.00%","0.00%","0.00%"};
        Discounted = new String[]{"$0.00","$0.00","$0.00","$0.00","$0.00"};
        pictures = new String[]{"", "luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
                {"Electricity bill","New chair","New desk"},
                {"Flat rent","Flat rent","Flat rent","interest paid :(","Flat rent","Car repair","Invoice #2,356 that should have been paid on August"},
                {"Test address"},
                {"More pictures"}
        };

        transactions_amounts = new String[][]{

                {},
                {"$325.00","$55.00","$420.00"},
                {"$1,400.00","$1,200.00","$1,400.00","$40.00","$1,900.00","$10,550.00","$1.00"},
                {"$0.50"},
                {"$25.00"}
        };

        transactions_whens = new String[][]{

                {},
                {"3 min ago","15 min ago","yesterday"},
                {"2 hours ago","yesterday","last Friday","last Friday","14 May 14","11 May 14","5 Jan 14"},
                {"today 9:24 AM"},
                {"yesterday"}
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_send_to_contact, container, false);

        int tagId = MyApplication.getTagId();
        TextView tv;

        tv = (TextView) rootView.findViewById(R.id.notes);
        tv.setTypeface(MyApplication.getDefaultTypeface());


        tv = (TextView) rootView.findViewById(R.id.amount);
        tv.setTypeface(MyApplication.getDefaultTypeface());

        tv = (TextView) rootView.findViewById(R.id.percentage);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(Percentage[tagId]);

        tv = (TextView) rootView.findViewById(R.id.discounted);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(Discounted[tagId]);

        tv = (TextView) rootView.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[tagId]);

        ImageView profile_picture = (ImageView) rootView.findViewById(R.id.profile_picture);
        switch (tagId)
        {
            case 1:
                profile_picture.setImageResource(R.drawable.luis_profile_picture);

                break;
            case 2:
                profile_picture.setImageResource(R.drawable.guillermo_profile_picture);

                break;
            case 3:
                profile_picture.setImageResource(R.drawable.pedro_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.mariana_profile_picture);

                break;
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


}