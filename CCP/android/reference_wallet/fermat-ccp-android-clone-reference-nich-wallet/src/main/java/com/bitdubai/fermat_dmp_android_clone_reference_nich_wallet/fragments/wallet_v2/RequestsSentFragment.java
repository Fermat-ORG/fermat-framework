package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragments.wallet_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.R;


public  class RequestsSentFragment extends FermatWalletFragment {

    private static final String ARG_POSITION = "position";

    View rootView;
    ExpandableListView lv;
    private String[] contacts;
    private String[] amounts;
    private String[] whens;
    private String[] notes;
    private String[] pictures;
    private String[][] transactions;
    private String[][] transactions_amounts;
    private String[][] transactions_whens;


    public static RequestsSentFragment newInstance() {
        RequestsSentFragment f = new RequestsSentFragment();
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"Taylor Backus","Stephanie Himonidis","Kimberly Brown",  "Florence Kerns","Kevin Helms","Teddy Truchot","Hélène Derosier","John Smith","Caroline Mignaux","Guillaume Thery","Brant Cryder","Thomas Levy","Verena Stenz"};
        amounts = new String[]{ "$3.00","$290.00","$600.00",                             "$00.50","$40.00","$20.00","$125.00","$250.00","$75.00","$50.00","$5.00","$100.00","$40.00"};
        whens = new String[]{ "24 Mar 14","3 Feb 14","1 year ago",                       "1 year ago","2 years ago","2 years ago","2 years ago","3 years ago","3 years ago","3 years ago","3 years ago","4 years ago","4 years ago"};
        notes = new String[]{ "Sandwich","Conference ticket","Computer monitor",         "Pen","Headphones","Computer mouse","New Chair","Tablet","Sun glasses","Sport shoes","Charger cable","Biclyclete","Camera"};


        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},
        };
        transactions_amounts = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},

        };

        transactions_whens = new String[][]{

                {},
                {},
                {},
                {},
                {},
                {},
                {},
                {},


        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_multiple_fragments_expandable_list_view, container, false);


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

        private final LayoutInflater mInflater;
        private String[] contacts;
        private String[][] transactions;

        public ExpandableListAdapter(String[] contacts, String[][] transactions) {
            this.contacts = contacts;
            this.transactions = transactions;
            mInflater = LayoutInflater.from(getActivity());
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
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;
            ImageView profile_picture;
            ImageView account_picture;


            TextView tv;

            switch (groupPosition)
            {
                case 0:
                    convertView = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                    tv = ((TextView)convertView.findViewById(R.id.title));
                    tv.setText("Requests waiting to be accepted");


                    break;


                case 2:
                    convertView = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                    tv = ((TextView)convertView.findViewById(R.id.title));
                    tv.setText("Requests already accepted");


                    break;

                case 1: case 3:case 4:case 5:case 6:case 7:case 8:case 9:case 10:case 11:case 12:

                    convertView = mInflater.inflate(R.layout.wallets_teens_multiple_fragments_request_sent_list_item, parent, false);
                    account_picture = (ImageView) convertView.findViewById(R.id.profile_picture);


                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);

                    holder.text.setText(contacts[groupPosition].toString());

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);


                    amount.text.setText(amounts[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);


                    when.text.setText(whens[groupPosition].toString());

                    note = new ViewHolder();
                    note.text = (TextView) convertView.findViewById(R.id.notes);


                    note.text.setText(notes[groupPosition].toString());

                    switch (groupPosition)
                    {

                        case 1:
                            account_picture.setImageResource(R.drawable.mati_profile);
                            break;
                        case 2:
                            account_picture.setImageResource(R.drawable.kevin_profile_picture);
                            break;
                        case 3:
                            account_picture.setImageResource(R.drawable.mati_profile);
                            break;
                        case 4:
                            account_picture.setImageResource(R.drawable.kevin_profile_picture);
                            break;
                        case 5:
                            account_picture.setImageResource(R.drawable.mati_profile);
                            break;
                        case 6:
                            account_picture.setImageResource(R.drawable.helene_profile_picture);
                            break;
                        case 7:
                            account_picture.setImageResource(R.drawable.john_profile_picture);
                            break;
                        case 8:
                            account_picture.setImageResource(R.drawable.caroline_profile_picture);
                            break;
                        case 9:
                            account_picture.setImageResource(R.drawable.guillaume_profile_picture);
                            break;
                        case 10:
                            account_picture.setImageResource(R.drawable.mati_profile);
                            break;
                        case 11:
                            account_picture.setImageResource(R.drawable.mati_profile);
                            break;
                        case 12:
                            account_picture.setImageResource(R.drawable.mati_profile);
                            break;






                    }


                    if (groupPosition > 1 )
                    {
                        ImageView iv;
                        iv = (ImageView) convertView.findViewById(R.id.action_accept);
                        iv.setVisibility(View.INVISIBLE);
                        iv = (ImageView) convertView.findViewById(R.id.action_cancel);
                        iv.setVisibility(View.INVISIBLE);
                    }

                    break;


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