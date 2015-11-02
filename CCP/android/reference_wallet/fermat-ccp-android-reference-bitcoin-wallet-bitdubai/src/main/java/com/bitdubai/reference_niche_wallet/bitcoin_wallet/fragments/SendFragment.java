package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_resources.interfaces.WalletResourcesProviderManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentResult;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by natalia on 19/06/15.
 */
public class SendFragment extends FermatWalletFragment {

    private static final String ARG_POSITION = "position";
    /**
     * DealsWithWalletModuleCryptoWallet Interface member variables.
     */
    private static CryptoWalletManager cryptoWalletManager;

    View rootView;
    String walletPublicKey = "reference_wallet";
    String user_id = UUID.fromString("afd0647a-87de-4c56-9bc9-be736e0c5059").toString();
    Typeface tf;
    CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    private AutoCompleteTextView autocompleteContacts;
    private WalletContactListAdapter adapter;

    /**
     * Layout members
     */
    private EditText editAddress;
    private EditText editAmount;
    private EditText editNotes;

    //private LinearLayout linear_notes;
    //private LinearLayout linear_send;

    private WalletContact contact;

    public boolean fromContacts = true;

    private ReferenceWallet referenceWallet = ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET;

    /**
     * Resources
     */
    private WalletResourcesProviderManager walletResourcesProviderManager;
    private ReferenceWalletSession referenceWalletSession;

    /**
     * Create a new instance of SendFragment and set walletSession and platforms plugin inside
     *
     * @return
     */
    public static SendFragment newInstance() {
        SendFragment f = new SendFragment();

        return f;
    }

    /**
     * Set wallet contact to pre-load ui controls
     *
     * @param contact wallet contact
     */
    public void setContact(WalletContact contact) {
        this.contact = contact;
    }

    /**
     * Load cryptoWallet and errorManager inside the fragment
     *
     * @param savedInstanceState
     */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        referenceWalletSession = (ReferenceWalletSession) walletSession;

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/roboto.ttf");


