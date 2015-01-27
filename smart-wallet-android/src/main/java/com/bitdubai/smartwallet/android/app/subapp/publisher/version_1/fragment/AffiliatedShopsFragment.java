package com.bitdubai.smartwallet.android.app.subapp.publisher.version_1.fragment;


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
import com.bitdubai.smartwallet.android.app.common.version_1.classes.MyApplication;


public  class AffiliatedShopsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] addresses;

    private String[] directions;

    private String[] name;

    private String[][] Base;


    public static AffiliatedShopsFragment newInstance(int position) {
        AffiliatedShopsFragment f = new AffiliatedShopsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addresses = new String[]{"518 9th ave.#1","502 9th ave.","27 W. 38th st.","506 9th ave."};

        directions = new String[]{"delivery.com","bebablue.com","havanany.com","nycfinecigars.com"};

        name = new String[]{"Pomodoro Restaurant","Beba Blue Salon","Havana New York","NYC Fine Cigars"};

        Base = new String[][]{

                {"20"+"M"},
                {"20"+"M"},
                {"20"+"M"},
                {"20"+"M"}
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

        lv.setOnItemClickListener(null);

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
        convertView = inf.inflate(R.layout.affilited_shops_header, parent, false);

            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
            convertView = inf.inflate(R.layout.affilited_shops_header, parent, false);
            ViewHolder address;
            ViewHolder direction;
            ViewHolder names;
            ImageView photo;

            names = new ViewHolder();
            names.text = (TextView) convertView.findViewById(R.id.store_name);
            names.text.setTypeface(MyApplication.getDefaultTypeface());
            names.text.setText(name[groupPosition].toString());

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
