package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.AbstractFermatFragment;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_android_api.ui.transformation.CircleTransform;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrencyVault;
import com.bitdubai.fermat_api.layer.all_definition.enums.Engine;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.enums.VaultType;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_ccp_api.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.ContactNameAlreadyExistsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantCreateLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantFindLossProtectedWalletContactException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetAllLossProtectedWalletContactsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantRequestLossProtectedAddressException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWalletContact;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.Confirm_send_dialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ConnectionWithCommunityDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ErrorConnectingFermatNetworkDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.BitmapWorkerTask;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Matias Furszyfer on 2015.11.05..
 */
public class SendFormFragment extends AbstractFermatFragment<LossProtectedWalletSession,ResourceProviderManager> implements View.OnClickListener{


    /**
     * Plaform reference
     */
    private LossProtectedWallet lossProtectedWalletManager;
    /**
     * UI
     */
    private View rootView;
    private AutoCompleteTextView contactName;
    private EditText editTextAmount;
    private ImageView imageView_contact;
    private FermatButton send_button;
    private TextView txt_notes;
    private BitcoinConverter bitcoinConverter;
    /**
     * Adapters
     */
    private WalletContactListAdapter contactsAdapter;

    /**
     * User selected
     */
    private LossProtectedWalletContact lossProtectedWalletContact;

    private WalletContact walletContact;
    private boolean connectionDialogIsShow;
    private boolean onFocus;
    private Spinner spinner;
    private FermatTextView txt_type;
    private ImageView spinnerArrow;

    private LossProtectedWalletSettings lossProtectedWalletSettings;
    private BlockchainNetworkType blockchainNetworkType;
    boolean lossProtectedEnabled;


