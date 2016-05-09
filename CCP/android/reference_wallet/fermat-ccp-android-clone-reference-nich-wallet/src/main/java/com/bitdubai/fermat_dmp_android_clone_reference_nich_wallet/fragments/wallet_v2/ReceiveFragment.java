package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.fragments.wallet_v2;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.GeneratorQR;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.R;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.custom_anim.Fx;
import com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.session.ReferenceWalletSession;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.google.zxing.WriterException;

import java.util.UUID;

import static com.bitdubai.fermat_android_api.layer.definition.wallet.utils.GeneratorQR.generateBitmap;


public  class ReceiveFragment extends AbstractFermatFragment {

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

    ReferenceWalletSession referenceWalletSession;

    /**
     * Wallet contact Adapter
     */
    //private WalletContactListAdapter adapter;

    private AutoCompleteTextView contact_name;

    /**
     * font Style
     */
    Typeface tf;


    private String user_address_wallet = "";
    final int colorQR = Color.BLACK;
    final int colorBackQR = Color.WHITE;
    final int width = 400;
    final int height = 400;

    private CryptoWallet cryptoWallet;


    // TODO: Necesito saber de donde sacarlo
    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();

    private LinearLayout linear_layout_receive_form;


    public static ReceiveFragment newInstance(int position) {
        ReceiveFragment f = new ReceiveFragment();
        f.setReferenceWalletSession((ReferenceWalletSession) f.appSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contacts = new String[]{"Lucia Alarcon De Zamacona", "Juan Luis R. Pons", "Karina Rodríguez", "Simon Cushing","Céline Begnis","Taylor Backus","Stephanie Himonidis","Kimberly Brown" };
        amounts = new String[]{"$200.00", "$3,000.00", "$400.00", "$3.00","$45.00","$600.00","50.00","$80,000.00"};
        whens = new String[]{"4 hours ago", "5 hours ago", "yesterday 11:00 PM", "24 Mar 14","3 Feb 14","1 year ago","1 year ago","2 year ago"};
        notes = new String[]{"New telephone", "Old desk", "Car oil", "Sandwich","Headphones","Computer monitor","Pen","Apartment in Dubai"};
        totalAmount = new String[]{"$" + "17,485.00","$" + "156,340.00","$" + "422,545","$" + "62,735.00","$" + "45.00","$" + "12,360.00","$" + "75.00","$"+ "80,000"};
        historyCount = new String[] {"9 records","19 records","32 records","11 records","1 record","11 records","2 records","1 record"};
        //pictures = new String[]{"luis_profile_picture", "guillermo_profile_picture", "pedro_profile_picture", "mariana_profile_picture"};

        transactions = new String[][]{

                {"New telephone","Hot dog","Telephone credit","Coffee"},
                {"Old desk","Flat rent","New glasses","House in Europe","Coffee","Gum"},
                {"Car oil","Headphgiones","Apartment"},
                {"Sandwich","New kitchen","Camera repair"},
                {"Headphones"},
                {"Computer monitor","New car"},
                {"Pen"},
                {"Apartment in Dubai"}
        };
        transactions_amounts = new String[][]{


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
        rootView = inflater.inflate(R.layout.bitcoin_wallet_receive_fragment_base, container, false);

        linear_layout_receive_form = (LinearLayout)rootView.findViewById(R.id.receive_form);

        rootView.findViewById(R.id.btn_expand_receive_form).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isShow = linear_layout_receive_form.isShown();
                //linear_layout_send_form.setVisibility(isShow?View.GONE:View.VISIBLE);
                if (isShow) {
                    Fx.slide_up(getActivity(), linear_layout_receive_form);
                    linear_layout_receive_form.setVisibility(View.GONE);
                } else {
                    linear_layout_receive_form.setVisibility(View.VISIBLE);
                    Fx.slide_down(getActivity(), linear_layout_receive_form);
                }

            }
        });

        contact_name = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);

        Button btn_address = (Button) rootView.findViewById(R.id.btn_address);
