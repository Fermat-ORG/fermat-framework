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


public  class ShopsAffiliatedShopsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] addresses;

    private String[] directions;

    private String[] name;

    private String[] phone;

    private String[] End;

    private String[][] Base;

    private String[] Start;


    public static ShopsAffiliatedShopsFragment newInstance(int position) {ShopsAffiliatedShopsFragment f = new ShopsAffiliatedShopsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addresses = new String[]{"620 8th Ave","518 9th ave.#1","502 9th ave.","27 W. 38th st.","506 9th ave."};

        directions = new String[]{"delivery.com","dunkindonuts.com","bebablue.com","havanany.com","nycfinecigars.com"};

        phone = new String[]{"+1 800-671-4332","+1 212-714-0858","+1 212-594-9462","+1 212-239-7019","+1 646-964-4964",};

        name = new String[]{"Pomodoro Restaurant","Dunkin' Donuts","Beba Blue Salon","Havana New York","NYC Fine Cigars"};

        End = new String[]{
                "15/01/16",
                "01/01/16",
                "31/12/15",
                "31/06/15",
                "20/07/15",


        };
        Start = new String[]{
                "14/01/15",
                "01/01/15",
                "30/11/14",
                "31/06/14",
                "19/07/13",


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
        convertView = inf.inflate(R.layout.affiliated_shops_header, parent, false);

            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
            convertView = inf.inflate(R.layout.affiliated_shops_header, parent, false);
            ViewHolder address;
            ViewHolder direction;
            ViewHolder start;
            ViewHolder names;
            ViewHolder Phone;
            ViewHolder end;
            ImageView photo = (ImageView) convertView.findViewById(R.id.store_image);

            switch (groupPosition){
                case 0:
                    photo.setImageResource(R.drawable.store_4);
                    break;
                case 1:
                    photo.setImageResource(R.drawable.store_9);
                    break;
                case 2:
                    photo.setImageResource(R.drawable.store_6);
                    break;
                case 3:
                    photo.setImageResource(R.drawable.store_16);
                    break;
                case 4:
                    photo.setImageResource(R.drawable.store_2);
                    break;
            }

            names = new ViewHolder();
            names.text = (TextView) convertView.findViewById(R.id.store_name);
            names.text.setTypeface(MyApplication.getDefaultTypeface());
            names.text.setText(name[groupPosition]);

            end = new ViewHolder();
            end.text = (TextView) convertView.findViewById(R.id.deal_end);
            end.text.setTypeface(MyApplication.getDefaultTypeface());
            end.text.setText(End[groupPosition]);

            Phone = new ViewHolder();
            Phone.text = (TextView) convertView.findViewById(R.id.store_number);
            Phone.text.setTypeface(MyApplication.getDefaultTypeface());
            Phone.text.setText(phone[groupPosition]);

            start = new ViewHolder();
            start.text = (TextView) convertView.findViewById(R.id.deal_start);
            start.text.setTypeface(MyApplication.getDefaultTypeface());
            start.text.setText(Start[groupPosition]);


            direction = new ViewHolder();
            direction.text = (TextView) convertView.findViewById(R.id.store_direction);
            direction.text.setTypeface(MyApplication.getDefaultTypeface());
            direction.text.setText(directions[groupPosition]);

            address = new ViewHolder();
            address.text = (TextView) convertView.findViewById(R.id.store_address);
            address.text.setTypeface(MyApplication.getDefaultTypeface());
            address.text.setText(addresses[groupPosition]);

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
