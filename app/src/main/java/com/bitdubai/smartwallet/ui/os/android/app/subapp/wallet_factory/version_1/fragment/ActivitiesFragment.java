package com.bitdubai.smartwallet.ui.os.android.app.subapp.wallet_factory.version_1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.smartwallet.ui.os.android.app.common.version_1.classes.MyApplication;

/**
 * Created by Natalia on 19/12/2014.
 */
public class ActivitiesFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] item;
    private String[] actionbar;
    private String[] drawer;
    private String[] tabs;

    private String[][] activity_item;



    public static ActivitiesFragment newInstance(int position) {
        ActivitiesFragment f = new ActivitiesFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = new String[]{
                "AccountDetailActivity",
                "AccountsActivity",
                "AvailableBalanceActivity",
                "ChatOverReceiveTrxActivity",
                "ChatOverTrxActivity",
                "ChatWithContactActivity",
                "ContactsActivity",
                "DailyDiscountsActivity",
                "EditPersonalProfileActivity",
                "FrameworkActivity",
        };
        actionbar = new String[]{
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
                "ActionBar",
        };
        drawer = new String[]{
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
                "Navigation Drawer",
        };
        tabs = new String[]{
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
                "Tabs",
        };
        activity_item = new String[][]{
                {"a"},
                {"a"},
                {"a"},
                {"a"},
                {"a"},
                {"a"},
                {"a"},
                {"a"},
                {"a"},
                {"a"},
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
        lv.setAdapter(new ExpandableListAdapter(item, activity_item));
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
        private String[][] activity_item;

        public ExpandableListAdapter(String[] items, String[][] activity_item) {
            this.item = items;
            this.activity_item = activity_item;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return item.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return activity_item[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return item[groupPosition];
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return activity_item[groupPosition][childPosition];
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


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            convertView = inf.inflate(R.layout.wallet_factory_activities_detail_fragment, parent, false);


            item = new ViewHolder();
            item.text = (TextView) convertView.findViewById(R.id.items);
            item.text.setTypeface(MyApplication.getDefaultTypeface());
            item.text.setText(activity_item[childPosition].toString());

            return convertView;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder Item;
            ViewHolder actionbars;
            ViewHolder drawers;
            ViewHolder tab;

            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            convertView = inf.inflate(R.layout.wallet_factory_activities_header_fragment, parent, false);

            Item = new ViewHolder();
            Item.text = (TextView) convertView.findViewById(R.id.description);
            Item.text.setTypeface(MyApplication.getDefaultTypeface());
            Item.text.setText(item[groupPosition].toString());

            actionbars = new ViewHolder();
            actionbars.text = (TextView) convertView.findViewById(R.id.actionbar);
            actionbars.text.setTypeface(MyApplication.getDefaultTypeface());
            actionbars.text.setText(actionbar[groupPosition].toString());

            drawers = new ViewHolder();
            drawers.text = (TextView) convertView.findViewById(R.id.drawer);
            drawers.text.setTypeface(MyApplication.getDefaultTypeface());
            drawers.text.setText(drawer[groupPosition].toString());

            tab = new ViewHolder();
            tab.text = (TextView) convertView.findViewById(R.id.tab);
            tab.text.setTypeface(MyApplication.getDefaultTypeface());
            tab.text.setText(tabs[groupPosition].toString());

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


