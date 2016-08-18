package com.bitdubai.reference_niche_wallet.bitcoin_wallet.app_connection;

import android.content.Context;

import com.bitdubai.android_fermat_ccp_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums.ShowMoneyType;

import com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.utils.WalletUtils;

/**
 * Created by natalia on 22/02/16.
 */
public class BitcoinWalletBuildNotificationPainter {

    public static NotificationPainter getNotification(int code,String involvedActor, long amount, Context context )
    {
        NotificationPainter notification = null;
        try {


                 //find last transaction
                switch (code){
                    case CCPBroadcasterConstants.TRANSACTION_ARRIVE:
                       notification = new BitcoinWalletNotificationPainter(context.getResources().getString(R.string.notification_receive_btc_1),  WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " " + context.getResources().getString(R.string.notification_receive_btc_2),"","",true, Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN.getCode());

                        break;
                    case CCPBroadcasterConstants.TRANSACTION_REVERSE:
                       notification = new BitcoinWalletNotificationPainter(context.getResources().getString(R.string.notification_reversed_1), context.getResources().getString(R.string.notification_reversed_2) + " " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " " + context.getResources().getString(R.string.notification_reversed_3), "", "",true,Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_MAIN.getCode());

                        break;


                    case CCPBroadcasterConstants.PAYMENT_REQUEST_ARRIVE:
                       notification = new BitcoinWalletNotificationPainter("Received new Payment Request","You have received a Payment Request, for" + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC","","",true,Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST.getCode());

                        break;

                    case CCPBroadcasterConstants.PAYMENT_DENIED:
                       notification = new BitcoinWalletNotificationPainter("Payment Request deny","Your Payment Request, for " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC was deny.","","",true,Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST.getCode());
                        break;

                    case CCPBroadcasterConstants.PAYMENT_ERROR:

                            notification = new BitcoinWalletNotificationPainter("Payment Request reverted","Your Payment Request, for " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC was reverted.","","",true,Activities.CWP_WALLET_RUNTIME_WALLET_BASIC_WALLET_BITDUBAI_VERSION_1_PAYMENT_REQUEST.getCode());

                        break;

                }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}

