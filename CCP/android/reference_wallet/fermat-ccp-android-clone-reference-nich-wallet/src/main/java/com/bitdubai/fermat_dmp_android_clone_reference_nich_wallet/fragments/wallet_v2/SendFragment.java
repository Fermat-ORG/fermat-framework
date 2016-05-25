package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragments.wallet_v2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.R;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.custom_anim.Fx;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.session.ReferenceWalletSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;


public  class SendFragment extends AbstractFermatFragment {

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

    ReferenceWalletSession referenceWalletSession;

    CryptoWallet cryptoWallet;


    private EditText editTextAmount;
    private LinearLayout linear_layout_send_form;


    private AutoCompleteTextView autocompleteContacts;
    private EditText editTextAddress;
   //private WalletContactListAdapter adapter;

    public static SendFragment newInstance(int position) {
        SendFragment f = new SendFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession) appSession;

        try {

            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();



            contacts = new String[]{  "Guillermo Villanueva", "Luis Fernando Molina", "Pedro Perrotta", "Mariana Duyos"};
            amounts = new String[]{  "$1,400.00", "$325.00", "$0.50", "$25.00"};
            whens = new String[]{  "2 hours ago", "3 min ago", "today 9:24 AM", "yesterday"};
            notes = new String[]{ "Flat rent",  "Plasma TV", "Test address", "More pictures"};
            totalAmount = new String[]{"$22,730.00","$785.00","$0.50","$125.00"};
            historyCount = new String[] {"16 records","7 records","1 record","6 records"};
            pictures = new String[]{ "guillermo_profile_picture", "luis_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

            transactions = new String[][]{

                    {"Flat rent","Flat rent","Flat rent","interest paid :(","Flat rent","Car repair","Invoice #2,356 that should have been paid on August"},
                    {"Plasma TV","New chair","New desk"},
                    {"Test address"},
                    {"More pictures"}
            };

            transactions_amounts = new String[][]{

                    {"$1,400.00","$1,200.00","$1,400.00","$40.00","$1,900.00","$10,550.00","$1.00"},
                    {"$325.00","$55.00","$420.00"},
                    {"$0.50"},
                    {"$25.00"}
            };

            transactions_whens = new String[][]{

                    {"2 hours ago ","1 months ago","2 months ago","4 months ago","4 months ago","5 months ago","6 months ago"},
                    {"3 min ago","a week ago","last month"},
                    {"today 9:24 AM"},
                    {"yesterday"}
            };

        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,UnexpectedWalletExceptionSeverity.DISABLES_THIS_FRAGMENT,e);
        }




    }



//...



    /**
     * onClick handler
     */
    public void toggle_contents(View v){


    }

    public void showDialog() {
        CharSequence[] items = {"Red", "Green", "Blue"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =   inflater.inflate(R.layout.bitcoin_wallet_send_fragment_base, container, false);

         linear_layout_send_form = (LinearLayout)rootView.findViewById(R.id.send_form);

                rootView.findViewById(R.id.btn_expand_send_form).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isShow = linear_layout_send_form.isShown();
                        //linear_layout_send_form.setVisibility(isShow?View.GONE:View.VISIBLE);
                        if (isShow) {
                            Fx.slide_up(getActivity(), linear_layout_send_form);
                            linear_layout_send_form.setVisibility(View.GONE);
                            showDialog();
                        } else {
                            linear_layout_send_form.setVisibility(View.VISIBLE);
                            Fx.slide_down(getActivity(), linear_layout_send_form);
                        }

                    }
                });

        autocompleteContacts = (AutoCompleteTextView)rootView.findViewById(R.id.contact_name);

//        adapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());
//
//        autocompleteContacts.setAdapter(adapter);
//        //autocompleteContacts.setTypeface(tf);
//        autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                WalletContact walletContact = (WalletContact) arg0.getItemAtPosition(position);
//                editTextAddress.setText(walletContact.address);
//            }
//        });

        editTextAddress = (EditText) rootView.findViewById(R.id.address);

        TextView tv;

        tv = (TextView) rootView.findViewById(R.id.notes);


        editTextAmount = (EditText) rootView.findViewById(R.id.amount);
        /**
         *  Amount observer
         */
        editTextAmount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                try {
                    Long amount = Long.parseLong(editTextAmount.getText().toString());
                    if (amount > 0) {
                        //long actualBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
                        //editTextAmount.setHint("Available amount: " + actualBalance + " bits");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        /**
         * BarCode Scanner
         */
        rootView.findViewById(R.id.scan_qr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
//                integrator.initiateScan();
            }
        });



        //tv = (TextView) convertView.findViewById(R.id.new_contact_name);

        Button btnSend = (Button) rootView.findViewById(R.id.send_button);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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

                //                    Platform platform = ApplicationSession.getFermatPlatform();
//                    CorePlatformContext platformContext = platform.getCorePlatformContext();
//
//                    AppRuntimeManager appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
//                    appRuntimeMiddleware =  (AppRuntimeManager)platformContext.getPlugin(Plugins.BITDUBAI_APP_RUNTIME_MIDDLEWARE);
//                    Intent intent;
//                    appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_SEND);
//                    intent = new Intent(getActivity(), com.bitdubai.android_core.app.FragmentActivity.class);
//                    startActivity(intent);
// intent = new Intent(getActivity(), SendToNewContactActivity.class);
//  startActivity(intent);
                return groupPosition == 0;
            }
        });

    }

    /**
     * Obtain the wallet contacts from the cryptoWallet
     *
     * @return
     */
