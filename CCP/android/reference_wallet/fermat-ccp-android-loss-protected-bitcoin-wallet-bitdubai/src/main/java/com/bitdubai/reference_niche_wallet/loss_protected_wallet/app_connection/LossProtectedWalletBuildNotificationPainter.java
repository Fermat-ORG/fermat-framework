package com.bitdubai.reference_niche_wallet.loss_protected_wallet.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;

import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;



/**
 * Created by natalia on 22/02/16.
 */
public class LossProtectedWalletBuildNotificationPainter {

    public static NotificationPainter getNotification(int code,String involvedActor, long amount,String codeReturn)
    {
        NotificationPainter notification = null;
        try {

            switch (code){
                case CCPBroadcasterConstants.TRANSACTION_ARRIVE:
                    notification = new LossProtectedWalletNotificationPainter("Received money",  WalletUtils.formatBalanceStringNotDecimal(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC Arrived","","",true,codeReturn);

                    break;
                case CCPBroadcasterConstants.TRANSACTION_REVERSE:
                    notification = new LossProtectedWalletNotificationPainter("Sent Transaction reversed", "Sending " + WalletUtils.formatBalanceStringNotDecimal(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC could not be completed.", "", "",true,codeReturn);

                    break;


                case CCPBroadcasterConstants.PAYMENT_REQUEST_ARRIVE:
                    notification = new LossProtectedWalletNotificationPainter("Received new Payment Request","You have received a Payment Request, for" + WalletUtils.formatBalanceStringNotDecimal(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC","","",true,codeReturn);

                    break;

                case CCPBroadcasterConstants.PAYMENT_DENIED:
                    notification = new LossProtectedWalletNotificationPainter("Payment Request deny","Your Payment Request, for " + WalletUtils.formatBalanceStringNotDecimal(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC was deny.","","",true,codeReturn);
                    break;

                case CCPBroadcasterConstants.PAYMENT_ERROR:

                    notification = new LossProtectedWalletNotificationPainter("Payment Request reverted","Your Payment Request, for " + WalletUtils.formatBalanceStringNotDecimal(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC was reverted.","","",true,codeReturn);

                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
