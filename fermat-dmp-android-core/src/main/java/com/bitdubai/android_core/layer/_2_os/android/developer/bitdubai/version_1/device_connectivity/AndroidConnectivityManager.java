package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_connectivity;

import android.content.Context;

import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectionCapacity;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectionType;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectivityManager;

import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;

/**
 * Created by toshiba on 04/05/2015.
 */
public class AndroidConnectivityManager implements ConnectivityManager {

    /**
     * ConnectivityManager Interface member variables.
     */
    private Context context;


    /**
     * ConnectivityManager Interface implementation.
     */

    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }

    @Override
    public ConnectionType getConnectionType(){
        android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connection.getActiveNetworkInfo();

        switch(netinfo.getType()) {

            case android.net.ConnectivityManager.TYPE_MOBILE:  //0
               return ConnectionType.MOBILE_DATA;

            case android.net.ConnectivityManager.TYPE_WIFI: //1
                return ConnectionType.WI_FI;

            case android.net.ConnectivityManager.TYPE_WIMAX: //6
                return ConnectionType.WIMAX;

            case android.net.ConnectivityManager.TYPE_ETHERNET://9
                return ConnectionType.ETHERNET;

            case android.net.ConnectivityManager.TYPE_BLUETOOTH://7
                return ConnectionType.BLUETOOTH;

            default:
                return null;
        }


    }

    @Override
    public ConnectionCapacity getConnectionIntensity(){
      //  android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo info = connection.getNetworkInfo(1);

       // WifiP2pManager wifiP2pManager = (WifiP2pManager)context.getSystemService(context.WIFI_P2P_SERVICE);

        return null;
    }

    @Override
    public boolean isConnected(ConnectionType redType){
        android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        int networkType =1;
        switch(redType) {

            case WI_FI:
                networkType = 1;
                break;
            case BLUETOOTH:
                networkType = 7;
                break;
            case MOBILE_DATA:
                networkType = 0;
                break;
            case ETHERNET:
                networkType = 9;
                break;
            case WIMAX:
                networkType = 6;
                break;
        }


        NetworkInfo info = connection.getNetworkInfo(networkType);

        return info.isConnected();
    }
}
