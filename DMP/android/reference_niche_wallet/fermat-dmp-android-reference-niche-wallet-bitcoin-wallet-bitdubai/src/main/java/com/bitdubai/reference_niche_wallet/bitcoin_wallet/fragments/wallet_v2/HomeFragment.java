package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_v2;

import android.graphics.Color;
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
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_resources.WalletResourcesProviderManager;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;


public class HomeFragment extends FermatWalletFragment {
    View rootView;
    ExpandableListView lv;
    String[] contacts;
    String[] amounts;
    String[] whens;
    String[] notes;
    String[] account_types;
    String[] balances;
    String[] balances_available;
    private String[][] transactions;
    private static final String ARG_POSITION = "position";


    /**
     *
     */
    private long availableBalance;
    private long bookBalance;

    private ReferenceWalletSession referenceWalletSession;

    private CryptoWallet cryptoWallet;

    private int position;

    public static HomeFragment newInstance(int position, ReferenceWalletSession walletSession) {
        HomeFragment f = new HomeFragment();
        f.setWalletSession(walletSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession) walletSession;


        try {

            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }
        contacts = new String[]{"","","","Stephanie Himonidis","Kimberly Brown","Ginny Kaltabanis","Piper Faust" };
        amounts = new String[]{"","", "", "$290.00","$600.00","$50.00","$30.00"};
        whens = new String[]{"", "", "", "3 Feb 14","1 year ago","1 year ago","4 yeat ago"};
        notes = new String[]{"","", "",  "conference ticket","computer monitor","bag","computer keyboard"};
        transactions = new String[][]{
                { "Mati", "Mati", "Mati", "Mati"},
                {  "Mati", "Mati", "Mati", "Mati" }
        };

        account_types = new String[]{"1 current and 2 saving accounts"};
        balances = new String[]{"$5,693.50"};
        balances_available = new String[]{"$1,970.00 available"};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_teens_fragment_send_and_receive, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            String publicKey = referenceWalletSession.getWalletSessionType().getWalletPublicKey();
            availableBalance = cryptoWallet.getAvailableBalance(publicKey);

            bookBalance = cryptoWallet.getBookBalance(referenceWalletSession.getWalletSessionType().getWalletPublicKey());


        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions));
        lv.setGroupIndicator(null);

        lv.setOnItemClickListener(null);


       /* lv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

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
        });*/




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
        public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {


       ImageView account_picture;
            ViewHolder holder;
            ViewHolder amount;
            ViewHolder when;
            ViewHolder note;


            ImageView  send_message;

            TextView tv;
            if (groupPosition == 0) {
                convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                tv = ((TextView)convertView.findViewById(R.id.title));
                tv.setText("Total balance in all accounts");

            } else{
                    switch (groupPosition) {

                        case 1:
                            //never gets here

                            convertView = inf.inflate(R.layout.wallets_teens_fragment_home_list_item, parent, false);

                            TextView balance = ((TextView)convertView.findViewById(R.id.balance));
                            balance.setText(formatBalanceString(availableBalance, ShowMoneyType.BITCOIN.getCode()));


                            TextView balance_available = ((TextView)convertView.findViewById(R.id.balance_available));
                            balance_available.setText(formatBalanceString(bookBalance, ShowMoneyType.BITCOIN.getCode())+" book");

                            break;

                        case 2:
                            convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                            TextView title = ((TextView)convertView.findViewById(R.id.title));
                            title.setText("Requests received waiting to be accepted");


                            break;

                        // aca irian los ultimos 2 request recibidos
                        case 3: case 4:

                        convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_request_received_list_item, parent, false);
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

                        send_message = (ImageView) convertView.findViewById(R.id.icon_edit_profile);
                        send_message.setTag("ContactsChatActivity|"+contacts[groupPosition].toString());


                        switch (groupPosition) {

                            case 3:
                                account_picture.setImageResource(R.drawable.mati_profile);
                                break;
                            case 4:
                                account_picture.setImageResource(R.drawable.kimberly_profile_picture);
                                break;
                        }
                        break;
                        // aca irian los request enviados
                        case 6:case 7:
                            convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_request_received_list_item, parent, false);
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

                            send_message = (ImageView) convertView.findViewById(R.id.icon_edit_profile);
                            send_message.setTag("ContactsChatActivity|"+contacts[groupPosition].toString());


                            switch (groupPosition) {

                                case 6:
                                    account_picture.setImageResource(R.drawable.dea_profile_picture);
                                    break;
                                case 7:
                                    account_picture.setImageResource(R.drawable.deniz_profile_picture);
                                    break;
                            }
                            break;
                        case 5:
                            convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);
                            tv = ((TextView)convertView.findViewById(R.id.title));
                            tv.setText("Requests sent waiting to be accepted");
                            break;
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

