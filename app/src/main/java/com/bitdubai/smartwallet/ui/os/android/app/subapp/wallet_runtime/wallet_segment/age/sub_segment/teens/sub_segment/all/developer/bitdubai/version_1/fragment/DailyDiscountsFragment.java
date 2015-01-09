package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.classes.MyApplication;

/**
 * Created by Natalia on 22/12/2014.
 */
public class DailyDiscountsFragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] dates;
    private String[] saved_money;

    private String[] adiscount_count;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;


    public static DailyDiscountsFragment newInstance() {
        DailyDiscountsFragment f = new DailyDiscountsFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dates = new String[]{"today", "yesterday", "Wed 21 Feb", "Tue 20 Feb","Mon 19 Feb","Sun 18 Feb", "Sat 17 Feb" };
        adiscount_count = new String[]{"1.52","2.25","12.26","3.73","8.48","9.76","1.74"};
        saved_money = new String[] {"$2.54", "$4.55", "$50.24","$6.52","$23.42","$26.46","$2.47"};

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
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_daily_discounts, container, false);


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(dates, transactions));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] dates;
        private String[][] transactions;

        public ExpandableListAdapter(String[] dates, String[][] transactions) {
            this.dates = dates;
            this.transactions = transactions;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return dates.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return transactions[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return dates[groupPosition];
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

            ViewHolder  holder = new ViewHolder();



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 != 1) {

                    holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder count;
            ViewHolder date;
            ViewHolder Average;
            ViewHolder savedMoney;

            ImageView discount_picture;

            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_daily_discounts_list_item, parent, false);



                date= new ViewHolder();
                date.text = (TextView) convertView.findViewById(R.id.date);
                date.text.setTypeface(MyApplication.getDefaultTypeface());
                date.text.setText(dates[groupPosition].toString());

                Average = new ViewHolder();
                Average.text = (TextView) convertView.findViewById(R.id.average);
                Average.text.setTypeface(MyApplication.getDefaultTypeface());

                count = new ViewHolder();
                count.text = (TextView) convertView.findViewById(R.id.discount_count);
                count.text.setTypeface(MyApplication.getDefaultTypeface());
                count.text.setText(adiscount_count[groupPosition].toString() + "%");

                savedMoney = new ViewHolder();
                savedMoney.text = (TextView) convertView.findViewById(R.id.money_saved);
                savedMoney.text.setTypeface(MyApplication.getDefaultTypeface());
                savedMoney.text.setText(saved_money[groupPosition].toString());

            } else {
                holder = (ViewHolder) convertView.getTag();
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