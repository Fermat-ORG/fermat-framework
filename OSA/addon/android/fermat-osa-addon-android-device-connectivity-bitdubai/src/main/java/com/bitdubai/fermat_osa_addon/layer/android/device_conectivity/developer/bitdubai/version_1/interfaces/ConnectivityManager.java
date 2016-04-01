package com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.exceptions.CantGetActiveConnectionException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.exceptions.CantGetConnectionsException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.exceptions.CantGetIsConnectedException;
import com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.structure.NetworkStateReceiver;

import java.util.List;

/**
 *
 *  <p>The abstract class <code>ConnectivityManager</code> is a interface
 *     that define the methods to manage device connection network.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   04/05/15.
 * */

 public interface ConnectivityManager {

    List<Network> getConnections() throws CantGetConnectionsException;

    Network getActiveConnection() throws CantGetActiveConnectionException;

    boolean isConnected(ConnectionType redType) throws CantGetIsConnectedException;

    void setContext(Object context);

    void addListener(NetworkStateReceiver.NetworkStateReceiverListener networkStateReceiver);
}
