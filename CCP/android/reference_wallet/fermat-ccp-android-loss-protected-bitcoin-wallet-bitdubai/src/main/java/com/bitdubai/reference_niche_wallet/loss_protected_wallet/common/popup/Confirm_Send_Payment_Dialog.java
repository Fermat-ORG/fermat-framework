package com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.popup;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces.ReferenceAppFermatSession;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantApproveLossProtectedRequestPaymentException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.CantGetLossProtectedBalanceException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedPaymentRequestNotFoundException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.exceptions.LossProtectedRequestPaymentInsufficientFundsException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.loss_protected_wallet.interfaces.LossProtectedWallet;


import java.util.UUID;

/**
 * Created by natalia on 06/05/16.
 */
public class Confirm_Send_Payment_Dialog extends Dialog implements
        View.OnClickListener {



public Context context;
public Dialog d;

private BlockchainNetworkType blockchainNetworkType;
private long cryptoAmount;

private UUID requestId;

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


public Confirm_Send_Payment_Dialog(Context a,long cryptoAmount, UUID  requestId,
                                   ReferenceAppFermatSession<LossProtectedWallet> appSession, BlockchainNetworkType blockchainNetworkType,LossProtectedWallet lossProtectedWallet){
    super(a);

    this.requestId = requestId;
    this.appSession = appSession;
    this.cryptoAmount = cryptoAmount;
    this.blockchainNetworkType = blockchainNetworkType;
    this.lossProtectedWallet = lossProtectedWallet;
    this.context = a;
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
                            , lossProtectedWallet.getSelectedActorIdentity().getPublicKey());
                    Toast.makeText(this.context, "Request accepted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this.context, "Action not allowed, Insufficient Funds.", Toast.LENGTH_LONG).show();
                }


            } catch (LossProtectedPaymentRequestNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(context, "Payment Request Not Found error", Toast.LENGTH_SHORT).show();
            } catch (CantGetLossProtectedBalanceException e) {
                Toast.makeText(context, "Cant Get Balance error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (CantApproveLossProtectedRequestPaymentException e) {
                Toast.makeText(context, "Cant Approve Request Payment", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (LossProtectedRequestPaymentInsufficientFundsException e) {
                Toast.makeText(context, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            catch (Exception e) {
                Toast.makeText(context, "Unexpected error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Toast.makeText(context, "Sending...", Toast.LENGTH_SHORT).show();
            dismiss();


        }
    }



}