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
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.ReferenceWallet;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListCryptoWalletIntraUserIdentityException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantApproveLossProtectedRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetCryptoLossProtectedWalletException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedRequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.session.LossProtectedWalletSession;

import android.content.Context;

import java.util.UUID;

/**
 * Created by natalia on 06/05/16.
 */
public class Confirm_Send_Payment_Dialog extends Dialog implements
        View.OnClickListener {



public Activity activity;
public Dialog d;

private BlockchainNetworkType blockchainNetworkType;
private long cryptoAmount;

private UUID requestId;

/**
 *  Deals with crypto wallet interface
 */

private LossProtectedWallet lossProtectedWallet;

private LossProtectedWalletSession appSession;
        /**
         *  UI components
         */
        Button cancel_btn;
        Button accept_btn;


public Confirm_Send_Payment_Dialog(Context a,long cryptoAmount, UUID  requestId,
                                   LossProtectedWalletSession appSession, BlockchainNetworkType blockchainNetworkType,LossProtectedWallet lossProtectedWallet){
    super(a);

    this.requestId = requestId;
    this.appSession = appSession;
    this.cryptoAmount = cryptoAmount;
    this.blockchainNetworkType = blockchainNetworkType;
    this.lossProtectedWallet = lossProtectedWallet;
 }


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpScreenComponents();

        }

private void setUpScreenComponents(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.confirmation_window);

        accept_btn = (Button) findViewById(R.id.accept_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);

        accept_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel_btn) {
            //activity.finish();
            dismiss();
        }else if( i == R.id.accept_btn){
            try {

                //tengo que validar el balance real
                long realBalance = lossProtectedWallet.getRealBalance(appSession.getAppPublicKey(), blockchainNetworkType);

                if(cryptoAmount < realBalance) //Balance value is greater than send amount
                {
                    lossProtectedWallet.approveRequest(this.requestId
                            , appSession.getIntraUserModuleManager().getPublicKey());
                    Toast.makeText(this.activity, "Request accepted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this.activity, "Action not allowed, Insufficient Funds.", Toast.LENGTH_LONG).show();
                }


            } catch (CantListCryptoWalletIntraUserIdentityException e) {
                e.printStackTrace();
            } catch (LossProtectedPaymentRequestNotFoundException e) {
                e.printStackTrace();
            } catch (CantGetLossProtectedBalanceException e) {
                e.printStackTrace();
            } catch (CantApproveLossProtectedRequestPaymentException e) {
                e.printStackTrace();
            } catch (LossProtectedRequestPaymentInsufficientFundsException e) {
                e.printStackTrace();
            } catch (CantGetCryptoLossProtectedWalletException e) {
                e.printStackTrace();
            }
            Toast.makeText(this.activity, "Sending...", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }



}