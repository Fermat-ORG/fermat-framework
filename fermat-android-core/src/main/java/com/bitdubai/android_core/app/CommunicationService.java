package com.bitdubai.android_core.app;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by mati on 2016.03.09..
 */
public class CommunicationService extends Service{


    private static final String TAG = "CommService";
    private static final int MSG_UNSOL_MESSAGE = 1;

    /**
     * Handler of incoming messages from service.
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UNSOL_MESSAGE:
                    Log.d(TAG, "Received from service: " + msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /**
     * Target we publish for clients to send messages to IncomingHandler.
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());
    boolean mBound;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "entering onBind");
        return mMessenger.getBinder();
    }
}
