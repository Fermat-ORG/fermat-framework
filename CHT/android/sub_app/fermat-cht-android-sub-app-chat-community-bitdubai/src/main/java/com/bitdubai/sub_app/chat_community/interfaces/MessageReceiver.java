package com.bitdubai.sub_app.chat_community.interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * MessageReceiver
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
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
