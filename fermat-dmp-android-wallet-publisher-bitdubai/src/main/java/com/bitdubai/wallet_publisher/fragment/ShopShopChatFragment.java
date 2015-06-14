package com.bitdubai.wallet_publisher.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bitdubai.wallet_publisher.R;
import com.bitdubai.wallet_publisher.common.classes.MyApplication;


public class ShopShopChatFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[][] transactions;

    public static ShopShopChatFragment newInstance(int position) {
        ShopShopChatFragment f = new ShopShopChatFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"","","","","","","","","","","","","","","","",""};

        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_shop_chat, container, false);


        TextView userInput;
        TextView button;

        userInput = ((TextView)rootView.findViewById(R.id.edit_text));
        userInput.setTypeface(MyApplication.getDefaultTypeface());

        button = ((TextView)rootView.findViewById(R.id.send_message_button));
        button.setTypeface(MyApplication.getDefaultTypeface());

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private String[] contacts;
        private String[][] transactions;

        public ExpandableListAdapter(String[] contacts, String[][] transactions) {
            this.contacts = contacts;
            this.transactions = transactions;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return contacts.length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return transactions[groupPosition].length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return contacts[groupPosition];
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

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

                View view;
                view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);

                TextView hours;
                TextView dates;
                TextView tv;

                switch (groupPosition) {

                    case 0:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);
                        dates = ((TextView)view.findViewById(R.id.date));
                        dates.setText("01/01/15");
                        dates.setTypeface(MyApplication.getDefaultTypeface());
                        break;

                    case 1:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hello.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:35");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 2:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hi! How can I help you?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:35");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 3:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Well, I am in the need of some donuts for a party.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());



                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:37");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 4:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("How many dozens do you have available?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:37");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 5:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("We have 5 in stock, but in 10 minutes were getting 3 more.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:38");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 6:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("I just need 4 dozens, how much would it be?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:38");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 7:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("The 4 dozens have a value of $76.25.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:39");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 8:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, can you reserve it to me? I will pick it up in half an hour.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());

                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:40");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 9:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("No problem, see you then.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("16:42");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 10:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);
                        dates = ((TextView)view.findViewById(R.id.date));
                        dates.setText("30 dec 14");
                        dates.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 11:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hello.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("09:45");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 12:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Good morning, can i help you?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("09:53");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 13:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("I wanted to know if your delivery service is operating today.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("09:55");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 14:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Yes, what do you need to be delivered?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("09:57");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 15:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("I need 2 boxes of mixed donuts at 134 of 43 street");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("10:00");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 16:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok , on the way.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("10:02");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                }
                return view;
        }
        private class ViewHolder {
            TextView text;
        }
    }
}