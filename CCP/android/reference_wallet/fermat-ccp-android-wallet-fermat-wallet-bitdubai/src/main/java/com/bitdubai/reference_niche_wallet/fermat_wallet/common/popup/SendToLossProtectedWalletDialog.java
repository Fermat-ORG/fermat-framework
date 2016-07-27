package com.bitdubai.reference_niche_wallet.fermat_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_wallet_fermat.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatButton;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.CantGetSettingsException;
import com.bitdubai.fermat_api.layer.all_definition.settings.exceptions.SettingsNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_ccp_api.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletWalletContact;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.InstalledWallet;
import com.bitdubai.reference_niche_wallet.fermat_wallet.fragments.FermatWalletSettings;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gian Barboza on 04/05/16.
 */
public class SendToLossProtectedWalletDialog extends Dialog implements View.OnClickListener {

    public Activity activity;
    public Dialog d;


    private SettingsManager<FermatWalletSettings> settingsManager;
    private BlockchainNetworkType blockchainNetworkType;

    /**
     * wallet selected
     */
    private FermatWalletWalletContact walletContact;
    /**
     *  Deals with crypto wallet interface
     */
    private FermatWallet fermatWallet;
    private ReferenceAppFermatSession<FermatWallet> appSession;
    /**
     * Deals with error manager interface
     */

    private ErrorManager errorManager;

    /**
     *  UI components
     */
    private ImageView spinnerArrow;
    private  TextView txt_notes;
    private EditText editTextAmount;
    private FermatButton send_button;
    private FermatButton cancel_button;
    private FermatTextView txt_type;
    private Spinner spinner;
    private BitcoinConverter bitcoinConverter;
    private com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.FermatWalletSettings bitcoinWalletSettings = null;

    private Typeface tf;
    /**
     *
     * @param activity

     * @param blockchainNetworkType
     */

    public SendToLossProtectedWalletDialog(Activity activity,
                                           FermatWallet fermatWallet,
                                           ReferenceAppFermatSession<FermatWallet> appSession,
                                           BlockchainNetworkType blockchainNetworkType) {

        super(activity);
        this.activity = activity;
        this.fermatWallet = fermatWallet;
        this.appSession = appSession;
        this.blockchainNetworkType = blockchainNetworkType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();
        bitcoinConverter = new BitcoinConverter();


        try {
            bitcoinWalletSettings = fermatWallet.loadAndGetSettings(appSession.getAppPublicKey());
            if (bitcoinWalletSettings.getBlockchainNetworkType() == null)
                bitcoinWalletSettings.setBlockchainNetworkType(BlockchainNetworkType.getDefaultBlockchainNetworkType());
            else
                blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
        } catch (CantGetSettingsException e) {
            blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
        } catch (SettingsNotFoundException e) {
            blockchainNetworkType = bitcoinWalletSettings.getBlockchainNetworkType();
        }



    }

    private void setUpScreenComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_to_loss_protected_wallet);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        spinnerArrow = (ImageView) findViewById(R.id.spinner_open);
        txt_notes = (TextView) findViewById(R.id.notes);
        editTextAmount = (EditText) findViewById(R.id.amount);
        send_button = (FermatButton) findViewById(R.id.send_button);
        cancel_button = (FermatButton) findViewById(R.id.cancel_button);
        txt_type = (FermatTextView) findViewById(R.id.txt_type);
        spinner = (Spinner) findViewById(R.id.spinner);

        send_button.setOnClickListener(this);
        cancel_button.setOnClickListener(this);

        setUpUI();

    }

    private void setUpUI() {

        List<String> list = new ArrayList<String>();
        list.add("BTC");
        list.add("Bits");
        list.add("Satoshis");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity,
                R.layout.send_to_loss_protected_list_item_spinner, list);
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
                        if (!amount.equals("") && amount != null) {
                            if (txtType.equals("[bits]")) {
                                newAmount = bitcoinConverter.getBitcoinsFromBits(amount);
                            } else if (txtType.equals("[satoshis]")) {
                                newAmount = bitcoinConverter.getBTC(amount);
                            } else {
                                newAmount = amount;
                            }
                        } else {
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
                        } else {
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

    @Override
    public void onClick(View v) {
        int i = v.getId();

        if (i == R.id.cancel_button){
            dismiss();
        }else if (i == R.id.send_button){
            sendCrypto();
        }

    }

    private void sendCrypto() {

      //  Toast.makeText(activity, "Sending btc to Loss Protected Wallet", Toast.LENGTH_SHORT).show();

        try {

            EditText txtAmount = editTextAmount;
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
                    List<InstalledWallet> list= fermatWallet.getInstalledWallets();
                    InstalledWallet wallet = null;
                    for (int i = 0; i < list.size() ; i++) {
                        if (list.get(i).getWalletName().equals("Loss Protected Wallet")){
                            wallet = list.get(i);
                        }
                    }
                    if (wallet != null){
                        Actors actor = Actors.LOSS_PROTECTED_USER;

                        switch (wallet.getWalletName()){

                            case "Bitcoin Wallet":
                                actor = Actors.BITCOIN_BASIC_USER;
                                break;
                            case "Loss Protected Wallet":
                                actor = Actors.LOSS_PROTECTED_USER;
                                break;

                            default:
                                actor = Actors.DEVICE_USER;
                        }

                        if (operator.compareTo(minSatoshis) == 1) {
                            fermatWallet.sendToWallet(
                                    operator.longValueExact(),
                                    appSession.getAppPublicKey(),
                                    wallet.getWalletPublicKey(),//RECEIVE WALLET KEY
                                    notes,
                                    Actors.BITCOIN_BASIC_USER,
                                    actor,
                                    ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                                    ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                                    blockchainNetworkType
                            );
                            Toast.makeText(activity, "Sending btc to Loss Protected Wallet...", Toast.LENGTH_SHORT).show();
                            dismiss();
                        } else {
                            Toast.makeText(activity, "Invalid Amount, must be greater than " + msg, Toast.LENGTH_LONG).show();
                            dismiss();
                        }

                    }else{
                        Toast.makeText(activity, "Wallet Public key not found " , Toast.LENGTH_LONG).show();
                        dismiss();
                    }


                } catch (LossProtectedInsufficientFundsException e) {
                    Toast.makeText(activity, "Insufficient funds", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    dismiss();
                } catch (CantSendLossProtectedCryptoException e) {
                    appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                    Toast.makeText(activity, "Error Send not Complete", Toast.LENGTH_LONG).show();
                    dismiss();
                } catch (Exception e) {
                    appSession.getErrorManager().reportUnexpectedUIException(UISource.VIEW, UnexpectedUIExceptionSeverity.UNSTABLE, e);
                    Toast.makeText(activity, "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            } else {
                Toast.makeText(activity, "Invalid Amount", Toast.LENGTH_LONG).show();
                dismiss();
            }

        } catch (Exception e) {
            Toast.makeText(activity, "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            dismiss();
        }

    }

    /**
     * Bitmap to byte[]
     *
     * @param bitmap Bitmap
     * @return byte array
     */
    private byte[] toByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private byte[] convertImage(int resImage){
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), resImage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,stream);
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
