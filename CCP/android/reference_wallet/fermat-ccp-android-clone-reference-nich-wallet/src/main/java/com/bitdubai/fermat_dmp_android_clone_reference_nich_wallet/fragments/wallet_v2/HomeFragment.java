package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragments.wallet_v2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.R;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.session.ReferenceWalletSessionReferenceApp;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;


import java.util.List;

import static com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.utils.WalletUtils.formatBalanceString;


public class HomeFragment extends AbstractFermatFragment {
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
    String walletPublicKey = "reference_wallet";

    TextView txtBalance;
    TextView balance_type;


    /**
     *
     */
    private long availableBalance;
    private long bookBalance;

    private ReferenceWalletSessionReferenceApp referenceWalletSession;
    SettingsManager<BitcoinWalletSettings> settingsManager;
    BlockchainNetworkType blockchainNetworkType;

    private CryptoWallet cryptoWallet;

    private List<PaymentRequest> lstPaymentRequestReceived;
    private List<PaymentRequest> lstPaymentRequestSended;



    private int position;

    public static HomeFragment newInstance() {
        HomeFragment f = new HomeFragment();
        Bundle b = new Bundle();
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSessionReferenceApp) appSession;


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

        settingsManager = referenceWalletSession.getModuleManager().getSettingsManager();


        BitcoinWalletSettings bitcoinWalletSettings = null;
        try {
            bitcoinWalletSettings = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey());
        }catch (Exception e){
            bitcoinWalletSettings = null;
        }
        if(bitcoinWalletSettings == null){
            bitcoinWalletSettings = new BitcoinWalletSettings();
            bitcoinWalletSettings.setIsContactsHelpEnabled(true);
            bitcoinWalletSettings.setIsPresentationHelpEnabled(true);

            if(bitcoinWalletSettings.getBlockchainNetworkType()==null){
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            }


            try {
                settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(),bitcoinWalletSettings);
            } catch (CantPersistSettingsException e) {
                e.printStackTrace();
            }
        }

        if(bitcoinWalletSettings.getBlockchainNetworkType()==null){
            bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
        }
        try {
            settingsManager.persistSettings(referenceWalletSession.getAppPublicKey(),bitcoinWalletSettings);
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }

        try {
            blockchainNetworkType = settingsManager.loadAndGetSettings(referenceWalletSession.getAppPublicKey()).getBlockchainNetworkType();
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Network Type"+blockchainNetworkType);

        account_types = new String[]{"1 current and 2 saving accounts"};
        balances = new String[]{"$5,693.50"};
        balances_available = new String[]{"$1,970.00 available"};


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try{
            rootView = inflater.inflate(R.layout.wallets_teens_fragment_send_and_receive, container, false);

            lstPaymentRequestReceived =  cryptoWallet.listReceivedPaymentRequest(walletPublicKey,blockchainNetworkType,10,0);

            lstPaymentRequestSended = cryptoWallet.listSentPaymentRequest(walletPublicKey,blockchainNetworkType,10,0);


        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            String publicKey = referenceWalletSession.getWalletSessionType().getWalletPublicKey();
            availableBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE, publicKey,blockchainNetworkType);

            bookBalance = cryptoWallet.getBalance(BalanceType.BOOK, referenceWalletSession.getWalletSessionType().getWalletPublicKey(),blockchainNetworkType);


        } catch (CantGetBalanceException e) {
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }

        lv = (ExpandableListView) view.findViewById(R.id.expListView);
        lv.setAdapter(new ExpandableListAdapter(contacts, transactions,7));
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

    /**
     * Method to change the balance amount
     */
    private void changeBalance(TextView textView) {
        try {
            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                textView.setText(formatBalanceString(availableBalance, referenceWalletSession.getTypeAmount()));
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                textView.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txtViewBalance,TextView txtViewTypeBalance) {

        try {
            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                txtViewBalance.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.book_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                txtViewBalance.setText(formatBalanceString(availableBalance, referenceWalletSession.getTypeAmount()));
                txtViewTypeBalance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }
    }

    private String getBalanceTypeInLetters(){
        String balanceType="";
        if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
            balanceType = "Available";
        }else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
            balanceType = "Book";
        }
        return balanceType;
    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        final int END_OF_FIRST_ROW = 2;

        private final LayoutInflater inf;
        private String[] contacts;
        private String[][] transactions;

        boolean isTxtBalanceTouched=false;
        boolean isTxtBalanceAmountTouched=false;

        // es el numero para pintar los row en pantalla
        private int groupCount;

        public ExpandableListAdapter(String[] contacts, String[][] transactions,int groupCount) {
            this.contacts = contacts;
            this.transactions = transactions;
            this.groupCount = groupCount;
            inf = LayoutInflater.from(getActivity());
        }


        @Override
        public int getGroupCount() {
            return groupCount;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition){
                case 1:
                    if(isTxtBalanceAmountTouched){
                        changeBalanceType(txtBalance,balance_type);
                        isTxtBalanceAmountTouched=false;
                    }else if(isTxtBalanceTouched){
                        changeBalance(txtBalance);
                        isTxtBalanceTouched=false;
                    }
                    break;

            }

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

                            txtBalance = ((TextView)convertView.findViewById(R.id.balance));
                            txtBalance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isTxtBalanceTouched=true;
                                    //changeBalance(txtBalance);
                                    //refreshBalance();
                                }
                            });
                            changeBalance(txtBalance);

                            balance_type = ((TextView)convertView.findViewById(R.id.balance_available));
                            balance_type.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    isTxtBalanceAmountTouched = true;
                                    //changeBalanceType(txtBalance,balance_type);
                                }
                            });
                            balance_type.setText(getBalanceTypeInLetters());

                            break;

                        case 2:
                            convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);

                            TextView title = ((TextView)convertView.findViewById(R.id.title));
                            title.setText("Requests received waiting to be accepted");


                            break;


                    }

                    if(groupPosition>END_OF_FIRST_ROW && groupPosition<lstPaymentRequestReceived.size()+END_OF_FIRST_ROW){
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
                                account_picture.setImageResource(R.drawable.juan_profile_picture);
                                break;
                            case 4:
                                account_picture.setImageResource(R.drawable.kimberly_profile_picture);
                                break;
                        }
                    }else if(groupPosition == lstPaymentRequestReceived.size()+END_OF_FIRST_ROW){
                        convertView = inf.inflate(R.layout.wallets_teens_multiple_fragments_titles_list_item, parent, false);
                        tv = ((TextView)convertView.findViewById(R.id.title));
                        tv.setText("Requests sent waiting to be accepted");
                    }else if(groupPosition>lstPaymentRequestReceived.size()+END_OF_FIRST_ROW){
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