        cryptoWalletManager = referenceWalletSession.getCryptoWalletManager();
        errorManager = walletSession.getErrorManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
            ;
        }
    }

    /**
     * Load UI
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_send_new, container, false);
        try {

            CryptoWalletWalletContact cryptoWalletWalletContact = referenceWalletSession.getLastContactSelected();
            if(cryptoWalletWalletContact!=null) contact = new WalletContact(cryptoWalletWalletContact.getContactId(),cryptoWalletWalletContact.getActorPublicKey(),cryptoWalletWalletContact.getActorName(),cryptoWalletWalletContact.getReceivedCryptoAddress().get(0).getAddress(),cryptoWalletWalletContact.isConnection(),cryptoWalletWalletContact.getProfilePicture());

            //contact = referenceWalletSession.getLastContactSelected();

            editAddress = (EditText) rootView.findViewById(R.id.address);
            editAddress.setTypeface(tf);
            editAmount = (EditText) rootView.findViewById(R.id.amount);
            editAmount.setTypeface(tf);
            editNotes = (EditText) rootView.findViewById(R.id.notes);
            editNotes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        /* Send coins... */
                        rootView.findViewById(R.id.send_button).performClick();
                        return true;
                    }
                    return false;
                }
            });
            editNotes.setTypeface(tf);
            //linear_notes = (LinearLayout) rootView.findViewById(R.id.linear_notes);
            //linear_send = (LinearLayout) rootView.findViewById(R.id.linear_send);

            autocompleteContacts = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);
            adapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());
            autocompleteContacts.setAdapter(adapter);
            autocompleteContacts.setTypeface(tf);


            autocompleteContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    WalletContact walletContact = (WalletContact) arg0.getItemAtPosition(position);
                    editAddress.setText(walletContact.address);
                }
            });

            try {
                long availableBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE, walletPublicKey);
                editAmount.setHint("available funds: " + availableBalance + " bits");
            } catch (Exception ex) {

            }

            final ImageView sendButton = (ImageView) rootView.findViewById(R.id.send_button);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                        im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                    sendCrypto();
                }
            });
            if (fromContacts) {
                rootView.findViewById(R.id.change_account).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().getFragmentManager()
                                .beginTransaction()
                                        // TODO commented due to error
                                //.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
                                .replace(R.id.fragment_container2, ContactsFragment.newInstance())
                                .commit();
                    }
                });
                ((TextView) rootView.findViewById(R.id.change_account)).setTypeface(tf);
            } else {
                rootView.findViewById(R.id.change_account).setVisibility(View.GONE);
            }
            /**
             *  Address validation
             */
          /*  editAddress.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (validateAddress(editAddress.getText().toString()) != null) {
                        editAddress.setTextColor(Color.parseColor("#72af9c"));
                    } else {
                        editAddress.setTextColor(Color.parseColor("#b46a54"));
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });*/

            /**
             *  Paste clipboard button
             ImageView pasteFromClipboardButton = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
             pasteFromClipboardButton.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
             pasteFromClipboard(rootView);
             }
             });*/

            /**
             *  Amount observer
             */
            editAmount.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    try {
                        Long amount = Long.parseLong(editAmount.getText().toString());
                        if (amount > 0) {
                            sendButton.setEnabled(true);
                            editNotes.setEnabled(true);
                        }
                    } catch (Exception e) {
                        try {
                            long actualBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE, walletPublicKey);
                            editAmount.setHint("Available amount: " + actualBalance + " bits");
                        } catch (Exception ex) {

                        }
                        sendButton.setEnabled(false);
                        editNotes.setEnabled(false);
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

            /* pre-load wallet contact if needed */
            if (contact != null) {
                editAddress.setText(contact.address);
                autocompleteContacts.setText(contact.name);
            }
            /* Set Enable only if it's called from tab, otherwise the user cannot edit this controls */
            editAddress.setEnabled(contact == null);
            autocompleteContacts.setEnabled(contact == null);
            /*Setting up typeface*/
            ((TextView) rootView.findViewById(R.id.transaction_title)).setTypeface(tf);
            ((TextView) rootView.findViewById(R.id.account_details_title)).setTypeface(tf);
            ((TextView) rootView.findViewById(R.id.account_name_title)).setTypeface(tf);
            ((TextView) rootView.findViewById(R.id.account_address_title)).setTypeface(tf);
            ((TextView) rootView.findViewById(R.id.amount_title)).setTypeface(tf);

        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), " CreateView Exception- " + e.getMessage());

        }

        return rootView;
    }


    /**
     * Obtain the wallet contacts from the cryptoWallet
     *
     * @return
     */
    private List<WalletContact> getWalletContactList() {
        List<WalletContact> contacts = new ArrayList<>();
        try {
            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listWalletContacts(walletPublicKey);
            for (CryptoWalletWalletContact wcr : walletContactRecords) {
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), wcr.getReceivedCryptoAddress().get(0).getAddress(),wcr.isConnection(),wcr.getProfilePicture()));
            }
        } catch (CantGetAllWalletContactsException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        }
        return contacts;
    }

    /**
     * Send action
     */
    private void sendCrypto() {
        CryptoAddress validAddress = validateAddress(editAddress.getText().toString());
        if (validAddress != null) {
            EditText amount = (EditText) rootView.findViewById(R.id.amount);

            if(!amount.getText().toString().equals("") && amount.getText()!=null) {
                try {
                    //TODO que pasa si no puedo crear el user?
                    //CryptoWalletWalletContact walletContactRecord = cryptoWallet.createWalletContact(validAddress, autocompleteContacts.getText().toString(), Actors.EXTRA_USER, ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, walletPublicKey);
                    //CryptoWalletWalletContact walletContactRecord = cryptoWallet.
                    // TODO harcoded deliveredbyactorid
                    String notes=null;
                    if(editNotes.getText().toString().length()!=0){
                        notes = editNotes.getText().toString();
                    }


                    cryptoWallet.send(
                            Long.parseLong(amount.getText().toString()),
                            validAddress,
                            notes,
                            walletPublicKey,
                            user_id,
                            Actors.INTRA_USER,
                            contact.actorPublicKey,
                            Actors.EXTRA_USER,referenceWallet
                    );

                    Toast.makeText(getActivity(), "Send OK", Toast.LENGTH_LONG).show();
                } catch (InsufficientFundsException e) {
                    Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_LONG).show();
                } catch (CantSendCryptoException e) {
                    errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "Error send satoshis - " + e.getMessage());
                }
            }
        } else {
            Toast.makeText(getActivity(), "Invalid Address", Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Paste clipboard into editText
     *
     * @param rootView
     */
    private void pasteFromClipboard(View rootView) {
        ClipboardManager clipboard = (ClipboardManager) rootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Gets the ID of the "paste" menu item
        ImageView mPasteItem = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            mPasteItem.setEnabled(true);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            EditText editText = (EditText) rootView.findViewById(R.id.address);
            CryptoAddress validAddress = validateAddress(item.getText().toString());
            if (validAddress != null) {
                editText.setText(validAddress.getAddress());
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cannot find an address in the clipboard text.\n\n" + item.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // This enables the paste menu item, since the clipboard contains plain text.
            mPasteItem.setEnabled(false);
        }
    }

    /**
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (data != null) {
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

                //get references to edit text control to show scand result
                EditText textResult = IntentIntegrator.getTextResult();
                if (scanResult != null) {

                    // handle scan result
                    String contantsString = scanResult.getContents() == null ? "0" : scanResult.getContents();
                    if (contantsString.equalsIgnoreCase("0")) {
                        Toast.makeText(this.getActivity(), "Problem to get contact address", Toast.LENGTH_LONG).show();

                    } else {
                        //load into text address
                        textResult.setText(contantsString);
                    }
                } else {
                    Toast.makeText(this.getActivity(), "Problem to scan the barcode.", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), " Load address Exception- " + e.getMessage());
        }
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
     * Set wallet session
     *
     * @param walletSession
     */
    public void setWalletSession(ReferenceWalletSession walletSession) {
        this.walletSession = walletSession;
    }

    public void setWalletResourcesProviderManager(WalletResourcesProviderManager walletResourcesProviderManager) {
        this.walletResourcesProviderManager = walletResourcesProviderManager;
    }
}
