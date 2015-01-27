package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

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
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;

/**
 * Created by Natalia on 19/12/2014.
 */
public class ShopHistoryFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] totalAmount;
    private String[] dates;
    private String[] historyCount;
    private String[][] names;
    private String[] whens;
    private String[] notes;
    private String[][] items;
    private String[] pictures;
    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;



    public static ShopHistoryFragment newInstance(int position) {
        ShopHistoryFragment f = new ShopHistoryFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"14 units", "12 units", "8 units", "24 units"};
        amounts = new String[]{"$12.00", "$21.50", "$12.00", "$34.00"};
        whens = new String[]{"wallet discount","wallet discount","wallet discount","wallet discount","wallet discount","wallet discount",};
        notes = new String[]{ "total paid ", "total paid ", "total paid ", "total paid ", "total paid "};
        totalAmount = new String[]{"$16.00", "$24.00", "$15.50", "$36.00"};
        historyCount = new String[] {"-$4.00", "-$2.50", "-$2.50", "-$2.00"};
        pictures = new String[]{"product_14_history", "product_8_history", "product_13_history", "product_2_history"};
        dates = new String[]{"yesterday","2 days ago","4 day ago","4 day ago"};


        transactions = new String[][]{
                {"$3.00","$2.00"},
                {"$2.00","$1.00","$1.00"},
                {"$2.50","$2.00"},
                {"$1.50"},
        };

        items = new String[][]{
                {"2 units","12 units"},{"4 units","4 units","4 units"},{"4 units","4 units"},{"24 units",""}
        };

        names = new String[][]{

                {"French Roll","Chocolate chips"},{"Caramel Chocolate Crunch","Chocolate with sparkles","Classic Glazed Chocolate"},
                {"Cinnamon Cake","Honey bran raisins"},
                {"Clasisc Iced Pink",""},
        };

        transactions_amounts = new String[][]{
                {"$6.00","$24.00"},
                {"$8.00","$4.00","$4.00"},
                {"$10.00","$8.00"},
                {"$36.00",""},
        };

        transactions_whens = new String[][]{

                {"Quantity","Quantity"},
                {"Quantity","Quantity","Quantity"},
                {"Quantity","Quantity"},
                {"Quantity",},
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_history_store, container, false);



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
/*
                Intent intent;
                intent = new Intent(getActivity(), SentDetailActivity.class);
                startActivity(intent);*/
                return false;
            }
        });

        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
/*
                if (groupPosition == 0) {
                    Intent intent;
                    intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    startActivity(intent);
                    return false;
                }
                else*/
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
            ViewHolder item;
            ViewHolder name;
            ImageView profile_picture;


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_history_list_detail, parent, false);

                profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                switch (groupPosition)
                {
                    case 0:
                        switch (childPosition)
                        {
                            case 0:
                                profile_picture.setImageResource(R.drawable.product_14_history);
                                break;
                            case 1:
                                profile_picture.setImageResource(R.drawable.product_11_history);
                                break;
                        }
                        break;
                    case 1:
                        switch (childPosition)
                        {
                            case 0:
                                profile_picture.setImageResource(R.drawable.product_8_history);
                                break;
                            case 1:
                                profile_picture.setImageResource(R.drawable.product_9_history);
                                break;
                            case 2:
                                profile_picture.setImageResource(R.drawable.product_3_history);
                        }

                        break;
                    case 2:
                        switch (childPosition)
                        {
                            case 0:
                                profile_picture.setImageResource(R.drawable.product_13_history);
                                break;
                            case 1:
                                profile_picture.setImageResource(R.drawable.product_12_history);
                                break;
                        }
                        break;
                    case 3:
                        switch (childPosition)
                        {
                            case 0:
                                profile_picture.setImageResource(R.drawable.product_2_history);
                                break;
                        }
                        break;

                }

                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                name = new ViewHolder();
                name.text = (TextView) convertView.findViewById(R.id.contact_name);
                name.text.setTypeface(MyApplication.getDefaultTypeface());
                name.text.setText(names[groupPosition][childPosition].toString());

                item = new ViewHolder();
                item.text = (TextView) convertView.findViewById(R.id.name);
                item.text.setTypeface(MyApplication.getDefaultTypeface());
                item.text.setText(items[groupPosition][childPosition].toString());



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
            ImageView send_picture;
            ViewHolder total;
            ViewHolder date;
            ViewHolder history;



                //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
                // if (convertView == null) {
                if (1 == 1) {
                    convertView = inf.inflate(R.layout.wallets_teens_fragment_history_list_header, parent, false);


                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                    holder.text.setTypeface(MyApplication.getDefaultTypeface());
                    convertView.setTag(holder);

                    date = new ViewHolder();
                    date.text = (TextView) convertView.findViewById(R.id.date);
                    date.text.setTypeface(MyApplication.getDefaultTypeface());
                    date.text.setText(dates[groupPosition].toString());

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);
                    amount.text.setTypeface(MyApplication.getDefaultTypeface());
                    amount.text.setText(amounts[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);
                    when.text.setTypeface(MyApplication.getDefaultTypeface());
                    when.text.setText(whens[groupPosition].toString());

                    total = new ViewHolder();
                    total.text = (TextView) convertView.findViewById(R.id.total_amount);
                    total.text.setTypeface(MyApplication.getDefaultTypeface());
                    total.text.setText(totalAmount[groupPosition].toString());

                    history = new ViewHolder();
                    history.text = (TextView) convertView.findViewById(R.id.history_count);
                    history.text.setTypeface(MyApplication.getDefaultTypeface());
                    history.text.setText(historyCount[groupPosition].toString());

                    note = new ViewHolder();
                    note.text = (TextView) convertView.findViewById(R.id.notes);
                    note.text.setTypeface(MyApplication.getDefaultTypeface());
                    note.text.setText(notes[groupPosition].toString());

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


