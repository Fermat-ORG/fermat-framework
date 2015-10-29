package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListTransactionsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedPluginExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.TransactionNewAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.CreateContactFragmentDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.09.28..
 */
public class SendTransactionsFragment extends FermatWalletListFragment<CryptoWalletTransaction> implements FermatListItemListeners<CryptoWalletTransaction>, DialogInterface.OnDismissListener, Thread.UncaughtExceptionHandler {


    // TODO: preguntar de donde saco el user id
    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_LOAD_IMAGE = 2;
    public static final int CONTEXT_MENU_CAMERA = 1;
    public static final int CONTEXT_MENU_GALLERY = 2;

    private static final int CONTEXT_MENU_NO_PHOTO = 4;

    private static final int UNIQUE_FRAGMENT_GROUP_ID = 15;

    /**
     * MANAGERS
     */
    private CryptoWallet cryptoWallet;

    private IntraUserModuleManager intraUserModuleManager;
    /**
     * Session
     */
    ReferenceWalletSession referenceWalletSession;

    /**
     * DATA
     */
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsAvailable;
    private List<CryptoWalletTransaction> lstCryptoWalletTransactionsBook;

    private CryptoWalletTransaction selectedItem;

    /**
     * Executor Service
     */
    private ExecutorService executor;

    private int MAX_TRANSACTIONS = 20;

    private int available_offset = 0;
    private int book_offset = 0;

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
    private ReferenceWallet referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;


    private Bitmap contactImageBitmap;

    private AtomicBoolean start;

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

        referenceWalletSession = (ReferenceWalletSession) walletSession;

        lstCryptoWalletTransactionsBook = new ArrayList<>();
        lstCryptoWalletTransactionsAvailable = new ArrayList<>();

        intraUserModuleManager = referenceWalletSession.getIntraUserModuleManager();

        start = new AtomicBoolean(false);

        try {
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();


            updateTransactions();
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
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
                        empty.setVisibility(View.VISIBLE);
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

                    //add connection like a wallet contact
                    try
                    {
                        if(!walletContact.isConnection)
                            cryptoWallet.convertConnectionToContact(walletContact.name,
                                    Actors.INTRA_USER,
                                    walletContact.actorPublicKey,
                                    new byte[0],
                                    Actors.INTRA_USER,
                                    intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey(),
                                    "reference_wallet"/*referenceWalletSession.getWalletSessionType().getWalletPublicKey()*/ ,
                                    CryptoCurrency.BITCOIN,
                                    BlockchainNetworkType.TEST);

                    }
                    catch (CantGetActiveLoginIdentityException e) {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(getActivity(), "CantGetActiveLoginIdentityException- " + e.getMessage());
                    }
                    catch(CantCreateWalletContactException e)
                    {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(getActivity(), "CantCreateWalletContactException- " + e.getMessage());

                    }
                    catch(ContactNameAlreadyExistsException e)
                    {
                        referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                        showMessage(getActivity(), "ContactNameAlreadyExistsException- " + e.getMessage());

                    }
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
                        //Long amount = Long.parseLong(editTextAmount.getText().toString());
                        //if (amount > 0) {
                            //long actualBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE,referenceWalletSession.getWalletSessionType().getWalletPublicKey());
                            //editTextAmount.setHint("Available amount: " + actualBalance + " bits");
                        //}
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });


            FermatButton send_button = (FermatButton) rootView.findViewById(R.id.send_button);
            send_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                        im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }

                  if(walletContact!= null)
                        sendCrypto();
                    else
                        Toast.makeText(getActivity(), "Contact not found, please add it.", Toast.LENGTH_LONG).show();

                    //testing metadata
                         /*   cryptoWallet.sendMetadataLikeChampion(Long.parseLong("100000"),
                                    null,
                                    "holasdad",
                                    referenceWalletSession.getWalletSessionType().getWalletPublicKey(),
                                    "actor_prueba_juan_public_key",
                                    Actors.INTRA_USER,
                                    "actor_prueba_robert_public_key",
                                    Actors.INTRA_USER);*/

                }
            });

            send_button.selector(R.drawable.bg_home_accept_active,R.drawable.bg_home_accept_normal,R.drawable.bg_home_accept_active);


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


            empty=(LinearLayout) rootView.findViewById(R.id.empty);

            if (lstCryptoWalletTransactionsBook.isEmpty() && BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.BOOK)) {
                empty.setVisibility(View.VISIBLE);
            }else if (lstCryptoWalletTransactionsAvailable.isEmpty() && BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE)){
                empty.setVisibility(View.VISIBLE);
            }

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
            adapter = new TransactionNewAdapter(getActivity(), BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE) ? lstCryptoWalletTransactionsAvailable : lstCryptoWalletTransactionsBook,cryptoWallet,referenceWalletSession);
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


        updateTransactions();

        return null;
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
            //if (result != null && result.length > 0) {
            //lstCryptoWalletTransactions.addAll((ArrayList) result[0]);
            if (adapter != null)
                adapter.changeDataSet(BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE) ? lstCryptoWalletTransactionsAvailable : lstCryptoWalletTransactionsBook);


            if(start.get()) {

                if (!lstCryptoWalletTransactionsBook.isEmpty() && BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.BOOK)) {
                    empty.setVisibility(View.GONE);
                } else if (!lstCryptoWalletTransactionsAvailable.isEmpty() && BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE)) {
                    empty.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.VISIBLE);
                }
            }
            //}
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

    //TODO: VER QUE PASA  SI EL CONTACTO NO TIENE UNA WALLET ADDRESS
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
                            cryptoWalletWalletContact.getActorType(),
                            referenceWallet
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
                } catch (Exception e) {
                e.printStackTrace();
                    Toast.makeText(getActivity(),"oooopps",Toast.LENGTH_SHORT).show();
                 }
            }
        } else {
            Toast.makeText(getActivity(), "Invalid Address", Toast.LENGTH_LONG).show();

        }
    }

    private void updateTransactions(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try {

                    lstCryptoWalletTransactionsAvailable.addAll(cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.AVAILABLE, TransactionType.DEBIT, referenceWalletSession.getWalletSessionType().getWalletPublicKey(), MAX_TRANSACTIONS, available_offset));

                    available_offset = lstCryptoWalletTransactionsAvailable.size();

                    lstCryptoWalletTransactionsBook.addAll(cryptoWallet.listLastActorTransactionsByTransactionType(BalanceType.BOOK, TransactionType.DEBIT, referenceWalletSession.getWalletSessionType().getWalletPublicKey(), MAX_TRANSACTIONS, book_offset));

                    book_offset = lstCryptoWalletTransactionsBook.size();



                } catch (CantListTransactionsException e) {
                    e.printStackTrace();
                }

            }
        };

        Thread thread = new Thread(runnable);

        thread.start();
    }



    /**
     * Obtain the wallet contacts from the cryptoWallet
     *
     * @return
     */
    private List<WalletContact> getWalletContactList() {
        List<WalletContact> contacts = new ArrayList<>();
        try
        {
            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listAllActorContactsAndConnections(referenceWalletSession.getWalletSessionType().getWalletPublicKey(), intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey());
            for (CryptoWalletWalletContact wcr : walletContactRecords) {

                String contactAddress = "";
                if(wcr.getReceivedCryptoAddress().size() > 0)
                    contactAddress = wcr.getReceivedCryptoAddress().get(0).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress,wcr.isConnection()));
            }
        }
        catch (CantGetAllWalletContactsException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }
        catch (CantGetActiveLoginIdentityException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetActiveLoginIdentityException- " + e.getMessage());
        }
        return contacts;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            contactImageBitmap = null;
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    contactImageBitmap = (Bitmap) extras.get("data");
                    break;
                case REQUEST_LOAD_IMAGE:
                    Uri selectedImage = data.getData();
                    try {
                        contactImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);

                        //imageBitmap = Bitmap.createScaledBitmap(imageBitmap,take_picture_btn.getWidth(),take_picture_btn.getHeight(),true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity().getApplicationContext(), "Error cargando la imagen", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            //take_picture_btn.setBackground(new RoundedDrawable(imageBitmap, take_picture_btn));
            //take_picture_btn.setImageDrawable(null);
            //contactPicture = imageBitmap;
            this.lauchCreateContactDialogSend(true);

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select contact picture");
        menu.setHeaderIcon(getActivity().getResources().getDrawable(R.drawable.ic_camera_green));
        menu.add(UNIQUE_FRAGMENT_GROUP_ID, CONTEXT_MENU_CAMERA, Menu.NONE, "Camera");
        menu.add(UNIQUE_FRAGMENT_GROUP_ID, CONTEXT_MENU_GALLERY, Menu.NONE, "Gallery");
        menu.add(UNIQUE_FRAGMENT_GROUP_ID, CONTEXT_MENU_NO_PHOTO, Menu.NONE, "No photo");
