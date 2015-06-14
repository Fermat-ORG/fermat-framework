package com.bitdubai.wallet_publisher.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.wallet_publisher.R;
import com.bitdubai.wallet_publisher.common.classes.MyApplication;


public  class PublisherScripsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] items;

    private String[] unit_amount;

    private String[] Investment;

    private String[] name;

    private String[] description;

    private String[] Per_User;

    private String[][] Base;

    private String[][] Target;

    private String[][] Received_by;

    private String[][] Seen;

    private String[][] Sold;

    private String[][] Used;

    private String[][] Cashed_out;

    private String[][] Shops;

    private String[][] Affiliated;

    private String[][] Received;

    public static PublisherScripsFragment newInstance(int position) {
        PublisherScripsFragment f = new PublisherScripsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //the strings have to be modified with meaningful information.

        items = new String[]{"Voucher","Voucher","Voucher","Voucher"};

        unit_amount = new String[]{"12"+"M","20"+"M","15"+"M","5"+"M"};

        Per_User = new String[]{"$"+"10.00","$"+"5.00","$"+"12.00","$"+"16.00"};

        Investment = new String[]{"$"+"120"+"M","$"+"100"+"M","$"+"180"+"M","$"+"80"+"M"};

        description = new String[]{"Description","Description","Description","Description"};

        name = new String[]{"Voucher No.4","Voucher No.3","Voucher No.2","Voucher No.1"};

        Base = new String[][]{

                {"20"+"M"},
                {"20"+"M"},
                {"15"+"M"},
                {"5"+"M"}
        };
        Target = new String[][]{
                {"20"+"M"+" "+"100"+"%"},
                {"20"+"M"+" "+"100"+"%"},
                {"10"+"M"+" "+"66.6"+"%"},
                {"5"+"M"+" "+"100"+"%"}
        };
        Received_by = new String[][]{
                {"20"+"M"+" "+"100"+"%"},
                {"20"+"M"+" "+"100"+"%"},
                {"8.8"+"M"+" "+"58.6"+"%"},
                {"4.7"+"M"+" "+"94"+"%"}
        };
        Seen = new String[][]{
                {"20"+"M"+" "+"100"+"%"},
                {"20"+"M"+" "+"100"+"%"},
                {"6"+"M"+" "+"40"+"%"},
                {"4.2"+"M"+" "+"84"+"%"}
        };
        Sold = new String[][]{
                {"20"+"M"+" "+"100"+"%"},
                {"20"+"M"+" "+"100"+"%"},
                {"2.3"+"M"+" "+"15.3"+"%"},
                {"1.1"+"M"+" "+"22"+"%"}

        };
        Cashed_out = new String[][]{
                {"20"+"M"+" "+"100"+"%"},
                {"20"+"M"+" "+"100"+"%"},
                {"400"+"K"+" "+"2.6"+"%"},
                {"900"+"K"+" "+"18"+"%"},
        };
        Used = new String[][]{
                {"20"+"M"+" "+"100"+"%"},
                {"20"+"M"+" "+"100"+"%"},
                {"4"+"M"+" "+"26.6"+"%"},
                {"2.3"+"M"+" "+"46"+"%"},
        };
        Shops = new String[][]{
                {"110"},
                {"140"},
                {"70"},
                {"90"},
        };
        Affiliated = new String[][]{
                {"171"},
                {"171"},
                {"171"},
                {"171"},
        };
        Received = new String[][]{
                {"28"+"M"},
                {"28"+"M"},
                {"28"+"M"},
                {"36.8"+"M"},
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.publisher_expandablelisview_inflater, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(items, Base));
        lv.setGroupIndicator(null);

        lv.setOnItemClickListener(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] items;
        private String[][] Base;

        public ExpandableListAdapter(String[] items, String[][] Base) {
            this.items = items;
            this.Base = Base;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return items.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return Base[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return items[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return Base[groupPosition][childPosition];
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
        convertView = inf.inflate(R.layout.publisher_scrip_detail, parent, false);
            ViewHolder User_Base;
            ViewHolder User_Target;
            ViewHolder received_by;
            ViewHolder text_1;
            ViewHolder text_2;
            ViewHolder text_3;
            ViewHolder text_4;
            ViewHolder text_5;
            ViewHolder text_6;
            ViewHolder text_7;
            ViewHolder text_8;
            ViewHolder text_9;
            ViewHolder text_10;
            ViewHolder used;
            ViewHolder sold;
            ViewHolder cashed_out;
            ViewHolder seen;
            ViewHolder received;
            ViewHolder affiliated;
            ViewHolder shops;

            text_1 = new ViewHolder();
            text_1.text = (TextView) convertView.findViewById(R.id.text_1);
            text_1.text.setTypeface(MyApplication.getDefaultTypeface());

            text_2 = new ViewHolder();
            text_2.text = (TextView) convertView.findViewById(R.id.text_2);
            text_2.text.setTypeface(MyApplication.getDefaultTypeface());

            text_3 = new ViewHolder();
            text_3.text = (TextView) convertView.findViewById(R.id.text_3);
            text_3.text.setTypeface(MyApplication.getDefaultTypeface());

            text_4 = new ViewHolder();
            text_4.text = (TextView) convertView.findViewById(R.id.text_4);
            text_4.text.setTypeface(MyApplication.getDefaultTypeface());

            text_5 = new ViewHolder();
            text_5.text = (TextView) convertView.findViewById(R.id.text_5);
            text_5.text.setTypeface(MyApplication.getDefaultTypeface());

            text_6 = new ViewHolder();
            text_6.text = (TextView) convertView.findViewById(R.id.text_6);
            text_6.text.setTypeface(MyApplication.getDefaultTypeface());

            text_7 = new ViewHolder();
            text_7.text = (TextView) convertView.findViewById(R.id.text_7);
            text_7.text.setTypeface(MyApplication.getDefaultTypeface());

            text_8 = new ViewHolder();
            text_8.text = (TextView) convertView.findViewById(R.id.text_8);
            text_8.text.setTypeface(MyApplication.getDefaultTypeface());

            text_9 = new ViewHolder();
            text_9.text = (TextView) convertView.findViewById(R.id.text_9);
            text_9.text.setTypeface(MyApplication.getDefaultTypeface());

            text_10 = new ViewHolder();
            text_10.text = (TextView) convertView.findViewById(R.id.text_10);
            text_10.text.setTypeface(MyApplication.getDefaultTypeface());

            User_Base = new ViewHolder();
            User_Base.text = (TextView) convertView.findViewById(R.id.user_base);
            User_Base.text.setTypeface(MyApplication.getDefaultTypeface());
            User_Base.text.setText(Base[groupPosition][childPosition]);

            seen = new ViewHolder();
            seen.text = (TextView) convertView.findViewById(R.id.views_amount);
            seen.text.setTypeface(MyApplication.getDefaultTypeface());
            seen.text.setText(Seen[groupPosition][childPosition]);

            sold = new ViewHolder();
            sold.text = (TextView) convertView.findViewById(R.id.sold_amount);
            sold.text.setTypeface(MyApplication.getDefaultTypeface());
            sold.text.setText(Sold[groupPosition][childPosition]);

            cashed_out = new ViewHolder();
            cashed_out.text = (TextView) convertView.findViewById(R.id.cashed_out_amount);
            cashed_out.text.setTypeface(MyApplication.getDefaultTypeface());
            cashed_out.text.setText(Cashed_out[groupPosition][childPosition]);

            used = new ViewHolder();
            used.text = (TextView) convertView.findViewById(R.id.used_amount);
            used.text.setTypeface(MyApplication.getDefaultTypeface());
            used.text.setText(Used[groupPosition][childPosition]);

            User_Target = new ViewHolder();
            User_Target.text = (TextView) convertView.findViewById(R.id.user_target);
            User_Target.text.setTypeface(MyApplication.getDefaultTypeface());
            User_Target.text.setText(Target[groupPosition][childPosition]);

            received_by = new ViewHolder();
            received_by.text = (TextView) convertView.findViewById(R.id.users_reached);
            received_by.text.setTypeface(MyApplication.getDefaultTypeface());
            received_by.text.setText(Received_by[groupPosition][childPosition]);

            received = new ViewHolder();
            received.text = (TextView) convertView.findViewById(R.id.received_scrips);
            received.text.setTypeface(MyApplication.getDefaultTypeface());
            received.text.setText(Received[groupPosition][childPosition]);

            shops = new ViewHolder();
            shops.text = (TextView) convertView.findViewById(R.id.shops_amount);
            shops.text.setTypeface(MyApplication.getDefaultTypeface());
            shops.text.setText(Affiliated[groupPosition][childPosition]);

            affiliated = new ViewHolder();
            affiliated.text = (TextView) convertView.findViewById(R.id.acepting_shops);
            affiliated.text.setTypeface(MyApplication.getDefaultTypeface());
            affiliated.text.setText(Shops[groupPosition][childPosition]);

            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
            convertView = inf.inflate(R.layout.publisher_scrip_header, parent, false);
            ViewHolder item;
            ImageView image;
            ViewHolder Units;
            ViewHolder Invest;
            ViewHolder user;
            ViewHolder Name;
            ViewHolder Description;
            ViewHolder text_1;
            ViewHolder text_2;
            ViewHolder text_3;
            image = (ImageView) convertView.findViewById(R.id.card_icon);

            switch (groupPosition)
            {
                case 0:
                    image.setImageResource(R.drawable.voucher_image_4);
                    break;
                case 1:
                    image.setImageResource(R.drawable.voucher_image_3);
                    break;
                case 2:
                    image.setImageResource(R.drawable.voucher_image );
                    break;
                case 3:
                    image.setImageResource(R.drawable.voucher_image_1);
                    break;
            }

            item = new ViewHolder();
            item.text = (TextView) convertView.findViewById(R.id.scrip);
            item.text.setTypeface(MyApplication.getDefaultTypeface());
            item.text.setText(items[groupPosition].toString());

            Units = new ViewHolder();
            Units.text = (TextView) convertView.findViewById(R.id.units);
            Units.text.setTypeface(MyApplication.getDefaultTypeface());
            Units.text.setText(unit_amount[groupPosition].toString());

            Invest = new ViewHolder();
            Invest.text = (TextView) convertView.findViewById(R.id.investment);
            Invest.text.setTypeface(MyApplication.getDefaultTypeface());
            Invest.text.setText(Investment[groupPosition].toString());

            user = new ViewHolder();
            user.text = (TextView) convertView.findViewById(R.id.per_user);
            user.text.setTypeface(MyApplication.getDefaultTypeface());
            user.text.setText(Per_User[groupPosition].toString());

            Name = new ViewHolder();
            Name.text = (TextView) convertView.findViewById(R.id.scrip_name);
            Name.text.setTypeface(MyApplication.getDefaultTypeface());
            Name.text.setText(name[groupPosition].toString());

            Description = new ViewHolder();
            Description.text = (TextView) convertView.findViewById(R.id.scrip_description);
            Description.text.setTypeface(MyApplication.getDefaultTypeface());
            Description.text.setText(description[groupPosition].toString());

            text_1 = new ViewHolder();
            text_1.text = (TextView) convertView.findViewById(R.id.text_1);
            text_1.text.setTypeface(MyApplication.getDefaultTypeface());


            text_2 = new ViewHolder();
            text_2.text = (TextView) convertView.findViewById(R.id.text_2);
            text_2.text.setTypeface(MyApplication.getDefaultTypeface());


            text_3 = new ViewHolder();
            text_3.text = (TextView) convertView.findViewById(R.id.text_3);
            text_3.text.setTypeface(MyApplication.getDefaultTypeface());

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
