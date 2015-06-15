package com.bitdubai.sub_app.wallet_factory.fragment.version_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.common.MyApplication;

/**
 * Created by Natalia on 19/12/2014.
 */
public class MenusFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] item;
    private String[][] menu_item;



    public static MenusFragment newInstance(int position) {
        MenusFragment f = new MenusFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = new String[]{
                "account detail menu",
                "accounts menu",
                "available balance menu",
                "chat over transaction menu",
                "contacts menu",
                "daily discounts menu",
                "edit personal profile menu",
                "send to contact menu",
                "framework menu",
                "monthly discounts menu",
        };
        menu_item = new String[][]{

                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"About","Preferences","Settings","Help"},
                {"Request sent","Request received"},
                {"About","Preferences","Settings","Help"},
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
        lv.setAdapter(new ExpandableListAdapter(item, menu_item));
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
        private String[][] menu_item;

        public ExpandableListAdapter(String[] items, String[][] menu_items) {
            this.item = items;
            this.menu_item = menu_items;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return item.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return menu_item[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return item[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return menu_item[groupPosition][childPosition];
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
            ViewHolder Switch;


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            convertView = inf.inflate(R.layout.wallet_factory_menus_detail_fragment, parent, false);


            item = new ViewHolder();
            item.text = (TextView) convertView.findViewById(R.id.items);
            item.text.setTypeface(MyApplication.getDefaultTypeface());
            item.text.setText(menu_item[groupPosition][childPosition].toString());

            Switch = new ViewHolder();
            Switch.text = (TextView) convertView.findViewById(R.id.switch_1);
            Switch.text.setTypeface(MyApplication.getDefaultTypeface());
            return convertView;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder Item;
            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            convertView = inf.inflate(R.layout.wallet_factory_menus_header_fragment, parent, false);

            Item = new ViewHolder();
            Item.text = (TextView) convertView.findViewById(R.id.description);
            Item.text.setTypeface(MyApplication.getDefaultTypeface());
            Item.text.setText(item[groupPosition].toString());

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


