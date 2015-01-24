package com.bitdubai.smartwallet.ui.os.android.app.subapp.marketplace.version_1.fragment;


import android.content.Intent;
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
import com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_runtime.wallet_framework.version_1.activity.SendToNewContactActivity;


public  class ScripsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] items;

    private String[][] sub_items_1;

    private String[][] sub_items_2;


    public static ScripsFragment newInstance(int position) {
        ScripsFragment f = new ScripsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new String[]{"Voucher","Coupon","Gift card","Discount"};

        sub_items_1 = new String[][]{

                {"Create new voucher"},
                {"Create new coupon"},
                {"Create new gift card"},
                {"Create new discount"},
        };

        sub_items_2 = new String[][]{
                {"Released vouchers"},
                {"Released coupons"},
                {"Released gift cards"},
                {"Released discounts"}
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.marketplace_inflater, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(items, sub_items_1));
        lv.setGroupIndicator(null);

        lv.setOnItemClickListener(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] items;
        private String[][] sub_items_1;

        public ExpandableListAdapter(String[] items, String[][] sub_items_1) {
            this.items = items;
            this.sub_items_1 = sub_items_1;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return items.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return sub_items_1[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return items[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return sub_items_1[groupPosition][childPosition];
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
        convertView = inf.inflate(R.layout.marketplace_scrip_detail, parent, false);
        ViewHolder sub_1;
        ViewHolder sub_2;
            sub_1 = new ViewHolder();
            sub_1.text = (TextView) convertView.findViewById(R.id.subItem1);
            sub_1.text.setTypeface(MyApplication.getDefaultTypeface());
            sub_1.text.setText(sub_items_1[groupPosition][childPosition].toString());
            sub_2 = new ViewHolder();
            sub_2.text = (TextView) convertView.findViewById(R.id.subItem2);
            sub_2.text.setTypeface(MyApplication.getDefaultTypeface());
            sub_2.text.setText(sub_items_2[groupPosition][childPosition].toString());
            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        convertView = inf.inflate(R.layout.marketplace_scrip_header, parent, false);
        ViewHolder item;
        ImageView image;
        image = (ImageView) convertView.findViewById(R.id.icon);

            item = new ViewHolder();
            item.text = (TextView) convertView.findViewById(R.id.scrip);
            item.text.setTypeface(MyApplication.getDefaultTypeface());
            item.text.setText(items[groupPosition].toString());

            switch(groupPosition)
            {
                case 0:
                    image.setImageResource(R.drawable.ic_action_voucher_grey);
                    break;
                case 1:
                    image.setImageResource(R.drawable.ic_action_coupon_grey);
                    break;
                case 2:
                    image.setImageResource(R.drawable.ic_action_gift_card_grey);
                    break;
                case 3:
                    image.setImageResource(R.drawable.ic_action_discount_grey);
                    break;
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
