package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.PaintActivtyFeactures;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetBalanceException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.TransactionNewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ReceiveFragmentDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.formatBalanceString;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by mati on 2015.09.28..
 */
public class ReceiveTransactionsFragment extends FermatWalletListFragment<CryptoWalletTransaction> implements FermatListItemListeners<CryptoWalletTransaction>{

    // TODO: preguntar de donde saco el user id
    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();

    /**
     * MANAGERS
     */
    private CryptoWallet cryptoWallet;

    /**
     * Session
     */
    ReferenceWalletSession referenceWalletSession;

    /**
     * DATA
     */
    private List<CryptoWalletTransaction> lstCryptoWalletTransactions;
    private CryptoWalletTransaction selectedItem;

    /**
     * Executor Service
     */
    private ExecutorService executor;

    private int MAX_TRANSACTIONS = 20;
    private int offset = 0;

    View rootView;
    private LinearLayout linear_layout_receive_form;
    private AutoCompleteTextView autocompleteContacts;
    private WalletContactListAdapter contactsAdapter;
    private TextView txt_type_balance;
    private TextView txt_balance_amount;
    private WalletContact walletContact;
    private String user_address_wallet="";

    private long bookBalance;
    private long balanceAvailable;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static ReceiveTransactionsFragment newInstance() {
        ReceiveTransactionsFragment requestPaymentFragment = new ReceiveTransactionsFragment();
        return new ReceiveTransactionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession)walletSession;

