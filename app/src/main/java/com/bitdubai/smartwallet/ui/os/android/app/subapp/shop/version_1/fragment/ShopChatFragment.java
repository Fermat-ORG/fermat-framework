package com.bitdubai.smartwallet.ui.os.android.app.subapp.shop.version_1.fragment;


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


public  class ShopChatFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] contacts;

    private String[] chats;

    private String[] whens;


    private String[][] transactions;

    String[][] transactions_amounts;

    private String[][] transactions_whens;




    public static ShopChatFragment newInstance(int position) {
        ShopChatFragment f = new ShopChatFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"Victoria Gandit","Mariana Duyos","Jennifer Johnson","Teddy Truchot","Caroline Mignaux","Brant Cryder"};
        whens = new String[]{ "1 hour ago",       "2 hours ago",  "2 hours ago"     ,"5 hours ago",  "8 hours ago"      ,"8 hours ago"};
        chats = new String[]{"Yes, we got 4.","It's a pretty new system.","No, it is out of stock", "It will be delivered shortly.", "Ok, we will be waiting.","Thanks for your purchase."};

        transactions = new String[][]{

                {},
                {},
                {},
                {},
                {}
        };

        transactions_amounts = new String[][]{

                {},
                {},
                {},
                {},
                {}
        };

        transactions_whens = new String[][]{

                {},
                {},
                {},
                {},
                {}
        };


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_send_and_receive, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

        lv.setOnItemClickListener(null);

     /*  lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent;
                 intent = new Intent(getActivity(), SentDetailActivity.class);
                startActivity(intent);

                return true;
            }
        });*/
/*
        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 0) {

                    Intent intent;
                    intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    startActivity(intent);
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

*/


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

            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {



                convertView = inf.inflate(R.layout.wallets_teens_fragment_send_list_detail, parent, false);
                holder = new ViewHolder();



                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                amount.text.setTypeface(MyApplication.getDefaultTypeface());
                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());


                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                when.text.setTypeface(MyApplication.getDefaultTypeface());
                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

                ImageView send_to_contact =  (ImageView) convertView.findViewById(R.id.icon_send_to_contact);
                send_to_contact.setTag(groupPosition + "|" + childPosition);

                ImageView  send_message = (ImageView) convertView.findViewById(R.id.icon_chat_over_trx);
                send_message.setTag(groupPosition + "|" + childPosition);


            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
            ViewHolder holder;
            ViewHolder when;
            ImageView profile_picture;
            ViewHolder chat;

/*
            if (groupPosition == 0)
            {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_send_and_receive_first_row, parent, false);

                TextView tv;

                tv = (TextView) convertView.findViewById(R.id.notes);
                tv.setTypeface(MyApplication.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.amount);
                tv.setTypeface(MyApplication.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.new_contact_name);
                tv.setTypeface(MyApplication.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.when);
                tv.setTypeface(MyApplication.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.contact_name);
                tv.setTypeface(MyApplication.getDefaultTypeface());
                tv.setText("Send to new contact");


            }
            else
            {
*/
                //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
                // if (convertView == null) {
               // if (1 == 1) {
                    convertView = inf.inflate(R.layout.shop_fragment_chat_header, parent, false);


                    profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                    //asigned tagId at icons action

                    switch (groupPosition)
                    {
                        case 0:
                            profile_picture.setImageResource(R.drawable.victoria_profile_picture);
                            break;
                        case 1:
                            profile_picture.setImageResource(R.drawable.mariana_profile_picture);
                            break;
                        case 2:
                            profile_picture.setImageResource(R.drawable.jennifer_profile_picture);
                            break;
                        case 3:
                            profile_picture.setImageResource(R.drawable.teddy_profile_picture);
                            break;
                        case 4:
                            profile_picture.setImageResource(R.drawable.caroline_profile_picture);
                            break;
                        case 5:
                            profile_picture.setImageResource(R.drawable.brant_profile_picture);
                    }



                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                    holder.text.setTypeface(MyApplication.getDefaultTypeface());
                    convertView.setTag(holder);


                    chat = new ViewHolder();
                    chat.text = (TextView) convertView.findViewById(R.id.title);
                    chat.text.setTypeface(MyApplication.getDefaultTypeface());
                    chat.text.setText(chats[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);
                    when.text.setTypeface(MyApplication.getDefaultTypeface());

                    when.text.setText(whens[groupPosition].toString());



                    //expand icon
                    ImageView  recent_transactions = (ImageView) convertView.findViewById(R.id.recent_transactions);

                    //Set the arrow programatically, so we can control it - to expand child
/*
                    recent_transactions.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                            else ((ExpandableListView) parent).expandGroup(groupPosition, true);
                        }
                    });*/
                //} else {
                    holder = (ViewHolder) convertView.getTag();
                //}
                holder.text.setText(getGroup(groupPosition).toString());
            //}
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
