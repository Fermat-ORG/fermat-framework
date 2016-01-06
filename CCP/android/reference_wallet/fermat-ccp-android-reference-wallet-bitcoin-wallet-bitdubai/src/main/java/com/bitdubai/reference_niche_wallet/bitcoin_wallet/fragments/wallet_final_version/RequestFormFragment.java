package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.Compatibility;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ConnectionWithCommunityDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 */

public class RequestFormFragment extends AbstractFermatFragment implements View.OnClickListener{

    /**
     * Plaform reference
     */
    private ReferenceWalletSession referenceWalletSession;
    private CryptoWallet cryptoWallet;

    /**
     * UI
     */
    private View rootView;
    private AutoCompleteTextView contactName;
    private EditText editTextAmount;
    private ImageView imageView_contact;
    private FermatButton send_button;
    private TextView txt_notes;

    /**
     * User selected
     */
    private CryptoWalletWalletContact cryptoWalletWalletContact;
    private boolean isFragmentFromDetail;
    private boolean connectionDialogIsShow;
    private boolean onFocus;
    private WalletContactListAdapter contactsAdapter;
    private WalletContact walletContact;


    public static RequestFormFragment newInstance() {
        return new RequestFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = (ReferenceWalletSession) appSession;
        setChangeBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (CantGetCryptoWalletException e) {
            referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.request_form_base, container, false);
            setUpUI();
            setUpActions();
            setUpUIData();
            return rootView;
        }catch (Exception e){
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return null;
    }

    private void setUpUI(){
        contactName = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);
        txt_notes = (TextView) rootView.findViewById(R.id.notes);
        editTextAmount = (EditText) rootView.findViewById(R.id.amount);
        imageView_contact = (ImageView) rootView.findViewById(R.id.profile_Image);
        send_button = (FermatButton) rootView.findViewById(R.id.send_button);
    }

    private void setUpContactAddapter(){
        contactsAdapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());

        contactName.setAdapter(contactsAdapter);
        //autocompleteContacts.setTypeface(tf);
        contactName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                walletContact = (WalletContact) arg0.getItemAtPosition(position);

                //add connection like a wallet contact
                try {
                    if(walletContact.isConnection)
                        cryptoWalletWalletContact = referenceWalletSession.getModuleManager().getCryptoWallet().convertConnectionToContact(
                                walletContact.name,
                                Actors.INTRA_USER,
                                walletContact.actorPublicKey,
                                walletContact.profileImage,
                                Actors.INTRA_USER,
                                referenceWalletSession.getIntraUserModuleManager().getPublicKey(),
                                appSession.getAppPublicKey() ,
                                CryptoCurrency.BITCOIN,
                                BlockchainNetworkType.TEST);
                    setUpUIData();

                } catch(CantCreateWalletContactException e) {
                    appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "CantCreateWalletContactException- " + e.getMessage());

                }
                catch(ContactNameAlreadyExistsException e) {
                    appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "ContactNameAlreadyExistsException- " + e.getMessage());

                } catch (CantGetCryptoWalletException e) {
                    e.printStackTrace();
                } catch (CantListCryptoWalletIntraUserIdentityException e) {
                    e.printStackTrace();
                }
            }
        });


        contactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                    linear_address.setVisibility(activeAddress ? View.VISIBLE : View.GONE);
