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

/**
 * Created by Natalia on 22/12/2014.
 */
public class WeeklyDiscountsFragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] dates;
    private String[] adiscount_count;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;


    public static WeeklyDiscountsFragment newInstance() {
        WeeklyDiscountsFragment f = new WeeklyDiscountsFragment();
        return f;
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dates = new String[]{"Week 1", "Week 2", "Week 3", "Week 4","Week5" };
        adiscount_count = new String[]{"6","-9","0","10","2","5","-1"};

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
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_weekly_discounts, container, false);


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

            ImageView discount_picture;

            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_weekly_discounts_list_item, parent, false);

                discount_picture = (ImageView) convertView.findViewById(R.id.discount_picture);

                if(Integer.parseInt(adiscount_count[groupPosition]) > 0) {

                    discount_picture.setImageResource(R.drawable.account_type_current_small);


                }else {
                    if(Integer.parseInt(adiscount_count[groupPosition]) < 0) {
                        discount_picture.setImageResource(R.drawable.account_type_savings_1_small);
                    }
                    else
                    {
                        if(Integer.parseInt(adiscount_count[groupPosition]) == 0) {
                            discount_picture.setImageResource(R.drawable.account_type_savings_2_small);
                        }
                    }
                }


                date= new ViewHolder();
                date.text = (TextView) convertView.findViewById(R.id.date);
                date.text.setTypeface(MyApplication.getDefaultTypeface());
                date.text.setText(dates[groupPosition].toString());

                count = new ViewHolder();
                count.text = (TextView) convertView.findViewById(R.id.discount_count);
                count.text.setTypeface(MyApplication.getDefaultTypeface());
                count.text.setText(adiscount_count[groupPosition].toString());


                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);


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