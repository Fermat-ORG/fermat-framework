package com.bitdubai.reference_niche_wallet.bitcoin_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.utils.ImagesUtils;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.interfaces.FermatWorkerCallBack;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_android_api.ui.util.FermatWorker;
import com.bitdubai.fermat_api.AndroidCoreManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
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
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_ccp_api.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.BitcoinWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantCreateWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantFindWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantGetAllWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantRequestCryptoAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.WalletContactNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletWalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup.ErrorConnectingFermatNetworkDialog;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 */

public class RequestFormFragment extends AbstractFermatFragment<ReferenceAppFermatSession<CryptoWallet>, ResourceProviderManager> implements View.OnClickListener {

    private AndroidCoreManager androidCoreManager;
    /**
     * Plaform reference
     */
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
    private Spinner spinner;
    private FermatTextView txt_type;
    private BitcoinConverter bitcoinConverter;
    private ImageView spinnerArrow;
    BlockchainNetworkType blockchainNetworkType;
    private ExecutorService _executor;

    private FermatWorker fermatWorker;

    private List<WalletContact> walletContactList = new ArrayList<>();

    public static RequestFormFragment newInstance() {
        return new RequestFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChangeBackActivity(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST);
        try {

            _executor = Executors.newFixedThreadPool(2);
            bitcoinConverter = new BitcoinConverter();
            cryptoWallet = appSession.getModuleManager();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            BitcoinWalletSettings bitcoinWalletSettings = null;
            bitcoinWalletSettings = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey());

            if(bitcoinWalletSettings != null) {

                if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                    bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                appSession.getModuleManager().persistSettings(appSession.getAppPublicKey(), bitcoinWalletSettings);

            }

            blockchainNetworkType = appSession.getModuleManager().loadAndGetSettings(appSession.getAppPublicKey()).getBlockchainNetworkType();
            System.out.println("Network Type"+blockchainNetworkType);
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.request_form_base, container, false);
            NetworkStatus networkStatus = getFermatNetworkStatus();
            if (networkStatus != null) {
                switch (networkStatus) {
                    case CONNECTED:

                        setUpUI();
                        setUpActions();
                        setUpUIData();
                        break;
                    case DISCONNECTED:
                        showErrorConnectionDialog();
                        setUpUI();
                        contactName.setText("");
                        setUpActions();
                        setUpUIData();

                        break;
                }
            } else {

                setUpUI();
                setUpActions();
                setUpUIData();

            }

