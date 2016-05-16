package com.bitdubai.reference_niche_wallet.loss_protected_wallet.fragments.wallet_final_version;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.bitdubai.fermat_api.AndroidCoreManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.NetworkStatus;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantGetCommunicationNetworkStatusException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantPersistSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.pip_engine.interfaces.ResourceProviderManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.interfaces.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_ccp_api.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.LossProtectedWalletSettings;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.exceptions.CantListWalletsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.bar_code_scanner.IntentIntegrator;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.contacts_list_adapter.WalletContact;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.contacts_list_adapter.WalletContactListAdapter;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup.ErrorConnectingFermatNetworkDialog;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;
import static com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils.showMessage;

/**
 * Created by Joaquin Carrasquero on 16/03/16.
 */
public class SendFormWalletFragment extends AbstractFermatFragment<LossProtectedWalletSession, ResourceProviderManager> implements View.OnClickListener {

    private AndroidCoreManager androidCoreManager;
    private NetworkStatus networkStatus;
    /**
     * Plaform reference
     */
    private LossProtectedWallet cryptoWallet;
    /**
     * UI
     */
    private View rootView;
    private EditText editTextAmount;
    private FermatButton send_button;
    private TextView txt_notes;
    private BitcoinConverter bitcoinConverter;
    private ImageView imageview_wallet;


    /**
     * Adapters
     */
    private WalletContactListAdapter contactsAdapter;



    private WalletContact walletContact;
    private boolean onFocus;
    private Spinner spinner;
    private Spinner spinner_name;
    private FermatTextView txt_type;
    private ImageView spinnerArrow;

    SettingsManager<LossProtectedWalletSettings> settingsManager;
    BlockchainNetworkType blockchainNetworkType;
    String walletName = "";
    InstalledWallet walletSelected = null;

