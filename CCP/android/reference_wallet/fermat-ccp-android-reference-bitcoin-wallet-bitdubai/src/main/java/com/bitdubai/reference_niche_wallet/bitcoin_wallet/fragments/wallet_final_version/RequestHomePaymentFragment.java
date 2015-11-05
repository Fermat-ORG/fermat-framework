package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FontType;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.ui.adapters.FermatAdapter;
import com.bitdubai.fermat_android_api.ui.enums.FermatRefreshTypes;
import com.bitdubai.fermat_android_api.ui.fragments.FermatWalletListFragment;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatListItemListeners;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums.SubApps;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedSubAppExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.adapters.PaymentRequestHomeAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.custom_anim.Fx;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.10.01..
 */
public class RequestHomePaymentFragment extends FermatWalletListFragment<PaymentRequest>
        implements FermatListItemListeners<PaymentRequest> {

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
    private List<PaymentRequest> lstPaymentRequest;
    private PaymentRequest selectedItem;

    String walletPublicKey = "reference_wallet";
    /**
     * Executor Service
     */
    private ExecutorService executor;

    private int offset = 0;
    private WalletContact walletContact;
    private boolean activeAddress = true;


    private View rootView;
    private LinearLayout linear_layout_send_form;
    private AutoCompleteTextView autocompleteContacts;
    private EditText editTextAddress;
    private EditText editTextAmount;
    private LinearLayout empty;
    private WalletContactListAdapter contactsAdapter;
    private TextView txt_notes;
    private LinearLayout linear_address;

    /**
     * MANAGERS
     */

    private IntraUserModuleManager intraUserModuleManager;

    /**
     * Fragment Style
     */
    Typeface tf;

    /**
     * Create a new instance of this fragment
     *
     * @return InstalledFragment instance object
     */
    public static RequestHomePaymentFragment newInstance() {
        RequestHomePaymentFragment requestPaymentFragment = new RequestHomePaymentFragment();
        return new RequestHomePaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");
        referenceWalletSession = (ReferenceWalletSession)walletSession;

        intraUserModuleManager = referenceWalletSession.getIntraUserModuleManager();

        super.onCreate(savedInstanceState);
        try {
            cryptoWallet = referenceWalletSession.getCryptoWalletManager().getCryptoWallet();

            lstPaymentRequest = getMoreDataAsync(FermatRefreshTypes.NEW, 0); // get init data
        } catch (Exception ex) {
            //CommonLogger.exception(TAG, ex.getMessage(), ex);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

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

            autocompleteContacts.setTypeface(tf);
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
                        if(walletContact.isConnection)
                            cryptoWallet.convertConnectionToContact(walletContact.name,
                                    Actors.INTRA_USER,
                                    walletContact.actorPublicKey,
                                    walletContact.profileImage,
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
            editTextAddress.setTypeface(tf);

            /**
             * Notes line
             */

            txt_notes = (TextView) rootView.findViewById(R.id.notes);
            txt_notes.setTypeface(tf);

            /**
             * Amount
             */

            editTextAmount = (EditText) rootView.findViewById(R.id.amount);
            editTextAmount.setTypeface(tf);
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

            send_button.setFont(FontType.ROBOTO_REGULAR);
            send_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                        im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }

//                    if(walletContact!= null)
//                        //TODO: ACA VA EL REQUEST
//
//                        //sendCrypto();
//                    else
//                        Toast.makeText(getActivity(), "Contact not found, please add it.", Toast.LENGTH_LONG).show();

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

            if (lstPaymentRequest.isEmpty() && BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.BOOK)) {
                empty.setVisibility(View.VISIBLE);
            }else if (lstPaymentRequest.isEmpty() && BalanceType.getByCode(referenceWalletSession.getBalanceTypeSelected()).equals(BalanceType.AVAILABLE)){
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
        return R.layout.home_request_fragment_main;
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
            adapter = new PaymentRequestHomeAdapter(getActivity(), lstPaymentRequest, cryptoWallet,(ReferenceWalletSession)walletSession);
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
    public List<PaymentRequest> getMoreDataAsync(FermatRefreshTypes refreshType, int pos) {
        List<PaymentRequest> lstPaymentRequest  = null;

        try {
            lstPaymentRequest = cryptoWallet.listPaymentRequestDateOrder(walletPublicKey,10,0);
            offset = lstPaymentRequest.size();

        } catch (Exception e) {
            referenceWalletSession.getErrorManager().reportUnexpectedSubAppException(SubApps.CWP_WALLET_STORE,
                    UnexpectedSubAppExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);

            //data = RequestPaymentListItem.getTestData(getResources());
        }

        return lstPaymentRequest;
    }

    @Override
    public void onItemClickListener(PaymentRequest item, int position) {
        selectedItem = item;
        //showDetailsActivityFragment(selectedItem);
    }

    @Override
    public void onLongItemClickListener(PaymentRequest data, int position) {
        // do nothing
    }

    @Override
    public void onPostExecute(Object... result) {
        isRefreshing = false;
        if (isAttached) {
            swipeRefreshLayout.setRefreshing(false);
            if (result != null && result.length > 0) {
                lstPaymentRequest = (ArrayList) result[0];
                if (adapter != null)
                    adapter.changeDataSet(lstPaymentRequest);
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

    /**
     * Obtain the wallet contacts from the cryptoWallet
     *
     * @return
     */
    private List<WalletContact> getWalletContactList() {
        List<WalletContact> contacts = new ArrayList<>();
        try
        {
            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listAllActorContactsAndConnections("reference_wallet"/*referenceWalletSession.getWalletSessionType().getWalletPublicKey()*/, intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey());
            for (CryptoWalletWalletContact wcr : walletContactRecords) {
                String contactAddress = "";
                if(wcr.getReceivedCryptoAddress().size() > 0)
                    contactAddress = wcr.getReceivedCryptoAddress().get(0).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress,wcr.isConnection(),wcr.getProfilePicture()));
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



}
