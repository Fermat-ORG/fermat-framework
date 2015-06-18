package com.bitdubai.niche_wallet.bitcoin_wallet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BitcoinReceiveFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] historyCount;
    private String[] notes;
    private String[] totalAmount;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;



    public static BitcoinReceiveFragment newInstance(int position) {
        BitcoinReceiveFragment f = new BitcoinReceiveFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"","Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"","$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};
        whens = new String[]{"","4 hours ago", "5 hours ago", "yesterday 11:00 PM", "24 Mar 14","3 Feb 14","1 year ago","1 year ago","2 year ago"};
        notes = new String[]{"","New telephone", "Old desk", "Car oil", "Sandwich","Headphones","Computer monitor","Pen","Apartment in Dubai"};
        totalAmount = new String[]{"","$" + "17,485.00","$" + "156,340.00","$" + "422,545","$" + "62,735.00","$" + "45.00","$" + "12,360.00","$" + "75.00","$"+ "80,000"};
        historyCount = new String[] {"","9 records","19 records","32 records","11 records","1 record","11 records","2 records","1 record"};
        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{
                {},
                {"New telephone","Hot dog","Telephone credit","Coffee"},
                {"Old desk","Flat rent","New glasses","House in Europe","Coffee","Gum"},
                {"Car oil","Headphones","Apartment"},
                {"Sandwich","New kitchen","Camera repair"},
                {"Headphones"},
                {"Computer monitor","New car"},
                {"Pen"},
                {"Apartment in Dubai"}
        };
        transactions_amounts = new String[][]{

                {},
                {"$200.00", "$3.00", "$460.00", "$2.00", "$1.5"},
                {"$3,000.00", "$34,200.00", "$4,500.00", "$4,000,000", "$2,00.00", "$0.50"},
                {"$400,00", "$43.00", "$350,000.00"},
                {"$3.00", "$55,000.00", "$7,500.00"},
                {"$45.00"},
                {"$600.00","$5050.00"},
                {"$50.00"},
                {"$80,000.00"}

        };

        transactions_whens = new String[][]{

                {},
                {"4 hours ago","8 hours ago","yesterday 10:33 PM","yesterday 9:33 PM"},
                {"5 hours ago","yesterday","20 Sep 14","16 Sep 14","13 Sep 14","12 Sep 14"},
                {"yesterday 11:00 PM","23 May 14", "12 May 14"},
                {"24 Mar 14","15 Apr 14","2 years ago"},
                {"3 Feb 14"},
                {"1 year ago","1 year ago"},
                {"1 year ago"},
                {"2 years ago"}


        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send_and_receive, container, false);

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

                   /* Intent intent;
                    intent = new Intent(getActivity(), ReceiveFromNewContactActivity.class);
                    startActivity(intent);*/

                 /*   Platform platform = MyApplication.getPlatform();
                    CorePlatformContext platformContext = platform.getCorePlatformContext();

                    AppRuntimeManager appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                    appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                    Intent intent;
                    appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE);
                    intent = new Intent(getActivity(), com.bitdubai.android_core.app.FragmentActivity.class);
                    startActivity(intent);*/
                    return true;
                }
                else
                {
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

  if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_bitcoin_fragment_receive_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);
                 convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);

                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);

                when.text.setText(transactions_whens[groupPosition][childPosition].toString());



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
            TextView tv;

            if (groupPosition == 0)
            {
                convertView = inf.inflate(R.layout.wallets_bitcoin_fragment_send_and_receive_first_row, parent, false);

                tv = (TextView) convertView.findViewById(R.id.new_contact_name);

                tv = (TextView) convertView.findViewById(R.id.contact_name);
                tv.setText("Receive from new contact");

                //assign the event to go Receive from New Contact Fragment
                ImageView  send_profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                send_profile_picture.setTag("BitcoincReceiveFromNewContactActivity");
            }
            else
            {

                if(groupPosition == 1) {
                    convertView = inf.inflate(R.layout.wallets_bitcoin_multiple_fragments_titles_list_item, parent, false);

                    tv = ((TextView)convertView.findViewById(R.id.title));
                    tv.setText("Contacts");
                }
                else {
                    if (1 == 1) {
                        convertView = inf.inflate(R.layout.wallets_bitcoin_fragment_receive_list_header, parent, false);


                        //asigned tagId at icons action
                        ImageView  send_profile_picture = (ImageView) convertView.findViewById(R.id.icon_receive_profile);
                        send_profile_picture.setTag("BitcoincReceiveFromContactActivity|" +groupPosition + "|-1");


                        holder = new ViewHolder();
                        holder.text = (TextView) convertView.findViewById(R.id.contact_name);
                        convertView.setTag(holder);

                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
                    holder.text.setText(getGroup(groupPosition).toString());
                }




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