//                    // if (!editTextAddress.getText().equals("")) linear_address.setVisibility(View.VISIBLE);
            }
        });
    }
    private void setUpActions(){
        /**
         * Listeners
         */
        imageView_contact.setOnClickListener(this);
        send_button.setOnClickListener(this);
        rootView.findViewById(R.id.scan_qr).setOnClickListener(this);

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

        contactName.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    // in.hideSoftInputFromWindow(autoEditText.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //Commented line is for hide keyboard. Just make above code as comment and test your requirement
                    //It will work for your need. I just putted that line for your understanding only
                    //You can use own requirement here also.

                    if (!connectionDialogIsShow) {
                        ConnectionWithCommunityDialog connectionWithCommunityDialog = new ConnectionWithCommunityDialog(getActivity(), referenceWalletSession, referenceWalletSession.getResourceProviderManager());
                        connectionWithCommunityDialog.show();
                        connectionWithCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                contactName.setText("");
                                connectionDialogIsShow = false;
                            }
                        });
                        connectionDialogIsShow = true;
                    }
                    return true;
                }
                return false;
            }
        });
        contactName.postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                InputMethodManager keyboard = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.showSoftInput(contactName, 0);
            }
        }, 50);
        contactName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                onFocus = hasFocus;
                if (!onFocus) {
                    if (walletContact == null) {
                        contactName.setText("");
                    }
                }
            }
        });

        /**
         * Selector
         */
        //send_button.selector(R.drawable.bg_home_accept_active, R.drawable.bg_home_accept_normal, R.drawable.bg_home_accept_active);
    }

    private void setUpUIData(){

        cryptoWalletWalletContact = referenceWalletSession.getLastContactSelected();
        if(cryptoWalletWalletContact!=null) {
            isFragmentFromDetail = true;
            try {
                if (cryptoWalletWalletContact.getProfilePicture() != null) {
                    imageView_contact.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), cryptoWalletWalletContact.getProfilePicture()));

                } else
                    Picasso.with(getActivity()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(imageView_contact);
            } catch (Exception e) {
                Picasso.with(getActivity()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(imageView_contact);
            }
            contactName.setText(cryptoWalletWalletContact.getActorName());
        }else{
            isFragmentFromDetail = false;
            setChangeBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
            Picasso.with(getActivity()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(imageView_contact);
        }
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.scan_qr) {
            IntentIntegrator integrator = new IntentIntegrator(getActivity(), (EditText) rootView.findViewById(R.id.address));
            integrator.initiateScan();
        }
        else if (id == R.id.send_button){
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }

            sendRequest();

        }
        else if (id == R.id.imageView_contact){
            // if user press the profile image
        }
    }

    private void sendRequest(){

        try {

            if (cryptoWalletWalletContact == null) {
                Toast.makeText(getActivity(), "Contact not found, please add it first.", Toast.LENGTH_LONG).show();
            } else if (cryptoWalletWalletContact.getCompatibility().equals(Compatibility.INCOMPATIBLE)) {
                Toast.makeText(getActivity(), "The user doesn't have a compatible wallet.", Toast.LENGTH_LONG).show();
            } else if (cryptoWalletWalletContact.getReceivedCryptoAddress().isEmpty()) {
                Toast.makeText(getActivity(), "We can't find an address for the contact yet.", Toast.LENGTH_LONG).show();
            } else {
                String identityPublicKey = referenceWalletSession.getIntraUserModuleManager().getPublicKey();

                CryptoAddress cryptoAddress = cryptoWallet.requestAddressToKnownUser(
                        identityPublicKey,
                        Actors.INTRA_USER,
                        cryptoWalletWalletContact.getActorPublicKey(),
                        cryptoWalletWalletContact.getActorType(),
                        Platforms.CRYPTO_CURRENCY_PLATFORM,
                        VaultType.CRYPTO_CURRENCY_VAULT,
                        CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                        appSession.getAppPublicKey(),
                        ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
                        );
                cryptoWallet.sendCryptoPaymentRequest(
                        cryptoWalletWalletContact.getWalletPublicKey(),
                        identityPublicKey,
                        Actors.INTRA_USER,
                        cryptoWalletWalletContact.getActorPublicKey(),
                        cryptoWalletWalletContact.getActorType(),
                        cryptoAddress,
                        txt_notes.getText().toString(),
                        Long.valueOf(editTextAmount.getText().toString()),
                        BlockchainNetworkType.DEFAULT,
                        ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET

                );
                Toast.makeText(getActivity(), "Request Sent", Toast.LENGTH_LONG).show();
                if(isFragmentFromDetail) onBack(null);
                else onBack(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST.getCode());
            }

        } catch (Exception e) {

            reportUnexpectedError(e);
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
            List<CryptoWalletWalletContact> walletContactRecords = referenceWalletSession.getModuleManager().getCryptoWallet().listAllActorContactsAndConnections(appSession.getAppPublicKey(), referenceWalletSession.getIntraUserModuleManager().getPublicKey());
            for (CryptoWalletWalletContact wcr : walletContactRecords) {

                String contactAddress = "";
                if(wcr.getReceivedCryptoAddress().size() > 0)
                    contactAddress = wcr.getReceivedCryptoAddress().get(0).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress,wcr.isConnection(),wcr.getProfilePicture()));
            }
        }
        catch (CantGetAllWalletContactsException e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        } catch (CantListCryptoWalletIntraUserIdentityException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    void reportUnexpectedError(final Exception e) {

        referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.TASK, UnexpectedUIExceptionSeverity.UNSTABLE, e);
    }
}
