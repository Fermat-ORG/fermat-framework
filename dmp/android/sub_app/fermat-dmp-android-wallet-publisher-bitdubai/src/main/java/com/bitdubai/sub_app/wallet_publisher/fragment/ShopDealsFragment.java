package com.bitdubai.sub_app.wallet_publisher.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import com.bitdubai.sub_app.wallet_publisher.R;
import com.bitdubai.sub_app.wallet_publisher.common.classes.MyApplication;

public  class ShopDealsFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] Type;

    private String[] End;

    private String[] Status;

    private String[] Start;

    private String[] Description;

    private String[][] Base;


    public static ShopDealsFragment newInstance(int position) {
        ShopDealsFragment f = new ShopDealsFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Start = new String[]{
                "01/01/15"
        };

        Description = new String[]{
                "This store has permission to accept all vouchers released by us."
        };

        Status = new String[]{
                "active"
        };

        End = new String[]{
                "31/12/15"
        };

        Type = new String[]{
                "Voucher"
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
        lv.setAdapter(new ExpandableListAdapter(Start, Base));
        lv.setGroupIndicator(null);

        lv.setOnItemClickListener(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] Start;
        private String[][] Base;

        public ExpandableListAdapter(String[] Start, String[][] Base) {
            this.Start = Start;
            this.Base = Base;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return Start.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return Base[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return Start[groupPosition];
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
            convertView = inf.inflate(R.layout.shop_shop_deals_header, parent, false);
            ViewHolder type;
            ViewHolder description;
            ViewHolder end;
            ViewHolder start;
            ViewHolder status;


            end = new ViewHolder();
            type = new ViewHolder();
            start = new ViewHolder();
            status = new ViewHolder();
            description = new ViewHolder();
            end.text = (TextView) convertView.findViewById(R.id.deal_end);
            type.text = (TextView) convertView.findViewById(R.id.deal_type);
            start.text = (TextView) convertView.findViewById(R.id.deal_start);
            status.text = (TextView) convertView.findViewById(R.id.deal_status);
            description.text = (TextView) convertView.findViewById(R.id.deal_description);
            end.text.setText(End[groupPosition]);
            type.text.setText(Type[groupPosition]);
            start.text.setText(Start[groupPosition]);
            status.text.setText(Status[groupPosition]);
            description.text.setText(Description[groupPosition]);
            end.text.setTypeface(MyApplication.getDefaultTypeface());
            type.text.setTypeface(MyApplication.getDefaultTypeface());
            start.text.setTypeface(MyApplication.getDefaultTypeface());
            status.text.setTypeface(MyApplication.getDefaultTypeface());
            description.text.setTypeface(MyApplication.getDefaultTypeface());

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
