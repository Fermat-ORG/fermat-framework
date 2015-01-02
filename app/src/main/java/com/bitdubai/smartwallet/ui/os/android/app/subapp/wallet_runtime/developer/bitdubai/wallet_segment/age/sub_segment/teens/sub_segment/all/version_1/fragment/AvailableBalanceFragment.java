package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.developer.bitdubai.wallet_segment.age.sub_segment.teens.sub_segment.all.version_1.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.SentDetailActivity;


public  class AvailableBalanceFragment extends android.app.Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] availabilities;
    private String[] types;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] crypto_amounts;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;




    public static AvailableBalanceFragment newInstance(int position) {
        AvailableBalanceFragment f = new AvailableBalanceFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        availabilities = new String[]{ "10.05%", "-1.23%", "7.30%", "2.25%", "-7.04%","11.84%","-5.52%"};
        types = new String[]{ "Received $200.00 ", "Received $3,000.00", "Refill $350.00", "Refill $1,500.00", "Received $400.00","Refill $250.00","received $600.00"};
        contacts = new String[]{"Lucia Alarcon De Zamacona", "Juan Luis R. Pons","Kalustyan´s","Kings Super Market", "Karina Rodriguez","D´Agostino", "Taylor Backus"};
        amounts = new String[]{"$90.00", "$2,000.00", "$25.00", "$1,500.00","$400.00","$0.00","$600.00"};
        whens = new String[]{"4 hours ago", "5 hours ago", "yesterday", "yesterday","31 dec 14","5 sep 14","1 year ago"};
        notes = new String[]{"New telephone", "Old desk", "For electricity bill", "for this week expenses","Car oil", "Refill test","Computer monitor"};
        pictures = new String[]{"lucia_profile_picture", "juan_profile_picture", "refill_2", "refill_4","karina_profile_picture","refill_1","taylor_profile_picture"};
        crypto_amounts = new String[]{"mBTC 10.3475","mBTC 150.8347","mBTC 15.9304","mBTC 8.1923","mBTC 20.5743","mBTC 12.8724","mBTC 30.4365"};

        transactions = new String[][]{
                {},
                {},
                {},
                {}
        };

        transactions_amounts = new String[][]{
                {},
                {},
                {},
                {}
        };

        transactions_whens = new String[][]{
                {},
                {},
                {},
                {}
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_available_balance, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent;
                intent = new Intent(getActivity(), SentDetailActivity.class);
                startActivity(intent);

                return true;
            }
        });

         lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 0) {
                    return false;
                }
                else
                {
                    return false;
                }
            }
        });


    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] contacts;
        private String[][] transactions;

        public ExpandableListAdapter(String[] contacts, String[][] transactions) {
            this.contacts = contacts;
            this.transactions = transactions;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return contacts.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return transactions[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return contacts[groupPosition];
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

            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_available_balance_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                when.text.setTypeface(MyApplication.getDefaultTypeface());

                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;
            ImageView profile_picture;
            ViewHolder type;
            ViewHolder percentage;
            ViewHolder crypto_amount;
            ImageView frame_tx_record;


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototipo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_available_balance_list_header, parent, false);

                profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);


                switch (groupPosition)
                {
                    case 0:
                        profile_picture.setImageResource(R.drawable.lucia_profile_picture);
                        break;
                    case 1:
                        profile_picture.setImageResource(R.drawable.juan_profile_picture);
                        break;
                    case 2:
                        profile_picture.setImageResource(R.drawable.refill_2);
                        break;
                    case 3:
                        profile_picture.setImageResource(R.drawable.refill_4);
                        break;
                    case 4:
                        profile_picture.setImageResource(R.drawable.karina_profile_picture);
                        break;
                    case 5:
                        profile_picture.setImageResource(R.drawable.refill_1);
                        break;
                    case 6:
                        profile_picture.setImageResource(R.drawable.taylor_profile_picture);
                        break;
                }



                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(amounts[groupPosition].toString());

                percentage = new ViewHolder();
                percentage.text = (TextView) convertView.findViewById(R.id.availability);
                percentage.text.setTypeface(MyApplication.getDefaultTypeface());
                percentage.text.setText(availabilities[groupPosition].toString());

                type = new ViewHolder();
                type.text = (TextView) convertView.findViewById(R.id.type);
                type.text.setTypeface(MyApplication.getDefaultTypeface());
                type.text.setText(types[groupPosition].toString());

                crypto_amount = new ViewHolder();
                crypto_amount.text = (TextView) convertView.findViewById(R.id.crypto_amount);
                crypto_amount.text.setTypeface(MyApplication.getDefaultTypeface());
                crypto_amount.text.setText(crypto_amounts[groupPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                when.text.setTypeface(MyApplication.getDefaultTypeface());

                when.text.setText(whens[groupPosition].toString());

                note = new ViewHolder();
                note.text = (TextView) convertView.findViewById(R.id.notes);
                note.text.setTypeface(MyApplication.getDefaultTypeface());

                note.text.setText(notes[groupPosition].toString());

                frame_tx_record = (ImageView) convertView.findViewById(R.id.frame_tx_record);


                switch (groupPosition)
                {
                    case 2:case 3:case 5:case 7:case 8:
                    frame_tx_record.setBackgroundResource(R.drawable.object_frame_3x1_frozen);
                    holder.text.setTextColor(Color.WHITE);
                    holder.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    amount.text.setTextColor(Color.WHITE);
                    amount.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    percentage.text.setTextColor(Color.WHITE);
                    percentage.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    type.text.setTextColor(Color.WHITE);
                    type.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    crypto_amount.text.setTextColor(Color.WHITE);
                    crypto_amount.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    when.text.setTextColor(Color.WHITE);
                    when.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    note.text.setTextColor(Color.WHITE);
                    note.text.setTypeface(MyApplication.getDefaultTypeface(),1);
                    break;

                }

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getGroup(groupPosition).toString());




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
