package com.bitdubai.fermat_api.layer.osa_android;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;

/**
 * <p>The abstract class <code>ConnectivityManager</code> is a interface
 * that define the methods to manage device connection network.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 04/05/15.
 */

public interface ConnectivityManager extends FermatManager {

//    List<Network> getConnections() throws CantGetConnectionsException;
//
//    Network getActiveConnection() throws CantGetActiveConnectionException;
//
//    boolean isConnected(ConnectionType redType) throws CantGetIsConnectedException;

//    void setContext(Object context);

//    void addListener(NetworkStateReceiver.NetworkStateReceiverListener networkStateReceiver);

    void registerListener(NetworkStateReceiver networkStateReceiver);

    void unregisterListener(NetworkStateReceiver networkStateReceiver);

    boolean isOnline();

    boolean isConnected(ConnectionType connectionType) throws Exception;
}
