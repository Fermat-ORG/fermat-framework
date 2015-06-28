package com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bitdubai.fermat.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;


/**
 * Created by Natalia on 24/12/2014.
 */
public class ReceiveFromContactFragment  extends android.app.Fragment {


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


    public static ReceiveFromContactFragment newInstance() {
        ReceiveFromContactFragment f = new ReceiveFromContactFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"","$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};
        whens = new String[]{"","4 hours ago", "5 hours ago", "yesterday 11:00 PM", "24 Mar 14","3 Feb 14","1 year ago","1 year ago","2 year ago"};
        notes = new String[]{"","New telephone", "Old desk", "Car oil", "Sandwich","Headphones","Computer monitor","Pen","Apartment in Dubai"};
        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{
                {},
                {"New telephone","Hot dog","Telephone credit","Coffee"},
                {"Old desk","Flat rent","New glasses","House in Europe","Coffee","Gum"},
                {"Car oil","Headphones","Apartment"},
                {"Sandwich","New kitchen","Camera repair"},
                {"Headphones"},
                {"Computer monitor","New car"},
                {"Pen"},
                {"Apartment in Dubai"}
        };
        transactions_amounts = new String[][]{

                {},
                {"$200.00", "$3.00", "$460.00", "$2.00", "$1.5"},
                {"$3,000.00", "$34,200.00", "$4,500.00", "$4,000,000", "$2,00.00", "$0.50"},
                {"$400,00", "$43.00", "$350,000.00"},
                {"$3.00", "$55,000.00", "$7,500.00"},
                {"$45.00"},
                {"$600.00","$5050.00"},
                {"$50.00"},
                {"$80,000.00"}

        };

        transactions_whens = new String[][]{

                {},
                {"4 hours ago","8 hours ago","yesterday 10:33 PM","yesterday 9:33 PM"},
                {"5 hours ago","yesterday","20 Sep 14","16 Sep 14","13 Sep 14","12 Sep 14"},
                {"yesterday 11:00 PM","23 May 14", "12 May 14"},
                {"24 Mar 14","15 Apr 14","2 years ago"},
                {"3 Feb 14"},
                {"1 year ago","1 year ago"},
                {"1 year ago"},
                {"2 years ago"}


        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_receive_from_contact, container, false);

        String[] tagId = MyApplication.getChildId().split("-");
        TextView tv;

        tv = (TextView) rootView.findViewById(R.id.notes);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

        tv = (TextView) rootView.findViewById(R.id.amount);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions_amounts[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);
        tv.requestFocus();

        tv = (TextView) rootView.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[Integer.parseInt(tagId[0])]);

        ImageView profile_picture = (ImageView) rootView.findViewById(R.id.profile_picture);
        switch (Integer.parseInt(tagId[0])) {

            case 1:
                profile_picture.setImageResource(R.drawable.lucia_profile_picture);
                break;
            case 2:
                profile_picture.setImageResource(R.drawable.juan_profile_picture);
                break;
            case 3:
                profile_picture.setImageResource(R.drawable.karina_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.simon_profile_picture);
                break;
            case 5:
                profile_picture.setImageResource(R.drawable.celine_profile_picture);
                break;
            case 6:
                profile_picture.setImageResource(R.drawable.taylor_profile_picture);
                break;
            case 7:
                profile_picture.setImageResource(R.drawable.stephani_profile_picture);
                break;
            case 8:
                profile_picture.setImageResource(R.drawable.kimberly_profile_picture);
                break;
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}


