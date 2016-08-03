package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;

/**
 * Created by natalia on 22/02/16.
 */
public class BitcoinWalletBuildNotificationPainter {

    public static NotificationPainter getNotification(int code,String involvedActor, long amount, String codeReturn )
    {
        NotificationPainter notification = null;
        try {


                 //find last transaction
                switch (code){
                    case CCPBroadcasterConstants.TRANSACTION_ARRIVE:
                       notification = new BitcoinWalletNotificationPainter("Received money",  WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC Arrived","","",true,codeReturn);

                        break;
                    case CCPBroadcasterConstants.TRANSACTION_REVERSE:
                       notification = new BitcoinWalletNotificationPainter("Sent Transaction reversed", "Sending " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC could not be completed.", "", "",true,codeReturn);

                        break;


                    case CCPBroadcasterConstants.PAYMENT_REQUEST_ARRIVE:
                       notification = new BitcoinWalletNotificationPainter("Received new Payment Request","You have received a Payment Request, for" + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC","","",true,codeReturn);

                        break;

                    case CCPBroadcasterConstants.PAYMENT_DENIED:
                       notification = new BitcoinWalletNotificationPainter("Payment Request deny","Your Payment Request, for " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC was deny.","","",true,codeReturn);
                        break;

                    case CCPBroadcasterConstants.PAYMENT_ERROR:

                            notification = new BitcoinWalletNotificationPainter("Payment Request reverted","Your Payment Request, for " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC was reverted.","","",true,codeReturn);

                        break;

                }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}

