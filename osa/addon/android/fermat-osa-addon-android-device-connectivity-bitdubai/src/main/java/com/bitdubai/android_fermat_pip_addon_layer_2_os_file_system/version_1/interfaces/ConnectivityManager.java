package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.exceptions.CantGetActiveConnectionException;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.exceptions.CantGetConnectionsException;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.exceptions.CantGetIsConnectedException;

import java.util.List;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces.ConnectivityManager</code> is a interface
 *     that define the methods to manage device connection network.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   04/05/15.
 * */

 public interface ConnectivityManager {

    public List<Network> getConnections() throws CantGetConnectionsException;

    public Network getActiveConnection() throws CantGetActiveConnectionException;

    public boolean isConnected(ConnectionType redType) throws CantGetIsConnectedException;

    public void setContext (Object context);
}
