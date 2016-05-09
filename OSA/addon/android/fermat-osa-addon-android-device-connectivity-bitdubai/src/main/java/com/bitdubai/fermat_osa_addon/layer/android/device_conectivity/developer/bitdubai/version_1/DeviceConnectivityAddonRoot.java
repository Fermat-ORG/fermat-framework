package com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1;

import android.content.Context;
import android.net.NetworkInfo;

import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.exceptions.CantGetActiveConnectionException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.exceptions.CantGetConnectionsException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.exceptions.CantGetIsConnectedException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.ConnectionType;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.ConnectivityAgent;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.ConnectivityManager;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces.Network;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.structure.DeviceNetwork;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.structure.NetworkStateReceiver;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.DealsWithErrors;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.DealsWithEvents;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * This addon handles a layer of Device Connectivity representation.
 * Encapsulates all the necessary functions for recovering the network to which the device is connected.
 *
 * * * *
 */

public class DeviceConnectivityAddonRoot implements Addon,ConnectivityManager,DealsWithErrors, DealsWithEvents, Service {

    /**
     * ConnectivityManager Interface member variables.
     */
    private Context context;

    private List<Network> conecctions;

    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithEvents Interface member variables.
     */
    EventManager eventManager;

    /**
     * Plugin Interface member variables.
     */
    private UUID pluginId;
    private NetworkStateReceiver networkState = NetworkStateReceiver.getInstance();

    @Override
    public FermatManager getManager() {
        return null;
    }

    public UUID getPluginId() {
		return pluginId;
	}

	public void setPluginId(UUID pluginId) {
		this.pluginId = pluginId;
	}

	/**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;
    ConnectivityAgent monitor;


    /**
     * ConnectivityManager Interface implementation.
     */

    /**
     *<p> This method set the context object
     *
     * @param context Android Context object
     */
    @Override
    public void setContext (Object context){
        this.context = (Context)context;
    }

    @Override
    public void addListener(NetworkStateReceiver.NetworkStateReceiverListener networkStateReceiver) {
        this.networkState.addListener(networkStateReceiver);
    }

    /**
     * <p> This method Returns a list of networks available on the device.
     *
     * @return List of Network interface object
     * @throws CantGetConnectionsException
     */
    @Override
    public List<Network> getConnections() throws CantGetConnectionsException {

        android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netinfo = connection.getAllNetworkInfo();

        conecctions = new ArrayList<Network>();

        for (NetworkInfo n : netinfo)
        {
            Network connectionIfo = new DeviceNetwork();


            
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
                default:
                	break;
            }


        }

        return this.conecctions;

    }


    /**
     *<p>Return the network who is connected in the phone
     *
     * @return Network objects
     * @throws CantGetActiveConnectionException
     */
    @Override
    public Network getActiveConnection() throws CantGetActiveConnectionException {
        android.net.ConnectivityManager connection = (android.net.ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = connection.getActiveNetworkInfo();
        Network connectionIfo = new DeviceNetwork();

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
            default:
            	break;
        }

        return connectionIfo;
    }


    /**
     *<p>Returns whether the device is connected to a network.
     *
     * @param redType ConnectionType enum object
     * @return boolean if connected
     * @throws CantGetIsConnectedException
     */
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

    /**
     *DealsWithErrors Interface implementation.
     */
    @Override
    public void setErrorManager(ErrorManager errorManager) {
        this.errorManager = errorManager;
    }


    /**
     * DealWithEvents Interface implementation.
     */
    @Override
    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }


    public EventManager getEventManager() {
		return eventManager;
	}

	/**
     * Service Interface implementation.
     */
    @Override
    public void start()  throws CantStartPluginException {
        /**
         * I will start the Monitor Agent.
         */
//        try {
//            this.monitor = new DeviceConnectivityAgent(getActiveConnection().getType());
//        }
//        catch (CantGetActiveConnectionException cantGetActiveConnectionException) {
//        	cantGetActiveConnectionException.printStackTrace();
//        }
//        try {
//
//            ((DealsWithErrors) this.monitor).setErrorManager(this.errorManager);
//            this.monitor.start();
//        }
//        catch (CantStartAgentException cantStartAgentException) {
//
//        	cantStartAgentException.printStackTrace();
//            /**
//             * I cant continue if this happens.
//             */
//            errorManager.reportUnexpectedAddonsException(Addons.DEVICE_CONNECTIVITY, UnexpectedAddonsExceptionSeverity.DISABLES_SOME_FUNCTIONALITY_WITHIN_THIS_ADDONS, cantStartAgentException);
//
//            throw new CantStartPluginException(Plugins.BITDUBAI_DEVICE_CONNECTIVITY);
//        }

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

//       this.monitor.stop();
        this.networkState.clear();
        this.serviceStatus = ServiceStatus.STOPPED;
    }

    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }
}
