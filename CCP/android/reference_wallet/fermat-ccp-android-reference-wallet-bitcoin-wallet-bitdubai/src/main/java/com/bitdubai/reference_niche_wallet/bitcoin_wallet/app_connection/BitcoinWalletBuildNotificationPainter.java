package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.util.WalletUtils;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.CryptoWalletTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.interfaces.PaymentRequest;

import java.util.UUID;

/**
 * Created by natalia on 22/02/16.
 */
public class BitcoinWalletBuildNotificationPainter {

    public static NotificationPainter getNotification(CryptoWallet moduleManager,String code,String walletPublicKey, String codeReturn )
    {
        NotificationPainter notification = null;
        try {



                CryptoWalletTransaction transaction;
                PaymentRequest paymentRequest;
                String loggedIntraUserPublicKey;

                String[] params = code.split("_");
                String notificationType = params[0];
                String transactionId = params[1];
                //find last transaction
                switch (notificationType){
                    case "TRANSACTIONARRIVE":
                        if(moduleManager != null){
                            loggedIntraUserPublicKey = moduleManager.getSelectedActorIdentity().getPublicKey();
                            try{
                                transaction= moduleManager.getTransaction(UUID.fromString(transactionId), walletPublicKey,loggedIntraUserPublicKey);
                                notification = new BitcoinWalletNotificationPainter("Received money", transaction.getInvolvedActor().getName() + " send "+ WalletUtils.formatBalanceString(transaction.getTotal()) + " BTC","","",true,codeReturn);

                            }catch(Exception ex) {
                                notification = new BitcoinWalletNotificationPainter("Received money", "BTC Arrived","","",true,codeReturn);
                            }


                        }else{
                            notification = new BitcoinWalletNotificationPainter("Received money", "BTC Arrived","","",true,codeReturn);
                        }
                        break;
                    case "TRANSACTIONREVERSE":
                        if(moduleManager != null) {
                            loggedIntraUserPublicKey = moduleManager.getActiveIdentities().get(0).getPublicKey();

                            try{
                                 transaction = moduleManager.getTransaction(UUID.fromString(transactionId), walletPublicKey, loggedIntraUserPublicKey);
                                 notification = new BitcoinWalletNotificationPainter("Sent Transaction reversed", "Sending " + WalletUtils.formatBalanceString(transaction.getTotal()) + " BTC could not be completed.", "", "",true,codeReturn);

                             }catch(Exception ex) {
                                notification = new BitcoinWalletNotificationPainter("Sent Transaction reversed","Your last Sending could not be completed.","","",true,codeReturn);
                            }
                        }else
                        {
                            notification = new BitcoinWalletNotificationPainter("Sent Transaction reversed","Your last Sending could not be completed.","","",true,codeReturn);
                        }
                        break;


                    case "PAYMENTREQUEST":
                        if(moduleManager != null){

                            paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                            notification = new BitcoinWalletNotificationPainter("Received new Payment Request","You have received a Payment Request, for" + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC","","",true,codeReturn);
                        }
                        else
                        {
                            notification = new BitcoinWalletNotificationPainter("Received new Payment Request","You have received a new Payment Request.","","",true,codeReturn);
                        }
                        break;

                    case "PAYMENTDENIED":
                        if(moduleManager != null){
                            paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                            notification = new BitcoinWalletNotificationPainter("Payment Request deny","Your Payment Request, for " + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC was deny.","","",true,codeReturn);
                        }
                        else
                        {
                            notification = new BitcoinWalletNotificationPainter("Payment Request deny","Your Payment Request was deny.","","",true,codeReturn);
                        }
                        break;

                    case "PAYMENTERROR":
                        if(moduleManager != null){
                            paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                            notification = new BitcoinWalletNotificationPainter("Payment Request reverted","Your Payment Request, for " + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC was reverted.","","",true,codeReturn);
                        }
                        else
                        {
                            notification = new BitcoinWalletNotificationPainter("Payment Request reverted","Your Last Payment Request was reverted.","","",true,codeReturn);
                        }
                        break;

                }


        } catch (CantListReceivePaymentRequestException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
