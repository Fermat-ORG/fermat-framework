package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_android_api.layer.definition.wallet.views.FermatTextView;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_bch_api.layer.definition.crypto_fee.FeeOrigin;
import com.bitdubai.fermat_ccp_api.all_definition.util.BitcoinConverter;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantSendLossProtectedCryptoException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;


/**
 * Created by Joaquin Carrasuquero on 11/04/16.
 */

public class ConfirmSendDialog_feeCase extends Dialog implements
        View.OnClickListener {



    public Activity activity;
    public Dialog d;

    private BlockchainNetworkType blockchainNetworkType;
    private long cryptoAmount;
    private long fee;
    private long total;
    private CryptoAddress destinationAddress;
    private String notes;
    private String walletPublicKey;
    private String deliveredByActorPublicKey;
    private Actors deliveredByActorType;
    private String deliveredToActorPublicKey;
    private Actors deliveredToActorType;
    private ReferenceWallet referenceWallet;
    private FeeOrigin feeOrigin;

    /**
     *  Deals with crypto wallet interface
     */

    private LossProtectedWallet lossProtectedWallet;

    private ReferenceAppFermatSession<LossProtectedWallet> appSession;

    /**
     *  UI components
     */

    Button cancel_btn;
    Button accept_btn;
    FermatTextView confirmText;

    public ConfirmSendDialog_feeCase(Activity a,
                                     LossProtectedWallet lossProtectedWallet,
                                     long cryptoAmount,
                                     long fee,
                                     long total,
                                     FeeOrigin feeOrigin,
                                     CryptoAddress destinationAddress,
                                     String notes,
                                     String deliveredByActorPublicKey,
                                     Actors deliveredByActorType,
                                     BlockchainNetworkType blockchainNetworkType,
                                     ReferenceAppFermatSession<LossProtectedWallet> appSession) {
        super(a);
        this.activity = a;
        this.lossProtectedWallet=lossProtectedWallet;
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
        setContentView(R.layout.confirmation_window);

        accept_btn = (Button) findViewById(R.id.accept_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        confirmText =  (FermatTextView) findViewById(R.id.description_msg);

        accept_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        String newAmount = bitcoinConverter.getBTC(String.valueOf(total));
        String confirmationText = getContext().getResources().getString(R.string.confirmation_text);
        confirmationText = confirmationText.replace("Amount", newAmount);
        confirmText.setText(confirmationText);

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn) {
            //activity.finish();
            dismiss();
        }else if( i == R.id.accept_btn){
            try {

                lossProtectedWallet.send(
                        cryptoAmount,
                        destinationAddress,
                        notes,
                        appSession.getAppPublicKey(),
                        lossProtectedWallet.getActiveIdentities().get(0).getPublicKey(),
                        Actors.INTRA_USER,
                        deliveredByActorPublicKey,
                        deliveredByActorType,
                        ReferenceWallet.BASIC_WALLET_LOSS_PROTECTED_WALLET,
                        blockchainNetworkType,
                        CryptoCurrency.BITCOIN,
                        fee,
                        feeOrigin);

                Toast.makeText(this.activity, getContext().getString(R.string.Sending_text), Toast.LENGTH_SHORT).show();

            } catch (CantSendLossProtectedCryptoException e) {
                e.printStackTrace();
                Toast.makeText(this.activity, getContext().getString(R.string.Unexpected_error), Toast.LENGTH_SHORT).show();
            } catch (LossProtectedInsufficientFundsException e) {
                e.printStackTrace();
                Toast.makeText(this.activity, getContext().getString(R.string.Insufficient_funds), Toast.LENGTH_SHORT).show();
        /*    } catch (CantGetLossProtectedBalanceException e) {
                Toast.makeText(this.activity, "Unexpected error", Toast.LENGTH_SHORT).show();
        */    }
             catch (Exception e) {
                Toast.makeText(activity.getApplicationContext(), getContext().getResources().getString(R.string.Whooops_text2), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            dismiss();
        }
    }



}