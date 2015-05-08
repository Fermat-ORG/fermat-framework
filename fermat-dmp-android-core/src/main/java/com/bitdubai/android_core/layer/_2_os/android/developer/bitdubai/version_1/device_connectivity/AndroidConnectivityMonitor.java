package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;

import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectionType;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectivityMonitor;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.Network;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.DealsWithEvents;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventSource;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventType;

/**
 * Created by toshiba on 08/05/2015.
 */
public class AndroidConnectivityMonitor extends BroadcastReceiver implements ConnectivityMonitor,DealsWithEvents {

    Network connectionIfo = new AndroidNetwork();
    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;


    /**
     * ConnectivityMonitor Interface implementation.
     */

    @Override
    public void start(){
      //  IntentFilter filter = new IntentFilter(ACTION);
       //  this.registerReceiver(this, filter);

    }

    @Override
    public void stop(){
        //stop
       // unregisterReceiver(this);
    }

    public Network getConnectionInfo(){
        return connectionIfo;
    }
    /**
     * BroadcastReceiver Interface implementation.
     */

        @Override
        public void onReceive( Context context, Intent intent )
        {
            android.net.ConnectivityManager connectivityManager = ( android.net.ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
          //  NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(android.net.ConnectivityManager.TYPE_MOBILE );
            if ( activeNetInfo != null )
            {
                connectionIfo.setIsConnected(activeNetInfo.isConnected());

                switch(activeNetInfo.getType()) {

                    case android.net.ConnectivityManager.TYPE_MOBILE_DUN:
                        connectionIfo.setType(ConnectionType.MOBILE_DUN);

                        break;
                    case android.net.ConnectivityManager.TYPE_MOBILE_HIPRI:
                        connectionIfo.setType(ConnectionType.MOBILE_HIPRI);

                        break;
                    case android.net.ConnectivityManager.TYPE_MOBILE_SUPL:
                        connectionIfo.setType(ConnectionType.MOBILE_SUPL);

                        break;
                    case android.net.ConnectivityManager.TYPE_MOBILE_MMS:
                        connectionIfo.setType(ConnectionType.MOBILE_MMS);

                        break;
                    case android.net.ConnectivityManager.TYPE_MOBILE:  //0
                        connectionIfo.setType(ConnectionType.MOBILE_DATA);
                        break;
                    case android.net.ConnectivityManager.TYPE_WIFI: //1
                        connectionIfo.setType(ConnectionType.WI_FI);

                        break;
                    case android.net.ConnectivityManager.TYPE_WIMAX: //6
                        connectionIfo.setType(ConnectionType.WIMAX);

                        break;
                    case android.net.ConnectivityManager.TYPE_ETHERNET://9
                        connectionIfo.setType(ConnectionType.ETHERNET);

                        break;
                    case android.net.ConnectivityManager.TYPE_BLUETOOTH://7
                        connectionIfo.setType(ConnectionType.BLUETOOTH);

                        break;
                }
                 PlatformEvent platformEvent = eventManager.getNewEvent(EventType.DEVICE_CONNECTIVITY_NETWORK_CHANGE);
                platformEvent.setSource(EventSource.DEVICE_CONNECTIVITY);
                eventManager.raiseEvent(platformEvent);
            }

        }


    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
