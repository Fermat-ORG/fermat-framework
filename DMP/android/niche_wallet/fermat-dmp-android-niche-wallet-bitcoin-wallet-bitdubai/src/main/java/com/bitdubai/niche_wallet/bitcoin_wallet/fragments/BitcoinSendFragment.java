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


import com.bitdubai.niche_wallet.bitcoin_wallet.Platform;
import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.NicheWalletTypeCryptoWalletManager;

/**
 * Created by Natalia on 02/06/2015.
 */
public class BitcoinSendFragment extends Fragment {

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
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private static NicheWalletTypeCryptoWalletManager cryptoWalletManager;
    private static Platform platform = new Platform();



    public static BitcoinSendFragment newInstance(int position) {
        BitcoinSendFragment f = new BitcoinSendFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set instance of Contact Manager
      //  cryptoWalletManager = platform.getCryptoWalletManager();

        //get contacts list
     //   try
      //  {
        //    List<WalletContact> walletContacts = cryptoWalletManager.listWalletContacts(UUID.randomUUID());


      //  }
       // catch(CantGetAllWalletContactsException cantGetAllWalletContactsException) {

      //  }

        contacts = new String[]{ "", "Guillermo Villanueva", "Luis Fernando Molina", "Pedro Perrotta", "Mariana Duyos"};
        amounts = new String[]{ "", "BTC 1.00", "BTC 3.25", "BTC 0.50", "BTC 25"};

        transactions = new String[][]{

                {},
                {"Flat rent","Flat rent","Flat rent","interest paid :(","Flat rent","Car repair","Invoice #2,356 that should have been paid on August"},
                {"Plasma TV","New chair","New desk"},
                {"Test address"},
                {"More pictures"}
        };

        transactions_amounts = new String[][]{

                {},
                {"$1,400.00","$1,200.00","$1,400.00","$40.00","$1,900.00","$10,550.00","$1.00"},
                {"$325.00","$55.00","$420.00"},
                {"$0.50"},
                {"$25.00"}
        };

        transactions_whens = new String[][]{

                {},
                {"2 hours ago ","1 months ago","2 months ago","4 months ago","4 months ago","5 months ago","6 months ago"},
                {"3 min ago","a week ago","last month"},
                {"today 9:24 AM"},
                {"yesterday"}
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
                  /*  Platform platform = MyApplication.getPlatform();
                    CorePlatformContext platformContext = platform.getCorePlatformContext();

                    AppRuntimeManager appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                    appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
                    Intent intent;
                    appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND);
                    intent = new Intent(getActivity(), com.bitdubai.android_core.app.FragmentActivity.class);
                    startActivity(intent);*/

                    // intent = new Intent(getActivity(), SendToNewContactActivity.class);
                    //  startActivity(intent);
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


                convertView = inf.inflate(R.layout.wallets_bitcoin_fragment_send_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);
               // holder.text.setTypeface(MyApplication.getDefaultTypeface());
                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);
               // amount.text.setTypeface(MyApplication.getDefaultTypeface());

                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);
               // when.text.setTypeface(MyApplication.getDefaultTypeface());

                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

                ImageView send_to_contact =  (ImageView) convertView.findViewById(R.id.icon_send_to_contact);
                send_to_contact.setTag("SendToContactActivity|" + groupPosition + "|" + childPosition);




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
            TextView tv;

            if (groupPosition == 0)
            {
                convertView = inf.inflate(R.layout.wallets_bitcoin_fragment_send_and_receive_first_row, parent, false);


                tv = (TextView) convertView.findViewById(R.id.contact_name);
                tv.setText("Send to new contact");

                //assign the event to go Send New Contact Fragment
                ImageView  send_profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                send_profile_picture.setTag("BasicSendToNewContactActivity");

            }
            else
            {

                if(groupPosition == 1) {
                    convertView = inf.inflate(R.layout.wallets_bitcoin_multiple_fragments_titles_list_item, parent, false);

                    tv = ((TextView)convertView.findViewById(R.id.title));
                    tv.setText("Contacts");
                }
                else
                {
                    if (1 == 1) {
                        convertView = inf.inflate(R.layout.wallets_bitcoin_fragment_send_list_header, parent, false);


                        //asigned tagId at icons action
                        ImageView  send_profile_picture = (ImageView) convertView.findViewById(R.id.icon_send_to_contact);
                        send_profile_picture.setTag("BasicSendToContactActivity|" + groupPosition + "|-1");


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