    public static SendFormWalletFragment newInstance() {
        return new SendFormWalletFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bitcoinConverter = new BitcoinConverter();
        setHasOptionsMenu(true);
        try {
            settingsManager = appSession.getModuleManager().getSettingsManager();
            LossProtectedWalletSettings bitcoinWalletSettings = null;
            bitcoinWalletSettings = settingsManager.loadAndGetSettings(appSession.getAppPublicKey());

            if (bitcoinWalletSettings != null) {

                if (bitcoinWalletSettings.getBlockchainNetworkType() == null) {
                    bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
                }
                settingsManager.persistSettings(appSession.getAppPublicKey(), bitcoinWalletSettings);

            }

            blockchainNetworkType = settingsManager.loadAndGetSettings(appSession.getAppPublicKey()).getBlockchainNetworkType();
            System.out.println("Network Type" + blockchainNetworkType);
            cryptoWallet = appSession.getModuleManager().getCryptoWallet();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        } catch (CantGetSettingsException e) {
            e.printStackTrace();
        } catch (SettingsNotFoundException e) {
            e.printStackTrace();
        } catch (CantPersistSettingsException e) {
            e.printStackTrace();
        } catch (CantGetCryptoLossProtectedWalletException e) {
            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
            showMessage(getActivity(), "CantGetCryptoWalletException- " + e.getMessage());

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        try {
            rootView = inflater.inflate(R.layout.send_wallet_form_base, container, false);
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
                        setUpActions();
                        setUpUIData();
                        break;
                }
            } else {
                setUpUI();
                setUpActions();
                setUpUIData();
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
                // changeActivity(Activities.CCP_BITCOIN_WALLET_SETTINGS_ACTIVITY, appSession.getAppPublicKey());
            }
        });
        errorConnectingFermatNetworkDialog.show();
    }

    private void setUpUI() {
        spinnerArrow = (ImageView) rootView.findViewById(R.id.spinner_open);
        txt_notes = (TextView) rootView.findViewById(R.id.notes);
        editTextAmount = (EditText) rootView.findViewById(R.id.amount);
        send_button = (FermatButton) rootView.findViewById(R.id.send_button);
        txt_type = (FermatTextView) rootView.findViewById(R.id.txt_type);
        spinner_name = (Spinner) rootView.findViewById(R.id.spinner_name);
        imageview_wallet = (ImageView) rootView.findViewById(R.id.wallet_image);
        try {
            final List<InstalledWallet> list= cryptoWallet.getInstalledWallets();
            List<String> walletList = new ArrayList<String>();
            for (int i = 0; i < list.size() ; i++) {
               if (list.get(i).getWalletName().equals("Bitcoin Wallet")){
                   walletList.add(list.get(i).getWalletName());
               }
            }
            ArrayAdapter<String> walletDataAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.list_item_spinner, walletList);
            walletDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_name.setAdapter(walletDataAdapter);
            spinner_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    walletName = spinner_name.getSelectedItem().toString();
                    walletSelected = list.get(position);
                    setUpUIData();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (CantListWalletsException e) {
            e.printStackTrace();
            List<String> walletList = new ArrayList<String>();
            walletList.add("Wallet1");
            walletList.add("Wallet2");
            walletList.add("Wallet3");
            ArrayAdapter<String> walletDataAdapter = new ArrayAdapter<>(getActivity(),
                    R.layout.list_item_spinner, walletList);
            walletDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_name.setAdapter(walletDataAdapter);
            spinner_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    walletName = spinner_name.getSelectedItem().toString();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


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
                if (amount.equals("")){
                    amount = "0";
                }
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
        imageview_wallet.setOnClickListener(this);
        send_button.setOnClickListener(this);
        rootView.findViewById(R.id.scan_qr).setOnClickListener(this);


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

        if (walletSelected != null) {
            try {

                        switch (walletSelected.getWalletName()){

                            case "Bitcoin Wallet":
                                Picasso.with(getActivity()).load(R.drawable.bitcoin_wallet_2).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case "Loss Protected Wallet":
                                Picasso.with(getActivity()).load(R.drawable.loss_protected).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case "Crypto Broker Wallet":
                                Picasso.with(getActivity()).load(R.drawable.crypto_broker).transform(new CircleTransform()).into(imageview_wallet);
                            break;
                            case "Crypto Customer Wallet":
                                Picasso.with(getActivity()).load(R.drawable.crypto_customer).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case "Asset Issuer":
                                Picasso.with(getActivity()).load(R.drawable.asset_issuer).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case  "Asset User":
                                Picasso.with(getActivity()).load(R.drawable.asset_user_wallet).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case "Redeem Point":
                                Picasso.with(getActivity()).load(R.drawable.redeem_point).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case "Banking wallet":
                                Picasso.with(getActivity()).load(R.drawable.bank_wallet_xxhdpi).transform(new CircleTransform()).into(imageview_wallet);
                                break;
                            case "Cash wallet":
                                Picasso.with(getActivity()).load(R.drawable.cash_wallet_xxhdpi).transform(new CircleTransform()).into(imageview_wallet);
                                break;

                            default:
                                Picasso.with(getActivity()).load(R.drawable.bitcoin_wallet_2).transform(new CircleTransform()).into(imageview_wallet);
                                break;

                        }

            } catch (Exception e) {
                Picasso.with(getActivity()).load(R.drawable.bitcoin_wallet_2).transform(new CircleTransform()).into(imageview_wallet);
            }


        } else {
            Picasso.with(getActivity()).load(R.drawable.bitcoin_wallet_2).transform(new CircleTransform()).into(imageview_wallet);
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
                sendCrypto();
        } else if (id == R.id.btn_expand_send_form) {
            Object[] objects = new Object[1];
            objects[0] = walletContact;
            changeApp(appSession.getCommunityConnection(), objects);
        }


    }

    private void sendCrypto() {
        try {

                    EditText txtAmount = (EditText) rootView.findViewById(R.id.amount);
                    String amount = txtAmount.getText().toString();

                    BigDecimal money;

                    if (amount.equals(""))
                        money = new BigDecimal("0");
                    else
                        money = new BigDecimal(amount);

                    if (!amount.equals("") && !money.equals(new BigDecimal("0"))) {
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
                                msg = bitcoinConverter.getBTC(String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND)) + " BTC.";
                            } else if (txtType.equals("[satoshis]")) {
                                newAmount = amount;
                                msg = String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND) + " SATOSHIS.";
                            } else if (txtType.equals("[bits]")) {
                                newAmount = bitcoinConverter.getSathoshisFromBits(amount);
                                msg = bitcoinConverter.getBits(String.valueOf(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND)) + " BITS.";
                            }

                            BigDecimal minSatoshis = new BigDecimal(BitcoinNetworkConfiguration.MIN_ALLOWED_SATOSHIS_ON_SEND);
                            BigDecimal operator = new BigDecimal(newAmount);
                            List<InstalledWallet> list= cryptoWallet.getInstalledWallets();
                            InstalledWallet wallet = null;
                            for (int i = 0; i < list.size() ; i++) {
                                if (walletName.equals(list.get(i).getWalletName())){
                                    wallet = list.get(i);
                                }
                            }
                            if (wallet != null){

                                if (operator.compareTo(minSatoshis) == 1) {
                                    cryptoWallet.sendToWallet(
                                            operator.longValueExact(),
                                            appSession.getAppPublicKey(),
                                            wallet.getWalletPublicKey(),//RECEIVE WALLET KEY
                                            notes,
                                            Actors.DEVICE_USER,
                                            ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                                            ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                                            blockchainNetworkType

                                           );
                                    Toast.makeText(getActivity(), "Sending...", Toast.LENGTH_SHORT).show();
                                    onBack(null);
                                } else {
                                    Toast.makeText(getActivity(), "Invalid Amount, must be greater than " + msg, Toast.LENGTH_LONG).show();
                                }

                            }else{
                                Toast.makeText(getActivity(), "Wallet Public key not found " , Toast.LENGTH_LONG).show();
                            }


                        } catch (LossProtectedInsufficientFundsException e) {
                            Toast.makeText(getActivity(), "Insufficient funds", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        } catch (CantSendLossProtectedCryptoException e) {
                            appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                            Toast.makeText(getActivity(), "Error Send not Complete", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                            Toast.makeText(getActivity(), "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Invalid Amount", Toast.LENGTH_LONG).show();
                    }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

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
