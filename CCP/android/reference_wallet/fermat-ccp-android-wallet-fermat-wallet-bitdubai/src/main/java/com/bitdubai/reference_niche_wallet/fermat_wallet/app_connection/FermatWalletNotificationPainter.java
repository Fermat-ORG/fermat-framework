package com.bitdubai.reference_niche_wallet.fermat_wallet.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.util.WalletUtils;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.exceptions.CantListReceivePaymentRequestException;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWallet;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.FermatWalletModuleTransaction;
import com.bitdubai.fermat_ccp_api.layer.wallet_module.fermat_wallet.interfaces.PaymentRequest;

import java.util.UUID;

/**
 * Created by natalia on 22/02/16.
 */
public class FermatWalletNotificationPainter {

    public static NotificationPainter getNotification(FermatWallet moduleManager,String code,String walletPublicKey, String codeReurn )
    {
        NotificationPainter notification = null;
        try {



                FermatWalletModuleTransaction transaction;
                PaymentRequest paymentRequest;
                String loggedIntraUserPublicKey;

                String[] params = code.split("_");
                String notificationType = params[0];
                String transactionId = params[1];
                //find last transaction
                switch (notificationType){
                    case "TRANSACTIONARRIVE":
                        if(moduleManager != null){
                            loggedIntraUserPublicKey = moduleManager.getActiveIdentities().get(0).getPublicKey();
                            try{
                                transaction= moduleManager.getTransaction(UUID.fromString(transactionId), walletPublicKey,loggedIntraUserPublicKey);
                                notification = new FermatWalletBuildNotificationPainter("Received money", transaction.getInvolvedActor().getName() + " send "+ WalletUtils.formatBalanceString(transaction.getAmount()) + " BTC","","",true,codeReurn);

                            }catch(Exception ex) {
                                notification = new FermatWalletBuildNotificationPainter("Received money", "BTC Arrived","","",true,codeReurn);
                            }


                        }else{
                            notification = new FermatWalletBuildNotificationPainter("Received money", "BTC Arrived","","",true,codeReurn);
                        }
                        break;
                    case "TRANSACTIONREVERSE":
                        if(moduleManager != null) {
                            loggedIntraUserPublicKey = moduleManager.getActiveIdentities().get(0).getPublicKey();

                            try{
                                 transaction = moduleManager.getTransaction(UUID.fromString(transactionId), walletPublicKey, loggedIntraUserPublicKey);
                                 notification = new FermatWalletBuildNotificationPainter("Sent Transaction reversed", "Sending " + WalletUtils.formatBalanceString(transaction.getAmount()) + " BTC could not be completed.", "", "",true,codeReurn);

                             }catch(Exception ex) {
                                notification = new FermatWalletBuildNotificationPainter("Sent Transaction reversed","Your last Sending could not be completed.","","",true,codeReurn);
                            }
                        }else
                        {
                            notification = new FermatWalletBuildNotificationPainter("Sent Transaction reversed","Your last Sending could not be completed.","","",true,codeReurn);
                        }
                        break;


                    case "PAYMENTREQUEST":
                        if(moduleManager != null){

                            paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                            notification = new FermatWalletBuildNotificationPainter("Received new Payment Request","You have received a Payment Request, for" + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC","","",true,codeReurn);
                        }
                        else
                        {
                            notification = new FermatWalletBuildNotificationPainter("Received new Payment Request","You have received a new Payment Request.","","",true,codeReurn);
                        }
                        break;

                    case "PAYMENTDENIED":
                        if(moduleManager != null){
                            paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                            notification = new FermatWalletBuildNotificationPainter("Payment Request deny","Your Payment Request, for " + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC was deny.","","",true,codeReurn);
                        }
                        else
                        {
                            notification = new FermatWalletBuildNotificationPainter("Payment Request deny","Your Payment Request was deny.","","",true,codeReurn);
                        }
                        break;

                    case "PAYMENTERROR":
                        if(moduleManager != null){
                            paymentRequest = moduleManager.getPaymentRequest(UUID.fromString(transactionId));
                            notification = new FermatWalletBuildNotificationPainter("Payment Request reverted","Your Payment Request, for " + WalletUtils.formatBalanceString(paymentRequest.getAmount()) + " BTC was reverted.","","",true,codeReurn);
                        }
                        else
                        {
                            notification = new FermatWalletBuildNotificationPainter("Payment Request reverted","Your Last Payment Request was reverted.","","",true,codeReurn);
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
