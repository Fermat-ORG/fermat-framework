package com.bitdubai.reference_niche_wallet.loss_protected_wallet.app_connection;

import android.content.Context;

import com.bitdubai.android_fermat_ccp_loss_protected_wallet_bitcoin.R;
import com.bitdubai.fermat_android_api.engine.NotificationPainter;
import com.bitdubai.fermat_ccp_api.all_definition.constants.CCPBroadcasterConstants;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.enums.ShowMoneyType;
import com.bitdubai.reference_niche_wallet.loss_protected_wallet.common.utils.WalletUtils;



/**
 * Created by natalia on 22/02/16.
 * updated by Andres Abreu on 18/08/16
 */
public class LossProtectedWalletBuildNotificationPainter {

    public static NotificationPainter getNotification(int code,String involvedActor, long amount,String codeReturn, Context context)
    {
        NotificationPainter notification = null;
        try {

            switch (code){
                case CCPBroadcasterConstants.TRANSACTION_ARRIVE:
                    notification = new LossProtectedWalletNotificationPainter(context.getResources().getString(R.string.lpw_notification_transaction_arrive_1),  WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " "+context.getResources().getString(R.string.lpw_notification_transaction_arrive_2),"","",true,codeReturn);

                    break;
                case CCPBroadcasterConstants.TRANSACTION_REVERSE:
                    notification = new LossProtectedWalletNotificationPainter(context.getResources().getString(R.string.lpw_notification_transaction_reverse_1), context.getResources().getString(R.string.lpw_notification_transaction_reverse_2)+" " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " "+context.getResources().getString(R.string.lpw_notification_transaction_reverse_3), "", "",true,codeReturn);

                    break;


                case CCPBroadcasterConstants.PAYMENT_REQUEST_ARRIVE:
                    notification = new LossProtectedWalletNotificationPainter(context.getResources().getString(R.string.lpw_notification_request_arrive_1),context.getResources().getString(R.string.lpw_notification_transaction_reverse_2)+" " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " BTC","","",true,codeReturn);

                    break;

                case CCPBroadcasterConstants.PAYMENT_DENIED:
                    notification = new LossProtectedWalletNotificationPainter(context.getResources().getString(R.string.lpw_notification_payment_denied_1),context.getResources().getString(R.string.lpw_notification_transaction_reverse_2)+" " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " "+context.getResources().getString(R.string.lpw_notification_payment_denied_3),"","",true,codeReturn);
                    break;

                case CCPBroadcasterConstants.PAYMENT_ERROR:

                    notification = new LossProtectedWalletNotificationPainter(context.getResources().getString(R.string.lpw_notification_payment_error_1),context.getResources().getString(R.string.lpw_notification_payment_error_2)+" " + WalletUtils.formatBalanceString(amount, ShowMoneyType.BITCOIN.getCode()) + " "+context.getResources().getString(R.string.lpw_notification_payment_error_3),"","",true,codeReturn);
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return notification;
    }
}
