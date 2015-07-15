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
import com.bitdubai.fermat_api.layer.all_definition.enums.PlatformWalletType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.dmp_middleware.app_runtime.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_api.layer.dmp_niche_wallet_type.crypto_wallet.interfaces.CryptoWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.Platform;

import java.util.UUID;

/**
 * Created by natalia on 19/06/15.
 */
public class CreateContactFragment extends Fragment {

    private static final String ARG_POSITION = "position";
    View rootView;
    UUID wallet_id = UUID.fromString("25428311-deb3-4064-93b2-69093e859871");

    TextView editAddress;

    String contactName;

    Typeface tf ;


    /**
     * DealsWithNicheWalletTypeCryptoWallet Interface member variables.
     */
    private CryptoWalletManager cryptoWalletManager;
    private Platform platform;
    private CryptoWallet cryptoWallet;
    private ErrorManager errorManager;

    public static CreateContactFragment newInstance(int position) {
        CreateContactFragment f = new CreateContactFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    public static CreateContactFragment newInstance(final int position, final Platform platform) {
        CreateContactFragment f = new CreateContactFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tf=Typeface.createFromAsset(getActivity().getAssets(), "fonts/CaviarDreams.ttf");

        platform = new Platform();
        errorManager = platform.getErrorManager();

        cryptoWalletManager = platform.getCryptoWalletManager();

        try {
            cryptoWallet = cryptoWalletManager.getCryptoWallet();
        } catch (CantGetCryptoWalletException e) {
            errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage("Unexpected error get Contact list - " + e.getMessage());
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
            showMessage("Contacts Fragment onCreateView Exception - " + e.getMessage());

        }
        return rootView;
    }

    private void saveContact() {
        EditText contact_name = (EditText) rootView.findViewById(R.id.contact_name);
        EditText address = (EditText) rootView.findViewById(R.id.contact_address);

        CryptoAddress validAddress = validateAddress(address.getText().toString());

        if (validAddress != null) {
            try {
                // first i add the contact
                cryptoWallet.createWalletContact(validAddress, contact_name.getText().toString(), Actors.EXTRA_USER, PlatformWalletType.BASIC_WALLET_BITCOIN_WALLET, wallet_id);

                Toast.makeText(getActivity().getApplicationContext(), "Contact saved!", Toast.LENGTH_SHORT).show();

                returnToContacts();

            } catch (CantCreateWalletContactException e) {
                errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                showMessage("Error creating wallet contact - " + e.getMessage());
            }
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Please enter a valid address...", Toast.LENGTH_SHORT).show();
        }
    }

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
        return null;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    private void returnToContacts() {
        ContactsFragment contactsFragment = new ContactsFragment();

        FragmentTransaction FT = getFragmentManager().beginTransaction();

        FT.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //linearLayout.setVisibility(View.GONE);
        //listViewContacs.setVisibility(View.GONE);
        //textViewEmptyListView.setVisibility(View.GONE);
        //createContactFragment.detach(createContactFragment);
        //FT.attach(createContactFragment);
        FT.replace(R.id.fragment_container2,contactsFragment);
        FT.commit();
    }

    private void pasteFromClipboard(View rootView) {
        ClipboardManager clipboard = (ClipboardManager) rootView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);

        // Gets the ID of the "paste" menu item
        ImageView mPasteItem = (ImageView) rootView.findViewById(R.id.paste_from_clipboard_btn);
        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            mPasteItem.setEnabled(true);
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
            EditText editText = (EditText) rootView.findViewById(R.id.contact_address);
            CryptoAddress validAddress = validateAddress(item.getText().toString());
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

    //show alert
    private void showMessage(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this.getActivity()).create();
        alertDialog.setTitle("Warning");
        alertDialog.setMessage(text);
        alertDialog.setButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // aquí puedes añadir funciones
            }
        });
        //alertDialog.setIcon(R.drawable.icon);
        alertDialog.show();
    }
}