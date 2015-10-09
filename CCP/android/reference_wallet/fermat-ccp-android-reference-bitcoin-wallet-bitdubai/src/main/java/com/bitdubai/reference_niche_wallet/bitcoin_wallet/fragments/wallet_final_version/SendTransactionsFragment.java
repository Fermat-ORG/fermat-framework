package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_api.layer.dmp_wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.TransactionNewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.09.28..
 */
public class SendTransactionsFragment extends FermatWalletListFragment<CryptoWalletTransaction> implements FermatListItemListeners<CryptoWalletTransaction>{


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

    /**
     * UI
     */
    private View rootView;
    private LinearLayout linear_layout_send_form;
    private AutoCompleteTextView autocompleteContacts;
    private EditText editTextAddress;
    private EditText editTextAmount;

    private WalletContactListAdapter contactsAdapter;

    private WalletContact walletContact;


    private int actorType;
    private TextView txt_notes;
    private LinearLayout linear_address;


    private Handler mHandler = new Handler();
    private boolean activeAddress = true;
    private LinearLayout empty;

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

            lstCryptoWalletTransactions = getMoreDataAsync(FermatRefreshTypes.NEW, offset); // get init data
        } catch (Exception ex) {
            ex.printStackTrace();
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(getActivity());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        try {

            rootView = super.onCreateView(inflater, container, savedInstanceState);
            empty = (LinearLayout)rootView.findViewById(R.id.empty);

            linear_layout_send_form = (LinearLayout) rootView.findViewById(R.id.send_form);

            ((com.melnykov.fab.FloatingActionButton) rootView.findViewById(R.id.fab_action)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isShow = linear_layout_send_form.isShown();
                    //linear_layout_send_form.setVisibility(isShow?View.GONE:View.VISIBLE);
                    if (isShow) {
                        Fx.slide_up(getActivity(), linear_layout_send_form);
                        linear_layout_send_form.setVisibility(View.GONE);
                        if(lstCryptoWalletTransactions.isEmpty()){
                            empty.setVisibility(View.VISIBLE);
                        }
                        //showDialog();
                    } else {
                        linear_layout_send_form.setVisibility(View.VISIBLE);
                        Fx.slide_down(getActivity(), linear_layout_send_form);
                        empty.setVisibility(View.GONE);
                    }

                }
            });

           empty.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
               @Override
               public void onSystemUiVisibilityChange(int i) {
                   Fx.slide_up(getActivity(), empty);
               }
           });


            autocompleteContacts = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);


            contactsAdapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());

            autocompleteContacts.setAdapter(contactsAdapter);
            //autocompleteContacts.setTypeface(tf);
            autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    walletContact = (WalletContact) arg0.getItemAtPosition(position);
                    editTextAddress.setText(walletContact.address);
                    linear_address.setVisibility(View.GONE);
                }
            });


            autocompleteContacts.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    linear_address.setVisibility(activeAddress ? View.VISIBLE : View.GONE);
                    // if (!editTextAddress.getText().equals("")) linear_address.setVisibility(View.VISIBLE);
                }
            });

            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView_new_contact);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    walletContact = new WalletContact();
                    walletContact.setName(autocompleteContacts.getText().toString());
                    registerForContextMenu(autocompleteContacts);
                    getActivity().openContextMenu(autocompleteContacts);
                }
            });




            /**
             *  Address line
             */
            linear_address = (LinearLayout) rootView.findViewById(R.id.linear_address);


            editTextAddress = (EditText) rootView.findViewById(R.id.address);
            editTextAddress.setText("");

            /**
             * Notes line
             */

            txt_notes = (TextView) rootView.findViewById(R.id.notes);

            /**
             * Amount
             */

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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });


            ((Button) rootView.findViewById(R.id.send_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                        im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                    sendCrypto();

                    //testing
//
//                    EditText amount = (EditText) rootView.findViewById(R.id.amount);
//
//
//                    String notes = txt_notes.getText().toString();
//
//                    cryptoWallet.sendMetadataLikeChampion(Long.parseLong("100000"),
//                            null,
//                            "holasdad",
//                            referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
//                            user_id,
//                            Actors.INTRA_USER,
//                            "actor_prueba_robert_public_key",
//                            Actors.INTRA_USER);
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


            if (lstCryptoWalletTransactions.isEmpty()) {
                empty = (LinearLayout) rootView.findViewById(R.id.empty);
                empty.setVisibility(View.VISIBLE);
            }


            //tv = (TextView) convertView.findViewById(R.id.new_contact_name);


            return rootView;
        }catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error",
                    LENGTH_LONG).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH,e);
        }
        return null;
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

       try {
           lstTransactions = cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()), TransactionType.DEBIT,referenceWalletSession.getWalletSessionType().getWalletPublicKey(),MAX_TRANSACTIONS,offset);
           offset+=lstTransactions.size();
       }
       catch (Exception e) {
           referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                   UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            e.printStackTrace();
            // data = RequestPaymentListItem.getTestData(getResources());
        }
       /* CryptoWalletTransactionsTest cryptoWalletTransactionsTest = new CryptoWalletTransactionsTest();
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
                lstCryptoWalletTransactions.addAll((ArrayList) result[0]);
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
            referenceWalletSession.getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_CRYPTO_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, ex);
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }



    public void setReferenceWalletSession(ReferenceWalletSession referenceWalletSession) {
        this.referenceWalletSession = referenceWalletSession;
    }
    /**
     * Validate address taking the cryptoWallet reference
     *
     * @param strToValidate
     * @return
     */
    private CryptoAddress validateAddress(String strToValidate) {
        String[] tokens = strToValidate.split("-|\\.|:|,|;| ");

        CryptoAddress cryptoAddress = new CryptoAddress(null, CryptoCurrency.BITCOIN);
        for (String token : tokens) {
            token = token.trim();
            if (token.length() > 25 && token.length() < 40) {
                cryptoAddress.setAddress(token);
                if (cryptoWallet.isValidAddress(cryptoAddress)) {
                    return cryptoAddress;
                }
            }
        }
        //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
        return null;
    }

    /**
     * Send action
     */
    private void sendCrypto() {

        CryptoAddress validAddress = validateAddress(editTextAddress.getText().toString());


        if (validAddress != null) {
            EditText amount = (EditText) rootView.findViewById(R.id.amount);

            if(!amount.getText().toString().equals("") && amount.getText()!=null) {
                try {
                    String notes=null;
                    if(txt_notes.getText().toString().length()!=0){
                        notes = txt_notes.getText().toString();
                    }

                    CryptoWalletWalletContact cryptoWalletWalletContact = cryptoWallet.findWalletContactById(walletContact.contactId);

                    //TODO: ver que mas puedo usar del cryptoWalletWalletContact

                    cryptoWallet.send(
                            Long.parseLong(amount.getText().toString()),
                            validAddress,
                            notes,
                            referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
                            user_id,
                            Actors.INTRA_USER,
                            walletContact.actorPublicKey,
                            cryptoWalletWalletContact.getActorType()
                    );




                    Toast.makeText(getActivity(), "Send OK", Toast.LENGTH_LONG).show();
                } catch (InsufficientFundsException e) {
                    Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_LONG).show();
                } catch (CantSendCryptoException e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "Error send satoshis - " + e.getMessage());
                } catch (WalletContactNotFoundException e) {
                    e.printStackTrace();
                } catch (CantFindWalletContactException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Invalid Address", Toast.LENGTH_LONG).show();

        }
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