        lstCryptoWalletTransactions = new ArrayList<CryptoWalletTransaction>();
        try {
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

            balanceAvailable = loadBalance(BalanceType.AVAILABLE);
            bookBalance = loadBalance(BalanceType.BOOK);

            lstCryptoWalletTransactions = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lstCryptoWalletTransactions = new ArrayList<CryptoWalletTransaction>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = super.onCreateView(inflater,container, savedInstanceState);
//
        RelativeLayout container_header_balance = getActivityHeader();

        inflater =
                (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        container_header_balance.setVisibility(View.VISIBLE);

        View balance_header = inflater.inflate(R.layout.balance_header, container_header_balance, true);

         txt_type_balance = (TextView) balance_header.findViewById(R.id.txt_type_balance);


        TextView txt_touch_to_change = (TextView) balance_header.findViewById(R.id.txt_touch_to_change);
        txt_touch_to_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"balance cambiado",Toast.LENGTH_SHORT).show();
                //txt_type_balance.setText(referenceWalletSession.getBalanceTypeSelected());
                changeBalanceType(txt_type_balance,txt_balance_amount);
            }
        });

        txt_balance_amount = (TextView) balance_header.findViewById(R.id.txt_balance_amount);

        try {
            long balance = cryptoWallet.getBalance(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()),referenceWalletSession.getWalletSessionType().getWalletPublicKey());
            txt_balance_amount.setText(formatBalanceString(balance, referenceWalletSession.getTypeAmount()));
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }

        //container_header_balance.invalidate();

        //((PaintActivtyFeactures)getActivity()).invalidate();

        //rootView    = inflater.inflate(R.layout.receive_button, container, false);


        linear_layout_receive_form = (LinearLayout) rootView.findViewById(R.id.receive_form);

        ((com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.fab_action)).setOnClickListener(new View.OnClickListener() {
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

        autocompleteContacts = (AutoCompleteTextView)rootView.findViewById(R.id.contact_name);

        contactsAdapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());

        autocompleteContacts.setAdapter(contactsAdapter);
        //autocompleteContacts.setTypeface(tf);
        autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                walletContact = (WalletContact) arg0.getItemAtPosition(position);
                //editTextAddress.setText(walletContact.address);
            }
        });

        Button btn_share = (Button) rootView.findViewById(R.id.btn_share);
        btn_share.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shareAddress();
            }
        });


        ((Button)rootView.findViewById(R.id.btn_address)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (walletContact != null) {
                    ReceiveFragmentDialog receiveFragmentDialog = new ReceiveFragmentDialog(getActivity(),cryptoWallet,referenceWalletSession.getErrorManager(),walletContact,user_id,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
                    receiveFragmentDialog.show();
                } else {
                    Toast.makeText(getActivity(),"Acá deberia salir para agregar contacto",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return rootView;
    }


    @Override
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.transaciotn_main_fragment_receive;
    }

    @Override
    protected int getSwipeRefreshLayoutId() {
        return R.id.swipe_refresh;
    }

    @Override
    protected int getRecyclerLayoutId() {
        return R.id.transactions_recycler_view;
    }

    @Override
    protected boolean recyclerHasFixedSize() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public FermatAdapter getAdapter() {
        if (adapter == null) {
            //WalletStoreItemPopupMenuListener listener = getWalletStoreItemPopupMenuListener();
            adapter = new TransactionNewAdapter(getActivity(), lstCryptoWalletTransactions,cryptoWallet,referenceWalletSession);
            adapter.setFermatListEventListener(this); // setting up event listeners
        }
        return adapter;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (layoutManager == null) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        return layoutManager;
    }

    @Override
    public List<CryptoWalletTransaction> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<CryptoWalletTransaction> lstTransactions  = new ArrayList<CryptoWalletTransaction>();

        try {
           lstTransactions = cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.CREDIT,referenceWalletSession.getWalletSessionType().getWalletPublicKey(),MAX_TRANSACTIONS,offset);
           offset+=MAX_TRANSACTIONS;
        }
        catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                    UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
           e.printStackTrace();
            // data = RequestPaymentListItem.getTestData(getResources());
        }
        /*CryptoWalletTransactionsTest cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
         cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
         cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);*/
        return lstTransactions;
    }

    @Override
    public void onItemClickListener(CryptoWalletTransaction item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
    }

    /**
     * On Long item Click Listener
     *
     * @param data
     * @param position
     */
    @Override
    public void onLongItemClickListener(CryptoWalletTransaction data, int position) {

    }


    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstCryptoWalletTransactions = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstCryptoWalletTransactions);
            }
        }
    }

    @Override
    public void onErrorOccurred(Exception ex) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }



    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }

    /**
     * Obtain the wallet contacts from the cryptoWallet
     *
     * @return
     */
    private List<WalletContact> getWalletContactList() {
        List<WalletContact> contacts = new ArrayList<>();

//        new FermatWorker(getActivity(), new FermatWorkerCallBack() {
//            @SuppressWarnings("unchecked")
//            @Override
//            public void onPostExecute(Object... result) {
//                if (isAttached) {
//                    if (adapter != null) {
//                        intraUserItemList = (ArrayList<IntraUserConnectionListItem>) result[0];
//                        adapter.changeDataSet(intraUserItemList);
//                        isStartList = true;
//
//                    }
//                    showEmpty();
//                }
//            }
//
//            @Override
//            public void onErrorOccurred(Exception ex) {
//                if (isAttached) {
//                    dialog.dismiss();
//                    dialog = null;
//                    Toast.makeText(getActivity(), "Some Error Occurred: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
//                    showEmpty();
//                }
//            }
//        }) {
//
//            @Override
//            protected Object doInBackground() throws Exception {
//
//                return getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
//
//            }
//        }.execute();
        try {
            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listWalletContacts("reference_wallet"/*referenceWalletSession.getWalletSessionType().getWalletPublicKey()*/);
            for (CryptoWalletWalletContact wcr : walletContactRecords) {
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), wcr.getReceivedCryptoAddress().get(0).getAddress()));
            }
        } catch (CantGetAllWalletContactsException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }
        return contacts;
    }





    /**
     * Method to change the balance type
     */
    private void changeBalanceType(TextView txt_type_balance,TextView txt_balance_amount) {

        try {
            if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.AVAILABLE.getCode())) {
                balanceAvailable = loadBalance(BalanceType.AVAILABLE);
                txt_balance_amount.setText(formatBalanceString(bookBalance, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.book_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.BOOK);
            } else if (referenceWalletSession.getBalanceTypeSelected().equals(BalanceType.BOOK.getCode())) {
                bookBalance = loadBalance(BalanceType.BOOK);
                txt_balance_amount.setText(formatBalanceString(balanceAvailable, referenceWalletSession.getTypeAmount()));
                txt_type_balance.setText(R.string.available_balance);
                referenceWalletSession.setBalanceTypeSelected(BalanceType.AVAILABLE);
            }
        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.ACTIVITY, UnexpectedUIExceptionSeverity.CRASH, FermatException.wrapException(e));
            Toast.makeText(getActivity().getApplicationContext(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
        }

    }

    private long loadBalance(BalanceType balanceType){
        long balance = 0;
        try {
            balance = cryptoWallet.getBalance(balanceType,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
        } catch (CantGetBalanceException e) {
            e.printStackTrace();
        }
        return balance;
    }



    public void shareAddress() {
        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");
        intent2.putExtra(Intent.EXTRA_TEXT, user_address_wallet);
        startActivity(Intent.createChooser(intent2, "Share via"));
    }


}