    public static SendFormFragment newInstance() {
        return new SendFormFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitcoinConverter = new BitcoinConverter();
        setHasOptionsMenu(true);
        try {

            lossProtectedWalletManager = appSession.getModuleManager();


            lossProtectedWalletSettings = lossProtectedWalletManager.loadAndGetSettings(appSession.getAppPublicKey());

            if(lossProtectedWalletSettings != null) {

                blockchainNetworkType = lossProtectedWalletSettings.getBlockchainNetworkType();

                lossProtectedEnabled = lossProtectedWalletSettings.getLossProtectedEnabled();
            }



            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (CantGetSettingsException e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());

        } catch (SettingsNotFoundException e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.loss_send_form_base, container, false);
            NetworkStatus networkStatus = getFermatNetworkStatus();
            if (networkStatus!= null) {
                switch (networkStatus) {
                    case CONNECTED:
                        setUpUI();
                        contactName.setText("");
                        setUpActions();
                        setUpUIData();
                        setUpContactAddapter();
                        break;
                    case DISCONNECTED:
                        showErrorConnectionDialog();
                        setUpUI();
                        contactName.setText("");
                        setUpActions();
                        setUpUIData();
                        setUpContactAddapter();
                        break;
                }
            }else {
                setUpUI();
                contactName.setText("");
                setUpActions();
                setUpUIData();
                setUpContactAddapter();
            }

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
                        if (!amount.equals("") && amount != null){
                            if (txtType.equals("[bits]")) {
                                newAmount = bitcoinConverter.getBitcoinsFromBits(amount);
                            } else if (txtType.equals("[satoshis]")) {
                                newAmount = bitcoinConverter.getBTC(amount);
                            } else {
                                newAmount = amount;
                            }
                        }else{
                            newAmount = amount;
                        }


                        break;
                    case 1:
                        text = "[bits]";
                        if (!amount.equals("") && amount != null) {
                            if (txtType.equals("[btc]")) {
                                newAmount = bitcoinConverter.getBitsFromBTC(amount);
                            } else if (txtType.equals("[satoshis]")) {
                                newAmount = bitcoinConverter.getBits(amount);
                            } else {
                                newAmount = amount;
                            }
                        }else{
                            newAmount = amount;
                        }

                        break;
                    case 2:
                        text = "[satoshis]";
                        if (!amount.equals("") && amount != null) {
                            if (txtType.equals("[bits]")) {
                                newAmount = bitcoinConverter.getSathoshisFromBits(amount);
                            } else if (txtType.equals("[btc]")) {
                                newAmount = bitcoinConverter.getSathoshisFromBTC(amount);
                            } else {
                                newAmount = amount;
                            }
                        }else{
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

    private void setUpActions() {

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
         *  Amount observer
         */
        editTextAmount.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                try {

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

    private void setUpUIData() {
        if(lossProtectedWalletContact==null) {
            lossProtectedWalletContact = appSession.getLastContactSelected();
        }
        if (lossProtectedWalletContact != null) {
            try {
                BitmapWorkerTask bitmapWorkerTask = new BitmapWorkerTask(imageView_contact, getResources(), false);
                bitmapWorkerTask.execute(lossProtectedWalletContact.getProfilePicture());
            } catch (Exception e) {
                Picasso.with(getActivity()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(imageView_contact);
            }
            contactName.setText(lossProtectedWalletContact.getActorName());

        } else {
            Picasso.with(getActivity()).load(R.drawable.ic_profile_male).transform(new CircleTransform()).into(imageView_contact);
        }

    }

    private void setUpContactAddapter() {
        contactsAdapter = new WalletContactListAdapter(getActivity(), R.layout.wallets_bitcoin_fragment_contacts_list_item, getWalletContactList());

        contactName.setAdapter(contactsAdapter);
        //autocompleteContacts.setTypeface(tf);
        contactName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                walletContact = (WalletContact) arg0.getItemAtPosition(position);

                //add connection like a wallet contact
                try {
                    if (walletContact.isConnection) {
                        lossProtectedWalletContact = lossProtectedWalletManager.convertConnectionToContact(
                                walletContact.name,
                                Actors.INTRA_USER,
                                walletContact.actorPublicKey,
                                walletContact.profileImage,
                                Actors.INTRA_USER,
                                appSession.getIntraUserModuleManager().getPublicKey(),
                                appSession.getAppPublicKey(),
                                CryptoCurrency.BITCOIN,
                                blockchainNetworkType);
                    } else {
                        try {
                            lossProtectedWalletContact = lossProtectedWalletManager.findWalletContactById(walletContact.contactId, appSession.getIntraUserModuleManager().getPublicKey());
                        } catch (CantFindLossProtectedWalletContactException e) {
                            e.printStackTrace();
                        } catch (com.bitdubai.fermat_ccp_api.layer.middleware.wallet_contacts.exceptions.WalletContactNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    walletContact.name = lossProtectedWalletContact.getActorName();
                    walletContact.actorPublicKey = lossProtectedWalletContact.getActorPublicKey();
                    if (lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType) == null) {
                        lossProtectedWalletManager.requestAddressToKnownUser(
                                appSession.getIntraUserModuleManager().getPublicKey(),
                                Actors.INTRA_USER,
                                lossProtectedWalletContact.getActorPublicKey(),
                                lossProtectedWalletContact.getActorType(),
                                Platforms.CRYPTO_CURRENCY_PLATFORM,
                                VaultType.CRYPTO_CURRENCY_VAULT,
                                CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                appSession.getAppPublicKey(),
                                ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                                blockchainNetworkType
                        );

                        Toast.makeText(getActivity(), "Contact don't have an Address from red " + blockchainNetworkType.getCode() + "\nplease wait 2 minutes.", Toast.LENGTH_LONG).show();

                    } else {

                        walletContact.address = lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress();

                        if (lossProtectedWalletContact != null) {
                            walletContact.name = lossProtectedWalletContact.getActorName();
                            walletContact.actorPublicKey = lossProtectedWalletContact.getActorPublicKey();

                            lossProtectedWalletManager.requestAddressToKnownUser(
                                    appSession.getIntraUserModuleManager().getPublicKey(),
                                    Actors.INTRA_USER,
                                    lossProtectedWalletContact.getActorPublicKey(),
                                    lossProtectedWalletContact.getActorType(),
                                    Platforms.CRYPTO_CURRENCY_PLATFORM,
                                    VaultType.CRYPTO_CURRENCY_VAULT,
                                    CryptoCurrencyVault.BITCOIN_VAULT.getCode(),
                                    appSession.getAppPublicKey(),
                                    ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                                    blockchainNetworkType
                            );

                            walletContact.contactId = lossProtectedWalletContact.getContactId();
                            walletContact.profileImage = lossProtectedWalletContact.getProfilePicture();
                            walletContact.isConnection = lossProtectedWalletContact.isConnection();
                        }

                        setUpUIData();

                    }
                }

                catch(CantCreateLossProtectedWalletContactException e)
                {
                    appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "CantCreateWalletContactException- " + e.getMessage());

                }
                catch(ContactNameAlreadyExistsException e )
                {
                    appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    showMessage(getActivity(), "ContactNameAlreadyExistsException- " + e.getMessage());
                }
                catch(CantListCryptoWalletIntraUserIdentityException e)
                {
                    e.printStackTrace();

                }catch(CantRequestLossProtectedAddressException e)
                {
                    e.printStackTrace();
                }
                catch(CantGetCryptoLossProtectedWalletException e)
                {
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

            }
        });
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
            if (lossProtectedWalletContact != null) {
                sendCrypto();
            } else
                Toast.makeText(getActivity(), "Contact not found, please add it.", Toast.LENGTH_LONG).show();
        } else if (id == R.id.imageView_contact) {
            // if user press the profile image
        } else if (id == R.id.btn_expand_send_form) {
            Object[] objects = new Object[1];
            objects[0] = walletContact;
            changeApp(Engine.BITCOIN_WALLET_CALL_INTRA_USER_COMMUNITY, objects);
        }


    }
    //TODO: Check if the setting for protecting the user from spending btc is working
    private void sendCrypto() {
        try {
            //first check if have exchange rate info
            if(appSession.getActualExchangeRate() != 0){
                if (lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType) != null) {
                    CryptoAddress validAddress = WalletUtils.validateAddress(lossProtectedWalletContact.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress(), lossProtectedWalletManager);
                    if (validAddress != null) {
                        EditText txtAmount = (EditText) rootView.findViewById(R.id.amount);
                        String amount = txtAmount.getText().toString();

                        BigDecimal money;

                        if (amount.equals(""))
                            money = new BigDecimal("0");
                        else
                            money = new BigDecimal(amount);

                        if(!amount.equals("") && !money.equals(new BigDecimal("0"))) {
                            try {
                                String notes = null;
                                if (txt_notes.getText().toString().length() != 0) {
                                    notes = txt_notes.getText().toString();
                                }

                                String txtType = txt_type.getText().toString();
                                String newAmount = "";
                                String msg = "";

                                if (txtType.equals("[btc]")) {
                                    newAmount = bitcoinConverter.getSathoshisFromBTC(amount);
                                    msg       = bitcoinConverter.getBTC(String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND))+" BTC.";
                                } else if (txtType.equals("[satoshis]")) {
                                    newAmount = amount;
                                    msg       = String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND)+" SATOSHIS.";
                                } else if (txtType.equals("[bits]")) {
                                    newAmount = bitcoinConverter.getSathoshisFromBits(amount);
                                    msg       = bitcoinConverter.getBits(String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND))+" BITS.";
                                }

                                BigDecimal minSatoshis = new BigDecimal(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND);
                                BigDecimal amountDecimal = new BigDecimal(newAmount);

                                if (amountDecimal.compareTo(minSatoshis) == 1) {

                                    long availableBalance = lossProtectedWalletManager.getBalance(BalanceType.AVAILABLE, appSession.getAppPublicKey(), blockchainNetworkType, String.valueOf(appSession.getActualExchangeRate()));

                                    if(amountDecimal.compareTo(new BigDecimal(availableBalance)) == 1) //Balance value is greater than send amount
                                    {
                                        if (!lossProtectedEnabled) {
                                            Confirm_send_dialog confirm_send_dialog = new Confirm_send_dialog(getActivity(),lossProtectedWalletManager,amountDecimal.longValueExact(),
                                                    validAddress,
                                                    notes,
                                                    appSession.getAppPublicKey(),
                                                    lossProtectedWalletManager.getActiveIdentities().get(0).getPublicKey(),
                                                    Actors.INTRA_USER,
                                                    lossProtectedWalletContact.getActorPublicKey(),
                                                    lossProtectedWalletContact.getActorType(),
                                                    ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                                                    blockchainNetworkType,
                                                    appSession);
                                            confirm_send_dialog.show();
                                        }
                                        else
                                        {
                                            //setting protected eneabled can't send
                                            Toast.makeText(getActivity(), "Action not allowed, you will lose money. Restricted by LossProtected Configuration. ", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        try {

                                            lossProtectedWalletManager.send(
                                                    amountDecimal.longValueExact(),
                                                    validAddress,
                                                    notes,
                                                    appSession.getAppPublicKey(),
                                                    lossProtectedWalletManager.getActiveIdentities().get(0).getPublicKey(),
                                                    Actors.INTRA_USER,
                                                    lossProtectedWalletContact.getActorPublicKey(),
                                                    lossProtectedWalletContact.getActorType(),
                                                    ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                                                    blockchainNetworkType
                                            );
                                            Toast.makeText(getActivity(), "Sending...", Toast.LENGTH_SHORT).show();
                                            onBack(null);

                                        } catch (LossProtectedInsufficientFundsException e) {
                                            e.printStackTrace();
                                            Toast.makeText(getActivity(), "Action not allowed, Insufficient Funds. ", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                } else {
                                    Toast.makeText(getActivity(), "Invalid Amount, must be greater than " + msg, Toast.LENGTH_LONG).show();
                                }


                            } catch (Exception e) {
                                appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                                Toast.makeText(getActivity(), "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Invalid Amount", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Contact don't have an valid Address", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Contact don't have an Address from red "+ blockchainNetworkType.getCode() + "\nplease wait 2 minutes", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(getActivity(), "Action not allowed.Could not retrieve the dollar exchange rate.\nCheck your internet connection.. ", Toast.LENGTH_LONG).show();

            }


        } catch (Exception e) {
            Toast.makeText(getActivity(), "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
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
            List<LossProtectedWalletContact> walletContactRecords = lossProtectedWalletManager.listAllActorContactsAndConnections(appSession.getAppPublicKey(), appSession.getIntraUserModuleManager().getPublicKey());
            for (LossProtectedWalletContact wcr : walletContactRecords) {

                String contactAddress = "";
                if (wcr.getReceivedCryptoAddress().get(blockchainNetworkType) != null)
                    contactAddress = wcr.getReceivedCryptoAddress().get(blockchainNetworkType).getAddress();
                contacts.add(new WalletContact(wcr.getContactId(), wcr.getActorPublicKey(), wcr.getActorName(), contactAddress, wcr.isConnection(), wcr.getProfilePicture()));
            }

        } catch (CantListCryptoWalletIntraUserIdentityException e) {
            e.printStackTrace();
        } catch (CantGetAllLossProtectedWalletContactsException e) {
            e.printStackTrace();
        } catch (CantGetCryptoLossProtectedWalletException e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetAllWalletContactsException- " + e.getMessage());;
        }
        return contacts;
    }


    @Override
    public void onDestroy() {
        contactsAdapter = null;
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}
