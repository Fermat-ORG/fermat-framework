package com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

/**
 * Created by Natalia on 26/12/2014.
 */
public class ReceiveAllFragment extends android.app.Fragment {

 View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] notes;
    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;


    public static ReceiveAllFragment newInstance() {
        ReceiveAllFragment f = new ReceiveAllFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        final ViewGroup actionBarLayout = (ViewGroup) getActivity().getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_received_history_action_bar,
                null);

        // Set up your ActionBar
        String[] contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);
        int tagId = MyApplication.getTagId();
        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contacts[tagId].toString());

        tv = (TextView) actionBarLayout.findViewById(R.id.activity_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());

        ImageView profile_picture = (ImageView) actionBarLayout.findViewById(R.id.profile_picture);
        switch (tagId)
        {
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
//find de tag id,
        contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"","$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};

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
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_receive_all, container, false);
        int tagId = MyApplication.getTagId();

        amounts = transactions_amounts[tagId];
        whens = transactions_whens[tagId];
        notes = transactions[tagId];

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(notes, transactions));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] notes;
        private String[][] transactions;

        public ExpandableListAdapter(String[] notes, String[][] transactions) {
            this.notes = notes;
            this.transactions = transactions;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return notes.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return transactions[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return notes[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return transactions[groupPosition][childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;
            int tagId = MyApplication.getTagId();
            if (groupPosition == 0)
            {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_receive_all_list_header, parent, false);

                ImageView  icon_receive_form_contact = (ImageView) convertView.findViewById(R.id.icon_receive_form_contact);
                icon_receive_form_contact.setTag(groupPosition);
                icon_receive_form_contact.setTag("ReceiveFromContactActivity|" + tagId + "-" + groupPosition);

                ImageView  icon_chat_over_trx = (ImageView) convertView.findViewById(R.id.icon_chat_over_trx);
                icon_chat_over_trx.setTag("ChatOverTrxActivity|" + tagId + "|" + groupPosition);

                holder = new ViewHolder();

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(amounts[groupPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                when.text.setTypeface(MyApplication.getDefaultTypeface());

                when.text.setText(whens[groupPosition].toString());

                note = new ViewHolder();
                note.text = (TextView) convertView.findViewById(R.id.notes);
                note.text.setTypeface(MyApplication.getDefaultTypeface());

                note.text.setText(notes[groupPosition]);
            }
            else {

                //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
                // if (convertView == null) {
                if (1 == 1) {
                    convertView = inf.inflate(R.layout.wallets_teens_fragment_receive_all_list_header, parent, false);

                    ImageView  icon_receive_form_contact = (ImageView) convertView.findViewById(R.id.icon_receive_form_contact);
                    icon_receive_form_contact.setTag(tagId + "-" + groupPosition);

                    ImageView  icon_chat_over_trx = (ImageView) convertView.findViewById(R.id.icon_chat_over_trx);
                    icon_chat_over_trx.setTag(tagId + "|" + groupPosition);

                    holder = new ViewHolder();

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);
                    amount.text.setTypeface(MyApplication.getDefaultTypeface());

                    amount.text.setText(amounts[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);
                    when.text.setTypeface(MyApplication.getDefaultTypeface());

                    when.text.setText(whens[groupPosition].toString());

                    note = new ViewHolder();
                    note.text = (TextView) convertView.findViewById(R.id.notes);
                    note.text.setTypeface(MyApplication.getDefaultTypeface());

                    note.text.setText(notes[groupPosition]);



                } else {
                    holder = (ViewHolder) convertView.getTag();
                }

                // holder.text.setText(getGroup(groupPosition).toString());
            }

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class ViewHolder {
            TextView text;
        }
    }
}