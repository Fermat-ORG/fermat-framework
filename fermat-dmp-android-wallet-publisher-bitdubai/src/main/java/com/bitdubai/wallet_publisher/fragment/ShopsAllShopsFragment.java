package com.bitdubai.wallet_publisher.fragment;


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

import com.bitdubai.wallet_publisher.R;
import com.bitdubai.wallet_publisher.common.classes.MyApplication;
import com.bitdubai.wallet_publisher.activity.ShopActivity;


public  class ShopsAllShopsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] addresses;

    private String[] directions;

    private String[] name;

    private String[] phone;

    private String[][] Base;


    public static ShopsAllShopsFragment newInstance(int position) {
        ShopsAllShopsFragment f = new ShopsAllShopsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addresses = new String[]{"502 9th Ave",
                "481 8th Ave",
                "481 8th Ave",
                "240 W 40th St",
                "20 W 38th St",
                "452 5th Ave",
                "22 W 38th St",
                "389 5th Ave",
                "32 W 39th St",
                "5 E 38th St",
                "1271 Broadway #1",};

        directions = new String[]{
                "fatsals.com",
                "snackeos.com",
                "ticktockdinerny.com",
                "newyorkerhotel.com",
                "nytstore.com",
                "theaustraliannyc.com",
                "panerabread.com",
                "mustangcafe.com",
                "pret.com",
                "subway.com",
                "butterfield8nyc.com"};

        phone = new String[]{"+1 212-792-6999",
                "+1 212-268-8444",
                "+1 212-971-0101",
                "+1 212-395-9280",
                "+1 212-869-8601",
                "+1 212-938-6950",
                "+1 212-354-5522",
                "+1 212-401-8686",
                "+1 212-719-4044",
                "+1 212-679-0646",
                "+1 212-944-0990",
        };

        name = new String[]{
                "Fat Sal's Pizza",
                "Snack EOS",
                "Tick Tock Diner",
                "The New Yorker Hotel",
                "The New York Times Store",
                "The Australian",
                "Panera Bread",
                "Mustang Cafe",
                "Pret A. Manger",
                "Subway",
                "Butterfield 8",
                };

        Base = new String[][]{

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
        lv.setAdapter(new ExpandableListAdapter(name, Base));
        lv.setGroupIndicator(null);

        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent intent;
                intent = new Intent(getActivity(), ShopActivity.class);
                startActivity(intent);
                return true;


            }
        });
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] name;
        private String[][] Base;

        public ExpandableListAdapter(String[] name, String[][] Base) {
            this.name = name;
            this.Base = Base;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return name.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return Base[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return name[groupPosition];
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
        convertView = null;

            return null;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
            convertView = inf.inflate(R.layout.all_shops_header, parent, false);
            ViewHolder address;
            ViewHolder direction;
            ViewHolder names;
            ImageView photo = (ImageView) convertView.findViewById(R.id.store_image);
            ViewHolder Phone;

            switch (groupPosition){
                case 0:
                    photo.setImageResource(R.drawable.store_3);
                    break;
                case 1:
                    photo.setImageResource(R.drawable.store_5);
                    break;
                case 2:
                    photo.setImageResource(R.drawable.store_7);
                    break;
                case 3:
                    photo.setImageResource(R.drawable.store_8);
                    break;
                case 4:
                    photo.setImageResource(R.drawable.store_1);
                    break;
                case 5:
                    photo.setImageResource(R.drawable.store_10);
                    break;
                case 6:
                    photo.setImageResource(R.drawable.store_11);
                    break;
                case 7:
                    photo.setImageResource(R.drawable.store_12);
                    break;
                case 8:
                    photo.setImageResource(R.drawable.store_13);
                    break;
                case 9:
                    photo.setImageResource(R.drawable.store_14);
                    break;
                case 10:
                    photo.setImageResource(R.drawable.store_15);
                    break;
            }

            names = new ViewHolder();
            names.text = (TextView) convertView.findViewById(R.id.store_name);
            names.text.setTypeface(MyApplication.getDefaultTypeface());
            names.text.setText(name[groupPosition].toString());

            Phone = new ViewHolder();
            Phone.text = (TextView) convertView.findViewById(R.id.store_number);
            Phone.text.setTypeface(MyApplication.getDefaultTypeface());
            Phone.text.setText(phone[groupPosition].toString());

            direction = new ViewHolder();
            direction.text = (TextView) convertView.findViewById(R.id.store_direction);
            direction.text.setTypeface(MyApplication.getDefaultTypeface());
            direction.text.setText(directions[groupPosition].toString());

            address = new ViewHolder();
            address.text = (TextView) convertView.findViewById(R.id.store_address);
            address.text.setTypeface(MyApplication.getDefaultTypeface());
            address.text.setText(addresses[groupPosition].toString());

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