//        if(contactImageBitmap!=null)
//            menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //only this fragment's context menus have group ID
        if (item.getGroupId() == UNIQUE_FRAGMENT_GROUP_ID) {
            switch (item.getItemId()) {
                case CONTEXT_MENU_CAMERA:
                    dispatchTakePictureIntent();
                    break;
                case CONTEXT_MENU_GALLERY:
                    loadImageFromGallery();
                    break;
                case CONTEXT_MENU_NO_PHOTO:

//                takePictureButton.setBackground(getActivity().getResources().
//                        getDrawable(R.drawable.rounded_button_green_selector));
//                takePictureButton.setImageResource(R.drawable.ic_camera_green);
//                contactPicture = null;
                    this.lauchCreateContactDialogSend(false);
                    break;
            }
        }

        return super.onContextItemSelected(item);
    }

    private void loadImageFromGallery() {
        Intent intentLoad = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentLoad, REQUEST_LOAD_IMAGE);
    }

    private void lauchCreateContactDialogSend(boolean withImage){
        CreateContactFragmentDialog dialog = new CreateContactFragmentDialog(
                getActivity(),
                referenceWalletSession,
                walletContact,
                user_id,
                ((withImage) ? contactImageBitmap : null));
        dialog.setOnDismissListener(this);
        dialog.show();
    }


    @Override
    public void onDismiss(DialogInterface dialogInterface) {


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                contactsAdapter.clear();
                contactsAdapter.addAll(getWalletContactList());

                contactsAdapter.notifyDataSetChanged();
            }
        };
        Thread thread = new Thread(runnable);

        thread.setUncaughtExceptionHandler(this);

        thread.start();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        referenceWalletSession.getErrorManager().reportUnexpectedPluginException(Plugins.BITDUBAI_BANK_NOTES_WALLET_WALLET_MODULE, UnexpectedPluginExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_PLUGIN, new Exception());
        Toast.makeText(getActivity(),"oooopps",Toast.LENGTH_SHORT).show();
    }
}
