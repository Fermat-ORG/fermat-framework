package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedUIExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.error_manager.enums.UnexpectedWalletExceptionSeverity;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.enums.UISource;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.BitcoinNetworkConfiguration;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_ccp_api.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantSendCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.InsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;

import java.math.BigDecimal;

/**
 * Created by natalia on 14/07/16.
 */
public class SendConfirmDialog extends Dialog implements
        View.OnClickListener {


    public Activity activity;
    public Dialog d;

    private BlockchainNetworkType blockchainNetworkType;
    private long cryptoAmount;
    private long fee;
    private long total;
    private CryptoAddress destinationAddress;
    private String notes;
    private String deliveredByActorPublicKey;
    private Actors deliveredByActorType;
    private FeeOrigin feeOrigin;

    /**
     *  Deals with crypto wallet interface
     */

    private CryptoWallet moduleManager;

    private ReferenceAppFermatSession<CryptoWallet> appSession;

    /**
     *  UI components
     */

    Button cancel_btn;
    Button accept_btn;

    FermatTextView confirmText;


    public SendConfirmDialog(Activity a,
                             CryptoWallet moduleManager,
                               long cryptoAmount,
                             long fee,
                             long total,
                             FeeOrigin feeOrigin,
                               CryptoAddress destinationAddress,
                               String notes,
                               String deliveredByActorPublicKey,
                               Actors deliveredByActorType,
                               BlockchainNetworkType blockchainNetworkType,
                               ReferenceAppFermatSession<CryptoWallet> appSession) {
        super(a);
        this.activity = a;
        this.moduleManager=moduleManager;
        this.cryptoAmount = cryptoAmount;
        this.destinationAddress = destinationAddress;
        this.notes = notes;
        this.deliveredByActorPublicKey = deliveredByActorPublicKey;
        this.deliveredByActorType = deliveredByActorType;
        this.fee = fee;
        this.total = total;
       this.feeOrigin = feeOrigin;
        this.blockchainNetworkType = blockchainNetworkType;
        this.appSession = appSession;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();

    }

    private void setUpScreenComponents(){
        BitcoinConverter bitcoinConverter = new BitcoinConverter();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.send_confirmation_windows);

        accept_btn = (Button) findViewById(R.id.accept_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        confirmText =  (FermatTextView) findViewById(R.id.txt_confirm);

        accept_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        String newAmount = bitcoinConverter.getBTC(String.valueOf(total));
        confirmText.setText("You will sending " + newAmount +" btc. Confirm?");

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn) {
            //activity.finish();
            dismiss();
        } else if (i == R.id.accept_btn) {
            try {

                moduleManager.send(
                        cryptoAmount,
                        destinationAddress,
                        notes,
                        appSession.getAppPublicKey(),
                        moduleManager.getSelectedActorIdentity().getPublicKey(),
                        Actors.INTRA_USER,
                        deliveredByActorPublicKey,
                        deliveredByActorType,
                        ReferenceWallet.BASIC_WALLET_BITCOIN_WALLET,
                        blockchainNetworkType,
                        CryptoCurrency.BITCOIN,
                        fee,
                        feeOrigin);


            } catch (InsufficientFundsException e) {
                Toast.makeText(activity.getApplicationContext(), "Insufficient funds", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (CantSendCryptoException e) {
                appSession.getErrorManager().reportUnexpectedWalletException(Wallets.CWP_WALLET_RUNTIME_WALLET_BITCOIN_WALLET_ALL_BITDUBAI, UnexpectedWalletExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_FRAGMENT, e);
                Toast.makeText(activity.getApplicationContext(), "Send btc Error", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(), "oooopps, we have a problem here", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            dismiss();

        }

    }

}