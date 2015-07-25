package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_dmp_wallet_bitcoin.R;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.WalletSession;

import java.util.UUID;

import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.validateAddress;

/**
 * Created by natalia on 19/06/15.
 */
public class CreateContactFragment extends Fragment {



    private static final String ARG_POSITION = "position";

    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    /**
     * Wallet session
     */

    WalletSession walletSession;
    


    /**
     * Screen views
     */
    private View rootView;
    private TextView editAddress;

    /**
     * Members
     */
    String contactName;

    /**
     * Fragment style
     */
    Typeface tf ;


    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private CryptoWallet cryptoWallet;

    /**
     * deals with Error manager
     */
    private ErrorManager errorManager;

    public static CreateContactFragment newInstance(int position,WalletSession walletSession) {
        CreateContactFragment f = new CreateContactFragment();
        f.setWalletSession(walletSession);
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        errorManager = walletSession.getErrorManager();

        cryptoWalletManager = walletSession.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(),"Unexpected error get Contact list - " + e.getMessage());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.wallets_bitcoin_fragment_create_contact, container, false);
        try {


            // save_contact button definition
            Button saveContactButton = (Button) rootView.findViewById(R.id.save_contact_btn);
            saveContactButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    saveContact();
                }
            });
            saveContactButton.setTypeface(tf);

            // add_contact button definition
            ImageView pasteFromClipboardButton = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
            pasteFromClipboardButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pasteFromClipboard(rootView);
                }
            });

            TextView textView = (TextView) rootView.findViewById(R.id.contact_name);
            textView.setText(contactName);
            textView.setTypeface(tf);


            editAddress = (TextView) rootView.findViewById(R.id.contact_address);
            editAddress.setTypeface(tf);
            editAddress.addTextChangedListener(new TextWatcher() {
                public void afterTextChanged(Editable s) {
                    if (validateAddress(editAddress.getText().toString(),cryptoWallet) != null) {
                        editAddress.setTextColor(Color.parseColor("#72af9c"));
                    } else {
                        editAddress.setTextColor(Color.parseColor("#b46a54"));
                    }
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });

            ImageView scanImage = (ImageView) rootView.findViewById(R.id.scan_qr);

            scanImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.contact_address));
                    integrator.initiateScan();
                }
            });

        } catch (Exception e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(),"Contacts Fragment onCreateView Exception - " + e.getMessage());

        }
        return rootView;
    }

    /**
     * create contact and save it into database
     */
    private void saveContact() {
        EditText contact_name = (EditText) rootView.findViewById(R.id.contact_name);
        EditText address = (EditText) rootView.findViewById(R.id.contact_address);

        CryptoAddress validAddress = validateAddress(address.getText().toString(),cryptoWallet);

        if (validAddress != null) {
            try {
                // first i add the contact
                cryptoWallet.createWalletContact(validAddress, contact_name.getText().toString(), Actors.EXTRA_USER, ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET, wallet_id);

                Toast.makeText(getActivity().getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();

                returnToContacts();

            } catch (CantCreateWalletContactException e) {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage(getActivity(),"Error creating wallet contact - " + e.getMessage());
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
        }
    }



    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * back to contacts
     */
    private void returnToContacts() {
        ContactsFragment contactsFragment = new ContactsFragment();
        contactsFragment.setWalletSession(walletSession);

        FragmentTransaction FT = getFragmentManager().beginTransaction();

        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        FT.replace(R.id.fragment_container2,contactsFragment);
        FT.commit();
    }

    /**
     *  Paste valid clipboard text into a view
     * @param rootView
     */
    private void pasteFromClipboard(View rootView) {
        ClipboardManager clipboard = (ClipboardManager) rootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Gets the ID of the "paste" menu item
        ImageView mPasteItem = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            mPasteItem.setEnabled(true);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            EditText editText = (EditText) rootView.findViewById(R.id.contact_address);
            CryptoAddress validAddress = validateAddress(item.getText().toString(),cryptoWallet);
            if (validAddress != null) {
                editText.setText(validAddress.getAddress());
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cannot find an address in the clipboard text.\n\n"+item.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            // This enables the paste menu item, since the clipboard contains plain text.
            mPasteItem.setEnabled(false);
        }
    }


    /**
     * Set wallet session
     *
     * @param walletSession
     */
    public void setWalletSession(WalletSession walletSession) {
        this.walletSession = walletSession;
    }
}