            _executor.execute(new Runnable() {
                @Override
                public void run() {
                    setUpContactAddapter();

                }
            });
            return rootView;
        } catch (Exception e) {
            makeText(getActivity(), "Oooops! recovering from system error", Toast.LENGTH_SHORT).show();
            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.CRASH, e);
        }
        return null;
    }

    private void showErrorConnectionDialog() {
        final ErrorConnectingFermatNetworkDialog errorConnectingFermatNetworkDialog = new ErrorConnectingFermatNetworkDialog(getActivity(), appSession, null);
        errorConnectingFermatNetworkDialog.setLeftButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnectingFermatNetworkDialog.dismiss();
                getActivity().onBackPressed();
            }
        });

        errorConnectingFermatNetworkDialog.setRightButton("CONNECT", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorConnectingFermatNetworkDialog.dismiss();
                try {
                    if (getFermatNetworkStatus() == NetworkStatus.DISCONNECTED) {
                        Toast.makeText(getActivity(), "Wait a minute please, trying to reconnect...", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                } catch (CantGetCommunicationNetworkStatusException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                changeActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY, appSession.getAppPublicKey());
            }
        });
        errorConnectingFermatNetworkDialog.show();
    }

    private void setUpUI() {
        contactName = (AutoCompleteTextView) rootView.findViewById(R.id.contact_name);
        spinnerArrow = (ImageView) rootView.findViewById(R.id.spinner_open);
        txt_notes = (TextView) rootView.findViewById(R.id.notes);
        editTextAmount = (EditText) rootView.findViewById(R.id.amount);
        imageView_contact = (ImageView) rootView.findViewById(R.id.profile_Image);
        send_button = (FermatButton) rootView.findViewById(R.id.send_button);
        txt_type = (FermatTextView) rootView.findViewById(R.id.txt_type);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("BTC");
        list.add("Bits");
        list.add("Satoshis");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(),
                R.layout.list_item_spinner, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = "";
                String txtType = txt_type.getText().toString();
                String amount = editTextAmount.getText().toString();
                String newAmount = "";


                    switch (position) {
                        case 0:
                            text = "[btc]";
                            if (txtType.equals("[bits]")) {
                                newAmount = bitcoinConverter.getBitcoinsFromBits(amount);
                            } else if (txtType.equals("[satoshis]")) {
                                newAmount = bitcoinConverter.getBTC(amount);
                            } else {
                                newAmount = amount;
                            }

                            break;
                        case 1:
                            text = "[bits]";
                            if (txtType.equals("[btc]")) {
                                newAmount = bitcoinConverter.getBitsFromBTC(amount);
                            } else if (txtType.equals("[satoshis]")) {
                                newAmount = bitcoinConverter.getBits(amount);
                            } else {
                                newAmount = amount;
                            }

                            break;
                        case 2:
                            text = "[satoshis]";
                            if (txtType.equals("[bits]")) {
                                newAmount = bitcoinConverter.getSathoshisFromBits(amount);
                            } else if (txtType.equals("[btc]")) {
                                newAmount = bitcoinConverter.getSathoshisFromBTC(amount);
                            } else {
                                newAmount = amount;
                            }
                            break;
                    }


                AlphaAnimation alphaAnimation = new AlphaAnimation((float) 0.4, 1);
                alphaAnimation.setDuration(300);
                final String finalText = text;

                if (newAmount.equals("0"))
                    newAmount = "";

                final String finalAmount = newAmount;
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        txt_type.setText(finalText);
                        editTextAmount.setText(finalAmount);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                txt_type.startAnimation(alphaAnimation);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinnerArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.performClick();
            }
        });
    }

    private void setUpContactAddapter() {

        fermatWorker = new FermatWorker(getActivity()) {
            @Override
            protected Object doInBackground()  {
                try{
                    walletContactList =   getWalletContactList();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                return walletContactList;
            }
        };

        fermatWorker.setCallBack(new FermatWorkerCallBack() {
            @Override
            public void onPostExecute(Object... result) {
                if (result != null && result.length > 0) {
                    contactsAdapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, (List<WalletContact>)result[0]);

                    contactName.setAdapter(contactsAdapter);
                    //autocompleteContacts.setTypeface(tf);
                    contactName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                            walletContact = (WalletContact) arg0.getItemAtPosition(position);

                            //add connection like a wallet contact
                            try {
                                if (walletContact.isConnection) {
                                    cryptoWalletWalletContact = cryptoWallet.convertConnectionToContact(
                                            walletContact.name,
                                            Actors.INTRA_USER,
                                            walletContact.actorPublicKey,
                                            walletContact.profileImage,
                                            Actors.INTRA_USER,
                                            cryptoWallet.getSelectedActorIdentity().getPublicKey(),
                                            appSession.getAppPublicKey(),
                                            CryptoCurrency.BITCOIN,
                                            blockchainNetworkType);

                                } else {
                                    try {
                                        cryptoWalletWalletContact = cryptoWallet.findWalletContactById(walletContact.contactId, appSession.getModuleManager().getActiveIdentities().get(0).getPublicKey());
                                    } catch (CantFindWalletContactException e) {
                                        e.printStackTrace();
                                    } catch (WalletContactNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (cryptoWalletWalletContact != null) {
                                    walletContact.name = cryptoWalletWalletContact.getActorName();
                                    walletContact.actorPublicKey = cryptoWalletWalletContact.getActorPublicKey();
                                    if (cryptoWalletWalletContact.getReceivedCryptoAddress().isEmpty()) {
                                        cryptoWallet.requestAddressToKnownUser(
                                                cryptoWallet.getSelectedActorIdentity().getPublicKey(),
                                                Actors.INTRA_USER,
                                                cryptoWalletWalletContact.getActorPublicKey(),
                                                cryptoWalletWalletContact.getActorType(),
                                                Platforms.CRYPTO_CURRENCY_PLATFORM,
                                                VaultType.CRYPTO_CURRENCY_VAULT,
                                                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                                appSession.getAppPublicKey(),
                                                ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                                                blockchainNetworkType
                                        );
                                    }
                                } else {
                                    if (cryptoWalletWalletContact != null)
                                        walletContact.address = cryptoWalletWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress();
                                }
                                if (cryptoWalletWalletContact != null) {
                                    walletContact.contactId = cryptoWalletWalletContact.getContactId();
                                    walletContact.profileImage = cryptoWalletWalletContact.getProfilePicture();
                                    walletContact.isConnection = cryptoWalletWalletContact.isConnection();
                                }
                                setUpUIData();

                            } catch (
                                    CantCreateWalletContactException e
                                    )

                            {
                                appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                                showMessage(getActivity(), "CantCreateWalletContactException- " + e.getMessage());


                            } catch (
                                    CantRequestCryptoAddressException e
                                    )

                            {
                                e.printStackTrace();
                            } catch (ContactNameAlreadyExistsException e) {
                                e.printStackTrace();
                            } catch (CantGetSelectedActorIdentityException e) {
                                e.printStackTrace();
                            } catch (ActorIdentityNotSelectedException e) {
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
            }

            @Override
            public void onErrorOccurred(Exception ex) {

                ErrorManager errorManager = appSession.getErrorManager();
                if (errorManager != null)
                    errorManager.reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI,
                            UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, ex);
                else
                    Log.e("getWalletContactList", ex.getMessage(), ex);

            }
        });

        fermatWorker.execute();



    }

    private void setUpActions() {
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
                    //long actualBalance = cryptoWallet.getBalance(BalanceType.AVAILABLE,appSession.getWalletSessionType().getWalletPublicKey());
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

                    /*if (!connectionDialogIsShow) {
                        ConnectionWithCommunityDialog connectionWithCommunityDialog = new ConnectionWithCommunityDialog(getActivity(), appSession, appSession.getResourceProviderManager());
                        connectionWithCommunityDialog.show();
                        connectionWithCommunityDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                contactName.setText("");
                                connectionDialogIsShow = false;
                            }
                        });
                        connectionDialogIsShow = true;
                    }*/
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

        setUpContactAddapter();
        /**
         * Selector
         */
        //send_button.selector(R.drawable.bg_home_accept_active, R.drawable.bg_home_accept_normal, R.drawable.bg_home_accept_active);
    }

    private void setUpUIData() {
        if(cryptoWalletWalletContact==null) {
            cryptoWalletWalletContact = (CryptoWalletWalletContact) appSession.getData("LastContactSelected");
        }
        if (cryptoWalletWalletContact != null) {
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
        } else {
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
        } else if (id == R.id.send_button) {
            InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (getActivity().getCurrentFocus() != null && im.isActive(getActivity().getCurrentFocus())) {
                im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }

            sendRequest();

        } else if (id == R.id.imageView_contact) {
            // if user press the profile image
        }
    }

    @Override
    public void onStop() {

        if(fermatWorker != null)
            fermatWorker.shutdownNow();

        super.onStop();
    }

    private void sendRequest() {

        try {

            if (cryptoWalletWalletContact == null) {
                Toast.makeText(getActivity(), "Contact not found, please add it first.", Toast.LENGTH_LONG).show();
            } else if (cryptoWalletWalletContact.getCompatibility().equals(Compatibility.INCOMPATIBLE)) {
                Toast.makeText(getActivity(), "The user doesn't have a compatible wallet.", Toast.LENGTH_LONG).show();
            } else if (cryptoWalletWalletContact.getReceivedCryptoAddress().isEmpty()) {
                Toast.makeText(getActivity(), "We can't find an address for the contact yet.", Toast.LENGTH_LONG).show();
            } else {

                String amount = editTextAmount.getText().toString();

                BigDecimal money;

                if (amount.equals(""))
                    money = new BigDecimal("0");
                else
                    money = new BigDecimal(amount);

                if (!amount.equals("") && amount != null && !money.equals(0)) {

                    String txtType = txt_type.getText().toString();
                    String newAmount = "";

                    String notes = "";
                    if (txt_notes.getText().toString().length() != 0){
                      notes = txt_notes.getText().toString();
                    }
                    if (txtType.equals("[btc]")) {
                        newAmount = bitcoinConverter.getSathoshisFromBTC(amount);
                    } else if (txtType.equals("[satoshis]")) {
                        newAmount = amount;
                    } else if (txtType.equals("[bits]")) {
                        newAmount = bitcoinConverter.getSathoshisFromBits(amount);
                    }


                    BigDecimal minSatoshis = new BigDecimal(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND);
                    BigDecimal operator = new BigDecimal(newAmount);
                    if(operator.compareTo(minSatoshis) == 1 )
                    {

                           String identityPublicKey = cryptoWallet.getSelectedActorIdentity().getPublicKey();

                           CryptoAddress cryptoAddress = cryptoWallet.requestAddressToKnownUser(
                                   identityPublicKey,
                                   Actors.INTRA_USER,
                                   cryptoWalletWalletContact.getActorPublicKey(),
                                   cryptoWalletWalletContact.getActorType(),
                                   Platforms.CRYPTO_CURRENCY_PLATFORM,
                                   VaultType.CRYPTO_CURRENCY_VAULT,
                                   CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                   appSession.getAppPublicKey(),
                                   ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                                   blockchainNetworkType
                           );
                           cryptoWallet.sendCryptoPaymentRequest(
                                   cryptoWalletWalletContact.getWalletPublicKey(),
                                   identityPublicKey,
                                   Actors.INTRA_USER,
                                   cryptoWalletWalletContact.getActorPublicKey(),
                                   cryptoWalletWalletContact.getActorType(),
                                   cryptoAddress,
                                   notes,
                                   operator.longValueExact(),
                                   blockchainNetworkType,
                                   ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                                   CryptoCurrency.BITCOIN

                           );
                           Toast.makeText(getActivity(), "Request Sent", Toast.LENGTH_LONG).show();
                           if (isFragmentFromDetail) onBack(null);
                           else
                               onBack(Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST.getCode());
                    }else {
                       Toast.makeText(getActivity(), "Invalid Amount, must be greater than " + bitcoinConverter.getSathoshisFromMBTC(String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND)) + " BTC.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    showMessage(getActivity(), "Invalid Request Amount");
                }
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
        try {
            List<CryptoWalletWalletContact> walletContactRecords = cryptoWallet.listAllActorContactsAndConnections(appSession.getAppPublicKey(), cryptoWallet.getSelectedActorIdentity().getPublicKey());
            for (CryptoWalletWalletContact wcr : walletContactRecords) {

                String contactAddress = "";
                if (wcr.getReceivedCryptoAddress().get(blockchainNetworkType) != null)
                    contactAddress = wcr.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress, wcr.isConnection(), wcr.getProfilePicture()));
            }
        } catch (CantGetAllWalletContactsException e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());

        } catch (CantGetSelectedActorIdentityException e) {
            e.printStackTrace();
        } catch (ActorIdentityNotSelectedException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    void reportUnexpectedError(final Exception e) {

        appSession.getErrorManager().reportUnexpectedUIException(UISource.TASK, UnexpectedUIExceptionSeverity.UNSTABLE, e);
    }
}
