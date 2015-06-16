package com.bitdubai.niche_wallet.bitcoin_wallet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BitcoinSendToContactFragment extends android.app.Fragment {


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
    private String[] Discounted;
    private String[] Percentage;
    private String contact_id;



    public static BitcoinSendToContactFragment newInstance() {
        BitcoinSendToContactFragment f = new BitcoinSendToContactFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get contact Id
        contact_id = this.getArguments().get("contactId").toString();

        //MyApplication.changeColor(Color.parseColor("#F0E173"), super.getActivity().getResources());
        contacts = new String[]{ "", "Guillermo Villanueva", "Luis Fernando Molina", "Pedro Perrotta", "Mariana Duyos"};
        amounts = new String[]{ "", "$1,400.00", "$325.00", "$0.50", "$25.00"};
        whens = new String[]{ "", "2 hours ago", "3 min ago", "today 9:24 AM", "yesterday"};
        notes = new String[]{"", "Flat rent",  "Electricity bill", "Test address", "More pictures"};
        Percentage = new String[]{"","1%","10%","1%","0.8%"};
        Discounted = new String[]{"","$3.25","$140.00","$0.05","$0.10"};

        pictures = new String[]{"", "guillermo_profile_picture", "luis_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
                {"Flat rent","Flat rent","Flat rent","interest paid :(","Flat rent","Car repair","Invoice #2,356 that should have been paid on August"},
                {"Electricity bill","New chair","New desk"},
                {"Test address"},
                {"More pictures"}
        };

        transactions_amounts = new String[][]{

                {},
                {"BTC 1400.00","BTC 1200.00","$1400.00","$40.00","$1900.00","$10550.00","$1.00"},
                {"$325.00","$55.00","$420.00"},
                {"$0.50"},
                {"BTC 25.00"}
        };

        transactions_whens = new String[][]{

                {},
                {"2 hours ago ","1 months ago","2 months ago","4 months ago","4 months ago","5 months ago","6 months ago"},
                {"3 min ago","15 min ago","yesterday"},
                {"today 9:24 AM"},
                {"yesterday"}
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send_to_contact, container, false);


        String[] tagId = contact_id.split("\\|");
        TextView tv;
        //if tagId[1] = -1 no show data
        if (Integer.parseInt(tagId[1]) == -1) {

            tv = (TextView) rootView.findViewById(R.id.amount);
             tv.requestFocus();
            tv = (TextView) rootView.findViewById(R.id.contact_name);
            tv.setText(contacts[Integer.parseInt(tagId[0])]);


        }
        else {
            tv = (TextView) rootView.findViewById(R.id.notes);
            tv.setText(transactions[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);
            tv.setSelected(false);

            tv = (TextView) rootView.findViewById(R.id.amount);
            tv.setText(transactions_amounts[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

            tv.requestFocus();

        }

        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}