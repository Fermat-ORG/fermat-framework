package com.bitdubai.sub_app.intra_user_community.interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * @author Jose Manuel De Sousa 12/12/2015.
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
