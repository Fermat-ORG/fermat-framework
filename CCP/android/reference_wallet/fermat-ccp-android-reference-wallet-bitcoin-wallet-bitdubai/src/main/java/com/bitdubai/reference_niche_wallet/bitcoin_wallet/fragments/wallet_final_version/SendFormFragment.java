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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.FermatWalletFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetActiveLoginIdentityException;
import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserModuleManager;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetCryptoWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ConnectionWithCommunityDialog;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragment_factory.ReferenceFragmentsEnumType;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.session.ReferenceWalletSession;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 */
public class SendFormFragment extends FermatWalletFragment implements View.OnClickListener{


    /**
     * Plaform reference
     */
    private ReferenceWalletSession referenceWalletSession;
    private CryptoWallet cryptoWallet;
    private IntraUserModuleManager intraUserModuleManager;

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
     * Adapters
     */
    private WalletContactListAdapter contactsAdapter;

    /**
     * User selected
     */
    private CryptoWalletWalletContact cryptoWalletWalletContact;

    private WalletContact walletContact;
    private boolean connectionDialogIsShow;
    private boolean onFocus;


    public static SendFormFragment newInstance() {
        return new SendFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        referenceWalletSession = (ReferenceWalletSession) walletSession;
        intraUserModuleManager = referenceWalletSession.getIntraUserModuleManager();
        try {
            cryptoWallet = referenceWalletSession.getModuleManager().getCryptoWallet();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
            rootView = inflater.inflate(R.layout.send_form_base, container, false);
            setUpUI();
            setUpActions();
            setUpUIData();
            setUpContactAddapter();
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

    private void setUpActions(){

        /**
         * Listeners
         */
        imageView_contact.setOnClickListener(this);
        send_button.setOnClickListener(this);
        rootView.findViewById(R.id.scan_qr).setOnClickListener(this);

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
                        ConnectionWithCommunityDialog connectionWithCommunityDialog = new ConnectionWithCommunityDialog(getActivity() ,referenceWalletSession, referenceWalletSession.getResourceProviderManager());
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

        /**
         * Selector
         */
        //send_button.selector(R.drawable.bg_home_accept_normal,R.drawable.bg_home_accept_active, R.drawable.bg_home_accept_normal );
    }

    private void setUpUIData(){
        cryptoWalletWalletContact = referenceWalletSession.getLastContactSelected();
        if(cryptoWalletWalletContact!=null) {
            try {
                imageView_contact.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), cryptoWalletWalletContact.getProfilePicture()));
            } catch (Exception e) {
                imageView_contact.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(), R.drawable.profile_image));
            }
            contactName.setText(cryptoWalletWalletContact.getActorName());
        }else{
            imageView_contact.setImageDrawable(ImagesUtils.getRoundedBitmap(getResources(),R.drawable.profile_image));
        }

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
                        referenceWalletSession.getModuleManager().getCryptoWallet().convertConnectionToContact(
                                walletContact.name,
                                Actors.INTRA_USER,
                                walletContact.actorPublicKey,
                                walletContact.profileImage,
                                Actors.INTRA_USER,
                                referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey(),
                                referenceWalletSession.getAppPublicKey() ,
                                CryptoCurrency.BITCOIN,
                                BlockchainNetworkType.TEST);

                }
                catch (CantGetActiveLoginIdentityException e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "CantGetActiveLoginIdentityException- " + e.getMessage());
                }
                catch(CantCreateWalletContactException e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "CantCreateWalletContactException- " + e.getMessage());

                }
                catch(ContactNameAlreadyExistsException e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "ContactNameAlreadyExistsException- " + e.getMessage());

                } catch (CantGetCryptoWalletException e) {
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
            if (cryptoWalletWalletContact != null)
                sendCrypto();
            else
                Toast.makeText(getActivity(), "Contact not found, please add it.", Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.imageView_contact){
            // if user press the profile image
        }
        else if (id == R.id.btn_expand_send_form){
            Object[] objects = new Object[1];
            objects[0]= walletContact;
            changeApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY,objects);
        }


    }




    //TODO: VER QUE PASA  SI EL CONTACTO NO TIENE UNA WALLET ADDRESS
    private void sendCrypto() {
        CryptoAddress validAddress = WalletUtils.validateAddress(cryptoWalletWalletContact.getReceivedCryptoAddress().get(0).getAddress(), cryptoWallet);
        if (validAddress != null) {
            EditText amount = (EditText) rootView.findViewById(R.id.amount);

            if(!amount.getText().toString().equals("") && amount.getText()!=null) {
                try {
                    String notes=null;
                    if(txt_notes.getText().toString().length()!=0){
                        notes = txt_notes.getText().toString();
                    }
                    cryptoWallet.send(
                            Long.parseLong(amount.getText().toString()),
                            validAddress,
                            notes,
                            referenceWalletSession.getAppPublicKey(),
                            intraUserModuleManager.getActiveIntraUserIdentity().getPublicKey(),
                            Actors.INTRA_USER,
                            cryptoWalletWalletContact.getActorPublicKey(),
                            cryptoWalletWalletContact.getActorType(),
                            ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET
                    );
                    Toast.makeText(getActivity(), "Send OK", Toast.LENGTH_LONG).show();
                } catch (InsufficientFundsException e) {
                    Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_LONG).show();
                } catch (CantSendCryptoException e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "Error send satoshis - " + e.getMessage());
                } catch (Exception e) {
                    referenceWalletSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW,UnexpectedUIExceptionSeverity.UNSTABLE,e);
                    Toast.makeText(getActivity(),"oooopps",Toast.LENGTH_SHORT).show();
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
        try
        {
            List<CryptoWalletWalletContact> walletContactRecords = referenceWalletSession.getModuleManager().getCryptoWallet().listAllActorContactsAndConnections(referenceWalletSession.getAppPublicKey(), referenceWalletSession.getIntraUserModuleManager().getActiveIntraUserIdentity().getPublicKey());
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
        } catch (CantGetCryptoWalletException e) {
            e.printStackTrace();
        }
        return contacts;
    }

}
