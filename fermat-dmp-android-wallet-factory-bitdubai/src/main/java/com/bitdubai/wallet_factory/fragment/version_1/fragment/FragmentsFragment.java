package com.bitdubai.wallet_factory.fragment.version_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.wallet_factory.R;
import com.bitdubai.wallet_factory.common.MyApplication;

/**
 * Created by Natalia on 19/12/2014.
 */
public class FragmentsFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] item;
    private String[][] fragment_item;



    public static FragmentsFragment newInstance(int position) {
        FragmentsFragment f = new FragmentsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = new String[]{
                "Account Detail Account",
                "Account Detail All",
                "Account Detail Credits",
                "Account Detail Debits",
                "Account Detail Filters",
                "Available Balance",
                "Balance",
                "Chat Over Receive Transaction",
                "Chat Over Transaction",
                "Chat With Contact",
                "Contacts",
                "Daily Discounts",
        };
        fragment_item = new String[][]{

                { },
                { },
                { },
                { },
                { },
                { },
                { },
                { },
                { },
                { },
                { },
                { },
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallet_factory_fragment_inflater, container, false);



        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(item, fragment_item));
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
        private String[] item;
        private String[][] fragment_item;

        public ExpandableListAdapter(String[] items, String[][] fragment_items) {
            this.item = items;
            this.fragment_item = fragment_items;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return item.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return fragment_item[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return item[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return fragment_item[groupPosition][childPosition];
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

            ViewHolder item;
            ImageView profile_picture;


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            convertView = inf.inflate(R.layout.wallet_factory_drawers_detail_fragment, parent, false);

            item = new ViewHolder();
            item.text.setTypeface(MyApplication.getDefaultTypeface());
            item.text.setText(fragment_item[groupPosition][childPosition].toString());

            return convertView;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder Item;
            ViewHolder Switch;

            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            convertView = inf.inflate(R.layout.wallet_factory_fragments_header_fragment, parent, false);

            Item = new ViewHolder();
            Item.text = (TextView) convertView.findViewById(R.id.items);
            Item.text.setTypeface(MyApplication.getDefaultTypeface());
            Item.text.setText(item[groupPosition].toString());

            Switch = new ViewHolder();
            Switch.text = (TextView) convertView.findViewById(R.id.switch_1);
            Switch.text.setTypeface(MyApplication.getDefaultTypeface());
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