//        btn_address.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                requestAddress(null);
//            }
//        });
//        btn_address.setTypeface(tf);
//
//        Button btn_share = (Button) rootView.findViewById(R.id.btn_share);
//        btn_share.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                shareAddress();
//            }
//        });
        //btn_share.setTypeface(tf);


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

                /* Intent intent;
                 intent = new Intent(getActivity(), ReceiveFromNewContactActivity.class);
                 startActivity(intent);*///                    Intent intent;
//                    appRuntimeMiddleware.getActivity(Activities.CWP_WALLET_RUNTIME_ADULTS_ALL_CONTACTS_NEW_RECEIVE);
//                    intent = new Intent(getActivity(), com.bitdubai.android_core.app.FragmentActivity.class);
//                    startActivity(intent);
                return groupPosition == 0;
            }
        });

    }

    /**
     * copy wallet address to clipboard
     */

    public void copyToClipboard() {

        ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", this.user_address_wallet);
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(getActivity().getApplicationContext(), "Text Copied",
                Toast.LENGTH_SHORT).show();
    }

    private void showQRCodeAndAddress() {
        try {
            // set qr image
            ImageView imageQR = (ImageView) rootView.findViewById(R.id.qr_code);
            // get qr image


            Bitmap bitmapQR = generateBitmap(user_address_wallet,width, height, GeneratorQR.MARGIN_AUTOMATIC, colorQR, colorBackQR);

            imageQR.setImageBitmap(bitmapQR);
            // show qr image
            imageQR.setVisibility(View.VISIBLE);

            // set string_address
            TextView string_address = (TextView) rootView.findViewById(R.id.string_address);
            string_address.setText(user_address_wallet);
            // show string_address
            string_address.setVisibility(View.VISIBLE);

            // show share_btn
            Button share_btn = (Button) rootView.findViewById(R.id.share_btn);
            share_btn.setEnabled(true);
            share_btn.setFocusable(true);
            share_btn.setTextColor(Color.parseColor("#72af9c"));
        } catch (WriterException writerException) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(writerException));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }


//    public void requestAddress(WalletContact walletcontact) {
//        if (contact_name != null && contact_name.getText().toString() != null && !contact_name.getText().toString().equals("")) {
//            if (walletcontact == null) {
//                getWalletAddress(contact_name.getText().toString());
//            } else {
//                getWalletAddress(walletcontact);
//            }
//            showQRCodeAndAddress();
//        } else {
//            Toast.makeText(getActivity().getApplicationContext(), "Enter a name to share your address", Toast.LENGTH_SHORT).show();
//        }
//    }

    public void shareAddress() {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, user_address_wallet);
        startActivity(Intent.createChooser(intent2, "Share via"));
    }

    private void getWalletAddress(String contact_name) {
        try {
            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
            CryptoAddress cryptoAddress = cryptoWallet.requestAddressToNewExtraUser(
                    user_id,
                    Actors.INTRA_USER,
                    contact_name,
                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                    VaultType.CRYPTO_CURRENCY_VAULT,
                    "BITV",
                    null,
                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
            ); user_address_wallet = cryptoAddress.getAddress();
        } catch (CantRequestCryptoAddressException e) {
            //referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();

        }
    }
//    private void getWalletAddress(WalletContact walletContact) {
//        try {
//            //TODO parameters deliveredByActorId deliveredByActorType harcoded..
//            CryptoAddress cryptoAddress = cryptoWallet.requestAddressToKnownUser(
//                    user_id,
//                    Actors.INTRA_USER,
//                    walletContact.actorPublicKey,
//                    Actors.EXTRA_USER,
//                    Platforms.CRYPTO_CURRENCY_PLATFORM,
//                    VaultType.CRYPTO_CURRENCY_VAULT,
//                    "BITV",
//                    referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
//                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
//            );
//            user_address_wallet = cryptoAddress.getAddress();
//        } catch (CantRequestCryptoAddressException e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
//            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
//
//        }
//    }

    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
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


            if (convertView == null) {

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
                            profile_picture.setImageResource(R.drawable.juan_profile_picture);
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