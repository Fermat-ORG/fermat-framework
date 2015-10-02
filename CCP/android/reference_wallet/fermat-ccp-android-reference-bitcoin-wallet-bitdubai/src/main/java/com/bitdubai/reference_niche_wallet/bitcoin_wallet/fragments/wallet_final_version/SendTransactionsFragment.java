package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.TransactionNewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.09.28..
 */
public class SendTransactionsFragment extends FermatWalletListFragment<CryptoWalletTransaction> implements FermatListItemListeners<CryptoWalletTransaction>{

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

    /**
     * UI
     */
    private View rootView;
    private LinearLayout linear_layout_send_form;
    private AutoCompleteTextView autocompleteContacts;
    private EditText editTextAddress;
    private EditText editTextAmount;

    private WalletContactListAdapter contactsAdapter;


    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static SendTransactionsFragment newInstance() {
        SendTransactionsFragment requestPaymentFragment = new SendTransactionsFragment();
        return new SendTransactionsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        referenceWalletSession = (ReferenceWalletSession)walletSession;

        lstCryptoWalletTransactions = new ArrayList<CryptoWalletTransaction>();
        try {
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

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
        rootView = super.onCreateView(inflater, container, savedInstanceState);


        linear_layout_send_form = (LinearLayout)rootView.findViewById(R.id.send_form);

        ((com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.fab_action)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isShow =linear_layout_send_form.isShown();
                //linear_layout_send_form.setVisibility(isShow?View.GONE:View.VISIBLE);
                if(isShow){
                    Fx.slide_up(getActivity(), linear_layout_send_form);
                    linear_layout_send_form.setVisibility(View.GONE);
                    //showDialog();
                }
                else{
                    linear_layout_send_form.setVisibility(View.VISIBLE);
                    Fx.slide_down(getActivity(), linear_layout_send_form);
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
                WalletContact walletContact = (WalletContact) arg0.getItemAtPosition(position);
                editTextAddress.setText(walletContact.address);
            }
        });

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
                IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
                integrator.initiateScan();
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
    protected boolean hasMenu() {
        return false;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.transaction_main_fragment_send;
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

//        try {
//            lstTransactions = cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.DEBIT,referenceWalletSession.getWalletSessionType().getWalletPublicKey(),MAX_TRANSACTIONS,offset);
//            offset+=MAX_TRANSACTIONS;
//        } catch (Exception e) {
//            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(e,
//                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
        //    e.printStackTrace();
            // data = RequestPaymentListItem.getTestData(getResources());
        //}
        CryptoWalletTransactionsTest cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
         cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
         cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
        lstTransactions.add(cryptoWalletTransactionsTest);
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

}
