package com.bitdubai.sub_app.wallet_factory.fragment.version_3.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.SubAppsSession;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_factory.interfaces.WalletFactoryManager;
import com.bitdubai.sub_app.wallet_factory.R;
import com.bitdubai.sub_app.wallet_factory.session.WalletFactorySubAppSession;
import com.squareup.picasso.Picasso;


public class SendFragment extends FermatFragment {

    private static final String ARG_POSITION = "position";

    View rootView;

    ExpandableListView lv;

    private String[] contacts;

    private String[] amounts;

    private String[] totalAmount;

    private String[] historyCount;

    private String[] whens;

    private String[] notes;

    private String[] pictures;

    private String[][] transactions;

    String[][] transactions_amounts;

    private String[][] transactions_whens;

    /**
     * Manager
     */
    private WalletFactoryManager manager;


    public static SendFragment newInstance() {
        SendFragment f = new SendFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            /*setting up manager*/
            manager = ((WalletFactorySubAppSession) subAppsSession).getWalletFactoryManager();
        } catch (Exception ex) {
            Log.getStackTraceString(ex);
        }
        contacts = new String[]{"", "Guillermo Villanueva", "Luis Fernando Molina", "Pedro Perrotta", "Mariana Duyos"};
        amounts = new String[]{"", "$1,400.00", "$325.00", "$0.50", "$25.00"};
        whens = new String[]{"", "2 hours ago", "3 min ago", "today 9:24 AM", "yesterday"};
        notes = new String[]{"", "Flat rent", "Plasma TV", "Test address", "More pictures"};
        totalAmount = new String[]{"", "$22,730.00", "$785.00", "$0.50", "$125.00"};
        historyCount = new String[]{"", "16 records", "7 records", "1 record", "6 records"};
        pictures = new String[]{"", "guillermo_profile_picture", "luis_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
                {"Flat rent", "Flat rent", "Flat rent", "interest paid :(", "Flat rent", "Car repair", "Invoice #2,356 that should have been paid on August"},
                {"Plasma TV", "New chair", "New desk"},
                {"Test address"},
                {"More pictures"}
        };

        transactions_amounts = new String[][]{

                {},
                {"$1,400.00", "$1,200.00", "$1,400.00", "$40.00", "$1,900.00", "$10,550.00", "$1.00"},
                {"$325.00", "$55.00", "$420.00"},
                {"$0.50"},
                {"$25.00"}
        };

        transactions_whens = new String[][]{

                {},
                {"2 hours ago ", "1 months ago", "2 months ago", "4 months ago", "4 months ago", "5 months ago", "6 months ago"},
                {"3 min ago", "a week ago", "last month"},
                {"today 9:24 AM"},
                {"yesterday"}
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

        lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (groupPosition == 0) {
                    //Platform platform = ApplicationSession.getFermatPlatform();
                    //CorePlatformContext platformContext = platform.getCorePlatformContext();

                    //AppRuntimeManager appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                    //appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                    Intent intent;
                    //appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND);
                    //intent = new Intent(getActivity(), com.bitdubai.android_core.app.FragmentActivity.class);
                    //startActivity(intent);

                    // intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    //  startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });


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
            if (1 == 1) {


                convertView = inf.inflate(R.layout.wallets_teens_fragment_send_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);
                //holder.text.setTypeface(ApplicationSession.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
                //amount.text.setTypeface(ApplicationSession.getDefaultTypeface());

                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
                //when.text.setTypeface(ApplicationSession.getDefaultTypeface());

                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

                ImageView send_to_contact = (ImageView) convertView.findViewById(R.id.icon_send_to_contact);
                send_to_contact.setTag("SendToContactActivity|" + groupPosition + "|" + childPosition);

                ImageView send_message = (ImageView) convertView.findViewById(R.id.icon_chat_over_trx);

                send_message.setTag("ChatOverTrxActivity|" + groupPosition + "|" + childPosition);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.text.setText(getChild(groupPosition, childPosition).toString());

            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;
            ImageView profile_picture;
            ViewHolder total;
            ViewHolder history;
            ViewHolder new_name;

            if (groupPosition == 0) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_send_and_receive_first_row, parent, false);

                TextView tv;

                tv = (TextView) convertView.findViewById(R.id.notes);
                //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.amount);
                //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.new_contact_name);
                //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.when);
                //tv.setTypeface(ApplicationSession.getDefaultTypeface());

                tv = (TextView) convertView.findViewById(R.id.contact_name);
                //tv.setTypeface(ApplicationSession.getDefaultTypeface());
                tv.setText("Send to new contact");


            } else {

                //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
                // if (convertView == null) {
                if (1 == 1) {
                    convertView = inf.inflate(R.layout.wallets_teens_fragment_send_list_header, parent, false);

                    profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                    //asigned tagId at icons action
                    ImageView send_profile_picture = (ImageView) convertView.findViewById(R.id.icon_send_profile);
                    send_profile_picture.setTag("SendToContactActivity|" + groupPosition + "|-1");

                    ImageView send_message = (ImageView) convertView.findViewById(R.id.icon_send_message);
                    send_message.setTag("ContactsChatActivity|" + contacts[groupPosition].toString());

                    ImageView history_picture = (ImageView) convertView.findViewById(R.id.open_history);
                    history_picture.setTag("SentHistoryActivity|" + groupPosition);

                    switch (groupPosition) {
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


                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                    //holder.text.setTypeface(ApplicationSession.getDefaultTypeface());
                    convertView.setTag(holder);

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);
                    //amount.text.setTypeface(ApplicationSession.getDefaultTypeface());

                    amount.text.setText(amounts[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);
                    //when.text.setTypeface(ApplicationSession.getDefaultTypeface());

                    when.text.setText(whens[groupPosition].toString());

                    total = new ViewHolder();
                    total.text = (TextView) convertView.findViewById(R.id.total_amount);
                    //total.text.setTypeface(ApplicationSession.getDefaultTypeface());
                    total.text.setText(totalAmount[groupPosition].toString());

                    history = new ViewHolder();
                    history.text = (TextView) convertView.findViewById(R.id.history_count);
                    //history.text.setTypeface(ApplicationSession.getDefaultTypeface());
                    history.text.setText(historyCount[groupPosition].toString());

                    note = new ViewHolder();
                    note.text = (TextView) convertView.findViewById(R.id.notes);
                    //note.text.setTypeface(ApplicationSession.getDefaultTypeface());

                    note.text.setText(notes[groupPosition].toString());

                    //expand icon
                    ImageView recent_transactions = (ImageView) convertView.findViewById(R.id.recent_transactions);

                    //Set the arrow programatically, so we can control it - to expand child

                    recent_transactions.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if (isExpanded)
                                ((ExpandableListView) parent).collapseGroup(groupPosition);
                            else ((ExpandableListView) parent).expandGroup(groupPosition, true);
                        }
                    });
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.text.setText(getGroup(groupPosition).toString());
            }
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
