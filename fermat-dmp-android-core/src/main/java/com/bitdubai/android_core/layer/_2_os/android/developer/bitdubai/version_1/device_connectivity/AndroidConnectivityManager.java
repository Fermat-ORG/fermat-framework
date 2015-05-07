package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1.device_connectivity;

import android.bluetooth.le.ScanResult;

import android.content.Context;

import com.bitdubai.fermat_api.CantGetActiveConnectionException;
import com.bitdubai.fermat_api.CantGetConnectionsException;
import com.bitdubai.fermat_api.CantGetIsConnectedException;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectionCapacity;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectionType;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.ConnectivityManager;
import com.bitdubai.fermat_api.layer._2_os.device_connectivity.Network;

import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toshiba on 04/05/2015.
 */
public class AndroidConnectivityManager implements ConnectivityManager {

    /**
     * ConnectivityManager Interface member variables.
     */
    private Context context;

    private List<Network> conecctions;


    /**
     * ConnectivityManager Interface implementation.
     */

    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }

    @Override
    public List<Network> getConnections() throws CantGetConnectionsException {

        android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netinfo = connection.getAllNetworkInfo();

        conecctions = new ArrayList<Network>();

        for (NetworkInfo n : netinfo)
        {
            Network connectionIfo = new AndroidNetwork();


            NetworkInfo.DetailedState state = n.getDetailedState();
            switch( n.getType()) {

                case android.net.ConnectivityManager.TYPE_MOBILE_DUN://4
                    connectionIfo.setType(ConnectionType.MOBILE_DUN);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_MOBILE_HIPRI://5
                    connectionIfo.setType(ConnectionType.MOBILE_HIPRI);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_MOBILE_SUPL://3
                    connectionIfo.setType(ConnectionType.MOBILE_SUPL);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_MOBILE_MMS://2
                    connectionIfo.setType(ConnectionType.MOBILE_MMS);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_MOBILE:  //0
                    connectionIfo.setType(ConnectionType.MOBILE_DATA);
                    break;
                case android.net.ConnectivityManager.TYPE_WIFI: //1
                    connectionIfo.setType(ConnectionType.WI_FI);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_WIMAX: //6
                    connectionIfo.setType(ConnectionType.WIMAX);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_ETHERNET://9
                    connectionIfo.setType(ConnectionType.ETHERNET);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
                case android.net.ConnectivityManager.TYPE_BLUETOOTH://7
                    connectionIfo.setType(ConnectionType.BLUETOOTH);
                    connectionIfo.setIsConnected(n.isConnected());
                    this.conecctions.add(connectionIfo);
                    break;
            }


        }

            return this.conecctions;

    }

    /**
     * Return the network who is connected in the phone
     */
    public Network getActiveConnection() throws CantGetActiveConnectionException {
        android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connection.getActiveNetworkInfo();
        Network connectionIfo = new AndroidNetwork();

        connectionIfo.setIsConnected(netinfo.isConnected());

        switch(netinfo.getType()) {

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

        return connectionIfo;
    }




    @Override
    public boolean isConnected(ConnectionType redType) throws CantGetIsConnectedException {
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
            case MOBILE_DUN:
                networkType = 4;
                break;
            case MOBILE_HIPRI:
                networkType = 5;
                break;
            case MOBILE_SUPL:
                networkType =3;
                break;
            case MOBILE_MMS:
                networkType =2;
                break;
        }

        NetworkInfo info = connection.getNetworkInfo(networkType);

        return info.isConnected();
    }
}
