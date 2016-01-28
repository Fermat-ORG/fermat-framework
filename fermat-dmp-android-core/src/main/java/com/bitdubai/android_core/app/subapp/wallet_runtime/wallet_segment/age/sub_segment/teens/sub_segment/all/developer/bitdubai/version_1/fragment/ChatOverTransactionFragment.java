package com.bitdubai.android_core.app.subapp.wallet_runtime.wallet_segment.age.sub_segment.teens.sub_segment.all.developer.bitdubai.version_1.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.smartwallet.R;
import com.bitdubai.android_core.app.common.version_1.classes.MyApplication;

/**
 * Created by Natalia on 08/01/2015.
 */
public class ChatOverTransactionFragment extends android.app.Fragment {
    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] countries;
    private String[] states;
    private String[] cities;

    private String[][] transactions;
    String[][] transactions_amounts;
    private String[][] transactions_whens;



    public static ChatOverTransactionFragment newInstance(int position) {
        ChatOverTransactionFragment f = new ChatOverTransactionFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          /* Custom Action Bar with Icon and Text */
      /* Custom Action Bar with Icon and Text */
        String[] contactsheader = new String[]{ "", "Guillermo Villanueva", "Luis Fernando Molina", "Pedro Perrotta", "Mariana Duyos"};

        String[][] transactionsheader = new String[][]{

                {},
                {"Flat rent","Flat rent","Flat rent","interest paid :(","Flat rent","Car repair","Invoice #2,356 that should have been paid on August"},
                {"Electricity bill","New chair","New desk"},
                {"Test address"},
                {"More pictures"}
        };

        String[][] transactions_amountsheader = new String[][]{

                {},
                {"$1,400.00","$1,200.00","$1,400.00","$40.00","$1,900.00","$10,550.00","$1.00"},
                {"$325.00","$55.00","$420.00"},
                {"$0.50"},
                {"$25.00"}
        };

        String[][] transactions_whensheader = new String[][]{

                {},
                {"3 min ago","15 min ago","yesterday"},
                {"2 hours ago","1 month ago","2 months ago","3 months ago","3 months ago","5 months ago","7 months ago"},
                {"today 9:24 AM"},
                {"yesterday"}
        };
        String[] tagId = MyApplication.getChildId().split("\\|");


        final ViewGroup actionBarLayout = (ViewGroup) getActivity().getLayoutInflater().inflate(
                R.layout.wallet_framework_activity_chat_over_trx_action_bar,
                null);

        // Set up your ActionBar
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(actionBarLayout);


        TextView tv;

        tv = (TextView) actionBarLayout.findViewById(R.id.contact_name);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(contactsheader[Integer.parseInt(tagId[0])].toString());


        ImageView profile_picture = (ImageView) actionBarLayout.findViewById(R.id.profile_picture);
        switch (Integer.parseInt(tagId[0]))
        {
            case 1:
                profile_picture.setImageResource(R.drawable.guillermo_profile_picture);

                break;
            case 2:
                profile_picture.setImageResource(R.drawable.luis_profile_picture);

                break;
            case 3:
                profile_picture.setImageResource(R.drawable.pedro_profile_picture);
                break;
            case 4:
                profile_picture.setImageResource(R.drawable.mariana_profile_picture);

                break;
        }

        tv = (TextView) actionBarLayout.findViewById(R.id.notes);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactionsheader[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

        tv = (TextView) actionBarLayout.findViewById(R.id.amount);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions_amountsheader[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);;

        tv = (TextView) actionBarLayout.findViewById(R.id.when);
        tv.setTypeface(MyApplication.getDefaultTypeface());
        tv.setText(transactions_whensheader[Integer.parseInt(tagId[0])][Integer.parseInt(tagId[1])]);

        this.contacts = new String[]{"","","","","","","","","","","","",""};
        this.countries = new String[]{};
        this.states = new String[]{};
        this.cities = new String[]{};

        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
        };
        transactions_amounts = new String[][]{

                {},
        };

        transactions_whens = new String[][]{

                {},
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_trx_chat, container, false);


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

            ViewHolder holder;



            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                // convertView = inf.inflate(R.layout.wallets_teens_fragment_send_and_receive_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.city);
                holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);



            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                View view;
                view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);

                TextView hours;
                TextView dates;
                TextView tv;

                switch (groupPosition) {

                    case 0:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_date, parent, false);
                        dates = ((TextView)view.findViewById(R.id.date));
                        dates.setText("4 jan 2015");
                        dates.setTypeface(MyApplication.getDefaultTypeface());
                        break;

                    case 1:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hi, did you receive the payment ok?");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:43");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 2:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Hello, yes it's confirmed.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:50");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 3:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, but I wanted to tell you I've got a problem.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());



                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:52");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 4:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Since this morning that i don't have water or electricity.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:53");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 5:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Oh, I see, I will see what's happening.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:55");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 6:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, thanks you.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("19:56");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 7:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);

                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Tessa, there was a problem with one of the building's pipes and it's all flooded, so for security the suspended the light.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:43");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 8:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ah, maybe that's why the electricity company truck is parked outside.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());

                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20: 45");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;
                    case 9:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Yes, that's why they are there");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:45");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 10:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("As you don't have water neither light to use the heating system.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:46");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;


                    case 11:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_left, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("I suggest you staying the night in the hotel down the street, and don't worry i will pay for it.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:46");
                        hours.setTypeface(MyApplication.getDefaultTypeface());

                        break;

                    case 12:
                        view = inf.inflate(R.layout.wallets_teens_fragment_shop_chat_right, parent, false);
                        tv = ((TextView)view.findViewById(R.id.title));
                        tv.setText("Ok, thanks you for your kindness.");
                        tv.setTypeface(MyApplication.getDefaultTypeface());


                        hours = ((TextView)view.findViewById(R.id.hour));
                        hours.setText("20:47");
                        hours.setTypeface(MyApplication.getDefaultTypeface());
                }
                return view;
            }
            return convertView;
        }
        private class ViewHolder {
            TextView text;
        }
    }
}