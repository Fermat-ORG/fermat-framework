package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;


public  class ReceiveFragment extends FermatWalletFragment {

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



    public static ReceiveFragment newInstance(int position) {
        ReceiveFragment f = new ReceiveFragment();
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

                   /* Intent intent;
                    intent = new Intent(getActivity(), ReceiveFromNewContactActivity.class);
                    startActivity(intent);*/


//                    Intent intent;
//                    appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE);
//                    intent = new Intent(getActivity(), com.bitdubai.android_core.app.FragmentActivity.class);
//                    startActivity(intent);
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


            //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
            // if (convertView == null) {
            if (1 == 1) {
                convertView = inf.inflate(R.layout.wallets_teens_fragment_receive_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);

                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);


                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);


                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

                //asigned tagId at icons action
                ImageView  icon_receive_form_contact = (ImageView) convertView.findViewById(R.id.icon_receive_form_contact);
                icon_receive_form_contact.setTag("ReceiveFromContactActivity|" + groupPosition + "-" + childPosition);

                ImageView  send_message = (ImageView) convertView.findViewById(R.id.icon_chat_over_trx);
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

            if (groupPosition == 0) {
                convertView = inf.inflate(R.layout.wallet_receive_fragment_first_row, parent, false);

                AutoCompleteTextView contact_name = (AutoCompleteTextView) convertView.findViewById(R.id.contact_name);

                Button btn_address = (Button) convertView.findViewById(R.id.btn_address);

                Button btn_share = (Button) convertView.findViewById(R.id.btn_share);


            }else{

                if (convertView == null) {
                    convertView = inf.inflate(R.layout.wallets_teens_fragment_receive_list_header, parent, false);

                    profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
//asigned tagId at icons action
                    ImageView  send_profile_picture = (ImageView) convertView.findViewById(R.id.icon_receive_profile);
                     send_profile_picture.setTag("ReceiveFromContactActivity|" +groupPosition + "-0");

                    //ImageView  history_picture = (ImageView) convertView.findViewById(R.id.open_history);
                    //history_picture.setTag("ReceiveAllHistoryActivity|" +groupPosition);

                    ImageView  send_message = (ImageView) convertView.findViewById(R.id.icon_send_message);
                    send_message.setTag("ContactsChatActivity|" + contacts[groupPosition].toString());

                    switch (groupPosition) {
                        case 1:
                            profile_picture.setImageResource(R.drawable.mati_profile);
                            break;
                        case 2:
                            profile_picture.setImageResource(R.drawable.juan_profile_picture);
                            break;
                        case 3:
                            profile_picture.setImageResource(R.drawable.karina_profile_picture);
                            break;
                        case 4:
                            profile_picture.setImageResource(R.drawable.dea_profile_picture);
                            break;
                        case 5:
                            profile_picture.setImageResource(R.drawable.celine_profile_picture);
                            break;
                        case 6:
                            profile_picture.setImageResource(R.drawable.guillaume_profile_picture);
                            break;
                        case 7:
                            profile_picture.setImageResource(R.drawable.helen_profile_picture);
                            break;
                        case 8:
                            profile_picture.setImageResource(R.drawable.kimberly_profile_picture);
                            break;

                    }


                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);

                    convertView.setTag(holder);

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);


                    amount.text.setText(amounts[groupPosition].toString());

                    total = new ViewHolder();
                    total.text = (TextView) convertView.findViewById(R.id.total_amount);

                    total.text.setText(totalAmount[groupPosition].toString());

                    history = new ViewHolder();
                    history.text = (TextView) convertView.findViewById(R.id.history_count);
                    history.text.setText(historyCount[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);

                    when.text.setText(whens[groupPosition].toString());

                    note = new ViewHolder();
                    note.text = (TextView) convertView.findViewById(R.id.notes);


                    note.text.setText(notes[groupPosition].toString());

                    //expand icon
                    ImageView  recent_transactions = (ImageView) convertView.findViewById(R.id.recent_transactions);

                    //Set the arrow programatically, so we can control it - to expand child

                    recent_transactions.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if(isExpanded) ((ExpandableListView) parent).collapseGroup(groupPosition);
                            else ((ExpandableListView) parent).expandGroup(groupPosition, true);

                        }
                    });

                } else {
                    holder = (ViewHolder) convertView.getTag();
                }




               // holder.text.setText(getGroup(groupPosition).toString());

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