package com.bitdubai.sub_app.crypto_broker_community.interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public interface MessageReceiver {

    /**
     * It is called when a message is received inside a broadcast reveiver function
     *
     * @param data    Intent data passed through local broadcast
     * @param context Context
     */
    void onMessageReceive(Context context, Intent data);


    /**
     * Get explicit intent channel where you can able to receive message through local broadcast
     *
     * @return Intent
     */
    IntentFilter getBroadcastIntentChannel();

}