//    private List<WalletContact> getWalletContactList() {
//        List<WalletContact> contacts = new ArrayList<>();
//
////        new FermatWorker(getActivity(), new FermatWorkerCallBack() {
////            @SuppressWarnings("unchecked")
////            @Override
////            public void onPostExecute(Object... result) {
////                if (isAttached) {
////                    if (adapter != null) {
////                        intraUserItemList = (ArrayList<IntraUserConnectionListItem>) result[0];
////                        adapter.changeDataSet(intraUserItemList);
////                        isStartList = true;
////
////                    }
////                    showEmpty();
////                }
////            }
////
////            @Override
////            public void onErrorOccurred(Exception ex) {
////                if (isAttached) {
////                    dialog.dismiss();
////                    dialog = null;
////                    Toast.makeText(getActivity(), "Some Error Occurred: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
////                    showEmpty();
////                }
////            }
////        }) {
////
////            @Override
////            protected Object doInBackground() throws Exception {
////
////                return getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
////
////            }
////        }.execute();
//        try {
//            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listWalletContacts("reference_wallet"/*referenceWalletSession.getWalletSessionType().getWalletPublicKey()*/);
//            for (CryptoWalletWalletContact wcr : walletContactRecords) {
//                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), wcr.getReceivedCryptoAddress().get(0).getAddress()));
//            }
//        } catch (CantGetAllWalletContactsException e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
//            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
//        }
//        return contacts;
//    }

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
            Toast.makeText(getActivity(),"transaccion"+childPosition,Toast.LENGTH_SHORT).show();
            return null;//transactions[groupPosition][childPosition];
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


             if (convertView == null) {

                convertView = inf.inflate(R.layout.wallets_teens_fragment_send_list_detail, parent, false);
                holder = new ViewHolder();

                holder.text = (TextView) convertView.findViewById(R.id.notes);

                convertView.setTag(holder);

                amount = new ViewHolder();
                amount.text = (TextView) convertView.findViewById(R.id.amount);


                amount.text.setText(transactions_amounts[groupPosition][childPosition].toString());

                when = new ViewHolder();
                when.text = (TextView) convertView.findViewById(R.id.when);


                when.text.setText(transactions_whens[groupPosition][childPosition].toString());

                ImageView send_to_contact =  (ImageView) convertView.findViewById(R.id.icon_send_to_contact);
                send_to_contact.setTag("SendToContactActivity|" + groupPosition + "|" + childPosition);

                ImageView  send_message = (ImageView) convertView.findViewById(R.id.icon_chat_over_trx);
                send_message.setTag("ChatOverTrxActivity|" + groupPosition + "|" + childPosition);


            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //holder.text.setText(getChild(groupPosition, childPosition).toString());

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


                //*** Seguramente por una cuestion de performance lo hacia asi, yo lo saque para que ande el prototippo
                if (convertView == null) {
                    convertView = inf.inflate(R.layout.wallets_teens_fragment_send_list_header, parent, false);

                    profile_picture = (ImageView) convertView.findViewById(R.id.profile_picture);
                    //asigned tagId at icons action
                    ImageView  send_profile_picture = (ImageView) convertView.findViewById(R.id.icon_send_profile);
                    send_profile_picture.setTag("SendToContactActivity|" + groupPosition + "|-1");

                    ImageView  send_message = (ImageView) convertView.findViewById(R.id.icon_send_message);
                    send_message.setTag("ContactsChatActivity|" + contacts[groupPosition].toString());


                    switch (groupPosition){
                        case 0:
                            profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                            break;
                        case 1:
                            profile_picture.setImageResource(R.drawable.brant_profile_picture);
                            break;
                        case 2:
                            profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                            break;
                        case 3:
                            profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                            break;
                        default:
                            profile_picture.setImageResource(R.drawable.guillermo_profile_picture);
                    }



                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.contact_name);

                    convertView.setTag(holder);

                    amount = new ViewHolder();
                    amount.text = (TextView) convertView.findViewById(R.id.amount);


                    amount.text.setText(amounts[groupPosition].toString());

                    when = new ViewHolder();
                    when.text = (TextView) convertView.findViewById(R.id.when);


                    when.text.setText(whens[groupPosition].toString());

                    total = new ViewHolder();
                    total.text = (TextView) convertView.findViewById(R.id.total_amount);

                    total.text.setText(totalAmount[groupPosition].toString());

                    history = new ViewHolder();
                    history.text = (TextView) convertView.findViewById(R.id.history_count);

                    history.text.setText(historyCount[groupPosition].toString());

                    note = new ViewHolder();
                    note.text = (TextView) convertView.findViewById(R.id.notes);


                    note.text.setText(notes[groupPosition].toString());

                    //expand icon
                    ImageView  recent_transactions = (ImageView) convertView.findViewById(R.id.recent_transactions);

                    //Set the arrow programatically, so we can control it - to expand child

                    recent_transactions.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            if(isExpanded){
                                ((ExpandableListView) parent).collapseGroup(groupPosition);
                            }
                            else ((ExpandableListView) parent).expandGroup(groupPosition, true);
                        }
                    });
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                //holder.text.setText(getGroup(groupPosition).toString());

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
